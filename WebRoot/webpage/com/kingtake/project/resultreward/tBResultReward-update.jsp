<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>成果奖励基本信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
  <script type="text/javascript">
  function saveCallback(data){
      var win;
      var dialog = W.$.dialog.list['processDialog'];
      if(dialog==undefined){
          win = frameElement.api.opener;
      }else{
          win = dialog.content;
      }
      win.tip(data.msg);
      if(data.success){
          win.reloadTable();
          frameElement.api.close();
      }
  }
  
  $(function(){
      //加载项目类型
      var projectType = $("#projectType_hidden").val();
      $("#projectType").combotree({
  	    url : 'tPmProjectController.do?getProjectType&projectTypeId='+projectType,
  		valueField : 'id',
  		textField : 'projectTypeName',
  		multiple:true,
  		onLoadSuccess : function() {
  			var projectType = $("#projectType_hidden").val();
  			if (projectType != "") {
  				$("#projectType").combotree('setValues', projectType.split(","));
  			} 
  		}
  	}); 
  });
  </script>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBResultRewardController.do?doUpdate"  tiptype="1" callback="@Override saveCallback">
					<input id="id" name="id" type="hidden" value="${tBResultRewardPage.id }">
					<input id="projectId" type="hidden" value="${projectId }">
					<input id="sourceProjectIds" name="sourceProjectIds" type="hidden" value="${tBResultRewardPage.sourceProjectIds }">
		      <input id="finishInfoStr" name="finishInfoStr" type="hidden">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 154px;">
          <label class="Validform_label">
            项目名称<img alt="成果经过鉴定的，名称要与鉴定成果名称一致" title="成果经过鉴定的，名称要与鉴定成果名称一致" src="plug-in\easyui1.4.2\themes\icons\tip.png">:<font color="red">*</font>
          </label>
        </td>
        <td class="value" colspan="3">
          <input id="rewardName" name="rewardName" datatype="byterange" max="120" min="1" type="text" style="width: 70%" class="inputxt" value='${tBResultRewardPage.rewardName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            项目来源:<font color="red">*</font>
          </label>
        </td>
        <td class="value" colspan="3">
          <input id="mainSurceId" name="mainSurceId" type="hidden" value="${tBResultRewardPage.mainSurceId }">
          <input id="mainSurce" name="mainSurce" datatype="byterange" max="120" min="0" type="text" style="width: 70%" readonly="readonly" class="inputxt" value='${tBResultRewardPage.mainSurce}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目来源</label>
        </td>
      </tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								任务来源<img alt="合同甲方或军队业管理机关" title="合同甲方或军队业管理机关" src="plug-in\easyui1.4.2\themes\icons\tip.png">:<font color="red">*</font>
							</label>
						</td>
						<td class="value" colspan="3">
						    <textarea id="taskSource" name="taskSource" cols="3" rows="4" style="width: 70%;"  datatype="byterange" max="1000" min="1" >${tBResultRewardPage.taskSource}</textarea><%-- <t:chooseProject inputName="taskSource" inputId="sourceProjectIds" isclear="true"></t:chooseProject> --%>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">任务来源</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								拟申报奖励类别<img alt="国家科技进步（发明）奖、军队科技进步奖等" title="国家科技进步（发明）奖、军队科技进步奖等" src="plug-in\easyui1.4.2\themes\icons\tip.png">:
							</label>
						</td>
						<td class="value">
						    <t:codeTypeSelect name="reportRewardLevel" type="select" codeType="1" code="SBJLLB" id="reportRewardLevel" defaultVal="${tBResultRewardPage.reportRewardLevel}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">拟申报奖励类别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								拟申报等级<img alt="一、二、三等奖" title="一、二、三等奖" src="plug-in\easyui1.4.2\themes\icons\tip.png">:
							</label>
						</td>
						<td class="value">
						   <t:codeTypeSelect name="reportLevel" type="select" codeType="1" code="SBDJ" id="reportLevel"></t:codeTypeSelect> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">拟申报等级</label>
						</td>
					</tr>
					<tr>
						<td align="right"><label class="Validform_label"> 成果鉴定时间<img alt="鉴定会议时间" title="鉴定会议时间" src="plug-in\easyui1.4.2\themes\icons\tip.png">: </label></td>
                        <td class="value"><input id="appraisalTime" name="appraisalTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
                            value='<fmt:formatDate value='${tBResultRewardPage.appraisalTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
                             style="display: none;">成果鉴定时间</label></td>
						<td align="right">
							<label class="Validform_label">
								总投资额<img alt="请输入数字" title="请输入数字" src="plug-in\easyui1.4.2\themes\icons\tip.png">:
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="investedAmount" name="investedAmount" type="text"    value='${tBResultRewardPage.investedAmount}'style="width:150px;text-align:right;"
						     class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">总投资额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								起始日期:
							</label>
						</td>
						<td class="value">
							<input id="beginDate" name="beginDate" type="text" style="width: 150px" 
						     class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'endDate\')}'})"value='${tBResultRewardPage.beginDate}'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								截止日期:
							</label>
						</td>
						<td class="value">
							<input id="endDate" name="endDate" type="text" style="width: 150px" 
						     class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'beginDate\')}'})" value='${tBResultRewardPage.endDate}'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">截止日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系人<img alt="例如：张三" title="例如：张三" src="plug-in\easyui1.4.2\themes\icons\tip.png">:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="contacts" name="contacts" type="text"  datatype="byterange" max="36" min="1" style="width: 150px" class="inputxt"  value='${tBResultRewardPage.contacts}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人</label>
						</td>
						<td align="right"> 
							<label class="Validform_label">
								联系电话<img alt="例如：1390000000" title="例如：1390000000" src="plug-in\easyui1.4.2\themes\icons\tip.png">:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="contactPhone" name="contactPhone" type="text" style="width: 150px" class="inputxt" value='${tBResultRewardPage.contactPhone}' datatype="n1-30">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系电话</label>
						</td>
					</tr>
                    <tr>
                    <td align="right">
                      <label class="Validform_label"> 主要完成人<img alt="例如：张三，王五，**，完整名单" title="例如：张三，王五，**，完整名单" src="plug-in\easyui1.4.2\themes\icons\tip.png">: <font color="red">*</font></label>
                    </td>
                    <td class="value" colspan="3">
                      <input id="finishUserid" name="finishUserid" type="hidden" value="${tBResultRewardPage.finishUserid}">
                      <input id="finishUsername" name="finishUsername" type="text" datatype="*" 
                      	style="width: 420px" class="inputxt" readonly="readonly" value="${tBResultRewardPage.finishUsername}">
                      <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" 
                        isclear="true" inputTextname="finishUserid,finishUsername,finishDepartid,finishDepartname" 
                        idInput="finishUserid" mode="multiply" ></t:chooseUser>
                      <span class="Validform_checktip"></span> 
                      <label class="Validform_label" style="display: none;">主要完成人</label></td>
                    </tr>
                    <tr>
                    <td align="right">
                      <label class="Validform_label"> 主要完成单位<img alt="例如：海军工程大学，航天科技四院，船舶重工701所，完整名单" title="例如：海军工程大学，航天科技四院，船舶重工701所，完整名单" src="plug-in\easyui1.4.2\themes\icons\tip.png">: <font color="red">*</font></label>
                    </td>
                    <td class="value" colspan="3">
                      <input id="finishDepartid" name="finishDepartid" type="hidden" value="${tBResultRewardPage.finishDepartid}">
                      <input id="finishDepartname" name="finishDepartname" type="text" style="width: 420px" class="inputxt" datatype="*" readonly="readonly" value="${tBResultRewardPage.finishDepartname}">
                      <span class="Validform_checktip"></span> 
                      <label class="Validform_label" style="display: none;">主要完成单位</label></td>
                    </tr>
                    <tr>
                   <%--  <td align="right">
                      <label class="Validform_label"> 项目类型: </label>
                    </td>
                    <td class="value" colspan="3">
                      <input id="projectType_hidden" type="hidden" value="${tBResultRewardPage.projectType}" >
                      <input id="projectType" name="projectType" style="width: 426px;">
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">项目类型</label>
                    </td>
                    </tr> --%>
                    <tr>
                    <td align="right">
						<label class="Validform_label"> 创新点: </label>
						</td>
						<td class="value" colspan="3">
						    <textarea id="innovationPoint" name="innovationPoint" rows="3" style="width: 70%;" datatype="*0-1000" class="inputxt" >${tBResultRewardPage.innovationPoint}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创新点</label>
						</td>
                    </tr>
                    <tr>
                    <td align="right">
						<label class="Validform_label"> 成果简介<img alt="200字以内" title="200字以内" src="plug-in\easyui1.4.2\themes\icons\tip.png">: </label>
						</td>
						<td class="value" colspan="3">
						    <textarea id="summary" name="summary" rows="3" style="width: 70%;" datatype="*0-200" class="inputxt" >${tBResultRewardPage.summary}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">成果简介</label>
						</td>
                    </tr>
                    <tr>
						<td align="right">
							<label class="Validform_label">
								我校在项目中的贡献:
							</label>
						</td>
						<td class="value" colspan="3">
							<textarea rows="3" style="width: 70%;" id="hgdDevote" name="hgdDevote" datatype="byterange" max="800" min="0">${tBResultRewardPage.hgdDevote}</textarea>
						</td>
					</tr>
                    <tr>
						<td align="right">
							<label class="Validform_label">
								外协单位及主要贡献:
							</label>
						</td>
						<td class="value" colspan="3">
							<textarea rows="3" style="width: 70%;" id="deptMajorContribute" name="deptMajorContribute" >${tBResultRewardPage.deptMajorContribute}</textarea>
						</td>
					</tr>
                    <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${tBResultRewardPage.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width: 360px;" id="fileShow">
                  <c:forEach items="${tBResultRewardPage.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload"  buttonText="添加文件" formData="bid,projectId"
                    uploader="commonController.do?saveUploadFilesToFTP&businessType=resultReward" dialog="false" auto="true" onUploadSuccess="uploadSuccess"></t:upload>
                </div>
              </td>
            </tr>
			</table>
		</t:formvalid>
    <script type="text/javascript">
    $(function(){
    	initTab();
    })
  //初始化成员表格
	function initTab(){
		var toolbar = [];
// 		var localUrl = $('#localPanelUrl').val();
// 		if(localUrl.indexOf("load=detail") == -1){//不包含
		if(true){
			toolbar = [{
	            iconCls : 'icon-add',
	            text : '添加',
	            handler : function() {
	                $('#memberList').datagrid('appendRow',{
	                    memberName:'',
	                    workUnit:'',
	                    memo:''
	                });
	            }
	        },
	        {
	            iconCls : 'icon-remove',
	            text : '删除',
	            handler : function() {
	                var selected = $("#memberList").datagrid("getSelected");
					var index = $("#memberList").datagrid('getRowIndex', selected);
					$("#memberList").datagrid("deleteRow",index);
	            }
	        }];
		}
	    $('#memberList').datagrid({
	        title:'外协单位及主要贡献',
	        fitColumns : true,
	        nowrap : true,
	        striped : true,
	        remoteSort : false,
	        idField : 'id',
	        editRowIndex:-1,
	        toolbar : toolbar,
	        columns : [ [ 
	         {
	            field : 'id',
	            title : 'id',
	            width : 100,
	            hidden : true
	        },{
	            field : 'finishDepartname',
	            title : '外协单位',
	            width : 100,
	            editor:{
	                type:'validatebox',
	                options:{
	                    required:true,
	                    validType:'maxLength[500]'
	                }
				}
	        }, {
	            field : 'finishUsername',
	            title : '主要贡献',
	            width : 100,
	            editor:{
	                type:'validatebox',
	                
	                options:{
	                    required:true,
	                    validType:'maxLength[500]'
	                }
				}
	        }
	         ] ],
	        onDblClickRow:function(rowIndex, rowData){
	            $(this).datagrid('beginEdit', rowIndex);
	
	            },
	            onBeforeEdit : function(rowIndex, rowData) {
	
	            },
	            onAfterEdit : function(rowIndex, rowData) {
	
	            },
	            pagination : false,
	            rownumbers : true,
	            onLoadSuccess : function() {
	            }
	        });
	
	    loadData();

    }
    
    function loadData(){
      //加载数据
        var id = $("#id").val();
        if (id != "") {
            var x_url = "tBResultRewardController.do?getFinishUserByReward&id=" + id;
            $('#memberList').datagrid('options').url = x_url;
            setTimeout(function() {
                $('#memberList').datagrid('load');
            }, 0);

        }
    }
    
    </script>
 </body>
  <script src = "webpage/com/kingtake/project/resultreward/tBResultReward.js"></script>
  <script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
  <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>	