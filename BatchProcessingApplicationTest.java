package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.person.Person;

public class BatchProcessingApplicationTest {
	
	static ConfigurableApplicationContext context;

	@Autowired
	void setContext(ConfigurableApplicationContext context) {
		BatchProcessingApplicationTest.context = context;
	}

	@Rule 
	public OutputCapture outputCapture = new OutputCapture(); 

	@Test 
	public void testBatchProcessing() throws Exception { 
		assertThat(SpringApplication 
				.exit(SpringApplication.run(BatchProcessingApplication.class))).isEqualTo(0); 
		String output = this.outputCapture.toString(); 
		assertThat(output).contains("[COMPLETED]"); 
	} 
	 
	@Autowired
	private RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void testFindPerson() {
		setContext(SpringApplication.run(BatchProcessingApplication.class));
		ResponseEntity<Person[]> response = restTemplate.getForEntity(
				"http://localhost:8080/api/getPerson", 
				Person[].class
				);
		Person[] personData = response.getBody();
		assertEquals(5, Arrays.asList(personData).size());
		SpringApplication.exit(context);
	}

	@Test
	public void testPersistPerson() {
		setContext(SpringApplication.run(BatchProcessingApplication.class));
		Person person = new Person("JOHN", "SMITH");
		ResponseEntity<Person> response = restTemplate.postForEntity(
				"http://localhost:8080/api/persistPerson",
				 person,
				Person.class
				);
		assertEquals(HttpStatus.OK,  response.getStatusCode());
		SpringApplication.exit(context);
	}	

}
