<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>合同验收报告</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmContractCheckController.do?doUpdate" tiptype="1" tipSweep="true" callback="@Override checkSaveCallback">
<link rel="stylesheet" href="webpage/com/kingtake/project/panelUtil/panel.css" type="text/css"></link>
<script type="text/javascript" src="webpage/com/kingtake/project/panelUtil/panel.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/apprcontractcheck/tPmContractCheck.js"></script>
<script type="text/javascript">
$(function(){
	//如果页面是详细查看页面(审批查看 详情)
	if(location.href.indexOf("edit=false")!=-1){
		//无效化所有表单元素，只能进行查看
		$(":input").attr("disabled","true");
		//隐藏选择框和清空框
		$("a[icon='icon-search']").css("display","none");
		$("a[icon='icon-redo']").css("display","none");
		//隐藏下拉框箭头
		$(".combo-arrow").css("display","none");
		//隐藏添加附件
		$("#filediv").parent().css("display","none");
		//隐藏附件的删除按钮
		$(".jeecgDetail").parent().css("display","none");
		//将发送蛇皮设为可点
		$(".sendButton").removeAttr("disabled");
	}
	
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("load=detail&tip=true") != -1){
		var parent = frameElement.api.opener;
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
});
</script>
<style type="text/css">
	.test22{
		width:530px;
	}
	*{padding:0; margin:0}
	.tdLabel{
		font-size:20px;
	}
	.tdInput{
		font-size:20px;
		width:300px;
		height:30px;
		line-height:30px;
	}
	.divLabel{
		font-size:14px;
		/* color:#5E7595; */
		/* font-weight: bold; */
		height:20px;
		line-height:20px;
	}
	.divInput{
		font-size:14px;
		width:150px;
		height:20px;
		line-height:20px;
	}
	
	/* 设置表单样式 */
	.tableDiv{
		text-align:center
	}
	.formtable2{
		margin:auto;
	}
	.tableField{
		text-align:right;
		padding:8px 3px 8px 20px;
	}
	.tableValue{
		padding:8px 20px 8px 3px;
	}
	.colspanWidth{
		width:411px;
	}
</style>
<script type="text/javascript">
	$(function(){
		//加载单位树下拉框
		//责任单位selectDutyDepart dutyDepartname dutyDepartid
    	$("#selectDutyDepart").combotree({
    		url:'departController.do?getDepartTree', 
    		width:305,
    		height:30,
 			editable:false,
 			onSelect: function(rec){
 				$('#dutyDepartid').val(rec.id);
	        	$('#dutyDepartname').val(rec.text);
	        }
    	});
		//组织单位organizationUnitid organizationUnitname selectOrganizationUnit
    	$("#selectOrganizationUnit").combotree({
    		url:'departController.do?getDepartTree', 
 			editable:false,
 			onSelect: function(rec){
 				$('#organizationUnitid').val(rec.id);
	        	$('#organizationUnitname').val(rec.text);
	        }
    	});
		
    	//给单位树下拉框赋值
    	$("#selectDutyDepart").combotree("setValue", "${tPmContractCheckPage.dutyDepartname}");
    	$("#selectOrganizationUnit").combotree("setValue", "${tPmContractCheckPage.organizationUnitname}");
	})
	
</script>
<div style="height:610px;overflow-y:hidden;">
  	<input id="id" name="id" type="hidden" value="${tPmContractCheckPage.id}">
  	<input id="projectId" name="contract.project.id"  type="hidden" value="${contract.project.id }">
    <input id="contractId" name="contractId" type="hidden" value="${tPmContractCheckPage.contractId}" >
  	<input id="contractType" name="contractType" type="hidden" value="${tPmContractCheckPage.contractType}">
  	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title">
			封面
		</div>
		<div class="tool" state="live">
			<a class="collapse expand" href="javascript:void(0)"></a>
			<span>隐藏</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:84%">
		<div style="height:30px; line-height:30px; margin-right:20px;" class="tableField">
			<label class="divLabel">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级:</label><font color="red">*</font>
			<t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" codeType="0"
			     		code="XMMJ" defaultVal="${tPmContractCheckPage.secretDegree}"></t:codeTypeSelect>
		</div>
		<div style="height:30px; line-height:30px; margin-right:20px;" class="tableField">
			<label class="divLabel">合同编号:</label>
			<input id="contractCode" name="contractCode" type="text" class="divInput" 
				value='${tPmContractCheckPage.contractCode}' readonly="readonly"
				style="width:150px;">
		</div>
		<p style="padding:10px 0 10px 0; text-align:center;font-size: 30px; font-weight:bolder; letter-spacing:10px;">
			海军工程大学${contractTypeArray[0]}合同<br>验收报告
		</p>
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td class="tableField" style="width:35%">
					<label class="tdLabel">
						会计编码:
					</label>
				</td>
				<td class="tableValue"><!-- length = 20 -->
			     	<input id="accountingCode" name="accountingCode" type="text" class="tdInput" 
			     		value='${tPmContractCheckPage.accountingCode}' readonly="readonly" >
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">会计编码</label>
				</td>
			</tr>
			<tr>
				<td class="tableField" style="width: 35%;">
					<label class="tdLabel">
						项目名称:<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue"><!-- length = 100 -->
			     	<input id="projectName" name="projectName" type="text" class="tdInput"
			     		value='${tPmContractCheckPage.projectName}' onChange="sameProjectName(2)"
			     		datatype="*1-50">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">项目名称</label>
				</td>
			</tr>
			<tr>
				<td class="tableField" style="width: 35%;">
					<label class="tdLabel">
						合同名称:<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue"><!-- length = 100 -->
			     	<input id="contractName" name="contractName" type="text" class="tdInput" 
			     		value='${tPmContractCheckPage.contractName}' onChange="sameContractName(2)"
			     		datatype="*1-50">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">合同名称</label>
				</td>
			</tr>
			<tr>
				<td class="tableField" style="width: 35%;">
					<label class="tdLabel">
						责任单位:<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue">
					<input id="dutyDepartid" name="dutyDepartid" type="hidden" datatype="*"
						value='${tPmContractCheckPage.dutyDepartid}'>
					<!-- length = 60 -->
			     	<input id="dutyDepartname" name="dutyDepartname" type="hidden" 
			     		value='${tPmContractCheckPage.dutyDepartname}'>
			     	<input id="selectDutyDepart" type="text" class="tdInput"/>
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">责任单位</label>
				</td>
			</tr>
			<tr>
				<td class="tableField" style="width: 35%;">
					<label class="tdLabel">
						合同乙方:<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue"><!-- length = 100 -->
			     	<input id="contractSecondParty" name="contractSecondParty" type="text" class="tdInput" 
			     		value='${tPmContractCheckPage.contractSecondParty}' datatype="*1-50">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">合同乙方</label>
				</td>
			</tr>
			<tr>
				<td class="tableField" style="width: 35%;">
					<label class="tdLabel">
						验收时间:<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue">
			     	<input id="checkTime" name="checkTime" type="text" class="tdInput Wdate"
			     		value='<fmt:formatDate value="${tPmContractCheckPage.checkTime}" 
			      			pattern="yyyy-MM-dd" type="date" />'
			     		onClick="WdatePicker()" datatype="date">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">验收时间</label>
				</td>
			</tr>
		</table>
	</div>
	
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title">
			说明
		</div>
		<div class="tool" state="dead">
			<a class="collapse" href="javascript:void(0)"></a>
			<span>展开</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:84%;display:none">
		<div style="width:100%">
			<p style="text-align:center; font-size: 30px; font-weight: bold; padding:20px 0 20px 0;">填表说明</p>
			<div style="font-size: 20px; padding:0 50px 0 50px;">
			1、本报告适合于学校${contractTypeArray[1]}类合同验收。<br>
			2、合同验收一般有项目承研单位组织，经责任单位审核后报科研部审批；有特殊要求合同按合同约定组织。<br>
			3、提交验收报告时需一并合同标的相关作证材料，
			</div>
		</div>
	</div>
	
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title">
			详细信息
		</div>
		<div class="tool" state="dead">
			<a class="collapse" href="javascript:void(0)"></a>
			<span>展开</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:84%;display:none" class="tableDiv">
		<table cellpadding="10" cellspacing="0" class="formtable2">
			<!-- <tr>
				<td class="tableValue" colspan="4" align="right">
				 	单位：元
				</td>
			</tr> -->
			<br>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						项目名称
					</label>
				</td>
				<td class="tableValue" colspan="3">
			     	<input id="projectName2" type="text" class="inputxt colspanWidth" 
			     		value='${tPmContractCheckPage.projectName}' onChange="sameProjectName()">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">项目名称</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						合同名称
					</label>
				</td>
				<td class="tableValue" colspan="3">
			     	<input id="contractName2" type="text" class="inputxt colspanWidth"
			     		onChange="sameContractName()" value='${tPmContractCheckPage.contractName}'>
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">合同名称</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						合同价款
					</label>
				</td>
				<td class="tableValue"><!-- scale=2,length=10 -->
				    <input id="contractAmount" name="contractAmount" type="text" style="width:135px;text-align:right;" 
				    	value='${tPmContractCheckPage.contractAmount}' class="easyui-numberbox"
			     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">合同价款</label>
				</td>
				<td class="tableField">
					<label class="Validform_label">
						签订时间<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue">
					<input id="contractSigningTime" name="contractSigningTime" type="text" style="width: 150px" 
			      		value='<fmt:formatDate value="${tPmContractCheckPage.contractSigningTime}" pattern="yyyy-MM-dd" type="date" />'
			      		class="Wdate" onClick="WdatePicker()" datatype="date">      
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">签订时间</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						执行期限<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue">
					<input id="beginTime" name="beginTime" type="text" style="width: 150px" 
     					value='<fmt:formatDate value="${tPmContractCheckPage.beginTime}" pattern="yyyy-MM-dd" type="date" />' 
     					class="Wdate" onClick="WdatePicker()" datatype="date">    
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">(执行期限)合同开始时间</label>
				</td>
				<td class="tableField">
					<label class="Validform_label">
						至<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue">
					<input id="endTime" name="endTime" type="text" style="width: 150px" 
						value='<fmt:formatDate value="${tPmContractCheckPage.endTime}" pattern="yyyy-MM-dd" type="date" />' 
						class="Wdate" onClick="WdatePicker()" datatype="date">    
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">(执行期限)合同截止时间</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						已付价款
					</label>
				</td>
				<td class="tableValue"><!-- scale=2,length=10 -->
			     	<input id="paidMoney" name="paidMoney" type="text" style="width:135px;text-align:right;" 
			     		value="${tPmContractCheckPage.paidMoney}" class="easyui-numberbox"
			     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">已付价款</label>
				</td>
				<td class="tableField">
					<label class="Validform_label">
						待付价款
					</label>
				</td>
				<td class="tableValue"><!-- scale=2,length=10 -->
			     	<input id="waitMoney" name="waitMoney" type="text" style="width:135px;text-align:right;" 
			     		class="easyui-numberbox" value="${tPmContractCheckPage.waitMoney}"
			     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">待付价款</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						组织单位<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue"> 
			     	<input id="organizationUnitid" name="organizationUnitid" type="hidden" datatype="*"
			     		value="${tPmContractCheckPage.organizationUnitid}">
			     	<!-- length=60 -->
		     		<input id="organizationUnitname" name="organizationUnitname" type="hidden"
		     			value="${tPmContractCheckPage.organizationUnitname}">
		     		<input id="selectOrganizationUnit" type="text" style="width: 155px" 
			     		class="inputxt">
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">组织单位</label>
				</td>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						${contractTypeArray[2]}<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue" colspan="3">
					<!-- 3000 -->
				  	<textarea class="inputxt colspanWidth" rows="4" id="contractTarget" 
				  		name="contractTarget" datatype="*1-1500">${tPmContractCheckPage.contractTarget}</textarea>
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">${contractTypeArray[2]}</label>
				</td>
			<tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						${contractTypeArray[3]}<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue" colspan="3">
					<!-- 3000 -->
				  	<textarea class="inputxt colspanWidth" rows="4" id="researchResult" 
				  		name="researchResult" datatype="*1-1500">${tPmContractCheckPage.researchResult}</textarea>
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">${contractTypeArray[3]}</label>
				</td>
			</tr>
			<tr>
				<td class="tableField">
					<label class="Validform_label">
						备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注<font color="red">*</font>
					</label>
				</td>
				<td class="tableValue" colspan="3">
					<!-- 300 -->
					<textarea  class="inputxt colspanWidth" rows="4" id="memo" 
				  		name="memo" datatype="*1-300">${tPmContractCheckPage.memo}</textarea>
					<span class="Validform_checktip" style="margin-left:0px;"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
  			<tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tPmContractCheckPage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tPmContractCheckPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmContractCheck" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
		</table>
	</div>
</div>
</t:formvalid>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>