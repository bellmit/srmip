<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>维修科研申报书信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/declare/army/tBPmDeclareArmyResearch.js"></script>
</head>
<style>
.panel-body {
	border-width: 0px;
	border-style: none;
}

input[name='bpm_status'] {
	width: 152px;
}
</style>
<input id="read" value=${read } type="hidden" />
<body>
  <div>
    <div style="padding: 10px 0; background-color: #f4f4f4;">
      <table style="width: 860px; margin: auto;" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td align="center" bgcolor="#E5EFFF"><b>${research.projectName }：申报书基本信息</b></td>
          <td width="25%" bgcolor="#E5EFFF" align="right"><c:if test="${read == '0' }">
              <a id="saveBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="保存项目申报书" onclick="saveBaseInfo();">保存</a>
            </c:if> <c:if test="${research.bpmStatus eq 1 && read eq 0 }">
              <a id="commitBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="提交流程" onclick="startProcess('t_b_pm_declare_repair');">提交流程</a>
            </c:if> <c:if test="${research.bpmStatus ne 1}">
              <a id="viewBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="查看流程" onclick="viewHistory('${research.processInstId }');">查看流程</a>
            </c:if></td>
        </tr>
      </table>
      <t:formvalid formid="baseInfo" dialog="true" usePlugin="password" layout="table" action="tBPmDeclareRepairController.do?doUpdate" tiptype="showValidMsg" callback="@Override saveCallback">
        <input id="id" name="id" type="hidden" value="${research.id }" />
        <table style="width: 860px; background-color: white; margin: 0 auto; position: relative;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
            <td align="right"><label class="Validform_label">项目名称:</label></td>
            <td class="value" colspan="5"><input id="projectId" name="tpId" type="hidden" value="${research.tpId }" /> <input id="projectName" name="projectName" value="${research.projectName }"
                style="width: 520px;" type="text" class="inputxt" readonly="readonly" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">项目类型:</label></td>
            <td class="value" style="width: 180px;"><input name="projectType.id" value="${research.projectType.id }" type="hidden" /> <input value="${research.projectType.projectTypeName }"
                type="text" class="inputxt" readonly="readonly" style="width: 150px;" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目类型</label></td>
            <td align="right"><label class="Validform_label">
                项目类别:<font color="red">*</font>
              </label></td>
            <td class="value"><t:codeTypeSelect id="projectCategory" name="projectCategory" type="select" codeType="1" code="WXSBSXMLB" defaultVal="${research.projectCategory }">
              </t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目类别</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">项目负责人:</label></td>
            <td class="value"><input id="projectManagerId" name="projectManagerId" type="hidden" value="${research.projectManagerId }" /> <input id="projectManagerName" name="projectManagerName"
                type="text" style="width: 100px" class="inputxt" value="${research.projectManagerName }" /> <c:if test="${read == '0'}">
                <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="projectManagerId" textname="id,realName" inputTextname="projectManagerId,projectManagerName"></t:chooseUser>
              </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目负责人</label></td>
            <td align="right"><label class="Validform_label">流程流转状态:</label></td>
            <td class="value"><input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly" style="width:150px;"> <input id="bpmStatus" name="bpmStatus" value="${research.bpmStatus }"
                type="hidden" class="inputxt" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">流程流转状态</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                填报单位:<font color="red">*</font>
              </label></td>
            <td class="value"><input id="applyDeptId" name="applyDeptId" type="hidden" value="${research.applyDeptId }" /> <input id="applyDeptName" name="applyDeptName" type="hidden"
                value="${research.applyDeptName }" datatype="*" /> <t:departComboTree id="applyDept" idInput="applyDeptId" nameInput="applyDeptName" lazy="true" value="${research.applyDeptName}"
                width="156"></t:departComboTree> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">填报单位</label></td>
            <td align="right"><label class="Validform_label">
                填报日期:<font color="red">*</font>
              </label></td>
            <td class="value"><input id="applyTime" name="applyTime" style="width: 150px;" datatype="date" value="<fmt:formatDate value='${research.applyTime}' pattern='yyyy-MM-dd'/>" type="text"
                class="Wdate" onClick="WdatePicker()" /> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">填报日期</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                项目简介
                <br />
                (150字以内):<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="projectIntroduce" name="projectIntroduce" maxlength="150" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.projectIntroduce }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目简介</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                军事需求:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="militaryDemand" name="militaryDemand" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.militaryDemand }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">军事需求</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                应用前景:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="applicationProspect" name="applicationProspect" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.applicationProspect }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">应用前景</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                技术途径
                <br />
                研究方案:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="technicalResearch" name="technicalResearch" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.technicalResearch }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">技术途径/研究方案</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                先进程度:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="advancedDegree" name="advancedDegree" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.advancedDegree }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">先进程度</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                技术指标:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="technicalIndex" name="technicalIndex" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.technicalIndex }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">技术指标</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                进度安排:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="scheduling" name="scheduling" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.scheduling }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">进度安排</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                风险评估:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="riskAssessment" name="riskAssessment" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.riskAssessment }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">风险评估</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                成果形式:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="resultForm" name="resultForm" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.resultForm }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">成果形式</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                承研能力
                <br />
                设施条件:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="bearingFacility" name="bearingFacility" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.bearingFacility }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承研能力/设施条件</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                以往承担
                <br />
                装备维修科学研究
                <br />
                与改革项目
                <br />
                的推广应用情况:<font color="red">*</font>
              </label></td>
            <td class="value" colspan="5"><textarea id="pastSituation" name="pastSituation" maxlength="200" datatype="*" style="width: 710px; height: 100px;" class="inputxt">${research.pastSituation }</textarea>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">以往承担装备维修科学研究与改革项目的推广应用情况</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
            <td colspan="3" class="value">
                <input type="hidden" value="${research.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width:360px;" id="fileShow">
                    <c:forEach items="${research.attachments}" var="file" varStatus="idx">
                      <tr style="height: 30px;">
                        <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                        <td style="width:40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                        <c:if test="${read == '0' }">
                        <td style="width:60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                        </c:if>
                      </tr>
                    </c:forEach>
                </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmDeclareRepair" ></t:upload>
            </div>
              </td>
          </tr>
        </table>
      </t:formvalid>
    </div>

    <!-- <div title="人员信息" style="overflow:auto;">
		<c:if test="${read == '0' }">
			<div id="memberTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
						onclick="add('录入','tPmDeclareMemberController.do?goUpdate&projDeclareId=${research.id }',
							'dg', 630, 230)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="update('编辑','tPmDeclareMemberController.do?goUpdate','dg',630,330)">编辑</a>
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
	</div>-->
    <%-- <div title="维修科学研究项目经费预算表（金额单位：元）" style="overflow:auto;">
		<c:if test="${read == '0' }">
			<div id="budgetTool" style="height:auto">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" 
						onclick="add('录入(金额单位：元)','tPmFundsBudgetController.do?goUpdate&projDeclareId=${research.id }',
							'budgetTable', 300, 230)">录入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" 
						onclick="update('编辑','tPmFundsBudgetController.do?goUpdate','budgetTable',300, 230)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" 
						onclick="deleteBudget();">删除</a>
			</div> 
		</c:if>
		<table id="budgetTable" class="easyui-datagrid" data-options="
				toolbar:'#budgetTool', fit:true, fitColumns:true, 
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
	</div> --%>
    <%-- <div title="经费预算明细表-设备费（金额单位：元）" style="overflow:auto;">
		<table id="equTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetEquipController.do?datagrid&projDeclareId=${research.id }&flag=repair',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${research.bpmStatus eq 1 &&  read == '0'}">
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
		            <c:if test="${read == '0' }">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div> --%>
    <%-- <div title="经费预算明细表-材料费（金额单位：元）" style="overflow:auto;">
		<table id="mateTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetMaterialController.do?datagrid&projDeclareId=${research.id }&flag=repair',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${research.bpmStatus eq 1 &&  read == '0'}">
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
		            <c:if test="${read == '0' }">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div> --%>
    <%-- <div title="经费预算明细表-外协费（金额单位：元）" style="overflow:auto;">
		<table id="outTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetOutsideController.do?datagrid&projDeclareId=${research.id }&flag=repair',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${research.bpmStatus eq 1 &&  read == '0'}">
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
		            <c:if test="${read == '0' }">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div> --%>
    <%-- <div title="经费预算明细表-业务费（金额单位：元）" style="overflow:auto;">
		<table id="busiTree" class="easyui-treegrid"
        	data-options="url:'tPmFundsBudgetBusiController.do?datagrid&projDeclareId=${research.id }&flag=repair',
        					idField:'ID',treeField:'NAME', fit:true, fitColumns:true,
        					<c:if test="${research.bpmStatus eq 1 &&  read == '0'}">
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
		            <c:if test="${read == '0' }">
		            	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
		            </c:if>
		        </tr>   
		    </thead>
		</table>
	</div> --%>
    <%-- <div title="减免垫支信息" style="overflow:auto;">
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
            var editId = "";
            var editTree = "";
            $(document).ready(function() {
                if ($("#read").val() == '1') {
                    $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
                    $("textarea").attr("disabled", "disabled");
                    $("select").attr("disabled", "disabled");
                    $("#applyDept").combotree({
                        disabled : true
                    });
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
            
          //上传文件成功后刷新
            function refresh() {
                window.location.reload();
            }
        </script>
  <script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>