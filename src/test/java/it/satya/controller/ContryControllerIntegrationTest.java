package it.satya.controller;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.satya.entity.Country;

@SpringBootTest(classes = ContryControllerIntegrationTest.class)
public class ContryControllerIntegrationTest {

	
	static List<Country> countryList;
	TestRestTemplate testRestTemplate ;
	
	@BeforeAll
	public static void setupCountryList() {
		countryList = Arrays.asList( new Country(1,"India","New Delhi"), new Country(2,"USA","Washington DC"),new Country(3,"Uk","London"));
	}
	
	@BeforeEach
	public void setup() {
		testRestTemplate = new TestRestTemplate() ;
	}
	
	
	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception {
		String expected ="[\r\n" + 
				"    {\r\n" + 
				"        \"countryId\": 1,\r\n" + 
				"        \"name\": \"US\",\r\n" + 
				"        \"capital\": \"Washington DC\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"countryId\": 2,\r\n" + 
				"        \"name\": \"UK\",\r\n" + 
				"        \"capital\": \"LONDON\"\r\n" + 
				"    }\r\n" + 
				"]";
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("http://localhost:9090/country/all", String.class) ;
		System.out.println(responseEntity.getBody());
		System.out.println(responseEntity.getStatusCode());
		
		JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
	}
	
	
	@Test
	@Order(2)
	public void test_saveCountry() throws JsonProcessingException, Exception {
		Country  country = new Country(3,"India","New Delhi") ;
		/*ObjectMapper om = new ObjectMapper() ;
		String countryString = om.writeValueAsString(country) ;
		System.out.println(countryString);*/
		String expString ="{\r\n" + 
				"	\"id\":3,\r\n" + 
				"    \"name\": \"India\",\r\n" + 
				"    \"capital\": \"New Delhi\"\r\n" + 
				"}" ;
		HttpHeaders headers = new HttpHeaders() ;
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity< Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> responseEntity = testRestTemplate
				.postForEntity("http://localhost:9090/country/add", request, String.class); 
		
		System.out.println(responseEntity.getBody());
		JSONAssert.assertEquals(expString, responseEntity.getBody(), false);
	}
	
}