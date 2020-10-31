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
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.service.VehicleTypeManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class VehicleTypeManagementController {

	private static Logger logger = LoggerFactory.getLogger(VehicleTypeManagementController.class);

	@Autowired
	VehicleTypeManagementService vehicleTypeManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String VEHICLE_TYPE_SEARCH = "vehicle-type-search";
	private static final String VEHICLE_TYPE_SARCH = "vehicleTypeSearch";
	private static final String VEHICLE_TYPE_SEARCH_PAGINATION = "vehicle-type-search-pagination";
	private static final String VEHICLE_TYPE_SEARCH_REQUEST = "vehicleTypeSearchRequest";
	private static final String VEHICLE_TYPE_DATA = "vehicleTypeData";
	private static final String VEHICLE_TYPE_DATA_SIZE = "vehicleTypeDataSize";
	private static final String VEHICLE_TYPE_VIEW_ACTION = "vehicle-type-view-action";
	private static final String VEHICLE_TYPE_VIEW_REQ = "vehicleTypeViewRequest";
	private static final String VEHICLE_TYPE_REGISTRATION = "vehicle-type-registration";
	private static final String VEHICLE_TYPE_STATUS_UPDATE = "vehicle-type-status-update";
	private static final String VEHICLE_TYPE_UPDATE = "vehicle-type-update";
	private static final String VEHICLE_TYPE_EDIT_REQ = "vehicleTypeEditRequest";
	private static final String VEHICLE_TYPE_EDIT_ACTION = "vehicle-type-edit-action";
    private static final String DOWNLOAD_VEHICLE_TYPE_REPORT = "downloadVehicleTypeReport";
    
	@GetMapping(value = VEHICLE_TYPE_REGISTRATION)
	public ModelAndView vehicleTypeRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(VEHICLE_TYPE_REGISTRATION);
		VehicleTypeRegistrationRequest request = new VehicleTypeRegistrationRequest();
		model.put("vehicleTypeRequest", request);
		return modelandview;
	}

	@PostMapping(value = VEHICLE_TYPE_REGISTRATION)
	public ModelAndView vehicleTypeRegisterPost(Map<String, Object> model, VehicleTypeRegistrationRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelandview = new ModelAndView(VEHICLE_TYPE_SEARCH);
		VehicleTypeSearchRequest deviceTypeSearch = new VehicleTypeSearchRequest();
		model.put(VEHICLE_TYPE_SARCH, deviceTypeSearch);
		WebResponse response = vehicleTypeManagementService.saveVehicleTypeRegistration(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.type.create.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelandview;
	}

	@GetMapping(value = VEHICLE_TYPE_SEARCH)
	public ModelAndView vehicleTypeSearchGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_SEARCH);
		VehicleTypeSearchRequest vehicleTypeResponse = new VehicleTypeSearchRequest();
		vehicleTypeResponse.setPageSize(10);
		vehicleTypeResponse.setPageIndex(Constants.ONE);
		session.setAttribute(VEHICLE_TYPE_DATA, vehicleTypeResponse);
		model.put(VEHICLE_TYPE_SARCH, vehicleTypeResponse);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_SEARCH)
	public ModelAndView vehicleTypeSearchPost(Map<String, Object> model, VehicleTypeSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_SEARCH);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return vehicleTypeDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageIndex(Constants.ONE);
		model.put(VEHICLE_TYPE_SARCH, request);
		session.setAttribute(VEHICLE_TYPE_SEARCH_REQUEST, request);
		VehicleTypeSearchResponse response = vehicleTypeManagementService.vehicleTypeSearch(request);
		session.setAttribute(VEHICLE_TYPE_DATA, request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_TYPE_DATA, response.getListOfVehicleType());
			session.setAttribute(VEHICLE_TYPE_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(VEHICLE_TYPE_DATA_SIZE, response.getTotalNoOfRecords());
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, VEHICLE_TYPE_DATA_SIZE);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_SEARCH_PAGINATION)
	public ModelAndView vehicleTypeDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		VehicleTypeSearchRequest request = (VehicleTypeSearchRequest) session.getAttribute(VEHICLE_TYPE_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = vehicleTypeManagementService.vehicleTypeSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_TYPE_DATA, response.getListOfVehicleType());
			model.put(VEHICLE_TYPE_SARCH, request);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, VEHICLE_TYPE_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_VIEW_ACTION)
	public ModelAndView editUserData(final HttpSession session, @RequestParam("vehicleTypeId") Long vehicleTypeCode,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("vehicle-type-view");
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		model.put(VEHICLE_TYPE_VIEW_REQ, new VehicleTypeSearchRequest());
		try {
			VehicleTypeSearchRequest vehicleTypeSearchRequest = new VehicleTypeSearchRequest();
			vehicleTypeSearchRequest.setPageIndex(Constants.ONE);
			vehicleTypeSearchRequest.setVehicleTypeId(vehicleTypeCode);
			response = vehicleTypeManagementService.vehicleTypeSearch(vehicleTypeSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleTypeSearchRequest viewRequest = populateViewVehicleType(response);
			model.put(VEHICLE_TYPE_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_EDIT_ACTION)
	public ModelAndView editVehicleType(final HttpSession session,
			@RequestParam("vehicleTypeCode") Long vehicleTypeCode, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_UPDATE);
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		try {
			VehicleTypeSearchRequest vehicleTypeSearchRequest = new VehicleTypeSearchRequest();
			vehicleTypeSearchRequest.setPageIndex(Constants.ONE);
			vehicleTypeSearchRequest.setVehicleTypeId(vehicleTypeCode);
			response = vehicleTypeManagementService.vehicleTypeSearch(vehicleTypeSearchRequest);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleTypeSearchRequest vehicleTypeStatusUpdate = populateViewVehicleType(response);
			model.put(VEHICLE_TYPE_EDIT_REQ, vehicleTypeStatusUpdate);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_UPDATE)
	public ModelAndView updateVechicleData(HttpSession session, Map<String, Object> model,
			VehicleTypeStatusUpdate vehicleTypeStatusUpdate) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_UPDATE);
		WebResponse response = null;
		model.put(VEHICLE_TYPE_EDIT_REQ, vehicleTypeStatusUpdate);
		try {
			response = vehicleTypeManagementService.updateVehicleTypeProfile(vehicleTypeStatusUpdate);
		} catch (AFCSException e) {
			logger.error("VehicleTypeManagementController class :: updateVechicleData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.type.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(VEHICLE_TYPE_EDIT_REQ, vehicleTypeStatusUpdate);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_TYPE_STATUS_UPDATE)
	public ModelAndView updateVehicleTypeStatus(HttpSession session, Map<String, Object> model,
			@RequestParam("vehicleTypeId") long vehicleTypeId, @RequestParam("vehicleTypeStatus") String status) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_SEARCH);
		VehicleTypeStatusUpdate request = new VehicleTypeStatusUpdate();
		model.put(VEHICLE_TYPE_EDIT_REQ, request);
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setVehicleTypeId(vehicleTypeId);
			response = vehicleTypeManagementService.updateVehicleTypeStatus(request);
		} catch (AFCSException e) {
			logger.error("VehicleTypeManagementController class :: updateVehicleTypeStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.type.status.update.success"));
			return vehicleTypeDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}

		return modelAndView;
	}

	@PostMapping(value = DOWNLOAD_VEHICLE_TYPE_REPORT)
	public ModelAndView downloadVehicleTypeReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleController:: downloadRoleReport method");
		ModelAndView modelAndView = new ModelAndView(VEHICLE_TYPE_SEARCH);
		VehicleTypeSearchResponse orgResponse = null;
		try {
			VehicleTypeSearchRequest vehicleTypeResponse = (VehicleTypeSearchRequest) session.getAttribute(VEHICLE_TYPE_DATA);
			vehicleTypeResponse.setPageIndex(downLoadPageNumber);
			Integer pageSize = vehicleTypeResponse.getPageSize();
			if (downloadAllRecords) {
				vehicleTypeResponse.setPageIndex(Constants.ONE);
				vehicleTypeResponse.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			orgResponse = vehicleTypeManagementService.vehicleTypeSearch(vehicleTypeResponse);
			List<VehicleTypeSearchRequest > reuestResponses = orgResponse.getListOfVehicleType();
			if (!StringUtil.isNull(orgResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleTypeReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			vehicleTypeResponse.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
		}
		logger.info("Exit:: RoleController:: downloadRoleReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleTypeReport(List<VehicleTypeSearchRequest > orgResp,
			ExportDetails exportDetails) {
		exportDetails.setReportName("VehicleType_");
		exportDetails.setHeaderMessageProperty("chatak.header.vehicletypemanagement.messages");

		exportDetails.setHeaderList(getVehicleTypeHeaderList());
		exportDetails.setFileData(getVehicleTypeFileData(orgResp));
	}

	private List<String> getVehicleTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("vehicleTypeManagement.file.exportutil.vehicleType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("vehicleTypeManagement.file.exportutil.description", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("vehicleTypeManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				      
		};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getVehicleTypeFileData(List<VehicleTypeSearchRequest > orgResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (VehicleTypeSearchRequest  roleData : orgResponse) {

			Object[] rowData = { roleData.getVehicleTypeName(), roleData.getDescription(),
					roleData.getStatus() 

			};
			fileData.add(rowData);
		}

		return fileData;
	}
	protected VehicleTypeSearchRequest populateViewVehicleType(VehicleTypeSearchResponse response) {
		return response.getListOfVehicleType().get(0);
	}
}