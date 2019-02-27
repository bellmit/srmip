<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划下达录入信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {            
        //垫支科目代码下拉选择框事件
        $("#payfirstId").combobox({
            onLoadSuccess : function() {
            	if($("#payfirstId").val() != ""){
            		$("#payfirstFundsLabel").css('visibility','visible');
                	$("#payfirstFundsText").css('visibility','visible');
            	}
            },
            onChange : function(newValue, oldValue) {
            	$("#payfirstFundsLabel").css('visibility','visible');
            	$("#payfirstFundsText").css('visibility','visible');
            }
        });
        
        //判断是否有子项目，有的话则显示
        var hasSubprojectFlag = $("#hasSubprojectFlag").val();
        if (hasSubprojectFlag == 'true') {
        	initschoolCooperationTab();
        	$('#schoolCooperationList').css("display","block");
        }else{
        	$('#schoolCooperationList').css("display","none");
        }        
        
        var jffjFlag = $("#jffjFlag").val();
        if(jffjFlag == "true"){
        	$("#documentInfo").find('input,textarea,select').attr('readonly',true);
        	$("#projectName").next('a').css('display','none');
        	$("#documentTime").removeAttr("onclick");
        }               
        
        //判断是否校内协作
//         var schoolCooperationFlag = $("#schoolCooperationFlag").val();
//         if (schoolCooperationFlag == '1') {
//         	$("#fundsSources").val('2');
//         }else{
//         	$("#fundsSources").val('0');
//         } 
    });

    //编写自定义JS代码
    function uploadFile(data) {
    	if(W.$.dialog.list['processDialog'] != undefined){
    		var win = W.$.dialog.list['processDialog'].content;
            win.reloadTable();
            win.tip(data.msg);
            frameElement.api.close();
    	}            
    	else{
            	frameElement.api.opener.reloadTable();
            	frameElement.api.opener.tip(data.msg);
               frameElement.api.close();
               
           }
    }
    
    function uploadCallback(){
//     	if(windowapi != undefined){
//     		var win = windowapi;
//             win.reloadTable();
//             win.tip(data.msg);
//             frameElement.api.close();
//     	}     
//         var win = W.$.dialog.list['processDialog'].content;
//         win.reloadTable();
//         win.tip("保存数据成功!");
        frameElement.api.close();
//         reloadTable();
//         tip("保存数据成功!");
    }

    //提交前检验
    function checkData() {    
    	var planAmount = $("#planAmount").numberbox("getValue");
    	
    	//判断直接经费、间接经费和归垫经费加起来是否超过了认领金额
        var jffjFlag = $("#jffjFlag").val();
        if(jffjFlag == "true"){
        	var directFunds = parseFloat($("#directFunds").numberbox("getValue"));
        	var indirectFunds = parseFloat($("#indirectFunds").numberbox("getValue"));
        	var payfirstFunds = $("#payfirstFunds").numberbox("getValue") == "" ? 0 : parseFloat($("#payfirstFunds").numberbox("getValue"));
        	var totalFunds = directFunds + indirectFunds + payfirstFunds;
        	if (parseFloat(planAmount) < totalFunds) {
            	tip("经费构成合计大于认领金额！");
            	return false;
        	}
        }
    
//         var rows = $('#schoolCooperationList').datagrid("getRows");
        if ($('#schoolCooperationList').css('display') == "block") {
            var rows = $('#schoolCooperationList').datagrid("getRows");
            var delRows = [];
            for (var i = 0; i < rows.length; i++) {
                var editors = $('#schoolCooperationList').datagrid("getEditors", i);
                if (editors.length == 0) {
                    if (rows[i].PLANAMOUNT == "" || rows[i].PLANAMOUNT == null) {
                        delRows.push(i);
                    }
                    continue;
                }
                if (!$('#schoolCooperationList').datagrid('validateRow', i)) {
                    return false;
                }
                $('#schoolCooperationList').datagrid("endEdit", i);
            }
            for (var i = 0; i < delRows.length; i++) {
                $('#schoolCooperationList').datagrid("deleteRow", delRows[i]);
            }
            rows = $('#schoolCooperationList').datagrid("getRows");
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total = total + parseFloat(rows[i].PLANAMOUNT);
            }

//             applyAmount = applyAmount.replace(",","");
            if (parseFloat(planAmount) < total + totalFunds) {
                tip("经费构成合计大于认领金额！");
                return false;
            }

            var schoolCooperationListStr = JSON.stringify(rows);
            $("#schoolCooperationListStr").val(schoolCooperationListStr);
        }               
        
        //判断预留金额加起来是否超过了认领金额
        var ylFlag = $("#ylFlag").val();
        if(ylFlag == "true"){
        	var universityAmount = parseFloat($("#universityAmount").numberbox("getValue"));
            var academyAmount = parseFloat($("#academyAmount").numberbox("getValue"));
            var departmentAmount = parseFloat($("#departmentAmount").numberbox("getValue"));
            var performanceAmount = parseFloat($("#performanceAmount").numberbox("getValue"));
            var indirectFunds = parseFloat($("#indirectFunds").numberbox("getValue"));
            var totalAmount = universityAmount + academyAmount + departmentAmount + performanceAmount;
            if (parseFloat(indirectFunds) < totalAmount) {
                tip("预留金额模块合计大于间接经费！");
                return false;
            }
        }        
    }
    
    function openDialog(msgText){
        W.$.dialog({
			content: '<textarea id="msgTextArea" rows="5" cols="5" style="width: 327px; height: 188px;" readonly="true">'+msgText+'</textarea>',
			lock : true,
			width:350,
			height:200,
			parent:windowapi,
			title:'查看修改意见',
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
    }
    
    //初始化校内协作经费表格
    function initschoolCooperationTab() {
//         var toolbar = [];
//         toolbar = [ {
//             iconCls : 'icon-add',
//             text : '录入',
//             handler : function() {
//             	openSelectSchoolCooperationWin();
//             }
//         }, {
//             iconCls : 'icon-remove',
//             text : '删除',
//             handler : function() {
//                 var checked = $("#schoolCooperationList").datagrid("getSelections");
//                 for (var i = 0; i < checked.length; i++) {
//                     var index = $("#schoolCooperationList").datagrid('getRowIndex', checked[i]);
//                     $("#schoolCooperationList").datagrid("deleteRow", index);
//                 }
//             }
//         } ];	
		var id = $("#id").val();
        var projectId = $("#projectId").val();
        var url = "";
        if (projectId != "") {
            url = 'tPmIncomePlanController.do?getSubprojectList&projectId=' + projectId + '&planId='+ id;
        }
        $('#schoolCooperationList').datagrid({
            url : url,
            title : '校内协作经费',
            fitColumns : true,
            nowrap : true,
            height : 200,
            //width : 900,
            striped : true,
            remoteSort : false,
            idField : 'id',
            editRowIndex : -1,
            singleSelect : true,
//             toolbar : toolbar,
            columns : [ [  {
                field : 'INCOMEPLANID',
                title : '计划下达id',
                width : 100,
                hidden : true
            },{
                field : 'PROJECTNAME',
                title : '项目名称',
                width : 100
            }, {
                field : 'PROJECTID',
                title : '项目id',
                width : 100,
                hidden : true
            },{
                field : 'ALPLANAMOUNT',
                title : '已分配金额',
                width : 100
            }, {
                field : 'PLANAMOUNT',
                title : '分配金额',
                width : 100,
                editor : {
                    type : 'numberbox',
                    options : {
                        min : -99999999.99,
                        precision : '2',
                        groupSeparator : ','
                    }
                },
            } ] ],
            onDblClickRow : function(rowIndex, rowData) {
                $(this).datagrid('beginEdit', rowIndex);
            },
            onBeforeEdit : function(rowIndex, rowData) {

            },
            onAfterEdit : function(rowIndex, rowData) {

            },
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
//                 var auditStatus = $("#auditStatus").val();
//                 if (auditStatus == '1'||auditStatus == '2'||location.href.indexOf("load=detail")!=-1) {
//                     $("a.jeecgDetail").hide();
//                     $('div.datagrid-toolbar').hide();
//                 }
            }
        });
    }
    
    //打开录入校内协作页面
//     function openSelectSchoolCooperationWin() {
//         var projectId = $("#projectId").val();
//         var url = 'tPmIncomeApplyController.do?goSchoolCooperation';
//         if (typeof (windowapi) == 'undefined') {
//             $.dialog({
//                 content : 'url:' + url,
//                 lock : true,
//                 width : '400px',
//                 height : '270px',
//                 top : '20%',
//                 title : "新增校内协作经费",
//                 opacity : 0.3,
//                 cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     var schoolCooperationInfo = iframe.getSchoolCooperationInfo();
//                         $("#schoolCooperationList").datagrid("appendRow", {
//                             id : '',
//                             projectName : schoolCooperationInfo[1],
//                             projectId : schoolCooperationInfo[0],
//                             applyAmount : schoolCooperationInfo[2]
//                         });
//                 },
//                 cancelVal : '关闭',
//                 cancel : function() {
//                 }
//             });
//         } else {
//             W.$.dialog({
//                 content : 'url:' + url,
//                 lock : true,
//                 width : '400px',
//                 height : '270px',
//                 top : '20%',
//                 title : "新增校内协作经费",
//                 opacity : 0.3,
//                 parent : windowapi,
//                 cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     var schoolCooperationInfo = iframe.getSchoolCooperationInfo();
//                     $("#schoolCooperationList").datagrid("appendRow", {
//                         id : '',
//                         projectName : schoolCooperationInfo[1],
//                         projectId : schoolCooperationInfo[0],
//                         applyAmount : schoolCooperationInfo[2]
//                     });
//                 },
//                 cancelVal : '关闭',
//                 cancel : function() {
//                 }
//             });
//         }
//     }
    
	function getProjectId(){
		return document.getElementById("projectId").value ;
  	}
	function getPlanAmount(){
		return document.getElementById("planAmount").value ;
  	}
</script>
</head>
<body>
  <div style="">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tPmIncomePlanController.do?doSave" tiptype="1" callback="@Override uploadCallback" beforeSubmit="checkData">
      <input id="id" name="id" type="hidden" value="${tPmIncomePlanPage.id }">      
      <input id="projectPlanId" name="projectPlanId" type="hidden" value="${tPmIncomePlanPage.projectPlanId }">
      <input id="createBy" name="createBy" type="hidden" value="${tPmIncomePlanPage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tPmIncomePlanPage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tPmIncomePlanPage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tPmIncomePlanPage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tPmIncomePlanPage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tPmIncomePlanPage.updateDate }">
      <input id="hasSubprojectFlag" type="hidden" value="${hasSubprojectFlag}">
      <input id="schoolCooperationFlag" type="hidden" value="${schoolCooperationFlag}">
      <input id="ylFlag" type="hidden" value="${ylFlag}">
      <input id="jffjFlag" type="hidden" value="${jffjFlag}">
      <table id="documentInfo" style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
        <c:if test="${jffjFlag ne false}">
        <tr>
          <td align="right" ><label class="Validform_label"> 文件号: </label> <font color="red">*</font></td>
          <td class="value">
          <input id="documentNo" name="documentNo" type="text" datatype="*1-50" style="width: 250px" value='${tPmIncomePlanPage.documentNo}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">文件号</label></td>
          <td align="right" ><label class="Validform_label"> 文件名: </label> <font color="red">*</font></td>
          <td class="value">
          <input id="documentName" name="documentName" type="text" datatype="*1-100" style="width: 250px" value='${tPmIncomePlanPage.documentName}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">文件名</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 发文时间: </label><font color="red">*</font></td>
            <td class="value"><input id="documentTime" name="documentTime" type="text" datatype="date" style="width: 250px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmIncomePlanPage.documentTime}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发文时间</label></td>
          <td align="right"><label class="Validform_label">年度:<font color="red">*</font></label></td>
			<td class="value"><input id="planYear" name="planYear" type="text" style="width: 250px" class="inputxt" datatype="/[0-9]{1,4}/" errormsg="请输入1-4位数字" value='${tPmIncomePlanPage.planYear}'><img alt="会计年度，导出数据包给财务时将按照此年度来导出" title="会计年度，导出数据包给财务时将按照此年度来导出" src="plug-in\easyui1.4.2\themes\icons\tip.png">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">年度</label>
						</td>          
        </tr>
        </c:if>
        <tr>
        <c:if test="${jffjFlag ne false}">
        <td align="right"><label class="Validform_label"> 来源经费科目: </label><font color="red">*</font></td>
          <td class="value"><input id="fundsSubject" name="fundsSubject" type="text" style="width: 250px" value='${tPmIncomePlanPage.fundsSubject}' datatype="*1-10"> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源经费科目</label></td>
              </c:if>
         <td align="right" width="120px"><label class="Validform_label"> 金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="planAmount" name="planAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.planAmount}'> <span class="Validform_checktip"></span> 
              <label class="Validform_label" style="display: none;">金额</label></td>
        </tr>        
        <tr>
        	<td align="right"><label class="Validform_label"> 项目名称: </label></td>
            <td class="value" colspan="3"><input id="projectId" name="project.id" type="hidden" value="${tPmIncomePlanPage.project.id}"> 
            <input id="projectName" type="text" style="width: 250px" class="inputxt" readonly="readonly" value="${tPmIncomePlanPage.project.projectName}"> 
              <t:choose url="tPmProjectController.do?projectSelect&plan=1" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="projectId,projectName"
              isclear="true"></t:choose>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
<!--         	<td align="right"><label class="Validform_label">项目名称:<font color="red">*</font></label></td> -->
<!--         	<td class="value"> -->
<%--         	<input id="projectId" name="project.id" type="hidden" value="${tPmIncomePlanPage.project.id }"> --%>
<%--         	<input id="projectName" name="projectName" type="text" style="width: 250px" class="inputxt" value='${tPmIncomePlanPage.project.projectName}' datatype="*">  --%>
<%--         	<t:chooseProject inputName="projectName" inputId="projectId" mode="single" all="true"></t:chooseProject>  --%>
<!--         	<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">项目名称</label> -->
<!--             </td> -->
     	</tr>      
      </table>
      <c:if test="${jffjFlag eq true || ylFlag eq true}">
      <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">经费构成</span>
      </legend>
      <div>
      <table style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      	<tr>
        	<td align="right"><label class="Validform_label"> 经费来源: </label><font color="red">*</font></td>
          	<td class="value">      
          		<t:codeTypeSelect name="fundsSources" type="select" codeType="1" code="JFLY" defaultVal='${tPmIncomePlanPage.fundsSources}' id="fundsSources" labelText="请选择" extendParam="{datatype:'*'; style:'width:255px;'; disabled:'true'}" ></t:codeTypeSelect> 
          		<span class="Validform_checktip"></span> 
          		<label class="Validform_label" style="display: none;">经费来源</label>   		
            </td>
        </tr>
      	<tr>
        <td align="right" width="120px"><label class="Validform_label"> 直接经费(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="directFunds" name="directFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.directFunds}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">直接经费</label></td>
        <td align="right" width="120px"><label class="Validform_label"> 间接经费(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="indirectFunds" name="indirectFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.indirectFunds}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">间接经费</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 垫支科目代码: </label> </td>
          <td class="value">
          	<input id="payfirstId" name="payfirstId" class="easyui-combobox" value="${tPmIncomePlanPage.payfirstId}" data-options="valueField:'id',editable:false,textField:'text',url:'tPmPayfirstController.do?getPayFirstList&projectId=${tPmIncomePlanPage.project.id}'" style="width: 256px;" />          
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">垫支科目代码</label></td>
          <td align="right" width="120px" id="payfirstFundsLabel" style="visibility:hidden;"><label class="Validform_label"> 归垫经费(元): </label></td>
          <td class="value" id="payfirstFundsText" style="visibility:hidden;">
          	<input id="payfirstFunds" name="payfirstFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" min="1" value='${tPmIncomePlanPage.payfirstFunds}'> 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">归垫经费</label></td>
        </tr>
      </table> 
      <div style="margin: 10px auto; width: 900px;">
        <div id="schoolCooperationList"></div>
      </div>       
      </div>
    </fieldset> 
    </c:if>   
    <c:if test="${ylFlag eq true}">
    <fieldset id="ylfs" style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">预留金额模块</span>
      </legend>
      <div>
      <table style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      	<tr>
        <td align="right" width="150px"><label class="Validform_label"> 大学预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="universityAmount" name="universityAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.universityAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">大学预留金额</label></td>
        <td align="right" width="150px"><label class="Validform_label"> 院预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="academyAmount" name="academyAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.academyAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">院预留金额</label></td>
        </tr>
        <tr>
        <td align="right" width="150px"><label class="Validform_label"> 系预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="departmentAmount" name="departmentAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.departmentAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">系预留金额</label></td>
        <td align="right" width="150px"><label class="Validform_label"> 绩效奖励金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="performanceAmount" name="performanceAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomePlanPage.performanceAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">绩效奖励金额</label></td>
        </tr>
      </table>      
      </div>
    </fieldset> 
    </c:if>  
      <input id="schoolCooperationListStr" name="schoolCooperationListStr" type="hidden">
    </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>