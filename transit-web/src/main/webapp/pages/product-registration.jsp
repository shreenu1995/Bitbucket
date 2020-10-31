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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Product Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/bootstrap-datepicker.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css" />
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

 <script src="../js/common.js"></script>
<script src="../js/rome.js"></script> 
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/productManagement.js"></script>
<script src="../js/messages.js"></script>
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="../js/ptoManagement.js"></script>

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
				<div class="breadCrumb">
					<span class="breadcrumb-text">Manage</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Product</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Create</span>
				</div>
				<span class="success-msg help-block align-center " id="success">${success}</span>
				<span class="has-error help-block align-center " id="error">${error}</span>

				<div class="tab-header-container  marginL40">
					<a href="product-search">Search</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="product-registration">Create</a>
				</div>

				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">
							<!-- Main Page Error Messages Start -->
							<span class="success-msg help-block align-center" id="sucessDiv"></span>
							<span class="has-error help-block align-center" id="errorDiv"></span>
							<!-- Main Page Error Messages End -->

							<!-- Page Form Start -->
							<form:form modelAttribute="productRequest"
								action="product-registration" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<div class="row">
										<c:choose>
											<c:when
												test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
												<div class="widgetDescriptionRow">
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
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoList();">
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
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" onblur="validateOrgId();"
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
													<legend class = "custom-legend-view"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePtoList();">
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
												<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Product
													Type </legend>
												<form:select id="productType" name="productType" path="productType"
													class="form-control" onblur="validateProductType();">
													<form:option value="">..:Select:..</form:option>
													<form:option value="Ticket">Ticket</form:option>
													<form:option value="Pass">Pass</form:option>

												</form:select>
												<div class="discriptionErrorMsg">
													<span id="productTypeMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>


										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Product
													Name</legend>
												<form:input id="productName" name="productName"
													path="productName" class="form-control"
													onblur="this.value=this.value.trim();validateProductName();"
													type="text" value="" maxlength="50" autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="productNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Ticket/Pass
													Type </legend>
												<form:select id="ticketAndPassType" path="ticketAndPassType"
													name="ticketAndPassType" class="form-control"
													onblur="validateTicketAndPassType();">
													<form:option value="">..:Select:..</form:option>
													<form:option value="Fixed">Fixed</form:option>
													<form:option value="Variable">Variable</form:option>

												</form:select>
												<div class="discriptionErrorMsg">
													<span id="ticketAndPassTypeMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Amount </legend>
												<form:input id="amount" name="amount" path="amount"
													class="form-control"
													onblur="this.value=this.value.trim();validateAmount();"
													type="text" value="" maxlength="13" autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="amountMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend-view"><span class="requiredFiled">*</span>Discount
												</legend> <input id="discount" name="discount" path="discount"
													class="form-control"
													onblur="this.value=this.value.trim();validateDiscount();"
													type="text" value="" maxlength="13" autocomplete="off" />
												<div class="discriptionErrorMsg">
													<span id="discountMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class="custom-legend-view">Valid From</legend>
												<div class="input-group generationDate">
													<form:input type="text" id="validFrom" path="validFrom"
														class="form-control generationDateshow"></form:input>
													<label class="input-group-addon btn"
														for="generationDateStart"> <span
														class="fa fa-calendar"></span>
													</label>
												</div>
												<div class="discriptionErrorMsg">
													<span id="validFromMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow clearBoth">
											<fieldset class="col-sm-3 form-group">
												<legend class="custom-legend-view">Valid To</legend>
												<div class="input-group generationDate">
													<form:input type="text" id="validTo" path="validTo"
														class="form-control generationDateshow"></form:input>
													<label class="input-group-addon btn"
														for="generationDateStart"> <span
														class="fa fa-calendar"></span>
													</label>
												</div>
												<div class="discriptionErrorMsg">
													<span id="validToMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

									</div>
									<!-- Form Button Start -->
									<div class="role-makerchecker-btn">
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0 "
												style="float: right">
												<input type="submit" id="product-registration"
													value="Create" onclick="return validateProductSubmit();"
													class="form-control button pull-right">
												<a href="product-registration"
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

			<jsp:include page="footer.jsp" />

		</div>
		<!--Container block End -->
	</div>
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