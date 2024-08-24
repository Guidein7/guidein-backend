package com.GuideIn.subscription;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.plivo.DLTdetails;
import com.GuideIn.plivo.PlivoMessageService;
import com.GuideIn.razorpay.RazorpayConfig;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
	
	@Autowired
	SubscriptionRepository repo;
	
	@Autowired
	RazorpayConfig config;
	
	@Autowired
	PlivoMessageService messageService;

	public SubscritionResponse subscribe(SubscriptionRequest request) {

		SubscritionResponse response = new SubscritionResponse();
		try {
			Subscription subscription = repo.findByEmailAndActive(request.getEmail(), true).orElse(null);
			
			if(subscription == null) {
				
				RazorpayClient razorpay = new RazorpayClient(config.getKeyId(), config.getSecret());

				JSONObject orderRequest = new JSONObject();
				orderRequest.put("amount", request.getPlan().getPrice() * 100);
				orderRequest.put("currency","INR");
				JSONObject notes = new JSONObject();
				notes.put("Plan", request.getPlan());
				notes.put("Total referral credits", request.getPlan().getTotalCredits());
				orderRequest.put("notes",notes);

				Order order = razorpay.orders.create(orderRequest);
				
				response.setKey(config.getKeyId());
				response.setOrderId(order.get("id").toString());
				response.setName(request.getName());
				response.setEmail(request.getEmail());
				response.setContact(request.getContact());
			}
			
			else {
				response.setKey("Your current plan is already active");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return response;
		}
		return response;
	}
	
	@Transactional
	public boolean submitSubscription(SubmitSubscriptionDTO request) {
		//RazorpayClient razorpay = new RazorpayClient(config.getKeyId(), config.getSecret());

		JSONObject options = new JSONObject();
		options.put("razorpay_order_id", request.getOrder_id());
		options.put("razorpay_payment_id", request.getPayment_id());
		options.put("razorpay_signature", request.getRazorPaySignature());
		
		boolean status = false;
		try {
			status = Utils.verifyPaymentSignature(options, config.getSecret());
			if(status == false) // verify payment
				return false;
			
			Subscription subscription = repo.findByEmailAndActive(request.getEmail(), true).orElse(new Subscription());
			subscription.setOrderId(request.getOrder_id());
			subscription.setPaymentId(request.getPayment_id());
			subscription.setRazorPaySignature(request.getRazorPaySignature());
			subscription.setPlan(request.getPlan());
			subscription.setActive(true);
			subscription.setTotalReferralCredits(request.getPlan().getTotalCredits());
			subscription.setAvailableReferralCredits(request.getPlan().getTotalCredits());
			subscription.setEmail(request.getEmail());
			subscription.setMobile(request.getContact());
			subscription.setName(request.getName());

			repo.save(subscription);
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
		messageService.sendMessage(DLTdetails.POST_SUCCESSFULL_SUBSCRIPTION,request.getContact());
		return status;
	}

	public boolean checkActiveSubscription(String email) {
		try {
			repo.findByEmailAndActive(email, true).orElseThrow();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
}
