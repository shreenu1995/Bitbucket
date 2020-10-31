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
<title>Depot View</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/depotManagement.js"></script>
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
						<span class="breadcrumb-text">Manage</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Depot</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">View</span>
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
								<form:form modelAttribute="depotProfileViewRequest"
									action="card-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
											<div class="widgetDescriptionRow">
												<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot
															Id </legend>
														<form:input cssClass="form-control" name="depotId"
															path="depotId" id="depotId" maxlength="50"
															readonly="true"
															onblur="this.value=this.value.trim();validateStationAddress();"
															onclick="clearErrorMsg('addressMsgDiv');"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="addressMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">
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
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															disabled="true" onblur="validatePto();"
															class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${ptoData}" var="ptoData">
																<form:option value="${ptoData.ptoMasterId}">${ptoData.ptoName}</form:option>
															</c:forEach>

														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot
															Name </legend>
														<form:input cssClass="form-control" path="depotName"
															readonly="true" id="depotName" maxlength="20"
															onblur="this.value=this.value.trim();validateDistrict();"
															onclick="clearErrorMsg('districtMsgDiv');"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="districtMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot
															Short Name </legend>
														<form:input cssClass="form-control" path="depotShortName"
															id="depotShortName" maxlength="50" readonly="true"
															onblur="this.value=this.value.trim();validateStationAddress();"
															onclick="clearErrorMsg('addressMsgDiv');"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="addressMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot
															Incharge </legend>
														<form:input cssClass="form-control" path="depotIncharge"
															readonly="true" id="depotIncharge" maxlength="20"
															onblur="this.value=this.value.trim();validateCity();"
															onclick="clearErrorMsg('cityMsgDiv');" autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="cityMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>

												<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span> Depot Mobile Number
														</legend>
														<form:input cssClass="form-control" path="mobile"
															id="mobile" maxlength="20" readonly="true"
															onblur="this.value=this.value.trim();validateDistrict();"
															onclick="clearErrorMsg('districtMsgDiv');"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="districtMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>
											</div>
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div class="col-sm-6 widgetSearchButton no-search-criteria padding0" style="float: right;">
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