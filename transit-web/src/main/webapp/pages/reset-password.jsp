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
<title>Reset Password</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.css">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<!-- jQuery library -->
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>
</head>

<body>
	<div class="container cw-admin-login">
		<div class="cw-admin-login-main-container">
			<header id="loginHeader" class="col-xs-12 login-header"> <img
				src="../images/logo_loginpage.jpg"  alt="logo_loginpage"class="center"> </header>
			<section class="login-container">
			<div class="col-xs-8 center login-sub-container clearfix">
				<h3>Reset Password</h3>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>
			<div class="col-xs-8 center login-sub-container clearfix">
				<form:form modelAttribute="resetPassword" action="reset-password"
					method="post">
					<div class="login-form-elements">
						<div class="col-xs-2 login-form-icon">
							<img src="../images/passwordicon.png" alt="User Name Icon"
								class="pass-icon">
						</div>
						<fieldset class="col-xs-10 form-group login-form-div">
							<input class="form-control" id="newPassword" name="newPassword"
								maxlength="13" type="password" placeholder="New Password"
								onblur='validateNewPassword();' autocomplete="off">
							<div class="discriptionErrorMsg">
								<span id="newPasswordMsgDiv" class="red-color-error">&nbsp;</span>
							</div>
						</fieldset>
						<div class="col-xs-2 login-form-icon">
							<img src="../images/passwordicon.png" alt="User Name Icon"
								class="pass-icon">
						</div>
						<fieldset class="col-xs-10 form-group">
							<input class="form-control" id="confirmNewPassword"
								name="conformNewPassword" type="password"
								onblur='validateConfirmNewPassword();'
								placeholder="Confirm New Password">
							<div class="discriptionErrorMsg">
								<span id="confirmPasswordMsgDiv" class="red-color-error">&nbsp;</span>
							</div>
						</fieldset>

						<div class="col-xs-12 no-padding login-btn1">
							<div class="col-xs-10 form-group reset_buttons_align">

								<input value="Confirm"
									class="btn btn-blue form-control pull-right" id="resetSubmit"
									type="submit" onclick="return validateResetPasswordSubmit();" style="width: 116px"> <a href="user-login"><input
									value="Cancel" class="btn btn-blue form-control pull-right" style="width: 114px;margin-right: 10px;"></a>
							</div>
						</div>

					</div>

				</form:form>
			</div>
			<div class="error-response" id="error-response"></div>
			</section>
			<jsp:include page="footer.jsp" />
		</div>
	</div>
	<script type="text/javascript" src="../js/changePassword.js"></script>
</body>
</html>