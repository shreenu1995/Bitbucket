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

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Chatak Admin Portal</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<!-- Tablesorter plugin End-->
<link href="../css/main.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">
<link href="../css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css" />
<link href="../css/jquery-ui.css" rel="stylesheet">
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/bootstrap-datepicker.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/terminal-setup-report.js" type="text/javascript"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js" type="text/javascript"></script>

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
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Reports</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Terminal Setup Report</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Search</span>
			</div>
			<div
				class="tab-header-container active-background marginL40 tab-header-active">
				<a href="terminal-setup-report">Terminal Setup Report</a>
			</div>
			<span class="success-msg help-block align-center pull-left"
				id="sucessDiv">${success}</span> <span
				class="has-error help-block align-center pull-left" id="errorDiv">${error}</span>
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<!-- Main Page Error Messages End -->
						<!-- Page Form Start -->
						<form action="terminal-setup-report-pagination"
							name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" />
						</form>
						<form:form action="terminal-setup-report"
							modelAttribute="terminalSetupRequest" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!--  Overview section start-->
								<h4>Overview</h4>
								<div class="row">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<label><span class="requiredFiled">*</span>Pto
												Operation List</label> <select id="ptoOperationId"
												path="ptoOperationId" name="ptoOperationId"
												class="form-control"
												onblur="this.value=this.value.trim();validatePtoOperationList();"
												class="required">
												<option value="select">..:Select:..</option>
												<c:forEach items="${ptoOperationList}"
													var="ptoOperationList">
													<option value="${ptoOperationList.ptoOperationId}">${ptoOperationList.ptoOperationName}</option>
												</c:forEach>

											</select>
											<div class="discriptionErrorMsg">
												<span id="ptoOperationIdDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<label><span class="requiredFiled"></span> Equiment
												Oem Id </label>
											<form:input cssClass="form-control" path="equimentOemId"
												id="equimentOemId" maxlength="13"
												onblur="this.value=this.value.trim();validateEquimentOemId();"
												onclick="clearErrorMsg('equimentOemIdDiv');"
												autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="equimentOemIdDiv" class="red-color-error"></span>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<label><span class="requiredFiled"></span> Equiment
												Model Number</label>
											<form:input cssClass="form-control"
												path="equimentModelNumber" id="equimentModelNumber"
												maxlength="13"
												onblur="this.value=this.value.trim();validateEquimentModelNumber();"
												onclick="clearErrorMsg('equimentModelNumberDiv');"
												autocomplete='off'></form:input>
											<div class="discriptionErrorMsg">
												<span id="equimentModelNumberDiv" class="red-color-error"></span>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<label><span class="imp-star"></span>Generated
												Date-From</label>
											<div class="input-group generationDate">
												<input type="text" id="generationDateStart"
													path="generationDateStart"
													class="form-control generationDateshow" /> <label
													class="input-group-addon btn" for="generationDateStart">
													<span class="fa fa-calendar"></span>
												</label>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<label><span class="imp-star"></span>Generated
												Date-To</label>
											<div class="input-group generationDate">
												<input type="text" id="generationDateEnd"
													class="form-control generationDateshow"> <label
													class="input-group-addon btn" for="generationDateEnd">
													<span class="fa fa-calendar"></span>
												</label>
											</div>
										</fieldset>
									</div>
								</div>
								<!-- Operator section end-->
								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" value="Report View"
												class="form-control button pull-right" id="register"
												onclick="return validateReportSubmit();"> <a
												href="terminal-setup-report"
												class="form-control button pull-right wedgetResetBtn">Reset</a>
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
			<div id="noofRecordFetched"
				style="margin-left: 50px; padding-bottom: 10px; font-size: 14px; font-weight: bold"></div>
			<div id="table_wrapper"
				style="margin: 20px 19px; border: 1px solid #c0bfbf; display: none">
				<table id="myTable" class="display">
				</table>
			</div>
			<!-- Search Table Block Start -->
			<script type="text/javascript">
				$(function() {
					$('#datetimepicker1').datetimepicker({
						language : 'pt-BR'
					});
				});
			</script>

			<c:if test="${searchFlag eq true}">
				<div class="searchResultsTable">
					<table
						class="table table-striped table-condensed hasFilters no-marginB"
						role="grid">
						<table
							class="table table-striped table-condensed hasFilters no-marginB"
							role="grid">
							<!-- Search Table Header Start -->
							<thead>
								<tr>
									<td colspan="9" class="searchRsltTblHeading">
										<h6>
											<label>Search Result</label> <span class="pull-right">Total
												Count : <label id="totalRecords">${terminalReportDataSize}</label>
											</span>
										</h6>
									</td>
								</tr>
							</thead>
						</table>
						<table>

							<div class="table-sub-container">
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
												<div class="tablesorter-header-inner">Pto Operation Id</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device Id</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Eqipment Oem Id</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Device Model</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Generation Date
													Time</div>
											</th>
										</tr>
									</thead>
									<tbody>

										<!-- Loop through the service provider list and display appropriately -->
										<c:choose>
											<c:when test="${!(fn:length(terminalReportData) eq 0) }">
												<c:forEach items="${terminalReportData}"
													var="terminalReportData">
													<tr>
														<td>${terminalReportData.ptoOperationId}</td>
														<td>${terminalReportData.deviceId}</td>
														<td>${terminalReportData.eqipmentOemId}</td>
														<td>${terminalReportData.deviceModel}</td>
														<td>${terminalReportData.generationDateTime}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="15" style="color: red">No Terminal Report
														data found</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<!-- Search Table Block end-->

								<table
									class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT">
									<c:if test="${!(fn:length(terminalReportData) eq 0) }">
										<tr>
											<td colspan="15" class="tblPagination">
												<div class="exportResults">
													<table>
														<tr>
															<c:if test="${!(fn:length(terminalReportData) eq 0) }">

																<td><a href="downloadDeviceXlSReport"> <img
																		src="../images/excel.png" alt="">
																</a></td>
																<!-- bellow comment Required in future -->
																<td><a href="downloadDevicePDFReport"> <img
																		src="../images/pdf_icon.png" alt=""></a></td>
															</c:if>
														</tr>
													</table>

												</div> <!-- Pagination --> <c:if
													test="${ !(fn:length(terminalReportData) eq 0)}">
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

							<!-- Loading ...-->
							<div id="wait"
								style="display: none; width: 69px; height: 89px; border: 0px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
								<img src='../images/ajax-loader.gif' alt="ajax-loader" width="64" height="64" /><br>
							</div>
							</body>
							</html>