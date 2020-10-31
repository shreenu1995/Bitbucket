package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatak.transit.afcs.server.dao.model.TransitFeature;

public interface TransitFeatureRepository extends JpaRepository<TransitFeature, Long> {

}
