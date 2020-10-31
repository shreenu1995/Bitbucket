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
<title>Role Edit</title>
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
		<div class="container-fluid cw-admin-search-widget">
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<form name="inActiveRoleForm" action="inActivePage" method="post">
				</form>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Setup</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Role</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">Edit</span>
					</div>
					<form:form action="getRoleCategory" name="roleCategoryForm"
						method="post">
						<input type="hidden" id="roleCategory" name="roleCategory" /> <input
							type="hidden" id="rolesName" name="roleName" /> <input
							type="hidden" id="roleDiscription" name="description" />
					</form:form>
					<form action="role-search" name="cancelForm" method="post"
						value="">
						<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
					</form>

					<!-- Breadcrumb End -->
					<!-- Tab Buttons Start -->

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
								<form:form action="transitUpdateRole"
									modelAttribute="transitRoleRequest" method="post"
									onsubmit="buttonDisabled()">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
									<input type="hidden" id="permission" name="permission">
									<form:hidden path="roleId" />
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-sm-3">
													<label>Role Category</label>
													<form:input path="roleCategory" cssClass="form-control"
														readonly="true" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleCategoryError">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label><spring:message
															code="transit-admin-roles-search-label.RoleName" /><span
														class="required-field">*</span></label>
													<form:input path="roleName"
														maxlength="<%=Constants.ROLE_NAME.toString()%>"
														cssClass="form-control" readonly="true"
														onblur="this.value=this.value.trim();validRoleName('roleName','roleDiv','Please enter the role name','Please enter the valid role name')" />
													<div class="discriptionErrorMsg">
														<span class="red-error" id="roleDiv">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3">
													<label><spring:message
															code="transit-admin-roles-search-label.RoleDescription" /><span
														class="required-field">*</span></label>
													<form:input cssClass="form-control" id="description"
														path="description"
														maxlength="<%=Constants.ROLE_DESCRIPTION.toString()%>"
														readonly="true"
														onblur="this.value=this.value.trim();validRoleDescription('description','roleDescription','Please enter the description','Please enter the valid description')" />
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
																	<div class="panel panel-default clearfix">
																		<!-- Panel Header Start -->
																		<div class="panel-heading permission_color">
																			<h4 class="panel-title">
																				<a id="anchorClick${feature.getFeatureId()}"
																					data-toggle="collapse" data-parent="#accordion"
																					href="#collapse${feature.getFeatureId()}"
																					class="fa fa-angle-double-left"> <label
																					class="permission_cursor_type"
																					style="font-size: 14px; font-family: Verdana, Geneva, sans-serif; font-style: normal;"><spring:message
																							text="${feature.getName()}"
																							code="${feature.getName()}" /></label>
																				</a>
																			</h4>
																		</div>
																		<!-- Panel Header End -->
																		<!-- Panel Content Start -->
																		<div id="collapse${feature.getFeatureId()}"
																			class="panel-collapse collapse">

																			<li><label><input type="checkbox"
																					name="feature" value="${feature.getFeatureId()}"
																					id="${feature.getFeatureId()}"
																					<c:if test="${fn:contains(roleExistingFeatures,feature.getRoleFeatureId())}">checked</c:if> />
																					<spring:message text="${feature.getName()}"
																						code="${feature.getName()}" /></label>
																				<ul>
																					<c:forEach items="${featureList}" var="featureSub"
																						varStatus="counter">
																						<c:if
																							test="${featureSub.getRefFeatureId() eq feature.getFeatureId()}">

																							<!-- DisplayChild(Level2) -->
																							<fieldset class="col-md-3 col-sm-6">
																								<li><label><input type="checkbox"
																										name="feature"
																										id="${featureSub.getFeatureId()}"
																										value="${featureSub.getFeatureId()}"
																										<c:if test="${fn:contains(roleExistingFeatures,featureSub.getRoleFeatureId())}">checked</c:if> />
																										<spring:message text="${featureSub.getName()}"
																											code="${featureSub.getName()}" /></label>
																									<ul>

																										<c:forEach items="${featureList}"
																											var="featureChild">
																											<c:if
																												test="${featureChild.getRefFeatureId() eq featureSub.getFeatureId()}">
																												<!-- DisplayChild(Level3) -->
																												<li><label><input
																														type="checkbox" name="feature"
																														id="${featureChild.getFeatureId()}"
																														value="${featureChild.getFeatureId()}"
																														<c:if test="${fn:contains(roleExistingFeatures,featureChild.getRoleFeatureId())}">checked</c:if> />
																														<spring:message
																															text="${featureChild.getName()}"
																															code="${featureChild.getName()}" /></label></li>
																											</c:if>
																										</c:forEach>
																									</ul>
																							</fieldset></li>
																</c:if>
															</c:forEach>
														</ul>
														</li>
													</div>

													<!-- Panel Content End -->
												</div>
												<!-- User Information Collapseable panel End-->
												</c:if>
												</c:forEach>
												</ul>
											</div>

										</div>

									</div>
								</form:form>
							</div>
							<!--Panel Action Button Start -->
							<div class="col-sm-12 form-action-buttons">
								<div class="col-sm-6">&nbsp;</div>
								<div class="col-sm-6">
									<c:if test="${SAME_ROLE_FLAG eq true}">
										<input type="submit" class="form-control button pull-right"
											value=" <spring:message code="transit-admin.button.Update"/> "
											disabled="disabled" id="buttonCreate"
											onclick="return continueEditRole()">
										<a href="#" id="processingButton1" style="display: none;"
											class="form-control button pull-right"><spring:message
												code="transit-admin-roles-search-label.Processing" /> </a>
										<a href="javascript:cancelButton()"
											class="form-control button pull-right"> Cancel </a>
									</c:if>
									<c:if test="${SAME_ROLE_FLAG eq false}">
										<input type="submit" class="form-control button pull-right"
											value="<spring:message code="transit-admin.button.Update"/>"
											id="buttonCreate" onclick="return continueEditRole()">
										<a href="#" id="processingButton1" style="display: none;"
											class="form-control button pull-right"><spring:message
												code="transit-admin-roles-search-label.Processing" /> </a>
										<a href="javascript:cancelButton()"
											class="form-control button pull-right"> Cancel </a>
									</c:if>
								</div>
							</div>

							<!--Panel Action Button End -->
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
		var APP_SERVICE_BASE_URL="<%=request.getContextPath()%>";
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
