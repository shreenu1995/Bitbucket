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
<title>Edit</title>
<script src="../js/jquery.js" type="text/javascript"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<link href="../css/main.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">
<!-- Bootstrap JS and all Bootstrap Plugins JS-->
<link href="../css/datatable-override.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
 
<script src="../js/common.js"></script>
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="../js/routeRegistration.js" type="text/javascript"></script>

</head>
<!-- Main Body Wrapper Start -->
<div id="wrapper">
	<!--Container block Start -->
	<div class="container-fluid cw-admin-search-widget">
		<!-- Navigation Bar Start --->
		<div id="header"></div>
		<!-- Navigation Bar End -->
		<!--Article Block Start-->
		<jsp:include page="header.jsp" />
		<article>
		<div class="col-xs-12 content-wrapper">
			<!-- Breadcrumb start -->
			<div class="breadCrumb">
						<span class="breadcrumb-text">Masters</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> 
						<span class="breadcrumb-text">Route</span> 
						<span class="glyphicon glyphicon-play icon-font-size"></span> <span class="breadcrumb-text">Edit</span>
					</div>
			<form action="route-search" name="cancelForm" method="post" value="">
				<input type="hidden" id="cancelTypeId" name="cancelTypeId" />
			</form>
			<span class="success-msg help-block align-center" id="success">${success}</span>
			<span class="has-error help-block align-center" id="error">${error}</span>

			<!-- Tab Buttons End -->
			<!-- Content Block Start -->
			<div class="searchConteiner">
				<div class="row rowfluidalignment">
					<div class="col-sm-12">
						<!-- Main Page Error Messages Start -->

						<form action="delete" name="stageAction" method="post">
							<input type="hidden" id="stageDelete" name="id" />
							<input type="hidden" id="routeId" name="routeId" />
						</form>

						<form:form modelAttribute="routeEditRequest"
							action="route-update" method="post">
							<div class="col-sm-12 widgetDescriptionForm">
								<!-- Overview section start -->
								 
								<div class="row Overview-section">

									<div class="widgetDescriptionRow">
										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-route">Route Id</legend>
											<form:input id="routeId" name="routeId" path="routeId"
												class="form-control" readonly="true" type="text"></form:input>
											<div class="routeIdMsgDiv">
												<span id="routeIdMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<c:choose>
												<c:when
													test="${userType  eq 'SUPER_ADMIN' || userType  eq 'ORG_ADMIN'}">
													<fieldset class="col-sm-3 form-group">
														<legend class  = "custom-legend-route"><span class="requiredFiled">*</span>
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
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span>PTO
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
												</c:when>
												<c:otherwise>
													<fieldset class="col-sm-3 form-group">
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span>
															Organization Name</legend>
														<form:select id="organizationId" name="organizationId"
															path="organizationId" onblur="validateOrganizationId();"
															onchange="fetchActivePtoListByOrganizationId(this.value, 'ptoName')"
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
														<legend class = "custom-legend-route"><span class="requiredFiled">*</span>PTO
															Name</legend>
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
											<legend class = "custom-legend-route"><span class="requiredFiled">*</span>Route Name</legend>
											<form:input id="routeName" path="routeName" name="routeName"
												class="form-control" onblur="validateRouteName();"
												type="text" maxlength="50" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="routeNameMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>

										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-route"><span class="requiredFiled">*</span>From Route</legend>
											<form:input id="fromRoute" path="fromRoute" name="fromRoute"
												class="form-control" onblur="validateFromRoute();"
												type="text" maxlength="50" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="fromRouteMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>


										<fieldset class="col-sm-3 form-group">
											<legend class = "custom-legend-route"><span class="requiredFiled">*</span>To Route</legend>
											<form:input id="toRoute" path="toRoute" name="toRoute"
												class="form-control" onblur="validateToRoute();" type="text"
												maxlength="50" autocomplete="off"></form:input>
											<div class="discriptionErrorMsg">
												<span id="toRouteMsgDiv" class="red-color-error">&nbsp;</span>
											</div>
										</fieldset>
										<div class="widgetDescriptionRow">
											<fieldset class="col-sm-3 form-group">
												<legend class = "custom-legend-route"><span class="requiredFiled">*</span>Route
													Code</legend>
												<form:input id="routeCode" name="routeCode" path="routeCode"
													class="form-control" type="text" value="" maxlength="50"
													autocomplete="off" onblur="validateRouteCode();" />
												<div class="discriptionErrorMsg">
													<span id="routeCodeMsgDiv" class="red-color-error">&nbsp;</span>
												</div>
											</fieldset>
										</div>

										<div class="col-xs-12 content-wrapper">
											<div class="col-xs-12 label-align">
												<label><span fontsize="2px" style="color: #8866ff;">
														Add Stages </span></label>
											</div>

											<article>
											<form id="dataFieldsMgmtRequest" method="post">
												<fieldset class="col-sm-12" id="readRecord">

													<tr id="addRowId0">
														<c:forEach items="${stageView}" var="stageView"
															varStatus="s">


															<td><input class="form-control" id="id"
																name="dataFieldList[${s.index}].id"
																value="${stageView.id}" type="hidden"></td>

															<td>
																<fieldset class="col-sm-3 data_filed clearBoth">
																	<legend class = "custom-legend-route">Stage Sequence Number</legend> <input
																		class="form-control" id="stageSequenceNumber"
																		onblur="validateStageSequenceNumber();"
																		name="dataFieldList[${s.index}].stageSequenceNumber"
																		value="${stageView.stageSequenceNumber}">
																	<div class="discriptionErrorMsg">
																		<span id="stageSequenceNumberMsgDiv"
																			class="error-span-hide red-error"></span>
																	</div>
																</fieldset>
															</td>

															<td>
																<fieldset class="col-sm-3 data_filed">
																	<legend class = "custom-legend-route">Stage Name</legend> <input
																		class="form-control" id="stageName"
																		onblur="validateStageName();" maxlength="50"
																		value="${stageView.stageName}"
																		name="dataFieldList[${s.index}].stageName">
																	<div class="discriptionErrorMsg">
																		<span id="dataFieldNameError0"
																			class="error-span-hide red-error"></span>
																	</div>
																</fieldset>
															</td>

															<td>
																<fieldset class="col-sm-3 data_filed">
																	<legend class = "custom-legend-route">Distance</legend> <input
																		class="form-control" id="distance"
																		onblur="validateDistance();" maxlength="13"
																		name="dataFieldList[${s.index}].distance"
																		value="${stageView.distance}">
																	<div class="discriptionErrorMsg">
																		<span id="distanceMsgDiv"
																			class="error-span-hide red-error"></span>
																	</div>
																</fieldset>
															</td>
															<td style="vertical-align: middle">
																<fieldset class="col-sm-1 textCenter" id="plusID0">
																	<span class="glyphicon glyphicon-plus "
																		style="margin: 32px 0 0 30px; color: #656565;"></span>
																	<a
																		href="javascript:deleteStage('${stageView.id}','${routesearchrequest.routeId}')"
																		title="Edit"> <span id="true"
																		class="glyphicon glyphicon-trash delete-icon"
																		style="margin: -11px 0 0 -10px; color: #656565;"></span></a>

																</fieldset>
															</td>

														</c:forEach>

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
								<!-- Form Button End -->
								<!-- Form Button Start -->
								<div class="role-makerchecker-btn">
									<fieldset class="form-group">
										<div class="col-sm-6 widgetSearchButton no-search-criteria padding0">
											<input type="submit" id="routeUpdate" value="Update"
												onclick="return validateRouteEditSubmit();"
												class="form-control button pull-right">
											<a href="javascript:cancelButton()" class="form-control button pull-right">
											<legend class = "custom-legend-button">	Cancel </legend> </a> 
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
	</div>
	</article>
	<!--Article Block Start-->
	<jsp:include page="footer.jsp" />
	<div id="footer"></div>
</div>
</div>
</div>
</div>
</div>
<!-- Popup container End -->
</div>
<!--Container block End -->
<!-- Main Body Wrapper End -->

<script type="text/javascript">
// Add New BIN Functionality Start 
var count = ${fn:length(stageView)};
var check = 0;
var total = 0;
var fields = 0;
var newField;
$(".glyphicon-plus").click(function(){
  if(count==0){
	$(".totalFields").each(function() {
		count++;
		fields=count;
	});
	count = count-parseInt(total);
	total=fields;
  }
 if(count==total){
	total = count - check;
	 check=0;
 }
 else{
	 total = total - check;
	 if(check>0){
	 check=0;
	 }
	 if(parseInt(total)<0){
		 total=0;
	 }
	 }

 oneormore=true;
 newField = '<tr id="addRowId'+count+'" class="col-sm-12"><fieldset class="col-sm-12"><fieldset class="col-sm-10" id="rowCount'+count+'">'
 
+'<td style="width: 28.199999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Sequence Number<span class="required-field"></span></lable>'
+'<input  class="form-control" id="stageSequenceNumber'+count+'"  name="dataFieldList['+count+'].stageSequenceNumber" onblur="validateStageSequenceNumber1(this.id,'+count+','+oneormore+');" />'
+'<div class="discriptionErrorMsg">'
+'<span id="MsgDivstageSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
+'</div>'
+'</fieldset></td>'
+'<input id="dataFieldPosition'+count+'" type="hidden" name="dataFieldList['+count+'].position"' 
+'value="'+count+'"/>'

+'<td style="width: 28.5833%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Name<span class="required-field"></span></lable>'
+'<input  class="form-control" id="stageName'+count+'"  name="dataFieldList['+count+'].stageName" onblur="validateStageName1(this.id,'+count+','+oneormore+');" />'
+'<div class="discriptionErrorMsg">'
+'<span id="MsgDivstageName'+count+'"  class="error-span-hide red-error"></span>'
+'</div>'
+'</fieldset></td>'


+'<td style="width: 28.699%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance<span class="required-field"></span></lable><input  class="form-control totalFields" id="distance'+count+'" name="dataFieldList['+count+'].distance" onblur="validateDistance1(this.id,'+count+','+oneormore+');" />'
+'<div class="discriptionErrorMsg">'
+'<span id="MsgDivdistance'+count+'" class="error-span-hide red-error"></span>'
+'</div>'
+'</fieldset></td>'

+'<td style="vertical-align: middle;width: 70px;"><fieldset class="col-sm-1 textCenter"><span id="true" class="glyphicon glyphicon-trash delete-icon"></span></</fieldset></td>'
+'<td style="vertical-align: middle"><fieldset class="col-sm-1 textCenter" id="plusID'+count+'" ><span id="true" class="glyphicon glyphicon-plus" onclick="addRow(addRowId'+count+',dataFieldPosition'+count+')"></span></fieldset></td>'
+'</fieldset></fieldset></tr>';
 count++;
 total++;
 $(".add-bin-btn-div").before(newField);

 $('.delete-icon').click(function(){
	 if(this.id == "true"){
		 this.id=false;
		 check++;
	 }
  $(this).parent().parent().parent().remove();
 });
});


$('.delete-icon').click(function(){
	if(this.id == "true"){
		 this.id=false;
		 total--;
	 }
 $(this).parent().parent().remove();
});

</script>

<script type="text/javascript">

	function addRow(ID,position){
	var newField;
	var newCount = $(position).val();
	newCount++;
	  if(count==0){
		$(".totalFields").each(function() {
			count++;
			fields=count;
		});
		count = count-parseInt(total);
		total=fields;
	  }
	 if(count==total){
		total = count - check;
		 check=0;
	 }
	 else{
		 total = total - check;
		 if(check>0){
		 check=0;
		 }
		 if(parseInt(total)<0){
			 total=0;
		 }
		 }
	 
	 var temp = count;
		while(temp>=newCount){
			if (get("dataFieldPosition"+temp) != null){
				var oldValue = get("dataFieldPosition"+temp).value;
				var newValue = ++oldValue;
				var posValue = 'dataFieldPosition'+newValue;
				document.getElementById("dataFieldPosition"+temp).value = newValue;
				document.getElementById("dataFieldPosition"+temp).id = posValue;
				
				var clickfun = $("#plusID"+temp).children('#true').attr("onclick");
				var funname = clickfun.substring(0,clickfun.indexOf(","));
				$("#plusID"+temp).children('#true').attr("onclick",funname+","+posValue+")");
				document.getElementById("plusID"+temp).id = "plusID"+newValue;
			}
			temp--;
		}
	 
	oneormore=true;
	newField = '<tr id="addRowId'+count+'" class="col-sm-12"><fieldset class="col-sm-12"><fieldset class="col-sm-10" id="rowCount'+count+'">'
	+'<td style="width: 28.199999999%;"><fieldset class="col-sm-12 data_filed" style="padding:0 31px 0 0px"><lable data-toggle="tooltip" data-placement="top" title="">Stage Sequence Number<span class="required-field"></span></lable>'
	+'<input  class="form-control" id="stageSequenceNumber'+count+'"  name="dataFieldList['+count+'].stageSequenceNumber" onblur="validateStageSequenceNumber1(this.id,'+count+','+oneormore+');" />'
	+'<div class="discriptionErrorMsg">'
	+'<span id="MsgDivstageSequenceNumber'+count+'"  class="error-span-hide red-error"></span>'
	+'</div>'
	+'</fieldset></td>'
	+'<input id="dataFieldPosition'+newCount+'" type="hidden" name="dataFieldList['+count+'].position"' 
	+'value="'+newCount+'"/>'
	
    +'<td style="width: 28.5833%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Stage Name<span class="required-field"></span></lable><input  class="form-control totalFields" id="stageName'+count+'" name="dataFieldList['+count+'].stageName" onblur="validateStageName1(this.id,'+count+','+oneormore+');" />'
	+'<div class="discriptionErrorMsg">'
	+'<span id="MsgDivstageName'+count+'" class="error-span-hide red-error"></span>'
	+'</div>'
	+'</fieldset></td>'

	+'<td style="width: 28.699%;"><fieldset class="desc col-sm-12" style="padding:0 31px 0 0px"><lable>Distance<span class="required-field"></span></lable><input  class="form-control totalFields" id="distance'+count+'" name="dataFieldList['+count+'].distance" onblur="validateDistance1(this.id,'+count+','+oneormore+');" />'
	+'<div class="discriptionErrorMsg">'
	+'<span id="MsgDivdistance'+count+'" class="error-span-hide red-error"></span>'
	+'</div>'
	+'</fieldset></td>'
	
	+'<td style="vertical-align: middle;width: 70px;"><fieldset class="col-sm-1 textCenter"><span id="true" class="glyphicon glyphicon-trash delete-icon"></span></</fieldset></td>'
	+'<td style="vertical-align: middle"><fieldset class="col-sm-1 textCenter" id="plusID'+newCount+'" ><span id="true" class="glyphicon glyphicon-plus" onclick="addRow(addRowId'+count+',dataFieldPosition'+newCount+')"></span></fieldset></td>'
	+'</fieldset></fieldset></tr>';
	 count++;
	 total++;
	 $(ID).after(newField);
	 $('.delete-icon').click(function(){
		 if(this.id == "true"){
			 this.id=false;
			 check++;
		 }
	  $(this).parent().parent().parent().remove();
	 });
	}
	
</script>

</body>
</html>