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
</style>
<title>项目基本信息表</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<%
    request.setAttribute("no", SrmipConstants.NO);
			request.setAttribute("projectStatusMap",
					ProjectConstant.projectStatusMap);
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
        tip(data.msg);
        if (data.success) {
            var projectId = $("#id").val();
            var url = "tBPmProjectChangeController.do?goProjectChangeInfo";
            var dialog;
            var width = window.top.document.body.offsetWidth;
            var height = window.top.document.body.offsetHeight - 100;
            var title = "项目变更申请";
            if (typeof (windowapi) == 'undefined') {
                dialog = $.dialog({
                    id : 'apprDialog',
                    content : 'url:' + url,
                    data : {
                        'list' : data.obj,
                        'projectId' : projectId
                    },
                    lock : true,
                    width : width,
                    height : height,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            } else {
                dialog = W.$.dialog({
                    id : 'apprDialog',
                    content : 'url:' + url,
                    data : {
                        'list' : data.obj,
                        'projectId' : projectId
                    },
                    lock : true,
                    width : width,
                    height : height,
                    parent : windowapi,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
        }
    }

    $(function() {
    	
    	//预算类型
	   	 $("#yslx").combotree({
	   			url : 'tBXmlbController.do?getbudgetCategory',
	   			valueField : 'id',
	   			textField : 'text',
	   			onChange : function() {
	   				var yslx = $("#yslx").combobox('getValue');
	   				$("#yslx").val(yslx);
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

        $("#projectType").combobox({
            url : 'tBProjectTypeController.do?getProjectTypeList',
            valueField : 'id',
            textField : 'projectTypeName',
            disabled : true,
            onLoadSuccess : function() {
                var projectType = $("#projectType").val();
                if (projectType != "") {
                    $("#projectType").combobox('setValue', projectType);
                } else {
                    var data = $("#projectType").combobox('getData');
                    if (data.length > 0) {
                        $("#projectType").combobox('setValue', data[0].id);
                    }
                }
            },
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
                       // $("#feeTypeName").val(fund.fundsName);
                       // $("#feeType").val(fund.id);
                       // $("#planContractFlagName").val(approval.name);
                       // $("#planContractFlag").val(approval.code);
                    }
                });
            }
        });
		
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
        
//         if($("#approvalAuthorityValue").val() != ""){
//         	var approvalAuthorityValue = $("#approvalAuthorityValue").val();
//         	$("#approvalAuthority option[value=" + approvalAuthorityValue + "]").attr("selected", true);
//         } 
        
        if($("#parentProjectId").val() == ""){
        	$('#parentProjectLabel').attr('style', 'display:none;');
        }
    });

    function godetail(id) {
        createdetailwindow('项目基本信息', 'tPmProjectController.do?goUpdateForResearchGroup&load=detail&id=' + id, "1100px",
                "600px");
    }

    function setValue() {
        var managerDepartCode = $("#manageDepartCode").val();
        if (managerDepartCode != "") {
            var manageDepart = $("#manageDepartCode").find("option:selected").text();
            $("#manageDepart").val(manageDepart);
        }
        return true;       
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

    function uidChange() {
    	var uid = $('#projectManagerId').val();
    	var orgid = $('#orgId').val();
    	$.ajax({
    		type : 'post',
    		async : false,
    		url:'tPmProjectMemberController.do?findInfoById',
    		data:{'uid':uid,'orgid':orgid},
    		success : function(data) {
    			var d = $.parseJSON(data);
    			var user = d.attributes.user;
    			var departId = d.attributes.departId;
    			var departName = d.attributes.departName;
    			var departPid = d.attributes.departPid;
    			var departPname = d.attributes.departPname;
    			$('#managerPhone').val(user.officePhone ? user.officePhone : user.mobilePhone);
    			$("#developerDepartId").val(departId = '' ? '' : departId);
    			$("#devDepartDepartname").combotree('setValue', departName = '' ? '' : departName);
    			$("#dutyDepartId").val(departPid = '' ? '' : departPid);
    			$("#dutyDepartDepartname").combotree('setValue', departPname = '' ? '' : departPname);
    		}
    	});
    }

    function close() {
        frameElement.api.close();
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
  <div>
    <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td align="center" bgcolor="#E5EFFF"><b>${tPmProjectPage.projectName}-项目基本信息表</b></td>
        <td width="5%" bgcolor="#E5EFFF"></td>
      </tr>
    </table>
    <t:formvalid postonce="false" formid="formobj" dialog="true" layout="table" action="tBPmProjectChangeController.do?createProjectChangeInfo" tiptype="showValidMsg" beforeSubmit="setValue"
      callback="@Override callback">
      <input id="id" name="id" type="hidden" value="${tPmProjectPage.id }">
      
      <input id="gdStatus" name="gdStatus" type="hidden" value="${tPmProjectPage.gdStatus }">
      <input id="auditStatus" name="auditStatus" type="hidden" value="${tPmProjectPage.auditStatus }">
      <input id="lxyj" name="lxyj" type="hidden" value="${tPmProjectPage.lxyj }">
      <input id="rwzd" name="rwzd" type="hidden" value="${tPmProjectPage.rwzd }">
      <input id="cxm" name="cxm" type="hidden" value="${tPmProjectPage.cxm }">
      <input id="lsh" name="lsh" type="hidden" value="${tPmProjectPage.lsh }">
      <input id="xmml" name="xmml" type="hidden" value="${tPmProjectPage.xmml }">
      <input id="scbz" name="scbz" type="hidden" value="${tPmProjectPage.scbz }">
      <input id="jflx" name="jflx.id" type="hidden" value="${tPmProjectPage.jflx.id }"
      <input id="jflxStr" name="jflxStr" type="hidden" value="${tPmProjectPage.jflxStr }">
      <input id="xmlx" name="xmlx" type="hidden" value="${tPmProjectPage.xmlx }">
      <input id="xmxz" name="xmxz" type="hidden" value="${tPmProjectPage.xmxz }">
      <input id="xmly" name="xmly" type="hidden" value="${tPmProjectPage.xmly }">
      <input id="lhsb" name="lhsb" type="hidden" value="${tPmProjectPage.lhsb }">
      <input id="xmlbStr" name="xmlbStr" type="hidden" value="${tPmProjectPage.xmlbStr }">
      <input id="lxStatus" name="lxStatus" type="hidden" value="${tPmProjectPage.lxStatus }">
      <input id="glxmid"  name="glxm.id" type="hidden" value="${tPmProjectPage.glxm.id }">
      <input id="jhid" name="jhid" type="hidden" value="${tPmProjectPage.jhid }">      
      <input id="zgdw" name="zgdw" type="hidden" value="${tPmProjectPage.zgdw }">   
      <input id="yap"  name="yap" type="hidden" value="${tPmProjectPage.yap }">
      <input id="dnjf" name="dnjf" type="hidden" value="${tPmProjectPage.dnjf }">      
       <input id="zmye" name="zmye" type="hidden" value="${tPmProjectPage.zmye }">   
      
      
      <input id="approvalStatus" name="approvalStatus" type="hidden" value="${tPmProjectPage.approvalStatus }">
      <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA;">
        <!-- border: ridge; border-radius: 10px; -->
        <legend onclick="show_hide('baseData','showTxt')">
          <span class="legendFont" style="color: #A52A2A">基本信息${tPmProjectPage.bmlx }</span>
        </legend>
        <div align="left">
          <table style="width: 100%;" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <td align="right" width="90px"><label class="Validform_label"> 项目名称: </label> <font color="red">*</font></td>
              <td class="value" colspan="3"><input id="projectName" name="projectName" type="text" datatype="*2-30" style="width: 440px" class="inputxt" value='${tPmProjectPage.projectName}'>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
              <td align="right" width="95px"><label class="Validform_label"> 项目密级:</label><font color="red">*</font></td>
              <td class="value"><t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" code="XMMJ" codeType="0" defaultVal="${tPmProjectPage.secretDegree}"
                  extendParam="{style:'width:150px'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目密级</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目编号: </label></td>
              <td class="value"><input id="projectNo" name="projectNo" type="text" readonly="true" ignore="ignore" datatype="s2-20" style="width: 160px" class="inputxt" validType="T_PM_PROJECT,PROJECT_NO,ID"
                  value='${tPmProjectPage.projectNo}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label></td>
              <td align="right" width="105px"><label class="Validform_label"> 会计编码: </label></td>
              <td class="value"><input id="accountingCode" name="accountingCode" type="text" datatype="s2-250" readonly="true" ignore="ignore" style="width: 160px" class="inputxt"
                  value='${tPmProjectPage.accountingCode}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
              <input id="greatSpecialFlag" name="greatSpecialFlag" type="hidden" value="${tPmProjectPage.greatSpecialFlag }">
          	<%--
              <td align="right"><label class="Validform_label"> 重大专项 : </label></td>
              <td class="value"><c:if test="${empty tPmProjectPage.greatSpecialFlag }">
                  <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect>
                </c:if> <c:if test="${!empty tPmProjectPage.greatSpecialFlag }">
                  <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.greatSpecialFlag}"></t:codeTypeSelect>
                </c:if> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否重大专项 </label></td>
             --%>
           
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 关键词: </label></td>
              <td class="value"><input id="accordingNum" name="accordingNum" type="text" style="width: 100px" class="inputxt" readonly="readonly" value="${tPmProjectPage.accordingNum}"> <t:choose
                  url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px" height="450px" icon="icon-search" title="收发文列表" textname="mergeFileNum"
                  inputTextname="accordingNum" isclear="true"></t:choose> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关键词</label></td>
                  <td align="right"><label class="Validform_label">
                 项目组长: <font color="red">*</font>
                </label></td>
              <td class="value"><input id="projectManagerId" name="projectManagerId" type="hidden" style="width: 150px" class="inputxt" value="${tPmProjectPage.projectManagerId}"> <input
                  id="orgId" name="orgId" type="hidden" style="width: 150px" class="inputxt"> <input id="projectManager" name="projectManager" type="text" datatype="*" style="width: 150px"
                  class="inputxt" value="${tPmProjectPage.projectManager}"> <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId" isclear="true"
                  inputTextname="projectManagerId,projectManager,orgId" idInput="projectManagerId" mode="single" fun="uidChange"></t:chooseUser> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">项目组长</label></td>
              <td align="right"><label class="Validform_label"> 承研部门: </label> <font color="red">*</font></td>
              <td class="value"><input name="devDepartDepartname" id="devDepartDepartname" value="${tPmProjectPage.devDepart.departname}"> <script type="text/javascript">
                                                            //选择承研单位时，将承研单位的父单位加入责任单位
                                                            $(function() {
                                                                $('#devDepartDepartname')
                                                                        .combotree(
                                                                                {
                                                                                    url : 'departController.do?getDepartTree&lazy=false',
                                                                                    width : '165',
                                                                                    height : '27',
                                                                                    multiple : false,
                                                                                    onSelect : function(record) {
                                                                                        $("#developerDepartId").val(
                                                                                                record.id);
                                                                                        var tree = $(
                                                                                                '#devDepartDepartname')
                                                                                                .combotree('tree');
                                                                                        var parent = tree.tree(
                                                                                                'getParent',
                                                                                                record.target);
                                                                                        $("#dutyDepartId").val(
                                                                                                parent.id);
                                                                                        $("#dutyDepartDepartname")
                                                                                                .combotree('setValue',
                                                                                                        parent.text);
                                                                                    }
                                                                                });
                                                            });
                                                        </script> <input id="developerDepartId" name="devDepart.id" type="hidden" value="${tPmProjectPage.devDepart.id}"> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承研部门</label></td>              
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 责任部门: </label></td>
              <td class="value"><t:departComboTree name="" id="dutyDepartDepartname" idInput="dutyDepartId" lazy="false" value="${tPmProjectPage.dutyDepart.departname}" width="150"></t:departComboTree>
                <input id="dutyDepartId" name="dutyDepart.id" type="hidden" class="inputxt" value='${tPmProjectPage.dutyDepart.id}'> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">责任部门</label></td>
              <td align="right"><label class="Validform_label"> 负责人电话: </label> <font color="red">*</font></td>
              <td class="value"><input id="managerPhone" name="managerPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.managerPhone}'
                  datatype="*1-30"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人电话</label>
              </td>
              <td align="right"><label class="Validform_label"> 起始日期: </label> <font color="red">*</font></td>
              <td class="value"><input id="beginDate" name="beginDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"
                  value='<fmt:formatDate value='${tPmProjectPage.beginDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">起始日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label> <font color="red">*</font></td>
              <td class="value"><input id="contact" name="contact" type="text" datatype="s2-16" style="width: 160px" class="inputxt" value='${tPmProjectPage.contact}'><img alt="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" title="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" src="plug-in\easyui1.4.2\themes\icons\tip.png"> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人</label></td>
              <td align="right"><label class="Validform_label"> 联系人电话: </label> <font color="red">*</font></td>
              <td class="value"><input id="contactPhone" name="contactPhone" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.contactPhone}'
                  datatype="*1-30"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人电话</label>
              </td>
              <td align="right"><label class="Validform_label"> 截止日期: </label> <font color="red">*</font></td>
              <td class="value"><input id="endDate" name="endDate" type="text" datatype="date" style="width: 145px" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"
                  value='<fmt:formatDate value='${tPmProjectPage.endDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">截止日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目状态:</label> <font color="red">*</font></td>
              <td class="value" colspan="5"><input type="text" style="width: 160px" class="inputxt" readonly="readonly" value='${projectStatusMap[tPmProjectPage.projectStatus]}'> <input
                  id="projectStatus" name="projectStatus" type="hidden" class="inputxt" value='${tPmProjectPage.projectStatus}'> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">项目状态</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目简介:</label> <font color="red">*</font></td>
              <td colspan="5" class="value"><textarea id="projectAbstract" name="projectAbstract" type="text" datatype="*2-250" style="width: 98%; height: 80px;" class="inputxt">${tPmProjectPage.projectAbstract}</textarea>
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
              <td align="right"><label class="Validform_label"> 项目类型: </label><font color="red">*</font></td>
              <td class="value"><input id="projectType" name="xmlbStr" style="width: 165px; height:27px;" value="${tPmProjectPage.xmlbStr}">
              <input id="projectTypeId" type="hidden" value="${tPmProjectPage.xmlb.id}" name="xmlb.id" datatype="*"> <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">项目类型</label></td>
              
              <td align="right"><label class="Validform_label"> 经费类型: </label><font color="red">*</font></td>
              <td class="value"><input id="feeTypeName" type="text" style="width: 150px" name="feeTypeStr" class="inputxt" readonly value="${tPmProjectPage.feeTypeStr}">
               <input id="feeType" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}" datatype="*"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">经费类型</label>
              </td>
	          <td align="right"><label class="Validform_label"> 预算类型: </label><font color="red">*</font></td>
              <td class="value"><input id="yslx" name="yslx" style="width: 165px; height:27px;" value="${tPmProjectPage.yslx}">
              <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">预算类型</label></td>
	          
	        </tr>  
	        
            <tr>
            	<td align="right"><label class="Validform_label"> 间接费计算方式: </label><font color="red">*</font></td>
	              <td class="value"><input id="jjfjsfs" name="jjfjsfs" style="width: 165px; height:27px;" value="${tPmProjectPage.jjfjsfs}">
	              <span class="Validform_checktip"></span> <label
	                  class="Validform_label" style="display: none;">间接费计算方式</label></td>
            	<td align="right"><label class="Validform_label"> 大学比例: </label></td>
	             <td class="value">
	            	<%-- <t:codeTypeSelect id="dxbl" name="dxbl" type="select" code="DXBL" codeType="0"></t:codeTypeSelect> --%>
	            	<input id="dxbl" name="dxbl" type="text" value="${tPmProjectPage.dxbl}" />
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">大学比例</label>
	            </td>
            	<td align="right"><label class="Validform_label"> 责任单位比例: </label></td>   
	            <td class="value">
	            	<%-- <t:codeTypeSelect id="zrdwbl" name="zrdwbl" type="select" code="ZRDWBL" codeType="0"></t:codeTypeSelect> --%>
	            	
	            	<input id="zrdwbl" name="zrdwbl" type="text" value="${tPmProjectPage.zrdwbl}"  />
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">责任单位比例</label>
	            </td>
            </tr>
            
            <tr>
            	<td align="right"><label class="Validform_label"> 承研单位比例: </label></td>   
	             <td class="value">
	            	<%-- <t:codeTypeSelect id="cydwbl" name="cydwbl" type="select" code="CYDWBL" codeType="0"></t:codeTypeSelect> --%>
	            	
	            	<input id="cydwbl" name="cydwbl" type="text" value="${tPmProjectPage.cydwbl}"  />
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">承研单位比例</label>
	            </td> 
	            <td align="right"><label class="Validform_label"> 项目组比例: </label></td>   
	             <td class="value">
	            	<%-- <t:codeTypeSelect id="xmzbl" name="xmzbl" type="select" code="XMZBL" codeType="0"></t:codeTypeSelect> --%>
	            	
	            	<input id="xmzbl"  name="xmzbl" type="text" value="${tPmProjectPage.xmzbl}"  />
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">项目组比例</label>
	            </td> 
            </tr>
            
<!--               <td align="right"><label class="Validform_label"> 子类型: </label></td> -->
<!--               <td class="value"><input id="subType" name="subType" type="text" style="width: 160px" class="inputxt" datatype="byterange" max="40" min="0" ignore="ignore" -->
<%--                   value='${tPmProjectPage.subType}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">子类型</label></td> --%>
				<td align="right"><label class="Validform_label"> 是否校内协作: </label></td>
            	<td class="value">
            	<c:if test="${empty tPmProjectPage.schoolCooperationFlag}">
            		<t:codeTypeSelect id="schoolCooperationFlag" name="schoolCooperationFlag" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="0"></t:codeTypeSelect>
            	</c:if>
            	<c:if test="${!empty tPmProjectPage.schoolCooperationFlag}">
            		<t:codeTypeSelect id="schoolCooperationFlag" name="schoolCooperationFlag" code="SFBZ" codeType="0" extendParam="{onclick:'selectSchoolCooperation(this.value);'}" type="radio" defaultVal="${tPmProjectPage.schoolCooperationFlag}"></t:codeTypeSelect>
            	</c:if>
             	<span class="Validform_checktip"></span>
              	<label class="Validform_label" style="display: none;">是否校内协作</label></td>
              <td align="right"><label class="Validform_label"> 分管部门: </label></td>
              <td class="value">
                <t:codeTypeSelect name="manageDepartCode" type="select" codeType="1" code="FGBM" id="manageDepartCode" labelText="请选择" defaultVal="${tPmProjectPage.manageDepartCode}"></t:codeTypeSelect>
                <input id="manageDepart" name="manageDepart" value="${tPmProjectPage.manageDepart}" type="hidden" datatype="s2-20" ignore="ignore">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">分管部门</label>
              </td>
              <td align="right"><label class="Validform_label"> 审批权限: </label></td>
              <td class="value">
	          			<t:codeTypeSelect name="approvalAuthority" type="select" codeType="1" code="SPQX" id="approvalAuthority" labelText="请选择"
	              defaultVal="${tPmProjectPage.approvalAuthority}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审批权限</label>	
             </td>
            </tr>
            <tr id="parentProjectLabel">
            	<td align="right"><label class="Validform_label"> 所属母项目: </label></td>
              <td class="value" colspan="3"><input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}"> <input id="parentProjectName"
                  type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.parentProjectName}' readonly="readonly"> 
               <t:choose url="tPmProjectController.do?projectSelect" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="parentProjectId,parentProjectName"
              isclear="true" fun="parentProjectSelectCallback"></t:choose> <span class="Validform_checktip"></span> <label class="Validform_label"
                  style="display: none;">所属母项目</label></td>
                 <td align="right"><label class="Validform_label"> 间接费: </label></td>
            <td class="value">
            		<t:codeTypeSelect id="indirect" name="indirect" code="JJF" codeType="0" type="radio" defaultVal="1"></t:codeTypeSelect>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">间接费</label></td>
            </tr>
            
            <tr>
              <td align="right"><label class="Validform_label"> 申报金额: </label></td>
            <td class="value"><input id="budgetAmount" name="budgetAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
              style="width: 180px; text-align: right;" value="${tPmProjectPage.budgetAmount}">&nbsp;元 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报金额</label></td> 
              <td align="right"><label class="Validform_label"> 经费单列: </label></td>
              <td class="value"><input id="feeSingleColumn" name="feeSingleColumn" type="text" style="width: 140px" class="inputxt" value='${tPmProjectPage.feeSingleColumn}'> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">经费单列</label></td>
              <td align="right"><label class="Validform_label"> 总经费(元): </label></td>
              <td class="value"><input id="allFee" name="allFee" type="text" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" style="width: 140px; text-align: right;"
                  value='${tPmProjectPage.allFee}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">总经费</label></td>
            </tr>
            
            <tr>
              <td align="right"><label class="Validform_label"> 来源计划/合同名称: </label></td>
              <td class="value"><input id="planContractName" name="planContractName" type="text" style="width: 330px" class="inputxt" value='${tPmProjectPage.planContractName}'><img alt="甲方合同名称" title="甲方合同名称" src="plug-in\easyui1.4.2\themes\icons\tip.png">
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源计划/合同名称</label></td>
              <td align="right"><label class="Validform_label"> 计划/合同标志: </label></td>
              <td class="value"><input type="text" id="planContractFlagName" readonly style="width: 140px" class="inputxt" class="inputxt" value="${planContractFlag}"> <input
                  type="hidden" id="planContractFlag" name="planContractFlag" value="${tPmProjectPage.planContractFlag}" > <span class="Validform_checktip"></span> <label
                  class="Validform_label" style="display: none;">计划/合同标志</label></td>
              <td align="right"><label class="Validform_label"> 合同日期: </label></td>
              <td class="value"><input id="contractDate" name="contractDate" type="text" ignore="ignore" datatype="date" style="width: 140px" class="Wdate" onClick="WdatePicker()"
                  value='<fmt:formatDate value='${tPmProjectPage.contractDate}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
                  style="display: none;">合同日期</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 来源计划/合同文号: </label></td>
              <td class="value"><input id="planContractRefNo" name="planContractRefNo" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.planContractRefNo}'> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源计划/合同文号</label></td>
              <td align="right"><label class="Validform_label"> 外来编号: </label></td>
              <td class="value"><input id="outsideNo" name="outsideNo" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.outsideNo}'> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">外来编号</label></td>
              <td align="right"><label class="Validform_label"> 是否需要鉴定: </label></td>
              <td class="value"><t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.appraisalFlag}"></t:codeTypeSelect> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否需要鉴定</label></td>
            </tr>
            <tr>
              <td align="right"><label class="Validform_label"> 项目来源: </label></td>
              <td class="value"><input id="projectSource" name="projectSource" type="text" style="width: 160px" class="inputxt" value='${tPmProjectPage.projectSource}'> <span
                  class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目来源</label></td>
              <td align="right"><label class="Validform_label"> 来源单位: </label><font color="red">*</font></td>
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
            	<input type="text" id="sourceUnit" name="sourceUnit" style="width:150px;" nullmsg="" errormsg="数据不存在,请重新输入" value="${tPmProjectPage.sourceUnit}">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">来源单位</label></td>
<%--               <td class="value"><input id="sourceUnit" name="sourceUnit" type="text" style="width: 430px" class="inputxt" value='${tPmProjectPage.sourceUnit}' datatype="*"> <span --%>
<!--                   class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源单位</label></td> -->
              <td align="right"><label class="Validform_label"> 是否合并其他项目: </label></td>
                <td class="value"><t:codeTypeSelect id="mergeFlag" name="mergeFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"
                extendParam="{onclick:'selectProject(this.value);'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目编号</label></td>
            </tr>
            <tr id="merge" style="display: none;">
            	<td align="right"><label class="Validform_label"> 合并项目:<font color="red">*</font></label></td>
            	<td class="value" colspan="3" ><input type="hidden" id="mergePId" name="mergePId"> <textarea wrap="off" id="mergePName" name="mergePName" type="text"
                	style="width: 300px; height: 40px;" class="inputxt" readonly="readonly"></textarea>
              	<t:chooseProject inputId="mergePId" inputName="mergePName" title="合并项目" isclear="true"></t:chooseProject> <%-- <t:choose url="tPmProjectController.do?projectMultipleSelect" name="processtree" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="mergePId,mergePName" isclear="true"></t:choose> --%></td>
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
            <!-- modified by zhangls,at 2016-01-19，取消计划年度 begin -->
            <%--<tr>
             <td align="right">
              <label class="Validform_label"> 计划年度: </label>
            </td>
            <td class="value">
              <input id="planYear" name="planYear" type="text" ignore="ignore" datatype="*" style="width: 100px" readonly="readonly" value="${tPmProjectPage.planYear}" class="Wdate"
                onClick="WdatePicker({dateFmt: 'yyyy'})">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">计划年度</label>
            </td> 
          </tr>--%>
            <!-- modified by zhangls,at 2016-01-19，取消计划年度 end -->
            <tr>
              <td align="right"><label class="Validform_label"> 大学主管参谋: </label><font color="red">*</font></td>
              <td class="value"><input type="text" id="officeStaffRealname" readonly style="width: 120px" class="inputxt" value="${officeStaffRealname}" datatype="*"> <input type="hidden"
                  id="officeStaff" name="officeStaff" value='${tPmProjectPage.officeStaff}'><t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="officeStaff"
                  textname="id,realName" inputTextname="officeStaff,officeStaffRealname"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">大学主管参谋</label></td>
              <td align="right"><label class="Validform_label"> 院系主管参谋: </label></td>
              <td class="value"><input type="text" id="departStaffRealname" readonly style="width: 120px" class="inputxt" value='${departStaffRealname}'> <input type="hidden"
                  id="departStaff" name="departStaff" value='${tPmProjectPage.departStaff}'> <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="departStaff"
                  textname="id,realName" inputTextname="departStaff,departStaffRealname"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">院系主管参谋</label></td>
              <td align="right"><label class="Validform_label"> 是否需要认款: </label></td>
              <td class="value">
                 <t:codeTypeSelect id="incomeApplyFlag" name="incomeApplyFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.incomeApplyFlag}" ></t:codeTypeSelect> 
                 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否需要认款</label></td>
            </tr>
            <tr>
          		<td align="right"><label class="Validform_label"> 型号背景: </label></td>
          		<td class="value">
          			<input type="hidden" id="modelBackgroundStr" value='${tPmProjectPage.modelBackground}'>
                	<select id="modelBackground" class="easyui-combobox" name="modelBackground" style="width:200px; height:27px;">   
 
					</select><img alt="可输可选" title="可输可选" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            	</td>
            	<td align="right"><label class="Validform_label"> 舰艇舷号: </label></td>
            	<td class="value"><input id="shipNumber" name="shipNumber" type="text" datatype="*0-5"
              		style="width: 200px; text-align: right;" value="${tPmProjectPage.shipNumber}"><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">舰艇舷号</label></td>
              	<td align="right"><label class="Validform_label"> 配套情况: </label></td>
                <td class="value"><t:codeTypeSelect name="matchSituation" type="select" codeType="1" code="PTQK" id="matchSituation" labelText="请选择"
                    defaultVal="${tPmProjectPage.matchSituation}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">配套情况</label></td>
          	  </tr>
          	     
          </table>
        </div>
      </fieldset>
    </t:formvalid>
  </div>
  <!-- -------------------项目组成员-------------华丽的分割线---------------------------------- -->
  <%-- <div class="easyui-layout" style="margin-top:10px;height:500px;width:850px;">
  <div region="center" style="padding:1px;">
    <div id="tempSearchColums" style="display: none">
        <div name="searchColums">
          <span style="display: -moz-inline-box; display: inline-block;">
              <span style="vertical-align: middle; display: -moz-inline-box; 
                display: inline-block; width: 120px; text-align: right; 
                text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; 
                white-space: nowrap;" title="学位">学位：</span>
              <t:codeTypeSelect name="degree" type="select" codeType="0" code="XWLB" 
                labelText="全选" id=""></t:codeTypeSelect>
          </span>
        </div>
    </div>
   
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
     <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
     <t:dgDelOpt exp="projectManager#eq\#${no}" title="删除" url="tPmProjectMemberController.do?doDel&id={id}" />
     <t:dgToolBar title="录入" icon="icon-add" width="450" height="370" url="tPmProjectMemberController.do?goAdd&projectId=${tPmProjectPage.id }" funname="add"></t:dgToolBar>
     <t:dgToolBar title="编辑" icon="icon-edit" width="450" height="370"  url="tPmProjectMemberController.do?goUpdate&projectId=${tPmProjectPage.id }" funname="update"></t:dgToolBar>
    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmProjectMemberController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
     <t:dgToolBar title="查看" width="450" height="370" icon="icon-search" url="tPmProjectMemberController.do?goUpdate" funname="detail"></t:dgToolBar>
     <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
     <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
     <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
    </t:datagrid>
  </div>
  </div> --%>
  </div>
</body>
<script src="webpage/com/kingtake/project/manage/tPmProject.js"></script>
<script type="text/javascript">
    //双击查看方法
    function dblClickDetail(rowIndex, rowData) {
        var title = "查看";
        var url = "tPmProjectMemberController.do?goUpdate&load=detail&id=" + rowData.id;
        createdetailwindow(title, url, null, null);
    }
</script>
<script src="webpage/com/kingtake/project/manage/project-edit.js"></script>