<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>任务管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.Wdate {
	height: 14px;
}
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $('#tt').tabs({
            onSelect : function(title) {
                $('#tt .panel-body').css('width', 'auto');
            }
        });
        $(".tabs-wrap").css('width', '100%');

        var projId = $("#projectId").val();
        if (projId == "") {
            tip("当前还没有项目，请先新建项目!");
        }

        var auditStatus = $("#auditStatus").val();

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
    
    function disableForm(){
        //无效化所有表单元素，只能进行查看
        $("#taskTitle").attr("disabled", "true");
        //隐藏添加附件
        $("#filediv").parent().css("display", "none");
        //隐藏附件的删除按钮
        $(".jeecgDetail").parent().css("display", "none");
        //隐藏easyui-linkbutton
        $(".easyui-linkbutton").css("display", "none");
        $(".icon-add").hide();
        $(".icon-remove").hide();
        $(".icon-edit").hide();
    }

    
    //刷新
	function refresh(data) {
		tip(data.msg);
		if (data.success) {
			window.location.reload();
		}
	}

	//上传成功后，刷新
	function uploadCallback() {
		window.location.reload();
	}

	//初始化成员表格
	function initTab() {
		var toolbar = [];
		var auditStatus = $("#auditStatus").val();
		$('#taskNodeList').datagrid({
							title : '任务节点',
							fitColumns : true,
							nowrap : true,
							height : 300,
							striped : true,
							remoteSort : false,
							idField : 'id',
							editRowIndex : -1,
							singleSelect : true,
							toolbar : toolbar,
							onDblClickRow:function(rowIndex, rowData){
								createdetailwindow("任务节点查看","tPmTaskController.do?goTaskNodeUpdate&load=detail&id="+rowData.id,700,450);
							},
							columns : [ [
									{
										field : 'id',
										title : 'id',
										width : 100,
										hidden : true
									},
									{
										field : 'taskNodeName',
										title : '任务节点名称',
										width : 100
									},
									{
										field : 'planTimeStr',
										title : '开始时间',
										width : 100
									},
									{
										field : 'endTimeStr',
										title : '计划完成时间',
										width : 100
									},
									{
										field : 'taskContent',
										title : '任务内容',
										width : 150
									},
									{
										field : 'finishFlag',
										title : '是否完成',
										width : 80,
										formatter : function(value, row, index) {
											if (value == "1") {
												return "是";
											} else {
												return "否";
											}
										}
									},
									{
										field : 'finishTimeStr',
										title : '完成时间',
										width : 100,
										fomatter : function(value, row, index) {
											return "${tPmTaskPage.finishTime }"
										}
									},
									{
										field : 'opt',
										title : '查看完成情况',
										width : 150,
										formatter : function(value, row, index) {
											var id = row.id;
											if (id != null
													&& row.finishFlag == '1') {
												return "<a href=\"#\" class=\"jeecgDetail\" style=\"text-decoration: underline;color: blue; \" onclick=\"openFinishWindow('"
														+ id
														+ "');\">查看完成情况说明</a>";
											} else {
												return "";
											}

										}
									} ] ],
							onBeforeEdit : function(rowIndex, rowData) {

							},
							onAfterEdit : function(rowIndex, rowData) {

							},
							pagination : false,
							rownumbers : true,
							onLoadSuccess : function() {
								var auditStatus = $("#auditStatus").val();
								if (auditStatus == '1') {
									$("a.jeecgDetail").hide();
									$('div.datagrid-toolbar').hide();
								}
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
	
	function reloadTable(){
		loadData();
	}

	//更新审核状态
	function updateTaskFinishFlag(apprId) {
		var url1 = 'tPmTaskController.do?updateFinishFlag';
		var url2 = url1 + '2';
		updateFinishFlag(apprId, url1, url2);
	}

	//完成或取消完成后，刷新界面
	function reloadTable() {
		var win = frameElement.contentWindow;
		frameElement.contentWindow.location.reload();
	}

	function loadButton() {

	}
</script>
</head>
<body>
<div  style="height: 480px;">
  <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
    <h1 align="center" style="line-height: 100%;">${tPmTaskPage.project.projectName }-任务书及节点申请审核 </h1>
  </div>
  <br>
  <br>
  <input id="auditStatus" type="hidden" value="${tPmTaskPage.auditStatus}">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tPmTaskController.do?doAddUpdate" callback="@Override refresh" >
    <input id="id" name="id" type="hidden" value="${tPmTaskPage.id }">
    <input id="projectId" name="project.id" type="hidden" value="${tPmTaskPage.project.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmTaskPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmTaskPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmTaskPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmTaskPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmTaskPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmTaskPage.updateDate }">
    <table style="margin: 0 auto;width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            标题:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="taskTitle" name="taskTitle" type="text" style="width: 250px" class="inputxt" datatype="*2-100" value='${tPmTaskPage.taskTitle}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
      <td align="right">
          <label class="Validform_label"> 审核状态: </label>
        </td>
        <td class="value">
         <t:codeTypeSelect code="SPZT" codeType="1" id="" name="" type="select" defaultVal="${tPmTaskPage.auditStatus }" extendParam="{'disabled':'true'}" ></t:codeTypeSelect>
         <input id="auditStatus" name="auditStatus" value="${tPmTaskPage.auditStatus }" type="hidden" />
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">审核状态</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td class="value"><input type="hidden" value="${tPmTaskPage.attachmentCode}" id="bid" name="attachmentCode">
          <table style="max-width: 92%;" id="fileShow">
            <c:forEach items="${tPmTaskPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件"  auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
		    	formData="bid,projId" uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmTask" >
            </t:upload>
          </div></td>
      </tr>
    </table>
    <!-- <input id="taskNodeListStr" name="taskNodeListStr" type="hidden"> -->
  </t:formvalid>
  <div align="center" style="width:900px;margin:0 auto;">
    <table id="taskNodeList" ></table>
  </div>
  </div>
</body>
<script src="webpage/com/kingtake/project/task/tPmTask.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>