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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Forgot Password</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<script src="../js/bootstrap.js"></script>
</head>
<body>
	<div class="container cw-admin-login">
		<div class="cw-admin-login-main-container">
			<header id="loginHeader" class="col-xs-12 login-header">
				<img src="../images/logo_loginpage.jpg" alt="logo_loginpage" class="center">
			</header>
			<section class="login-container">
				<div class="col-xs-12 ">
					<h3>Forgot Password</h3>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
				</div>
				<div class="col-xs-8 center login-sub-container clearfix">
					<form:form modelAttribute="forgotPassword" action="forgot-password"
						method="post">
						<div class="login-form-elements">
							<div class="col-xs-2 login-form-icon login-form-div">
								<img draggable="false" src="../images/usernameicon.png"
									alt="User Name Icon" class="user-icon" ondrag="return false">
							</div>
							<div class="col-xs-10 form-group login-form-div">
								<input class="form-control" id="loginUser" name="userId"
									onblur="validateLoginUserName();" placeholder="Email"
									autocomplete="off" type="text">
							</div>
							<div class="col-xs-12 no-padding forgot_buttons">
								<div class="col-xs-5 form-group login-btn" style="
                                     margin-left: 15px;
                                     margin-right: 18px;">
									<a href="user-login"><input value="Cancel"
										class="form-control button pull-right for_but"></a>

								</div>
								<div class="col-xs-5 form-group login-btn">
									<input value="Submit"
										class="form-control button pull-right for_but"
										id="loginSubmit" type="submit">
								</div>
							</div>

						</div>

					</form:form>
				</div>
			</section>
			<!-- login-container end -->
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>