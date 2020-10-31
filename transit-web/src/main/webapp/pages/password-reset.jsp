<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="icon" href="../images/favicon.png" type="image/png">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/main.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
    <script src="../js/common.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body onload="IPlaceholder();">
	<!--Body Wrapper block Start -->	
    <div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid marginT15">
			<!--Header Block Start -->
			<header class="col-sm-12 login-page-main">
				<!--Header Logo Start -->
				<div class="col-md-3 col-sm-6">
				</div>	
				<div class="col-sm-6"> 
					<img src="../images/logo_loginpage.jpg" class="login-logo-size"  alt="Logo"/>
				</div>
				<!--Header Logo End -->	
			</header>
			<!--Header Block End -->			
			<!--Article Block Start-->
			<article >
				<div class="col-xs-3"></div>
				<div class="col-xs-6 content-wrapper login-page-content loginSize">
					<h3>Reset Password</h3>
					<div class="row">
						<div class="col-sm-12">
							<!--Success and Failure Message Start-->
							<div class="col-xs-12">
									<span class="success-msg help-block align-center" id="success">${success}</span>
									<span class="has-error help-block align-center" id="error">${error}</span>
									
								</div>
							<!--Success and Failure Message End-->
							<!-- Page Form Start -->
							<form:form action="changePasswordFirstTime"  modelAttribute="changePassword"  name="changePassword" method="post">
								<div class="col-sm-12 login-elements-holder paddingL15">									
									<fieldset class="col-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/passwordicon.png" alt="passwordicon"></span>
											<form:input type="password" class="form-control"  path="currentPassword"  placeholder="Current Password" maxlength="13"
											  id="currentPassword" onblur='validateCurrentPassword();' name="currentChatakPass"></form:input>
										</div>	
										<div class="discriptionErrorMsg">
											<span class="red-error" id="currentPasswordMsgDiv">&nbsp;</span>
										</div> 
									</fieldset>
									
									<fieldset class="col-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/passwordicon.png" alt="passwordincon"></span>
											<form:input type="password" class="form-control"  onblur='validateNewPassword();' maxlength="13"
											path="newPassword" placeholder="New Password"  id="newPassword" />
										</div>	
										<div class="discriptionErrorMsg">
											<span class="red-error" id="newPasswordMsgDiv">&nbsp;</span>
										</div> 
									</fieldset>
									
									<fieldset class="col-sm-10">
										<div class="input-group">
											<span class="input-group-addon"><img src="../images/passwordicon.png" alt="passwordicon"></span>
											<form:input type="password" onblur='validateConfirmNewPassword();' class="form-control" maxlength="13"
											path="confirmNewPassword"  placeholder="Confirm New Password"  id="confirmNewPassword" />
										</div>	
										<div class="discriptionErrorMsg">
											<span class="red-error" id="confirmPasswordMsgDiv">&nbsp;</span>
										</div> 
									</fieldset>
									
									
								</div>								
								<!--login Action Button Start -->
								<div class="col-sm-12 login-elements-holder paddingL10P">																		
									<div class="col-sm-6">
										<a href="user-login" class="form-control button login-main-button width82P">Cancel</a>									
									</div>
									<div class="col-sm-6">
										<input type="submit" onclick="return validateChangePasswordSubmit();" class="form-control button login-main-button width82P" value="Submit">
									</div>	
								</div>	
								<!--login Action Button End -->
							</form:form>
						</div>
					</div>		
				</div>	
				<div class="col-xs-3"></div>
			</article>	
			<!--Article Block End-->
			<jsp:include page="footer.jsp"/>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../js/jquery.min.js"></script>
    <script src="../js/jquery.cookie.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap.min.js"></script>	
    <script src="../js/messages.js"></script>
    <script type="text/javascript" src="../js/changePassword.js"></script>
    
  </body>  
</html>