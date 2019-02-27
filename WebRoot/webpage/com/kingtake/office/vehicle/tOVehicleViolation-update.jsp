<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆违章信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  	//使用记录id变化时，使用信息变化
	function getUseInfo() {
		$('#useInfo').val($("#useName").val()+$("#outTime").val().substr(0,10)+"用车");
		$('#useInfo').blur();
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOVehicleViolationController.do?doUpdate" 
  	tiptype="1" tipSweep="true">
					<input id="id" name="id" type="hidden" value="${tOVehicleViolationPage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="vehicleId" name="vehicleId" type="hidden" style="width: 150px" class="inputxt" value="${tOVehicleViolationPage.vehicleId}">
				     	<input type="text" style="width: 150px" class="inputxt" value="${vehicleLicenseNo}" readonly="readonly">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆信息表id</label>
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
				     		value="${tOVehicleViolationPage.useId}">
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
								var url = "tOVehicleUseController.do?tOVehicleUseChoose&vehicleId=${tOVehicleViolationPage.vehicleId}";
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
									
									if ($('#violationId').length >= 1) {
										$('#violationId').val(useId);
									}
									if ($('#violationName').length >= 1) {
										$('#violationName').val(useName);
									}
									if ($('#violationTime').length >= 1) {
										$('#violationTime').val(outTime.toString().substr(0,19));
									}
								}
							}
						</script>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆使用登记</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违&nbsp;章&nbsp;人&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="violationName" name="violationName" type="text" style="width: 150px" class="inputxt"
				     		datatype="*1-18" value="${tOVehicleViolationPage.violationName}">
				     	<input id="violationId" name="violationId" type="hidden" value="${tOVehicleViolationPage.violationId}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="violationId"
							textname="id,realName" inputTextname="violationId,violationName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章人姓名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违章时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="violationTime" name="violationTime" type="text" style="width: 150px" 
				      		datatype="date" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
				      		value='<fmt:formatDate value="${tOVehicleViolationPage.violationTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违章地点:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
			     		<input id="violationAddr" name="violationAddr" type="text" style="width: 150px" class="inputxt" 
			     			datatype="*1-75" value="${tOVehicleViolationPage.violationAddr}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章地点</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违章代码:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="violationCode" name="violationCode" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-16" value="${tOVehicleViolationPage.violationCode}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章代码</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违章分数:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="violationScore" name="violationScore" type="text" style="width: 150px" class="inputxt" 
				     		datatype="n1-5" value="${tOVehicleViolationPage.violationScore}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章分数</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处罚金额:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="fines" name="fines" type="text" 
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
							data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		value="${tOVehicleViolationPage.fines}">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">处罚金额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							违章描述:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
					    <textarea id="violationDesc" name="violationDesc" style="width:260px; height:50px;"
					    	datatype="*1-250" ignore="ignore" >${tOVehicleViolationPage.violationDesc}</textarea>						
					    <span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">违章描述</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<textarea id="remark" name="remark" style="width: 260px;height:30px;"
				     		datatype="*1-100" ignore="ignore" >${tOVehicleViolationPage.remark}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicleViolation.js"></script>		