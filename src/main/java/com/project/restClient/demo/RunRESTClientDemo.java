package com.project.restClient.demo;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.project.restClient.demo.config.AppConfig;
import com.project.restClient.demo.model.Customer;

public class RunRESTClientDemo {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(RunRESTClientDemo.class);

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[] args={}) - start", args); //$NON-NLS-1$
		}
		try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				AppConfig.class)) {
			// System.out.println(
			// Arrays.asList(applicationContext.getBeanDefinitionNames()));
			RestTemplate restTemplate = applicationContext
					.getBean(RestTemplate.class);
			AppConfig appConfig = applicationContext.getBean(AppConfig.class);
			// works with plain REST template initialized without auth header
			// prepare authentication header
			/*HttpEntity<String> request = new HttpEntity<String>(
					appConfig.getHeaders());*/

			/*ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
					appConfig.getRESTURL(), HttpMethod.GET, request,
					new ParameterizedTypeReference<List<Customer>>() {});*/

			// make REST call
			ResponseEntity<List<Customer>> responseEntity = restTemplate
					.exchange(appConfig.getRESTURL(), HttpMethod.GET, null,
							new ParameterizedTypeReference<List<Customer>>() {});
			// get the list of customers from response
			List<Customer> customers = responseEntity.getBody();
			logger.info("GET customers java POJO response {}", customers);

			// getting json string as response
			ResponseEntity<String> jsonResponse = restTemplate
					.exchange(appConfig.getRESTURL(), HttpMethod.GET, null,
							new ParameterizedTypeReference<String>() {});
			logger.info("GET customers json response: {}",
					jsonResponse.getBody());

			// getting single object in GET method
			Customer firstCustomer = restTemplate.getForObject(
					appConfig.getRESTURL() + "/" + customers.get(0).getId(),
					Customer.class);
			logger.info("GET single customer object: {}",
					firstCustomer);

			Integer randInt = new Random(System.currentTimeMillis())
					.nextInt(999999);
			Customer newCustomer = new Customer();
			newCustomer.setFirstName("FN " + randInt);
			newCustomer.setLastName("LN " + randInt);
			newCustomer.setEmail(randInt + "@test.com");
			logger.info("new customer before request: " + newCustomer);

			// if needed, can also get String response, specify last param for
			// postForEntity as String.class instead of Customer.class
			newCustomer = restTemplate
					.postForEntity(appConfig.getRESTURL(),
					newCustomer,
							Customer.class)
					.getBody();
			logger.info("new customer after request: " + newCustomer);

			// update using PUT
			firstCustomer.setFirstName("FN updated " + randInt);
			restTemplate.put(
					appConfig.getRESTURL(),
					firstCustomer);

			logger.info("deleting customer {}",
					customers.get(customers.size() - 2));
			restTemplate.delete(appConfig.getRESTURL() + "/"
					+ customers.get(customers.size() - 2).getId());

		} finally {

			if (logger.isDebugEnabled()) {
				logger.debug("main(String[] args={}) - end", args); //$NON-NLS-1$
			}
		}
	}
}
