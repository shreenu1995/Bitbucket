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
import com.afcs.web.service.OperatorSessionReportService;
import com.afcs.web.service.TransactionReportService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationRequest;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.OperatorSessionReportResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class OperatorSessionReportController {
	
	@Autowired
	OperatorSessionReportService operatorSessionReportService;
	
	@Autowired
	TransactionReportService transactionReportService;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(PtoSummaryController.class);
	
	private static final String OPERATOR_SESSION_REPORT = "operator-session-report";
	private static final String TRANSACTION_ID_LIST = "transactionIdList";
	private static final String TXN_REPORT_REQ = "txnReportRequest";
	private static final String TXN_DATA_SIZE = "transactionDataSize";
	private static final String TRANSACTION_DATA = "transactionData";
	private static final String OPERATOR_REPORT_PAGINATION = "operation-report-pagination";
	private static final String PAGE_NUMBER_TXN_REPORT = "pageNumberTxnReport";
	private static final String TXN_REPORT_REQUEST = "transactionReportRequest";
	private static final String DOWNLOAD_OPERATOR_SESSION_DOWNLOAD_REPORT = "downloadOperatorSessionDownloadReport";
	
	@GetMapping(value = OPERATOR_SESSION_REPORT)
	public ModelAndView showOperatorSessionReport(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SESSION_REPORT);
		OperatorSessionReportGenerationRequest generationRequest = new OperatorSessionReportGenerationRequest();
		model.put(TXN_REPORT_REQUEST, generationRequest);
		return modelAndView;
	}
	
	@PostMapping(value = OPERATOR_SESSION_REPORT)
	public ModelAndView processOperatorSessionReport(Map<String, Object> model, HttpSession session,
			OperatorSessionReportGenerationRequest operatorSessionReportRequest) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SESSION_REPORT);
		model.put(TRANSACTION_ID_LIST, session.getAttribute(TRANSACTION_ID_LIST));
		model.put(TXN_REPORT_REQUEST, operatorSessionReportRequest);
		OperatorSessionReportGenerationResponse response = null;
		try {
			operatorSessionReportRequest.setPageIndex(Constants.ONE);
			operatorSessionReportRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
			session.setAttribute(TXN_REPORT_REQ, operatorSessionReportRequest);
			response = operatorSessionReportService.generateOperatorSessionReport(operatorSessionReportRequest);
			session.setAttribute(TRANSACTION_DATA, operatorSessionReportRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView.addObject(TRANSACTION_DATA, response.getListOfOperatorSessionResponse());
			session.setAttribute(TXN_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(TXN_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(TXN_REPORT_REQUEST, operatorSessionReportRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, TXN_DATA_SIZE);
		} else {
		    model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;

	}
	
	@PostMapping(value = OPERATOR_REPORT_PAGINATION)
	public ModelAndView operatorSessionReportPagination(Map<String, Object> model, HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber) {
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SESSION_REPORT);
		OperatorSessionReportGenerationResponse response = null;
		OperatorSessionReportGenerationRequest operatorSessionReportRequest = (OperatorSessionReportGenerationRequest) session.getAttribute(TXN_REPORT_REQ);
		session.setAttribute(PAGE_NUMBER_TXN_REPORT, pageNumber);
		operatorSessionReportRequest.setPageIndex(pageNumber);
		operatorSessionReportRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = operatorSessionReportService.generateOperatorSessionReport(operatorSessionReportRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		PaginationUtil.performPagination(modelAndView, session, pageNumber, TXN_DATA_SIZE);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView.addObject(TRANSACTION_DATA, response.getListOfOperatorSessionResponse());
			model.put(TXN_REPORT_REQUEST, operatorSessionReportRequest);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
	@PostMapping(value =  DOWNLOAD_OPERATOR_SESSION_DOWNLOAD_REPORT)
	public ModelAndView downloadOperatorSessionReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: OperatorSessionController:: downloadOperatorSessionReport method");
		ModelAndView modelAndView = new ModelAndView(OPERATOR_SESSION_REPORT);
		OperatorSessionReportGenerationResponse operatorSessionResponse = null;
		try {
			OperatorSessionReportGenerationRequest operatorSessionReportRequest = (OperatorSessionReportGenerationRequest) session.getAttribute(TRANSACTION_DATA);
			operatorSessionReportRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = operatorSessionReportRequest.getPageSize();
			if (downloadAllRecords) {
				operatorSessionReportRequest.setPageIndex(Constants.ONE);
				operatorSessionReportRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			operatorSessionResponse =operatorSessionReportService.generateOperatorSessionReport(operatorSessionReportRequest);
			List<OperatorSessionReportResponse> reuestResponses = operatorSessionResponse. getListOfOperatorSessionResponse();
			if (!StringUtil.isNull(operatorSessionResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadOperatorSessionRequestReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			operatorSessionReportRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::PassengerAnalysisController:: PassengerAnalysisReport method", e);
		}
		logger.info("Exit:: PassengerAnalysisController:: downloadPassengerAnalysisReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadOperatorSessionRequestReport(List<OperatorSessionReportResponse> operatorSessionRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Operator Session");
		exportDetails.setHeaderMessageProperty("chatak.header.OperatorSession.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(operatorSessionRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("OperatorSession.file.exportutil.userId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OperatorSession.file.exportutil.ptoId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OperatorSession.file.exportutil.deviceId", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OperatorSession.file.exportutil.transaction", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("OperatorSession.file.exportutil.generateDateAndTime", null,
						LocaleContextHolder.getLocale()),
				
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<OperatorSessionReportResponse> operatorSessionRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (OperatorSessionReportResponse operatorSessionData : operatorSessionRequest) {

			Object[] rowData = {    operatorSessionData.getUserId() , operatorSessionData.getPtoId() , operatorSessionData.getDeviceId() ,
			                        operatorSessionData.getTransactionId() , operatorSessionData.getGenerateDateAndTime() 
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

	
}
