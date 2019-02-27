<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="所属单位名称">
                                       所属单位名称：
            </span>
            <input type="text" name="departname" style="width: 100px" onclick="choose_297e201048183a730148183ad85c0001()"/>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_meeting_list">
<div region="center" style="padding:1px;">
  <t:datagrid name="tOMeetingRoomList" checkbox="true" fitColumns="true" title="会议室列表" actionUrl="tOMeetingRoomController.do?datagrid" 
  idField="id" sortName="createDate" sortOrder="desc"
  fit="true" queryMode="group" onDblClick="dblClickDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会议室编号"  field="roomNum"  hidden="true" query="false" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="会议室名称"  field="roomName"   query="true" isLike="true" width="120"></t:dgCol>
   <t:dgCol title="所属单位id"  field="departId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属单位名称"  field="departName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="最大容纳人数"  field="maxnum"    queryMode="group"  width="60" hidden="true"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="地点"  field="address"    queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="备注"  field="memo"  hidden="true"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOMeetingRoomController.do?doDel&id={id}" />
   <t:dgToolBar title="会议室录入" icon="icon-add" url="tOMeetingRoomController.do?goAdd"  width="730" height="240" funname="add" ></t:dgToolBar>
   <t:dgToolBar title="会议室编辑" icon="icon-edit" url="tOMeetingRoomController.do?goUpdate" width="730" height="240" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOMeetingRoomController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看会议室信息" icon="icon-search" url="tOMeetingRoomController.do?goUpdate" width="730" height="240" funname="detail"></t:dgToolBar>
   <t:dgFunOpt title="会议室使用情况" funname="viewMeetiingInfo(id)"></t:dgFunOpt>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
  title:'mytitle',
  collapsed:true,
  split:true,
  border:false,
  onExpand : function(){
    li_east = 1;
  },
  onCollapse : function() {
      li_east = 0;
  }"
     style="width: 380px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="useListpanel"></div>
    </div>
    </div>
 <script src = "webpage/com/kingtake/office/meeting/tOMeetingRoomList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
//双击查看方法
function dblClickDetail(rowIndex, rowData){
    var title = "查看会议室信息";
    var url = "tOMeetingRoomController.do?goUpdate&load=detail&id=" + rowData.id;
    var width = 730;
    var height = 240;
    createdetailwindow(title,url,width,height);
}

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOMeetingRoomController.do?upload', "tOMeetingRoomList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOMeetingRoomController.do?exportXls","tOMeetingRoomList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOMeetingRoomController.do?exportXlsByT","tOMeetingRoomList");
}

 </script>