<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input type="hidden" id="role" value="${role }">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="jfjlList" checkbox="true" fitColumns="true" title="缴费记录列表" actionUrl="tZSqController.do?jfjlDatagrid&sqId=${sqId }" idField="id" fit="true" queryMode="group" onDblClick="goViewDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="缴纳方式" field="jnfs" replace="发明人缴纳_1,办公室缴纳_2" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="缴纳金额" field="jnje" extendParams="formatter:formatCurrency," queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="费用审批时间" field="fyspsj" extendParams="formatter:spsjFormatter," queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="汇款人账号" field="hkrzh" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="汇款人" field="hkrmc"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="联系方式" field="lxfs" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
      <t:dgDelOpt  title="删除" url="tZSqController.do?doDelJfjl&id={id}" />
      <t:dgToolBar title="录入" icon="icon-add" url="tZSqController.do?goJfjlUpdate&role=${role}&sqId=${sqId }&opt=add" funname="add" height="100%"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tZSqController.do?goJfjlUpdate&role=${role}" funname="update"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tZSqController.do?goJfjlUpdate&opt=view" funname="detail" height="100%"></t:dgToolBar>
      <c:if test="${role eq 'depart'}">
      <t:dgFunOpt exp="jnfs#eq#1" funname="goUpdate(id)" title="审批"></t:dgFunOpt>
      </c:if>
    </t:datagrid>
  </div>
</div>
<input id="tipMsg" type="hidden">
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tZZlsqListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
    function goUpdate(id) {
        var url = "tZSqController.do?goJfjlUpdate";
        url += '&id=' + id;
        createwindow("编辑", url, null, "100%");
    }
    
    function goViewDetail(rowIndex, rowData) {
        var id = rowData.id;
        var url = "tZSqController.do?goJfjlUpdate&load=detail";
        url += '&id=' + id;
        createdetailwindow("查看", url, null, "100%");
    }
    
    //重新加载
    function reloadJfjl(){
        $("#jfjlList").datagrid("reload");
    }
    
    //编辑
    function goUpdate(id){
        var role = $("#role").val();
        var url = "tZSqController.do?goJfjlUpdate&role="+role+"&id="+id;
        createwindow("审核",url,null,null);
    }
    
    function spsjFormatter(value,rec,index){
        var s = "";
        if(value!=""){
        var str = new Date().format('yyyy-MM-dd',value);
        s = "<span><font color='red'>"+str+"</span>"
        }
        return s;
    }
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script> 