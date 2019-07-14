package com.example.demo;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.vo.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class DemoApplicationTests {

	//@InjectMocks
	//MainController mainController;
	
	private	JSONArray jsonArray = null;
	
	private	JSONArray jsonArrayEmpty = new JSONArray();

	@InjectMocks
	private ServiceController serviceController;

	@Mock
	private RestTemplate restTemplate;
	
	@MockBean
	URI uri;
	
	//@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource("jsonFeedFile.json");
		FileReader fileReader = new FileReader(resource.getFile());

		JSONParser parser = new JSONParser(); 
		jsonArray = (JSONArray) parser.parse(fileReader);
		UserDetails[] userDetailArray = new ObjectMapper().readValue(jsonArray.toString(), UserDetails[].class);
		System.out.println("Size of JSON array data from file == "+userDetailArray.length);
	}

	/**
	 * Test Case to calculate the unique userIds
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void testCount() throws IOException, URISyntaxException {
		mockGetForEntity();
		int count = serviceController.findUniqueUserIds();
		assertEquals(10, count);
	}
		
	/**
	 * Test Case to modify the 4th category
	 * JSON and return the modified JSON
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test 
	public void testUpdateList() throws IOException, URISyntaxException { 
		 mockGetForEntity();
		 List<UserDetails> modifiedList = serviceController.updateList();
		 System.out.println("Modified body == "+modifiedList.get(0).getBody());
		 System.out.println("Modified Title "+modifiedList.get(0).getTitle());
		 assertEquals(modifiedList.get(0).getBody(), "1800Flowers");
		 assertEquals(modifiedList.get(0).getTitle(), "1800Flowers"); 
	}
	
	/**
	 * Test Case to test IndexOutOfBoundsException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testUpdateListIndexOutOfBoundsException() throws  URISyntaxException, IOException {
		 mockGetForEntityForException();
		 serviceController.updateList();
	}
	
	/**
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mockGetForEntityForException() throws IOException {
		Mockito.when(restTemplate.getForEntity(Mockito.any(), ArgumentMatchers.any(Class.class)))
		.thenReturn(new ResponseEntity(jsonArrayEmpty.toString(), HttpStatus.OK));
	}
	
	/**
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mockGetForEntity() throws IOException {
		Mockito.when(restTemplate.getForEntity(Mockito.any(), ArgumentMatchers.any(Class.class)))
		.thenReturn(new ResponseEntity(jsonArray.toString(), HttpStatus.OK));
	}
	
	/*
	/**
	 * Test Case to test IO Exception
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	/*
	@Test(expected=URISyntaxException.class)
	public void testURISyntaxException() throws  URISyntaxException, IOException {
        // mockGetForEntityForException();
		 serviceController.findUniqueUserIds();
	}*/

}