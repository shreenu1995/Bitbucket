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
<title>Depot Edit</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/depotManagement.js"></script>
<script src="../js/addressData.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/ptoManagement.js"></script>
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
							class="breadcrumb-text">Depot</span> <span
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

								<form action="depot-search" name="cancelForm" method="post"
									value="">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
								</form>

								<!-- Page Form Start -->
								<form:form modelAttribute="depotProfileUpdateRequest"
									action="updateDepotProfile" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot
														Id </legend>
													<form:input cssClass="form-control" name="depotId"
														readonly="true" path="depotId" id="depotId" maxlength="13"
														onblur="this.value=this.value.trim();validateStationAddress();"
														onclick="clearErrorMsg('addressMsgDiv');"
														autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="addressMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
												<c:choose>
												<c:when
													test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
													<fieldset class="col-sm-3 form-group">
														<legend class  = "custom-legend-view"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
															Class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${organizationList}"
																var="organizationList">

																<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>

															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoId();">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${ptoData}" var="ptoData">
															<form:option value="${ptoData.ptoMasterId}">${ptoData.ptoName}</form:option>
														</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</c:when>
												<c:otherwise>
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoName')"
															Class="form-control">
															<c:forEach items="${organizationList}"
																var="organizationList">
																<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
														</div>
													</fieldset>
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoId();">
															<c:forEach items="${ptoData}" var="ptoData">
																<form:option value="${ptoData.ptoMasterId}">${ptoData.ptoName}</form:option>
															</c:forEach>

														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</c:otherwise>
											</c:choose>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Depot Name</span></legend>
													<form:input id="depotName" name="depotName" readonly="true"
														path="depotName" class="form-control" type="text"
														onblur="validateDepotName();" value="" maxlength="50"
														autocomplete="off" />
													<div class="discriptionErrorMsg">
														<span id="depotNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Depot Short Name</span></legend>
													<form:input id="depotShortName" name="depotShortName"
														path="depotShortName" class="form-control" type="text"
														onblur="validateDepotShortName();" value="" maxlength="50"
														autocomplete="off" />
													<div class="discriptionErrorMsg">
														<span id="depotShortNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Depot Incharge</span></legend>
													<form:input id="depotIncharge" name="depotIncharge"
														path="depotIncharge" class="form-control" type="text"
														onblur="validateDepotIncharge();" value="" maxlength="50"
														autocomplete="off" />
													<div class="discriptionErrorMsg">
														<span id="depotInchargeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Depot Mobile Number</span></legend>
													<form:input id="mobile" name="mobile" path="mobile"
														class="form-control" type="text"
														onblur="validateMobileNumber();" value="" maxlength="13"
														autocomplete="off" />
													<div class="discriptionErrorMsg">
														<span id="mobileMsgDiv" class="red-color-error">&nbsp;</span>
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
													<input type="submit" id="depotUpdate" value="Update"
														onclick="return validateDepotSubmit();"
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
		<!--Container block End -->
	</div>
	<!-- Main Body Wrapper End -->

</body>
</html>