package com.example.demo.person;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = PersonController.class, secure = false)
public class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindPersons() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/getPerson").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expectedStr = "[{\"lastName\":\"Doe\",\"firstName\":\"Jill\"},{\"lastName\":\"Doe\",\"firstName\":\"Joe\"},{\"lastName\":\"Doe\",\"firstName\":\"Justin\"},{\"lastName\":\"Doe\",\"firstName\":\"Jane\"},{\"lastName\":\"Doe\",\"firstName\":\"John\"}]";
		JSONAssert.assertEquals(expectedStr,mvcResult.getResponse().getContentAsString(),false);
	}

	@Test
	public void testPersistPersons() throws Exception {
		String jsonStr = "{\"lastName\":\"Smith\",\"firstName\":\"John\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/persistPerson")
				.accept(MediaType.APPLICATION_JSON).content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}
}
