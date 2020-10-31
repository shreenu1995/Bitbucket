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
<title>Operator View</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.js"></script>
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/common.js"></script>
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
					class="breadcrumb-text">View</span>
			</div>
			<form action="operator-search" name="cancelForm" method="post"
				value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>
			<!-- Tab Buttons Start -->

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="operatorViewRequest" action="#"
							method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator Id</span></legend>
											<form:input id="operatorId" name="operatorId"
												path="operatorId" readonly="true" onblur=""
												class="form-control" type="text" value="" maxlength="15"></form:input>
											<div class="discriptionErrorMsg">
												<span id="stageNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled"></span>
												Organization Name</legend>
											<form:select id="organizationId" name="organizationId"
												path="organizationId" onblur="validateOrganization();"
												disabled="true"
												onchange="fetchPtoByOrganizationId(this.value, 'ptoId')"
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
												disabled="true" onblur="validatePtoName();"
												class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoList}" var="ptoList">
													<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
												</c:forEach>

											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator Name</span></legend>
											<form:input id="operatorName" name="operatorName"
												path="operatorName" class="form-control" readonly="true"
												onblur="validateOperatorName();" type="text" value=""
												maxlength="32"></form:input>
											<div class="userIdMsgDiv">
												<span id="operatorNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<div class="widgetDescriptionRow clear-operator">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Operator ContactNumber</span></legend>
												<form:input id="operatorContactNumber" readonly="true"
													name="operatorContactNumber" path="operatorContactNumber"
													class="form-control" onblur="validateMobileNumber();"
													rows="5" maxlength="100" cols="50"></form:input>
												<div class="discriptionErrorMsg">
													<span id="mobileMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Operator User Id</span></legend>
											<form:input id="operatorUserId" readonly="true"
												name="operatorUserId" path="operatorUserId"
												class="form-control" onblur="validateOperatorUserId();"
												rows="5" maxlength="100" cols="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="operatorIdMsgDiv" class="red-color-error">&nbsp;</span>
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
											<a href="javascript:cancelButton()"
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
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->
</body>

</html>
