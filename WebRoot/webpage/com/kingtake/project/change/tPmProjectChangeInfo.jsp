<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant,com.kingtake.project.entity.manage.TPmProjectEntity,com.kingtake.common.constant.SrmipConstants"
  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.tab {
	border-collapse: collapse;
}

.tab td {
	border: solid 1px blue;
	width: 30%;
}
</style>
<title>项目基本信息变更</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    var list;
    $(function() {
        var opt = $("#opt").val();
        if (opt == 'edit') {
            var propertyChangeStr = $("#propertyChangeStr").val();
            list = $.parseJSON(propertyChangeStr);
        } else {
            list = frameElement.api.data.list;
            projectId = frameElement.api.data.projectId;
            $("#projectId").val(projectId);
            $("#propertyChangeStr").val(JSON.stringify(list));
        }
       var showList =[];
       for(var i=0;i<list.length;i++){
           if(list[i].showFlag=='1'){
               showList.push(list[i]);
           }
       }
       $("#changeTab").datagrid("loadData", {
            'rows' : showList,
            'total' : showList.length
        });
        /* var table = $("<table class='tab'></table>");
        $(table).append("<tr><td>字段</td><td>旧值</td><td>新值</td></tr>");
        for (var i = 0; i < list.length; i++) {
            var change = list[i];
            var trStr = "<tr><td>" + change.propertyName + "</td><td>" + getValue(change.oldValue) + "</td><td>"
                    + getValue(change.newValue) + "</td></tr>";
            $(table).append(trStr);
        }
        $("#tabDiv").html(table); */

        var read = $("#read").val();
        if (read == '1') {
            //无效化所有表单元素，只能进行查看
            $(":input").attr("disabled", "true");
            //隐藏选择框和清空框
            $("a[icon='icon-search']").css("display", "none");
            $("a[icon='icon-redo']").css("display", "none");
            $("a[icon='icon-save']").css("display", "none");
            $("a[icon='icon-ok']").css("display", "none");
            //隐藏下拉框箭头
            $(".combo-arrow").css("display", "none");
            //隐藏添加附件
            $("#filediv").parent().css("display", "none");
            //隐藏附件的删除按钮
            $(".jeecgDetail").parent().css("display", "none");
            //隐藏easyui-linkbutton
            $(".easyui-linkbutton").css("display", "none");
        }

    });

    function getValue(value) {
        if (value == undefined) {
            value = "";
        }
        return value;
    }

    function saveCallback(data) {
        $("#bid").val(data.obj.id);
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        } else {
            var win = W.$.dialog.list['processDialog'].content;
            var updateDialog = W.$.dialog.list['projectUpdateDialog'];
            tip(data.msg);
            if (data.success) {
                if (updateDialog != undefined) {
                    updateDialog.content.close();
                }
                win.reloadTable();
                frameElement.api.close();
            }
        }

    }

    function uploadCallback(data) {
        var win = W.$.dialog.list['processDialog'].content;
        var dialog = W.$.dialog.list['projectUpdateDialog'];
            if (dialog != undefined) {
                dialog.content.close();
            }
            win.reloadTable();
            frameElement.api.close();
            win.tip("保存成功!");
    }
</script>
</head>
<body>
  <div class="easyui-layout" fit="true">
    <div id="formDiv" data-options="region:'north'" title="变更申请" style="height: 300px;">
      <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBPmProjectChangeController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
        <input id="id" name="id" type="hidden" value="${projectChangePage.id }">
        <input id="projectId" name="projectId" type="hidden" value="${projectChangePage.projectId}">
        <input id="projectName" name="projectName" type="hidden" value="${projectChangePage.projectName}">
        <input id="changeTime" name="changeTime" type="hidden" value="${projectChangePage.changeTime }">
        <input id="createBy" name="createBy" type="hidden" value="${projectChangePage.createBy }">
        <input id="createName" name="createName" type="hidden" value="${projectChangePage.createName }">
        <input id="createDate" name="createDate" type="hidden" value="${projectChangePage.createDate }">
        <input id="updateBy" name="updateBy" type="hidden" value="${projectChangePage.updateBy }">
        <input id="updateName" name="updateName" type="hidden" value="${projectChangePage.updateName }">
        <input id="updateDate" name="updateDate" type="hidden" value="${projectChangePage.updateDate }">
        <input id="bpmStatus" name="bpmStatus" type="hidden" value="${projectChangePage.bpmStatus }">
        <textarea id="propertyChangeStr" cols="1" rows="1" style="display: none;" name="propertyChangeStr">${propertyChangeStr}</textarea>
        <input id="opt" type="hidden" value="${opt}">
        <input id="read" type="hidden" value="${read}">
        <table cellpadding="0" cellspacing="1" style="margin: auto;">
          <tr>
            <td align="right"><label class="Validform_label">
                变&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因:<font color="red">*</font>
              </label></td>
            <td class="value"><textarea id="changeReason" name="changeReason" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="400" min="1" rows="5">${projectChangePage.changeReason}</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更原因</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                变&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;&nbsp;依&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据:<font color="red">*</font>
              </label></td>
            <td class="value"><input id="changeAccording" name="changeAccording" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="100" min="1"
                value='${projectChangePage.changeAccording}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更依据</label></td>
          </tr>

          <tr>
            <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
            <td class="value"><input type="hidden" value="${projectChangePage.id}" id="bid" name="bid" />
              <table style="max-width: 450px;">
                <c:forEach items="${projectChangePage.certificates }" var="file" varStatus="idx">
                  <tr>
                    <td><a href="javascript:void(0)" title="${file.attachmenttitle}"
                        onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBPmChangeProjectmanagerPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">
                        ${file.attachmenttitle} </a>&nbsp;&nbsp;&nbsp;</td>
                    <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                    <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                  </tr>
                </c:forEach>
              </table>
              <div>
                <div class="form" id="filediv"></div>
                <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" dialog="false" buttonText="添加文件" formData="bid,projectId"
                  uploader="commonController.do?saveUploadFiles&businessType=tBPmChangeProject"  callback="uploadCallback">
                </t:upload>
              </div></td>
          </tr>
        </table>
      </t:formvalid>
    </div>
    <div id="tabDiv" data-options="region:'center'" title="项目变更明细">
      <table class="easyui-datagrid" id="changeTab"
			data-options="singleSelect:true,rownumbers:true,fitColumns:true,striped:true,pagination:false,fit:true,nowrap:false,idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'id',width:80,hidden:true">id</th>
				<th data-options="field:'propertyDesc',width:80">字段名</th>
				<th data-options="field:'oldValue',width:100">旧值</th>
				<th data-options="field:'newValue',width:80">新值</th>
			</tr>
		</thead>
	</table>
    </div>
  </div>

</body>