package com.chatak.transit.afcs.server.dao;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.City;
import com.chatak.transit.afcs.server.dao.model.Country;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.pojo.web.CityRequest;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;

public interface RegionManagementServiceDao {

	List<StateRequest> getStatesByCountry(int id);

	List<Country> getCountry();

	List<State> getAllStates();
	
	List<City> getCityByStateId(int id);
	
	List<CityRequest> getCityByState(int id);

	State findByState(int state);

	State findByStateName(String stateName);

	List<City> getAllCities();

	Country findByCountryName(String countryName);
	
	List<State> getStateByCountryId(int countryId);
	
	City findByCityName(String cityName);
}
