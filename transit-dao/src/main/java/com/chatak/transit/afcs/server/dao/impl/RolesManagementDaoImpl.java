package com.chatak.transit.afcs.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.transit.afcs.server.constants.Constants;
import com.chatak.transit.afcs.server.dao.RolesManagementDao;
import com.chatak.transit.afcs.server.dao.model.AfcsRole;
import com.chatak.transit.afcs.server.dao.model.AfcsRoleFeatureMap;
import com.chatak.transit.afcs.server.dao.model.QAfcsRole;
import com.chatak.transit.afcs.server.dao.model.QAfcsRoleFeatureMap;
import com.chatak.transit.afcs.server.dao.model.QEntities;
import com.chatak.transit.afcs.server.dao.model.QEntityFeature;
import com.chatak.transit.afcs.server.dao.model.QTransitFeature;
import com.chatak.transit.afcs.server.dao.model.TransitFeature;
import com.chatak.transit.afcs.server.dao.repository.AfcsRoleFeatureMapRepository;
import com.chatak.transit.afcs.server.dao.repository.AfcsRoleRepository;
import com.chatak.transit.afcs.server.enums.RoleLevel;
import com.chatak.transit.afcs.server.enums.Status;
import com.chatak.transit.afcs.server.pojo.web.RoleRequest;
import com.chatak.transit.afcs.server.pojo.web.RolesSearchResponse;
import com.chatak.transit.afcs.server.util.StringUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository
public class RolesManagementDaoImpl implements RolesManagementDao {

	@Autowired
	AfcsRoleRepository afcsRoleRepository;

	@Autowired
	AfcsRoleFeatureMapRepository afcsRoleFeatureMapRepository;

	@PersistenceContext
	EntityManager em;

	@Override
	public AfcsRole findByRoleId(Long id) {
		return afcsRoleRepository.findById(id).orElse(null);
	}

	@Override
	public List<AfcsRole> findByRoleName(String name) {
		return afcsRoleRepository.findByName(name);
	}

	@Override
	public List<AfcsRole> findByRoleNameAndId(String name, Long agentId) {
		JPAQuery query = new JPAQuery(em);
		QAfcsRole qAfcsRole = QAfcsRole.afcsRole;
		return query.from(qAfcsRole).where(qAfcsRole.name.equalsIgnoreCase(name)).list(qAfcsRole);
	}

	@Override
	public AfcsRole saveOrUpdateAfcsRole(AfcsRole agentRole) {
		return afcsRoleRepository.save(agentRole);
	}

	@Override
	public AfcsRoleFeatureMap saveOrUpdateAfcsRoleFeatureMap(AfcsRoleFeatureMap featureMap) {
		return afcsRoleFeatureMapRepository.save(featureMap);
	}

	@Override
	public void deleteAfcsRoleFeatureMap(Long roleId) {

		List<AfcsRoleFeatureMap> featureMaps = afcsRoleFeatureMapRepository.findByRoleId(roleId);
		afcsRoleFeatureMapRepository.deleteAll(featureMaps);
	}

	@Override
	public List<TransitFeature> getTransitFeatureForEntityType(String roleLevel, String status) {
		JPAQuery query = new JPAQuery(em);
		return query.from(QTransitFeature.transitFeature, QEntities.entities, QEntityFeature.entityFeature)
				.where(QEntities.entities.name.equalsIgnoreCase(roleLevel),
						QTransitFeature.transitFeature.status.equalsIgnoreCase(status),
						QTransitFeature.transitFeature.featureId.eq(QEntityFeature.entityFeature.featureId),
						QEntityFeature.entityFeature.entityId.eq(QEntities.entities.id))
				.orderBy(QEntityFeature.entityFeature.featureId.asc()).list(QTransitFeature.transitFeature);
	}

	@Override
	public List<Long> getFeatureDataOnRoleIdData(Long roleId) {
		JPAQuery query = new JPAQuery(em);
		QTransitFeature qPrepaidFeature = QTransitFeature.transitFeature;
		QAfcsRoleFeatureMap qUserRoleFeatureMap = QAfcsRoleFeatureMap.afcsRoleFeatureMap;
		return query.from(qUserRoleFeatureMap, qPrepaidFeature)
				.where(isRoleId(roleId),
						QTransitFeature.transitFeature.featureId.eq(QAfcsRoleFeatureMap.afcsRoleFeatureMap.featureId))
				.list(qUserRoleFeatureMap.featureId);

	}

	private BooleanExpression isRoleId(Long roleId) {
		return roleId != null ? QAfcsRoleFeatureMap.afcsRoleFeatureMap.roleId.eq(roleId) : null;
	}

	@Override
	public RolesSearchResponse searchRole(RoleRequest roleRequest) {
		RolesSearchResponse rolesSearchResponse = new RolesSearchResponse();
		List<RoleRequest> searchRoleRequestsList = new ArrayList<>();
		Integer pageIndexNo = roleRequest.getPageSize();
		int fromIndex = 0;
		int toIndex;
		if (pageIndexNo != null) {
			toIndex = pageIndexNo * Constants.SIZE;
			fromIndex = toIndex - Constants.SIZE;
		}
		List<Tuple> tupleList = null;
		JPAQuery query = new JPAQuery(em);

		if (roleRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.toString())) {
			tupleList = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							isUserRoleId(roleRequest.getRoleId()))
					.offset(fromIndex).limit(10).orderBy(orderByRoleIDDesc()).list(QAfcsRole.afcsRole.id,
							QAfcsRole.afcsRole.name, QAfcsRole.afcsRole.status, QAfcsRole.afcsRole.userType,
							QAfcsRole.afcsRole.updatedBy, QAfcsRole.afcsRole.createdBy, QAfcsRole.afcsRole.description);
		} else if (roleRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.toString())) {
			tupleList = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							QAfcsRole.afcsRole.userType.ne(RoleLevel.SUPER_ADMIN.getValue()),
							QAfcsRole.afcsRole.createdBy.eq(roleRequest.getCreatedBy()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							isUserRoleId(roleRequest.getRoleId()))
					.offset(fromIndex).limit(10).orderBy(orderByRoleIDDesc()).list(QAfcsRole.afcsRole.id,
							QAfcsRole.afcsRole.name, QAfcsRole.afcsRole.status, QAfcsRole.afcsRole.userType,
							QAfcsRole.afcsRole.updatedBy, QAfcsRole.afcsRole.createdBy, QAfcsRole.afcsRole.description);

		} else {
			tupleList = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							QAfcsRole.afcsRole.userType.eq(RoleLevel.PTO_ADMIN.getValue()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							QAfcsRole.afcsRole.createdBy.eq(roleRequest.getCreatedBy()),
							isUserRoleId(roleRequest.getRoleId()))
					.offset(fromIndex).limit(10).orderBy(orderByRoleIDDesc()).list(QAfcsRole.afcsRole.id,
							QAfcsRole.afcsRole.name, QAfcsRole.afcsRole.status, QAfcsRole.afcsRole.userType,
							QAfcsRole.afcsRole.updatedBy, QAfcsRole.afcsRole.createdBy, QAfcsRole.afcsRole.description);
		}
		for (Tuple tuple : tupleList) {
			RoleRequest roleRUserRoleRequest = new RoleRequest();
			roleRUserRoleRequest.setRoleId(tuple.get(QAfcsRole.afcsRole.id));
			roleRUserRoleRequest.setRoleName(tuple.get(QAfcsRole.afcsRole.name));
			roleRUserRoleRequest.setStatus(tuple.get(QAfcsRole.afcsRole.status));
			roleRUserRoleRequest.setRoleType(tuple.get(QAfcsRole.afcsRole.userType));
			roleRUserRoleRequest.setDescription(tuple.get(QAfcsRole.afcsRole.description));
			roleRUserRoleRequest.setCreatedBy(tuple.get(QAfcsRole.afcsRole.createdBy));
			searchRoleRequestsList.add(roleRUserRoleRequest);
		}
		rolesSearchResponse.setListOfAfcsRole(searchRoleRequestsList);
		rolesSearchResponse.setTotalNoOfRecords(getTotalUserRolesRows(roleRequest));
		return rolesSearchResponse;

	}

	private int getTotalUserRolesRows(RoleRequest roleRequest) {
		Long count = null;
		JPAQuery query = new JPAQuery(em);
		if (roleRequest.getUserType().equalsIgnoreCase(RoleLevel.SUPER_ADMIN.toString())) {
			count = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							isUserRoleId(roleRequest.getRoleId()))
					.orderBy().count();
		} else if (roleRequest.getUserType().equalsIgnoreCase(RoleLevel.ORG_ADMIN.toString())) {
			count = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							QAfcsRole.afcsRole.userType.ne(RoleLevel.SUPER_ADMIN.getValue()),
							QAfcsRole.afcsRole.createdBy.eq(roleRequest.getCreatedBy()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							isUserRoleId(roleRequest.getRoleId()))
					.orderBy().count();
		} else {
			count = query.from(QAfcsRole.afcsRole)
					.where(QAfcsRole.afcsRole.status.ne(Status.TERMINATED.getValue()),
							QAfcsRole.afcsRole.userType.eq(RoleLevel.PTO_ADMIN.getValue()),
							QAfcsRole.afcsRole.createdBy.eq(roleRequest.getCreatedBy()),
							isRoleNameLike(roleRequest.getRoleName()), isStatusRole(roleRequest.getStatus()),
							isUserRoleId(roleRequest.getRoleId()))
					.orderBy().count();
		}
		return count != null ? count.intValue() : 0;
	}

	private BooleanExpression isUserRoleId(Long userRoleId) {
		return userRoleId != null ? QAfcsRole.afcsRole.id.eq(userRoleId) : null;
	}

	private BooleanExpression isStatusRole(String status) {
		return !StringUtil.isNullEmpty(status) ? QAfcsRole.afcsRole.status.eq(status) : null;
	}

	private OrderSpecifier<Long> orderByRoleIDDesc() {
		return QAfcsRole.afcsRole.id.desc();
	}

	private BooleanExpression isRoleNameLike(String roleName) {
		return (roleName != null && !"".equals(roleName))
				? QAfcsRole.afcsRole.name.toUpperCase().like("%" + roleName.replace("*", "").toUpperCase() + "%")
				: null;
	}

	@Transactional
	@Override
	public AfcsRole updateRoleStatus(RoleRequest request) {
		AfcsRole afcsRole = afcsRoleRepository.findByIdAndStatusNotLike(request.getRoleId(),Status.TERMINATED.getValue());
		afcsRole.setStatus(request.getStatus());
		afcsRole.setName(afcsRole.getName());
		return afcsRoleRepository.save(afcsRole);
	}

	@Override
	public List<AfcsRole> getAllRole() {
		JPAQuery query = new JPAQuery(em);
		return query.from(QAfcsRole.afcsRole).where(QAfcsRole.afcsRole.status.eq(Status.ACTIVE.getValue())).orderBy()
				.list(QAfcsRole.afcsRole);
	}

	@Override
	public List<AfcsRole> getRoleList() {
		return afcsRoleRepository.findByStatusNotLike(Status.TERMINATED.getValue());
	}

	@Override
	public List<AfcsRole> getRoleListByUserType(RoleRequest request) {
		return afcsRoleRepository.findByUserTypeAndStatusNotLike(request.getRoleCategory(),
				Status.TERMINATED.getValue());
	}

	@Override
	public List<AfcsRole> getRoleListByUserTypeAndCreatedBy(RoleRequest request) {
		return afcsRoleRepository.findByUserTypeAndCreatedByAndStatusNotLike(request.getRoleCategory(),request.getCreatedBy(),
				Status.TERMINATED.getValue());
	}

}
