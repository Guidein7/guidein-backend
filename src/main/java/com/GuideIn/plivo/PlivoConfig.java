package com.GuideIn.plivo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@Component
@Data
@ConfigurationProperties(prefix = "plivo")
public class PlivoConfig {
	
	private String authId;
	private String authToken;
	private String verifyAppUuid;

}
