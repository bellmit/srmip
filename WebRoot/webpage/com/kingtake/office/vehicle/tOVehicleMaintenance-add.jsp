<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆维护保养信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tOVehicleMaintenanceController.do?doAdd" 
  	tiptype="1" tipSweep="true" callback="@Override uploadFile">
					<input id="id" name="id" type="hidden" value="${tOVehicleMaintenancePage.id}">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="vehicleId" name="vehicleId" type="hidden" style="width: 150px" class="inputxt" 
				     		value="${tOVehicleMaintenancePage.vehicleId}" >
				     	<input id="licenseNo" type="text" readonly="readonly" style="width: 150px" class="inputxt" 
				     		value="${vehicleLicenseNo}"  >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆主键</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect  id="opType" name="opType" type="select" codeType="1" code="WXBYLX"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							维&nbsp;护&nbsp;人&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="maintainUserId" name="maintainUserId" type="hidden" 
							value="${tOVehicleMaintenancePage.maintainUserId}">
				     	<input id="maintainUserName" name="maintainUserName" type="text" style="width: 150px" 
				     		class="inputxt" datatype="*1-18" value="${tOVehicleMaintenancePage.maintainUserName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="maintainUserId"
							textname="id,realName" inputTextname="maintainUserId,maintainUserName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">维护保养人</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							维护时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="maintenanceTime" name="maintenanceTime" type="text" style="width: 150px" 
				      		datatype="date"	class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">维护保养时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="maintainUserPay" name="maintainUserPay" type="text" 
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
				     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">维护保养费用</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							维护事项:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<textarea id="maintenanceItems" name="maintenanceItems" style="width:270px; height:80px;"
				     		datatype="*1-1000"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">维护保养事项</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;
						</label>
					</td>
      				<td class="value" colspan="3">
      					<input type="hidden" value="${tOVehicleMaintenancePage.id}" id="bid" name="bid" />
						<div>
							<div class="form" id="filediv"></div>
							<t:upload name="fiels" id="file_upload"  buttonText="添加文件" 
								formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOVehicleMaintenance"></t:upload>
						</div>
      				</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicleMaintenance.js"></script>		