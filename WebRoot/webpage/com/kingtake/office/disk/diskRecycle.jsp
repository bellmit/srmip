<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<style>
.btn {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
      touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
}
.btn:focus,
.btn:active:focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn.active.focus {
  outline: thin dotted;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
.btn:hover,
.btn:focus,
.btn.focus {
  color: #333;
  text-decoration: none;
}
.btn:active,
.btn.active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
}
.btn-primary {
  color: #fff;
  background-color: #337ab7;
  border-color: #2e6da4;
}
.btn-primary:hover,
.btn-primary:focus,
.btn-primary.focus,
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  color: #fff;
  background-color: #286090;
  border-color: #204d74;
}
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  background-image: none;
}
</style>
<script type="text/javascript">
$(function(){
    $('#file_upload1').uploadify({
        buttonText : '浏览',
        auto : false,
        progressData : 'speed',
        multi : true,
        height : 25,
        width : 120,
        overrideEvents : [ 'onDialogClose' ],
        fileTypeDesc : '文件格式:',
        queueID : 'filediv1',
        fileTypeExts : '*.*;',
        fileSizeLimit : '100MB',
        swf : 'plug-in/uploadify/uploadify.swf',
        uploader : 'tODiskController.do?saveDiskFiles&businessType=personalDisk&uploadType=personal',
        onUploadStart : function(file) {
          var bid = $('#bid').val();
          $('#file_upload1').uploadify("settings", "formData", {
            'bid' : bid
          });
        },
        onQueueComplete : function(queueData) {
            $("#diskList").datagrid("reload");
        },
        onUploadSuccess : function(file, data, response) {
            var d = $.parseJSON(data);
            updateUploadSuccess("fileShow1",d,file,response);
        },
        onFallback : function() {
          tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
        },
        onSelectError : function(file, errorCode, errorMsg) {
          switch (errorCode) {
            case -100:
              tip("上传的文件数量已经超出系统限制的" + $('#file_upload1').uploadify('settings', 'queueSizeLimit') + "个文件！");
              break;
            case -110:
              tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload1').uploadify('settings', 'fileSizeLimit') + "大小！");
              break;
            case -120:
              tip("文件 [" + file.name + "] 大小异常！");
              break;
            case -130:
              tip("文件 [" + file.name + "] 类型不正确！");
              break;
          }
        },
        onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
        }
      }); 
});


</script>
<input id="uploadType" type="hidden" value="${uploadType}">
<input id="groupId" type="hidden" value="${groupId}">
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px;">
  <t:datagrid name="diskList" checkbox="true" fitColumns="true" title="恢复网盘列表" 
  		actionUrl="tODiskController.do?datagridForRecycle&uploadType=${uploadType}&groupId=${groupId}" 
 	 	idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="文件名"  field="attachmenttitle" query="true"  queryMode="single" width="220" extendParams="formatter:fileFormatter,"></t:dgCol>
   <t:dgCol title="文件后缀名"  field="extend" hidden="true"  queryMode="group" width="120" ></t:dgCol>
   <t:dgCol title="上传时间"  field="createdate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="删除人"  field="delUserName"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="删除时间"  field="delTime"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="文件路径"  field="realpath" hidden="true"  queryMode="single"  width="120"></t:dgCol>
  <t:dgToolBar title="恢复" icon="icon-redo" funname="doBack"></t:dgToolBar>
  <t:dgToolBar title="彻底删除" icon="icon-no" funname="doThoroughDelete"></t:dgToolBar>

  </t:datagrid>
  </div>
  </div>

</div>
 <script type="text/javascript">
 $(document).ready(function(){
 });

//导入
 function ImportXls() {
 	openuploadwin('数据导入', 'dbImportController.do?upload', "dbImportList");
 }

 //导出
 function ExportXls() {
 	JeecgExcelExport("dbImportController.do?exportXls","dbImportList");
 }

 function doBack(){
     var row = $('#diskList').datagrid('getSelections');
     if (row.length <= 0) {
         tip('请至少选择一条记录！');
         return;
     } else {
         $.messager.confirm('确认', '您确认恢复勾选的文件吗？', function(r) {
             if (r) {
         var ids = [];
         for (var i = 0; i < row.length; i++) {
             ids.push(row[i].id);
         }
         ids = ids.join(',');
         $.ajax({
             url : 'tODiskController.do?doBack',
             data : {
                 ids : ids
             },
             type : 'post',
             dataType : 'json',
             success : function(data) {
                 tip(data.msg);
                 $("#diskList").datagrid('reload');
             }
         });
     }
     });
     }
 }
 
 function doThoroughDelete(){
     var row = $('#diskList').datagrid('getSelections');
     if (row.length <= 0) {
         tip('请至少选择一条记录！');
         return;
     } else {
         $.messager.confirm('确认', '您确认删除勾选的文件吗？', function(r) {
             if (r) {
         var ids = [];
         for (var i = 0; i < row.length; i++) {
             ids.push(row[i].id);
         }
         ids = ids.join(',');
         $.ajax({
             url : 'tODiskController.do?doThoroughDelete',
             data : {
                 ids : ids
             },
             type : 'post',
             dataType : 'json',
             success : function(data) {
                 tip(data.msg);
                 $("#diskList").datagrid('reload');
             }
         });
     }
     });
     }
 }
</script>
<script type="text/javascript" src="webpage/com/kingtake/office/disk/disk.js"></script>