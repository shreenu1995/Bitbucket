<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Edit Vehicle Onboard</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/vehicle.js"></script>

</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!-- Navigation Bar Start --->
		<div id="header"></div>
		<!-- Navigation Bar End -->
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Manage</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Vehicle</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Vehicle Onboard</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>
			<form action="vehicle-onboard-search" name="cancelForm" method="post"
				value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<form:form modelAttribute="vehicleOnboardEditRequest"
							action="vehicle-onboard-update" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Vehicle Onboard Code</span></legend>
											<form:input id="vehicleOnboardingId"
												name="vehicleOnboardingId" path="vehicleOnboardingId"
												class="form-control" onblur="" readonly="true" value=""
												maxlength="50"></form:input>
											<div class="deviceTypeCodeMsgDiv">
												<span id="deviceTypeCodeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>
												Organization Name</legend>
											<form:select id="organizationId" name="organizationId"
												path="organizationId"
												onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
												onblur="validateOrganizationName();" Class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${organizationList}"
													var="organizationList">
													<form:option value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="organizationNameMsgDiv" style="color: red;">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
												Name</legend>
											<form:select id="ptoId" path="ptoId" name="ptoId"
												class="form-control" onblur="validatePtoOperationList();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoListData}"
													var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoOperationMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										</c:when>
													<c:otherwise>
														<fieldset class="col-sm-3 form-group">
															<legend class = "custom-legend"><span class="requiredFiled">*</span>
																Organization Name</legend>
															<form:select id="organizationId" name="organizationId"
																path="organizationId" onblur="validateOrganizationName();"
																onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
																Class="form-control">
																<c:forEach items="${organizationList}"
																	var="organizationList">
																	<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg">
																<span id="organizationNameMsgDiv" style="color: red;">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3 form-group">
															<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
																Name</legend>
															<form:select id="ptoId" name="ptoId" path="ptoId"
																class="form-control" onblur="validatePtoOperationList();">
																<c:forEach items="${ptoListData}" var="ptoListData">
																	<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg">
																<span id="ptoOperationMsgDiv" class="red-color-error">&nbsp;</span>
															</div>
														</fieldset>
													</c:otherwise>
												</c:choose>
										

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
												Type Name</legend>
											<form:select id="vehicleTypeId" path="vehicleTypeId"
												name="vehicleTypeId" class="form-control" type="text" value=""
												maxlength="50" autocomplete="off"
												onchange="doAjaxFetchVehicleManufacturer(this.value)"
												onblur="validateVehicleTypeName();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${vehicleTypeList}" var="vehicleTypeList">
													<form:option value="${vehicleTypeList.vehicleTypeId}">${vehicleTypeList.vehicleTypeName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="vehicleTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
												Manufacturer Name</legend>
											<form:select id="vehicleManufacturerId"
												path="vehicleManufacturerId"
												name="vehicleManufacturerId" class="form-control"
												onchange="doAjaxFetchVehicleModel(this.value);"
												onblur="validateVehicleManufacturerName();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${vehicleManufList}"
													var="vehicleManufList">
													<form:option value="${vehicleManufList.vehicleManufacturerId}">${vehicleManufList.vehicleManufacturerName}</form:option>
												</c:forEach>

											</form:select>
											<div class="discriptionErrorMsg">
												<span id="vehicleManufacturerNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
												Model Name</legend>
											<form:select id="vehicleModelId" path="vehicleModelId"
												name="vehicleModelId" class="form-control"
												onblur="validateVehicleModelName();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${vehicleModelList}"
													var="vehicleModelList">
													<form:option value="${vehicleModelList.vehicleModelId}">${vehicleModelList.vehicleModelName}</form:option>
												</c:forEach>

											</form:select>
											<div class="discriptionErrorMsg">
												<span id="vehicleModelnameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

									</div>
								</div>
								<!-- Operator section end-->
								<!-- Form Button End -->

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0"
											style="float: right;">
											<input type="submit" id="vehicleOnboardUpdate" value="Update"
												onclick="return validateVehicleOnboardEditSubmit();"
												class="form-control button pull-right"> <a
												href="javascript:cancelButton()"
												class="form-control button pull-right"> <legend class = "custom-legend-button">Cancel </legend></a>
										</div>
									</fieldset>
								</div>
								<!-- Form Button End -->

							</div>
						</form:form>
						<!-- Page Form End -->
					</div>
				</div>

			</div>
			<!-- Content Block End -->
		</div>
		</article>
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
		<div id="footer"></div>
	</div>
	<!-- Popup container End -->
</div>
<!--Container block End -->
<!-- Main Body Wrapper End -->
</body>
</html>