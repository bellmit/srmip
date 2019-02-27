<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>技术基础项目申报书</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
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
<div>
      <div style="padding: 10px 0; background-color: #f4f4f4;">
      <table style="width: 860px;margin: auto;" cellpadding="0" cellspacing="0" border="0" >
          <tr>
            <td align="center" bgcolor="#E5EFFF">
              <b>${research.projectName }：申报书基本信息</b>
            </td>
            <td width="25%" bgcolor="#E5EFFF" align="right">
              <c:if test="${read == '0' }">
                <a id="saveBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="保存项目申报书" onclick="saveBaseInfo();">保存项目申报书</a>
              </c:if>
              <c:if test="${research.bpmStatus eq 1 && read eq 0 }">
                <a id="commitBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="提交流程" onclick="startProcess('t_b_pm_declare_technology');">提交流程</a>
              </c:if>
              <c:if test="${research.bpmStatus ne 1}">
                <a id="viewBtn"  class="easyui-linkbutton" plain="true" style="border: solid 1px;"  title="查看流程" onclick="viewHistory('${research.processInstId }');">查看流程</a>
              </c:if>
            </td>
          </tr>
        </table>
		<t:formvalid formid="baseInfo" dialog="true" usePlugin="password" 
				layout="table" beforeSubmit="checkCon"
				action="tBPmDeclareTechnologyController.do?doUpdate" tiptype="showValidMsg" 
				callback="@Override saveCallback">
			<input id="id" name="id" type="hidden" value="${research.id }" />
			<table style="width: 860px; background-color: white; margin: 0 auto; position: relative;" 
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
							lazy="true" value="${research.hostDeptName}" width="156"></t:departComboTree> 
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
							lazy="true" value="${research.argumentDeptName}" width="156"></t:departComboTree>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">论证单位</label>
					</td>
                </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">流程流转状态:</label>
					</td>
					<td class="value">
                         <input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly">
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
	<%-- <div title="人员信息" style="overflow:auto;">
		<c:if test="${read == '0' }">
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
				toolbar:'#memberTool', onDblClickRow:function(index, row){detailMember()},
				idField:'id', fit:true, fitColumns:true, singleSelect: true,
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
	<div title="经费预算（金额单位：元）" style="overflow:auto;">
		<c:if test="${read == '0' }">
			<div id="budgetTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
						onclick="add('录入(金额单位：元)','tPmDeclareFundsTechnologyController.do?goUpdate&projDeclareId=${research.id }',
							'budgetTable', 300, 150)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="update('编辑','tPmDeclareFundsTechnologyController.do?goUpdate','budgetTable', 300, 150)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
						onclick="deleteTechnologyFunds();">删除</a>
			</div> 
		</c:if>
		<table id="budgetTable" class="easyui-datagrid" data-options="
				toolbar:'#budgetTool', fit:true, fitColumns:true,
				idField:'id', 
				singleSelect: true,
				url:'tPmDeclareFundsTechnologyController.do?datagrid&projDeclareId=${research.id }'">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'id', hidden:true">id</th>
		            <th data-options="field:'budgetYear', width:100, align:'center'">年度</th>   
		            <th data-options="field:'budgetName', width:100">开支范围</th> 
		            <th data-options="field:'budgetFunds', width:100, align:'right'" formatter="formatCurrency">预算经费</th>  
		            <th data-options="field:'memo', width:100">备注</th>  
		        </tr>   
		    </thead>   
		</table> 
	</div>
	<div title="减免垫支信息" style="overflow:auto;">
		<c:if test="${read == '0' }">
			<div id="abatePayTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls:'icon-add',plain:true" 
					onclick="add('录入','tPmAbatePayfirstController.do?goAddUpdate&tpId=${research.tpId}',
						'abatePay', 620, 300)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls:'icon-edit',plain:true" 
					onclick="update('编辑','tPmAbatePayfirstController.do?goAddUpdate&tpId=${research.tpId}', 
						'abatePay', 620, 300)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls:'icon-search',plain:true" 
					onclick="detail('查看','tPmAbatePayfirstController.do?goAddUpdate&tpId=${research.tpId}', 
						'abatePay', 620, 300)">查看</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" 
					data-options="iconCls:'icon-remove',plain:true" 
					onclick="deleteAbatePay();">删除</a>
			</div>
		</c:if>
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