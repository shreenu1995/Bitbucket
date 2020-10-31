package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.chatak.transit.afcs.server.dao.model.VehicleManufacturer;

public interface VehicleManufacturerRepository extends JpaRepository<VehicleManufacturer, Integer> {
	@Query("select p.vehicleManufacturerName from VehicleManufacturer p ")
	public List<String> findAllManufacturerName();

	boolean existsByvehicleManufacturerId(int eqManufacturerId);

	public List<VehicleManufacturer> findByVehicleTypeId(long vehicleTypeId);

	boolean existsByVehicleManufacturerName(String vehicleManufName);

	VehicleManufacturer findByVehicleManufacturerName(String vehicleManufacturerName);

	VehicleManufacturer findByVehicleManufacturerId(int vehicleManufacturerId);
}
