<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="projectTypeList" fitColumns="false"  title="项目类别" actionUrl="tBProjectTypeController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="sortCode"
      pagination="false">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目类型" field="projectTypeName" queryMode="group" width="300"></t:dgCol>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });
    function close(){
      frameElement.api.close();
    }
</script>