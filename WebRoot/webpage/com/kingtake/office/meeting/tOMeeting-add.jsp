<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会议登记管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
	$(function(){
  		//加载单位树下拉框
    	$("#selectDepart").combotree({
    		url:'departController.do?getDepartTree', 
    		width:155,
 			editable:false,
 			onSelect: function(rec){
 				$('#hostUnitId').val(rec.id);
	        	$('#hostUnitName').val(rec.text);
	        }
    	});
  		
    	        $("#meetingRoomName").combobox({
    	            onShowPanel:function(){
    	               var beginDate = $("#beginDate").val();
    	               var endDate = $("#endDate").val();
    	               if(beginDate==""||endDate==""){
    	                   $.messager.alert("警告","请先选择开始时间和结束时间！");
    	               }else{
    	                   $('#meetingRoomName').combobox({
    	                       loader:function(param,success,error){
    	                           $.ajax({  
    	                               url: 'tOMeetingController.do?getFreeMeetingRooms',  
    	                               dataType: 'json', 
    	                               type:'POST',
    	                               data:{"beginDate":beginDate,"endDate":endDate},
    	                               success: function(data){  
    	                                   success(data);
    	                               }
    	                       });
    	                       }
    	                   });  
    	               }
    	            }
    	        });
    }); 
	
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOMeetingController.do?doAdd" 
  	tiptype="1" tipSweep="true" beforeSubmit="valiBeforeSubmit">
				<input id="id" name="id" type="hidden" value="${tOMeetingPage.id }">
		<table style="width:100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							会议议题:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
				     	<input id="meetingTitle" name="meetingTitle" type="text" style="width:465px" class="inputxt" datatype="*1-100">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">会议议题</label>
					</td>
				</tr>
        <tr>
          <td align="right">
            <label class="Validform_label">
              开始时间:<font color="red">*</font>
            </label>
          </td>
          <td class="value">
            <input id="beginDate" name="beginDate" type="text" style="width: 150px" datatype="date" class="Wdate" 
                            onClick="WdatePicker({ dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endDate\')}'})">    
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">开始时间</label>
          </td>
          
          <td align="right">
            <label class="Validform_label">
              结束时间:<font color="red">*</font>
            </label>
          </td>
          <td class="value">
            <input id="endDate" name="endDate" type="text" style="width: 150px" datatype="date" 
                  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginDate\')}'})">    
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">结束时间</label>
          </td>
        </tr>
				<tr>	
					<td align="right">
						<label class="Validform_label">
							会&nbsp;议&nbsp;室&nbsp;:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="meetingRoomId" name="meetingRoomId" type="hidden" >
						<input id="meetingRoomName" name="meetingRoomName" class="easyui-combobox" style="width: 155px" 
							data-options=
								"valueField: 'NAME',
							        textField: 'NAME', 
							        onSelect: function(rec){
							        	$('#meetingRoomId').val(rec.ID);
							        },
						        " />     
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">会议室名称</label>
					</td>
				
					<td align="right">
						<label class="Validform_label">
							主办单位:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="hostUnitName" name="hostUnitName" type="hidden" datatype="*">
				     	<input id="hostUnitId" name="hostUnitId" type="hidden" >
				     	<input id="selectDepart" type="text">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">主办单位名称</label>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							与会单位数:
						</label>
					</td>
					<td class="value" colspan='3'>
				     	<input id="attendUnitNum" name="attendUnitNum" type="text" style="width: 150px" class="inputxt" 
				     		datatype="n0-2" ignore="ignore">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">与会单位数</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							关联项目:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan='3'>
                        <input id="projectId" name="projectId" type="hidden">
                        <input id="projectName" type="text" style="width: 150px" class="inputxt">
                        <t:chooseProject inputId="projectId"  inputName="projectName" icon="icon-search" 
                        	title="关联项目" isclear="true" mode="single"></t:chooseProject>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">关联项目</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							参会人员:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan='3'>
						<input id="attendeesId" name="attendeesId" type="hidden" >
				     	<textarea id="attendeesName" name="attendeesName" datatype="*1-500"
				     		style="width:448px;height:80px" readonly="true"></textarea><br>
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="attendeesId"
				     		textname="id,realName"  inputTextname="attendeesId,attendeesName" ></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">参会人员姓名</label>
            <label class="Validform_label" style="color:red;">[非系统内人员姓名请在备注中填写]</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							会议简介:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan='3'>
				     	<textarea id="meetingContent" name="meetingContent"  datatype="*1-1000" ignore="ignore" 
				     		style="width: 448px;height:80px"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">会议内容简介</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value" colspan='3'>
				     	<textarea id="memo" name="memo" style="width: 448px;height:40px"  
				     		datatype="*1-100" ignore="ignore"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/meeting/tOMeeting.js"></script>		