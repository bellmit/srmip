<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>节点指定</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function checkAmount(){
	  var balance = Number($('#BALANCE').val());
	  var amount = Number($('#amount').val());
	  if(balance<amount){
		  $.Showmsg("指定金额不得大于可选金额，请重新填写指定金额！");
		  return false;
	  }
	  return true;
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmContractNodeRelatedController.do?doAdd" beforeSubmit="checkAmount" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmContractNodeRelatedPage.id }">
					<input id="contractNodeId" name="contractNodeId" type="hidden" value="${contractNodeId}">
					<input id="incomePayNodeId" name="incomePayNodeId" type="hidden" value="${tPmContractNodeRelatedPage.incomePayNodeId}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							节点可选金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="BALANCE" name="BALANCE" type="text" style="width: 150px;text-align:right" class="inputxt" readonly="readonly">
					     	 <t:choose url="tPmContractNodeRelatedController.do?goAssign&tpid=${tpid}&contractNodeId=${contractNodeId}" icon="icon-search" left="50%" top="50%" tablename="tPmNodeList" width="500px" height="300px" textname="id,BALANCE,BALANCE" inputTextname="incomePayNodeId,BALANCE,amount"  title="选择节点"></t:choose>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">节点可选金额</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							指定金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="amount" name="amount" type="text" style="width: 150px;text-align:right" datatype="n1-10" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">指定金额</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/contractnoderelated/tPmContractNodeRelated.js"></script>		