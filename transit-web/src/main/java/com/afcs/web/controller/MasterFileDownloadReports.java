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
import com.afcs.web.service.MasterFileDownloadReportsService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadRequest;
import com.chatak.transit.afcs.server.pojo.web.MasterFileDownloadResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class MasterFileDownloadReports {

	@Autowired
	Environment properties;

	@Autowired
	private PtoManagementService ptoManagementService;

	@Autowired
	private OrganizationManagementService organizationManagementService;

	@Autowired
	private MasterFileDownloadReportsService downloadReportsService;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(MasterFileDownloadReports.class);

	private static final String MASTER_FILE_DOWNLOAD_SEARCH = "master-file-download-search";

	private static final String MASTER_FILE_DOWNLOAD_REQUEST = "masterFileDownloadRequest";

	private static final String MASTER_FILE_DOWNLOAD_DATA = "masterFileData";

	private static final String MASTER_FILE_DOWNLOAD_DATA_SIZE = "masterFileDataSize";

	private static final String ORGANIZATION_LIST = "organizationList";
	
	private static final String DOWNLOAD_MASTER_FILE_DOWNLOAD_REPORT = "downloadMasterFileDownloadReport";

	@GetMapping(value = MASTER_FILE_DOWNLOAD_SEARCH)
	public ModelAndView masterFileDownloadReportSearchGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(MASTER_FILE_DOWNLOAD_SEARCH);
		MasterFileDownloadRequest masterFileRequest = new MasterFileDownloadRequest();
		model.put(MASTER_FILE_DOWNLOAD_REQUEST, masterFileRequest);
		try {
			masterFileRequest.setPageSize(10);
			masterFileRequest.setPageIndex(Constants.ONE);
			session.setAttribute(MASTER_FILE_DOWNLOAD_DATA, masterFileRequest);
			OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
			organizationSearchRequest.setPageIndex(Constants.ONE);
			organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			model.put("organizationSearch", organizationSearchRequest);
			OrganizationSearchResponse response = new OrganizationSearchResponse();
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoResponse(model, session, ptoListRequest);
			getOrganizationResponse(model, session, organizationSearchRequest, response, ptoListRequest);
		} catch (Exception e) {
			logger.error(
					"ERROR :: MasterFileDownloadReports class :: masterFileDownloadReportSearchPost method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	private PtoListResponse getPtoResponse(Map<String, Object> model, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		PtoListResponse ptoListResponse;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}

		model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		return ptoListResponse;
	}

	@PostMapping(value = MASTER_FILE_DOWNLOAD_SEARCH)
	public ModelAndView masterFileDownloadReportSearchPost(Map<String, Object> model, HttpSession session,
			MasterFileDownloadRequest masterFileDownloadRequest) {
		ModelAndView modelAndView = new ModelAndView(MASTER_FILE_DOWNLOAD_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		masterFileDownloadRequest.setPageSize(Constants.ONE);
		masterFileDownloadRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		MasterFileDownloadResponse masterFileDownloadResponse = new MasterFileDownloadResponse();
		try {
			OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
			organizationSearchRequest.setPageIndex(Constants.ONE);
			organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			model.put("organizationSearch", organizationSearchRequest);
			OrganizationSearchResponse response = new OrganizationSearchResponse();
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoResponse(model, session, ptoListRequest);
			getOrganizationResponse(model, session, organizationSearchRequest, response, ptoListRequest);
			masterFileDownloadResponse = downloadReportsService.searchMasterFileReports(masterFileDownloadRequest);
			session.setAttribute(MASTER_FILE_DOWNLOAD_DATA, masterFileDownloadRequest);
			model.put(MASTER_FILE_DOWNLOAD_DATA, masterFileDownloadResponse.getListOfAfceMasterFileReport());
			model.put(MASTER_FILE_DOWNLOAD_DATA_SIZE, masterFileDownloadResponse.getTotalNoOfRecords());
			
			session.setAttribute(MASTER_FILE_DOWNLOAD_DATA_SIZE, masterFileDownloadResponse.getTotalNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, MASTER_FILE_DOWNLOAD_DATA_SIZE);
			
		} catch (Exception e) {
			logger.error(
					"ERROR :: MasterFileDownloadReports class :: masterFileDownloadReportSearchPost method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	private void getOrganizationResponse(Map<String, Object> model, HttpSession session,
			OrganizationSearchRequest organizationSearchRequest, OrganizationSearchResponse response,
			PtoListRequest ptoListRequest) throws AFCSException {
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
		if (!StringUtil.isNull(response)) {
			model.put(ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(ORGANIZATION_LIST, response.getOrganizationList());
		}
	}
	@PostMapping(value =  DOWNLOAD_MASTER_FILE_DOWNLOAD_REPORT)
	public ModelAndView downloadMasterFileReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: MasterFileController:: downloadMasterFileReport method");
		ModelAndView modelAndView = new ModelAndView(MASTER_FILE_DOWNLOAD_SEARCH);
		MasterFileDownloadResponse masterFileResponse = null;
		try {
			MasterFileDownloadRequest masterfileRequest = (MasterFileDownloadRequest) session.getAttribute(MASTER_FILE_DOWNLOAD_DATA);
			masterfileRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = masterfileRequest.getPageSize();
			masterfileRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				masterfileRequest.setPageIndex(Constants.ONE);
				masterfileRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			masterFileResponse =downloadReportsService.searchMasterFileReports(masterfileRequest);
			List<MasterFileDownloadRequest> reuestResponses = masterFileResponse. getListOfAfceMasterFileReport();
			if (!StringUtil.isNull(masterFileResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadmasterfileRequestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			masterfileRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::MasterFileController:: MasterFileReport method", e);
		}
		logger.info("Exit:: MasterFileController:: downloadMasterFileReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadmasterfileRequestReport(List<MasterFileDownloadRequest> masterfileRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Master File");
		exportDetails.setHeaderMessageProperty("chatak.header.MasterFile.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(masterfileRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("MasterFile.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("MasterFile.file.exportutil.fileName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("MasterFile.file.exportutil.fileVersion", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("MasterFile.file.exportutil.deviceSerial", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("MasterFile.file.exportutil.time", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("MasterFile.file.exportutil.location", null,
						LocaleContextHolder.getLocale()),
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<MasterFileDownloadRequest> masterfileRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (MasterFileDownloadRequest masterfileData : masterfileRequest) {

			Object[] rowData = {   masterfileData.getPtoName() , masterfileData.getFileName() , masterfileData.getFileVersion() , masterfileData.getDeviceSerial() ,     
								   masterfileData.getTime() , masterfileData.getLocation() 
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}
}
