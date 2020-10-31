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
<title>Edit Product</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap-datepicker.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css" />
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<script src="../js/rome.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/productManagement.js"></script>
<script src="../js/messages.js"></script>
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>

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
				<span class="breadcrumb-text">Manage</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Product</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Edit</span>
			</div>
			<form action="product-search" name="cancelForm" method="post"
				value="">
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
						<span class="success-msg help-block align-center" id="sucessDiv"></span>
						<span class="has-error help-block align-center" id="errorDiv"></span>
						<form:form modelAttribute="productViewRequest"
							action="update-product" method="post">
							<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">
									
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span><span
												class="imp-star">Product Id</span></legend>
											<form:input id="productId" name="productId" path="productId" class="form-control"
												onblur="" readonly="true" type="text" value=""
												maxlength="13"></form:input>
											<div class="deviceTypeCodeMsgDiv">
												<span id="deviceTypeCodeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<c:choose>
												<c:when
													test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrgId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
															Class="form-control">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${organizationList}"
																var="organizationList">
																<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
															</c:forEach>
														</form:select>
														<div class="orgNameMsgDiv">
															<span id="orgNameMsgDiv" style="color: red;">&nbsp;</span>
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
															path="organizationId" onblur="validateOrgId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoName')"
															Class="form-control">
															<c:forEach items="${organizationList}"
																var="organizationList">
																<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
															</c:forEach>
														</form:select>
														<div class="orgNameMsgDiv">
															<span id="orgNameMsgDiv" style="color: red;">&nbsp;</span>
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
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Product
												Type </legend>
											<form:select id="productType" path="productType"
												name="productType" class="form-control"
												onblur="validateProductType();">
												<form:option value="">..:Select:..</form:option>
												<form:option value="Ticket">Ticket</form:option>
												<form:option value="Pass">Pass</form:option>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="productTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Product
												Name</legend>
											<form:input id="productName" path="productName"
												name="productName" class="form-control"
												onblur="this.value=this.value.trim();validateProductName();"
												type="text" value="" maxlength="50" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="productNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Ticket/Pass
												Type </legend>
											<form:select id="ticketAndPassType" name="ticketAndPassType"
												path="ticketAndPassType" class="form-control"
												onblur="validateTicketAndPassType();">
												<form:option value="">..:Select:..</form:option>
												<form:option value="Fixed">Fixed</form:option>
												<form:option value="Variable">Variable</form:option>

											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ticketAndPassTypeMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Amount</legend>
											<form:input id="amount" name="amount" path="amount"
												class="form-control"
												onblur="this.value=this.value.trim();validateAmount();"
												type="text" value="" maxlength="13" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="amountMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Discount</legend>
											<form:input id="discount" name="discount" path="discount"
												class="form-control"
												onblur="this.value=this.value.trim();validateDiscount();"
												type="text" value="" maxlength="13" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="discountMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view">Valid From</legend>
											<div class="input-group generationDate">
												<form:input type="text" id="validFrom" name="validFrom"
													path="validFrom"
													class="form-control generationDateshow" />
												<label class="input-group-addon btn" for="validFrom">
													<span class="fa fa-calendar"></span>
												</label>
											</div>
											<div class="discriptionErrorMsg">
												<span id="validFromMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-view">Valid To</legend>
											<div class="input-group generationDate">
												<form:input type="text" id="validTo" name="validTo"
													path="validTo"
													class="form-control generationDateshow" />
												<label class="input-group-addon btn" for="validTo">
													<span class="fa fa-calendar"></span>
												</label>
											</div>
											<div class="discriptionErrorMsg">
												<span id="validToMsgDiv" class="red-color-error">&nbsp;</span>
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
											<input type="submit" id="productUpdate" value="Update"
												onclick="return validateProductEditSubmit();"
												class="form-control button pull-right"> <a
												href="javascript:cancelButton()"
												class="form-control button pull-right"><legend class = "custom-legend-button"> Cancel </legend></a>
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

<script>
	rome(validFrom, {
		time : false,
		"inputFormat" : "DD/MM/YYYY"

	});
	rome(validTo, {
		time : false,
		"inputFormat" : "DD/MM/YYYY"
	});
</script>
</body>
</html>