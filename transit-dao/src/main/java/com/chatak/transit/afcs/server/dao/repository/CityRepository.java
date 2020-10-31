package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.City;

public interface CityRepository extends JpaRepository<City, Integer>{

	List<City> findByStateId(int stateId);
	
	City findByCityName(String cityName);
}
