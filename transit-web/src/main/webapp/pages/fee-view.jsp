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
<title>Fee Registration</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/common.js"></script>
<script src="../js/feeRegistration.js"></script>
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
						<span class="breadcrumb-text">Manage</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Fee</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">View</span>
					</div>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
					<!-- Breadcrumb End -->

					<form action="fee-search" name="cancelForm" method="post">
						<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
					</form>
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="feeUpdateRequest"
									action="#" method="post">
										<div class="col-sm-12">
										
										<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span> Organization Name </legend>
														<form:input cssClass="form-control" name="organizationId"
															path="organizationId" id="organizationId" maxlength="50"
															readonly="true"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="addressMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>
												<div class="widgetDescriptionRow">

													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span> PTO Name </legend>
														<form:input cssClass="form-control" name="ptoId"
															path="ptoName" id="ptoId" maxlength="50"
															readonly="true"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="addressMsgDiv" class="red-color-error"></span>
														</div>
													</fieldset>
												</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span> Fee Name </legend>
													<form:input cssClass="form-control" path="feeName"
														id="feeName" maxlength="20" readonly="true"
														onblur="validateFeeName();"
														onclick="clearErrorMsg('stateMsgDiv');" autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="feeNameMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

										</div>
										<div class="col-xs-12">
											<div class="col-xs-12 content-wrapper">
												<div class="col-xs-12 label-align">
													<label><span fontsize="2px" style="color: #8866ff;">
															PTO Share </span></label>
												</div>
												<div>
												<div class="col-sm-12">

												<fieldset class="col-sm-3 form-group" style="padding-left:0;">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span> Based on Type</legend>
													<form:select id="ptoshare" name="ptoShareType" 
													disabled="true" onblur="validatePtoshareType();" path="ptoShareType"
														class="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="NumberOfTickets">Number Of Tickets</form:option>
														<form:option value="NumberOFPasses">Number Of Passes</form:option>
														<form:option value="TicketType">Ticket Type</form:option>
														<form:option value="PassType">Pass Type</form:option>
														<form:option value="TransactionsType">Transactions Type</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoshareMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												</div>
												
												<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route" class="marginT10"><span class="requiredFiled">*</span> Based On </legend>
														<form:input class="form-control" name="ptoShareValue" path="ptoShareValue"
												 			id="ptoshareValue" maxlength="20" readonly="true"
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="ptoshareValueMsgDiv" class="red-color-error"></span>
														</div>
												</fieldset>
												
												
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span class="requiredFiled">*</span> Fee Type</legend>
													<form:select id="ptoFeeType" onblur="validateFeeType();" name="ptoFeeType" disabled="true" path="ptoFeeType"
														class="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="percentage">Percentage</form:option>
														<form:option value="amount">Amount</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoFeeTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												
												
												<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route" id="feevalue" class="marginT10"><span class="requiredFiled">*</span> Fee Value</legend>
														<form:input cssClass="form-control" path="ptoFeeValue" id="ptoFeeValue"
												 			 maxlength="20" name="ptoFeeValue" readonly="true" onblur="validateFeeValue();"
															autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="ptoFeeValueMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												</div>
												
											</div>
							
											<div class="col-xs-12 content-wrapper">
												<div class="col-xs-12 label-align">
													<label><span fontsize="2px" style="color: #8866ff;">
															Organisation Share </span></label>
												</div>
												<div>
												<div class="col-sm-12">

												<fieldset class="col-sm-3 form-group" style="padding-left:0;">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span> Based on Type</legend>
													<form:select id="orgshare" name="orgShareType" path="orgShareType"
														class="form-control" disabled="true" onblur="validateOrgShareType();">
														<form:option value="">..:Select:..</form:option>
														<form:option value="NumberOfTickets">Number Of Tickets</form:option>
														<form:option value="NumberOFPasses">Number Of Passes</form:option>
														<form:option value="TicketType">Ticket Type</form:option>
														<form:option value="PassType">Pass Type</form:option>
														<form:option value="TransactionsType">Transactions Type</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="orgShareTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												</div>
												
												
												<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route" class="marginT10"><span class="requiredFiled">*</span> Based on</legend>
														<form:input class="form-control" path="orgShareValue" name="orgShareValue" 
												 			readonly="true" id="orgShareValue"  maxlength="20" 
															autocomplete='off'></form:input>

														<div class="discriptionErrorMsg">
															<span id="orgShareValueMsgDiv" class="red-color-error"></span>
														</div>
												</fieldset>
												
												
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span class="requiredFiled">*</span> Fee Type</legend>
													<form:select id="orgFeeType" name="orgFeeType" onblur="validateOrgFeeType();" 
													path="orgFeeType" disabled="true" class="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="percentage">Percentage</form:option>
														<form:option value="amount">Amount</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="orgFeeTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												
												
												<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route" id="orgfeevalue" class="marginT10"><span class="requiredFiled">*</span> Fee Value</legend>
														<form:input cssClass="form-control" path="orgFeeValue" id="orgshareFeeVal"
												 			 maxlength="20" name="orgFeeValue" readonly="true" onblur="validateOrgFeeValue();"
															autocomplete='off'></form:input>
														<div class="discriptionErrorMsg">
															<span id="orgfeevalueMsgDiv" class="red-color-error">&nbsp;</span>
													</div>

												</fieldset>
												
												</div>
											</div>									
							
											

											<!-- Form Button Start -->
											<div class="col-xs-12 role-makerchecker-btn">
												<fieldset class="form-group">
													<div class="col-sm-6 widgetSearchButton no-search-criteria padding0">
														 <a href="javascript:cancelButton()" class="form-control button pull-right"><legend class = "custom-legend-button">Cancel</legend>
														</a>
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