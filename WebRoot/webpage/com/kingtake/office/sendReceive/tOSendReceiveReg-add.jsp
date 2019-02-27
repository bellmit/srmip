<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<title>收发文登记</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
function parse(data){
    	var parsed = [];
        	$.each(data.rows,function(index,row){
        		parsed.push({data:row,result:row,value:row.id});
        	});
		return parsed;
}
	
function undertakeContactNameReceiveParse(data){
	var d = $.parseJSON(data);
	var parsed = [];
    	$.each(d,function(index,row){
    		parsed.push({data:row,result:row,value:row.USERID});
    	});
	return parsed;
}
/**
 * 选择后回调 
 * 
 * @param {Object} data
 */
function callBack(data) {
	$("#office").val(data.unitName);
	$("#receiveUnitName1").val(data.unitName);
	$("#contact").val(data.contact);
	$("#contactPhone").val(data.contactPhone);
}
 
function undertakeContactNameReceiveCallBack(data) {
	$("#undertakeContactId").val(data.USERID);
	$("#undertakeContactName").val(data.REALNAME);
	
	$("#undertakeDeptId").val(data.ORGID);
	$("#undertakeDeptName").val(data.DEPARTNAME);
	
	if(data.officePhone != ''){
		$("#undertakeTelephone").val(data.OFFICEPHONE);
	}
	else if(data.mobilePhone != ''){
		$("#undertakeTelephone").val(data.MOBILEPHONE);
	}
}

 /**
  * 每一个选择项显示的信息
  * 
  * @param {Object} data
  */
function formatItem(data) {
	return data.unitName;
}
  
  function undertakeContactNameReceiveFormatItem(data) {
	  if(data.DEPARTNAME != null){
		  return data.REALNAME+"("+data.DEPARTNAME+")";
	  }
	  else{
		  return data.REALNAME;
	  }
	}
  
  function getSerialNum(){
	  var ckType ="";
		var temp = document.getElementsByName("type");
		   for(var i=0;i<temp.length;i++){
		      if(temp[i].checked){
		    	  ckType = temp[i].value;
		      }
		   }
	  var businessCode = ckType;
	  $('#businessCode').val(businessCode);
	  $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : 'tOSendReceiveRegController.do?getSerialNum&businessCode='+businessCode,// 请求的action路径
			success : function(data) {
				var d = $.parseJSON(data);
				$('#currentNum').val(d.obj);
				$('#filesId').val(d.obj);
			}
		});
  }
  
  
  function checkFileNum(){
	  var mergeFileNum = $('#fileNumPrefix').val()+"20"+$('#fileNumYear').val()+$('#fileNum').val()
	  var flag = true;
	  $.ajax({
		  	async : false,
			cache : false,
			type : 'POST',
			data :{'mergeFileNum':mergeFileNum},
			url : 'tOSendReceiveRegController.do?checkFileNum',// 请求的action路径
			success : function(data) {//存在返回false 不存在返回true
				var d = $.parseJSON(data);
				flag = d.obj;
				if(!flag){//公文编号存在，提示
					$.Showmsg('公文编号已经存在，请重新输入！');
				}
			}
	  });
	  if(!flag){
	      return false;
	  }
	  //保存正文
	  if(OFFICE_CONTROL_OBJ){
	  		var result = OFFICE_CONTROL_OBJ.SaveToURL("<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&businesskey=tOReceiveBill&cusPath=office&id="+$('#contentFileId').val(),"EDITFILE");
	  		result = $.parseJSON(result);
	  		if(result.success){
	  			$('#contentFileId').val(result.obj.id);
	  		}else{
	  			alert("保存失败，请刷新页面后重新操作！");
	  		}
	  	}
	  var eauditFlag = $("input:checked[name='eauditFlag']").val();
	  if(eauditFlag=='1'){
	  /* var contentFlag = checkContent();
	  	if(!contentFlag){
	  		return false;
	  	} */
	  }
      return flag;
  }
  
 /*  function checkContent(){
		if($('#contentFileId').val()){
			return true;
		}
		$.Showmsg("请添加正文！");
		return false;
	} */
  
  $(function(){
	  $('input:radio[name="type"]').change( function(){
		  getSerialNum();
	  });
	  initDeptTree();
	  
	  //手动修改承办人清空承办人ID
	  $('#undertakeContactName').change(function(){
			$("#undertakeContactId").val("");
		})
	  
  });
  
  
    function uidChange() {
        var uid = $('#undertakeContactId').val();
        $.ajax({
            cache : false,
            url : 'tPmProjectMemberController.do?getContactPhone',
            data : {
                'uid' : uid
            },
            success : function(data) {
                var d = $.parseJSON(data);
                $('#undertakeTelephone').val(d.obj.userContact);
            }
        });
    }

    function addTemplateToMain() {
        $('#officecontrol').attr('style', 'display:block;height:800px;');
        OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
    	OFFICE_CONTROL_OBJ.width="100%";
    	OFFICE_CONTROL_OBJ.height="95%";
        addPDFPlugin();
        var registerType = $("#registerType").val();
        if(registerType=='1'){//发文
        var realPath = $('#realPath').val();
        if (realPath) {
                TANGER_OCX_openFromUrl('<%=rootPath%>/' + realPath, false);
                OFFICE_CONTROL_OBJ.FileSave = false;
            } else {
                alert("此公文种类模板不存在，请在收发文模板管理中上传模板！");
            }
        }
    }

    //初始化单位树
    function initDeptTree() {
        $("#cc").combotree({
            url : 'departController.do?getDepartTree',
            width : '155',
            height : '20',
            multiple : true,
            onCheck : function() {
                var orgTexts = $("#cc").combotree("getText");
                $("#office").val(orgTexts);
            },
            onLoadSuccess : function(node, data) {
                var orgTexts = $("#office").val();
                if (orgTexts != "") {
                    $("#cc").combotree("setText", orgTexts);
                }
            }
        });
    }
    
  	//需要电子阅批
    function ydzyp(){
        $("#yzbj").show();
    }
    
    //不电子阅批
    function ndzyp(){
        $("#yzbj").hide();
    }
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOSendReceiveRegController.do?doAdd" tiptype="1" beforeSubmit="checkFileNum" callback="@Override uploadFile">
		<input id="id" name="id" type="hidden" value="${tOSendReceiveRegPage.id }">
		<input id="generationFlag" name="generationFlag" type="hidden" value="${tOSendReceiveRegPage.generationFlag}">
		<input id="registerType" name="registerType" type="hidden" class="inputxt"  value="${registerType}">
		<input id="businessCode" name="businessCode" type="hidden" class="inputxt">
		<input id="currentNum" name="currentNum"  type="hidden" class="inputxt">
        <input id="realPath" name="realPath" type="hidden">
        <input id="contentFileId" name="contentFileId" type="hidden" value="${tOSendReceiveRegPage.contentFileId}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">公文标题:<font color="red">*</font></label></td>
				<td class="value" colspan="5"><input id="title" name="title" type="text" style="width: 70%" class="inputxt" value='${tOSendReceiveRegPage.title}' datatype="byterange" max="200" min="1">
				<c:if test="${registerType==0}"><t:codeTypeSelect name="type" type="radio" codeType="1" code="SFWLX" id="type" defaultVal="YW"></t:codeTypeSelect></c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文标题</label></td>
			</tr>
        <c:choose>
        <c:when test="${registerType==0}">
          <tr>
            <td align="right"><label class="Validform_label">
                密级:<font color="red">*</font>
              </label></td>
            <td class="value"><t:codeTypeSelect id="securityGrade" name="securityGrade" defaultVal="${tOSendReceiveRegPage.securityGrade}" type="select" code="XMMJ" codeType="0"></t:codeTypeSelect>
            <input id="referenceNum" name="referenceNum" type="text" style="width: 40px;margin-left: 10px;" class="inputxt" value='${tOSendReceiveRegPage.referenceNum}' placeholder="期限" datatype="byterange" max="10" min="0"><img alt="密级期限，不必填" title="密级期限，不必填" src="plug-in\easyui1.4.2\themes\icons\tip.png">
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label></td>
            <td align="right"><label class="Validform_label">
                公文编号:
              </label></td>
            <td class="value" colspan="3"><input id="fileNumPrefix" name="fileNumPrefix" type="text" datatype="*" ignore="ignore"
                style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOSendReceiveRegPage.fileNumPrefix}'> ﹝20<input
                id="fileNumYear" name="fileNumYear" datatype="*1-2" ignore="ignore" type="text" style="width: 20px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt"
                value='${tOSendReceiveRegPage.fileNumYear}'>﹞ <input id="fileNum" name="fileNum" datatype="*1-20" type="text" ignore="ignore"
                style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOSendReceiveRegPage.fileNum}'>号 <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文编号</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label">
                公文种类: <font color="red">*</font>
              </label></td>
            <td class="value"><t:codeTypeSelect name="regType" type="select" codeType="1" code="GWZL" id="regType" defaultVal="${tOSendReceiveRegPage.regType}"></t:codeTypeSelect> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文种类</label></td>
            <td align="right"><label class="Validform_label">
                收文编号: <font color="red">*</font>
              </label></td>
            <td class="value"><input id="filesId" name="filesId" datatype="*1-10" type="text" validType="t_o_send_receive_reg,files_Id,id" errormsg="收文编号重复，请重新获取" style="width: 150px"
                class="inputxt" value='${tOSendReceiveRegPage.filesId}' > <a class="easyui-linkbutton" href="javascript:getSerialNum();" data-options="plain:true" icon="icon-edit">获取收文编号</a> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">收文编号</label></td>

          </tr>
        </c:when>
        <c:otherwise>
        <tr>
        <td align="right"><label class="Validform_label">
                密级:<font color="red">*</font>
              </label></td>
            <td class="value"><t:codeTypeSelect id="securityGrade" name="securityGrade" defaultVal="${tOSendReceiveRegPage.securityGrade}" type="select" code="XMMJ" codeType="0"></t:codeTypeSelect>
            <input id="referenceNum" name="referenceNum" type="text" style="width: 50px;margin-left: 10px;" class="inputxt" value='${tOSendReceiveRegPage.referenceNum}' placeholder="期限" datatype="byterange" max="10" min="0"><img alt="密级期限，不必填" title="密级期限，不必填" src="plug-in\easyui1.4.2\themes\icons\tip.png">
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label></td>
              <td align="right"><label class="Validform_label">
                公文种类: <font color="red">*</font>
              </label></td>
            <td class="value"><t:codeTypeSelect name="regType" type="select" codeType="1" code="GWZL" id="regType" defaultVal="${tOSendReceiveRegPage.regType}"></t:codeTypeSelect> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文种类</label></td>
                </tr>
        </c:otherwise>
        </c:choose>
      
			<c:choose>
			<c:when test="${registerType==0}">
			<tr>
				<td align="right"><label class="Validform_label">收文日期:<font color="red">*</font></label></td>
				<td class="value" colspan="5"><input id="registerDate" name="registerDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${tOSendReceiveRegPage.registerDate}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">收文日期</label></td>
           </tr>
           <tr>
				<td align="right"><label class="Validform_label">来文单位:<font color="red">*</font></label></td>
				<td class="value"><t:autocomplete minLength="2" dataSource="commonController.do?getAutoList" closefun="close" 
								valueField="unitName" searchField="unitPinyin,unitName" labelField="unitName,contact,contactPhone" 
								parse="parse" formatItem="formatItem" result="callBack" name="receiveUnitName1" 
								entityName="TBSendDocUnitEntity" datatype="*1-250" maxRows="10" errormsg="数据不存在,请重新输入" 
								value="unitName" label="${tOSendReceiveRegPage.office}"></t:autocomplete>
							<a id="addBtn" class="easyui-linkbutton l-btn l-btn-plain" style="vertical-align: top;" 
								title="添加来文单位" data-options="iconCls:'icon-add',plain:true" 
								onclick="add('来文单位','tBSendDocUnitController.do?goAdd',null,300,180);"></a>
							<input id="office" name="office" type="hidden" style="width: 150px" class="inputxt" 
								value='${tOSendReceiveRegPage.office}'> 
							<span class="Validform_checktip"></span> 
							<label class="Validform_label" style="display: none;">来文单位</label></td>
				<td align="right"><label class="Validform_label">联系人:</label></td>
				<td class="value"><input id="contact" name="contact" type="text" style="width: 150px" class="inputxt" value='${tOSendReceiveRegPage.contact}' datatype="byterange" max="30" min="0"><span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">联系人</label></td>
				<td align="right"><label class="Validform_label">联系电话:</label></td>
				<td class="value"><input id="contactPhone" name="contactPhone" type="text" style="width: 150px" class="inputxt" value='${tOSendReceiveRegPage.contactPhone}' datatype="n0-30"><span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">联系电话</label></td>
			</tr>
          <tr>
            <td align="right"><label class="Validform_label">
                承办人:<font color="red">*</font>
              </label></td>
            <td class="value">
            	<script type="text/javascript">
            		$(document).ready(function() {
            			$("#undertakeContactName").autocomplete("commonController.do?getAutoUserList",{
            				max: 50,
            				minChars: 1,
            				width: 200,
            				scrollHeight: 300,
            				matchContains: true,
            				autoFill: false,
            				extraParams:{
            					maxRows : 10,
            					searchField : "name_pinyin,realname",
            					trem: getUndertakeContactNameTremValue
            				},
            				parse:function(data){
            					return undertakeContactNameReceiveParse.call(this,data);
            				},
            				formatItem:function(row, i, max){
            					return undertakeContactNameReceiveFormatItem.call(this,row, i, max);
            				} 
            			}).result(function (event, row, formatted) {
            				undertakeContactNameReceiveCallBack.call(this,row); 
            			}); 
            		});
            		function getUndertakeContactNameTremValue(){
            			return $("#undertakeContactName").val();
            		}
            	</script>
            	<input type="text" id="undertakeContactName" name="undertakeContactName" style="width:150px;" nullmsg="" errormsg="数据不存在,请重新输入" value="${tOSendReceiveRegPage.undertakeContactName}">
            	<input id="undertakeContactId" name="undertakeContactId" type="hidden" value="${tOSendReceiveRegPage.undertakeContactId}" datatype="*" nullMsg="承办人不是从用户/下拉列表中选择">

                <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName"
                inputTextname="undertakeContactId,undertakeContactName,undertakeDeptId,undertakeDeptName" isclear="true" idInput="undertakeContactId" departIdInput="undertakeDeptId" mode="single" fun="uidChange"></t:chooseUser>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办人</label></td>
            <td align="right"><label class="Validform_label">
                承办单位:<font color="red">*</font>
              </label></td>
            <td class="value"><input id="undertakeDeptId" name="undertakeDeptId" type="hidden" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeDeptId}"> <input id="undertakeDeptName"
                name="undertakeDeptName" type="text" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeDeptName}" datatype="*"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办单位</label>
            </td>

            <td align="right"><label class="Validform_label">
                电话: <font color="red">*</font>
              </label></td>
            <td class="value"><input id="undertakeTelephone" name="undertakeTelephone" type="text" datatype="n1-16" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeTelephone}"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">电话</label></td>
          </tr>
        </c:when>
			<c:otherwise>
			<tr>
				<td align="right"><label class="Validform_label">发文日期:<font color="red">*</font></label></td>
				<td class="value"><input id="registerDate" name="registerDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${tOSendReceiveRegPage.registerDate}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发文日期</label></td>
				<td align="right"><label class="Validform_label">发文单位:<font color="red">*</font></label></td>
				<td class="value"><input id="cc" type="text" width="155" ><input id="office" name="office" type="hidden" datatype="*" value="${tOSendReceiveRegPage.office}">
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发文单位</label></td>
                <td></td>
			</tr>
          <tr>
            <td align="right"><label class="Validform_label">
                承办人:<font color="red">*</font>
              </label></td>
            <td class="value">
            <script type="text/javascript">
            		$(document).ready(function() {
            			$("#undertakeContactName").autocomplete("commonController.do?getAutoUserList",{
            				max: 5,
            				minChars: 1,
            				width: 200,
            				scrollHeight: 100,
            				matchContains: true,
            				autoFill: false,
            				extraParams:{
            					maxRows : 10,
            					searchField : "name_pinyin,realname",
            					trem: getUndertakeContactNameTremValue
            				},
            				parse:function(data){
            					return undertakeContactNameReceiveParse.call(this,data);
            				},
            				formatItem:function(row, i, max){
            					return undertakeContactNameReceiveFormatItem.call(this,row, i, max);
            				} 
            			}).result(function (event, row, formatted) {
            				undertakeContactNameReceiveCallBack.call(this,row); 
            			}); 
            		});
            		function getUndertakeContactNameTremValue(){
            			return $("#undertakeContactName").val();
            		}
            	</script>
            <input id="undertakeContactId" name="undertakeContactId" type="hidden" value="${tOSendReceiveRegPage.undertakeContactId}" datatype="byterange" max="36" min="1"> 
            <input id="undertakeContactName" name="undertakeContactName" type="text" style="width: 150px" class="inputxt"  value="${tOSendReceiveRegPage.undertakeContactName}"> <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName"
                inputTextname="undertakeContactId,undertakeContactName,undertakeDeptId,undertakeDeptName" isclear="true" idInput="undertakeContactId" departIdInput="undertakeDeptId" mode="multiply" fun="uidChange"></t:chooseUser>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办人</label></td>
            <td align="right"><label class="Validform_label">
                承办单位:<font color="red">*</font>
              </label></td>
            <td class="value"><input id="undertakeDeptId" name="undertakeDeptId" type="hidden" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeDeptId}"> <input id="undertakeDeptName"
                name="undertakeDeptName" type="text" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeDeptName}" datatype="*"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办单位</label>
            </td>
            <td align="right"><label class="Validform_label">
                电话: <font color="red">*</font>
              </label></td>
            <td class="value"><input id="undertakeTelephone" name="undertakeTelephone" type="text" datatype="*1-200" style="width: 150px" class="inputxt" value="${tOSendReceiveRegPage.undertakeTelephone}"> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">电话</label></td>
          </tr>
        </c:otherwise>
			</c:choose>
			<tr>
				<td align="right"><label class="Validform_label"> 份数:<font
						color="red">*</font></label></td>
				<td class="value"><input id="count" name="count"
					datatype="n1-3" type="text" style="width: 150px" class="inputxt"
					value='${tOSendReceiveRegPage.count}'> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">份数</label></td>
				<td align="right"><label class="Validform_label">是否电子审批:
				</label></td>
				<td class="value"><input id="eauditFlagY" name="eauditFlag"
					type="radio" onclick="ydzyp()" style="width: 20px; height: 20px;"
					value="1"
					<c:if test="${tOSendReceiveRegPage.eauditFlag ne '0'}">checked="true"</c:if>>是
					<input id="eauditFlagN" name="eauditFlag" type="radio"
					onclick="ndzyp()" style="width: 20px; height: 20px;" value="0"
					<c:if test="${tOSendReceiveRegPage.eauditFlag eq '0'}">checked="true"</c:if>>否
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">是否电子审批</label></td>
					<c:if test="${registerType==0}">
				<td align="right"><label class="Validform_label">是否加急:
				</label></td>
				<td class="value"><input id="zyjbY" name="zyjb"
					type="radio"  style="width: 20px; height: 20px;"
					value="1"
					<c:if test="${tOSendReceiveRegPage.zyjb eq '1'}">checked="true"</c:if>>是
					<input id="zyjbN" name="zyjb" type="radio"
					 style="width: 20px; height: 20px;" value="0"
					<c:if test="${tOSendReceiveRegPage.zyjb ne '1'}">checked="true"</c:if>>否
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">是否加急</label></td>
					</c:if>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">关键字:</label></td>
				<td class="value" colspan="3"><input id="keyname" name="keyname" type="text" style="width: 85%;" class="inputxt" value='${tOSendReceiveRegPage.keyname}'  datatype="byterange" max="50" min="0"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关键字</label></td>
                <td></td>
			</tr>
      <c:if test="${registerType==1}">
        <tr>
          <td align="right"><label class="Validform_label">是否联合发文: </label></td>
		  <td class="value">
               <input id="isUnionY" name="isUnion" type="radio" style="width: 20px;height: 20px;" value="1" <c:if test="${tOSendReceiveRegPage.isUnion eq '1'}">checked="true"</c:if> >是 
               <input id="isUnionN" name="isUnion" type="radio" style="width: 20px;height: 20px;" value="0" <c:if test="${tOSendReceiveRegPage.isUnion ne '1'}">checked="true"</c:if> >否
               <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否联合发文</label></td>
          <td align="right"><label class="Validform_label"> 公文编号:</label></td>
          <td class="value" colspan="3"><input id="fileNumPrefix" name="fileNumPrefix" type="text"
              style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOSendReceiveRegPage.fileNumPrefix}'> ﹝20<input
              id="fileNumYear" name="fileNumYear" datatype="*1-2" ignore="ignore" type="text" style="width: 20px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;"
              class="inputxt" value='${tOSendReceiveRegPage.fileNumYear}'>﹞ <input id="fileNum" name="fileNum" datatype="*1-20" ignore="ignore" type="text"
              style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOSendReceiveRegPage.fileNum}'>号 <a class="easyui-linkbutton" href="javascript:getFileNum();" data-options="plain:true" icon="icon-edit">获取公文编号</a><span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文编号</label></td>
        </tr>
      </c:if>
      <tr>
				<td align="right" style="height: 40px;"><label class="Validform_label"> 关联项目类型: </label></td>
				<td class="value" colspan="3"><input id="projectType" name="projectType" style="width: 256px;"  readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关联项目类型</label></td>
                <c:choose>
                <c:when test="${registerType==0}">
				<td align="right"><label class="Validform_label"> 关联项目: </label></td>
				<td class="value" colspan="3">
                <input id="projectRelaId" name="projectRelaId" type="hidden" value="${tOSendReceiveRegPage.projectRelaId}">
                <input id="projectRelaName" name="projectRelaName" type="text" style="width: 256px;"  readonly="readonly" value="${tOSendReceiveRegPage.projectRelaName}">
                <t:choose url="tPmProjectController.do?projectSelect" width="1000px" height="460px" left="10%" top="10%"
                name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="projectRelaId,projectRelaName"
                isclear="true"></t:choose> 
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关联项目</label></td>
                </c:when>
                <c:otherwise>
                <td align="right"><label class="Validform_label"> 关联项目: </label></td>
                <td class="value" colspan="3">
                <input id="projectRelaId" name="projectRelaId" type="hidden" value="${tOSendReceiveRegPage.projectRelaId}">
                <input id="projectRelaName" name="projectRelaName" type="text" style="width: 256px;"  readonly="readonly" value="${tOSendReceiveRegPage.projectRelaName}">
                <t:chooseProject inputId="projectRelaId" inputName="projectRelaName" icon="icon-search" title="关联项目" isclear="true" ></t:chooseProject>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关联项目</label></td>
                </c:otherwise>
                </c:choose>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label></td>
				<td class="value" colspan="3"><textarea id="remark" name="remark" style="width: 87%;" rows="5" class="inputxt" datatype="byterange" max="400" min="0">${tOSendReceiveRegPage.remark}</textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
                <td></td>
			</tr>
      <tr>
        <td align="right"><label class="Validform_label">正文：<!-- <font id="yzbj" color="red">*</font> --></label></td>
        <td class="value" colspan="3"><a class="easyui-linkbutton" data-options="plain:true" onclick="addMain()" href="#officecontrol" icon="icon-add"><c:if test="${empty contentFileId}">添加正文</c:if></a></td>
     </tr>
		 <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td colspan="3" class="value"><input type="hidden" value="${tOSendReceiveRegPage.id}" id="bid" name="bid" />
          <table style="max-width:92%;">
	        <c:forEach items="${tOSendReceiveRegPage.certificates }" var="file"  varStatus="idx">
	          <tr>
	            <td><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOSendReceiveRegPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
	            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
	            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
	          </tr>
	        </c:forEach>
	      </table>
	      <div>
		    <div class="form" id="filediv"></div>
		    <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
		    	formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOSendReceiveReg">
		  	</t:upload>
		  </div>
	    </td>
      </tr>
		</table>
	</t:formvalid>
    <div id="officecontrol"  style="width: 1px;height: 1px;">
<!--    <a class="easyui-linkbutton" href="javascript:saveToUrl()" icon="icon-save">保存到服务器</a> -->
    <object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="1px" height="1px">
        <param name="IsUseUTF8URL" value="-1">
        <param name="IsUseUTF8Data" value="-1">
        <param name="BorderStyle" value="1">
    <param name="BorderColor" value="14402205">
        <param name="TitlebarColor" value="15658734">
    <param name="TitlebarTextColor" value="0">
    <param name="MenubarColor" value="14402205">
    <param name="MenuButtonColor" VALUE="16180947">
    <param name="MenuBarStyle" value="3">
    <param name="MenuButtonStyle" value="7">   
    <param name="Caption" value="">
    <param name="ProductCaption" value="<%=ProductCaption%>">
    <param name="ProductKey" value="<%=ProductKey%>">
    <SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
</object>
 <script src="webpage/com/kingtake/officeonline/officeControl.js"></script>
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
  setFileOpenedOrClosed(false);
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
  setFileOpenedOrClosed(true);
</script>
</div>
</body>
<script src="webpage/com/kingtake/office/sendReceive/tOSendReceiveReg.js?${tm}"></script>