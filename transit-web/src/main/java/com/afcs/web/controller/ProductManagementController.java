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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.afcs.web.constants.Constants;
import com.afcs.web.service.OrganizationManagementService;
import com.afcs.web.service.ProductManagementService;
import com.afcs.web.service.PtoManagementService;
import com.afcs.web.util.ExportUtil;
import com.afcs.web.util.JsonUtil;
import com.afcs.web.util.PaginationUtil;
import com.chatak.transit.afcs.server.enums.CustomErrorCodes;
import com.chatak.transit.afcs.server.enums.ExportType;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.exception.AFCSException;
import com.chatak.transit.afcs.server.pojo.web.OrganizationResponse;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.OrganizationSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductRegistrationRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoList;
import com.chatak.transit.afcs.server.pojo.web.PtoListRequest;
import com.chatak.transit.afcs.server.pojo.web.PtoListResponse;
import com.chatak.transit.afcs.server.pojo.web.WebResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class ProductManagementController {

	@Autowired
	ProductManagementService productManagementService;

	@Autowired
	PtoManagementService ptoManagementService;

	@Autowired
	OrganizationManagementService organizationManagementService;

	@Autowired
	Environment properties;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(PtoSummaryController.class);

	private static final String PRODUCT_SEARCH = "product-search";
	private static final String PRODUCT_SARCH = "productSearch";
	private static final String PRODUCT_SEARCH_PAGINATION = "product-search-pagination";
	private static final String PRODUCT_DATA = "productData";
	private static final String PRODUCT_DATA_SIZE = "productDataSize";
	private static final String PRODUCT_VIEW_ACTION = "product-view-action";
	private static final String PRODUCT_EDIT_ACTION = "product-edit-action";
	private static final String PRODUCT_VIEW_REQ = "productViewRequest";
	private static final String PRODUCT_REGISTRATION = "product-registration";
	private static final String UPDATE_PRODUCT_STATUS = "update-product-status";
	private static final String PRODUCT_UPDATE = "update-product";
	private static final String GET_PTO_BY_ORGANIZATION_NAME = "getPtoByOrganizationName";
	private static final String PTO_LIST_DATA = "ptoListData";
	private static final String PRODUCT_NAME = "&&productName";
	private static final String DOWNLOAD_PRODUCT_DOWNLOAD_REPORT  = "downloadProductDownloadReport";
	
	@GetMapping(value = PRODUCT_REGISTRATION)
	public ModelAndView productRegisterGet(Map<String, Object> model, HttpSession session) {
		ModelAndView modelandView = new ModelAndView(PRODUCT_REGISTRATION);
		ProductRegistrationRequest request = new ProductRegistrationRequest();
		model.put("productRequest", request);
		return modelandView;
	}

	@PostMapping(value = PRODUCT_REGISTRATION)
	public ModelAndView productRegisterPost(@ModelAttribute("productRequest") ProductRegistrationRequest request,
			Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		ProductSearchRequest productSearchRequest = new ProductSearchRequest();
		model.put(PRODUCT_SARCH, productSearchRequest);
		request.setUserId(session.getAttribute(Constants.USER_ID).toString());
		WebResponse response = null;
		try {
			response = productManagementService.saveProductRegistration(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put("productRequest", new ProductRegistrationRequest());
			model.put(Constants.SUCCESS, properties.getProperty("product.created.successfully"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		return modelAndView;
	}

	@GetMapping(value = PRODUCT_SEARCH)
	public ModelAndView productSearchGet(Map<String, Object> model, HttpSession session) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		ProductSearchRequest productSearchRequest = new ProductSearchRequest();
		productSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put(PRODUCT_SARCH, productSearchRequest);
		OrganizationSearchRequest searchPtoRequest = new OrganizationSearchRequest();
		searchPtoRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		model.put("ptoSearch", searchPtoRequest);
		OrganizationSearchResponse response = new OrganizationSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		ptoListRequest.setUserId(session.getAttribute(Constants.USER_ID).toString());
		PtoListResponse ptoOperationListResponse = ptoManagementService.getPtoList(ptoListRequest);
		model.put(PTO_LIST_DATA, ptoOperationListResponse.getPtoList());
		session.setAttribute(PTO_LIST_DATA, ptoOperationListResponse.getPtoList());
		PtoListResponse ptoListResponse = null;
		if (ptoListRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else if (ptoListRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
			ptoListRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		} else {
			ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
			ptoListResponse = ptoManagementService.getPtoList(ptoListRequest);
		}

		if (!StringUtil.isNull(ptoListResponse)
				&& ptoListResponse.getStatusMessage().equalsIgnoreCase(Constants.SUCCESS)) {
			model.put(PTO_LIST_DATA, ptoListResponse.getPtoList());
			session.setAttribute(PTO_LIST_DATA, ptoListResponse.getPtoList());
		}
		try {
			productSearchRequest.setPageIndex(10);
			productSearchRequest.setPageIndex(Constants.ONE);
			session.setAttribute(PRODUCT_DATA, productSearchRequest);
			if (searchPtoRequest.getUserType().equals(RoleLevel.SUPER_ADMIN.name())) {
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else if (searchPtoRequest.getUserType().equals(RoleLevel.ORG_ADMIN.name())) {
				searchPtoRequest.setOrgId(Long.valueOf(session.getAttribute(Constants.ORG_ID).toString()));
				response = organizationManagementService.getOrganizationList(searchPtoRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				PtoListResponse ptoList = ptoManagementService.getPtoByPtoId(ptoListRequest);
				if (StringUtil.isListNotNullNEmpty(ptoList.getPtoList())) {
					searchPtoRequest.setOrgId(ptoList.getPtoList().get(0).getOrgId());
					response = organizationManagementService.getOrganizationList(searchPtoRequest);
				}
			}
		} catch (AFCSException e) {
			logger.error(
					"ERROR :: PtoOperationManagementController class :: showPtoOperationSearch method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response)) {
			model.put("organizationList", response.getOrganizationList());
			session.setAttribute("organizationList", response.getOrganizationList());
		}
		return modelAndView;
	}

	@PostMapping(value = PRODUCT_SEARCH)
	public ModelAndView productSearchPost(Map<String, Object> model, ProductSearchRequest request, HttpSession session,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		model.put(PRODUCT_SARCH, new ProductSearchRequest());
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			model.put(PRODUCT_SARCH, new ProductSearchRequest());
			return productDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		request.setPageIndex(Constants.ONE);
		model.put(PRODUCT_SARCH, new ProductSearchRequest());
		session.setAttribute(PRODUCT_SARCH, new ProductSearchRequest());
		ProductSearchResponse response = null;

		try {
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = productManagementService.searchProduct(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = productManagementService.searchProduct(request);
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
				response = productManagementService.searchProduct(request);
				}
			session.setAttribute(PRODUCT_DATA, request);

		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}

		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(PRODUCT_DATA, response.getListOfProduct());
			session.setAttribute(PRODUCT_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(PRODUCT_DATA_SIZE, response.getTotalNoOfRecords());
			model.put(PRODUCT_SARCH, request);
			session.setAttribute(PRODUCT_SARCH, request);
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
		}
		PtoListRequest ptoListRequest = new PtoListRequest();
		ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		getPtoList(model, request, session, ptoListRequest);
		PaginationUtil.performPagination(modelAndView, session, Constants.ONE, PRODUCT_DATA_SIZE);
		return modelAndView;
	}
	
	private void getPtoList(Map<String, Object> model, ProductSearchRequest request, HttpSession session,
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

	@PostMapping(value = PRODUCT_SEARCH_PAGINATION)
	public ModelAndView productDataPagination(final HttpSession session,
			@RequestParam(Constants.PAGE_NUMBER) Integer pageNumber, Map<String, Object> model) throws AFCSException {
		ProductSearchRequest request = (ProductSearchRequest) session.getAttribute(PRODUCT_SARCH);
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		ProductSearchResponse response = new ProductSearchResponse();
		model.put(PRODUCT_SARCH, request);
		try {
			request.setPageIndex(pageNumber);
			request.setProductId(null);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = productManagementService.searchProduct(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = productManagementService.searchProduct(request);
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
				response = productManagementService.searchProduct(request);
			}
		} catch (AFCSException e) {
			logger.error("ProductManagementController class :: productDataPagination method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		model.put(Constants.SEARCH_FLAG, Constants.FLAG_TRUE);
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			model.put(PRODUCT_DATA, response.getListOfProduct());
			model.put(PRODUCT_SARCH, request);
			session.setAttribute(PRODUCT_DATA_SIZE, response.getTotalNoOfRecords());
			PtoListRequest ptoListRequest = new PtoListRequest();
			ptoListRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			getPtoList(model, request, session, ptoListRequest);
			PaginationUtil.performPagination(modelAndView, session, pageNumber, PRODUCT_DATA_SIZE);
		}
		return modelAndView;
	}

	@PostMapping(value = PRODUCT_VIEW_ACTION)
	public ModelAndView viewProductData(final HttpSession session, @RequestParam("id") Long id,
			Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView("product-view");
		ProductSearchResponse response = new ProductSearchResponse();
		model.put(PRODUCT_VIEW_REQ, new ProductUpdateRequest());
		ProductSearchRequest productSearchRequest = new ProductSearchRequest();
		productSearchRequest.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
		try {
			ProductSearchRequest request = (ProductSearchRequest) session.getAttribute(PRODUCT_SARCH);

			request.setPageIndex(1);
			request.setProductId(id);

			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
				response = productManagementService.searchProduct(request);
			} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = productManagementService.searchProduct(request);
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
				response = productManagementService.searchProduct(request);
			}

		} catch (AFCSException e) {
			logger.error("ProductManagementController class :: viewProductData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			ProductUpdateRequest viewRequest = response.getListOfProduct().get(0);
			model.put(PRODUCT_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}

	@PostMapping(value = UPDATE_PRODUCT_STATUS)
	public ModelAndView updateProductStatus(final HttpSession session, @RequestParam("id") Long id,
			@RequestParam("status") String status, @RequestParam("reason") String reason, Map<String, Object> model) {
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		model.put(PRODUCT_SARCH, new ProductSearchRequest());
		ProductStatusChangeRequest request = new ProductStatusChangeRequest();
		ProductSearchResponse response = null;
		try {
			request.setStatus(status);
			request.setUserId(session.getAttribute("userId").toString());
			request.setReason(reason);
			request.setProductId(id);
			response = productManagementService.updateProductStatus(request);
			if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {

				if (Constants.ACTIVE.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("product.status.suspend.changed")
							.replace(PRODUCT_NAME, response.getProductName()));
				} else if (Constants.SUSPENDED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("product.status.active.changed")
							.replace(PRODUCT_NAME, response.getProductName()));
				} else if (Constants.TERMINATED.equalsIgnoreCase(status)) {
					model.put(Constants.SUCCESS, properties.getProperty("product.status.terminate.changed")
							.replace(PRODUCT_NAME, response.getProductName()));
				}

				return productDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);

			}
		} catch (AFCSException e) {
			logger.error("ProductManagementController class :: updateProductStatus method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		return modelAndView;
	}

	@PostMapping(value = PRODUCT_EDIT_ACTION)
	public ModelAndView editProductData(final HttpSession session, @RequestParam("id") Long id,
			Map<String, Object> model) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("product-edit");
		ProductSearchResponse response = new ProductSearchResponse();
		PtoListRequest ptoListRequest = new PtoListRequest();
		model.put(PRODUCT_VIEW_REQ, new ProductUpdateRequest());
		PtoListResponse ptoListResponse = new PtoListResponse();
		try {
			ProductSearchRequest request = (ProductSearchRequest) session.getAttribute(PRODUCT_SARCH);
			request.setPageIndex(1);
			request.setProductId(id);
			request.setUserType(session.getAttribute(Constants.USER_TYPE).toString());
			if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
				response = productManagementService.searchProduct(request);
				ptoListRequest
						.setOrgId(response.getListOfProduct().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
			} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
				request.setOrganizationId(Long.parseLong(session.getAttribute(Constants.ORG_ID).toString()));
				response = productManagementService.searchProduct(request);
				ptoListRequest
						.setOrgId(response.getListOfProduct().get(0).getOrganizationId());
				ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
			} else {
				ptoListRequest.setPtoMasterId(Long.valueOf(session.getAttribute(Constants.PTO_MASTER_ID).toString()));
				ptoListResponse = ptoManagementService.getPtoByPtoId(ptoListRequest);
				PtoList pto = new PtoList();
				if (StringUtil.isListNotNullNEmpty(ptoListResponse.getPtoList())) {
					pto.setPtoName(ptoListResponse.getPtoList().get(0).getPtoName());
					request.setPtoName(pto.getPtoName());
				}
				response = productManagementService.searchProduct(request);
				if (ptoListResponse.getPtoList().get(0).getStatus().equalsIgnoreCase(Status.ACTIVE.getValue())) {
					model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
				}
			}

		} catch (AFCSException e) {
			logger.error("ProductManagementController class :: viewProductData method :: exception", e);
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			ProductUpdateRequest viewRequest = populateProductViewRequest(response);
			model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			session.setAttribute(Constants.PTO_DATA, ptoListResponse.getPtoList());
			model.put(PRODUCT_VIEW_REQ, viewRequest);
			modelAndView.addObject(PRODUCT_VIEW_REQ, viewRequest);
		}
		return modelAndView;
	}

	private ProductUpdateRequest populateProductViewRequest(ProductSearchResponse response) throws AFCSException {
		PtoListRequest ptoListRequest = new PtoListRequest();
		ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
		ProductUpdateRequest productProfileUpdateRequest = response.getListOfProduct().get(0);
		productUpdateRequest.setProductId(productProfileUpdateRequest.getProductId());
		productUpdateRequest.setProductType(productProfileUpdateRequest.getProductType());
		productUpdateRequest.setProductName(productProfileUpdateRequest.getProductName());
		productUpdateRequest.setTicketAndPassType(productProfileUpdateRequest.getTicketAndPassType());
		productUpdateRequest.setAmount(productProfileUpdateRequest.getAmount());
		productUpdateRequest.setDiscount(productProfileUpdateRequest.getDiscount());
		productUpdateRequest.setOrganizationId(productProfileUpdateRequest.getOrganizationId());
		productProfileUpdateRequest.setValidFrom(productProfileUpdateRequest.getValidFrom());
		productProfileUpdateRequest.setValidTo(productProfileUpdateRequest.getValidTo());
		if (!StringUtil.isNull(productProfileUpdateRequest.getPtoId())) {
			ptoListRequest.setPtoMasterId(productProfileUpdateRequest.getPtoId());
			PtoListResponse ptoList = ptoManagementService.getPtoDataByPtoId(ptoListRequest);
			try {
				if (!StringUtil.isNull(ptoList.getPtoList().get(0).getPtoMasterId())) {
					productProfileUpdateRequest.setPtoId(ptoList.getPtoList().get(0).getPtoMasterId());
				}
			} catch (IndexOutOfBoundsException e) {
				logger.error("ProductManagementController class :: populateProductViewRequest method :: exception", e);
			}
		}
		return productProfileUpdateRequest;
	}

	@PostMapping(value = PRODUCT_UPDATE)
	public ModelAndView updatePtoData(HttpSession session, Map<String, Object> model, ProductUpdateRequest request,
			@RequestParam("cancelTypeId") String cancelType) throws AFCSException {
		ModelAndView modelAndView = new ModelAndView("product-edit");
		WebResponse response = null;
		model.put(PRODUCT_VIEW_REQ, request);
		PtoListRequest ptoListRequest = new PtoListRequest();
		if (!StringUtil.isNullEmpty(cancelType) && null != session.getAttribute(Constants.PAGE_NUMBER)) {
			return productDataPagination(session, (Integer) session.getAttribute(Constants.PAGE_NUMBER), model);
		}
		try {
			response = productManagementService.updateProduct(request);
		} catch (AFCSException e) {
			model.put(Constants.ERROR, e.getMessage());
			return modelAndView;
		}
		if (!StringUtil.isNull(response) && response.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			ptoListRequest.setOrgId(request.getOrganizationId());
			PtoListResponse ptoListResponse = ptoManagementService.getActivePtoListByOrganizationId(ptoListRequest);
			model.put(Constants.PTO_DATA, ptoListResponse.getPtoList());
			model.put(Constants.SUCCESS, properties.getProperty("product.update.success"));
		} else {
			model.put(Constants.ERROR, response.getStatusMessage());
			model.put(PRODUCT_VIEW_REQ, request);
		}
		return modelAndView;
	}

	@GetMapping(value = GET_PTO_BY_ORGANIZATION_NAME)
	public @ResponseBody String getPtoByOrganizationName(Map model, HttpServletRequest request,
			HttpServletResponse httpServletResponse, HttpSession session,
			@RequestParam("organizationName") String organizationName, PtoListResponse response)
			throws JsonProcessingException, AFCSException {
		OrganizationSearchRequest organizationSearchRequest = new OrganizationSearchRequest();
		PtoListRequest ptoListRequest = new PtoListRequest();
		PtoListResponse ptoListResponse = null;
		organizationSearchRequest.setOrganizationName(organizationName);
		OrganizationResponse organizationResponse = new OrganizationResponse();
		organizationResponse = organizationManagementService
				.getOrganizationIdByOrganizationName(organizationSearchRequest, organizationResponse);
		organizationSearchRequest.setOrgId(organizationResponse.getOrgId());
		ptoListRequest.setOrgId(organizationSearchRequest.getOrgId());
		ptoListRequest.setUserId((String) session.getAttribute(Constants.USER_ID));
		ptoListResponse = ptoManagementService.getPtoByOrganizationIdAndUserId(ptoListRequest);
		if (!StringUtil.isNull(ptoListResponse) && ptoListResponse.getStatusCode().equals(Constants.SUCCESS_CODE)) {
			ptoListResponse.setStatusCode(CustomErrorCodes.SUCCESS.getCustomErrorCode());
			return JsonUtil.convertObjectToJSON(ptoListResponse);
		}
		return null;
	}
	@PostMapping(value =  DOWNLOAD_PRODUCT_DOWNLOAD_REPORT)
	public ModelAndView downloadProductManagementReport(HttpSession session, Map model, HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "downLoadPageNumber") final Integer downLoadPageNumber,
			@RequestParam(value = "totalRecords") final Integer totalRecords,
			@RequestParam(value = "downloadAllRecords") final boolean downloadAllRecords) {
		String downloadType = request.getParameter("downloadType");
		logger.info("Entering:: ProductManagementController:: downloadProductManagementReport method");
		ModelAndView modelAndView = new ModelAndView(PRODUCT_SEARCH);
		ProductSearchResponse productSearchResponse = null;
		try {
			ProductSearchRequest productSearchRequest = (ProductSearchRequest) session.getAttribute(PRODUCT_DATA);
			productSearchRequest.setPageIndex(downLoadPageNumber);
			Integer pageSize = productSearchRequest.getPageSize();
			if (downloadAllRecords) {
				productSearchRequest.setPageIndex(Constants.ONE);
				productSearchRequest.setPageSize(totalRecords);
			}
			ExportDetails exportDetails = new ExportDetails();
			productSearchResponse = productManagementService.searchProduct(productSearchRequest);
			List<ProductUpdateRequest> reuestResponses = productSearchResponse. getListOfProduct();
			if (!StringUtil.isNull(productSearchResponse)) {
				if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.PDF);
				} else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
					exportDetails.setExportType(ExportType.XLS);
					exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				}
				setExportDetailsProductManagementReport(reuestResponses, exportDetails);
				ExportUtil.exportData(exportDetails, response, messageSource);
			}
			productSearchRequest.setPageSize(pageSize);
		} catch (Exception e) {
			modelAndView.addObject(Constants.ERROR,
					messageSource.getMessage(Constants.CHATAK_GENERAL_ERROR, null, LocaleContextHolder.getLocale()));
			logger.error("ERROR::ProductManagementController:: ProductManagementReport method", e);
		}
		logger.info("Exit:: ProductManagementController:: downloadProductManagementReport method");
		return null;
	}

	private void setExportDetailsProductManagementReport(List<ProductUpdateRequest> productUpdateRequest,
			ExportDetails exportDetails) {
		exportDetails.setReportName("Product Management");
		exportDetails.setHeaderMessageProperty("chatak.header.ProductManagement.messages");

		exportDetails.setHeaderList(getDetailTypeHeaderList());
		exportDetails.setFileData(getDetailTypeFileData(productUpdateRequest));
	}

	private List<String> getDetailTypeHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("ProductManagement.file.exportutil.organizationName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.ptoName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.productType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.productName", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.ticketAndPassType", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.amount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.discount", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.validFrom", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.validTo", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("ProductManagement.file.exportutil.status", null,
						LocaleContextHolder.getLocale()),
				
			    };
		return new ArrayList<>(Arrays.asList(headerArr));
	}
   	
	private static List<Object[]> getDetailTypeFileData(List<ProductUpdateRequest> productUpdateRequest) {
		List<Object[]> fileData = new ArrayList<>();

		for (ProductUpdateRequest productUpdateData : productUpdateRequest) {

	 	Object[] rowData = {  
	 			productUpdateData.getOrganizationName() , productUpdateData.getPtoName() , productUpdateData.getProductType() , 
	 			productUpdateData.getProductName() , productUpdateData.getTicketAndPassType() , productUpdateData.getAmount() ,
	 			productUpdateData.getDiscount() , productUpdateData.getValidFrom() , productUpdateData.getValidTo() , 
	 			productUpdateData.getStatus()
			};
			
			fileData.add(rowData);
		}

		return fileData;
	}

}
