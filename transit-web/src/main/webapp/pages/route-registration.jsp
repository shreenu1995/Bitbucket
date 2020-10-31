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
<title>Route Create</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/config.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">

<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap-datetimepicker.min.js"
	type="text/javascript"></script>
<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
<script src="../js/routeRegistration.js" type="text/javascript"></script>
<script src="../js/common.js" type="text/javascript"></script>
<script src="../js/messages.js" type="text/javascript"></script>
</head>
<body>
	<!-- Main Body Wrapper Start -->
	<div id="wrapper">
		<!--Container block Start -->
		<div class="container-fluid cw-admin-search-widget">

			<!-- Navigation Block Starts -->
			<jsp:include page="header.jsp" />

			<!-- Navigation Block Ends -->

			<article>
				<div class="col-xs-12 content-wrapper">
					<!-- Breadcrumb start -->
					<div class="breadCrumb">
						<span class="breadcrumb-text">Masters</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Route</span> <span
							class="glyphicon glyphicon-play icon-font-size"></span> <span
							class="breadcrumb-text">Create</span>
					</div>
					<!-- Breadcrumb End -->

					<span class="success-msg help-block align-center" id="success">${success}</span>
					<span class="has-error help-block align-center" id="error">${error}</span>

					<!-- Tab Buttons Start -->
					<div class="tab-header-container marginL40 ">
						<a href="route-search">Search</a>
					</div>

					<div class="tab-header-container active-background ">
						<a href="route-registration">Create</a>
					</div>

					<!-- Tab Buttons End -->

					<div class="searchConteiner">
						<div class="row rowfluidalignment">
							<div class="col-sm-12">
								<!-- Page Form Start -->
								<form:form modelAttribute="routeRegistrationRequest"
									action="route-registration" method="post">
									<div class="col-sm-12 widgetDescriptionForm">
										<div class="row">

											<c:choose>
											<c:when
												test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
												<div class="widgetDescriptionRow">
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span>
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
														<legend class ="custom-legend-route"><span class="requiredFiled">*</span>PTO
															Name</legend>
														<form:select id="ptoId" name="ptoId" path="ptoId"
															class="form-control" onblur="validatePtoId();">
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
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>
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
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>PTO Name</legend>
													<form:select id="ptoId" name="ptoId" path="ptoId"
														class="form-control" onblur="validatePtoId();">
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
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>Route
														Name</legend> <input id="routeName" name="routeName"
														path="routeName" class="form-control"
														onblur="validateRouteName();" type="text" value=""
														maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="routeNameMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>

												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>From
														Route</legend> <input id="fromRoute" name="fromRoute"
														path="fromRoute" onblur="validateFromRoute();"
														class="form-control" type="text" value="" maxlength="50"
														autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="fromRouteMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>To Route</legend>
													<input id="toRoute" name="toRoute" path="toRoute"
														class="form-control" onblur="validateToRoute();"
														type="text" value="" maxlength="50" autocomplete="off">
													<div class="discriptionErrorMsg">
														<span id="toRouteMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>
											
												<fieldset class="col-sm-3 form-group">
													<legend class = "custom-legend-route"><span class="requiredFiled">*</span>Route Code</legend>
													<form:input id="routeCode" name="routeCode" path="routeCode"
														class="form-control" onblur="validateRouteCode();" 
														type="text" value="" maxlength="50" autocomplete="off"/>
													<div class="discriptionErrorMsg">
														<span id="routeCodeMsgDiv" class="red-color-error">&nbsp;</span>
													</div>
												</fieldset>

											<div class="col-xs-12 col-sm-12 content-wrapper">
												<div class="col-xs-12 col-sm-12 label-align">
													<label><span fontsize="2px" style="color: #8866ff;">
															Add Stages </span></label>
												</div>

												<article>
													<form id="dataFieldsMgmtRequest" method="post">
														<fieldset class="col-sm-12 totalFields" id="readRecord">

															<tr id="addRowId0">
																<td>
																	<fieldset class="col-sm-3 data_filed">
																		<legend class = "custom-legend-route" data-toggle="tooltip" data-placement="top"
																			title="" style="color:#656569">Stage Sequence
																		Number</legend>
																		<input class="form-control" id="stageSequenceNumber"
																			value="0" name="dataFieldList[0].stageSequenceNumber" onblur="validateStageSequenceNumber();">
																		<div class="discriptionErrorMsg">
																			<span id="stageSequenceNumberMsgDiv"
																				class="error-span-hide red-error"></span>
																		</div>
																	</fieldset>
																</td>

																<td>
																	<fieldset class="col-sm-3 data_filed">
																		<legend class = "custom-legend-route" data-toggle="tooltip" data-placement="top"
																			title="" style="color:#656569">Stage Name</legend>
																		<input class="form-control" id="stageName" maxlength="50"
																			name="dataFieldList[0].stageName" path="stageName"
																			onblur="validateStageName();">
																		<div class="discriptionErrorMsg">
																			<span id="stageNameMsgDiv"
																				class="error-span-hide red-error"></span>
																		</div>
																	</fieldset>
																</td>

																<td>
																	<fieldset class=" col-sm-3 data_filed"
																		style="color: #656569">
																		<legend class = "custom-legend-route" data-toggle="tooltip" data-placement="top"
																			title="">Distance</legend>
																		<input class="form-control" id="distance" maxlength="13"
																			name="dataFieldList[0].distance" path="distance" value="0.0"
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
										<!-- Form Button Start -->
										<div class="role-makerchecker-btn">
											<fieldset class="form-group">
												<div
													class="col-sm-6 widgetSearchButton no-search-criteria padding0">
													<input type="submit" id="user-search" value="Create"
														onclick="return validateRouteRegistrationSubmit();"
														class="form-control button pull-right" onclick="">
													<a href="route-registration"
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
				</div>
			</article>

			<jsp:include page="footer.jsp" />

		</div>
		<!--Container block End -->
	</div>

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

									+ '<td style="width: 27.999999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Sequence Number</lable>'
									+ '<input  class="form-control" value="0" id="stageSequenceNumber'
									+ count
									+ '"  name="dataFieldList['
									+ count
									+ '].stageSequenceNumber" onblur="validateStageSequenceNumber1(this.id,'
									+ count
									+ ','
									+ oneormore
									+ ');" />'
									+ '<div class="discriptionErrorMsg">'
									+ '<span id="MsgDivstageSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
									+ '</div>'
									+ '</fieldset></td>'
									+ '<input id="dataFieldPosition'+count+'" type="hidden" name="dataFieldList['+count+'].position"' 
                                    +'value="'+count+'"/>'

									+ '<td style="width: 28.7333%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Name</lable>'
									+ '<input  class="form-control" id="stageName'
									+ count
									+ '"  name="dataFieldList['
									+ count
									+ '].stageName" onblur="validateStageName1(this.id,'
									+ count
									+ ','
									+ oneormore
									+ ');" />'
									+ '<div class="discriptionErrorMsg">'
									+ '<span id="MsgDivstageName'+count+'"  class="error-span-hide red-error"></span>'
									+ '</div>'
									+ '</fieldset></td>'

									+ '<td style="width: 29.2%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance</lable><input  class="form-control totalFields" value="0.0" id="distance'
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
					+ '<td style="width: 27.999999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Sequence Number</lable>'
					+ '<input  class="form-control" value="0" id="stageSequenceNumber'
					+ count
					+ '"  name="dataFieldList['
					+ count
					+ '].stageSequenceNumber" onblur="validateStageSequenceNumber1(this.id,'
					+ count
					+ ','
					+ oneormore
					+ ');" />'
					+ '<div class="discriptionErrorMsg">'
					+ '<span id="MsgDivstageSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
					+ '</div>'
					+ '</fieldset></td>'
					+ '<input id="dataFieldPosition'+newCount+'" type="hidden" name="dataFieldList['+count+'].position"' 
	                +'value="'+newCount+'"/>'

					+ '<td style="width: 28.7333%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Stage Name</lable><input  class="form-control totalFields" id="stageName'
					+ count
					+ '" name="dataFieldList['
					+ count
					+ '].stageName" onblur="validateStageName1(this.id,'
					+ count
					+ ','
					+ oneormore
					+ ');" />'
					+ '<div class="discriptionErrorMsg">'
					+ '<span id="MsgDivstageName'+count+'" class="error-span-hide red-error"></span>'
					+ '</div>'
					+ '</fieldset></td>'

					+ '<td style="width: 29.2%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance</lable><input  class="form-control totalFields" value="0.0" id="distance'
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