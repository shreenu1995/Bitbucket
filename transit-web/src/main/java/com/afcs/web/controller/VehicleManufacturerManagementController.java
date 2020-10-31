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
import com.afcs.web.service.VehicleManufacturerManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerProfileUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleManufacturerStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class VehicleManufacturerManagementController {
	
	private static Logger logger = LoggerFactory.getLogger(VehicleManufacturerManagementController.class);

	@Autowired
	VehicleManufacturerManagementService vehicleManufacturerManagementService;

	@Autowired
	PtoManagementService ptoOperationService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String VEHICLE_MANUF_SEARCH = "vehicle-manuf-search";
	private static final String VEHICLE_MANUF_SARCH = "vehicleManufSearch";
	private static final String VEHICLE_MANUF_SEARCH_PAGINATION = "vehicle-manuf-search-pagination";
	private static final String VEHICLE_MANUF_SEARCH_REQUEST = "vehicleManufSearchRequest";
	private static final String VEHICLE_MANUF_DATA = "vehicleManufData";
	private static final String VEHICLE_MANUF_DATA_SIZE = "vehicleManufDataSize";
	private static final String VEHICLE_MANUF_VIEW_ACTION = "vehicle-manuf-view-action";
	private static final String VEHICLE_MANUF_VIEW_REQ = "vehicleManufViewRequest";
	private static final String VEHICLE_MANUF_REGISTRATION = "vehicle-manuf-registration";
	private static final String VEHICLE_MANUF_STATUS_UPDATE = "vehicle-manuf-status-update";
	private static final String VEHICLE_MANUF_EDIT_ACTION = "vehicle-manuf-edit-action";
	private static final String VEHICLE_MANUF_EDIT_REQUEST = "vehicleManufEditRequest";
	private static final String VEHICLE_MANUF_UPDATE = "vehicle-manuf-update";
	private static final String VEHICLE_TYPE_LIST = "vehicleTypeList";
	private static final String DOWNLOAD_VEHICLE_MANUF = "downloadVehicleManufmanagement";
		
	@GetMapping(value = VEHICLE_MANUF_REGISTRATION)
	public ModelAndView vehicleTypeRegisterGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelandview = new ModelAndView(VEHICLE_MANUF_REGISTRATION);
		List<VehicleTypeSearchRequest> listvehicle = vehicleManufacturerManagementService.getVehicleType().getListOfVehicleType();
		session.setAttribute(VEHICLE_TYPE_LIST, listvehicle);
		VehicleManufacturerRegistrationRequest request = new VehicleManufacturerRegistrationRequest();
		model.put(VEHICLE_TYPE_LIST, listvehicle);
		model.put("vehicleManufRequest", request);
		return modelandview;
	}

	@PostMapping(value = VEHICLE_MANUF_REGISTRATION)
	public ModelAndView vehicleManufRegisterPost(Map<String, Object> model,
			VehicleManufacturerRegistrationRequest request, HttpSession session) throws AFCSException {
		ModelAndView modelandview = new ModelAndView(VEHICLE_MANUF_SEARCH);
		VehicleManufacturerSearchRequest deviceTypeSearch = new VehicleManufacturerSearchRequest();
		model.put(VEHICLE_MANUF_SARCH, deviceTypeSearch);
		WebResponse response = vehicleManufacturerManagementService.saveVehicleManufacturerRegistration(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.manufacturer.create.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelandview;
	}

	@GetMapping(value = VEHICLE_MANUF_SEARCH)
	public ModelAndView vehicleTypeSearchGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_SEARCH);
		List<VehicleTypeSearchRequest> listvehicle = vehicleManufacturerManagementService.getVehicleType().getListOfVehicleType();
		session.setAttribute(VEHICLE_TYPE_LIST, listvehicle);
		model.put(VEHICLE_TYPE_LIST, listvehicle);
		VehicleManufacturerResponse vehicleManufacturerResponse = new VehicleManufacturerResponse();
		vehicleManufacturerResponse.setPageSize(10);
		vehicleManufacturerResponse.setPageIndex(Constants.ONE);
		session.setAttribute(VEHICLE_MANUF_DATA, vehicleManufacturerResponse);
		model.put(VEHICLE_MANUF_SARCH, vehicleManufacturerResponse);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MANUF_SEARCH)
	public ModelAndView vehicleTypeSearchPost(Map<String, Object> model, VehicleManufacturerSearchRequest request,
			HttpSession session, @RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_SEARCH);
		
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return vehicleTypeDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageIndex(Constants.ONE);
		model.put(VEHICLE_MANUF_SARCH, request);
		session.setAttribute(VEHICLE_MANUF_SEARCH_REQUEST, request);
		VehicleManufacturerSearchResponse response = vehicleManufacturerManagementService.vehicleManufSearch(request);
		session.setAttribute(VEHICLE_MANUF_DATA, request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_MANUF_DATA, response.getListOfVehicleManuf());
			session.setAttribute(VEHICLE_MANUF_DATA_SIZE, response.getTotalNoOfCount());
			model.put(VEHICLE_MANUF_DATA_SIZE, response.getTotalNoOfCount());
			model.put(VEHICLE_MANUF_SEARCH_REQUEST, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, VEHICLE_MANUF_DATA_SIZE);
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MANUF_SEARCH_PAGINATION)
	public ModelAndView vehicleTypeDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) {
		VehicleManufacturerSearchRequest request = (VehicleManufacturerSearchRequest) session
				.getAttribute(VEHICLE_MANUF_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		VehicleManufacturerSearchResponse response = new VehicleManufacturerSearchResponse();
		try {
			request.setPageIndex(pageNumber);
			response = vehicleManufacturerManagementService.vehicleManufSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(VEHICLE_MANUF_DATA, response.getListOfVehicleManuf());
			model.put(VEHICLE_MANUF_SARCH, request);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, VEHICLE_MANUF_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = VEHICLE_MANUF_VIEW_ACTION)
	public ModelAndView viewVehicleManufacturerData(final HttpSession session,
			@RequestParam("vehicleManufacturerId") int vehicleTypeCode, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("vehicle-manuf-view");
		VehicleManufacturerSearchResponse response = new VehicleManufacturerSearchResponse();
		model.put(VEHICLE_MANUF_VIEW_REQ, new VehicleManufacturerResponse());
		try {
			VehicleManufacturerSearchRequest request = new VehicleManufacturerSearchRequest();
			request.setPageIndex(Constants.ONE);
			request.setVehicleManufId(vehicleTypeCode);
			response = vehicleManufacturerManagementService.vehicleManufSearch(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleManufacturerResponse viewRequest = response.getListOfVehicleManuf().get(0);
			model.put(VEHICLE_MANUF_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}
	
	@PostMapping(value = VEHICLE_MANUF_STATUS_UPDATE)
	public ModelAndView vehicleManufactureStatuUpdate(HttpSession session, Map<String, Object> model,
			@RequestParam("vehicleManufId") int vehicleManufId,
			@RequestParam("vehicleManufStatus") String status) {
		
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_SEARCH);
		VehicleManufacturerStatusUpdate request = new VehicleManufacturerStatusUpdate();
		WebResponse response = null;
		try {
			request.setUserId(session.getAttribute(Constants.USER_ID).toString());
			request.setStatus(status);
			request.setVehicleManufacturerId(vehicleManufId);
			response = vehicleManufacturerManagementService.updateVehicleManufactureStatus(request);
		}catch(AFCSException e) {
			logger.error("VehicleManufacturerManagementController class :: vehicleManufactureStatuUpdate method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.manufacture.status.update.success"));
			return vehicleTypeDataPagination(session, (Integer)session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		return modelAndView;
	}
	
	@PostMapping(value = VEHICLE_MANUF_EDIT_ACTION)
	public ModelAndView editVehicleManufactureProfile(HttpSession session, Map<String, Object> model,
			@RequestParam("vehicleManufId") int vehicleManufId) {
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_UPDATE);
		VehicleManufacturerSearchRequest request = new VehicleManufacturerSearchRequest();
		VehicleManufacturerSearchResponse response = null;
		try {
			request.setVehicleManufId(vehicleManufId);
			request.setPageIndex(Constants.ONE);
			response = vehicleManufacturerManagementService.vehicleManufSearch(request);
		} catch(AFCSException e) {
			logger.error("VehicleManufacturerManagementController class :: updateVehicleManufactureProfile method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if(!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			VehicleManufacturerProfileUpdate vehicleManufacturerProfileUpdate = populateVehicleManufData(response);
			model.put(VEHICLE_MANUF_EDIT_REQUEST, vehicleManufacturerProfileUpdate);
		}
		return modelAndView;
	}
	
	@PostMapping(value = VEHICLE_MANUF_UPDATE)
	public ModelAndView updateVehicleManufactureProfile(HttpSession session, Map<String, Object> model,
			VehicleManufacturerProfileUpdate vehicleManufacturerProfileUpdate) {
		WebResponse response = null;
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_UPDATE);
		try {
			response = vehicleManufacturerManagementService.updateVehicleManufactureProfile(vehicleManufacturerProfileUpdate);
		} catch (AFCSException e) {
			logger.error("VehicleManufacturerManagementController class :: updateVehicleManufactureProfile method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("vehicle.manufacture.update.success"));
			model.put(VEHICLE_MANUF_EDIT_REQUEST, vehicleManufacturerProfileUpdate);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(VEHICLE_MANUF_EDIT_REQUEST, vehicleManufacturerProfileUpdate);
		}
		
		return modelAndView;
	}
	
	protected VehicleManufacturerProfileUpdate populateVehicleManufData(VehicleManufacturerSearchResponse response) {
		VehicleManufacturerProfileUpdate vehicleManufacturerProfileUpdateRequest = new VehicleManufacturerProfileUpdate();
		VehicleManufacturerResponse vehicleManufacturerResponse= response.getListOfVehicleManuf().get(0);
		vehicleManufacturerProfileUpdateRequest.setDescription(vehicleManufacturerResponse.getDescription());
		vehicleManufacturerProfileUpdateRequest.setVehicleManufacturerId(vehicleManufacturerResponse.getVehicleManufacturerId());
		vehicleManufacturerProfileUpdateRequest.setStatus(vehicleManufacturerResponse.getStatus());
		vehicleManufacturerProfileUpdateRequest.setVehicleManufacturerName(vehicleManufacturerResponse.getVehicleManufacturerName());
		vehicleManufacturerProfileUpdateRequest.setVehicleTypeId(vehicleManufacturerResponse.getVehicleTypeId());
		return vehicleManufacturerProfileUpdateRequest;
	}
	@PostMapping(value = DOWNLOAD_VEHICLE_MANUF)
	public ModelAndView downloadVehicleManufacturerReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: RoleController:: downloadRoleReport method");
		ModelAndView modelAndView = new ModelAndView(VEHICLE_MANUF_SEARCH);
		VehicleManufacturerSearchResponse orgResponse = null;
		try {
			VehicleManufacturerSearchRequest vehicleManufacturerResponse = (VehicleManufacturerSearchRequest) session.getAttribute(VEHICLE_MANUF_DATA);
			vehicleManufacturerResponse.setPageIndex(downLoadPageNumber);
			Integer pageSize = vehicleManufacturerResponse.getPageSize();
			if (downloadAllRecords) {
				vehicleManufacturerResponse.setPageIndex(Constants.ONE);
				vehicleManufacturerResponse.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			
			orgResponse = vehicleManufacturerManagementService.vehicleManufSearch(vehicleManufacturerResponse);
			List<VehicleManufacturerResponse > reuestResponses = orgResponse.getListOfVehicleManuf();
			if (!StringUtil.isNull(orgResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleManufacturerReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			vehicleManufacturerResponse.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: RoleController:: downloadRoleReport method", e);
		}
		logger.info("Exit:: RoleController:: downloadRoleReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleManufacturerReport(List<VehicleManufacturerResponse > orgResp,
			ExportDetails exportDetails) {
		exportDetails.setReportName("VehicleManufacturer_");
		exportDetails.setHeaderMessageProperty("chatak.header.vehicleManufactuere.messages");

		exportDetails.setHeaderList(getVehicleManufacturerHeaderList());
		exportDetails.setFileData(getVehicleManufacturerFileData(orgResp));
	}

	private List<String> getVehicleManufacturerHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("vehicleManufacturer.file.exportutil.vehicleTypeName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("vehicleManufacturer.file.exportutil.vehicleManufacturerName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("vehicleManufacturer.file.exportutil.vehicleDescription", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("vehicleManufacturer.file.exportutil.status", null,
						LocaleContextHolder.getLocale())      
		};
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getVehicleManufacturerFileData(List<VehicleManufacturerResponse > orgResponse) {
		List<Object[]> fileData = new ArrayList<>();

		for (VehicleManufacturerResponse  roleData : orgResponse) {

			Object[] rowData = { roleData.getVehicleType(), roleData.getVehicleManufacturerName(),
					roleData.getDescription(), roleData.getStatus()

			};
			fileData.add(rowData);
		}

		return fileData;
	}
	
}
