package com.project.restClient.demo.config;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource({ "classpath:application.properties" })
public class AppConfig {

	private Environment env;

	private HttpHeaders headers;

	AppConfig() {

	}

	@PostConstruct
	public void initializeHttpHeader() {
		// Below code not needed if you are using BasicAuthenticationInterceptor
		// or BasicAuthenticationInterceptor while initializing restTemplate
		String plainCreds = env.getProperty("username") + ":"
				+ env.getProperty("password");
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		// can also use below method to set basic auth
		// headers.setBasicAuth(username, password);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
				.add(new BasicAuthenticationInterceptor(
						env.getProperty("username"),
						env.getProperty("password")));
		return restTemplate;
	}

	public String getRESTURL() {
		return env.getProperty("rest.url");
	}

	public HttpHeaders getHeaders() { return headers; }

	@Autowired
	public void setEnv(Environment environment) { this.env = environment; }
}
