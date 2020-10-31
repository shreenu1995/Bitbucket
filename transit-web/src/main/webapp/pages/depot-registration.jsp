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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon"
	href="http://localhost:8010/transit-web/images/favicon.ico">
<title>Depot Create</title>
<script src="js/jquery.js" type="text/javascript"></script>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="js/bootstrap.js"></script>
<script src="../js/depotManagement.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>

</head>
<style>
label.error {
	position: absolute;
}

.searchConteiner input[type="text"], .searchConteiner select {
	display: block;
}
</style>
<body>
	<!-- Main Body Wrapper Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid cw-admin-search-widget">

			<!-- Navigation Bar Start --->
			<jsp:include page="header.jsp" />
			<!-- Navigation Bar End -->

			<!--Article Block Start-->
			<article>
			<div class="col-xs-12 content-wrapper">
				<!-- Breadcrumb start -->
				<div class="breadCrumb">
					<span class="breadcrumb-text">Manage</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Depot</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Create</span>
				</div>
				<span class="success-msg help-block align-center" id="success">${success}</span>
				<span class="has-error help-block align-center" id="error">${error}</span>
				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->

				<div class="tab-header-container  marginL40">
					<a href="depot-search">Search</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="depot-registration">Create</a>
				</div>
				<!-- Tab Buttons End -->

				<!-- Content Block Start -->
				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">
							<!-- Main Page Error Messages Start -->

							<form:form modelAttribute="depotRegistrationRequest"
								action="depot-registration" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<!-- Overview section start -->

									<div class="row Overview-section">
										<div class="widgetDescriptionRow">
											<c:choose>
												<c:when
													test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>
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
														<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoId();">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${ptoListData}" var="ptoListData">
															<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
														</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</c:when>
												<c:otherwise>
												
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
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
														<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoId();">
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
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Depot Name</span></legend>
												<form:input id="depotName" name="depotName" path="depotName"
													class="form-control" type="text" maxlength="50"
													onblur="this.value=this.value.trim();return validateDepotName();"
													autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="depotNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Depot Short Name</span></legend>
												<form:input id="depotShortName" name="depotShortName"
													path="depotShortName" class="form-control" type="text" maxlength="50"
													onblur="validateDepotShortName();" autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="depotShortNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Depot Incharge</span></legend>
												<form:input id="depotIncharge" name="depotIncharge"
													path="depotIncharge" class="form-control" type="text" maxlength="50"
													onblur="validateDepotIncharge();" autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="depotInchargeMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Depot Mobile Number</span></legend>
												<form:input id="mobile" name="mobile" path="mobile"
													class="form-control" type="text" maxlength="13"
													onblur="validateMobileNumber();" autocomplete="off" />
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
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" value="Create"
													class="form-control button pull-right" id="register"
													onclick="return validateDepotSubmit();"> <a
													href="depot-registration"
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
			<!--Article Block Start-->
			<jsp:include page="footer.jsp" />
		</div>
		<!--Container block End -->
	</div>
	<!-- Main Body Wrapper End -->

</body>
</html>