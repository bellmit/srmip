<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input id="typeCatalogRelaStr" type="hidden" value="${typeCatalogRelaStr}">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmSidecatalogList" checkbox="true" fitColumns="true" title="项目模块配置表" actionUrl="tPmSidecatalogController.do?datagrid" idField="id" fit="true" queryMode="group"
      pagination="false" onLoadSuccess="checkRow">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="类型" field="moduleType" queryMode="single" replace="基本信息_1,过程管理_2" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" queryMode="group" width="120"></t:dgCol>
	  <t:dgToolBar title="从其它类型复制" icon="icon-put" funname="copyFromOther"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    function getCheckedRows() {
        var checked = $("#tPmSidecatalogList").datagrid("getChecked");
        return checked;
    }

    function close() {
        frameElement.api.close();
    }

    //选中行
    function checkRow(data) {
        var typeCatalogRelaStr = $("#typeCatalogRelaStr").val();
        var array = typeCatalogRelaStr.split(",");
        var rows = $("#tPmSidecatalogList").datagrid('getRows');
        for (var i = 0; i < array.length; i++) {
            for (var j = 0; j < rows.length; j++) {
                if (rows[j].id == array[i]) {
                    var index = $("#tPmSidecatalogList").datagrid("getRowIndex", rows[j]);
                    $("#tPmSidecatalogList").datagrid("checkRow", index);
                    break;
                }
            }
        }
    }
    
    /**
     * 选择从哪个项目类型复制相应的关联模块
     */
    function copyFromOther() {
      gridname = "tPmSidecatalogList";
      var projTypeId = "${projectType}" ;
      if (typeof (windowapi) == 'undefined') {
        $.dialog({
          id : 'projectTypeList',
          content : 'url:tBProjectTypeController.do?goSelect',
          lock : true,
          title : '请选择复制哪个项目类型的关联模块',
          width : 350,
          height : window.top.document.body.offsetHeight - 100,
          opacity : 0.3,
          cache : false,
          ok : function() {
            iframe = this.iframe.contentWindow;
            var rows = iframe.getprojectTypeListSelections('id')[0];
            saveProjectTypeCatalogRela(projTypeId, rows);
            return false;
          },
          cancelVal : '关闭',
          cancel : true
        }).zindex();
      } else {
        W.$.dialog({
          id : 'projectTypeList',
          content : 'url:tBProjectTypeController.do?goSelect',
          lock : true,
          title : '请选择复制哪个项目类型的关联模块',
          width : 350,
          height : window.top.document.body.offsetHeight - 100,
          parent : windowapi,
          opacity : 0.3,
          cache : false,
          ok : function() {
            iframe = this.iframe.contentWindow;
            var rows = iframe.getprojectTypeListSelections('id')[0];
            saveProjectTypeCatalogRela(projTypeId, rows);
            return false;
          },
          cancelVal : '关闭',
          cancel : true
        }).zindex();
      }
    }
    
  //保存复制的项目关联模块数据
    function saveProjectTypeCatalogRela(projTypeId, rows) {
      if (typeof (windowapi) == 'undefined') {
        $.dialog.confirm('一旦确定将会把该类型现有的关联模块全部删除，然后再从相应的项目类型复制过来，该操作不可逆，确定复制？', function() {
        	saveCatalogRela(projTypeId,rows);
        }, function() {
        }).zindex();
    } else {
        W.$.dialog.confirm('一旦确定将会把该类型现有的关联模块全部删除，然后再从相应的项目类型复制过来，该操作不可逆，确定复制？', function() {
        	saveCatalogRela(projTypeId,rows);
        }, function() {
        }, windowapi).zindex();
    }
    }

    function saveCatalogRela(projTypeId,rows){
      var catalogListStr = JSON.stringify(rows);
      //alert(projTypeId + "+"+ rows);
      $.ajax({
          url : 'tBApprovalBudgetRelaController.do?saveCatalogRelaFromCopy',
          data : {
              'projectTypeId' : projTypeId,
              'fromProjectTypeId' : rows
          },
          type : 'POST',
          dataType : 'json',
          success : function(data) {
              if (data.success) {
            	  var typeCatalogRelaStr = data.obj;
            	  $("#typeCatalogRelaStr").val(data.obj);
                  reloadTable();
                  var data =  $('#tPmSidecatalogList').datagrid('getData');
                  checkRow(data);
                  iframe.close();
              }
              tip(data.msg);
          }
      });
    }
</script>
