<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>支付节点管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPayNodeController.do?doUpdate" 
  	tiptype="1" tipSweep="true" callback="@Override uploadFile">
					<input id="id" name="id" type="hidden" value="${tPmPayNodePage.id }">
					<input id="tpId" name="tpId" type="hidden" value="${tPmPayNodePage.tpId}">
					<input id="createBy" name="createBy" type="hidden" value="${tPmPayNodePage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmPayNodePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmPayNodePage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmPayNodePage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmPayNodePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmPayNodePage.updateDate }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="payTime" name="payTime" type="text" style="width: 150px" datatype="date"	class="Wdate"
							value='<fmt:formatDate value="${tPmPayNodePage.payTime}" type="date" pattern="yyyy-MM-dd" />'>    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">支付时间</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							支付金额:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="payAmount" name="payAmount" type="text"
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
							data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" 
				     		value="${tPmPayNodePage.payAmount}">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">支付金额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付条件:
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="payCondition" name="payCondition" style="width:438px;height:80px;"
				     		datatype="*0-200" ignore="ignore">${tPmPayNodePage.payCondition}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">支付条件</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="memo" name="memo" style="width:438px;height:80px;"
				     		datatype="*1-150" ignore="ignore">${tPmPayNodePage.memo}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;
						</label>
					</td>
      				<td colspan="3" class="value">
      					<input type="hidden" value="${tPmPayNodePage.id}" id="bid" name="bid" />
				        <table style="max-width:438px;">
				            <c:forEach items="${tPmPayNodePage.certificates}" var="file" varStatus="idx">
					            <tr style="height: 30px;">
						            <td><a href="javascript:void(0);"
						              onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmPayNodePage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
						            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
						            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
					            </tr>
				            </c:forEach>
				        </table>
						<div>
							<div class="form" id="filediv"></div>
							<t:upload name="fiels" id="file_upload"   extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
								formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tPmPayNode"></t:upload>
						</div>
      				</td>
    			</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
  <script src = "webpage/com/kingtake/project/m2pay/tPmPayNode.js"></script>		