<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant,com.kingtake.project.entity.manage.TPmProjectEntity,com.kingtake.common.constant.SrmipConstants" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.legendFont {
	font-size: 12px;
	font-weight: bold;
}
.head{
 height:25px;
 vertical-align: middle;
 line-height: 20px;
}
</style>
<title>项目基本信息表</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<%
  request.setAttribute("no", SrmipConstants.NO);
  request.setAttribute("projectStatusMap", ProjectConstant.projectStatusMap);
  String showMode = request.getParameter("showMode");
%>
<script type="text/javascript">
  /**
   * 检测项目编号唯一性
   */
  function checkNo() {
    var flag = true;
    var projectNo = $("#projectNo").val();
    var id = $("#id").val();

    if (projectNo) {
      $.ajax({
        type : 'post',
        url : 'tPmProjectController.do?checkProjectNo',
        async : false,
        data : {
          projectNo : projectNo,
          id : id
        },
        success : function(result) {
          result = $.parseJSON(result);
          console.info(result);
          flag = result.success;
        }
      });
    }
    if (!flag) {
      $.Showmsg("项目编号不能重复!");
    }
    return flag;
  }

  function callback(data) {
    var submitFlag = $("#submitFlag").val();
    if(submitFlag=="1"){
    	if(data.success){
    		$("#submitFlag").val("");
    		submitProj();
    	}else{
           $.messager.alert('提示',data.msg);
    	}
    }else{
       $.messager.alert('提示',data.msg);
    }
    
    uploadFile(data);
  }

  $(function() {
	  
	 /*   $("#jflx").combotree({
			url : 'tBXmlbController.do?getJflx',
			valueField : 'id',
			textField : 'text',
			onChange : function() {
				var jflx = $("#jflx").combobox('getValue');
				$("#jflxId").val(jflx);
			}
		});
 */
		$("#feeType").combotree({
			url : 'tBXmlbController.do?getFeeType',
			valueField : 'id',
			textField : 'text',
			onChange : function() {
				var feeType = $("#feeType").combobox('getValue');
				$("#feeTypeId").val(feeType);
			}
		});

		$("#xmlb").combotree({
		    url : 'tPmProjectController.do?getXmlb',
			valueField : 'id',
			textField : 'text',
			onChange : function() {
				var xmlb = $("#xmlb").combobox('getValue');
				$("#xmlbId").val(xmlb);
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : 'tBXmlbController.do?getXmlbAll',// 请求的action路径
					dataType : 'json',
					data : {
						'id' : xmlb
					},
					success : function(data) {
						if(data.msg=="1"){
							tip('不能选择母项目！');
							$("#zgdw").val("");
							$("#jflx").combotree('setValue', "");
							$("#jflxId").val("");
							$("#xmlx").val("");
							$("#xmxz").val("");
							$("#xmly").val("");
							$("#xmml").val("");
						}else{
							$("#zgdw").val(data.zgdw);
							$("#jflx").combotree('setValue', data.jflxStr);
							$("#jflxId").val(data.jflx);
							$("#xmlx").val(data.xmlx);
							$("#xmxz").val(data.xmxz);
							$("#xmly").val(data.xmly);
							$("#xmml").val(data.xmml);
						}
					}
				});
			 }
		});
		
		
		//预算类型
		 $("#yslx").combotree({
				url : 'tBXmlbController.do?getbudgetCategory',
				valueField : 'id',
				textField : 'text',
				onChange : function() {
					var yslx = $("#yslx").combobox('getValue');
					$("#yslxId").val(yslx);
				}
		 });
		 
		 
		 //间接费计算方式
	   	 $("#jjfjsfs").combotree({
			 data: [
			        {id: '1',text: '根据直接费计算间接费'}, 
				 	{id: '2',text: '根据总经费计算绩效'},
				 	{id: '3',text: '无间接费'},
				 	{id: '4',text: '合同直接约定间接费'},
				 	{id: '5',text: '科研管理部门下拨'},
				 	{id: '6',text: '总经费的1/21'}
			 ]
		 });
		
	  
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });

    //如果页面是详细查看页面,隐藏保存按钮
    if (location.href.indexOf("load=detail") != -1) {
      $("#saveBtn").css("display", "none");
      $("#subBtn").css("display", "none");
    }

    var projectType = $("#projectType").val();
    $("#projectType").combotree({
      url : 'tPmProjectController.do?getProjectType&projectTypeId='+projectType,
      onChange : function() {
        var projectType = $("#projectType").combobox('getValue');
        $("#projectTypeId").val(projectType);
        $.ajax({
          async : false,
          cache : false,
          type : 'POST',
          url : 'tBProjectTypeController.do?getFundType',// 请求的action路径
          dataType : 'json',
          data : {
            'id' : projectType
          },
          success : function(data) {
            var fund = data.fund;
            var approval = data.approval;
            var office = data.office;
            $("#feeTypeName").val(fund.fundsName);
            $("#feeType").val(fund.id);
            $("#planContractFlagName").val(approval.name);
            $("#planContractFlag").val(approval.code);
          }
        });
      }
    });

    //项目组成员
    $("#tPmProjectMemberListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
    
    $('#modelBackground').combobox({    
	    url:'tBCodeTypeController.do?typeCombo&codeType=1&code=XHBJ',    
	    valueField:'NAME',    
	    textField:'NAME',
	    onLoadSuccess : function() {
	    	var modelBackgroundStr = $('#modelBackgroundStr').val();
	        if(modelBackgroundStr != ""){
	        	$('#modelBackground').combobox('setValue', modelBackgroundStr);
	        }
	    }
	});  
    
//     if($("#approvalAuthorityValue").val() != ""){
//     	var approvalAuthorityValue = $("#approvalAuthorityValue").val();
//     	$("#approvalAuthority option[value=" + approvalAuthorityValue + "]").attr("selected", true);
//     } 
	
    if($("#parentProjectId").val() == ""){
    	$('#parentProjectLabel').attr('style', 'display:none;');
    }
  });

  function godetail(id) {
    createdetailwindow('项目基本信息', 'tPmProjectController.do?goUpdateForResearchGroup&load=detail&id=' + id, "1100px", "600px");
  }

  function setValue() {
      var managerDepartCode = $("#manageDepartCode").val();
      if(managerDepartCode!=""){
         var manageDepart=$("#manageDepartCode").find("option:selected").text();
         $("#manageDepart").val(manageDepart);
      }
//     return true;
    
    var budgetAmount = $('#budgetAmount').numberbox('getValue');
	if(budgetAmount == ""){
		$('#budgetAmount').numberbox('setValue', 0);
	}
  }
  
  function selectProject(value) {
		if (value == 1) {
			$('#merge').attr('style', 'display:blank;');
			$('#mergePName').attr('datatype', '*');
		} else {
			$('#merge').attr('style', 'display:none;');
			$('#mergePName').removeAttr('datatype');
			$('#mergePId').val('');
			$('#mergePName').val('');
		}
	}
  
  function selectSchoolCooperation(value) {
		if (value == 1) {
			$('#parentProjectLabel').attr('style', 'display:blank;');
		} else {
			$('#parentProjectLabel').attr('style', 'display:none;');
			$('#parentProjectId').val('');
			$('#parentProjectName').val('');
		}
	}
  
  //查看修改意见
  function viewMsg(){
      var msgText = $("#msgText").val();
      $.messager.alert('修改意见',msgText);    
  }
  
  //提交确认
  function submitDepart(){
	  $("#submitFlag").val("1");
	  $("#saveBtn").click();
	  //closeWin();
  }
  
  //提交确认
  function submitProj(){
      var id = $("#id").val();
      /* var officeStaffRealname = $("#officeStaffRealname").val();
      if(officeStaffRealname==""){
    	  $.messager.alert("警告","请选择机关主管参谋！");
    	  return false;
      } */
      if(typeof(windowapi) == 'undefined'){
    	  doSub(id);
      }else{
    	  doSub(id);
      }
  }
  
  //提交
  function doSub(id){
	  var bmlx = $("#bmlx").val();
	  var auditStatus = $("#auditStatus").val();
	    if(auditStatus==0){
	    	$.messager.confirm('确认','您确认将该项目发送审核吗？',function(r){    
	    	    if (r){    
	    	    	sendStart(id,auditStatus,bmlx);
	    	    }    
	    	});
	    }else{
	    	sendStart(id,auditStatus,bmlx);
	    }
      <%-- $.ajax({
          url:'tPmProjectController.do?doSubmit',
          data:{id:id},
          cache:false,
          type:'POST',
          dataType:'json',
          success:function(data){
        	  var bmlx = $("#bmlx").val();
        	  var title = "审核";
        	  var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
        				'&apprId=' + id +
        				'&bmlx=' + bmlx +
        				'&apprType=<%=ApprovalConstant.APPR_TYPE_XM%>';
        	  var width = 900;
        	  var height = window.top.document.body.offsetHeight-100;
        	  sendApprDialog2(title, url, width, height, id,'0','<%=ApprovalConstant.APPR_TYPE_XM%>' );
          }
      }); --%>
  }

  function sendStart(id, auditStatus,bmlx){
		var title = "审核";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
				'&apprId=' + id +
				'&bmlx=' + bmlx +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_XM%>';
		var width = 900;
		var height = window.top.document.body.offsetHeight-100;
		
		if(auditStatus == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("审核流程已完成，不能再发送审核");
		}else if(auditStatus == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑审核信息！");
		}
		//debugger;
		sendApprDialog(title, url, width, height, id,auditStatus,'<%=ApprovalConstant.APPR_TYPE_XM%>' );
	}
  
//来源单位自动联想
  function sourceUnitParse(data){
  	var d = $.parseJSON(data);
  	var parsed = [];
      	$.each(d,function(index,row){
      		parsed.push({data:row,result:row,value:row.NAME});
      	});
  	return parsed;
  }

  function sourceUnitFormatItem(data) {
  	return data.NAME;	  
  }

  function sourceUnitCallBack(data) {
  	$("#sourceUnit").val(data.NAME);
  }
</script>
</head>
<body>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px;overflow-y: auto" >
  <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td align="center" bgcolor="#E5EFFF" style="height:25px;">
        <b>${tPmProjectPage.projectName}-项目基本信息表<c:if test="${tPmProjectPage.approvalStatus eq '2'}"><a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看修改意见</a></c:if></b>
      </td>
      <td width="15%" align="right" bgcolor="#E5EFFF">
        <input id="applying" value="${applying }" type="hidden" />
        <c:if test="${empty tPmProjectPage.approvalStatus or tPmProjectPage.approvalStatus eq '2' }">
          <input id="saveBtn" type="button" value="保存">
          <input id="subBtn" type="button" value="提交" onclick="submitDepart()">
        </c:if>
      </td>
    </tr>
  </table>
  <textarea id="msgText" rows="1" cols="1" style="display:none;">${tPmProjectPage.msgText}</textarea>
  <input id="submitFlag" type="hidden" > 
  <t:formvalid postonce="false" formid="formobj" dialog="true" layout="table" action="tPmProjectController.do?doUpdate" tiptype="showValidMsg" beforeSubmit="setValue" callback="@Override callback">
    <input id="id" name="id" type="hidden" value="${tPmProjectPage.id }">
    <input id="auditStatus" type="hidden" value="${tPmProjectPage.auditStatus }">
    <input id="approvalStatus" name="approvalStatus" type="hidden" value="${tPmProjectPage.approvalStatus }">
    <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA;">
      <!-- border: ridge; border-radius: 10px; -->
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">基本信息</span>
      </legend>
      <div align="left">
        <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td align="right" width="90px">
              <label class="Validform_label"> 项目名称: </label>
              <font color="red">*</font>
            </td>
            <td class="value" colspan="3">
              <input id="projectName" name="projectName" type="text" datatype="*2-30" style="width: 440px" class="inputxt" value='${tPmProjectPage.projectName}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目名称</label>
            </td>
           <td align="right" width="95px">
              <label class="Validform_label"> 项目密级:</label><font color="red">*</font>
            </td>
            <td class="value">
              <t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" code="XMMJ" codeType="0" defaultVal="${tPmProjectPage.secretDegree}" extendParam="{style:'width:150px'}"></t:codeTypeSelect>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目密级</label>
            </td>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 申报编号: </label>
            </td>
            <td class="value">
              <input id="cxm" name="cxm" type="text" ignore="ignore" datatype="s2-20" style="width: 160px" class="inputxt" 
                value='${tPmProjectPage.cxm}' readonly="readonly">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">申报编号</label>
            </td>
            <%-- <td align="right" width="105px">
              <label class="Validform_label"> 会计编码: </label>
            </td>
            <td class="value">
              <input id="accountingCode" name="accountingCode" type="text" datatype="s2-250" ignore="ignore" style="width: 160px" class="inputxt" value='${tPmProjectPage.accountingCode}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">会计编码</label>
            </td> --%>
       
          
          <!-- 是否重大专项  默认否 -0-->
           <input id="greatSpecialFlag" name="greatSpecialFlag" defaultVal="0" type="hidden" value="${tPmProjectPage.greatSpecialFlag}">
          <%--
            <td align="right">
              <label class="Validform_label"> 重大专项 : </label>
            </td>
            <td class="value">
              <c:if test="${empty tPmProjectPage.greatSpecialFlag }">
                <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect>
              </c:if>
              <c:if test="${!empty tPmProjectPage.greatSpecialFlag }">
                <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.greatSpecialFlag}"></t:codeTypeSelect>
              </c:if>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">是否重大专项 </label>
            </td>
            --%>
            
            <td align="right">
              <label class="Validform_label"> 关键词: </label>
            </td>
            <td class="value" colspan="3">
              <input id="accordingNum" name="accordingNum" type="text" style="width: 100px" class="inputxt" readonly="readonly" value="${tPmProjectPage.accordingNum}">
              <c:if test="${tPmProjectPage.approvalStatus ne '1' }">
                <t:choose url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px" height="450px" icon="icon-search" title="收发文列表" textname="mergeFileNum"
                  inputTextname="accordingNum" isclear="true"></t:choose>
              </c:if>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">关键词</label>
            </td>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 项目组长: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="projectManager" name="projectManager" type="text" ignore="ignore" datatype="s2-16" style="width: 160px" class="inputxt" datatype="s2-16"
                value='${tPmProjectPage.projectManager}' readonly="readonly">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目组长</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 承研部门: </label>
              <font color="red">*</font>
            </td>
            <td class="value" colspan="3">
              <input name="devDepartDepartname" id="devDepartDepartname" value="${tPmProjectPage.devDepart.departname}">
              <script type="text/javascript">
                              //选择承研单位时，将承研单位的父单位加入责任单位
                              $(function() {
                                $('#devDepartDepartname').combotree({
                                  url : 'departController.do?getDepartTree&lazy=false',
                                  width : '165',
                                  height : '27',
                                  multiple : false,
                                  onSelect : function(record) {
                                    $("#developerDepartId").val(record.id);

                                    $.ajax({
                                        url:'departController.do?getBmlx',
                                        data:{id:record.id},
                                        cache:false,
                                        type:'POST',
                                        dataType:'json',
                                        success:function(data){
                                        	$("#bmlx").val(data.msg);
                                        }
                                    });
                                    
                                    var tree = $('#devDepartDepartname').combotree('tree');
                                    var parent = tree.tree('getParent', record.target);
                                    $("#dutyDepartId").val(parent.id);
                                    $("#dutyDepartDepartname").combotree('setValue', parent.text);
                                  }
                                });
                              });
                            </script>
              <input id="developerDepartId" name="devDepart.id" type="hidden" value="${tPmProjectPage.devDepart.id}">
              <input id="bmlx" name="devDepart.bmlx" type="hidden" value='${tPmProjectPage.devDepart.bmlx}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">承研部门</label>
            </td>          
            <%-- <td align="right">
              <label class="Validform_label"> 项目状态:</label>
              <font color="red">*</font>
            </td>
            <td class="value" colspan="5">
              <input type="text" style="width: 160px" class="inputxt" readonly="readonly" value='${projectStatusMap[tPmProjectPage.projectStatus]}'>
              <input id="projectStatus" name="projectStatus" type="hidden" class="inputxt" value='${tPmProjectPage.projectStatus}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目状态</label>
            </td>  --%> 
          </tr>	
          <tr>
            <td align="right">
              <label class="Validform_label"> 责任部门: </label>
            </td>
            <td class="value">
              <t:departComboTree name="" id="dutyDepartDepartname" idInput="dutyDepartId" lazy="false" value="${tPmProjectPage.dutyDepart.departname}" width="150"></t:departComboTree>
              <input id="dutyDepartId" name="dutyDepart.id" type="hidden" class="inputxt" value='${tPmProjectPage.dutyDepart.id}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">责任部门</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 负责人电话: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="managerPhone" name="managerPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.managerPhone}'
                datatype="*1-30">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">负责人电话</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 起始日期: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="beginDate" name="beginDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"
                value='<fmt:formatDate value='${tPmProjectPage.beginDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">起始日期</label>
            </td>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="contact" name="contact" type="text" datatype="s2-16" style="width: 160px" class="inputxt" value='${tPmProjectPage.contact}'><img alt="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" title="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" src="plug-in\easyui1.4.2\themes\icons\tip.png">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">联系人</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 联系人电话: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="contactPhone" name="contactPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.contactPhone}'
                datatype="*1-30">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">联系人电话</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 截止日期: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="endDate" name="endDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"
                value='<fmt:formatDate value='${tPmProjectPage.endDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">截止日期</label>
            </td>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 项目简介:</label>
              <font color="red">*</font>
            </td>
            <td colspan="5" class="value">
              <textarea id="projectAbstract" placeholder="简要介绍项目的背景，字数不超过100字"  name="projectAbstract" type="text" datatype="*" style="width: 98%; height: 80px;background-color: rgba(228, 228, 228, 0.21);" class="inputxt">${tPmProjectPage.projectAbstract}</textarea>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目简介</label>
            </td>
          </tr>
        </table>
      </div>
    </fieldset>

    <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA;">
      <!-- border: ridge; border-radius: 10px; -->
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #228B22">其他信息</span>
      </legend>
      <div>
        <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0" class="formtable">
        		<tr>
            		<td align="right" width="130px;"><label class="Validform_label"> 项目类型: </label><font color="red">*</font></td>
		            <td class="value">
		            	<input id="xmml" name='xmml' type="text" readonly="readonly" class="inputxt" datatype="*" value="${tPmProjectPage.xmml}" style='width: 120px;' />
		            	<img alt="该项属于项目子类的母项，无需填写，选择项目子类后自动带出" title="该项属于项目子类的母项，无需填写，选择项目子类后自动带出" src="plug-in\easyui1.4.2\themes\icons\tip.png">
		            	<span class="Validform_checktip"></span>
		            	<label class="Validform_label" style="display: none;">项目类型</label>
		            </td>
		            <td align="right" style='width: 90px;'><label class="Validform_label"> 项目子类: </label><font color="red">*</font></td>
		            <td class="value">
		            	<input id="xmlb" value="${tPmProjectPage.xmlb.xmlb}" style='width: 130px;'>
		            	<input id="xmlbId" name="xmlb.id" type="hidden" datatype="*" value="${tPmProjectPage.xmlb.id}">
		            	<span class="Validform_checktip"></span> 
		            	<label class="Validform_label" style="display: none;">项目类型</label>
		            </td>
		            <td align="right">
		            	<label class="Validform_label"> 经费类型: </label>
		            	<font color="red">*</font>
		            </td>
		            <td class="value">
		              <%-- 	<input id="jflx" value="${tPmProjectPage.jflx.jflxmc}" style='width: 150px;'>
		            	<input id="jflxId" name="jflx.id" type="hidden" value="${tPmProjectPage.jflx.id}">
		            	<span class="Validform_checktip"></span> 
		            	<label class="Validform_label" style="display: none;">经费类型</label>
		            	--%>
		            	   <input id="feeType" value="${tPmProjectPage.feeTypeStr}">
			            	<input id="feeTypeId" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}" datatype="*">
			            	<span class="Validform_checktip"></span> 
			            	<label class="Validform_label" style="display: none;">经费类型</label>
		            </td>
	          </tr>
	          
	          <tr>
	            <%-- 
	          	<td align="right"><label class="Validform_label"> 主管单位: </label><font color="red">*</font></td>
	            <td class="value">
	            	<input id="zgdw" name='zgdw' type="text" class="inputxt" datatype="*" value="${tPmProjectPage.zgdw}">
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">主管单位</label>
	            </td>
	            --%>
	            
	            <td align="right"><label class="Validform_label"> 项目属性: </label><font color="red">*</font></td>
	            <td class="value">
	            	<t:codeTypeSelect id="xmlx" name="xmlx" type="select" code="XMLX" codeType="0" defaultVal="${tPmProjectPage.xmlx}"></t:codeTypeSelect>
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">项目属性</label>
	            </td>
	            <td align="right"><label class="Validform_label"> 项目性质: </label><font color="red">*</font></td>
	            <td class="value">
	            	<t:codeTypeSelect id="xmxz" name="xmxz" type="select" code="XMXZ" codeType="0" defaultVal="${tPmProjectPage.xmxz}"></t:codeTypeSelect>
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">项目性质</label>
	            </td>
	            <td align="right" style='width: 90px;'><label class="Validform_label"> 预算类型: </label><font color="red">*</font></td>
	            <td class="value">
	            	<input id="yslx" value="${tPmProjectPage.yslx}" style='width: 130px;'>
	            	<input id="yslxId" name="yslx" type="hidden" datatype="*" value="${tPmProjectPage.yslx}">
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">预算类型</label>
	            </td>
	            
	          </tr>
	          
	          <tr>
	            <td align="right">
	            	<label class="Validform_label"> 项目来源: </label>
	            	<font color="red">*</font>
	            </td>
	            <td class="value">
	            	<t:codeTypeSelect id="xmly" name="xmly" type="select" code="XMLY" codeType="0" defaultVal="${tPmProjectPage.xmly}"></t:codeTypeSelect>
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">项目来源</label>
	            </td>
	            <td align="right"><label class="Validform_label"> 任务指定标志: </label></td>
	            <td class="value">
	            	<t:codeTypeSelect id="rwzd" name="rwzd" type="select" code="RWZD" codeType="0" defaultVal="${tPmProjectPage.rwzd}"></t:codeTypeSelect>
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">任务指定标志</label>
	            </td>
	            <td align="right" style='width: 90px;'><label class="Validform_label"> 间接费计算方式: </label><font color="red">*</font></td>
	            <td class="value">
	            	<input id="jjfjsfs" value="${tPmProjectPage.jjfjsfs}" style='width: 130px;'>
	            	<input id="jjfjsfsId" name="jjfjsfs" type="hidden" datatype="*" value="${tPmProjectPage.jjfjsfs}">
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">间接费计算方式</label>
	            </td>
          	</tr>
        
        
          <%-- <tr>
            <td align="right">
              <label class="Validform_label"> 项目类型: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="projectType" name="projectType.id" style="width: 165px; height:27px;" value="${tPmProjectPage.projectType.id}">
              <input id="projectTypeId" type="hidden" value="${tPmProjectPage.projectType.id}" datatype="*">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目类型</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 经费类型: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input id="feeTypeName" type="text" style="width: 150px" class="inputxt" readonly value="${tPmProjectPage.feeType.fundsName}" datatype="*">
              <input id="feeType" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">经费类型</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 计划/合同标志: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input type="text" id="planContractFlagName" readonly style="width: 140px" class="inputxt" class="inputxt" value="${planContractFlag}" datatype="*">
              <input type="hidden" id="planContractFlag" name="planContractFlag" value="${tPmProjectPage.planContractFlag}">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">计划/合同标志</label>
            </td>
          </tr> --%>
          <tr>
<!--             <td align="right"> -->
<!--               <label class="Validform_label"> 子类型: </label> -->
<!--             </td> -->
<!--             <td class="value"> -->
<%--               <input id="subType" name="subType" type="text" style="width: 160px" class="inputxt" datatype="byterange" max="40" min="0" ignore="ignore" value='${tPmProjectPage.subType}'> --%>
<!--               <span class="Validform_checktip"></span> -->
<!--               <label class="Validform_label" style="display: none;">子类型</label> -->
<!--             </td> -->
			<td align="right">
              <label class="Validform_label"> 是否需要鉴定: </label>
            </td>
            <td class="value">
              <t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.appraisalFlag}"></t:codeTypeSelect>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">是否需要鉴定</label>
              <input type="hidden" name="mergeFlag" id="mergeFlag" value="0"> 
            </td>
            
			<td align="right"><label class="Validform_label"> 是否校内协作: </label></td>
            <td class="value">
            	<c:if test="${empty tPmProjectPage.schoolCooperationFlag}">
            		<t:codeTypeSelect id="schoolCooperationFlag" name="schoolCooperationFlag" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="0"></t:codeTypeSelect>
            	</c:if>
            	<c:if test="${!empty tPmProjectPage.schoolCooperationFlag}">
            		<t:codeTypeSelect id="schoolCooperationFlag" name="schoolCooperationFlag" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="${tPmProjectPage.schoolCooperationFlag}"></t:codeTypeSelect>
            	</c:if>
            	<img alt="如选择是，则必须上传校内协作附件" title="如选择是，则必须上传校内协作附件" src="plug-in\easyui1.4.2\themes\icons\tip.png">
             	<span class="Validform_checktip"></span>
              	<label class="Validform_label" style="display: none;">是否校内协作</label>
              	
              	<table style="max-width:92%;">
		        <c:forEach items="${tPmProjectPage.certificates }" var="file"  varStatus="idx" >
		          <c:if test="${file.businessType eq 'tPmProject_schoolCooperationFlag'}">
		          <tr>
		            <td><a href="javascript:void(0)"  onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tPmProjectPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}.${file.extend}</a></td>
		            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">[下载]</a></td>
		            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">[删除]</a></td>
		          </tr>
		          </c:if>
		        </c:forEach>
		        </table>
		        
		         <div style="display:none; background-color: #e5efff;">
				      	<div id="file_upload_schoolCooperationFlag"></div>
					    <div class="form" id="filediv_schoolCooperationFlag"></div>
				 </div>
            </td>
            <td align="right"><label class="Validform_label"> 大学比例: </label></td>
            <td class="value">
            	<input style='width: 130px;' id="dxbl" name="dxbl" type="text" class="inputxt" value='${tPmProjectPage.dxbl}'> 
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">大学比例</label>
            </td>
            
            <tr id="parentProjectLabel" style="display:none;">
          	<td align="right"><label class="Validform_label"> 所属母项目申报码: </label></td>
            <td class="value">
             <input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}"> 
             <input id="parentProjectName" type="hidden" style="width: 300px" class="inputxt" value="${tPmProjectPage.parentProjectName}"> 
             <input id="parentProjectNo" name="parentPmProject.projectNo" type="text" value="${tPmProjectPage.parentPmProject.projectNo}"> 
             <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属母项目申报码</label>
            </td>
            <td align="right"><label class="Validform_label"> 间接费: </label></td>
            <td class="value" colspan="3">
            	<c:if test="${empty tPmProjectPage.indirect}">
            		<t:codeTypeSelect id="indirect" name="indirect" code="JJF" codeType="0" type="radio" defaultVal="1"></t:codeTypeSelect>
            	</c:if>
            	<c:if test="${!empty tPmProjectPage.indirect}">
            		<t:codeTypeSelect id="indirect" name="indirect" code="JJF" codeType="0" type="radio" defaultVal="2"></t:codeTypeSelect>
            	</c:if>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">间接费</label></td>
          </tr>
            <%-- 
            <td align="right"><label class="Validform_label"> 是否合并其他项目: </label></td>
            <td class="value"><t:codeTypeSelect id="mergeFlag" name="mergeFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"
                extendParam="{onclick:'selectProject(this.value);'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label></td>
           --%>
            <%-- <td align="right">
              <label class="Validform_label"> 总经费(元): </label>              
            </td>
            <td class="value">
              <input id="allFee" name="allFee" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','" style="width: 140px; text-align: right;"
                value='${tPmProjectPage.allFee}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">总经费</label>
            </td> --%>
          </tr>
          <tr id="merge" style="display: none;">
            <td align="right"><label class="Validform_label"> 合并项目:<font color="red">*</font></label></td>
            <td class="value" colspan="3" ><input type="hidden" id="mergePId" name="mergePId"> <textarea wrap="off" id="mergePName" name="mergePName" type="text"
                style="width: 300px; height: 40px;" class="inputxt" readonly="readonly"></textarea>
              <t:chooseProject inputId="mergePId" inputName="mergePName" title="合并项目" isclear="true"></t:chooseProject> <%-- <t:choose url="tPmProjectController.do?projectMultipleSelect" name="processtree" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="mergePId,mergePName" isclear="true"></t:choose> --%></td>
          </tr>
          <tr>
            <c:if test="${!empty mergeProjList}">
              <td align="right" width="15%">
                <label class="Validform_label"> 合并项目: </label>
              </td>
              <td colspan="5" class="value">
                <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                  <tr>
                    <td colspan="3" class="value" width="85%">
                      <c:forEach items="${mergeProjList}" var="s">
                        <a href="javascript:godetail('${s.id}');">${s.projectName}</a>
                      </c:forEach>
                    </td>
                  </tr>
                </table>
              </td>
            </c:if>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 申报金额: </label></td>
            <td class="value"><input id="budgetAmount" name="budgetAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
              style="width: 180px; text-align: right;" value="${tPmProjectPage.budgetAmount}">&nbsp;元 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报金额</label></td> 
          
            <td align="right"><label class="Validform_label"> 立项依据标志: </label></td>
            <td class="value">
            	<t:codeTypeSelect id="lxyj" name="lxyj" type="select" code="LXYJ" codeType="0" defaultVal="${tPmProjectPage.lxyj}"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">立项依据标志</label>
            </td>
            <td align="right"><label class="Validform_label"> 责任单位比例: </label></td>
            <td class="value">
            	<input style='width: 130px;' id="zrdwbl" name="zrdwbl" type="text" class="inputxt" value='${tPmProjectPage.zrdwbl}'> 
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">责任单位比例</label>
            </td>
          
            <%-- 
            <td align="right">
              <label class="Validform_label"> 经费单列: </label>
            </td>
            <td class="value">
              <input id="feeSingleColumn" name="feeSingleColumn" type="text" style="width: 140px" class="inputxt" value='${tPmProjectPage.feeSingleColumn}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">经费单列</label>
            </td>
            --%>
            
            <%-- 
            <td align="right">
              <label class="Validform_label"> 分管部门: </label>
            </td>
            <td class="value">
              <t:codeTypeSelect name="manageDepartCode" type="select" codeType="1" code="FGBM" id="manageDepartCode" labelText="请选择" defaultVal="${tPmProjectPage.manageDepartCode}"></t:codeTypeSelect>
              <input id="manageDepart" name="manageDepart" value="${tPmProjectPage.manageDepart}" type="hidden" datatype="s2-20" ignore="ignore">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">分管部门</label>
            </td>
            --%>
            
<!--             <td align="right"> -->
<!--               <label class="Validform_label"> 科目代码: </label> -->
<!--             </td> -->
<!--             <td class="value"> -->
<%--             	<input id="subject_code" name="subject_code" type="text" style="width: 200px" class="inputxt" value="${tPmProjectPage.subject_code}"> <span class="Validform_checktip"></span>  --%>
<!--             	<label class="Validform_label" style="display: none;">科目代码</label> -->
<!--             </td> -->
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 来源计划/合同名称: </label>
            </td>
            <td class="value">
              <input id="planContractName" name="planContractName" type="text" datatype="byterange" max="1000" min="0" class="inputxt" value='${tPmProjectPage.planContractName}'><img alt="甲方合同名称" title="甲方合同名称" src="plug-in\easyui1.4.2\themes\icons\tip.png">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">来源计划/合同名称</label>
            </td>
            <c:if test="${entryType eq 'JG'}">
          		<td align="right"><label class="Validform_label"> 审批权限: </label><font color="red">*</font></td>
          		<td class="value">      
          		<t:codeTypeSelect name="approvalAuthority" type="select" codeType="1" code="SPQX" id="approvalAuthority" labelText="请选择" extendParam="{datatype:'*'}"
              		defaultVal="${tPmProjectPage.approvalAuthority}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审批权限</label>   		
            	</td>
          	  </c:if>
          	  <c:if test="${entryType eq 'KTZ'}">
          		<td align="right"><label class="Validform_label"> 审批权限: </label></td>
          		<td class="value">      
          		<t:codeTypeSelect name="approvalAuthority" type="select" codeType="1" code="SPQX" id="approvalAuthority" labelText="请选择"
              		defaultVal="${tPmProjectPage.approvalAuthority}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审批权限</label>   		
            	</td>
          	  </c:if>
          		<td align="right"><label class="Validform_label"> 审批权限: </label></td>
          		<td class="value">
          			<t:codeTypeSelect name="approvalAuthority" type="select" codeType="1" code="SPQX" id="approvalAuthority" labelText="请选择"
	               defaultVal="${tPmProjectPage.approvalAuthority}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审批权限</label>
            	</td>
            	
            	<td align="right"><label class="Validform_label"> 承研单位比例: </label></td>
	            <td class="value">
	            	<input style='width: 130px;' id="cydwbl" name="cydwbl" type="text" class="inputxt" value='${tPmProjectPage.cydwbl}'> 
	            	<span class="Validform_checktip"></span> 
	            	<label class="Validform_label" style="display: none;">承研单位比例</label>
	            </td>
            	
            <%-- <td align="right">
              <label class="Validform_label"> 合同签订日期: </label>
            </td>
            <td class="value">
              <input id="contractDate" name="contractDate" type="text" ignore="ignore" datatype="date" style="width: 140px" class="Wdate" onClick="WdatePicker()"
                value='<fmt:formatDate value='${tPmProjectPage.contractDate}' type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">合同签订日期</label>
            </td> --%>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label"> 来源计划/合同文号: </label>
            </td>
            <td class="value">
              <input id="planContractRefNo" name="planContractRefNo" type="text" style="width: 160px" datatype="byterange" max="1000" min="0" class="inputxt" value='${tPmProjectPage.planContractRefNo}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">来源计划/合同文号</label>
            </td>
            <td align="right"><label class="Validform_label"> 型号背景: </label></td>
          		<td class="value">
          			<input type="hidden" id="modelBackgroundStr" value='${tPmProjectPage.modelBackground}'>
                	<select id="modelBackground" class="easyui-combobox" name="modelBackground" style="width:200px; height:27px;">   
 
					</select><img alt="可输可选" title="可输可选" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            </td>
            
            <td align="right"><label class="Validform_label"> 项目组比例: </label></td>
            <td class="value">
            	<input style='width: 130px;' id="xmzbl" name="xmzbl" type="text" class="inputxt" value='${tPmProjectPage.xmzbl}'> 
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">项目组比例</label>
            </td>
            
            
             <%--
            <td align="right">
              <label class="Validform_label"> 外来编号: </label>
            </td>
            <td class="value">
              <input id="outsideNo" name="outsideNo" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.outsideNo}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">外来编号</label>
            </td>
            --%>
           </tr>
           <tr> 
            
            <td align="right"><label class="Validform_label"> 舰艇舷号: </label></td>
            <td class="value"><input id="shipNumber" name="shipNumber" type="text" datatype="*0-5"
              		style="width: 200px; text-align: right;" value="${tPmProjectPage.shipNumber}"><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">舰艇舷号</label>
            </td>
            
            <td align="right"><label class="Validform_label"> 是否联合校外单位申报: </label></td>
            <td class="value" colspan="3">
            
            <c:if test="${empty tPmProjectPage.lhsb}">
            		<t:codeTypeSelect id="lhsb" name="lhsb" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="0"></t:codeTypeSelect>
           	</c:if>
           	<c:if test="${!empty tPmProjectPage.lhsb}">
           		<t:codeTypeSelect id="lhsb" name="lhsb" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="${tPmProjectPage.lhsb}"></t:codeTypeSelect>
           	</c:if>
            
            
            <img alt="如选择是，则必须上传联合申报附件" title="如选择是，则必须上传联合申报附件" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否联合校外单位申报</label>
            
            <table style="max-width:92%;">
		        <c:forEach items="${tPmProjectPage.certificates }" var="file"  varStatus="idx" >
		          <c:if test="${file.businessType eq 'tPmProject_lhsb'}">
		          <tr>
		            <td><a href="javascript:void(0)"  onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tPmProjectPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}.${file.extend}</a></td>
		            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">[下载]</a></td>
		            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">[删除]</a></td>
		          </tr>
		          </c:if>
		        </c:forEach>
		      </table>
              
              <div style="display:none; background-color: #e5efff;">
		      	<div id="file_upload_lhsb"></div>
			    <div class="form" id="filediv_lhsb"></div>
			  </div>
            </td>
            
          </tr>
          <tr>
          	<td align="right"><label class="Validform_label"> 配套情况: </label></td>
                <td class="value"><t:codeTypeSelect name="matchSituation" type="select" codeType="1" code="PTQK" id="matchSituation" labelText="请选择"
                    defaultVal="${tPmProjectPage.matchSituation}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">配套情况</label></td>
            <%-- <td align="right">
              <label class="Validform_label"> 项目来源: </label>
            </td>
            <td class="value">
              <input id="projectSource" name="projectSource" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.projectSource}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目来源</label>
            </td> --%>
            
            <td align="right">
              <label class="Validform_label"> 主管部门/甲方单位: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
            	<script type="text/javascript">
            		$(document).ready(function() {
            			$("#sourceUnit").autocomplete("tPmProjectController.do?getAutoSourceUnitList",{
            				max: 50,
            				minChars: 1,
            				width: 150,
            				scrollHeight: 300,
            				matchContains: true,
            				autoFill: false,
            				extraParams:{
            					maxRows : 10,
            					searchField : "name",
            					trem: getsourceUnitTremValue
            				},
            				parse:function(data){
            					return sourceUnitParse.call(this,data);
            				},
            				formatItem:function(row, i, max){
            					return sourceUnitFormatItem.call(this,row, i, max);
            				} 
            			}).result(function (event, row, formatted) {
            				sourceUnitCallBack.call(this,row); 
            			}); 
            		});
            		function getsourceUnitTremValue(){
            			return $("#sourceUnit").val();
            		}
            	</script>
            	<input type="text" id="sourceUnit" name="sourceUnit" style="width:150px;" datatype="byterange" max="2000" min="1" nullmsg="" errormsg="数据不存在,请重新输入" value="${tPmProjectPage.sourceUnit}">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">主管部门/甲方单位</label></td>
<!--             <td class="value"> -->
<%--               <input id="sourceUnit" name="sourceUnit" type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.sourceUnit}' datatype="*"> --%>
<!--               <span class="Validform_checktip"></span> -->
<!--               <label class="Validform_label" style="display: none;">来源单位</label> -->
<!--             </td>             -->
            
          </tr>
          <%-- <tr>
            <td align="right">
              <label class="Validform_label"> 大学主管参谋: </label>
              <font color="red">*</font>
            </td>
            <td class="value">
              <input type="text" id="officeStaffRealname" readonly style="width: 120px" class="inputxt" value="${officeStaffRealname}" datatype="*">
              <input type="hidden" id="officeStaff" name="officeStaff" value='${tPmProjectPage.officeStaff}'>
              <c:if test="${tPmProjectPage.approvalStatus ne '1' }">
                <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="officeStaff" textname="id,realName" inputTextname="officeStaff,officeStaffRealname"></t:chooseUser>
              </c:if>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">大学主管参谋</label>
            </td>
            <td align="right">
              <label class="Validform_label"> 院系主管参谋: </label>
            </td>
            <td class="value">
              <input type="text" id="departStaffRealname" readonly style="width: 120px" class="inputxt" value='${departStaffRealname}'>
              <input type="hidden" id="departStaff" name="departStaff" value='${tPmProjectPage.departStaff}'>
              <c:if test="${tPmProjectPage.approvalStatus ne '1' }">
                <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="departStaff" textname="id,realName" inputTextname="departStaff,departStaffRealname"></t:chooseUser>
              </c:if>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">院系主管参谋</label>
            </td>
            </tr> --%>
           
           <tr>
	        <td align="right"><label class="Validform_label">申报书/合同草本: </label></td>
	        <td colspan="3" class="value">
	          <table style="max-width:92%;">
		        <c:forEach items="${tPmProjectPage.certificates }" var="file"  varStatus="idx" >
		          <c:if test="${file.businessType eq 'tPmProject'}">
		          <tr>
		            <td><a href="javascript:void(0)"  onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tPmProjectPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}.${file.extend}</a></td>   
		            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">[下载]</a></td>
		            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">[删除]</a></td>
		          </tr>
		          </c:if>
		        </c:forEach>
		      </table>
		      <div style="width:auto;background-color: #e5efff;">
		      	<div id="file_upload"></div>
			    <div class="form" id="filediv"></div>
			  </div>
		    </td>
	      </tr>
        </table>
        <input type="hidden" value="${tPmProjectPage.id}" id="bid" name="bid" />
        <div style="color:red;text-align: center;">注：本功能区所有上传文件操作，在点击"保存"按钮后才会真正开始上传！</div>
      </div>
    </fieldset>
  </t:formvalid>
  <!-- -------------------项目组成员-------------华丽的分割线---------------------------------- -->
  <t:datagrid name="tPmProjectMemberList" checkbox="false" fitColumns="true" 
    title="项目组成员" actionUrl="tPmProjectMemberController.do?datagrid&project.id=${tPmProjectPage.id }" 
    onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="姓名"  field="name" queryMode="single" isLike="true" query="true"  width="120"></t:dgCol>
     <t:dgCol title="性别"  field="sex" codeDict="0,SEX"   queryMode="group"  width="70"></t:dgCol>
     <t:dgCol title="出生年月"  field="birthday" formatter="yyyy-MM-dd"   queryMode="group"  width="110" align="center"></t:dgCol>
     <t:dgCol title="学位"  field="degree" codeDict="0,XWLB" query="true" queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="职称职务"  field="position"  codeDict="0,PROFESSIONAL"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="任务分工"  field="taskDivide"    queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="所属单位"  field="superUnitName"    queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="是否负责人"  field="projectManager" codeDict="0,SFBZ"   queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
     <t:dgDelOpt exp="projectManager#eq\#${no}" title="删除" url="tPmProjectMemberController.do?doDel&id={id}" />
     <t:dgToolBar title="录入" icon="icon-add" width="450" height="370" url="tPmProjectMemberController.do?goAdd&projectId=${tPmProjectPage.id }" funname="add"></t:dgToolBar>
     <t:dgToolBar title="编辑" icon="icon-edit" width="450" height="370"  url="tPmProjectMemberController.do?goUpdate&projectId=${tPmProjectPage.id }" funname="update"></t:dgToolBar>
     <t:dgToolBar title="查看" width="450" height="370" icon="icon-search" url="tPmProjectMemberController.do?goUpdate" funname="detail"></t:dgToolBar>
     <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
</body>
<script src="webpage/com/kingtake/project/manage/tPmProject.js"></script>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript">
//双击查看方法
function dblClickDetail(rowIndex, rowData){
	var title = "查看";
	var url = "tPmProjectMemberController.do?goUpdate&load=detail&id=" + rowData.id;
	createdetailwindow(title,url,null,null);
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectMemberController.do?exportXls&project.id="+$("#id").val()
			,"tPmProjectMemberList");
}

/**做成详情只读界面*/
$(function(){
	var showMode = '<%=showMode%>';
	if(showMode=="readonly"){
	   $("#saveBtn,#subBtn").hide();
		
		//$("input").attr("readonly","true");
	   $("#formobj").find("input,select,textarea").attr("disabled", "disabled").css("background-color", "#EBEBE4");
	}
});

$(function(){
	if(document.formobj.schoolCooperationFlag.value=="1"){
		$("#file_upload_schoolCooperationFlag").parent().show();
	}
	if(document.formobj.lhsb.value=="1"){
		$("#file_upload_lhsb").parent().show();
	}
});
</script>
<script src="webpage/com/kingtake/project/manage/project-edit.js"></script>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/tPmProject-uploadfile.js"></script>
