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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Discount View</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/discountReg.js"></script>
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
						<span class="breadcrumb-text">Manage</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Discount</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">View</span>
					</div>

			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<form action="discount-search" name="cancelForm" method="post"
				value="cancel">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="discountViewRequest"
							action="discount-view" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start --> 
								<div class="row Overview-section">
									<div class="widgetDescriptionRow"> 
									<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view">Discount Id</legend>
											<form:input id="discountId" name="discountId"
												path="discountId" class="form-control" type="text"
												readonly="true"></form:input>
											<div class="discriptionErrorMsg">
												<span id="discountNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
												Organization Name</legend>
											<form:select id="organizationId" name="organizationId"
												path="organizationId" disabled="true"
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
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO Name</legend>
											<form:select id="ptoId" name="ptoId" path="ptoId"
												disabled="true" class="form-control">
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
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Discount Type</legend>
											<form:input id="discountType" name="discountType"
												path="discountType" class="form-control" type="text"
												readonly="true"></form:input>
											<div class="discountTypeMsgDiv">
												<span id="discountTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group clearBoth">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Discount Name</legend>
											<form:input id="discountName" name="discountName"
												path="discountName" class="form-control" type="text"
												readonly="true"></form:input>
											<div class="discriptionErrorMsg">
												<span id="discountNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Route/Stage/Station Difference</legend>
											<form:input id="routeStageStationDifference"
												name="routeStageStationDifference"
												path="routeStageStationDifference" class="form-control"
												type="text" readonly="true"></form:input>
											<div class="discriptionErrorMsg">
												<span id="routeStageStationDifferenceMsgDiv"
													class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Discount%</legend>
											<form:input id="discount" name="discount" path="discount"
												class="form-control" type="text" readonly="true"></form:input>
											<div class="discriptionErrorMsg">
												<span id="discountMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
								</div>

								<!-- Operator section end-->
								<!-- Form Button End -->

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
					</div>
				</div>
				<!-- Page Form End -->
			</div>
		</div>

		</article>
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
		<div id="footer"></div>
	</div>
</div>
</body>
</html>