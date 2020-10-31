<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.chatak.transit.afcs.server.constants.Constants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title> Role Create </title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../js/roleRegistration.js" type="text/javascript"></script>
<script src="../js/common.js"></script>
<link href="../css/font-new-awesome.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/style.css" rel="stylesheet">
 
</head>
<body
	oncontextmenu="disableRightClick(<%=Constants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<div id="header"></div>
		<jsp:include page="header.jsp" />
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Setup</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Role</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">Create</span>
					</div>
					<form:form action="getRoleCategory" name="roleCategoryForm"
						method="post">
						<input type="hidden" id="roleCategory" name="roleCategory" />
						<input type="hidden" id="rolesName" name="roleName" />
						<input type="hidden" id="roleDiscription" name="description" />
						<input type="hidden" name="CSRFToken" value="${tokenval}">
					</form:form>
					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->
					<div class="marginL40">
						<c:if
							test="${fn:contains(existingFeatures,roleView)||fn:contains(existingFeatures,roleEdit)||fn:contains(existingFeatures,roleSuspend)||fn:contains(existingFeatures,roleActivate)}">
							<div class="tab-header-container">
								<a href="role-search"> <spring:message
										code="transit-admin-button.search" />
								</a>
							</div>
						</c:if>
						<c:if test="${fn:contains(existingFeatures,roleCreate)}">
							<div class="tab-header-container active-background">
								<a href="#"> <spring:message
										code="transit-admin-button.create" />
								</a>
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
										<span class="red-error" id="errorDataPermission">${error }</span>
										<span class="green-error" id="sucessDiv">${sucess }</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form action="transitAdminAddRole"
									modelAttribute="userRoleRequest" method="post"
									onsubmit="buttonDisabled()">
									<input type="hidden" name="CSRFToken" value="${tokenval}">
									<input type="hidden" id="permission" name="permission">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="transit-admin-roles-search-label.RoleCategory" /></label>
													<form:select id="roleCategory" name="roleCategory"
														path="roleCategory" cssClass="form-control"
														selected="true"
														onchange="validateRoleCategory(this.value)">
														<c:forEach items="${roleLevelList}" var="roleLevelList">
															<form:option value="${roleLevelList.name()}">
																<spring:message text="${roleLevelList.value}"
																	code="${roleLevelList.value}" />
															</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleCategoryError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="transit-admin-roles-search-label.RoleName" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" id="roleName"
														path="roleName"
														maxlength="<%=Constants.ROLE_NAME.toString()%>"
														onblur="this.value=this.value.trim();validRoleName('roleName','roleDiv',webMessages.ROLE_NAME,webMessages.VALID_ROLE_NAME);clientValidation('roleName','last_name','rolenameerror');"
														onkeypress="return charactersonly(this,event)" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message
															code="transit-admin-roles-search-label.RoleDescription" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" id="description"
														path="description"
														maxlength="<%=Constants.ROLE_DESCRIPTION.toString()%>"
														onblur="this.value=this.value.trim();validRoleDescription('description','roleDescription',webMessages.ENTER_DESC,webMessages.VALID_DESC_ENTER);clientValidation('description','last_name','roleDescription');"
														onkeypress="return charactersonly(this,event)" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleDescription">&nbsp;</span>
													</div>
												</fieldset>
												<div class="col-sm-12 role_assign_message">
													<label><spring:message
															code="transit-admin-roles-search-label.SelectPermissionstoAssign" /></label>
												</div>
												<div class="col-sm-12 roleListDiv">
													<!-- Collapseable panel Main Start -->
													<div class="panel-group" id="accordion">
														<ul id="check-tree" class="marginLM60">
															<c:forEach items="${featureList}" var="feature">
																<c:if test="${feature.getRefFeatureId() == 0}">
																	<!-- User Information Collapseable panel Start-->
																	<div
																		class="panel panel-default clearfix create-dropdown ">
																		<!-- Panel Header Start -->
																		<div class="panel-heading permission_color">
																			<h4 class="panel-title">
																				<a id="anchorClick${feature.getFeatureId()}"
																					data-toggle="collapse" data-parent="#accordion"
																					href="#collapse${feature.getFeatureId()}"
																					class="fa fa-angle-double-left"><label
																					class="permission_cursor_type"
																					style="font-size: 14px; font-family: Verdana, Geneva, sans-serif; font-style: normal;margin:0"><spring:message
																							text="${feature.getName()}"
																							code="${feature.getName()}" /></label> </a>
																			</h4>
																		</div>

																		<!-- Panel Header End -->
																		<!-- Panel Content Start -->
																		<div id="collapse${feature.getFeatureId()}"
																			class="panel-collapse collapse">

																			<li><label><form:checkbox path="feature"
																						value="${feature.getFeatureId()}"
																						id="${feature.getFeatureId()}" /><span
																					class="role-title"><spring:message
																							text="${feature.getName()}"
																							code="${feature.getName()}" /></span></label>
																				<ul>
																					<%
																						int count = 1;
																					%>
																					<c:forEach items="${featureList}" var="featureSub"
																						varStatus="counter">

																						<c:if
																							test="${featureSub.getRefFeatureId() eq feature.getFeatureId()}">

																							<!-- DisplayChild(Level2) -->
																							<%
																								if (count % 4 == 0) {
																							%>
																							<div class="row">
																								<fieldset class="col-md-3 col-sm-6">
																									<li><label><form:checkbox
																												path="feature"
																												id="${featureSub.getFeatureId()}"
																												value="${featureSub.getFeatureId()}" /><span
																											class="role-title"><spring:message
																													text="${featureSub.getName()}"
																													code="${featureSub.getName()}" /></span></label>
																										<ul>

																											<c:forEach items="${featureList}"
																												var="featureChild">
																												<c:if
																													test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																													<!-- DisplayChild(Level3) -->
																													<li><div class="row">
																															<label><form:checkbox
																																	path="feature"
																																	id="${featureChild.getFeatureId()}"
																																	value="${featureChild.getFeatureId()}" /><span
																																class="role-title"><spring:message
																																		text="${featureChild.getName()}"
																																		code="${featureChild.getName()}" /></span></label>
																														</div></li>
																												</c:if>
																											</c:forEach>
																										</ul>
																								</fieldset>

																							</div>
																							<%
																								} else {
																							%>
																							<fieldset class="col-md-3 col-sm-6">
																								<li><label><form:checkbox
																											path="feature"
																											id="${featureSub.getFeatureId()}"
																											value="${featureSub.getFeatureId()}" /><span
																										class="role-title"><spring:message
																												text="${featureSub.getName()}"
																												code="${featureSub.getName()}" /></span></label>
																									<ul>

																										<c:forEach items="${featureList}"
																											var="featureChild">
																											<c:if
																												test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																												<!-- DisplayChild(Level3) -->
																												<li><div class="row">
																														<label><form:checkbox
																																path="feature"
																																id="${featureChild.getFeatureId()}"
																																value="${featureChild.getFeatureId()}" /><span
																															class="role-title"><spring:message
																																	text="${featureChild.getName()}"
																																	code="${featureChild.getName()}" /> </span></label>
																													</div></li>
																											</c:if>
																										</c:forEach>
																									</ul>
																							</fieldset>
																							<%
																								}
																													count++;
																							%>
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
												<input type="submit" id="buttonCreate"
													class="form-control button pull-right"
													value=" <spring:message code="transit-admin-button.create"/> "
													onclick="return submitData()"> <a href="#"
													id="processingButton1" style="display: none;"> <input
													type="button" class="form-control button pull-right"
													value="Processing...">
												</a> <a href="showCreateRole"
													class="form-control button pull-right"><spring:message
														code="transit-admin-button.Reset" /></a>
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
	<jsp:include page="footer.jsp" />
	</div>
	<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-checktree.js"></script>
	<script src="../js/messages.js" type="text/javascript"></script>
	<script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
	<script src="../js/rome.js"></script>
	<script src="../js/backbutton.js" type="text/javascript"></script>
	<script>
	  /*  $('collapse'+'${feature.getFeatureId()}').mouseleave(function(){
		   $('#anchorClick' + (collapsID + 1)).hide(1000);
	   }); */
		var APP_SERVICE_BASE_URL = "<%=request.getContextPath()%>";
		/* Common Navigation Include Start */
		$(function() {
			$("#main-navigation").load("main-navigation.html");
		});
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */
		/*Collapse Panel functionality Start */
		/* function nextPanelOpen(collapsID) {
			$('#anchorClick' + (collapsID + 1)).click();
		} */
		/*Collapse Panel functionality End */
		/* var self = this;
		Spry.Widget.Accordion.addEventListener(tab, 'mouseover', function(e) { return self.onPanelTabClick(e, panel); }, false);
		Spry.Widget.Accordion.addEventListener(tab, 'mouseover', function(e) { return self.onPanelTabMouseOver(e, panel); }, false);
		Spry.Widget.Accordion.addEventListener(tab, 'mouseout', function(e) { return self.onPanelTabMouseOut(e, panel); }, false); */
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
	</script>
</body>
</html>
