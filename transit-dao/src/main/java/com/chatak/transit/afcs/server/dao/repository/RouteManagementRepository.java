package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.RouteMaster;

public interface RouteManagementRepository extends JpaRepository<RouteMaster, Long>{
	
	  boolean existsByRouteName(String routeName);
	  
	  public RouteMaster findByRouteId(Long routeId);
	  
	  public List<RouteMaster> findByPtoIdAndStatusNotLike(Long ptoId,String status);
	  
	  boolean existsByRouteCodeAndPtoId(String routeCode, Long ptoId);
	  
	  RouteMaster findByRouteCodeAndPtoId(String routeCode, Long ptoId);

}
