package com.chatak.transit.afcs.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.dao.model.Country;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.pojo.web.CityRequest;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.CountryResponse;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;
import com.chatak.transit.afcs.server.pojo.web.StateResponse;
import com.chatak.transit.afcs.server.service.RegionManagementService;

@RestController
@RequestMapping(value = "/region/")
public class RegionManagementRestController {

	@Autowired
	RegionManagementService regionManagementService;

	@GetMapping(value = "country")
	public List<Country> getcountry() {
		return regionManagementService.getCountry();
	}

	@PostMapping(value = "state")
	public List<StateRequest> getStatesByCoutryId(@RequestBody CountryRequest request) {
		return regionManagementService.getStates(request);

	}

	@PostMapping(value = "city")
	public List<CityRequest> getCityByStateId(@RequestBody StateRequest request) {
		return regionManagementService.getCities(request);
	}

	@PostMapping(value = "getAllStates")
	public StateListResponse getAllStates(StateListResponse response) {
		StateListResponse stateListResponse = new StateListResponse();
		return regionManagementService.getAllStates(stateListResponse);

	}

	@PostMapping(value = "cityByStateId")
	public CityResponse getCityByStateId(@RequestBody StateRequest request, CityResponse response) {
		CityResponse cityResponse = new CityResponse();
		return regionManagementService.getCitiesbyStateId(request, cityResponse);
	}

	@PostMapping(value = "getStateId")
	public StateResponse getStateId(@RequestBody StateRequest request, StateResponse response) {
		StateResponse stateResponse = new StateResponse();
		State state = regionManagementService.getStateId(request);
		stateResponse.setId(state.getId());
		return stateResponse;

	}
	
	@PostMapping(value = "getAllCities")
	public CityResponse getAllCities(CityResponse response) {
		CityResponse responseCity = new CityResponse();
		return regionManagementService.getAllCities(responseCity);

	}
	
	@PostMapping(value = "getAllCountries")
	public CountryListResponse getAllCountries(CountryListResponse response) {
		return regionManagementService.getAllCountries(response);

	}

	@PostMapping(value = "statesByCountryId")
	public StateListResponse getStateByCountryId(@RequestBody CountryRequest request,
			StateListResponse stateListResponse) {
		return regionManagementService.getStatesbyCountryId(request, stateListResponse);
	}

	@PostMapping(value = "getCountryId")
	public CountryResponse getCountryId(@RequestBody CountryRequest request, CountryResponse response) {
		Country country = regionManagementService.getCountryId(request, response);
		response.setId(country.getId());
		return response;
	}
}
