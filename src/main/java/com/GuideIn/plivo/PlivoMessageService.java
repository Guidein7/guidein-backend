package com.GuideIn.plivo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plivo.api.Plivo;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import com.plivo.api.models.message.MessageCreateResponse;

@Service
public class PlivoMessageService {
	
	@Autowired
	PlivoConfig config;
	
	public void sendMessage(DLTdetails dltTemplate, String recipient, String... args) {

        try {
        	
        	String formattedMessage = dltTemplate.formatMessage(args);
        	
        	Plivo.init(config.getAuthId(), config.getAuthToken());
        	
            MessageCreateResponse response = Message.creator("Guidn", recipient, formattedMessage)
                    .dlt_entity_id(dltTemplate.getDlt_entity_id())
                    .dlt_template_id(dltTemplate.getDlt_template_id())
                    .dlt_template_category(dltTemplate.getTemplate_category())
                    .create();

        } catch (PlivoRestException | IOException e) {
            e.printStackTrace();
            return;
        }
        
	}
	
	public boolean sendOTPMessage(DLTdetails dltTemplate, String recipient, String... args) {

        try {
        	
        	String formattedMessage = dltTemplate.formatMessage(args);
        	
        	Plivo.init(config.getAuthId(), config.getAuthToken());
        	
            MessageCreateResponse response = Message.creator("Guidn", recipient, formattedMessage)
                    .dlt_entity_id(dltTemplate.getDlt_entity_id())
                    .dlt_template_id(dltTemplate.getDlt_template_id())
                    .dlt_template_category(dltTemplate.getTemplate_category())
                    .create();

        } catch (PlivoRestException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}
	
}
