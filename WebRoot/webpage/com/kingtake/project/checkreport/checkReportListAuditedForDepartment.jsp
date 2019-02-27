<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmCheckReportList" onDblClick="goDetail" fitColumns="true" title="中期检查报告审核" actionUrl="tPmCheckReportController.do?checkReportListDatagrid&type=2" idField="id" fit="true"
      queryMode="group" >
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目基_主键"  field="tpId" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="reportTitle"   query="true" queryMode="single" isLike="true"  width="120"></t:dgCol>
   <t:dgCol title="报告起始时间"  field="yearStart" formatter="yyyy-MM-dd"   queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="报告结束时间"  field="yearEnd" formatter="yyyy-MM-dd"   queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="备注"  field="remark"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查状态"  field="checkStatus" replace="未提交_0,已提交_1,审核通过_2,审核不通过_3"   queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="审查意见"  field="checkSuggest" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查人id"  field="checkUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核人姓名"  field="checkUsername" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核时间"  field="checkDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt exp="checkStatus#eq#3" title="查看修改意见" funname="viewSuggestion(id)" />
      <t:dgToolBar title="查看" icon="icon-search" url="tPmCheckReportController.do?goUpdate" funname="detail" width="750" height="400"></t:dgToolBar>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });

    
  
  //审核
  function doAudit(id,opt){
      $.ajax({
          url:'tPmIncomeApplyController.do?doAudit',
          type:'GET',
          dataType:'json',
          cache:'false',
          data:{'id':id,"opt":opt},
          success:function(data){
             tip(data.msg);
             if(data.success){
                 reloadTable();
             }
          }
      });
  }
  
//查看审批意见
  function viewSuggestion(id,index){
      var url = "tPmCheckReportController.do?goPropose&id="+id;
      createdetailwindow("查看修改意见",url,450,120);
  }
  
  //查看
  function goDetail(rowIndex, rowData){
      var url = "tPmCheckReportController.do?goUpdate&load=detail&id="+rowData.id;
      createdetailwindow("查看",url,750,400);
  }
</script>