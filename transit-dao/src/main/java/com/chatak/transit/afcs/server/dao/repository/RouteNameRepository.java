package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatak.transit.afcs.server.dao.model.RouteMaster;

public interface RouteNameRepository extends JpaRepository<RouteMaster, Long> {

	@Query("select p.routeName from RouteMaster p ")
	public List<String> findAllRouteName();
	
	List<RouteMaster> findByPtoIdAndStatusLike(Long valueOf, String status);

}
