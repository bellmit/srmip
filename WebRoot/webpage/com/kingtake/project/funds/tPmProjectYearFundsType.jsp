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
<title>项目年度预算管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function ys(flag){
	var postdata = {};
	postdata.tpId = "${projectId }";
	postdata.fundsType = flag;
	if(flag == '7'){
		postdata.totalFunds = 0;
	}else{
		postdata.totalFunds = "${totalFunds}";
	}
	if(flag == '5'){//零基预算
		postdata.iBalanceIds = "${iBalanceIds}";
	}
	if(flag == '6'){//开支计划
		postdata.iPlanIncomeIds = "${iPlanIncomeIds}";
		postdata.incomeIds = "${incomeIds}";
	}
	$.ajax({
        url : 'tPmProjectFundsApprController.do?getIsCanApplyYearFunds',
        data : {"projectId":"${projectId}"},
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.success==true){
           		var r=confirm("是否申请？");
           		if (r==true){
           			postdata.tzApprId = "${tzApprId}";
           			$.ajax({
           		        url : 'tPmProjectFundsApprController.do?addYearFundsDefault',
           		        data : postdata,
           		        type : 'post',
           		        dataType : 'json',
           		        success : function(data) {
           		        	tip(data.msg);
           		        	//frameElement.api.opener.tPmProjectYearFundsApprListsearch();
           		        	frameElement.api.config.parent.iframe.contentWindow.tPmProjectYearFundsApprListsearch();
           		        	frameElement.api.close();
           		        }
           		    });
           		}
            }else{
            	tip(data.msg);
            }
        }
    });
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
       	<c:if test="${fundsType == '5'}">
           <div class="ysjt menuBtn" group="XMYSJT" onclick="ys(5)"><span class="menuTxt">零基预算</span></div>
	    </c:if>
	    <c:if test="${fundsType == '6'}">
           <div class="lx menuBtn" group="XMLX" onclick="ys(6)"><span class="menuTxt">开支计划</span></div>
	    </c:if>
	    <c:if test="${((fundsType == '5' || fundsType == '6') && iIsCanTz == '1') || fundsType == '7'}">
	       <div class="zx menuBtn" group="XMZX" onclick="ys(7)"><span class="menuTxt">调整预算</span></div>
	    </c:if>
	    <c:if test="${fundsType == '-1'}">
	       "${msg}"
	    </c:if>
      </div>
      <div id="contentDiv" style="margin-top: 20px;margin-bottom:20px;overflow: auto;padding-bottom: 10px;">
      </div>
    </div>
  </div>
</body>
</html>