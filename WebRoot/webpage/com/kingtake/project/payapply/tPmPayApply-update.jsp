<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>支付申请</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<script type="text/javascript">
    function saveCallback(){
        var win = W.$.dialog.list['processDialog'].content;
        win.reloadTable();
        frameElement.api.close();
    }
    
    $(function(){
    //如果页面是详细查看页面(审批查看 详情)
    if(location.href.indexOf("edit=false")!=-1){
      //无效化所有表单元素，只能进行查看
      $(":input").attr("disabled","true");
      //隐藏选择框和清空框
      $("a[icon='icon-search']").css("display","none");
      $("a[icon='icon-redo']").css("display","none");
      //隐藏下拉框箭头
      $(".combo-arrow").css("display","none");
      //隐藏添加附件
      $("#filediv").parent().css("display","none");
      //隐藏附件的删除按钮
      $(".jeecgDetail").parent().css("display","none");
      //将发送蛇皮设为可点
      $(".sendButton").removeAttr("disabled");
    }
    
    //编辑时审批已处理：提示不可编辑
    if(location.href.indexOf("load=detail&tip=true") != -1){
      var parent = frameElement.api.opener;
      var msg = $("#tipMsg", parent.document).val();
      tip(msg);
    }
  });
</script>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPayApplyController.do?doAddUpdate" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tPmPayApplyPage.id }">
    <input id="contractNodeId" name="contractNodeId" type="hidden" value="${tPmPayApplyPage.contractNodeId }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 承研单位名称: </label></td>
        <td class="value"><input id="devdepartName" name="devdepartName" type="text" style="width: 150px" class="inputxt" value='${tPmPayApplyPage.devdepartName}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承研单位名称</label></td>
        <td align="right"><label class="Validform_label"> 项目负责人名称: </label></td>
        <td class="value"><input id="projectManagerName" name="projectManagerName" type="text" style="width: 156px" class="inputxt" value='${tPmPayApplyPage.projectManagerName}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目负责人名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 项目名称: </label></td>
        <td class="value" colspan="3"><input id="projectName" name="projectName" type="text" style="width: 456px" class="inputxt" value='${tPmPayApplyPage.projectName}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label"> 会计编码: </label></td>
        <td class="value"><input id="accountingCode" name="accountingCode" type="text" style="width: 150px" class="inputxt" value='${tPmPayApplyPage.accountingCode}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
        <td align="right"><label class="Validform_label"> 项目编码: </label></td>
        <td class="value"><input id="projectCode" name="projectCode" type="text" style="width: 156px" class="inputxt" value='${tPmPayApplyPage.projectCode}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编码</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label"> 合同名称: </label></td>
        <td class="value"><input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt" value='${tPmPayApplyPage.contractName}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同名称</label></td>
        <td align="right"><label class="Validform_label"> 合同编号: </label></td>
        <td class="value"><input id="contractCode" name="contractCode" type="text" style="width: 156px" class="inputxt" value='${tPmPayApplyPage.contractCode}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同编号</label></td>
      </tr>
      <tr>
      <td align="right" ><label class="Validform_label"> 合同乙方: </label></td>
        <td class="value" colspan="3"><input id="contractUnitnameb" name="contractUnitnameb" type="text" style="width: 456px" class="inputxt" value='${tPmPayApplyPage.contractUnitnameb}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同乙方</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同开始时间: </label></td>
        <td class="value"><input id="contractStartTime" name="contractStartTime" type="text" style="width: 150px;height: 28px;" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tPmPayApplyPage.contractStartTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">合同开始时间</label></td>
        <td align="right"><label class="Validform_label"> 合同结束时间: </label></td>
        <td class="value"><input id="contractEndTime" name="contractEndTime" type="text" style="width: 156px; height: 28px;" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tPmPayApplyPage.contractEndTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">合同结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同总价款: </label></td>
        <td class="value"><input id="contractTotalAmount" name="contractTotalAmount" type="text" style="width: 150px;text-align: right;" class="inputxt easyui-numberbox" value='${tPmPayApplyPage.contractTotalAmount}' 
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" > <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同总价款</label></td>
        <td align="right"><label class="Validform_label"> 已付合同款: </label></td>
        <td class="value"><input id="contractPaidAmount" name="contractPaidAmount" type="text" style="width: 156px;text-align: right;" class="inputxt easyui-numberbox" value='${tPmPayApplyPage.contractPaidAmount}' 
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">已付合同款</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 本期付款金额: <font color="red">*</font></label></td>
        <td class="value"><input id="currentPayAmount" name="currentPayAmount" type="text" style="width: 150px;text-align: right;" class="inputxt easyui-numberbox" value='${tPmPayApplyPage.currentPayAmount}' 
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">本期付款金额</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 本期付款要求: </label></td>
        <td class="value" colspan="3"><textarea id="payDemand" style="width: 456px; height: 100px;" class="inputxt" name="payDemand">${tPmPayApplyPage.payDemand}</textarea> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">本期付款要求</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同执行情况: </label></td>
        <td class="value" colspan="3"><textarea id="executeState" style="width: 456px; height: 100px;" class="inputxt" name="executeState">${tPmPayApplyPage.executeState}</textarea> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同执行情况</label></td>
      </tr>
      <tr>
    </table>
  </t:formvalid>
</body>
