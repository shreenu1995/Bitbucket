<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Device Manufacturer search</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/deviceManufacturer.js"></script>
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
					<span class="breadcrumb-text">Manage</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Device</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Device Manufacturer</span> <span
						class="glyphicon glyphicon-play icon-font-size"></span> <span
						class="breadcrumb-text">Search</span>
				</div>
				<span class="success-msg help-block align-center " id="success">${success}</span>
				<span class="has-error help-block align-center " id="error">${error}</span>
				<div
					class="tab-header-container active-background marginL40 tab-header-active">
					<a href="device-manufacturer-search">Search</a>
				</div>
				<div class="tab-header-container">
					<a href="device-manufacturer-registration">Create</a>
				</div>
				<div class="searchConteiner">
					<div class="row rowfluidalignment">
						<div class="col-sm-12">
							<!-- Main Page Error Messages Start -->
							<!-- Main Page Error Messages End -->
							<!-- Page Form Start -->

							<form action="device-manufacturer-search-pagination"
								name="paginationForm" method="post">
								<input type="hidden" id="pageNumberId" name="pageNumber" />
							</form>

							<form action="device-manufacturer-edit-action"
								name="editDeviceManufacturerAction" method="post">
								<input type="hidden" id="deviceManufacturerEdit"
									name="deviceManufacturerId" />
							</form>

							<form action="device-manufacturer-view-action"
								name="viewDeviceManufacturerAction" method="post">
								<input type="hidden" id="deviceManufacturerView"
									name="deviceManufacturerId" /> <input type="hidden"
									id="deviceTypeManufacturerView" name="deviceTypeName" />
							</form>

							<form action="update-device-manufacturer-status"
								name="changeDeviceManufacturerStatusAction" method="post">
								<input type="hidden" id="deviceManufacturerId"
									name="deviceManufacturerId" /> <input type="hidden"
									id="deviceManufacturerStatus" name="deviceManufacturerStatus" />
							</form>
                            
                            <form:form action="downloadDeviceManufacturerReport" name="downloadReport"
							method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords"
								name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="gfghfhf">
						    </form:form>
							
							<form:form modelAttribute="deviceManufacturerSearch"
								action="device-manufacturer-search" method="post">
								<input type="hidden" id="cancelManufacturerId"
									name="cancelManufacturerId" />
								<div class="col-sm-12 widgetDescriptionForm">
									<!-- Overview section start -->

									<div class="row Overview-section">
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="requiredFiled"></span> Device
													Type List</legend>
												<form:select id="deviceTypeId" path="deviceTypeId" name="deviceTypeId"
													Class="form-control">
													<form:option value="">..:Select:..</form:option>
													<c:forEach items="${deviceTypeListData}"
														var="deviceTypeListData">
														<form:option value="${deviceTypeListData.deviceTypeId}">${deviceTypeListData.deviceTypeName}</form:option>
													</c:forEach>
												</form:select>
												<div class="discriptionErrorMsg">
													<span id="deviceTypeNameDiv" style="color: red;">&nbsp;</span>
												</div>
											</fieldset>
										</div>
										<div class="widgetDescriptionRow">

											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend"><span class="imp-star">Device
														Manufacturer Name</span></legend>
												<form:input id="deviceManufacturer"
													name="deviceManufacturer" path="deviceManufacturer"
													class="form-control" type="text" value="" maxlength="50"></form:input>
												<div class="discriptionErrorMsg">
													<span id="deviceManufacturerMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>
										<%-- In future we will use this code --%>
										<%-- <div class="role-makerchecker-btn">
										<fieldset class="col-sm-3 form-group">
											<label><span class="imp-star">Status</span></label>
											<form:select id="status" path="status" class="form-control"
												name="status">
												<form:option value="">..:Select:..</form:option>
												<form:option value="Active">Active</form:option>
												<form:option value="Suspended">Suspended</form:option>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="discriptionErrorMsg" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div> --%>
									</div>
									<!-- Form Button Start -->
									<div class="widgetDescriptionRow">
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" id="device-manufacturer-search"
													value="Search" class="form-control button pull-right">
												<a href="device-manufacturer-search"
													class="form-control button pull-right"><legend class = "custom-legend-button">Reset </legend></a>
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
												Count : <label id="totalRecords">${deviceManufacturerDataSize}</label>
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
									<th data-column="0"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Name: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Device
											Manufacturer</div>
									</th>
									<th data-column="1"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Device Type</div>
									</th>
									<th data-column="3"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Status: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Description</div>
									</th>

									<th data-column="3"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Status: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Status</div>
									</th>

									<th data-column="3"
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
									<c:choose>
										<c:when
											test="${!(fn:length(deviceManufacturerListData) eq 0) }">
											<c:forEach items="${deviceManufacturerListData}"
												var="deviceManufacturerListData">
												<tr>
													<td>${deviceManufacturerListData.deviceManufacturer}</td>
													<td>${deviceManufacturerListData.deviceTypeName}</td>
													<td>${deviceManufacturerListData.description}</td>
													<td><c:if
															test="${deviceManufacturerListData.status eq 'Active'}">
															<div class="active">Active</div>
														</c:if> <c:if
															test="${deviceManufacturerListData.status eq 'Suspended'}">
															<div class="suspend">Suspended</div>
														</c:if></td>
													<td><c:choose>
															<c:when
																test="${fn:containsIgnoreCase(deviceManufacturerListData.status, 'Active') }">
																<a
																	href="javascript:viewDeviceManufacturer('${deviceManufacturerListData.deviceManufacturerId}')"
																	title="View"> <img src="../images/eyeimage.png" alt="eyeimage"
																	title="view"></img></a>
																<a
																	href="javascript:editDeviceManufacturer('${deviceManufacturerListData.deviceManufacturerId}')"
																	title="Edit"> <span
																	class="glyphicon glyphicon-pencil"></span></a>
															</c:when>
														</c:choose> <%-- In future we will use this code --%> <%-- <c:choose>
														<c:when
															test="${fn:containsIgnoreCase(deviceManufacturerListData.status, 'Active') }">
															<a class="operation-status operation-status_suspended"
																title="Suspend"
																href="javascript:changeDeviceManufacturerStatus('${deviceManufacturerListData.deviceManufacturerId}','Suspended')"><img
																src="../images/deactive.png" alt="Suspend"
																title="Suspend"></img></a>
															<a class="operation-status operation-status_terminated"
																title="Terminate"
																href="javascript:changeDeviceManufacturerStatus('${deviceManufacturerListData.deviceManufacturerId}','Terminated')"><i
																class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>
														</c:when>
														<c:when
															test="${fn:containsIgnoreCase(deviceManufacturerListData.status, 'Suspended') }">
															<a class="operation-status operation-status_active"
																title="Active"
																href="javascript:changeDeviceManufacturerStatus('${deviceManufacturerListData.deviceManufacturerId}','Active')"><img
																alt="Active" src="../images/active.png" title="Activate"></img></a>
															<a class="operation-status operation-status_suspended"
																title="Terminate"
																href="javascript:changeDeviceManufacturerStatus('${deviceManufacturerListData.deviceManufacturerId}','Terminated')"><i
																class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>
														</c:when>
													</c:choose> --%></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="15" style="color: red">No Records Found</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</table>
						<!-- Search Table Block end-->
						<table
							class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT">
							<c:if test="${!(fn:length(deviceManufacturerListData) eq 0) }">
								<tr>
									<td colspan="15" class="tblPagination">
										<div class="exportResults">
											<table>
												<tr>
													<a
													href="javascript:downloadReport('${portalListPageNumber}', 'XLS', ${deviceManufacturerDataSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/excel.png" alt="excel download image">
													</button>
												</a>
												<a
													href="javascript:downloadReport('${portalListPageNumber}', 'PDF', ${deviceManufacturerDataSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/pdf_icon.png" alt="pdf download image">
													</button>
												</a>
												</tr>
											</table>
										</div> <!-- Pagination --> <c:if
											test="${ !(fn:length(deviceManufacturerListData) eq 0)}">
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
		<div id="footer"></div>
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->
</body>
</html>