package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.DiscountDao;
import com.chatak.transit.afcs.server.dao.model.DiscountMaster;
import com.chatak.transit.afcs.server.dao.model.OrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.PtoMaster;
import com.chatak.transit.afcs.server.dao.model.QDiscountMaster;
import com.chatak.transit.afcs.server.dao.repository.DiscountRepository;
import com.chatak.transit.afcs.server.dao.repository.OrganizationMasterRepository;
import com.chatak.transit.afcs.server.dao.repository.PtoMasterRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.DiscountListResponse;
import com.chatak.transit.afcs.server.pojo.web.DiscountRequest;
import com.chatak.transit.afcs.server.pojo.web.DiscountStatusChangeRequest;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class DiscountDaoImpl implements DiscountDao {

	@Autowired
	DiscountRepository discountRepository;

	@Autowired
	OrganizationMasterRepository organizationMasterRepository;

	@Autowired
	PtoMasterRepository ptoMasterRepository;

	@PersistenceContext
	private EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(DiscountDaoImpl.class);

	@Override
	public void saveDiscountRegistration(DiscountMaster discountMaster) {
		discountRepository.save(discountMaster);

	}

	@Override
	public DiscountListResponse getDiscountList(DiscountRequest request) {
		Integer pageIndexNo = request.getPageIndex();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		JPAQuery query = new JPAQuery(em);
		List<DiscountMaster> discountMasterList = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			discountMasterList = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDiscountMaster.discountMaster.discountId.desc())
					.list(QDiscountMaster.discountMaster);
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			discountMasterList = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							QDiscountMaster.discountMaster.organizationId.eq(request.getOrganizationId()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDiscountMaster.discountMaster.discountId.desc())
					.list(QDiscountMaster.discountMaster);
		} else {
			discountMasterList = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.offset(fromIndex).limit(Constants.SIZE).orderBy(QDiscountMaster.discountMaster.discountId.desc())
					.list(QDiscountMaster.discountMaster);
		}
		DiscountListResponse discountListResponse = new DiscountListResponse();
		List<DiscountRequest> listDiscountSearchResponse = new ArrayList<>();
		DiscountRequest discountSearchResponse = null;
		for (DiscountMaster discountMaster : discountMasterList) {
			discountSearchResponse = new DiscountRequest();
			discountSearchResponse.setDiscount(discountMaster.getDiscount());
			discountSearchResponse.setOrganizationId(discountMaster.getOrganizationId());
			discountSearchResponse.setPtoId(discountMaster.getPtoId());
			discountSearchResponse.setDiscountId(discountMaster.getDiscountId());
			discountSearchResponse.setDiscountName(discountMaster.getDiscountName());
			discountSearchResponse.setDiscountType(discountMaster.getDiscountType());
			discountSearchResponse.setRouteStageStationDifference(discountMaster.getRouteStageStationDifference());
			discountSearchResponse.setStatus(discountMaster.getStatus());
			if (!StringUtil.isNull(discountMaster.getOrganizationId())) {
				Long organnizationId = discountMaster.getOrganizationId();
				try {
					OrganizationMaster organizationMaster = organizationMasterRepository.findByOrgId(organnizationId);
					discountSearchResponse.setOrganizationName(organizationMaster.getOrganizationName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: DiscountDaoImpl class :: getDiscountList method :: exception", e);
				}
			}
			if (!StringUtil.isNull(discountMaster.getPtoId())) {
				Long ptoId = discountMaster.getPtoId();
				try {
					PtoMaster ptoMaster = ptoMasterRepository.findByPtoMasterId(ptoId);
					discountSearchResponse.setPtoName(ptoMaster.getPtoName());
				} catch (NullPointerException e) {
					logger.error("ERROR :: ProductDaoImpl class :: searchProduct method :: exception", e);
				}
			}
			listDiscountSearchResponse.add(discountSearchResponse);
		}
		discountListResponse.setTotalRecords(getTotalNoOfRows(request));
		discountListResponse.setListOfDiscount(listDiscountSearchResponse);
		return discountListResponse;
	}

	private int getTotalNoOfRows(DiscountRequest request) {
		JPAQuery query = new JPAQuery(em);
		Long count = null;
		if (request.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.getValue())) {
			count = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.orderBy(QDiscountMaster.discountMaster.discountId.desc()).count();
		} else if (request.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.getValue())) {
			count = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							QDiscountMaster.discountMaster.organizationId.eq(request.getOrganizationId()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.orderBy(QDiscountMaster.discountMaster.discountId.desc()).count();
		} else {
			count = query.from(QDiscountMaster.discountMaster)
					.where(QDiscountMaster.discountMaster.status.ne(Status.TERMINATED.getValue()),
							isDiscountId(request.getDiscountId()), isDiscount(request.getDiscount()),
							isPtoId(request.getPtoId()), isOrganizationId(request.getOrganizationId()),
							isStatus(request.getStatus()), isDiscountName(request.getDiscountName()),
							isDiscountType(request.getDiscountType()),
							isRouteStageStationDifference(request.getRouteStageStationDifference()))
					.orderBy(QDiscountMaster.discountMaster.discountId.desc()).count();
		}

		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isDiscountId(Long discountId) {
		return !StringUtil.isNull(discountId) ? QDiscountMaster.discountMaster.discountId.eq(discountId) : null;

	}

	private BooleanExpression isDiscount(Long discount) {
		return !StringUtil.isNull(discount) ? QDiscountMaster.discountMaster.discount.eq(discount) : null;
	}

	private BooleanExpression isDiscountName(String discountName) {
		return !StringUtil.isNullEmpty(discountName) ? QDiscountMaster.discountMaster.discountName.toUpperCase()
				.like("%" + discountName.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isDiscountType(String discountType) {
		return !StringUtil.isNullEmpty(discountType) ? QDiscountMaster.discountMaster.discountType.toUpperCase()
				.like("%" + discountType.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isRouteStageStationDifference(Long routeStageStationDifference) {
		return !StringUtil.isNull(routeStageStationDifference)
				? QDiscountMaster.discountMaster.routeStageStationDifference.eq(routeStageStationDifference) : null;
	}

	private BooleanExpression isStatus(String status) {
		return !StringUtil.isNullEmpty(status) ? QDiscountMaster.discountMaster.status.toUpperCase()
				.like("%" + status.toUpperCase().replace("*", "") + "%") : null;
	}

	private BooleanExpression isPtoId(Long ptoId) {
		return !StringUtil.isNull(ptoId) ? QDiscountMaster.discountMaster.ptoId.eq(ptoId) : null;
	}

	private BooleanExpression isOrganizationId(Long organizationId) {
		return !StringUtil.isNull(organizationId) ? QDiscountMaster.discountMaster.organizationId.eq(organizationId)
				: null;
	}

	@Override
	public boolean validateDiscountStatusUpdate(DiscountStatusChangeRequest request) {
		return discountRepository.existsByDiscountId(request.getDiscountId());
	}

	@Transactional
	@Override
	public boolean updateDiscountProfile(DiscountRequest request) {
		QDiscountMaster discount = QDiscountMaster.discountMaster;
		long noOfRowDiscount = new JPAUpdateClause(em, discount).where(discount.discountId.eq(request.getDiscountId()))
				.set(discount.discount, request.getDiscount()).set(discount.organizationId, request.getOrganizationId())
				.set(discount.ptoId, request.getPtoId()).set(discount.discountId, request.getDiscountId())
				.set(discount.discountName, request.getDiscountName())
				.set(discount.discountType, request.getDiscountType())
				.set(discount.routeStageStationDifference, request.getRouteStageStationDifference()).execute();
		return noOfRowDiscount == 1l;
	}

	@Override
	public DiscountMaster updateDiscountStatus(DiscountStatusChangeRequest request) {
		DiscountMaster discountMaster = discountRepository.findByDiscountId(request.getDiscountId());
		discountMaster.setStatus(request.getStatus());
		discountMaster.setReason(request.getReason());
		return discountRepository.save(discountMaster);
	}

}
