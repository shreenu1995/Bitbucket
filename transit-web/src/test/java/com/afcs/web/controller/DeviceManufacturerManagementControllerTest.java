package com.afcs.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.DeviceManufacturerManagementService;
import com.afcs.web.service.DeviceOnboardService;
import com.afcs.web.service.PtoManagementService;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerListSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DeviceManufacturerStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DeviceTypeSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

@RunWith(MockitoJUnitRunner.class)
public class DeviceManufacturerManagementControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(DeviceManufacturerManagementController.class);

	private static final String DEVICE_MANUFACTURER_REGISTRATION = "device-manufacturer-registration";
	private static final String DEVICE_MANUFACTURER_SEARCH = "device-manufacturer-search";
	private static final String DEVICE_MANUFACTURER_SEARCH_PAGINATION = "device-manufacturer-search-pagination";
	private static final String DEVICE_MANUFACTURER_SEARCH_REQUEST = "deviceManufacturerSearchRequest";
	private static final String DEVICE_MANUFACTURER_VIEW_ACTION = "device-manufacturer-view-action";
	private static final String DEVICE_MANUFACTURER_EDIT_ACTION = "device-manufacturer-edit-action";
	private static final String DEVICE_MANUFACTURER_UPDATE = "update-device-manufacturer";
	private static final String UPDATE_DEVICE_MANUFACTURER_STATUS = "update-device-manufacturer-status";
	private static final String DEVICE_TYPE_LIST_DATA = "deviceTypeListData";
	private static final String DEVICE_MANUFACTURER_LIST_DATA = "deviceManufacturerListData";
	private static final String DOWNLOAD_DEVICE_MANUFACTURER_REPORT = "downloadDeviceManufacturerReport";

	private MockMvc mockMvc;

	@InjectMocks
	DeviceManufacturerManagementController deviceManufacturerManagementController = new DeviceManufacturerManagementController();

	@Mock
	PtoManagementService ptoOperationService;

	@Mock
	DeviceManufacturerManagementService deviceManufacturereManagementService;

	@Mock
	DeviceOnboardService deviceOnboardService;

	@Mock
	Environment properties;

	@Mock
	private MessageSource messageSource;

	@Mock
	DeviceManufacturerRegistrationRequest deviceManufacturerRegistrationRequest;

	@Mock
	DeviceManufacturerRegistrationResponse deviceManufacturerRegistrationResponse;

	@Mock
	DeviceManufacturerSearchRequest deviceManufacturerSearchRequest;

	@Mock
	DeviceManufacturerListSearchResponse deviceManufacturerListSearchResponse;

	@Mock
	DeviceManufacturerSearchResponse deviceManufacturerSearchResponse;

	@Mock
	DeviceManufacturerProfileUpdateRequest deviceManufacturerProfileUpdateRequest;

	@Mock
	WebResponse webResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(deviceManufacturerManagementController).setViewResolvers(viewResolver)
				.build();
	}

	@Before
	public void setProperties() {
		properties.getProperty("device.manufacturer.created.successfully", "device manufacturer created successfully");
		properties.getProperty("device.manufacturer.update.success", "Device Manufacturer updated successfully.");

	}

	@Test
	public void testDepotManagementRegistrationGet() {
		try {
			mockMvc.perform(get("/" + DEVICE_MANUFACTURER_REGISTRATION))
					.andExpect(view().name(DEVICE_MANUFACTURER_REGISTRATION));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDepotManagementRegistrationGet method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerRegisterPost() {
		deviceManufacturerRegistrationResponse = new DeviceManufacturerRegistrationResponse();
		deviceManufacturerRegistrationResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerRegistration(Matchers.any(DeviceManufacturerRegistrationRequest.class)))
					.thenReturn(deviceManufacturerRegistrationResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_REGISTRATION).sessionAttr(Constants.USER_ID, "userId"))
					.andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerRegisterPost method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerSearchGet() {
		List<DeviceTypeSearchRequest> listDevice = new ArrayList<DeviceTypeSearchRequest>();
		DeviceTypeSearchRequest deviceTypeSearchRequest = new DeviceTypeSearchRequest();
		deviceTypeSearchRequest.setDeviceTypeName("Mobile");
		deviceTypeSearchRequest.setDeviceTypeId(123l);
		listDevice.add(deviceTypeSearchRequest);
		try {
			mockMvc.perform(get("/" + DEVICE_MANUFACTURER_SEARCH).sessionAttr(DEVICE_TYPE_LIST_DATA, listDevice))
					.andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerRegisterPost method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerSearchPost() {
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_SEARCH)
					.sessionAttr(DEVICE_MANUFACTURER_LIST_DATA, deviceManufacturerSearchRequest)
					.param("cancelManufacturerId", "23")).andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerSearchPost method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerSearchError() {
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("error");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_SEARCH)
					.sessionAttr(DEVICE_MANUFACTURER_LIST_DATA, deviceManufacturerSearchRequest)
					.param("cancelManufacturerId", "23")).andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerSearchError method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testViewUserData() {
		List<DeviceManufacturerSearchResponse> listDeviceManuFacturer = new ArrayList<DeviceManufacturerSearchResponse>();
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		deviceManufacturerSearchResponse = new DeviceManufacturerSearchResponse();
		deviceManufacturerSearchResponse.setDescription("description");
		listDeviceManuFacturer.add(deviceManufacturerSearchResponse);
		deviceManufacturerListSearchResponse.setListDeviceManuFacturer(listDeviceManuFacturer);
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_VIEW_ACTION).param("deviceManufacturerId", "23"))
					.andExpect(view().name("device-manufacturer-view"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testViewUserData method", e.getMessage(),
					e);
		}
	}

	@Test
	public void testViewUserDataException() {
		List<DeviceManufacturerSearchResponse> listDeviceManuFacturer = new ArrayList<DeviceManufacturerSearchResponse>();
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		deviceManufacturerSearchResponse = new DeviceManufacturerSearchResponse();
		deviceManufacturerSearchResponse.setDescription("description");
		listDeviceManuFacturer.add(deviceManufacturerSearchResponse);
		deviceManufacturerListSearchResponse.setListDeviceManuFacturer(listDeviceManuFacturer);
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenThrow(new AFCSException());
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_VIEW_ACTION).param("deviceManufacturerId", "23"))
					.andExpect(view().name("device-manufacturer-view"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testViewUserDataException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerDataPagination() {
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_SEARCH_PAGINATION)
					.sessionAttr(DEVICE_MANUFACTURER_SEARCH_REQUEST, deviceManufacturerSearchRequest)
					.param(Constants.PAGE_NUMBER, "10")).andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerDataPagination method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDeviceManufacturerDataPaginationException() {
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenThrow(new AFCSException());
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_SEARCH_PAGINATION)
					.sessionAttr(DEVICE_MANUFACTURER_SEARCH_REQUEST, deviceManufacturerSearchRequest)
					.param(Constants.PAGE_NUMBER, "10")).andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDeviceManufacturerDataPaginationException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testEditDeviceManufacturerData() {
		List<DeviceManufacturerSearchResponse> listDeviceManuFacturer = new ArrayList<DeviceManufacturerSearchResponse>();
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		deviceManufacturerSearchResponse = new DeviceManufacturerSearchResponse();
		deviceManufacturerSearchResponse.setDescription("description");
		listDeviceManuFacturer.add(deviceManufacturerSearchResponse);
		deviceManufacturerListSearchResponse.setListDeviceManuFacturer(listDeviceManuFacturer);
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_EDIT_ACTION)
					.sessionAttr(DEVICE_MANUFACTURER_SEARCH_REQUEST, deviceManufacturerSearchRequest)
					.param("deviceManufacturerId", "10")).andExpect(view().name("device-manufacturer-update"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testEditDeviceManufacturerData method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testEditDeviceManufacturerDataException() {
		deviceManufacturerListSearchResponse = new DeviceManufacturerListSearchResponse();
		deviceManufacturerListSearchResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenThrow(new AFCSException());
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_EDIT_ACTION)
					.sessionAttr(DEVICE_MANUFACTURER_SEARCH_REQUEST, deviceManufacturerSearchRequest)
					.param("deviceManufacturerId", "10")).andExpect(view().name("device-manufacturer-update"));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testEditDeviceManufacturerDataException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceData() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturer(Matchers.any(DeviceManufacturerProfileUpdateRequest.class)))
					.thenReturn(webResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_UPDATE))
					.andExpect(view().name("device-manufacturer-update"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceData method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceDataError() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("error");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturer(Matchers.any(DeviceManufacturerProfileUpdateRequest.class)))
					.thenReturn(webResponse);
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_UPDATE))
					.andExpect(view().name("device-manufacturer-update"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceDataError method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceDataException() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturer(Matchers.any(DeviceManufacturerProfileUpdateRequest.class)))
					.thenThrow(new AFCSException());
			mockMvc.perform(post("/" + DEVICE_MANUFACTURER_UPDATE))
					.andExpect(view().name("device-manufacturer-update"));
		} catch (Exception e) {
			logger.error("ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceDataException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceManufacturerStatus() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturereStatus(Matchers.any(DeviceManufacturerStatusUpdateRequest.class)))
					.thenReturn(webResponse);
			mockMvc.perform(post("/" + UPDATE_DEVICE_MANUFACTURER_STATUS).param("deviceManufacturerId", "22")
					.param("deviceManufacturerStatus", "deviceManufacturerStatus")
					.sessionAttr(Constants.USER_ID, "userId").sessionAttr(Constants.PAGE_NUMBER, 10))
					.andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceManufacturerStatus method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceManufacturerStatusError() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("error");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturereStatus(Matchers.any(DeviceManufacturerStatusUpdateRequest.class)))
					.thenReturn(webResponse);
			mockMvc.perform(post("/" + UPDATE_DEVICE_MANUFACTURER_STATUS).param("deviceManufacturerId", "22")
					.param("deviceManufacturerStatus", "deviceManufacturerStatus")
					.sessionAttr(Constants.USER_ID, "userId").sessionAttr(Constants.PAGE_NUMBER, 10))
					.andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceManufacturerStatusError method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDeviceManufacturerStatusException() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("0");
		try {
			Mockito.when(deviceManufacturereManagementService
					.updateDeviceManufacturereStatus(Matchers.any(DeviceManufacturerStatusUpdateRequest.class)))
					.thenThrow(new AFCSException());
			mockMvc.perform(post("/" + UPDATE_DEVICE_MANUFACTURER_STATUS).param("deviceManufacturerId", "22")
					.param("deviceManufacturerStatus", "deviceManufacturerStatus")
					.sessionAttr(Constants.USER_ID, "userId").sessionAttr(Constants.PAGE_NUMBER, 10))
					.andExpect(view().name(DEVICE_MANUFACTURER_SEARCH));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testUpdateDeviceManufacturerStatusException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDownloadDeviceManufacturerReport() {
		try {
			Mockito.when(deviceManufacturereManagementService
					.deviceManufacturerSearch(Matchers.any(DeviceManufacturerSearchRequest.class)))
					.thenReturn(deviceManufacturerListSearchResponse);
			mockMvc.perform(post("/" + DOWNLOAD_DEVICE_MANUFACTURER_REPORT).param("downLoadPageNumber", "22")
					.param("totalRecords", "100").param("downloadAllRecords", "true")
					.sessionAttr(DEVICE_MANUFACTURER_LIST_DATA, deviceManufacturerSearchRequest));
		} catch (Exception e) {
			logger.error(
					"ERROR:: DeviceManufacturerManagementControllerTest:: testDownloadDeviceManufacturerReport method",
					e.getMessage(), e);
		}
	}

}
