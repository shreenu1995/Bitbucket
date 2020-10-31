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
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.VehicleManufacturerManagementService;
import com.afcs.web.service.VehicleModelManagementService;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.afcs.web.util.ExportUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelListResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleModelStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class VehicleModelManagementController {
	@Autowired
	VehicleManufacturerManagementService vehicleManufacturerManagementService;

	@Autowired
	VehicleModelManagementService vehicleModelManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleModelManagementController.class);

	private static final String VEHICLE_MODEL_SEARCH = "vehicle-model-search";
	private static final String VEHICLE_MODEL_SARCH = "vehicleModelSearch";
	private static final String VEHICLE_TYPE_LIST = "vehicleTypeList";
	private static final String VEHICLE_MODEL_REGISTRATION = "vehicle-model-registration";
	private static final String VEHICLE_MODEL_REGISTRATION_REQUEST = "vehicleModelRegistrationRequest";
	private static final String VEHICLE_MODEL_SEARCH_PAGINATION = "vehicle-model-search-pagination";
	private static final String VEHICLE_MODEL_DATA = "vehicleModelData";
	private static final String VEHICLE_MODEL_DATA_SIZE = "vehicleModelDataSize";
	private static final String VEHICLE_MODEL_VIEW_ACTION = "vehicle-model-view-action";
	private static final String VEHICLE_MODEL_VIEW_REQ = "vehicleModelViewRequest";
	private static final String FETCH_VEHICLE_MANUFACTURER_FOR_VEHICLE_TYPE = "fetchVehicleManufacturerForVehicleType";
	private static final String VEHICLE_MODEL_EDIT_ACTION="vehicle-model-edit-action";
	private static final String VEHICLE_MODEL_UPDATE="vehicle-model-update";
	private static final String VEHICLE_MODEL_EDIT_REQ="vehicleModelEditRequest";
	private static final String UPDATE_VEHICLE_MODEL_STATUS="update-vehicle-model-status";
	private static final String ENGINE_NUMBER_EXISTS_CHECK="engineNumberExistsCheck";
	private static final String CHASSIS_NUMBER_EXISTS_CHECK="chassisNumberExistsCheck";
	private static final String VEHICLE_MODEL_NAME = "&&vehicleModelName";
	private static final String VEHICLE_MANUFLIST = "vehicleManufList";
	private static final String DOWNLOAD_VEHICLE_MODEL_REPORT = "downloadVehicleModelReport";
		
	@GetMapping(value = VEHICLE_MODEL_REGISTRATION)
	public ModelAndView vehicleTypeRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandView = new ModelAndView(VEHICLE_MODEL_REGISTRATION);
		VehicleModelRegistrationRequest request = new VehicleModelRegistrationRequest();
		model.put(VEHICLE_MODEL_REGISTRATION_REQUEST, request);
		return modelandView;
	}

	@GetMapping(value = FETCH_VEHICLE_MANUFACTURER_FOR_VEHICLE_TYPE)
	public @ResponseBody String getVehicleModelForManufacturer(HttpServletRequest request, HttpServletResponse reponse,
			HttpSession session) throws AFCSException {
		String selectedVehicleTypeId = request.getParameter("selectedVehicleTypeId");
		String status = request.getParameter("status");
		VehicleModelListResponse response1 = null;
		VehicleManufacturerSearchRequest vehicleManufacturerSearchRequest = new VehicleManufacturerSearchRequest();
		try {
			if (!StringUtil.isNullEmpty(selectedVehicleTypeId)) {
				vehicleManufacturerSearchRequest.setVehicleTypeId(Long.valueOf(selectedVehicleTypeId));
				vehicleManufacturerSearchRequest.setStatus(status);
				response1 = vehicleModelManagementService
						.getVehicleModelForManufacturerListForVehicleType(vehicleManufacturerSearchRequest);
				return JsonUtil.convertObjectToJSON(response1);
			}
		} catch (Exception e) {
			logger.error("ERROR:: DashboardController:: getSubServiceProviderForSelectedServiceProvider method", e);
		}
		return null;
	}

	@PostMapping(value = VEHICLE_MODEL_REGISTRATION)
	public ModelAndView vehicleManufRegisterPost(Map<String, Object> model, VehicleModelRegistrationRequest request,
			HttpSession session) throws AFCSException {
		VehicleModelSearchRequest vehicleModelSearchRequest = new VehicleModelSearchRequest();
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
		model.put(VEHICLE_MODEL_SARCH, vehicleModelSearchRequest);
		WebResponse response=null;
		try{
			response = vehicleModelManagementService.saveVehicleModelRegistration(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.model.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = ENGINE_NUMBER_EXISTS_CHECK)
	public @ResponseBody String engineNumberExistsCheck(@RequestParam("name") final String name) {
		return vehicleModelManagementService.engineNumberExistsCheck(name);
	}
	
	@GetMapping(value = CHASSIS_NUMBER_EXISTS_CHECK)
	public @ResponseBody String chassisNumberExistsCheck(@RequestParam("name") final String name) {
		return vehicleModelManagementService.chassisNumberExistsCheck(name);
	}
	
	@GetMapping(value = VEHICLE_MODEL_SEARCH)
	public ModelAndView vehicleModelSearchGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
		List<VehicleTypeSearchRequest> listvehicle = vehicleManufacturerManagementService.getVehicleType().getListOfVehicleType();
		List<VehicleManufacturerResponse> listVehicleManufacturer = vehicleManufacturerManagementService.getVehicleManuf().getListOfManufacturer();
		session.setAttribute(VEHICLE_TYPE_LIST, listvehicle);
		model.put(VEHICLE_MANUFLIST, listVehicleManufacturer);
		model.put(VEHICLE_TYPE_LIST, session.getAttribute(VEHICLE_TYPE_LIST));
		VehicleModelSearchRequest vehicleModelSearchRequest = new VehicleModelSearchRequest();
		vehicleModelSearchRequest.setPageSize(10);
		vehicleModelSearchRequest.setPageIndex(Constants.ONE);
		session.setAttribute(VEHICLE_MODEL_DATA, vehicleModelSearchRequest);
		model.put(VEHICLE_MODEL_SARCH, vehicleModelSearchRequest);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MODEL_SEARCH)
	public ModelAndView vehicleTypeSearchPost(Map<String, Object> model, VehicleModelSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelTypeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
	    VehicleModelSearchResponse response=null;
		if (!StringUtil.isNullEmpty(cancelTypeId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return vehicleModelDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageIndex(Constants.ONE);
		model.put(VEHICLE_MODEL_SARCH, new VehicleModelSearchRequest());
		response = vehicleModelManagementService.vehicleModelSearch(request);
		
		List<VehicleManufacturerResponse> listVehicleManufacturer = vehicleManufacturerManagementService.getVehicleManuf()
				.getListOfManufacturer();
		session.setAttribute(VEHICLE_MANUFLIST, listVehicleManufacturer);
		model.put(VEHICLE_MANUFLIST, listVehicleManufacturer);
		
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(VEHICLE_MODEL_DATA_SIZE, response.getTotalNoOfRecords());
			session.setAttribute(VEHICLE_MODEL_SARCH, request);
			model.put(VEHICLE_TYPE_LIST, session.getAttribute(VEHICLE_TYPE_LIST));
			model.put(VEHICLE_MODEL_SARCH, request);
			model.put(VEHICLE_MODEL_DATA, response.getListOfVehicleModel());
			modelAndView.addObject(VEHICLE_MODEL_DATA, response.getListOfVehicleModel());
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, VEHICLE_MODEL_DATA_SIZE);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		session.setAttribute(VEHICLE_MODEL_DATA, request);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MODEL_SEARCH_PAGINATION)
	public ModelAndView vehicleModelDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		VehicleModelSearchRequest request = (VehicleModelSearchRequest) session
				.getAttribute(VEHICLE_MODEL_SARCH);
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		VehicleModelSearchResponse response = null;
		try {
			request.setPageIndex(pageNumber);
			response = vehicleModelManagementService.vehicleModelSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_MODEL_DATA, response.getListOfVehicleModel());
			model.put(VEHICLE_MODEL_SARCH, request);
			modelAndView.addObject(VEHICLE_TYPE_LIST, session.getAttribute(VEHICLE_TYPE_LIST));
			session.setAttribute(VEHICLE_MODEL_DATA_SIZE, response.getTotalNoOfRecords());
			PaginationUtil.performPagination(modelAndView, session, pageNumber, VEHICLE_MODEL_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MODEL_VIEW_ACTION)
	public ModelAndView viewVehicleModelData(final HttpSession session,
			@RequestParam("vehicleModelId") String vehicleTypeCode, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("vehicle-model-view");
		VehicleModelSearchResponse response = null;
		model.put(VEHICLE_MODEL_VIEW_REQ, new VehicleModelRegistrationRequest());
		 VehicleModelSearchRequest request = new VehicleModelSearchRequest();
		try {
           
			request.setPageIndex(1);
			request.setVehicleModelId(vehicleTypeCode);
			response = vehicleModelManagementService.vehicleModelSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleModelResponse viewRequest  = vehicleModelManagementService.vehicleModelSearch(request).getListOfVehicleModel().get(0);
			model.put(VEHICLE_MODEL_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MODEL_EDIT_ACTION)
	public ModelAndView editVehicleModelData(final HttpSession session,
			@RequestParam("vehicleModelId") String vehicleModelId, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_UPDATE);
		VehicleModelSearchResponse response = new VehicleModelSearchResponse();
		try {
			VehicleModelSearchRequest request = new VehicleModelSearchRequest();
			request.setPageIndex(1);
			request.setVehicleModelId(vehicleModelId);
			response = vehicleModelManagementService.vehicleModelSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleModelProfileUpdate vehicleModelProfileUpdateData = populateUpdateVehicleModel(response);
		model.put(VEHICLE_MODEL_EDIT_REQ, vehicleModelProfileUpdateData);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MODEL_UPDATE)
	public ModelAndView updateVehicleModelData(HttpSession session, Map<String, Object> model,
			VehicleModelProfileUpdate request) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_UPDATE);
		WebResponse response = null;
		model.put(VEHICLE_MODEL_EDIT_REQ, request);
		try {		
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			response = vehicleModelManagementService.updateVehicleModel(request);
		} catch (AFCSException e) {
			logger.error("Exception :: VehicleModelManagementController :: updateVehicleModelData :  ",
					e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.model.profile.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(VEHICLE_MODEL_EDIT_REQ, request);
		}
		return modelAndView;
	}
	
	@PostMapping(value = UPDATE_VEHICLE_MODEL_STATUS)
	public ModelAndView updateDeviceModelStatus(final HttpSession session,
			@RequestParam("vehicleModelId") long vehicleModelId, @RequestParam("status") String status,
			@RequestParam("reason") String reason, Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
		model.put(VEHICLE_MODEL_SARCH, new VehicleModelSearchRequest());
		model.put(VEHICLE_TYPE_LIST, new VehicleModelRegistrationRequest());
		VehicleModelStatusUpdate request = new VehicleModelStatusUpdate();
		VehicleModelSearchResponse response = null;
		request.setStatus(status);
		request.setVehicleModelId(vehicleModelId);
		request.setReason(reason);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		response = vehicleModelManagementService.updateVehicleModelStatus(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			
			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.model.status.suspend.changed")
						.replace(VEHICLE_MODEL_NAME, response.getVehicleModelName()));
			} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.model.status.active.changed")
						.replace(VEHICLE_MODEL_NAME, response.getVehicleModelName()));
			} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
				model.put(Constants.SUCCESS, properties.getProperty("vehicle.model.status.terminate.changed")
						.replace(VEHICLE_MODEL_NAME, response.getVehicleModelName()));
			}
			
			return vehicleModelDataPagination(session,
					(Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		return modelAndView;
	}
	
	private VehicleModelProfileUpdate populateUpdateVehicleModel(VehicleModelSearchResponse vehicleModelSearchResponse) {
		VehicleModelProfileUpdate request = new VehicleModelProfileUpdate();
		VehicleModelResponse response=vehicleModelSearchResponse.getListOfVehicleModel().get(0);
		request.setDescription(response.getDescription());
		request.setVehicleChassisNumber(response.getVehicleChassisNumber());
		request.setVehicleEngineNumber(response.getVehicleEngineNumber());
		request.setVehicleManufacturerName(response.getVehicleManufacturerName());
		request.setVehicleModelId(response.getVehicleModelId());
		request.setVehicleModelName(response.getVehicleModelName());
		request.setVehicleRegistrationNumber(response.getVehicleRegistrationNumber());
		request.setVehicleManufacturerId(response.getVehicleManufacturerId());
		request.setVehicleTypeId(response.getVehicleTypeId());
		request.setVehicleType(response.getVehicleType());
		return request;
	}
	@PostMapping(value = DOWNLOAD_VEHICLE_MODEL_REPORT)
	public ModelAndView downloadVehicleModelReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: VehiclewModelController:: downloadVehicleReport method");
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MODEL_SEARCH);
		VehicleModelSearchResponse vehicleModelSearchResponse = null;
		try {
			VehicleModelSearchRequest deviceVehicleModelRequest = (VehicleModelSearchRequest) session.getAttribute(VEHICLE_MODEL_DATA);
			deviceVehicleModelRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = deviceVehicleModelRequest.getPageSize();
			if (downloadAllRecords) {
				deviceVehicleModelRequest.setPageIndex(Constants.ONE);
				deviceVehicleModelRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			vehicleModelSearchResponse = vehicleModelManagementService.vehicleModelSearch(deviceVehicleModelRequest);
			List<VehicleModelResponse> reuestResponses = vehicleModelSearchResponse.getListOfVehicleModel();
			if (!StringUtil.isNull(vehicleModelSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleModelReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			deviceVehicleModelRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: VehicleModelController:: downloadVehicleModelReport method", e);
		}
		logger.info("Exit:: VehicleModelController:: downloadVehicleModelReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleModelReport(List<VehicleModelResponse> vehicleModelSearchRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("VehicleModel_");
		exportDetails.setHeaderMessageProperty("chatak.header.vehicleModel.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(vehicleModelSearchRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleTypeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleManufacturerName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleModelName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleRegistrationNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleEngineNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.vehicleChassisNumber", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("VehicleModel.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<VehicleModelResponse> vehicleModelResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (VehicleModelResponse vehicleModelData : vehicleModelResponse) {

			Object[] rowData = { 
					vehicleModelData.getVehicleType(), vehicleModelData.getVehicleManufacturerName() , vehicleModelData.getVehicleModelName() ,
					vehicleModelData.getVehicleRegistrationNumber()  ,  vehicleModelData.getVehicleEngineNumber() , vehicleModelData.getVehicleChassisNumber() ,
					vehicleModelData.getStatus() ,
			};
			fileData.add(rowData);
		}

		return fileData;
	}
}
