package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	Country findByCountryName(String countryName);
}
