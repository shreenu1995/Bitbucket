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
import com.afcs.web.service.DiscountManagementService;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;

@Controller
public class DiscountManagementController {

	private static Logger logger = LoggerFactory.getLogger(DiscountManagementController.class);

	@Autowired
	DiscountManagementService discountManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;

	private static final String DISCOUNT_REGISTRATION = "discount-registration";
	private static final String DISCOUNT_REQUEST = "discountRegistration";
	private static final String DISCOUNT_SEARCH = "discount-search";
	private static final String DISCOUNT_SEARCH_PAGINATION = "discount-search-pagination";
	private static final String DISCOUNT_DATA_SIZE = "discountDataSize";
	private static final String DISCOUNT_SARCH = "discountSearch";
	private static final String DISCOUNT_SEARCH_REQUEST = "discountSearchRequest";
	private static final String DISCOUNT_VIEW_ACTION = "discount-view-action";
	private static final String UPDATE_DISCOUNT_STATUS = "update-discount-status";
	private static final String DISCOUNT_EDIT_ACTION = "discount-edit-action";
	private static final String DISCOUNT_UPDATE = "discount-edit";
	private static final String DISCOUNT_VIEW_REQUEST = "discountViewRequest";
	private static final String DISCOUNT_NAME = "&&discountName";
	private static final String DISCOUNT_EDIT_REQUEST = "discountEditRequest";
	private static final String DISCOUNT_LIST_DATA = "discountListData";
	private static final String DOWNLOAD_DISCOUNT_REPORT = "downloadDiscountReport";
	
		
	@GetMapping(value = DISCOUNT_REGISTRATION)
			public ModelAndView discountRegistrationGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandview = new ModelAndView(DISCOUNT_REGISTRATION);
		DiscountRequest discountRequest = new DiscountRequest();
		model.put(DISCOUNT_REQUEST, discountRequest);
		return modelandview;
	}

	@PostMapping(value = DISCOUNT_REGISTRATION)
	public ModelAndView discountRegistrationPost(Map<String, Object> model, DiscountRequest request,
			HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		model.put(DISCOUNT_SARCH, new DiscountRequest());
		DiscountResponse response = discountManagementService.discountRegistration(request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DISCOUNT_REQUEST, new DiscountRequest());
			model.put(Constants.SUCCESS, properties.getProperty("discount.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = DISCOUNT_SEARCH)
	public ModelAndView discountSearchGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		DiscountRequest discountRequest = new DiscountRequest();
		model.put(DISCOUNT_SARCH, discountRequest);
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		organizationSearchRequest.setPageIndex(Constants.ONE);
		organizationSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("organizationSearch", organizationSearchRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		PtoListResponse ptoListResponse;
		try {
			discountRequest.setPageSize(10);
			discountRequest.setPageIndex(Constants.ONE);
			session.setAttribute(DISCOUNT_LIST_DATA, discountRequest);
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
			logger.error("ERROR :: DiscountManagementController class :: discountSearchGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
			model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		try {
			if (organizationSearchRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else if (organizationSearchRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				organizationSearchRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(organizationSearchRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					organizationSearchRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(organizationSearchRequest);
				}
			}
		} catch (AFCSException e) {
			logger.error("ERROR :: DiscountManagementController class :: discountSearchGet method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = DISCOUNT_SEARCH)
	public ModelAndView discountSearchPost(Map<String, Object> model, DiscountRequest request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelTypeId) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		DiscountListResponse response = null;
		if (!StringUtil.isNullEmpty(cancelTypeId) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return discountDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		request.setPageIndex(Constants.ONE);
		request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			response = discountManagementService.discountSearch(request);
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
			response = discountManagementService.discountSearch(request);
		} else {
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
			PtoList pto = new PtoList();
			if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
				pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
				request.setPtoName(pto.getPtoName());
			}
			request.setPageIndex(Constants.ONE);
			request.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			response = discountManagementService.discountSearch(request);

		}
		session.setAttribute(DISCOUNT_LIST_DATA, request);
		model.put(DISCOUNT_SARCH, new DiscountRequest());
		session.setAttribute(DISCOUNT_SEARCH_REQUEST, request);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			session.setAttribute(DISCOUNT_DATA_SIZE, response.getTotalRecords());
			session.setAttribute(DISCOUNT_SARCH, request);
			model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
			model.put(DISCOUNT_LIST_DATA, response.getListOfDiscount());
			model.put(DISCOUNT_SARCH, request);
			modelAndView.addObject(DISCOUNT_SARCH, request);
			model.put(DISCOUNT_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, Constants.ONE, DISCOUNT_DATA_SIZE);
		}
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, DiscountRequest request, HttpSession session,
			PtoListRequest ptoListRequest) throws AFCSException {
		if (!StringUtil.isNull(request.getOrganizationId())) {
			ptoListRequest.setOrgId(request.getOrganizationId());
			if (!StringUtil.isNullEmpty(session.getAttribute(Constants.USER_TYPE).toString())
					&& !session.getAttribute(Constants.USER_TYPE).toString()
							.equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
				PtoListResponse ptoListResponse = ptoManagementService
						.getPtoListByOrganizationIdAndUserId(ptoListRequest);
				if (!StringUtil.isNull(ptoListResponse)) {
					model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
				}
			}
		}
	}

	@PostMapping(value = DISCOUNT_SEARCH_PAGINATION)
	public ModelAndView discountDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		DiscountRequest request = (DiscountRequest) session.getAttribute(DISCOUNT_SEARCH_REQUEST);
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		model.put(DISCOUNT_SARCH, new DiscountRequest());
		DiscountListResponse response = new DiscountListResponse();
		try {
			request.setPageIndex(pageNumber);
			response = discountManagementService.discountSearch(request);
		} catch (AFCSException e) {
			logger.error("DiscountManagementController class :: discountDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(DISCOUNT_LIST_DATA, response.getListOfDiscount());
			model.put(DISCOUNT_SARCH, request);
			session.setAttribute(DISCOUNT_DATA_SIZE, response.getTotalRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, DISCOUNT_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = DISCOUNT_VIEW_ACTION)
	public ModelAndView viewDiscountData(final HttpSession session, @RequestParam("discountId") Long discountId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("discount-view");
		DiscountListResponse response = new DiscountListResponse();
		model.put(DISCOUNT_VIEW_REQUEST, new DiscountRequest());
		try {
			DiscountRequest request = new DiscountRequest();
			request.setPageIndex(1);
			request.setDiscountId(discountId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = discountManagementService.discountSearch(request);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = discountManagementService.discountSearch(request);
			} else {
				PtoListRequest ptoListRequest = new PtoListRequest();
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = discountManagementService.discountSearch(request);

			}
		} catch (AFCSException e) {
			logger.error("DiscountManagementController class :: viewDiscountData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DiscountRequest discountRequest = response.getListOfDiscount().get(0);
			model.put(DISCOUNT_VIEW_REQUEST, discountRequest);
		}
		return modelAndView;
	}

	protected DiscountRequest populateDiscountUpdateData(DiscountListResponse response) {
		DiscountRequest discountRequest = new DiscountRequest();
		DiscountRequest discountResponse = response.getListOfDiscount().get(0);
		discountRequest.setOrganizationId(discountResponse.getOrganizationId());
		discountRequest.setPtoId(discountResponse.getPtoId());
		discountRequest.setDiscountId(discountResponse.getDiscountId());
		discountRequest.setDiscountType(discountResponse.getDiscountType());
		discountRequest.setDiscountName(discountResponse.getDiscountName());
		discountRequest.setDiscount(discountResponse.getDiscount());
		discountRequest.setRouteStageStationDifference(discountResponse.getRouteStageStationDifference());
		return discountRequest;
	}

	@PostMapping(value = UPDATE_DISCOUNT_STATUS)
	public ModelAndView updateDiscountStatus(final HttpSession session, @RequestParam("discountId") Long discountId,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		model.put(DISCOUNT_SEARCH, new ProductSearchRequest());
		DiscountStatusChangeRequest request = new DiscountStatusChangeRequest();
		DiscountListResponse response = null;
		try {
			request.setStatus(status);
			request.setDiscountId(discountId);
			request.setReason(reason);
			response = discountManagementService.updateDiscountStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
				
				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("discount.status.suspend.changed")
							.replace(DISCOUNT_NAME, response.getDiscountName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("discount.status.active.changed")
							.replace(DISCOUNT_NAME, response.getDiscountName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("discount.status.terminate.changed")
							.replace(DISCOUNT_NAME, response.getDiscountName()));
				}
				
				return discountDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);

			}
		} catch (AFCSException e) {
			logger.error("ProductManagementController class :: updateProductStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = DISCOUNT_EDIT_ACTION)
	public ModelAndView editDiscountData(final HttpSession session, @RequestParam("discountId") Long discountId,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_UPDATE);
		DiscountListResponse response = new DiscountListResponse();
		model.put(DISCOUNT_EDIT_REQUEST, new DiscountRequest());
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = new PtoListResponse();
		try {
			DiscountRequest request = new DiscountRequest();
			request.setPageIndex(1);
			request.setDiscountId(discountId);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = discountManagementService.discountSearch(request);
				ptoListRequest.setOrgId(response.getListOfDiscount().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = discountManagementService.discountSearch(request);
				ptoListRequest.setOrgId(response.getListOfDiscount().get(0).getOrganizationId());
		        ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
		        model.put(Constants.PTO_LIST_DATA, ptoListResponse.getPtoList());
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					pto.setPtoName(ptoList.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				request.setPageIndex(Constants.ONE);
				request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
				response = discountManagementService.discountSearch(request);
				if (ptoList.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_LIST_DATA, ptoList.getPtoList());
				}
			}
		} catch (AFCSException e) {
			logger.error("DiscountManagementController class :: editDiscountData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			DiscountRequest discountRequest = populateDiscountUpdateData(response);
			model.put(DISCOUNT_EDIT_REQUEST, discountRequest);
			modelAndView.addObject(DISCOUNT_EDIT_REQUEST, discountRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = DISCOUNT_UPDATE)
	public ModelAndView updatePtoData(HttpSession session, Map<String, Object> model, DiscountRequest request,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_UPDATE);
		WebResponse response = null;
		model.put(DISCOUNT_EDIT_REQUEST, request);
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return discountDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			response = discountManagementService.updateDiscount(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(Constants.SUCCESS, properties.getProperty("discount.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(DISCOUNT_EDIT_REQUEST, request);
		}
		return modelAndView;
	} 
	@PostMapping(value = DOWNLOAD_DISCOUNT_REPORT)
	public ModelAndView downloadDiscountReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: DiscountController:: downloadDiscountReport method");
		ModelAndView modelAndView = new ModelAndView(DISCOUNT_SEARCH);
		DiscountListResponse discountListResponse = null;
		try {
			DiscountRequest	discountRequest = (DiscountRequest) session.getAttribute(DISCOUNT_LIST_DATA);
			discountRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = discountRequest.getPageSize();
			discountRequest.setUserType(RoleLevel.SUPER_ADMIN.getValue());
			if (downloadAllRecords) {
				discountRequest.setPageIndex(Constants.ONE);
				discountRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			discountListResponse =  discountManagementService.discountSearch(discountRequest);
			List<DiscountRequest> reuestResponses = discountListResponse.getListOfDiscount();
			if (!StringUtil.isNull(discountListResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsDataForDownloadVehicleOnboardReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			discountRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR:: DiscountController:: DiscountReport method", e);
		}
		logger.info("Exit:: DiscountController:: downloadDiscountReport method");
		return null;
	}

	private void setExportDetailsDataForDownloadVehicleOnboardReport(List<DiscountRequest> discountRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("DiscountManagement_");
		exportDetails.setHeaderMessageProperty("chatak.header.discount.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(discountRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("DiscountManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.pto", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.discountType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.discountName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.routeStageStationDifference", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.discount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("DiscountManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				 };
		return new ArrayList<>(Arrays.asList(headerArr));
	}

	private static List<Object[]> getDetailTypeFileData(List<DiscountRequest> discountRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (DiscountRequest discountData : discountRequest) {

			Object[] rowData = { 
					discountData.getOrganizationName() , discountData.getPtoName() , discountData.getDiscountType() , discountData.getDiscountName(), 
			        discountData.getRouteStageStationDifference() , discountData.getDiscount() , discountData.getStatus()
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}


}
