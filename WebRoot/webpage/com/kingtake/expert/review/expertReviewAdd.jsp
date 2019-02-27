<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专家评审管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<style type="text/css">
a,img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.select-result .exp {
	width: 160px;
	padding: 0px;
	background: #aa36ed;
	display: inline-block;
	border: solid 1px;
	text-decoration: none;
	margin: 2px 5px;
	text-align: center;
	vertical-align: middle;
	color: white;
	border-radius: 4px;
}

.exp .close {
	background: url("images/close.gif") no-repeat scroll;
	height: 20px;
	position: relative;
	right: -145px;
	top: 8px;
	width: 20px;
	z-index: 1;
}

.exp table {
	width: 100%;
}

.exp tr td {
	border: 1px solid yellow;
}

.exp .close:hover {
	right: -144px;
	top: 9px;
}
</style>
<script>
//     function addExpert(row) {
//         var selectedDiv = $("<div class=\"exp\" id=\"" + nvl(row.id)
//                 + "\"><div class=\"close\"></div><table style=\"border-collapse:collapse\"><tr><td colspan=\"2\" align=\"center\" >" + nvl(row.name)
//                 + "</td></tr><tr><td>账号:</td><td>" + nvl(row.userName) + "</td></tr><tr><td>密码:</td><td>"
//                 + nvl(row.userPwd) + "</td></tr><tr><td>联系方式:</td><td>" + nvl(row.expertPhone)
//                 + "</td></tr></table></div>");
//         $("#expertDiv").append(selectedDiv);
//         selectedDiv.find(".close").bind("click", function() {
//             $(selectedDiv).remove();
//         });
//     }

//     function nvl(dat) {
//         if (dat) {
//             return dat;
//         } else {
//             return "";
//         }
//     }

    //选择专家
    function selectExp(expertId) {
//         var exps = $("#expertDiv").find(".exp");
        var ids = [];
        if(expertId != ""){
        	ids = expertId.split(",");
        }        
//         for (var i = 0; i < exps.length; i++) {
//             ids.push($(exps[i]).attr("id"));
//         }
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:tErExpertController.do?goSelectExpert&excludeIds=' + ids.join(","),
                zIndex : 2100,
                title : '选择专家',
                lock : true,
                width : '1040px',
                height : '400px',
                left : '20%',
                top : '40%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : clickcallback_40288aec4f39c2ca014f39c67cc30002,
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            });
        } else {
            $.dialog({
                content : 'url:tErExpertController.do?goSelectExpert&excludeIds=' + ids.join(","),
                zIndex : 2100,
                title : '选择专家',
                lock : true,
                parent : windowapi,
                width : '1040px',
                height : '400px',
                left : '20%',
                top : '40%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : clickcallback_40288aec4f39c2ca014f39c67cc30002,
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            });
        }
    }

    //清空专家
//     function clearExp() {
//         $("#expertDiv").find(".exp").remove();
//     }

    function clickcallback_40288aec4f39c2ca014f39c67cc30002() {
    	var rowIndex = $('#projectList').datagrid('getRowIndex',$('#projectList').datagrid('getSelected'));
        iframe = this.iframe.contentWindow;
        var rows = iframe.getselectedUserListSelections();
//         clearExp();
		var expertId = "";
		var expertName = "";
        for (var i = 0; i < rows.length; i++) {
        	expertName += rows[i].name + ",";
        	expertId += rows[i].id + ",";
        }
        if(expertId != ""){
        	expertId = expertId.substring("0", (expertId.length - 1));        	
        }
        if(expertName != ""){
        	expertName = expertName.substring("0", (expertName.length - 1));        	
        }
        $('#projectList').datagrid('updateRow',{
    		index: rowIndex,
    		row: {
    			expertId: expertId,
    			expertName: expertName
    		}
    	});
    }

    //回调保存方法
    function saveExpertReview() {
        var param = {};
        var id = $("#id").val();
        if(id!=""){
            param['id']=id;
        }
        var processType = $("#processType").val();
        var apprType = $("#apprType").val();
        var reviewProcess = processType+"_"+apprType;
        param['reviewProcess'] = reviewProcess;
        var accordingNum = $.trim($("#accordingNum").val());
        if (accordingNum == "") {
            tip('依据文号不能为空！');
            return false;
        }
        if (accordingNum.length > 12) {
            tip('依据文号长度不能大于12！');
            return false;
        }
        param["accordingNum"] = accordingNum;

        var reviewTitle = $.trim($("#reviewTitle").val());
        if (reviewTitle == "") {
            tip('评审标题不能为空！');
            return false;
        }
        if (reviewTitle.length > 100) {
            tip('评审标题长度不能大于100！');
            return false;
        }
        param["reviewTitle"] = reviewTitle;

        var reviewContent = $.trim($("#reviewContent").val());
        if (reviewContent == "") {
            tip('评审内容不能为空！');
            return false;
        }
        if (reviewContent.length > 400) {
            tip('评审内容长度不能大于400！');
            return false;
        }
        param["reviewContent"] = reviewContent;

        var planReviewDateBegin = $.trim($("#planReviewDateBegin").val());
//         if (planReviewDate == "") {
//             tip('计划评审时间不能为空！');
//             return false;
//         }
        param["planReviewDateBegin"] = planReviewDateBegin;
        var planReviewDateEnd = $.trim($("#planReviewDateEnd").val());
        param["planReviewDateEnd"] = planReviewDateEnd;
        var expertReviewDateBegin = $.trim($("#expertReviewDateBegin").val());
        param["expertReviewDateBegin"] = expertReviewDateBegin;
        var expertReviewDateEnd = $.trim($("#expertReviewDateEnd").val());
        param["expertReviewDateEnd"] = expertReviewDateEnd;
        var planReviewAddress = $.trim($("#planReviewAddress").val());
        param["planReviewAddress"] = planReviewAddress;

        var reviewMode = $.trim($("#reviewMode").val());
        if (reviewMode == "") {
            tip('评审方式不能为空！');
            return false;
        }
        param["reviewMode"] = reviewMode;

        var reviewTypes = $('[name="reviewType"]:checked');

        if (reviewTypes.length == 0) {
            tip("请选择项目评审内容！");
            return false;
        } else {
            var reviewTypeArr = [];
            for (var i = 0; i < reviewTypes.length; i++) {
                reviewTypeArr.push($(reviewTypes[i]).val());
            }
            param["reviewTypes"] = reviewTypeArr.join(",");
        }
		var rows = $('#projectList').datagrid('getRows');
		if(rows.length == 0){
			tip("请选择评审项目！");
         	return false;
		}
		for(var i = 0; i < rows.length; i++){
			if(rows[i].expertId == undefined || rows[i].expertId == ""){
				tip("请选择评审专家！");
	         	return false;
			}
		}
		param['projectExpertStr'] = JSON.stringify(rows);
		param['attachmentCode'] = $("#bid").val();
        var win = frameElement.api.opener;
        $.ajax({
            url : 'tErReviewMainController.do?saveReviewMainInfo',
            data :  param,
            dataType : 'json',
            type : 'POST',
            success : function(data) {
                $("#bid").val(data.obj.id);
                if (data.success) {
                           win.$("#tErReviewMainList").datagrid("reload");
                           win.tip(data.msg);
                           frameElement.api.close();
                } else {
                    win.tip(data.msg);
                }
            }
        });
        return false;
    }

    //选择所有
    function checkAll() {
        var checked = $("#checkAll").attr("checked");
        if (checked) {
            $('input[name="reviewType"]').attr("checked", "checked");
        } else {
            $('input[name="reviewType"]').removeAttr("checked");
        }
    }
    
    //选择项目
    function selectProject(){
    if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:tPmProjectController.do?projectMultipleSelect&mode=multiply&all=true',
                title : '选择项目',
                lock : true,
                width : '300px',
                height : '400px',
                left : '50%',
                top : '50%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : setProjectValue,
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            }).zindex();
        } else {
            W.$.dialog({
                content : 'url:tPmProjectController.do?projectMultipleSelect&mode=multiply&all=true',
                title : '选择项目',
                lock : true,
                parent : windowapi,
                width : '300px',
                height : '400px',
                left : '50%',
                top : '50%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : setProjectValue,
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            }).zindex();
        }
    }

//  旧的方式
//     function setProjectValue(){
//         iframe = this.iframe.contentWindow;
//         var check;
//         check = iframe.getChecked();
//         if ($('#projectIds').length >= 1) {
//             $('#projectIds').val(check[0]);
//             $('#projectIds').blur();
//         }
//         if ($('#projectNames').length >= 1) {
//             $('#projectNames').val(check[1]);
//             $('#projectNames').blur();
//         }
//     }

	function setProjectValue(){
        iframe = this.iframe.contentWindow;
        var check;
        check = iframe.getChecked();
        if (check.length >= 1) {
        	var arrayProjectIds = [];
        	arrayProjectIds = check[0].split(',');
        	var arrayProjectNames = [];
        	arrayProjectNames = check[1].split(',');
        	for (var i = 0; i < arrayProjectIds.length; i++) {
        		$('#projectList').datagrid('appendRow',{
        			id : arrayProjectIds[i],
        			projectId : arrayProjectIds[i],
                	projectName : arrayProjectNames[i]
                });
            }
        	
        }        
    }
    
    function clearProject() {
        var rows = $("#projectList").datagrid("getRows"); 
        while(rows.length > 0)        	
        {
        	$('#projectList').datagrid('deleteRow', 0);             
        }        		       
    }
    
    function deleteProject(index) {
        $('#projectList').datagrid('deleteRow', index);                    		       
    }
    
    var processTypeOpt = 
    [ {
        "processType" : "01",
        "name" : "项目申报"
    }, /* {
        "processType" : "02",
        "name" : "项目立项"
    }, */ {
        "processType" : "03",
        "name" : "项目执行"
    }, {
        "processType" : "04",
        "name" : "验收及结题"
    }, {
        "processType" : "05",
        "name" : "成果鉴定"
    } ];
    var apprTypeOpt = {
        "01" : [ {
            "apprType" : "01",
            "name" : "申报书评审"
        }, {
            "apprType" : "02",
            "name" : "进账合同审批"
        } ],
        "03" : [ {
            "apprType" : "01",
            "name" : "来款申请审核"
        }, {
            "apprType" : "02",
            "name" : "出账合同审批"
        } ],
        "04" : [ {
            "apprType" : "01",
            "name" : "结题申请审批"
        }, {
            "apprType" : "02",
            "name" : "合同验收报告"
        } ],
        "05" : [ {
            "apprType" : "01",
            "name" : "鉴定申请审批"
        } ]
    };

    $(function(){
        initSelect();//初始化过程
        initReviewType();//初始化评审材料
//         initExpert();//初始化专家
    });
    
    //初始化process
    function initSelect(){
        for(var i=0;i<processTypeOpt.length;i++){
            $("#processType").append("<option value=\""+processTypeOpt[i].processType+"\">"+processTypeOpt[i].name+"</option>");
        }
        var reviewProcess = $("#reviewProcess").val();
        if (reviewProcess == "") {
            var apprType1 = apprTypeOpt['01'];
            for (var i = 0; i < apprType1.length; i++) {
                $("#apprType").append("<option value=\""+apprType1[i].apprType+"\">" + apprType1[i].name + "</option>");
            }
        } else {
                var processArray = reviewProcess.split("_");
                var apprType1 = apprTypeOpt[processArray[0]];
                $("#processType").val(processArray[0]);
                for (var i = 0; i < apprType1.length; i++) {
                    $("#apprType").append("<option value=\""+apprType1[i].apprType+"\">" + apprType1[i].name + "</option>");
                }
                $("#apprType").val(processArray[1]);
        }
        $("#processType").change(
                function() {
                    $("#apprType").empty();
                    var processType = $("#processType").val();
                    var apprTypeList = apprTypeOpt[processType];
                    if (apprTypeList.length > 0) {
                        for (var i = 0; i < apprTypeList.length; i++) {
                            $("#apprType").append(
                                    "<option value=\""+apprTypeList[i].apprType+"\">" + apprTypeList[i].name
                                            + "</option>");
                        }
                    }
                });

    }

    //初始化评审材料
    function initReviewType() {
        var typeListStr = $("#typeListStr").val();
        var typeListArr = $.parseJSON(typeListStr);
        if (typeListStr != "") {
            for (var i = 0; i < typeListArr.length; i++) {
                $("#ck_" + typeListArr[i]).attr("checked", "checked");
            }
        }
    }

    //初始化专家信息
//     function initExpert() {
//         var expertListStr = $("#expertListStr").val();
//         if (expertListStr != "") {
//             var expertListArr = $.parseJSON(expertListStr);
//             for (var i = 0; i < expertListArr.length; i++) {
//                 addExpert(expertListArr[i]);
//             }
//         }
//     }
    
     function goUpdate(){
	 var row = $('#projectList').datagrid('getSelections')[0];
	 if(row==null){
		 tip("请选择一条记录进行编辑！");
	 }else{
		 var id = row.id;
		 if (typeof (windowapi) == 'undefined') {
	            $.dialog({
	            	content: 'url:tErReviewProjectController.do?goChooseForProjectAndExpert&id='+id,
					lock : true,
					title:"编辑",
					width : window.top.document.body.offsetWidth,
					height : window.top.document.body.offsetHeight-100,
					opacity : 0.3,
					cache:false,
					okVal:'保存',
					ok:function(){
						iframe = this.iframe.contentWindow;
						saveObj();
						return false;
					},
				    cancelVal: '关闭',
				    cancel: function(){

				    }
	            }).zindex();
	        } else {
	            W.$.dialog({
	            	content: 'url:tErReviewProjectController.do?goChooseForProjectAndExpert&id='+id,
					lock : true,
					title:"编辑",
					width : window.top.document.body.offsetWidth,
					height : window.top.document.body.offsetHeight-100,
					opacity : 0.3,
					cache:false,
					okVal:'保存',
					ok:function(){
						iframe = this.iframe.contentWindow;
						saveObj();
						return false;
					},
				    cancelVal: '关闭',
				    cancel: function(){

				    }
	            }).zindex();
	        }			 		
	 }
	 
 }
</script>
</head>
<body>
<textarea id="typeListStr" style="display: none;">${typeListStr}</textarea>
<textarea id="expertListStr" style="display: none;">${expertListStr}</textarea>
  <div class="easyui-layout" fit="true" split="true">
    <div region="west" style="padding: 1px; width: 800px;">
      <div class="easyui-layout" data-options="fit:true,split:true">
        <div data-options="region:'north'" title="<font color='red'>第一步:</font>填写评审信息" style="height: 330px;">
          <form id="mainForm">
          <input id="id" type="hidden" value="${tErReviewMainPage.id}">
            <table style="margin-left: 5px;" cellpadding="0" cellspacing="1">
              <tr style="display:none;">
                <td style="width: width: 135px; padding: 5px;" align="left"><label>
                    选择过程: <font color="red">*</font>
                  </label></td>
                  <td>
                <select id="processType" style="width: 100px;">
                </select>
                <select id="apprType" style="width:150px;">
                </select>
                <input id="reviewProcess" name="reviewProcess" type="hidden" value="${tErReviewMainPage.reviewProcess }">
                </td>
              </tr>
              <tr>
                <td style="width: width: 135px;padding:5px;" align="left"><label>
                    依据文号: <font color="red">*</font>
                  </label></td>
                <td style="width: 305px;padding:5px;"><span><input id="accordingNum" name="accordingNum" type="text" style="width: 170px" readonly="readonly" datatype="*" nullmsg="依据文号不能为空"
                    value="${tErReviewMainPage.accordingNum}"><a href="#" class="easyui-linkbutton" plain="true"
                    icon="icon-search" onClick="choose_40288aec4f449eb0014f44a80569000e()">选择</a> <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo"
                    onClick="clearAll_40288aec4f449eb0014f44a80569000e();">清空</a></span> <script type="text/javascript">
                                                                                    function choose_40288aec4f449eb0014f44a80569000e() {
                                                                                        if (typeof (windowapi) == 'undefined') {
                                                                                            $.dialog({
                                                                                                        content : 'url:tOSendReceiveRegController.do?selectReg',
                                                                                                        title : '收发文列表列表',
                                                                                                        lock : true,
                                                                                                        width : '800px',
                                                                                                        height : 350,
                                                                                                        left : '85%',
                                                                                                        top : '65%',
                                                                                                        opacity : 0.4,
                                                                                                        button : [
                                                                                                                {
                                                                                                                    name : '确定',
                                                                                                                    callback : clickcallback_40288aec4f449eb0014f44a80569000e,
                                                                                                                    focus : true
                                                                                                                },
                                                                                                                {
                                                                                                                    name : '取消',
                                                                                                                    callback : function() {
                                                                                                                    }
                                                                                                                } ]
                                                                                                    }).zindex();
                                                                                        } else {
                                                                                            W.$.dialog({
                                                                                                        content : 'url:tOSendReceiveRegController.do?selectReg',
                                                                                                        title : '收发文列表列表',
                                                                                                        lock : true,
                                                                                                        parent : windowapi,
                                                                                                        width : '800px',
                                                                                                        height : 350,
                                                                                                        left : '85%',
                                                                                                        top : '65%',
                                                                                                        opacity : 0.4,
                                                                                                        button : [
                                                                                                                {
                                                                                                                    name : '确定',
                                                                                                                    callback : clickcallback_40288aec4f449eb0014f44a80569000e,
                                                                                                                    focus : true
                                                                                                                },
                                                                                                                {
                                                                                                                    name : '取消',
                                                                                                                    callback : function() {
                                                                                                                    }
                                                                                                                } ]
                                                                                                    }).zindex();
                                                                                        }
                                                                                    }
                                                                                    function clearAll_40288aec4f449eb0014f44a80569000e() {
                                                                                        if ($('#accordingNum').length >= 1) {
                                                                                            $('#accordingNum').val('');
                                                                                            $('#accordingNum').blur();
                                                                                        }
                                                                                        if ($("input[name='accordingNum']").length >= 1) {
                                                                                            $(
                                                                                                    "input[name='accordingNum']")
                                                                                                    .val('');
                                                                                            $(
                                                                                                    "input[name='accordingNum']")
                                                                                                    .blur();
                                                                                        }
                                                                                    }
                                                                                    function clickcallback_40288aec4f449eb0014f44a80569000e() {
                                                                                        iframe = this.iframe.contentWindow;
                                                                                        var fileNum = iframe
                                                                                                .gettOSendReceiveRegListSelections('mergeFileNum');
                                                                                        if ($('#accordingNum').length >= 1) {
                                                                                            $('#accordingNum').val(
                                                                                                    fileNum);
                                                                                            $('#accordingNum').blur();
                                                                                        }
                                                                                        if ($("input[name='accordingNum']").length >= 1) {
                                                                                            $(
                                                                                                    "input[name='accordingNum']")
                                                                                                    .val(fileNum);
                                                                                            $(
                                                                                                    "input[name='accordingNum']")
                                                                                                    .blur();
                                                                                        }
                                                                                    }
                                                                                </script> <label class="Validform_label" style="display: none;">依据文号</label></td>
                </td>
              </tr>
              <!--  <tr>
            <td align="left"><label> 呈批文件号: </label></td>
            <td><input id="docNum" name="docNum" type="text" readOnly="true" style="width: 172px;"></td>
          </tr> -->
              <tr>
                <td align="left" style="padding:5px;"><label>
                    评审标题: <font color="red">*</font>
                  </label></td>
                <td style="padding:5px;"><input id="reviewTitle" name="reviewTitle" type="text" value='${tErReviewMainPage.reviewTitle}' style="width: 172px;" datatype="*" nullmsg="评审标题不能为空"> <label class="Validform_label" style="display: none;">评审标题</label></td>
              </tr>
              <tr>
                <td align="left" style="padding:5px;"><label>
                    评审内容: <font color="red">*</font>
                  </label></td>
                <td style="padding:5px;"><textarea id="reviewContent" name="reviewContent" rows="5" style="width: 172px; border-color: #54A5D5;" datatype="*" nullmsg="评审内容不能为空" errormsg="评审内容不能为空">${tErReviewMainPage.reviewContent}</textarea> <label
                    class="Validform_label" style="display: none;">评审内容</label></td>
              </tr>
              <tr>
                <td align="left" style="padding:5px;"><label>
                    计划评审开始时间: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewDateBegin" name="planReviewDateBegin" type="text" style="width: 172px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" datatype="date" nullmsg="计划评审开始时间不能为空"
                    value='<fmt:formatDate value='${tErReviewMainPage.planReviewDateBegin}' type="date" pattern="yyyy-MM-dd HH:mm"/>'> <label class="Validform_label" style="display: none;">计划评审开始时间</label></td>
                <td align="left" style="padding:5px;"><label>
                    计划评审结束时间: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewDateEnd" name="planReviewDateEnd" type="text" style="width: 172px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" datatype="date" nullmsg="计划评审结束时间不能为空"
                    value='<fmt:formatDate value='${tErReviewMainPage.planReviewDateEnd}' type="date" pattern="yyyy-MM-dd HH:mm"/>'> <label class="Validform_label" style="display: none;">计划评审结束时间</label></td>
              </tr>
              <tr>
                <td align="left" style="padding:5px;"><label>
                    专家评审开始时间: 
                  </label></td>
                <td style="padding:5px;"><input id="expertReviewDateBegin" name="expertReviewDateBegin" type="text" style="width: 172px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="date" nullmsg="专家评审开始时间不能为空"
                    value='<fmt:formatDate value='${tErReviewMainPage.expertReviewDateBegin}' type="date" pattern="yyyy-MM-dd"/>'> <label class="Validform_label" style="display: none;">专家评审开始时间</label></td>
                <td align="left" style="padding:5px;"><label>
                    专家评审结束时间: 
                  </label></td>
                <td style="padding:5px;"><input id="expertReviewDateEnd" name="expertReviewDateEnd" type="text" style="width: 172px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="date" nullmsg="专家评审结束时间不能为空"
                    value='<fmt:formatDate value='${tErReviewMainPage.expertReviewDateEnd}' type="date" pattern="yyyy-MM-dd"/>'> <label class="Validform_label" style="display: none;">专家评审结束时间</label></td>
              </tr>
              <tr>
              	<td align="left" style="padding:5px;"><label>
                    计划会审地点: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewAddress" name="planReviewAddress" type="text" value='${tErReviewMainPage.planReviewAddress}' style="width: 172px;" nullmsg="计划会审地点不能为空"> <label class="Validform_label" style="display: none;">计划会审地点</label></td>
                <td align="left" style="padding:5px;"><label>
                    评审方式: <font color="red">*</font>
                  </label></td>
                <td style="padding:5px;"><t:codeTypeSelect name="reviewMode" type="select" codeType="1" code="PSFS" id="reviewMode" defaultVal="${tErReviewMainPage.reviewMode}" extendParam="{style:\"width:178px;\"}"></t:codeTypeSelect></td>
              </tr>
            </table>
          </form>
        </div>
        <div data-options="region:'center'" title="<font color='red'>第二步:</font>选择评审项目及专家">
          <table style="margin-left: 5px;">
            <tr>
              <td style="padding:5px; width:70px;">选择项目：</td>
              <td style="padding:5px;">
                 <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onClick="selectProject()">选择</a> 
                 <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" onClick="clearProject();">清空</a>
              </td>
            </tr>
            <tr>
              <td colspan="2" height="400px" width="800px">
              <textarea id="projectExpertStr" rows="3" cols="3" style="display: none;"></textarea>
				<t:datagrid name="projectList" checkbox="false" fitColumns="true" idField="id" fit="true" queryMode="group" title="评审项目已选列表"
					actionUrl="tErReviewProjectController.do?datagridForProjectExpertList&reviewMain.id=${tErReviewMainPage.id}">
   					<t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
    				<t:dgCol title="项目id"  field="projectId" hidden="true"></t:dgCol>
    				<t:dgCol title="项目名称"  field="projectName" width="280" queryMode="group"></t:dgCol>
    				<t:dgCol title="评审专家ID" hidden="true" field="expertId" width="280" queryMode="group"></t:dgCol>
    				<t:dgCol title="评审专家"  field="expertName" width="280" queryMode="group"></t:dgCol>
<%--     				<t:dgToolBar title="新增评审" icon="icon-add" url="tErReviewProjectController.do?goChooseForProjectAndExpert" funname="add" height="100%" width="100%"></t:dgToolBar> --%>
<%--     				<t:dgToolBar title="编辑" icon="icon-edit"  funname="goUpdate" height="100%" width="100%"></t:dgToolBar> --%>
                   <c:if test="${load ne 'detail' }">
    				<t:dgCol title="操作" field="opt" width="150"></t:dgCol> 
   					<t:dgFunOpt funname="selectExp(expertId)" title="选择专家"></t:dgFunOpt>   
   					<t:dgFunOpt funname="deleteProject()" title="删除"></t:dgFunOpt> 
   				   </c:if>
  				</t:datagrid>			
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div region="center" style="padding: 1px;">
    <div class="easyui-layout" data-options="fit:true,split:true">
   		<div data-options="region:'center'" title="<font color='red'>第三步:</font>选择评审材料">
      		<table style="width: 100%; line-height: 3;" cellpadding="0" cellspacing="1">
        		<tr>
          			<td>
          				<label style="margin-left: 20px;">
          					<input type="checkbox" id="checkAll" onclick="checkAll()">全选
            			</label>
            		</td>
        		</tr>
        		<c:forEach items="${reviewTypeList}" var="type">
         		<tr>
            		<td>
            			<label style="margin-left: 20px;">
                		<input name="reviewType" id="ck_${type.id}" type="checkbox" value="${type.id}">${type.title}</label>
                		<input id="typeListStr" type="hidden" value="${typeListStr}">
                	</td>
          		</tr>
        		</c:forEach>
      		</table>
    	</div>
      <div data-options="region:'south'" title="<font color='red'>第四步:</font>上传附件" style="height: 200px;">
      <table>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value">
            <input type="hidden" value="${tErReviewMainPage.attachmentCode}" id="bid" name="attachmentCode">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tErReviewMainPage.attachments}" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=expertReviewMain" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
      </table>
    </div>
      </div>
    </div>
  </div>
  <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>
</html>