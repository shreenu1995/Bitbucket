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
<title>Edit Device Manufacturer</title> <
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/deviceManufacturer.js"></script>
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
					class="breadcrumb-text">Device Manufacturer</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<span class="success-msg help-block align-center " id="success">${success}</span>
			<span class="has-error help-block align-center " id="error">${error}</span>
			<form action="device-manufacturer-search" name="cancelForm"
				method="post" value="">
				<input type="hidden" id="cancelManufacturerId"
					name="cancelManufacturerId" />
			</form>
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="deviceManufacturerEditRequest"
							action="update-device-manufacturer" method="post">
							<div class="col-sm-12 widgetDescriptionForm">

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
												class="imp-star">Device Manufacturer Code</span></legend>
											<form:input id="deviceManufacturerCode"
												name="deviceManufacturerCode" path="deviceManufacturerCode"
												class="form-control" readonly="true" type="text"></form:input>
											<div class="discriptionErrorMsg">
												<span id="deviceManufacturerIdMsgDiv"
													class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												Type Name</legend>
											<form:select id="deviceTypeId" path="deviceTypeId"
												name="deviceTypeId" onblur="validateDeviceType();"
												Class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${deviceTypeListData}"
													var="deviceTypeListData">
													<form:option value="${deviceTypeListData.deviceTypeId}">${deviceTypeListData.deviceTypeName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="deviceTypeNameDiv" style="color: red;">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Device
												Manufacturer Name </legend>
											<form:input cssClass="form-control" path="deviceManufacturer"
												id="deviceManufacturer" maxlength="50"
												onblur="this.value=this.value.trim();validateDeviceManufacturer();" autocomplete='off'></form:input>

											<div class="discriptionErrorMsg">
												<span id="deviceManufacturerMsgDiv" class="red-color-error"></span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Description</legend>
											<form:input id="description" path="description"
												name="description" class="form-control" type="text"
												maxlength="250" autocomplete="off"></form:input>
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
											class="col-sm-6 widgetSearchButton no-search-criteria padding0"
											style="float: right;">
											<input type="submit" id="deviceManufacturerUpdate"
												value="Update"
												onclick="return validateDeviceManufacturerEditSubmit();"
												class="form-control button pull-right"> <a
												href="javascript:cancelButton()"
												class="form-control button pull-right"><legend class = "custom-legend-button"> Cancel</legend> </a>
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
</html>