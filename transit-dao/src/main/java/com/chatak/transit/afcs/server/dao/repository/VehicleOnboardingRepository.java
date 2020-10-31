package com.chatak.transit.afcs.server.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatak.transit.afcs.server.dao.model.VehicleOnboarding;

public interface VehicleOnboardingRepository extends JpaRepository<VehicleOnboarding, Long> {

	boolean existsByVehicleOnboardingId(Long valueOf);

	VehicleOnboarding findByVehicleOnboardingId(Long vehicleOnboardingId);

}
