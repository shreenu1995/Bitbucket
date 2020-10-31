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
<title>Forgot Password</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>

</head>
<body>
	<div class="container cw-admin-login">
		<div class="cw-admin-login-main-container">
			<header id="loginHeader" class="col-xs-12 login-header">
				<img src="../images/logo_loginpage.jpg" alt="logo_loginpage" class="center">
			</header>
			<section class="cw-page-session-expire">
				<div class="session-expire-container">
					<div class="session-expire-panel">
						<h3>Reset password request has been expired!</h3>
						<p>
							Please click here to <a href="/user-login">Login</a>
						</p>
					</div>
				</div>
			</section>
			<jsp:include page="footer.jsp" />
		</div>
	</div>
</body>
</html>