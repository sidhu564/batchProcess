package com.example.demo.rest.in;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.person.Person;

public class RESTPersonReader implements ItemReader<Person>{
	
    private final String apiUrl;
    private final RestTemplate restTemplate;
 
    private int nextPersonIndex;
    private List<Person> personData;
 
    RESTPersonReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        nextPersonIndex = 0;
    }
 
    @Override
    public Person read() throws Exception {
        if (personDataIsNotInitialized()) {
        	personData = fetchPersonDataFromAPI();
        }
 
        Person nextPerson = null;
 
        if (nextPersonIndex < personData.size()) {
        	nextPerson = personData.get(nextPersonIndex);
            nextPersonIndex++;
        }
 
        return nextPerson;
    }
 
    private boolean personDataIsNotInitialized() {
        return this.personData == null;
    }
 
    private List<Person> fetchPersonDataFromAPI() {
        ResponseEntity<Person[]> response = restTemplate.getForEntity(
            apiUrl, 
            Person[].class
        );
        Person[] personData = response.getBody();
        return Arrays.asList(personData);
    }


}
