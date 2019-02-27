<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true" style="height:250px;">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmProjectReportList" onDblClick="dbcl" idField="id" fitColumns="true" actionUrl="tPmProjectReportController.do?datagrid&projectId=${projectId}" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="来源" field="relatedType" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" queryMode="group" width="250"></t:dgCol>
      <t:dgCol title="公文编号" field="fileNum" queryMode="group" width="120"></t:dgCol>
      <%--      <t:dgCol title="相关人员"  field="relatedUserName"  queryMode="group"  width="120"></t:dgCol> --%>
      <t:dgCol title="发文日期" field="relatedTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="查看路径" field="viewUrl" hidden="true" queryMode="group" width="120"></t:dgCol>
      <%--    <t:dgCol title="所属项目"  field="project.projectName" queryMode="group"  width="180"></t:dgCol> --%>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
  $(document).ready(function() {
    //设置datagrid的title
    $("#tPmProjectReportList").datagrid({
      title : '${projectName }-申报依据'
    });
  });

  function dbcl(rowIndex, rowData) {
    var viewUrl = rowData.viewUrl;
    detail('查看', viewUrl, 'tPmProjectReportList', "100%", "100%");
  }
</script>