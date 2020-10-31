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
import com.afcs.web.service.PassengerAnalysisService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisRequest;
import com.chatak.transit.afcs.server.pojo.web.PassengerAnalysisResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoSearchResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class PassengerAnalysisController {

	private static final Logger logger = LoggerFactory.getLogger(PtoSummaryController.class);

	private static final String PASSENGER_ANALYSIS_SEARCH = "passenger-analysis-search";
	private static final String PAGINATION_FOR_PASSENGER_ANALYSIS_SEARCH = "pagination-for-passenger-analysis-search";
	private static final String PASSENGER_ANALYSIS = "passengerAnalysisRequest";
	private static final String PASSENGER_ANALYSIS_DATA = "passengerAnalysisData";
	private static final String TOTAL_RECORDS = "totalRecords";
	private static final String ORGANIZATION_LIST = "organizationList";
	private static final String PASSENGER_ANALYSIS_REQUEST_DATA = "passengerAnalysisRequestData";
	private static final String PAGE_NUMBER_PASSENGER_ANALYSIS_REPORT = "pageNumberpassengerAnalysisReport";
	private static final String DOWNLOAD_PASSENGER_ANALYSIS = "downloadPassengerAnalysisDownloadReport";

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	PassengerAnalysisService passengerAnalysisService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = PASSENGER_ANALYSIS_SEARCH)
	public ModelAndView showPassengerAnalysisSearch(Map<String, Object> model, HttpSession session) {
		PtoSearchRequest ptoSearchRequest = new PtoSearchRequest();
		ModelAndView modelAndView = new ModelAndView(PASSENGER_ANALYSIS_SEARCH);
		PassengerAnalysisRequest passengerAnalysisRequest = new PassengerAnalysisRequest();
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchResponse response = null;
		PtoListResponse ptoListResponse;
		
		try {
			passengerAnalysisRequest.setPageSize(10);
			passengerAnalysisRequest.setIndexPage(Constants.ONE);
			session.setAttribute(PASSENGER_ANALYSIS_DATA, passengerAnalysisRequest);
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				ptoSearchRequest.setPtoName(pto.getPtoName());
				ptoSearchRequest.setPageIndex(Constants.ONE);
				ptoSearchRequest.setPtoMasterId(ptoList.getPtoList().get(0).getPtoMasterId());
				ptoSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				PtoSearchResponse ptoSearchResponse = ptoManagementService.searchPto(ptoSearchRequest);
				organizationSearchRequest
						.setOrgId(ptoSearchResponse.getPtosearchList().get(0).getOrgId());
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			}
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: PassengerAnalysisController class :: showPassengerAnalysisSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
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
			logger.error("ERROR :: DepotManagementController class :: depotSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if (!StringUtil.isNull(ptoListResponse)) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		
		if (response != null && response.getOrganizationList() != null) {
			model.put(ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(ORGANIZATION_LIST, response.getOrganizationList());
		}
		
		model.put(PASSENGER_ANALYSIS, passengerAnalysisRequest);
		return modelAndView;
	}

	@PostMapping(value = PASSENGER_ANALYSIS_SEARCH)
	public ModelAndView processSearch(Map<String, Object> model, PassengerAnalysisRequest passengerAnalysisRequest,
			HttpSession session) {
		session.setAttribute(PASSENGER_ANALYSIS_REQUEST_DATA, passengerAnalysisRequest);
		ModelAndView modelAndView = new ModelAndView(PASSENGER_ANALYSIS_SEARCH);
		PassengerAnalysisResponse analysisResponse = null;
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(PASSENGER_ANALYSIS, passengerAnalysisRequest);
			passengerAnalysisRequest.setIndexPage(Constants.ONE);
			passengerAnalysisRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			
			
			if (passengerAnalysisRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			} else if (passengerAnalysisRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				passengerAnalysisRequest.setOrgId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			} else {
				passengerAnalysisRequest.setPtoMasterId(Long.parseLong(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			}
			
			session.setAttribute(PASSENGER_ANALYSIS_DATA, passengerAnalysisRequest);

			if (!StringUtil.isNull(analysisResponse)
					&& analysisResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(PASSENGER_ANALYSIS_DATA, analysisResponse.getListOfPassengerAnalysisRequest());
				model.put(TOTAL_RECORDS, analysisResponse.getTotalNoOfRecords());
				session.setAttribute(TOTAL_RECORDS, analysisResponse.getTotalNoOfRecords());
				PaginationUtil.performPagination(modelAndView, session, Constants.ONE, TOTAL_RECORDS);
			} else {
				model.put(Constants.ERROR, analysisResponse.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("PassengerAnalysisController class :: processSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			showPassengerAnalysisSearch(model, session);
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = PAGINATION_FOR_PASSENGER_ANALYSIS_SEARCH)
	public ModelAndView passengerAnalysisReportPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PASSENGER_ANALYSIS_SEARCH);
		PassengerAnalysisRequest passengerAnalysisRequest = (PassengerAnalysisRequest) session
				.getAttribute(PASSENGER_ANALYSIS_REQUEST_DATA);
		session.setAttribute(PAGE_NUMBER_PASSENGER_ANALYSIS_REPORT, pageNumber);
		passengerAnalysisRequest.setIndexPage(pageNumber);
		passengerAnalysisRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		session.setAttribute(PASSENGER_ANALYSIS_REQUEST_DATA, passengerAnalysisRequest);
		PassengerAnalysisResponse analysisResponse = null;
		try {
			passengerAnalysisRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (passengerAnalysisRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			} else if (passengerAnalysisRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				passengerAnalysisRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			} else {
				passengerAnalysisRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_ID).toString()));
				analysisResponse = passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			}
			if (!StringUtil.isNull(analysisResponse)
					&& analysisResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(PASSENGER_ANALYSIS_DATA, analysisResponse.getListOfPassengerAnalysisRequest());
				model.put(PASSENGER_ANALYSIS, passengerAnalysisRequest);
				model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
				PaginationUtil.performPagination(modelAndView, session, pageNumber, TOTAL_RECORDS);
			} else {
				model.put(Constants.ERROR, analysisResponse.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("PassengerAnalysisController class :: passengerAnalysisReportPagination method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			showPassengerAnalysisSearch(model, session);
			return modelAndView;
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_PASSENGER_ANALYSIS)
	public ModelAndView downloadPassengerAnalysisReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: PassengerAnalysisController:: downloadPassengerAnalysisReport method");
		ModelAndView modelAndView = new ModelAndView(PASSENGER_ANALYSIS_SEARCH);
		PassengerAnalysisResponse passengerAnalysis = null;
		try {
			PassengerAnalysisRequest passengerAnalysisRequest = (PassengerAnalysisRequest) session.getAttribute(PASSENGER_ANALYSIS_DATA);
			passengerAnalysisRequest.setIndexPage(downLoadPageNumber);
			Integer pageSize = passengerAnalysisRequest.getPageSize();
			passengerAnalysisRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				passengerAnalysisRequest.setIndexPage(Constants.ONE);
				passengerAnalysisRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			passengerAnalysis =passengerAnalysisService.searchPassengerAnalysisReport(passengerAnalysisRequest);
			List<PassengerAnalysisRequest> reuestResponses = passengerAnalysis. getListOfPassengerAnalysisRequest();
			if (!StringUtil.isNull(passengerAnalysis)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadPassengerAnalysisRequestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			passengerAnalysisRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::PassengerAnalysisController:: PassengerAnalysisReport method", e);
		}
		logger.info("Exit:: PassengerAnalysisController:: downloadPassengerAnalysisReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadPassengerAnalysisRequestReport(List<PassengerAnalysisRequest> passengerAnalysisRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Passenger Analysis");
		exportDetails.setHeaderMessageProperty("chatak.header.PassengerAnalysis.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(passengerAnalysisRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("PassengerAnalysis.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.generalCount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.childrenCount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.seniorCitizenCount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.studentCount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PassengerAnalysis.file.exportutil.baggageCount", null,
						LocaleContextHolder.getLocale()),
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<PassengerAnalysisRequest> passengerAnalysisRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (PassengerAnalysisRequest passengerAnalysisData : passengerAnalysisRequest) {

			Object[] rowData = { passengerAnalysisData.getOrganizationName() , passengerAnalysisData.getPtoName() , passengerAnalysisData.getGeneralCount() ,    
								 passengerAnalysisData.getChildrenCount() , passengerAnalysisData.getSeniorCitizenCount() , passengerAnalysisData.getStudentCount() , 
			                     passengerAnalysisData.getBaggageCount()
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

}
