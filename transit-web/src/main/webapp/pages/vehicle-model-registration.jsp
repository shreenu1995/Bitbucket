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
<title>Vehicle Model Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<link href="../css/main.css" rel="stylesheet">
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
						class="breadcrumb-text">Vehicle Model</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Create</span>
				</div>
				<span class="success-msg help-block align-center" id="success">${success}</span>
				<span class="has-error help-block align-center" id="error">${error}</span>
				<div class="tab-header-container  marginL40">
					<a href="vehicle-model-search">Search</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="vehicle-model-registration">Create</a>
				</div>

				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">

							<!-- Page Form Start -->
							<form:form modelAttribute="vehicleModelRegistrationRequest"
								action="vehicle-model-registration" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<div class="row">
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Type List</legend>

												<form:select id="vehicleTypeId" path="vehicleTypeId" name="vehicleTypeId"
													class="form-control"
													onchange="doAjaxFetchVehicleManufacturer(this.value)"
													onblur="validateVehicleTypeName()">
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
													onblur="validateVehicleManufacturerName()">
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
													Model Name</legend> <input id="vehicleModelName"
													name="vehicleModelName" class="form-control"
													onblur="validateVehicleModelName()" type="text" value=""
													maxlength="50" autocomplete="off">
												<div class="discriptionErrorMsg">
													<span id="vehicleModelnameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Registration Number</legend> <input id="vehicleRegistrationNumber"
													name="vehicleRegistrationNumber" class="form-control"
													onblur="validateVehicleRegistrationNumber()" type="text"
													value="" maxlength="13" autocomplete="off">
												<div class="discriptionErrorMsg">
													<span id="vehicleRegistrationNumberMsgDiv"
														class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Engine Number</legend> <input id="vehicleEngineNumber"
													name="vehicleEngineNumber" class="form-control"
													onblur="validateVehicleEngineNumber()" type="text" value=""
													maxlength="13" autocomplete="off">
												<div class="discriptionErrorMsg">
													<span id="vehicleEngineNumberMsgDiv"
														class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Vehicle
													Chassis Number</legend> <input id="vehicleChassisNumber"
													name="vehicleChassisNumber" class="form-control"
													onblur="validateVehicleChassisNumber()" type="text"
													value="" maxlength="13" autocomplete="off">
												<div class="discriptionErrorMsg">
													<span id="vehicleChassisNumberMsgDiv"
														class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>


										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Description</legend>
												<input id="description" name="description"
													class="form-control" onblur="validateDiscription();"
													type="text" value="" maxlength="250" autocomplete="off">
												<div class="discriptionErrorMsg">
													<span id="descriptionMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

									</div>
									<!-- Form Button Start -->
									<div class="role-makerchecker-btn">
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" value="Create"
													onclick="return validateVehicleModelSubmit();"
													class="form-control button pull-right"> <a
													href="vehicle-model-registration"
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

		</div>
		<!--Container block End -->
	</div>
	<!-- Main Body Wrapper End -->

	<!-- Loading -->
</body>
</html>