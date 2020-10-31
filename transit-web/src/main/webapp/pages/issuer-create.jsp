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
<title>Issuer create</title>
<script src="../js/jquery.js" type="text/javascript"></script>

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/messages.js"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/organizationManagement.js"></script>
<script src="../js/issuer.js"></script>

</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!-- Navigation Bar Start --->
		<div id="header"></div>
		<jsp:include page="header.jsp" />
		<!-- Navigation Bar End -->
		<!--Article Block Start-->
		<article>
		<div class="col-xs-12 content-wrapper">
			<div class="breadCrumb">
				<span class="breadcrumb-text">Interface</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Issuer</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Create</span>
			</div>
			<div class="tab-header-container  marginL40">
				<a href="issuer-search">Search</a>
			</div>
			<div class="tab-header-container active-background">
				<a href="showCreateIssuer">Create</a>
			</div>

			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Page Form Start -->
						<form:form modelAttribute="issuerRequest" action="issuer-create"
							method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<span class="success-msg help-block" id="success">${success}</span>
								<span class="has-error help-block" id="error">${error}</span>
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Issuer Name</span></legend>
											<form:input id="issuerName" path="issuerName"
												class="form-control" type="text" maxlength="50"
												onblur="validateIssuerName();"></form:input>
											<div class="discriptionErrorMsg">
												<span id="issuerNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
											<form:select id="ptoMasterId" path="ptoMasterId"
												class="form-control"
												onblur="validatePto();">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoListData}" var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoMasterIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									</c:when>
									<c:otherwise>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
											<form:select id="ptoMasterId" path="ptoMasterId"
												class="form-control"
												onblur="validatePto();">
												<c:forEach items="${ptoListData}" var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoMasterIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									</c:otherwise>
									</c:choose>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Service URL</span></legend>
											<form:input id="serviceUrl" path="serviceUrl"
												class="form-control" type="text" maxlength="50"
												onblur="validateServiceUrl();"></form:input>
											<div class="discriptionErrorMsg">
												<span id="serviceUrlMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend">Country</legend>
											<form:select id="countryId" path="countryId"
												class="form-control"
												onchange="fetchstate(this.value, 'state')">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${countryData}" var="countryData">
													<form:option value="${countryData.countryId}">${countryData.countryName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="countryMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>

									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend">State</legend>
											<form:select id="state" name="state" path="state"
												Class="form-control"
												onchange="fetchcity(this.value, 'city')" type="text"
												value="" maxlength="50" autocomplete="off">
												<form:option value="">..:Select:..</form:option>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="stateMsgDiv" style="color: red;">&nbsp;</span>
											</div>
										</fieldset>
									</div>


									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend">City</legend>
											<form:select id="city" name="city" path="city"
												class="form-control" type="text" value="" maxlength="50"
												autocomplete="off">
												<form:option value="">..:Select:..</form:option>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="citydiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
								</div>

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="issuer-create" value="Create"
												class="form-control button pull-right"
												onclick="return ValidateIssuer();"> <a
												href="issuer-search" class="form-control button pull-right"><legend class = "custom-legend-button">Cancel</legend></a>
										</div>
									</fieldset>

								</div>
							</div>

							<!-- Form Button End -->
						</form:form>
						<!-- Page Form End -->
					</div>
				</div>

			</div>
			<!-- Content Block End -->
		</div>
		</article>
		<jsp:include page="footer.jsp" />
		<!--Article Block Start-->
		<div id="footer"></div>
	</div>
	<!--Container block End -->
</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.popupoverlay.js"></script>
<!-- Main Body Wrapper End -->
</body>