<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//完成提醒
function finishWarn(id){
   add("完成提醒","tOWarnController.do?goFinish&id="+id);
}
</script>
<input type="hidden" id="warnId" value="${warnId}">
<t:datagrid name="tOWarnReceiveList" checkbox="true" fitColumns="false" title="公用提醒接收人" actionUrl="tOWarnController.do?datagridReceiveList&id=${warnId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人姓名"  field="receiveUsername"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否完成"  field="finishFlag" codeDict="0,WARNSTATUS"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="完成时间"  field="finishDate"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="memo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgToolBar title="添加接收人" icon="icon-add" url="tOWarnController.do?goAddReceiveUser&warnId=${warnId}" funname="add" width="430" height="100"></t:dgToolBar>
   <%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tOWarnController.do?goAddUpdateDetail&warnId=${warnId}" funname="update" width="500" height="250"></t:dgToolBar> --%>
   <t:dgDelOpt title="删除" url="tOWarnController.do?doDelDetail&id={id}" />
   <t:dgFunOpt funname="finishWarn(id)" exp="finishFlag#ne#1" title="完成"></t:dgFunOpt>
  </t:datagrid>
