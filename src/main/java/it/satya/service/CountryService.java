package it.satya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.satya.entity.Country;
import it.satya.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepository ;
	
	public Country addCountry(Country country) {
		return countryRepository.save(country) ;
	}
	
	public Country getCountryById(int id) {
		
		 Optional<Country> cOptional = countryRepository.findById(id);
		 if(cOptional.isPresent())
			 return cOptional.get();
		 else
			 return new Country() ;
		 
		/*
		 * List<Country> allCountries = countryRepository.findAll(); Country country =
		 * allCountries.stream().filter(c->c.getCountryId()== id ).findFirst().get() ;
		 * return country;
		 */
	}
	
	public List<Country> getAllCountries(){
		return countryRepository.findAll() ;
	}
	
	
	public Country updateCountry(int id,Country country) {
		
		Optional<Country> cOptional = countryRepository.findById(id);
		
		if(cOptional.isPresent()) {
			cOptional.get();
			return countryRepository.save(country);
		}
		
		return new Country(0, null, null);
	}
	
	public void deleteCountry(int id) {
		
		countryRepository.deleteById(id);
	}

}
