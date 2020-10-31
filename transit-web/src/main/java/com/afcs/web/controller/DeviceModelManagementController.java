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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.DeviceManufacturerManagementService;
import com.afcs.web.service.DeviceModelManagementService;
import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceManuFacturerListViewResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchData;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceModelStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DeviceModelManagementController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceModelManagementController.class);

	@Autowired
	DeviceModelManagementService deviceModelManagementService;

	@Autowired
	DeviceManufacturerManagementService deviceManufacturereManagementService;

	@Autowired
	DeviceOnboardService deviceOnboardService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String DEVICE_MODEL_REGISTRATION = "device-model-registration";
	private static final String DEVICE_MODEL_REGISTRATION_REQUEST = "deviceModelRegistration";
	private static final String DEVICE_MODEL_DATASIZE = "deviceModelDataSize";
	private static final String DEVICE_MODEL_SEARCH = "device-model-search";
	private static final String DEVICE_MODEL_SEARCH_PAGINATION = "device-model-search-pagination";
	private static final String DEVICE_MODEL_VIEW_ACTION = "device-model-view-action";
	private static final String DEVICE_MODEL_VIEW_REQ = "deviceModelViewRequest";
	private static final String DEVICE_MODEL_VIEW = "device-model-view";
	private static final String FETCH_MANFUACTURER_FOR_DEVICE_TYPE = "fetchManufacturerForDeviceType";
	private static final String DEVICE_MODEL_EDIT_REQ = "deviceModelEditRequest";
	private static final String DEVICE_MODEL_EDIT_ACTION = "edit-device-model-action";
	private static final String DEVICE_MODEL_UPDATE = "device-model-edit";
	private static final String UPDATE_DEVICE_MODEL_STATUS = "update-device-model-status";
	private static final String DEVICE_MODEL_SEARCH_REQ = "deviceModelSearch";
	private static final String DEVICE_TYPE_LIST_DATA = "deviceTypeListData";
	private static final String DEVICE_MODEL_NAME = "&&deviceModelName";
	private static final String DEVICE_MODEL_LIST_DATA = "deviceModelListData";
	private static final String DOWNLOAD_DEVICE_MODEL_REPORT = "downloadDeviceModelReport";
	
	@GetMapping(value = DEVICE_MODEL_REGISTRATION)
	public ModelAndView deviceModelRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(DEVICE_MODEL_REGISTRATION);
		DeviceModelRequest deviceModelRequest = new DeviceModelRequest();
		model.put(DEVICE_MODEL_REGISTRATION_REQUEST, deviceModelRequest);
		return modelandview;
	}

	@GetMapping(value = FETCH_MANFUACTURER_FOR_DEVICE_TYPE)
	public @ResponseBody String getManufacturerForDeviceType(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String selectedDeviceTypeId = request.getParameter("selectedDeviceTypeId");
		String status = request.getParameter("status");
		DeviceManuFacturerListViewResponse response1 = null;
		DeviceManufacturerSearchData deviceManufacturerSearchData = new DeviceManufacturerSearchData();
		try {
			if (!StringUtil.isNullEmpty(selectedDeviceTypeId)) {
				deviceManufacturerSearchData.setDeviceTypeId(Long.valueOf(selectedDeviceTypeId));
				deviceManufacturerSearchData.setStatus(status);
				response1 = deviceManufacturereManagementService
						.geteEuipmentManufacturerListForEquipmetType(deviceManufacturerSearchData);
				return JsonUtil.convertObjectToJSON(response1);
			}

		} catch (Exception e) {
			logger.error("ERROR:: DashboardController:: getSubServiceProviderForSelectedServiceProvider method", e);
		}
		return null;
	}

	@PostMapping(value = DEVICE_MODEL_REGISTRATION)
	public ModelAndView deviceModelRegisterPost(DeviceModelRequest request, HttpSession session,
			Map<String, Object> model) {
		ModelAndView modelandview = new ModelAndView(DEVICE_MODEL_SEARCH);
		DeviceModelSearchRequest deviceModelSearchRequest = new DeviceModelSearchRequest();
		model.put(DEVICE_MODEL_SEARCH_REQ, deviceModelSearchRequest);
		DeviceModelResponse response = null;
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		try {
			response = deviceModelManagementService.deviceModelRegistration(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelandview;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.model.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelandview;
	}

	@GetMapping(value = DEVICE_MODEL_SEARCH)
	public ModelAndView deviceModelSearch(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_SEARCH);
		DeviceModelSearchRequest deviceModelSearchRequest = new DeviceModelSearchRequest();
		List<DeviceTypeSearchRequest> listDevice = deviceOnboardService.getDeviceTypeList().getListOfDeviceType();
		session.setAttribute(DEVICE_TYPE_LIST_DATA, listDevice);
		model.put(DEVICE_TYPE_LIST_DATA, listDevice);
		List<DeviceManufacturerSearchRequest> listDeviceManufacturer = deviceOnboardService.getDeviceManuf().getListOfManufacturer();
		session.setAttribute("deviceManufacturerListData", listDeviceManufacturer);
		model.put("deviceManufacturerListData", listDeviceManufacturer);
		deviceModelSearchRequest.setPageSize(10);
		deviceModelSearchRequest.setIndexPage(Constants.ONE);
		session.setAttribute(DEVICE_MODEL_LIST_DATA, deviceModelSearchRequest);
		model.put(DEVICE_MODEL_SEARCH_REQ, deviceModelSearchRequest);
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MODEL_SEARCH)
	public ModelAndView deviceModelSearchData(Map<String, Object> model, DeviceModelSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelTypeId) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_SEARCH);
		DeviceModelListSearchResponse response = null;
		model.put(DEVICE_MODEL_SEARCH_REQ, new DeviceModelSearchRequest());
		if (!StringUtil.isNullEmpty(cancelTypeId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return deviceModelSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		try {
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			request.setIndexPage(Constants.ONE);
			model.put(DEVICE_MODEL_SEARCH, new DeviceModelSearchRequest());
			response = deviceModelManagementService.searchDeviceModel(request);
			session.setAttribute(DEVICE_MODEL_LIST_DATA, request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DEVICE_MODEL_DATASIZE, response.getNoOfRecords());
			session.setAttribute(DEVICE_MODEL_SEARCH_REQ, request);
			model.put(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			model.put(DEVICE_MODEL_LIST_DATA, response.getListDeviceModelSearchResponse());
			model.put(DEVICE_MODEL_SEARCH_REQ, request);
			model.put(DEVICE_MODEL_DATASIZE, response.getNoOfRecords());
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DEVICE_MODEL_DATASIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MODEL_VIEW_ACTION)
	public ModelAndView viewDeviceModelData(final HttpSession session, @RequestParam("deviceId") String deviceId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_VIEW);
		DeviceModelListSearchResponse response = new DeviceModelListSearchResponse();
		model.put(DEVICE_MODEL_VIEW_REQ, new DeviceModelRequest());
		try {
			DeviceModelSearchRequest searchUserRequest = new DeviceModelSearchRequest();
			searchUserRequest.setIndexPage(1);
			searchUserRequest.setDeviceId(deviceId);
			response = deviceModelManagementService.searchDeviceModel(searchUserRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceModelRequest deviceModelRequest = populateDeviceModelData(response);
			model.put(DEVICE_MODEL_VIEW_REQ, deviceModelRequest);
		}
		return modelAndView;
	}

	private DeviceModelRequest populateDeviceModelData(DeviceModelListSearchResponse response) {
		DeviceModelRequest request = new DeviceModelRequest();
		DeviceModelSearchResponse deviceModel = response.getListDeviceModelSearchResponse().get(0);
		request.setDescription(deviceModel.getDescription());
		request.setDeviceId(deviceModel.getDeviceId());
		request.setDeviceIMEINumber(deviceModel.getDeviceIMEINumber());
		request.setDeviceManufacturer(deviceModel.getDeviceManufacturer());
		request.setDeviceModel(deviceModel.getDeviceModel());
		request.setDeviceTypeName(deviceModel.getDeviceTypeName());
		return request;
	}

	@PostMapping(value = DEVICE_MODEL_SEARCH_PAGINATION)
	public ModelAndView deviceModelSearchDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		DeviceModelSearchRequest request = (DeviceModelSearchRequest) session.getAttribute(DEVICE_MODEL_SEARCH_REQ);
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_SEARCH);
		model.put(DEVICE_MODEL_SEARCH_REQ, new DeviceModelSearchRequest());
		model.put(DEVICE_MODEL_SEARCH, new DeviceModelSearchRequest());
		DeviceModelListSearchResponse response = null;
		try {
			request.setIndexPage(pageNumber);
			response = deviceModelManagementService.searchDeviceModel(request);
		} catch (AFCSException e) {
			logger.error("Exception :: DeviceModelManagementController :: deviceModelSearchDataPagination : ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DEVICE_MODEL_LIST_DATA, response.getListDeviceModelSearchResponse());
			model.put(DEVICE_MODEL_SEARCH_REQ, request);
			modelAndView.addObject(DEVICE_TYPE_LIST_DATA, session.getAttribute(DEVICE_TYPE_LIST_DATA));
			session.setAttribute(DEVICE_MODEL_DATASIZE, response.getNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DEVICE_MODEL_DATASIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MODEL_EDIT_ACTION)
	public ModelAndView editDeviceModelData(final HttpSession session, @RequestParam("deviceId") String deviceModelId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_UPDATE);
		DeviceModelListSearchResponse response = new DeviceModelListSearchResponse();
		try {
			DeviceModelSearchRequest searchUserRequest = new DeviceModelSearchRequest();
			searchUserRequest.setIndexPage(Constants.ONE);
			searchUserRequest.setDeviceId(deviceModelId);
			response = deviceModelManagementService.searchDeviceModel(searchUserRequest);
		} catch (AFCSException e) {
			logger.error("Exception :: DeviceModelManagementController :: editDeviceModelData :  ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DeviceModelProfileUpdateRequest userData = populateUserUpdateData(response);
			model.put(DEVICE_MODEL_EDIT_REQ, userData);
		}
		return modelAndView;
	}

	@PostMapping(value = DEVICE_MODEL_UPDATE)
	public ModelAndView updateDeviceModelData(HttpSession session, Map<String, Object> model,
			DeviceModelProfileUpdateRequest request) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_UPDATE);
		WebResponse response = null;
		model.put(DEVICE_MODEL_EDIT_REQ, request);
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = deviceModelManagementService.updateDeviceModel(request);
		} catch (AFCSException e) {
			logger.error("Exception :: DeviceModelManagementController :: updateDeviceModelData :  ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("device.model.profile.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DEVICE_MODEL_EDIT_REQ, request);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_DEVICE_MODEL_STATUS)
	public ModelAndView updateDeviceModelStatus(final HttpSession session,

			@RequestParam("deviceId") Long deviceModelId, @RequestParam("status") String status,
			@RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_SEARCH);
		model.put(DEVICE_MODEL_SEARCH_REQ, new DeviceModelSearchRequest());
		model.put(DEVICE_TYPE_LIST_DATA, new DeviceModelRequest());
		DeviceModelStatusUpdateRequest request = new DeviceModelStatusUpdateRequest();
		DeviceModelListSearchResponse response = null;
		try {
			request.setStatus(status);
			request.setDeviceId(deviceModelId);
			request.setReason(reason);
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = deviceModelManagementService.updateDeviceModelStatus(request);
		} catch (AFCSException e) {
			logger.error("Exception :: DeviceModelManagementController :: updateDeviceModelStatus :  ", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("device.model.status.suspend.changed")
						.replace(DEVICE_MODEL_NAME, response.getDeviceModelName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("device.model.status.active.changed")
						.replace(DEVICE_MODEL_NAME, response.getDeviceModelName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("device.model.status.terminate.changed")
						.replace(DEVICE_MODEL_NAME, response.getDeviceModelName()));
			}

			return deviceModelSearchDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER),
					model);
		}
		
		return modelAndView;
	}

	private DeviceModelProfileUpdateRequest populateUserUpdateData(DeviceModelListSearchResponse response) {
		DeviceModelProfileUpdateRequest request = new DeviceModelProfileUpdateRequest();
		DeviceModelSearchResponse deviceModel = response.getListDeviceModelSearchResponse().get(0);
		request.setDeviceTypeName(deviceModel.getDeviceTypeName());
		request.setDeviceManufacturer(deviceModel.getDeviceManufacturer());
		request.setDeviceTypeId(deviceModel.getDeviceTypeId());
		request.setDeviceManufacturerCode(deviceModel.getDeviceManufacturerId());
		request.setDeviceModel(deviceModel.getDeviceModel());
		request.setDeviceId(deviceModel.getDeviceId());
		request.setDescription(deviceModel.getDescription());
		request.setStatus(deviceModel.getStatus());
		request.setDeviceIMEINumber(deviceModel.getDeviceIMEINumber());
		return request;
	}
	@PostMapping(value = DOWNLOAD_DEVICE_MODEL_REPORT)
	public ModelAndView downloadDeviceModelReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: DeviceModelController:: downloadDeviceReport method");
		ModelAndView modelAndView = new ModelAndView(DEVICE_MODEL_SEARCH);
		DeviceModelListSearchResponse deviceModelResponse = null;
		try {
			DeviceModelSearchRequest deviceModelSearchRequest = (DeviceModelSearchRequest) session.getAttribute(DEVICE_MODEL_LIST_DATA);
			deviceModelSearchRequest.setIndexPage(downLoadPageNumber);
			Integer pageSize = deviceModelSearchRequest.getPageSize();
			if (downloadAllRecords) {
				deviceModelSearchRequest.setIndexPage(Constants.ONE);
				deviceModelSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			deviceModelResponse = deviceModelManagementService.searchDeviceModel(deviceModelSearchRequest);
			List<DeviceModelSearchResponse> reuestResponses = deviceModelResponse.getListDeviceModelSearchResponse();
			if (!StringUtil.isNull(deviceModelResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadDeviceModelReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			deviceModelSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: DeviceModelController:: downloadDeviceModelReport method", e);
		}
		logger.info("Exit:: DeviceModelController:: downloadDeviceModelReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadDeviceModelReport(List<DeviceModelSearchResponse> deviceModelSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DeviceModel_");
		exportDetails.setHeaderMessageProperty("chatak.header.deviceModel.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(deviceModelSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("DeviceModel.file.exportutil.deviceType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceModel.file.exportutil.deviceManufacturerName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceModel.file.exportutil.deviceModelName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceModel.file.exportutil.deviceImeiNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceModel.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DeviceModel.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<DeviceModelSearchResponse> deviceModelResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (DeviceModelSearchResponse deviceModelData : deviceModelResponse) {

			Object[] rowData = {   deviceModelData.getDeviceTypeName() , deviceModelData. getDeviceManufacturer() , deviceModelData.getDeviceModel(), 
					deviceModelData.getDeviceIMEINumber()	, deviceModelData.getDescription() , deviceModelData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
}
