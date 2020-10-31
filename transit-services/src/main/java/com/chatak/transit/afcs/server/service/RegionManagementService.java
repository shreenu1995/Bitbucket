package com.chatak.transit.afcs.server.service;

import java.util.List;

import com.chatak.transit.afcs.server.dao.model.Country;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.pojo.web.CityRequest;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.CountryResponse;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;

public interface RegionManagementService {
	
	List<CityRequest> getCities(StateRequest request);

	List<StateRequest> getStates(CountryRequest request);

	List<Country> getCountry();

	StateListResponse getAllStates(StateListResponse response);

	CityResponse getCitiesbyStateId(StateRequest request, CityResponse response);

	State getStateId(StateRequest request);

	CityResponse getAllCities(CityResponse response);
	
	CountryListResponse getAllCountries(CountryListResponse response);
	
	Country getCountryId(CountryRequest request, CountryResponse response);
	
	StateListResponse getStatesbyCountryId(CountryRequest request, StateListResponse response);
}
