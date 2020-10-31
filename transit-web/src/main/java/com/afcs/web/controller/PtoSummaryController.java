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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.PtoSummaryService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSummaryResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class PtoSummaryController {

	private static final Logger logger = LoggerFactory.getLogger(PtoSummaryController.class);

	private static final String PTO_SUMMARY_SEARCH = "pto-summary-search";

	private static final String PTO_SUMMARY = "ptoSummaryRequest";

	private static final String ORGANIZATION_LIST = "organizationList";

	private static final String TOTAL_RECORDS = "totalRecords";

	private static final String PTO_SUMMARY_DATA = "ptoSummaryData";

	private static final String PTO_SUMMARY_REQUEST_DATA = "ptoSummaryRequestData";

	private static final String PAGINATION_FOR_PTO_SUMMARY_SEARCH = "pagination-for-pto-summary-search";

	private static final String PAGE_NUMBER_PTO_SUMMARY_REPORT = "pageNumberPtoSummaryReport";
	
	private static final String DOWNLOAD_PTOSUMMARY_DOWNLOAD_REPORT = "downloadPtoSummaryDownloadReport";
	
	private static final String PTO_LIST_DATA = "ptoListData";

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	PtoSummaryService ptoSummaryService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = PTO_SUMMARY_SEARCH)
	public ModelAndView showPtoSummarySearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PTO_SUMMARY_SEARCH);
		PtoSummaryRequest passengerAnalysisRequest = new PtoSummaryRequest();
		session.setAttribute(PTO_SUMMARY_DATA, passengerAnalysisRequest);
		model.put(PTO_SUMMARY, passengerAnalysisRequest);
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = null;
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		organizationSearchRequest.setPageIndex(Constants.ONE);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
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
			passengerAnalysisRequest.setPageSize(10);
			passengerAnalysisRequest.setIndexPage(Constants.ONE);
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
			model.put(ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(ORGANIZATION_LIST, response.getOrganizationList());
		}
		
		model.put(PTO_LIST_DATA, session.getAttribute(Constants.PTO_LIST_DATA));
		return modelAndView;
	}

	@PostMapping(value = PTO_SUMMARY_SEARCH)
	public ModelAndView processSearch(Map<String, Object> model, PtoSummaryRequest ptoSummaryRequest,
			HttpSession session) throws AFCSException {
		session.setAttribute(PTO_SUMMARY_REQUEST_DATA, ptoSummaryRequest);
		ModelAndView modelAndView = new ModelAndView(PTO_SUMMARY_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		ptoSummaryRequest.setIndexPage(Constants.ONE);
		model.put(PTO_SUMMARY, ptoSummaryRequest);
		session.setAttribute(PTO_SUMMARY, ptoSummaryRequest);
		ptoSummaryRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoSummaryResponse ptoSummaryResponse = null;
		try {
			ptoSummaryRequest.setIndexPage(Constants.ONE);
			ptoSummaryRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
			ptoSummaryRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (ptoSummaryRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			} else if (ptoSummaryRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoSummaryRequest.setOrgId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			} else {
				ptoSummaryRequest.setPtoMasterId(Long.parseLong(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			}
			
			session.setAttribute(PTO_SUMMARY_DATA, ptoSummaryRequest);
			if (!StringUtil.isNull(ptoSummaryResponse)
					&& ptoSummaryResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				session.setAttribute(TOTAL_RECORDS, ptoSummaryResponse.getTotalNoOfRecords());
				model.put(TOTAL_RECORDS, ptoSummaryResponse.getTotalNoOfRecords());
				model.put(PTO_SUMMARY_DATA, ptoSummaryResponse.getListOfPtoSummaryRequest());
				PaginationUtil.performPagination(modelAndView, session, Constants.ONE, TOTAL_RECORDS);
			} else {
				model.put(Constants.ERROR, ptoSummaryResponse.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("PtoSummaryController class :: processSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			showPtoSummarySearch(model, session);
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = PAGINATION_FOR_PTO_SUMMARY_SEARCH)
	public ModelAndView ptoSummaryReportPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PTO_SUMMARY_SEARCH);
		PtoSummaryRequest ptoSummaryRequest = (PtoSummaryRequest) session.getAttribute(PTO_SUMMARY_REQUEST_DATA);
		session.setAttribute(PAGE_NUMBER_PTO_SUMMARY_REPORT, pageNumber);
		ptoSummaryRequest.setIndexPage(pageNumber);
		ptoSummaryRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		session.setAttribute(PTO_SUMMARY_REQUEST_DATA, ptoSummaryRequest);
		PtoSummaryResponse ptoSummaryResponse = null;
		ptoSummaryRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			if (ptoSummaryRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			} else if (ptoSummaryRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				ptoSummaryRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			} else {
				ptoSummaryRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_ID).toString()));
				ptoSummaryResponse = ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: PtoSummaryController class :: ptoSummaryReportPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(ptoSummaryResponse)
				&& ptoSummaryResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(PTO_SUMMARY_DATA, ptoSummaryResponse.getListOfPtoSummaryRequest());
			model.put(PTO_SUMMARY, ptoSummaryRequest);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, TOTAL_RECORDS);
		} else {
			model.put(Constants.ERROR, ptoSummaryResponse.getStatusMessage());
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_PTOSUMMARY_DOWNLOAD_REPORT)
	public ModelAndView downloadPtoSummaryReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: PtoSummaryController:: downloadPtoSummaryReport method");
		ModelAndView modelAndView = new ModelAndView(PTO_SUMMARY_SEARCH);
		PtoSummaryResponse ptoSummaryResponse = null;
		try {
			PtoSummaryRequest ptoSummaryRequest = (PtoSummaryRequest) session.getAttribute(PTO_SUMMARY_DATA);
			ptoSummaryRequest.setIndexPage(downLoadPageNumber);
			Integer pageSize = ptoSummaryRequest.getPageSize();
			ptoSummaryRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				ptoSummaryRequest.setIndexPage(Constants.ONE);
				ptoSummaryRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			ptoSummaryResponse =ptoSummaryService.searchPTOSummaryReport(ptoSummaryRequest);
			List<PtoSummaryRequest> reuestResponses = ptoSummaryResponse. getListOfPtoSummaryRequest();
			if (!StringUtil.isNull(ptoSummaryResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadPtoSummaryRequestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			ptoSummaryRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::PtoSummaryController:: PtoSummaryReport method", e);
		}
		logger.info("Exit:: PtoSummaryController:: downloadPtoSummaryReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadPtoSummaryRequestReport(List<PtoSummaryRequest> ptoSummaryRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Pto Summary");
		exportDetails.setHeaderMessageProperty("chatak.header.PtoSummary.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(ptoSummaryRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("PtoSummary.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoSummary.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoSummary.file.exportutil.numberOfRoutes", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoSummary.file.exportutil.tickedIssued", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PtoSummary.file.exportutil.totalRevenue", null,
						LocaleContextHolder.getLocale()),
				};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<PtoSummaryRequest> ptoSummaryRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (PtoSummaryRequest ptoSummaryData : ptoSummaryRequest) {

			Object[] rowData = { ptoSummaryData.getOrganizationName() , ptoSummaryData.getPtoName() , ptoSummaryData.getNoOfRoutes() ,      
								 ptoSummaryData.getTicketsIssued() , ptoSummaryData.getTotalRevenue()  
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

}
