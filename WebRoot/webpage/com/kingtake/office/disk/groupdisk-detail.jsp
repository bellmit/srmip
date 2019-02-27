<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
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

.btn:focus,.btn:active:focus,.btn.active:focus,.btn.focus,.btn:active.focus,.btn.active.focus
  {
  outline: thin dotted;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}

.btn:hover,.btn:focus,.btn.focus {
  color: #333;
  text-decoration: none;
}

.btn:active,.btn.active {
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

.btn-primary:hover,.btn-primary:focus,.btn-primary.focus,.btn-primary:active,.btn-primary.active,.open>.dropdown-toggle.btn-primary
  {
  color: #fff;
  background-color: #286090;
  border-color: #204d74;
}

.btn-primary:active,.btn-primary.active,.open>.dropdown-toggle.btn-primary
  {
  background-image: none;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
    $(function() {
        var groupId = $("#groupId").val();
        $('#file_upload1').uploadify({
                    buttonText : '浏览',
                    auto : false,
                    progressData : 'speed',
                    multi : true,
                    height : 25,
                    width : 100,
                    overrideEvents : [ 'onDialogClose' ],
                    fileTypeDesc : '文件格式:',
                    queueID : 'filediv1',
                    fileTypeExts : '*.*',
                    fileSizeLimit : '2GB',
                    successTimeout:120,
                    swf : 'plug-in/uploadify/uploadify.swf',
                    uploader : 'tODiskController.do?saveDiskFiles&uploadType=group&businessType=groupDisk&groupId='
                            + groupId,
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
                        //updateUploadSuccess("fileShow1", d, file, response);
                    },
                    onFallback : function() {
                        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
                    },
                    onSelectError : function(file, errorCode, errorMsg) {
                        switch (errorCode) {
                        case -100:
                            tip("上传的文件数量已经超出系统限制的" + $('#file_upload1').uploadify('settings', 'queueSizeLimit')
                                    + "个文件！");
                            break;
                        case -110:
                            tip("文件 [" + file.name + "] 大小超出系统限制的"
                                    + $('#file_upload1').uploadify('settings', 'fileSizeLimit') + "大小！");
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
        var columns = [  {
            field : 'id',
            title : 'id',
            width : 100,
            hidden : true
        },{
            field : '用户id',
            title : 'userId',
            width : 100,
            hidden : true
        },{
            field : 'userName',
            title : '用户名',
            width : 100,
            formatter:nameFormatter
        }];
        var toolbars = [];
        var isMgr = $("#isMgr").val();
        if(isMgr=='1'){
            columns.push({
                field : 'opt',
                title : '操作',
                width : 100,
                formatter:optFormatter
            });
            toolbars.push({
                iconCls : 'icon-add',
                text : '添加成员',
                handler : function() {
                   addUser();
                }
            });
            toolbars.push({
                iconCls : 'icon-cancel',
                text : '解散',
                handler : function() {
                   breakGroup();
                }
            });
        }
        $('#memberList').datagrid({
            title:'群组成员',
            url:'tODiskController.do?datagridMember&groupId='+groupId+"&field=id,userId,userName,isMgr",
            fitColumns : true,
            nowrap : true,
            striped : true,
            remoteSort : false,
            idField : 'id',
            checkOnSelect : false,
            columns : [ columns ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            },
            toolbar : toolbars
        });
    });

    //操作列
    function optFormatter(value, row, index) {
        if(row.isMgr=='0'){
            return '<a href="#" style="color:blue;text-decoration: underline;" onclick=removeUser("'+row.userId+'")>移除</a>';
        }
    }
    
    //移除成员
    function removeUser(userId) {
        var groupId = $("#groupId").val();
        $.messager.confirm('确认', '您确认移除该成员吗？', function(r) {
            if (r) {
                $.ajax({
                    url : 'tODiskController.do?doRemoveMember',
                    type : 'POST',
                    data:{groupId:groupId,userId:userId},
                    dataType : 'json',
                    cache : false,
                    success : function(data) {
                        tip(data.msg);
                        if(data.success){
                            $('#memberList').datagrid('reload');
                        }
                    }
                });
            }
        });
    }

    function nameFormatter(value, row, index) {
        if (row.isMgr == '1') {
            return "<font color='red'>" + value + "</font>";
        } else {
            return value;
        }
    }

    function addUser() {
        var url = 'tODiskController.do?goSelectMember';
        var width = 400;
        var height = 150;
        var title = "选择成员";
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var realName = iframe.getRealName();
                var userId = iframe.getUserId();
                if (realName == "") {
                    return false;
                }
                doAddMember(userId);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
    
    //解散群组
    function breakGroup() {
        $.messager.confirm('确认', '您确认解散该群组吗？', function(r) {
            if (r) {
                var groupId = $("#groupId").val();
        $.ajax({
            url : 'tODiskController.do?breakGroup',
            data : {
                groupId : groupId
            },
            cache : false,
            type : 'POST',
            dataType : 'json',
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    window.parent.$("#grouptree").tree("reload");
                    window.location = "tODiskController.do?goGroupDiskMain";
                }
            }
        });
       }
      });
    }

    function doAddMember(userId) {
        var groupId = $("#groupId").val();
        $.ajax({
            url : 'tODiskController.do?doAddMember',
            data : {
                groupId : groupId,
                userId : userId
            },
            cache : false,
            type : 'POST',
            dataType : 'json',
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    $("#memberList").datagrid("reload");
                }
            }
        });
    }

    function goDelete() {
    	var row = $('#diskList').datagrid('getSelections');
    	if (row.length <= 0) {
    		tip('请至少选择一条记录！');
    		return;
    	} else {
    		$.messager.confirm('确认', '您确认隐藏勾选的文件吗？', function(r) {
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

    //回收站
    function goRecycle(){
        var title="我的回收站";
        var url = "tODiskController.do?goRecycle&uploadType=group&groupId=${groupId}";
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
    	    cancelVal: '关闭',
    	    cancel: function(){
    	        $("#diskList").datagrid('reload');
    	    }
    	}).zindex();
    }
</script>
<input id="groupId" type="hidden" value="${groupId}">
<input id="isMgr" type="hidden" value="${isMgr}">
<div class="easyui-layout" fit="true">
  <div data-options="region:'east'" style="width:200px;">
  <table id="memberList"></table>
  <%-- <t:datagrid name="memberList" checkbox="true" fitColumns="true" title="群组成员" actionUrl="tODiskController.do?datagridMember&groupId=${groupId}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
    <t:dgCol title="姓名" field="userName" queryMode="single" width="120"></t:dgCol>
    <t:dgCol title="是否管理员" field="isMgr" queryMode="single" width="120"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <c:if test="${isMgr eq '1' }">
       <t:dgDelOpt exp="isMgr#ne#1" title="移除" url="commonController.do?delFile&id={id}" />
    </c:if>
  </t:datagrid>
</div> --%>
</div>
<div data-options="region:'center'">
  <div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'north'" style="height: 120px;" split="true">
    <table id="materiaTab" style="width: 100%; margin-top: 30px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 120px;"><label class="Validform_label" style="font-size: 20px;"> 上传附件: </label></td>
        <td class="value" style="width: 120px;"><input type="hidden" id="bid" name="bid" />
       <table style="max-width: 360px;" id="fileShow1">
          </table>
          <div>
            <span id="file_upload1span">
              <input type="file" name="fiels" id="file_upload1" />
            </span>
          </div></td>
        <td style="width: 150px;"><input type="button" value="上传" onclick='$("#file_upload1").uploadify("upload", "*");' style="width: 105px; height:30px; position: relative; top: -5px; border-radius:20px"
            class="btn btn-primary"></td>
        <td>
          <div class="form jeecgDetail" id="filediv1"></div>
        </td>
      </tr>
      <tr>
        <td colspan="4"><span style="margin-left: 40px;">
            <font color="red" style="font-size: 20px;">说明：1、不能上传文件夹。</font>
          </span></td>
      </tr>
    </table>
  </div>
  <div data-options="region:'center'">
    <t:datagrid name="diskList" checkbox="true" fitColumns="true" title="群共享文件" actionUrl="tODiskController.do?datagridForGroup&groupId=${groupId}" idField="id" fit="true" queryMode="group" >
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="文件名" field="attachmenttitle" query="true" queryMode="single" width="220" extendParams="formatter:fileFormatter,"></t:dgCol>
      <t:dgCol title="文件后缀名" field="extend" hidden="true" queryMode="group" width="120" ></t:dgCol>
      <t:dgCol title="上传时间" field="createdate" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="文件路径" field="realpath" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgToolBar title="删除" icon="icon-hidden" funname="goDelete"></t:dgToolBar>
      <t:dgToolBar title="回收站" icon="icon-recycle" funname="goRecycle"></t:dgToolBar>
<%--       <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgDelOpt title="删除" url="commonController.do?delFile&id={id}" /> --%>
    </t:datagrid>
    </div>
  </div>
  </div>
</div>
</div>
<script type="text/javascript" src="webpage/com/kingtake/office/disk/disk.js"></script>