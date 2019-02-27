<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:if test="${1 eq 1 }">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</c:if>
<div class="easyui-layout" fit="true" style="width: 900px;height:500px;">
  <div id="tPmIncome" region="center" style="padding:1px;">
  <t:datagrid name="tPmIncomeList" fitColumns="true" checkbox="false" title="到账信息表" actionUrl="tPmIncomeController.do?datagrid2&voucherNo=${voucherNo}"  idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="10"></t:dgCol>
   <t:dgCol title="摘要"  field="digest"  query="false" isLike="true"  queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="到账年度"  field="incomeYear"  query="false" isLike="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="到账日期"  field="incomeTime" formatter="yyyy-MM-dd"   queryMode="group" query="false"  width="100"></t:dgCol>
   <t:dgCol title="凭证号"  field="certificate"    queryMode="single" query="false"  width="100"></t:dgCol>
   <t:dgCol title="到账顺序号"  field="incomeNo" query="false" isLike="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="到款科目"  field="incomeSubject" query="false" isLike="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="到账金额(元)"  field="incomeAmount"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="可分配余额(元)"  field="balance"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="到账借贷(元)"  field="incomeBorrow"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="会计年度"  field="accountingYear" hidden="true" query="false" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>

 <script type="text/javascript">
 $(document).ready(function(){
     $("#tPmIncomeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:23px;width:80px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tPmIncomeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:23px;width:80px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>