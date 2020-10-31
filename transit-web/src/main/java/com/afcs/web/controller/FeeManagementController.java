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
import com.afcs.web.service.FeeManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.FeeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.FeeUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class FeeManagementController {

	private static Logger logger = LoggerFactory.getLogger(FeeManagementController.class);

	@Autowired
	FeeManagementService feeManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	Environment properties;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	private MessageSource messageSource;

	private static final String FEE_REGISTRATION = "fee-registration";
	private static final String FEE_DATA_SIZE = "feeDataSize";
	private static final String FEE_SEARCH = "fee-search";
	private static final String PTO_OPERATION_LIST = "ptoOperationList";
	private static final String FEE_SEARCH_PAGINATION = "fee-search-pagination";
	private static final String FEE_VIEW_ACTION = "view-fee-action";
	private static final String FEE_STATUS_UPDATE = "fee-status-action";
	private static final String FEE_EDIT = "fee-edit";
	private static final String FEE_EDIT_ACTION = "fee-edit-action";
	private static final String FEE_EDIT_REQUEST = "feeEditRequest";
	private static final String FEE_SEARCH_REQUEST = "feeSearchRequest";
	private static final String FEE_NAME = "&&feeName";
	private static final String DOWNLOAD_FEE_DOWNLOAD = "downloadFeeDownloadReport";
	private static final String FEE_LIST = "feeList";

	@GetMapping(value = FEE_REGISTRATION)
	public ModelAndView registerFee(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(FEE_REGISTRATION);
		FeeRegistrationRequest feeRegistrationRequest = new FeeRegistrationRequest();
		model.put(PTO_OPERATION_LIST, session.getAttribute(Constants.PTO_LIST));
		feeRegistrationRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("feeRegistrationRequest", feeRegistrationRequest);
		return modelandview;
	}

	@PostMapping(value = FEE_REGISTRATION)
	public ModelAndView registerFee(Map<String, Object> model, FeeRegistrationRequest feeRegistrationRequest,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FEE_SEARCH);
		FeeSearchRequest feeSearchRequest = new FeeSearchRequest();
		model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
		FeeRegistrationResponse feeRegistrationResponse = new FeeRegistrationResponse();
		try {
			feeRegistrationResponse = feeManagementService.feeRegistration(feeRegistrationRequest);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: feeRegister method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(feeRegistrationResponse)
				&& feeRegistrationResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("fee.created.successfully"));
			model.put("feeRegistrationRequest", feeRegistrationRequest);
		} else {
			model.put(Constants.ERROR, feeRegistrationResponse.getStatusMessage());
		}

		return modelAndView;
	}

	@GetMapping(value = FEE_SEARCH)
	public ModelAndView showSearchFee(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FEE_SEARCH);
		FeeSearchRequest feeSearchRequest = new FeeSearchRequest();
		feeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		ptoListRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		PtoListResponse ptoListResponse = null;
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
			model.put("ptoListData", ptoListResponse.getPtoList());
			session.setAttribute("ptoListData", ptoListResponse.getPtoList());
		}
		try {
			feeSearchRequest.setPageIndex(10);
			feeSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(FEE_LIST, feeSearchRequest);
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

		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}

		model.put(PTO_OPERATION_LIST, session.getAttribute(Constants.PTO_LIST));
		model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
		model.put("usertype", feeSearchRequest.getUserType());
		return modelAndView;
	}

	@PostMapping(value = FEE_SEARCH)
	public ModelAndView processSearchFee(Map<String, Object> model, FeeSearchRequest feeSearchRequest,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FEE_SEARCH);
		FeeSearchResponse response = null;
		model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return processFeePagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		feeSearchRequest.setPageIndex(Constants.ONE);
		try {
			feeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (feeSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = feeManagementService.searchFee(feeSearchRequest);
			} else if (feeSearchRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				feeSearchRequest
						.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = feeManagementService.searchFee(feeSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					feeSearchRequest.setPtoName(pto.getPtoName());
				}
				feeSearchRequest.setPageIndex(Constants.ONE);
				feeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = feeManagementService.searchFee(feeSearchRequest);
			}
			session.setAttribute(FEE_LIST, feeSearchRequest);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: feeSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(FEE_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(FEE_SEARCH_REQUEST, feeSearchRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(FEE_LIST, response.getFeeList());
			model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
			model.put(FEE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, feeSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, FEE_DATA_SIZE);
		}

		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, FeeSearchRequest feeSearchRequest, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(feeSearchRequest.getOrgId())) {
			ptoListRequest.setOrgId(feeSearchRequest.getOrgId());
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

	@PostMapping(value = FEE_SEARCH_PAGINATION)
	public ModelAndView processFeePagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		FeeSearchRequest feeSearchRequest = (FeeSearchRequest) session.getAttribute(FEE_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(FEE_SEARCH);
		model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
		FeeSearchResponse response = new FeeSearchResponse();
		feeSearchRequest.setPageIndex(pageNumber);
		try {
			response = feeManagementService.searchFee(feeSearchRequest);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: feeSearchDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(FEE_LIST, response.getFeeList());
			model.put(FEE_SEARCH_REQUEST, feeSearchRequest);
			session.setAttribute(FEE_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, feeSearchRequest, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, FEE_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = FEE_VIEW_ACTION)
	public ModelAndView viewFeeData(final HttpSession session, @RequestParam("feeId") Long feeId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("fee-view");
		FeeSearchResponse response = new FeeSearchResponse();
		model.put("feeUpdateRequest", new FeeUpdateRequest());
		FeeSearchRequest feeSearchRequest = new FeeSearchRequest();
		feeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			feeSearchRequest.setPageIndex(Constants.ONE);
			feeSearchRequest.setFeeId(feeId);

			if (feeSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = feeManagementService.searchFee(feeSearchRequest);
			} else if (feeSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				feeSearchRequest
						.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = feeManagementService.searchFee(feeSearchRequest);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					feeSearchRequest.setPtoName(pto.getPtoName());
				}
				feeSearchRequest.setPageIndex(Constants.ONE);
				feeSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = feeManagementService.searchFee(feeSearchRequest);
			}
			FeeUpdateRequest feeUpdateRequest = populateviewRequest(response);
			model.put("feeUpdateRequest", feeUpdateRequest);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: viewFeeData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = FEE_STATUS_UPDATE)
	public ModelAndView statusUpdateFee(HttpSession session, Map<String, Object> model,
			@RequestParam("feeId") Long feeId, @RequestParam("feeStatus") String status,
			@RequestParam("reason") String reason) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(FEE_STATUS_UPDATE);
		FeeSearchResponse response = null;
		FeeUpdateRequest request = new FeeUpdateRequest();
		try {
			request.setReason(reason);
			request.setStatus(status);
			request.setFeeId(feeId);
			response = feeManagementService.updateFeeStatus(request);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: feeStatusUpdate method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS,
						properties.getProperty("fee.status.suspend.changed").replace(FEE_NAME, response.getFeeName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS,
						properties.getProperty("fee.status.active.changed").replace(FEE_NAME, response.getFeeName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("fee.status.terminate.changed").replace(FEE_NAME,
						response.getFeeName()));
			}
			return processFeePagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}

		return modelAndView;
	}

	@PostMapping(value = FEE_EDIT_ACTION)
	public ModelAndView editFee(HttpSession session, Map<String, Object> model, @RequestParam("feeId") Long feeId) {
		ModelAndView modelAndView = new ModelAndView(FEE_EDIT);
		FeeSearchResponse response = null;
		FeeSearchRequest request = new FeeSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = new PtoListResponse();
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			request.setFeeId(feeId);
			request.setPageIndex(Constants.ONE);

			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = feeManagementService.searchFee(request);
				ptoListRequest.setOrgId(response.getFeeList().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = feeManagementService.searchFee(request);
				ptoListRequest.setOrgId(response.getFeeList().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
				model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
					pto.setPtoName(ptoListResponse.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = feeManagementService.searchFee(request);
				if (ptoListResponse.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}

		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: editFee method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			FeeUpdateRequest manufacturerResponse = populateviewRequest(response);
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			model.put(FEE_EDIT_REQUEST, manufacturerResponse);
			model.put(FEE_SEARCH_REQUEST, request);
		}
		return modelAndView;

	}

	@PostMapping(value = FEE_EDIT)
	public ModelAndView updateFee(HttpSession session, Map<String, Object> model, FeeUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(FEE_EDIT);
		WebResponse response = null;
		try {
			response = feeManagementService.updateFeeProfile(request);
		} catch (AFCSException e) {
			logger.error("FeeManagementController class :: updateFee method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("fee.updated.successfully"));
			model.put(FEE_EDIT_REQUEST, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(FEE_EDIT_REQUEST, request);
		}

		return modelAndView;
	}

	private FeeUpdateRequest populateviewRequest(FeeSearchResponse response) {
		FeeUpdateRequest feeUpdateRequest = new FeeUpdateRequest();
		FeeSearchRequest feeSearchRequest = response.getFeeList().get(0);
		feeUpdateRequest.setFeeId(feeSearchRequest.getFeeId());
		feeUpdateRequest.setOrganizationId(feeSearchRequest.getOrganizationId());
		feeUpdateRequest.setPtoId(feeSearchRequest.getPtoId());
		feeUpdateRequest.setOrganizationName(feeSearchRequest.getOrganizationName());
		feeUpdateRequest.setPtoName(feeSearchRequest.getPtoName());
		feeUpdateRequest.setFeeName(feeSearchRequest.getFeeName());
		feeUpdateRequest.setPtoShareType(feeSearchRequest.getPtoShareType());
		feeUpdateRequest.setPtoShareValue(feeSearchRequest.getPtoshareValue());
		feeUpdateRequest.setPtoFeeValue(feeSearchRequest.getPtoshareValue());
		feeUpdateRequest.setPtoFeeType(feeSearchRequest.getPtoFeeType());
		feeUpdateRequest.setPtoFeeValue(feeSearchRequest.getPtoFeeValue());
		feeUpdateRequest.setOrgShareType(feeSearchRequest.getOrgShareType());
		feeUpdateRequest.setOrgShareValue(feeSearchRequest.getOrgShareValue());
		feeUpdateRequest.setOrgFeeValue(feeSearchRequest.getOrgFeeValue());
		feeUpdateRequest.setOrgFeeType(feeSearchRequest.getOrgFeeType());
		feeUpdateRequest.setOrgFeeValue(feeSearchRequest.getOrgFeeValue());
		return feeUpdateRequest;
	}
	@PostMapping(value =  DOWNLOAD_FEE_DOWNLOAD)
	public ModelAndView downloadFeeManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: FeeManagementController:: downloadFeeManagementReport method");
		ModelAndView modelAndView = new ModelAndView(FEE_SEARCH);
		FeeSearchResponse feeSearchResponse = null;
		try {
			FeeSearchRequest feeSearchRequest = (FeeSearchRequest) session.getAttribute(FEE_LIST);
			feeSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = feeSearchRequest.getPageSize();
			if (downloadAllRecords) {
				feeSearchRequest.setPageIndex(Constants.ONE);
				feeSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			feeSearchResponse = feeManagementService.searchFee(feeSearchRequest);
			List<FeeSearchRequest> reuestResponses = feeSearchResponse. getFeeList();
			if (!StringUtil.isNull(feeSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsProductManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			feeSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::FeeManagementController:: FeeManagementReport method", e);
		}
		logger.info("Exit:: FeeManagementController:: downloadFeeManagementReport method");
		return null;
	}

	private void setExportDetailsProductManagementReport(List<FeeSearchRequest> feeSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Fee Management");
		exportDetails.setHeaderMessageProperty("chatak.header.FeeManagement.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(feeSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("FeeManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.feeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.PtoShareType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.PtoShareValue", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.PtoFeeType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.PtoFeeValue", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.OrgShareType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.OrgShareValue", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.OrgFeeType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.OrgFeeValue", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("FeeManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}
   	
	private static List<Object[]> getDetailTypeFileData(List<FeeSearchRequest> feeSearchRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (FeeSearchRequest feeSearchData : feeSearchRequest) {

	 	Object[] rowData = {  
	 			feeSearchData.getOrganizationName() , feeSearchData.getPtoName() , feeSearchData.getFeeName() , feeSearchData.getPtoShareType() , feeSearchData.getPtoshareValue() ,
	 			feeSearchData.getPtoFeeType() , feeSearchData.getPtoFeeValue() , feeSearchData.getOrgShareType() , feeSearchData.getOrgShareValue() , feeSearchData.getOrgFeeType() ,
	 			feeSearchData.getOrgFeeValue() , feeSearchData.getStatus() 
	 			
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

}
