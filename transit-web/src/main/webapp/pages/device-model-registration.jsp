<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Device Model Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/deviceModelReg.js"></script>
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
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Manage</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Device</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Device Model</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<span class="success-msg help-block align-center " id="success">${success}</span>
					<span class="has-error help-block align-center " id="error">${error}</span>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container  marginL40">
						<a href="device-model-search">Search</a>
					</div>
					<div class="tab-header-container active-background">
						<a href="device-model-registration">Create</a>
					</div>
					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">

								<!-- Page Form Start -->
								<form:form modelAttribute="deviceModelRegistration"
									action="device-model-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">


											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Device
														Type List</legend>

													<form:select id="deviceTypeId" path="deviceTypeId" name="deviceTypeId"
														class="form-control"
														onchange="doAjaxFetchDeviceMAnufacturer(this.value);"
														onlbur="validateDeviceTypeName();">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${deviceTypeListData}"
															var="deviceTypeListData">
															<form:option value="${deviceTypeListData.deviceTypeId}">${deviceTypeListData.deviceTypeName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="deviceTypeNameMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Device
														Manufacturer </legend>
													<form:select id="deviceManufacturerCode"
														name="deviceManufacturerCode" path="deviceManufacturerCode"
														class="form-control"
														onblur="validateDeviceManufacturer();">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${deviceManufacturerListData}"
															var="deviceManufacturerListData">
															<form:option value="${deviceManufacturerListData.deviceManufacturerCode}">${deviceManufacturerListData.deviceManufacturer}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="deviceManufacturerMsgDiv"
															class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Device
														Model</legend>
													<form:input cssClass="form-control" path="deviceModel"
														id="deviceModel" maxlength="50"
														onblur="validateDeviceModel();" autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="deviceModelMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Device
														IMEI Number </legend>
													<form:input cssClass="form-control" path="deviceIMEINumber"
														id="deviceIMEINumber" maxlength="13"
														onblur="validateDeviceIMEINumber();" autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="deviceIMEINumberMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Description </legend>
													<form:input cssClass="form-control" path="description"
														id="description" maxlength="255"
														onblur="this.value=this.value.trim();validateDescription();"
														onclick="clearErrorMsg('descriptionMsgDiv');"
														autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="descriptionMsgDiv" class="red-color-error"></span>
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
														class="form-control button pull-right"
														onclick="return validateDeviceModelSubmit();" /> <a
														href="device-model-registration"
														class="form-control button pull-right"><legend class = "custom-legend-button">Reset </legend></a>
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

</body>
</html>