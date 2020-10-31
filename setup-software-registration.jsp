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
<title>Software Maintenance Registration</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Tablesorter plugin End-->
<link href="../css/main.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>
 <link href="../css/bootstrap-datepicker.css" rel="stylesheet">

<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<script src="../js/rome.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/setupsoftwaremaintenance.js"></script>
<script src="../js/txnReportDatagenarate.js" type="text/javascript"></script>
<script src="../js/file-download.js"></script>
<script src="../js/productManagement.js"></script>

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
						<p>
							<span class="breadCrumbIcon"></span><span> Setup </span> <span
								class="fa fa-angle-right"></span> <span> Software
								Maintenance </span> <span class="fa fa-angle-right"></span> <span>Create
							</span> <span class="success-msg help-block align-center" id="success">${success}</span>
							<span class="has-error help-block align-center" id="error">${error}</span>
						</p>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

					<div class="searchButton">
						<a href="setup-software-search">Search</a>
					</div>
					<div class="summarySearchButton tab-header-active">
						<a href="setup-software-registration">Create</a>
					</div>

					<!-- Tab Buttons End -->
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Main Page Error Messages Start -->
								<!-- Main Page Error Messages End -->

								<!-- Page Form Start -->
								<form:form modelAttribute="setupSoftwareRegistrationRequest"
									action="setup-software-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
										
										<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span>Organization
														Name</label>
													<form:select id="organizationName" name="organizationName"
														path="organizationName"
														onchange="fetchPto(this.value, 'ptoName')"
														onblur="validateOrganization();" Class="form-control">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${organizationList}"
															var="organizationList">
															<form:option value="${organizationList.organizationName}">${organizationList. organizationName}</form:option>
														</c:forEach>
													</form:select>
													<div class="organizationMsgDiv">
														<span id="organizationMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span>PTO Name</label> <select
														id="pto" class="form-control" path="pto" name="pto"
														onblur="validatePto();">
														<option value="">..:Select:..</option>
														<c:forEach items="${ptoList}"
															var="ptoList">
															<option value="${ptoList.ptoName}">${ptoList.ptoName}</option>
														</c:forEach>

													</select>
													<div class="discriptionErrorMsg">
														<span id="ptoMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
											
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span> Version
														Number </label> <input id="versionNumber" name="versionNumber"
														class="form-control" maxlength="50"
														onblur="validateVersionNumber();" autocomplete='off'>

													<div class="discriptionErrorMsg">
														<span id="versionNumberMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span>Inherit
													</label> <form:select id="inherit" name="inherit" path="inherit" class="form-control"
														onblur="validateInherit();">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${setupSoftwareInheritPtoList}"
															var="setupSoftwareInheritPtoList">
															<form:option value="${setupSoftwareInheritPtoList.inherit}">${setupSoftwareInheritPtoList.inherit}</form:option>
														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="inheritMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
											<fieldset class="col-sm-4 form-group">
												<label><span class="imp-star">Delivery Date</span></label>
												<div class="input-group generationDate">
													<form:input type="text" id="deliveryDate" path="deliveryDate"
														class="form-control generationDateshow"></form:input> <label
														class="input-group-addon btn" for="generationDateStart">
														<span class="fa fa-calendar" onclick="showDate('deliveryDate')"></span>
													</label>
												</div>
											</fieldset> 
											</div>
											
											<div class="widgetDescriptionRow">
											<fieldset class="col-sm-4 form-group">
												<label><span class="imp-star">Apply Date</span></label>
												<div class="input-group generationDate">
													<form:input type="text" id="applyDate" path="applyDate"
														class="form-control generationDateshow"></form:input> <label
														class="input-group-addon btn" for="generationDateEnd">
														<span class="fa fa-calendar"></span>
													</label>
												</div>  
											</fieldset>
											</div>
											
											<div class="widgetDescriptionRow clearBoth">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span>Description</label>
													<input id="description" name="description"
														class="form-control" onblur="validateDiscription();"
														type="text" maxlength="100" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="descriptionMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

										<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<label><span class="requiredFiled">*</span><span
														class="imp-star">Full Version</span></label> <select
														id="fullVersion" class="form-control"
														onblur="validateFullVersion();" name="fullVersion">
														<option value="">..:Select:..</option>
														<option>Yes</option>
														<option>No</option>
													</select>
													<div class="discriptionErrorMsg">
														<span id="fullVersionMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div class="widgetSearchButton no-search-criteria">
													<a href="setup-software-registration" class="btn btn-info">Reset
													</a> <input type="submit" value="Create" class="btn btn-info"
														onclick="return validateSetupSoftwareSubmit();" />
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
		rome(deliveryDate, {
			time : false,
		 "inputFormat" : "DD/MM/YYYY" 
			
		});
		rome(applyDate, {
			time : false,
			 "inputFormat" : "DD/MM/YYYY" 
		});
		
	</script>
</body>
</html>