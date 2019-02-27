<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="loginUser" type="hidden" value="${uid }">
  <input id="submitNo" type="hidden" value="${submitNo }">
  <input id="submitYes" type="hidden" value="${submitYes }">
  <input id="submitReceive" type="hidden" value="${submitReceive }">
  <input id="submitReturn" type="hidden" value="${submitReturn }">
  <t:datagrid name="tOHandoverList" checkbox="true" fitColumns="true" title="交班材料信息"  pageSize="30" pageList="[30,50,100]"
  	sortName="handoverTime" sortOrder="desc" idField="id" fit="true" onDblClick="dblClickDetail"
  	actionUrl="tOHandoverController.do?datagrid" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建id"  field="handoverId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="handoverName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人单位"  field="handoverDepartName"   query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="提交时间"  field="handoverTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="本周工作内容"  field="handoverContent"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人"  field="receiverName" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="接收人id"  field="receiver" hidden="true" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="接收时间"  field="recieveTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="交班状态"  field="submitFlag"   queryMode="group"  width="80" codeDict="1,TJZT"></t:dgCol>
   <t:dgCol title="状态变更时间"  field="submitTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&handoverId#eq\#${uid}" title="删除" url="tOHandoverController.do?doDel&id={id}" />
   <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&handoverId#eq\#${uid}" title="提交" funname="doHandoverSubmit(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiver#eq\#${uid}" title="接收" funname="doRecieve(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiver#eq\#${uid}" title="退回" funname="doReturn(id)" ></t:dgFunOpt>
   <%-- <t:dgFunOpt exp="submitFlag#eq#1" title="已提交" funname="goSubmit(id)" ></t:dgFunOpt> --%>
   <t:dgToolBar title="录入交班材料" icon="icon-add" url="tOHandoverController.do?goAdd" funname="add" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑交班材料" icon="icon-edit" url="tOHandoverController.do?goUpdate" funname="update" height="500"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOHandoverController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看交班材料" icon="icon-search" url="tOHandoverController.do?goUpdate" funname="detail" height="500"></t:dgToolBar>
   <t:dgToolBar title="复制交班材料" icon="icon-edit"  funname="goCopy" height="500"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
   <t:dgToolBar title="发送提醒" icon="icon-message"  funname="sendPlanMsg"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/information/tOHandoverList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
        $('#tOHandoverList').datagrid({
             onClickRow : function(rowIndex, rowData) {
                 rowid = rowData.id;
                 gridname = 'tOHandoverList';
                 var receiver = rowData.receiver;//点击行接收人Id
                 var handoverId = rowData.handoverId;//点击行交班人id
                 var submitFlag = rowData.submitFlag;//点击行交班状态
                 
                 var loginId = $("#loginUser").val();//当前登录人Id
                 var submitReceive = $("#submitReceive").val();//常量-已接收
                 var submitYes = $("#submitYes").val();//常量-已接收
                 var a = $("a[onclick*='update']");//获取编辑按钮
                 a.linkbutton('enable');//启用编辑按钮
                 /*
                 *满足下面任意一个条件，就禁用编辑按钮
                 *1)交班状态为已提交
                 *2)交班人为当前登录人，且交班状态为已接收
                 */
                 if((receiver == submitYes) ||
                     (handoverId == loginId && submitFlag == submitReceive)){
                     a.linkbutton('disable');//禁用编辑按钮
                 }
                 
         	}
        });
 		//给时间控件加上样式
		$("#tOHandoverListtb").find("input[name='handoverTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='handoverTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//双击查看方法
function dblClickDetail(rowIndex, rowData){
    var title = "查看交班材料信息";
    var url = "tOHandoverController.do?goUpdate&load=detail&id=" + rowData.id;
    var width = 700;
    var height = 350;
    createdetailwindow(title,url,width,height);
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOHandoverController.do?upload', "tOHandoverList");
}

//导出
function ExportXls() {
    var rows = $("#tOHandoverList").datagrid("getChecked");
    var idArr = [];
    for(var i=0;i<rows.length;i++){
        idArr.push(rows[i].id);
    }
    var ids = null;
    if(idArr.length>0){
       ids = idArr.join(",");
	   JeecgExcelExport("tOHandoverController.do?exportXls","tOHandoverList",ids);
    }else{
	   JeecgExcelExport("tOHandoverController.do?exportXls","tOHandoverList");
    }
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOHandoverController.do?exportXlsByT","tOHandoverList");
}

function sendPlanMsg(){
    sendMsg("交班材料");
}
 </script>
 <script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>