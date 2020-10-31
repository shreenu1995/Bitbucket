package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.RegionManagementServiceDao;
import com.chatak.transit.afcs.server.dao.model.City;
import com.chatak.transit.afcs.server.dao.model.Country;
import com.chatak.transit.afcs.server.dao.model.State;
import com.chatak.transit.afcs.server.dao.repository.CityRepository;
import com.chatak.transit.afcs.server.dao.repository.CountryRepository;
import com.chatak.transit.afcs.server.dao.repository.StateRepository;
import com.chatak.transit.afcs.server.pojo.web.CityRequest;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;

@Repository
public class RegionManagementDaoImpl implements RegionManagementServiceDao {

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CityRepository cityRepository;

	@Override
	public List<Country> getCountry() {
		return countryRepository.findAll();
	}

	@Override
	public List<StateRequest> getStatesByCountry(int id) {
		List<State> stateList = stateRepository.findByCountryId(id);
		List<StateRequest> stateResponseList = new ArrayList<>();
		for (State state : stateList) {
			StateRequest stateReq = new StateRequest();
			stateReq.setCountryId(state.getCountryId());
			stateReq.setStateName(state.getStateName());
			stateReq.setStateId(state.getId());
			stateResponseList.add(stateReq);
		}

		return stateResponseList;
	}

	@Override
	public List<City> getCityByStateId(int id) {
		return cityRepository.findByStateId(id);

	}

	@Override
	public List<State> getAllStates() {
		return stateRepository.findAll();
	}

	@Override
	public List<CityRequest> getCityByState(int id) {
		List<City> cityList = cityRepository.findByStateId(id);
		List<CityRequest> cityResponseList = new ArrayList<>();
		for (City city : cityList) {
			CityRequest cityRequest = new CityRequest();
			cityRequest.setCityId(city.getId());
			cityRequest.setCityName(city.getCityName());
			cityRequest.setStateId(city.getStateId());
			cityResponseList.add(cityRequest);
		}
		return cityResponseList;
	}

	@Override
	public State findByState(int state) {
		return stateRepository.findById(state);
	}

	@Override
	public State findByStateName(String stateName) {
		return stateRepository.findByStateName(stateName);

	}

	@Override
	public List<City> getAllCities() {
		return cityRepository.findAll();
	}

	@Override
	public List<State> getStateByCountryId(int countryId) {
		return stateRepository.findByCountryId(countryId);
	}

	@Override
	public Country findByCountryName(String countryName) {
		return countryRepository.findByCountryName(countryName);
	}

	@Override
	public City findByCityName(String cityName) {
		return cityRepository.findByCityName(cityName);
	}
}
