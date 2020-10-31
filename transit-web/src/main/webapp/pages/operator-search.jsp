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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Operator Search</title>
<script src="../js/jquery.js" type="text/javascript"></script>

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.min.js"></script>
<script src="../js/operatormanagement.js"></script>
<script src="../js/ptoManagement.js"></script>
<script src="../js/common.js"></script>

</head>
<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!-- Navigation Bar Start --->
		<div id="header"></div>
		<jsp:include page="header.jsp" />
		<!-- Navigation Bar End -->
		<!--Article Block Start-->
		<article>
		<div class="col-xs-12 content-wrapper">
			<div class="breadCrumb">
				<span class="breadcrumb-text">Masters</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Operator</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Search</span>
			</div>
			<span class="success-msg help-block align-center " id="success">${success}</span>
			<span class="has-error help-block align-center " id="error">${error}</span>

			<!-- Tab Buttons Start -->
			<div
				class="tab-header-container active-background marginL40 tab-header-active">
				<a href="operator-search">Search</a>
			</div>
			<div class="tab-header-container ">
				<a href="operator-register">Create</a>
			</div>

			<!-- Tab Buttons End -->

			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Page Form Start -->

						<form action="operator-search-pagination" name="paginationForm"
							method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" />
						</form>

						<form action="operator-view" name="viewOperatorAction"
							method="post">
							<input type="hidden" id="operatorView" name="operatorId" />
						</form>

						<form action="operator-edit-action" name="editOperatorAction"
							method="post">
							<input type="hidden" id="operatorEdit" name="operatorId" />
						</form>
						
						<form:form action="downloadOperatorReport" name="downloadReport"
							method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords"
								name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="gfghfhf">
						</form:form>
						

						<form:form modelAttribute="operatorsearchrequest"
							action="operator-search" method="post">
							<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
									<c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled"></span>
													Organization Name</legend>
												<form:select id="organizationId" name="orgId"
													path="orgId"
													onchange="fetchPtoListByOrganizationId(this.value, 'ptoId')"
													Class="form-control">
													 <form:option value="">..:Select:..</form:option>
													<c:forEach items="${organizationList}"
														var="organizationList">
														<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled"></span>PTO Name</legend>
												<form:select id="ptoId" name="ptoId" path="ptoId"
													class="form-control" onblur="validatePtoName();">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${ptoList}" var="ptoList">
														<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</c:when>
										<c:otherwise>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled"></span>
													Organization Name</legend>
												<form:select id="organizationId" name="orgId"
													path="orgId" Class="form-control">
													<c:forEach items="${organizationList}"
														var="organizationList">
														<form:option value="${organizationList.orgId}">${organizationList. organizationName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
												</div>
											</fieldset>
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled"></span>PTO Name</legend>
												<form:select id="ptoId" name="ptoId" path="ptoId"
													class="form-control" onblur="validatePtoName();">
													<c:forEach items="${ptoList}" var="ptoList">
														<form:option value="${ptoList.ptoMasterId}">${ptoList.ptoName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</c:otherwise>
									</c:choose>
									<fieldset class="col-sm-3 form-group">
										<legend class = "custom-legend"><span class="imp-star">Operator Name</span></legend>
										<form:input id="operatorName" name="operatorName"
											onblur="this.value=this.value.trim();" path="operatorName"
											class="form-control" type="text" value="" maxlength="50"></form:input>
										<div class="discriptionErrorMsg">
											<span id="operatorNameMsgDiv" class="red-color-error">&nbsp;</span>
										</div>
									</fieldset>
									<fieldset class="col-sm-3 form-group">
										<legend class = "custom-legend"><span class="requiredFiled"></span><span
											class="imp-star">Status</span></legend>
										<form:select id="status" name="status" path="status"
											class="form-control" onblur="validateRoleName();">
											<form:option value="">..:Select:..</form:option>
											<form:option value="Active">Active</form:option>
											<form:option value="Suspended">Suspended</form:option>
										</form:select>
										<div class="discriptionErrorMsg">
											<span id="statusMsgDiv" class="red-color-error">&nbsp;</span>
										</div>
									</fieldset>

								</div>

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="operator-search" value="Search"
												class="form-control button pull-right"> <a
												href="operator-search"
												class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend></a>
										</div>
									</fieldset>
								</div>
								<!-- Form Button End -->

							</div>
						</form:form>
						<!-- Page Form End -->
					</div>
				</div>

			</div>
			<!-- Content Block End -->

			<c:if test="${searchFlag eq true}">
				<div class="searchResultsTable">
					<table
						class="table table-striped table-condensed hasFilters no-marginB"
						role="grid">
						<!-- Search Table Header Start -->
						<thead>
							<tr>
								<td colspan="9" class="searchRsltTblHeading">
									<h6>
										<label>Search Result</label> <span class="pull-right">Total
											Count : <label id="totalRecords">${operatorListSize}</label>
										</span>
									</h6>
								</td>
							</tr>
						</thead>
					</table>
					<table>

						<table id="serviceResults"
							class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT no-marginB"
							role="grid">
							<thead>
								<!-- Search Table Header End -->
								<!-- Search Table Content Start -->
								<tr role="row" class="tablesorter-headerRow">
									<th data-column="2"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Operator Name</div>
									</th>

									<th data-column="0"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Name: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">PTO Name</div>
									</th>
									<th data-column="1"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Operator Contact
											Number</div>
									</th>

									<th data-column="1"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Status</div>
									</th>
									<th data-column="2"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Status: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Actions</div>
									</th>
								</tr>
							</thead>
							<tbody>

								<!-- Loop through the service provider list and display appropriately -->
								<c:if test="${ !(fn:length(operatorListData) eq 0)}">
									<c:forEach items="${operatorListData}" var="operatorListData">
										<tr>
											<td>${operatorListData.operatorName}</td>
											<td>${operatorListData.ptoName}</td>
											<td>${operatorListData.operatorContactNumber}</td>
											<td><c:if test="${operatorListData.status eq 'Active'}">
													<div class="active">Active</div>
												</c:if> <c:if test="${operatorListData.status eq 'Suspended'}">
													<div class="suspended">Suspended</div>
												</c:if></td>
											<td><a
												href="javascript:viewOperator('${operatorListData.operatorId}')"
												title="View"><img src="../images/eyeimage.png" alt="eyeimage"
													title="view"></img></a> <c:choose>
													<c:when
														test="${fn:containsIgnoreCase(operatorListData.status, 'Active') }">
														<a
															href="javascript:editOperator('${operatorListData.operatorId}')"
															title="Edit"> <span
															class="glyphicon glyphicon-pencil"></span></a>
													</c:when>
												</c:choose> <c:choose>
													<c:when
														test="${fn:containsIgnoreCase(operatorListData.status, 'Active') }">
														<a class="operation-status operation-status_suspended"
															title="Suspend"
															href="javascript:changeOperatorStatus('${operatorListData.operatorId}','Suspended')"><img
															src="../images/deactive.png" alt="Suspend"
															title="Suspend"></img></a>
														<a class="operation-status operation-status_terminated"
															title="Terminate"
															href="javascript:changeOperatorStatus('${operatorListData.operatorId}','Terminated')"><i
															class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>

													</c:when>
													<c:when
														test="${fn:containsIgnoreCase(operatorListData.status, 'Suspended') }">
														<a class="operation-status operation-status_active"
															title="Active"
															href="javascript:changeOperatorStatus('${operatorListData.operatorId}','Active')"><img
															alt="Active" src="../images/active.png" title="Activate"></img></a>
														<a class="operation-status operation-status_suspended"
															title="Terminate"
															href="javascript:changeOperatorStatus('${operatorListData.operatorId}','Terminated')"><i
															class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>

													</c:when>
												</c:choose></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${ (fn:length(operatorListData) eq 0)}">
									<tr>
										<td colspan="15" style="color: red">No Records Found</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</table>

					<!-- Search Table Block end-->
					<table
						class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT">
						<c:if test="${!(fn:length(operatorListData) eq 0) }">
							<tr>
								<td colspan="15" class="tblPagination">
									<div class="exportResults">
										<table>
											<tr>
													<a
													href="javascript:downloadReport('${portalListPageNumber}', 'XLS', ${operatorListSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/excel.png" alt="excel download image">
													</button>
												</a>
												<a
													href="javascript:downloadReport('${portalListPageNumber}', 'PDF', ${operatorListSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/pdf_icon.png" alt="pdf download image">
													</button>
												</a>
											</tr>
										</table>

									</div> <!-- Pagination --> <c:if
										test="${ !(fn:length(operatorListData) eq 0)}">
										<div class="afcspagination">
											<ul>
												<c:if test="${portalListPageNumber gt 1}">
													<li><a href="javascript:getPortalOnPage('1')"><img
															src="../images/first_icon.png" alt="first_icon"></a></li>
													<li><a
														href="javascript:getPortalPrevPage('${portalListPageNumber}')">
															<img src="../images/prev.png" alt="prev">
													</a></li>
												</c:if>

												<c:forEach var="page" begin="${beginPortalPage}"
													end="${endPortalPage}" step="1" varStatus="pagePoint">
													<c:if test="${portalListPageNumber == pagePoint.index}">
														<li
															class="${portalListPageNumber == pagePoint.index?'active':''}">
															<a href="javascript:">${pagePoint.index}</a>
														</li>
													</c:if>
													<c:if test="${portalListPageNumber ne pagePoint.index}">
														<li class=""><a
															href="javascript:getPortalOnPage('${pagePoint.index}')">${pagePoint.index}</a>
														</li>
													</c:if>
												</c:forEach>

												<c:if test="${portalListPageNumber lt portalPages}">
													<li><a
														href="javascript:getPortalNextPage('${portalListPageNumber}')"><img
															src="../images/next.png" alt="next"></a></li>
													<li><a
														href="javascript:getPortalOnPage('${portalPages}')"><img
															src="../images/last.png" alt="last"></a></li>
												</c:if>

											</ul>

										</div>
									</c:if> <!-- End of pagination -->

								</td>

							</tr>
						</c:if>
						<!-- Search Table Export Buttons End -->
					</table>
				</div>
			</c:if>
		</div>
		</article>
		<jsp:include page="footer.jsp" />
		<!--Article Block Start-->
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->

<div id="operatorDiv" class="locatioin-list-popup">
	<span class="glyphicon glyphicon-remove pull-right"
		onclick="closePopup()"></span>
	<h5 id="statusId">Change Status</h5>
	<form:form action="update-operator-status"
		name="changeOperatorStatusAction" method="post">
		<input type="hidden" id="operatorId" name="operatorId" />
		<input type="hidden" id="operatorStatus" name="status" />
		<label><span class="requiredFiled">*</span>Reason</label>
		<textarea id="reason" name="reason" onblur="this.value=this.value.trim().replace(/(?:\r\n|\r|\n)/g, ' ');validateReason();"></textarea>
		<div class="discriptionErrorMsg">
			<span class="red-color-error" id="reasonMsgDiv">&nbsp;</span>
		</div>
		<!--Panel Action Button Start -->
		<div class="col-sm-12 form-action-buttons">
			<div class="">
				<input type="submit" value="confirm"
					onclick="return validateReason();"
					class="form-control button pull-right pull-right"> <input
					type="button"
					class="form-control form-control button pull-right pull-right"
					onclick="closePopup()" value="Cancel">
			</div>
		</div>
	</form:form>
	<!--Panel Action Button End -->

</div>

<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.popupoverlay.js"></script>
<script>
	$(document).ready(function() {
		$('#operatorDiv').popup({
			blur : false
		});
		$('input:visible:enabled:first').focus();
	});

	function closePopup() {
		$('#operatorDiv').popup("hide");
	}
</script>
</body>
</html>