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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>View Device Model</title> <
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/deviceModelReg.js"></script>
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
					class="breadcrumb-text">Device</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Device Model</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">View</span>
			</div>
			<form action="device-model-search" name="cancelForm" method="post"
				value="cancel">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="deviceModelViewRequest"
							action="device-model-view" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												Type Name </legend>
											<form:input cssClass="form-control" path="deviceTypeName"
												id="deviceTypeName" name="deviceTypeName" maxlength="50"
												readonly="true" autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="deviceTypeNameDiv" class="red-color-error"></span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												Manufacturer </legend>
											<form:input cssClass="form-control" path="deviceManufacturer"
												id="deviceManufacturer" name="deviceManufacturer"
												maxlength="50" readonly="true" autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="deviceManufacturerMsgDiv" class="red-color-error"></span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												Model</legend>
											<form:input cssClass="form-control" path="deviceModel"
												id="deviceModel" name="deviceModel" readonly="true"
												maxlength="50" autocomplete='off'></form:input>

											<div class="discriptionErrorMsg">
												<span id="deviceModelMsgDiv" class="red-color-error"></span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												IMEI Number </legend>
											<form:input cssClass="form-control" path="deviceIMEINumber"
												id="deviceIMEINumber" name="deviceIMEINumber" maxlength="50"
												readonly="true" autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="deviceIMEINumberMsgDiv" class="red-color-error"></span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
												Description </legend>
											<form:input cssClass="form-control" id="description"
												name="description" path="description" maxlength="50"
												readonly="true" autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="descriptionMsgDiv" class="red-color-error"></span>
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
											<a href="javascript:cancelButton()"
												class="form-control button pull-right"><legend class = "custom-legend-button"> Cancel </legend></a>
										</div>
									</fieldset>
								</div>
								<!-- Form Button End -->

							</div>
						</form:form>
					</div>
				</div>
				<!-- Page Form End -->
			</div>
		</div>

		</article>
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
		<div id="footer"></div>
	</div>
</div>
</body>
</html>