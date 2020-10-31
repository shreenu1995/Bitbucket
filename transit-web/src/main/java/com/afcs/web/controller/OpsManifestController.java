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
import com.afcs.web.service.DepotManagementService;
import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.service.OperatorManagementService;
import com.afcs.web.service.OpsManifestManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceOnboardSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OpsManifestUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class OpsManifestController {

	private static Logger logger = LoggerFactory.getLogger(OpsManifestController.class);

	@Autowired
	OpsManifestManagementService opsManifestManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OperatorManagementService operatorManagementService;

	@Autowired
	DeviceOnboardService deviceOnboardService;

	@Autowired
	DepotManagementService depotManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String OPS_MANIFEST_REGISTRATION = "opsmanifest-registration";
	private static final String OPS_MANIFEST_DATA_SIZE = "opsmanifestDataSize";
	private static final String OPS_MANIFEST_SEARCH = "opsmanifest-search";
	private static final String OPS_MANIFEST_SEARCH_PAGINATION = "opsmanifest-search-pagination";
	private static final String OPS_MANIFEST_VIEW_ACTION = "view-opsmanifest-action";
	private static final String DEVICE_ONBOARDING_ID_EXISTS_CHECK = "deviceNoExistsCheck";
	private static final String OPS_MANIFEST_SEARCH_REQUEST = "opsManifestSearchRequest";
	private static final String OPS_MANIFEST_UPDATE_REQUEST = "opsManifestUpdateRequest";
	private static final String DOWNLOAD_OPS_MANIFEST_REPORT = "downloadOpsManifestReport";
	private static final String OPS_MANIFEST_LIST = "opsmanifestList";
	
	@GetMapping(value = OPS_MANIFEST_REGISTRATION)
	public ModelAndView opsManifestRegister(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(OPS_MANIFEST_REGISTRATION);
		OpsManifestRegistrationRequest opsManifestRegistrationRequest = new OpsManifestRegistrationRequest();
		model.put("opsManifestRegistrationRequest", opsManifestRegistrationRequest);
		return modelandview;
	}

	@PostMapping(value = OPS_MANIFEST_REGISTRATION)
	public ModelAndView opsManifestRegister(Map<String, Object> model,
			OpsManifestRegistrationRequest opsManifestRegistrationRequest, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		OpsManifestSearchRequest opsManifestSearchRequest = new OpsManifestSearchRequest();
		model.put(OPS_MANIFEST_SEARCH_REQUEST, opsManifestSearchRequest);
		OpsManifestRegistrationResponse opsManifestRegistrationResponse = new OpsManifestRegistrationResponse();
		try {
			opsManifestRegistrationResponse = opsManifestManagementService
					.opsmanifestRegistration(opsManifestRegistrationRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: opsManifestRegister method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(opsManifestRegistrationResponse)
				&& opsManifestRegistrationResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("opsManifest.created.successfully"));
			model.put("opsManifestRegistrationRequest", new OpsManifestRegistrationRequest());
		} else {
			model.put(Constants.ERROR, opsManifestRegistrationResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@GetMapping(value = OPS_MANIFEST_SEARCH)
	public ModelAndView opsManifestSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		OpsManifestSearchRequest opsManifestSearchRequest = new OpsManifestSearchRequest();
		model.put(OPS_MANIFEST_SEARCH_REQUEST, opsManifestSearchRequest);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = new PtoListResponse();
		ptoListResponse = getPtoList(model, session, ptoListRequest, ptoListResponse);
		opsManifestSearchRequest.setPageSize(10);
		opsManifestSearchRequest.setPageIndex(Constants.ONE);
		session.setAttribute(OPS_MANIFEST_LIST, opsManifestSearchRequest);
		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}
		response = getOrganizationList(model, session, organizationSearchRequest, response, ptoListRequest);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		OperatorSearchRequest operatorSearchRequest = new OperatorSearchRequest();
		OperatorResponse operatorSearchResponse = null;
		operatorSearchRequest.setPageIndex(Constants.ONE);
		operatorSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		operatorSearchResponse = getOperatorList(session, operatorSearchRequest);
		if (!StringUtil.isNull(operatorSearchResponse)
				&& operatorSearchResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put("operatorListData", operatorSearchResponse.getListOfOperators());
			session.setAttribute("operatorListData", operatorSearchResponse.getListOfOperators());
		}
		DeviceOnboardSearchRequest deviceOnboardSearchRequest = new DeviceOnboardSearchRequest();
		DeviceOnboardListSearchResponse deviceOnboardListSearchResponse = null;
		deviceOnboardSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		deviceOnboardSearchRequest.setPageIndex(Constants.ONE);
		if (deviceOnboardSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			deviceOnboardListSearchResponse = deviceOnboardService.searchDeviceOnboard(deviceOnboardSearchRequest);
		} else if (deviceOnboardSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			deviceOnboardSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			deviceOnboardListSearchResponse = deviceOnboardService.searchDeviceOnboard(deviceOnboardSearchRequest);
		} else {
			ptoListRequest = new PtoListRequest();
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
			PtoList pto = new PtoList();
			if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				deviceOnboardSearchRequest.setPtoName(pto.getPtoName());
			}
			deviceOnboardListSearchResponse = deviceOnboardService.searchDeviceOnboard(deviceOnboardSearchRequest);
		}
		if (!StringUtil.isNull(deviceOnboardListSearchResponse)
				&& deviceOnboardListSearchResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("deviceOnboardingListData", deviceOnboardListSearchResponse.getListDeviceOnboard());
			session.setAttribute("deviceOnboardingListData", deviceOnboardListSearchResponse.getListDeviceOnboard());
		}
		DepotSearchRequest depotSearchRequest = new DepotSearchRequest();
		DepotSearchResponse depotSearchResponse = null;
		depotSearchRequest.setPageIndex(Constants.ONE);
		depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		depotSearchResponse = getDepotList(session, depotSearchRequest);
		if (!StringUtil.isNull(depotSearchResponse)
				&& depotSearchResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("depotListData", depotSearchResponse.getDepotListResponse());
			session.setAttribute("depotListData", depotSearchResponse.getDepotListResponse());
		}
		return modelAndView;
	}

	private DepotSearchResponse getDepotList(HttpSession session, DepotSearchRequest depotSearchRequest)
			throws AFCSException {
		PtoListRequest ptoListRequest;
		DepotSearchResponse depotSearchResponse;
		if (depotSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			depotSearchResponse = depotManagementService.searchDepot(depotSearchRequest);
		} else if (depotSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			depotSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			depotSearchResponse = depotManagementService.searchDepot(depotSearchRequest);
		} else {
			ptoListRequest = new PtoListRequest();
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
			PtoList pto = new PtoList();
			if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				depotSearchRequest.setPtoName(pto.getPtoName());
			}
			depotSearchRequest.setPageIndex(Constants.ONE);
			depotSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			depotSearchResponse = depotManagementService.searchDepot(depotSearchRequest);
		}
		return depotSearchResponse;
	}

	private PtoListResponse getPtoList(Map<String, Object> model, HttpSession session, PtoListRequest ptoListRequest,
			PtoListResponse ptoListResponse) {
		try {
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
			logger.error("ERROR :: OpsManifestController class :: opsManifestSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
		}
		return ptoListResponse;
	}

	private OperatorResponse getOperatorList(HttpSession session, OperatorSearchRequest operatorSearchRequest)
			throws AFCSException {
		PtoListRequest ptoListRequest;
		OperatorResponse operatorSearchResponse;
		if (operatorSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);
		} else if (operatorSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			operatorSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);
		} else {
			ptoListRequest = new PtoListRequest();
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
			PtoList pto = new PtoList();
			if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				operatorSearchRequest.setPtoName(pto.getPtoName());
			}
			operatorSearchRequest.setPageIndex(Constants.ONE);
			operatorSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);

		}
		return operatorSearchResponse;
	}

	private OrganizationSearchResponse getOrganizationList(Map<String, Object> model, HttpSession session,
			OrganizationSearchRequest organizationSearchRequest, OrganizationSearchResponse response,
			PtoListRequest ptoListRequest) {
		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					organizationSearchRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(organizationSearchRequest);
				}
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: opsManifestSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
		}
		return response;
	}

	@PostMapping(value = OPS_MANIFEST_SEARCH)
	public ModelAndView opsManifestSearchData(Map<String, Object> model, OpsManifestSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) {
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		OpsManifestSearchResponse response = null;
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return opsmanifestSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		try {
			request.setPageIndex(Constants.ONE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = opsManifestManagementService.searchOpsManifest(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = opsManifestManagementService.searchOpsManifest(request);
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
				response = opsManifestManagementService.searchOpsManifest(request);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: opsManifestSearchData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(OPS_MANIFEST_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(OPS_MANIFEST_SEARCH_REQUEST, request);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(OPS_MANIFEST_LIST, response.getOpsManifestList());
			model.put(OPS_MANIFEST_SEARCH_REQUEST, request);
			model.put(OPS_MANIFEST_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, OPS_MANIFEST_DATA_SIZE);
		}
		session.setAttribute(OPS_MANIFEST_LIST, request);
		return modelAndView;
	}

	@PostMapping(value = OPS_MANIFEST_SEARCH_PAGINATION)
	public ModelAndView opsmanifestSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		OpsManifestSearchRequest opsManifestSearchRequest = (OpsManifestSearchRequest) session
				.getAttribute(OPS_MANIFEST_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		model.put(OPS_MANIFEST_SEARCH_REQUEST, opsManifestSearchRequest);
		OpsManifestSearchResponse response = new OpsManifestSearchResponse();
		try {
			opsManifestSearchRequest.setPageIndex(pageNumber);
			response = opsManifestManagementService.searchOpsManifest(opsManifestSearchRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: opsmanifestSearchDataPagination method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(OPS_MANIFEST_LIST, response.getOpsManifestList());
			model.put(OPS_MANIFEST_SEARCH_REQUEST, opsManifestSearchRequest);
			session.setAttribute(OPS_MANIFEST_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, OPS_MANIFEST_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = OPS_MANIFEST_VIEW_ACTION)
	public ModelAndView viewOpsManifestData(final HttpSession session,
			@RequestParam("opsManifestId") Long opsManifestId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("opsmanifest-view");
		OpsManifestSearchResponse response = new OpsManifestSearchResponse();
		model.put(OPS_MANIFEST_UPDATE_REQUEST, new OpsManifestUpdateRequest());
		try {
			OpsManifestSearchRequest request = new OpsManifestSearchRequest();
			request.setPageIndex(1);
			request.setOpsManifestId(opsManifestId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = opsManifestManagementService.searchOpsManifest(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = opsManifestManagementService.searchOpsManifest(request);
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
				response = opsManifestManagementService.searchOpsManifest(request);
			}
			OpsManifestUpdateRequest opsManifestUpdateRequest = populateviewRequest(response);
			model.put(OPS_MANIFEST_UPDATE_REQUEST, opsManifestUpdateRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: viewOpsManifestData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	private OpsManifestUpdateRequest populateviewRequest(OpsManifestSearchResponse response) {
		OpsManifestUpdateRequest opsManifestUpdateRequest = new OpsManifestUpdateRequest();
		OpsManifestSearchRequest opsManifestSearchRequest = response.getOpsManifestList().get(0);
		opsManifestUpdateRequest.setDepotId(opsManifestSearchRequest.getDepotId());
		opsManifestUpdateRequest.setDepotName(opsManifestSearchRequest.getDepotName());
		opsManifestUpdateRequest.setDeviceNo(opsManifestSearchRequest.getDeviceNo());
		opsManifestUpdateRequest.setOperatorName(opsManifestSearchRequest.getOperatorName());
		opsManifestUpdateRequest.setOpsManifestId(opsManifestSearchRequest.getOpsManifestId());
		opsManifestUpdateRequest.setPtoId(opsManifestSearchRequest.getPtoId());
		opsManifestUpdateRequest.setOrganizationId(opsManifestSearchRequest.getOrganizationId());
		opsManifestUpdateRequest.setPtoName(opsManifestSearchRequest.getPtoName());
		opsManifestUpdateRequest.setOperatorId(opsManifestSearchRequest.getOperatorId());
		opsManifestUpdateRequest.setOrganizationName(opsManifestSearchRequest.getOrganizationName());
		opsManifestUpdateRequest.setDate(opsManifestSearchRequest.getDate());
		return opsManifestUpdateRequest;
	}

	@PostMapping(value = "opsManifest-edit-action")
	public ModelAndView editOpsManifestData(final HttpSession session,
			@RequestParam("opsManifestId") Long opsManifestId, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("opsmanifest-edit");
		OpsManifestSearchResponse response = new OpsManifestSearchResponse();
		try {
			OpsManifestSearchRequest request = new OpsManifestSearchRequest();
			request.setPageIndex(1);
			request.setOpsManifestId(opsManifestId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = opsManifestManagementService.searchOpsManifest(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = opsManifestManagementService.searchOpsManifest(request);
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
				response = opsManifestManagementService.searchOpsManifest(request);
			}

		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: editOpsManifestData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			OpsManifestUpdateRequest opsManifestUpdateRequest = populateviewRequest(response);
			model.put(OPS_MANIFEST_UPDATE_REQUEST, opsManifestUpdateRequest);
			modelAndView.addObject(OPS_MANIFEST_UPDATE_REQUEST, opsManifestUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = "update-opsManifest-status")
	public ModelAndView updateOpsManifestStatus(final HttpSession session,
			@RequestParam("opsManifestId") Long opsManifestId, @RequestParam("status") String status,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		model.put(OPS_MANIFEST_SEARCH_REQUEST, new OpsManifestSearchRequest());
		OpsManifestStatusChangeRequest request = new OpsManifestStatusChangeRequest();
		WebResponse response = null;
		try {
			request.setStatus(status);
			request.setOpsManifestId(opsManifestId);
			response = opsManifestManagementService.updateOpsManifestStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("opsManifest.active.status.update.success"));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("opsManifest.suspend.status.update.success"));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("opsManifest.terminate.status.update.success"));
				}
				return opsmanifestSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
						model);
			}
		} catch (AFCSException e) {
			logger.error("OpsManifestController class :: updateOpsManifestStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = "opsmanifest-edit")
	public ModelAndView updateOpsManifest(HttpSession session, Map<String, Object> model,
			OpsManifestUpdateRequest request, @RequestParam("cancelTypeId") String cancelType) {
		ModelAndView modelAndView = new ModelAndView("opsmanifest-edit");
		WebResponse response = null;
		model.put(OPS_MANIFEST_UPDATE_REQUEST, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return opsmanifestSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		try {
			response = opsManifestManagementService.updateOpsManifest(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: OpsManifestController class :: updateOpsManifest method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("opsManifest.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(OPS_MANIFEST_UPDATE_REQUEST, request);
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_OPS_MANIFEST_REPORT)
	public ModelAndView downloadOpsManifestReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: OpsManifestController:: downloadOpsManifestReport method");
		ModelAndView modelAndView = new ModelAndView(OPS_MANIFEST_SEARCH);
		OpsManifestSearchResponse opsManifestSearchResponse = null;
		try {
			OpsManifestSearchRequest opsManifestSearchRequest = (OpsManifestSearchRequest) session.getAttribute(OPS_MANIFEST_LIST);
			opsManifestSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = opsManifestSearchRequest.getPageSize();
			opsManifestSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				opsManifestSearchRequest.setPageIndex(Constants.ONE);
				opsManifestSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			opsManifestSearchResponse =opsManifestManagementService.searchOpsManifest(opsManifestSearchRequest);
			List<OpsManifestSearchRequest> reuestResponses = opsManifestSearchResponse. getOpsManifestList();
			if (!StringUtil.isNull(opsManifestSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadOpsManifestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			opsManifestSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::OpsManifestController:: OpsManifestReport method", e);
		}
		logger.info("Exit:: OpsManifestController:: downloadOpsManifestReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadOpsManifestReport(List<OpsManifestSearchRequest> opsManifestSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("OpsManifest");
		exportDetails.setHeaderMessageProperty("chatak.header.opsManifest.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(opsManifestSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("OpsManifest.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.date", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.operator", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.opsManifestNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.deviceNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.depotName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.depotCode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OpsManifest.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<OpsManifestSearchRequest> opsManifestSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (OpsManifestSearchRequest opsManifestData : opsManifestSearchRequest) {

			Object[] rowData = { opsManifestData.getOrganizationName() , opsManifestData.getPtoName() , opsManifestData.getDate() , opsManifestData.getOperatorName() , opsManifestData.getOpsManifestId() ,         
								 opsManifestData.getDeviceNo() , opsManifestData.getDepotName() , opsManifestData.getDepotId() ,opsManifestData.getStatus()  
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

	@GetMapping(value = DEVICE_ONBOARDING_ID_EXISTS_CHECK)
	public @ResponseBody String userExistsCheck(@RequestParam("deviceNo") final Long deviceNo) {
		return opsManifestManagementService.deviceOnboardIdExistCheck(deviceNo);
	}
}
