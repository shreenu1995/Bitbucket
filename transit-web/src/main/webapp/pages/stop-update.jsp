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
<title>Edit Stop</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/messages.js"></script>
<script src="../js/stopRegistration.js" type="text/javascript"></script>

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
				<span class="breadcrumb-text">Masters</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Stop</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<form action="stop-search" name="cancelForm" method="post" value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="stopEditRequest" action="stop-update"
							method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->


								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view">Stop Id</legend>
											<form:input id="stopId" name="stopId" path="stopId"
												class="form-control" readonly="true"></form:input>
											<div class="stopIdMsgDiv">
												<span id="stopIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<c:choose>
												<c:when
													test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
													<div class="widgetDescriptionRow">
														<fieldset class="col-sm-3 form-group">
															<legend class="custom-legend">
																<span class="requiredFiled">*</span> Organization Name
															</legend>
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
													</div>
													<div class="widgetDescriptionRow">
														<fieldset class="col-sm-3 form-group">
															<legend class="custom-legend">
																<span class="requiredFiled">*</span>PTO Name
															</legend>
															<form:select id="ptoId" name="ptoId" path="ptoId"
																class="form-control" onblur="validatePto();"
																onchange="doAjaxFetchRouteByPto(this.value);">
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
													<fieldset class="col-sm-3 form-group">
														<legend class="custom-legend">
															<span class="requiredFiled">*</span> Organization Name
														</legend>
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
														<legend class="custom-legend">
															<span class="requiredFiled">*</span>PTO Name
														</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePto();"
															onchange="doAjaxFetchRouteByPto(this.value);">
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
												<legend class="custom-legend">
													<span class="requiredFiled">*</span>Route Name
												</legend>
												<form:select id="routeId" name="routeId" path="routeId"
													onblur="validateRouteName();"
													onchange="doAjaxFetchStageByRoute(this.value)"
													class="form-control" >
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${routeNameList}" var="routeNameList">
														<form:option value="${routeNameList.routeId}">${routeNameList.routeName}</form:option>
													</c:forEach>

												</form:select>
												<div class="discriptionErrorMsg">
													<span id="routeNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>Stage Name</legend>
											<form:select id="stageId" name="stageId" path="stageId"
												onblur="validateStageName();" Class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${stageNameList}" var="stageNameList">
													<form:option value="${stageNameList.stageId}">${stageNameList.stageName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="stageNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Stop Name</span></legend>
											<form:input id="stopName" name="stopName" path="stopName"
												class="form-control" type="text"
												onblur="validateStopName();" onclick="" value=""
												maxlength="50"></form:input>
											<div class="discriptionErrorMsg">
												<span id="stopNameMsgDiv" class="red-color-error">&nbsp;</span>
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
											<input type="submit" id="stopUpdate" value="Update"
												onclick="return validateStopEditSubmit();"
												class="form-control button pull-right"> <a
												href="javascript:cancelButton()"
												class="form-control button pull-right"> <legend class = "custom-legend-button">Cancel </legend> </a>
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
</div>
</div>
</div>
</div>
<!-- Popup container End -->
</div>
<!--Container block End -->
<!-- Main Body Wrapper End -->
</body>
</html>