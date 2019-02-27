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

        $("#saveBtn").click(function() {
            $("#btn_sub").click();
        });
        var auditStatus = $("#auditStatus").val();
        $("#auditBtn").click(function() {
        	var rows = $("#taskNodeList").datagrid("getRows");
        	if(rows.length==0){
        		$.messager.alert("警告","请先添加任务节点，再提交审核！");
        		return false;
        	}
            $("#saveBtn").hide();
            disableForm();
            var id = $("#id").val();
            if (auditStatus == '0' || auditStatus == '3') {
                W.$.dialog({
                    id : 'apprInfo',
                    lock : true,
                    background : '#000', /* 背景色 */
                    opacity : 0.5, /* 透明度 */
                    width : 950,
                    height : window.top.document.body.offsetHeight-100,
                    title : '任务书审核',
                    parent : windowapi,
                    content : 'url:tPmTaskController.do?goAudit&id=' + id,
                    ok : function() {
                    	openSendAudit(id,auditStatus,'<%=ApprovalConstant.APPR_TYPE_TASK%>');
                    	return false;
                    },
                    okVal : "发送审核",
                    cancelVal : '关闭',
                    cancel : true
                });
            } else if (auditStatus == '1'||auditStatus == '2') {
                W.$.dialog({
                    id : 'apprInfo',
                    lock : true,
                    background : '#000', /* 背景色 */
                    opacity : 0.5, /* 透明度 */
                    width : 950,
                    height : window.top.document.body.offsetHeight-100,
                    title : '任务书审核',
                    parent : windowapi,
                    content : 'url:tPmTaskController.do?goAudit&id=' + id,
                    cancelVal : '关闭',
                    cancel : true
                });
            }
        });

        initTab();//加载表格

        if (auditStatus == '1' || auditStatus == '2') {
            //无效化所有表单元素，只能进行查看
            $("#taskTitle").attr("disabled", "true");
            //隐藏添加附件
            $("#filediv").parent().css("display", "none");
            //隐藏附件的删除按钮
            $(".jeecgDetail").parent().css("display", "none");
            //隐藏easyui-linkbutton
            $(".easyui-linkbutton").css("display", "none");
        }
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
		if (location.href.indexOf("load=detail") == -1
				&& (auditStatus == "" || auditStatus == '0' || auditStatus == '3')) {//不包含
			toolbar = [
					{
						iconCls : 'icon-add',
						text : '添加',
						handler : function() {
							var id = $("#id").val();
							if (id == "") {
								$.messager.alert('警告', '请先录入任务书，保存后再录入任务节点');
								return false;
							}
							add("任务节点录入", "tPmTaskController.do?goTaskNodeUpdate&tpId="+id,
									"taskNodeList", 700, 450);
						}
					},{
						iconCls : 'icon-edit',
						text : '编辑',
						handler : function() {
							var checked = $("#taskNodeList").datagrid("getSelections");
					        if(checked.length==0){
						       tip("请选中一行进行编辑！");
						       return false;
					        }
					        createwindow("任务节点编辑","tPmTaskController.do?goTaskNodeUpdate&id="+checked[0].id,700,450);
							
						}
					},
					{
						iconCls : 'icon-remove',
						text : '删除',
						handler : function() {
							var checked = $("#taskNodeList").datagrid(
									"getSelections");
							if(checked.length==0){
								tip("请选中一行进行删除！");
								return false;
							}
							$.messager.confirm('确认','您确认要删除选中的任务节点吗？',function(r){    
							    if (r){    
							        $.ajax({
							        	url:'tPmTaskController.do?doDelTaskNode&id='+checked[0].id,
							        	cache:false,
							        	type:'POST',
							        	dataType:'json',
							        	success:function(data){
							        			tip(data.msg);
							        		if(data.success){
							        			reloadTable();
							        		}
							        	}
							        });
							    }    
							});  
						}
					} ];
		}
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
    <span>
      <c:if test="${empty tPmTaskPage.auditStatus or tPmTaskPage.auditStatus eq '0' or tPmTaskPage.auditStatus eq '3'}">
        <input id="saveBtn" type="button" style="position: fixed; right: 80px; top: 3px;" value="保存任务书">
      </c:if>
      <c:if test="${tPmTaskPage.auditStatus eq '1'}">
        <label style="position: fixed; right: 100px; top: 5px;color:red;">任务书已提交流程，不可修改</label>
      </c:if>
      <c:if test="${tPmTaskPage.auditStatus eq '2'}">
        <label style="position: fixed; right: 100px; top: 5px;color:red;">任务书已完成，不可修改</label>
      </c:if>
      <c:if test="${tPmTaskPage.auditStatus eq '0' or tPmTaskPage.auditStatus eq '1' or tPmTaskPage.auditStatus eq '2' or tPmTaskPage.auditStatus eq '3'  }">
        <input id="auditBtn" type="button" style="position: fixed; right: 3px; top: 3px;" value="发送审核">
      </c:if>
    </span>
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