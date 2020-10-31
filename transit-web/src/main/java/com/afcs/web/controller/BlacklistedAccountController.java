package com.afcs.web.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.BlacklistedAccountService;
import com.afcs.web.service.IssuerOnboardService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountRequest;
import com.chatak.transit.afcs.server.pojo.web.BlacklistedAccountResponse;
import com.chatak.transit.afcs.server.pojo.web.IssuerRequest;
import com.chatak.transit.afcs.server.pojo.web.IssuerResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class BlacklistedAccountController {

	@Autowired
	Environment properties;

	@Autowired
	BlacklistedAccountService blacklistedAccountService;
	
	@Autowired
	IssuerOnboardService issuerOnboardService;
	
	@Autowired
	private MessageSource messageSource;


	private static final Logger logger = LoggerFactory.getLogger(BlacklistedAccountController.class);

	public static final String BLACKLISTED_ACCOUNT_CREATE = "blacklisted-account-create";
	public static final String BLACKLISTED_ACCOUNT_SEARCH = "blacklisted-account-search";
	public static final String BLACKLISTED_ACCOUNT_REQUEST = "blacklistedAccountRequest";
	public static final String BLACKLISTED_ACCOUNT_REQUESTS = "blacklistedAccountRequests";
	public static final String BLACKLISTED_ACCOUNT_LIST = "blacklistedAccountList";
	public static final String BLACKLISTED_ACCOUNT_SEARCH_PAGINATION = "blacklisted-account-search-pagination";
	public static final String BLACKLISTED_ACCOUNT_PAGINATION_REQUSET = "blacklisted-account-pagination-request";
	public static final String BLACKLISTED_ACCOUNT_DATA_SIZE = "blacklistedAccountDataSize";
	public static final String BLACKLISTED_ACCOUNT_ISSUER_NAME = "blacklistedAccountIssuerName";
	public static final String DOWNLOAD_BLACKLISTED_ACCOUNT_TEMPLATE = "downloadBlacklistedAccountTemplate";
	public static final String DOWNLOAD_BLACKLISTED_REPORT = "downloadBlackListedReport";

	@GetMapping(value = BLACKLISTED_ACCOUNT_SEARCH)
	public ModelAndView blacklistedAccountSearch(Map<String, Object> model, HttpSession session) {
		BlacklistedAccountRequest blacklistedAccountRequest = new BlacklistedAccountRequest();
		ModelAndView modelAndView = new ModelAndView(BLACKLISTED_ACCOUNT_SEARCH);
		BlacklistedAccountResponse response = new BlacklistedAccountResponse();
		try {
			blacklistedAccountRequest.setPageSize(10);
			blacklistedAccountRequest.setPageIndex(Constants.ONE);
			session.setAttribute(BLACKLISTED_ACCOUNT_LIST, blacklistedAccountRequest);
			blacklistedAccountRequest.setPageIndex(Constants.ONE);
			response = blacklistedAccountService.searchBlacklistedAccount(blacklistedAccountRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(BLACKLISTED_ACCOUNT_ISSUER_NAME, response.getBlacklistedAccountList());
		session.setAttribute(BLACKLISTED_ACCOUNT_ISSUER_NAME, response.getBlacklistedAccountList());
		model.put(BLACKLISTED_ACCOUNT_REQUEST, blacklistedAccountRequest);
		return modelAndView;

	}

	@GetMapping(value = BLACKLISTED_ACCOUNT_CREATE)
	public ModelAndView blacklistedAccountCreate(Map<String, Object> model,
			BlacklistedAccountRequest blacklistedAccountRequest, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(BLACKLISTED_ACCOUNT_CREATE);
		BlacklistedAccountRequest blacklistedAccountRequests = new BlacklistedAccountRequest();
		model.put(BLACKLISTED_ACCOUNT_REQUESTS, blacklistedAccountRequests);
		
		IssuerResponse issuerResponse = issuerOnboardService.getAllIssuers();
		model.put("issuerList", issuerResponse.getListOfAfcsIssuer());
		return modelAndView;
	}

    @PostMapping(value = BLACKLISTED_ACCOUNT_SEARCH)
	public ModelAndView blacklistedAccount(Map<String, Object> model,
			BlacklistedAccountRequest blacklistedAccountRequest, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(BLACKLISTED_ACCOUNT_SEARCH);
		BlacklistedAccountResponse response = new BlacklistedAccountResponse();
		try {
			blacklistedAccountRequest.setPageIndex(Constants.ONE);
			response = blacklistedAccountService.searchBlacklistedAccount(blacklistedAccountRequest);
			session.setAttribute(BLACKLISTED_ACCOUNT_LIST, blacklistedAccountRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(BLACKLISTED_ACCOUNT_REQUEST, blacklistedAccountRequest);
		session.setAttribute("issuerList", response.getBlacklistedAccountList());
		model.put("searchFlag", "true");
		model.put(BLACKLISTED_ACCOUNT_LIST, response.getBlacklistedAccountList());
		model.put(BLACKLISTED_ACCOUNT_DATA_SIZE, response.getNoOfrecords());
		session.setAttribute(BLACKLISTED_ACCOUNT_DATA_SIZE, response.getNoOfrecords());
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, BLACKLISTED_ACCOUNT_DATA_SIZE);
		return modelAndView;

	}

	@PostMapping(value = BLACKLISTED_ACCOUNT_CREATE)
	public ModelAndView blacklistedAccountCreate(Map<String, Object> model,
			@RequestParam("browse") MultipartFile file, BlacklistedAccountRequest blacklistedAccountRequest,
			HttpSession session) throws IOException, AFCSException {
		IssuerRequest issuerRequest = new IssuerRequest();
		issuerRequest.setIssuerName(blacklistedAccountRequest.getIssuerName());
		IssuerResponse issuerResponse = issuerOnboardService.getIssuerById(issuerRequest);
		blacklistedAccountRequest.setIssuerId(issuerResponse.getListOfAfcsIssuer().get(0).getIssuerId());
		getblacklistedAccountDetails(blacklistedAccountRequest, file);
		List<BlacklistedAccountRequest> blacklistedAccountList = getBlacklistedAccountList(file.getBytes(),
				blacklistedAccountRequest);
		WebResponse response = null;
		try{
			response = blacklistedAccountService.blacklistedAccountCreate(blacklistedAccountList);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return blacklistedAccountSearch(model, session);
		}
		for (int i = 0, size = blacklistedAccountList.size(); i < size; i++) {
			if (!StringUtil.isNull(blacklistedAccountList.get(i).getBlacklistedErrorCause())) {
				model.put(Constants.ERROR, properties.getProperty("fare.bulk.upload.failure.alphanumerics"));
				return blacklistedAccountSearch(model, session);
			}
		}
		if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("blacklisted.created.successfully"));
		}else {	
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		model.put(BLACKLISTED_ACCOUNT_REQUEST, new BlacklistedAccountRequest());
		return blacklistedAccountSearch(model, session);

	}

	private void getblacklistedAccountDetails(BlacklistedAccountRequest blacklistedAccountRequest, MultipartFile file) {
		byte[] bytes = null;
		if (!file.isEmpty()) {
			try {
				bytes = file.getBytes();
			} catch (Exception e) {
				logger.error("ERROR:: BlacklistedAccountController:: getblacklistedAccountDetails method", e);
			}
		}
		blacklistedAccountRequest.setBrowseBlacklistedAccount(bytes);
		logger.info("Exiting:: BlacklistedAccountController:: getblacklistedAccountDetails method");
	}

	public static List<BlacklistedAccountRequest> getBlacklistedAccountList(byte[] blacklistedAccountFile,
			BlacklistedAccountRequest blacklistedAccountReq) {
		List<BlacklistedAccountRequest> blacklistedAccountList = new ArrayList<>();
		try {
			String fileData = new String(blacklistedAccountFile, StandardCharsets.UTF_8);
			String[] lines = fileData.split("\n");
			int length = lines.length;
			String[] columns = null;
			for (int i = 0; i < length; i++) {
				columns = lines[i].split(",");

				// empty line
				if (columns.length == 0) {
					continue;
				}

				// Remove carriage return("\r") if present at the end of the
				// each row
				if (columns[columns.length - 1].contains("\r")) {
					columns[columns.length - 1] = columns[columns.length - 1].replaceAll("\r", "");
				}

				// Validate the file header
				if ((i == 0) && !(columns[Constants.ZERO].equalsIgnoreCase("Issuer")
						&& columns[Constants.ONE].equalsIgnoreCase("Pto")
						&& columns[Constants.TWO].equalsIgnoreCase("Account")
						&& columns[Constants.THREE].equalsIgnoreCase("Reason"))) {
					logger.info("Validate the file header");
				} else if (i == 0) {
					continue;
				}

				// Getting the Columns Values from Row
				BlacklistedAccountRequest blacklistedAccountRequest = new BlacklistedAccountRequest();
				String issuer = StringUtil.isNullEmpty(columns[Constants.ZERO]) ? "" : columns[Constants.ZERO];
				if (blacklistedAccountReq.getIssuerName().equalsIgnoreCase(issuer)) {
					getBlacklistedAccountList(blacklistedAccountReq, blacklistedAccountList, columns,
							blacklistedAccountRequest, issuer);
				}
				
			}

		} catch (Exception e) {
			logger.error("ERROR:: BlacklistedAccountController :: getBlacklistedAccountList method: Exception ", e);
		}

		return blacklistedAccountList;
	}

	private static void getBlacklistedAccountList(BlacklistedAccountRequest blacklistedAccountReq,
			List<BlacklistedAccountRequest> blacklistedAccountList, String[] columns,
			BlacklistedAccountRequest blacklistedAccountRequest, String issuer) {
		StringBuilder errorMessage = new StringBuilder();
		checkIssuer(blacklistedAccountRequest,errorMessage, issuer);
		
		String pto = StringUtil.isNullEmpty(columns[Constants.ONE]) ? "" : columns[Constants.ONE];
		checkPto(blacklistedAccountRequest,errorMessage, pto);

		String account = StringUtil.isNullEmpty(columns[Constants.TWO]) ? "" : columns[Constants.TWO];
		checkAccount(blacklistedAccountRequest,errorMessage, account);

		String reason = StringUtil.isNullEmpty(columns[Constants.THREE]) ? "" : columns[Constants.THREE];
		checkReason(blacklistedAccountRequest,errorMessage, reason);

		blacklistedAccountRequest.setIssuerId(blacklistedAccountReq.getIssuerId());
		blacklistedAccountList.add(blacklistedAccountRequest);
	}

	private static void checkAccount(BlacklistedAccountRequest blacklistedAccountRequest,StringBuilder errorMessage, String account) {
		if (StringUtil.isNullEmpty(account) || !isValidAlfaNumeric(account) || !isValidNumeric(account)) {
			errorMessage.append("Please check account number Field");
			blacklistedAccountRequest.setBlacklistedErrorCause(errorMessage.toString());
		} else {
			blacklistedAccountRequest.setAccount(account);
		}
	}

	private static void checkReason(BlacklistedAccountRequest blacklistedAccountRequest,StringBuilder errorMessage, String reason) {
		if (StringUtil.isNullEmpty(reason) || !isValidAlfaNumeric(reason)) {
			errorMessage.append("Please check reason Field");
			blacklistedAccountRequest.setBlacklistedErrorCause(errorMessage.toString());
		}else {
			blacklistedAccountRequest.setReason(reason);
		}
	}
	
	private static void checkIssuer(BlacklistedAccountRequest blacklistedAccountRequest,StringBuilder errorMessage, String issuer) {
		if (StringUtil.isNullEmpty(issuer) || !isValidAlfaNumeric(issuer)) {
			errorMessage.append("please check Issuer Field");
			blacklistedAccountRequest.setBlacklistedErrorCause(errorMessage.toString());
		}else {
			blacklistedAccountRequest.setIssuerName(issuer);
		}
	}

	private static void checkPto(BlacklistedAccountRequest blacklistedAccountRequest,StringBuilder errorMessage, String pto) {
		if (StringUtil.isNullEmpty(pto)) {
			errorMessage.append("please check pto Field");
			blacklistedAccountRequest.setBlacklistedErrorCause(errorMessage.toString());
		}else {
			blacklistedAccountRequest.setPtoName(pto);
		}
	}
	
	@PostMapping(value = BLACKLISTED_ACCOUNT_SEARCH_PAGINATION)
	public ModelAndView blacklistedAccountSearchPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		BlacklistedAccountRequest blacklistedAccountRequest = new BlacklistedAccountRequest();
		ModelAndView modelAndView = new ModelAndView(BLACKLISTED_ACCOUNT_SEARCH);
		model.put(BLACKLISTED_ACCOUNT_PAGINATION_REQUSET, blacklistedAccountRequest);
		BlacklistedAccountResponse response = new BlacklistedAccountResponse();
		blacklistedAccountRequest.setPageIndex(pageNumber);
		try {
			response = blacklistedAccountService.searchBlacklistedAccount(blacklistedAccountRequest);
		} catch (AFCSException e) {
			logger.error(
					"ERROR:: BlacklistedAccountController :: blacklistedAccountSearchPagination method: Exception ", e);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		model.put(BLACKLISTED_ACCOUNT_LIST, response.getBlacklistedAccountList());
		model.put(BLACKLISTED_ACCOUNT_REQUEST, blacklistedAccountRequest);
		model.put(BLACKLISTED_ACCOUNT_DATA_SIZE, response.getNoOfrecords());
		session.setAttribute(BLACKLISTED_ACCOUNT_DATA_SIZE, response.getNoOfrecords());
		PaginationUtil.performPagination(modelAndView, session, pageNumber, BLACKLISTED_ACCOUNT_DATA_SIZE);
		return modelAndView;
	}
	
	@PostMapping(value = DOWNLOAD_BLACKLISTED_ACCOUNT_TEMPLATE)
	public void downloadBlacklistedAccountTemplate(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fileName = "BLACKLISTED_ACCOUNT_DETAILS.csv";
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		StringBuilder builder = new StringBuilder();
		builder.append("Issuer");
		builder.append(',');
		builder.append("Pto");
		builder.append(',');
		builder.append("Account");
		builder.append(',');
		builder.append("Reason");
		builder.append('\n');
		response.getWriter().print(builder);
		response.flushBuffer();
	}
	@PostMapping(value = DOWNLOAD_BLACKLISTED_REPORT)
	public ModelAndView downloadBlacklistedAccountReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: BlacklistedAccountController:: downloadBlacklistedAccountReport method");
		ModelAndView modelAndView = new ModelAndView(BLACKLISTED_ACCOUNT_SEARCH);
		BlacklistedAccountResponse blackListAccountResponse = null;
		try {
			BlacklistedAccountRequest blackListedAccountRequest = (BlacklistedAccountRequest) session.getAttribute(BLACKLISTED_ACCOUNT_LIST);
			blackListedAccountRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = blackListedAccountRequest.getPageSize();
			if (downloadAllRecords) {
				blackListedAccountRequest.setPageIndex(Constants.ONE);
				blackListedAccountRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			blackListAccountResponse =  blacklistedAccountService.searchBlacklistedAccount(blackListedAccountRequest);
			List<BlacklistedAccountRequest> reuestResponses = blackListAccountResponse.getBlacklistedAccountList();
			if (!StringUtil.isNull(blackListAccountResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleOnboardReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			blackListedAccountRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: BlacklistedAccountController:: downloadBlacklistedAccountReport method", e);
		}
		logger.info("Exit:: BlacklistedAccountController:: downloadBlacklistedAccountReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleOnboardReport(List<BlacklistedAccountRequest> blackListAccountRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("BlackListedAccount_");
		exportDetails.setHeaderMessageProperty("chatak.header.blackListedAccount.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(blackListAccountRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("BlackListedAccount.file.exportutil.issuer", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("BlackListedAccount.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("BlackListedAccount.file.exportutil.account", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("BlackListedAccount.file.exportutil.reason", null,
						LocaleContextHolder.getLocale()),
			
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<BlacklistedAccountRequest> blackListedAccountRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (BlacklistedAccountRequest blacklistData : blackListedAccountRequest) {

			Object[] rowData = { 
					blacklistData.getIssuerName() , blacklistData.getPtoName() , blacklistData.getAccount(), blacklistData.getReason(),  
			
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}
   
	private static boolean isValidAlfaNumeric(String data) {
		String pattern = "^[a-zA-Z0-9-\\\\_\\\\s ]*$";
		return data.matches(pattern);
	}

	private static boolean isValidNumeric(String data) {
		String pattern = "[0-9]+";
		return data.matches(pattern);
	}
}
