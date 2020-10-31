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
		<link rel="shortcut icon" href="../images/favicon.png">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../css/bootstrap.css">
        <link href="../css/main.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		 
		<link href="../css/bootstrap.css" rel="stylesheet">

<!-- jQuery library -->
       <script src="../js/jquery.js" type="text/javascript"></script>
        <!-- jQuery CDN -->
        <!-- Bootstrap JS and all Bootstrap Plugins JS-->
        <script src="../js/bootstrap.js"></script>
        <script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
        <script src="../js/login.js"></script>
        <script src="../js/common.js"></script>
        
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            label.error{position:absolute;color:red;font-size:10px;margin:2px}
            .login-form-div{;margin-bottom:22px !important}
            .form-group{margin-bottom:15px}
            .error-response{color:red;text-align: center}
        </style>
   </head>
   
    <body>
        <div class="container cw-admin-login">
            <div class="cw-admin-login-main-container">
                <header id="loginHeader" class="col-xs-12 login-header"> 
                    <img src="../images/logo_loginpage.jpg" alt="logo_loginpage" class="center">
                </header>
                <section class="login-container">
                    <div class="col-xs-12 ">
                        <h3>Login</h3>
                        <div id="loginError" class="error-msg align-center"> <span class="success-msg help-block" id="errorDiv"></span>
                        </div>
                    </div>
                    <div class="col-xs-8 center login-sub-container clearfix">
                       <form:form modelAttribute="userLogin"
									action="user-login" method="post">
                            <div class="login-form-elements">
                                <div class="col-xs-2 login-form-icon login-form-div">
                                    <img draggable="false" src="../images/usernameicon.png" alt="User Name Icon" class="user-icon" ondrag="return false">							
                                </div>
                                <div class="col-xs-10 form-group login-form-div">
                                    <input class="form-control" id="loginUser" name="userId" onblur="validateLoginUserName();" placeholder="Email" autocomplete="off" type="text">
                                </div>
                                <div class="col-xs-2 login-form-icon">
                                    <img draggable="false" src="../images/passwordicon.png" alt="User Name Icon" class="pass-icon" ondrag="return false">
                                </div>
                                <div class="col-xs-10 form-group">
                                    <input class="form-control" id="loginPass" name="password" onblur="validateLoginPassword();" placeholder="Password" maxlength="15" type="password">
                                </div>
                                
                                <div class="error-response" id="error-response">${error}</div>
								<span class="success-msg help-block align-center" id="success">${success}</span>
                                
                                <div class="col-xs-6 no-padding">
                                    <div class="form-group forgot-password-div">
                                        <a href="forgot-password"> Forgot password? </a>
                                    </div>
                                </div>
                                <input type="hidden" value="WA06" name="transactionId">
                                <input type="hidden" name="dateTime"  id="timeZone">
                                
                                <div class="col-xs-6 no-padding">
                                    <div class="form-group login-btn">
                                        <input value="Login" class="form-control button pull-right" onclick="return validateLogin();" id="loginSubmit" type="submit">
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
	<div style="margin-top: -30px; margin-right: 10px; float: right;">
		<p class="footer-txt">
			<strong> <spring:message code="current.release.version" /></strong>
		</p>
	</div>
	<div id="wait" style="display:none;width:69px;height:89px;border:0px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='images/ajax-loader.gif' alt="ajax-loader" width="64" height="64" /><br></div>
        <script>
          
            $.fn.serializeObject = function () {
                var o = {};
                var a = this.serializeArray();
                $.each(a, function () {
                    if (o[this.name]) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                return o;
            };

            $(document).ready(function () {
                // validate signup form on keyup and submit
                sendCurentDate();
                
                $("#loginDetails").validate({
                    rules: {
                        userId: {
                            required: true,
                            email:true
                        },
                        password: {
                            required: true,
                        },
                    },
                    messages: {
                        userId: {
                            required: "Username required",
                            email: "Please enter valid username"
                        },
                        password: {
                            required: "Password required",
                        }
                    },
                    //  onkeyup:false,
                    onfocusout: function (ele) {
                        $(ele).valid()
                    },
                    submitHandler: function () {
                        frm = $("#loginDetails");
                        $.ajax({
                            type: frm.attr('method'),
                            url: serverURLs.login,
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify(frm.serializeObject()),
                            beforeSend: function (){
                                 $("#wait").css("display", "block");
                            },
                            success: function (data) {
                                
                               data.userName = $('#loginUser').val();
                               data.deviceType = "WA06";
                               localStorage.setItem('usrDetails', JSON.stringify(data));
                               
                               if(data.statusCode !="afcs_24") {
                            	   window.location.href="admin-dashboard.html";                            	   
                               } else {
                            	   $('#error-response').html(data.statusMessage);
                                   setTimeout(" $('#error-response').html('');", 3000);
                               }
                                
                            },
                            error: function (data) {
                                console.log('An error occurred.');
                                if(data.status === 0)
                                {
                                    $('#error-response').html("Server not responding"); 
                                    setTimeout(" $('#error-response').html('');", 3000);
                                }
                                else if(data.status === 404)
                                {
                                    $('#error-response').html("Invalid credentials");
                                    setTimeout(" $('#error-response').html('');", 3000);
                                }
                                else
                                {
                                    $('#error-response').html(data.message);
                                    setTimeout(" $('#error-response').html('');", 3000);
                                }
                                
                                console.log(data);
                            },
                             complete: function() {
                                  $("#wait").css("display", "none");
                              }
                        });
                        return false;
                    }

                });
            });
            function sendCurentDate() {
                $datetime = new Date();
                $res = $datetime.toString();
                $month = $datetime.getMonth()+1;
                $date = $datetime.getDate();
                $hours = $datetime.getHours();
                $minutes = $datetime.getMinutes();
                $sec = $datetime.getSeconds();
                if(($month)<10)
                {
                   $month = "0"+$month;
                }
                if($date < 10)
                {
                    $date = "0"+$date;
                }
                if($hours < 10)
                {
                  $hours = "0"+$hours;  
                }
                 if($minutes < 10)
                {
                  $minutes = "0"+$minutes;  
                }
                 if($sec < 10)
                {
                  $sec = "0"+$sec;  
                }
               $result = ""+$datetime.getFullYear()+"-"+$month+"-"+$date+" "+$hours+":"+$minutes+":"+$sec;
               $("#timeZone").val($result);
         }
         
        </script>
</body>
</html>