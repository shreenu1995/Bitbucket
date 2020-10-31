package com.afcs.web.service;

import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;

public interface RegionManagementService {

	StateListResponse getAllStates(StateListResponse stateResponse);

	CityResponse getCities(StateRequest request, CityResponse cityResponse);

	int getStateId(StateRequest stateRequest);

	CityResponse getAllCities(CityResponse cityResponse);
	
	CountryListResponse getAllCountries(CountryListResponse countryResponse);
	
	int getCountryId(CountryRequest countryRequest);
	
	StateListResponse getStates(CountryRequest countryRequest, StateListResponse stateListResponse);
}
