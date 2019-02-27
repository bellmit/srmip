<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>技术基础项目申报书</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%@ include file="/webpage/com/kingtake/common/toastr/toastr.jsp"%>
  <script type="text/javascript" src="webpage/common/util.js"></script>
  <script type="text/javascript" src="webpage/com/kingtake/project/declare/army/tBPmDeclareArmyResearch.js"></script>
 </head>
<style>
.panel-body {
    border-width: 0px;
    border-style: none;
}
</style>
<input id="read" value=${read } type="hidden"/>
<body>
<input id="read" value="${read}" type="hidden"/>
<div align="center" style="width: 810px;margin: 0 auto;">
	<div class="easyui-panel" title="${research.projectName }：申报书基本信息<a href='#' onclick=toastrMsg('techDeclare') style='color: red'>(查看说明)</a>" style="overflow:auto;border: solid 1px #95B8E7;" data-options="tools:'#tb'">
      <div id="tb">
      	<c:if test="${read eq '0' }">
       		<a href="#"  class="icon-save" title="保存" onclick="saveBaseInfo();"></a>
       	</c:if>
       	<c:if test="${!empty research.id && research.bpmStatus eq '1' && read eq '0' }">
          	<a href="#"  class="icon-ok" title="提交流程" onclick="startProcess('t_b_pm_declare_technology');"></a>
       	</c:if>
        <c:if test="${research.bpmStatus eq '5' && read eq '0' }">
            <a href="#"  class="icon-ok" title="修改提交" onclick="compeleteProcess('tech');"></a>
            <a href="#"  class="icon-search" title="查看历史意见" onclick="viewRemark();"></a>
        </c:if>
        <c:if test="${research.planStatus eq '2' && read eq '0' }">
                <a href="#" class="icon-ok"  title="修改完成后重新提交" onclick="compeleteProcess();"></a>
                <a href="#" class="icon-search"  title="查看计划草案驳回意见" onclick="viewMsgText('${research.id}');"></a>
        </c:if>
        <c:if test="${research.bpmStatus ne '1' and research.bpmStatus ne '5' and research.planStatus ne '2' and research.bpmStatus ne '6' and opt ne 'audit'}">
            <a href="#"  class="icon-search" title="查看流程" onclick="viewHistory('${research.processInstId }');"></a>
        </c:if>
      </div>
      <div style="padding: 10px 0; background-color: #f4f4f4;">
		<t:formvalid formid="baseInfo" dialog="true" usePlugin="password" 
				layout="table" beforeSubmit="checkCon"
				action="tBPmDeclareTechnologyController.do?doUpdate" tiptype="showValidMsg" 
				callback="@Override saveCallback">
			<input id="id" name="id" type="hidden" value="${research.id }" />
            <input id="processInsId" type="hidden" value="${processInstId }" />
            <input id="taskId" type="hidden" value="${taskId }" />
            <input id="tableName" type="hidden" />
			<table style="width: 875px; background-color: white; margin: 0 auto; position: relative;" 
				cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">项目名称:</label>
					</td>
					<td class="value" colspan="5">
						<input id="projectId" name="tpId" type="hidden" value="${research.tpId }" />
						<input id="projectName" name="projectName" value="${research.projectName }" style="width: 500px;"
							type="text" class="inputxt" readonly="readonly" />
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">项目名称</label>
					</td>
                </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">项目类别:</label>
					</td>
					<td class="value">
						<input name="projectType.id" value="${research.projectType.id }" type="hidden"/>
			     	 	<input value="${research.projectType.projectTypeName }" type="text" class="inputxt" 
			     	 		readonly="readonly" style="width: 150px;"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">项目类别</label>
					</td>
                    <td align="right">
                        <label class="Validform_label">填报日期:<font color="red">*</font></label>
                    </td>
                    <td class="value">
                        <input id="applyTime" name="applyTime" style="width: 150px;" datatype="date"
	                        value="<fmt:formatDate value='${research.applyTime}' pattern='yyyy-MM-dd'/>" 
	                        type="text" class="Wdate" onClick="WdatePicker()"/>    
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">申报日期</label>
                    </td>
                </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">主办单位:<font color="red">*</font></label>
					</td>
					<td class="value">
						<input id="hostDeptId" name="hostDeptId" type="hidden" value="${research.hostDeptId }" /> 
						<input id="hostDeptName" name="hostDeptName" type="hidden" value="${research.hostDeptName }" 
							datatype="*" />
						<t:departComboTree id="hostDept" idInput="hostDeptId" nameInput="hostDeptName" 
							lazy="false" value="${research.hostDeptName}" width="156"></t:departComboTree> 
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">主办单位</label>
					</td>
					<td align="right">
						<label class="Validform_label">论证单位:<font color="red">*</font></label>
					</td>
					<td class="value">
						<input id="argumentDeptId" name="argumentDeptId" type="hidden" value="${research.argumentDeptId }" />
						<input id="argumentDeptName" name="argumentDeptName" type="hidden" 
							value="${research.argumentDeptName }" datatype="*" />
						<t:departComboTree id="argumentDept" idInput="argumentDeptId" nameInput="argumentDeptName"
							lazy="false" value="${research.argumentDeptName}" width="156"></t:departComboTree>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">论证单位</label>
					</td>
                </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">流程流转状态:</label>
					</td>
					<td class="value">
                        <input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly" style="width:150px;">
				     	<input id="bpmStatus" name="bpmStatus" value="${research.bpmStatus }" type="hidden" 
				     		class="inputxt" /> 
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">流程流转状态</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">内容与范围 :<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="contentRange" name="contentRange" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.contentRange }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">内容与范围</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">需求分析:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="demandAnalysis" name="demandAnalysis" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.demandAnalysis }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">需求分析</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">国内标准现状分析:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="domesticAnalysis" name="domesticAnalysis" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.domesticAnalysis }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">国内标准现状分析</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">国外标准情况分析:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="internationalAnalysis" name="internationalAnalysis" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.internationalAnalysis }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">国外标准情况分析</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">可行性分析:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="feasibilityAnalysis" name="feasibilityAnalysis" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.feasibilityAnalysis }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">可行性分析</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">有关单位及意见:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="relatedUnitOpinion" name="relatedUnitOpinion" maxlength="250" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.relatedUnitOpinion }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">有关单位及意见</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">项目进度:<font color="red">*</font></label>
					</td>
					<td class="value" colspan="5">
				     	<textarea id="projectSchedule" name="projectSchedule" maxlength="200" datatype="*"
				     		style="width: 710px; height:100px;" class="inputxt">${research.projectSchedule }</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">项目进度</label>
					</td>
				</tr>
        <tr>
            <td align="right">
            <label class="Validform_label">
                               附件:&nbsp;&nbsp;
            </label>
            </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${research.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width:360px;" id="fileShow">
                    <c:forEach items="${research.attachments}" var="file" varStatus="idx">
                      <tr style="height: 30px;">
                        <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                        <td style="width:40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                        <td style="width:60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                      </tr>
                    </c:forEach>
                </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload  name="fiels" id="file_upload" buttonText="添加文件" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
               uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmDeclareTechnology" ></t:upload>
            </div>
              </td>
          </tr>
			</table>
		</t:formvalid>
      </div> 
	</div>
	<div style="height: 10px;"></div>
    <div class="easyui-panel" title="项目组成员列表" style="height:300px;width: 810px; border: solid 1px #95B8E7;" >
	<t:datagrid name="tPmProjectMemberList" checkbox="false" fitColumns="true" 
		actionUrl="tPmProjectMemberController.do?datagrid&project.id=${research.tpId}" 
		onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group">
	   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="姓名"  field="name" queryMode="single" isLike="true"  width="120"></t:dgCol>
	   <t:dgCol title="性别"  field="sex" codeDict="0,SEX"   queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="出生年月"  field="birthday" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
	   <t:dgCol title="学位"  field="degree" codeDict="0,XWLB"   queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="职称职务"  field="position"  codeDict="0,PROFESSIONAL"  queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="任务分工"  field="taskDivide"    queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="所属单位"  field="superUnitName"    queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="是否负责人"  field="projectManager" codeDict="0,SFBZ"   queryMode="group"  width="120"></t:dgCol>
  	</t:datagrid>
    </div>
    </div>
    <div style="height: 10px;"></div>
	<%-- <div class="easyui-panel" title="减免垫支信息" style="overflow:auto;height: 300px;width: 890px; border: solid 1px #95B8E7;">
		<table id="abatePay" class="easyui-datagrid" data-options="
				toolbar:'#abatePayTool', fit:true, fitColumns:true, 
				idField:'id', singleSelect: true, onDblClickRow: function(index, row){detailAbatePay();},
				url:'tPmAbatePayfirstController.do?datagridEasyUI&tpId=${research.tpId}'">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'id', hidden:true">id</th>
		            <th data-options="field:'abatePayfirstFlag', width:100, 
		            			formatter:function(value){
		            				var params = {codeType:'1', code:'JMDZ', value:value};
		            				return getValue(params, 'tBCodeTypeController.do?getValue');
		            			}">减免垫支标记</th>   
		            <th data-options="field:'abatePayfirstFunds', width:100, align:'right'" formatter="formatCurrency">减免或垫支经费额度(元)</th> 
		            <th data-options="field:'abatePayfirstReason', width:100">减免垫支理由</th>  
		            <th data-options="field:'abatePayfirstSuggestion', width:100">减免具体意见</th>   
		        </tr>   
		    </thead>   
		</table> 
	</div> --%>
</div>

<script type="text/javascript">
$(document).ready(function(){
	if($("#read").val() == '1'){
		$("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
		$("textarea").attr("disabled", "disabled");
		$("#hostDept").combotree({disabled:true});
		$("#argumentDept").combotree({disabled:true});
	}
});

function checkCon(){
	var applyTime = $("#applyTime").val();
	if(!applyTime){
		$.Showmsg('申报日期不能为空！');
		return false;
	}
	return true;
}

function deleteTechnologyFunds(){
	var record = $("#budgetTable").datagrid('getSelected');
	if(record){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.ajax({
		        	type:'post',
		        	data:"id="+record.id,
		        	url:'tPmDeclareFundsTechnologyController.do?doDel',
		        	success:function(result){
		        		var json = $.parseJSON(result);
		        		reloadBudget();
		        		showMsg(json.msg);
		        	}
		        });  
		    }    
		});
	}else{
		showMsg("请选择一条记录后再删除！");
	}
}
</script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>