<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" href="../images/favicon.png" type="image/png">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet"/>
<script src="../js/jquery.js" type="text/javascript"></script>
<script src="../js/loginbackbutton.js" type="text/javascript"></script>
<title>User Logout</title>
</head>
<div id="wrapper" class="container-fluid prepaid-admin-dashboard">
	<header class="col-sm-12 all-page-header">
		<!--Header Logo Start -->
		<div class="col-sm-3">
			<img src="images/logo.jpg" class="subPageLogo disable-drag"
				alt="ChatakWallet Heading" draggable="false">
		</div>
	</header>
	<article>
		<div id="loginContainer"
			class="col-xs-12 content-wrapper login-page-content">
				<h5 class="font-style-text afcspagination-centered">You have successfully logged
				out from the portal! Please click here to <a class="logout-link" href="user-login">Login </a>again.</h5>
		</div>
	</article>
	
	<jsp:include page="footer.jsp" />
</div>
<script src="../js/messages.js"></script>
<script src="../js/bootstrap.min.js"></script>
</body>
</html>