package com.afcs.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afcs.web.service.RegionManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.CountryResponse;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;
import com.chatak.transit.afcs.server.pojo.web.StateResponse;

@Service
public class RegionManagementServiceImpl implements RegionManagementService {

	@Autowired
	private JsonUtil jsonUtil;

	@Override
	public StateListResponse getAllStates(StateListResponse stateResponse) {
		return jsonUtil.postRequest(null, StateListResponse.class, "region/getAllStates");
	}

	@Override
	public CityResponse getCities(StateRequest request, CityResponse cityResponse) {
		return jsonUtil.postRequest(request, CityResponse.class, "region/cityByStateId");
	}

	@Override
	public int getStateId(StateRequest request) {
		StateResponse stateId = jsonUtil.postRequest(request, StateResponse.class, "region/getStateId");
		return stateId.getId();
	}

	@Override
	public CityResponse getAllCities(CityResponse cityResponse) {
		return jsonUtil.postRequest(null, CityResponse.class, "region/getAllCities");
	}

	@Override
	public CountryListResponse getAllCountries(CountryListResponse countryResponse) {
		return jsonUtil.postRequest(null, CountryListResponse.class, "region/getAllCountries");
	}

	@Override
	public StateListResponse getStates(CountryRequest countryRequest, StateListResponse stateListResponse) {
		return jsonUtil.postRequest(countryRequest, StateListResponse.class, "region/statesByCountryId");
	}

	@Override
	public int getCountryId(CountryRequest countryRequest) {
		CountryResponse countryId = jsonUtil.postRequest(countryRequest, CountryResponse.class, "region/getCountryId");
		return countryId.getId();
	}
}
