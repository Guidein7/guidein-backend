package com.GuideIn.jobPoster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpiIdDTO {
	
	private String email;
	private String upiId;

}
