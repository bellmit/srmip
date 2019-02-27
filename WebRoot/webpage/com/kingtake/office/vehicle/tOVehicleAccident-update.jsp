<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆事故信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOVehicleAccidentController.do?doUpdate" 
  	tiptype="1" tipSweep="true">
					<input id="id" name="id" type="hidden" value="${tOVehicleAccidentPage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				        <input id="vehicleId" name="vehicleId" type="hidden" style="width: 150px" class="inputxt" value="${tOVehicleAccidentPage.vehicleId}">
				        <input type="text" style="width: 150px" class="inputxt" value="${vehicleLicenseNo}" readonly="readonly">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆主键</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联&nbsp;&nbsp;&nbsp;<br>
							使用信息:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<input id="useId" name="useId" type="hidden" style="width: 150px" class="inputxt"
				     		value="${tOVehicleAccidentPage.useId}">
				     	<input id="useName" type="hidden" style="width: 150px" class="inputxt" >
				     	<input id="outTime" type="hidden" style="width: 150px" class="inputxt" >
				     	<input id="useInfo" type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${useInfo}">
				     	<a href="#" class="easyui-linkbutton l-btn l-btn-plain"
							plain="true" icon="icon-search"
							onclick="choose_40288a814ef6c556014ef77120550012()" id="">选择</a>
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain"
							plain="true" icon="icon-redo"
							onclick="clearAll_40288a814ef6c556014ef77120550012();" id="">清空</a>
						<script type="text/javascript">
							var windowapi = frameElement.api, W = windowapi.opener;
							function choose_40288a814ef6c556014ef77120550012() {
								var url = "tOVehicleUseController.do?tOVehicleUseChoose&vehicleId=${tOVehicleAccidentPage.vehicleId}";
								if (typeof (windowapi) == 'undefined') {
									$.dialog({
										content : 'url:'+url,
										zIndex : 3000,
										title : '车辆使用登记信息表',
										lock : true,
										width : '670px',
										height : '400px',
										left : '85%',
										top : '65%',
										opacity : 0.4,
										button : [
											{
												name : '确定',
												callback : clickcallback_40288a814ef6c556014ef77120550012,
												focus : true
											}, {
												name : '取消',
												callback : function() {
												}
											} ]
									});
								} else {
									$.dialog({
										content : 'url:'+url,
										zIndex : 3000,
										title : '车辆使用登记信息表',
										lock : true,
										parent : windowapi,
										width : '670px',
										height : '400px',
										left : '85%',
										top : '65%',
										opacity : 0.4,
										button : [
										{
											name : '确定',
											callback : clickcallback_40288a814ef6c556014ef77120550012,
											focus : true
										}, {
											name : '取消',
											callback : function() {
											}
										} ]
									});
								}
							}
							function clearAll_40288a814ef6c556014ef77120550012() {
								if ($('#useId').length >= 1) {
									$('#useId').val('');
								}
								if ($('#useInfo').length >= 1) {
									$('#useInfo').val('');
								}
								$('#null').val("");
							}
							function clickcallback_40288a814ef6c556014ef77120550012() {
								iframe = this.iframe.contentWindow;
								var id = iframe.gettOVehicleUseListSelections('id');
								if(id.length>0){
									if ($('#useId').length >= 1) {
										$('#useId').val(id);
									}
									
									var useId = iframe.gettOVehicleUseListSelections('useId');
									var useName = iframe.gettOVehicleUseListSelections('useName');
									var outTime = iframe.gettOVehicleUseListSelections('outTime');
									if ($('#useInfo').length >= 1) {
										$('#useInfo').val(useName + outTime.toString().substr(0,10) + "用车");
									}
									
									if ($('#personId').length >= 1) {
										$('#personId').val(useId);
									}
									if ($('#personName').length >= 1) {
										$('#personName').val(useName);
									}
									if ($('#occurTime').length >= 1) {
										$('#occurTime').val(outTime.toString().substr(0,19));
									}
								}
							}
						</script>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆使用登记表id</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							事&nbsp;故&nbsp;人&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="personId" name="personId" type="hidden" value="${tOVehicleAccidentPage.personId }">
				     	<input id="personName" name="personName" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-18" value="${tOVehicleAccidentPage.personName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="personId"
							textname="id,realName" inputTextname="personId,personName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">事故人姓名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发生时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="occurTime" name="occurTime" type="text" style="width: 150px" 
				      		datatype="date" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				      		value='<fmt:formatDate value="${tOVehicleAccidentPage.occurTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">事故发生时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发生地点:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="occurAddress" name="occurAddress" type="text" style="width: 265px" class="inputxt"
				     		datatype="*1-30" value="${tOVehicleAccidentPage.occurAddress}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">事故发生地点</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理方式:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="handleMode" name="handleMode" type="select" codeType="1" code="CLFS"
				     		extendParam="{style:'width:270px'}" defaultVal="${tOVehicleAccidentPage.handleMode}" ></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">处理方式</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							交&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;警&nbsp;&nbsp;&nbsp;<br>
							处理结果:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<textarea id="result" name="result" style="width:265px; height:60px;" datatype="*1-75"
				     		>${tOVehicleAccidentPage.result}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">交警处理结果</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<textarea id="remark" name="remark" style="width:265px;height:40px;" datatype="*1-100" ignore="ignore"
				     		>${tOVehicleAccidentPage.remark}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicleAccident.js"></script>		