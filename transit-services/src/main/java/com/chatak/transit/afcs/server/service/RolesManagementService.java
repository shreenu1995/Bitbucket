package com.chatak.transit.afcs.server.service;

import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleFeatureResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleLevelFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.impl.TransitServiceException;

public interface RolesManagementService {

	WebResponse addOrUpdateAfcsRole(RoleRequest agentRoleRequest) throws TransitServiceException;

	RoleFeatureResponse getTransitFeatureForEntityType(RoleLevelFeatureRequest roleLevelFeatureRequest) throws TransitServiceException;

	WebResponse getRoleFeatureListByUserRoleId(RoleRequest roleRequest, WebResponse response);

	RolesSearchResponse searchRole(RoleRequest roleRequest);

	EditRoleResponse getRoleData(RoleRequest roleRequest);

	RolesSearchResponse updateRoleStatus(RoleRequest request, RolesSearchResponse response);

	RolesSearchResponse getAllRole() throws InstantiationException, IllegalAccessException;

	RoleResponse getRoleList() throws InstantiationException, IllegalAccessException;

	RoleResponse getRoleListByUserType(RoleRequest request);


}
