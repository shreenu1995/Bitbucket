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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="../images/favicon.png">
<title>Stage Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<script src="../js/bootstrap.js"></script>
<!-- jQuery CDN -->
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/common.js"></script>
<script src="../js/messages.js"></script>
<script src="../js/stageManagement.js"></script>
</head>

<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
				<span class="breadcrumb-text">Masters</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">stage</span> <span
					class="glyphicon glyphicon-play icon-font-size"></span> <span
					class="breadcrumb-text">Create</span>
			</div>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons Start -->
			<div class="tab-header-container  marginL40">
				<a href="stage-search">Search</a>
			</div>
			<div class="tab-header-container active-background">
				<a href="stage-registration">Create</a>
			</div>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->
						<form:form modelAttribute="stageRegister"
							action="stage-registration" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->
								<div class="row">
									<div class="widgetDescriptionRow">

										 <c:choose>
										<c:when
												test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoId')"
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
												</div>
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class ="custom-legend"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePto();"
															onchange="doAjaxFetchRouteByPto(this.value);">
															<form:option value="">..:Select:..</form:option>
															<c:forEach items="${ptoListData}" var="ptoListData">
																<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
															</c:forEach>
														</form:select>
														<div class="discriptionErrorMsg">
															<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
														</div>
													</fieldset>
												</div>
											</c:when>
										<c:otherwise>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend"><span class="requiredFiled">*</span>
														Organization Name</legend>
													<form:select id="organizationId" name="organizationId"
														path="organizationId" onblur="validateOrganizationId();"
														Class="form-control">
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
													<legend class = "custom-legend"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePto();">
														<c:forEach items="${ptoListData}" var="ptoListData">
															<form:option value="${ptoListData.ptoMasterId}">${ptoListData.ptoName}</form:option>
														</c:forEach>

													</form:select>
													<div class="discriptionErrorMsg">
														<span id="ptoNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											</c:otherwise>
									</c:choose>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span>Route Name</legend>
											<select id="routeId" name="routeId" path="routeId"
												onclick="" onblur="validateRouteName();"
												class="form-control" onblur="">
												<option value="">..:Select:..</option>
												<c:forEach items="${routeNameList}" var="routeNameList">
													<option value="${routeNameList.routeId}">${routeNameList.routeName}</option>
												</c:forEach>

											</select>
											<div class="discriptionErrorMsg">
												<span id="routeNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend"><span class="requiredFiled">*</span><span
												class="imp-star">Stage Name</span></legend> <input id="stageName"
												name="stageName" path="stageName"
												onblur="validateStageName();" onclick=""
												class="form-control" type="text" value="" maxlength="50"></input>
											<div class="discriptionErrorMsg">
												<span id="stageNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<div class="col-xs-12 col-sm-12 content-wrapper">
											<div class="col-xs-12 col-sm-12 label-align">
												<label><span fontsize="2px" style="color: #8866ff;">
														Add Stops </span></label>
											</div>

											<article>
											<form id="dataFieldsMgmtRequest" method="post">
												<fieldset class="col-sm-12 totalFields" id="readRecord">

													<tr id="addRowId0">
														<td>
															<fieldset class="col-sm-3 data_filed">
																<legend class = "custom-legend" data-toggle="tooltip" data-placement="top"
																	title="" style="color:#656569">Stop Sequence
																Number</legend>
																<input class="form-control" id="stopSequenceNumber" value="0" 
																	name="dataFieldList[0].stopSequenceNumber" onblur="validateStopSequenceNumber();">
																<div class="discriptionErrorMsg">
																	<span id="stopSequenceNumberMsgDiv"
																		class="error-span-hide red-error"></span>
																</div>
															</fieldset>
														</td>

														<td>
															<fieldset class="col-sm-3 data_filed">
																<legend class = "custom-legend" data-toggle="tooltip" data-placement="top"
																	title="" style="color:#656569">Stop Name</legend>
																<input class="form-control" id="stopName" maxlength="50"
																	name="dataFieldList[0].stopName" path="stopName"
																	onblur="validateStopName();">
																<div class="discriptionErrorMsg">
																	<span id="stopNameMsgDiv"
																		class="error-span-hide red-error"></span>
																</div>
															</fieldset>
														</td>

														<td>
															<fieldset class=" col-sm-3 data_filed"
																style="color: #656569">
																<legend class = "custom-legend" data-toggle="tooltip" data-placement="top"
																	title="">Distance</legend>
																<input class="form-control" id="distance" value="0.0" maxlength="13"
																	name="dataFieldList[0].distance" path="distance"
																	onblur="validateDistance();">
																<div class="discriptionErrorMsg">
																	<span id="distanceMsgDiv"
																		class="error-span-hide red-error"></span>
																</div>
															</fieldset>
														</td>

														<td style="vertical-align: middle; width: 70px;"><fieldset
																class="col-sm-1 textCenter">
															</fieldset></td>
														<td style="vertical-align: middle"><fieldset
																class="col-sm-1 textCenter" id="plusID0">
																<span class="glyphicon glyphicon-plus "
																	style="margin: 15px 0 0 17px; color: #656565;"></span>
															</fieldset></td>
													</tr>


													<div
														class="col-sm-8 marginT10 add-bin-btn-div addButtonAliegn"
														id="readRecordBtn"></div>

												</fieldset>
											</form>

											</article>

										</div>

									</div>
								</div>
								<!-- Operator section end-->

								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div
											class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="user-search" value="Create"
												onclick="return validateStageSubmit();"
												class="form-control button pull-right" onclick=""> <a
												href="stage-registration"
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
		</div>

		</article>
		<!--Article Block Start-->
		<jsp:include page="footer.jsp" />
	</div>
	<!--Container block End -->
</div>
<!-- Main Body Wrapper End -->

<script type="text/javascript">
		// Add New BIN Functionality Start 
		var count = 0;
		var check = 0;
		var total = 0;
		var fields = 0;
		var newField;
		$(".glyphicon-plus")
				.click(
						function() {
							if (count == 0) {
								$(".totalFields").each(function() {
									count++;
									fields = count;
								});
								count = count - parseInt(total);
								total = fields;
							}
							if (count == total) {
								total = count - check;
								check = 0;
							} else {
								total = total - check;
								if (check > 0) {
									check = 0;
								}
								if (parseInt(total) < 0) {
									total = 0;
								}
							}

							oneormore = true;
							newField = '<tr id="addRowId'+count+'" class="col-sm-12"><fieldset class="col-sm-12"><fieldset class="col-sm-10" id="rowCount'+count+'">'

									+ '<td style="width: 27.999999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stop Sequence Number<span class="required-field"></span></lable>'
									+ '<input  class="form-control" value="0" id="stopSequenceNumber'
									+ count
									+ '"  name="dataFieldList['
									+ count
									+ '].stopSequenceNumber" onblur="validateStopSequenceNumber1(this.id,'
									+ count
									+ ','
									+ oneormore
									+ ');" />'
									+ '<div class="discriptionErrorMsg">'
									+ '<span id="MsgDivstopSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
									+ '</div>'
									+ '</fieldset></td>'
									+ '<input id="dataFieldPosition'+count+'" type="hidden" name="dataFieldList['+count+'].position"' 
                                    +'value="'+count+'"/>'

									+ '<td style="width: 28.7333%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stop Name<span class="required-field"></span></lable>'
									+ '<input  class="form-control" id="stopName'
									+ count
									+ '"  name="dataFieldList['
									+ count
									+ '].stopName" onblur="validateStopName1(this.id,'
									+ count
									+ ','
									+ oneormore
									+ ');" />'
									+ '<div class="discriptionErrorMsg">'
									+ '<span id="MsgDivstopName'+count+'"  class="error-span-hide red-error"></span>'
									+ '</div>'
									+ '</fieldset></td>'

									+ '<td style="width: 29.2%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance<span class="required-field"></span></lable><input  class="form-control totalFields" value="0.0" id="distance'
									+ count
									+ '" name="dataFieldList['
									+ count
									+ '].distance" onblur="validateDistance1(this.id,'
									+ count
									+ ','
									+ oneormore
									+ ');" />'
									+ '<div class="discriptionErrorMsg">'
									+ '<span id="MsgDivdistance'+count+'" class="error-span-hide red-error"></span>'
									+ '</div>'
									+ '</fieldset></td>'

									+ '<td style="vertical-align: middle;width: 70px;"><fieldset class="col-sm-1 textCenter"><span id="true" class="glyphicon glyphicon-trash delete-icon"></span></</fieldset></td>'
									+ '<td style="vertical-align: middle"><fieldset class="col-sm-1 textCenter" id="plusID'+count+'" ><span id="true" class="glyphicon glyphicon-plus" onclick="addRow(addRowId'
									+ count
									+ ',dataFieldPosition'
									+ count
									+ ')"></span></fieldset></td>'
									+ '</fieldset></fieldset></tr>';
							count++;
							total++;
							$(".add-bin-btn-div").before(newField);

							$('.delete-icon').click(function() {
								if (this.id == "true") {
									this.id = false;
									check++;
								}
								$(this).parent().parent().parent().remove();
							});
						});

		$('.delete-icon').click(function() {
			if (this.id == "true") {
				this.id = false;
				total--;
			}
			$(this).parent().parent().remove();
		});
	</script>

<script type="text/javascript">
		function addRow(ID, position) {
			var newField;
			var newCount = $(position).val();
			newCount++;
			if (count == 0) {
				$(".totalFields").each(function() {
					count++;
					fields = count;
				});
				count = count - parseInt(total);
				total = fields;
			}
			if (count == total) {
				total = count - check;
				check = 0;
			} else {
				total = total - check;
				if (check > 0) {
					check = 0;
				}
				if (parseInt(total) < 0) {
					total = 0;
				}
			}

			var temp = count;
			while (temp >= newCount) {
				if (get("dataFieldPosition" + temp) != null) {
					var oldValue = get("dataFieldPosition" + temp).value;
					var newValue = ++oldValue;
					var posValue = 'dataFieldPosition' + newValue;
					document.getElementById("dataFieldPosition" + temp).value = newValue;
					document.getElementById("dataFieldPosition" + temp).id = posValue;

					var clickfun = $("#plusID" + temp).children('#true').attr(
							"onclick");
					var funname = clickfun.substring(0, clickfun.indexOf(","));
					$("#plusID" + temp).children('#true').attr("onclick",
							funname + "," + posValue + ")");
					document.getElementById("plusID" + temp).id = "plusID"
							+ newValue;
				}
				temp--;
			}

			oneormore = true;
			newField = '<tr id="addRowId'+count+'" class="col-sm-12"><fieldset class="col-sm-12"><fieldset class="col-sm-10" id="rowCount'+count+'">'
					+ '<td style="width: 27.999999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stop Sequence Number<span class="required-field"></span></lable>'
					+ '<input  class="form-control" value="0" id="stopSequenceNumber'
					+ count
					+ '"  name="dataFieldList['
					+ count
					+ '].stopSequenceNumber" onblur="validateStopSequenceNumber1(this.id,'
					+ count
					+ ','
					+ oneormore
					+ ');" />'
					+ '<div class="discriptionErrorMsg">'
					+ '<span id="MsgDivstopSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
					+ '</div>'
					+ '</fieldset></td>'
					+ '<input id="dataFieldPosition'+newCount+'" type="hidden" name="dataFieldList['+count+'].position"' 
	                +'value="'+newCount+'"/>'

					+ '<td style="width: 28.7333%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Stop Name<span class="required-field"></span></lable><input  class="form-control totalFields" id="stopName'
					+ count
					+ '" name="dataFieldList['
					+ count
					+ '].stopName" onblur="validateStopName1(this.id,'
					+ count
					+ ','
					+ oneormore
					+ ');" />'
					+ '<div class="discriptionErrorMsg">'
					+ '<span id="MsgDivstopName'+count+'" class="error-span-hide red-error"></span>'
					+ '</div>'
					+ '</fieldset></td>'

					+ '<td style="width: 29.2%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance<span class="required-field"></span></lable><input  class="form-control totalFields" value="0.0" id="distance'
					+ count
					+ '" name="dataFieldList['
					+ count
					+ '].distance" onblur="validateDistance1(this.id,'
					+ count
					+ ','
					+ oneormore
					+ ');" />'
					+ '<div class="discriptionErrorMsg">'
					+ '<span id="MsgDivdistance'+count+'" class="error-span-hide red-error"></span>'
					+ '</div>'
					+ '</fieldset></td>'

					+ '<td style="vertical-align: middle;width: 70px;"><fieldset class="col-sm-1 textCenter"><span id="true" class="glyphicon glyphicon-trash delete-icon"></span></</fieldset></td>'
					+ '<td style="vertical-align: middle"><fieldset class="col-sm-1 textCenter" id="plusID'+newCount+'" ><span id="true" class="glyphicon glyphicon-plus" onclick="addRow(addRowId'
					+ count
					+ ',dataFieldPosition'
					+ newCount
					+ ')"></span></fieldset></td>'
					+ '</fieldset></fieldset></tr>';
			count++;
			total++;
			$(ID).after(newField);
			$('.delete-icon').click(function() {
				if (this.id == "true") {
					this.id = false;
					check++;
				}
				$(this).parent().parent().parent().remove();
			});
		}
	</script>

</body>

</html>
