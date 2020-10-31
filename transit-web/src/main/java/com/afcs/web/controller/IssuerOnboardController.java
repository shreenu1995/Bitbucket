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
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.IssuerOnboardService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RegionManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class IssuerOnboardController {

	@Autowired
	private PtoManagementService ptoManagementService;

	@Autowired
	private RegionManagementService regionManagementService;

	@Autowired
	private IssuerOnboardService issuerOnboardService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(IssuerOnboardController.class);

	private static final String ISSUER_SEARCH = "issuer-search";

	private static final String ISSUER_REQUEST = "issuerRequest";

	private static final String ISSUER_DATA = "issuerData";

	private static final String ISSUER_DATA_SIZE = "issuerDataSize";

	private static final String SHOW_CREATE_ISSUER = "showCreateIssuer";

	private static final String ISSUER_CREATE = "issuer-create";

	private static final String COUNTRY_DATA = "countryData";

	private static final String DELETE_ISSUER = "deleteIssuer";

	private static final String ISSUER_SEARCH_PAGINATION = "issuer-search-pagination";
	
	private static final String DOWNLOAD_ISSUER_REPORT = "downloadIssuerReport";
	
	private static final String ISSUER_NAME = "&&issuerName";

	@GetMapping(value = ISSUER_SEARCH)
	public ModelAndView issuerSearchGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		IssuerRequest issuerRequest = new IssuerRequest();
		model.put(ISSUER_REQUEST, issuerRequest);
		try {
			issuerRequest.setPageSize(10);
			issuerRequest.setPageIndex(Constants.ONE);
			session.setAttribute(ISSUER_DATA, issuerRequest);
			getPtoList(model, session);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: issuerSearchGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = ISSUER_SEARCH)
	public ModelAndView issuerSearchPost(Map<String, Object> model, HttpSession session, IssuerRequest issuerRequest) {
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		issuerRequest.setPageSize(Constants.ONE);
		issuerRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			IssuerResponse issuerResponse = issuerOnboardService.searchIssuer(issuerRequest);
			session.setAttribute(ISSUER_REQUEST, issuerRequest);
			getPtoList(model, session);
			model.put(ISSUER_DATA, issuerResponse.getListOfAfcsIssuer());
			model.put(ISSUER_DATA_SIZE, issuerResponse.getTotalNoOfRecords());
			session.setAttribute(ISSUER_REQUEST, issuerRequest);
			session.setAttribute(ISSUER_DATA_SIZE, issuerResponse.getTotalNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, ISSUER_DATA_SIZE);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: issuerSearchPost method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = ISSUER_SEARCH_PAGINATION)
	public ModelAndView issuerDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		IssuerRequest request = (IssuerRequest) session.getAttribute(ISSUER_REQUEST);
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		IssuerResponse response = new IssuerResponse();
		try {
			request.setPageSize(pageNumber);
			response = issuerOnboardService.searchIssuer(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: issuerDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(ISSUER_DATA, response.getListOfAfcsIssuer());
			model.put(ISSUER_REQUEST, request);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, ISSUER_DATA_SIZE);
		}
		return modelAndView;
	}

	@GetMapping(value = SHOW_CREATE_ISSUER)
	public ModelAndView showCreateIssuer(HttpServletRequest request, HttpServletResponse response,
			IssuerRequest issuerRequest, Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ISSUER_CREATE);
		try {
			getPtoList(model, session);
			CountryListResponse countryListResponse = new CountryListResponse();
			countryListResponse = regionManagementService.getAllCountries(countryListResponse);

			if (!StringUtil.isNull(countryListResponse)
					&& countryListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(COUNTRY_DATA, countryListResponse.getCountryList());
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: showCreateIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	private void getPtoList(Map<String, Object> model, HttpSession session) throws AFCSException {
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
	}

	@PostMapping(value = ISSUER_CREATE)
	public ModelAndView createIssuer(HttpServletRequest request, HttpServletResponse response,
			IssuerRequest issuerRequest, Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		try {
			IssuerRequest issuerReq = new IssuerRequest();
			model.put(ISSUER_REQUEST, issuerReq);
			IssuerResponse issuerResponse = issuerOnboardService.createIssuer(issuerRequest);
			if (!StringUtils.isEmpty(issuerResponse) && issuerResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(Constants.SUCCESS, properties.getProperty("issuer.created.successfully"));
				issuerSearchGet(model, session);
			} else {
				model.put(Constants.ERROR, issuerResponse.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: createIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = DELETE_ISSUER)
	public ModelAndView deleteIssuer(HttpSession session, Map<String, Object> model,
			@RequestParam("issuerId") Long issuerId) {
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		IssuerRequest issuerRequest = new IssuerRequest();
		IssuerResponse issuerResponse = null;
		try {
			issuerRequest.setIssuerId(issuerId);
			issuerResponse = issuerOnboardService.deleteIssuer(issuerRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: deleteIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(issuerResponse) && issuerResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("issuer.delete.success").replace(ISSUER_NAME, issuerResponse.getIssuerName()));
			issuerSearchGet(model, session);
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_ISSUER_REPORT)
	public ModelAndView downloadIssuerReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: IssuerController:: downloadIssuerReport method");
		ModelAndView modelAndView = new ModelAndView(ISSUER_SEARCH);
		IssuerResponse issuerResponse = null;
		try {
			IssuerRequest issuerRequest = (IssuerRequest) session.getAttribute(ISSUER_REQUEST);
			issuerRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = issuerRequest.getPageSize();
			issuerRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				issuerRequest.setPageIndex(Constants.ONE);
				issuerRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			issuerResponse =issuerOnboardService.searchIssuer(issuerRequest);
			List<IssuerRequest> reuestResponses = issuerResponse. getListOfAfcsIssuer();
			if (!StringUtil.isNull(issuerResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadIssuerReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			issuerRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::IssuerController:: IssuerReport method", e);
		}
		logger.info("Exit:: IssuerController:: downloadIssuerReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadIssuerReport(List<IssuerRequest> issuerRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Issuer");
		exportDetails.setHeaderMessageProperty("chatak.header.Issuer.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(issuerRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("Issuer.file.exportutil.issuerName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Issuer.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("Issuer.file.exportutil.serviceUrl", null,
						LocaleContextHolder.getLocale()),
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<IssuerRequest> issuerRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (IssuerRequest issuerData : issuerRequest) {

			Object[] rowData = {  issuerData.getIssuerName() , issuerData.getPtoName() ,  issuerData.getServiceUrl()       
								  
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}
}
