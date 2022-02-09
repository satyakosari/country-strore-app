package it.satya.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
//@Data
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int countryId;
	private String name;
	private String capital;

	public Country() {
	}

	public Country(int countryId, String name, String capital) {
		super();
		this.countryId = countryId;
		this.name = name;
		this.capital = capital;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", name=" + name + ", capital=" + capital + "]";
	}

}
