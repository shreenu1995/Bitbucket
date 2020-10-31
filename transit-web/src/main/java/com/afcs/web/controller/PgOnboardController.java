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
import com.afcs.web.service.PgOnboardService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.RegionManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.CountryListResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PgRequest;
import com.chatak.transit.afcs.server.pojo.web.PgResponse;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingRequest;
import com.chatak.transit.afcs.server.pojo.web.PmOnboardingResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class PgOnboardController {

	@Autowired
	Environment properties;

	@Autowired
	private RegionManagementService regionManagementService;

	@Autowired
	private PtoManagementService ptoManagementService;

	@Autowired
	private PgOnboardService pgOnboardService;
	
	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(PgOnboardController.class);

	private static final String PG_ONBOARDING_SEARCH = "pg-onboarding-search";

	private static final String PG_REQUEST = "pgRequest";

	private static final String PG_DATA = "pgData";

	private static final String PG_DATA_SIZE = "pgDataSize";

	private static final String SHOW_CREATE_PG = "showCreatePg";

	private static final String PG_ONBOARDING_CREATE = "pg-onboarding-create";

	private static final String COUNTRY_DATA = "countryData";

	private static final String DELETE_PG = "deletePg";

	private static final String PG_SEARCH_PAGINATION = "pg-search-pagination";

	private static final String GET_PM_BY_CURRENCY = "getPmBycurrency";
	
	private static final String DOWNLOAD_PAYMENT_GATEWAY_REPORT = "downloadPaymentGatewayReport" ;
	
	private static final String PG_NAME = "&&pgName" ;
	
	@GetMapping(value = PG_ONBOARDING_SEARCH)
	public ModelAndView pgSearchGet(Map<String, Object> model, HttpSession session) {

		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		PgRequest pgRequest = new PgRequest();
		model.put(PG_REQUEST, pgRequest);
		try {
			pgRequest.setPageSize(10);
			pgRequest.setPageIndex(Constants.ONE);
			session.setAttribute(PG_DATA, pgRequest);
			getPtoList(model, session);
		} catch (Exception e) {
			logger.error("ERROR :: PgOnboardController class :: pgSearchGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = PG_ONBOARDING_SEARCH)
	public ModelAndView pgSearchPost(Map<String, Object> model, HttpSession session, PgRequest pgRequest) {
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		pgRequest.setPageSize(Constants.ONE);
		pgRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			getPtoList(model, session);
			PgResponse pgResponse = pgOnboardService.searchPaygate(pgRequest);
			session.setAttribute(PG_DATA, pgRequest);
			model.put(PG_DATA, pgResponse.getListOfAfcsPg());
			model.put(PG_DATA_SIZE, pgResponse.getTotalNoOfRecords());
			session.setAttribute(PG_REQUEST, pgRequest);
			session.setAttribute(PG_DATA_SIZE, pgResponse.getTotalNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, PG_DATA_SIZE);
		} catch (AFCSException e) {
			logger.error("ERROR :: PgOnboardController class :: pgSearchPost method :: exception", e);
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

	@PostMapping(value = PG_SEARCH_PAGINATION)
	public ModelAndView pgDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		PgRequest request = (PgRequest) session.getAttribute(PG_REQUEST);
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		PgResponse response = new PgResponse();
		try {
			request.setPageSize(pageNumber);
			response = pgOnboardService.searchPaygate(request);
		} catch (AFCSException e) {
			logger.error("ERROR :: PgOnboardController class :: pgDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(PG_DATA, response.getListOfAfcsPg());
			model.put(PG_REQUEST, request);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, PG_DATA_SIZE);
		}
		return modelAndView;
	}

	@GetMapping(value = SHOW_CREATE_PG)
	public ModelAndView showCreateIssuer(HttpServletRequest request, HttpServletResponse response, PgRequest pgRequest,
			Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_CREATE);
		try {
			getPtoList(model, session);
			CountryListResponse countryListResponse = new CountryListResponse();
			countryListResponse = regionManagementService.getAllCountries(countryListResponse);
			if (!StringUtil.isNull(countryListResponse)
					&& countryListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(COUNTRY_DATA, countryListResponse.getCountryList());
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: PgOnboardController class :: showCreateIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = PG_ONBOARDING_CREATE)
	public ModelAndView createIssuer(HttpServletRequest request, HttpServletResponse response, PgRequest pgRequest,
			Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		try {
			PgRequest pgReq = new PgRequest();
			model.put(PG_REQUEST, pgReq);
			PgResponse pgResponse = pgOnboardService.createPaygate(pgRequest);
			if (!StringUtils.isEmpty(pgResponse) && pgResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(Constants.SUCCESS, properties.getProperty("pg.created.successfully"));
				pgSearchGet(model, session);
			} else {
				model.put(Constants.ERROR, pgResponse.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: createIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = DELETE_PG)
	public ModelAndView deleteIssuer(HttpSession session, Map<String, Object> model, @RequestParam("pgId") Long pgId) {
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		PgRequest pgRequest = new PgRequest();
		PgResponse pgResponse = null;
		try {
			pgRequest.setPgId(pgId);
			pgResponse = pgOnboardService.deletePaygate(pgRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: deleteIssuer method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(pgResponse) && pgResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("pg.delete.success").replace(PG_NAME,pgResponse.getPgName()));
			pgSearchGet(model, session);
		}
		return modelAndView;
	}
	
	@GetMapping(value = GET_PM_BY_CURRENCY)
	public @ResponseBody String getPmBycurrency(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session, @RequestParam("currency") String currency,
			PmOnboardingResponse response) {
		PmOnboardingRequest onboardingRequest = new PmOnboardingRequest();
		try {
			onboardingRequest.setCurrency(currency);
			pgOnboardService.getPmByCurrency(onboardingRequest);
		} catch (AFCSException e) {
			logger.error("ERROR :: IssuerOnboardController class :: getPmBycurrency method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
		}
		return null;
	}
	@PostMapping(value =  DOWNLOAD_PAYMENT_GATEWAY_REPORT)
	public ModelAndView downloadPaymentGatewayReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: PaymentGatewayController:: downloadPaymentGatewayReport method");
		ModelAndView modelAndView = new ModelAndView(PG_ONBOARDING_SEARCH);
		PgResponse pgResponse = null;
		try {
			PgRequest pgRequest = (PgRequest) session.getAttribute(PG_DATA);
			pgRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = pgRequest.getPageSize();
			pgRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());

			if (downloadAllRecords) {
				pgRequest.setPageIndex(Constants.ONE);
				pgRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			pgResponse =pgOnboardService.searchPaygate(pgRequest);
			List<PgRequest> reuestResponses = pgResponse. getListOfAfcsPg();
			if (!StringUtil.isNull(pgResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadPaymentGatewayReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			pgRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::PaymentGatewayController:: PaymentGatewayReport method", e);
		}
		logger.info("Exit:: PaymentGatewayController:: downloadPaymentGatewayReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadPaymentGatewayReport(List<PgRequest> pgRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("PaymentGateway");
		exportDetails.setHeaderMessageProperty("chatak.header.PaymentGateway.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(pgRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("PaymentGateway.file.exportutil.paymentGatewayName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PaymentGateway.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("PaymentGateway.file.exportutil.serviceUrl", null,
						LocaleContextHolder.getLocale()),
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<PgRequest> pgRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (PgRequest pgData : pgRequest) {

			Object[] rowData = { pgData.getPgName() , pgData.getPtoName() ,  pgData.getServiceUrl()       
								  
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}
}
