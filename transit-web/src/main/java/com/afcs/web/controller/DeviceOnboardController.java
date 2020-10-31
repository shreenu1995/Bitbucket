package com.afcs.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchData;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DeviceOnboardController {

	@Autowired
	Environment properties;

	@Autowired
	DeviceOnboardService deviceOnboardService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(DeviceOnboardController.class);
	private static final String DEVICE_ONBOARDING_REGISTRATION = "device-onboarding-registration";
	private static final String DEVICE_ONBOARDING_REGISTRATION_REQUEST = "deviceOnboardingRegistration";
	private static final String DEVICE_ONBOARDING_SEARCH = "device-onboarding-search";
	private static final String DEVICE_ONBOARDING_DATASIZE = "deviceOnboardDataSize";
	private static final String DEVICE_ONBOARDING_SEARCH_PAGINATION = "device-onboard-search-pagination";
	private static final String DEVICE_ONBOARDING_VIEW_ACTION = "device-onboarding-view-action";
	private static final String DEVICE_ONBOARDING_VIEW_REQ = "deviceOnboardViewRequest";
	private static final String DEVICE_ONBOARDING_VIEW = "device-onboarding-view";
	private static final String FETCH_MODEL_FOR_MANFUACTURER_NAME = "fetchModelForManufacturerName";
	private static final String DEVICE_ONBOARDING_EDIT_ACTION = "edit-device-onboarding-action";
	private static final String DEVICE_ONBOARDING_UPDATE = "device-onboarding-update";
	private static final String DEVICE_ONBOARDING_EDIT_REQ = "deviceOnboardingEditRequest";
	private static final String UPDATE_DEVICE_ONBOARDING_STATUS = "update-device-onboarding-status";
	private static final String DEVICE_ONBOARDING_SEARCH_REQ = "deviceOnboardSearch";
	private static final String DEVICE_TYPE_LIST_DATA = "deviceTypeListData";
	private static final String DEVICE_ONBOARDING_LIST_DATA= "deviceOnboardingListData";
	private static final String DOWNLOAD_DEVICE_ONBOARDING_REPORT = "downloadDeviceOnboardingReport";
	private static final String DEVICE_ONBOARDING_ID = "&&deviceOnboardingId";
	
	@GetMapping(value = DEVICE_ONBOARDING_REGISTRATION)
	public ModelAndView saveDeviceOnboardGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(DEVICE_ONBOARDING_REGISTRATION);
		DeviceOnboardingRequest deviceOnboardingRequest = new DeviceOnboardingRequest();
		model.put(DEVICE_ONBOARDING_REGISTRATION_REQUEST, deviceOnboardingRequest);
		return modelandview;
	}

	@GetMapping(value = FETCH_MODEL_FOR_MANFUACTURER_NAME)
	public @ResponseBody String getModelForManufacturerNameAndDeviceType(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String selectedManufacturerId = request.getParameter("selectedManufacturerId");
		String status = request.getParameter("status");
		DeviceModelListResponse response1 = null;
		DeviceModelSearchData deviceModelSearchData = new DeviceModelSearchData();
		try {
			if (!StringUtil.isNullEmpty(selectedManufacturerId)) {
				deviceModelSearchData.setDeviceManufacturerCode(Long.valueOf(selectedManufacturerId));
				deviceModelSearchData.setStatus(status);
				response1 = deviceOnboardService.geteEuipmentModelListForManufacturer(deviceModelSearchData);
				return JsonUtil.convertObjectToJSON(response1);
			}

		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceOnboardController:: getModelForManufacturerNameAndDeviceType method :: Exception",
					e);
		}
		return null;
	}

	@PostMapping(value = DEVICE_ONBOARDING_REGISTRATION)
	public ModelAndView saveDeviceOnboardPost(DeviceOnboardingRequest request, HttpSession session,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelandview = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		DeviceOnboardSearchRequest deviceOnboardSearchRequest = new DeviceOnboardSearchRequest();
		model.put(DEVICE_ONBOARDING_SEARCH_REQ, deviceOnboardSearchRequest);
		DeviceOnboardingResponse response = new DeviceOnboardingResponse();
		try {
			 response = deviceOnboardService.saveDeviceOnboard(request);
		} catch (AFCSException e) {
			logger.error("DeviceOnboardController class :: saveDeviceOnboardPost method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelandview;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.onboarding.create.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelandview;
	}

	@GetMapping(value = DEVICE_ONBOARDING_SEARCH)
	public ModelAndView searchDeviceOnboardGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		DeviceOnboardSearchRequest deviceOnboardSearchRequest = new DeviceOnboardSearchRequest();
		deviceOnboardSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(DEVICE_ONBOARDING_SEARCH_REQ, deviceOnboardSearchRequest);

		List<DeviceTypeSearchRequest> listDevice = deviceOnboardService.getDeviceTypeList().getListOfDeviceType();
		session.setAttribute(DEVICE_TYPE_LIST_DATA, listDevice);
		model.put(DEVICE_TYPE_LIST_DATA, listDevice);

		List<DeviceManufacturerSearchRequest> listDeviceManufacturer = deviceOnboardService.getDeviceManuf().getListOfManufacturer();
		session.setAttribute("deviceManufacturerListData", listDeviceManufacturer);
		model.put("deviceManufacturerListData", listDeviceManufacturer);

		List<DeviceModelRequest> listOfDeviceModel = deviceOnboardService.getDeviceModel().getListOfDeviceModel();
		model.put("deviceModelListData", listOfDeviceModel);
		session.setAttribute("deviceModelListData", listOfDeviceModel);

		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = null;
		try {
			deviceOnboardSearchRequest.setPageSize(10);
			deviceOnboardSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(DEVICE_ONBOARDING_LIST_DATA, deviceOnboardSearchRequest);
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: DeviceOnboardController class :: searchDeviceOnboardGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}
		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService
						.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService
						.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					organizationSearchRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(organizationSearchRequest);
				}
			}

		} catch (AFCSException e) {
			logger.error("ERROR :: DeviceOnboardController class :: searchDeviceOnboardGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_ONBOARDING_SEARCH)
	public ModelAndView searchDeviceOnboardPost(Map<String, Object> model, DeviceOnboardSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelTypeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		DeviceOnboardListSearchResponse response = null;
		model.put(DEVICE_ONBOARDING_SEARCH_REQ, new DeviceOnboardSearchRequest());
		if (!StringUtil.isNullEmpty(cancelTypeId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return searchDataPaginationDeviceOnboard(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		request.setPageIndex(Constants.ONE);
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = deviceOnboardService.searchDeviceOnboard(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = deviceOnboardService.searchDeviceOnboard(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = deviceOnboardService.searchDeviceOnboard(request);
			}
			session.setAttribute(DEVICE_ONBOARDING_LIST_DATA, request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceOnboardController:: searchDeviceOnboardPost method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DEVICE_ONBOARDING_DATASIZE, response.getTotalRecords());
			session.setAttribute(DEVICE_ONBOARDING_SEARCH_REQ, request);
			model.put(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			model.put(DEVICE_ONBOARDING_LIST_DATA, response.getListDeviceOnboard());
			model.put(DEVICE_ONBOARDING_DATASIZE, response.getTotalRecords());
			model.put(DEVICE_ONBOARDING_SEARCH_REQ, request);
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DEVICE_ONBOARDING_DATASIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, DeviceOnboardSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrgId())) {
			ptoListRequest.setOrgId(request.getOrgId());
			if (!StringUtil.isNullEmpty(session.getAttribute(Constants.USER_TYPE).toString())
					&& !session.getAttribute(Constants.USER_TYPE).toString()
							.equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
				PtoListResponse ptoListResponse = ptoManagementService
						.getPtoListByOrganizationIdAndUserId(ptoListRequest);
				if (!StringUtil.isNull(ptoListResponse)) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}
		}
	}

	@PostMapping(value = DEVICE_ONBOARDING_SEARCH_PAGINATION)
	public ModelAndView searchDataPaginationDeviceOnboard(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		DeviceOnboardSearchRequest request = (DeviceOnboardSearchRequest) session
				.getAttribute(DEVICE_ONBOARDING_SEARCH_REQ);
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		model.put(DEVICE_ONBOARDING_SEARCH_REQ, new DeviceOnboardSearchRequest());
		model.put(DEVICE_ONBOARDING_SEARCH, new DeviceOnboardSearchRequest());
		DeviceOnboardListSearchResponse response = new DeviceOnboardListSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = deviceOnboardService.searchDeviceOnboard(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceOnboardController:: searchDataPaginationDeviceOnboard method :: Exception", e);
			return modelAndView;
		}
		model.put("searchFlag", "true");
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DEVICE_ONBOARDING_LIST_DATA, response.getListDeviceOnboard());
			model.put(DEVICE_ONBOARDING_SEARCH_REQ, request);
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			session.setAttribute(DEVICE_ONBOARDING_DATASIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DEVICE_ONBOARDING_DATASIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_ONBOARDING_VIEW_ACTION)
	public ModelAndView viewDeviceOnboardData(final HttpSession session,
			@RequestParam("deviceOnboardId") Long deviceOnboardId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_VIEW);
		DeviceOnboardListSearchResponse response = new DeviceOnboardListSearchResponse();
		model.put(DEVICE_ONBOARDING_VIEW_REQ, new DeviceOnboardingRequest());
		DeviceOnboardSearchRequest request = new DeviceOnboardSearchRequest();
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setPageIndex(Constants.ONE);
			request.setDeviceOnboardId(deviceOnboardId);
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = deviceOnboardService.searchDeviceOnboard(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = deviceOnboardService.searchDeviceOnboard(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				response = deviceOnboardService.searchDeviceOnboard(request);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceOnboardController:: updateDeviceOnboardData method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceOnboardingRequest deviceOnboardingRequest = populateDeviceUpdateData(response);
			model.put(DEVICE_ONBOARDING_VIEW_REQ, deviceOnboardingRequest);
		}
		return modelAndView;
	}

	private DeviceOnboardingRequest populateDeviceUpdateData(DeviceOnboardListSearchResponse response) {
		DeviceOnboardingRequest request = new DeviceOnboardingRequest();
		DeviceOnboardSearchResponse deviceOnboardSearchResponse = response.getListDeviceOnboard().get(0);
		request.setDeviceOnboardId(deviceOnboardSearchResponse.getDeviceOnboardId());
		request.setDeviceTypeName(deviceOnboardSearchResponse.getDeviceTypeName());
		request.setOrganizationId(deviceOnboardSearchResponse.getOrganizationId());
		request.setPtoId(deviceOnboardSearchResponse.getPtoId());
		request.setPtoName(deviceOnboardSearchResponse.getPtoName());
		request.setOrganizationName(deviceOnboardSearchResponse.getOrganizationName());
		request.setDeviceTypeId(deviceOnboardSearchResponse.getDeviceTypeId());
		request.setDeviceManufacturerCode(deviceOnboardSearchResponse.getDeviceManufacturerId());
		request.setDeviceManufacturer(deviceOnboardSearchResponse.getDeviceManufacturer());
		request.setDeviceModelId(deviceOnboardSearchResponse.getDeviceModelId());
		request.setDeviceModel(deviceOnboardSearchResponse.getDeviceModel());
		return request;
	}

	@PostMapping(value = DEVICE_ONBOARDING_EDIT_ACTION)
	public ModelAndView editDeviceOnboardData(final HttpSession session,
			@RequestParam("deviceOnboardId") Long deviceOnboardId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_UPDATE);
		DeviceOnboardListSearchResponse response = new DeviceOnboardListSearchResponse();
		DeviceOnboardSearchRequest request = new DeviceOnboardSearchRequest();
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = new PtoListResponse();
		try {
			request.setPageIndex(Constants.ONE);
			request.setDeviceOnboardId(deviceOnboardId);
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = deviceOnboardService.searchDeviceOnboard(request);
				ptoListRequest.setOrgId(response.getListDeviceOnboard().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = deviceOnboardService.searchDeviceOnboard(request);
				ptoListRequest.setOrgId(response.getListDeviceOnboard().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = deviceOnboardService.searchDeviceOnboard(request);
				if (ptoList.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_LIST_DATA, ptoList.getPtoList());
				}
			}

		} catch (AFCSException e) {
			logger.error("ERROR:: DeviceOnboardController :: editDeviceOnboardData method: AFCSException ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceOnboardingRequest userData = populateDeviceUpdateData(response);
			model.put(DEVICE_ONBOARDING_EDIT_REQ, userData);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_ONBOARDING_UPDATE)
	public ModelAndView updateDeviceOnboardData(HttpSession session, Map<String, Object> model,
			DeviceOnboardingRequest request) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_UPDATE);
		WebResponse response = null;
		model.put(DEVICE_ONBOARDING_EDIT_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());

		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = deviceOnboardService.updateDeviceOnboard(request);
		} catch (AFCSException e) {
			logger.error("ERROR:: DeviceOnboardController :: updateDeviceOnboardData method: Exception ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.model.profile.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DEVICE_ONBOARDING_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_DEVICE_ONBOARDING_STATUS)
	public ModelAndView updateDeviceOnboardStatus(final HttpSession session,
			@RequestParam("deviceOnboardId") Long deviceOnboardId, @RequestParam("status") String status,
			@RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		model.put(DEVICE_ONBOARDING_SEARCH_REQ, new DeviceOnboardingRequest());
		model.put(DEVICE_TYPE_LIST_DATA, new DeviceModelRequest());
		DeviceOnboardProfileUpdateRequest request = new DeviceOnboardProfileUpdateRequest();
		DeviceOnboardSearchResponse response = new DeviceOnboardSearchResponse();
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setStatus(status);
			request.setDeviceOnboardId(deviceOnboardId);
			request.setReason(reason);
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = deviceOnboardService.updateDeviceOnboardStatus(request);
				if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
					if (Constants.ACTIVE.equalsIgnoreCase(status)) {
						model.put(Constants.SUCCESS, properties.getProperty("device.onboarding.status.suspend.changed")
								.replaceAll(DEVICE_ONBOARDING_ID, String.valueOf(response.getDeviceOnboardId())));
					} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
						model.put(Constants.SUCCESS, properties.getProperty("device.onboarding.status.active.changed")
								.replaceAll(DEVICE_ONBOARDING_ID, String.valueOf(response.getDeviceOnboardId())));
					} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
						model.put(Constants.SUCCESS,
								properties.getProperty("device.onboarding.status.terminate.changed").replaceAll(
										DEVICE_ONBOARDING_ID, String.valueOf(response.getDeviceOnboardId())));
					}
					return searchDataPaginationDeviceOnboard(session,
							(Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
				}
			
		} catch (AFCSException e) {
			logger.error("ERROR:: DeviceOnboardController :: updateDeviceOnboardStatus method: Exception ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}
	
	@PostMapping(value = DOWNLOAD_DEVICE_ONBOARDING_REPORT)
	public ModelAndView downloadDeviceOnboardingReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: DeviceModelController:: downloadDeviceReport method");
		ModelAndView modelAndView = new ModelAndView(DEVICE_ONBOARDING_SEARCH);
		DeviceOnboardListSearchResponse deviceOnboardResponse = null;
		try {
			DeviceOnboardSearchRequest deviceOnboardSearchRequest = (DeviceOnboardSearchRequest) session.getAttribute(DEVICE_ONBOARDING_LIST_DATA);
			deviceOnboardSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = deviceOnboardSearchRequest.getPageSize();
			if (downloadAllRecords) {
				deviceOnboardSearchRequest.setPageIndex(Constants.ONE);
				deviceOnboardSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			deviceOnboardResponse = deviceOnboardService.searchDeviceOnboard(deviceOnboardSearchRequest);
			List<DeviceOnboardSearchResponse> reuestResponses = deviceOnboardResponse.getListDeviceOnboard();
			if (!StringUtil.isNull(deviceOnboardResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadDeviceOnboardingReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			deviceOnboardSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: DeviceModelController:: downloadDeviceModelReport method", e);
		}
		logger.info("Exit:: DeviceModelController:: downloadDeviceModelReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadDeviceOnboardingReport(List<DeviceOnboardSearchResponse> deviceOnboardSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DeviceOnboarding_");
		exportDetails.setHeaderMessageProperty("chatak.header.deviceOnboarding.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(deviceOnboardSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("DeviceOnboarding.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceOnboarding.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceOnboarding.file.exportutil.deviceType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceOnboarding.file.exportutil.deviceManufacturer", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceOnboarding.file.exportutil.deviceModel", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceOnboarding.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<DeviceOnboardSearchResponse> deviceOnboardResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DeviceOnboardSearchResponse deviceModelData : deviceOnboardResponse) {

			Object[] rowData = {  deviceModelData.getOrganizationName() , deviceModelData.getPtoName() , deviceModelData.getDeviceTypeName() , deviceModelData. getDeviceManufacturer() , deviceModelData.getDeviceModel(), 
						 deviceModelData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}
