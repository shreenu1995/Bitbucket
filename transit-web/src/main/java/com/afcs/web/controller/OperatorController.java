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
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OperatorManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class OperatorController {

	private static Logger logger = LoggerFactory.getLogger(OperatorController.class);

	@Autowired
	OperatorManagementService operatorManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String OPERATOR_CREATE = "operator-register";
	private static final String OPERATOR_CREATE_OBJ = "operatorcreate";
	private static final String PTO_LIST = "ptoList";
	private static final String OPERATOR_SEARCH = "operator-search";
	private static final String OPERATOR_LIST_SIZE = "operatorListSize";
	private static final String OPERATOR_SEARCH_PAGINATION = "operator-search-pagination";
	private static final String OPERATOR_VIEW = "operator-view";
	private static final String OPERATOR_EDIT_ACTION = "operator-edit-action";
	private static final String OPERATOR_EDIT = "operator-edit";
	private static final String OPERATOR_SEARCH_REQUEST = "operatorsearchrequest";
	private static final String OPERATOR_UPDATE_REQUEST = "operatorUpdateRequest";
	private static final String OPERATOR_NAME = "&&operatorName";
    private static final String OPERATOR_LIST_DATA = "operatorListData";
    private static final String DOWNLOAD_OPERATOR_REPORT = "downloadOperatorReport";
	
	@GetMapping(value = OPERATOR_CREATE)
	public ModelAndView showCreateOperator(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_CREATE);
		OperatorRegistrationRequest request = new OperatorRegistrationRequest();
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		model.put(OPERATOR_CREATE_OBJ, request);
		return modelAndView;
	}

	@PostMapping(value = OPERATOR_CREATE)
	public ModelAndView processOperatorRegistration(Map<String, Object> model, HttpSession session,
			OperatorRegistrationRequest request) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		OperatorSearchRequest operatorSearchRequest = new OperatorSearchRequest();
		model.put(OPERATOR_SEARCH_REQUEST, operatorSearchRequest);
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = operatorManagementService.createOperator(request);
		} catch (AFCSException e) {
			logger.error("OperatorController class :: processOperatorRegistration method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(Constants.SUCCESS, properties.getProperty("operator.created.successfully"));
			model.put(OPERATOR_CREATE_OBJ, new OperatorRegistrationRequest());
		} else {
			model.put(OPERATOR_CREATE_OBJ, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = OPERATOR_SEARCH)
	public ModelAndView showOperatorSearch(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		OperatorSearchRequest request = new OperatorSearchRequest();
		model.put(OPERATOR_SEARCH_REQUEST, request);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		try {
			request.setPageSize(10);
			request.setPageIndex(Constants.ONE);
			session.setAttribute(OPERATOR_LIST_DATA, request);
			if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoListByOrganizationIdAndUserId(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: OperatorController class :: showOperatorSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST, ptoListResponse.getPtoList());
		}
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
			logger.error("ERROR :: OperatorController class :: showOperatorSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = OPERATOR_SEARCH)
	public ModelAndView operatorSearch(Map<String, Object> model, @RequestParam("cancelTypeId") String cancelType,
			OperatorSearchRequest request, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		OperatorResponse response = null;
		model.put(OPERATOR_SEARCH_REQUEST, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return operatorSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			request.setPageIndex(Constants.ONE);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = operatorManagementService.searchOperator(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = operatorManagementService.searchOperator(request);
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
				response = operatorManagementService.searchOperator(request);
			}
			session.setAttribute(OPERATOR_LIST_DATA, request);
		} catch (AFCSException e) {
			logger.error("OperatorController class :: showOperatorSearch method :: exception", e);
			model.put(Constants.ERROR, request);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			session.setAttribute(OPERATOR_LIST_SIZE, response.getTotalRecords());
			model.put("searchFlag", "true");
			model.put(OPERATOR_SEARCH_REQUEST, request);
			session.setAttribute(OPERATOR_SEARCH_REQUEST, request);
			model.put(OPERATOR_LIST_DATA, response.getListOfOperators());
			model.put(OPERATOR_LIST_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, OPERATOR_LIST_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, OperatorSearchRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrgId())) {
			ptoListRequest.setOrgId(request.getOrgId());
			if (!StringUtil.isNullEmpty(session.getAttribute(Constants.USER_TYPE).toString())
					&& !session.getAttribute(Constants.USER_TYPE).toString()
							.equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
				PtoListResponse ptoListResponse = ptoManagementService
						.getPtoListByOrganizationIdAndUserId(ptoListRequest);
				if (!StringUtil.isNull(ptoListResponse)) {
					model.put(Constants.PTO_LIST, ptoListResponse.getPtoList());
				}
			}
		}
	}

	@PostMapping(value = OPERATOR_SEARCH_PAGINATION)
	public ModelAndView operatorSearchPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		OperatorSearchRequest request = (OperatorSearchRequest) session.getAttribute(OPERATOR_SEARCH_REQUEST);
		model.put("operatorsearchreqeust", request);
		OperatorResponse response = new OperatorResponse();
		try {
			request.setPageIndex(pageNumber);
			response = operatorManagementService.searchOperator(request);
		} catch (AFCSException e) {
			logger.error("OperatorController class :: operatorSearchPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put("searchFlag", "true");
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(OPERATOR_LIST_DATA, response.getListOfOperators());
			model.put(OPERATOR_SEARCH_REQUEST, request);
			session.setAttribute(OPERATOR_LIST_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, OPERATOR_LIST_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = OPERATOR_VIEW)
	public ModelAndView viewOperator(Map<String, Object> model, HttpSession session,
			@RequestParam("operatorId") Long operatorId) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_VIEW);
		OperatorSearchRequest request = new OperatorSearchRequest();
		model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		OperatorResponse response = new OperatorResponse();
		model.put("operatorViewRequest", request);
		try {
			request.setPageIndex(Constants.ONE);
			request.setOperatorId(operatorId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = operatorManagementService.searchOperator(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = operatorManagementService.searchOperator(request);
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
				response = operatorManagementService.searchOperator(request);

			}
		} catch (AFCSException e) {
			logger.error("OperatorController class :: viewOperator method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			OperatorSearchRequest operatorSearchRequest = populateOperatorData(response);
			model.put("operatorViewRequest", operatorSearchRequest);
			model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		}
		return modelAndView;
	}

	protected OperatorSearchRequest populateOperatorData(OperatorResponse response) {
		return response.getListOfOperators().get(0);
	}

	@PostMapping(value = OPERATOR_EDIT_ACTION)
	public ModelAndView editOperatorAction(Map<String, Object> model, HttpSession session,
			@RequestParam("operatorId") Long operatorId) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_EDIT);
		OperatorSearchRequest request = new OperatorSearchRequest();
		OperatorResponse response = new OperatorResponse();
		model.put(OPERATOR_UPDATE_REQUEST, request);
		try {
			request.setPageIndex(Constants.ONE);
			request.setOperatorId(operatorId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = operatorManagementService.searchOperator(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = operatorManagementService.searchOperator(request);
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
				response = operatorManagementService.searchOperator(request);
			}

		} catch (AFCSException e) {
			logger.error("OperatorController class :: editOperatorAction method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			OperatorSearchRequest operatorSearchRequest = populateOperatorData(response);
			model.put(OPERATOR_UPDATE_REQUEST, operatorSearchRequest);
			model.put(PTO_LIST, session.getAttribute(Constants.PTO_LIST));
		}
		return modelAndView;
	}

	@PostMapping(value = "updateOperatorProfile")
	public ModelAndView updateOperatorProfile(HttpSession session, Map<String, Object> model,
			OperatorUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_EDIT);
		WebResponse response = null;
		model.put(OPERATOR_UPDATE_REQUEST, new OperatorUpdateRequest());
		try {
			response = operatorManagementService.updateOperatorProfile(request);
		} catch (AFCSException e) {
			logger.error("OperatorController class :: updateOperatorProfile method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("operator.update.success"));
			model.put(OPERATOR_UPDATE_REQUEST, request);

		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(OPERATOR_UPDATE_REQUEST, request);
		}
		return modelAndView;
	}

	@PostMapping(value = "update-operator-status")
	public ModelAndView updateOperatorStatus(final HttpSession session, @RequestParam("operatorId") Long operatorId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		model.put(OPERATOR_SEARCH_REQUEST, new OperatorSearchRequest());
		OperatorStatusChangeRequest request = new OperatorStatusChangeRequest();
		OperatorResponse response = null;
		try {
			request.setStatus(status);
			request.setOperatorId(operatorId);
			request.setReason(reason);
			response = operatorManagementService.updateOperatorStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("operator.status.suspend.changed")
							.replace(OPERATOR_NAME, response.getOperatorName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("operator.status.active.changed")
							.replace(OPERATOR_NAME, response.getOperatorName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("operator.status.terminate.changed")
							.replace(OPERATOR_NAME, response.getOperatorName()));
				}
				return operatorSearchPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);

			}
		} catch (AFCSException e) {
			logger.error("OperatorController class :: updateOperatorStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_OPERATOR_REPORT)
	public ModelAndView downloadOperatorManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: OperatorManagementController:: downloadOperatorManagementReport method");
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SEARCH);
		OperatorResponse operatorSearchResponse = null;
		try {
			OperatorSearchRequest operatorSearchRequest = (OperatorSearchRequest) session.getAttribute(OPERATOR_LIST_DATA);
			operatorSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = operatorSearchRequest.getPageSize();
			operatorSearchRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				operatorSearchRequest.setPageIndex(Constants.ONE);
				operatorSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			operatorSearchResponse =  operatorManagementService.searchOperator(operatorSearchRequest);
			List<OperatorSearchRequest> reuestResponses = operatorSearchResponse.getListOfOperators();
			if (!StringUtil.isNull(operatorSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadOperatorManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			operatorSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::OperatorManagementController:: OperatorManagementReport method", e);
		}
		logger.info("Exit:: OperatorManagementController:: downloadOperatorManagementReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadOperatorManagementReport(List<OperatorSearchRequest> operatorRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Operator_");
		exportDetails.setHeaderMessageProperty("chatak.header.operator.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(operatorRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				
				messageSource.getMessage("Operator.file.exportutil.operatorName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Operator.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Operator.file.exportutil.operatorContactNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Operator.file.exportutil.status", null,
						LocaleContextHolder.getLocale()), };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<OperatorSearchRequest> operatorSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (OperatorSearchRequest operatorData : operatorSearchRequest) {

			Object[] rowData = {  operatorData.getOperatorName() , operatorData.getPtoName() , operatorData.getOperatorContactNumber() ,  operatorData.getStatus() 
			};

			fileData.add(rowData);
		}

		return fileData;
	}
}
