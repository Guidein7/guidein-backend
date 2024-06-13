package com.GuideIn.razorpay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@ConfigurationProperties(prefix = "razorpay")
@Data
public class RazorpayConfig {
	private String keyId;
	private String secret;
}
