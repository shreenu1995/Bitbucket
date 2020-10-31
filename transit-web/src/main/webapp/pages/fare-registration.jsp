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
<title>Fare Create</title>
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
<script src="../js/fare-registration.js"></script>
<script src="../js/addressData.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/fare-registration.js"></script>

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
						<span class="breadcrumb-text">Masters</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Fare</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

					<div class="tab-header-container  marginL40">
						<a href="fare-search">Search</a>
					</div>
					<div class="tab-header-container active-background">
						<a href="fare-registration">Create</a>
					</div>
					<div class="tab-header-container">
						<a href="fare-bulk-upload">BulkUpload</a>
					</div>

					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="fareRegistrationRequest"
									action="fare-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
                                           
                                       <c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Organization
														Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId"
														onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
														onblur="validateOrganizationId();" Class="form-control">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="organizationMsgDiv">
														<span id="organizationListMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														onblur="validatePtoOperationName();" class="form-control">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${ptoListData}" var="ptoListData">
															<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoOperationNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											</c:when>
										<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" Class="form-control" onblur="validateOrganizationId();">
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="organizationListMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
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
											
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span><span
														class="imp-star">Fare Type</span></legend> <select id="fareType"
														class="form-control" onblur="validateFareType();"
														name="fareType">
														<option value="">..:Select:..</option>
														<option>Ticket</option>
														<option>Pass</option>
													</select>
													<div class="discriptionErrorMsg">
														<span id="fareTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span> Fare
														Name </legend>
													<form:input cssClass="form-control" path="fareName"
														id="fareName" maxlength="50"
														onblur="this.value=this.value.trim();validateFareName();"
														onclick="clearErrorMsg('stateMsgDiv');" autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="fareNameMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Difference </legend>
													<form:input cssClass="form-control" path="difference"
														id="difference" maxlength="13"
														onblur="this.value=this.value.trim();validateDifference();"
														onclick="clearErrorMsg('stateMsgDiv');" autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="differenceMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Fare
														Amount</legend>
													<form:input class="form-control" path="fareAmount" name="fareAmount"
														id="fareAmount" maxlength="13"
														onblur="this.value=this.value.trim();validateFareAmount();"
														onclick="clearErrorMsg('stateMsgDiv');" autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="fareAmountMsgDiv" class="red-color-error"></span>
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
														class="form-control button pull-right"
														onclick="return validateFareSubmit();" /> <a
														href="fare-registration"
														class="form-control button pull-right"><legend class = "custom-legend-button">Reset </legend></a>
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
	<script type="text/javascript" src="js/fare-registration.js"></script>

</body>
</html>