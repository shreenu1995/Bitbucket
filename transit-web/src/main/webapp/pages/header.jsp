<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.chatak.transit.afcs.server.constants.Constants"%>

<spring:message code="afcs.service.transit.dashboard.feature.id"
	var="dashboard"></spring:message>

<spring:message code="afcs.services.transit.manage.feature.id"
	var="manage"></spring:message>
	
<spring:message code="afcs.services.transit.organization.feature.id"
	var="organization"></spring:message>
<spring:message
	code="afcs.services.transit.organization.create.feature.id"
	var="createOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.edit.feature.id" var="editOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.view.feature.id" var="viewOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.terminate.feature.id"
	var="terminateOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.suspend.feature.id"
	var="suspendOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.active.feature.id"
	var="activeOrg"></spring:message>
<spring:message
	code="afcs.services.transit.organization.search.feature.id"
	var="searchOrg"></spring:message>

<spring:message code="afcs.services.transit.pto.feature.id" var="pto"></spring:message>

<spring:message code="afcs.services.transit.pto.create.feature.id"
	var="createPto"></spring:message>
<spring:message code="afcs.services.transit.pto.edit.feature.id"
	var="editPto"></spring:message>
<spring:message code="afcs.services.transit.pto.view.feature.id"
	var="viewPto"></spring:message>
<spring:message code="afcs.services.transit.pto.terminate.feature.id"
	var="terminatePto"></spring:message>
<spring:message code="afcs.services.transit.pto.suspend.feature.id"
	var="suspendPto"></spring:message>
<spring:message code="afcs.services.transit.pto.active.feature.id"
	var="activePto"></spring:message>
<spring:message code="afcs.services.transit.pto.search.feature.id"
	var="searchPto"></spring:message>

<spring:message code="afcs.services.transit.role.feature.id" var="role"></spring:message>

<spring:message code="afcs.services.transit.role.create.feature.id"
	var="createRole"></spring:message>
<spring:message code="afcs.services.transit.role.edit.feature.id"
	var="editRole"></spring:message>
<spring:message code="afcs.services.transit.role.view.feature.id"
	var="viewRole"></spring:message>
<spring:message code="afcs.services.transit.role.terminate.feature.id"
	var="terminateRole"></spring:message>
<spring:message code="afcs.services.transit.role.suspend.feature.id"
	var="suspendRole"></spring:message>
<spring:message code="afcs.services.transit.role.active.feature.id"
	var="activeRole"></spring:message>
<spring:message code="afcs.services.transit.role.search.feature.id"
	var="searchRole"></spring:message>

<spring:message code="afcs.services.transit.users.feature.id" var="user"></spring:message>

<spring:message code="afcs.services.transit.users.create.feature.id"
	var="createUser"></spring:message>
<spring:message code="afcs.services.transit.users.edit.feature.id"
	var="editUser"></spring:message>
<spring:message code="afcs.services.transit.users.view.feature.id"
	var="viewUser"></spring:message>
<spring:message code="afcs.services.transit.users.terminate.feature.id"
	var="terminateUser"></spring:message>
<spring:message code="afcs.services.transit.users.suspend.feature.id"
	var="suspendUser"></spring:message>
<spring:message code="afcs.services.transit.users.active.feature.id"
	var="activeUser"></spring:message>
<spring:message code="afcs.services.transit.users.search.feature.id"
	var="searchUser"></spring:message>

<spring:message code="afcs.services.transit.fee.feature.id" var="fee"></spring:message>

<spring:message code="afcs.services.transit.fee.create.feature.id"
	var="createFee"></spring:message>
<spring:message code="afcs.services.transit.fee.edit.feature.id"
	var="editFee"></spring:message>
<spring:message code="afcs.services.transit.fee.view.feature.id"
	var="viewFee"></spring:message>
<spring:message code="afcs.services.transit.fee.terminate.feature.id"
	var="terminateFee"></spring:message>
<spring:message code="afcs.services.transit.fee.suspend.feature.id"
	var="suspendFee"></spring:message>
<spring:message code="afcs.services.transit.fee.active.feature.id"
	var="activeFee"></spring:message>
<spring:message code="afcs.services.transit.fee.search.feature.id"
	var="searchFee"></spring:message>

<spring:message code="afcs.services.transit.product.feature.id"
	var="product"></spring:message>

<spring:message code="afcs.services.transit.product.create.feature.id"
	var="createProduct"></spring:message>
<spring:message code="afcs.services.transit.product.edit.feature.id"
	var="editProduct"></spring:message>
<spring:message code="afcs.services.transit.product.view.feature.id"
	var="viewProduct"></spring:message>
<spring:message
	code="afcs.services.transit.product.terminate.feature.id"
	var="terminateProduct"></spring:message>
<spring:message code="afcs.services.transit.product.suspend.feature.id"
	var="suspendProduct"></spring:message>
<spring:message code="afcs.services.transit.product.active.feature.id"
	var="activeProduct"></spring:message>
<spring:message code="afcs.services.transit.product.search.feature.id"
	var="searchProduct"></spring:message>

<spring:message code="afcs.services.transit.depot.feature.id"
	var="depot"></spring:message>

<spring:message code="afcs.services.transit.depot.create.feature.id"
	var="createDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.edit.feature.id"
	var="editDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.view.feature.id"
	var="viewDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.terminate.feature.id"
	var="terminateDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.suspend.feature.id"
	var="suspendDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.active.feature.id"
	var="activeDepot"></spring:message>
<spring:message code="afcs.services.transit.depot.search.feature.id"
	var="searchDepot"></spring:message>

<spring:message code="afcs.services.transit.device.type.feature.id"
	var="deviceType"></spring:message>

<spring:message
	code="afcs.services.transit.device.type.create.feature.id"
	var="createDeviceType"></spring:message>
<spring:message code="afcs.services.transit.device.type.edit.feature.id"
	var="editDeviceType"></spring:message>
<spring:message code="afcs.services.transit.device.type.view.feature.id"
	var="viewDeviceType"></spring:message>
<spring:message
	code="afcs.services.transit.device.type.terminate.feature.id"
	var="terminateDeviceType"></spring:message>
<spring:message
	code="afcs.services.transit.device.type.suspend.feature.id"
	var="suspendDeviceType"></spring:message>
<spring:message
	code="afcs.services.transit.device.type.active.feature.id"
	var="activeDeviceType"></spring:message>
<spring:message
	code="afcs.services.transit.device.type.search.feature.id"
	var="searchDeviceType"></spring:message>

<spring:message
	code="afcs.services.transit.device.manufacturer.feature.id"
	var="deviceManufacture"></spring:message>

<spring:message
	code="afcs.services.transit.device.manufacturer.create.feature.id"
	var="createDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.edit.feature.id"
	var="editDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.view.feature.id"
	var="viewDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.terminate.feature.id"
	var="terminateDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.suspend.feature.id"
	var="suspendDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.active.feature.id"
	var="activeDeviceManufacture"></spring:message>
<spring:message
	code="afcs.services.transit.device.manufacturer.search.feature.id"
	var="searchDeviceManufacture"></spring:message>

<spring:message code="afcs.services.transit.device.model.feature.id"
	var="deviceModel"></spring:message>

<spring:message
	code="afcs.services.transit.device.model.create.feature.id"
	var="createDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.edit.feature.id"
	var="editDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.view.feature.id"
	var="viewDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.terminate.feature.id"
	var="terminateDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.suspend.feature.id"
	var="suspendDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.active.feature.id"
	var="activeDeviceModel"></spring:message>
<spring:message
	code="afcs.services.transit.device.model.search.feature.id"
	var="searchDeviceModel"></spring:message>

<spring:message
	code="afcs.services.transit.device.onboarding.feature.id"
	var="deviceOnboarding"></spring:message>

<spring:message
	code="afcs.services.transit.device.onboarding.create.feature.id"
	var="createDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.edit.feature.id"
	var="editDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.view.feature.id"
	var="viewDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.terminate.feature.id"
	var="terminateDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.suspend.feature.id"
	var="suspendDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.active.feature.id"
	var="activeDeviceOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.device.onboarding.search.feature.id"
	var="searchDeviceOnboarding"></spring:message>

<spring:message code="afcs.services.transit.discount.feature.id"
	var="discount"></spring:message>

<spring:message code="afcs.services.transit.discount.create.feature.id"
	var="createDiscount"></spring:message>
<spring:message code="afcs.services.transit.discount.edit.feature.id"
	var="editDiscount"></spring:message>
<spring:message code="afcs.services.transit.discount.view.feature.id"
	var="viewDiscount"></spring:message>
<spring:message
	code="afcs.services.transit.discount.terminate.feature.id"
	var="terminateDiscount"></spring:message>
<spring:message code="afcs.services.transit.discount.suspend.feature.id"
	var="suspendDiscount"></spring:message>
<spring:message code="afcs.services.transit.discount.active.feature.id"
	var="activeDiscount"></spring:message>
<spring:message code="afcs.services.transit.discount.search.feature.id"
	var="searchDiscount"></spring:message>

<spring:message code="afcs.services.transit.vehicle.type.feature.id"
	var="vehicleType"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.type.create.feature.id"
	var="createVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.edit.feature.id"
	var="editVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.view.feature.id"
	var="viewVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.terminate.feature.id"
	var="terminateVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.suspend.feature.id"
	var="suspendVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.active.feature.id"
	var="activeVehicleType"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.type.search.feature.id"
	var="searchVehicleType"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.manufacturer.feature.id"
	var="vehicleManufacturer"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.manufacturer.create.feature.id"
	var="createVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.edit.feature.id"
	var="editVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.view.feature.id"
	var="viewVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.terminate.feature.id"
	var="terminateVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.suspend.feature.id"
	var="suspendVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.active.feature.id"
	var="activeVehicleManufacturer"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.manufacturer.search.feature.id"
	var="searchVehicleManufacturer"></spring:message>

<spring:message code="afcs.services.transit.vehicle.model.feature.id"
	var="vehicleModel"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.model.create.feature.id"
	var="createVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.edit.feature.id"
	var="editVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.view.feature.id"
	var="viewVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.terminate.feature.id"
	var="terminateVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.suspend.feature.id"
	var="suspendVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.active.feature.id"
	var="activeVehicleModel"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.model.search.feature.id"
	var="searchVehicleModel"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.onboarding.feature.id"
	var="vehicleOnboarding"></spring:message>

<spring:message
	code="afcs.services.transit.vehicle.onboarding.create.feature.id"
	var="createVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.edit.feature.id"
	var="editVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.view.feature.id"
	var="viewVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.terminate.feature.id"
	var="terminateVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.suspend.feature.id"
	var="suspendVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.active.feature.id"
	var="activeVehicleOnboarding"></spring:message>
<spring:message
	code="afcs.services.transit.vehicle.onboarding.search.feature.id"
	var="searchVehicleOnboarding"></spring:message>

<spring:message code="afcs.services.transit.setup.feature.id"
	var="setUp"></spring:message>

<spring:message code="afcs.services.transit.transit.master.feature.id"
	var="transitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.create.feature.id"
	var="createTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.edit.feature.id"
	var="editTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.view.feature.id"
	var="viewTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.terminate.feature.id"
	var="terminateTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.suspend.feature.id"
	var="suspendTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.active.feature.id"
	var="activeTransitMaster"></spring:message>
<spring:message
	code="afcs.services.transit.transit.master.search.feature.id"
	var="searchTransitMaster"></spring:message>

<spring:message
	code="afcs.services.transit.software.maintenance.feature.id"
	var="softwareMaintenance"></spring:message>

<spring:message
	code="afcs.services.transit.software.maintenance.create.feature.id"
	var="createSoftwareMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.software.maintenance.edit.feature.id"
	var="editSoftwareMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.software.maintenance.view.feature.id"
	var="viewSoftwareMaintenance"></spring:message>

<spring:message
	code="afcs.services.transit.software.maintenance.terminate.feature.id"
	var="terminateSoftwareMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.software.maintenance.suspend.feature.id"
	var="suspendSoftwareMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.software.maintenance.active.feature.id"
	var="activeSoftwareMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.software.maintenance.search.feature.id"
	var="searchSoftwareMaintenance"></spring:message>

<spring:message
	code="afcs.services.transit.device.maintenance.feature.id"
	var="deviceMaintenance"></spring:message>

<spring:message
	code="afcs.services.transit.device.maintenance.create.feature.id"
	var="createDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.edit.feature.id"
	var="editDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.view.feature.id"
	var="viewDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.terminate.feature.id"
	var="terminateDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.suspend.feature.id"
	var="suspendDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.active.feature.id"
	var="activeDeviceMaintenance"></spring:message>
<spring:message
	code="afcs.services.transit.device.maintenance.search.feature.id"
	var="searchDeviceMaintenance"></spring:message>

<spring:message code="afcs.services.transit.ops.manifest.feature.id"
	var="opsManifest"></spring:message>

<spring:message
	code="afcs.services.transit.ops.manifest.create.feature.id"
	var="createOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.edit.feature.id"
	var="editOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.view.feature.id"
	var="viewOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.terminate.feature.id"
	var="terminateOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.suspend.feature.id"
	var="suspendOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.active.feature.id"
	var="activeOpsManifest"></spring:message>
<spring:message
	code="afcs.services.transit.ops.manifest.search.feature.id"
	var="searchOpsManifest"></spring:message>

<spring:message code="afcs.services.transit.masters.feature.id"
	var="masters"></spring:message>

<spring:message code="afcs.services.transit.route.feature.id"
	var="route"></spring:message>

<spring:message code="afcs.services.transit.route.create.feature.id"
	var="createRoute"></spring:message>
<spring:message code="afcs.services.transit.route.edit.feature.id"
	var="editRoute"></spring:message>
<spring:message code="afcs.services.transit.route.view.feature.id"
	var="viewRoute"></spring:message>
<spring:message code="afcs.services.transit.route.terminate.feature.id"
	var="terminateRoute"></spring:message>
<spring:message code="afcs.services.transit.route.suspend.feature.id"
	var="suspendRoute"></spring:message>
<spring:message code="afcs.services.transit.route.active.feature.id"
	var="activeRoute"></spring:message>
<spring:message code="afcs.services.transit.route.search.feature.id"
	var="searchRoute"></spring:message>

<spring:message code="afcs.services.transit.stage.feature.id"
	var="stage"></spring:message>

<spring:message code="afcs.services.transit.stage.create.feature.id"
	var="createStage"></spring:message>
<spring:message code="afcs.services.transit.stage.edit.feature.id"
	var="editStage"></spring:message>
<spring:message code="afcs.services.transit.stage.view.feature.id"
	var="viewStage"></spring:message>
<spring:message code="afcs.services.transit.stage.terminate.feature.id"
	var="terminateStage"></spring:message>
<spring:message code="afcs.services.transit.stage.suspend.feature.id"
	var="suspendStage"></spring:message>
<spring:message code="afcs.services.transit.stage.active.feature.id"
	var="activeStage"></spring:message>
<spring:message code="afcs.services.transit.stage.search.feature.id"
	var="searchStage"></spring:message>

<spring:message code="afcs.services.transit.stop.feature.id" var="stop"></spring:message>

<spring:message code="afcs.services.transit.stop.create.feature.id"
	var="createStop"></spring:message>
<spring:message code="afcs.services.transit.stop.edit.feature.id"
	var="editStop"></spring:message>
<spring:message code="afcs.services.transit.stop.view.feature.id"
	var="viewStop"></spring:message>
<spring:message code="afcs.services.transit.stop.terminate.feature.id"
	var="terminateStop"></spring:message>
<spring:message code="afcs.services.transit.stop.suspend.feature.id"
	var="suspendStop"></spring:message>
<spring:message code="afcs.services.transit.stop.active.feature.id"
	var="activeStop"></spring:message>
<spring:message code="afcs.services.transit.stop.search.feature.id"
	var="searchStop"></spring:message>

<spring:message code="afcs.services.transit.fare.feature.id" var="fare"></spring:message>

<spring:message code="afcs.services.transit.fare.create.feature.id"
	var="createFare"></spring:message>
<spring:message code="afcs.services.transit.fare.edit.feature.id"
	var="editFare"></spring:message>
<spring:message code="afcs.services.transit.fare.view.feature.id"
	var="viewFare"></spring:message>
<spring:message code="afcs.services.transit.fare.terminate.feature.id"
	var="terminateFare"></spring:message>
<spring:message code="afcs.services.transit.fare.suspend.feature.id"
	var="suspendFare"></spring:message>
<spring:message code="afcs.services.transit.fare.active.feature.id"
	var="activeFare"></spring:message>
<spring:message code="afcs.services.transit.fare.search.feature.id"
	var="searchFare"></spring:message>

<spring:message code="afcs.services.transit.operator.feature.id"
	var="operator"></spring:message>

<spring:message code="afcs.services.transit.operator.create.feature.id"
	var="createOperator"></spring:message>
<spring:message code="afcs.services.transit.operator.edit.feature.id"
	var="editOperator"></spring:message>
<spring:message code="afcs.services.transit.operator.view.feature.id"
	var="viewOperator"></spring:message>
<spring:message
	code="afcs.services.transit.operator.terminate.feature.id"
	var="terminateOperator"></spring:message>
<spring:message code="afcs.services.transit.operator.suspend.feature.id"
	var="suspendOperator"></spring:message>
<spring:message code="afcs.services.transit.operator.active.feature.id"
	var="activeOperator"></spring:message>
<spring:message code="afcs.services.transit.operator.search.feature.id"
	var="searchOperator"></spring:message>

<spring:message code="afcs.services.transit.interface.feature.id"
	var="interface"></spring:message>

<spring:message code="afcs.services.transit.issuer.feature.id"
	var="issuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.create.feature.id"
	var="createIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.edit.feature.id"
	var="editIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.view.feature.id"
	var="viewIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.terminate.feature.id"
	var="terminateIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.suspend.feature.id"
	var="suspendIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.active.feature.id"
	var="activeIssuer"></spring:message>
<spring:message code="afcs.services.transit.issuer.search.feature.id"
	var="searchIssuer"></spring:message>

<spring:message code="afcs.services.transit.payment.gateway.feature.id"
	var="paymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.create.feature.id"
	var="createPaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.edit.feature.id"
	var="editPaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.view.feature.id"
	var="viewPaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.terminate.feature.id"
	var="terminatePaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.suspend.feature.id"
	var="suspendPaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.active.feature.id"
	var="activePaymentGateway"></spring:message>
<spring:message
	code="afcs.services.transit.payment.gateway.search.feature.id"
	var="searchPaymentGateway"></spring:message>

<spring:message code="afcs.services.transit.report.feature.id"
	var="report"></spring:message>

<spring:message
	code="afcs.services.transit.transaction.data.report.feature.id"
	var="transactionDataReport"></spring:message>
<spring:message
	code="afcs.services.transit.transaction.data.report.generate.feature.id"
	var="generateTransactionDataReport"></spring:message>
<spring:message
	code="afcs.services.transit.transaction.data.report.search.feature.id"
	var="searchTransactionDataReport"></spring:message>

<spring:message
	code="afcs.services.transit.operator.session.report.feature.id"
	var="operatorSessionReport"></spring:message>
<spring:message
	code="afcs.services.transit.operator.session.report.generate.feature.id"
	var="generateOperatorSessionReport"></spring:message>
<spring:message
	code="afcs.services.transit.operator.session.report.search.feature.id"
	var="searchOperatorSessionReport"></spring:message>

<spring:message
	code="afcs.services.transit.passenger.analysis.feature.id"
	var="passengerAnalysis"></spring:message>
<spring:message
	code="afcs.services.transit.passenger.analysis.generate.feature.id"
	var="generatePassengerAnalysis"></spring:message>
<spring:message
	code="afcs.services.transit.passenger.analysis.search.feature.id"
	var="searchPassengerAnalysis"></spring:message>

<spring:message
	code="afcs.services.transit.pto.summary.report.feature.id"
	var="ptoSummaryReport"></spring:message>
<spring:message
	code="afcs.services.transit.pto.summary.report.generate.feature.id"
	var="generatePtoSummaryReport"></spring:message>
<spring:message
	code="afcs.services.transit.pto.summary.report.search.feature.id"
	var="searchPtoSummaryReport"></spring:message>

<spring:message
	code="afcs.services.transit.transaction.report.feature.id"
	var="transactionReport"></spring:message>
<spring:message
	code="afcs.services.transit.transaction.report.generate.feature.id"
	var="generateTransactionDataReport"></spring:message>
<spring:message
	code="afcs.services.transit.transaction.report.search.feature.id"
	var="searchTransactionReport"></spring:message>

<spring:message
	code="afcs.services.transit.master.file.download.feature.id"
	var="masterFileDownload"></spring:message>
<spring:message
	code="afcs.services.transit.master.file.download.generate.feature.id"
	var="generateMasterFileDownload"></spring:message>
<spring:message
	code="afcs.services.transit.master.file.download.search.feature.id"
	var="searchMasterFileDownload"></spring:message>
	
	
	<spring:message code="afcs.services.transit.device.tracking.feature.id"
	var="deviceTracking"></spring:message>

<spring:message code="afcs.services.transit.settings.feature.id"
	var="settings"></spring:message>
	
<spring:message
	code="afcs.services.transit.change.password.feature.id"
	var="changePassword"></spring:message>
<spring:message
	code="afcs.services.transit.change.password.edit.feature.id"
	var="editChangePassword"></spring:message>


<spring:message code="afcs.services.transit.black.listed.card.feature.id"
	var="blackListedCard"></spring:message>
	
<!-- Navigation Bar Start --->
<!--Header Block Start -->
<header class="col-sm-12 all-page-header"> <!--Header Logo Start -->
<div class="col-sm-3">
	<img src="images/logo.jpg" class="subPageLogo disable-drag"
		alt="ChatakWallet Heading" draggable="false">
</div>
<!--Header Logo End --> <!--Header Welcome Text and Logout button Start -->
<div class="col-sm-5 col-xs-offset-3">
	<div class="pull-right user-settings">
		<table>
			<tbody>
				<tr>
					<td><p>
							Welcome
							<%=session.getAttribute("userId")%>
							<span id="login-user"></span>
						</p></td>
					<td><a href="logout"><span
							class="glyphicon glyphicon-log-out"></span> Logout </a></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<!--Header Welcome Text and Logout button End --> </header>
<!--Header Block End -->



<head>
<title><%=Constants.PAGE_TITLE%></title>
<link href="../css/font-awesome.css" rel="stylesheet">
</head>

<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
<script src="../js/jquery.cookie.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/messages.js"></script>
<!-- This api is being used to get user timezone -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js"></script>

<!--Header Block End -->
<!--Navigation Block Start -->
<nav class="col-sm-12 nav-bar">
<ul class="navigation">
	<c:if test="${fn:contains(existingFeatures,dashboard)}">
		<li id="navListId1"><a href="dashboard"><spring:message
					code="navigation_panel.menu.Dashboard" /></a></li>
	</c:if>

	<c:if test="${fn:contains(existingFeatures,manage)}">
		<li id="navListId2" class="dropdown"><a href="#"
			class="dropdown-toggle"><spring:message
					code="navigation_panel.menu.Manage" /></a> <!--Navigation Block Sub Menu Start -->
			<ul class="dropdown-menu" role="menu">
				<li class="transperent-background"><a href="javascript:void(0)"></a></li>
				<c:if test="${fn:contains(existingFeatures,organization)}">
					<li><a href="organization-search"><spring:message
								code="navigation_panel.submenu.Organization" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,pto)}">
					<li><a href="pto-search"><spring:message
								code="navigation_panel.submenu.PTO" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,depot)}">
					<li><a href="depot-search"><spring:message
								code="navigation_panel.submenu.Depot" /></a></li>
				</c:if>

				<li class="subopen"><a href="#"> Vehicle </a>
					<div class="sublinksSub" style="display: none;">
						<ul class="sublinksalign">
							<c:if test="${fn:contains(existingFeatures,vehicleType)}">
								<li><a href="vehicle-type-search"><spring:message
											code="navigation_panel.submenu.VehicleType" /></a></li>
							</c:if>

							<c:if test="${fn:contains(existingFeatures,vehicleManufacturer)}">
								<li><a href="vehicle-manuf-search"><spring:message
											code="navigation_panel.submenu.VehicleManufacturer" /></a></li>
							</c:if>

							<c:if test="${fn:contains(existingFeatures,vehicleModel)}">
								<li><a href="vehicle-model-search"><spring:message
											code="navigation_panel.submenu.VehicleModel" /></a></li>
							</c:if>

							<c:if test="${fn:contains(existingFeatures,vehicleOnboarding)}">
								<li><a href="vehicle-onboard-search"><spring:message
											code="navigation_panel.submenu.VehicleOnboarding" /></a></li>
							</c:if>
						</ul>
					</div></li>

				<li class="subopen"><a href="#"> Device </a>
					<div class="sublinksSub" style="display: none;">
						<ul class="sublinksalign">
							<c:if test="${fn:contains(existingFeatures,deviceType)}">
								<li><a href="device-type-search"><spring:message
											code="navigation_panel.submenu.DeviceType" /></a></li>
							</c:if>

							<c:if test="${fn:contains(existingFeatures,deviceManufacturer)}">
								<li><a href="device-manufacturer-search"><spring:message
											code="navigation_panel.submenu.DeviceManufacturer" /></a></li>
							</c:if>

							<c:if test="${fn:contains(existingFeatures,deviceModel)}">

								<li><a href="device-model-search"><spring:message
											code="navigation_panel.submenu.DeviceModel" /></a></li>

							</c:if>
							<c:if test="${fn:contains(existingFeatures,deviceOnboarding)}">
								<li><a href="device-onboarding-search"><spring:message
											code="navigation_panel.submenu.DeviceOnboarding" /></a></li>
							</c:if>
						</ul>
					</div></li>

				<c:if test="${fn:contains(existingFeatures,fee)}">
					<li><a href="fee-search"><spring:message
								code="navigation_panel.submenu.Fee" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,product)}">
					<li><a href="product-search"><spring:message
								code="navigation_panel.submenu.Product" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,discount)}">
					<li><a href="discount-search"><spring:message
								code="navigation_panel.submenu.Discount" /></a></li>
				</c:if>

				<li><a href="blacklisted-account-search"><spring:message
							code="navigation_panel.submenu.Blacklisted" /></a></li>

			</ul> <!--Navigation Block Sub Menu End --></li>
	</c:if>


	<c:if test="${fn:contains(existingFeatures,masters)}">
		<li id="navListId2" class="dropdown"><a href="#"
			class="dropdown-toggle"><spring:message
					code="navigation_panel.menu.Masters" /></a> <!--Navigation Block Sub Menu Start -->
			<ul class="dropdown-menu" role="menu">
				<li class="transperent-background"><a href="javascript:void(0)"></a></li>

				<c:if test="${fn:contains(existingFeatures,route)}">
					<li><a href="route-search"><spring:message
								code="navigation_panel.submenu.Route" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,stage)}">
					<li><a href="stage-search"><spring:message
								code="navigation_panel.submenu.Stage" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,stop)}">
					<li><a href="stop-search"><spring:message
								code="navigation_panel.submenu.Stop" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,fare)}">
					<li><a href="fare-search"><spring:message
								code="navigation_panel.submenu.Fare" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,operator)}">
					<li><a href="operator-search"><spring:message
								code="navigation_panel.submenu.Operator" /></a></li>
				</c:if>

			</ul> <!--Navigation Block Sub Menu End --></li>

	</c:if>


	<c:if test="${fn:contains(existingFeatures,setUp)}">
		<li id="navListId2" class="dropdown"><a href="#"
			class="dropdown-toggle"><spring:message
					code="navigation_panel.menu.SetUp" /></a> <!--Navigation Block Sub Menu Start -->
			<ul class="dropdown-menu" role="menu">
				<li class="transperent-background"><a href="javascript:void(0)"></a></li>

				<c:if test="${fn:contains(existingFeatures,role)}">
					<li><a href="role-search"><spring:message
								code="navigation_panel.submenu.Role" /></a></li>
				</c:if>
				<c:if test="${fn:contains(existingFeatures,user)}">
					<li><a href="user-search"><spring:message
								code="navigation_panel.submenu.User" /></a></li>
				</c:if>


				<c:if test="${fn:contains(existingFeatures,transitMaster)}">
					<li><a href="transit-master-search"><spring:message
								code="navigation_panel.submenu.TransitMaster" /></a></li>
				</c:if>

				<c:if test="${fn:contains(existingFeatures,softwareMaintenance)}">
					<li><a href="setup-software-search"><spring:message
								code="navigation_panel.submenu.SoftwareMaintenance" /></a></li>
				</c:if>
				<!-- Required in future -->
				<%-- <c:if test="${fn:contains(existingFeatures,deviceMaintenance)}">
						<li><a href="prepaid-show-agent-search"><spring:message
									code="navigation_panel.submenu.CardMaintenance" /></a></li>
					</c:if> --%>

				<c:if test="${fn:contains(existingFeatures,opsManifest)}">
					<li><a href="opsmanifest-search"><spring:message
								code="navigation_panel.submenu.OpsManifest" /></a></li>
				</c:if>

			</ul> <!--Navigation Block Sub Menu End --></li>
	</c:if>


	<li id="navListId2" class="dropdown"><a href="#"
		class="dropdown-toggle"><spring:message
				code="navigation_panel.menu.Interface" /></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"><a href="javascript:void(0)"></a></li>


			<li><a href="issuer-search"><spring:message
						code="navigation_panel.submenu.Issuer" /></a></li>

			<li><a href="pg-onboarding-search"><spring:message
						code="navigation_panel.submenu.Pg" /></a></li>


		</ul> <!--Navigation Block Sub Menu End --></li>


	<li id="navListId2" class="dropdown"><a href="#"
		class="dropdown-toggle"><spring:message
				code="navigation_panel.menu.Reports" /></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"><a href="javascript:void(0)"></a></li>


			<%-- <li><a href="#"><spring:message
							code="navigation_panel.submenu.ErrorTransactionReport" /></a></li>

				<li><a href="#"><spring:message
							code="navigation_panel.submenu.RevenueDataReport" /></a></li>

				<li><a href="#"><spring:message
							code="navigation_panel.submenu.FileDownloadReport" /></a></li>

				<li><a href="#"><spring:message
							code="navigation_panel.submenu.DeviceAttendanceReport" /></a></li> --%>

			<li><a href="transaction-data-report"><spring:message
						code="navigation_panel.submenu.TransactionDataReport" /></a></li>

			<%-- <li><a href="#"><spring:message
							code="navigation_panel.submenu.TerminalSetupReport" /></a></li> --%>

			<li><a href="operator-session-report"><spring:message
						code="navigation_panel.submenu.OperatorSessionReport" /></a></li>
			<li><a href="passenger-analysis-search"><spring:message
						code="navigation_panel.submenu.PassengerAnalysisReport" /></a></li>
			<li><a href="pto-summary-search"><spring:message
						code="navigation_panel.submenu.PTOSummaryReport" /></a></li>

			<li><a href="master-file-download-search"><spring:message
						code="navigation_panel.submenu.MasterFileDownload" /></a></li>

		</ul> <!--Navigation Block Sub Menu End --></li>

	<li id="navListId2" class="dropdown"><a href="device-tracking"
		class="dropdown-toggle"><spring:message
				code="navigation_panel.menu.DeviceTracking" /></a> <!--Navigation Block Sub Menu Start -->
	</li>


	<li id="navListId2" class="dropdown"><a href="#"
		class="dropdown-toggle"><spring:message
				code="navigation_panel.menu.Settings" /></a> <!--Navigation Block Sub Menu Start -->
		<ul class="dropdown-menu" role="menu">
			<li class="transperent-background"><a href="javascript:void(0)"></a></li>


			<li><a href="change-password"><spring:message
						code="navigation_panel.submenu.ChangePassword" /></a></li>


		</ul> <!--Navigation Block Sub Menu End --></li>
</nav>
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
<script src="../js/jquery.cookie.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/messages.js"></script>
<!-- This api is being used to get user timezone -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jstimezonedetect/1.0.4/jstz.min.js"></script>
<script>
	$(document).ready(function() {
		$(".menu-icon").click(function() {
			$(".nav-bar ul").slideToggle();
		});

	});
	$(document).ready(
			function() {
				$('#runuploadfund', '#manualsettlement', '#agentautosweep',
						'#chargeAccountInactivityFee',
						'#chargeCardInactivityFee', '#recon',
						'#chargeMaintenanceFee', '#chargeCardMaintenanceFee',
						'#chargePeriodicFee', '#preauthexpire',
						'#cardBalanceReport', '#autoSweep',
						'#customerAutoSweep', '#autoGenerateCard',
						'#programManagerAccountSweep', '#partnerAccountSweep',
						'#expireloginsessions', '#runSchedulerForEnitityBal')
						.popup({
							blur : false
						});

				/* //for every 1000 milli sceonds function will execute
				var myVar=setInterval(function () {myTimer()}, 1000);
				var counter = 0;
				function myTimer() {
				   var date = new Date();
				   var getDay=date.getDayName();
				   var getMonth=date.getMonthName();
				   var getHours=date.getHours();
				   var getDate=date.getDate();
				   var getMin=date.getMinutes();
				   var geSec=date.getSeconds();
				   var getYear=date.getFullYear();
				   
				   date= getDay+" "+getMonth+" "+getDate+" "+getHours+":"+getMin+":"+geSec+" "+"CST"+" "+getYear;
				   document.getElementById("time").innerHTML = date;
				}	 */

				//to get day name and month name
				/* (function() {
					var days = [ webMessages.Sunday, webMessages.Monday,
							webMessages.Tuesday, webMessages.Wednesday,
							webMessages.Thursday, webMessages.Friday,
							webMessages.Saturday ];

					var months = [ webMessages.January, webMessages.February,
							webMessages.March, webMessages.April,
							webMessages.May, webMessages.June,
							webMessages.July, webMessages.August,
							webMessages.September, webMessages.October,
							webMessages.November, webMessages.December ];

					Date.prototype.getMonthName = function() {
						return months[this.getMonth()];
					};
					Date.prototype.getDayName = function() {
						return days[this.getDay()];
					};
				})(); */

			})

	function uploadClosePopup() {
		$('#runuploadfund').popup("hide");
	}
	function uploadOpenPopup() {
		$('#runuploadfund').popup("show");

	}
	function settlementClosePopup() {
		$('#manualsettlement').popup("hide");
	}
	function settlementOpenPopup() {
		$('#manualsettlement').popup("show");

	}
	function agentClosePopup() {
		$('#agentautosweep').popup("hide");
	}
	function agentOpenPopup() {
		$('#agentautosweep').popup("show");

	}
	function accInactiveClosePopup() {
		$('#chargeAccountInactivityFee').popup("hide");
	}
	function accInactiveOpenPopup() {
		$('#chargeAccountInactivityFee').popup("show");

	}
	function cardInactiveClosePopup() {
		$('#chargeCardInactivityFee').popup("hide");
	}
	function cardInactiveOpenPopup() {
		$('#chargeCardInactivityFee').popup("show");

	}
	function agentLoadClosePopup() {
		$('#recon').popup("hide");
	}
	function agentLoadOpenPopup() {
		$('#recon').popup("show");

	}
	function reconClosePopup() {
		$('#recon').popup("hide");
	}
	function reconOpenPopup() {
		$('#recon').popup("show");

	}
	function maintenanceClosePopup() {
		$('#chargeMaintenanceFee').popup("hide");
	}
	function maintenanceOpenPopup() {
		$('#chargeMaintenanceFee').popup("show");

	}
	function cardMaintenanceClosePopup() {
		$('#chargeCardMaintenanceFee').popup("hide");
	}
	function cardMaintenanceOpenPopup() {
		$('#chargeCardMaintenanceFee').popup("show");

	}
	function periodicClosePopup() {
		$('#chargePeriodicFee').popup("hide");
	}
	function periodicOpenPopup() {
		$('#chargePeriodicFee').popup("show");

	}
	function preauthClosePopup() {
		$('#preauthexpire').popup("hide");
	}
	function preauthOpenPopup() {
		$('#preauthexpire').popup("show");

	}
	function cardClosePopup() {
		$('#cardBalanceReport').popup("hide");
	}
	function cardOpenPopup() {
		$('#cardBalanceReport').popup("show");

	}
	function autoClosePopup() {
		$('#autoSweep').popup("hide");
	}
	function autoOpenPopup() {
		$('#autoSweep').popup("show");

	}
	function customerClosePopup() {
		$('#customerAutoSweep').popup("hide");
	}
	function customerOpenPopup() {
		$('#customerAutoSweep').popup("show");

	}
	function generateClosePopup() {
		$('#autoGenerateCard').popup("hide");
	}
	function generateOpenPopup() {
		$('#autoGenerateCard').popup("show");

	}
	function pmClosePopup() {
		$('#programManagerAccountSweep').popup("hide");
	}
	function pmOpenPopup() {
		$('#programManagerAccountSweep').popup("show");

	}
	function partnerClosePopup() {
		$('#partnerAccountSweep').popup("hide");
	}
	function partnerOpenPopup() {
		$('#partnerAccountSweep').popup("show");

	}
	function loginClosePopup() {
		$('#expireloginsessions').popup("hide");
	}
	function loginOpenPopup() {
		$('#expireloginsessions').popup("show");

	}
	function entityBalSchedulerClosePopup() {
		$('#runSchedulerForEnitityBal').popup("hide");
	}
	function entityBalSchedulerOpenPopup() {
		$('#runSchedulerForEnitityBal').popup("show");

	}

	$(document).ready(function() {
		getUserOffsetAndRegion();
	});

	function getUserOffsetAndRegion() {
		var offset = new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1];
		var region = jstz.determine().name();
		if (!(typeof region != 'undefined' && region != null && region != '')) {
			region = offset;
		}
		$('#deviceTimeZoneOffset').val(offset);
		$('#deviceTimeZoneRegion').val(region);
		$('#deviceTzOffset').val(offset);
		$('#deviceTzRegion').val(region);
	}

	$(document).ready(function() {
		$('#alertPopUp').popup({
			blur : false
		});
	});

	function closeAlertPopup() {
		$('#alertPopUp').popup("hide");
	}
	function showAlertPopup(message) {
		$('#alertPopUpMsg').text(message);
		$('#alertPopUp').popup("show");
	}
</script>

<!-- Common Alert PopUp STARTS here -->
<div id="alertPopUp" class="locatioin-list-popup">
	<h3 id="alertPopUpMsg"></h3>
	<div>&nbsp;</div>
	<div>&nbsp;</div>
	<div class="col-sm-12 form-action-buttons">
		<div class="col-sm-12">
			<input type="button" name="Ok" value="Ok"
				class="form-control button pull-right" onclick="closeAlertPopup()">
		</div>
	</div>
</div>
<!-- Pop Up box information starts here -->


<!--Navigation Block End -->
