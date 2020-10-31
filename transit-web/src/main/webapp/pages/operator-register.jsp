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
<link rel="shortcut icon" href="../images/favicon.png">
<title>Operator Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.js"></script>
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/common.js"></script>
<script src="../js/operatormanagement.js"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>

</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Masters</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Operator</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Create</span>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons Start -->
			<div class="tab-header-container  marginL40">
				<a href="operator-search">Search</a>
			</div>
			<div class="tab-header-container active-background">
				<a href="operator-register">Create</a>
			</div>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="operatorcreate"
							action="operator-register" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->
								<div class="row">
									<div class="widgetDescriptionRow">
										<c:choose>
											<c:when
												test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" onblur="validateOrganizationName();"
														onchange="fetchPtoListByOrganizationId(this.value, 'ptoId')"
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
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePtoId();">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${ptoList}" var="ptoList">
															<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</c:when>
											<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled"></span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" onblur="validateOrganization();"
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
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePto();">
														<c:forEach items="${ptoList}" var="ptoList">
															<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
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
												class="imp-star">Operator Name</span></legend>
											<form:input id="operatorName" name="operatorName"
												path="operatorName" class="form-control"
												onblur="this.value=this.value.trim();return validateOperatorName();" type="text" value=""
												maxlength="50"></form:input>
											<div class="userIdMsgDiv">
												<span id="operatorNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3  form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator Contact Number</span></legend>
											<form:input id="operatorContactNumber"
												name="operatorContactNumber" path="operatorContactNumber"
												class="form-control" onblur="validateMobileNumber();"
												maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="mobileMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3  form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator User Id</span></legend>
											<form:input id="operatorUserId"
												name="operatorUserId" path="operatorUserId"
												class="form-control" onblur="validateOperatorUserId();"
												maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="operatorIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3  form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator Password</span></legend>
											<form:input id="operatorPassword" type="password"
												name="operatorPassword" path="operatorPassword"
												class="form-control"  onblur="validateOperatorPassword();"
												maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="operatorPasswordMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
									</div>
								</div>
								<!-- Operator section end-->

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="user-search" value="Create"
												onclick="return validateOperatorSubmit();"
												class="form-control button pull-right"> <a
												href="operator-register"
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
