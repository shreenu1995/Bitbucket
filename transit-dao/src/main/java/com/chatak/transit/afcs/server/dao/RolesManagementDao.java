package com.chatak.transit.afcs.server.dao;

import java.util.List;
import com.chatak.transit.afcs.server.dao.model.AfcsRole;
import com.chatak.transit.afcs.server.dao.model.AfcsRoleFeatureMap;
import com.chatak.transit.afcs.server.dao.model.TransitFeature;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;

public interface RolesManagementDao {

	AfcsRole findByRoleId(Long id);

	List<AfcsRole> findByRoleName(String name);

	List<AfcsRole> findByRoleNameAndId(String name, Long agentId);

	AfcsRole saveOrUpdateAfcsRole(AfcsRole agentRole);

	AfcsRoleFeatureMap saveOrUpdateAfcsRoleFeatureMap(AfcsRoleFeatureMap featureMap);

	void deleteAfcsRoleFeatureMap(Long roleId);

	List<TransitFeature> getTransitFeatureForEntityType(String roleLevel, String status);

	List<Long> getFeatureDataOnRoleIdData(Long roleId);

	RolesSearchResponse searchRole(RoleRequest roleRequest);

	AfcsRole updateRoleStatus(RoleRequest request);

	List<AfcsRole> getAllRole();

	List<AfcsRole> getRoleList();

	List<AfcsRole> getRoleListByUserType(RoleRequest request);

	List<AfcsRole> getRoleListByUserTypeAndCreatedBy(RoleRequest request);

}
