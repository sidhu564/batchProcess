package com.example.demo.rest.out;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.web.client.RestTemplate;

import com.example.demo.person.Person;

public class RESTPersonWriter implements ItemWriter<Person> {
	
    private final String apiUrl;
    private final RestTemplate restTemplate;

    
	public RESTPersonWriter(String apiUrl, RestTemplate restTemplate) {
		super();
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
	}


	@Override
	public void write(List<? extends Person> personList) throws Exception {
		if(personList!=null){
			for(Person person : personList){
				restTemplate.postForObject(apiUrl, person, Person.class);
			}
		}
		
	}




}
