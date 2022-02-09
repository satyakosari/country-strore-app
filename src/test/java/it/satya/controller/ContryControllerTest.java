package it.satya.controller;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.satya.entity.Country;
import it.satya.service.CountryService;

@ComponentScan(basePackages = {"it.satya"})
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = ContryControllerTest.class)
public class ContryControllerTest {
	@InjectMocks
	CountryController countryController;
	
	@Mock
	CountryService countryService;
	
	@Autowired
	MockMvc mockMvc ;
	
	static List<Country> countryList;
	
	ArgumentCaptor<Integer> argumentCaptor ;
	
	@BeforeAll
	public static void setupCountryList() {
		countryList = Arrays.asList( new Country(1,"India","New Delhi"), new Country(2,"USA","Washington DC"),new Country(3,"Uk","London"));
	}
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}
	
	
	@Test
	@Order(1)
	public void getAllCountries() throws Exception {
		when(countryService.getAllCountries()).thenReturn(countryList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/country/all")
						.accept(MediaType.APPLICATION_JSON_VALUE)
						)
		.andDo(print())
		.andExpect(status().isFound());
	}
	
	@Test
	@Order(2)
	public void test_saveCountry() throws Exception {
		Country country = new Country(3,"US","Washington DC");
		String expected ="{\r\n" + 
				"	\"id\":3,\r\n" + 
				"    \"name\": \"US\",\r\n" + 
				"    \"capital\": \"Washington DC\"\r\n" + 
				"}" ;
		when(countryService.addCountry(country)).thenReturn(country);
		
		ObjectMapper om = new ObjectMapper() ;
		String countryVal = om.writeValueAsString(country) ;
		
		ResultActions andExpect = mockMvc.perform(post("/country/add")
						.content(countryVal)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						)
				.andDo(print())
				.andExpect(status().isCreated());
		
		//JSONAssert.assertEquals(expected, countryVal, false);
	}
	
	@Test
	@Order(3)
	public void test_getCountryById() throws Exception {
		
		int countryId =1;
		System.out.println("*****************"+countryList.get(countryId));
		when(countryService.getCountryById(countryId)).thenReturn(countryList.get(countryId));
		
		mockMvc.perform(get("/country/{countryId}",countryId)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				)
		.andDo(print())
		.andExpect(status().isFound());
		
		verify(countryService, atLeastOnce()).getCountryById(1);
		 
	}
}