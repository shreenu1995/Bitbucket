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
<title>Device Tracking</title>
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
				<span class="breadcrumb-text">Device Tracking</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Search</span> 
			</div>
			<span class="success-msg help-block align-center " id="success">${success}</span>
			<span class="has-error help-block align-center " id="error">${error}</span>

			<div
				class="tab-header-container active-background marginL40 tab-header-active">
				<a href="device-tracking">Search</a>
			</div>
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<!-- Main Page Error Messages End -->
						<!-- Page Form Start -->

						<form action="device-tracking-pagination" name="paginationForm"
							method="post">
							<input type="hidden" id="pageNumberId" name="pageNumber" />
						</form>
                        	<form:form action="download_device_tracking_report" name="downloadReport"
									method="post">
									<input type="hidden" id="downloadPageNumberId" name="downLoadPageNumber" /> 
									<input type="hidden" id="downloadTypeId" name="downloadType" />
									<input type="hidden" id="totalRecords" name="totalRecords" />
									<input type="hidden" id="downloadAllRecords" name="downloadAllRecords" />
									<input type="hidden" name="CSRFToken" value="gfghfhf">
								</form:form>
						<form:form modelAttribute="trackingRequest"
							action="device-tracking" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->

								<div class="row Overview-section">
								 <c:choose>
										<c:when
											test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled"></span>PTO Name</legend>
											<form:select id="ptoId" name="ptoId" path="ptoId"
												class="form-control">
												 <form:option value="">..:Select:..</form:option> 
												<c:forEach items="${ptoListData}" var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									</c:when>
									<c:otherwise>
									 <div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled"></span>PTO Name</legend>
											<form:select id="ptoId" name="ptoId" path="ptoId"
												class="form-control">
												<c:forEach items="${ptoListData}" var="ptoListData">
													<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
												</c:forEach>
											</form:select>
										</fieldset>
									</div>
									</c:otherwise>
									</c:choose>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend">Route ID</legend>
											<form:input id="firstName" name="routeId" path="routeId"
												class="form-control" type="text" value="" maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="firstNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend">Device Serial Number</legend>
											<form:input id="firstName" name="deviceSerialNo" path="deviceSerialNo"
												class="form-control" type="text" value="" maxlength="13"></form:input>
											<div class="discriptionErrorMsg">
												<span id="firstNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
									</div>
								</div>
								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="device-tracking" value="Search"
												class="form-control button pull-right"> <a
												href="device-tracking" class="form-control button pull-right"><legend class = "custom-legend-button">Reset</legend></a>
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
											Count : <label id="totalRecords">${searchDataSize}</label>
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
										<div class="tablesorter-header-inner">PTO</div>
									</th>
									<th data-column="2"
										class="tablesorter-header tablesorter-headerUnSorted"
										tabindex="0" scope="col" role="columnheader"
										aria-disabled="false" aria-controls="serviceResults"
										unselectable="on" aria-sort="none"
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Device Serial #</div>
									</th>
									<th data-column="2"
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
										aria-label="Type: No sort applied, activate to apply an ascending sort">
										<div class="tablesorter-header-inner">Details</div>
									</th>
								</tr>
							</thead>
							<tbody>

								<!-- Loop through the service provider list and display appropriately -->
								<c:if test="${ !(fn:length(searchList) eq 0)}">
									<c:forEach items="${searchList}" var="searchData">
										<tr>
											<td>${searchData.ptoName}</td>
											<td>${searchData.deviceSerialNo}</td>
											<td>${searchData.status}</td>
											<%-- <td><a  target="_blank" href="javascript:openMap('${searchData.trackLink}')">Click Here</a></td> --%>
											<td><a  target="_blank" href="javascript:loadMap('${searchData.latitude}','${searchData.longitude}')">Click Here</a></td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${ (fn:length(searchList) eq 0)}">
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
						<c:if test="${!(fn:length(searchList) eq 0) }">
							<tr>
								<td colspan="15" class="tblPagination">
									<div class="exportResults">
										<table>
											<tr>
												 <a
															href="javascript:downloadReport('${portalListPageNumber}', 'XLS', ${searchDataSize})">
															<button type="button" class="btn btn-default">
																<img src="../images/excel.png" alt="excel download image">
															</button>
														</a>
														 <a
															href="javascript:downloadReport('${portalListPageNumber}', 'PDF', ${searchDataSize})">
															<button type="button" class="btn btn-default">
																<img src="../images/pdf_icon.png" alt="pdf downlaod image">
															</button>
														</a>
											</tr>
										</table>

									</div> <!-- Pagination --> <c:if
										test="${ !(fn:length(searchList) eq 0)}">
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
	<div id="mapPopup" class="locatioin-list-popup">
		<span class="glyphicon glyphicon-remove" onclick="closePopup();"></span>
		<h2>Device Location</h2>
		<div id="map_container"></div>
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.popupoverlay.js"></script>
<script>
	rome(fromDate, {
		time : false,
		"inputFormat" : "DD/MM/YYYY"

	});
	rome(toDate, {
		time : false,
		"inputFormat" : "DD/MM/YYYY"

	});
	function openMap(url) {
		  window.open(url);
		}
	$(document).ready(function() {
		$('#mapPopup').popup({
			blur : false
		});
	});
	
	function closePopup() {
		$('#mapPopup').popup("hide");
	}
	function openPopup() {
		$('#mapPopup').popup("show");
	}
</script>
<style type="text/css">
div#map_container{
	width:100%;
	height:350px;
}
</style>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAztpUNd6QsPS73sgu37nBwVno9dMwAI-I"
    async defer></script>

<script type="text/javascript">
  function loadMap(lat,lon) {
		$('#mapPopup').popup('show');
    //var latlng = new google.maps.LatLng(12.9560848, 77.7165814);
    var latlng = new google.maps.LatLng(lat,lon);
    var myOptions = {
      zoom: 17,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
	
    var marker = new google.maps.Marker({
      position: latlng, 
      map: map, 
      title:"my hometown, Malim Nawar!"
    }); 
  
  }
</script>
</body>
</html>