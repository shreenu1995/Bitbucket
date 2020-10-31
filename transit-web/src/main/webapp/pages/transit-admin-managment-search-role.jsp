<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <%@page import="com.chatak.prepaid.enums.RoleLevel"%> --%>
<%@page import="com.chatak.transit.afcs.server.constants.Constants"%>
<%-- <%@page import="com.chatak.prepaid.admin.constants.JSPConstants"%> --%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Role Search</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script src="../js/roleRegistration.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/style.css" rel="stylesheet"> 
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
</head>
<body oncontextmenu="disableRightClick(<%=Constants.ALLOW_RIGHT_CLICK%>)">
	<!--Body Wrapper block Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid">
			<!--Navigation Block Start -->
			<%@include file="header.jsp"%>
			<!--Navigation Block Start -->
			<!--Article Block Start-->
			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
					<p>
						<span class="breadCrumbIcon"></span><span> Manage </span> 
						<span class="fa fa-angle-right"></span>
						<span> Role </span>
						<span class="fa fa-angle-right"></span><span> Search</span> 
						<span class="success-msg help-block align-center " id="success">${success}</span>
						<span class="has-error help-block align-center " id="error">${error}</span>
					</p>
				</div>
					
						<form action="agent-edit-role" name="editRoleForm" method="post">
					<input type="hidden" id="roleId" name="roleId" />
				</form>
					
					<form:form action="getRolesPages" name="paginationForm" method="post" >
					<input type="hidden" id="pageNumberId" name="pageNumber" />
					<input type="hidden" id="totalRecordsId" name="totalRecords"/>
				    <input type="hidden" name="CSRFToken" value="${tokenval}">
				</form:form>
				<form:form action="downloadRoleReport" name="downloadReport" method="post">
				      <input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" />
				      <input type="hidden" id="downloadTypeId" name="downloadType" />
				      <input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
				      <input type="hidden" id="totalRecordsflag" name="totalRecords" />
				      <input type="hidden" name="CSRFToken" value="${tokenval}">
			        </form:form>
				<form:form action="prepaid-admin-managment-view-role" name="viewRoleForm"
					method="post">
					<input type="hidden" id="viewRoleId" name="roleId" />
				    <input type="hidden" name="CSRFToken" value="${tokenval}">
				</form:form>
				<form name="viewUpdateRoleForm" action="viewUpdateRole"
					method="post">
					<input type="hidden" id="viewRolId" name="viewRoleId" /> <input
						type="hidden" id="viewRoleStatus" name="roleStatus" />
					<input type="hidden" name="CSRFToken" value="${tokenval}">
				</form>
					
					<!-- Tab Buttons Start -->
					<div class="marginL40">
					<c:if test="${fn:contains(existingFeatures,roleView)||fn:contains(existingFeatures,roleEdit)||fn:contains(existingFeatures,roleSuspend)||fn:contains(existingFeatures,roleActivate)}">
					<div class="tab-header-container active-background">
						<a href="#"><spring:message code="transit-admin-button.search"/></a>
					</div>
					</c:if>
					<c:if test="${fn:contains(existingFeatures,roleCreate)}">
						<div class="tab-header-container">
							<a href="prepaid-admin-create-role"><spring:message code="transit-admin-button.create"/></a>
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
										<span class="red-error" id="errorDiv">&nbsp;${error}</span> <span
											class="green-error" id="sucessDiv">&nbsp;${sucess}</span>
									</div>
								</div>
								<!--Success and Failure Message End-->
								<!-- Page Form Start -->
								<form:form modelAttribute="userRoleRequest"
									action="process-prepaid-admin-search-role" method="post">
							    <input type="hidden" name="CSRFToken" value="${tokenval}">
									<div class="col-sm-12">
										<div class="row">
											<div class="field-element-row">
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="transit-admin-roles-search-label.RoleName"/></label>
													<form:input path="roleName" id="roleName" maxlength="<%= Constants.ROLE_NAME.toString() %>"
														title="Use wild card like %* to search"
														cssClass="form-control" 
														onblur="clientValidation('roleName','middle_name','rolenameerror');" onkeypress="return charactersonly(this,event)"/>
													<div class="discriptionErrorMsg">
														<span id="rolenameerror" class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												  <fieldset class="col-md-3 col-sm-6"> 
														<label><spring:message code="transit-admin-roles-search-label.RoleCategory"/></label>
															<form:select  path="roleCategory" cssClass="form-control" onchange="validateRoleCategory(this.value)">
															  <form:option value=""><spring:message code="transit-admin-label.select"/></form:option>
																   <c:forEach items="${roleLevelList}" var="roleLevelList">
																         <form:option value="${roleLevelList.name()}"><%-- <spring:message code="${roleLevelList.value}"/> --%> </form:option>
															        </c:forEach>
															 </form:select>
															<div class="discriptionErrorMsg">
																<span class="red-error" id="roleCategoryError">&nbsp;</span>
															</div>
													</fieldset>
												<fieldset class="col-md-3 col-sm-6">
													<label><spring:message code="transit-admin-label.Status"/></label>
													<form:select id="status" path="status"
														cssClass="form-control">
														<form:option value=""><spring:message code="transit-admin-label.all"/></form:option>
														 <c:forEach items="${userRoleStatusList}" var="userRoleStatus">
															<form:option value="${userRoleStatus}"><%-- <spring:message code="${userRoleStatus}"/> --%></form:option>
														</c:forEach> 
														<%-- <form:option value="Terminated">Terminate</form:option> --%>
													</form:select>
													<div class="statusErrorMsg">
														<span class="red-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-md-3 col-sm-6"> 
													<label><spring:message code="transit-admin-label.recordPerPage"/></label>
													<form:select path="pageSize" cssClass="form-control">
													    	<c:forEach items="${recordsperpage}" var="recordperpage">	 
																	<form:option value="${recordperpage}">${recordperpage}</form:option>
															</c:forEach>
 													   </form:select>
											</div>
										</div>
										<!--Panel Action Button Start -->
										<div class="col-sm-12 form-action-buttons">
											<div class="col-sm-5"></div>
											<div class="col-sm-7">
												<input type="submit" class="form-control button pull-right"
													value="<spring:message code="transit-admin-button.search"/>" onclick="return searchValidateRoleName();"> <input type="button"
													class="form-control button pull-right"
													onclick="window.location.href='prepaid-admin-search-role'"
													value="<spring:message code="transit-admin-button.Reset"/>">
											</div>
										</div>

										<!--Panel Action Button End -->
									</div>
								</form:form>
								<!-- Page Form End -->
							</div>
						</div>
					</div>
					<!-- Content Block End -->
					<!-- Search Table Block Start -->
					 <c:set var ="flageCheck" scope="session" value="<%=Constants.YES%>"/>
					<c:if test="${roleList ne flageCheck }">
					<div class="search-results-table">
						<table class="table table-striped table-bordered table-condensed marginBM1">
							<!-- Search Table Header Start -->
							<tr>
								<td class="search-table-header-column widthP80"><span
									class="glyphicon glyphicon-search search-table-icon-text"></span>
									<span><spring:message code="transit-admin-label.searchSummary"/></span></td>
									<td class="search-table-header-column" style ="font-weight:bold;"><spring:message code="transit-admin-label.totalCount"/> : ${totalRecords}</td>
							</tr>
							</table>
							<!-- Search Table Header End -->
							<!-- Search Table Content Start -->
							<table id="serviceResults" class="table table-striped table-bordered table-responsive table-condensed tablesorter marginBM1 common-table resize-table">
							<thead>
							<tr>
								<th><spring:message code="transit-admin-roles-search-label.RoleName"/></th>
								<th><spring:message code="transit-admin-roles-search-label.RoleDescription"/></th>
								<th><spring:message code="transit-admin-roles-search-label.RoleCategory"/></th>
								<th><spring:message code="transit-admin-label.Status"/></th>
								<th class="sorter-false tablesorter-header tablesorter-headerUnSorted"><spring:message code="transit-admin-label.action"/></th>
							</tr>
							</thead>
							<c:choose>
								<c:when test="${!(fn:length(roleList) eq 0) }">
									<c:forEach items="${roleList}" var="role">
										<tr>
											<td data-title="Role Name" class="ellipsis" id="15" title="${role.roleName}">${role.roleName}&nbsp;</td>
											<td data-title="Role Description"><c:choose>
													<c:when test="${fn:length(role.description) gt 25}">
														<div title="${fn:substring(role.description,0,50)}">${fn:substring(role.description,0,25)}...</div>
													</c:when>
													<c:otherwise>
														<div>${role.description}&nbsp;</div>
													</c:otherwise>
												</c:choose></td>
												<td data-title="Role Category"><spring:message code="${role.roleType.value}" text="${role.roleType.value}"/>&nbsp;</td>
											<td data-title="Status"><spring:message code="${role.status}"/>&nbsp;</td>
											<td data-title="Action"><c:choose>
													<c:when
														test="${fn:containsIgnoreCase(role.status,'Active') }">
														<c:if test="${fn:contains(existingFeatures,roleEdit)}">
															<a href="javascript:editRole('${role.roleId}')"
																title=<spring:message code="prepaid-admin-label.Edit"/>><span
																class="glyphicon glyphicon-pencil"></span></a>
														</c:if>
														<c:if test="${fn:contains(existingFeatures,roleSuspend)}">
															<a
																href="javascript:changeStatus('${role.roleId}','Suspended','Suspended')"
																title="Suspend"> <img src="../images/deactive.png"
																alt="Suspend" title=<spring:message code="prepaid-admin-label.Suspended"/>></img></a>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if
															test="${fn:containsIgnoreCase(role.status,'Suspended') && fn:contains(existingFeatures,roleActivate)}">
															<a
																href="javascript:changeStatus('${role.roleId}','Active','Active')"
																title="Active"> <img alt="Active"
																src="../images/active.png" title=<spring:message code="transit-admin-label.Active"/>"></img>
															</a>
														</c:if>
													</c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5" style="color: red;"><spring:message code="transit-admin-RolesAreNotAvailable-message"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</table>
							<!-- Search Table Content End -->	
							<table class="table table-striped table-bordered table-condensed">
							<c:if test="${!(fn:length(roleList) eq 0) }">
								<tr class="table-footer-main">
									<td colspan="10" class="search-table-header-column">
										<div class="col-sm-12">
											<div class="col-md-3 col-sm-6">
												<div class="btn-toolbar" role="toolbar">
													<div class="btn-group custom-table-footer-button">
														<a href="javascript:downloadReport('${portalListPageNumber}', '<%=Constants.XLS_FILE_FORMAT%>','${totalRecords}')">
                                                    	<button type="button" class="btn btn-default">
                                                    		<img src="../images/excel.png">
                                                    	</button>
                                                    </a>
                                                    
                                                    <a href="javascript:downloadReport('${portalListPageNumber}','<%=Constants.PDF_FILE_FORMAT%>','${totalRecords}')">
														<button type="button" class="btn btn-default">
															<img src="../images/PDF.png"/>
														</button>
													</a>
													</div>
													<div>
															   <input type="checkbox"	class="autoCheck check" id="totalRecordsDownload">
																<spring:message code="transit-admin-label.downloadAll" />
														</div>
												</div>
											</div>
										<div class="col-md-9 col-sm-6">
										<c:if test="${ !(fn:length(roleList) eq 0)}">
													<ul class="pagination custom-table-footer-pagination">
														<c:if test="${portalListPageNumber gt 1}">
															<li><a href="javascript:getPortalOnPageWithRecords('1','${totalRecords}')">
																				&laquo;</a></li>
															<li><a href="javascript:getPortalPrevPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
																				&lsaquo; </a></li>
														</c:if>
		
														<c:forEach var="page" begin="${beginPortalPage }"
																end="${endPortalPage}" step="1" varStatus="pagePoint">
																<c:if test="${portalListPageNumber == pagePoint.index}">
																	<li	class="${portalListPageNumber == pagePoint.index?'active':''}">
																		 <a href="javascript:">${pagePoint.index}</a>
																	 </li>
																</c:if>
																<c:if test="${portalListPageNumber ne pagePoint.index}">
																	<li class=""><a
																		href="javascript:getPortalOnPageWithRecords('${pagePoint.index }','${totalRecords}')">${pagePoint.index}</a>
																	</li>
																</c:if>
															</c:forEach>
															<c:if test="${portalListPageNumber lt portalPages}">
																<li><a href="javascript:getPortalNextPageWithRecords('${portalListPageNumber }', '${totalRecords}')">
																				&rsaquo;</a></li>
																<li><a href="javascript:getPortalOnPageWithRecords('${portalPages }', '${totalRecords}')">&raquo;
																    </a></li>
														   </c:if>
													   </ul>
									              </c:if>
										     </div>
										</div> <!-- <div class="col-md-9 col-sm-6">	
											<ul class="pagination custom-table-footer-pagination">
												<li><a href="#">&laquo;</a></li>
												<li><a href="#">1</a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li><a href="#">5</a></li>
												<li><a href="#">&raquo;</a></li>
											</ul>	
										</div> -->
									</td>

								</tr>
							</c:if>

							<!-- Search Table Content End -->
						</table>
							<!--Panel Action Button Start -->
							<div class="col-sm-12 form-action-buttons">
								<fieldset class="col-sm-3 pull-right">
									<input type="button"
										class="form-control button pull-right table-hide-btn"
										value=<spring:message code="transit-admin-button.Back" />>
								</fieldset>
							</div>
							<!--Panel Action Button End -->
						</div>
						</c:if>
					<!-- Search Table Block End -->
				</div>
			</article>
			<!--Article Block End-->
			<jsp:include page="footer.jsp"/>
		</div>
		<!--Container block End -->
	</div>
	<!--Body Wrapper block End -->


	<div id="rolePopupDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove"
			onclick="closePopup();clearPopupDesc();"></span>
		<h2><spring:message code="transit-admin-programmanager-search-label.ChangeStatus"/></h2>
		<form:form action="roleActivation" name="roleActivationForm" method="post">
			<input type="hidden" id="roleActivateId" name="roleActivateId" /> <input
				type="hidden" id="roleStatus" name="roleStatus" /> 
				<input type="hidden" id="roleType" name="roleType" /> 
				 <input type="hidden" name="CSRFToken" value="${tokenval}">
				<label><span class="requiredFiled">*</span> <spring:message code="transit-admin-label.Reason"/> </label>
			<textarea id="reason" name="reason" maxlength="<%= Constants.REASON.toString() %>" onblur="this.value=this.value.trim().replace(/(?:\r\n|\r|\n)/g, ' ');validateReason();"
				onblur="validatePopupDesc();clientValidation('reason', 'reason','popDescError_div')"></textarea>
			<div class="discriptionErrorMsg">
				<span class="red-error" id="popDescError_div">&nbsp;</span>
			</div>
			<!--Panel Action Button Start -->
			<div class="col-sm-12 form-action-buttons"><div class="">
					<input type="submit" class="form-control button pull-right"
						value="<spring:message code="transit-admin-button.submit"/>" onclick="return validatePopupDesc();">
				</div>
			</div>
		</form:form>
		<!--Panel Action Button End -->
		<p><spring:message code="transit-admin-roles-search-label.RoleNote"/></p>
	</div>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery.popupoverlay.js" type="text/javascript"></script>
	<script src="../js/role.js" type="text/javascript"></script>
	<script src="../js/backbutton.js" type="text/javascript"></script>
	<script src="../js/messages.js"></script>
	<!-- Tablesorter plugin Start-->
	<!-- <script src="../js/validation.js"></script>  -->
	<script src="../js/sortable.js"></script>
   <!--  <script src="../js/bank.js"></script>  -->
	<script>
		/* Select li full area function Start */
		$("li").click(function() {
			window.location = $(this).find("a").attr("href");
			return false;
		});
		/* Select li full area function End */
		/* Common Navigation Include Start */
		function highlightMainContent() {
			$("#navListId2").addClass("active-background");
		}
		/* Common Navigation Include End */

		$(document).ready(function() {
			$('#rolePopupDiv').popup({
				blur : false
			});
			 $('#roleName').focus();
		});
		function closePopup() {
			$('#rolePopupDiv').popup("hide");
		}
		function openPopup() {
			$('#rolePopupDiv').popup("show");
		}
		
		$(".table-hide-btn").click(function(){
			$(".search-results-table").slideUp();
		});
		
		$(document).ready(function() {
			/* Table Sorter includes Start*/
			$(function() {
				
					  // call the tablesorter plugin
					  $('#serviceResults').sortable({
						
						 divBeforeTable: '#divbeforeid',
						divAfterTable: '#divafterid',
						initialSort: false,
						locale: 'th',
						//negativeSort: [1, 2]
					});
			});
			});
	</script>
</body>
</html>
