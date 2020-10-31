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
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script src="../js/bootstrap-datepicker.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<script src="../js/rome.js"></script>
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/messages.js"></script>
<link href="../css/rome.css" rel="stylesheet">
<script src="../js/bootstrap.min.js"></script>
<script src="../js/setupsoftwaremaintenance.js"></script>
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
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Reports</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text"> Transaction Data Report</span>
					<span class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Search</span>
			</div>
			<span class="success-msg help-block align-center " id="success">${success}</span>
			<span class="has-error help-block align-center " id="error">${error}</span>
			<div class="tab-header-container active-background marginL40 tab-header-active">
				      <a href="transaction-data-report">Search</a>
			</div>
			
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<!-- Main Page Error Messages End -->
						<!-- Page Form Start -->
						<form action="transaction-data-report-pagination"
							name="paginationForm" method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" />
						</form>

						<form:form action="downloadTransactionDataDownloadReport"
							name="downloadReport" method="post">
							<input type="hidden" id="downloadPageNumberId"
								name="downLoadPageNumber" />
							<input type="hidden" id="downloadTypeId" name="downloadType" />
							<input type="hidden" id="totalRecords" name="totalRecords" />
							<input type="hidden" id="downloadAllRecords"
								name="downloadAllRecords" />
							<input type="hidden" name="CSRFToken" value="gfghfhf">
						</form:form>
						<form:form action="transaction-data-report"
							modelAttribute="transactionReportRequest" method="post">
							<div class="col-sm-12 widgetDescriptionForm">

								<div class="row Overview-section">
									<div class="widgetDescriptionRow">

										<fieldset class="col-sm-3 form-group">
											<legend class="custom-legend">
												<span class="requiredFiled"></span> Organization Name
											</legend>
											<form:select id="organizationId" name="organizationId"
												path="organizationId"
												onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
												Class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${organizationList}"
													var="organizationList">
													<form:option value="${organizationList.orgId}">${organizationList.organizationName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="organizationIdMsgDiv" style="color: red;">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class="custom-legend">
												<span class="requiredFiled"></span><span class="imp-star">PTO
													Name</span>
											</legend>
											<form:select id="ptoId" name="ptoId" path="ptoId" onblur=""
												class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${ptoListData}" var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class="custom-legend">
												<span class="requiredFiled"></span>Operator Name
											</legend>
											<form:select id="operatorId" name="operatorId"
												path="operatorId" class="form-control">
												<form:option value="">..:Select:..</form:option>
												<c:forEach items="${operatorListData}" var="operatorListData">
													<form:option value="${operatorListData.operatorId}">${operatorListData.operatorName}</form:option>
												</c:forEach>
											</form:select>
											<div class="discriptionErrorMsg">
												<span id="ptoOperationNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class="custom-legend">
											<span class="requiredFiled"></span>Transaction	Type
											</legend>
											<select id="transactionIdList" name=transactionId
												class="form-control" onchange="">
												<option value="">..:Select:..</option>
												<c:forEach items="transactionIdList"
													var="transactionIdList">
													<option value="TT01">TT01</option>
													<option value="TT02">TT02</option>
													<option value="TL01">TL01</option>
													<option value="A001">A001</option>
													<option value="A002">A002</option>
													<option value="F001">F001</option>
													<option value="F002">F002</option>
													<option value="P001">P001</option>
													<option value="TP01">TP01</option>
													<option value="TP02">TP02</option>
													<option value="CP01">CP01</option>
													<option value="CP01">CP01</option>
												</c:forEach>

											</select>
											<div class="discriptionErrorMsg">
												<span id="ptoOperationTypeDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group clear_both">
											<legend class="custom-legend-report">
												<span class="imp-star">Generated Date-From</span>
											</legend>
											<div class="input-group generationDate">
												<form:input type="text" id="generationDateStart"
													name="generationDateStart" path="generationDateStart"
													class="form-control generationDateshow"></form:input>
												<label class="input-group-addon btn"
													for="generationDateStart"> <span
													class="fa fa-calendar"></span>
												</label>
											</div>
										</fieldset>
										
										<fieldset class="col-sm-3 form-group">
											<legend class="custom-legend-report">
												<span class="imp-star">Generated Date-To</span>
											</legend>
											<div class="input-group generationDate">
												<form:input type="text" id="generationDateEnd"
													name="generationDateEnd" path="generationDateEnd"
													class="form-control generationDateshow"></form:input>
												<label class="input-group-addon btn" for="generationDateEnd">
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
											<input type="submit" id="transactionData" value="Generate"
												onclick="" class="form-control button pull-right"> <a
												href="transaction-data-report"
												class="form-control button pull-right wedgetResetBtn"><legend
													class="custom-legend-button">Reset</legend></a>
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
						<table
							class="table table-striped table-condensed hasFilters no-marginB"
							role="grid">
							<!-- Search Table Header Start -->
							<thead>
								<tr>
									<td colspan="9" class="searchRsltTblHeading">
										<h6>
											<label>Search Result</label> <span class="pull-right">Total
												Count : <label id="totalRecords">${transactionDataSize}</label>
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
												<div class="tablesorter-header-inner">Ticket number</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Transaction id</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Ticket date time</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Ticket fare
													amount</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Origin station
													code</div>
											</th>
											<th data-column="1"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Type: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Destination
													station code</div>
											</th>
											<th data-column="2"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Status: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Payment mode</div>
											</th>
											<th data-column="3"
												class="tablesorter-header tablesorter-headerUnSorted"
												tabindex="0" scope="col" role="columnheader"
												aria-disabled="false" aria-controls="serviceResults"
												unselectable="on" aria-sort="none"
												aria-label="Status: No sort applied, activate to apply an ascending sort">
												<div class="tablesorter-header-inner">Station code</div>
											</th>
										</tr>
									</thead>

									<tbody>

										<!-- Loop through the service provider list and display appropriately -->
										<c:choose>
											<c:when test="${!(fn:length(transactionData) eq 0) }">
												<c:forEach items="${transactionData}" var="transactionData">
													<tr>
														<td>${transactionData.ticketNumber}</td>
														<td>${transactionData.transactionId}</td>
														<td>${transactionData.ticketDateTime}</td>
														<td>${transactionData.ticketFareAmount}</td>
														<td>${transactionData.ticketOriginStationCode}</td>
														<td>${transactionData.ticketDestStationCode}</td>
														<td><c:if
																test="${fn:containsIgnoreCase(transactionData.ticketPaymentMode, 'P0')}">
																<div>Cash Payment</div>
															</c:if> <c:if
																test="${fn:containsIgnoreCase(transactionData.ticketPaymentMode, 'P1')}">
																<div>Card Payment</div>
															</c:if> <c:if
																test="${fn:containsIgnoreCase(transactionData.ticketPaymentMode, 'P2')}">
																<div>Wallet Payment</div>
															</c:if></td>
														<td>${transactionData.ticketOriginStationCode}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="15" style="color: red">No Transaction
														data found</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>

								<!-- Search Table Block end-->

								<table
									class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT">
									<c:if test="${!(fn:length(transactionData) eq 0) }">
										<tr>
											<td colspan="15" class="tblPagination">
												<div class="exportResults">
													<table>
														<tr>
															<a
																href="javascript:downloadReport('${portalListPageNumber}', 'XLS', ${transactionDataSize})">
																<button type="button" class="btn btn-default">
																	<img src="../images/excel.png"
																		alt="excel download image">
																</button>
															</a>
															<a
																href="javascript:downloadReport('${portalListPageNumber}', 'PDF', ${transactionDataSize})">
																<button type="button" class="btn btn-default">
																	<img src="../images/pdf_icon.png"
																		alt="pdf download image">
																</button>
															</a>
														</tr>
													</table>

												</div> <!-- Pagination --> <c:if
													test="${ !(fn:length(transactionData) eq 0)}">
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
							<script>
								rome(generationDateStart, {
									time : false,
									"inputFormat" : "DD/MM/YYYY"

								});
								rome(generationDateEnd, {
									time : false,
									"inputFormat" : "DD/MM/YYYY"
								});
							</script>
							</body>
							</html>