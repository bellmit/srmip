<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆使用费用信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
	function getUseInfo() {
		$('#useInfo').val($("#useName").val()+$("#outTime").val().substr(0,10)+"用车");
		$('#useInfo').blur();
	}
	
	$(function(){
		//费用类型不是加油时，加油量隐藏
		hideOrShowFuelCharge();
	});
</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOVehicleExpenseController.do?doUpdate" 
  	tiptype="1" tipSweep="true">
					<input id="id" name="id" type="hidden" value="${tOVehicleExpensePage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车&nbsp;牌&nbsp;号&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="vehicleId" name="vehicleId" type="hidden" style="width: 150px" class="inputxt" value="${tOVehicleExpensePage.vehicleId}" >
				     	<input type="text" style="width: 150px" class="inputxt" value="${vehicleLicenseNo}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">车牌号</label>
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
				     		value="${tOVehicleExpensePage.useId}">
				     	<input id="useInfo" type="text" style="width: 150px" class="inputxt" value="${useInfo}">
				     	<a href="#" class="easyui-linkbutton l-btn l-btn-plain"
							plain="true" icon="icon-search"
							onclick="choose_40288a814ef6c556014ef77120550012()" id="">选择</a>
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain"
							plain="true" icon="icon-redo"
							onclick="clearAll_40288a814ef6c556014ef77120550012();" id="">清空</a>
						<script type="text/javascript">
							var windowapi = frameElement.api, W = windowapi.opener;
							function choose_40288a814ef6c556014ef77120550012() {
								var url = "tOVehicleUseController.do?tOVehicleUseChoose&vehicleId=${tOVehicleExpensePage.vehicleId}";
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
									
									if ($('#payerId').length >= 1) {
										$('#payerId').val(useId);
									}
									if ($('#payerName').length >= 1) {
										$('#payerName').val(useName);
									}
									if ($('#payTime').length >= 1) {
										$('#payTime').val(outTime.toString().substr(0,19));
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
							缴费人员:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="payerId" name="payerId" type="hidden" value="${tOVehicleExpensePage.payerId}">
				     	<input id="payerName" name="payerName" type="text" style="width: 150px" class="inputxt" 
				     		datatype="*1-18" value="${tOVehicleExpensePage.payerName}">
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="payerId"
							textname="id,realName" inputTextname="payerId,payerName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">缴费人员姓名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							缴费时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="payTime" name="payTime" type="text" style="width: 150px" datatype="date"	class="Wdate" 
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value='<fmt:formatDate value="${tOVehicleExpensePage.payTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">缴费时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							费用类型:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
					    <t:codeTypeSelect id="expenseType" name="expenseType" type="select" codeType="1" code="FYLX"
					    	defaultVal="${tOVehicleExpensePage.expenseType}" 
					    	extendParam="{onchange:hideOrShowFuelCharge()}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">费用类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							加&nbsp;油&nbsp;量&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="fuelCharge" name="fuelCharge" type="text"
				     		style="width:140px; text-align:right;" class="easyui-numberbox"
				     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		value="${tOVehicleExpensePage.fuelCharge}">L
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">加油量</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							缴费类型:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="payType" name="payType" type="select" codeType="1" code="JYJFLX"
				     		defaultVal="${tOVehicleExpensePage.payType}"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">缴费类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="money" name="money" type="text"
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
				     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
				     		value="${tOVehicleExpensePage.money}">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">金额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<textarea id="remark" name="remark" style="width:265px;height:60px;"
				     		datatype="*1-100" ignore="ignore">${tOVehicleExpensePage.remark}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/vehicle/tOVehicleExpense.js"></script>		