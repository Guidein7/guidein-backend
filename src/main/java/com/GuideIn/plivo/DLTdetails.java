package com.GuideIn.plivo;

public enum DLTdetails {
	
	/// JOB_SEEKER_NOTIFICATIONS ///
	
	POST_SUCCESSFULL_SUBSCRIPTION(
			"1101381140000080694",
			"1107172139710891660",
			"service_explicit",
			
			"Dear User,"
			+ " Congratulations! You have successfully subscribed to GuideIn."
			+ " Start using your referral credits and explore job opportuni -Guidein"),
	
	POST_SUCCESSFULL_REFERRAL(
			"1101381140000080694",
			"1107172146249362689",
			"service_implicit",
			"Dear User,"
			+ " Congratulations! Your referral request for {#var#} at {#var#} has been successfully processed."
			+ " Check your GuideIn dashboard for details. -Guidein"),
	
	POST_REFERRAL_REJECTION(
			"1101381140000080694",
			"1107172344071147343",
			"service_implicit",
			"Dear User,"
			+ " Your referral request for {#var#} at {#var#} has been rejected."
			+ " Your referral credit added back to your account. Visit "+"guidein.org"+" -Guidein"),
	
	
	/// JOB_POSTER_NOTIFICATIONS ///
	
	REFERRAL_REQUESTED(
			"1101381140000080694",
			"1107172344032376490",
			"service_implicit",
			"Hey, you received a referral request for the job {#var#}."
			+ " login to Guidein dashboard to refer the candidate. Visit "+"guidein.org/employee"+" -Guidein"),
	
	REFERRAL_APPROVED(
			"1101381140000080694",
			"1107172154968182314",
			"service_implicit",
			"Your referral proof for the job {#var#} is successfully validated,"
			+ " and the referral amount has been credited to your account."
			+ " Check your GuideIn wallet"),
	
	REFERRAL_REJECTED(
			"1101381140000080694",
			"1107172344051118089",
			"service_implicit",
			"Unfortunately, your referral proof for the job {#var#} has been rejected."
			+ " Check Guidein dashboard for details. Visit "+"guidein.org/employee"+" -Guidein"),
	
	/// OTP ///
	OTP(
			"1101381140000080694",
			"1107172135983381662",
			"service_implicit",
			"Your OTP for mobile verification is {#var#}."
			+ " This code is valid for the next 3 minutes. Please do not share this OTP with anyone. -Guidein");
	
	
	private final String dlt_entity_id;
	private final String dlt_template_id;
	private final String template_category;
	private final String message;
	
	DLTdetails(String dlt_entity_id, String dlt_template_id, String template_category, String message) {
		this.dlt_entity_id = dlt_entity_id;
		this.dlt_template_id = dlt_template_id;
		this.template_category = template_category;
		this.message = message;
	}

	public String getDlt_entity_id() {
		return dlt_entity_id;
	}

	public String getDlt_template_id() {
		return dlt_template_id;
	}

	public String getTemplate_category() {
		return template_category;
	}

	public String getMessage() {
		return message;
	}
	
	public String formatMessage(String... args) {
	    String formattedMessage = message;
	    for (String arg : args) {
	        // Split the argument into words
	        String[] words = arg.split("\\s+");
	        
	        // If the argument has more than 3 words, use only the first 3
	        if (words.length > 3) {
	            arg = String.join(" ", words[0], words[1], words[2]);
	        }
	        
	        // Replace the first occurrence of "{#var#}" with the argument enclosed in square brackets
	        formattedMessage = formattedMessage.replaceFirst("\\{#var#\\}", "[ " + arg + " ]");
	    }
	    return formattedMessage;
	}


}
