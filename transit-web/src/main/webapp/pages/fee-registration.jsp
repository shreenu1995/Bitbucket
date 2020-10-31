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
<link href="../css/font-awesome.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

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
						<span class="breadcrumb-text">Manage</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Fee</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="tab-header-container  marginL40">
						<a href="fee-search">Search</a>
					</div>
					<div class="tab-header-container active-background">
						<a href="fee-registration">create</a>
					</div>

					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="feeRegistrationRequest"
									action="fee-registration" method="post">
									<div class="col-sm-12">

										<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-route"><span class="requiredFiled">*</span>Organisation
												Name</legend>
											<form:select id="organizationId" name="organizationId"
												path="organizationId" onblur="validateOrgName();"
												onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
												class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${organizationList}"
													var="organizationList">
													<form:option value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="orgMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-route"><span class="requiredFiled">*</span><span
												class="imp-star">PTO Name</span></legend>
											<form:select id="ptoId" name="ptoId" path="ptoId"
												onblur="validatePto();" class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoListData}"
													var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										</c:when>
										<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" Class="form-control" onblur="validateOrgName();"
														onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')">
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="orgMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>PTO Name</legend>
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
												<legend class = "custom-legend-route"><span class="requiredFiled">*</span> Fee Name
												</legend>
												<form:input cssClass="form-control" path="feeName"
													id="feeName" maxlength="50" onblur="validateFeeName();"
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

													<fieldset class="col-sm-3 form-group"
														style="padding-left: 0;">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span><span
															class="imp-star"></span>Based on Type</legend> <select
															id="ptoshare" name="ptoShareType"
															onblur="validatePtoshareType();" path="ptoShareType"
															class="form-control">
															<option value="">..:Select:..</option>
															<option value="NumberOfTickets">Number OfTickets</option>
															<option value="NumberOFPasses">Number Of Passes</option>
															<option value="TicketType">Ticket Type</option>
															<option value="PassType">Pass Type</option>
															<option value="TransactionsType">Transactions
																Type</option>
														</select>
														<div class="discriptionErrorMsg">
															<span id="ptoshareMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span
														class="requiredFiled">*</span> Based On </legend> <input
														class="form-control" name="ptoshareValue"
														id="ptoshareValue" maxlength="20" readonly="true"
														autocomplete='off'></input>

													<div class="discriptionErrorMsg">
														<span id="ptoshareValueMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>


												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span
														class="requiredFiled">*</span><span class="imp-star"></span>Fee
														Type</legend>
													<form:select id="ptoFeeType" onblur="validateFeeType();"
														name="ptoFeeType" path="ptoFeeType" class="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="percentage">Percentage</form:option>
														<form:option value="amount">Amount</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoFeeTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>


												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" id="feevalue" class="marginT10"><span
														class="requiredFiled">*</span> Fee Value</legend>
													<form:input cssClass="form-control" path="ptoFeeValue"
														id="ptoFeeValue" maxlength="13" name="ptoFeeValue"
														onblur="validateFeeValue();" autocomplete='off'></form:input>
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

													<fieldset class="col-sm-3 form-group"
														style="padding-left: 0;">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span><span
															class="imp-star"></span>Based on Type</legend> <select
															id="orgshare" name="orgShareType" path="orgShareType"
															class="form-control" onblur="validateOrgShareType();">
															<option value="">..:Select:..</option>
															<option value="NumberOfTickets">Number Of Tickets</option>
															<option value="NumberOFPasses">Number Of Passes</option>
															<option value="TicketType">Ticket Type</option>
															<option value="PassType">Pass Type</option>
															<option value="TransactionsType">Transactions
																Type</option>
														</select>
														<div class="discriptionErrorMsg">
															<span id="orgShareTypeMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>


												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span
														class="requiredFiled">*</span> Based on</legend> <input
														class="form-control" name="orgShareValue"
														id="orgShareValue" maxlength="13" readonly="true"
														autocomplete='off'></input>

													<div class="discriptionErrorMsg">
														<span id="orgShareValueMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>


												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" class="marginT10"><span
														class="requiredFiled">*</span><span class="imp-star"></span>Fee
														Type</legend>
													<form:select id="orgFeeType" name="orgFeeType"
														onblur="validateOrgFeeType();" path="orgFeeType"
														class="form-control">
														<form:option value="">..:Select:..</form:option>
														<form:option value="percentage">Percentage</form:option>
														<form:option value="amount">Amount</form:option>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="orgFeeTypeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>


												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route" id="orgfeevalue" class="marginT10"><span
														class="requiredFiled">*</span> Fee Value</legend>
													<form:input cssClass="form-control" path="orgFeeValue"
														id="orgshareFeeVal" maxlength="13" name="orgFeeValue"
														onblur="validateOrgFeeValue();" autocomplete='off'></form:input>
													<div class="discriptionErrorMsg">
														<span id="orgfeevalueMsgDiv" class="red-color-error">&nbsp;</span>
													</div>

												</fieldset>

											</div>
										</div>
										<!-- Form Button Start -->
										<div class="col-xs-12 role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" value="Create"
														onclick="return validateFeeCreate();"
														class="form-control button pull-right"
														onclick="return validateFeeSubmit();" /> <a
														href="fee-registration"
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

	<script>
	
	$(document).ready(function () {
		
	    $('#ptoFeeType').change(
	    function () {
	        var ptoVal = $('#ptoFeeType option:selected').text();
	       	console.log(ptoVal);
	        if (this.value == "percentage") {
	            $('#feevalue').text("Percentage");
	        } else if (this.value == "amount") {
	            $('#feevalue').text("Amount");
	        } else {
	        	$('#feevalue').text("Fee Value");
	        }
	    });
	    
	    $('#ptoshare').change(function(){
	    	var ptoShareValue = $('#ptoshare option:selected').text();
	    	$('#ptoshareValue').val(ptoShareValue);
	    });
	    
	   
		$('#orgFeeType').change(function() {
			var orgVal = $('#orgFeeType option:selected').text();
			console.log(orgVal);
			if (this.value == "percentage") {
				$('#orgfeevalue').text("Percentage");
			} else if (this.value == "amount") {
				$('#orgfeevalue').text("Amount");
			} else {
	        	$('#orgfeevalue').text("Fee Value");
	        }
		});
	    
		$('#orgshare').change(function(){
	    	var orgShareValue = $('#orgshare option:selected').text();
	    	$('#orgShareValue').val(orgShareValue);
	    });
		
	});
	</script>

</body>
</html>