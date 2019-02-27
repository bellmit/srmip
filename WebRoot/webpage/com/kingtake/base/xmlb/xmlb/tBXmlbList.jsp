<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBXmlbList" checkbox="true" fitColumns="false" title="项目类别" actionUrl="tBXmlbController.do?getXmlbTree" idField="id" fit="true" treegrid="true" treeId="id" treeField="xmlb"  queryMode="group" 
      pagination="false" >
      <t:dgCol title="主键" field="id" treefield="id"  hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="项目类别" field="xmlb" treefield="xmlb" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="主管单位" field="zgdw" treefield="zgdw" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="经费类型" field="jflx" treefield="jflx" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="项目类型" field="xmlx" replace="_0,横向_1,纵向_2,自主立项项目_3,校内协作项目_4" treefield="xmlx" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="项目性质" field="xmxz" replace="_0,自然科学研究类_1,社会科学研究类_2,军事理论研究类_3,条件建设类_4,其他类_5" treefield="xmxz" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="项目来源" field="xmly" replace="_0,国家部委_1,省_2,地方政府及部门_3,军委_4,海军_5,战区、其他军兵种、舰队_6,部队、军队科研院所、军工厂_7,装备生产承制单位_8,地方高校、企业单位_9,其他情况_10" treefield="xmly" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="会计编码规则" field="kjbmgz" treefield="kjbmgz" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="唯一编号" field="wybh" treefield="wybh" queryMode="group" width="60" align="center"></t:dgCol>
      <t:dgCol title="排序码" field="sortCode" treefield="sortCode" queryMode="group" width="60" align="center"></t:dgCol>
      <t:dgCol title="备注" field="bz" treefield="bz" queryMode="group" overflowView="true"></t:dgCol>
      <t:dgCol title="操作" field="opt" treefield="opt" width="200"></t:dgCol>
      <%-- <t:dgOpenOpt url="tBApprovalBudgetRelaController.do?tBApprovalBudgetRelaFromFund&fundProperty={id}" title="预算类别"></t:dgOpenOpt> --%>
      <t:dgFunOpt title="删除" funname="del(id)" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBXmlbController.do?goAdd" funname="xmlbAdd" width="550" height="450"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBXmlbController.do?goUpdate" funname="xmlbUpdate" width="550" height="450"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBXmlbController.do?goUpdate" funname="detail" width="550" height="450"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
//删除
function del(id){
    var url = "tBXmlbController.do?doDel&id="+id;
    var name="tBXmlbList";
	createdialog('删除确认 ', '确定删除该项目类别及及子项吗 ?', url,name);
}
</script>