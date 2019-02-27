<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant,com.kingtake.project.entity.manage.TPmProjectEntity,com.kingtake.common.constant.SrmipConstants"
  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.legendFont {
	font-size: 12px;
	font-weight: bold;
}
.ftd{
border: solid skyblue 1px;
width:600px;
height:160px;
}
.txt{
font-size: 30px;
color: red;
}
</style>
<title>科技档案</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
  request.setAttribute("no", SrmipConstants.NO);
  request.setAttribute("projectStatusMap", ProjectConstant.projectStatusMap);
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
    //tip(data.msg);
    $.messager.alert('提示',data.msg);
    //$(".messager-window").css({top:"185px"});
    //$(".window-shadow").css({top:"185px"});
    //window.parent.parent.parentFunction();
  }

  $(function() {
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });

    //如果页面是详细查看页面,隐藏保存按钮
    if (location.href.indexOf("load=detail") != -1) {
      $("#saveBtn").css("display", "none");
    }

    var projectType = $("#projectType").val();
    $("#projectType").combotree({
      url : 'tPmProjectController.do?getProjectType&projectTypeId='+projectType,
      onChange : function() {
        var projectType = $("#projectType").combobox('getValue');
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

    var approvalStatus = $("#approvalStatus").val();
    if (approvalStatus == '1') {
      $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
      $("textarea").attr("disabled", "disabled");
      $("select").attr("disabled", "disabled");
      $("#projectType").combobox('disable'); 
    }
    
    <!--------------------------项目组成员---------------------------------------->
    $("#tPmProjectMemberListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
    
    initTab();
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
    return true;
  }
  
</script>
</head>
<body>
  <div>
    <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td align="center" bgcolor="#E5EFFF"><b>${tPmProjectPage.projectName}-项目基本信息表</b></td>
        <td width="5%" bgcolor="#E5EFFF"><input id="applying" value="${applying }" type="hidden" /></td>
      </tr>
    </table>
    <t:formvalid postonce="false" formid="formobj" dialog="true" layout="table" action="tPmProjectController.do?doUpdate" tiptype="showValidMsg" beforeSubmit="setValue" callback="@Override callback">
      <input id="id" name="id" type="hidden" value="${tPmProjectPage.id }">
      <input id="approvalStatus" name="approvalStatus" type="hidden" value="${tPmProjectPage.approvalStatus }">
      <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA;">
        <!-- border: ridge; border-radius: 10px; -->
        <legend onclick="show_hide('baseData','showTxt')">
          <span class="legendFont" style="color: #A52A2A">基本信息</span>
        </legend>
        <div align="left">
          <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td align="right" width="90px"><label class="Validform_label"> 项目名称: </label> <font color="red">*</font></td>
              <td class="value" colspan="3"><input id="projectName" name="projectName" type="text" datatype="*2-30" style="width: 561px" class="inputxt" value='${tPmProjectPage.projectName}'
                readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
              <td align="right" width="95px"><label class="Validform_label"> 项目密级:</label><font color="red">*</font></td>
              <td class="value"><t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" code="XMMJ" codeType="0" defaultVal="${tPmProjectPage.secretDegree}"
                  extendParam="{style:'width:150px'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目密级</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目编号: </label></td>
              <td class="value"><input id="projectNo" name="projectNo" type="text" ignore="ignore" datatype="s2-20" style="width: 160px" class="inputxt" validType="T_PM_PROJECT,PROJECT_NO,ID"
                value='${tPmProjectPage.projectNo}' readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label></td>
              <td align="right" width="105px"><label class="Validform_label"> 会计编码: </label></td>
              <td class="value"><input id="accountingCode" name="accountingCode" type="text" datatype="s2-250" ignore="ignore" style="width: 160px" class="inputxt"
                value='${tPmProjectPage.accountingCode}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
              <td align="right"><label class="Validform_label"> 重大专项 : </label></td>
              <td class="value"><c:if test="${empty tPmProjectPage.greatSpecialFlag }">
                  <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect>
                </c:if> <c:if test="${!empty tPmProjectPage.greatSpecialFlag }">
                  <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.greatSpecialFlag}"></t:codeTypeSelect>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否重大专项 </label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 依据文号: </label></td>
              <td class="value"><input id="accordingNum" name="accordingNum" type="text" style="width: 160px" class="inputxt" readonly="readonly" value="${tPmProjectPage.accordingNum}"> <c:if
                  test="${tPmProjectPage.approvalStatus ne '1' }">
                  <t:choose url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px" height="450px" icon="icon-search" title="收发文列表" textname="mergeFileNum"
                    inputTextname="accordingNum" isclear="true"></t:choose>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">依据文号</label></td>
              <td align="right"><label class="Validform_label"> 承研部门: </label> <font color="red">*</font></td>
              <td class="value"><input name="devDepartDepartname" id="devDepartDepartname" value="${tPmProjectPage.devDepart.departname}"> <script type="text/javascript">
                              //选择承研单位时，将承研单位的父单位加入责任单位
                              $(function() {
                                $('#devDepartDepartname').combotree({
                                  url : 'departController.do?getDepartTree&lazy=false',
                                  width : '165',
                                  height : '27',
                                  multiple : false,
                                  onSelect : function(record) {
                                    $("#developerDepartId").val(record.id);
                                    var tree = $('#devDepartDepartname').combotree('tree');
                                    var parent = tree.tree('getParent', record.target);
                                    $("#dutyDepartId").val(parent.id);
                                    $("#dutyDepartDepartname").combotree('setValue', parent.text);
                                  }
                                });
                              });
                            </script> <input id="developerDepartId" name="devDepart.id" type="hidden" value="${tPmProjectPage.devDepart.id}"> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">承研部门</label></td>
              <td align="right"><label class="Validform_label"> 责任部门: </label></td>
              <td class="value"><t:departComboTree name="" id="dutyDepartDepartname" idInput="dutyDepartId" lazy="false" value="${tPmProjectPage.dutyDepart.departname}" width="150"></t:departComboTree>
                <input id="dutyDepartId" name="dutyDepart.id" type="hidden" class="inputxt" value='${tPmProjectPage.dutyDepart.id}'> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">责任部门</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 负&nbsp;责&nbsp;人: </label> <font color="red">*</font></td>
              <td class="value"><input id="projectManager" name="projectManager" type="text" ignore="ignore" datatype="s2-16" style="width: 160px" class="inputxt" datatype="s2-16"
                value='${tPmProjectPage.projectManager}' readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人</label></td>
              <td align="right"><label class="Validform_label"> 负责人电话: </label> <font color="red">*</font></td>
              <td class="value"><input id="managerPhone" name="managerPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.managerPhone}' datatype="*1-30"> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人电话</label></td>
              <td align="right"><label class="Validform_label"> 起始日期: </label> <font color="red">*</font></td>
              <td class="value"><input id="beginDate" name="beginDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"
                value='<fmt:formatDate value='${tPmProjectPage.beginDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">起始日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label> <font color="red">*</font></td>
              <td class="value"><input id="contact" name="contact" type="text" datatype="s2-16" style="width: 160px" class="inputxt" value='${tPmProjectPage.contact}'> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人</label></td>
              <td align="right"><label class="Validform_label"> 联系人电话: </label> <font color="red">*</font></td>
              <td class="value"><input id="contactPhone" name="contactPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.contactPhone}' datatype="*1-30"> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人电话</label></td>
              <td align="right"><label class="Validform_label"> 截止日期: </label> <font color="red">*</font></td>
              <td class="value"><input id="endDate" name="endDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"
                value='<fmt:formatDate value='${tPmProjectPage.endDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">截止日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目状态:</label> <font color="red">*</font></td>
              <td class="value"><input type="text" style="width: 160px" class="inputxt" readonly="readonly" value='${projectStatusMap[tPmProjectPage.projectStatus]}'> <input
                id="projectStatus" name="projectStatus" type="hidden" class="inputxt" value='${tPmProjectPage.projectStatus}'> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">项目状态</label></td>
              <td align="right"><label class="Validform_label"> 原会计编码:</label></td>
              <td class="value" colspan="3"><input id="oldAccountingCode" name="oldAccountingCode" type="text" datatype="s2-250" ignore="ignore" style="width: 160px" class="inputxt"
                value='${tPmProjectPage.oldAccountingCode}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">原会计编码</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目简介:</label> <font color="red">*</font></td>
              <td colspan="5" class="value"><textarea id="projectAbstract" name="projectAbstract" type="text" datatype="*2-250" style="width: 94%; height: 80px;" class="inputxt">${tPmProjectPage.projectAbstract}</textarea>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目简介</label></td>
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
              <td align="right"><label class="Validform_label"> 项目类型: </label></td>
              <td class="value"><input id="projectType" name="projectType.id" style="width: 165px;" value="${tPmProjectPage.projectType.id}"> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">项目类型</label></td>
              <td align="right"><label class="Validform_label"> 经费类型: </label></td>
              <td class="value"><input id="feeTypeName" type="text" style="width: 150px" class="inputxt" readonly value="${tPmProjectPage.feeType.fundsName}"> <input id="feeType"
                name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">经费类型</label>
              </td>
              <td align="right"><label class="Validform_label"> 计划合同标志: </label></td>
              <td class="value"><input type="text" id="planContractFlagName" readonly style="width: 140px" class="inputxt" class="inputxt" value="${planContractFlag}"> <input
                type="hidden" id="planContractFlag" name="planContractFlag" value="${tPmProjectPage.planContractFlag}"> <span class="Validform_checktip"></span> <label class="Validform_label"
                style="display: none;">计划合同标志</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 子类型: </label></td>
              <td class="value"><input id="subType" name="subType" type="text" style="width: 160px" class="inputxt" datatype="byterange" max="40" min="0" ignore="ignore"
                value='${tPmProjectPage.subType}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">子类型</label></td>
              <td align="right"><label class="Validform_label"> 分管部门: </label></td>
              <td class="value"><t:codeTypeSelect name="manageDepartCode" type="select" codeType="1" code="FGBM" id="manageDepartCode" labelText="请选择"
                  defaultVal="${tPmProjectPage.manageDepartCode}"></t:codeTypeSelect> <input id="manageDepart" name="manageDepart" value="${tPmProjectPage.manageDepart}" type="hidden" datatype="s2-20"
                ignore="ignore"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">分管部门</label></td>
              <td align="right"><label class="Validform_label"> 总经费(元): </label></td>
              <td class="value"><input id="allFee" name="allFee" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
                style="width: 140px; text-align: right;" value='${tPmProjectPage.allFee}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">总经费</label>
              </td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 所属母项目: </label></td>
              <td class="value" colspan="3"><input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}"> <input id="parentProjectName"
                type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.parentPmProject.projectName}' readonly="readonly"> <c:if
                  test="${tPmProjectPage.approvalStatus ne '1' }">
                  <t:chooseProject inputId="parentProjectId" inputName="parentProjectName" icon="icon-search" title="所属母项目" isclear="true" mode="single"></t:chooseProject>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属母项目</label></td>
              <td align="right"><label class="Validform_label"> 经费单列: </label></td>
              <td class="value"><input id="feeSingleColumn" name="feeSingleColumn" type="text" style="width: 140px" class="inputxt" value='${tPmProjectPage.feeSingleColumn}'> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">经费单列</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 计划合同名称: </label></td>
              <td class="value" colspan="3"><input id="planContractName" name="planContractName" type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.planContractName}'>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同计划名称</label></td>
              <td align="right"><label class="Validform_label"> 合同签订日期: </label></td>
              <td class="value"><input id="contractDate" name="contractDate" type="text" ignore="ignore" datatype="date" style="width: 140px" class="Wdate" onClick="WdatePicker()"
                value='<fmt:formatDate value='${tPmProjectPage.contractDate}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
                style="display: none;">合同签订日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 计划合同文号: </label></td>
              <td class="value"><input id="planContractRefNo" name="planContractRefNo" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.planContractRefNo}'> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计划合同文号</label></td>
              <td align="right"><label class="Validform_label"> 外来编号: </label></td>
              <td class="value"><input id="outsideNo" name="outsideNo" type="text" style="width: 150px" class="inputxt" value='${tPmProjectPage.outsideNo}'> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">外来编号</label></td>
              <td align="right"><label class="Validform_label"> 是否需要鉴定: </label></td>
              <td class="value"><t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.appraisalFlag}"></t:codeTypeSelect> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否需要鉴定</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 来源单位: </label></td>
              <td class="value" colspan="3"><input id="sourceUnit" name="sourceUnit" type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.sourceUnit}'> <span
                class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源单位</label></td>
              <td align="right"><label class="Validform_label"> 院系主管参谋: </label></td>
              <td class="value"><input type="text" id="departStaffRealname" readonly style="width: 140px" class="inputxt" value='${departStaffRealname}'> <input type="hidden"
                id="departStaff" name="departStaff" value='${tPmProjectPage.departStaff}'> <c:if test="${tPmProjectPage.approvalStatus ne '1' }">
                  <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="departStaff" textname="id,realName" inputTextname="departStaff,departStaffRealname"></t:chooseUser>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">院系主管参谋</label></td>
            </tr>
            <tr>
              <c:if test="${!empty mergeProjList}">
                <td align="right" width="15%"><label class="Validform_label"> 合并项目: </label></td>
                <td colspan="5" class="value">
                  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                    <tr>
                      <td colspan="3" class="value" width="85%"><c:forEach items="${mergeProjList}" var="s">
                          <a href="javascript:godetail('${s.id}');">${s.projectName}</a>
                        </c:forEach></td>
                    </tr>
                  </table>
                </td>
              </c:if>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 机关主管参谋: </label></td>
              <td class="value"><input type="text" id="officeStaffRealname" readonly style="width: 160px" class="inputxt" value="${officeStaffRealname}"> <input type="hidden"
                id="officeStaff" name="officeStaff" value='${tPmProjectPage.officeStaff}'>  <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">机关主管参谋</label></td>
            </tr>
          </table>
        </div>
      </fieldset>
    </t:formvalid>
  </div>
  <!-- -------------------任务书-------------华丽的分割线---------------------------------- -->
  <div class="easyui-layout" style="margin-top: 10px; height: 500px; width: 100%;">
    <div region="center" title="任务书"  style="padding: 1px;">
      <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tPmTaskController.do?doAddUpdate" callback="@Override uploadFile" beforeSubmit="checkDate">
      <input id="taskId" name="taskId" type="hidden" value="${tPmTaskPage.id }">
      <input id="projectId" name="project.id" type="hidden" value="${tPmTaskPage.project.id }">
      <input id="createBy" name="createBy" type="hidden" value="${tPmTaskPage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tPmTaskPage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tPmTaskPage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tPmTaskPage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tPmTaskPage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tPmTaskPage.updateDate }">
      <table cellpadding="0" cellspacing="1" class="formtable" style="width: 100%;">
        <tr>
          <td align="right"><label class="Validform_label">
              标题:<font color="red">*</font>
            </label></td>
          <td class="value"><input id="taskTitle" name="taskTitle" type="text" style="width: 250px" class="inputxt" datatype="*2-100" value='${tPmTaskPage.taskTitle}'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标题</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
          <td class="value"><input type="hidden" value="${tPmTaskPage.id}" id="bid" name="bid">
            <table style="max-width: 92%;">
              <c:forEach items="${tPmTaskPage.attachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmTaskPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <%-- <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
            </div></td>
        </tr>
      </table>
      <input id="taskNodeListStr" name="taskNodeListStr" type="hidden">
    </t:formvalid>
    <div id="taskNodeList"></div>
    </div>
  </div>
  <div class="easyui-panel" title="项目相关文档">
  <table style="max-width: 92%;">
      <c:forEach items="${fileList}" var="file" varStatus="idx">
        <tr style="height: 30px;">
          <td><a href="javascript:void(0);" style="font-size: 20px;color: red;">${file.attachmenttitle}.${file.extend }</a>&nbsp;&nbsp;&nbsp;</td>
          <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="查看" style="text-decoration: underline;font-size: 18px;color: red;">查看</a></td>
          <%-- <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
<script src="webpage/com/kingtake/project/manage/tPmProject.js"></script>
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

//初始化成员表格
function initTab() {
    var toolbar = [];
    $('#taskNodeList').datagrid({
                        title : '任务节点',
                        fitColumns : true,
                        nowrap : true,
                        height : 350,
                        fit:true,
                        striped : true,
                        remoteSort : false,
                        idField : 'id',
                        editRowIndex : -1,
                        singleSelect : true,
                        /* toolbar : toolbar, */
                        columns : [ [
                                {
                                    field : 'id',
                                    title : 'id',
                                    width : 100,
                                    hidden : true
                                },
                                {
                                    field : 'planTimeStr',
                                    title : '计划完成时间',
                                    width : 80,
                                    editor : {
                                        type : 'datebox',
                                        options : {
                                            required : true
                                        }
                                    }
                                },
                                {
                                    field : 'finishTimeStr',
                                    title : '实际完成时间',
                                    width : 80,
                                    editor : {
                                        type : 'datebox',
                                        options : {
                                            required : true
                                        }
                                    }
                                },
                                {
                                    field : 'taskContent',
                                    title : '任务内容',
                                    width : 150,
                                    editor : {
                                        type : 'validatebox',
                                        options : {
                                            required : true
                                        }
                                    }
                                },
                                {
                                    field : 'finishFlag',
                                    title : '是否完成',
                                    width : 150,
                                    formatter : function(value, row, index) {
                                        if (value == "1") {
                                            return "是";
                                        } else {
                                            return "否";
                                        }
                                    }
                                },
                                {
                                    field : 'opt',
                                    title : '完成情况',
                                    width : 150,
                                    formatter : function(value, row, index) {
                                        var id = row.id;
                                        var finishFlag = row.finishFlag;
                                        if (id != null && finishFlag == '1') {
                                            return "<a href=\"#\" style=\"text-decoration: underline;color: blue; \" onclick=\"openFinishWindow('"
                                                    + id + "');\">查看完成情况说明</a>";
                                        } else {
                                            return "";
                                        }
                                    }
                                } ] ],
                        onDblClickRow : function(rowIndex, rowData) {
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

//打开完成情况窗口
function openFinishWindow(id) {
    var url = 'tPmTaskController.do?goFinishPage&load=detail';
    url += '&id=' + id;
    createdetailwindow('完成情况', url, 650, 400);
}

function loadData() {
    //加载数据
    var id = $("#taskId").val();
    if (id != "") {
        var x_url = "tPmTaskController.do?tPmTaskNodeList&id=" + id;
        $('#taskNodeList').datagrid('options').url = x_url;
        setTimeout(function() {
            $('#taskNodeList').datagrid('load');
        }, 0);

    }
}
</script>