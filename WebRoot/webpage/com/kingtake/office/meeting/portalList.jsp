<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px; border: 0px">
  <t:datagrid name="tOMeetingList" fit="true" fitColumns="true" actionUrl="tOMeetingController.do?datagridForPortal" 
  	idField="id" queryMode="group"  onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="标题"  field="content"   queryMode="group" width="80" overflowView="true"></t:dgCol>
   <t:dgCol title="时间"  field="time" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group" width="60"></t:dgCol>
   <t:dgCol title="发起人"  field="sender"   queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="待办类型"  field="type"  replace="会议信息_1,交班材料_2,差旅信息_3,日程安排_4"  queryMode="group"  width="40"></t:dgCol>
   <t:dgCol field="opt" title="操作"></t:dgCol>
   <t:dgFunOpt exp="type#eq#2" funname="doReceive(id)" title="接收"></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/meeting/tOMeetingList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tOMeetingListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
function goDetail(index,rowData){
    var type = rowData.type;
    var url = "";
    if(type=='1'){
        url = "tOMeetingController.do?goUpdate";
        detail('查看会议信息',url,'tOMeetingList',630,500);
    }else if(type=='2'){
        url = "tOHandoverController.do?goUpdate";
        detail('查看交班材料',url,'tOMeetingList',700,400);
    }else if(type=='3'){
        url = "tOTravelLeaveController.do?goUpdate";
        detail('查看差旅信息',url,'tOMeetingList',800,400);
    }else if(type=='4'){
        url = "tOScheduleController.do?goUpdate&isOpen=1";
        detail('查看日程安排',url,'tOMeetingList',800,400);
    }
}
    //交班材料接收
    function doReceive(id){
           var url = "tOHandoverController.do?doRecieve";
            $.ajax({
                async : false,
                cache : false,
                type : 'POST',
                data : {id:id},
                url : url,// 请求的action路径
                error : function() {// 请求失败处理函数
                },
                success : function(data) {
                	 var d = $.parseJSON(data);
                         tip(d.msg);
                     if (d.success) {
                         $("#tOMeetingList").datagrid('reload');
                     }
                }
            });
        }
 </script>
