<%@ page language="java"
  import="java.util.*,com.kingtake.common.constant.ProjectConstant,com.kingtake.project.entity.manage.TPmProjectEntity"
  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目基本信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
//对表单进行校验
function checkData(){
    var beginDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    if(beginDate>endDate){
        //tip("起始日期不能大于结束日期!");
        $.Showmsg("起始日期不能大于结束日期!");
        return false;
    }
}

function callback(data){
   tip(data.msg);
   window.parent.$("#processtree").tree("reload");//刷新项目数
}

$(function(){
	 var form = document.forms[0];
     for (var i = 0; i < form.length; ++i) {
         var element = form.elements[i];
      
         if (element.name) {
             if (element.nodeName == "INPUT") {
                   if(element.name != 'return'){
                       element.disabled="true";
                     element.readOnly = true;
                   }
             }
             else if (element.nodeName == "SELECT") {
                   element.disabled = true;
             }
             else if (element.nodeName == "TEXTAREA") {
                   element.disabled = true;
             }
           }
     }  
	
    $("#saveBtn").click(function(){
        $("#btn_sub").click();
    });
    
    $("#projectType").combobox({    
        url:'tBProjectTypeController.do?getProjectTypeList',    
        valueField:'id',    
        textField:'projectTypeName',
        onLoadSuccess:function(){
            var projectType = $("#projectType").val();
            if(projectType!=""){
                $("#projectType").combobox('setValue',projectType);
            }else{
                var data = $("#projectType").combobox('getData');
                if(data.length>0){
                  $("#projectType").combobox('setValue',data[0].id);
                }
            } 
        },
        onChange:function(){
            var projectType = $("#projectType").combobox('getValue');
            $.ajax({
    			async : false,
    			cache : false,
    			type : 'POST',
    			url : 'tBProjectTypeController.do?getFundType',// 请求的action路径
    			dataType : 'json',
    			data : {'id': projectType},
    			error : function() {// 请求失败处理函数
    			},
    			success : function(data) {
    				var fund = data.fund;
    				var approval = data.approval;
    				$("#feeTypeName").val(fund.fundsName);
    				$("#feeType").val(fund.id);
    				$("#planContractFlagName").val(approval.name);
    				$("#planContractFlag").val(approval.code);
    			}
    		});
        }
    }); 
});

</script>
</head>
<body>
<!-- <div style="position:fixed; top:0; left: 0; height: 30px;width:100%; background: #E5EFFF;border-bottom: solid 1px #95B8E7;"><h1 align="center">项目基本信息表</h1><span><input id="saveBtn" type="button" style="position: fixed;right: 5px;top: 3px;"  value="保存"></span></div> -->
<!-- <br/> -->
<!-- <br/> -->
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
    action="tPmProjectController.do?doUpdate" tiptype="1" beforeSubmit="checkData" callback="@Override callback">
    <input id="id" name="id" type="hidden" value="${tPmProjectPage.id }">
    <input id="projectStatus" name="projectStatus" type="hidden" value="${tPmProjectPage.projectStatus }">
    <table style="width: 100%;" cellpadding="0" cellspacing="0" class="mytable">
      <tr>
          <td align="right"><label class="Validform_label"> 项目名称:</label></td>
          <td class="value" colspan="5">${tPmProjectPage.projectName}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label>
          </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 项目编号: </label></td>
          <td class="value">${tPmProjectPage.projectNo}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label>
          </td>
          <td align="right"><label class="Validform_label"> 分管部门: </label></td>
          <td class="value">${tPmProjectPage.manageDepart}
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">分管部门</label>
          </td>
          <td align="right"><label class="Validform_label"> 所属母项目: </label></td>
          <td class="value">
              <input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}">
              ${tPmProjectPage.parentPmProject.projectName}
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属母项目</label>
          </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 负责人:</label></td>
          <td class="value">${tPmProjectPage.projectManager}
              <span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">负责人</label>
          </td>
          <td align="right"><label class="Validform_label"> 负责人电话:</label>
          </td>
          <td class="value">${tPmProjectPage.managerPhone}
              <span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">负责人电话</label>
          </td>
          <td align="right"><label class="Validform_label"> 起止日期:</label></td>
          <td class="value">
              <fmt:formatDate value='${tPmProjectPage.beginDate}' type="date" pattern="yyyy-MM-dd"/><label class="Validform_label"> 至  </label>
              <fmt:formatDate value='${tPmProjectPage.endDate}' type="date" pattern="yyyy-MM-dd"/>
          </td>
      </tr>
      <!-- 
      
      <tr>
        <td align="right"><label class="Validform_label"> 联系人: <font color="red">*</font></label></td>
        <td class="value"><input id="contact" name="contact" type="text" datatype="s2-16" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.contact}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">联系人</label></td>
        <td align="right"><label class="Validform_label"> 联系人电话: <font color="red">*</font></label></td>
        <td class="value"><input id="contactPhone" name="contactPhone" type="text" datatype="s2-30" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.contactPhone}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">联系人电话</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 计划合同标志: </label></td>
        <td class="value">
         <input type="text" id="planContractFlagName" readonly style="width: 150px" class="inputxt" class="inputxt" value="${planContractFlag}">
         <input type="hidden" id="planContractFlag" name="planContractFlag" value="${tPmProjectPage.planContractFlag}">
        <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计划合同标志</label></td>
        <td align="right"><label class="Validform_label"> 项目类型: </label></td>
        <td class="value">
        <input id="projectType" name="projectType.id" style="width:156px;" value="${tPmProjectPage.projectType.id}">
        <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">项目类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 外来编号: </label></td>
        <td class="value"><input id="outsideNo" name="outsideNo" type="text" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.outsideNo}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">外来编号</label></td>
        <td align="right"><label class="Validform_label"> 经费类型: </label></td>
        <td class="value">
        <input id="feeTypeName" type="text" style="width: 150px" class="inputxt" readonly value="${tPmProjectPage.feeType.fundsName}">
        <input id="feeType" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}">
        <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">经费类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 子类型: </label></td>
        <td class="value"><input id="subType" name="subType" type="text" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.subType}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">子类型</label></td>
        <td align="right"><label class="Validform_label"> 会计编码: </label></td>
        <td class="value"><input id="accountingCode" name="accountingCode" type="text" datatype="s2-250" ignore="ignore" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.accountingCode}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">会计编码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 计划合同文号: </label></td>
        <td class="value"><input id="planContractRefNo" name="planContractRefNo" type="text" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.planContractRefNo}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">计划合同文号</label></td>
        <td align="right"><label class="Validform_label"> 合同日期: </label></td>
        <td class="value"><input id="contractDate" name="contractDate" type="text" ignore="ignore" datatype="date" style="width: 150px"
          class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tPmProjectPage.contractDate}' type="date" pattern="yyyy-MM-dd"/>'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同日期</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同计划名称: </label></td>
        <td class="value"><input id="planContractName" name="planContractName" type="text" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.planContractName}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">合同计划名称</label></td>
        <td align="right"><label class="Validform_label"> 来源单位: </label></td>
        <td class="value"><input id="sourceUnit" name="sourceUnit" type="text" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.sourceUnit}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">来源单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 项目来源: </label></td>
        <td class="value"><input id="projectSource" name="projectSource" type="text" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.projectSource}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">项目来源</label></td>
        <td align="right"><label class="Validform_label"> 承研部门: <font color="red">*</font></label></td>
        <td class="value">
        <t:departComboTree name="" id="cc" idInput="developerDepartId" lazy="true" value="${tPmProjectPage.devDepart.departname}" width="155"></t:departComboTree> 
        <input id="developerDepartId" name="devDepart.id" type="hidden" value="${tPmProjectPage.devDepart.id}">
          <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">承研部门</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 责任部门: </label></td>
        <td class="value"><input id="dutyDepart" name="dutyDepart" type="text" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.dutyDepart}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">责任部门</label></td>
        <td align="right"><label class="Validform_label"> 经费单列: </label></td>
        <td class="value"><input id="feeSingleColumn" name="feeSingleColumn" type="text" style="width: 150px"
          class="inputxt" value='${tPmProjectPage.feeSingleColumn}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">经费单列</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 项目密级: </label></td>
        <td class="value">
        <t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" code="XMMJ" codeType="0" defaultVal="${tPmProjectPage.secretDegree}"></t:codeTypeSelect>
        <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目密级</label></td>
        <td align="right"><label class="Validform_label"> 是否需要鉴定: </label></td>
        <td class="value">
        <t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0"
            type="radio" defaultVal="${tPmProjectPage.appraisalFlag}"></t:codeTypeSelect>
        <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">是否需要鉴定</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 总经费: </label></td>
        <td class="value"><input id="allFee" name="allFee" type="text" ignore="ignore" datatype="/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/" errormsg="请填入正确的金额" style="width: 150px" class="inputxt"
          value='${tPmProjectPage.allFee}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">总经费</label></td>
        <td align="right"><label class="Validform_label"> 计划年度: </label></td>
        <td class="value"><input id="planYear" name="planYear" type="text" ignore="ignore" datatype="*" style="width: 150px" readonly="readonly" value="${tPmProjectPage.planYear}"
          class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy'})"> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">计划年度</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 依据文号: </label></td>
        <td class="value"><input id="accordingNum" name="accordingNum" type="text" style="width: 150px" class="inputxt" readonly="readonly"  datatype="*" value="${tPmProjectPage.accordingNum}">
<%-- 				  <t:choose url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px" --%>
<%--                   icon="icon-search" title="收发文列表列表" textname="mergeFileNum" inputTextname="accordingNum" isclear="true"></t:choose> --%>
                  <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">依据文号</label></td>
        <td align="right"><label class="Validform_label"> 是否重大专项 : </label></td>
        <td class="value">
        <c:if test="${empty tPmProjectPage.greatSpecialFlag }">
          <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0"  type="radio" 
          defaultVal="0"></t:codeTypeSelect>
        </c:if>
        <c:if test="${!empty tPmProjectPage.greatSpecialFlag }">
          <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0"  type="radio" 
          defaultVal="${tPmProjectPage.greatSpecialFlag}"></t:codeTypeSelect>
        </c:if>
        <span class="Validform_checktip"></span> 
        <label class="Validform_label" style="display: none;">是否重大专项 </label></td>
        
      </tr>
      <tr>
       <td align="right"><label class="Validform_label"> 项目简介: <font color="red">*</font></label></td>
        <td colspan="3" class="value"><textarea id="projectAbstract" name="projectAbstract" type="text" datatype="*2-250" style="width: 500px;height:80px;"
          class="inputxt" >${tPmProjectPage.projectAbstract}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">项目简介</label></td>
      </tr>
       -->
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/manage/tPmProject.js"></script>