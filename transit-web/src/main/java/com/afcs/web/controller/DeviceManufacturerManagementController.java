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
import com.afcs.web.service.DeviceManufacturerManagementService;
import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeListViewRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.UserProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DeviceManufacturerManagementController {

	@Autowired
	DeviceManufacturerManagementService deviceManufacturereManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	DeviceOnboardService deviceOnboardService;
	
	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;


	private static final Logger logger = LoggerFactory.getLogger(DeviceManufacturerManagementController.class);

	private static final String DEVICE_MANUFACTURER_REGISTRATION = "device-manufacturer-registration";
	private static final String DEVICE_MANUFACTURER_REQUEST = "deviceManufacturerRequest";
	private static final String DEVICE_MANUFACTURER_SEARCH = "device-manufacturer-search";
	private static final String DEVICE_MANUFACTURER_SEARCH_PAGINATION = "device-manufacturer-search-pagination";
	private static final String DEVICE_MANUFACTURER_DATA_SIZE = "deviceManufacturerDataSize";
	private static final String DEVICE_MANUFACTURER_SARCH = "deviceManufacturerSearch";
	private static final String DEVICE_MANUFACTURER_SEARCH_REQUEST = "deviceManufacturerSearchRequest";
	private static final String DEVICE_MANUFACTURER_VIEW_ACTION = "device-manufacturer-view-action";
	private static final String DEVICE_MANUFACTURER_EDIT_ACTION = "device-manufacturer-edit-action";
	private static final String DEVICE_MANUFACTURER_EDIT_REQ = "deviceManufacturerEditRequest";
	private static final String DEVICE_MANUFACTURER_UPDATE = "update-device-manufacturer";
	private static final String UPDATE_DEVICE_MANUFACTURER_STATUS = "update-device-manufacturer-status";
	private static final String DEVICE_TYPE_LIST_DATA = "deviceTypeListData";
	private static final String DEVICE_MANUFACTURER_LIST_DATA = "deviceManufacturerListData";
	private static final String DOWNLOAD_DEVICE_MANUFACTURER_REPORT = "downloadDeviceManufacturerReport";
	
	@GetMapping(value = DEVICE_MANUFACTURER_REGISTRATION)
	public ModelAndView deviceManufacturerRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_REGISTRATION);
		DeviceManufacturerRegistrationRequest deviceManufacturerRegistrationRequest = new DeviceManufacturerRegistrationRequest();
		model.put(DEVICE_MANUFACTURER_REQUEST, deviceManufacturerRegistrationRequest);
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_REGISTRATION)
	public ModelAndView deviceManufacturerRegisterPost(Map<String, Object> model,
			DeviceManufacturerRegistrationRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		DeviceManufacturerSearchRequest deviceManufacturerSearchRequest = new DeviceManufacturerSearchRequest();
		model.put(DEVICE_MANUFACTURER_SARCH, deviceManufacturerSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		DeviceManufacturerRegistrationResponse response = deviceManufacturereManagementService.deviceManufacturerRegistration(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.manufacturer.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = DEVICE_MANUFACTURER_SEARCH)
	public ModelAndView deviceManufacturerSearchGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		List<DeviceTypeSearchRequest> listDevice = deviceOnboardService.getDeviceTypeList().getListOfDeviceType();
		session.setAttribute(DEVICE_TYPE_LIST_DATA, listDevice);
		model.put(DEVICE_TYPE_LIST_DATA, listDevice);
		DeviceManufacturerSearchRequest deviceManufacturerSearch = new DeviceManufacturerSearchRequest();
		deviceManufacturerSearch.setPageSize(10);
		deviceManufacturerSearch.setPageIndex(Constants.ONE);
		session.setAttribute(DEVICE_MANUFACTURER_LIST_DATA, deviceManufacturerSearch);
		model.put(DEVICE_MANUFACTURER_SARCH, deviceManufacturerSearch);
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_SEARCH)
	public ModelAndView deviceManufacturerSearchPost(Map<String, Object> model,
			DeviceManufacturerSearchRequest request, HttpSession session,
			@RequestParam("cancelManufacturerId") String cancelManufacturerId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		if (!StringUtil.isNullEmpty(cancelManufacturerId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return deviceManufacturerDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageIndex(Constants.ONE);
		model.put(DEVICE_MANUFACTURER_SEARCH, new DeviceManufacturerSearchRequest());
		DeviceManufacturerListSearchResponse response = deviceManufacturereManagementService
				.deviceManufacturerSearch(request);
		session.setAttribute(DEVICE_MANUFACTURER_LIST_DATA, request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DEVICE_MANUFACTURER_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(DEVICE_MANUFACTURER_SEARCH_REQUEST, request);
			model.put(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			model.put(DEVICE_MANUFACTURER_SARCH, new DeviceManufacturerSearchRequest());
			model.put(DEVICE_MANUFACTURER_LIST_DATA, response.getListDeviceManuFacturer());
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DEVICE_MANUFACTURER_DATA_SIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_VIEW_ACTION)
	public ModelAndView viewUserData(final HttpSession session,
			@RequestParam("deviceManufacturerId") Long deviceManufacturerId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("device-manufacturer-view");
		DeviceManufacturerListSearchResponse response = new DeviceManufacturerListSearchResponse();
		model.put("deviceManufacturerViewRequest", new UserProfileUpdateRequest());
		try {
			DeviceManufacturerSearchRequest deviceManufacturerSearchRequest = new DeviceManufacturerSearchRequest();
			deviceManufacturerSearchRequest.setPageIndex(1);
			deviceManufacturerSearchRequest.setDeviceManufacturerCode(deviceManufacturerId);
			response = deviceManufacturereManagementService
					.deviceManufacturerSearch(deviceManufacturerSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceManufacturerProfileUpdateRequest deviceManufacturerProfileUpdateRequest = populateUserUpdateData(
					response);
			model.put("deviceManufacturerViewRequest", deviceManufacturerProfileUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_SEARCH_PAGINATION)
	public ModelAndView deviceManufacturerDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		DeviceManufacturerSearchRequest request = (DeviceManufacturerSearchRequest) session
				.getAttribute(DEVICE_MANUFACTURER_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		model.put(DEVICE_MANUFACTURER_SEARCH, new DeviceManufacturerSearchRequest());
		DeviceManufacturerListSearchResponse response = null;
		try {
			request.setPageIndex(pageNumber);
			response = deviceManufacturereManagementService.deviceManufacturerSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DEVICE_MANUFACTURER_LIST_DATA, response.getListDeviceManuFacturer());
			model.put(DEVICE_MANUFACTURER_SARCH, request);
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			session.setAttribute(DEVICE_MANUFACTURER_DATA_SIZE, response.getTotalRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DEVICE_MANUFACTURER_DATA_SIZE);
		}
		return modelAndView;
	}

	protected DeviceManufacturerProfileUpdateRequest populateUserUpdateData(
			DeviceManufacturerListSearchResponse response) {
		DeviceManufacturerProfileUpdateRequest deviceManufacturerProfileUpdateRequest = new DeviceManufacturerProfileUpdateRequest();
		DeviceManufacturerSearchResponse deviceManufacturerSearchResponse = response
				.getListDeviceManuFacturer().get(0);
		deviceManufacturerProfileUpdateRequest.setDescription(deviceManufacturerSearchResponse.getDescription());
		deviceManufacturerProfileUpdateRequest.setDeviceTypeName(deviceManufacturerSearchResponse.getDeviceTypeName());
		deviceManufacturerProfileUpdateRequest.setDeviceTypeId(deviceManufacturerSearchResponse.getDeviceTypeId());
		deviceManufacturerProfileUpdateRequest.setDeviceManufacturerCode(deviceManufacturerSearchResponse.getDeviceManufacturerId());
		deviceManufacturerProfileUpdateRequest.setDeviceManufacturer(deviceManufacturerSearchResponse.getDeviceManufacturer());
		deviceManufacturerProfileUpdateRequest.setStatus(deviceManufacturerSearchResponse.getStatus());
		return deviceManufacturerProfileUpdateRequest;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_EDIT_ACTION)
	public ModelAndView editDeviceManufacturerData(final HttpSession session,
			@RequestParam("deviceManufacturerId") Long deviceManufacturerId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("device-manufacturer-update");
		DeviceManufacturerListSearchResponse response = new DeviceManufacturerListSearchResponse();
		try {
			DeviceManufacturerSearchRequest deviceManufacturerSearchRequest = new DeviceManufacturerSearchRequest();
			deviceManufacturerSearchRequest.setPageIndex(Constants.ONE);
			deviceManufacturerSearchRequest.setDeviceManufacturerCode(deviceManufacturerId);
			response = deviceManufacturereManagementService
					.deviceManufacturerSearch(deviceManufacturerSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceManufacturerProfileUpdateRequest deviceManufacturerProfileUpdateRequest = populateUserUpdateData(
					response);
			model.put(DEVICE_MANUFACTURER_EDIT_REQ, deviceManufacturerProfileUpdateRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MANUFACTURER_UPDATE)
	public ModelAndView updateDeviceData(HttpSession session, Map<String, Object> model,
			DeviceManufacturerProfileUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView("device-manufacturer-update");
		WebResponse response = null;
		model.put(DEVICE_MANUFACTURER_EDIT_REQ, request);
		try {
			response = deviceManufacturereManagementService.updateDeviceManufacturer(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.manufacturer.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DEVICE_MANUFACTURER_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_DEVICE_MANUFACTURER_STATUS)
	public ModelAndView updateDeviceManufacturerStatus(final HttpSession session,
			@RequestParam("deviceManufacturerId") Long deviceManufacturerId,
			@RequestParam("deviceManufacturerStatus") String status, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		model.put(DEVICE_TYPE_LIST_DATA, new DeviceTypeListViewRequest());
		DeviceManufacturerStatusUpdateRequest request = new DeviceManufacturerStatusUpdateRequest();
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setDeviceManufacturerCode(deviceManufacturerId);
			response = deviceManufacturereManagementService.updateDeviceManufacturereStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				model.put(Constants.SUCCESS, properties.getProperty("device.manufacturer.status.update.success"));
				return deviceManufacturerDataPagination(session,
						(Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
			}
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}
	@PostMapping(value = DOWNLOAD_DEVICE_MANUFACTURER_REPORT)
	public ModelAndView downloadDeviceManufacturerReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: DeviceManufacturerController:: downloadDeviceReport method");
		ModelAndView modelAndView = new ModelAndView(DEVICE_MANUFACTURER_SEARCH);
		DeviceManufacturerListSearchResponse deviceManufacturerResponse = null;
		try {
			DeviceManufacturerSearchRequest deviceManufacturerSearch = (DeviceManufacturerSearchRequest) session.getAttribute(DEVICE_MANUFACTURER_LIST_DATA);
			deviceManufacturerSearch.setPageIndex(downLoadPageNumber);
			Integer pageSize = deviceManufacturerSearch.getPageSize();
			if (downloadAllRecords) {
				deviceManufacturerSearch.setPageIndex(Constants.ONE);
				deviceManufacturerSearch.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			deviceManufacturerResponse = deviceManufacturereManagementService.deviceManufacturerSearch(deviceManufacturerSearch);
			List<DeviceManufacturerSearchResponse> reuestResponses = deviceManufacturerResponse.getListDeviceManuFacturer();
			if (!StringUtil.isNull(deviceManufacturerResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadDeviceManufacturerReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			deviceManufacturerSearch.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: DeviceManufacturerController:: downloadDeviceManufacturerReport method", e);
		}
		logger.info("Exit:: DeviceManufacturerController:: downloadDeviceManufacturerReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadDeviceManufacturerReport(List<DeviceManufacturerSearchResponse> deviceTypeSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DeviceManufacturer_");
		exportDetails.setHeaderMessageProperty("chatak.header.deviceManufacturer.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(deviceTypeSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("DeviceManufacturer.file.exportutil.deviceManufacturer", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceManufacturer.file.exportutil.deviceTypeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceManufacturer.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceManufacturer.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<DeviceManufacturerSearchResponse> deviceManufacturerResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DeviceManufacturerSearchResponse deviceManufacturerData : deviceManufacturerResponse) {

			Object[] rowData = {     deviceManufacturerData. getDeviceManufacturer() , deviceManufacturerData.getDeviceTypeName(), deviceManufacturerData.getDescription(),	
					deviceManufacturerData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}