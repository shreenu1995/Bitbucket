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
import com.afcs.web.service.OperatorManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.TransactionReportService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.TicketTxnDataResponse;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.TransactionReportGenerationResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class TransactionDataReportController {
	
	@Autowired
	TransactionReportService transactionReportService;
	
	@Autowired
	PtoManagementService ptoManagementService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	OrganizationManagementService organizationManagementService;
	
	@Autowired
	OperatorManagementService operatorManagementService;
	
	private static final Logger logger = LoggerFactory.getLogger(PtoSummaryController.class);
	
	private static final String TRANSACTION_DATA_REPORT ="transaction-data-report";
	private static final String TRANSACTION_ID_LIST = "transactionIdList";
	private static final String PAGE_NUMBER_TXN_REPORT = "pageNumberTxnReport";
	private static final String PAGINATION_TXN_DATA_REPORT = "transaction-data-report-pagination";
	private static final String TRANSACTION_DATA = "transactionData";
	private static final String TXN_REPORT_REQ = "txnReportRequest";
	private static final String TXN_DATA_SIZE = "transactionDataSize";
	private static final String TXN_REPORT_REQUEST = "transactionReportRequest";
	private static final String DOWNLOAD_TRANSACTION_DATA_DOWNLOAD = "downloadTransactionDataDownloadReport";
	private static final String ORGANIZATION_LIST = "organizationList";
	
	@GetMapping(value = TRANSACTION_DATA_REPORT)
	public ModelAndView transactionData(Map<String,Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(TRANSACTION_DATA_REPORT);
		TransactionReportGenerationRequest txnReportRequest = new TransactionReportGenerationRequest();
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		ptoListRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		PtoListResponse ptoListResponse = null;
		model.put(TXN_REPORT_REQUEST, txnReportRequest);
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
			if (!StringUtil.isNull(ptoListResponse)
					&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
				model.put("ptoListData", ptoListResponse.getPtoList());
				session.setAttribute("ptoListData", ptoListResponse.getPtoList());
			}

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
			
			OperatorSearchRequest operatorSearchRequest = new OperatorSearchRequest();
			OperatorResponse operatorSearchResponse = null;
			operatorSearchRequest.setPageIndex(Constants.ONE);
			operatorSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			operatorSearchResponse = getOperatorList(session, operatorSearchRequest);
			if (!StringUtil.isNull(operatorSearchResponse)
					&& operatorSearchResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
				model.put("operatorListData", operatorSearchResponse.getListOfOperators());
				session.setAttribute("operatorListData", operatorSearchResponse.getListOfOperators());
			}

		} catch (AFCSException e) {
			logger.error("TransactionReportController class :: getTransactionReportSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if (!StringUtil.isNull(response)) {
			model.put(ORGANIZATION_LIST, response.getOrganizationList());
			session.setAttribute(ORGANIZATION_LIST, response.getOrganizationList());
		}
		
		model.put(ORGANIZATION_LIST, response.getOrganizationList());
		return modelAndView;
	}
	
	private OperatorResponse getOperatorList(HttpSession session, OperatorSearchRequest operatorSearchRequest)
			throws AFCSException {
		PtoListRequest ptoListRequest;
		OperatorResponse operatorSearchResponse;
		if (operatorSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);
		} else if (operatorSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			operatorSearchRequest.setOrganizationId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);
		} else {
			ptoListRequest = new PtoListRequest();
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
			PtoList pto = new PtoList();
			if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				operatorSearchRequest.setPtoName(pto.getPtoName());
			}
			operatorSearchRequest.setPageIndex(Constants.ONE);
			operatorSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			operatorSearchResponse = operatorManagementService.searchOperator(operatorSearchRequest);

		}
		return operatorSearchResponse;
	}
	
	@PostMapping(value = TRANSACTION_DATA_REPORT)
	public ModelAndView transactionReportData(Map<String, Object> model, HttpSession session,
			TransactionReportGenerationRequest txnReportRequest) {
		ModelAndView modelAndView = new ModelAndView(TRANSACTION_DATA_REPORT);
		TransactionReportGenerationResponse response = null;
		model.put(TRANSACTION_ID_LIST,session.getAttribute(TRANSACTION_ID_LIST));
		model.put(TXN_REPORT_REQUEST, txnReportRequest);	
		try {
			txnReportRequest.setIndexPage(Constants.ONE);
			txnReportRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
			txnReportRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			session.setAttribute(TXN_REPORT_REQ, txnReportRequest);
			if (txnReportRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			} else if (txnReportRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				txnReportRequest.setOrganizationId(session.getAttribute(Constants.ORG_ID).toString());
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			} else {
				txnReportRequest.setPtoId(Long.parseLong(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			}
			session.setAttribute(TRANSACTION_DATA, txnReportRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView.addObject(TRANSACTION_DATA, response.getTxnResponse());
			session.setAttribute(TXN_DATA_SIZE, response.getNoOfRecords());
			model.put(TXN_DATA_SIZE, response.getNoOfRecords());
			model.put(TXN_REPORT_REQUEST, txnReportRequest);	
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE,TXN_DATA_SIZE);
		} else {
		    model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
	
	@PostMapping(value = PAGINATION_TXN_DATA_REPORT)
	public ModelAndView transactionReportDataPagination(final HttpSession session,
      @RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String,Object> model) {
		ModelAndView modelAndView = new ModelAndView(TRANSACTION_DATA_REPORT);
		TransactionReportGenerationRequest txnReportRequest = (TransactionReportGenerationRequest) session.getAttribute(TXN_REPORT_REQ);
	    session.setAttribute(PAGE_NUMBER_TXN_REPORT, pageNumber);
	    txnReportRequest.setIndexPage(pageNumber);
	    txnReportRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		session.setAttribute(TXN_REPORT_REQ, txnReportRequest);
		TransactionReportGenerationResponse response = null;
		try {
			txnReportRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			session.setAttribute(TXN_REPORT_REQ, txnReportRequest);
			if (txnReportRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			} else if (txnReportRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				txnReportRequest.setOrganizationId(session.getAttribute(Constants.ORGANIZATION_ID).toString());
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			} else {
				txnReportRequest.setPtoId(Long.parseLong(session.getAttribute(Constants.PTO_ID).toString()));
				response = transactionReportService.generateTransactionReport(txnReportRequest);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
	    PaginationUtil.performPagination(modelAndView, session, pageNumber,TXN_DATA_SIZE);
	    model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
	    if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView.addObject(TRANSACTION_DATA, response.getTxnResponse());
			model.put(TXN_REPORT_REQUEST, txnReportRequest);	
		} else {
		    model.put(Constants.ERROR, response.getStatusMessage());
		}
	    return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_TRANSACTION_DATA_DOWNLOAD)
	public ModelAndView downloadTransactionDataReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: TransactionDataController:: downloadTransactionDataReport method");
		ModelAndView modelAndView = new ModelAndView(TRANSACTION_DATA_REPORT);
		TransactionReportGenerationResponse transactionReportResponse = null;
		try {
			TransactionReportGenerationRequest txnReportRequest = (TransactionReportGenerationRequest) session.getAttribute(TRANSACTION_DATA);
			txnReportRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = txnReportRequest.getPageSize();
			if (downloadAllRecords) {
				txnReportRequest.setPageIndex(Constants.ONE);
				txnReportRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			transactionReportResponse = transactionReportService.generateTransactionReport(txnReportRequest);
			List<TicketTxnDataResponse> reuestResponses = transactionReportResponse. getTxnResponse();
			if (!StringUtil.isNull(transactionReportResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadOperatorSessionRequestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			txnReportRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::TransactionDataController:: TransactionDataReport method", e);
		}
		logger.info("Exit:: TransactionDataController:: downloadTransactionDataReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadOperatorSessionRequestReport(List<TicketTxnDataResponse> transactionDataRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Transaction Data");
		exportDetails.setHeaderMessageProperty("chatak.header.TransactionData.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(transactionDataRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("TransactionData.file.exportutil.ticketNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.transactionId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.ticketDateTime", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.ticketFareAmount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.ticketOriginStationCode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.ticketDestStationCode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.ticketPaymentMode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("TransactionData.file.exportutil.stationCode", null,
						LocaleContextHolder.getLocale()),
				
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}
   	
	private static List<Object[]> getDetailTypeFileData(List<TicketTxnDataResponse> ticketTxnDataResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (TicketTxnDataResponse ticketTxnData : ticketTxnDataResponse) {

			       if(ticketTxnData.getTicketPaymentMode().equalsIgnoreCase("P0")){
			    	   ticketTxnData.setTicketPaymentMode("Cash Payment");
			       }
			       else if(ticketTxnData.getTicketPaymentMode().equalsIgnoreCase("P1")){
			    	   
				    	   ticketTxnData.setTicketPaymentMode("Card Payment");
			       }
			       else if(ticketTxnData.getTicketPaymentMode().equalsIgnoreCase("P2")){
			    	   
				    	   ticketTxnData.setTicketPaymentMode("Wallet Payment");
			       }
			Object[] rowData = {  ticketTxnData.getTicketNumber() , ticketTxnData.getTransactionId() , ticketTxnData.getTicketDateTime() , ticketTxnData.getTicketFareAmount() ,
					              ticketTxnData.getTicketOriginStationCode() , ticketTxnData.getTicketDestStationCode() , 
					              ticketTxnData.getTicketPaymentMode() , ticketTxnData.getTicketOriginStationCode()
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}


}