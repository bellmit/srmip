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
  
  function formatDate(value, rec, index) {
	  var date = new Date(value);
		return date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate();
	}
  </script>
 </head>
 <body>
 <div class="easyui-layout" fit="true" >
  <div region="center" style="padding:0px;" >
  	<table id="center" id="dg" class="easyui-datagrid" title="节点指定"  fit="true" idField="firstname" fitColumns="true" url="tPmContractNodeRelatedController.do?assignDatagrid&tpid=${tpid}&contractNodeId=${contractNodeId}&inOutFlag=${inOutFlag}"
  	data-options="
  		idField : 'ID',
  		onDblClickRow : dbClickRow,
  		onBeforeEdit : beforeEdit
  	">
                <thead>
                <tr>
                	<th field="ID" width="50" hidden="true">主键</th>
                    <th field="TIME" width="50" formatter="formatDate"><c:if test="${inOutFlag eq 'i'}">来款</c:if><c:if test="${inOutFlag eq 'o'}">支付</c:if>时间</th>
                    <th data-options="field:'AMOUNT', width:100" formatter="formatCurrency"><c:if test="${inOutFlag eq 'i'}">来款</c:if><c:if test="${inOutFlag eq 'o'}">支付</c:if>金额(元)</th>
                    <th field="BALANCE" width="50" formatter="formatCurrency">可指定金额(元)</th>
                    <th field="CREATE_NAME" width="50">创建人</th>
                    <th field="assign" width="50" editor="numberbox">指定金额</th>
                    <th field="opt" width="50" frozenColumn="true">操作</th>
                </tr>
                </thead>
            </table>
            <input id="contractNodeId" type="hidden" value="${contractNodeId}">
  </div>
  </div>
  <script type="text/javascript" src="webpage/common/util.js"></script>
<!--   <script src = "webpage/com/kingtake/project/contractnoderelated/tPmContractNodeRelated.js"></script>	 -->
  <script type="text/javascript">
  var editId = null;
  var editIndex = null;
  var httpurl ="";
  function dbClickRow(rowIndex,rowData){
			if(editId!=rowData.ID){
				if(!saveNode()){
					return;
				}
			}
			$("#center").datagrid('beginEdit', rowIndex);
			editId = rowData.ID;
			editIndex = rowIndex;
	}
  
  /**
   * 保存节点
   * @param url
   */
  function saveNode(){
  	if(editId){
  		var data = $('#center').datagrid('getData').rows[editIndex];
  		var ed = $('#center').datagrid('getEditor', {index:editIndex,field:'assign'});
  		if(Number($(ed.target).val())<0||$(ed.target).val()==''){
  			showMsg('请正确填写指定金额！');
  			return false;
  		}
  		if(Number($(ed.target).val())>Number(data.BALANCE)){
  			showMsg('指定金额不能大于可指定金额！');
  			return false;
  		}
  		$('#center').datagrid('updateRow',{
  			index: editIndex,
  			row: {
  				assign: $(ed.target).val(),
  				opt: ""
  			}
  		});
  		
  	// 保存数据到数据库
	    	$.ajax({
	    		type : 'POST',
	    		url:'tPmContractNodeRelatedController.do?doAdd',
	    		dataType: "json",
	    		data:{
	    			'incomePayNodeId':data.ID,
	     			'amount': $(ed.target).val(),
	     			'contractNodeId':$('#contractNodeId').val()
				},
	    		success : function(data) {
	    			tip("节点指定成功！");
	    		}
	    	});
	    	$('#center').datagrid('endEdit',editIndex);
	    	editId = null;
	    	editIndex = null;
  	}
  	
  	return true;
  }
  
  function beforeEdit(rowIndex,rowDate){
	  $('#center').datagrid('updateRow',{
			index: rowIndex,
			row: {
				opt: "[<a href='javascript:void(0)' onclick='saveNode()'>保存</a>]"+"[<a href='javascript:void(0)' onclick='cancel("+rowIndex+")'>取消</a>]"
			}
		});
	}
  function cancel(index){
	  $('#center').datagrid('updateRow',{
			index: index,
			row: {
				opt: ""
			}
		});
	  $('#center').datagrid('endEdit',editIndex);
	  editId = null;
  	editIndex = null;
	  
  }
  
  function showMsg(msg){
		$.messager.show({
			title:'提示',
			msg:msg,
			timeout:5000,
			showType:'slide'
		});
	}
  </script>	
 </body>