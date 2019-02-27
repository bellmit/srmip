<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆基本信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  	$(function(){
  		//加载单位树下拉框
    	$("#selectDepart").combotree({
    		url:'departController.do?getDepartTree', 
    		width:155,
 			editable:false,
 			onSelect: function(rec){
 				$('#sectionId').val(rec.id);
	        	$('#sectionName').val(rec.text);
	        }
    	});
  		
  		//给单位树下拉框赋值
    	$("#selectDepart").combotree("setValue", "${tOVehiclePage.sectionName}");
    });  
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOVehicleController.do?doUpdate" 
  	tiptype="1" tipSweep="true">
					<input id="id" name="id" type="hidden" value="${tOVehiclePage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="licenseNo" name="licenseNo" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-10" value="${tOVehiclePage.licenseNo}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车牌号</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							所属处室:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="sectionName" name="sectionName" type="hidden" datatype="*1-30" value="${tOVehiclePage.sectionName}">
				     	<input id="sectionId" name="sectionId" type="hidden" datatype="*" value="${tOVehiclePage.sectionId}">
				     	<input id="selectDepart" type="text">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">所属处室名称</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							购置时间:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
						<input id="purchaseTime" name="purchaseTime" type="text" style="width: 150px" datatype="date" ignore="ignore"
				      		value='<fmt:formatDate value="${tOVehiclePage.purchaseTime}" type="date" pattern="yyyy-MM-dd"/>' class="Wdate" onClick="WdatePicker()">    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">购置时间</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							车辆状态:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="vehicleState" name="vehicleState" type="select" codeType="1" code="CLZT" 
				     		defaultVal="${tOVehiclePage.vehicleState}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆状态</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆类型:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="type" name="type" type="select" codeType="1" code="CLLX" 
				     		defaultVal="${tOVehiclePage.type}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车辆类型</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							军车标志:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="militaryFlag" name="militaryFlag" type="select" codeType="0" code="SFBZ"
				     		defaultVal="${tOVehiclePage.militaryFlag}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">军车标志</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							加&nbsp;&nbsp;油&nbsp;&nbsp;卡&nbsp;&nbsp;&nbsp;<br>
							卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="refuelNo" name="refuelNo" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-15" value="${tOVehiclePage.refuelNo}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">加油卡卡号</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							加油卡余额:
						</label>
					</td>
					<td class="value">
				     	<input id="refuelBalance" name="refuelBalance" type="text" 
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
				     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		value="${tOVehiclePage.refuelBalance}">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">加油卡余额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							购保标志:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="insuranceFlag" name="insuranceFlag" type="select" codeType="1" code="GBBZ"
				     		defaultVal="${tOVehiclePage.insuranceFlag}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">购保标志</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							购保时间:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
					    <input id="insuranceTime" name="insuranceTime" type="text" style="width: 150px" datatype="date" ignore="ignore"
			      			value='<fmt:formatDate value="${tOVehiclePage.insuranceTime}" type="date" pattern="yyyy-MM-dd" />'	class="Wdate" onClick="WdatePicker()">    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">购保时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							年检标志:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="annualSurveyFlag" name="annualSurveyFlag" type="select" codeType="1" code="NJBZ"
				     		defaultVal="${tOVehiclePage.annualSurveyFlag}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">年检标志</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							年检时间:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
						<input id="annualSurveyTime" name="annualSurveyTime" type="text" style="width: 150px" datatype="date" ignore="ignore"
				      		value='<fmt:formatDate value="${tOVehiclePage.annualSurveyTime}" type="date" pattern="yyyy-MM-dd" />'	class="Wdate" onClick="WdatePicker()">    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">年检时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机姓名:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="driverId" name="driverId" type="hidden" value="${tOVehiclePage.driverId}">
				     	<input id="driverName" name="driverName" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-18" ignore="ignore" value="${tOVehiclePage.driverName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="driverId"
							textname="id,realName" inputTextname="driverId,driverName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">司机姓名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="remark" name="remark" style="width:475px;height:30px;" 
				     		datatype="*1-100" ignore="ignore" >${tOVehiclePage.remark}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicle.js"></script>		