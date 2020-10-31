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
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.TransitMasterService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterListDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.TransitMasterUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserData;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class TransitMasterController {

	@Autowired
	TransitMasterService transitMasterService;

	@Autowired
	Environment properties;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(TransitMasterController.class);
	private static final String TRANSIT_MASTER_DATA_SIZE = "setupTransitMasterDataSize";
	private static final String TRANSIT_MASTER_SEARCH = "transit-master-search";
	private static final String TRANSIT_MASTER_SEARCH_PAGINATION = "transit-master-search-pagination";
	private static final String TRANSIT_MASTER_VIEW_ACTION = "view-transit-master-action";
	private static final String TRANSIT_MASTER_REQUEST = "transitMasterRegistrationRequest";
	private static final String TRANSIT_MASTER_EDIT_ACTION = "edit-transit-master-action";
	private static final String TRANSIT_MASTER_EDIT_REQ = "transitMasterEditRequest";
	private static final String TRANSIT_MASTER_UPDATE = "transit-master-edit";
	private static final String UPDATE_TRANSIT_MASTER_STATUS = "update-transit-master-status";
	private static final String TRANSIT_MASTER_SEARCH_DATA = "setupTransitMasterSearchRequest";
	private static final String TRANSIT_MASTER_INHERIT_PTO_LIST = "transitMasterInheritPtoList";
	private static final String PTO_LIST_DATA = "ptoListData";
	private static final String SETUP_TRANSIT_MASTER_LIST_DATA = "setupTransitMasterListData";
	private static final String DOWNLOAD_TRANSIT_MASTER_REPORT = "downloadTransitMasterReport";

	@GetMapping(value = "transit-master-registration")
	public ModelAndView transitMasterRegistrationGet(Map<String, Object> model, HttpSession session)
			throws AFCSException {
		ModelAndView modelandview = new ModelAndView("transit-master-registration");
		TransitMasterSearchRequest transitMasterSearchRequest = new TransitMasterSearchRequest();
		transitMasterSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		TransitMasterRegistrationRequest transitMasterRegistrationRequest = new TransitMasterRegistrationRequest();
		TransitMasterListDataResponse response = transitMasterService
				.getTransitMasterListDataResponse(transitMasterRegistrationRequest);
		model.put(TRANSIT_MASTER_REQUEST, transitMasterRegistrationRequest);
		model.put(TRANSIT_MASTER_INHERIT_PTO_LIST, response.getListOfInheritAndPto());
		model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
		session.setAttribute(TRANSIT_MASTER_INHERIT_PTO_LIST, response.getListOfInheritAndPto());
		model.put(PTO_LIST_DATA, session.getAttribute(Constants.PTO_LIST_DATA));
		return modelandview;
	}

	@PostMapping(value = "transit-master-registration")
	public ModelAndView transitMasterRegistrationPost(Map<String, Object> model,
			TransitMasterRegistrationRequest transitMasterRegistrationRequest, HttpSession session)
			throws AFCSException {

		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		TransitMasterSearchRequest transitMasterSearchRequest = new TransitMasterSearchRequest();
		transitMasterSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
		TransitMasterRegistrationResponse transitMasterRegistrationResponse = new TransitMasterRegistrationResponse();
		TransitMasterRegistrationRequest request = new TransitMasterRegistrationRequest();
		try {
			transitMasterRegistrationResponse = transitMasterService
					.transitMasterRegistrationResponse(transitMasterRegistrationRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(transitMasterRegistrationResponse)
				&& transitMasterRegistrationResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("transit.master.created.successfully"));
			model.put(TRANSIT_MASTER_REQUEST, request);
		} else {
			model.put(Constants.ERROR, transitMasterRegistrationResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@GetMapping(value = TRANSIT_MASTER_SEARCH)
	public ModelAndView transitMasterSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		TransitMasterSearchRequest request = new TransitMasterSearchRequest();
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		organizationSearchRequest.setPageIndex(Constants.ONE);
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = null;
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());

		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}
		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		try {
			request.setPageSize(10);
			request.setPageIndex(Constants.ONE);
			session.setAttribute(SETUP_TRANSIT_MASTER_LIST_DATA, request);
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
			logger.error(
					"ERROR :: PtoOperationManagementController class :: showPtoOperationSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}

		model.put(TRANSIT_MASTER_SEARCH_DATA, request);
		model.put(PTO_LIST_DATA, session.getAttribute(Constants.PTO_LIST_DATA));
		return modelAndView;
	}

	@PostMapping(value = TRANSIT_MASTER_SEARCH)
	public ModelAndView transitMasterSearch(Map<String, Object> model,
			TransitMasterSearchRequest transitMasterSearchRequest, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		TransitMasterSearchResponse response = null;
		transitMasterSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return setupTransitMasterSearchDataPagination(session,
					(Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		transitMasterSearchRequest.setPageIndex(Constants.ONE);
		try {
			if (transitMasterSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			} else if (transitMasterSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
				transitMasterSearchRequest
						.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				transitMasterSearchRequest.setPtoName(pto.getPtoName());
				transitMasterSearchRequest.setPageIndex(Constants.ONE);
				transitMasterSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			}
			session.setAttribute(SETUP_TRANSIT_MASTER_LIST_DATA, transitMasterSearchRequest);
			
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(TRANSIT_MASTER_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(SETUP_TRANSIT_MASTER_LIST_DATA, response.getTransitMasterSearchList());
			model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
			model.put(TRANSIT_MASTER_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, transitMasterSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, TRANSIT_MASTER_DATA_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, TransitMasterSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrganizationId())) {
			ptoListRequest.setOrgId(request.getOrganizationId());
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

	@PostMapping(value = TRANSIT_MASTER_SEARCH_PAGINATION)
	public ModelAndView setupTransitMasterSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		TransitMasterSearchRequest transitMasterSearchRequest = (TransitMasterSearchRequest) session
				.getAttribute(TRANSIT_MASTER_SEARCH_DATA);
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
		TransitMasterSearchResponse response = new TransitMasterSearchResponse();
		transitMasterSearchRequest.setPageIndex(pageNumber);
		try {
			response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(SETUP_TRANSIT_MASTER_LIST_DATA, response.getTransitMasterSearchList());
			model.put(TRANSIT_MASTER_SEARCH_DATA, transitMasterSearchRequest);
			session.setAttribute(TRANSIT_MASTER_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, transitMasterSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, TRANSIT_MASTER_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = TRANSIT_MASTER_VIEW_ACTION)
	public ModelAndView viewTransitMasterData(@RequestParam("transitMasterId") Long transitMasterId,
			Map<String, Object> model, final HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("transit-master-view");
		TransitMasterSearchResponse response = new TransitMasterSearchResponse();
		model.put(TRANSIT_MASTER_REQUEST, new TransitMasterSearchRequest());
		TransitMasterSearchRequest transitMasterSearchRequest = new TransitMasterSearchRequest();
		transitMasterSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			transitMasterSearchRequest.setPageIndex(Constants.ONE);
			transitMasterSearchRequest.setTransitMasterId(transitMasterId);

			if (transitMasterSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			} else if (transitMasterSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				transitMasterSearchRequest
						.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				DepotSearchRequest depotRequest = new DepotSearchRequest();
				depotRequest.setPtoName(pto.getPtoName());
				depotRequest.setPageIndex(Constants.ONE);
				depotRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			}

			TransitMasterUpdateRequest transitMasterUpdateRequest = populateviewRequest(response);
			model.put(TRANSIT_MASTER_REQUEST, transitMasterUpdateRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		return modelAndView;
	}

	private TransitMasterUpdateRequest populateviewRequest(TransitMasterSearchResponse response) {
		TransitMasterUpdateRequest request = new TransitMasterUpdateRequest();
		TransitMasterSearchRequest transitMasterSearchRequest = response.getTransitMasterSearchList().get(0);
		request.setTransitMasterId(transitMasterSearchRequest.getTransitMasterId());
		request.setApplyDate(transitMasterSearchRequest.getApplyDate());
		request.setDeliveryDate(transitMasterSearchRequest.getDeliveryDate());
		request.setDescription(transitMasterSearchRequest.getDescription());
		request.setInherit(transitMasterSearchRequest.getInherit());
		request.setPtoName(transitMasterSearchRequest.getPtoName());
		request.setStatus(transitMasterSearchRequest.getStatus());
		request.setVersionNumber(transitMasterSearchRequest.getVersionNumber());
		request.setFullVersion(transitMasterSearchRequest.getFullVersion());
		request.setTransitMasterId(transitMasterSearchRequest.getTransitMasterId());
		request.setOrganizationName(transitMasterSearchRequest.getOrganizationName());
		request.setOrganizationId(transitMasterSearchRequest.getOrganizationId());
		request.setPtoId(transitMasterSearchRequest.getPtoId());
		return request;
	}

	@PostMapping(value = TRANSIT_MASTER_EDIT_ACTION)
	public ModelAndView editTransitMasterData(final HttpSession session,
			@RequestParam("transitMasterId") Long transitMasterId, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_UPDATE);
		TransitMasterSearchResponse response = null;
		model.put(TRANSIT_MASTER_EDIT_REQ, new TransitMasterUpdateRequest());
		TransitMasterRegistrationRequest transitMasterRegistrationRequest = new TransitMasterRegistrationRequest();
		TransitMasterListDataResponse dataResponse = transitMasterService
				.getTransitMasterListDataResponse(transitMasterRegistrationRequest);
		TransitMasterUpdateRequest transitMasterData = new TransitMasterUpdateRequest();
		TransitMasterSearchRequest searchRequest = new TransitMasterSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse = new PtoListResponse();
		model.put(TRANSIT_MASTER_EDIT_REQ, transitMasterData);
		model.put(TRANSIT_MASTER_INHERIT_PTO_LIST, dataResponse.getListOfInheritAndPto());
		session.setAttribute(TRANSIT_MASTER_INHERIT_PTO_LIST, dataResponse.getListOfInheritAndPto());
		
		try {
			searchRequest.setPageIndex(Constants.ONE);
			searchRequest.setTransitMasterId(transitMasterId);
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = transitMasterService.getTransitMasterEditViewData(searchRequest);
				ptoListRequest.setOrgId(response.getTransitMasterSearchList().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				searchRequest.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = transitMasterService.getTransitMasterEditViewData(searchRequest);
				ptoListRequest.setOrgId(response.getTransitMasterSearchList().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
					pto.setPtoName(ptoListResponse.getPtoList().get(0).getPtoName());
					searchRequest.setPtoName(pto.getPtoName());
				}
				searchRequest.setPageIndex(Constants.ONE);
				searchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = transitMasterService.getTransitMasterEditViewData(searchRequest);
				if (ptoListResponse.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
				
			}
			
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR :: TransitMasterController class :: editTransitMasterData method :: exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			transitMasterData = populateviewRequest(response);
			model.put(TRANSIT_MASTER_EDIT_REQ, transitMasterData);
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(PTO_LIST_DATA, ptoListResponse.getPtoList());
		}

		return modelAndView;
	}

	@PostMapping(value = TRANSIT_MASTER_UPDATE)
	public ModelAndView updateTransitMasterData(HttpSession session, Map<String, Object> model,
			TransitMasterUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_UPDATE);
		model.put(TRANSIT_MASTER_EDIT_REQ, request);
		WebResponse response = null;
		try {
			response = transitMasterService.updateTransitMaster(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("transit.master.update.success"));
			model.put(TRANSIT_MASTER_EDIT_REQ, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(TRANSIT_MASTER_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_TRANSIT_MASTER_STATUS)
	public ModelAndView updateTransitMasterData(final HttpSession session,
			@RequestParam("transitMasterId") Long transitMasterId, @RequestParam("status") String status,
			Map<String, Object> model) {

		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		model.put(TRANSIT_MASTER_SEARCH_DATA, new UserData());
		TransitMasterRegistrationRequest request = new TransitMasterRegistrationRequest();
		WebResponse response = null;
		try {
			request.setStatus(status);
			request.setTransitMasterId(transitMasterId);
			response = transitMasterService.updateTransitMasterStatus(request);

			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("transit.master.status.suspend.changed"));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("transit.master.status.active.changed"));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("transit.master.status.terminate.changed"));
				}
				return setupTransitMasterSearchDataPagination(session,
						(Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: TransitMasterController class :: updateTransitMasterData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		return modelAndView;
	}

	@GetMapping(value = "getVersionNumber")
	public @ResponseBody String getVersionNumberByPtoName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session, @RequestParam("ptoName") String ptoName,
			TransitMasterListDataResponse response) throws JsonProcessingException, AFCSException {
		TransitMasterSearchRequest transitMasterSearchRequest = new TransitMasterSearchRequest();
		TransitMasterListDataResponse transitMaster = null;
		transitMasterSearchRequest.setPtoName(ptoName);
		transitMaster = transitMasterService.getVersionNumberByPtoName(transitMasterSearchRequest);
		if (!StringUtil.isNull(transitMaster) && transitMaster.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			transitMaster.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(transitMaster);
		}
		return null;
	}
	@PostMapping(value =  DOWNLOAD_TRANSIT_MASTER_REPORT)
	public ModelAndView downloadTransitMasterManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: TransitMasterManagementController:: downloadTransitMasterManagementReport method");
		ModelAndView modelAndView = new ModelAndView(TRANSIT_MASTER_SEARCH);
		TransitMasterSearchResponse transitMasterSearchResponse = null;
		try {
			TransitMasterSearchRequest transitMasterSearchRequest = (TransitMasterSearchRequest) session.getAttribute(SETUP_TRANSIT_MASTER_LIST_DATA);
			transitMasterSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = transitMasterSearchRequest.getPageSize();
			transitMasterSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				transitMasterSearchRequest.setPageIndex(Constants.ONE);
				transitMasterSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			transitMasterSearchResponse = transitMasterService.searchTransitMaster(transitMasterSearchRequest);
			List<TransitMasterSearchRequest> reuestResponses = transitMasterSearchResponse.getTransitMasterSearchList();
			if (!StringUtil.isNull(transitMasterSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadUserManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			transitMasterSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::TransitMasterManagementController:: TransitMasterManagementReport method", e);
		}
		logger.info("Exit:: TransitMasterManagementController:: downloadTransitMasterManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadUserManagementReport(List<TransitMasterSearchRequest> transitMasterRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("TransitMaster_");
		exportDetails.setHeaderMessageProperty("chatak.header.transitMaster.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(transitMasterRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("TransitMaster.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.inherit", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.fullVersion", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.versionNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.deliveryDate", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.applyDate", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransitMaster.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<TransitMasterSearchRequest> transitMasterSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (TransitMasterSearchRequest transitData : transitMasterSearchRequest) {

			Object[] rowData = { transitData.getOrganizationName() , transitData.getPtoName() , transitData.getInherit() , transitData.getFullVersion() ,
					            transitData.getVersionNumber() , transitData.getDeliveryDate() , transitData.getApplyDate() , transitData.getDescription() ,
					            transitData.getStatus()  
			};

			fileData.add(rowData);
		}

		return fileData;
	}
}
