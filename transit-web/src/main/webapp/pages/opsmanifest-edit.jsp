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
<title>OpsManifest Edit</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script src="../js/opsManifest.js"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<link href="../css/font-awesome.css" rel="stylesheet">
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/txnReportDatagenarate.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/opsManifest.js"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/addressData.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/rome.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>
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
						<span class="breadcrumb-text">Setup</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Ops Manifest</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Edit</span>
					</div>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->
								<form action="opsmanifest-search" name="cancelForm"
									method="post" value="">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
								</form>

								<!-- Page Form Start -->
								<form:form modelAttribute="opsManifestUpdateRequest"
									action="opsmanifest-edit" method="post">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
											<div class="widgetDescriptionRow">
											
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>
															OpsManifest Id </legend>
														<form:input cssClass="form-control" name="opsManifestId"
															path="opsManifestId" id="opsManifestId" readonly="true"
															autocomplete='off'></form:input>
														<div class="discriptionErrorMsg">
															<span id="addressMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class="custom-legend">
														<span class="requiredFiled">*</span> Depot Code </legend>
													<form:input cssClass="form-control" name="depotId"
														path="depotId" id="depotId" maxlength="13"
														readonly="true" onblur="this.value=this.value.trim();validateStationAddress();"
														onclick="clearErrorMsg('addressMsgDiv');"
														autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="addressMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<c:choose>
													<c:when
														test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
														<div class="widgetDescriptionRow">
															<fieldset class="col-sm-3 form-group">
																<legend class = "custom-legend"><span class="requiredFiled">*</span>
																	Organization Name</legend>
																<form:select id="organizationId" name="organizationId"
																	path="organizationId"
																	onblur="validateOrganizationId();"
																	onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
																	Class="form-control">
																	<form:option value="">..:Select:..</form:option>
																	<c:forEach items="${organizationList}"
																		var="organizationList">
																		<form:option
																			value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
																	</c:forEach>
																</form:select>
																<div class="discriptionErrorMsg">
																	<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
																</div>
															</fieldset>
														</div>
														<div class="widgetDescriptionRow">
															<fieldset class="col-sm-3 form-group">
																<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
																	Name</legend>
																<form:select id="ptoId" name="ptoId" path="ptoId"
																	onblur="validatePtoOperationName();"
																	class="form-control">
																	<form:option value="">..:Select:..</form:option>
																	<c:forEach items="${ptoListData}" var="ptoListData">
																		<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
																	</c:forEach>
																</form:select>
																<div class="discriptionErrorMsg">
																	<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
																</div>
															</fieldset>
														</div>
													</c:when>
													<c:otherwise>
														<div class="widgetDescriptionRow">
															<fieldset class="col-sm-3 form-group">
																<legend class = "custom-legend"><span class="requiredFiled">*</span>
																	Organization Name</legend>
																<form:select id="organizationId" name="organizationId"
																	path="organizationId"
																	onblur="validateOrganizationId();" Class="form-control">
																	<c:forEach items="${organizationList}"
																		var="organizationList">
																		<form:option
																			value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
																	</c:forEach>
																</form:select>
																<div class="discriptionErrorMsg">
																	<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
																</div>
															</fieldset>
														</div>
														<div class="widgetDescriptionRow">
															<fieldset class="col-sm-3 form-group">
																<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
																	Name</legend>
																<form:select id="ptoId" name="ptoId" path="ptoId"
																	onblur="validatePtoOperationName();"
																	class="form-control">
																	<c:forEach items="${ptoListData}" var="ptoListData">
																		<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
																	</c:forEach>
																</form:select>
																<div class="discriptionErrorMsg">
																	<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
																</div>
															</fieldset>
														</div>
													</c:otherwise>
												</c:choose>
												
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>Operator Name</legend>
														<form:select id="operatorId" name="operatorId" path="operatorId"
															onblur="validateOperator();" class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${operatorListData}"
																var="operatorListData">
																<form:option value="${operatorListData.operatorId}">${operatorListData.operatorName}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="operatorMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
												
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>Device
															Number</legend>
														<form:select id="deviceNo" name="deviceNo" path="deviceNo"
															onblur="validateDeviceNumber();" onchange="fetchUniqueDeviceNumber(this.value);"
															class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${deviceOnboardingListData}"
																var="deviceOnboardingListData">
																<form:option
																	value="${deviceOnboardingListData.deviceOnboardId}">${deviceOnboardingListData.deviceOnboardId}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="deviceNoMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>Depot
															Name</legend>
														<form:select id="depotName" name="depotName"
															selected="true" path="depotName"
															onblur="validateDepotName();" class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${depotListData}" var="depotListData">
																<form:option value="${depotListData.depotName}">${depotListData.depotName}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="depotNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="imp-star">Date</span></legend>
														<div class="input-group generationDate">
															<form:input path="date" cssClass="form-control readonly"
																id="date" onblur="validateDate('fromDateErrorMsg')"
																onkeypress="return numbersonly(this, event)" />
															<label class="input-group-addon btn"
																for="generationDateEnd"> <span
																class="fa fa-calendar"></span>
															</label>
														</div>
													</fieldset>
												</div>
											</div>
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" id="ptoUpdate" value="Update"
														onclick="return validateOpsManifestEditSubmit();"
														class="form-control button pull-right"> <a
														href="javascript:cancelButton()"
														class="form-control button pull-right"><legend class = "custom-legend-button"> Cancel </legend> </a>
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
	<script>
		rome(date, {
			time : false,
			"inputFormat" : "DD/MM/YYYY"
		});
		rome(date, {
			time : false,
			"inputFormat" : "DD/MM/YYYY"
		});
	</script>
</body>
</html>