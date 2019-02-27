<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<style type="text/css">
.Wdate {
	height: 14px;
}
</style>
<script type="text/javascript">
    $(document).ready(function() {

        var projId = $("#projectId").val();
        if (projId == "") {
            tip("当前还没有项目，请先新建项目!");
        }
        initTab();//加载表格

            //无效化所有表单元素，只能进行查看
            $("#taskTitle").attr("disabled", "true");
            //隐藏添加附件
            $("#filediv").parent().css("display", "none");
            //隐藏附件的删除按钮
            $(".jeecgDetail").parent().css("display", "none");
            //隐藏easyui-linkbutton
            $(".easyui-linkbutton").css("display", "none");
    });

    function uploadFile(data) {
        $("#bid").val(data.obj.id);
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        } else {
            tip(data.msg);
            if (data.success) {
                window.location.reload();
            }
        }

    }

    //上传成功后，刷新
    function uploadCallback(data) {
        tip(data.msg);
        window.location.reload();
    }

    //增加校验规则
    function checkDate() {
        var rows = $('#taskNodeList').datagrid("getRows");
        if (rows.length == 0) {
            tip("请录入任务节点！");
            return false;
        }
        var delRows = [];
        for (var i = 0; i < rows.length; i++) {
            var editors = $('#taskNodeList').datagrid("getEditors", i);
            if (editors.length == 0) {
                if (rows[i].planTimeStr == "") {
                    delRows.push(i);
                }
                continue;
            }
            if (!$('#taskNodeList').datagrid('validateRow', i)) {
                tip("任务节点请输入必填项！");
                return false;
            }
            $('#taskNodeList').datagrid("endEdit", i);
        }
        for (var i = 0; i < delRows.length; i++) {
            $('#taskNodeList').datagrid("deleteRow", delRows[i]);
        }
        var taskNodeListStr = JSON.stringify(rows);
        $("#taskNodeListStr").val(taskNodeListStr);
    }

    //初始化成员表格
    function initTab() {
        var toolbar = [];
        if (location.href.indexOf("load=detail") == -1) {//不包含
            toolbar = [ {
                iconCls : 'icon-add',
                text : '添加',
                handler : function() {
                    $('#taskNodeList').datagrid('appendRow', {
                        planTimeStr : '',
                        finishTimeStr : '',
                        taskContent : ''
                    });
                }
            }, {
                iconCls : 'icon-remove',
                text : '删除',
                handler : function() {
                    var checked = $("#taskNodeList").datagrid("getSelections");
                    for (var i = 0; i < checked.length; i++) {
                        var index = $("#taskNodeList").datagrid('getRowIndex', checked[i]);
                        $("#taskNodeList").datagrid("deleteRow", index);
                    }
                }
            } ];
        }
        $('#taskNodeList').datagrid({
                            title : '任务节点',
                            fitColumns : true,
                            nowrap : true,
                            height : 300,
                            width : 890,
                            striped : true,
                            remoteSort : false,
                            idField : 'id',
                            editRowIndex : -1,
                            singleSelect : true,
                            /* toolbar : toolbar, */
                            columns : [ [
                                    {
                                        field : 'id',
                                        title : 'id',
                                        width : 100,
                                        hidden : true
                                    },
                                    {
                                        field : 'planTimeStr',
                                        title : '计划完成时间',
                                        width : 80,
                                        editor : {
                                            type : 'datebox',
                                            options : {
                                                required : true
                                            }
                                        }
                                    },
                                    {
                                        field : 'finishTimeStr',
                                        title : '实际完成时间',
                                        width : 80,
                                        editor : {
                                            type : 'datebox',
                                            options : {
                                                required : true
                                            }
                                        }
                                    },
                                    {
                                        field : 'taskContent',
                                        title : '任务内容',
                                        width : 150,
                                        editor : {
                                            type : 'validatebox',
                                            options : {
                                                required : true
                                            }
                                        }
                                    },
                                    {
                                        field : 'finishFlag',
                                        title : '是否完成',
                                        width : 150,
                                        formatter : function(value, row, index) {
                                            if (value == "1") {
                                                return "是";
                                            } else {
                                                return "否";
                                            }
                                        }
                                    },
                                    {
                                        field : 'opt',
                                        title : '完成情况',
                                        width : 150,
                                        formatter : function(value, row, index) {
                                            var id = row.id;
                                            var finishFlag = row.finishFlag;
                                            if (id != null && finishFlag == '1') {
                                                return "<a href=\"#\" style=\"text-decoration: underline;color: blue; \" onclick=\"openFinishWindow('"
                                                        + id + "');\">查看完成情况说明</a>";
                                            } else {
                                                return "";
                                            }
                                        }
                                    } ] ],
                            onDblClickRow : function(rowIndex, rowData) {
                            },
                            onBeforeEdit : function(rowIndex, rowData) {

                            },
                            onAfterEdit : function(rowIndex, rowData) {

                            },
                            pagination : false,
                            rownumbers : true,
                            onLoadSuccess : function() {
                            }
                        });
        loadData();
    }

    //打开完成情况窗口
    function openFinishWindow(id) {
        var url = 'tPmTaskController.do?goFinishPage&load=detail';
        url += '&id=' + id;
        createdetailwindow('完成情况', url, 650, 400);
    }

    function loadData() {
        //加载数据
        var id = $("#id").val();
        if (id != "") {
            var x_url = "tPmTaskController.do?tPmTaskNodeList&id=" + id;
            $('#taskNodeList').datagrid('options').url = x_url;
            setTimeout(function() {
                $('#taskNodeList').datagrid('load');
            }, 0);

        }
    }
</script>
  <div style="height: 500px; width: 100%;">
    <div style="position: fixed; top: 30; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <h1 align="center">${tPmTaskPage.project.projectName }-任务书/任务节点管理</h1>
      <span> </span>
    </div>
    <br>
    <br>
    <input id="auditStatus" type="hidden" value="${tPmTaskPage.auditStatus}">
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tPmTaskController.do?doAddUpdate" callback="@Override uploadFile" beforeSubmit="checkDate">
      <input id="id" name="id" type="hidden" value="${tPmTaskPage.id }">
      <input id="projectId" name="project.id" type="hidden" value="${tPmTaskPage.project.id }">
      <input id="createBy" name="createBy" type="hidden" value="${tPmTaskPage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tPmTaskPage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tPmTaskPage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tPmTaskPage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tPmTaskPage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tPmTaskPage.updateDate }">
      <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label">
              标题:<font color="red">*</font>
            </label></td>
          <td class="value"><input id="taskTitle" name="taskTitle" type="text" style="width: 250px" class="inputxt" datatype="*2-100" value='${tPmTaskPage.taskTitle}'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标题</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
          <td class="value"><input type="hidden" value="${tPmTaskPage.id}" id="bid" name="bid">
            <table style="max-width: 92%;">
              <c:forEach items="${tPmTaskPage.attachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmTaskPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid,projectId"
                uploader="commonController.do?saveUploadFiles&businessType=tPmTask" onUploadSuccess="uploadCallback">
              </t:upload>
            </div></td>
        </tr>
      </table>
      <input id="taskNodeListStr" name="taskNodeListStr" type="hidden">
    </t:formvalid>
    <div id="taskNodeList"></div>
  </div>
<script src="webpage/com/kingtake/project/task/tPmTask.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</body>