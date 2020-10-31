package com.afcs.web.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.TerminalSetupReportService;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.TerminalSetupReportGenerationResponse;
import com.chatak.transit.afcs.server.pojo.web.TerminalsetupReportGenerationRequest;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class TerminalSetupReportController {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(TerminalSetupReportController.class);

	@Autowired
	TerminalSetupReportService terminalSetupReportService;

	private static final String TERMINAL_SETUP_REPORT = "terminal-setup-report";
	private static final String PTO_OPERATION_LIST = "ptoOperationList";
	private static final String TERMINAL_SETUP_REQUEST = "terminalSetupRequest";
	private static final String TERMINAL_REPORT_DATA = "terminalReportData";
	private static final String TERMINAL_REPORT_DATA_SIZE = "terminalReportDataSize";
	private static final String PAGE_NUMBER_TERMINAL_REPORT = "pageNumberTerminalReport";
	private static final String PAGINATION_TERMINAL_SETUP_REPORT = "terminal-setup-report-pagination";

	@GetMapping(value = TERMINAL_SETUP_REPORT)
	public ModelAndView terminalDataGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(TERMINAL_SETUP_REPORT);
		TerminalsetupReportGenerationRequest request = new TerminalsetupReportGenerationRequest();
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		model.put(TERMINAL_SETUP_REQUEST, request);
		model.put(PTO_OPERATION_LIST, session.getAttribute(Constants.PTO_LIST));
		return modelAndView;
	}

	@PostMapping(value = TERMINAL_SETUP_REPORT)
	public ModelAndView terminalReportDataPost(Map<String, Object> model, HttpSession session,
			TerminalsetupReportGenerationRequest request) {
		ModelAndView modelAndView = new ModelAndView(TERMINAL_SETUP_REPORT);
		TerminalSetupReportGenerationResponse response = null;

		try {
			request.setPageIndex(Constants.ONE);
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			model.put(PTO_OPERATION_LIST, session.getAttribute(Constants.PTO_LIST));
			session.setAttribute(TERMINAL_SETUP_REQUEST, request);
			response = terminalSetupReportService.generateTerminalSetupReport(request);
		} catch (AFCSException e) {
			model.put(TERMINAL_SETUP_REQUEST, request);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(TERMINAL_SETUP_REQUEST, request);
			modelAndView.addObject(TERMINAL_REPORT_DATA, response.getListOfterminalSetupResponse());
			session.setAttribute(TERMINAL_REPORT_DATA_SIZE, response.getNoOfRecords());
			model.put(TERMINAL_REPORT_DATA_SIZE, response.getNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, TERMINAL_REPORT_DATA_SIZE);
		} else {
			model.put(TERMINAL_SETUP_REQUEST, request);
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = PAGINATION_TERMINAL_SETUP_REPORT)
	public ModelAndView terminalReportPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(TERMINAL_SETUP_REPORT);
		TerminalsetupReportGenerationRequest request = (TerminalsetupReportGenerationRequest) session
				.getAttribute(TERMINAL_SETUP_REQUEST);
		session.setAttribute(PAGE_NUMBER_TERMINAL_REPORT, pageNumber);
		request.setPageIndex(pageNumber);
		model.put(TERMINAL_SETUP_REQUEST, request);
		session.setAttribute(TERMINAL_SETUP_REQUEST, request);

		TerminalSetupReportGenerationResponse response = null;
		try {
			response = terminalSetupReportService.generateTerminalSetupReport(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			String logError = "ERROR :: TerminalSetupReportController :: terminalReportDataPost Method :: terminalReportPagination ";
			logger.error(logError , e.getMessage());
			return modelAndView;
		}
		PaginationUtil.performPagination(modelAndView, session, pageNumber, TERMINAL_REPORT_DATA_SIZE);
		 model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			modelAndView.addObject(TERMINAL_REPORT_DATA, response.getListOfterminalSetupResponse());
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}
}