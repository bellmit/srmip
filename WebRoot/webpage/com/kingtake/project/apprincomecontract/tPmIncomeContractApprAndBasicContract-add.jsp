<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>进账合同审批</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmIncomeContractApprController.do?doSave" postonce="false"
  	tiptype="1" callback="@Override contractSaveCallback">
<link rel="stylesheet" href="webpage/com/kingtake/project/panelUtil/panel.css" type="text/css"></link>
<script type="text/javascript" src="webpage/com/kingtake/project/panelUtil/panel.js"></script>
<script src = "webpage/com/kingtake/project/apprincomecontract/tPmIncomeContractAppr.js"></script>
<script type="text/javascript">
function contractSaveCallback(data) {
        $("#id").val(data.attributes.contractId);//保存合同审批表id
        $("#rid").val(data.attributes.basicId);//保存合同基本信息id
        $("#inOutContractid").val(data.attributes.contractId);//合同基本信息中合同审批表id

        var win;
        var dialog = W.$.dialog.list['processDialog'];
        if (dialog == undefined) {
            win = frameElement.api.opener;
        } else {
            win = dialog.content;
        }
        win.reloadTable();//刷新进账合同列表

        var type = $("#callBackType").val();
        if (type == "node") {
            $("#tPmContractNodeListtb a[icon='icon-add']").click();
        } else if (type == "close") {
            frameElement.api.close();
        }

        var fileKeys = $("#fileKeys").val("");
    }
    
function getSerialNum() {
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        dataType:'json',
        data : {"inOutFlag":"i","greatSpecialFlag":"${project.greatSpecialFlag}"},
        url : 'tPmIncomeContractApprController.do?getContractCode',// 请求的action路径
        success : function(data) {
            $('#contractCode').val(data.code);
        }
    });
}

$(function(){
    var dutyDepart = $("#dutyDepart").val(); 
    var devDepart = $("#devDepart").val(); 
    var prefix = "海军工程大学";
    var applyUnit;
    if(dutyDepart==prefix){
        applyUnit = dutyDepart+devDepart;
    }else{
        applyUnit = prefix+dutyDepart+devDepart;
    }
    $("#applyUnit").val(applyUnit);
});


</script>
<style type="text/css">
.colspanWidth{
	width:355px;
}
</style>
<div style="overflow:hidden;width:100%;height:97%">
	<!-- 合同审批表 -->
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title" style="font-size:15px;">
			科技合同签订审批表<a href="#" onclick="toastrMsg('incomeContractAppr')" style="color: red">(查看说明)</a>
		</div>
		<div class="tool" state="live">
			<a class="collapse expand" href="javascript:void(0)"></a>
			<span>隐藏</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:80%;">
		<div style="margin-left:30px;">
			<input id="id" name="id" type="hidden" value="${tPmIncomeContractApprPage.id}">
			<input id="projectId" name="project.id" type="hidden" value="${tPmIncomeContractApprPage.project.id}">
      <input id="greatSpecialFlag" name="greatSpecialFlag" type="hidden" value="${project.greatSpecialFlag}">
			<table style="width:700px;margin:auto;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="2">
					</td>
					<td style="height:40px;">
						<span style="font-size:12px;">合同编号:<font color="red">*</font></span>
					    <input id="contractCode" name="contractCode" type="text" readonly="true"
					    	datatype="*1-15" errormsg="请在合同审批表-合同编号中填写1-15位任意字符"
					    	ajaxurl="tPmIncomeContractApprController.do?validformContractCode&id=${tPmIncomeContractApprPage.id}"
					   		style="font-size:12px; border-style: none none solid none; 
					   			border-color: #CACACA; padding: 2px;width:170px;">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中合同编号</label>
                      <a class="easyui-linkbutton" data-options="plain:true" href="javascript:getSerialNum();" title="点击获取合同编号" icon="icon-edit"></a>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							申请单位:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="2">
                        <input id="dutyDepart" type="hidden" value="${project.dutyDepart.departname}">
                        <input id="devDepart" type="hidden" value="${project.devDepart.departname}">
				     	<input id="applyUnit" name="applyUnit" type="text" class="colspanWidth"  
				     		datatype="*1-30" errormsg="请在合同审批表-申请单位中填写1-30位任意字符"
				     		value="" >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中申请单位</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目名称（科目代码）:<font color="red">*</font>
						</lable>
					</td>
					<td class="value" colspan="2">
					    <input id="projectName" name="projectName" type="text" class="colspanWidth"  
							readonly="readonly" value='${tPmIncomeContractApprPage.projectName}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中项目名称</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同名称:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="2">
				     	<input id="contractName" name="contractName" type="text" class="colspanWidth"
				     		datatype="*1-50" errormsg="请在合同审批表-合同名称中填写1-50位任意字符"
				     		value='${tPmIncomeContractApprPage.contractName}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中合同名称</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							对方单位（乙方）:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="2">
				     	<input id="approvalUnit" name="approvalUnit" type="text" class="colspanWidth"
				     		datatype="*1-30" errormsg="请在合同审批表-对方单位中填写1-30位任意字符">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中对方单位</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同第三方:
						</label>
					</td>
					<td class="value" colspan="2">
				     	<input id="theContractThird" name="theContractThird" type="text" class="colspanWidth"
				     		datatype="*1-30" errormsg="请在合同审批表-合同第三方中填写1-30位任意字符" ignore="ignore">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中合同第三方</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							起止时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="startTime" name="startTime" type="text" style="width:180px" 
				      		class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" 
				      		datatype="date" errormsg="请在合同审批表-开始时间中填写日期格式"
				      		value='<fmt:formatDate value="${tPmIncomeContractApprPage.startTime}" 
				      			pattern="yyyy-MM-dd" type="date" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中开始时间</label>
					</td>
					<td class="value">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至:<font color="red">*</font>
						<input id="endTime" name="endTime" type="text" style="width:180px" 
				      		class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})" 
				      		datatype="date" errormsg="请在合同审批表-截止时间中填写日期格式"
				      		value='<fmt:formatDate value="${tPmIncomeContractApprPage.endTime}" 
				      			pattern="yyyy-MM-dd" type="date" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中截止时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							总&nbsp;经&nbsp;费:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="totalFunds" name="totalFunds" type="text" datatype="*"
				     		value="${tPmIncomeContractApprPage.totalFunds}"
				     		style="width:165px;text-align:right;" class="easyui-numberbox"
				     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中总经费</label>
					</td>
				</tr>
				<!-- 合同其他信息 -->
				<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							合同类别:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="2">
						<table id="contractTypeRadio" style="width:90%">
						</table>
						<script type="text/javascript">
							$(function(){
								$.ajax({
									type : 'POST',
									url : "tBCodeTypeController.do?typeCombo",// 请求的action路径
									data : {"codeType":"1", "code":"HTLB"},
									success : function(data) {
										var radioList = $.parseJSON(data);
										var html = "";
										for(var i=0; i<radioList.length; i++){
											if(i%4 == 0){
												html += '<tr>'
											}
											
											var check = false;
											if(i == 0){
												check = true;
											}
											
											html += '<td style="background-color: white;width:100px;height:25px;">'+
												'<input id="contractType" name="contractType" type="radio" value="'+radioList[i].CODE + '"';
											if(check){
												html += ' checked="checked"';
											}
											
											if(radioList[i].NAME == "其他"){
												html += ' onclick="changeContent(\'0\')"/>'+radioList[i].NAME +
												'&nbsp;<input style="width:70px;display:none" type="text" id="contractTypeContent" '+
												'name="contractTypeContent" datatype="*1-25" errormsg="请在合同审批表-其他合同类别中填写1-25位任意字符" ignore="ignore"/>'
											}else{
												html += ' onclick="changeContent(\'1\')"/>'+radioList[i].NAME
											}
											
											html += '</td>';
											
											if(i%4 == 3){
												html += '</tr>'
											}
										}
										$("#contractTypeRadio").prepend(html);
									}
								});
							});
						</script>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中合同类别</label>
					</td>
				</tr> -->
				<tr>
					<td align="right">
						<label class="Validform_label">
							协作单位及&nbsp;<br>经&nbsp;费&nbsp;情&nbsp;况:
						</label>
					</td>
					<td class="value" colspan="2">
				     	<textarea id="coorUnitFundsInfo" name="coorUnitFundsInfo" style="height:50px;" 
				     		datatype="*1-1000" ignore="ignore" errormsg="请在合同审批表-协作单位及经费情况中填写1-1000位任意字符"
				     		class="colspanWidth"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中协作单位及经费情况</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同依据:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="2">
				     	<textarea id="contractBasis" name="contractBasis" style="height:50px;" 
				     		datatype="*1-500" errormsg="请在合同审批表-合同依据中填写1-500位任意字符"
				     		class="colspanWidth"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中合同依据</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							技术规范或&nbsp;<br>技术规格书:
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="technologyFlag" name="technologyFlag" type="select"
				     		codeType="1" code="HTPSZT"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中技术规范或技术规格书</label>
					</td>
	
					<td class="value">
						<label class="Validform_label">
							质量保证大纲:
						</label>
						<t:codeTypeSelect id="qualityCetifyFlag" name="qualityCetifyFlag" type="select" 
							codeType="1" code="HTPSZT"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中质量保证大纲</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							标准化大纲:
						</label>
					</td>
					<td class="value" colspan="2">
				     	<t:codeTypeSelect id="standardOutline" name="standardOutline" type="select" 
				     		codeType="1" code="HTPSZT"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中标准化大纲</label>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							研制内容:
						</label><font color="red">*</font>
					</td>
					<td class="value" colspan="2">
				     	<input id="developContent" name="developContent" type="text" class="colspanWidth"
				     		datatype="*1-100" errormsg="请填写研制内容中填写1-100位任意字符"
				     		value='${tPmIncomeContractApprPage.developContent}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中研制内容</label>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							变更说明:
						</label>
					</td>
					<td class="value" colspan="2">
				     	<textarea id="changeExplain" name="changeExplain" style="height:50px;" 
				     		datatype="*0-500" ignore="ignore" errormsg="请在合同审批表-变更说明中填写0-500位任意字符"
				     		class="colspanWidth">${tPmIncomeContractApprPage.changeExplain}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中研制内容</label>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同正本:
						</label>
					</td>
		    		<td colspan="2" class="value">
				        <table id="fileShow" style="max-width:515px;"></table>
						<div>
							<div class="form" id="filediv"></div>
							<input type="hidden" value="${tPmIncomeContractApprPage.attachmentCode}" id="bid" name="attachmentCode" />
							<t:upload queueID="filediv" name="fiels" id="file_upload"  buttonText="添加文件" 
	      	                   formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" fileSizeLimit="2GB"
								uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmIncomeContractAppr"
							></t:upload>
						</div>
	  				</td>
	  			</tr>
	  			<tr>
					<td colspan="2">
					</td>
					<td style="height:40px;">
						<span style="font-size:12px; margin-left:100px;">签订时间:</span>
					    <input id="contractSigningTime" name="contractSigningTime" type="text" 
				      		class="Wdate" onClick="WdatePicker()" 
				      		datatype="date" ignore="ignore" errormsg="请在合同审批表-签订时间中填写正确日期格式"
				      		style="font-size:12px; border-style: none none solid none; 
					   			border-color: #CACACA; padding: 2px;width:100px; text-align:center;"
					   		value='<fmt:formatDate value="${tPmIncomeContractApprPage.contractSigningTime}" 
				      			pattern="yyyy-MM-dd" type="date" />'> 
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同审批表中签订时间</label>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title" style="font-size:15px;">
			合同基本信息
		</div>
		<div class="tool" state="dead">
			<a class="collapse" href="javascript:void(0)"></a>
			<span>展开</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:80%;display:none">
		<input id="rid" name="rid" type="hidden" value="${tPmContractBasicPage.rid}">
		<input id="inOutContractid" name="inOutContractid" type="hidden">
		<table style="width:700px;margin:auto;text-align:center" cellpadding="0" cellspacing="1" class="formtable" >
			<thead>
				<tr style="font-weight:bold;">
					<td></td>
					<td class="value">甲方</td>
					<td class="value">乙方</td>
				</tr>
			</thead>
			<tr>
				<td>
					<label class="Validform_label">
						法人单位名称:
					</label>
				</td>
				<td class="value">
			     	<input id="unitNameA" name="unitNameA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-法人单位名称(甲方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法人单位名称(甲方)</label>
				</td>
				<td class="value">
			     	<input id="unitNameB" name="unitNameB" type="text" style="width: 150px" 
			     		class="inputxt"  datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-法人单位名称(乙方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法人单位名称(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						法定代表人职务:
					</label>
				</td>
				<td class="value">
			     	<input id="unitPositionA" name="unitPositionA" type="text" style="width: 150px" 
			     		class="inputxt"  datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-法定代表人职务(甲方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法定代表人职务(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						法定代表人职务(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="unitPositionB" name="unitPositionB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-法定代表人职务(乙方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法定代表人职务(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						法定代表人姓名:
					</label>
				</td>
				<td class="value">
			     	<input id="nameA" name="nameA" type="text" style="width: 150px" class="inputxt"  
		            	datatype="*1-18" ignore="ignore" errormsg="请在合同基本信息-法定代表人姓名(甲方)中填写1-18位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法定代表人姓名(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						法定代表人姓名(乙方):<font color="red">*</font>
					</label>
				</td> -->
				<td class="value">
			     	<input id="nameB" name="nameB" type="text" style="width: 150px" class="inputxt"  
						datatype="*1-18" ignore="ignore" errormsg="请在合同基本信息-法定代表人姓名(乙方)中填写1-18位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中法定代表人姓名(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						代理单位名称:
					</label>
				</td>
				<td class="value">
			     	<input id="agencyUintNameA" name="agencyUintNameA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-代理单位名称(甲方)中填写1-30位任意字符"> 
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代理单位名称(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						代表单位名称(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="agencyUnitNameB" name="agencyUnitNameB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-代表单位名称(乙方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代表单位名称(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						代理人职务:
					</label>
				</td>
				<td class="value">
			     	<input id="agencyUnitPositionA" name="agencyUnitPositionA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-代理人职务(甲方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代理人职务(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						代理人职务(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="agencyUnitPositionB" name="agencyUnitPositionB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-代理人职务(乙方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代理人职务(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						代理人姓名:
					</label>
				</td>
				<td class="value">
			     	<input id="agencyNameA" name="agencyNameA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-18" ignore="ignore" errormsg="请在合同基本信息-代理人姓名(甲方)中填写1-18位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代理人姓名(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						代理人姓名(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="agencyNameB" name="agencyNameB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-18" ignore="ignore" errormsg="请在合同基本信息-代理人姓名(乙方)中填写1-18位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中代理人姓名(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						单位地址:
					</label>
				</td>
				<td class="value" >
			     	<input id="addressA" name="addressA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-100" ignore="ignore" errormsg="请在合同基本信息-单位地址(甲方)中填写1-100位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中单位地址(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						单位地址(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="addressB" name="addressB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-100" ignore="ignore" errormsg="请在合同基本信息-单位地址(乙方)中填写1-100位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中单位地址(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						邮政编码:
					</label>
				</td>
				<td class="value">
			     	<input id="postalcodeA" name="postalcodeA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="p" ignore="ignore" errormsg="请在合同基本信息-邮政编码(甲方)中填写正确的邮政编码">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中邮政编码(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						邮政编码(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="postalcodeB" name="postalcodeB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="p" ignore="ignore" errormsg="请在合同基本信息-邮政编码(乙方)中填写正确的邮政编码">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中邮政编码(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						联系电话:
					</label>
				</td>
				<td class="value">
			     	<input id="telA" name="telA" type="text" style="width: 150px" class="inputxt"
			     		datatype="m | /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/" 
			     		ignore="ignore" errormsg="清在合同基本信息-联系电话(甲方)中填写正确的电话号码格式">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中联系电话(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						联系电话(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="telB" name="telB" type="text" style="width: 150px" class="inputxt"
			     		datatype="m | /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/" 
			     		ignore="ignore" errormsg="清在合同基本信息-联系电话(乙方)中填写正确的电话号码格式">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中联系电话(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						账户名称:
					</label>
				</td>
				<td class="value">
			     	<input id="accountNameA" name="accountNameA" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-50" ignore="ignore" errormsg="请在合同基本信息-账户名称(甲方)中填写1-50位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中账户名称(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						帐户名称(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="accountNameB" name="accountNameB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-50" ignore="ignore" errormsg="请在合同基本信息-帐户名称(乙方)中填写1-50位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中帐户名称(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						开户银行:
					</label>
				</td>
				<td class="value">
			     	<input id="bankA" name="bankA" type="text" style="width: 150px" class="inputxt"
			     		 datatype="*1-50" ignore="ignore" errormsg="请在合同基本信息-开户银行(甲方)中填写1-50位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中开户银行(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						开户银行(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="bankB" name="bankB" type="text" style="width: 150px" class="inputxt" 
			     		 datatype="*1-50" ignore="ignore" errormsg="请在合同基本信息-开户银行(乙方)中填写1-50位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中开户银行(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:
					</label>
				</td>
				<td class="value">
			     	<input id="accountIdA" name="accountIdA" type="text" style="width: 150px" class="inputxt" 
			     		 datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-帐号(甲方)中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中帐号(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						帐号(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="accountIdB" name="accountIdB" type="text" style="width: 150px" class="inputxt"
			     		 datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-帐号(乙方)中填写1-30位任意字符">	
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中帐号(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						签字地点:
					</label>
				</td>
				<td class="value">
			     	<input id="signAddressA" name="signAddressA" type="text" style="width: 150px" class="inputxt"
			     		 datatype="*1-100" ignore="ignore" errormsg="请在合同基本信息-签字地点(甲方)中填写1-100位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中签字地点(甲方)</label>
				</td>
				<!-- <td align="left">
					<label class="Validform_label">
						签字地点(乙方):
					</label>
				</td> -->
				<td class="value">
			     	<input id="signAddressB" name="signAddressB" type="text" style="width: 150px" 
			     		class="inputxt" datatype="*1-100" ignore="ignore" errormsg="请在合同基本信息-签字地点(乙方)中填写1-100位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中签字地点(乙方)</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						第&nbsp;三&nbsp;方:
					</label>
				</td>
				<td class="value" colspan="2">
			     	<input id="theThird" name="theThird" type="text" style="width:435px" class="inputxt"
		            	datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-第三方中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中第三方</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						甲方合同<br>履行监督单位:
					</label>
				</td>
				<td class="value" colspan="2">
			     	<input id="monitorUnit" name="monitorUnit" type="text" style="width:435px" 
			     		class="inputxt" datatype="*1-30" ignore="ignore" errormsg="请在合同基本信息-甲方合同履行监督单位中填写1-30位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中甲方合同履行监督单位</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="Validform_label">
						总军事代表:
					</label>
				</td>
				<td class="value" colspan="2">
			     	<input id="militaryDeputy" name="militaryDeputy" type="text" style="width:435px" 
			     		class="inputxt" datatype="*1-15" ignore="ignore" errormsg="请在合同基本信息-总军事代表中填写1-15位任意字符">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">合同基本信息中总军事代表</label>
				</td>
			</tr>
		</table>
	</div>
	<!-- 与产品有关的要求评审项目表 -->
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title" style="font-size:15px;">
			与产品有关的要求评审项目表
		</div>
		<div class="tool" state="dead">
			<a class="collapse" href="javascript:void(0)"></a>
			<span>展开</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:80%;display:none">
		<table style="text-align:center; margin:auto;" cellspacing="1" class="mytable">
			<tr>
			<td colspan="2"><label class="Validform_label">评审项目</label></td>
			<td><label class="Validform_label">评审内容</label></td>
			<td><label class="Validform_label">负责评审的单位或人员</label></td>
			</tr>
			<tr>
				<td rowspan="6" width="60px">
					<label class="Validform_label">
                              合</br>
                              同</br>
                              要</br>
                              求</label>
				</td>
        <td><label class="Validform_label">技术要求</label></td>
				<td class="value">
			     	<textarea id="technologyRequire" name="technologyRequire" style="height:50px;" class="colspanWidth" 
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-评审内容技术要求中中填写1-1000位任意字符"></textarea>
			     	<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中技术要求</label>
				</td>
				<td>
				<textarea id="personTechRequire" name="personTechRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-负责评审的单位或人员技术要求中填写1-1000位任意字符"></textarea>
			     	<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员技术要求</label>
				</td>
			</tr>
			<tr>
				<td><label class="Validform_label">进度要求</label></td>
				<td class="value">
			     	<textarea id="scheduleRequire" name="scheduleRequire" type="text" style="height:50px;" class="colspanWidth"
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-进度要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中进度要求</label>
				</td>
				<td class="value">
			     	<textarea id="personScheduleRequire" name="personScheduleRequire" type="text" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-进度要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员进度要求</label>
				</td>
			</tr>
			<tr>
				<td><label class="Validform_label">质量保证要求</label></td>
				<td class="value">
			     	<textarea id="qualityRequire" name="qualityRequire" style="height:50px;" class="colspanWidth" 
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-质量保证要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中质量保证要求</label>
				</td>
				<td class="value">
			     	<textarea id="personQualityRequire" name="personQualityRequire" style="height:50px;width: 150px;"  
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-负责评审的单位和人员质量保证要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员质量保证要求</label>
				</td>
			</tr>
			<tr>
				<td><label class="Validform_label">经&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费</label></td>
				<td class="value">
			     	<input id="fundsRequire" name="fundsRequire" type="text" 
			     		style="width:350px;text-align:right;border-color: #CACACA" class="easyui-numberbox"
			     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中要求经费</label>
				</td>
				<td class="value">
			     	<input id="personFundsRequire" name="personFundsRequire" type="text" datatype="*0-1000"
			     		style="width:150px;" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员要求经费</label>
				</td>
			</tr>
			<tr>
				<td><label class="Validform_label">特殊要求</label></td>
				<td class="value">
			     	<textarea id="specialRequire" name="specialRequire" style="height:50px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-特殊要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中特殊要求</label>
				</td>
				<td class="value">
			     	<textarea id="personSpecialRequire" name="personSpecialRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-负责评审的单位和人员-特殊要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员特殊要求</label>
				</td>
			</tr>
			<tr>
				<td><label class="Validform_label">其他要求</label></td>
				<td class="value">
			     	<textarea id="otherRequire" name="otherRequire" style="height:50px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-其他要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中其他要求</label>
				</td>
				<td class="value">
			     	<textarea id="personOtherRequire" name="personOtherRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore"  errormsg="请在与产品有关的要求评审项目表-负责评审的单位和人员-其他要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员其他要求</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<label class="Validform_label">
						法律法规要求
					</label>
				</td>
				<td class="value">
			     	<textarea id="regulationRequire" name="regulationRequire" style="height:50px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-法律法规要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中法律法规要求</label>
				</td>
				<td class="value">
			     	<textarea id="personLawRequire" name="personLawRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore"  errormsg="请在与产品有关的要求评审项目表-负责评审的单位和人员法律法规要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员法律法规要求</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<label class="Validform_label">
						研制责任单位<br>确定的附加要求
					</label>
				</td>
				<td class="value">
			     	<textarea id="additionRequire" name="additionRequire" style="height:50px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-研制责任单位确定的附加要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中研制责任单位确定的附加要求</label>
				</td>
				<td class="value">
			     	<textarea id="personAdditionRequire" name="personAdditionRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore" errormsg="请在与产品有关的要求评审项目表-研制责任单位确定的附加要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员研制责任单位确定的附加要求</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<label class="Validform_label">
						规定用途或<br>已知的预期用途<br>所必须的要求
					</label>
				</td>
				<td class="value">
			     	<textarea id="useRequire" name="useRequire" style="height:50px;" 
			     		datatype="*1-1000" ignore="ignore" class="colspanWidth" errormsg="请在与产品有关的要求评审项目表-规定用途或已知预期用途所必须的要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中规定用途或已知预期用途所必须的要求</label>
				</td>
				<td class="value">
			     	<textarea id="personUseRequire" name="personUseRequire" style="height:50px;width: 150px;" 
			     		datatype="*1-1000" ignore="ignore"  errormsg="请在与产品有关的要求评审项目表-负责评审的单位和人员-规定用途或已知预期用途所必须的要求中填写1-1000位任意字符"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">与产品有关的要求评审项目表中负责评审的单位和人员规定用途或已知预期用途所必须的要求</label>
				</td>
			</tr>
		</table>
	</div>
</div>
</t:formvalid>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
<%@ include file="/webpage/com/kingtake/common/toastr/toastr.jsp"%>
</body>
</html>
