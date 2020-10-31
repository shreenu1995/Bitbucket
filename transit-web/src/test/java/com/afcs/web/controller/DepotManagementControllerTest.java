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
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.DepotManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DepotProfileUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotRegistrationResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.DepotSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.DepotStatusUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;

@RunWith(MockitoJUnitRunner.class)
public class DepotManagementControllerTest {

	private static Logger logger = LoggerFactory.getLogger(DepotManagementController.class);

	private static final String DEPOT_MGMT_REGISTRATION = "depot-registration";
	private static final String DEPOT_SEARCH = "depot-search";
	private static final String DEPOT_LIST_DATA = "depotListData";
	private static final String DEPOT_LIST_DATASIZE = "depotDataSize";
	private static final String DEPOT_SEARCH_REQUEST = "depotSearchRequest";
	private static final String DEPOT_SEARCH_PAGINATION = "depot-search-pagination";
	private static final String DEPOT_VIEW_ACTION = "view-depot-action";
	private static final String DEPOT_EDIT_ACTION = "edit-depot-action";
	private static final String DOWNLOAD_DEPOT_REPORT = "downloadDepotReport";

	private MockMvc mockMvc;

	@InjectMocks
	DepotManagementController depotManagementController = new DepotManagementController();

	@Mock
	DepotManagementService depotManagementService;

	@Mock
	PtoManagementService ptoManagementService;

	@Mock
	OrganizationManagementService organizationManagementService;

	@Mock
	PtoListRequest ptoListRequest;

	@Mock
	PtoListResponse ptoListResponse;

	@Mock
	AFCSException afcsException;

	@Mock
	DepotRegistrationRequest depotRegistrationRequest;

	@Mock
	DepotRegistrationResponse dpotRegistrationResponse;

	@Mock
	Environment properties;

	@Mock
	DepotSearchRequest depotSearchRequest;

	@Mock
	OrganizationSearchRequest organizationSearchRequest;

	@Mock
	OrganizationSearchResponse organizationSearchResponse;

	@Mock
	DepotSearchResponse depotSearchResponse;

	@Mock
	DepotProfileUpdateRequest depotProfileUpdateRequest;

	@Mock
	WebResponse webResponse;

	@Mock
	DepotStatusUpdateRequest depotStatusUpdateRequest;
	
	@Mock
	ExportDetails exportDetails;
	
	@Mock
	List<String> getHeaderList;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/pages/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(depotManagementController).setViewResolvers(viewResolver).build();
	}

	@Before
	public void setProperties() {
		properties.getProperty("depot.created.successfully", "Depot created successfully");
		properties.getProperty("depot.status.suspend.changed","\"&&depotName\"status has been changed to Active Successfully");
		properties.getProperty("depot.status.active.changed","\"&&depotName\" status has been changed to Terminated Successfully");
		properties.getProperty("depot.status.terminate.changed","\"&&depotName\"status has been changed to Active Successfully");
		properties.getProperty("chatak.header.dtomanagement.messages","Depot");	
		properties.getProperty("organizationList.file.exportutil.organizationName","Organization Name");
		properties.getProperty("ptoManagement.file.exportutil.ptoname","PTO Name");
		}
	

	@Test
	public void testDepotManagementRegistrationGet() {
		ptoListRequest.setUserType("SUPER_ADMIN");
		try {
			Mockito.when(
					ptoManagementService.getActivePtoList(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);

			mockMvc.perform(get("/" + DEPOT_MGMT_REGISTRATION).sessionAttr(Constants.USER_TYPE, "Admin")
					.sessionAttr(Constants.PTO_ID, "Admin").sessionAttr(Constants.PTO_MASTER_ID, "1"))
					.andExpect(view().name(DEPOT_MGMT_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotManagementRegistrationGet method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testGetUserTypeSuperAdmin() {
		try {
			mockMvc.perform(get("/" + DEPOT_MGMT_REGISTRATION).sessionAttr(Constants.USER_TYPE, "SUPER_ADMIN")
					.sessionAttr(Constants.PTO_ID, "SUPER_ADMIN")).andExpect(view().name(DEPOT_MGMT_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testGetUserTypeSuperAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testGetUserTypeOrgAdmin() {
		try {

			mockMvc.perform(get("/" + DEPOT_MGMT_REGISTRATION).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(Constants.PTO_ID, "ORG_ADMIN").sessionAttr(Constants.ORG_ID, "1"))
					.andExpect(view().name(DEPOT_MGMT_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testGetUserTypeOrgAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotManagementRegistrationGetException() throws Exception {
		ptoListRequest.setUserType("SUPER_ADMIN");
		try {
			mockMvc.perform(get("/" + DEPOT_MGMT_REGISTRATION).sessionAttr(Constants.USER_TYPE, "Admin")
					.sessionAttr(Constants.PTO_ID, "Admin")).andExpect(view().name(DEPOT_MGMT_REGISTRATION));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotManagementRegistrationGetException method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDepotManagementRegistrationPost() {
		depotRegistrationRequest.setDepotName("Name");
		dpotRegistrationResponse = new DepotRegistrationResponse();
		dpotRegistrationResponse.setStatusCode(Constants.SUCCESS_CODE);
		try {
			Mockito.when(
					depotManagementService.depotManagementRegistration(Matchers.any(DepotRegistrationRequest.class)))
					.thenReturn(dpotRegistrationResponse);

			mockMvc.perform(post("/" + DEPOT_MGMT_REGISTRATION).sessionAttr(Constants.USER_ID, "userId"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotManagementRegistrationPost method",
					e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearch() {
		ptoListRequest.setUserType("SUPER_ADMIN");
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoId("1234");
		pto.setOrgId(22l);
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		organizationSearchRequest.setUserType("ORG_ADMIN");
		ptoListRequest.setOrgId(22l);
		try {
			mockMvc.perform(get("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "userId")
					.sessionAttr(DEPOT_LIST_DATA, depotSearchRequest).sessionAttr(Constants.PTO_ID, "ptoId")
					.sessionAttr(Constants.PTO_LIST_DATA, ptoList).sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.sessionAttr(Constants.ORG_ID, 22l)).andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearch method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchAdminType() {
		ptoListRequest.setUserType("SUPER_ADMIN");
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoId("1234");
		pto.setOrgId(22l);
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		organizationSearchRequest.setUserType("ORG_ADMIN");
		ptoListRequest.setOrgId(22l);
		try {
			Mockito.when(ptoManagementService.getPtoList(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(get("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "SUPER_ADMIN")
					.sessionAttr(DEPOT_LIST_DATA, depotSearchRequest).sessionAttr(Constants.PTO_LIST_DATA, ptoList)
					.sessionAttr(Constants.PTO_MASTER_ID, 2l).sessionAttr(Constants.ORG_ID, 22l))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchAdminType method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchOrgType() {
		ptoListRequest.setUserType("SUPER_ADMIN");
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoId("1234");
		pto.setOrgId(22l);
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		organizationSearchRequest.setUserType("ORG_ADMIN");
		ptoListRequest.setOrgId(22l);
		try {
			Mockito.when(ptoManagementService.getPtoList(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(get("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(DEPOT_LIST_DATA, depotSearchRequest).sessionAttr(Constants.PTO_LIST_DATA, ptoList)
					.sessionAttr(Constants.PTO_MASTER_ID, 2l).sessionAttr(Constants.ORG_ID, 22l))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchOrgType method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchPost() {
		try {
			Mockito.when(ptoManagementService.getPtoByPtoId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "userId")
					.sessionAttr(Constants.PTO_MASTER_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("cancelTypeId", "7")).andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchPost method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchAdmin() {
		try {
			mockMvc.perform(post("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "SUPER_ADMIN")
					.sessionAttr(Constants.PTO_MASTER_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("cancelTypeId", "7")).andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchOrgAdmin() {
		try {
			mockMvc.perform(post("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(Constants.ORG_ID, 1l).sessionAttr(Constants.PTO_MASTER_ID, "ptoId")
					.sessionAttr(Constants.PTO_MASTER_ID, 2l).param("cancelTypeId", "7"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchOrgAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testDepotSearchCancelTypeID() {
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setStatusCode(Constants.SUCCESS_CODE);
		depotSearchResponse.setNoOfRecords(7);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
			.thenReturn(depotSearchResponse);
			mockMvc.perform(post("/" + DEPOT_SEARCH).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(Constants.ORG_ID, 1l).sessionAttr(Constants.PTO_MASTER_ID, "ptoId")
					.sessionAttr(DEPOT_LIST_DATASIZE, 7).sessionAttr(DEPOT_SEARCH_REQUEST, depotSearchRequest)
					.sessionAttr(Constants.PTO_MASTER_ID, 2l).param("cancelTypeId", "7"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchCancelTypeID method", e.getMessage(),
					e);
		}
	}

	@Test
	public void testDepotSearchDataPagination() {
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setPageIndex(1);
		depotSearchRequest.setDepotName("Admin");
		try {
			mockMvc.perform(post("/" + DEPOT_SEARCH_PAGINATION).sessionAttr(Constants.USER_TYPE, "userId")
					.param(Constants.PAGE_NUMBER, "1").param("pageNumber", "1")).andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDepotSearchDataPagination method", e.getMessage(),
					e);
		}
	}

	@Test
	public void testViewDepotData() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		depotSearchRequest.setPtoId(123l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoName("BMTC");
		pto.setStatus("Active");
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
					.thenReturn(depotSearchResponse);
			Mockito.when(ptoManagementService.getActivePtoListByOrganizationId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			Mockito.when(ptoManagementService.getPtoDataByPtoId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_VIEW_ACTION).sessionAttr(Constants.USER_TYPE, "SUPER_ADMIN")
					.sessionAttr(Constants.PTO_MASTER_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("depotId", "7")).andExpect(view().name("depot-view"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testViewDepotData method", e.getMessage(), e);
		}
	}

	@Test
	public void testViewDepotDataOrgAdmin() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
					.thenReturn(depotSearchResponse);
			Mockito.when(ptoManagementService.getActivePtoListByOrganizationId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_VIEW_ACTION).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(Constants.ORG_ID, 2l).sessionAttr(Constants.PTO_MASTER_ID, 2l).param("depotId", "7"))
					.andExpect(view().name("depot-view"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testViewDepotDataOrgAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testViewDepotDataPtoId() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoName("BMTC");
		pto.setStatus("Active");
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		try {
			Mockito.when(ptoManagementService.getPtoByPtoId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_VIEW_ACTION).sessionAttr(Constants.USER_TYPE, "userType")
					.sessionAttr(Constants.PTO_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("depotId", "7")).andExpect(view().name("depot-view"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testViewDepotDataPtoId method", e.getMessage(), e);
		}
	}

	@Test
	public void testEditDepotData() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		depotSearchRequest.setPtoId(123l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoName("BMTC");
		pto.setStatus("Active");
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
					.thenReturn(depotSearchResponse);
			Mockito.when(ptoManagementService.getActivePtoListByOrganizationId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_EDIT_ACTION).sessionAttr(Constants.USER_TYPE, "SUPER_ADMIN")
					.sessionAttr(Constants.PTO_MASTER_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("depotId", "7")).andExpect(view().name("depot-edit"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testEditDepotData method", e.getMessage(), e);
		}
	}

	@Test
	public void testEditDepotDataOrgAdmin() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		depotSearchRequest.setPtoId(123l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoName("BMTC");
		pto.setStatus("Active");
		pto.setPtoMasterId(2l);
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
					.thenReturn(depotSearchResponse);
			Mockito.when(ptoManagementService.getActivePtoListByOrganizationId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			Mockito.when(ptoManagementService.getPtoDataByPtoId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_EDIT_ACTION).sessionAttr(Constants.USER_TYPE, "ORG_ADMIN")
					.sessionAttr(Constants.ORG_ID, 2l).sessionAttr(Constants.PTO_MASTER_ID, 2l).param("depotId", "7"))
					.andExpect(view().name("depot-edit"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testEditDepotDataOrgAdmin method", e.getMessage(), e);
		}
	}

	@Test
	public void testEditDepotPtoId() {
		List<DepotSearchRequest> list = new ArrayList<DepotSearchRequest>();
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setOrganizationId(2l);
		depotSearchRequest.setPtoId(123l);
		list.add(depotSearchRequest);
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setDepotListResponse(list);
		List<PtoList> ptoList = new ArrayList<PtoList>();
		PtoList pto = new PtoList();
		pto.setPtoName("BMTC");
		pto.setStatus("Active");
		pto.setPtoMasterId(2l);
		ptoList.add(pto);
		ptoListResponse = new PtoListResponse();
		ptoListResponse.setPtoList(ptoList);
		try {
			Mockito.when(ptoManagementService.getPtoByPtoId(Matchers.any(PtoListRequest.class)))
					.thenReturn(ptoListResponse);
			mockMvc.perform(post("/" + DEPOT_EDIT_ACTION).sessionAttr(Constants.USER_TYPE, "userType")
					.sessionAttr(Constants.PTO_ID, "ptoId").sessionAttr(Constants.PTO_MASTER_ID, 2l)
					.param("depotId", "7")).andExpect(view().name("depot-edit"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testEditDepotPtoId method", e.getMessage(), e);
		}
	}

	@Test
	public void testupdateDepotData() {
		webResponse = new WebResponse();
		webResponse.setStatusCode("0");
		try {
			Mockito.when(depotManagementService.updateDepotProfile(Matchers.any(DepotProfileUpdateRequest.class)))
					.thenReturn(webResponse);
			mockMvc.perform(post("/" + "updateDepotProfile").sessionAttr(Constants.USER_ID, "userId"))
					.andExpect(view().name("depot-edit"));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testupdateDepotData method", e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDepotStatus() {
		depotStatusUpdateRequest = new DepotStatusUpdateRequest();
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setStatusCode("0");
		depotSearchResponse.setDepotName("&&depotName");

		try {
			Mockito.when(depotManagementService.updateDepotStatus(Matchers.any(DepotStatusUpdateRequest.class)))
					.thenReturn(depotSearchResponse);
			mockMvc.perform(post("/" + "update-depot-status").sessionAttr(Constants.PAGE_NUMBER, "5")
					.param("depotId", "7").param("status", "Active").param("reason", "reason"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testUpdateDepotStatus method", e.getMessage(), e);
		}
	}

	@Test
	public void testUpdateDepotStatusSuspend() {
		depotStatusUpdateRequest = new DepotStatusUpdateRequest();
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setStatusCode("0");
		depotSearchResponse.setDepotName("&&depotName");

		try {
			Mockito.when(depotManagementService.updateDepotStatus(Matchers.any(DepotStatusUpdateRequest.class)))
					.thenReturn(depotSearchResponse);
			mockMvc.perform(post("/" + "update-depot-status").sessionAttr(Constants.PAGE_NUMBER, "5")
					.param("depotId", "7").param("status", "Suspended").param("reason", "reason"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testUpdateDepotStatusSuspend method", e.getMessage(),
					e);
		}
	}

	@Test
	public void testUpdateDepotStatusTerminated() {
		depotStatusUpdateRequest = new DepotStatusUpdateRequest();
		depotSearchResponse = new DepotSearchResponse();
		depotSearchResponse.setStatusCode("0");
		depotSearchResponse.setDepotName("&&depotName");

		try {
			Mockito.when(depotManagementService.updateDepotStatus(Matchers.any(DepotStatusUpdateRequest.class)))
					.thenReturn(depotSearchResponse);
			mockMvc.perform(post("/" + "update-depot-status").sessionAttr(Constants.PAGE_NUMBER, "5")
					.param("depotId", "7").param("status", "Terminated").param("reason", "reason"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testUpdateDepotStatusTerminated method",
					e.getMessage(), e);
		}
	}
	
	@Test
	public void testDownloadRoleReport() {
		depotSearchRequest = new DepotSearchRequest();
		depotSearchRequest.setPageIndex(1);
		depotSearchRequest.setPageSize(5);
		exportDetails = new ExportDetails();
		exportDetails.setHeaderList(getHeaderList);
		try {
			Mockito.when(depotManagementService.searchDepot(Matchers.any(DepotSearchRequest.class)))
					.thenReturn(depotSearchResponse);
			mockMvc.perform(post("/" + DOWNLOAD_DEPOT_REPORT).sessionAttr(DEPOT_LIST_DATA, depotSearchRequest)
					.param("downLoadPageNumber", "22").param("totalRecords", "100").param("downloadAllRecords", "true"))
					.andExpect(view().name(DEPOT_SEARCH));
		} catch (Exception e) {
			logger.error("ERROR:: DepotManagementControllerTest:: testDownloadRoleReport method",
					e.getMessage(), e);
		}
	}

}
