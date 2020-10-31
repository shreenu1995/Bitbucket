package com.afcs.web.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.DeviceTrackingService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTrackingResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DeviceTrackingController {

	private static Logger logger = LoggerFactory.getLogger(DeviceTrackingController.class);

	private static final String DEVICE_TRACKING = "device-tracking";
	private static final String TRACKING_REQUEST = "trackingRequest";
	private static final String PAGINATION_DEVICE_TRACKING = "device-tracking-pagination";
	private static final String PAGE_NUMBER_TXN_REPORT = "pageNoTxnReport";
	private static final String SEARCH_DATA = "searchDataSize";
	private static final String DOWNLOAD_DEVICE_TRACKING_REPORT = "download_device_tracking_report";
	private static final String SEARCH_LIST = "searchList";
	
	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	DeviceTrackingService deviceTrackingService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = DEVICE_TRACKING)
	public ModelAndView getDeviceTrackingSearch(
			@ModelAttribute("trackingRequest") DeviceTrackingRequest trackingRequest, Map<String, Object> model,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TRACKING);
		model.put(TRACKING_REQUEST, trackingRequest);
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		try {
			trackingRequest.setPageSize(10);
			trackingRequest.setIndexPage(Constants.ONE);
			session.setAttribute(SEARCH_LIST, trackingRequest);
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
			logger.error("ERROR :: DeviceTrackingController class :: getDeviceTrackingSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		return modelAndView;
	}

	@PostMapping(value = DEVICE_TRACKING)
	public ModelAndView processDeviceTrackingSearch(
			@ModelAttribute("trackingRequest") DeviceTrackingRequest trackingRequest, Map<String, Object> model,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TRACKING);
		DeviceTrackingResponse response = new DeviceTrackingResponse();
		trackingRequest.setIndexPage(Constants.ONE);
		try {
			response = deviceTrackingService.searchDeviceTracking(trackingRequest);
			session.setAttribute(SEARCH_LIST, trackingRequest);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				session.setAttribute(TRACKING_REQUEST, trackingRequest);
				session.setAttribute(SEARCH_DATA, response.getNoOfRecords());
				model.put("searchFlag", "true");
				model.put(SEARCH_LIST, response.getDeviceTrackingRequest());
				model.put(SEARCH_DATA, response.getNoOfRecords());
				model.put(TRACKING_REQUEST, trackingRequest);
				PaginationUtil.performPagination(modelAndView, session, Constants.ONE, SEARCH_DATA);
			} else {
				model.put(Constants.ERROR, response.getStatusMessage());
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: DeviceTrackingController class :: processDeviceTrackingSearch method :: exception",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		return modelAndView;
	}

	@PostMapping(value = PAGINATION_DEVICE_TRACKING)
	public ModelAndView transactionReportPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model,
			@ModelAttribute("trackingRequest") DeviceTrackingRequest trackingRequest) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TRACKING);
		DeviceTrackingRequest trackingReq = (DeviceTrackingRequest) session.getAttribute(TRACKING_REQUEST);
		session.setAttribute(PAGE_NUMBER_TXN_REPORT, pageNumber);
		trackingReq.setIndexPage(pageNumber);
		trackingReq.setUserId(session.getAttribute(Constants.USER_ID).toString());
		session.setAttribute(TRACKING_REQUEST, trackingReq);
		DeviceTrackingResponse response = null;
		try {
			trackingReq.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			response = deviceTrackingService.searchDeviceTracking(trackingReq);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		PaginationUtil.performPagination(modelAndView, session, pageNumber, SEARCH_DATA);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(SEARCH_LIST, response.getDeviceTrackingRequest());
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = DOWNLOAD_DEVICE_TRACKING_REPORT)
	public ModelAndView downloadRoleReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords)
			throws AFCSException, IOException {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleController:: downloadRoleReport method");
		ModelAndView modelAndView = new ModelAndView(DEVICE_TRACKING);

		// Call the API being called in view/search result
		try {
            
			DeviceTrackingRequest trackingRequest = (DeviceTrackingRequest) session.getAttribute(SEARCH_LIST);
			trackingRequest.setIndexPage(downLoadPageNumber);
			if (downloadAllRecords) {
				trackingRequest.setIndexPage(Constants.ONE);
				trackingRequest.setPageSize(totalRecords);
			}
			
			
			ExportDetails exportDetails = new ExportDetails();
			DeviceTrackingResponse deviceTrackingResponse = deviceTrackingService.searchDeviceTracking(trackingRequest);
			List<DeviceTrackingRequest> reuestResponses = deviceTrackingResponse.getDeviceTrackingRequest();
			if (!StringUtil.isNull(deviceTrackingResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadRoleReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
		}
		logger.info("Exit:: RoleController:: downloadRoleReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadRoleReport(List<DeviceTrackingRequest> deviceTracking,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DeviceTracking_");
		exportDetails.setHeaderMessageProperty("chatak.header.devicetracking.messages");

		exportDetails.setHeaderList(getOrganizationHeaderList());
		exportDetails.setFileData(getOrganizationFileData(deviceTracking));
	}

	private List<String> getOrganizationHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("deviceTrackingList.file.exportutil.Pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("deviceTrackingList.file.exportutil.deviceSerial", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("deviceTrackingList.file.exportutil.status", null,
						LocaleContextHolder.getLocale()) };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getOrganizationFileData(List<DeviceTrackingRequest> orgResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DeviceTrackingRequest roleData : orgResponse) {

			Object[] rowData = { roleData.getPtoName(), roleData.getDeviceSerialNo(), roleData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}
