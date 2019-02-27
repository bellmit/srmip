<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>军内科研项目申报书信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%@ include file="/webpage/com/kingtake/common/toastr/toastr.jsp"%>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/declare/army/tBPmDeclareArmyResearch.js"></script>
</head>
<style>
/* .panel-body {
	border-width: 0px;
	border-style: none;
} */
</style>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<body>
  <input id="read" value=${read } type="hidden" />
  <div style="height: 1800px;">
    <div title="${research.projectName }：申报书基本信息">
      <div style="padding: 5px 0; background-color: #f4f4f4;">
        <table style="width: 765px; margin: 0 auto;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td align="center" bgcolor="#E5EFFF"><b>${research.projectName }：申报书基本信息</b><a href="#" onclick="toastrMsg('armyDeclare')" style="color: red">(查看说明)</a></td>
            <td width="25%" bgcolor="#E5EFFF" align="right"><c:if test="${read eq '0' }">
                <a id="saveBtn" class="easyui-linkbutton" plain="true" title="保存申报书" onclick="saveBaseInfo();">保存</a>
              </c:if> <c:if test="${!empty research.id && research.bpmStatus eq '1' && read eq '0' }">
                <a id="commitBtn" class="easyui-linkbutton" plain="true" onclick="startProcess('t_b_pm_declare_army_research');">提交流程</a>
              </c:if> <c:if test="${research.bpmStatus eq '5' && read eq '0' }">
                <a href="#" class="easyui-linkbutton" plain="true" title="修改完成后重新提交流程" onclick="compeleteProcess('army');">修改提交</a>
                <a href="#" class="easyui-linkbutton" plain="true" title="查看历史" onclick="viewRemark();">查看历史</a>
              </c:if> <c:if test="${research.planStatus eq '2' && read eq '0' }">
                <a href="#" class="easyui-linkbutton" plain="true" title="修改完成后重新提交" onclick="compeleteProcess();">修改提交</a>
                <a href="#" class="easyui-linkbutton" plain="true" title="查看计划草案驳回意见" onclick="viewMsgText('${research.id}');">查看驳回意见</a>
              </c:if> <c:if test="${research.bpmStatus ne '1' and research.bpmStatus ne '5' and research.planStatus ne '2' and research.bpmStatus ne '6' and opt ne 'audit'}">
                <a id="viewBtn" class="easyui-linkbutton" plain="true" onclick="viewHistory('${research.processInstId }');">查看流程</a>
              </c:if></td>
          </tr>
        </table>
        <t:formvalid formid="baseInfo" dialog="true" usePlugin="password" layout="table" tiptype="showValidMsg" action="tBPmDeclareArmyResearchController.do?doUpdate" callback="@Override saveCallback">
          <input id="id" name="id" type="hidden" value="${research.id }" />
          <input id="processInsId" type="hidden" value="${processInstId }" />
          <input id="taskId" type="hidden" value="${taskId }" />
          <input id="tableName" type="hidden" />
          <table style="width: 765px; background-color: white; margin: 0 auto; position: relative;" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
              <td align="right"><label class="Validform_label">项目编号:</label></td>
              <td class="value"><input id="projectNo" name="projectNo" value="${research.projectNo }" style="width: 150px;" type="text" class="inputxt" readonly="readonly" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label></td>
              <td align="right"><label class="Validform_label">
                  项目类别: <font color="red">*</font>
                </label></td>
              <td class="value"><t:codeTypeSelect name="projectCategory" type="select" codeType="1" code="JNKYSBSXMLB" id="projectCategory" defaultVal="${research.projectCategory }"
                  extendParam="{'style':'width:155px;','datatype':'*'}" labelText="请选择"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目类别</label>
              </td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">项目名称:</label></td>
              <td class="value" colspan="3"><input id="projectId" name="tpId" type="hidden" value="${research.tpId }" /> <input id="projectName" name="projectName" value="${research.projectName }"
                  style="width: 575px;" type="text" class="inputxt" readonly="readonly" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label>
              </td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  项目批次: <font color="red">*</font>
                </label></td>
              <td class="value">
              <select id="projectGroup" name="projectGroup"  class="inputxt" style="width: 155px;" >
              <option value="1" <c:if test="${research.projectGroup eq '1' }">selected="selected"</c:if>>1</option>
              <option value="2" <c:if test="${research.projectGroup eq '2' }">selected="selected"</c:if>>2</option>
              <option value="3" <c:if test="${research.projectGroup eq '3' }">selected="selected"</c:if>>3</option>
              </select>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目批次</label></td>
              <td align="right"><label class="Validform_label">项目类型:</label></td>
              <td class="value"><input name="projectType.id" value="${research.projectType.id }" type="hidden" /> <input value="${research.projectType.projectTypeName }" type="text"
                  class="inputxt" readonly="readonly" style="width: 150px;" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目类型</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  申报单位: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="reportDepartId" name="reportDepartId" type="hidden" value="${research.reportDepartId }" /> <input id="reportDepartName" name="reportDepartName"
                  type="hidden" value="${research.reportDepartName }" /> <t:departComboTree id="reportDepart" idInput="reportDepartId" nameInput="reportDepartName" lazy="false"
                  value="${research.reportDepartId}" width="156"></t:departComboTree> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报单位</label></td>
              <td align="right"><label class="Validform_label">项目级别:</label></td>
              <td class="value"><t:codeTypeSelect name="projectGrade" type="select" codeType="1" code="XMJB" id="projectGrade" defaultVal="${research.projectGrade }" labelText="请选择"></t:codeTypeSelect>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目级别</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">项目性质:</label></td>
              <td class="value"><t:codeTypeSelect name="projectProperties" type="select" codeType="1" code="XMXZ" id="projectProperties" defaultVal="${research.projectProperties }"
                  labelText="请选择"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目性质</label></td>

              <td align="right"><label class="Validform_label">所属部门:</label></td>
              <td class="value"><t:codeTypeSelect name="superiorDepartname" type="select" codeType="1" code="SSBM" id="superiorDepartname" defaultVal="${research.superiorDepartname }"
                  labelText="请选择"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属部门</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  业务分管机关: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="branchDepartName" name="branchDepartName" type="hidden" value="${research.branchDepartName }" /> <t:codeTypeSelect name="branchDepartId" type="select"
                  codeType="1" code="FGBM" id="branchDepartId" defaultVal="${research.branchDepartId }" labelText="请选择"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">业务分管机关</label></td>

              <td align="right"><label class="Validform_label">
                  完成单位: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="completeDepartName" name="completeDepartName" value="${research.completeDepartName }" style="width: 150px;" type="text" class="inputxt" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">完成单位</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">合作单位:</label></td>
              <td class="value" colspan="3"><input id="cooperationDepartname" name="cooperationDepartname" value="${research.cooperationDepartname }" style="width: 575px;" type="text"
                  class="inputxt" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合作单位</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">申报日期:</label> <font color="red">*</font></td>
              <td class="value"><input id="applyTime" name="applyTime" style="width: 150px;" datatype="date"
                  <c:if test="${empty research.applyTime}">value="<fmt:formatDate value='${now}' pattern='yyyy-MM-dd'/>"</c:if>
                  <c:if test="${!empty research.applyTime}">value="<fmt:formatDate value='${research.applyTime}' pattern='yyyy-MM-dd'/>"</c:if> type="text" class="Wdate" onClick="WdatePicker()" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报日期</label></td>
              <td align="right"><label class="Validform_label">
                  项目周期（年）: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="projectCycle" name="projectCycle" type="text" datatype="*1-5" style="width: 150px;" value="${research.projectCycle }" class="inputxt" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目周期（年）</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">项目负责人:</label></td>
              <td class="value"><input id="projectManagerId" name="projectManagerId" type="hidden" value="${research.projectManagerId }" /> <input id="projectManagerName"
                  name="projectManagerName" class="inputtxt" style="width: 150px;" value="${research.projectManagerName }" readonly="readonly" /> <c:if test="${read == '0' }">
                  <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="projectManagerId" fun="uidChange" textname="id,realName"
                    inputTextname="projectManagerId,projectManagerName"></t:chooseUser>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目负责人</label></td>

              <td align="right"><label class="Validform_label">身份证号:</label></td>
              <td class="value"><input id="contactIdNo" name="contactIdNo" value="${research.contactIdNo }" style="width: 150px;" type="text" class="inputxt" /> <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">身份证号</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  固定电话: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="contactFixPhone" name="contactFixPhone" datatype="byterange" max="30" min="1" value="${research.contactFixPhone }" style="width: 150px;" type="text"
                  class="inputxt" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">固定电话</label></td>
              <td align="right"><label class="Validform_label">联系电话:<font color="red">*</font></label></td>
              <td class="value"><input id="contactPhone" name="contactPhone" value="${research.contactPhone }" style="width: 150px;" type="text" class="inputxt" datatype="*" ignore="ignore" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系电话</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  通信地址: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><input id="address" name="address" value="${research.address }" type="text" class="inputxt" style="width: 575px;" maxlength="75" datatype="*" /> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">通信地址</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">邮政编码:</label></td>
              <td class="value"><input id="postCode" name="postCode" value="${research.postCode }" type="text" style="width: 150px;" class="easyui-numberbox inputxt"
                  data-options="min:0, max:999999" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">邮政编码</label></td>
              <td align="right"><label class="Validform_label">流程流转状态:</label></td>
              <td class="value"><input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly" style="width: 150px;"> <input id="bpmStatus" name="bpmStatus"
                  value="${research.bpmStatus }" type="hidden" class="inputxt" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">流程流转状态</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  关键词: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="declareKey" name="declareKey" type="text" datatype="*1-250" style="width: 575px; height: 100px;" class="inputxt">${research.declareKey }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关键词</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  项目简介
                  <br>
                  （150字以内）: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectSummary" name="projectSummary" maxlength="150" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectSummary }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目简介（150字以内）</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  研究目的: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="researchAim" name="researchAim" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.researchAim }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究目的</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  主要研究内容: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="researchContent" name="researchContent" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.researchContent }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">主要研究内容</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  初步方案和
                  <br>
                  可行性分析: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="researchAnalyse" name="researchAnalyse" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.researchAnalyse }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">初步方案和可行性分析</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  进度安排: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectSchedule" name="projectSchedule" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectSchedule }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">进度安排</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  成果形式: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectResult" name="projectResult" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectResult }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">成果形式</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  主要考核指标: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectCheck" name="projectCheck" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectCheck }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">主要考核指标</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  推广应用前景
                  <br>
                  及效益分析: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectExtendBenefit" name="projectExtendBenefit" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectExtendBenefit }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">推广应用前景及效益分析</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label">
                  已有研究基础
                  <br>
                  和保障条件: <font color="red">*</font>
                </label></td>
              <td class="value" colspan="3"><textarea id="projectBaseCondition" name="projectBaseCondition" maxlength="200" datatype="*" style="width: 575px; height: 100px;" class="inputxt">${research.projectBaseCondition }</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">已有研究基础和保障条件</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
              <td colspan="3" class="value"><input type="hidden" value="${research.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width: 360px;" id="fileShow">
                  <c:forEach items="${research.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;</td>
                      <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                      <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                    </tr>

                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                    uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmDeclareArmy" ></t:upload>
                </div></td>
            </tr>
          </table>
        </t:formvalid>
      </div>
    </div>
    <%-- <div title="人员信息" style="overflow:auto;">
		<c:if test="${read == '0'}">
			<div id="memberTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
						onclick="add('录入','tPmDeclareMemberController.do?goUpdate&projDeclareId=${research.id }',
							'dg',630,230)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="update('编辑','tPmDeclareMemberController.do?goUpdate','dg',630,230)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="detail('查看','tPmDeclareMemberController.do?goUpdate', 'dg', 630, 230)">查看</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
						onclick="deleteMember();">删除</a>
			</div>
		</c:if>
		<table id="dg" class="easyui-datagrid" data-options="
				toolbar:'#memberTool', fit:true, fitColumns:true,
				idField:'id', singleSelect: true, onDblClickRow:function(index, row){detailMember()},
				url:'tPmDeclareMemberController.do?datagrid&projDeclareId=${research.id }'">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'id', hidden:true">id</th>
		            <th data-options="field:'userid', hidden:true">用户id</th>      
		            <th data-options="field:'name', width:100">用户姓名</th>   
		            <th data-options="field:'birthday', width:100, 
		            			formatter:function(value){
		            				return value == null ? value : value.substring(0, 10);
		            			}">出生日期</th> 
		            <th data-options="field:'sex', width:100, 
		            			formatter:function(value){
		            				var params = {codeType:'0', code:'SEX', value:value};
		            				return getValue(params, 'tBCodeTypeController.do?getValue');
		            			}">性别</th>  
		            <th data-options="field:'degree', width:100,
		            			formatter:function(value){
		            				var params = {codeType:'0', code:'XWLB', value:value};
		            				return getValue(params, 'tBCodeTypeController.do?getValue');
		            			}">学位</th>   
		            <th data-options="field:'superUnit', width:100,
		            			formatter:function(value){
		            				var params = {value:value};
		            				return getValue(params, 'departController.do?getValue');
		            			}">所属单位</th>  
		            <th data-options="field:'contactPhone', width:100">军线电话/手机</th>
		            <th data-options="field:'postCode', width:100">邮编</th>   
		            <th data-options="field:'postalAddress', width:200, hidden:true">通信地址</th>
		            <th data-options="field:'position', width:100,
		            			formatter:function(value){
		            				var params = {codeType:'0', code:'PROFESSIONAL', value:value};
		            				return getValue(params, 'tBCodeTypeController.do?getValue');
		            			}">职务</th>   
		            <th data-options="field:'taskDivide', width:200">任务分工</th> 
		            <th data-options="field:'projectManager', width:100,
		            			formatter:function(value){
		            				var params = {codeType:'0', code:'SFBZ', value:value};
		            				return getValue(params, 'tBCodeTypeController.do?getValue');
		            			}">是否负责人</th> 
		        </tr>   
		    </thead>   
		</table> 
	</div>
	<div title="项目经费年度预算（金额单位：元）" style="overflow:auto;">
		<c:if test="${read == '0'}">
			<div id="budgetTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
						onclick="add('录入(金额单位：元)','tPmFundsBudgetController.do?goUpdate&projDeclareId=${research.id }',
							'budgetTable', 300, 230)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="update('编辑','tPmFundsBudgetController.do?goUpdate','budgetTable', 300, 230)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
						onclick="deleteBudget();">删除</a>
			</div>
		</c:if>
		<table id="budgetTable" class="easyui-datagrid" data-options="
				toolbar:'#budgetTool',  fit:true, fitColumns:true,
				idField:'id', 
				singleSelect: true,
				url:'tPmFundsBudgetController.do?datagrid&projDeclareId=${research.id }'">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'id', hidden:true">id</th>
		            <th data-options="field:'budgetYear', width:100, align:'center'">年度</th>   
		            <th data-options="field:'equipFunds', width:100, align:'right'" formatter="formatCurrency">设备费</th> 
		            <th data-options="field:'materialFunds', width:100, align:'right'" formatter="formatCurrency">材料费</th>  
		            <th data-options="field:'outsideFunds', width:100, align:'right'" formatter="formatCurrency">外协费</th>   
		            <th data-options="field:'businessFunds', width:100, align:'right'" formatter="formatCurrency">业务费</th>
		            <th data-options="field:'totalFunds', width:100, align:'right'" formatter="formatCurrency">合计</th> 
		            <th data-options="field:'memo', width:100">备注</th>  
		        </tr>   
		    </thead>   
		</table> 
	</div>
	<div title="经费预算明细表-设备费（金额单位：元）" style="overflow:auto;">
		<table id="equTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetEquipController.do?datagrid&projDeclareId=${research.id }',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${read == '0'}">
        						onDblClickRow:function(row){dbClickRow(row, 'equTree')},
        					</c:if>
        					onBeforeEdit:function(row){row.editing=true; freshRow(row, 'equTree');},
					    	onAfterEdit:function(row){row.editing=false; freshRow(row, 'equTree');},
					    	onCancelEdit:function(row){row.editing=false; freshRow(row, 'equTree');}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'NAME', width:200" editor="text">名称</th>
		            <th data-options="field:'TREE', hidden:true"></th>   
		            <th data-options="field:'CONFIG',align:'center', width:200"  editor="text">型号及详细配置</th>  
		            <th data-options="field:'QUANTITY', width:80, align:'center'" editor="numberbox">数量</th> 
		            <th data-options="field:'PRICE', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">单价</th>   
		            <th data-options="field:'FUNDS', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">经费</th>   
		            <th data-options="field:'MEMO', width:200" editor="text">备注</th>
		            <c:if test="${read == '0'}">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div>
	<div title="经费预算明细表-材料费（金额单位：元）" style="overflow:auto;">
		<table id="mateTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetMaterialController.do?datagrid&projDeclareId=${research.id }',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${read == '0'}">
        						onDblClickRow:function(row){dbClickRow(row, 'mateTree')},
        					</c:if>
        					onBeforeEdit:function(row){row.editing=true; freshRow(row, 'mateTree');},
					    	onAfterEdit:function(row){row.editing=false; freshRow(row, 'mateTree');},
					    	onCancelEdit:function(row){row.editing=false; freshRow(row, 'mateTree');}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'NAME', width:200" editor="text">名称</th>
		            <th data-options="field:'TREE', hidden:true"></th>   
		            <th data-options="field:'MODEL', width:200"  editor="text">型号规格</th>
		            <th data-options="field:'QUANTITY', width:80, align:'center'" editor="numberbox">数量</th>   
		            <th data-options="field:'PRICE', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">单价</th>   
		            <th data-options="field:'FUNDS', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">经费</th>   
		            <th data-options="field:'MEMO', width:200" editor="text">备注</th>
		            <c:if test="${read == '0'}">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div>
	<div title="经费预算明细表-外协费（金额单位：元）" style="overflow:auto;">
		<table id="outTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetOutsideController.do?datagrid&projDeclareId=${research.id }',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${read == '0'}">
        						onDblClickRow:function(row){dbClickRow(row, 'outTree')},
        					</c:if>
        					onBeforeEdit:function(row){row.editing=true; freshRow(row, 'outTree');},
					    	onAfterEdit:function(row){row.editing=false; freshRow(row, 'outTree');},
					    	onCancelEdit:function(row){row.editing=false; freshRow(row, 'outTree');}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'NAME', width:200" editor="text">名称</th>
		            <th data-options="field:'TREE', hidden:true"></th>   
		            <th data-options="field:'UNIT', width:200"  editor="text">外协单位</th> 
		            <th data-options="field:'CONTENT', width:200" editor="text">外协内容</th>  
		            <th data-options="field:'FUNDS', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">经费</th>   
		            <th data-options="field:'MEMO', width:200" editor="text">备注</th>
		            <c:if test="${read == '0'}">
		           		<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div>
	<div title="经费预算明细表-业务费（金额单位：元）" style="overflow:auto;">
		<table id="busiTree" class="easyui-treegrid" 
        	data-options="url:'tPmFundsBudgetBusiController.do?datagrid&projDeclareId=${research.id }',
     			idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
     			<c:if test="${read == '0'}">
     				onDblClickRow:function(row){dbClickRow(row, 'busiTree')},
     			</c:if>
   				onBeforeEdit:function(row){row.editing=true; freshRow(row, 'busiTree');},
		    	onAfterEdit:function(row){row.editing=false; freshRow(row, 'busiTree');},
		    	onCancelEdit:function(row){row.editing=false; freshRow(row, 'busiTree');}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'NAME', width:200">名称</th>
		            <th data-options="field:'TREE', hidden:true"></th>   
		            <th data-options="field:'CONTENT', width:200" editor="text">简要内容</th>  
		            <th data-options="field:'FUNDS', align:'right', width:80, align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2}}" formatter="formatCurrency">经费</th>   
		            <th data-options="field:'MEMO', width:200" editor="text">备注</th>
		            <c:if test="${read == '0'}">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div>--%>
    <%-- <div style="height: 300px; width: 765px; border: solid 1px #95B8E7; margin: 0 auto;">
      <t:datagrid name="abatePayList" title="减免垫支信息" fitColumns="true" actionUrl="tPmAbatePayfirstController.do?datagridEasyUI&tpId=${research.tpId}" idField="id" fit="true" queryMode="group"
        pagination="false" onDblClick="detailAbatePay">
        <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="减免垫支标记" field="abatePayfirstFlag" codeDict="1,JMDZ" width="60"></t:dgCol>
        <t:dgCol title="减免或垫支经费额度(元)" field="abatePayfirstFunds" queryMode="single" width="100"></t:dgCol>
        <t:dgCol title="减免垫支理由" field="abatePayfirstReason" queryMode="group" width="90" align="center"></t:dgCol>
        <t:dgCol title="减免具体意见" field="abatePayfirstSuggestion" queryMode="group" width="120"></t:dgCol>
      </t:datagrid>
    </div>
  </div> --%>
  </div>

<script type="text/javascript">
    var editId = "";
    var editTree = "";
    $(document).ready(function() {
        if ($("#read").val() == '1') {
            $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
            $("textarea").attr("disabled", "disabled");
            $("select").attr("disabled", "disabled");
            $(".jeecgDetail").hide();
            $("#manageDepart").combotree({
                disabled : true
            });
            $("#reportDepart").combotree({
                disabled : true
            });
            $("#researchDepart").combotree({
                disabled : true
            });
        }

        //数据初始化
        var completeDepartName = $("#completeDepartName").val();
        if (completeDepartName == "") {
            $("#completeDepartName").val("海军工程大学");
        }
    });

    function checkCon() {
        var applyTime = $("#applyTime").val();
        if (!applyTime) {
            $.Showmsg('申报日期不能为空！');
            return false;
        }
        return true;
    }

    function uidChange() {
        var uid = $('#projectManagerId').val();
        $.ajax({
            type : 'post',
            async : false,
            url : 'tPmProjectMemberController.do?findInfoById',
            data : {
                'uid' : uid
            },
            success : function(data) {
                var d = $.parseJSON(data);
                var user = d.attributes.user;
                $('#contactPhone').val(user.officePhone ? user.officePhone : user.mobilePhone);
            }
        })
    }
    
  //上传文件成功后刷新
  function refresh() {
      window.location.reload();
  }
</script>
</body>