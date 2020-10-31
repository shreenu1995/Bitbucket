package com.afcs.web.service;

import java.util.List;

import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

public interface RolesManagementService {

	List<TransitFeatureRequest> getFeatueMapOnUserLevel(TransitFeatureRequest transitFeatureRequest) throws AFCSException;

	WebResponse addOrUpdateRole(RoleRequest roleRequest);

	RolesSearchResponse searchRole(RoleRequest request) throws AFCSException;

	EditRoleResponse getRoleDetail(RoleRequest request) throws AFCSException;

	RolesSearchResponse updateRoleStatus(RoleRequest request) throws AFCSException;

	RoleResponse getRoleList(RoleRequest roleRequest) throws AFCSException;

	RoleResponse getRoleListByUserType(RoleRequest roleRequest) throws AFCSException;

}
