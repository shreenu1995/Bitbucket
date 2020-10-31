package com.chatak.transit.afcs.server.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatak.transit.afcs.server.dao.model.AfcsRole;

public interface AfcsRoleRepository extends JpaRepository<AfcsRole, Long> {

	public List<AfcsRole> findByName(String name);

	public List<AfcsRole> findByNameAndId(String name, Long id);

	public List<AfcsRole> findByStatusNotLike(String status);

	public List<AfcsRole> findByUserTypeAndStatusNotLike(String roleCategory, String status);
	
	public List<AfcsRole> findByUserTypeAndId(String roleCategory, Long id );

	public AfcsRole findByIdAndStatusNotLike(Long roleId, String value);

	public List<AfcsRole> findByUserTypeAndCreatedByAndStatusNotLike(String roleCategory, String createdBy,
			String status);

}
