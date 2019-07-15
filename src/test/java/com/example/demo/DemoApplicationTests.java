package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.FileReader;
import java.io.IOException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.vo.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class DemoApplicationTests {
	
	private	JSONArray jsonArray = null;
	
	private	JSONArray jsonEmptyArray = new JSONArray();

	@InjectMocks
	private ServiceController serviceController;

	@Mock
	private RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		URL resource = getClass().getClassLoader().getResource("jsonFeedFile.json");

		JSONParser parser = new JSONParser(); 
		jsonArray = (JSONArray) parser.parse(new FileReader(resource.getFile()));
		System.out.println("Size of JSON array data from file == "+jsonArray.size());
	}

	/**
	 * Test Case to calculate the unique userIds
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void returnCountOfUniqueUserIds() throws IOException, URISyntaxException {
		//jsonArray = new JSONArray();
		//System.out.println("Size of JSON array data from file == "+jsonArray.size());
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
	public void returnTheModifiedList() throws IOException, URISyntaxException { 
		 mockGetForEntity();
		 List<UserDetails> modifiedList = serviceController.updateList();
		 System.out.println("Modified body == "+modifiedList.get(0).getBody());
		 System.out.println("Modified Title "+modifiedList.get(0).getTitle());
		 assertEquals(modifiedList.get(0).getBody(), "1800Flowers");
		 assertEquals(modifiedList.get(0).getTitle(), "1800Flowers"); 
	}
	
	
	/**
	 * Test Case to test empty count of unique userIds
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void returnEmptyCountOfUserIds() throws  URISyntaxException, IOException {
		mockGetForEntityToReturningEmptyData();
		int count = serviceController.findUniqueUserIds();
		assertEquals(0, count);

	}
	
	/**
	 * Test Case to test empty modified list
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void returnEmptyModifiedList() throws  URISyntaxException, IOException {
		mockGetForEntityToReturningEmptyData();
		 List<UserDetails> userDetailsList = serviceController.updateList();
		 assertSame(0,userDetailsList.size());
		 
	}
	
	/**
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mockGetForEntityToReturningEmptyData() throws IOException {
		Mockito.when(restTemplate.getForEntity(Mockito.any(), ArgumentMatchers.any(Class.class)))
		.thenReturn(new ResponseEntity(jsonEmptyArray.toString(), HttpStatus.OK));
	}
	
	/**
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void mockGetForEntity() throws IOException {
		Mockito.when(restTemplate.getForEntity(Mockito.any(), ArgumentMatchers.any(Class.class)))
		.thenReturn(new ResponseEntity(jsonArray.toString(), HttpStatus.OK));
	}
	
}