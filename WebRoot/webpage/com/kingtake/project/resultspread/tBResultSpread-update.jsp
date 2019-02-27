<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>成果推广基本信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function uploadFile(data){
		$("#bid").val(data.obj.id);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}
	
	function close(){
		frameElement.api.close();
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBResultSpreadController.do?doUpdate" tiptype="1"  callback="@Override uploadFile">
  <link rel="stylesheet" href="webpage/com/kingtake/project/panelUtil/panel.css" type="text/css"></link>
<script type="text/javascript" src="webpage/com/kingtake/project/panelUtil/panel.js"></script>
<div style="overflow:hidden;width:100%;height:97%">
<!-- 技术成果情况表 -->
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title" style="font-size:15px;">
			技术成果情况表
		</div>
		<div class="tool" state="live">
			<a class="collapse expand" href="javascript:void(0)"></a>
			<span>隐藏</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:420px;">
		<div style="margin-left:30px;">
					<input id="id" name="id" type="hidden" value="${tBResultSpreadPage.id }">
					<input id="finishUserid" name="finishUserid" type="hidden" value="${tBResultSpreadPage.finishUserid }">
					<input id="projectId" name="projectId" type="hidden" value="${tBResultSpreadPage.projectId }">
		<table style="width: 600px;margin:auto;"  cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								技术开发单位:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="techDevUnit" name="techDevUnit" datatype="byterange" max="120" min="1" 
						    type="text" style="width: 85%" class="inputxt"value='${tBResultSpreadPage.techDevUnit}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术开发单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								成果完成人:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="finishUsername" name="finishUsername"datatype="byterange" max="120" min="1"  type="text" style="width: 85%" 
						    class="inputxt" value='${tBResultSpreadPage.finishUsername}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">成果完成人姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								技术简介:
							</label>
						</td>
						<td class="value">
							<textarea id="techSummary" name="techSummary" rows="3"  style="width: 85%"
							datatype="byterange" max="800" min="0"  
							title="包括技术背景、技术原理、成果组成及特点、技术水平、应用效果（400字以内）" 
							placeholder="包括技术背景、技术原理、成果组成及特点、技术水平、应用效果（400字以内）">${tBResultSpreadPage.techSummary}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术简介</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								专利状态:
							</label>
						</td>
						<td class="value">
						    <input id="patentStatus" name="patentStatus" datatype="byterange" max="200" min="0" type="text" 
						    style="width: 85%" class="inputxt" value='${tBResultSpreadPage.patentStatus}' 
						    title="已申请或者授权的专利名及专利号" placeholder="已申请或者授权的专利名及专利号">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">专利状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								获奖情况:
							</label>
						</td>
						<td class="value">
						    <input id="rewardInfo" name="rewardInfo" datatype="byterange" max="120" min="0"  type="text" style="width: 85%" class="inputxt"  
									title="获各级奖励情况" placeholder="获各级奖励情况"value='${tBResultSpreadPage.rewardInfo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">获奖情况</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								技术状态:
							</label>
						</td>
						<td class="value">
						    <textarea id="techStatus" name="techStatus" datatype="byterange" max="800" min="0"  type="text" style="width: 85%" class="inputxt"  
							 title="该技术处于试验或者生产的何种阶段" placeholder="该技术处于试验或者生产的何种阶段">${tBResultSpreadPage.techStatus}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								可应用领域:
							</label>
						</td>
						<td class="value">
						    <textarea id="applyScope" name="applyScope" datatype="byterange" max="800" min="0"  style="width: 85%" class="inputxt"  
							 title="部队用户及地方产业等" placeholder="部队用户及地方产业等" >${tBResultSpreadPage.applyScope}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">可应用领域</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								转化预期:
							</label>
						</td>
						<td class="value">
						    <textarea id="changeExpect" name="changeExpect" datatype="byterange" max="800" min="0"  style="width: 85%" class="inputxt"  
							 title="需求情况及转化规模" placeholder="需求情况及转化规模" >${tBResultSpreadPage.changeExpect}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">转化预期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								投入需求:
							</label>
						</td>
						<td class="value">
						    <input id="devotionRequirement" name="devotionRequirement" type="text" style="width: 85%;text-align:right;" 
						    class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
							 value='${tBResultSpreadPage.devotionRequirement}'>(万元)
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">投入需求</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								预期效益:
							</label>
						</td>
						<td class="value">
						    <textarea id="expectBenefit" name="expectBenefit" rows="3" datatype="byterange" max="800" min="0"  style="width: 85%" class="inputxt"  
							 title="成果转化后所实现的军事、经济、社会效益分析，主要建设内容及规模" placeholder="成果转化后所实现的军事、经济、社会效益分析，主要建设内容及规模"             
							 >${tBResultSpreadPage.expectBenefit}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预期效益</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系人:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						     	 <input id="resultContact" name="resultContact" datatype="byterange" max="36" min="1"  type="text" style="width: 85%" class="inputxt"  
									value='${tBResultSpreadPage.resultContact}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术成果情况联系人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="resultPhone" name="resultPhone" datatype="n0-30" type="text" style="width: 85%" class="inputxt"  
								  value='${tBResultSpreadPage.resultPhone}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术成果情况联系电话</label>
						</td>
					</tr>
					
			</table>
		</div>
	</div>
	<!-- 技术成果转化情况表 -->
	<div style="border:1px solid #B8CCE2; cursor:pointer" onclick="apprOrBasic2(this);">
		<div class="table_title" style="font-size:15px;">
			技术成果转化情况表
		</div>
		<div class="tool" state="dead">
			<a class="collapse" href="javascript:void(0)"></a>
			<span>展开</span>
		</div>
	</div>
	<div style="overflow-y:auto;height:420px;display:none">
		<table style="width: 600px;margin:auto;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
						<td align="right">
							<label class="Validform_label" >
								转化进行情况:
							</label>
						</td>
						<td class="value">
						     <t:codeTypeSelect id="changeInfo" name="changeInfo" codeType="1" code="CGZHQK" type="select" extendParam="{'style':'width:87%;'}" defaultVal="${tBResultSpreadPage.changeInfo}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">转化进行情况</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								合作单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="cooperativeUnit" name="cooperativeUnit" datatype="byterange" max="350" min="0" 
						     	 type="text" style="width: 85%" class="inputxt" value='${tBResultSpreadPage.cooperativeUnit}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合作单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								转让形式:
							</label>
						</td>
						<td class="value">
						     	 <t:codeTypeSelect id="transferForm" name="transferForm" type="select"  extendParam="{'style':'width:87%;'}" codeType="1" code="CGZRXS" 
								  defaultVal="${tBResultSpreadPage.transferForm}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">转让形式</label>
						</td>
					</tr>
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								合同情况:
							</label>
						</td>
						<td class="value">
						     	 <input id="contractInfo" name="contractInfo" type="text" style="width: 85%" class="inputxt"  
								  value='${tBResultSpreadPage.contractInfo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同情况</label>
						</td>
					</tr> --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								合同期限:
							</label>
						</td>
						<td class="value">
						     	 <input id="contractDeadline" name="contractDeadline" type="text" style="width: 85%" class="inputxt"  
								  datatype="byterange" max="50" min="0" value='${tBResultSpreadPage.contractDeadline}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同期限</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								合同金额及到账情况:
							</label>
						</td>
						<td class="value">
						    <textarea id="contractAmount" name="contractAmount" type="text" style="width: 85%" class="inputxt"  
							datatype="byterange" max="800" min="0">${tBResultSpreadPage.contractAmount}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同金额及到账情况</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								年度收益及到账情况:
							</label>
						</td>
						<td class="value">
						    <textarea id="contractIncome" name="contractIncome" type="text" style="width: 85%" class="inputxt"  
							datatype="byterange" max="800" min="0">${tBResultSpreadPage.contractIncome}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">年度收益及到账情况</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								需解决困难:
							</label>
						</td>
						<td class="value">
						    <textarea id="resolveDifficult" name="resolveDifficult" type="text" style="width: 85%" class="inputxt"  
							datatype="byterange" max="800" min="0">${tBResultSpreadPage.resolveDifficult}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">需解决困难</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系人:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="changeContact" name="changeContact" type="text" style="width: 85%" class="inputxt"  
							datatype="byterange" max="36" min="1" value='${tBResultSpreadPage.changeContact}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术成果转化情况联系人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="changePhone" name="changePhone" type="text" style="width: 85%" class="inputxt"  
								 datatype="n0-30" value='${tBResultSpreadPage.changePhone}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">技术成果转化情况联系电话</label>
						</td>
					</tr>
					<tr>
        <td align="right"><label class="Validform_label">合同情况：</label></td>
        <td colspan="3" class="value"><input type="hidden" value="${tBResultSpreadPage.id}" id="bid" name="bid" />
          <table style="max-width:92%;">
	        <c:forEach items="${tBResultSpreadPage.certificates }" var="file"  varStatus="idx">
	          <tr>
	            <td><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tBResultSpreadPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
	            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
	            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
	          </tr>
	        </c:forEach>
	      </table>
	      <div>
		    <div class="form" id="filediv"></div>
		    <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
		    	formData="bid,projectId" uploader="commonController.do?saveUploadFiles&businessType=tBResultSpread">
		  	</t:upload>
		  </div>
	    </td>
      </tr>
		</table>
	</div>
</div>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/resultspread/tBResultSpread.js"></script>		