<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目申报</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
function checkData(){
    var beginDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    if(beginDate>endDate){
        //tip("起始日期不能大于结束日期!");
        $.Showmsg("开始时间不能大于结束时间!");
        return false;
    }
}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
    action="tPmDeclareController.do?doAddUpdate" tiptype="1" beforeSubmit="checkData">
    <input id="id" name="id" type="hidden" value="${tPmDeclarePage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmDeclarePage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmDeclarePage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmDeclarePage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmDeclarePage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmDeclarePage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmDeclarePage.updateDate }">
    <table  cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width:25%;" align="right"><label class="Validform_label"> 研究时限开始时间:<font color="red">*</font>
        </label></td>
        <td style="width:75%;" class="value"><input id="beginDate" name="beginDate" type="text" style="width: 150px" class="Wdate"
          onClick="WdatePicker()" datatype="date"
          value='<fmt:formatDate value='${tPmDeclarePage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究时限开始时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研究时限结束时间:<font color="red">*</font>
        </label></td>
        <td class="value"><input id="endDate" name="endDate" type="text" style="width: 150px" class="Wdate"
          onClick="WdatePicker()" datatype="date"
          value='<fmt:formatDate value='${tPmDeclarePage.endDate}' type="date" pattern="yyyy-MM-dd"/>'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究时限结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 项目来源: <font color="red">*</font></label></td>
        <td class="value"><input id="projectSrc" name="projectSrc" type="text" style="width: 150px" class="inputxt"
          datatype="*2-100" value='${tPmDeclarePage.projectSrc}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">项目来源</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 所属项目: <font color="red">*</font></label></td>
        <td class="value"><input type="hidden" id="tpId" name="tpId" datatype="*" value="${tPmDeclarePage.project.id}">
          <input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt"
          readonly="readonly" value="${tPmDeclarePage.project.projectName}"> <t:choose
            url="tPmProjectController.do?projectSelect" name="projectList" icon="icon-search" title="项目列表" width="1000px" height="460px" left="10%" top="10%"
            textname="id,projectName" inputTextname="tpId,projectName" isclear="true"></t:choose> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属项目</label></td>
      </tr>
      <input type="hidden" name="bpmStatus" value='<c:if test="${empty tPmDeclarePage.bpmStatus }">1</c:if><c:if test="${!empty tPmDeclarePage.bpmStatus }">${tPmDeclarePage.bpmStatus}</c:if>'>
      <tr>
        <td align="right"><label class="Validform_label"> 主要研究内容:<font color="red">*</font>
        </label></td>
        <td class="value"><textarea id="researchContent" name="researchContent" rows="4" style="width:75%;"
          class="inputxt" datatype="*2-1000"  >${tPmDeclarePage.researchContent}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">主要研究内容</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研究进度及成果形式:<font color="red">*</font>
        </label></td>
        <td class="value"><textarea id="scheduleAndAchieve" name="scheduleAndAchieve" rows="4" style="width:75%;"
          class="inputxt" datatype="*2-1000"  >${tPmDeclarePage.scheduleAndAchieve}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究进度及成果形式</label></td>
      </tr>
      
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/declare/tPmDeclare.js"></script>