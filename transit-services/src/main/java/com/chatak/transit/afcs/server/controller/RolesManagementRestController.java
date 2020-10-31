package com.chatak.transit.afcs.server.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatak.transit.afcs.server.pojo.web.EditRoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RoleLevelFeatureRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RoleResponse;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.service.RolesManagementService;
import com.chatak.transit.afcs.server.service.impl.TransitServiceException;

@RestController
@RequestMapping(value = "/online/")
public class RolesManagementRestController {

	Logger logger = LoggerFactory.getLogger(RolesManagementRestController.class);

	@Autowired
	RolesManagementService rolesManagementService;

	/**
	 * services to get add afcs role
	 * 
	 * @param request
	 * @param requestIdList
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "addOrUpdateAfcsRole")
	public WebResponse addOrUpdateAgentRole(@RequestBody @Valid RoleRequest transitRoleRequest,
			BindingResult bindingResult) throws TransitServiceException {
		logger.debug("Entering:: " + "RolesManagementRestController" + " :: addOrUpdateAfcsRole method");
		return rolesManagementService.addOrUpdateAfcsRole(transitRoleRequest);
	}

	@PostMapping("userLevelFeatueList")
	public WebResponse getUserLevelFeatureList(@RequestBody RoleLevelFeatureRequest roleLevelRequest)
			throws TransitServiceException {
		logger.info("Entering::  :: getUserLevelFeatureList method: ");
		return rolesManagementService.getTransitFeatureForEntityType(roleLevelRequest);
	}

	@PostMapping("getRoleFeatureByUserRoleId")
	public WebResponse getRoleFeatureListByUserRoleId(@RequestBody @Valid RoleRequest transitRoleRequest,
			BindingResult bindingResult, WebResponse response) {

		return rolesManagementService.getRoleFeatureListByUserRoleId(transitRoleRequest, response);
	}

	@PostMapping(value = "searchRoles")
	public RolesSearchResponse searchRole(@RequestBody RoleRequest request, BindingResult bindingResult) {
		return rolesManagementService.searchRole(request);
	}

	@PostMapping(value = "getRoleDetails")
	public EditRoleResponse getRoleDetails(@RequestBody RoleRequest request, BindingResult bindingResult) {
		return rolesManagementService.getRoleData(request);
	}

	@PostMapping(value = "updateRoleStatus")
	public RolesSearchResponse updateRoletatus(@RequestBody RoleRequest request, BindingResult bindingResult,
			RolesSearchResponse response) {
		return rolesManagementService.updateRoleStatus(request, response);
	}

	@PostMapping(value = "getAllRole")
	public RolesSearchResponse getAllRole() throws InstantiationException, IllegalAccessException {

		return rolesManagementService.getAllRole();
	}

	@PostMapping(value = "getRoleList")
	public RoleResponse getRoleList() throws InstantiationException, IllegalAccessException {

		return rolesManagementService.getRoleList();
	}
	
	@PostMapping(value = "getRoleListByUserType")
	public RoleResponse getRoleListByUserType(@RequestBody RoleRequest request) {

		return rolesManagementService.getRoleListByUserType(request);
	}
}
