/**
 * @author Girmiti Software
 *
 */
package com.chatak.transit.afcs.server.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.chatak.transit.afcs.server.dao.CustomErrorResolution;
import com.chatak.transit.afcs.server.dao.model.QDepotMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceManufacturerMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceModel;
import com.chatak.transit.afcs.server.dao.model.QDeviceOnboardMaster;
import com.chatak.transit.afcs.server.dao.model.QDeviceTypeMaster;
import com.chatak.transit.afcs.server.dao.model.QDiscountMaster;
import com.chatak.transit.afcs.server.dao.model.QFareMaster;
import com.chatak.transit.afcs.server.dao.model.QOperator;
import com.chatak.transit.afcs.server.dao.model.QOrganizationMaster;
import com.chatak.transit.afcs.server.dao.model.QProductManagement;
import com.chatak.transit.afcs.server.dao.model.QPtoMaster;
import com.chatak.transit.afcs.server.dao.model.QRouteMaster;
import com.chatak.transit.afcs.server.dao.model.QStageMaster;
import com.chatak.transit.afcs.server.dao.model.QStopProfile;
import com.chatak.transit.afcs.server.dao.model.QTicketsTxnData;
import com.chatak.transit.afcs.server.dao.model.QUserCredentials;
import com.chatak.transit.afcs.server.dao.model.QVehicleManufacturer;
import com.chatak.transit.afcs.server.dao.model.QVehicleModel;
import com.chatak.transit.afcs.server.dao.model.QVehicleTypeProfile;
import com.chatak.transit.afcs.server.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CustomErrorResolutionImpl implements CustomErrorResolution {

	@PersistenceContext
	private EntityManager em;

	JPAQuery query;

	@Override
	public boolean deviceTypeNameValidation(String deviceTypeName) {
		query = new JPAQuery(em);
		return query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.deviceTypeName.eq(deviceTypeName)).exists();

	}

	@Override
	public boolean isValidUser(String userID) {
		query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials).where(QUserCredentials.userCredentials.email.eq(userID))
				.exists();

	}

	@Override
	public boolean isValidAdminUser(String adminUserID) {
		query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.adminUserId.eq(adminUserID)).exists();

	}

	@Override
	public boolean ticketNoValidation(String ticketNo) {
		query = new JPAQuery(em);
		return query.from(QTicketsTxnData.ticketsTxnData)
				.where(QTicketsTxnData.ticketsTxnData.ticketNumber.eq(ticketNo)).exists();

	}

	@Override
	public boolean isValidDeviceModelNumber(String modelNumber) {
		query = new JPAQuery(em);
		return query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.deviceTypeName.eq(modelNumber)).exists();

	}

	@Override
	public boolean ptoIdValidation(Long ptoId) {
		query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster).where(QPtoMaster.ptoMaster.ptoMasterId.eq(ptoId)).exists();

	}

	@Override
	public boolean passwordValidation(String pass) {
		query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials).where(QUserCredentials.userCredentials.passWord.eq(pass))
				.exists();

	}

	@Override
	public boolean deviceModel(String model) {
		query = new JPAQuery(em);
		return query.from(QDeviceModel.deviceModel).where(QDeviceModel.deviceModel.deviceModelName.eq(model)).exists();
	}

	@Override
	public boolean deviceType(Long deviceTypeId) {
		query = new JPAQuery(em);
		return query.from(QDeviceTypeMaster.deviceTypeMaster)
				.where(QDeviceTypeMaster.deviceTypeMaster.deviceTypeId.eq(deviceTypeId)).exists();
	}

	public boolean routeNameValidation(String routeName) {
		query = new JPAQuery(em);
		return query.from(QRouteMaster.routeMaster).where(QRouteMaster.routeMaster.routeName.eq(routeName)).exists();
	}

	@Override
	public boolean validateOrganizationId(Long orgId) {
		query = new JPAQuery(em);
		return query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.orgId.eq(orgId)).exists();
	}

	@Override
	public boolean validateDepotId(Long depotId) {
		query = new JPAQuery(em);
		return query.from(QDepotMaster.depotMaster)
				.where(QDepotMaster.depotMaster.depotId.eq(depotId)).exists();
	}

	@Override
	public boolean deviceTypeIdValidation(Long deviceTypeId) {
		return deviceType(deviceTypeId);
	}

	@Override
	public boolean validateStopName(String stopName) {
		query = new JPAQuery(em);
		return query.from(QStopProfile.stopProfile)
				.where(QStopProfile.stopProfile.stopName.toLowerCase().eq(stopName.toLowerCase())).exists();
	}

	@Override
	public boolean validateDepotName(String depotName) {
		query = new JPAQuery(em);
		return query.from(QDepotMaster.depotMaster).where(QDepotMaster.depotMaster.depotName.eq(depotName))
				.exists();
	}

	@Override
	public boolean deviceManufacturerIdValidation(Long deviceManufacturerCode) {
		query = new JPAQuery(em);
		return query.from(QDeviceManufacturerMaster.deviceManufacturerMaster)
				.where(QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturerId
						.eq(deviceManufacturerCode))
				.exists();
	}

	@Override
	public boolean fareNameValidation(String fareName) {
		query = new JPAQuery(em);
		return query.from(QFareMaster.fareMaster).where(QFareMaster.fareMaster.fareName.eq(fareName)).exists();
	}

	@Override
	public boolean deviceManufacturerValidation(String deviceManufacturer) {
		query = new JPAQuery(em);
		return query.from(QDeviceManufacturerMaster.deviceManufacturerMaster)
				.where(QDeviceManufacturerMaster.deviceManufacturerMaster.deviceManufacturer.eq(deviceManufacturer))
				.exists();
	}

	@Override
	public boolean validateProductId(Long productId) {
		query = new JPAQuery(em);
		return query.from(QProductManagement.productManagement)
				.where(QProductManagement.productManagement.productId.eq(productId)).exists();
	}

	@Override
	public boolean validateDiscountId(Long discountId) {
		query = new JPAQuery(em);
		return query.from(QDiscountMaster.discountMaster)
				.where(QDiscountMaster.discountMaster.discountId.eq(discountId)).exists();
	}

	@Override
	public boolean validateOperatorId(Long operatorId) {
		query = new JPAQuery(em);
		return query.from(QOperator.operator).where(QOperator.operator.operatorId.eq(operatorId)).exists();
	}

	@Override
	public boolean stageIdValidation(Long stageId) {
		query = new JPAQuery(em);
		return query.from(QStageMaster.stageMaster).where(QStageMaster.stageMaster.stageId.eq(stageId))
				.exists();
	}

	@Override
	public boolean validateOrganizationName(String organizationName) {
		query = new JPAQuery(em);
		return query.from(QOrganizationMaster.organizationMaster)
				.where(QOrganizationMaster.organizationMaster.organizationName.toLowerCase()
						.eq(organizationName.toLowerCase()))
				.exists();
	}

	@Override
	public boolean validatePtoName(String ptoName) {
		query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster).where(QPtoMaster.ptoMaster.ptoName.eq(ptoName)).exists();
	}

	@Override
	public boolean validateOperatorName(String operatorName) {
		query = new JPAQuery(em);
		return query.from(QOperator.operator).where(QOperator.operator.operatorName.eq(operatorName)).exists();
	}

	@Override
	public boolean validateVehicleTypeName(String vehicleTypeName) {
		query = new JPAQuery(em);
		return query.from(QVehicleTypeProfile.vehicleTypeProfile)
				.where(QVehicleTypeProfile.vehicleTypeProfile.vehicleType.eq(vehicleTypeName)).exists();
	}

	@Override
	public boolean validateVehicleManufacturerName(String vehicleManufName) {
		query = new JPAQuery(em);
		return query.from(QVehicleManufacturer.vehicleManufacturer)
				.where(QVehicleManufacturer.vehicleManufacturer.vehicleManufacturerName.eq(vehicleManufName)).exists();
	}

	@Override
	public boolean validateVehicleModelName(String vehicleModelName) {
		query = new JPAQuery(em);
		return query.from(QVehicleModel.vehicleModel).where(QVehicleModel.vehicleModel.vehicleModelName
				.eq(vehicleModelName).and(QVehicleModel.vehicleModel.status.notLike("Terminated"))).exists();
	}

	@Override
	public boolean isValidUserName(String userName) {
		query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials)
				.where(QUserCredentials.userCredentials.userStatus.ne(Status.TERMINATED.getValue()),
						QUserCredentials.userCredentials.userName.eq(userName))
				.exists();
	}

	@Override
	public boolean isDeviceModelExists(Long deviceModelId) {
		query = new JPAQuery(em);
		return query.from(QDeviceOnboardMaster.deviceOnboardMaster)
				.where(QDeviceOnboardMaster.deviceOnboardMaster.deviceModelId.eq(deviceModelId),
						QDeviceOnboardMaster.deviceOnboardMaster.status.ne(Status.TERMINATED.getValue())).exists();
	}
	
	@Override
	public boolean deviceIMEINumber(String deviceIMEINumber) {
		query = new JPAQuery(em);
		return query.from(QDeviceModel.deviceModel).where(QDeviceModel.deviceModel.deviceIMEINumber
				.eq(deviceIMEINumber).and(QDeviceModel.deviceModel.status.notLike(Status.TERMINATED.getValue()))).exists();
	}

	@Override
	public boolean isUserActive(String userId) {
		query = new JPAQuery(em);
		return query.from(QUserCredentials.userCredentials).where(QUserCredentials.userCredentials.email
				.eq(userId).and(QUserCredentials.userCredentials.userStatus.like(Status.ACTIVE.getValue()))).exists();
	}

	@Override
	public boolean validatePtoMasterID(String ptoMasterId) {
		query = new JPAQuery(em);
		return query.from(QPtoMaster.ptoMaster).where(QPtoMaster.ptoMaster.ptoMasterId
				.eq(Long.valueOf(ptoMasterId))).exists();
	}

}
