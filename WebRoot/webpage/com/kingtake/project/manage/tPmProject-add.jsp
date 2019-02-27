<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<c:if test="${type eq '1'}">
<title>项目立项信息表</title>
</c:if>
<c:if test="${type ne '1'}">
<title>项目申报信息表</title>
</c:if>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript">
var assignFlag;
$(function() {


	$("#projectType").combotree({
	    url : 'tPmProjectController.do?getProjectType',
		valueField : 'id',
		textField : 'projectTypeName',
		onLoadSuccess : function() {
			var projectType = $("#projectType").val();
			if (projectType != "") {
				$("#projectType").combobox('setValue', projectType);
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
					$("#feeTypeName").val(fund.fundsName);
					$("#feeType").val(fund.id);
					$("#planContractFlagName").val(approval.name);
					$("#planContractFlag").val(approval.code);
				}
			});
		}
	});

/* 	$("#jflx").combotree({
		url : 'tBXmlbController.do?getJflx',
		valueField : 'id',
		textField : 'text',
		onChange : function() {
			var jflx = $("#jflx").combobox('getValue');
			$("#jflxId").val(jflx);
		}
	});
	 */
	 
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
		 ],
         onChange : function() {
             var jjfjsfs = $("#jjfjsfs").combobox('getValue');
             $("#jjfjsfs").val(jjfjsfs);
         }
	 });
	 
	//经费类型
	$("#feeType").combotree({
		url : 'tBXmlbController.do?getFeeType',
		valueField : 'id',
		textField : 'text',
		onChange : function() {
			var jflx = $("#feeType").combobox('getValue');
			$("#feeTypeId").val(jflx);
			console.log(jflx);
			
			//选择经费类型，联动预算类型和比例
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : 'tBXmlbController.do?getProportion',// 请求的action路径
				dataType : 'json',
				data : {
					'id' : jflx
				},
				success : function(data) {
					console.log(data);
					if(data.msg=="1"){
						tip('不能选择母项目！');
						$("#yslx").combotree('setValue', "");
						$("#jjfjsfs").combotree('setValue', "");
						$("#dxbl").val("");
						$("#zrdwbl").val("");
						$("#cydwbl").val("");
						$("#xmzbl").val("");
					}else{
						$("#yslx").combotree('setValue', data.yslx);
						$("#jjfjsfs").combotree('setValue', data.jjfjsfs);
						$("#dxbl").val(data.dxbl);
						$("#zrdwbl").val(data.zrdwbl);
						$("#cydwbl").val(data.cydwbl);
						$("#xmzbl").val(data.xmzbl);
					}
				}
			});
			
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
						//$("#jflx").combotree('setValue', data.jflxStr);
						//$("#jflxId").val(data.jflx);
						//$("#xmlx").val(data.xmlx);
						//$("#xmxz").val(data.xmxz);
						$("#xmly").val(data.xmly);
						$("#xmml").val(data.xmml);
					}
				}
			});
		 }
	});
	
	$('#modelBackground').combobox({    
	    url:'tBCodeTypeController.do?typeCombo&codeType=1&code=XHBJ',    
	    valueField:'NAME',    
	    textField:'NAME'   
	}); 
	
	$("#parentProjectNo").blur(function(){
		var parentPmProjectNo = $(this).val();
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "tPmProjectController.do?validformProjectNo",// 请求的action路径
			dataType : 'json',
			data : {
				"projectNo":parentPmProjectNo
			},
			success : function(data) {
				if(data.status){
				    $('#parentProjectId').val(data.projectId);
				    $('#parentProjectName').val(data.projectName);
				}else{
				    $.messager.alert('提示', '所属母项目申报码不存在，请重新输入');
	                $.Hidemsg();
				}
			}
		});
    });
	
});
	
	

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
	
//在提交之前进行检查
function checkBeforeSubmit(){
    var managerDepartCode = $("#manageDepartCode").val();
    if(managerDepartCode!=""){
       var manageDepart=$("#manageDepartCode").find("option:selected").text();
       $("#manageDepart").val(manageDepart);
    }
	if ($('#mergePId').val() != '') {
		$.dialog({
			title : '提示信息',
			content : '项目（'
					+ $('#mergePName').val()
					+ '）将会被合并，是否确定保存？',
			zIndex : 2101,
			lock : true,
			opacity : 0.3,
			button : [ {
				name : '确定',
				callback : function() {
				    $("#formobj").Validform({callback:callback}).ajaxPost();
				},
				focus : true
			}, {
				name : '关闭'
			} ]
		}).zindex();
		return false;
	} 
	
// 	var entryType = $("#entryType").val();
// 	if(entryType == "JG"){
// 		var approvalAuthority = $("#approvalAuthority").val();
// 		if(approvalAuthority == ""){
// 			tip('请选择审批权限');
// 			return false;
// 		}
// 	}	
	if('${type}'=='1'){
		return;
	}
	var budgetAmount = $('#budgetAmount').numberbox('getValue');
	if(budgetAmount == ""){
		$('#budgetAmount').numberbox('setValue', 0);
	}
	
}
	
//回调函数
function callback(data) {
    var win = frameElement.api.opener;
    if (data.success == true) {
        var d = $.parseJSON(data.obj);
        var projectId = d.id; 
        win.tip(data.msg);
        win.$("#projectId").val(projectId);
        win.$("#treeTabs").tabs('select',1);
        win.$("#unapprovalTree").tree("reload");//刷新项目树
        var entryType = $("#entryType").val();
    	if(entryType == "JG"){
        	win.reloadtPmProjectList();
    	}
        frameElement.api.close();        
        //win.$("#processtree")
       /*  var url = "tPmProjectController.do?goUpdateForResearchGroup&id="+projectId;
        win.$("#contentFrame").attr("src",url); */
    } else {
        if (data.responseText == '' || data.responseText == undefined) {
            $.messager.alert('错误', data.msg);
            $.Hidemsg();
        } else {
            try {
                var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText
                        .indexOf('错误信息'));
                $.messager.alert('错误', emsg);
                $.Hidemsg();
            } catch (ex) {
                $.messager.alert('错误', data.responseText + "");
                $.Hidemsg();
            }
        }
        return false;
    }
}

function uidChange(){
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
			//$('#managerPhone').val(user.officePhone ? user.officePhone : user.mobilePhone);
			$("#developerDepartId").val(departId = '' ? '' : departId);
			$("#devDepartDepartname").combotree('setValue', departName = '' ? '' : departName);
			$("#dutyDepartId").val(departPid = '' ? '' : departPid);
			$("#dutyDepartDepartname").combotree('setValue', departPname = '' ? '' : departPname);
		}
	});
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

/*
<<<<<<< .mine
=======
    $("#bid").val(data.obj.id);
    if($(".uploadify-queue-item").length>0){
        upload();
       // $(win).showTip('dsadsa');
    }else{
    	console.log($(win));
    	//$(win).selectTab('dsfdsf',0);
        frameElement.api.close();
    } 
    //var win = frameElement.api.opener;
    try{
    win.reloadtPmProjectList();
    }catch(e){
    	
    }
   // win.reloadtPmProjectList();
}
>>>>>>> .r114*/

function parentProjectSelectCallback(data){
	alert(data);
}

function selectSbProject(win){ 
	checkProIsContract($("#projectId").val());   //lijun拦截合同类型的项目没有上传合同且审批的情况
	uidChange();
	var xmlb = $("#xmlb").val();
	var xmlbId= $("#xmlbId").val();
	$("#xmlb").combotree('setValue', xmlb = '' ? '' : xmlb);
	$("#xmlbId").val(xmlbId);
	
/* 	var jflx = $("#jflx").val();
	var jflxId = $("#jflxId").val();
	$("#jflx").combotree('setValue', jflx = '' ? '' : jflx);
	$("#jflxId").val(jflxId); */
	
	var feeType = $("#feeType").val();
	var feeTypeId = $("#feeTypeId").val();
	$("#feeType").combotree('setValue', feeType = '' ? '' : feeType);
	$("#feeTypeId").val(feeTypeId);
	
	var modelBackground = $("#modelBackgroundStr").val();
	$('#modelBackground').combobox('setValue', modelBackground);
	
	var beginDate = $("#beginDate").val();
	 $("#beginDate").val(beginDate.substring(0,10));
	var endDate = $("#endDate").val();
	 $("#endDate").val(endDate.substring(0,10));
	
}

//=================lijun拦截合同类型的项目没有上传合同且审批的情况
function checkProIsContract(pId) { 
	$.ajax({
		type: "post",
		async: false,
		url: "tPmProjectController.do?checkProIsContract",
		data: {"pId": pId},
		dataType: "json",
		success: function(data) {
			var d = $.parseJSON(data);
			if($("#lxyj").val() == 2 && d.FINISH_FLAG != "2") {
				alert("该上传合同正本再立项！");
				window.location.reload(); 
				return false;
			}
		}
	});
}
</script>
</head>

<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmProjectController.do?doAdd" tiptype="1" btnsub="saveBtn" beforeSubmit="checkBeforeSubmit"
    callback="@Override uploadFile">
    <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <c:if test="${type eq '1'}">
      <h1 align="center">项目立项信息表</h1>
      </c:if>
      <c:if test="${type ne '1'}">
      <h1 align="center">新增项目申报信息表</h1>
      </c:if>
      <span><input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px;" value="保存"></span>
    </div>
    <br />
    <br />
    <input id="id" name="id" type="hidden" value="${tPmProjectPage.id }" >
    <input id="assignFlag" name="assignFlag" type="hidden" value="${tPmProjectPage.assignFlag }">
    <input id="projectStatus" name="projectStatus" type="hidden" value="<%=ProjectConstant.PROJECT_STATUS_APPLYING%>">
    <input id="entryType" name="entryType" type="hidden" value="${entryType}">
    <input id="reportState" name="reportState" type="hidden" value="<%=ProjectConstant.PROJECT_UNREPORT%>">
    <input id="lxStatus" name="lxStatus" type="hidden" value="${type}">
    <input id="jhid" name="jhid" type="hidden" value="${projectPlanId}">
    <input id="greatSpecialFlag" name="greatSpecialFlag" type="hidden" value="0">
    <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">基本信息</span>
      </legend>
      <div>
        <table style="width: 100%;" cellpadding="0" cellspacing="0" class="formtable">
         <tr>
          	<c:if test="${type eq '1'}">
				<td align="right"><label class="Validform_label"> 项目申报名称: </label></td>
            	<td class="value" colspan="3"><input id="projectId" name="glxm.id" type="hidden" > 
					<input id="projectName2" type="text" style="width: 822px" class="inputxt" readonly="readonly"> 
					 <t:choose url="tPmProjectController.do?projectSelect&plan=1" width="1000px" height="460px" left="10%" top="10%" fun="selectSbProject"
					name="projectList" icon="icon-search" title="项目列表" textname="id,projectName,projectName,beginDate,endDate,projectManagerId,projectManager,devDepart_id,contact,accordingNum,managerPhone,contactPhone,accountingCode,projectAbstract,xmlb_id,xmlb_xmlb,xmml,jflx_id,jflx_jflxmc,feeTypeStr,feeType_id,xmlx,xmxz,rwzd,lxyj,schoolCooperationFlag,appraisalFlag,feeSingleColumn,budgetAmount,matchSituation,planContractName,approvalAuthority,planContractRefNo,modelBackground,shipNumber,lhsb,officeStaff,officeStaffRealname,sourceUnit" inputTextname="projectId,projectName2,projectName,beginDate,endDate,projectManagerId,projectManager,orgId,contact,accordingNum,managerPhone,contactPhone,accountingCode,projectAbstract,xmlbId,xmlb,xmml,jflxId,jflx,feeType,feeTypeId,xmlx,xmxz,rwzd,lxyj,schoolCooperationFlag,appraisalFlag,feeSingleColumn,allFee,matchSituation,planContractName,approvalAuthority,planContractRefNo,modelBackgroundStr,shipNumber,lhsb,officeStaff,officeStaffRealname,sourceUnit" isclear="true"></t:choose>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目申报名称</label>
				</td>
			</c:if>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 项目名称: <font color="red">*</font></label></td>
            <td class="value" colspan="3"><input id="projectName" name="projectName" type="text" style="width: 822px" datatype="*2-30" class="inputxt" value="${tPmProjectPage.projectName }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目名称</label></td>
           
               <td align="right"><label class="Validform_label"> 项目密级: <font color="red">*</font></label></td>
            <td class="value"><t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" code="XMMJ" codeType="0" extendParam="{style:'width:206px'}" defaultVal="${tPmProjectPage.secretDegree }"></t:codeTypeSelect> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目密级</label></td>
              
           
          </tr>
          <tr>
           
           <%--
            <td align="right"><label class="Validform_label"> 是否重大专项 : </label></td>
            <td class="value">
            <c:if test="${empty tPmProjectPage.greatSpecialFlag}">
            <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect>
            </c:if>
            <c:if test="${!empty tPmProjectPage.greatSpecialFlag}">
            <t:codeTypeSelect id="greatSpecialFlag" name="greatSpecialFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.greatSpecialFlag}"></t:codeTypeSelect>
            </c:if>
            <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否重大专项 </label></td>
               --%>
          <c:if test="${type ne '1'}">
            <td align="right">
              <label class="Validform_label"> 申报编号: </label>
            </td>
            <td class="value">
              <input id="cxm" name="cxm" readonly="true" type="text" ignore="ignore" datatype="s2-20" style="width: 160px" class="inputxt" validType="T_PM_PROJECT,PROJECT_NO,ID"
                value='${tPmProjectPage.cxm}' readonly="readonly">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">申报编号</label>
            </td>
          </c:if>
          <c:if test="${type eq '1'}">
            <td align="right">
              <label class="Validform_label"> 项目编号: </label>
            </td>
            <td class="value">
              <input id="projectNo" name="projectNo" readonly="true" type="text" ignore="ignore" datatype="s2-20" style="width: 160px" class="inputxt" validType="T_PM_PROJECT,PROJECT_NO,ID"
                value='${tPmProjectPage.projectNo}' readonly="readonly">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目编号</label>
            </td>
          </c:if> 
              
              <td align="right"><label class="Validform_label"> 起始日期: <font color="red">*</font></label></td>
            <td class="value"><input id="beginDate" name="beginDate" type="text" datatype="date" style="width: 200px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value='<fmt:formatDate value='${tPmProjectPage.beginDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">起始日期</label></td>
              <td align="right"><label class="Validform_label"> 截止日期: <font color="red">*</font></label></td>
            <td class="value"><input id="endDate" name="endDate" type="text" datatype="date" style="width: 200px" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})" value='<fmt:formatDate value='${tPmProjectPage.endDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">截止日期</label></td>
          </tr>
          <tr>
           
            <td align="right"><label class="Validform_label"> 项目组长: <font color="red">*</font></label></td>
            <td class="value"><input id="projectManagerId" name="projectManagerId" type="hidden" style="width: 150px" class="inputxt" value="${tPmProjectPage.projectManagerId}"> <input id="orgId" name="orgId" type="hidden"
              style="width: 150px" class="inputxt"> <input id="projectManager" name="projectManager" type="text" datatype="*" style="width: 150px" class="inputxt" readonly="readonly" value="${tPmProjectPage.projectManager}">
              <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId" isclear="true" inputTextname="projectManagerId,projectManager,orgId" idInput="projectManagerId"
                mode="single" fun="uidChange"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报人</label></td>
            
            <td align="right"><label class="Validform_label"> 承研部门: <font color="red">*</font></label></td>
            <td class="value"><input name="devDepartDepartname" id="devDepartDepartname" value="${tPmProjectPage.devDepart.departname}"> <script type="text/javascript">
		                	//选择承研单位时，将承研单位的父单位加入责任单位
							$(function(){
								$('#devDepartDepartname').combotree({
									url : 'departController.do?getDepartTree&lazy=false',
									width : '206',
									height : '27',
									multiple : false,
									onSelect : function(record){
										$("#developerDepartId").val(record.id);
										var tree = $('#devDepartDepartname').combotree('tree');
										var parent = tree.tree('getParent', record.target);
										if(parent != null){
											$("#dutyDepartId").val(parent.id);
											$("#dutyDepartDepartname").combotree('setValue', parent.text);
										}
									}
								});
							});
						</script> <input id="developerDepartId" name="devDepart.id" type="hidden" datatype="*" value="${tPmProjectPage.devDepart.id }"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承研部门</label>
            </td>   
            
             <td align="right"><label class="Validform_label"> 责任部门:&nbsp;&nbsp;&nbsp; </label></td>
            <td class="value"><t:departComboTree id="dutyDepartDepartname" idInput="dutyDepartId" lazy="true" width="206" value="${tPmProjectPage.dutyDepart.departname}"></t:departComboTree> <input id="dutyDepartId" name="dutyDepart.id"
              type="hidden" style="width: 200px" class="inputxt" value="${tPmProjectPage.dutyDepart.id}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">责任部门</label></td>         
          </tr>
          <tr>
           
            <td align="right"><label class="Validform_label"> 负责人电话: <font color="red">*</font></label></td>
            <td class="value"><input id="managerPhone" name="managerPhone" type="text" style="width: 200px" class="inputxt" datatype="*1-30" value="${tPmProjectPage.managerPhone}"> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">负责人电话</label></td>
            <td align="right"><label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: <font color="red">*</font></label></td>
            <td class="value"><input id="contact" name="contact" type="text" datatype="s2-16" style="width: 200px" class="inputxt" value="${tPmProjectPage.contact}">
             <img alt="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" title="“联系人”与“联系人电话”为“科研助理人”与“科研助理人电话”" src="plug-in\easyui1.4.2\themes\icons\tip.png"> 
             <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人</label></td>
        
           <td align="right"><label class="Validform_label"> 关键词:&nbsp;&nbsp;&nbsp; </label></td>
            <td class="value"><input id="accordingNum" name="accordingNum" type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${tPmProjectPage.accordingNum }"> <t:choose
                url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px" height="450px" icon="icon-search" title="收发文列表" textname="mergeFileNum"
                inputTextname="accordingNum" isclear="true"></t:choose> <label class="Validform_label" style="display: none;">关键词</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 联系人电话: <font color="red">*</font></label></td>
            <td class="value"><input id="contactPhone" name="contactPhone" type="text" style="width: 200px" class="inputxt" datatype="*1-30" value="${tPmProjectPage.contactPhone}"> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">联系人电话</label></td>
            <td align="right"><label class="Validform_label"> 会计编码:&nbsp;&nbsp;&nbsp; </label></td>
            <td class="value"><input id="accountingCode" name="accountingCode" type="text" datatype="s2-250" ignore="ignore" style="width: 200px" class="inputxt" value="${tPmProjectPage.accountingCode}">
            <img alt="填写会计编码后必须做总预算，否则无法认款" title="填写会计编码后必须做总预算，否则无法认款" src="plug-in\easyui1.4.2\themes\icons\tip.png">
             <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 项目简介: <font color="red">*</font></label></td>
            <td colspan="5" class="value"><textarea id="projectAbstract" maxlength="100" name="projectAbstract"  datatype="*" style="width: 822px; height: 80px;" 
            onchange="this.value=this.value.substring(0, 100)" onkeydown="this.value=this.value.substring(0, 100)" onkeyup="this.value=this.value.substring(0, 100)"
            placeholder="简要介绍项目的背景，字数不超过100字"  >${tPmProjectPage.projectAbstract}</textarea> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目简介</label></td>
          </tr>
        </table>
      </div>
    </fieldset>
    <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #228B22">其他信息</span>
      </legend>
      <div>
        <table style="width: 100%;" cellpadding="0" cellspacing="0" class="formtable">
          <tr>
            <%-- <td align="right"><label class="Validform_label"> 项目类型: </label><font color="red">*</font></td>
            <td class="value"><input id="projectType" name="projectType.id" style="width: 306px; height: 27px;" value="${tPmProjectPage.projectType.id}"><input id="projectTypeId" type="hidden"
                  value="${tPmProjectPage.projectType.id}" datatype="*"><span class="Validform_checktip"></span> <label class="Validform_label"
              style="display: none;">项目类型</label></td>
            
            <td align="right"><label class="Validform_label"> 经费类型: </label><font color="red">*</font></td>
            <td class="value"><input id="feeTypeName" type="text" style="width: 200px" class="inputxt" datatype="*" readonly value="${tPmProjectPage.feeType.fundsName}"> <input id="feeType" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}"> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">经费类型</label></td>
            <td align="right"><label class="Validform_label"> 计划/合同标志: </label><font color="red">*</font></td>
            <td class="value"><input type="text" id="planContractFlagName" readonly style="width: 200px" class="inputxt" value="${planContractFlag}" datatype="*"> <input type="hidden" id="planContractFlag"
              name="planContractFlag" value="${tPmProjectPage.planContractFlag}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计划/合同标志</label></td>  
            --%>
            <td align="right"><label class="Validform_label"> 项目类型: </label><font color="red">*</font></td>
            <td class="value">
            	<input id="xmml" name='xmml' type="text" readonly="readonly" style="background-color: #E8E8E8;" class="inputxt" datatype="*">
            	<img alt="该项属于项目子类的母项，无需填写，选择项目子类后自动带出" title="该项属于项目子类的母项，无需填写，选择项目子类后自动带出" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">项目类型</label>
            </td>
            <td align="right"><label class="Validform_label"> 项目子类: </label><font color="red">*</font></td>
            <td class="value">
            	<input id="xmlb">
            	<input id="xmlbId" name="xmlb.id" type="hidden" datatype="*">
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">项目子类</label>
            </td>
            <td align="right">
            	<label class="Validform_label"> 经费类型: </label>
            	<font color="red">*</font>
            </td>
            <td class="value">
            <%--
            	<input id="jflx" style="width: 300px; height: 27px;">
            	<input id="jflxId" name="jflx.id" type="hidden">
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">经费类型</label>
            	
            	 --%>
            	<input id="feeType" >
            	<input id="feeTypeId" name="feeType.id" type="hidden" value="${tPmProjectPage.feeType.id}" datatype="*">
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">经费类型</label>
            	
            </td>
          </tr>
          
          <tr>
          	<%-- 
          	<td align="right"><label class="Validform_label"> 主管单位: </label><font color="red">*</font></td>
            <td class="value">
            	<input id="zgdw" name='zgdw' type="text" style="width: 200px" class="inputxt" datatype="*">
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">主管单位</label>
            </td>
            --%>
            <td align="right"><label class="Validform_label"> 项目属性: </label><font color="red">*</font></td>
            <td class="value">
            	<t:codeTypeSelect id="xmlx" name="xmlx" type="select" code="XMLX" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">项目属性</label>
            </td>
            <td align="right"><label class="Validform_label"> 项目性质: </label><font color="red">*</font></td>
            <td class="value">
            	<t:codeTypeSelect id="xmxz" name="xmxz" type="select" code="XMXZ" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">项目性质</label>
            </td>
            <td align="right"><label class="Validform_label"> 预算类型: </label></td>
            <td class="value">
            	<t:codeTypeSelect id="yslx" name="yslx" type="select" code="YSLX" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">预算类型</label>
            </td>
          </tr>
          
          <tr>
          	<%-- 
          	<td align="right">
            	<label class="Validform_label"> 项目来源: </label>
            	<font color="red">*</font>
            </td>
            <td class="value">
            	<t:codeTypeSelect id="xmly" name="xmly" type="select" code="XMLY" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">项目来源</label>
            </td>
            --%>
            
            <td align="right"><label class="Validform_label"> 任务指定标志位: </label></td>
            <td class="value">
            	<t:codeTypeSelect id="rwzd" name="rwzd" type="select" code="RWZD" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span> 
            	<label class="Validform_label" style="display: none;">任务指定标志位</label>
            </td>
            <td align="right"><label class="Validform_label"> 立项依据标志位: </label></td>
            <td class="value">
            	<t:codeTypeSelect id="lxyj" name="lxyj" type="select" code="LXYJ" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">立项依据标志位</label>
            </td>
            <%-- <td align="right"><label class="Validform_label"> 申报日期: </label></td>
            <td class="value"><input id="contractDate" name="contractDate" type="text" ignore="ignore" datatype="date" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmProjectPage.contractDate}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报日期</label></td> --%>
             <td align="right"><label class="Validform_label"> 间接费计算方式: </label></td>
             <td class="value">
            	<t:codeTypeSelect id="jjfjsfs" name="jjfjsfs" type="select" code="JJFJSFS" codeType="0"></t:codeTypeSelect>
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">间接费计算方式</label>
            </td>
          </tr>
          
          <tr>
<!--             <td align="right"><label class="Validform_label"> 子类型: </label></td> -->
<%--             <td class="value"><input id="subType" name="subType" type="text" style="width: 300px" class="inputxt" datatype="byterange" max="40" min="0" ignore="ignore" value="${tPmProjectPage.subType}"> <span --%>
<!--               class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">子类型</label></td> -->
				<td align="right"><label class="Validform_label"> 是否需要鉴定: </label></td>
           		<td class="value">
		            <c:if test="${empty tPmProjectPage.appraisalFlag}">
		            <t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0" type="radio" defaultVal="1"></t:codeTypeSelect>
		            </c:if>
		            <c:if test="${!empty tPmProjectPage.appraisalFlag}">
		            <t:codeTypeSelect id="appraisalFlag" name="appraisalFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tPmProjectPage.appraisalFlag}"></t:codeTypeSelect>
		            </c:if>
		             <span class="Validform_checktip"></span>
		              <label class="Validform_label" style="display: none;">是否需要鉴定</label>
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
		            <%--隐藏，默认0 --%>
		            <input type="hidden" name="mergeFlag" id="mergeFlag" value="0"> 
	              
	              	
	                 <div style="display:none; background-color: #e5efff;">
				      	<div id="file_upload_schoolCooperationFlag"></div>
					    <div class="form" id="filediv_schoolCooperationFlag"></div>
					 </div>
	             </td>
	             <td align="right"><label class="Validform_label"> 大学比例: </label></td>
	             <td class="value">
	            	<%-- <t:codeTypeSelect id="dxbl" name="dxbl" type="select" code="DXBL" codeType="0"></t:codeTypeSelect> --%>
	            	<input id="dxbl" name="dxbl" type="text" value="${tPmProjectPage.dxbl}" />
	            	<span class="Validform_checktip"></span>
	            	<label class="Validform_label" style="display: none;">大学比例</label>
	            </td>
	            
              <%-- 
              <td align="right"><label class="Validform_label"> 是否合并其他项目: </label></td>
           		 <td class="value"><t:codeTypeSelect id="mergeFlag" name="mergeFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"
                extendParam="{onclick:'selectProject(this.value);'}"></t:codeTypeSelect>
                <img alt="此情况适用于校内联合申报" title="此情况适用于校内联合申报" src="plug-in\easyui1.4.2\themes\icons\tip.png">
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否合并其他项目</label></td>
              --%>
          </tr>
          <tr id="parentProjectLabel" style="display:none;">
          	<td align="right"><label class="Validform_label"> 所属母项目申报码: </label></td>
            <td class="value">
             <input id="parentProjectId" name="parentPmProject.id" type="hidden" value="${tPmProjectPage.parentPmProject.id}"> 
             <input id="parentProjectName" type="hidden" style="width: 300px" class="inputxt" value="${tPmProjectPage.parentProjectName}"> 
             <input id="parentProjectNo" name="parentPmProject.projectNo" type="text" value="${tPmProjectPage.parentPmProject.projectNo}"> 
             <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属母项目申报码</label>
            </td>
           
            <%-- 
            <input id="parentProjectName" type="text" style="width: 300px"
              class="inputxt"  value="${tPmProjectPage.parentProjectName}"> 
              --%>
           
           
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
          <tr id="merge" style="display: none;">
            <td align="right"><label class="Validform_label"> 合并项目:<font color="red">*</font></label></td>
            <td class="value" colspan="3" ><input type="hidden" id="mergePId" name="mergePId"> <textarea wrap="off" id="mergePName" name="mergePName" type="text"
                style="width: 300px; height: 40px;" class="inputxt" readonly="readonly"></textarea>
              <t:chooseProject inputId="mergePId" inputName="mergePName" title="合并项目" isclear="true"></t:chooseProject> <%-- <t:choose url="tPmProjectController.do?projectMultipleSelect" name="processtree" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="mergePId,mergePName" isclear="true"></t:choose> --%></td>
          </tr>
          <tr>          	
            <td align="right"><label class="Validform_label"> 经费单列: </label></td>
            <td class="value"><input id="feeSingleColumn" name="feeSingleColumn" type="text" style="width: 200px" class="inputxt" value="${tPmProjectPage.feeSingleColumn}"> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">经费单列</label></td>
<!--             <td align="right"><label class="Validform_label"> 科目代码: </label></td> -->
<%--             <td class="value"><input id="subject_code" name="subject_code" type="text" style="width: 200px" class="inputxt" value="${tPmProjectPage.subject_code}"> <span class="Validform_checktip"></span> <label --%>
<!--               class="Validform_label" style="display: none;">科目代码</label></td> -->
<!--               <input id="tzys_status" name="tzys_status" type="text" value="0" style="display:none"> -->
          	<c:if test="${type eq '1'}">
          	<td align="right"><label class="Validform_label"> 总经费: </label><font color="red">*</font></td>
          	</c:if>
          	<c:if test="${type ne '1'}">
          	<td align="right"><label class="Validform_label"> 申报金额: </label><font color="red">*</font></td>
          	</c:if>
            <td class="value">
            <c:if test="${type eq '1'}">
	            <input id="allFee" name="allFee" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
		              style="width: 180px; text-align: right;" value="${tPmProjectPage.allFee}" datatype="*">&nbsp;元
	        </c:if>      
           <c:if test="${type ne '1'}">
	            <input id="budgetAmount"   name="budgetAmount" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
	              style="width: 180px; text-align: right;" value="${tPmProjectPage.budgetAmount}"  datatype="*">&nbsp;元 
            </c:if> 
              <c:if test="${type ne '1'}">
              <img alt="申报金额大于100万时，会由校长审签" title="申报金额大于100万时，会由校长审签" src="plug-in\easyui1.4.2\themes\icons\tip.png">
              </c:if>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申报金额</label></td>
             <td align="right"><label class="Validform_label"> 责任单位比例: </label></td>   
             <td class="value">
            	<%-- <t:codeTypeSelect id="zrdwbl" name="zrdwbl" type="select" code="ZRDWBL" codeType="0"></t:codeTypeSelect> --%>
            	
            	<input id="zrdwbl" name="zrdwbl" type="text" value="${tPmProjectPage.zrdwbl}"  />
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">责任单位比例</label>
            </td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 来源计划/合同名称: </label></td>
            <td class="value"><input id="planContractName" name="planContractName" type="text" datatype="byterange" max="1000" min="0" class="inputxt" value="${tPmProjectPage.planContractName}">
            <img alt="甲方合同名称" title="甲方合同名称" src="plug-in\easyui1.4.2\themes\icons\tip.png"> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">来源计划/合同名称</label></td>
            
             <td align="right"><label class="Validform_label"> 配套情况: </label></td>
            <td class="value"><t:codeTypeSelect name="matchSituation" type="select" codeType="1" code="PTQK" id="matchSituation" labelText="请选择" 
              defaultVal="${tPmProjectPage.matchSituation}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">配套情况</label></td>
             <td align="right"><label class="Validform_label"> 承研单位比例: </label></td>   
             <td class="value">
            	<%-- <t:codeTypeSelect id="cydwbl" name="cydwbl" type="select" code="CYDWBL" codeType="0"></t:codeTypeSelect> --%>
            	
            	<input id="cydwbl" name="cydwbl" type="text" value="${tPmProjectPage.cydwbl}"  />
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">承研单位比例</label>
            </td> 
            <%-- 
            <td align="right"><label class="Validform_label"> 分管部门: </label></td>
            <td class="value"><t:codeTypeSelect name="manageDepartCode"  labelText="请选择" type="select" codeType="1" code="FGBM" id="manageDepartCode" extendParam='{"style":"width:206px;"}' defaultVal="${tPmProjectPage.manageDepartCode}"></t:codeTypeSelect> <input id="manageDepart"
              name="manageDepart" type="hidden" datatype="s2-20" ignore="ignore" value="${tPmProjectPage.manageDepart}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">分管部门</label></td>
             --%>
             <%-- 
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
	          	--%>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 来源计划/合同文号: </label></td>
            <td class="value"><input id="planContractRefNo" name="planContractRefNo" type="text" datatype="byterange" max="1000" min="0" style="width: 300px" class="inputxt" value="${tPmProjectPage.planContractRefNo}"> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">来源计划/合同文号</label></td>
             <%-- 
               <td align="right"><label class="Validform_label"> 外来编号: </label></td>
              <td class="value"><input id="outsideNo" name="outsideNo" type="text" ignore="ignore" datatype="s2-20" style="width: 200px" class="inputxt" value="${tPmProjectPage.outsideNo}"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">外来编号</label></td>
               --%>
               
              <td align="right"><label class="Validform_label"> 型号背景: </label></td>
          	<td class="value">
          	    <input type="hidden" id="modelBackgroundStr" value=''>
                <select id="modelBackground" class="easyui-combobox" name="modelBackground" style="width:200px; height:27px;">   
 
				</select><img alt="可输可选" title="可输可选" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            </td>
            <td align="right"><label class="Validform_label"> 项目组比例: </label></td>   
             <td class="value">
            	<%-- <t:codeTypeSelect id="xmzbl" name="xmzbl" type="select" code="XMZBL" codeType="0"></t:codeTypeSelect> --%>
            	
            	<input id="xmzbl"  name="xmzbl" type="text" value="${tPmProjectPage.xmzbl}"  />
            	<span class="Validform_checktip"></span>
            	<label class="Validform_label" style="display: none;">项目组比例</label>
            </td> 
          </tr>
          <tr>
           <!--  <td align="right"><label class="Validform_label"> 项目来源: </label></td>
       		<td class="value"><input id="projectSource" name="projectSource" type="text" style="width: 300px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目来源</label></td> -->
<%--             <td class="value"><input id="sourceUnit" name="sourceUnit" type="text" style="width: 300px" class="inputxt" datatype="*" value="${tPmProjectPage.sourceUnit}"> <span class="Validform_checktip"></span> <label --%>
<!--               class="Validform_label" style="display: none;">来源单位</label></td> -->
          </tr>
          <%-- <tr>
			<td align="right"><label class="Validform_label"> 大学主管参谋: </label><font color="red">*</font></td>
            <td class="value">
              <input type="text" id="officeStaffRealname" readonly style="width: 200px" class="inputxt" value="${officeStaffRealname}" datatype="*"> 
              <input type="hidden" id="officeStaff" name="officeStaff" value="${tPmProjectPage.officeStaff}"> 
               <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="officeStaff" textname="id,realName"  inputTextname="officeStaff,officeStaffRealname"></t:chooseUser> 
               <span class="Validform_checktip"></span> 
               <label class="Validform_label" style="display: none;">大学主管参谋</label>
            </td>
            <td align="right"><label class="Validform_label"> 院系参谋: </label></td>
            <td class="value"><input type="text" id="departStaffRealname" readonly style="width: 300px" class="inputxt" value="${departStaffRealname}"> <input type="hidden" id="departStaff" name="departStaff" value="${tPmProjectPage.departStaff}">
              <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="departStaff" textname="id,realName" inputTextname="departStaff,departStaffRealname"></t:chooseUser> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">责任院系参谋</label></td>
                    
          </tr> --%>
          <tr>
            <td align="right"><label class="Validform_label">舰艇舷号: </label></td>
            <td class="value"><input id="shipNumber" name="shipNumber" type="text" datatype="*0-5"
              style="width: 200px; text-align: right;" value="${tPmProjectPage.shipNumber}"><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">舰艇舷号</label></td>
            <td align="right"><label class="Validform_label"> 是否联合校外单位申报: </label></td>
            <td class="value" colspan="3"><t:codeTypeSelect id="lhsb" name="lhsb" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect>
            <img alt="如选择是，则必须上传联合申报附件" title="如选择是，则必须上传联合申报附件" src="plug-in\easyui1.4.2\themes\icons\tip.png">
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否联合校外单位申报</label>
            
              
              <div style="display:none; background-color: #e5efff;">
		      	<div id="file_upload_lhsb"></div>
			    <div class="form" id="filediv_lhsb"></div>
			  </div>
            </td>
            
            <%--
            <td align="right"><label class="Validform_label"> 总经费: </label></td>
            <td class="value"><input id="allFee" name="allFee" type="text" class="easyui-numberbox" data-options="min:0,max:100000000000,precision:2,groupSeparator:','"
              style="width: 180px; text-align: right;" value="${tPmProjectPage.allFee}">&nbsp;元 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">总经费</label></td>
            --%>  
          </tr>
          <tr>
          	<td align="right"><label class="Validform_label"> 机关主管参谋: </label></td>
            <td class="value">
              <input type="text" id="officeStaffRealname" readonly style="width: 200px" class="inputxt" value="${officeStaffRealname}" > 
              <input type="hidden" id="officeStaff" name="officeStaff" value="${tPmProjectPage.officeStaff}" > 
               <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="officeStaff" textname="id,realName"  inputTextname="officeStaff,officeStaffRealname"></t:chooseUser> 
               <span class="Validform_checktip"></span> 
               <label class="Validform_label" style="display: none;">机关主管参谋</label>
            </td>
            <td align="right"><label class="Validform_label"> 主管部门/甲方单位: </label><font color="red">*</font></td>
            <td class="value">
            	<script type="text/javascript">
            		$(document).ready(function() {
            			$("#sourceUnit").autocomplete("tPmProjectController.do?getAutoSourceUnitList",{
            				max: 50,
            				minChars: 1,
            				width: 200,
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
            	<input type="text" id="sourceUnit" name="sourceUnit" datatype="byterange" max="2000" min="1" style="width:200px;" nullmsg="" errormsg="数据不存在,请重新输入" value="${tPmProjectPage.sourceUnit}">
            	<img alt="与公章名称一致" title="与公章名称一致" src="plug-in\easyui1.4.2\themes\icons\tip.png">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">主管部门/甲方单位</label></td>
          </tr>
         
          <tr>
	        <td align="right"><label class="Validform_label">申报书/合同草本: </label></td>
	        <td colspan="3" class="value">
	          <table style="max-width:92%;">
		        <c:forEach items="${tPmProjectPage.certificates }" var="file"  varStatus="idx" >
		          <tr>
		            <td><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tPmProjectPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td> 
		            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
		            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
		          </tr>
		        </c:forEach>
		      </table>
		      <div style="background-color: #e5efff;">
		      	<div id="file_upload"></div>
			    <div class="form" id="filediv"></div>
			  </div>
		    </td>
	      </tr>
        </table>
        <input type="hidden" value="${tPmProjectPage.id}" id="bid" name="bid" />
        <div style="color:red;text-align: center;">注：本功能区所有上传文件操作，在点击"保存"按钮后才会真正开始上传！</div>
        <!-- <button onclick="testUpload()">文件一起上传_测试</button> -->
      </div>
    </fieldset>
  </t:formvalid>
</body>
</html>
<script src="webpage/com/kingtake/project/manage/tPmProject.js"></script>
<script src="webpage/com/kingtake/project/manage/project-edit.js"></script>

<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/tPmProject-uploadfile.js"></script>

