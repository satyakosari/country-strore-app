package it.satya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.satya.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{
}
