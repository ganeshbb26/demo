package com.example.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.vo.UserDetails;

@RestController
public class MainController {
	
	@Autowired
	private ServiceController serviceController;
	
	/**
	 * Find the number of Unique UserIds
	 * from the JSON data in the URL
	 * @return Number of UniqueUserIds
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value="/count", method = RequestMethod.GET)
	public int findUniqueUserIds() throws IOException, URISyntaxException {
		return serviceController.findUniqueUserIds();
	}
	
	/**
	 * Modify the 4th JSON array item, changing the title and body of the object to "1800Flowers".
	 * @return modified JSON 
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value="/updateList", method = RequestMethod.GET)
	public List<UserDetails> updateList() throws IOException, URISyntaxException {
		return serviceController.updateList();
	}

}
