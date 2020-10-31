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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Change password</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>

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
						<span class="breadcrumb-text">Settings</span> 
						
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">Change Password</span>
					</div>

				<!-- Breadcrumb End -->
				<!-- Tab Buttons Start -->

				<div class="tab-header-container active-background marginL40 ">
					<a href="change-password">Change Password</a>
				</div>
				<!-- Tab Buttons End -->
				<!-- Content Block Start -->
				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">
							<!-- Main Page Error Messages Start -->
							<span class="success-msg help-block align-center" id="success">${success}</span>
							<span class="has-error help-block align-center" id="error">${error}</span>
							<form:form modelAttribute="changePassword"
								action="change-password" method="post">
								<div class="col-sm-12 widgetDescriptionForm">
									<!-- Overview section start -->
									<div class="row Overview-section">
										<div class="widgetDescriptionRow">

											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Current Password</span></legend> 
													<form:input id="currentPassword" path="currentPassword" type="password" name="currentPassword" placeholder="Password"
													class="form-control"  maxlength="13" onblur='validateCurrentPassword();'/>

												<div class="discriptionErrorMsg">
													<span id="currentPasswordMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>

											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">New Password</span></legend> <form:input
													id="newPassword" path="newPassword" name="newPassword" type="password" class="form-control" placeholder="newPassword"
													 maxlength="13" onblur='validateNewPassword();'/>

												<div class="discriptionErrorMsg">
													<span id="newPasswordMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled">*</span><span
													class="imp-star">Confirm Password</span></legend> <form:input
													id="confirmNewPassword" path="confirmNewPassword" type="password" name="confirmNewPassword" placeholder="confirmNewPassword"
													class="form-control"  maxlength="13"  onblur='validateConfirmNewPassword();'/>

												<div class="discriptionErrorMsg">
													<span id="confirmPasswordMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>

										</div>
									</div>
									<!-- Operator section end-->
									<!-- Form Button Start -->
									<div class="role-makerchecker-btn">
										<fieldset class="form-group">
											<div class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" id="generate" value="Update" onclick="return validateChangePasswordSubmit();"
													class="form-control button pull-right">
													<a href="change-password" class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend></a>
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
				<div id="noofRecordFetched"
					style="margin-left: 50px; padding-bottom: 10px; font-size: 14px; font-weight: bold"></div>
				<div id="table_wrapper"
					style="margin: 20px 19px; border: 1px solid #c0bfbf; display: none">
					<table id="myTable" class="display">

					</table>
				</div>

			</div>

		</article>
		<jsp:include page="footer.jsp" />
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->
<!-- Loading ...-->
<script type="text/javascript" src="../js/changePassword.js"></script>


</body>
</html>