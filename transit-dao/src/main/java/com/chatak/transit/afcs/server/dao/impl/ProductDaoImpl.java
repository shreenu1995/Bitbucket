package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.ProductDao;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.ProductManagement;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QProductManagement;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.ProductRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.UserCredentialsRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;
import com.chatak.transit.afcs.server.util.DateUtil;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	ProductRepository productRepository;

	@PersistenceContext
	EntityManager em;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	UserCredentialsRepository userCredentialsRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;
	
	@Autowired
	PtoMasterRepository ptoMasterRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(FeeManagementDaoImpl.class);


	@Override
	public void saveProduct(ProductManagement productManagement) {
		productRepository.save(productManagement);

	}

	@Override
	public ProductSearchResponse searchProduct(ProductSearchRequest request, ProductSearchResponse response) {

		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}

		JPAQuery query = new JPAQuery(em);

		List<ProductManagement> productList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			productList = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isProductType(request.getProductType()), 
							isStatus(request.getStatus()),
							isProductName(request.getProductName()), 
							isProductId(request.getProductId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QProductManagement.productManagement.productId.desc())
					.list(QProductManagement.productManagement);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			productList = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
							QProductManagement.productManagement.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isProductType(request.getProductType()), isStatus(request.getStatus()),
							isProductName(request.getProductName()), isProductId(request.getProductId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QProductManagement.productManagement.productId.desc())
					.list(QProductManagement.productManagement);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.PTO_ADMIN.getValue())) {
			productList = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()),
							isPtoId(request.getPtoId()),
							isProductType(request.getProductType()), isStatus(request.getStatus()),
							isProductName(request.getProductName()), isProductId(request.getProductId()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QProductManagement.productManagement.productId.desc())
					.list(QProductManagement.productManagement);
		}

		List<ProductUpdateRequest> searchProductList = new ArrayList<>();

		for (ProductManagement productManagementTabledata : productList) {
			ProductUpdateRequest productResponse = new ProductUpdateRequest();
			productResponse.setProductName(productManagementTabledata.getProductName());
			productResponse.setProductType(productManagementTabledata.getProductType());
			productResponse.setAmount(productManagementTabledata.getAmount());
			productResponse.setDiscount(productManagementTabledata.getDiscount());
			productResponse.setPtoId(productManagementTabledata.getPtoId());
			productResponse.setProductId(productManagementTabledata.getProductId());
			productResponse.setTicketAndPassType(productManagementTabledata.getTicketAndPassType());
			productResponse.setOrganizationId(productManagementTabledata.getOrganizationId());
			productResponse.setValidFrom(DateUtil.toDateStringFormat((productManagementTabledata.getValidFrom())));
			productResponse.setValidTo(DateUtil.toDateStringFormat((productManagementTabledata.getValidTo())));
			productResponse.setStatus(productManagementTabledata.getStatus());
			if (!StringUtil.isNull(productManagementTabledata.getOrganizationId())) {
				Long organnizationId = productManagementTabledata.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository
							.findByOrgId(organnizationId);
					productResponse.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}
			if (!StringUtil.isNull(productManagementTabledata.getPtoId())) {
				Long ptoId = productManagementTabledata.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					productResponse.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}
			searchProductList.add(productResponse);
		}

		response.setListOfProduct(searchProductList);
		response.setTotalNoOfRecords(getTotalProductRows(request));
		return response;
	}

	private int getTotalProductRows(ProductSearchRequest request) {
		JPAQuery query = new JPAQuery(em);

		Long count = null;
		if (request.getUserType().equals(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
					isOrganizationId(request.getOrganizationId()),
					isPtoId(request.getPtoId()),
					isProductType(request.getProductType()), 
					isStatus(request.getStatus()),
					isProductName(request.getProductName()), 
					isProductId(request.getProductId()))
					.orderBy(QProductManagement.productManagement.productId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
							QProductManagement.productManagement.organizationId.eq(request.getOrganizationId()),
							isOrganizationId(request.getOrganizationId()), 
							isPtoId(request.getPtoId()),
							isProductType(request.getProductType()), 
							isStatus(request.getStatus()),
							isProductName(request.getProductName()), 
							isProductId(request.getProductId()))
							.orderBy(QProductManagement.productManagement.productId.desc()).count();
		} else if (request.getUserType().equals(RoleLevel.PTO_ADMIN.getValue())) {
			count = query.from(QProductManagement.productManagement)
					.where(QProductManagement.productManagement.status.ne(Status.TERMINATED.getValue()),
							isOrganizationId(request.getOrganizationId()), 
							isPtoId(request.getPtoId()),
							isProductType(request.getProductType()), 
							isStatus(request.getStatus()),
							isProductName(request.getProductName()), 
							isProductId(request.getProductId()))
							.orderBy(QProductManagement.productManagement.productId.desc()).count();

		}

		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QProductManagement.productManagement.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isProductType(String productType) {
		return !StringUtil.isNullEmpty(productType) ? QProductManagement.productManagement.productType.toUpperCase()
				.like("%" + productType.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isProductName(String productName) {
		return !StringUtil.isNullEmpty(productName) ? QProductManagement.productManagement.productName.toUpperCase()
				.like("%" + productName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isProductId(Long productId) {
		return !StringUtil.isNull(productId)
				? QProductManagement.productManagement.productId.eq(productId) : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QProductManagement.productManagement.ptoId.eq(ptoId) : null;
	}
	
	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId)
				? QProductManagement.productManagement.organizationId.eq(organizationId):null;
	}

	@Override
	public boolean validateProductStatusUpdate(@Valid ProductStatusChangeRequest request) {
		return userCredentialsRepository.existsByEmail(request.getUserId())
				&& productRepository.existsByProductId(request.getProductId());
	}

	@Override
	public ProductManagement updateProductStatus(ProductStatusChangeRequest request) {
		ProductManagement productManagement = productRepository.findByProductId(request.getProductId());
		productManagement.setStatus(request.getStatus());
		productManagement.setReason(request.getReason());
		return productRepository.save(productManagement);
	}
	
	@Transactional
	@Override
	public boolean updateProductProfile(ProductUpdateRequest request) {
		QProductManagement product = QProductManagement.productManagement;
		long noOfRowProduct = new JPAUpdateClause(em, product).where(product.productId.eq(request.getProductId()))
				.set(product.organizationId, request.getOrganizationId()).set(product.ptoId, request.getPtoId())
				.set(product.productName, request.getProductName()).set(product.productType, request.getProductType())
				.set(product.amount, request.getAmount()).set(product.validFrom, DateUtil.convertStringToTimestamp(request.getValidFrom()))
				.set(product.validTo, DateUtil.convertStringToTimestamp(request.getValidTo())).set(product.productId, (request.getProductId()))
				.set(product.discount, request.getDiscount())
				.set(product.ticketAndPassType, request.getTicketAndPassType()).execute();

		return noOfRowProduct == 1l;
	}

}
