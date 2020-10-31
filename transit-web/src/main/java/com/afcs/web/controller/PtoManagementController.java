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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RegionManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.CityResponse;
import com.chatak.transit.afcs.server.pojo.web.CountryRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.StateListResponse;
import com.chatak.transit.afcs.server.pojo.web.StateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class PtoManagementController {

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	RegionManagementService regionManagementService;

	@Autowired
	Environment properties;

	@Autowired
	OrganizationManagementService organizationManagementService;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(PtoManagementController.class);

	private static final String PTO_REGISTRATION = "pto-registration";
	private static final String PTO_SEARCH = "pto-search";
	private static final String PTO_EDIT_ACTION = "pto-edit";
	private static final String PTO_VIEW = "pto-view";
	private static final String PTO_EDIT_REQ = "ptoEdit";
	private static final String PTO_UPDATE = "pto-update";
	private static final String PTO_LIST_SIZE = "ptoSize";
	private static final String PTO_LIST_PAGINATION = "pto-pagination";
	private static final String UPDATE_STATUS = "update-pto-status";
	private static final String PTO_DATA = "ptoData";
	private static final String PTO_SEARCH_REQUEST = "ptoSearch";
	private static final String PTO_VIEW_REQUEST = "ptoView";
	private static final String PTO_NAME = "&&ptoName";
	private static final String DOWNLOAD_PTO_DOWNLOAD = "downloadPtoDownloadReport";
	
	@GetMapping(value = PTO_REGISTRATION)
	public ModelAndView ptoRegistrationGet(Map<String, Object> model, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView(PTO_REGISTRATION);
		PtoRegistrationRequest ptoRegistrationRequest = new PtoRegistrationRequest();
		model.put("ptoRequest", ptoRegistrationRequest);
		getAllStates(model, session);
		return modelAndView;
	}

	private void getAllStates(Map<String, Object> model, HttpSession session) {
		StateListResponse stateResponse = new StateListResponse();
		stateResponse = regionManagementService.getAllStates(stateResponse);
		if (!StringUtil.isNull(stateResponse) && stateResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("stateList", stateResponse.getStateList());
			session.setAttribute("stateList", stateResponse.getStateList());
		}
	}

	@PostMapping(value = PTO_REGISTRATION)
	public ModelAndView ptoRegistrationPost(Map<String, Object> model, PtoRegistrationRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		PtoSearchRequest ptoSearchRequest = new PtoSearchRequest();
		PtoRegistrationResponse response = new PtoRegistrationResponse();
		model.put(PTO_SEARCH_REQUEST, ptoSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = ptoManagementService.ptoRegistration(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: showPtoSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtils.isEmpty(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("ptoRequest", new PtoRegistrationRequest());
			model.put(Constants.SUCCESS, properties.getProperty("pto.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		modelAndView=showPtoSearch(model, session);
		return modelAndView;
	}

	@GetMapping(value = PTO_SEARCH)
	public ModelAndView showPtoSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		PtoSearchRequest ptoSearchRequest = new PtoSearchRequest();
		model.put(PTO_SEARCH_REQUEST, ptoSearchRequest);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = null;
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}
		if (!StringUtil.isNull(ptoListResponse) && ptoListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		
		try {
			ptoSearchRequest.setPageIndex(10);
			ptoSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(PTO_DATA, ptoSearchRequest);
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
			logger.error("ERROR :: PtoManagementController class :: showPtoSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(Constants.ORGANIZATION_LIST, response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = PTO_SEARCH)
	public ModelAndView searchPto(Map<String, Object> model, PtoSearchRequest request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		PtoSearchResponse response = null;
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return searchPtoPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			request.setPageIndex(Constants.ONE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = ptoManagementService.searchPto(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = ptoManagementService.searchPto(request);
			} else {
				request.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = ptoManagementService.searchPto(request);
			}
			session.setAttribute(PTO_DATA, request);
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: searchPto method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			session.setAttribute(PTO_LIST_SIZE, response.getTotalRecords());
			session.setAttribute(PTO_SEARCH_REQUEST, request);
			model.put(PTO_DATA, response.getPtosearchList());
			model.put(PTO_SEARCH_REQUEST, request);
			PtoListRequest ptoListRequest = new PtoListRequest();
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, PTO_LIST_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, PtoSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrgId())) {
			ptoListRequest.setOrgId(request.getOrgId());
			if (!StringUtil.isNullEmpty(session.getAttribute(Constants.USER_TYPE).toString()) && !session
					.getAttribute(Constants.USER_TYPE).toString().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
				PtoListResponse ptoListResponse = ptoManagementService
						.getPtoByOrganizationIdAndUserId(ptoListRequest);
				if (!StringUtil.isNull(ptoListResponse)) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}
		}
	}

	@PostMapping(value = PTO_LIST_PAGINATION)
	public ModelAndView searchPtoPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		PtoSearchResponse response = new PtoSearchResponse();
		PtoSearchRequest request = (PtoSearchRequest) session.getAttribute(PTO_SEARCH_REQUEST);
		model.put(PTO_SEARCH, request);
		try {
			request.setPageIndex(pageNumber);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			response = ptoManagementService.searchPto(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: searchPtoPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_CODE)) {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(PTO_DATA, response.getPtosearchList());
			model.put(PTO_SEARCH_REQUEST, request);
			session.setAttribute(PTO_LIST_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, PTO_LIST_SIZE);
		}
		return modelAndView;

	}

	@PostMapping(value = PTO_VIEW)
	public ModelAndView viewPto(final HttpSession session, @RequestParam("ptoMasterId") Long ptoMasterId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PTO_VIEW);
		PtoSearchResponse response = new PtoSearchResponse();
		modelAndView.addObject(PTO_VIEW_REQUEST, new PtoUpdateRequest());
		try {
			PtoSearchRequest request = new PtoSearchRequest();
			request.setPageIndex(Constants.ONE);
			request.setPtoMasterId(ptoMasterId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = ptoManagementService.searchPto(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = ptoManagementService.searchPto(request);
			} else {
				request.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = ptoManagementService.searchPto(request);
			}

		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: viewPto method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			PtoUpdateRequest ptoView = populatePtoData(response);
			modelAndView.addObject(PTO_VIEW_REQUEST, ptoView);
			model.put(PTO_VIEW_REQUEST, ptoView);
		}
		return modelAndView;
	}

	@PostMapping(value = PTO_UPDATE)
	public ModelAndView updatePto(HttpSession session, Map<String, Object> model, PtoUpdateRequest request) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_EDIT_ACTION);
		model.put(PTO_EDIT_REQ, request);
		WebResponse response = null;
		try {
			request.setOrgId(request.getOrgId());
			response = ptoManagementService.updatePto(request);
		} catch (Exception e) {
			logger.error("ERROR :: PtoManagementController class :: updatePto method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("pto.update.success"));
			StateRequest stateRequest=new StateRequest();
			CityResponse cityResponse=new CityResponse();
			stateRequest.setStateName(request.getState());
			int stateId= regionManagementService.getStateId(stateRequest);
			stateRequest.setStateId(stateId);
			cityResponse=regionManagementService.getCities(stateRequest, cityResponse);
			model.put("cityList", cityResponse.getCityList());
			model.put(PTO_EDIT_REQ, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(PTO_EDIT_REQ, request);
		}
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}
		if (!StringUtil.isNull(ptoListResponse) && ptoListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		return modelAndView;
	}

	protected PtoUpdateRequest populatePtoData(PtoSearchResponse response) {
		PtoUpdateRequest ptoUpdateRequest = new PtoUpdateRequest();
		PtoSearchRequest ptoSearchRequest = response.getPtosearchList().get(0);
		ptoUpdateRequest.setCity(ptoSearchRequest.getCity());
		ptoUpdateRequest.setContactPerson(ptoSearchRequest.getContactPerson());
		ptoUpdateRequest.setPtoEmail(ptoSearchRequest.getPtoEmail());
		ptoUpdateRequest.setPtoMobile(ptoSearchRequest.getPtoMobile());
		ptoUpdateRequest.setPtoMasterId(ptoSearchRequest.getPtoMasterId());
		ptoUpdateRequest.setPtoName(ptoSearchRequest.getPtoName());
		ptoUpdateRequest.setSiteUrl(ptoSearchRequest.getSiteUrl());
		ptoUpdateRequest.setState(ptoSearchRequest.getState());
		ptoUpdateRequest.setOrgId(ptoSearchRequest.getOrgId());
		return ptoUpdateRequest;
	}

	@PostMapping(value = PTO_EDIT_ACTION)
	public ModelAndView editPto(final HttpSession session, @RequestParam("ptoMasterId") Long ptoMasterId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PTO_EDIT_ACTION);
		PtoSearchResponse response = new PtoSearchResponse();
		model.put(PTO_EDIT_REQ, new PtoUpdateRequest());
		modelAndView.addObject(PTO_EDIT_REQ, new PtoUpdateRequest());

		try {
			PtoSearchRequest request = new PtoSearchRequest();
			request.setPageIndex(Constants.ONE);
			request.setPtoMasterId(ptoMasterId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = ptoManagementService.searchPto(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = ptoManagementService.searchPto(request);
			} else {
				request.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = ptoManagementService.searchPto(request);
			}

		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: editPto method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		getAllStates(model, session);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			StateRequest stateRequest=new StateRequest();
			CityResponse cityResponse=new CityResponse();
			if(!StringUtil.isNullEmpty(response.getPtosearchList().get(0).getState())) {
				stateRequest.setStateName(response.getPtosearchList().get(0).getState());
				int stateId= regionManagementService.getStateId(stateRequest);
				stateRequest.setStateId(stateId);
				cityResponse=regionManagementService.getCities(stateRequest, cityResponse);
				model.put("cityList", cityResponse.getCityList());
			}
			PtoUpdateRequest ptoUpdateRequest = populatePtoData(response);
			model.put(PTO_EDIT_REQ, ptoUpdateRequest);
			modelAndView.addObject(PTO_EDIT_REQ, ptoUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_STATUS)
	public ModelAndView updatePtoStatus(final HttpSession session, @RequestParam("ptoMasterId") Long ptoMasterId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		model.put(PTO_SEARCH_REQUEST, new PtoSearchRequest());
		PtoStatusUpdateRequest request = new PtoStatusUpdateRequest();
		PtoSearchResponse response = null;
		try {
			request.setStatus(status);
			request.setPtoMasterId(ptoMasterId);
			request.setReason(reason);
			response = ptoManagementService.updatePtoStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				
				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("pto.status.suspend.changed")
							.replace(PTO_NAME, response.getPtoName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("pto.status.active.changed")
							.replace(PTO_NAME, response.getPtoName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("pto.status.terminate.changed")
							.replace(PTO_NAME, response.getPtoName()));
				}
				
				return searchPtoPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);

			}
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoManagementController class :: updatePtoStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@GetMapping(value = "getCityByStateId")
	public @ResponseBody String getCityBYStateId(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session, @RequestParam("stateName") String stateName,
			CityResponse response) throws JsonProcessingException {
		StateRequest stateRequest = new StateRequest();
		stateRequest.setStateName(stateName);
		int stateId = regionManagementService.getStateId(stateRequest);
		stateRequest.setStateId(stateId);
		response = regionManagementService.getCities(stateRequest, response);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(response);
		}
		return null;
	}

	@GetMapping(value = "getPtoByOrganizationId")
	public @ResponseBody String getPtoByOrganizationName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("orgId") Long orgId, PtoListResponse response)
			throws JsonProcessingException, AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = null;
		ptoListRequest.setOrgId(orgId);
		ptoListRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		ptoListResponse = ptoManagementService.getPtoByOrganizationIdAndUserId(ptoListRequest);
		session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		if (!StringUtil.isNull(ptoListResponse) && ptoListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			ptoListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(ptoListResponse);
		}
		return null;
	}
	
	@GetMapping(value = "getPtoListByOrganizationId")
	public @ResponseBody String getPtoByOrganizationName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("orgId") Long organizationId)
			throws JsonProcessingException, AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setOrgId(organizationId);
		ptoListRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		PtoListResponse response = ptoManagementService.getPtoListByOrganizationIdAndUserId(ptoListRequest);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(response);
		}
		return null;
	}
	
	@GetMapping(value = "getActivePtoListByOrganizationId")
	public @ResponseBody String getActivePtoByOrganizationName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("orgId") Long organizationId)
			throws JsonProcessingException, AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setOrgId(organizationId);
		ptoListRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		PtoListResponse response = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(response);
		}
		return null;
	}
	
	@GetMapping(value = "getStateByCountryId")
	public @ResponseBody String getStateByCountryId(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("countryId") int countryId, StateListResponse response)
			throws JsonProcessingException {
		CountryRequest countryRequest = new CountryRequest();
		countryRequest.setCountryId(countryId);
		response = regionManagementService.getStates(countryRequest, response);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			response.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(response);
		}
		return null;
	}
	@PostMapping(value =  DOWNLOAD_PTO_DOWNLOAD)
	public ModelAndView downloadPtoManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: PtoManagementController:: downloadPtoManagementReport method");
		ModelAndView modelAndView = new ModelAndView(PTO_SEARCH);
		PtoSearchResponse ptoSearchResponse = null;
		try {
			PtoSearchRequest ptoSearchRearch = (PtoSearchRequest) session.getAttribute(PTO_DATA);
			ptoSearchRearch.setPageIndex(downLoadPageNumber);
			Integer pageSize = ptoSearchRearch.getPageSize();
			if (downloadAllRecords) {
				ptoSearchRearch.setPageIndex(Constants.ONE);
				ptoSearchRearch.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			ptoSearchResponse = ptoManagementService.searchPto(ptoSearchRearch);
			List<PtoSearchRequest> reuestResponses = ptoSearchResponse. getPtosearchList();
			if (!StringUtil.isNull(ptoSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsProductManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			ptoSearchRearch.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::PtoManagementController:: PtoManagementReport method", e);
		}
		logger.info("Exit:: PtoManagementController:: downloadPtoManagementReport method");
		return null;
	}

	private void setExportDetailsProductManagementReport(List<PtoSearchRequest> ptoSearchRearch,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Pto Management");
		exportDetails.setHeaderMessageProperty("chatak.header.PtoManagement.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(ptoSearchRearch));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("PtoManagement.file.exportutil.ptoId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.state", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.city", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.mobile", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.email", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
								
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}
   	
	private static List<Object[]> getDetailTypeFileData(List<PtoSearchRequest> ptoSearchRearch) {
		List<Object[]> fileData = new ArrayList<>();

		for (PtoSearchRequest ptoSearchData : ptoSearchRearch) {

	 	Object[] rowData = {  		ptoSearchData.getPtoMasterId() , ptoSearchData.getOrganizationName() , ptoSearchData.getPtoName() , ptoSearchData.getState() ,
	 			ptoSearchData.getCity()	, ptoSearchData.getPtoMobile() , ptoSearchData.getPtoEmail() , ptoSearchData.getStatus() 
	 			
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

}
