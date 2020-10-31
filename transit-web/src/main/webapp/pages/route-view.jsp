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
<title>Route View</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/routeRegistration.js" type="text/javascript"></script>
<script src="../js/common.js" type="text/javascript"></script>
<script src="../js/messages.js" type="text/javascript"></script>

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
						<span class="breadcrumb-text">Masters</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Route</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">View</span>
					</div>
					<!-- Breadcrumb End -->

					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>

					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<form action="route-search" name="cancelForm" method="post"
									value="">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
								</form>

								<!-- Page Form Start -->
								<form:form modelAttribute="routesearchrequest"
									action="route-view" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										 
										<div class="row Overview-section">

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Route Id</span></legend>
													<form:input id="routeId" name="routeId" path="routeId"
														class="form-control" readonly="true"></form:input>
													<div class="routeIdMsgDiv">
														<span id="routeIdMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
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
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															disabled="true" onblur="validatePto();"
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

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">Route Name</span></legend>
													<form:input path="routeName" id="routeName"
														name="routeName" readonly="true" class="form-control"></form:input>
													<div class="discriptionErrorMsg">
														<span id="routeNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">From Route</span></legend>
													<form:input path="fromRoute" id="fromRoute"
														name="fromRoute" readonly="true" class="form-control"></form:input>
													<div class="discriptionErrorMsg">
														<span id="fromRouteMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
														class="imp-star">To Route</span></legend>
													<form:input id="toRoute" path="toRoute" name="toRoute"
														class="form-control" readonly="true"></form:input>
													<div class="discriptionErrorMsg">
														<span id="toRouteMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Route
															Code</legend>
														<form:input id="routeCode" name="routeCode"
															path="routeCode" class="form-control" type="text"
															value="" maxlength="50" autocomplete="off" readonly="true"/>
														<div class="discriptionErrorMsg">
															<span id="routeCodeMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>

												<div class="col-xs-12 content-wrapper">
													<div class="col-xs-12 label-align">
														<label><span fontsize="2px"
															style="color: #8866ff;"> Add Stages </span></label>
													</div>

													<form id="dataFieldsMgmtRequest" method="post">
														<fieldset class="col-sm-12 totalFields" id="readRecord">

															<tr id="addRowId0">
															<c:forEach items="${stageView}"
													var="stageView">
													<%-- <form:option value="${stageView.stageName}">${stageView.stageName}</form:option> --%>
											
																<td>
																	<fieldset class="col-md-4 col-sm-6 data_filed">
																		<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
																			class="imp-star">Stage Sequence Number</span></legend>
																		<form:input class="form-control"
																			id="stageSequenceNumber" readonly="true"
																			name="dataFieldList[0].stageSequenceNumber" value="${stageView.stageSequenceNumber}"
																			path="stageSequenceNumber"></form:input>
																		<div class="discriptionErrorMsg">
																			<span id="dataFieldNameError0"
																				class="error-span-hide red-error"></span>
																		</div>
																	</fieldset>
																</td>

																<td>
																	<fieldset class="col-md-4 col-sm-6 data_filed">
																		<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
																			class="imp-star">Stage Name</span></legend>
																		<form:input class="form-control" id="stageName"
																			readonly="true" value="${stageView.stageName}"
																			path="stageName"></form:input>
																		<div class="discriptionErrorMsg">
																			<span id="dataFieldNameError0"
																				class="error-span-hide red-error"></span>
																		</div>
																	</fieldset>
																</td>

																<td>
																	<fieldset class="col-md-4 col-sm-6 data_filed">
																		<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
																			class="imp-star">Distance</span></legend>
																		<form:input class="form-control" id="distance"
																			readonly="true" name="dataFieldList[0].distance" value="${stageView.distance}"
																			path="distance"></form:input>
																		<div class="discriptionErrorMsg">
																			<span id="dataFieldNameError0"
																				class="error-span-hide red-error"></span>
																		</div>
																	</fieldset>
																</td>
																
																	</c:forEach>
															</tr>

															<div
																class="col-sm-8 marginT10 add-bin-btn-div addButtonAliegn"
																id="readRecordBtn"></div>
												</div>
											</div>
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<a href="javascript:cancelButton()" class="form-control button pull-right">
														<legend class = "custom-legend-button">Cancel</legend> </a>
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