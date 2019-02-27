<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmPayfirstList" onDblClick="goDetail" fitColumns="true" title="垫支经费列表" actionUrl="tPmPayfirstController.do?datagrid" idField="id" fit="true"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="科目代码" field="paySubjectcode" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" queryMode="single" query="true" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="垫支时间" field="createDate" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="垫支经费额度(元)"  field="payFunds"  queryMode="group"  width="150" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="来源经费科目" field="fundsSubject" hidden="true" queryMode="single" width="120"></t:dgCol> 
      <t:dgCol title="垫支理由"  field="reason"    queryMode="group"  width="250"></t:dgCol>
      <t:dgCol title="垫支来源"  field="paySource"  replace="大学_1,责任单位_2,承研单位_3"  queryMode="group"  width="250"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" width="100" queryMode="group" dictionary="bpm_status"></t:dgCol>     
      <t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol> 
      <t:dgFunOpt title="生成垫支经费申报书" funname="exportWord(id)" />       
    </t:datagrid>
  </div>
</div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });
    
    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'tPmPayfirstList',
                content : 'url:tPmPayfirstController.do?goAddUpdate&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'tPmPayfirstList',
                content : 'url:tPmPayfirstController.do?goAddUpdate&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
     }   	               
    
    //导出垫支经费申请书WORD
    function exportWord(id) {    	
    	if(id == ""){
    		tip('请选择数据');
    	}else{
    		$.ajax({
                url : 'tPmPayfirstController.do?createWord&id=' + id,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                	downloadWord(data.attributes.FileName);
                }
            });
    	}    	
    }
    
    //下载计划经费分配通知书WORD
    function downloadWord(FileName) {
    	JeecgExcelExport("tPmPayfirstController.do?downloadWord&FileName=" + FileName,"tPmPayfirstList");
    }
</script>