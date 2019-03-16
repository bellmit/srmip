<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>计划下达录入信息</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript">
        function batchImportProject() {
            var formData = new FormData();
            formData.append("myfile", document.getElementById("file").files[0]);
            $.ajax({
                url: "tPmProjectPlanController.do?batchImportProject",
                type: "POST",
                data: formData,
                /**
                 *必须false才会自动加上正确的Content-Type
                 */
                contentType: false,
                /**
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 */
                processData: false,
                success: function (result) {
                    $("#fileName").html("上传成功");
                    $("#uploadFileName").val(result);
                },
                error: function () {

                }
            });
        }

    </script>
</head>
<body>
<div style="">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tPmProjectPlanController.do?doSave" tiptype="1">
        <input id="id" name="id" type="hidden" value="${tPmProjectPlanPage.id }">
        <table id="documentInfo" style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right" width="80px"><label class="Validform_label"> 文件号: </label> <font color="red">*</font>
                </td>
                <td class="value">
                    <input id="documentNo" name="documentNo" type="text" datatype="*1-50" style="width: 250px"
                           value='${tPmProjectPlanPage.documentNo}'>
                    <span class="Validform_checktip"></span> <label class="Validform_label"
                                                                    style="display: none;">文件号</label></td>
                <td align="right" width="80px"><label class="Validform_label"> 文件名: </label> <font color="red">*</font>
                </td>
                <td class="value">
                    <input id="documentName" name="documentName" type="text" datatype="*1-100" style="width: 250px"
                           value='${tPmProjectPlanPage.documentName}'>
                    <span class="Validform_checktip"></span> <label class="Validform_label"
                                                                    style="display: none;">文件名</label></td>
            </tr>
            <tr>
                <td align="right" width="80px"><label class="Validform_label"> 发文时间: </label><font color="red">*</font>
                </td>
                <td class="value"><input id="documentTime" name="documentTime" type="text" datatype="date"
                                         style="width: 250px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmProjectPlanPage.documentTime}'
                  type="date" pattern="yyyy-MM-dd"/>'> <span
                        class="Validform_checktip"></span> <label class="Validform_label"
                                                                  style="display: none;">发文时间</label></td>
                <td align="right" width="80px"><label class="Validform_label">年度:<font color="red">*</font></label></td>
                <td class="value"><input id="planYear" name="planYear" type="text" style="width: 250px" class="inputxt"
                                         datatype="/[0-9]{1,4}/" errormsg="请输入1-4位数字"
                                         value='${tPmProjectPlanPage.planYear}'><img alt="" title=""
                                                                                     src="plug-in\easyui1.4.2\themes\icons\tip.png">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">年度</label>
                </td>
            </tr>
            <tr style="display: none;">
                <td align="right"><label class="Validform_label"> 来源经费科目: </label><font color="red">*</font></td>
                <td class="value"><input id="fundsSubject" name="fundsSubject" type="text" style="width: 250px"
                                         value='${tPmProjectPlanPage.fundsSubject}'> <span
                        class="Validform_checktip"></span> <label class="Validform_label"
                                                                  style="display: none;">来源经费科目</label></td>
                <td align="right" width="120px"><label class="Validform_label"> 金额(元): </label> <font
                        color="red">*</font></td>
                <td class="value"><input id="amount" name="amount" type="text" style="width: 250px; text-align: right;"
                                         class="easyui-numberbox"
                                         data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" min="1"
                                         value='${tPmProjectPlanPage.amount}'> <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">金额</label></td>
            </tr>
            <tr>
                <td align="right" width="120px"><label class="Validform_label"> 项目批量导入: </label></td>
                <td class="value">
                    <input id="file" type="file" name="file">
                    <input type="button" value="上传" onclick="batchImportProject();"/>
                </td>
            </tr>
            <tr>
                <td align="right" width="120px"><label class="Validform_label"></label></td>
                <td><div id ="fileName"></div><input id="uploadFileName" name="uploadFileName" type="text" style="display: none;" class="inputxt"></td>
            </tr>
        </table>
    </t:formvalid>
</div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>