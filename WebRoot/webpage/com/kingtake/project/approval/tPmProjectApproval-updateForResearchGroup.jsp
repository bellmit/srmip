<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>论证报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    $(function() {
        $("#saveBtn").click(function() {
            $("#btn_sub").click();
        });

        var projId = $("#tpId").val();
         if(projId==""){
             tip("当前还没有项目，请先新建项目!");
        } 
    });
    function uploadFile(data) {
        tip(data.msg);
        $("#bid").val(data.obj.id);
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        }
        //window.location.reload();
    }
     
    function show_hide(id,text)
    {
    	if(document.all(id).style.display=="")
    	{
    		document.all(id).style.display = "none";
    		document.all(text).innerHTML='点击显示';
    		return;
    	}
    	document.all(id).style.display = "";
    		document.all(text).innerHTML='点击隐藏';
    }
    
    //上传文件成功后刷新
    function refresh(){
        window.location.reload();
    }
</script>
</head>
<body>
<div style="width:820px;">
  <div
    style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; ">
    <h1 align="center">${tPmProjectPage.projectName}-立项论证</h1>
    <span><input id="saveBtn" type="button"
      style="position: fixed; right: 5px; top: 3px;" value="保存论证信息"></span>
  </div>
  <br>
  <br>
  <fieldset style="border-color:#F5F5F5;">
  <legend onclick="show_hide('baseData','showTxt')"><span>项目基本信息[<label id='showTxt'>点击隐藏</label>]</span></legend>
  <div id="baseData">
  <table style="width:760px;margin-left:20px;" cellpadding="0" cellspacing="0" >
      <tr>
          <td align="right"><label class="Validform_label"> 项目名称:</label></td>
          <td class="value" colspan="5">${tPmProjectPage.projectName}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label>
          </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 项目编号: </label></td>
          <td class="value">${tPmProjectPage.projectNo}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label>
          </td>
          <td align="right"><label class="Validform_label"> 分&nbsp;管&nbsp;部&nbsp;门: </label></td>
          <td class="value">${tPmProjectPage.manageDepart}
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">分管部门</label>
          </td>
          <td align="right"><label class="Validform_label"> 所属母项目: </label></td>
          <td class="value">
              <input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}">
              ${tPmProjectPage.parentPmProject.projectName}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属母项目</label>
          </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 负&nbsp;责&nbsp;人&nbsp;:</label></td>
          <td class="value">${tPmProjectPage.projectManager}
              <span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">负责人</label>
          </td>
          <td align="right"><label class="Validform_label"> 负责人电话:</label>
          </td>
          <td class="value">${tPmProjectPage.managerPhone}
              <span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">负责人电话</label>
          </td>
          <td align="right"><label class="Validform_label"> 起&nbsp;止&nbsp;日&nbsp;期:</label></td>
          <td class="value">
              <fmt:formatDate value='${tPmProjectPage.beginDate}' type="date" pattern="yyyy-MM-dd"/><label class="Validform_label"> 至  </label>
              <fmt:formatDate value='${tPmProjectPage.endDate}' type="date" pattern="yyyy-MM-dd"/>
          </td>
      </tr>
      </table>
  </div>
  </fieldset>
  <fieldset style="border-color:#F5F5F5;">
  <legend onclick="show_hide('memberData','memberTxt')"><span>项目成员信息[<label id='memberTxt'>点击隐藏</label>]</span></legend>
  <div id="memberData">
  <table cellpadding="0" cellspacing="0" style="text-align:left;width:740px;margin-left:42px;">
  <tr height="30" style="color: #5E7595;">
  <th style="width: 25%;">姓名</th><th style="width: 25%;">单位</th><th style="width: 25%;">职称</th><th style="width: 25%;">分工</th>
  </tr>
  <c:forEach items="${memberList}" var="m">
  <tr>
  	<td class="value">${m.name}</td>
  	<td class="value">${m.superUnitName}</td>
  	<td class="value">
  		<c:if test='${not empty m.position}'>
	  		<t:convert codeType="0" code="PROFESSIONAL" value="${m.position}"></t:convert>
  		</c:if>
  	</td>
  	<td class="value">${m.taskDivide}</td>
  </tr>
  </c:forEach>
  </table>
  </div>
  </fieldset>
  <fieldset style="border-color:#F5F5F5;">
  <legend><span>论证信息</span></legend>
  
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" postonce="false"
    action="tPmProjectApprovalController.do?doUpdate" tiptype="showValidMsg" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmProjectApprovalPage.id }">
    <input id="projectId" name="tpId" type="hidden" value="${tPmProjectApprovalPage.project.id}">
    <input id="projectName" name="projectName" type="hidden" value="${tPmProjectApprovalPage.project.projectName}">
    <input id="createBy" name="createBy" type="hidden" value="${tPmProjectApprovalPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmProjectApprovalPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmProjectApprovalPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmProjectApprovalPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmProjectApprovalPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmProjectApprovalPage.updateDate }">
    <table cellpadding="0" cellspacing="1" style="width:700px;margin-left:30px;">
      <tr>
        <td align="right"><label class="Validform_label"> 建&nbsp;议&nbsp;等&nbsp;级&nbsp;: <font color="red">*</font></label></td>
        <td class="value">
        <t:codeTypeSelect name="suggestGrade" type="select" codeType="1" code="SUGGESTGRADE" id="suggestGrade" defaultVal="${tPmProjectApprovalPage.suggestGrade}" ></t:codeTypeSelect>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">建议等级</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 建&nbsp;议&nbsp;单&nbsp;位&nbsp;: <font color="red">*</font></label></td>
        <td class="value"><input id="suggestUnit" name="suggestUnit" type="text" style="width: 150px"
          class="inputxt" datatype="*1-30" value='${tPmProjectApprovalPage.suggestUnit}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">建议单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研&nbsp;究&nbsp;时&nbsp;限&nbsp;: <font color="red">*</font></label></td>
        <td class="value"><input id="studyTime" name="studyTime" type="text" datatype="*1-25" style="width: 150px"
          class="inputxt" value='${tPmProjectApprovalPage.studyTime}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">研究时限</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 项&nbsp;目&nbsp;来&nbsp;源&nbsp;: <font color="red">*</font></label></td>
        <td class="value"><input id="projectSrc" name="projectSrc" type="text" style="width: 150px" class="inputxt"
          value='${tPmProjectApprovalPage.projectSrc}' datatype="*1-50"> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">项目来源</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研究的必要性: </label></td>
        <td class="value"><textarea id="studyNecessity" style="width: 500px;" class="inputxt" rows="6"
            name="studyNecessity" datatype="*0-500">${tPmProjectApprovalPage.studyNecessity}</textarea> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">研究的必要性</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 国内外军内外&nbsp;<br>有关情况分析: </label></td>
        <td class="value"><textarea id="situationAnalysis" style="width: 500px;" class="inputxt" rows="6"
            name="situationAnalysis" datatype="*0-1000">${tPmProjectApprovalPage.situationAnalysis}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">国内外军内外有关情况分析</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 主要研究内容: </label></td>
        <td class="value"><textarea id="studyContent" style="width: 500px;" class="inputxt" rows="6"
            name="studyContent" datatype="*0-1000">${tPmProjectApprovalPage.studyContent}</textarea> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主要研究内容</label></td>
      </tr>
      <tr>
        <td align="right">
        	<label class="Validform_label"> 
        		研&nbsp;究&nbsp;进&nbsp;度&nbsp;与<br>成&nbsp;&nbsp;果&nbsp;&nbsp;形&nbsp;&nbsp;式:
        	</label>
        </td>
        <td class="value"><textarea id="scheduleAndAchieve" style="width: 500px;" class="inputxt" rows="6"
            name="scheduleAndAchieve" datatype="*0-1000">${tPmProjectApprovalPage.scheduleAndAchieve}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究进度与成果形式</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件: </label></td>
        <td colspan="3" class="value">
          <input type="hidden" value="${tPmProjectApprovalPage.id}" id="bid" name="bid">
          <table style="max-width:600px;">
            <c:forEach items="${tPmProjectApprovalPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);"
                  onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmProjectApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width:40px;"><a
                  href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"
                  title="下载">下载</a></td>
                <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail"
                  onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div class="form" id="filediv"></div>
		  <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" 
			buttonText="添加文件" formData="bid,projectId" uploader="commonController.do?saveUploadFiles&businessType=projectApproval" 
			dialog="false" callback="refresh">
          </t:upload>
         </td>
      </tr>
    </table>
  </t:formvalid>
  </fieldset>
  </div>
</body>
<script src="webpage/com/kingtake/project/approval/tPmProjectApproval.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>