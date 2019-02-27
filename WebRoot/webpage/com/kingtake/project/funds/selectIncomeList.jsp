<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
 <input id="selectRow" type="hidden"/>
<div id="income_div" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmIncomeList" fitColumns="true" title="到账信息表" actionUrl="tPmProjectFundsApprController.do?datagridIncomeInfo&projectId=${projectId}"  idField="id" fit="true" queryMode="group" pagination="false">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="到账日期"  field="incomeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="凭证号"  field="certificate"    queryMode="group"  width="120"></t:dgCol>
  <%--  <t:dgCol title="到账金额(元)"  field="incomeAmount"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="可分配余额(元)"  field="balance"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol> --%>
   <t:dgCol title="摘要"  field="digest"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="来款认领id"  field="incomeApplyId"  hidden="true" ></t:dgCol>
  </t:datagrid>
   
  </div>
  </div>
 <script src = "webpage/com/kingtake/project/tpmincome/tPmIncomeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmIncomeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>