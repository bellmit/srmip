<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>差旅-出差报告信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
   .headTitle{
      background-color:#F2F7FF;
      text-align:center;
      height:50px
   }
   
   .headLable{
      font-size: 20px;
      font-weight: bold;
      color: #5E7595;
   }
   
  </style>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript" src="webpage/com/kingtake/office/travel/tOTravelReport.js"></script>
  <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOTravelReportController.do?doAddUpdate"
   tiptype="1"  callback="@Override uploadFile">
	   <input id="id" name="id" type="hidden" value="${tOTravelReportPage.id }" >
	   <input id="toId" name="toId" type="hidden" value="${toId}">
	   <table  cellpadding="0" cellspacing="1" class="mytable">
          <tr>
            <td class="headTitle" colspan="6" ><label class="headLable">出差情况阅批单</label></td>
          </tr>
		  <tr>
              <td align="right"><label class="Validform_label"> 起&nbsp;止&nbsp;时&nbsp;间: </label></td>
              <td class="value">
                  <input id="startTime" name="startTime" type="text" style="width: 150px" class="Wdate" 
                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'endTime\')}'})"
                  value='<fmt:formatDate value='${tOTravelReportPage.startTime}' type="date" pattern="yyyy-MM-dd"/>'> 
                  <label class="Validform_label">  ~ </label>
                  <input id="endTime" name="endTime" type="text" style="width: 150px" class="Wdate" 
                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startTime\')}'})"
                value='<fmt:formatDate value='${tOTravelReportPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'> 
              <span class="Validform_checktip"></span> 
              <label class="Validform_label" style="display: none;">截止时间</label>
              </td>
          </tr>
          <tr>
              <td align="right"><label class="Validform_label"> 参&nbsp;加&nbsp;人&nbsp;员: </label></td>
              <td class="value">
                <input type="hidden" id="relateUserId" > 
                <input id="relateUsername" name="relateUsername" value='${tOTravelReportPage.relateUsername}' 
                style="width: 400px" class="inputxt" readonly="readonly"></input> 
                <t:chooseUser idInput="opinionUserid" url="commonUserController.do?commonUser" 
                tablename="selectedUserList" icon="icon-search" title="人员列表" textname="id,realName"
                 inputTextname="relateUserId,relateUsername" isclear="true"></t:chooseUser>
                 <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">参加人员</label>
              </td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点:</label></td>
            <td class="value">
            <input id="address" name="address" type="text" style="width: 400px" class="inputxt"
              value='${tOTravelReportPage.address}' maxLength="150" > <span class="Validform_checktip"></span> 
              <label class="Validform_label" style="display: none;">地点</label></td>
          </tr>
          <tr>
    		  <td align="right"><label class="Validform_label">出&nbsp;差&nbsp;事&nbsp;由:</label></td>
    		  <td class="value">
    			 <textarea id="travelReason" style="width: 400px;" maxLength="500"  class="inputxt" rows="4"  name="travelReason">${tOTravelReportPage.travelReason}</textarea>
    			 <span class="Validform_checktip"></span>
    			 <label class="Validform_label" style="display: none;">出差事由</label>
    		  </td>
          </tr>
          <tr>
             <td align="right"><label class="Validform_label"> 校首长阅批: </label></td>
             <td class="value">
                <textarea id="chiefApproval" class="inputxt" rows="4" style="width: 400px;" maxLength="500"
                name="chiefApproval">${tOTravelReportPage.chiefApproval}</textarea> 
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">校首长阅批</label>
             </td>
          </tr>
          <tr>
             <td align="right"><label class="Validform_label"> 部领导阅批: </label></td>
             <td class="value">
                <textarea id="departApproval" class="inputxt" rows="4" style="width: 400px;" maxLength="500"
                name="departApproval">${tOTravelReportPage.departApproval}</textarea> 
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">部领导阅批</label>
             </td>
          </tr>
          <tr>
             <td align="right"><label class="Validform_label"> 处领导阅批: </label></td>
             <td class="value">
                <textarea id="sectionApproval" class="inputxt" rows="4" style="width: 400px;" maxLength="500"
                name="sectionApproval">${tOTravelReportPage.sectionApproval}</textarea> 
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">处领导阅批</label>
             </td>
          </tr>
          <tr>
             <td align="right"><label class="Validform_label"> 传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;阅: </label></td>
             <td class="value">
                <textarea id="circelRead" style="width: 400px;" maxLength="500" class="inputxt" rows="4" name="circelRead">${tOTravelReportPage.circelRead}</textarea>
                <span class="Validform_checktip"></span> 
                <label class="Validform_label" style="display: none;">传阅</label>
             </td>
          </tr>
          <tr>
             <td align="right"><label class="Validform_label"> 备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注: </label></td>
             <td class="value">
                <textarea id="remark" style="width: 400px;" maxLength="200" class="inputxt" rows="4" name="remark">${tOTravelReportPage.remark}</textarea>
                <span class="Validform_checktip"></span> 
                <label class="Validform_label" style="display: none;">备注</label>
             </td>
          </tr>
          <tr>
          <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:</label></td>
          <td  class="value"><input type="hidden" value="${tOTravelReportPage.id}" id="bid" name="bid" />
	          <table style="max-width:400px;">
	            <c:forEach items="${tOTravelReportPage.certificates}" var="file" varStatus="idx">
	              <tr style="height: 30px;">
	                <td><a href="javascript:void(0);"
	                  onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOTravelReportPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
	                <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
	                <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
	              </tr>
	            </c:forEach>
	          </table>
	          <div>
	          <div class="form" id="filediv"></div>
	          <t:upload queueID="filediv" name="fiels" id="file_upload"
	             buttonText="添加文件" formData="bid"
	            uploader="commonController.do?saveUploadFiles&businessType=tOTravelReport">
	          </t:upload>
	          </div>
          </td>
        </tr>
    </table>
    <div class="form" style="text-align: right;padding:10px;">
        <label class="Validform_label"> 报告人: </label>
        <lable style="padding-right: 20px">${leaveName}</lable>
        <label class="Validform_label"> 报告提交日期: </label>
        <c:choose>
          <c:when test="${empty tOTravelReportPage.submitTime}">  
                <input id="submitTime" name="submitTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
                 value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'> 
          </c:when>
          <c:otherwise>
                <input id="submitTime" name="submitTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
                 value='<fmt:formatDate value='${tOTravelReportPage.submitTime}' type="date" pattern="yyyy-MM-dd"/>'> 
          </c:otherwise>
        </c:choose>
        <!-- <span class="Validform_checktip"></span>  -->
        <label class="Validform_label" style="display: none;">提交日期</label>
    </div>
  </t:formvalid>
 </body>
 </html>