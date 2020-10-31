package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;

public interface OrganizationMasterRepository extends JpaRepository<OrganizationMaster, Long> {

	OrganizationMaster findByOrganizationName(String organizationName);
	
	@Query("select o.organizationName from OrganizationMaster o ")
	public List<String> findAllOrganizationName();

	boolean existsByOrganizationNameAndStatusNotLike(String organizationName, String value);

	OrganizationMaster findByOrganizationNameAndStatusNotLike(String organizationName, String status);

	OrganizationMaster findByOrgId(Long organizationId);
	
	boolean existsByOrgId(Long organizationId);

}
