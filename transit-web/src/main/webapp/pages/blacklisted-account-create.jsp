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
<title>Blacklisted Account Create</title>
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
<script src="../js/common.js"></script>
<script src="../js/blacklistedAccount.js"></script>
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
						class="breadcrumb-text">Blacklisted Account</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Create</span>
				</div>
				<!-- Breadcrumb End -->
				<form:form action="downloadBlacklistedAccountTemplate"
						name="downloadBlacklistedAccountTemplate" method="post" />
				<!-- Tab Buttons Start -->
				<div class="tab-header-container  marginL40">
					<a href="blacklisted-account-search">Search</a>
				</div>
				<div class="tab-header-container active-background">
					<a href="blacklisted-account-create">Create</a>
				</div>
				<!-- Tab Buttons End -->
				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">
							<!-- Main Page Error Messages Start -->
							<span class="success-msg help-block align-center" id="success">${success}</span>
							<span class="has-error help-block align-center" id="error">${error}</span>
							<!-- Main Page Error Messages End -->

							<!-- Page Form Start -->
							<form:form modelAttribute="blacklistedAccountRequests"
								action="blacklisted-account-create"
								enctype="multipart/form-data" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<div class="row">
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Issuer
													Name </legend>
												<form:select id="issuerName" name="issuerName" path="issuerName" maxlength="50"
													class="form-control" onblur = "validateIssuerName();">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${issuerList}" var="issuer">
														<form:option value="${issuer.issuerName}">${issuer.issuerName}</form:option>
													</c:forEach>
												</form:select>

												<div class="discriptionErrorMsg">
													<span id="issuerNameMsgDiv" class="red-color-error"></span>
												</div>
											</fieldset>
										</div>

										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span>Browse</legend>
												<div class="input-group">
													<span class="input-group-btn"> <span
														style="height: 27px;" class="btn btn-primary btn-file">
															Browse&hellip; <input type="file" name="browse"
															id="browseFile" title="No Files chosen"
															onchange="return validateFile();" />
													</span>
													</span> <input id="browse" class="form-control readonly"
														readonly />
												</div>
												<div class="discriptionErrorMsg">
													<span id="dataFile_errDiv" class="red-color-error"></span>
												</div>
											</fieldset>
										</div>

									</div>
									<!-- Form Button Start -->
									<div class="role-makerchecker-btn">
									
									<fieldset class="col-md-3 col-sm-6">
											<div class="download-link">
												<a href="#" onclick="javascript:downloadBlacklistedAccountCsvTemplate();"><legend class = "custom-legend-download-report">Download
													CSV File Template</legend></a>
											</div>
											<div class="discriptionErrorMsg">
												<span class="red-error" id="csvErrorMsg">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" value="Create"
													class="form-control button pull-right"
													onclick="return validateBlacklistedAccountSubmit();" /> <a
													href="blacklisted-account-create"
													class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend> </a>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-12 green-error_align">
											<span class="green-error"> <span
												style="font-weight: bold;">Note: </span><br> <legend class = "custom-legend-note">Please
													upload CSV file.</legend>
											</span>
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

	<script type="text/javascript" src="js/utils.js"></script>
</body>
</html>