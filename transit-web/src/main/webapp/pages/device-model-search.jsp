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
<title>Device Onboarding Search</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">

<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/messages.js" type="text/javascript"></script>
<script src="../js/deviceModelReg.js"></script>
</head>
<body>
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
							class="breadcrumb-text">Device Model</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Search</span>
					</div>
					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>
					<div
						class="tab-header-container active-background marginL40 tab-header-active">
						<a href="device-model-search">Search</a>
					</div>

					<div class="tab-header-container">
						<a href="device-model-registration">Create</a>
					</div>
					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">

								<!-- Page Form Start -->
								<form action="device-model-search-pagination"
									name="paginationForm" method="post">
									<input type="hidden" id="pageNumberId" name="pageNumber" />
								</form>

								<form action="edit-device-model-action"
									name="editDeviceModelAction" method="post">
									<input type="hidden" id="deviceModelEdit" name="deviceId" />
								</form>

								<form action="device-model-view-action"
									name="viewDeviceModelAction" method="post">
									<input type="hidden" id="deviceModelView" name="deviceId" />
								</form>
								
								    <form:form action="downloadDeviceModelReport" name="downloadReport"
							method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords"
								name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="gfghfhf">
						    </form:form>

								<form:form modelAttribute="deviceModelSearch"
									action="device-model-search" method="post">
									<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
									<div class="col-sm-12 widgetDescriptionForm">
										<!-- Overview section start -->

										<div class="row Overview-section">
											<div class="widgetDescriptionRow">
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled"></span> Device
														Type List</legend>

													<form:select id="deviceTypeId" path="deviceTypeId"
														class="form-control"
														onchange="doAjaxFetchDeviceMAnufacturer(this.value);">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${deviceTypeListData}"
															var="deviceTypeListData">
															<form:option value="${deviceTypeListData.deviceTypeId}">${deviceTypeListData.deviceTypeName}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="deviceTypeNameMsgDiv" style="color: red;">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled"></span> Device
														Manufacturer </legend>
													<form:select id="deviceManufacturerCode"
														name="deviceManufacturerCode" path="deviceManufacturerCode"
														class="form-control">
														<form:option value="">..:Select:..</form:option>
														<c:forEach items="${deviceManufacturerListData}"
															var="deviceManufacturerListData">
															<form:option value="${deviceManufacturerListData.deviceManufacturerCode}">${deviceManufacturerListData.deviceManufacturer}</form:option>
														</c:forEach>
													</form:select>
													<div class="discriptionErrorMsg">
														<span id="deviceManufacturerMsgDiv"
															class="red-color-error"></span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled"></span> Device
														Model</legend>
													<form:input cssClass="form-control" path="deviceModel"
														id="deviceModel" maxlength="50"
														onblur="this.value=this.value.trim();" autocomplete='off'></form:input>

													<div class="discriptionErrorMsg">
														<span id="deviceModelMsgDiv" class="red-color-error"></span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="imp-star">Status</span></legend>
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
											</div>
										</div>

										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" id="device-model-search"
														value="Search" onclick=""
														class="form-control button pull-right"> <a
														href="device-model-search"
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
													Count : <label id="totalRecords">${deviceModelDataSize}</label>
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
											<th data-column="0"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Name: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device Type</div>
											</th>
											<th data-column="0"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Name: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device
													Manufacturer Name</div>
											</th>


											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device Model
													Name</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device IMEI
													Number</div>
											</th>

											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
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
											<c:when test="${!(fn:length(deviceModelListData) eq 0) }">
												<c:forEach items="${deviceModelListData}"
													var="deviceModelListData">
													<tr>
														<td>${deviceModelListData.deviceTypeName}</td>
														<td>${deviceModelListData.deviceManufacturer}</td>
														<td>${deviceModelListData.deviceModel}</td>
														<td>${deviceModelListData.deviceIMEINumber}</td>
														<td>${deviceModelListData.description}</td>
														<td><c:if
																test="${deviceModelListData.status eq 'Active'}">
																<div class="active">Active</div>
															</c:if> <c:if
																test="${deviceModelListData.status eq 'Suspended'}">
																<div class="suspend">Suspended</div>
															</c:if></td>
														<td><a
															href="javascript:viewDeviceModel('${deviceModelListData.deviceId}')"
															title="View"> <img src="../images/eyeimage.png" alt="eyeimage"
																title="view"></img></a> <c:choose>
																<c:when
																	test="${fn:containsIgnoreCase(deviceModelListData.status, 'Active') }">
																	<a
																		href="javascript:editDeviceModel('${deviceModelListData.deviceId}')"
																		title="Edit"> <span
																		class="glyphicon glyphicon-pencil"></span></a>
																</c:when>
															</c:choose> <c:choose>
																<c:when
																	test="${fn:containsIgnoreCase(deviceModelListData.status, 'Active') }">
																	<a class="operation-status operation-status_suspended"
																		title="Suspend"
																		href="javascript:changeDeviceModelStatus('${deviceModelListData.deviceId}','Suspended')"><img
																		src="../images/deactive.png" alt="Suspend"
																		title="Suspend"></img></a>
																	<a class="operation-status operation-status_terminated"
																		title="Terminate"
																		href="javascript:changeDeviceModelStatus('${deviceModelListData.deviceId}','Terminated')"><i
																		class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>
																</c:when>
																<c:when
																	test="${fn:containsIgnoreCase(deviceModelListData.status, 'Suspended') }">
																	<a class="operation-status operation-status_active"
																		title="Active"
																		href="javascript:changeDeviceModelStatus('${deviceModelListData.deviceId}','Active')"><img
																		alt="Active" src="../images/active.png"
																		title="Activate"></img></a>
																	<a class="operation-status operation-status_suspended"
																		title="Terminate"
																		href="javascript:changeDeviceModelStatus('${deviceModelListData.deviceId}','Terminated')"><i
																		class="glyphicon glyphicon-trash" aria-hidden="true"></i></a>
																</c:when>
															</c:choose></td>
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
								<c:if test="${!(fn:length(deviceModelListData) eq 0) }">
									<tr>
										<td colspan="15" class="tblPagination">
											<div class="exportResults">
												<table>
													<tr>
															<a
													href="javascript:downloadReport('${portalListPageNumber}', 'XLS', ${deviceModelDataSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/excel.png" alt="excel download image">
													</button>
												</a>
												<a
													href="javascript:downloadReport('${portalListPageNumber}', 'PDF', ${deviceModelDataSize})">
													<button type="button" class="btn btn-default">
														<img src="../images/pdf_icon.png" alt="pdf download image">
													</button>
												</a>
													</tr>
												</table>

											</div> <!-- Pagination --> <c:if
												test="${ !(fn:length(deviceModelListData) eq 0)}">
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

	<div id="deviceModelDiv" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove pull-right"
			onclick="closePopup()"></span>
		<h5 id="statusId">Change Status</h5>
		<form:form action="update-device-model-status"
			name="changeDeviceModelStatusAction" method="post">
			<input type="hidden" id="deviceModelIDStatus" name="deviceId" />
			<input type="hidden" id="deviceModelStatus" name="status" />
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
			$('#deviceModelDiv').popup({
				blur : false
			});
			$('input:visible:enabled:first').focus();
		});

		function closePopup() {
			$('#deviceModelDiv').popup("hide");
		}
	</script>
</body>
</html>