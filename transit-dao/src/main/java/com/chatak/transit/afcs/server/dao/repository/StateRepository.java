package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.State;

public interface StateRepository extends JpaRepository<State, Integer> {
	
	List<State> findByCountryId(int countryId);
	State findById(int state);
	State findByStateName(String stateName);
}
