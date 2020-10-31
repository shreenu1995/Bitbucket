package com.afcs.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.VehicleTypeManagementService;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.VehicleTypeStatusUpdate;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

@RunWith(MockitoJUnitRunner.class)
public class VehicleTypeManagementControllerTest {

	private static Logger logger = LoggerFactory.getLogger(VehicleTypeManagementController.class);

	@InjectMocks
	private VehicleTypeManagementController vehicleTypeManagementController = new VehicleTypeManagementController();

	private MockMvc mockMvc;

	@Mock
	Environment properties;

	@Mock
	VehicleTypeManagementService vehicleTypeManagementService;

	private static final String VEHICLE_TYPE_REGISTRATION = "vehicle-type-registration";
	private static final String VEHICLE_TYPE_SEARCH = "vehicle-type-search";
	private static final String VEHICLE_TYPE_VIEW_ACTION = "vehicle-type-view-action";
	private static final String VEHICLE_TYPE_EDIT_ACTION = "vehicle-type-edit-action";
	private static final String VEHICLE_TYPE_UPDATE = "vehicle-type-update";
	private static final String VEHICLE_TYPE_STATUS_UPDATE = "vehicle-type-status-update";
	private static final String DOWNLOAD_VEHICLE_TYPE_REPORT = "downloadVehicleTypeReport";

	@Before
	public void setUp() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/pages/");
		internalResourceViewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(vehicleTypeManagementController)
				.setViewResolvers(internalResourceViewResolver).build();
	}

	@Before
	public void setProperties() {
		properties.getProperty("vehicle.type.create.success", "Vehicle Type created successfully.");
		properties.getProperty("vehicle.type.update.success", "Vehicle Type updated successfully.");
		properties.getProperty("vehicle.type.status.update.success", "Vehicle Type status updated successfully.");
	}

	@Test
	public void testVehicleTypeRegisterGet() {
		try {
			mockMvc.perform(get("/" + VEHICLE_TYPE_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeRegisterGet method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testVehicleTypeRegisterPost() throws AFCSException {
		WebResponse response = new WebResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		Mockito.when(vehicleTypeManagementService
				.saveVehicleTypeRegistration(Matchers.any(VehicleTypeRegistrationRequest.class))).thenReturn(response);
		try {

			mockMvc.perform(post("/" + VEHICLE_TYPE_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeRegisterPost method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testVehicleTypeSearchGet() {
		try {
			mockMvc.perform(get("/" + VEHICLE_TYPE_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeSearchGet method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testVehicleTypeSearchPostIf() throws AFCSException {
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(response);
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_SEARCH).param("cancelTypeId", "CancelType"));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeSearchPostIf method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testVehicleTypeSearchPost() throws AFCSException {
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		VehicleTypeSearchRequest request = new VehicleTypeSearchRequest();
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(response);
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_SEARCH).param("cancelTypeId", "12").sessionAttr("pageNumber", 25)
					.sessionAttr("vehicleTypeSearchRequest", request));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeSearchPost method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testVehicleTypeSearchPostException() throws AFCSException {
		VehicleTypeSearchRequest request = new VehicleTypeSearchRequest();
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenThrow(new AFCSException());
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_SEARCH).param("cancelTypeId", "12").sessionAttr("pageNumber", 25)
					.sessionAttr("vehicleTypeSearchRequest", request));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testVehicleTypeSearchPostException method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testEditUserData() throws AFCSException {
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		VehicleTypeSearchRequest request = new VehicleTypeSearchRequest();
		List<VehicleTypeSearchRequest> listOfVehicleType = new ArrayList<>();
		listOfVehicleType.add(request);
		response.setListOfVehicleType(listOfVehicleType);
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(response);
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_VIEW_ACTION).param("vehicleTypeId", "12")
					.param("VehicleTypeSearchResponse", "response"));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testEditUserData method", e.getMessage(), e);
		}

	}

	@Test
	public void testEditUserDataException() throws AFCSException {
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenThrow(new AFCSException());
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_VIEW_ACTION).param("vehicleTypeId", "12"));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testEditUserDataException method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testEditVehicleType() throws AFCSException {
		VehicleTypeSearchResponse response = new VehicleTypeSearchResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		VehicleTypeSearchRequest request = new VehicleTypeSearchRequest();
		List<VehicleTypeSearchRequest> listOfVehicleType = new ArrayList<>();
		listOfVehicleType.add(request);
		response.setListOfVehicleType(listOfVehicleType);
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(response);
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_EDIT_ACTION).param("vehicleTypeCode", "12")
					.param("VehicleTypeSearchResponse", "response"));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testEditVehicleType method", e.getMessage(), e);
		}

	}

	@Test
	public void testEditVehicleTypeException() throws AFCSException {
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenThrow(new AFCSException());
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_EDIT_ACTION).param("vehicleTypeCode", "12"));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testEditVehicleTypeException method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testUpdateVechicleData() throws AFCSException {
		WebResponse response = new WebResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		try {
			Mockito.when(
					vehicleTypeManagementService.updateVehicleTypeProfile(Matchers.any(VehicleTypeStatusUpdate.class)))
					.thenReturn(response);
			mockMvc.perform(post("/" + VEHICLE_TYPE_UPDATE));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testUpdateVechicleData method", e.getMessage(),
					e);
		}

	}

	@Test
	public void testUpdateVechicleDataException() throws AFCSException {
		Mockito.when(vehicleTypeManagementService.updateVehicleTypeProfile(Matchers.any(VehicleTypeStatusUpdate.class)))
				.thenThrow(new AFCSException());
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_UPDATE));
		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testUpdateVechicleDataException method",
					e.getMessage(), e);
		}

	}

	@Test
	public void testUpdateVehicleTypeStatus() throws AFCSException {
		VehicleTypeStatusUpdate request = new VehicleTypeStatusUpdate();
		WebResponse response = new WebResponse();
		response.setStatusCode(Constants.SUCCESS_CODE);
		Mockito.when(vehicleTypeManagementService.updateVehicleTypeStatus(Matchers.any(VehicleTypeStatusUpdate.class)))
				.thenReturn(response);
		VehicleTypeSearchResponse vehicleTypeSearchResponse = new VehicleTypeSearchResponse();
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(vehicleTypeSearchResponse);
		VehicleTypeSearchRequest vehicleTypeSearchRequest = new VehicleTypeSearchRequest();
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_STATUS_UPDATE).param("vehicleTypeId", "32")
					.param("vehicleTypeStatus", "success").sessionAttr("userId", request).param("pageNumber", "1")
					.sessionAttr("pageNumber", 25).sessionAttr("vehicleTypeSearchRequest", vehicleTypeSearchRequest));

		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testUpdateVehicleTypeStatus method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateVehicleTypeStatusException() throws AFCSException {
		VehicleTypeStatusUpdate request = new VehicleTypeStatusUpdate();
		Mockito.when(vehicleTypeManagementService.updateVehicleTypeStatus(Matchers.any(VehicleTypeStatusUpdate.class)))
				.thenThrow(new AFCSException());
		try {
			mockMvc.perform(post("/" + VEHICLE_TYPE_STATUS_UPDATE).param("vehicleTypeId", "32")
					.param("vehicleTypeStatus", "success").sessionAttr("userId", request));

		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testUpdateVehicleTypeStatusException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDownloadVehicleTypeReport() throws AFCSException {
		VehicleTypeSearchRequest vehicleTypeResponse = new VehicleTypeSearchRequest();
		vehicleTypeResponse.getPageIndex();
		vehicleTypeResponse.setPageSize(12);
		vehicleTypeResponse.setStatus("success");
		List<VehicleTypeSearchRequest> listOfVehicleType = new ArrayList<>();
		listOfVehicleType.add(vehicleTypeResponse);
		VehicleTypeSearchResponse orgResponse = new VehicleTypeSearchResponse();
		orgResponse.setListOfVehicleType(listOfVehicleType);
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(orgResponse);
		try {
			mockMvc.perform(post("/" + DOWNLOAD_VEHICLE_TYPE_REPORT).param("downLoadPageNumber", "32")
					.param("totalRecords", "13").param("downloadAllRecords", "true").param("pageSize", "12")
					.sessionAttr("vehicleTypeData", vehicleTypeResponse).param("downloadType", "PDF"));

		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testDownloadVehicleTypeReport method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDownloadVehicleTypeXlsReport() throws AFCSException {
		VehicleTypeSearchRequest vehicleTypeResponse = new VehicleTypeSearchRequest();
		vehicleTypeResponse.getPageIndex();
		vehicleTypeResponse.setPageSize(12);
		vehicleTypeResponse.setStatus("success");
		vehicleTypeResponse.setDescription("transport");
		List<VehicleTypeSearchRequest> listOfVehicleType = new ArrayList<>();
		listOfVehicleType.add(vehicleTypeResponse);
		VehicleTypeSearchResponse orgResponse = new VehicleTypeSearchResponse();
		orgResponse.setListOfVehicleType(listOfVehicleType);
		Mockito.when(vehicleTypeManagementService.vehicleTypeSearch(Matchers.any(VehicleTypeSearchRequest.class)))
				.thenReturn(orgResponse);
		try {
			mockMvc.perform(post("/" + DOWNLOAD_VEHICLE_TYPE_REPORT).param("downLoadPageNumber", "32")
					.param("totalRecords", "13").param("downloadAllRecords", "true").param("pageSize", "12")
					.sessionAttr("vehicleTypeData", vehicleTypeResponse).param("downloadType", "XLS"));

		} catch (Exception e) {
			logger.error("ERROR:: VehicleTypeManagementControllerTest:: testDownloadVehicleTypeXlsReport method",
					e.getMessage(), e);
		}
	}
}
