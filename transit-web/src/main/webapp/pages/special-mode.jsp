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
<title>Card Search</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/main.css" rel="stylesheet">

<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="../js/common.js"></script>
<script src="../js/cardManagement.js"></script>

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
				<p>
					<span class="breadCrumbIcon"></span><span> Special Mode for
						AFCS Management </span>
				</p>
			</div>

			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<!-- Main Page Error Messages End -->
						<!-- Page Form Start -->

						<form action="card-search-pagination" name="paginationForm"
							method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" />
						</form>
						<form action="view-card-action" name="viewCardAction"
							method="post">
							<input type="hidden" id="cardView" name="applicationId" />
						</form>

						<form:form modelAttribute="searchCardRequest"
							action="special-mode" method="post">
							<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->
								<div class="col-sm-12">
									<fieldset class="col-sm-3">
										<label><span class="imp-star">PTO Name</span></label> <select
											id="ptoName" class="form-control"
											onblur="validateRoleName();" name="ptoName">
											<option value="">..:Select:..</option>
											<option>Bangalore</option>
											<option>Mysore</option>
											<option>Chennai</option>
											<option>Mumbai</option>
											<option>Delhi</option>
											<option>Ahmedabad</option>
										</select>
										<div class="discriptionErrorMsg">
											<span id="categotyTypeDiv" class="red-color-error">&nbsp;</span>
										</div>
									</fieldset>
									<fieldset class="col-sm-3 col-xs-12">
										<label><span class="imp-star">Stop</span></label>
										<form:input id="name" name="name" path="name"
											class="form-control" type="text" value="" maxlength="50"></form:input>
										<div class="discriptionErrorMsg">
											<span id="cityMsgDiv" class="red-color-error">&nbsp;</span>
										</div>
									</fieldset>
									<fieldset class="col-sm-3 col-xs-12 ">
										<label><span class="imp-star">Device ID</span></label>
										<form:input id="emailID" name="emailID" path="emailID"
											class="form-control" type="text" value="" maxlength="13"></form:input>
										<div class="discriptionErrorMsg">
											<span id="countryMsgDiv" class="red-color-error">&nbsp;</span>
										</div>
									</fieldset>
								</div>
								<!-- Form Button Start -->
								<div class="col-sm-12">
									<div class="role-makerchecker-btn">
										<fieldset class="form-group">
											<div
												class="col-sm-6 widgetSearchButton no-search-criteria padding0">
												<input type="submit" id="stage-search" value="Search"
													class="form-control button pull-right"> <a
													href="special-mode" class="form-control button pull-right">Cancel</a>
											</div>
										</fieldset>
									</div>
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
										<label>Total Count : 0</label> </span>
									</h6>
								</td>
							</tr>
						</thead>
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
										<div class="tablesorter-header-inner">PTO</div>
									</th>
									<th data-column="2"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Location</div>
									</th>
									<th data-column="1"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Device ID</div>
									</th>

								</tr>
							</thead>
							<tbody>

								<!-- Loop through the service provider list and display appropriately -->
								<c:if test="${ !(fn:length(cardListData) eq 0)}">
									<c:forEach items="${cardListData}" var="cardListData">
										<tr>
											<td>${cardListData.passType}</td>
											<td>${cardListData.panNumber}</td>
											<td>${cardListData.name}</td>
											<td>${cardListData.mobileNumber}</td>
											<td>${cardListData.emailID}</td>
											<td>${cardListData.applicationId}</td>
											<td><a
												href="javascript:viewCard('${cardListData.applicationId}')"
												title="View"> <span class="table_icon table_icon_eye"></span></a>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${ (fn:length(cardListData) eq 0)}">
									<tr>
										<td colspan="15" style="color: red">No Records Found</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</table>
					<div class="col-sm-12">
						<fieldset class="col-sm-3 form-group align-center">
							<label><span class="requiredFiled">*</span><span
								class="imp-star">Special Mode</span></label> <select id="ptoName"
								class="form-control" onblur="validateRoleName();" name="ptoName">
								<option value="">..:Select:..</option>
								<option>Time mode override</option>
								<option>Entry/Exit override</option>
								<option>Stop Close</option>
								<option>Emergency Mode</option>
								<option>Incident Mode</option>
							</select>
							<div class="discriptionErrorMsg">
								<span id="categotyTypeDiv" class="red-color-error">&nbsp;</span>
							</div>
						</fieldset>
						<fieldset class="form-group spical_mode_serch_btn">
							<div
								class="col-sm-6 widgetSearchButton no-search-criteria padding0">
								<input type="submit" id="" value="Send"
									class="form-control button pull-right">
							</div>
						</fieldset>
					</div>




					<!-- Search Table Block end-->
					<table
						class="table table-striped table-bordered table-responsive table-condensed tablesorter tablesorter-default hasFilters no-marginT">
						<c:if test="${!(fn:length(cardListData) eq 0) }">
							<tr>
								<td colspan="15" class="tblPagination">
									<div class="exportResults"></div> <!-- Pagination --> <c:if
										test="${ !(fn:length(cardListData) eq 0)}">

									</c:if> <!-- End of pagination -->

								</td>

							</tr>
						</c:if>
						<!-- Search Table Export Buttons End -->
					</table>
				</div>
			</c:if>
		</div>
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
