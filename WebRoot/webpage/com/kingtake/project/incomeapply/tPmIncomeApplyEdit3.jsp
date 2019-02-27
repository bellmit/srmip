<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!DOCTYPE html>
<html>
<head>
<title>项目来款申请信息</title>
<style type="text/css">
	.formtable td{border:1px solid #95b8e7; border-bottom:0px;padding:5px 3px;}
</style>
<script type="text/javascript">
     $(function(){
    	 $("input").css("background","#fff");
     });
</script>
</head>
<body>
      <table style="width: 100%; margin: 0 auto;border:0px;" cellpadding="0" cellspacing="0" class="formtable">
        <tr>
          <td align="right" width="20%">计划文件号: </td>
          <td class="value" width="30%">${jh.voucherNo} </td>
          <td align="right" width="20%">计划文件名: </td>
          <td class="value" width="30%">${jh.invoice_invoiceNum1} </td>
        </tr>
        <tr>
          <td align="right">计划下达时间: </td>
          <td class="value">${jh.incomeTime} </td>
          <td align="right">计划年份: </td>
          <td class="value">${jh.planYear} </td>
        </tr>
        <tr>
          <td align="right">计划分配总金额:</td>
          <td class="value" colspan='3'>
          	<input feetype="sub" type="text" class="easyui-numberbox"  data-options="min:-99999999.99,max:99999999.99,precision:2,groupSeparator:','"
											value="${jh.applyAmount}" style="border:0px; width:120px; color:red;text-align:right;">&nbsp;元</td>
        </tr>
      </table>
      
     <!-- <table style="width: 100%; margin: 0 auto;" clpadding="0" clspacing="1" class="formtable"> 
      
        <tr>
          <td align="right">项目编号</td>
          <td align="right">项目名称</td>
          <td class="right">项目分配金额</td>
        </tr>
      </table> -->
      <div style="width:934px;height:440px;">
      <t:datagrid name="tPmProjectList" fitColumns="false" title="项目金额分配明细" pageSize="20" pageList="[20,40,60]" checkbox="fasle"
	  	actionUrl="tPmProjectController.do?datagridState2&lxStatus=1&lxyj=1,3&projectPlanId=${jh.id }" idField="id" fit="true" queryMode="group" >
		   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
		   <t:dgCol title="项目编号"  field="projectNo" queryMode="group"  width="150"></t:dgCol>
		   <t:dgCol title="项目申请号"  field="cxm" queryMode="single" width="160"></t:dgCol>
		   <t:dgCol title="申报项目名称"  field="glxm.projectName" width="120"></t:dgCol>
		   <t:dgCol title="立项项目名称"  field="projectName" query="false" isLike="true" width="120"></t:dgCol>
		   <t:dgCol title="本次分配(元)"  field="budgetAmount"  query="false"  isLike="true" queryMode="single"  align="right" width="110" formatter1="function(v){return '<font color=red >'+transformAmount(v)+'</font>';}"></t:dgCol>
		   <t:dgCol title="总经费(元)"  field="allFee"  query="false"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
		   <t:dgCol title="已安排(元)"  field="yap"  query="false"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
		   <t:dgCol title="当年经费(元)"  field="dnjf"  query="false"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
		   <t:dgCol title="账面余额(元)"  field="zmye"  query="false"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
		   <t:dgCol title="项目类型" field="xmlx" query="false" isLike="true" queryMode="single" codeDict="0,XMLX" width="60"></t:dgCol>
		   <t:dgCol title="项目密级" field="secretDegree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="70"></t:dgCol>
		   <t:dgCol title="起始日期"  field="beginDate"  query="false"  isLike="true" queryMode="single"  width="160"></t:dgCol>
		   <t:dgCol title="截止日期"  field="endDate"  query="false"  isLike="true" queryMode="single"  width="160"></t:dgCol>
		   <t:dgCol title="责任部门"  field="dutyDepartStr"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="承研部门"  field="devDepartStr" query="false" isLike="true" queryMode="single" width="120"></t:dgCol>  
		   <t:dgCol title="来源单位"  field="sourceUnit" width="120"></t:dgCol>  
		   <t:dgCol title="负责人"  field="projectManager"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="任务指定"  field="rwzd" codeDict="0,RWZD" width="80"></t:dgCol>
		   <t:dgCol title="立项依据"  field="lxyj" codeDict="0,LXYJ" width="80" query="false" isLike="false" queryMode="single" ></t:dgCol>
		   <t:dgCol title="联系人"  field="contact"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="联系电话"  field="contactPhone"  query="false"  isLike="true" queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="会计编码"  field="accountingCode"  query="false"  isLike="true" queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="合同名称"  field="planContractName"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="合同文号"  field="planContractRefNo"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="申报人"  field="projectManager"  query="false"  isLike="true" queryMode="single"  width="80"></t:dgCol>
		   <t:dgCol title="项目类别母项"  field="xmml" queryMode="single"  width="100"></t:dgCol>
		   <t:dgCol title="项目类别"  field="xmlbStr" queryMode="single"  width="100"></t:dgCol>
		   <t:dgCol title="经费类型"  field="jflxStr" queryMode="single"  width="100"></t:dgCol>
	  </t:datagrid>
	  </div>
</body>
</html>