<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>鉴定委员会成员</title>
<t:base type="jquery,easyui,tools,autocomplete"></t:base>
</head>
<body>
<t:formvalid formid="formobj" dialog="true"
	layout="table" action="" tiptype="1" callback="">
	<input id="id" name="id" type="hidden" >
		
	<table cellpadding="0" cellspacing="0" class="formtable" style="margin:auto;">
		<tr>
			<td align="right"><label class="Validform_label">姓名: <font color="red">*</font></label></td>
			<td class="value" >
				<input id="memberName" name="memberName" type="text" style="width: 256px"
					value='${appraisalProject.memberName}' >
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">技术职称: <font color="red">*</font></label></td>
			<td class="value" >
				<input id="memberPosition" name="memberPosition" class="easyui-combobox" type="text" style="width: 262px"
					value='${appraisalProject.memberPosition}' data-options="valueField:'CODE',textField:'NAME',url:'tBCodeTypeController.do?typeCombo&codeType=1&code=ZHCH',editable:false,panelHeight:100">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">技术职称</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">专业: <font color="red">*</font></label></td>
			<td class="value" >
				<input id="memberProfession" name="memberProfession"  type="text" style="width: 262px"
					value='${appraisalProject.memberProfession}' class="easyui-combotree" 
					data-options="valueField:'id',textField:'text',url:'tBCodeTypeController.do?typeComboTree&codeType=1&code=MAJOR',editable:false,panelHeight:100">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">专业</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">工作单位: <font color="red">*</font></label></td>
			<td class="value" >
				<input id="workUnit" name="workUnit" type="text" style="width: 256px"
					value='${appraisalProject.workUnit}' >
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">工作单位</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">备注: </label></td>
			<td class="value" >
				<textarea id="memo" name="memo" type="text" style="width: 256px;height: 50px;" cols="3" rows="3">${appraisalProject.memo}</textarea>
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
    $(function(){
    	$("#memberName").autocomplete("tBAppraisalMemberController.do?getAutoList", {
            max : 5,
            minChars : 0,
            width : 200,
            scrollHeight : 100,
            matchContains : false,
            autoFill : false,
            extraParams : {
                featureClass : "P",
                style : "full",
                maxRows : 10
            },
            parse : function(data) {
                return parse.call(this, data);
            },
            formatItem : function(row, i, max) {
                return formatItem.call(this, row, i, max);
            }
        }).result(function(event, row, formatted) {
            $(this).val(row.memberName);
            $("#memberPosition").combobox("setValue", row.memberPosition);
            $("#memberProfession").combotree("setValue", row.memberProfession);
            $("#workUnit").val(row.workUnit);
        });
    	
    	//自动赋值
    	if(frameElement.api.data!=undefined){
    	   $("#memberName").val(frameElement.api.data.memberName);
           $("#memberPosition").combobox("setValue", frameElement.api.data.memberPosition);
           $("#memberProfession").combotree("setValue", frameElement.api.data.memberProfession);
           $("#workUnit").val(frameElement.api.data.workUnit);
           $("#id").val(frameElement.api.data.id);
           $("#memo").val(frameElement.api.data.memo);
    	}
    });
    
    /**
     * 每一个选择项显示的信息
     * 
     * @param {Object} data
     */
    function formatItem(data) {
        return data.memberName + "," + data.workUnit;
    }
     
     function parse(data) {
         var parsed = [];
         $.each(data.rows, function(index, row) {
             parsed.push({
                 data : row,
                 result : row,
                 value : row.id
             });
         });
         return parsed;
     }
    
	function getFormData(){
		var param = {};
		var memberName = $("#memberName").val();
		if(memberName==""){
			$.messager.alert("警告","姓名不能为空！");
			param["check"]=false;
			return param;
		}
		if(memberName.length>25){
			$.messager.alert("警告","姓名长度不能超过25！");
			param["check"]=false;
			return param;
		}
		param['memberName'] = memberName;
		
		var memberPosition = $("#memberPosition").combobox("getValue");
		if(memberPosition==""){
			$.messager.alert("警告","技术职称不能为空！");
			param["check"]=false;
			return param;
		}
		param['memberPosition'] = memberPosition;
		var memberPositionName = $("#memberPosition").combobox("getText");
		param['memberPositionName'] = memberPositionName;
		
		var memberProfession = $("#memberProfession").combobox("getValue");
		if(memberProfession==""){
			$.messager.alert("警告","专业不能为空！");
			param["check"]=false;
			return param;
		}
		param['memberProfession'] = memberProfession;
		var memberProfessionName = $("#memberProfession").combobox("getText");
		param['memberProfessionName'] = memberProfessionName;
		
		var workUnit = $("#workUnit").val();
		if(workUnit==""){
			$.messager.alert("警告","工作单位不能为空！");
			param["check"]=false;
			return param;
		}
		if(workUnit.length>150){
			$.messager.alert("警告","工作单位长度不能超过150！");
			param["check"]=false;
			return param;
		}
		param['workUnit'] = workUnit;
		
		var memo = $("#memo").val();
		if(memo.length>150){
			$.messager.alert("警告","备注长度不能超过150！");
			param["check"]=false;
			return param;
		}
		param['memo'] = memo;
		param["check"]=true;
		return param;
	}
</script>