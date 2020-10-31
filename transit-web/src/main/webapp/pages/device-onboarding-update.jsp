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
<title>Edit Device Onboard</title> <
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/deviceOnboardingReg.js"></script>
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
					class="breadcrumb-text">Device Onboard</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<span class="success-msg help-block align-center " id="success">${success}</span>
			<span class="has-error help-block align-center " id="error">${error}</span>
			<form action="device-onboarding-search" name="cancelForm"
				method="post" value="cancel">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<form:form modelAttribute="deviceOnboardingEditRequest"
							action="device-onboarding-update" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view "><span class="requiredFiled">*</span><span
												class="imp-star"> Device Onboard Id</span></legend>
											<form:input id="deviceOnboardId" name="deviceOnboardId"
												path="deviceOnboardId" onblur="" readonly="true"
												class="form-control" type="text" value="" maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="deviceOnboardIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view "><span class="requiredFiled">*</span>
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
												<span id="organizationNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view "><span class="requiredFiled">*</span>PTO Name </legend>
											<form:select id="ptoId" name="ptoId" path="ptoId"
												Class="form-control" onblur="validatePtoOperationList();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoListData}"
													var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="ptoOperationTypeDiv">
												<span id="ptoOperationMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										         </c:when>
													<c:otherwise>
														<fieldset class="col-sm-3 form-group">
															<legend class = "custom-legend-view "><span class="requiredFiled">*</span>
																Organization Name</legend>
															<form:select id="organizationId" name="organizationId"
																path="organizationId" onblur="validateOrganizationId();"
																onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
																Class="form-control">
																<c:forEach items="${organizationList}"
																	var="organizationList">
																	<form:option value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg">
																<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
															</div>
														</fieldset>
														<fieldset class="col-sm-3 form-group">
															<legend class = "custom-legend-view "><span class="requiredFiled">*</span>PTO
																Name</legend>
															<form:select id="ptoId" name="ptoId" path="ptoId"
																class="form-control" onblur="validatePto();">
																<c:forEach items="${ptoListData}" var="ptoListData">
																	<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
																</c:forEach>
															</form:select>
															<div class="discriptionErrorMsg">
																<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
															</div>
														</fieldset>
													</c:otherwise>
												</c:choose>
										
										<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view "><span class="requiredFiled">*</span> Device
														Type List</legend>

													<form:select id="deviceTypeId" path="deviceTypeId" name="deviceTypeId"
														class="form-control"
														onchange="doAjaxFetchDeviceMAnufacturer(this.value);"
														onblur="validateDeviceTypeName();">
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
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view "><span class="requiredFiled">*</span> Device
												Manufacturer </legend>
											<form:select id="deviceManufacturerCode"
												name="deviceManufacturerCode" path="deviceManufacturerCode"
												class="form-control"
												onchange="doAjaxFetchDeviceModel(this.value);"
												onblur="validateDeviceManufacturer();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${deviceManufacturerListData}"
													var="deviceManufacturerListData">
													<form:option value="${deviceManufacturerListData.deviceManufacturerCode}">${deviceManufacturerListData.deviceManufacturer}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="deviceManufacturerMsgDiv" class="red-color-error"></span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view "><span class="requiredFiled">*</span> Device
												Model </legend>
											<form:select id="deviceModelId" name="deviceModelId"
												path="deviceModelId" class="form-control"
												onblur="validateDeviceModel();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${deviceModelListData}"
													var="deviceModelListData">
													<form:option value="${deviceModelListData.deviceModelId}">${deviceModelListData.deviceModel}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="deviceModelMsgDiv" class="red-color-error"></span>
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
											<input type="submit" id="deviceOnboardingUpdate"
												value="Update"
												onclick="return validateDeviceOnboardEditSubmit();"
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
	</div>

	</article>
	<!--Article Block Start-->
	<jsp:include page="footer.jsp" />
	<div id="footer"></div>
</div>
<!--Container block End -->
<!-- Main Body Wrapper End -->
</body>
</html>