package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.TripDetailsData;

public interface TripDetailsRepository extends JpaRepository<TripDetailsData, Long> {

}
