package it.satya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.satya.entity.Country;
import it.satya.service.CountryService;

@RestController
public class CountryController {

	@Autowired
	CountryService countryService;

	@PostMapping(value = "/country/add", produces = { "application/json", "application/xml" })
	public ResponseEntity<Country> saveCountry(@RequestBody Country country) {

		Country savedCountry = countryService.addCountry(country);
		return new ResponseEntity<Country>(savedCountry, HttpStatus.CREATED);
	}

	@GetMapping(value = "/country/{countryId}", produces = { "application/json", "application/xml" })
	public ResponseEntity<Country> getCountryById(@PathVariable("countryId") int countryId) {

		Country country = countryService.getCountryById(countryId);
		return new ResponseEntity<Country>(country, HttpStatus.FOUND);
	}

	@GetMapping(value = "/country/all")
	public ResponseEntity<List<Country>> getAllCountries() {
		
		List<Country> allCountries = countryService.getAllCountries();
		return new ResponseEntity<List<Country>>(allCountries, HttpStatus.FOUND);
	}
	
	@PutMapping(value ="/country/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable int id,@RequestBody Country country) {
		
		Country updateCountry = countryService.updateCountry(id, country);
		return new ResponseEntity<Country>(updateCountry, HttpStatus.OK);
	}
}
