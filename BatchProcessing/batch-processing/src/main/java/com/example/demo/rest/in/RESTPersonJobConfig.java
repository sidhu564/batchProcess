package com.example.demo.rest.in;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.PersonItemProcessor;
import com.example.demo.person.Person;
import com.example.demo.rest.out.RESTPersonWriter;

@Configuration
public class RESTPersonJobConfig {
	
	   private static final String PROPERTY_REST_GET_PERSON_URL = "rest.api.getPerson.url";
	   private static final String PROPERTY_REST_PERSIST_PERSON_URL = "rest.api.persistPerson.url";

	    @Bean
	    ItemReader<Person> restPersonReader(Environment environment, RestTemplate restTemplate) {
	        return new RESTPersonReader(environment.getRequiredProperty(PROPERTY_REST_GET_PERSON_URL), restTemplate);
	    }

	    @Bean
	    ItemProcessor<Person, Person> restPersonProcessor() {
	        return new PersonItemProcessor();
	    }

	    @Bean
	    ItemWriter<Person> restPersonWriter(Environment environment, RestTemplate restTemplate) {
	    	return new RESTPersonWriter(environment.getRequiredProperty(PROPERTY_REST_PERSIST_PERSON_URL), restTemplate);
	    }

	    @Bean
	    Step restPersonStep(ItemReader<Person> restPersonReader,
	                         ItemProcessor<Person, Person> restPersonProcessor,
	                         ItemWriter<Person> restPersonWriter,
	                         StepBuilderFactory stepBuilderFactory) {
	        return stepBuilderFactory.get("restPersonStep")
	                .<Person, Person>chunk(1)
	                .reader(restPersonReader)
	                .processor(restPersonProcessor)
	                .writer(restPersonWriter)
	                .build();
	    }
	    
	    @Bean
	    Job restPersonJob(JobBuilderFactory jobBuilderFactory,
	                       @Qualifier("restPersonStep") Step restPersonStep) {
	        return jobBuilderFactory.get("restPersonJob")
	                .incrementer(new RunIdIncrementer())
	                .flow(restPersonStep)
	                .end()
	                .build();
	    }	    

}
