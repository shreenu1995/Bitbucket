<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
</head>
		<link rel="shortcut icon" href="../images/favicon.png">
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="http://localhost:8080/chatakwalletadmin/images/favicon.png">
        <title>Chatak Admin Portal</title>
           
        <script src="../js/jquery.js" type="text/javascript"></script>
        <link href="../css/main.css" rel="stylesheet">
        <link href="../css/style.css" rel="stylesheet">
        <link href="../css/font-awesome.css" rel="stylesheet">
        <script src="../js/c3.min.js"></script>
        <script src="../js/d3.min.js"></script>
        
         <!-- Bootstrap JS and all Bootstrap Plugins JS-->
        <script src="../js/bootstrap.min.js"></script>
        <script type="text/javascript">
        
          
        </script>
    </head>
    <body>
        
        <div id="wrapper" class= "container-fluid">
         <jsp:include page="header.jsp" />
        <div class="col-xs-6 content-wrapper text-center">
        	<h3>Overall Count</h3>
        	<div id="overall-count"></div>
        </div>
        	
        <div class="col-xs-6 content-wrapper text-center">
        	<h3>Tickets/Passes</h3>
        	<div id="ticket-pass"></div>
        </div>
        
        <div class="col-xs-6 content-wrapper text-center">
        	<h3>Revenue and Transaction</h3>
        	<select id="revenue-report">
				<option value="dataDaily">Daily</option>
				<option value="dataWeekly">Weekly</option>
				<option value="dataMonthly">Monthly</option>
			</select>
        	<div id="revenue-transaction"></div>
        </div>
        
        <div class="col-xs-6 content-wrapper text-center">
        	<h3>Fee/Commission</h3>
        	<select id="fee-report">
				<option value="dataDaily">Daily</option>
				<option value="dataWeekly">Weekly</option>
				<option value="dataMonthly">Monthly</option>
			</select>
        	<div id="fee-commisssion"></div>
        </div>
        	<jsp:include page="footer.jsp" />
        </div>
        
        <script src="../js/dashboard.js"></script>
	
</body>
</html>