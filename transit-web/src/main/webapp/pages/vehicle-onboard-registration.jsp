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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Vehicle Onboard Create</title>
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
<body>
	<!-- Main Body Wrapper Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid cw-admin-search-widget">
			<!-- Navigation Block Starts -->
			<jsp:include page="header.jsp" />
			<!-- Navigation Block Ends -->
			<article>
			<div class="col-xs-12 content-wrapper">
				<div class="breadCrumb">
					<span class="breadcrumb-text">Manage</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Vehicle</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Vehicle Onboard</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Create</span>
				</div>
				<span class="success-msg help-block align-center" id="success">${success}</span>
				<span class="has-error help-block align-center" id="error">${error}</span>
				<div class="tab-header-container  marginL40">
					<a href="vehicle-onboard-search">Search</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="vehicle-onboard-registration">Create</a>
				</div>

				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">

							<!-- Page Form Start -->
							<form:form modelAttribute="vehicleOnboardRequest"
								action="vehicle-onboard-registration" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<div class="row">
									<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>
													Organization Name</legend>
												<form:select id="orgId" name="orgId"
													path="orgId"
													onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
													onblur="validateOrgId();" Class="form-control">
													 <option value="">..:Select:..</option>
													<c:forEach items="${organizationList}"
														var="organizationList">
														<form:option value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
													</c:forEach>
												</form:select>
												<div class="orgIdMsgDiv">
													<span id="orgIdMsgDiv" style="color: red;">&nbsp;</span>
												</div>
											</fieldset>
										</div>
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
													Name </legend>
												<form:select id="ptoId" name="ptoId" path="ptoId"
													onblur="validatePtoOperationList();" class="form-control">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${ptoListData}" var="ptoListData">
														<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="ptoOperationMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>
										</c:when>
										<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="orgId" name="orgId"
														path="orgId" Class="form-control" onblur="validateOrgId();">
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="orgIdMsgDiv">
														<span id="orgIdMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
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
										
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Type List</legend>
												<form:select id="vehicleTypeId" name="vehicleTypeId"
													path="vehicleTypeId" class="form-control"
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
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Manufacturer List</legend>
												<form:select id="vehicleManufacturerId"
													name="vehicleManufacturerId"
													path="vehicleManufacturerId" class="form-control"
													onchange="doAjaxFetchVehicleModel(this.value);"
													onblur="validateVehicleManufacturerName();">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${vehicleManufList}"
														var="vehicleManufList">
														<form:option value="${vehicleManufList.vehicleManufacturerId}">${vehicleManufList.vehicleManufacturerName}</form:option>
													</c:forEach>

												</form:select>
												<div class="discriptionErrorMsg">
													<span id="vehicleManufacturerNameMsgDiv"
														class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Model List</legend>
												<form:select id="vehicleModelId" path="vehicleModelId"
													name="vehicleModelId" class="form-control"
													onblur="validateVehicleModelName()">
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
									<!-- Form Button Start -->
									<div class="role-makerchecker-btn">
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" id="vehicle-onboard-search"
													value="Create" class="form-control button pull-right"
													onclick="return validateVehicleOnboardSubmit();"> <a
													href="vehicle-onboard-registration"
													class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend></a>
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

			<jsp:include page="footer.jsp" />
              <div id="footer"></div>
		</div>
		<!--Container block End -->
	</div>
	<!-- Main Body Wrapper End -->

	<!-- Loading -->
	<div id="wait"
		style="display: none; width: 69px; height: 89px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='images/ajax-loader.gif' alt="ajax-loader" width="64" height="64" /><br>
	</div>

	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Create</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>