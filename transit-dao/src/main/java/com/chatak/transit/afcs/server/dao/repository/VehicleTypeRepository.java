package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.chatak.transit.afcs.server.dao.model.VehicleTypeProfile;

public interface VehicleTypeRepository extends JpaRepository<VehicleTypeProfile, Integer> {
	@Query("select p.vehicleType from VehicleTypeProfile p ")
	public List<String> findAllVehicleName();
	
	boolean existsByvehicleTypeId(long vechicleTypeId);
	
	boolean existsByVehicleType(String vehicleTypeName);
	
	VehicleTypeProfile findByVehicleType(String vehicleType);
	
	VehicleTypeProfile findByVehicleTypeId(long vehicleTypeId);
	
}
