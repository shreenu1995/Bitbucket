package com.chatak.transit.afcs.server.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chatak.transit.afcs.server.constants.ServerConstants;
import com.chatak.transit.afcs.server.dao.RolesManagementDao;
import com.chatak.transit.afcs.server.dao.model.AfcsRole;
import com.chatak.transit.afcs.server.dao.model.AfcsRoleFeatureMap;
import com.chatak.transit.afcs.server.dao.model.TransitFeature;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleFeatureResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleLevelFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.RolesManagementService;
import com.chatak.transit.afcs.server.util.MapUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.chatak.transit.afcs.server.util.Utility;

@Service
public class RolesManagementServiceImpl implements RolesManagementService {
	Logger logger = LoggerFactory.getLogger(RolesManagementServiceImpl.class);

	@Autowired
	RolesManagementDao rolesManagementDao;

	@Override
	public WebResponse addOrUpdateAfcsRole(RoleRequest transitRoleRequest) throws TransitServiceException{
		AfcsRole agentRoles = new AfcsRole();
		transitRoleRequest.setIsAuditable(Boolean.TRUE);
		transitRoleRequest.setDataChange(ServerConstants.YES);
		
		try {

			AfcsRole createUserRole = new AfcsRole();
			createUserRole.setName(transitRoleRequest.getRoleName());
			createUserRole.setStatus(transitRoleRequest.getStatus());
			createUserRole.setUserType(transitRoleRequest.getRoleCategory());
			createUserRole.setDescription(transitRoleRequest.getDescription());
			createUserRole.setCreatedDate(Timestamp.from(Instant.now()));
			createUserRole.setCreatedBy(transitRoleRequest.getCreatedBy());
			if (transitRoleRequest.getRoleId() == null) {
				createUserRole.setStatus(Status.ACTIVE.getValue());
				createUserRole.setName(transitRoleRequest.getRoleName());

				createUserRole.setCreatedDate(Timestamp.from(Instant.now()));
			}
			if (transitRoleRequest.getRoleId() != null) {
				AfcsRole userRole = rolesManagementDao.findByRoleId(transitRoleRequest.getRoleId());

				if (transitRoleRequest.getStatus() == null) {
					transitRoleRequest.setStatus(userRole.getStatus());
				}
				createUserRole.setId(transitRoleRequest.getRoleId());
				createUserRole.setStatus(transitRoleRequest.getStatus());
				createUserRole.setName(transitRoleRequest.getRoleName());
				createUserRole.setDescription(transitRoleRequest.getDescription());
				createUserRole.setUserType(transitRoleRequest.getRoleCategory());
				createUserRole.setCreatedDate(Timestamp.from(Instant.now()));
			}
			agentRoles = rolesManagementDao.saveOrUpdateAfcsRole(createUserRole);

			if (transitRoleRequest.getRoleId() == null && transitRoleRequest.getFeature() != null) {
				for (String featureData : transitRoleRequest.getFeature()) {
					AfcsRoleFeatureMap featureMap = new AfcsRoleFeatureMap();
					featureMap.setRoleId(agentRoles.getId());
					featureMap.setFeatureId(Long.valueOf(featureData));
					featureMap.setCreatedDate(Timestamp.from(Instant.now()));
					rolesManagementDao.saveOrUpdateAfcsRoleFeatureMap(featureMap);
				}
			}

			if (transitRoleRequest.getRoleId() != null && transitRoleRequest.getFeature() != null) {
				rolesManagementDao.deleteAfcsRoleFeatureMap(transitRoleRequest.getRoleId());
				setFeatureMapData(transitRoleRequest);
			}

			WebResponse webResponse = new WebResponse();
			webResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			webResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return webResponse;
		} catch (Exception e) {
			rolesManagementDao.deleteAfcsRoleFeatureMap(transitRoleRequest.getRoleId());
			logger.error("ERROR:: RolesManagementServiceImpl:: addOrUpdateAfcsRole method", e);
			throw new TransitServiceException("Unable to process the request", null, null);
		}
	}

	private void setFeatureMapData(RoleRequest transitRoleRequest) {
		if (transitRoleRequest.getFeature() != null) {
			for (String featureData : transitRoleRequest.getFeature()) {
				AfcsRoleFeatureMap featureMap = new AfcsRoleFeatureMap();
				featureMap.setRoleId(transitRoleRequest.getRoleId());
				featureMap.setFeatureId(Long.valueOf(featureData));
				featureMap.setCreatedDate(Timestamp.from(Instant.now()));
				rolesManagementDao.saveOrUpdateAfcsRoleFeatureMap(featureMap);
			}
		}
	}

	@Override
	public RoleFeatureResponse getTransitFeatureForEntityType(RoleLevelFeatureRequest roleLevelFeatureRequest)
			throws TransitServiceException {
		logger.info("Entering:: AdminRoleHandlerImpl:: getFeatureOnRoleLevel method");
		RoleFeatureResponse roleFeatureResponse = new RoleFeatureResponse();

		try {
			List<TransitFeature> featureList = rolesManagementDao
					.getTransitFeatureForEntityType(roleLevelFeatureRequest.getRoleLevel(), Status.ACTIVE.getValue());

			List<TransitFeatureRequest> features = Utility.copyListBeanProperty(featureList,
					TransitFeatureRequest.class);
			roleFeatureResponse.setFeatureList((StringUtil.isListNotNullNEmpty(features)) ? features : null);
			roleFeatureResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			roleFeatureResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			logger.info("Exiting:: AdminRoleHandlerImpl:: getFeatureOnRoleLevel method");
			return roleFeatureResponse;
		} catch (Exception e) {
			logger.error("ERROR:: AdminRoleHandlerImpl:: getFeatureOnRoleLevel method", e);
			throw new TransitServiceException(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg(),
					"Unable to process your request. Please try again later", null);
		}

	}

	@Override
	public WebResponse getRoleFeatureListByUserRoleId(RoleRequest roleRequest, WebResponse response) {

		RoleFeatureResponse roleFeatureResponse = new RoleFeatureResponse();

		List<Long> listOfFeature = rolesManagementDao.getFeatureDataOnRoleIdData(roleRequest.getRoleId());
		List<TransitFeatureRequest> features = new ArrayList<>();

		for (Long featureId : listOfFeature) {
			TransitFeatureRequest transitFeatureRequest = new TransitFeatureRequest();
			transitFeatureRequest.setFeatureId(featureId);
			features.add(transitFeatureRequest);
		}
		roleFeatureResponse.setFeatureList(features);
		return roleFeatureResponse;

	}

	@Override
	public RolesSearchResponse searchRole(RoleRequest roleRequest) {
		RolesSearchResponse rolesSearchResponse = rolesManagementDao.searchRole(roleRequest);
		if (rolesSearchResponse != null) {
			rolesSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			rolesSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return rolesSearchResponse;
		}
		return rolesSearchResponse;

	}

	@Override
	public EditRoleResponse getRoleData(RoleRequest roleRequest) {
		logger.info("Entering:: AdminRoleHandlerImpl:: getRoleData method");
		EditRoleResponse editRoleResponse = new EditRoleResponse();
		roleRequest.setIsAuditable(Boolean.TRUE);
		try {
			AfcsRole userRole = rolesManagementDao.findByRoleId(roleRequest.getRoleId());
			List<Long> existingFeature = rolesManagementDao
					.getFeatureDataOnRoleIdData(roleRequest.getRoleId());

			List<TransitFeature> featureList = rolesManagementDao
					.getTransitFeatureForEntityType((userRole.getUserType()), Status.ACTIVE.name());

			List<TransitFeatureRequest> features = Utility.copyListBeanProperty(featureList,
					TransitFeatureRequest.class);
			roleRequest.setCreatedBy(userRole.getCreatedBy());
			roleRequest.setDescription(userRole.getDescription());
			roleRequest.setRoleName(userRole.getName());
			roleRequest.setRoleCategory(userRole.getUserType());
			if (userRole.getUserType() != null && userRole.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				roleRequest.setRoleCategory(RoleLevel.SUPER_ADMIN.name());
			} else if (userRole.getUserType() != null && userRole.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				roleRequest.setRoleCategory(RoleLevel.ORG_ADMIN.name());
			} else if (userRole.getUserType() != null && userRole.getUserType().equals(RoleLevel.PTO_ADMIN.name())) {
				roleRequest.setRoleCategory(RoleLevel.PTO_ADMIN.name());
			}
			roleRequest.setRoleType(userRole.getUserType());
			roleRequest.setStatus(userRole.getStatus());
			roleRequest.setRoleId(userRole.getId());

			editRoleResponse.setRoleRequest(roleRequest);
			editRoleResponse
					.setTransitFeatureMap((StringUtil.isListNotNullNEmpty(features)) ? getfeatureMap(features) : null);
			editRoleResponse.setExistingFeatures(existingFeature);
			editRoleResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			editRoleResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			logger.info("Exiting:: AdminRoleHandlerImpl:: getRoleData method");
			return editRoleResponse;
		} catch (Exception e) {
			logger.error("ERROR:: AdminRoleHandlerImpl:: getRoleData method", e);
		}
		return editRoleResponse;
	}

	private Map<Long, List<TransitFeatureRequest>> getfeatureMap(List<TransitFeatureRequest> featureList) {
		logger.info("Entering:: AgentRoleHandlerImpl:: getfeatureMap method");
		List<TransitFeatureRequest> featureListData = null;
		Map<Long, List<TransitFeatureRequest>> featureMap = new HashMap<>();
		for (TransitFeatureRequest feature : featureList) {
			if (feature.getFeatureLevel().longValue() == 0) {
				featureListData = featureMap.get(feature.getFeatureId());
				if (featureListData == null)
					featureListData = new ArrayList<>();
				featureListData.add(feature);
				featureMap.put(feature.getFeatureId(), featureListData);
			} else if (feature.getFeatureLevel().longValue() == 1) {
				featureListData = featureMap.get(feature.getRefFeatureId());
				if (featureListData == null)
					featureListData = new ArrayList<>();
				featureListData.add(feature);
				featureMap.put(feature.getRefFeatureId(), featureListData);
			}
		}
		logger.info("Exiting:: AgentRoleHandlerImpl:: getfeatureMap method");
		return MapUtil.mySortedMap(featureMap);
	}

	@Override
	public RolesSearchResponse updateRoleStatus(RoleRequest request, RolesSearchResponse response) {
		AfcsRole role=rolesManagementDao.updateRoleStatus(request);
		List<RoleRequest> roleList=new ArrayList<>();
		RoleRequest roleRequest=new RoleRequest();
		roleRequest.setRoleName(role.getName());
		roleList.add(roleRequest);
		if (!StringUtil.isNull(response)) {
			response.setListOfAfcsRole(roleList);
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return response;
		} else {
			response.setStatusCode(CustomErrorCodes.SERVER_ERROR.getCustomErrorCode());
			response.setStatusMessage(CustomErrorCodes.SERVER_ERROR.getCustomErrorMsg());
			return response;
		}
	}

	@Override
	public RolesSearchResponse getAllRole() throws InstantiationException, IllegalAccessException {
		RolesSearchResponse rolesSearchResponse = new RolesSearchResponse();
		List<AfcsRole> listOfRole = rolesManagementDao.getAllRole();
		List<RoleRequest> listOfRoleData = new ArrayList<>();
		if (StringUtil.isListNotNullNEmpty(listOfRole)) {
			for (AfcsRole role : listOfRole) {
				RoleRequest roleRequest = new RoleRequest();
				roleRequest.setRoleId(role.getId());
				roleRequest.setRoleName(role.getName());
				listOfRoleData.add(roleRequest);
			}
			rolesSearchResponse.setListOfAfcsRole(listOfRoleData);
			rolesSearchResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			rolesSearchResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return rolesSearchResponse;
		}
		rolesSearchResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
		rolesSearchResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		return rolesSearchResponse;
	}

	@Override
	public RoleResponse getRoleList() throws InstantiationException, IllegalAccessException {
		RoleResponse roleResponse = new RoleResponse();
		List<AfcsRole> listOfRole = rolesManagementDao.getRoleList();
		List<RoleRequest> listOfRoleData = new ArrayList<>();
		if (StringUtil.isListNotNullNEmpty(listOfRole)) {
			for (AfcsRole role : listOfRole) {
				RoleRequest roleRequest = new RoleRequest();
				roleRequest.setRoleId(role.getId());
				roleRequest.setRoleName(role.getName());
				listOfRoleData.add(roleRequest);
			}
			roleResponse.setRoleList(listOfRoleData);
			roleResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			roleResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return roleResponse;
		}
		roleResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
		roleResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		return roleResponse;
	}

	@Override
	public RoleResponse getRoleListByUserType(RoleRequest request) {
		RoleResponse roleResponse = new RoleResponse();
		List<AfcsRole> listOfRole;
		if(request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
		listOfRole = rolesManagementDao.getRoleListByUserType(request);
		} else if(request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			listOfRole = rolesManagementDao.getRoleListByUserTypeAndCreatedBy(request);
		} else {
			listOfRole = rolesManagementDao.getRoleListByUserTypeAndCreatedBy(request);
		}
		List<RoleRequest> listOfRoleData = new ArrayList<>();
		if (StringUtil.isListNotNullNEmpty(listOfRole)) {
			for (AfcsRole role : listOfRole) {
				RoleRequest roleRequest = new RoleRequest();
				roleRequest.setRoleId(role.getId());
				roleRequest.setRoleName(role.getName());
				listOfRoleData.add(roleRequest);
			}
			roleResponse.setRoleList(listOfRoleData);
			roleResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			roleResponse.setStatusMessage(CustomErrorCodes.SUCCESS.getCustomErrorMsg());
			return roleResponse;
		}
		roleResponse.setStatusCode(CustomErrorCodes.FAILURE.getCustomErrorCode());
		roleResponse.setStatusMessage(CustomErrorCodes.FAILURE.getCustomErrorMsg());
		return roleResponse;
	}

}
