<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBProjectTypeList" checkbox="true" fitColumns="false" title="项目类别" actionUrl="tBProjectTypeController.do?getProjectTypeTree" idField="projectTypeName" fit="true" treegrid="true" treeId="id" treeField="projectTypeName"  queryMode="group" 
      pagination="false" >
      <t:dgCol title="主键" field="id" treefield="id"  hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="项目类型" field="projectTypeName" treefield="projectTypeName" queryMode="group" width="250" overflowView="true"></t:dgCol>
      <t:dgCol title="立项形式" field="approvalCode" treefield="approvalCode" queryMode="group" width="60" replace="计划类_1,合同类_2"></t:dgCol>
      <t:dgCol title="经费性质" field="fundsPropertyId" treefield="fundsPropertyId" queryMode="group" width="60" dictionary="T_B_FUNDS_PROPERTY,ID,FUNDS_NAME"></t:dgCol>
      <%-- <t:dgCol title="负责该类项目机关参谋" field="officer" queryMode="group" width="150" dictionary="t_s_base_user,id,realname"></t:dgCol> --%>
      <t:dgCol title="排序码" field="sortCode" treefield="sortCode" queryMode="group" width="60" align="center"></t:dgCol>
      <t:dgCol title="备注" field="memo" treefield="memo" queryMode="group" width="150"></t:dgCol>
      <t:dgCol title="操作" field="opt" treefield="opt" width="200"></t:dgCol>
<%--       <t:dgOpenOpt url="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType={id}" title="预算类别"></t:dgOpenOpt> --%>
      <t:dgFunOpt funname="goSidecatelogRelaSet(id)" title="项目模块"></t:dgFunOpt>
      <t:dgFunOpt title="删除" funname="del(id)" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBProjectTypeController.do?goAdd" funname="add" width="350" height="300"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBProjectTypeController.do?goUpdate" funname="update" width="350" height="300"></t:dgToolBar>
      <%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="tBProjectTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBProjectTypeController.do?goUpdate" funname="detail" width="350" height="300"></t:dgToolBar>
      <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <%-- <t:dgToolBar title="数据初始化" icon="icon-putout" funname="initModule"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/base/projecttype/tBProjectTypeList.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBProjectTypeController.do?upload', "tBProjectTypeList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBProjectTypeController.do?exportXls", "tBProjectTypeList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBProjectTypeController.do?exportXlsByT", "tBProjectTypeList");
    }

    //项目类型模块设置
    function goSidecatelogRelaSet(id, index) {
        gridname="tBProjectTypeList";
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'sidecatelogList',
                content : 'url:tBApprovalBudgetRelaController.do?goProjectTypeInfoRela&typeId='+id,
                lock : true,
                title : '设置模块关联',
                width : 500,
                height : window.top.document.body.offsetHeight - 100,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var rows = iframe.getCheckedRows();
                    saveProjectTypeCatalogRela(id, rows);
                    return false;
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        } else {
            W.$.dialog({
                id : 'sidecatelogList',
                content : 'url:tBApprovalBudgetRelaController.do?goProjectTypeInfoRela&typeId='+id,
                lock : true,
                title : '设置模块关联',
                width : 500,
                height : window.top.document.body.offsetHeight - 100,
                parent : windowapi,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var rows = iframe.getCheckedRows();
                    saveProjectTypeCatalogRela(id, rows);
                    return false;
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }

    //保存项目类型与模块设置
    function saveProjectTypeCatalogRela(id, rows) {
        var catalogListStr = JSON.stringify(rows);
        $.ajax({
            url : 'tBApprovalBudgetRelaController.do?saveProjectTypeInfoRela',
            data : {
                'id' : id,
                'catalogListStr' : catalogListStr
            },
            type : 'POST',
            dataType : 'json',
            success : function(data) {
                if (data.success) {
                    reloadTable();
                    iframe.close();
                }
                tip(data.msg);
            }
        });
    }
    
    //删除
    function del(id){
        var url = "tBProjectTypeController.do?doDel&id="+id;
        var name="tBProjectTypeList";
        gridname=name;
    	createdialog('删除确认 ', '确定删除该项目类型及及子项吗 ?', url,name);
    }
</script>