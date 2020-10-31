package com.chatak.transit.afcs.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.transit.afcs.server.dao.RegionManagementServiceDao;
import com.chatak.transit.afcs.server.dao.model.City;
import com.chatak.transit.afcs.server.dao.model.Country;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.pojo.web.CityRequest;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.CountryResponse;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;
import com.chatak.transit.afcs.server.service.RegionManagementService;

@Service
public class RegionManagementServiceImpl implements RegionManagementService {

	@Autowired
	RegionManagementServiceDao regionManagementDao;

	@Override
	public List<Country> getCountry() {

		return regionManagementDao.getCountry();
	}

	@Override
	public List<StateRequest> getStates(CountryRequest request) {
		return regionManagementDao.getStatesByCountry(request.getCountryId());
	}

	@Override
	public StateListResponse getAllStates(StateListResponse response) {
		StateListResponse stateListResponse = new StateListResponse();
		List<State> stateList = regionManagementDao.getAllStates();
		List<StateRequest> stateResponseList = new ArrayList<>();
		for (State state : stateList) {
			StateRequest stateReq = new StateRequest();
			stateReq.setCountryId(state.getCountryId());
			stateReq.setStateName(state.getStateName());
			stateReq.setStateId(state.getId());
			stateResponseList.add(stateReq);
		}
		stateListResponse.setStateList(stateResponseList);
		stateListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		stateListResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return stateListResponse;
	}

	@Override
	public CityResponse getCitiesbyStateId(StateRequest request, CityResponse response) {
		CityResponse cityResponse = new CityResponse();
		List<City> cityList = regionManagementDao.getCityByStateId(request.getStateId());
		List<CityRequest> cityResponseList = new ArrayList<>();
		for (City city : cityList) {
			CityRequest cityRequest = new CityRequest();
			cityRequest.setStateId(city.getStateId());
			cityRequest.setCityName(city.getCityName());
			cityRequest.setStateId(city.getId());
			cityResponseList.add(cityRequest);
		}
		cityResponse.setCityList(cityResponseList);
		cityResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		cityResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return cityResponse;
	}

	@Override
	public List<CityRequest> getCities(StateRequest request){
		return regionManagementDao.getCityByState(request.getStateId());
	}

	@Override
	public State getStateId(StateRequest request) {
		return regionManagementDao.findByStateName(request.getStateName());
	}

	@Override
	public CityResponse getAllCities(CityResponse response) {
		CityResponse responseCity = new CityResponse();
		List<City> cityList = regionManagementDao.getAllCities();
		List<CityRequest> cityResponseList = new ArrayList<>();
		for (City city : cityList) {
			CityRequest cityRequest = new CityRequest();
			cityRequest.setCityId(city.getId());
			cityRequest.setCityName(city.getCityName());
			cityRequest.setStateId(city.getStateId());
			cityResponseList.add(cityRequest);
		}
		responseCity.setCityList(cityResponseList);
		responseCity.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		responseCity.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return responseCity;
	}

	@Override
	public CountryListResponse getAllCountries(CountryListResponse response) {
		List<Country> countryList = regionManagementDao.getCountry();
		List<CountryRequest> countryResponseList = new ArrayList<>();
		for (Country country : countryList) {
			CountryRequest countryRequest = new CountryRequest();
			countryRequest.setCountryId(country.getId());
			countryRequest.setCountryName(country.getCountryName());
			countryResponseList.add(countryRequest);
		}
		response.setCountryList(countryResponseList);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return response;
	}

	@Override
	public StateListResponse getStatesbyCountryId(CountryRequest request, StateListResponse response) {
		List<State> stateList = regionManagementDao.getStateByCountryId(request.getCountryId());
		List<StateRequest> stateResponseList = new ArrayList<>();
		for (State state : stateList) {
			StateRequest stateRequest = new StateRequest();
			stateRequest.setStateId(state.getId());
			stateRequest.setStateName(state.getStateName());
			stateRequest.setCountryId(state.getCountryId());
			stateResponseList.add(stateRequest);
		}
		response.setStateList(stateResponseList);
		response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
		response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
		return response;
	}

	@Override
	public Country getCountryId(CountryRequest request, CountryResponse response) {
		return regionManagementDao.findByCountryName(request.getCountryName());
	}
}
