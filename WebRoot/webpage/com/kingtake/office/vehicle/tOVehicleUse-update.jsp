<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆使用登记信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOVehicleUseController.do?doUpdate" 
  	tiptype="1" tipSweep="true" beforeSubmit="valiBeforeSubmit">
					<input id="id" name="id" type="hidden" value="${tOVehicleUsePage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="vehicleId" name="vehicleId" type="hidden" style="width: 150px" class="inputxt" 
				     		value="${tOVehicleUsePage.vehicleId}">
				     	<input type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${vehicleAndProjectPage.vehicleLicenseNo }">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆主键</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="driverId" name="driverId" type="hidden" style="width:45px;" class="inputxt" value="${tOVehicleUsePage.driverId}">
				     	<input id="driverName" name="driverName" type="text" style="width:45px;" class="inputxt" 
				     		datatype="*1-18" value="${tOVehicleUsePage.driverName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="driverId"
							textname="id,realName" inputTextname="driverId,driverName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">司机姓名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用&nbsp;车&nbsp;人&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="useId" name="useId" type="hidden" style="width: 150px" class="inputxt" value="${tOVehicleUsePage.useId}">
				     	<input id="useName" name="useName" type="text" style="width:45px;" class="inputxt" 
				     		datatype="*1-18" value="${tOVehicleUsePage.useName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="useId"
							textname="id,realName" inputTextname="useId,useName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">用车人姓名</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							申请时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="applyTime" name="applyTime" type="text" style="width: 150px" datatype="date"
				      		value='<fmt:formatDate value="${tOVehicleUsePage.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />' 
				      		class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">申请时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							关联项目:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="projectId" name="projectId" type="hidden" value="${tOVehicleUsePage.projectId}" >
				     	<input id="projectName" type="text" style="width: 350px" class="inputxt" readOnly="readonly" value="${vehicleAndProjectPage.projectName}">
				     	<t:chooseProject inputId="projectId"  inputName="projectName" icon="icon-search" 
                        	title="关联项目" isclear="true" mode="single"></t:chooseProject>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">关联项目id</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							到达地点:<font color="red">*</font>
						</label>
					</td>
					<td class="value"  colspan="3">
				     	<input id="acheivePlace" name="acheivePlace" type="text" style="width: 460px" class="inputxt" 
				     		datatype="*1-75" value="${tOVehicleUsePage.acheivePlace}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">到达地点</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出车事由:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
					    <textarea id="outReason" name="outReason"  style="width:460px;height:40px;" 
					    	datatype="*1-400">${tOVehicleUsePage.outReason}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">出车事由</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							行驶里程:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<input id="driverDistance" name="driverDistance" type="text" 
				     		style="width:120px; text-align:right;" class="easyui-numberbox"
							data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		ignore="ignore" value="${tOVehicleUsePage.driverDistance}">公里
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">行驶里程</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							油&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<input id="fuelUse" name="fuelUse" type="text" 
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
							data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		value="${tOVehicleUsePage.fuelUse}">升
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">油耗</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出场时间:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
					   	<input id="outTime" name="outTime" type="text" style="width: 150px" class="Wdate" 
					   		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'backTime\')}' })"
					   		datatype="date" ignore="ignore" 
					   		value='<fmt:formatDate value="${tOVehicleUsePage.outTime}" type="date" 
					   			pattern="yyyy-MM-dd HH:mm:ss" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">出场时间</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							回场时间:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
						<input id="backTime" name="backTime" type="text" style="width: 150px" class="Wdate" 
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'outTime\')}' })"
							datatype="date" ignore="ignore" 
							value='<fmt:formatDate value="${tOVehicleUsePage.backTime}" type="date" 
								pattern="yyyy-MM-dd HH:mm:ss" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">回场时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							批&nbsp;准&nbsp;人&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="approverId" name="approverId" type="hidden" style="width: 150px" class="inputxt" value="${tOVehicleUsePage.approverId}">
				     	<input id="approverName" name="approverName" type="text" style="width:45px;" class="inputxt" 
				     		datatype="*1-18" value="${tOVehicleUsePage.approverName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="approverId"
							textname="id,realName" inputTextname="approverId,approverName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">批准人姓名</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							批准时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="approverTime" name="approverTime" type="text" style="width: 150px" 
				      		datatype="date"	class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				      		value='<fmt:formatDate value="${tOVehicleUsePage.approverTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"  />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">批准时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="remark" name="remark" style="width:460px;height:30px;" 
				     		datatype="*1-100" ignore="ignore">${tOVehicleUsePage.remark}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicleUse.js"></script>		