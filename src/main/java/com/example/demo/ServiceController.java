package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.vo.UserDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServiceController {
	
	@Autowired
	RestTemplate restTemplate;
	
	// private final String JSON_FEED =
	// "https://clicktime.symantec.com/35MJxSE4ZBXLXKPswkRTDfg7Vc?u=http%3A%2F%2Fjsonplaceholder.typicode.com%2Fposts";
	private final String JSON_FEED = "http://jsonplaceholder.typicode.com/posts";

	private final String EIGHTEEN_HUNDERED_FLOWERS = "1800Flowers";

	private final long NUMBER_FOUR = 4;

	/**
	 * Find the number of Unique UserIds
	 * from the JSON data in the URL
	 * @return count of Unique UserIds
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public int findUniqueUserIds() throws IOException, URISyntaxException {
		LinkedHashSet<Long> uniqueUserIds = new LinkedHashSet<Long>();
		// Get the JSON Feed Data from URL
		List<UserDetails> jsonList = getJsonFeedData();
		// Iterate the jsonList and add the userIds to uniqueUserIds<LinkedHashSet>
		// to remove duplicate userIds
		jsonList.forEach(js -> {
			uniqueUserIds.add(js.getUserId());
		});
		// return the uniqueUserIds size
		return uniqueUserIds.size();
	}

	/**
	 * Modify the 4th JSON array item, changing 
	 * the title and body of the object to "1800Flowers".
	 * 
	 * @return modified List
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<UserDetails> updateList() throws IOException, URISyntaxException {
		//Get the JSON Feed Data from URL
		List<UserDetails> jsonList = getJsonFeedData();
		//Get the 4th item in JSON list
		UserDetails userDetails = jsonList.get(3);
		userDetails.setBody(EIGHTEEN_HUNDERED_FLOWERS);
		userDetails.setTitle(EIGHTEEN_HUNDERED_FLOWERS);
		//jsonList.set(0, userDetails);
		//Filter the fourth item using id and set the modified 
		//userDetails data and collect it in a new list
		List<UserDetails> modifiedList = jsonList.stream().filter(jl -> jl.getId().equals(NUMBER_FOUR))
				.map(ud -> userDetails).collect(Collectors.toList());
		return modifiedList;
		//return jsonList;
	}

	/**
	 * Get the JSON Feed Data from URL
	 * 
	 * @return the JSON Array
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<UserDetails> getJsonFeedData() throws URISyntaxException, IOException {
		//Set the URI for fetching JSON data
		URI uri = new URI(JSON_FEED); 
		//Fetch the JSON response from the above uri
		ResponseEntity<String> jsonString = restTemplate.getForEntity(uri, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		//UserDetails[] userDetailArray = objectMapper.readValue(jsonString.getBody(), UserDetails[].class);
		//List<UserDetails> userDetailList = Arrays.asList(userDetailArray);
		//Map the json data to userDetailList
		List<UserDetails> userDetailList = objectMapper.readValue(jsonString.getBody(),
				new TypeReference<List<UserDetails>>() {});
		System.out.println("Size of JSON array data from URL == "+userDetailList.size());
		return userDetailList;
	}
}
