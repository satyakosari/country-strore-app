package it.satya.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import it.satya.entity.Country;
import it.satya.repository.CountryRepository;

@SpringBootTest(classes = CountryServiceTest.class)
public class CountryServiceTest {

	@InjectMocks
	CountryService conService;
	
	@Mock
	CountryRepository conRepository;
	
	@Test
	@Order(1)
	public void addCountryTest() {
		Country country = new Country(1,"India","New Delhi");
		when(conRepository.save(country)).thenReturn(country);
		Country res = conRepository.save(country);
		//assertThat(country.getCountryId()).isEqualTo(expectedRes.getCountryId());
		
		assertEquals(country.getCountryId(), res.getCountryId());
	}
	
	@Test
	@Order(2)
	public void getCountryByIdTest() {
		Country country = new Country(1,"India","New Delhi");
		Optional<Country> optionalCntry = Optional.of(country) ;
		int countryId =1;
		when(conRepository.findById(countryId)).thenReturn(optionalCntry);
		assertEquals(countryId,conService.getCountryById(countryId).getCountryId());
		
	}
	
	@Test
	@Order(3)
	public void test_getAllCountries() {
		List<Country> counList = Arrays.asList( new Country(1,"India","New Delhi"),  
												new Country(1,"USA","Washington DC"),
												new Country(1,"UK","London")
												);
		
		when(conRepository.findAll()).thenReturn(counList) ;
		
		assertEquals(3, conService.getAllCountries().size());
	}
	
	@Test
	@Order(4)
	public void test_updateCountry() {
		
		Country country =new Country(1,"India","Delhi");
		int countryId =1 ;
		Optional<Country> oc = Optional.of(country) ;
		
		when(conRepository.findById(countryId)).thenReturn(oc);
		when(conRepository.save(country)).thenReturn(country);
		
		assertEquals(countryId, conService.updateCountry(countryId, country).getCountryId());
	}

}