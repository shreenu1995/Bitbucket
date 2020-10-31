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
<title>Edit Vehicle Manufacturer</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
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
					class="breadcrumb-text">Vehicle Manufacturer</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>
			<form action="vehicle-manuf-search" name="cancelForm" method="post"
				value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>


			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="vehicleManufEditRequest"
							action="vehicle-manuf-update" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
												class="imp-star">Vehicle Manufacturer Code</span></legend>
											<form:input id="vehicleManufacturerId"
												name="vehicleManufacturerId" path="vehicleManufacturerId"
												class="form-control" onblur="" readonly="true" type="text"
												value="" maxlength="50"></form:input>
											<div class="deviceTypeCodeMsgDiv">
												<span id="deviceTypeCodeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Vehicle
												Type List</legend>
											<form:select id="vehicleTypeId" path="vehicleTypeId" name="vehicleTypeId"
												onblur="validateVehicleTypeName();" cssClass="form-control"
												onchange="doAjaxFetchVehicleManufacturer(this.value)">
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
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Vehicle
												Manufacturer Name</legend>
											<form:input id="vehicleManufacturerName"
												path="vehicleManufacturerName"
												onblur="validateVehicleManufactureName();"
												name="vehicleManufacturerName" class="form-control"
												type="text" value="" maxlength="50" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="vehicleManufacturerNameMsgDiv"
													class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Description</legend>
											<form:input id="description" name="description"
												path="description" class="form-control"
												onblur="validateDiscription();" type="text" value=""
												maxlength="250" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="descriptionMsgDiv" class="red-color-error">&nbsp;</span>
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
											<input type="submit" id="vehicleManufacturerUpdate"
												value="Update"
												onclick="return validateVehicleManufacturerEditSubmit();"
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