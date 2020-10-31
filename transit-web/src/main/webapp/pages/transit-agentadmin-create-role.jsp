<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.chatak.transit.afcs.server.constants.Constants"%>
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
<link rel="icon" href="../images/favicon.png" type="image/png">
<!-- Bootstrap -->
<script src="../js/jquery.min.js"></script>
 <script src="../js/jquery.cookie.js"></script>
    <script src="../js/prepaid-lib.js"></script>
    <script src="../js/messages.js"></script>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet"> 
<link href="../css/jquery.datetimepicker.css" rel="stylesheet" type="text/css" />
<link href="../css/font-awesome.css" rel="stylesheet" type="text/css" />
<title>Transit Agent Admin Create</title>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body oncontextmenu="disableRightClick(<%=Constants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%@include file="navigation_panel.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text"><a href="#"><spring:message code="prepaid-agentadmin-agent-user-search.label.permissions"/></a></span>
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><a href="showAgentRole"><spring:message code="prepaid-agentadmin-role.label.roles"/></a></span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text"><a href="showCreateRole"><spring:message code="prepaid-agentadmin-agent-user-search.label.create"/></a></span>
					</div>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,role)||fn:contains(existingFeatures,roleEdit)||fn:contains(existingFeatures,roleSuspend)||fn:contains(existingFeatures,roleActive)}">
							<div class="tab-header-container" >
								<a href="role-search"><spring:message code="prepaid-agentadmin-agent-user-search.label.search"/></a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,roleCreate)}">
							<div class="tab-header-container active-background">
								<a href="#"><spring:message code="prepaid-agentadmin-agent-user-search.label.create"/></a>
							</div>
						</c:if>
					</div>
					<!-- Tab Buttons End -->
					<!-- Content Block Start -->
					<div class="main-content-holder">
						<div class="row">
							<div class="col-sm-12">
								<!--Success and Failure Message Start-->
								<div class="col-sm-12">
									<div class="discriptionErrorMsg">
										<span class="red-error" id="errorDataPermission">${error}</span>
										 <span class="green-error" id="sucessDiv">${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="createRole" modelAttribute="agentRoleRequest"
									method="post" onsubmit="buttonDisabled()">
									<input type="hidden" id="permission" name="permission">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="prepaid-agentadmin-agent-user-search.tableColumn.roleName"/><span class="required-field">*</span></label>
													<form:input cssClass="form-control" id="roleName" maxlength="<%= Constants.ROLE_NAME.toString() %>"
														path="roleName" onblur="validRoleName('roleName', 'roleDiv', 'Please enter the role name',	'Please enter the valid role name')" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="prepaid-agentadmin-role.label.roleDescription"/><span class="required-field">*</span></label>
													<form:input  cssClass="form-control" id="description"  maxlength="<%= Constants.ROLE_DESCRIPTION.toString() %>"
														path="description"  onblur="validRoleDescription('description', 'roleDescription',	'Please enter the description',	'Please enter the valid description')"/>
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleDescription">&nbsp;</span>
													</div>
												</fieldset>
												<div class="col-sm-12 role_assign_message">
													<label><spring:message code="prepaid-agentadmin-role.label.selectPermissionstoAssign"/></label>
												</div>
												<div class="col-sm-12 roleListDiv" >
													<!-- Collapseable panel Main Start -->
													<div class="panel-group" id="accordion">
														<ul id="check-tree" class="marginLM60">
															<c:forEach items="${featureList}" var="feature">
																<c:if test="${feature.getRefFeatureId() == 0}">
																	<!-- User Information Collapseable panel Start-->
																	<div class="panel panel-default clearfix">
																		<!-- Panel Header Start -->
																		<div class="panel-heading permission_color">
																			<h4 class="panel-title">
																				<a id="anchorClick${feature.getFeatureId()}" 
																					data-toggle="collapse" data-parent="#accordion"
																					href="#collapse${feature.getFeatureId()}" class="fa fa-angle-double-left" > 
																					<label class="permission_cursor_type" style="font-size: 14px;font-family: Verdana, Geneva, sans-serif;font-style: normal;">${feature.getName()}
 																					 </label>
																				</a>
																			</h4>
																		</div>
																		<!-- Panel Header End -->
																		<!-- Panel Content Start -->
																		<div id="collapse${feature.getFeatureId()}" class="panel-collapse collapse">
																			<li><label>
																			<input type="hidden" name="featureTOValues" value="${feature.getName()}" id="${feature.getFeatureId()}" />
																			<input type="checkbox" name="featureTO" value="${feature.getFeatureId()}" id="${feature.getFeatureId()}" />
																			<span class="role-title"> ${feature.getName()} </span></label>
																				<ul>
																				<%int count=1; %>
																					<c:forEach items="${featureList}" var="featureSub" varStatus="counter">
																						<c:if test="${featureSub.getRefFeatureId() eq feature.getFeatureId()}">
																							<!-- DisplayChild(Level2) -->
																							<%	if (count%4 == 0) { 
																					%><div class="row">
																			<fieldset class="col-md-3 col-sm-6">
																					<li style="width:300px"><label><input type="checkbox" name="featureTO" id="${featureSub.getFeatureId()}" /><span class="role-title"> ${featureSub.getName()}</span></label>
																					
																				<div class="userDiv">
																					 <c:if test="${feature.getName() eq 'Transactions'}">
																					    <c:if test="${featureSub.getName() eq 'Send Funds To Bank A/c (EFT)'}">
																					    <ul>
																				 			<li><label><input type="radio"  name="userType" id="userType1" value="INITIATOR" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Initiator"/></label></li>
																							<li><label><input type="radio"  name="userType" id="userType3" value="APPROVER_AND_INITIATOR" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.ApproverandInitiator"/></label></li>
																							
																					   </ul>
																					   </c:if>
																					     <c:if test="${featureSub.getName() eq 'Send Funds By Check'}">
																					        <ul>
																					        	<li><label><input type="radio"  name="userType" id="userType2" value="APPROVER" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Approver"/></label></li>
																					        	<li><label><input type="radio"  name="userType" id="userType4" value="ADMIN" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Admin"/></label></li>
																					       </ul>
																					     </c:if>
																					</c:if>
																			      </div>
																			<ul>
																				<c:forEach items="${featureList}" var="featureChild">
																					<c:if test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																							<!-- DisplayChild(Level3) -->
																							<li style="width:222px"><label><input type="checkbox" name="featureTO" id="${featureChild.getFeatureId()}" /><span class="role-title"> ${featureChild.getName()} </span></label></li>
																							<%-- <li><label <label style="width:222px"><input type="checkbox" name="featureTO" id="5008">Load Card Individual</label>><input type="checkbox" name="featureTO" id="${featureChild.getFeatureId()}" />${featureChild.getName()}</label></li> --%>
																					</c:if>
																			    </c:forEach>
																		   </ul>
																		</fieldset>
																		</div>
																		<%}
																		else {%>
																		<fieldset class="col-md-3 col-sm-6">
																					<li style="width:300px"><label><input type="checkbox" name="featureTO" id="${featureSub.getFeatureId()}" /><span class="role-title"> ${featureSub.getName()}</span></label>
																					
																				<div class="userDiv">
																					 <c:if test="${feature.getName() eq 'Transactions'}">
																					    <c:if test="${featureSub.getName() eq 'Send Funds To Bank A/c (EFT)'}">
																					    <ul>
																				 			<li><label><input type="radio"  name="userType" id="userType1" value="INITIATOR" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Initiator"/></label></li>
																							<li><label><input type="radio"  name="userType" id="userType3" value="APPROVER_AND_INITIATOR" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.ApproverandInitiator"/></label></li>
																							
																					   </ul>
																					   </c:if>
																					     <c:if test="${featureSub.getName() eq 'Send Funds By Check'}">
																					        <ul>
																					        	<li><label><input type="radio"  name="userType" id="userType2" value="APPROVER" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Approver"/></label></li>
																					        	<li><label><input type="radio"  name="userType" id="userType4" value="ADMIN" onclick="toggleRadio(this.value)" /><span>&nbsp;</span><spring:message code="prepaid-agentadmin.label.Admin"/></label></li>
																					       </ul>
																					     </c:if>
																					</c:if>
																			      </div>
																			<ul>
																				<c:forEach items="${featureList}" var="featureChild">
																					<c:if test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																							<!-- DisplayChild(Level3) -->
																							<li style="width:222px"><label><input type="checkbox" name="featureTO" id="${featureChild.getFeatureId()}" /><span class="role-title"> ${featureChild.getName()} </span></label></li>
																							<%-- <li><label <label style="width:222px"><input type="checkbox" name="featureTO" id="5008">Load Card Individual</label>><input type="checkbox" name="featureTO" id="${featureChild.getFeatureId()}" />${featureChild.getName()}</label></li> --%>
																					</c:if>
																			    </c:forEach>
																		   </ul>
																		</fieldset>
																		
																		<%} count++; %>
																		</li>
																				</c:if>
																			</c:forEach>
																</ul>
														</li>
													</div>
												</div>
												<!-- Panel Content End -->

												<!-- User Information Collapseable panel End-->

												</c:if>
												</c:forEach>

												</ul>
											</div>

										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" id="buttonCreate"	class="form-control button pull-right" value="<spring:message code="prepaid-agentadmin-agent-user-search.label.create"/>" onclick="return submitData()"> 
												<a href="#"	id="processingButton1" style="display: none;">
												 <input		type="button" class="form-control button pull-right"	value="Processing...">
												</a> <a href="showCreateRole"			class="form-control button pull-right"><spring:message code="prepaid-agentadmin.button.Reset"/></a>
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
							</div>
						</div>
						<!-- Page Form End -->
					</div>
				</div>
		</div>
		<!-- Content Block End -->
	</div>
	</article>
	<!--Article Block End-->
<jsp:include page="footer.jsp"/>
	</div>
	<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-checktree.js"></script>
	<script src="../js/prepaid-lib.js" type="text/javascript"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
     <script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
	<script src="../js/agentrole.js" type="text/javascript"></script>
	<script src="../js/validation.js" type="text/javascript"></script>
	<script src="../js/jquery.datetimepicker.js"></script>
	<script src="../js/backbutton.js" type="text/javascript"></script>
	<script src="../js/bootstrap.js" type="text/javascript"></script>
	<script>
		var APP_SERVICE_BASE_URL="<%=request.getContextPath()%>";
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId9").addClass("active-background");
		}
		/* Common Navigation Include End */
		/*Collapse Panel functionality Start */
		function nextPanelOpen(collapsID) {
			$('#anchorClick' + (collapsID + 1)).click();
		}
		/*Collapse Panel functionality End */
		/* Custom Checkbox function Start*/
		$('#check-tree').checktree();
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
		_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
		_gaq.push([ '_trackPageview' ]);

		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl'
					: 'http://www')
					+ '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
		/* Custom Checkbox function End*/
		function toggleRadio(value) {
			
		if(value == 'INITIATOR'){
			if(document.getElementById("userType1").checked) {
				return;
			}
		}else if(value == 'APPROVER'){
			if(document.getElementById("userType2").checked) {
				return;
			}
		}else if(value == 'APPROVER_AND_INITIATOR'){
			if(document.getElementById("userType3").checked) {
				return;
			}
		}else if(value == 'ADMIN'){
			if(document.getElementById("userType4").checked) {
				return;
			}
		}	
			
		var myRadios = document.getElementsByName('userType');
		var setCheck;
		var x = 0;
		for (x = 0; x < myRadios.length; x++) {

			myRadios[x].onclick = function() {
				if (setCheck != this) {
					setCheck = this;
				} else {
					this.checked = false;
					setCheck = null;
				}
			};

		}
		}
	</script>
</body>
</html>
