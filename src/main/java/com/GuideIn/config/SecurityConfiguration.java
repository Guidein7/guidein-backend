package com.GuideIn.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	  private final JwtAuthenticationFilter jwtAuthFilter;
	  private final AuthenticationProvider authenticationProvider;

	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    	.cors(Customizer.withDefaults())
	        .csrf(csrf->csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers("/api/guidein/v1/auth/**")
	        		.permitAll()
	        		.requestMatchers("/api/guidein/v1/job_poster/**").hasAuthority("JOB_POSTER")
	        		.requestMatchers("/api/guidein/v1/job_seeker/**").hasAuthority("JOB_SEEKER")
	        		.requestMatchers("/api/guidein/v1/admin/**").hasAuthority("ADMIN")
	        		.anyRequest()
	        		.authenticated())
//	        .sessionManagement(session -> session
//	        		.sessionCreationPolicy(SessionCreationPolicy.NEVER))
	        .authenticationProvider(authenticationProvider)
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	  }
	  
	  @Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	      CorsConfiguration configuration = new CorsConfiguration();
	      configuration.addAllowedOrigin("*"); 
	      configuration.addAllowedMethod("*"); 
	      configuration.addAllowedHeader("*");  
	      
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", configuration); 

	      return source;
	  }
	  
	  	@Bean
	    public CorsFilter corsFilter() {
	        return new CorsFilter(corsConfigurationSource());
	    }
	  
	
}
