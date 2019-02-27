<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="webpage/com/kingtake/project/manage/proccessPage/style.css" type="text/css"></link>
<style type="text/css">
div.menuBtn {
	width: 152px;
    height: 90px;
	line-height: 2;
	color: black;
	margin: 0;
	padding: 0;
	display: inline-block;
	_display: inline;
	*display: inline;
	zoom: 1;
	cursor: pointer;
}

div.fadeMenu {
	background:
		url('webpage/com/kingtake/project/manage/img/processFade.png');
		no-repeat left top;
}

div.activeMenu {
  font-weight:bold;
	background:
		url('webpage/com/kingtake/project/manage/img/processActive.png');
		no-repeat left top;
}
</style>

<head>
<title>项目预算管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function ys(flag){
	
	if(flag==1){
		$.ajax({
	        url : 'tPmProjectFundsApprController.do?checkBudget',
	        data : {"projectId":"${projectId }"},
	        type : 'post',
	        dataType : 'json',
	        success : function(data) {
	            if(data.success==true){
	            	createdetailwindow("录入预算","tPmProjectFundsApprController.do?goAddOrUpdate&projectId=${projectId}&changeFlag=0&type="+flag,950,600);
	            }else{
	            	tip("该项目已经做过总预算");
	            }
	        }
	    });
	}else if(flag==3){
		$.ajax({
	        url : 'tPmProjectFundsApprController.do?getProjectId',
	        data : {"projectId":"${projectId }"},
	        type : 'post',
	        dataType : 'json',
	        success : function(data) {
	            if(data.success==true){
	            	if(data.msg==0 || data.msg==""){
	            		var r=confirm("是否申请？");
	            		if (r==true){
	            			$.ajax({
	            		        url : 'tPmProjectFundsApprController.do?editProjectLjysStatus',
	            		        data : {"projectId":"${projectId }"},
	            		        type : 'post',
	            		        dataType : 'json',
	            		        success : function(data) {
	            		        	tip(data.msg);
	            		        }
	            		    });
	            		}
	            	}
	            	if(data.msg==1){
	            		tip("正在申请！");
	            	}
	            	if(data.msg==2){
	            		tip("已提交给财务，请等待！");
	            	}
	            	if(data.msg==3){
	            		createdetailwindow("选择项目余额","tPmProjectFundsApprController.do?balanceList&projectId=${projectId}&type="+flag,1150,500);
	            	}
	            	if(data.msg==4){
	            		tip("余额不足，无法做调整预算！");
	            	}
	            	if(data.msg==5){
	            		createdetailwindow("选择预算编制包","tPmProjectFundsApprController.do?ysbzbList&projectId=${projectId}&type="+flag,950,500);
	            	}
	            }else{
	            	tip("该项目做调整预算失败");
	            }
	        }
	    });
	}else if(flag==4){
		createdetailwindow("选择项目余额","tPmProjectFundsApprController.do?balanceList&projectId=${projectId}&type="+flag,1150,500);
	}else{
		createdetailwindow("选择预算编制包","tPmProjectFundsApprController.do?ysbzbList&projectId=${projectId }&type="+flag,1150,500);
	}
}
</script>
</head>

<body>
  <div style="position: fixed; top: 10px; left: 0; height: 30px; background-color: #FFFFFF; text-align: center; width: 100%;">
    <span style="font-size: 20px; font-weight: bold;" id="titleName"> 请选择预算类别 </span>
  </div>
  <br>
  <br>
  <br>
  <div id="container" class="easyui-layout" data-options="border:false,fit:true">
    <div id="container" data-options="region:'center'">
      <div style="text-align: center; margin-top: 8px;">
        <div class="sb_active menuBtn" group="XMSB" onclick="ys(1)"><span class="menuTxt_active">总预算</span></div>
        <div class="lx menuBtn" group="XMLX" onclick="ys(2)"><span class="menuTxt">当年指标</span></div>
        <div class="zx menuBtn" group="XMZX" onclick="ys(3)"><span class="menuTxt">调整预算</span></div>
        <div class="ysjt menuBtn" group="XMYSJT" onclick="ys(4)"><span class="menuTxt">零基预算</span></div>
      </div>
      <div id="contentDiv" style="margin-top: 20px;margin-bottom:20px;overflow: auto;padding-bottom: 10px;">
      </div>
    </div>
  </div>
</body>
</html>