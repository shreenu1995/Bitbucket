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
import com.afcs.web.service.DeviceTypeManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeData;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DeviceTypeManagementController {

	@Autowired
	DeviceTypeManagementService deviceTypeManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;


	private static final Logger logger = LoggerFactory.getLogger(DeviceTypeManagementController.class);
	private static final String DEVICE_TYPR_REGISTRATION = "device-type-registration";
    private static final String DEVICE_TYPE_SEARCH = "device-type-search";
	private static final String DEVICE_TYPE_SEARCH_PAGINATION = "device-type-search-pagination";
	private static final String DEVICE_TYPE_DATA_SIZE = "deviceTypeDataSize";
	private static final String DEVICE_TYPE_EDIT_ACTION = "device-type-edit-action";
	private static final String DEVICE_TYPE_VIEW_ACTION = "device-type-view-action";
	private static final String DEVICE_TYPE_EDIT_REQ = "deviceTypeEditRequest";
	private static final String DEVICE_TYPE_VIEW_REQ = "deviceTypeViewRequest";
	private static final String UPDATE_DEVICE_TYPE = "update-device-type";
	private static final String DEVICE_TYPE_UPDATE = "device-type-update";
	private static final String DEVICE_TYPE_REQUEST = "deviceTypeRequest";
	private static final String DEVICE_TYPE_SEARCH_REQUEST = "deviceTypeSearch";
	private static final String DEVICE_TYPE_DATA = "deviceTypeData";
	private static final String UPDATE_DEVICE_TYPE_STATUS = "update-device-type-status";
	private static final String DOWNLOAD_DEVICE_TYPE_REPORT = "downloadDeviceTypeReport";
	
	@GetMapping(value = DEVICE_TYPR_REGISTRATION)
	public ModelAndView saveDeviceTypeGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(DEVICE_TYPR_REGISTRATION);
		DeviceTypeRegistrationRequest request = new DeviceTypeRegistrationRequest();
		model.put(DEVICE_TYPE_REQUEST, request);
		return modelandview;
	}

	@PostMapping(value = DEVICE_TYPR_REGISTRATION)
	public ModelAndView saveDeviceTypePost(Map<String, Object> model, DeviceTypeRegistrationRequest request,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH);
		DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
		model.put(DEVICE_TYPE_SEARCH_REQUEST, deviceTypeSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		DeviceTypeRegistrationResponse response = deviceTypeManagementService.saveDeviceType(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.type.created.successfully"));
			modelAndView.addObject(Constants.SUCCESS, properties.getProperty("device.type.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			modelAndView.addObject(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = DEVICE_TYPE_SEARCH)
	public ModelAndView searchDeviceTypeGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH);
		DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
		deviceTypeSearchRequest.setPageSize(10);
		deviceTypeSearchRequest.setPageIndex(Constants.ONE);
		session.setAttribute(DEVICE_TYPE_DATA, deviceTypeSearchRequest);
		model.put(DEVICE_TYPE_SEARCH_REQUEST, deviceTypeSearchRequest);
		return modelAndView;
	}

	@PostMapping(value = DEVICE_TYPE_SEARCH)
	public ModelAndView searchDeviceTypePost(Map<String, Object> model, DeviceTypeSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH);
		modelAndView.addObject(DEVICE_TYPE_SEARCH_REQUEST, request);
		DeviceTypeSearchResponse response=null;
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return searchDeviceTypeDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try{
		request.setPageIndex(Constants.ONE);
		response = deviceTypeManagementService.searchDeviceType(request);
		session.setAttribute(DEVICE_TYPE_DATA, request);
		}
		 catch (AFCSException e) {
				model.put(Constants.ERROR, e.getMessage());
				logger.error("ERROR:: DeviceTypeManagementController:: searchDeviceTypePost method :: Exception", e);
				return modelAndView;
			}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DEVICE_TYPE_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(DEVICE_TYPE_SEARCH_REQUEST, request);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(DEVICE_TYPE_DATA, response.getListDeviceType());
			model.put(DEVICE_TYPE_SEARCH_REQUEST, new DeviceTypeSearchRequest());
			model.put(DEVICE_TYPE_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DEVICE_TYPE_DATA_SIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_TYPE_SEARCH_PAGINATION)
	public ModelAndView searchDeviceTypeDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		DeviceTypeSearchRequest request =(DeviceTypeSearchRequest) session.getAttribute(DEVICE_TYPE_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH);
		model.put(DEVICE_TYPE_SEARCH_REQUEST, new DeviceTypeSearchRequest());
		DeviceTypeSearchResponse response = new DeviceTypeSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = deviceTypeManagementService.searchDeviceType(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceTypeManagementController:: searchDeviceTypeDataPagination method :: Exception", e);
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DEVICE_TYPE_DATA, response.getListDeviceType());
			model.put(DEVICE_TYPE_SEARCH_REQUEST, request);
			session.setAttribute(DEVICE_TYPE_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DEVICE_TYPE_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_TYPE_EDIT_ACTION)
	public ModelAndView editDeviceType(final HttpSession session,
			@RequestParam("deviceTypeId") Long deviceTypeId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_UPDATE);
		DeviceTypeSearchResponse response = new DeviceTypeSearchResponse();
		try {
			DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
			deviceTypeSearchRequest.setPageIndex(Constants.ONE);
			deviceTypeSearchRequest.setDeviceTypeId(deviceTypeId);
			response = deviceTypeManagementService.searchDeviceType(deviceTypeSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceTypeManagementController:: editDeviceType method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceTypeProfileUpdateRequest deviceTypeProfileUpdateRequest = populateUserUpdateData(response);
			model.put(DEVICE_TYPE_EDIT_REQ, deviceTypeProfileUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_TYPE_VIEW_ACTION)
	public ModelAndView viewDeviceType(final HttpSession session,
			@RequestParam("deviceTypeId") Long deviceTypeId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("device-type-view");
		DeviceTypeSearchResponse response = new DeviceTypeSearchResponse();
		model.put(DEVICE_TYPE_VIEW_REQ, new UserProfileUpdateRequest());
		try {
			DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
			deviceTypeSearchRequest.setPageIndex(Constants.ONE);
			deviceTypeSearchRequest.setDeviceTypeId(deviceTypeId);
			response = deviceTypeManagementService.searchDeviceType(deviceTypeSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceTypeManagementController:: viewDeviceType method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceTypeProfileUpdateRequest deviceTypeProfileUpdateRequest = populateUserUpdateData(response);
			model.put(DEVICE_TYPE_VIEW_REQ, deviceTypeProfileUpdateRequest);
		}
		return modelAndView;
	}

	protected DeviceTypeProfileUpdateRequest populateUserUpdateData(DeviceTypeSearchResponse response) {
		DeviceTypeProfileUpdateRequest deviceTypeProfileUpdateRequest = new DeviceTypeProfileUpdateRequest();
		DeviceTypeSearchRequest deviceTypeSearchRequest = response.getListDeviceType().get(0);
		deviceTypeProfileUpdateRequest.setDescription(deviceTypeSearchRequest.getDescription());
		deviceTypeProfileUpdateRequest.setDeviceTypeName(deviceTypeSearchRequest.getDeviceTypeName());
		deviceTypeProfileUpdateRequest.setDeviceTypeId(deviceTypeSearchRequest.getDeviceTypeId());
		return deviceTypeProfileUpdateRequest;
		
	}
	
	@PostMapping(value = UPDATE_DEVICE_TYPE)
	public ModelAndView updateDeviceType(HttpSession session, Map<String, Object> model,
			DeviceTypeProfileUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_UPDATE);
		WebResponse response = null;
		model.put(DEVICE_TYPE_EDIT_REQ, request);
		try {
			response = deviceTypeManagementService.updateDeviceType(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceTypeManagementController:: updateDeviceType method :: Exception", e);
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.type.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DEVICE_TYPE_EDIT_REQ, request);
		}
		return modelAndView;
	}
	
	@PostMapping(value = UPDATE_DEVICE_TYPE_STATUS)
	public ModelAndView updateDeviceTypeStatus(final HttpSession session,
			@RequestParam("deviceTypeId") Long deviceTypeId, @RequestParam("deviceTypeStatus") String status, Map<String,Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH_REQUEST);
		model.put(DEVICE_TYPE_DATA, new DeviceTypeData());
		DeviceTypeStatusUpdateRequest request = new DeviceTypeStatusUpdateRequest();
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setDeviceTypeId(deviceTypeId);
			response = deviceTypeManagementService.updateDeviceTypeStatus(request);
			if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(Constants.SUCCESS, properties.getProperty("device.status.update.success"));
				return searchDeviceTypeDataPagination(session, (Integer)session.getAttribute(Constants.PAGE_NUMBER), model);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			logger.error("ERROR:: DeviceTypeManagementController:: updateDeviceTypeStatus method :: Exception", e);
			return modelAndView;
		}
		return modelAndView;
	}
	@PostMapping(value = DOWNLOAD_DEVICE_TYPE_REPORT)
	public ModelAndView downloadDeviceTypeReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: DeviceTypeController:: downloadDeviceReport method");
		ModelAndView modelAndView = new ModelAndView(DEVICE_TYPE_SEARCH);
		DeviceTypeSearchResponse deviceTypeResponse = null;
		try {
			DeviceTypeSearchRequest deviceTypeSearchRequest = (DeviceTypeSearchRequest) session.getAttribute(DEVICE_TYPE_DATA);
			deviceTypeSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = deviceTypeSearchRequest.getPageSize();
			if (downloadAllRecords) {
				deviceTypeSearchRequest.setPageIndex(Constants.ONE);
				deviceTypeSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			deviceTypeResponse = deviceTypeManagementService.searchDeviceType(deviceTypeSearchRequest);
			List<DeviceTypeSearchRequest> reuestResponses = deviceTypeResponse.getListDeviceType();
			if (!StringUtil.isNull(deviceTypeResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadDeviceTypeReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			deviceTypeSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: DeviceTypeController:: downloadDeviceTypeReport method", e);
		}
		logger.info("Exit:: DeviceTypeController:: downloadDeviceTypeReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadDeviceTypeReport(List<DeviceTypeSearchRequest> deviceTypeSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DeviceType_");
		exportDetails.setHeaderMessageProperty("chatak.header.deviceType.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(deviceTypeSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("DeviceType.file.exportutil.deviceTypeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceType.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceType.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<DeviceTypeSearchRequest> deviceTypeResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DeviceTypeSearchRequest deviceTypeData : deviceTypeResponse) {

			Object[] rowData = { deviceTypeData.getDeviceTypeName(), deviceTypeData.getDescription(),	
					deviceTypeData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}