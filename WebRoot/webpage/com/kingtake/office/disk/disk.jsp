<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
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
        buttonText : '??????',
        auto : false,
        progressData : 'speed',
        multi : true,
        height : 25,
        width : 100,
        overrideEvents : [ 'onDialogClose' ],
        fileTypeDesc : '????????????:',
        queueID : 'filediv1',
        fileTypeExts : '*.*',
        fileSizeLimit : '2GB',
        successTimeout:120,
        swf : 'plug-in/uploadify/uploadify.swf',
        uploader : 'tODiskController.do?saveDiskFiles&businessType=disk&uploadType=public',
        onUploadStart : function(file) {
          var bid = $('#bid').val();
          var filePath = $("#file_upload1").val();
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
          tip("????????????FLASH???????????????????????????????????????FLASH???????????????");
        },
        onSelectError : function(file, errorCode, errorMsg) {
          switch (errorCode) {
            case -100:
              tip("????????????????????????????????????????????????" + $('#file_upload1').uploadify('settings', 'queueSizeLimit') + "????????????");
              break;
            case -110:
              tip("?????? [" + file.name + "] ???????????????????????????" + $('#file_upload1').uploadify('settings', 'fileSizeLimit') + "?????????");
              break;
            case -120:
              tip("?????? [" + file.name + "] ???????????????");
              break;
            case -130:
              tip("?????? [" + file.name + "] ??????????????????");
              break;
          }
        },
        onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
        }
      }); 
});


</script>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px;" >
  <t:datagrid name="diskList" checkbox="true" fitColumns="true" title="??????????????????" 
  		actionUrl="tODiskController.do?datagridForPublic&uploadType=public" 
 	 	idField="id" fit="true" queryMode="group">
   <t:dgCol title="??????"  field="id"  hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="?????????"  field="attachmenttitle" query="true"  queryMode="single" width="220" extendParams="formatter:fileFormatter,"></t:dgCol>
   <t:dgCol title="???????????????"  field="extend" hidden="true"  queryMode="group" width="120" ></t:dgCol>
   <t:dgCol title="????????????"  field="createdate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="????????????"  field="realpath" hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgToolBar title="??????" icon="icon-hidden" funname="goDelete"></t:dgToolBar>
   <t:dgToolBar title="?????????" icon="icon-recycle" funname="goRecycle"></t:dgToolBar>
<%--    <t:dgCol title="??????" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="??????" url="commonController.do?delFile&id={id}" /> --%>
  </t:datagrid>
  </div>
  </div>
  <div region="north" style="height: 120px;" split="true">
  <table id="materiaTab" style="width: 100%;margin-top: 30px;" cellpadding="0" cellspacing="1" class="formtable">
  <tr>
        <td align="right" style="width: 120px;">
          <label class="Validform_label" style="font-size: 20px;"> ????????????: </label>
        </td>
        <td class="value" style="width: 120px;">
          <input type="hidden" id="bid" name="bid" />
          <table style="max-width: 360px;" id="fileShow1">
          </table>
          <div>
            <span id="file_upload1span">
              <input type="file" name="fiels" id="file_upload1" />
            </span>
          </div>
        </td>
        <td style="width: 150px;">
            <input type="button" value="??????" onclick='$("#file_upload1").uploadify("upload", "*");' style="width: 105px ; height:30px;position: relative; top: -5px; border-radius:20px" class="btn btn-primary">
        </td>
        <td>
        <div class="form jeecgDetail" id="filediv1"></div>
        </td>
      </tr>
      <tr>
      <td colspan="4"><span style="margin-left:40px;"><font color="red" style="font-size: 20px;">?????????1???????????????????????????</font></span></td>
      </tr>
      </table>
  </div>
</div>

 <script type="text/javascript">
 $(document).ready(function(){
 });
 
//??????
function ImportXls() {
	openuploadwin('????????????', 'dbImportController.do?upload', "dbImportList");
}

//??????
function ExportXls() {
	JeecgExcelExport("dbImportController.do?exportXls","dbImportList");
}


function goDelete() {
	var row = $('#diskList').datagrid('getSelections');
	if (row.length <= 0) {
		tip('??????????????????????????????');
		return;
	} else {
		$.messager.confirm('??????', '????????????????????????????????????', function(r) {
			if (r) {
				var ids = [];
				for (var i = 0; i < row.length; i++) {
					ids.push(row[i].id);
				}
				ids = ids.join(',');
				$.ajax({
					url : 'tODiskController.do?doHide',
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

//?????????
function goRecycle(){
    var title="???????????????";
    var url = "tODiskController.do?goRecycle&uploadType=public";
    var width = window.top.document.body.offsetWidth;
    var height = window.top.document.body.offsetHeight-100;
    $.dialog({
		content: 'url:'+url,
		lock : true,
		width:width,
		height:height,
		title:title,
		opacity : 0.3,
		cache:false,
	    cancelVal: '??????',
	    cancel: function(){
	        $("#diskList").datagrid('reload');
	    }
	}).zindex();
}

 </script>
 <script type="text/javascript" src="webpage/com/kingtake/office/disk/disk.js"></script>