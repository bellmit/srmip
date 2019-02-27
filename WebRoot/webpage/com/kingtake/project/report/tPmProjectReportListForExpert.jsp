<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools"></t:base>
<div style="width: 1000px;height: 300px;margin: 0 auto;">
    <t:datagrid name="tPmProjectReportList" onDblClick="dbcl" idField="id" fitColumns="true" actionUrl="tPmProjectReportController.do?datagrid&projectId=${projectId}"
         title="申报依据" fit="false" queryMode="group">
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
<script type="text/javascript">

  function dbcl(rowIndex, rowData) {
    var viewUrl = rowData.viewUrl;
    detail('查看', viewUrl, 'tPmProjectReportList', "100%", "100%");
  }
</script>