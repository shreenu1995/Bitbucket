package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.chatak.transit.afcs.server.dao.model.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
	@Query("select p.vehicleModelName from VehicleModel p where status = 'Active'")
	public List<String> findAllModelName();

	public boolean existsByVehicleModelId(long vehicleModelId);

	public boolean existsByVehicleModelNameAndStatusNotLike(String vehicleModelName, String status);
	
	public List<VehicleModel> findByVehicleManufacturerId(int vehicleManufacturerId);
	
	VehicleModel findByVehicleModelId(long vehicleModelId);

	public List<VehicleModel> findByVehicleManufacturerIdAndStatusLike(int vehicleManufacturerId, String status);  

}