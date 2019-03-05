
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>合同节点新增</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
	$(document).ready(function(){
		$("#payAmount").blur(function(){
		    var allFee = $("#allFee").val();
		    if(allFee){
                var a = (parseFloat($(this).val().replace(",","")) / $("#allFee").val() * 100 );
                $("#payPercent").val(a);
			}else{
                $("#payPercent").val(100);
			}
		});
	});
	  function calPayAmount(){
			var inOutContractid = $('#inOutContractid').val();
			var payPercent = $('#payPercent').val();
			if(payPercent != ""){
				$.ajax({
					type : 'post',
					async : false,
					url:'tPmContractNodeController.do?calPayAmount',
					data:{'inOutContractid':inOutContractid, 'payPercent':payPercent},
					success : function(data) {
						var d = $.parseJSON(data);
						$('#payAmount').numberbox('setValue', d.attributes.payAmount);
					}
				});
			}		
		}
	  
	  //---------------------lijun附件上传成功方法--------------------
	  function uploadSuccess2(d, file, response){
	      var html="";
	      html += 
	      	'<tr style="height: 30px;">'+
	          	'<td>' +
	              	'<a href="javascript:void(0);" >' + d.attributes.fileName + '</a>&nbsp;&nbsp;&nbsp;' +
	              '</td>' +
	          	'<td style="width:40px;">' +
	          	'<a href="commonController.do?viewFTPFile' +
	          			'&fileid=' + d.attributes.fileid + 
	          			'&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0"' + 
	          			'title="下载">下载</a>' +
	              '</td>' +
	             
	              '<td style="width:40px;">' +
	              	'<a href="javascript:void(0)" class="jeecgDetail" ' +
	              			'onclick="delFTPFile(\'commonController.do?delFTPFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
	              '</td>'+
	          '</tr>';
	      $("#fileShow").append(html);
	      document.getElementById("filetitle").value = "true";   //当上传成功则给校验文本框填值，让表单校验通过
	  }

  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmContractNodeController.do?doAdd" tiptype="1" tipSweep="true"
  	callback="@Override loadNodeList">
				<input id="id" name="id" type="hidden" value="${tPmContractNodePage.id }">
				<input id="projectId" type="hidden" value="${projectId }">
        <input id="inOutFlag" name="inOutFlag" type="hidden" value="${tPmContractNodePage.inOutFlag}">
				<input id="createBy" name="createBy" type="hidden" >
			    <input id="createName" name="createName" type="hidden" >
			    <input id="createDate" name="createDate" type="hidden" >
			    <input id="updateBy" name="updateBy" type="hidden" >
			    <input id="updateName" name="updateName" type="hidden" >
			    <input id="updateDate" name="updateDate" type="hidden" >
			    <input id="allFee" type="hidden" value="${allFee}">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
<!-- 					<td align="right"> -->
<!-- 						<label class="Validform_label"> -->
<!-- 							合同名称:<font color="red">*</font> -->
<!-- 						</label> -->
<!-- 					</td> -->
<!-- 					<td class="value"> -->
<!-- 				     	<input id="inOutContractid" name="inOutContractid" type="hidden"  -->
<%-- 				     		value="${tPmContractNodePage.inOutContractid}"> --%>
<!-- 				     	<input id="contractName" type="text" style="width: 150px" class="inputxt" -->
<%-- 				     		value="${contractName}" readonly="readonly"> --%>
<!-- 						<span class="Validform_checktip"></span> -->
<!-- 						<label class="Validform_label" style="display: none;">进出帐合同名称</label> -->
<!-- 					</td> -->
					<input id="inOutContractid" name="inOutContractid" type="hidden" value="${tPmContractNodePage.inOutContractid}">
					<td align="right">
						<label class="Validform_label">
							节点名称:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="contractNodeName" name="contractNodeName" type="text" datatype="*1-25"
				     		style="width: 150px" class="inputxt" >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">节点名称</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							完成时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="completeDate" name="completeDate" type="text" style="width: 150px" 
				      		class="Wdate" onClick="WdatePicker()" datatype="date" >    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">完成时间</label>
					</td>
				</tr>
				<tr>
<!-- 					<td align="right"> -->
<!-- 						<label class="Validform_label"> -->
<!-- 							类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:<font color="red">*</font> -->
<!-- 						</label> -->
<!-- 					</td> -->
<!-- 					<td class="value"> -->
<%-- 				     	<t:codeTypeSelect id="prjType" name="prjType" type="select" codeType="1" code="HTJDLX"></t:codeTypeSelect> --%>
<!-- 						<span class="Validform_checktip"></span> -->
<!-- 						<label class="Validform_label" style="display: none;">类型</label> -->
<!-- 					</td> -->
					
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							当前节点百分比(%):
						</label>
					</td>
					<td class="value">
				     	<input id="payPercent" name="payPercent" type="text" style="width: 150px;" disabled="true"  class="easyui-numberbox" data-options="min:1,max:100" datatype="n" ignore="ignore" onblur="calPayAmount()">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">当前节点百分比</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							支付金额:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="payAmount" name="payAmount" type="text" style="width: 150px" 
				      		class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" >   
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
				     	<textarea id="payCondition" name="payCondition" style="width:460px; height:50px;" 
				     		datatype="*1-500" ignore="ignore"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">支付条件</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							成果形式:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="resultForm" name="resultForm" style="width:460px; height:50px;" 
				     		datatype="*1-500"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">成果形式</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							评价方法:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="evaluationMethod" name="evaluationMethod" style="width:460px; height:50px;" 
							datatype="*1-500" ignore="ignore"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">评价方法</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="remarks" name="remarks" style="width:460px; height:50px;" 
							datatype="*1-250" ignore="ignore"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<!--附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;-->
							合同节点附件:
						</label>
					</td>
      				<td colspan="3" class="value">
      					<input type="hidden" value="" id="filetitle" name="filetitle" datatype="n" ignore="ignore"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">合同节点附件</label>
						
						
      					<input type="hidden" value="${tPmContractNodePage.attachmentCode}" id="bid" name="attachmentCode" />
				        <table id="fileShow" style="max-width:438px;">
				            <c:forEach items="${tPmContractNodePage.certificates}" var="file" varStatus="idx">
					            <tr style="height: 30px;">
						            <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
						            <td style="width:40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
						            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
					            </tr>
				            </c:forEach>
				        </table>
						<div>
							<div class="form" id="filediv"></div>
							<t:upload name="fiels" id="file_upload"  buttonText="添加文件" 
	      	                   formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess2" 
	      	                    uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmContractNode" ></t:upload>
						</div>
      				</td>
    			</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
  <script src = "webpage/com/kingtake/project/contractnode/tPmContractNode.js"></script>	
  <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>	