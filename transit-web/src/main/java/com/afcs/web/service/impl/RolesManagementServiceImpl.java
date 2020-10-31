package com.afcs.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.afcs.web.service.RolesManagementService;
import com.afcs.web.util.JsonUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleFeatureResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleLevelFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Service
public class RolesManagementServiceImpl implements RolesManagementService {
	@Autowired
	JsonUtil jsonUtil;

	@Autowired
	Environment properties;

	private static final String AFCS_SERVER_ERROR = "afcs.server.not.responding";
	
	private static final Logger logger = LoggerFactory.getLogger(RolesManagementServiceImpl.class);

	@Override
	public List<TransitFeatureRequest> getFeatueMapOnUserLevel(TransitFeatureRequest transitFeatureRequest)
			throws AFCSException {
		try {

			RoleLevelFeatureRequest roleLevelFeatureRequest = new RoleLevelFeatureRequest();
			roleLevelFeatureRequest.setRoleLevel(transitFeatureRequest.getRoleType());

			RoleFeatureResponse response = jsonUtil.postRequest(roleLevelFeatureRequest, RoleFeatureResponse.class, "online/userLevelFeatueList");

			return (response != null && response.getFeatureList() != null) ? response.getFeatureList() : null;
		} catch (Exception e) {
			logger.error("ERROR:: RolesManagementServiceImpl:: getFeatueMapOnUserLevel method", e);	
		}
		return Collections.emptyList();
	}

	@Override
	public WebResponse addOrUpdateRole(RoleRequest roleRequest) {

		try {
			WebResponse response = jsonUtil.postRequest(roleRequest, WebResponse.class, "online/addOrUpdateAfcsRole");

			return (response != null) ? response : null;
		} catch (Exception e) {
			logger.error("ERROR:: RolesManagementServiceImpl:: addOrUpdateRole method");	
			throw e;
		}
	}

	@Override
	public RolesSearchResponse searchRole(RoleRequest request) throws AFCSException {
		RolesSearchResponse response = jsonUtil.postRequest(request, RolesSearchResponse.class, "online/searchRoles");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public EditRoleResponse getRoleDetail(RoleRequest request) throws AFCSException {
		EditRoleResponse response = jsonUtil.postRequest(request, EditRoleResponse.class, "online/getRoleDetails");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RolesSearchResponse updateRoleStatus(RoleRequest request) throws AFCSException {
		RolesSearchResponse response = jsonUtil.postRequest(request, RolesSearchResponse.class, "online/updateRoleStatus");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RoleResponse getRoleList(RoleRequest roleRequest) throws AFCSException {
		RoleResponse response = jsonUtil.postRequest(roleRequest, RoleResponse.class, "online/getRoleList");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

	@Override
	public RoleResponse getRoleListByUserType(RoleRequest roleRequest) throws AFCSException {
		RoleResponse response = jsonUtil.postRequest(roleRequest, RoleResponse.class, "online/getRoleListByUserType");
		if (StringUtil.isNull(response)) {
			throw new AFCSException(properties.getProperty(AFCS_SERVER_ERROR));
		}
		return response;
	}

}
