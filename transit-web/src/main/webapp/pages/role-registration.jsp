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
<title>Chatak Admin Portal</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/roleRegistration.js" type="text/javascript"></script>
<script src="../js/common.js" type="text/javascript"></script>
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
						<p>
							<span class="breadCrumbIcon"></span><span> Manage </span> <span
								class="fa fa-angle-right"></span><span> Role Management </span>
							<span class="fa fa-angle-right"></span><span> Role
								Registration </span>
						</p>
					</div>

					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

					<div class="searchButton tab-header-active">
						<a href="route-registration.html">Role Registration</a>
					</div>

					<!-- Tab Buttons End -->
					<span class="success-msg help-block align-center" id="sucessDiv">${success}</span>
					<span class="has-error help-block align-center" id="errorDiv">${error}</span>

					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Page Form Start -->
								<form:form modelAttribute="roleRegistrationRequest"
									action="role-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Role
														Name</legend> <input id="roleName" name="roleName"
														class="form-control" onblur="validateRoleName();"
														type="text" value="" maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="roleNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-4 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Role
														Description</legend> <input id="roleDescription"
														name="roleDescription" class="form-control"
														onblur="validateRoleDescription();" type="text" value=""
														maxlength="250" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="roleDescriptionMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>

											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-2 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>Privilege
														List</legend> <select id="privilegesId" size="8"
														multiple="multiple" name="privilegesId"
														class="form-control" onblur="validatePrivilegeList();">
														<option value="">..:Select:..</option>
														<c:forEach items="${getPrivilegeList}"
															var="getPrivilegeList">
															<option value="${getPrivilegeList.privilegeIndex}">${getPrivilegeList.privilegeName}</option>
														</c:forEach>

													</select>
													<div class="discriptionErrorMsg">
														<span id="privilegeListDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</div>
										</div>
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div class="widgetSearchButton no-search-criteria">
													<input type="submit" id="user-search" value="Register"
														class="btn btn-info"
														onclick="return validateRoleRegistrationSubmit();">
													<input type="reset" class="btn btn-info wedgetResetBtn"
														value="Reset" onclick="resetRoleRegistration();">
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

	<script type="text/javascript" src="js/utils.js"></script>
	<!-- Loading -->
	<div id="wait"
		style="display: none; width: 69px; height: 89px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
		<img src='images/ajax-loader.gif' alt="ajax-loader" width="64" height="64" /><br>
	</div>

	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Registration</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>