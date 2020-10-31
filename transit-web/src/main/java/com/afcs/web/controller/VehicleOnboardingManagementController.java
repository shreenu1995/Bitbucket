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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.VehicleManufacturerManagementService;
import com.afcs.web.service.VehicleModelManagementService;
import com.afcs.web.service.VehicleOnboardingManagementService;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.afcs.web.util.ExportUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleOnboardingSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class VehicleOnboardingManagementController{

	@Autowired
	VehicleManufacturerManagementService vehicleManufacturerManagementService;

	@Autowired
	VehicleModelManagementService vehicleModelManagementService;

	@Autowired
	VehicleOnboardingManagementService vehicleOnboardingManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(VehicleOnboardingManagementController.class);
	private static final String VEHICLE_ONBOARD_SEARCH = "vehicle-onboard-search";
	private static final String VEHICLE_ONBOARD_SARCH = "vehicleOnboardSearch";
	private static final String VEHICLE_ONBOARD_SEARCH_PAGINATION = "vehicle-onboard-search-pagination";
	private static final String VEHICLE_ONBOARD_DATA = "vehicleOnboardData";
	private static final String VEHICLE_ONBOARD_DATA_SIZE = "vehicleOnboardDataSize";
	private static final String VEHICLE_ONBOARD_VIEW_ACTION = "vehicle-onboard-view-action";
	private static final String VEHICLE_ONBOARD_VIEW_REQ = "vehicleOnboardViewRequest";
	private static final String VEHICLE_ONBOARD_REGISTRATION = "vehicle-onboard-registration";
	private static final String FETCH_VEHICLE_MODEL_FOR_MANFUACTURER_NAME = "fetchVehicleModelForManufacturerName";
	private static final String VEHICLE_ONBOARD_EDIT_ACTION = "vehicleOnboardEditRequest";
	private static final String VEHICLE_ONBOARD_UPDATE = "vehicle-onboard-update";
	private static final String VEHICLE_ONBOARD_EDIT_REQ = "vehicleOnboardEditRequest";
	private static final String UPDATE_VEHICLE_ONBOARD_STATUS = "update-vehicle-onboard-status";
	private static final String VEHICLE_TYPE_LIST = "vehicleTypeList";
	private static final String ERROR_SEARCH_VEHICLE_ONBOARD = "ERROR :: VehicleOnboardingManagementController class :: searchVehicleOnboard method :: exception";
	private static final String DOWNLOAD_VEHICLE_ONBOARDING_REPORT = "downloadVehicleOnboardingReport";
	private static final String VEHICLE_ONBOARDING_ID = "&&vehicleOnboardingId";
	
	@GetMapping(value = VEHICLE_ONBOARD_REGISTRATION)
	public ModelAndView registerVehicleOnboard(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandView = new ModelAndView(VEHICLE_ONBOARD_REGISTRATION);
		VehicleOnboardingRequest request = new VehicleOnboardingRequest();
		model.put("vehicleOnboardRequest", request);
		return modelandView;
	}

	@GetMapping(value = FETCH_VEHICLE_MODEL_FOR_MANFUACTURER_NAME)
	public @ResponseBody String getModelForManufacturerNameAndVehicleType(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String selectedManufacturerId = request.getParameter("selectedManufacturerId");
		String status = request.getParameter("status");
		VehicleModelSearchRequest vehicleModelSearchRequest = new VehicleModelSearchRequest();
		try {
			if (!StringUtil.isNullEmpty(selectedManufacturerId)) {
				vehicleModelSearchRequest.setVehicleManufacturerId(Integer.valueOf(selectedManufacturerId));
				vehicleModelSearchRequest.setStatus(status);
				VehicleModelListResponse listOfVehicleModel = vehicleOnboardingManagementService
						.getVehicleModelByManufacturerName(vehicleModelSearchRequest);
				return JsonUtil.convertObjectToJSON(listOfVehicleModel);
			}

		} catch (Exception e) {
			logger.error(
					"ERROR:: VehicleOnboardingManagementController:: getModelForManufacturerNameAndDeviceType method :: Exception",
					e);
		}
		return null;
	}

	@PostMapping(value = VEHICLE_ONBOARD_REGISTRATION)
	public ModelAndView registerVehicleOnboard(Map<String, Object> model, VehicleOnboardingRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		VehicleOnboardingSearchRequest vehicleOnboardingSearchRequest = new VehicleOnboardingSearchRequest();
		model.put(VEHICLE_ONBOARD_SARCH, vehicleOnboardingSearchRequest);
		WebResponse response = new WebResponse();
		try {
			response = vehicleOnboardingManagementService.saveVehicleOnboardRegistration(request);
		} catch (AFCSException e) {
			logger.error("VehicleOnboardingManagementController class :: registerVehicleOnboard method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.onboarding.created.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = VEHICLE_ONBOARD_SEARCH)
	public ModelAndView searchVehicleOnboard(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		VehicleOnboardingSearchRequest vehicleOnboardingSearchRequest = new VehicleOnboardingSearchRequest();
		vehicleOnboardingSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(VEHICLE_ONBOARD_SARCH, vehicleOnboardingSearchRequest);
		
		List<VehicleTypeSearchRequest> listVehicle = vehicleManufacturerManagementService.getVehicleType().getListOfVehicleType();
		session.setAttribute(VEHICLE_TYPE_LIST, listVehicle);
		model.put(VEHICLE_TYPE_LIST, listVehicle);

		List<VehicleManufacturerResponse> listVehicleManufacturer = vehicleManufacturerManagementService.getVehicleManuf()
				.getListOfManufacturer();
		session.setAttribute("vehicleManufList", listVehicleManufacturer);
		model.put("vehicleManufList", listVehicleManufacturer);

		List<VehicleModelResponse> listOfVehicleModel = vehicleManufacturerManagementService.getVehicleModel()
				.getListOfVehicleModel();
		model.put("vehicleModelList", listOfVehicleModel);
		session.setAttribute("vehicleModelList", listOfVehicleModel);

		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response =new  OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = null;
		try {
			vehicleOnboardingSearchRequest.setPageSize(10);
			vehicleOnboardingSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(VEHICLE_ONBOARD_DATA, vehicleOnboardingSearchRequest);
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
			logger.error(
					ERROR_SEARCH_VEHICLE_ONBOARD,
					e);
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
			logger.error(
					ERROR_SEARCH_VEHICLE_ONBOARD,
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_ONBOARD_SEARCH)
	public ModelAndView searchVehicleOnboard(Map<String, Object> model, VehicleOnboardingSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		model.put(VEHICLE_ONBOARD_SARCH, new VehicleOnboardingSearchRequest());
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			model.put(VEHICLE_ONBOARD_SARCH, new VehicleOnboardingSearchRequest());
			return vehicleOnboardDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		VehicleOnboardingSearchResponse response = null;
		request.setPageIndex(Constants.ONE);
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(session.getAttribute(Constants.ORG_ID).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);

			} else {
				request.setPageIndex(Constants.ONE);
				request.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
			}
			session.setAttribute(VEHICLE_ONBOARD_DATA, request);
		} catch (AFCSException e) {
			logger.error(
					ERROR_SEARCH_VEHICLE_ONBOARD,
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(VEHICLE_ONBOARD_DATA_SIZE, response.getTotalNoOfCount());
			session.setAttribute(VEHICLE_ONBOARD_SARCH,request);
			model.put(VEHICLE_TYPE_LIST, session.getAttribute(VEHICLE_TYPE_LIST));
			model.put(VEHICLE_ONBOARD_DATA, response.getListOfOnboarding());
			model.put(VEHICLE_ONBOARD_DATA_SIZE, response.getTotalNoOfCount());
			model.put(VEHICLE_ONBOARD_SARCH, request);
			modelAndView.addObject(VEHICLE_TYPE_LIST, session.getAttribute(VEHICLE_TYPE_LIST));
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, VEHICLE_ONBOARD_DATA_SIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, VehicleOnboardingSearchRequest request, HttpSession session,
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

	@PostMapping(value = VEHICLE_ONBOARD_SEARCH_PAGINATION)
	public ModelAndView vehicleOnboardDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		VehicleOnboardingSearchRequest request = (VehicleOnboardingSearchRequest) session
				.getAttribute(VEHICLE_ONBOARD_SARCH);
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		VehicleOnboardingSearchResponse response = new VehicleOnboardingSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error(
					"ERROR:: VehicleOnboardingManagementController:: vehicleOnboardDataPagination method :: Exception",
					e);
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_ONBOARD_DATA, response.getListOfOnboarding());
			model.put(VEHICLE_ONBOARD_DATA_SIZE, response.getTotalNoOfCount());
			session.setAttribute(VEHICLE_ONBOARD_DATA_SIZE, response.getTotalNoOfCount());
			model.put(VEHICLE_ONBOARD_SARCH, request);
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, VEHICLE_ONBOARD_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_ONBOARD_VIEW_ACTION)
	public ModelAndView viewVehicleOnboardData(final HttpSession session,
			@RequestParam("vehicleOnboardingId") Long vehicleOnboardingId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("vehicle-onboard-view");
		VehicleOnboardingSearchResponse response = new VehicleOnboardingSearchResponse();
		model.put(VEHICLE_ONBOARD_VIEW_REQ, new VehicleOnboardingRequest());
		VehicleOnboardingSearchRequest request = new VehicleOnboardingSearchRequest();
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			request.setPageIndex(Constants.ONE);
			request.setVehicleOnboardingId(vehicleOnboardingId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(session.getAttribute(Constants.ORG_ID).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);

			} else {
				request.setPageIndex(Constants.ONE);
				request.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: VehicleOnboardingManagementController:: viewVehicleOnboardData method :: Exception",
					e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleOnboardProfileUpdate viewRequest = populateUpdateVehicleonBoard(response);
			model.put(VEHICLE_ONBOARD_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_ONBOARD_EDIT_ACTION)
	public ModelAndView editVehicleOnboardData(final HttpSession session,
			@RequestParam("vehicleOnboardingId") Long vehicleOnboardId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_UPDATE);
		VehicleOnboardingSearchResponse response = new VehicleOnboardingSearchResponse();
		VehicleOnboardingSearchRequest request = new VehicleOnboardingSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = new PtoListResponse();
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			request.setPageIndex(Constants.ONE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			request.setVehicleOnboardingId(vehicleOnboardId);
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
				ptoListRequest.setOrgId(response.getListOfOnboarding().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(session.getAttribute(Constants.ORG_ID).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
				ptoListRequest.setOrgId(response.getListOfOnboarding().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else {
				request.setPtoId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = vehicleOnboardingManagementService.vehicleOnboardSearch(request);
				if (ptoListResponse.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: VehicleOnboardingManagementController:: editVehicleOnboardData method :: Exception",
					e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleOnboardProfileUpdate vehicleOnboardProfileUpdate = populateUpdateVehicleonBoard(response);
			model.put(VEHICLE_ONBOARD_EDIT_REQ, vehicleOnboardProfileUpdate);
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_ONBOARD_UPDATE)
	public ModelAndView updateVehicleOnboardData(HttpSession session, Map<String, Object> model,
			VehicleOnboardProfileUpdate request) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_UPDATE);
		WebResponse response = null;
		model.put(VEHICLE_ONBOARD_EDIT_REQ, request);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = vehicleOnboardingManagementService.updateVehicleOnboard(request);
		} catch (AFCSException e) {
			logger.error("Exception :: VehicleOnboardingManagementController :: updateVehicleOnboardData :  ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.onboarding.profile.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(VEHICLE_ONBOARD_EDIT_REQ, request);
		}
		return modelAndView;
	}

	private VehicleOnboardProfileUpdate populateUpdateVehicleonBoard(VehicleOnboardingSearchResponse response) {
		VehicleOnboardProfileUpdate request = new VehicleOnboardProfileUpdate();
		VehicleOnboardingResponse vehicleOnboardingResponse = response.getListOfOnboarding().get(0);
		request.setPtoName(vehicleOnboardingResponse.getPtoName());
		request.setVehicleManufacturerName(vehicleOnboardingResponse.getVehicleManufacturerName());
		request.setVehicleModelName(vehicleOnboardingResponse.getVehicleModelName());
		request.setVehicleType(vehicleOnboardingResponse.getVehicleType());
		request.setOrganizationName(vehicleOnboardingResponse.getOrganizationName());
		request.setVehicleOnboardingId(vehicleOnboardingResponse.getVehicleOnboardingId());
		request.setVehicleTypeId(vehicleOnboardingResponse.getVehicleTypeId());
		request.setVehicleManufacturerId(vehicleOnboardingResponse.getVehicleManufacturerId());
		request.setVehicleModelId(vehicleOnboardingResponse.getVehicleModelId());
		request.setOrganizationId(vehicleOnboardingResponse.getOrganizationId());
		request.setPtoId(vehicleOnboardingResponse.getPtoId());
		return request;

	}

	@PostMapping(value = UPDATE_VEHICLE_ONBOARD_STATUS)
	public ModelAndView updateVehicleStatus(final HttpSession session,
			@RequestParam("vehicleOnboardingId") Long vehicleOnboardId, @RequestParam("status") String status,
			@RequestParam("reason") String reason, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		model.put(VEHICLE_ONBOARD_SARCH, new VehicleOnboardingSearchRequest());
		VehicleOnboardStatusUpdate request = new VehicleOnboardStatusUpdate();
		VehicleOnboardingResponse response = null;
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		request.setStatus(status);
		request.setVehicleOnboardingId(vehicleOnboardId);
		request.setReason(reason);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		response = vehicleOnboardingManagementService.updateVehicleOnboardStatus(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.onboarding.status.suspend.changed")
						.replaceAll(VEHICLE_ONBOARDING_ID, String.valueOf(response.getVehicleOnboardingId())));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.onboarding.status.active.changed")
						.replaceAll(VEHICLE_ONBOARDING_ID, String.valueOf(response.getVehicleOnboardingId())));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.onboarding.status.terminate.changed")
						.replaceAll(VEHICLE_ONBOARDING_ID, String.valueOf(response.getVehicleOnboardingId())));
			}
			return vehicleOnboardDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		return modelAndView;
	}
	@PostMapping(value = DOWNLOAD_VEHICLE_ONBOARDING_REPORT)
	public ModelAndView downloadVehicleOnboardReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: VehiclewOnboardController:: downloadVehicleReport method");
		ModelAndView modelAndView = new ModelAndView(VEHICLE_ONBOARD_SEARCH);
		VehicleOnboardingSearchResponse vehicleOnboardingSearchResponse = null;
		try {
			VehicleOnboardingSearchRequest vehicleOnboardingRequest = (VehicleOnboardingSearchRequest) session.getAttribute(VEHICLE_ONBOARD_DATA);
			vehicleOnboardingRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = vehicleOnboardingRequest.getPageSize();
			if (downloadAllRecords) {
				vehicleOnboardingRequest.setPageIndex(Constants.ONE);
				vehicleOnboardingRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			vehicleOnboardingSearchResponse = vehicleOnboardingManagementService.vehicleOnboardSearch(vehicleOnboardingRequest);
			List<VehicleOnboardingResponse> reuestResponses = vehicleOnboardingSearchResponse.getListOfOnboarding();
			if (!StringUtil.isNull(vehicleOnboardingSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleOnboardReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			vehicleOnboardingRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: VehicleModelController:: downloadVehicleModelReport method", e);
		}
		logger.info("Exit:: VehicleModelController:: downloadVehicleModelReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleOnboardReport(List<VehicleOnboardingResponse> vehicleOnboardSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("VehicleOnboarding_");
		exportDetails.setHeaderMessageProperty("chatak.header.vehicleOnboarding.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(vehicleOnboardSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("VehicleOnboard.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleOnboard.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleOnboard.file.exportutil.vehicleTypeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleOnboard.file.exportutil.vehicleManufacturerName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleOnboard.file.exportutil.vehicleModelName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleOnboard.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<VehicleOnboardingResponse> vehicleOnboardResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (VehicleOnboardingResponse vehicleOnboardData : vehicleOnboardResponse) {

			Object[] rowData = { 
					vehicleOnboardData.getOrganizationName() ,vehicleOnboardData.getPtoName() , vehicleOnboardData.getVehicleType(), vehicleOnboardData.getVehicleManufacturerName() , vehicleOnboardData.getVehicleModelName() ,
					vehicleOnboardData.getStatus() ,
			};
			fileData.add(rowData);
		}

		return fileData;
	}

}
