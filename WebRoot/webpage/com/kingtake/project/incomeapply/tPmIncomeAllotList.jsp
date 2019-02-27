<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:if test="${select eq 1 }">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</c:if>
<div class="easyui-layout" fit="true" style="width: 900px;height:500px;">
  <div id="tPmIncome" region="center" style="padding:1px;">
  <t:datagrid name="tPmIncomeList" fitColumns="true" checkbox="false" title="到账信息表" actionUrl="tPmIncomeController.do?datagrid&loadType=${loadType}"  idField="id" fit="true" queryMode="group" >
   <%-- <t:dgToolBar onclick="doApply2()" title="认领" icon="icon-add" width="800" height="200"></t:dgToolBar> --%>

   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="到账年度"  field="incomeYear"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到账日期"  field="incomeTime" formatter="yyyy-MM-dd"   queryMode="group" query="true"  width="120"></t:dgCol>
   <t:dgCol title="凭证号"  field="certificate"    queryMode="single" query="true"  width="120"></t:dgCol>
   <t:dgCol title="到账顺序号"  field="incomeNo" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到款科目"  field="incomeSubject" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到账金额(元)"  field="incomeAmount"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="可分配余额(元)"  field="balance"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="到账借贷(元)"  field="incomeBorrow"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="摘要"  field="digest"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="会计年度"  field="accountingYear" hidden="true" query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <c:if test="${select ne 1 }">
   <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt title="认领" funname="doApply(id)" />
   </c:if>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/kingtake/project/tpmincomeallot/tPmIncomeAllotList.js"></script>		 -->
 <script type="text/javascript">
 $(document).ready(function(){
     $("#tPmIncomeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:23px;width:80px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tPmIncomeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:23px;width:80px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function doApply2() {
	 var certificatelist = "", balances = 0, incomeId = "";
	 var checked = $("#tPmIncomeList").datagrid("getSelections");
	 var nList = $("#tPmIncomeList").datagrid("getRows");
     for (var i = 0; i < checked.length; i++) {
         var index = $("#tPmIncomeList").datagrid("getRowIndex", checked[i]);
         if(certificatelist == "") {
        	 certificatelist = nList[index].certificate;
         }else {
        	 certificatelist += "," + nList[index].certificate;
         }
         if(incomeId == ""){
        	 incomeId = nList[index].id;
         }else{
        	 incomeId += "," + nList[index].id;
         }
         balances += parseFloat(nList[index].balance.replace(",", ""));
     }
     
     if (typeof (windowapi) == 'undefined') {
         $.dialog({
             id : 'incomeApply',
             content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}&certificatelist=' + certificatelist + '&balances=' + balances + '&incomeId=' + incomeId,
             lock : true,
             title : '认领',
             width : 950,
             height : window.top.document.body.offsetHeight-100,
             opacity : 0.3,
             cache : false,
             ok : function() {
                 iframe = this.iframe.contentWindow;
                 saveObj();
                 return false;
             },
             cancelVal : '关闭',
             cancel : true
         }).zindex();
     } else {
         W.$.dialog({
             id : 'incomeApply',
             content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}&certificatelist=' + certificatelist + '&balances=' + balances+ '&incomeId=' + incomeId,
             lock : true,
             title : '认领',
             width : 950,
             height : window.top.document.body.offsetHeight-100,
             parent : windowapi,
             opacity : 0.3,
             cache : false,
             ok : function() {
                 iframe = this.iframe.contentWindow;
                 saveObj();
                 return false;
             },
             cancelVal : '关闭',
             cancel : true
         }).zindex();
     }
 }
 
 function doApply(id){
	 if (typeof (windowapi) == 'undefined') {
         $.dialog({
             id : 'incomeApply',
             content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}&incomeId='+id,
             lock : true,
             title : '认领',
             width : 950,
             height : window.top.document.body.offsetHeight-100,
             opacity : 0.3,
             cache : false,
             ok : function() {
                 iframe = this.iframe.contentWindow;
                 saveObj();
                 return false;
             },
             cancelVal : '关闭',
             cancel : true
         }).zindex();
     } else {
         W.$.dialog({
             id : 'incomeApply',
             content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}&incomeId='+id,
             lock : true,
             title : '认领',
             width : 950,
             height : window.top.document.body.offsetHeight-100,
             parent : windowapi,
             opacity : 0.3,
             cache : false,
             ok : function() {
                 iframe = this.iframe.contentWindow;
                 saveObj();
                 return false;
             },
             cancelVal : '关闭',
             cancel : true
         }).zindex();
     }
 }
 </script>