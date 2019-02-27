<%@ page language="java" import="java.util.*,com.kingtake.common.constant.SrmipConstants" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<title>机关项目管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
body {
	/*background-image: url(images/project/xm_bg.jpg);*/
	background-color:#f4f4f4;
}

.title1 {
	font-size: 24px;
	line-height: 100%;
	color: #000000;
	font-family: "Microsoft YaHei", 微软雅黑,;
}

.title2 {
	font-size: 14px;
	line-height: 100%;
	color: #cccccc;
	font-family: "Microsoft YaHei", 微软雅黑,;
}

.title3 {
	font-size: 30px;
	line-height: 180%;
	color: #009933;
	font-family: "Microsoft YaHei", "微软雅黑",;
	font-weight: bold;
}

.title4 {
	font-size: 16px;
	/* line-height: 180%; */
	color: #ffffff;
	font-family: "Microsoft YaHei", "微软雅黑",;
}

body,td,th {
	font-size: 14px;
	line-height: 180%;
	font-family: "Microsoft YaHei", "微软雅黑",;
}

.li1 {
	list-style-type: none;
    border: solid 3px #fff;
    position: relative;
    background-color: #F4F4F4;
    padding: 10px 5px;
    margin-top: 10px;
    border-left: solid 5px #16c288;
    border-bottom: solid 0.1px #16c288;
}

.li1 .count {
	font-size: 30px;
	position: absolute;
	right: 10px;
	color: #16c288;
}

.li2{
	list-style-type: none;
    border: solid 3px #fff;
    position: relative;
    background-color: #F4F4F4;
     padding: 10px 5px;
    margin-top: 10px;
	border-left: solid 5px #2c7adc;
	border-bottom: solid 0.1px #2c7adc;
}
.li2 .count {
	font-size: 30px;
	position: absolute;
	right: 10px;
	color: #2c7adc;
}

li:hover {
	background-color: #FF594F;
	cursor: pointer;
	color:#fff;
	font-weight: bolder;
}

#mainMenu td.subMenu {
	vertical-align: top;
}

.liFocus {
	background-color: #FF594F;
	cursor: pointer;
}

</style>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
$(function(){
	getMenu();
	setInterval("getMenu()",60*1000);  
});

function getMenu(){
	$.ajax({
        url:'projectMgrController.do?getPresenterMenu',
        type:'POST',
        cache:false,
        dataType:'json',
        success:function(data){
            var subs = $(".subMenu");
            for(var i=0;i<subs.length;i++){
                var code = $(subs[i]).attr("code");
                var htmlStr = data[code];
                $(subs[i]).html(htmlStr);
                
                var css = (code=="projectDepartment_lx"||code=="projectDepartment_ysjt") ? "li1" : "li2"
                var lis = $(".subMenu[code='"+code+"']").find("li");
                $(lis).addClass(css);		
            }
            $("#mainMenu").find("li").mouseover(function(){
                $(this).addClass("liFocus");
            });
            $("#mainMenu").find("li").mouseout(function(){
                $(this).removeClass("liFocus");
            });
        }
    });
}
</script>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<body>
  <div id="container" class="easyui-layout" fit="true" >
    <div id="mainMenu" data-options="region:'center'" style="height: 350px;">
      <table width="100%" border="0" cellspacing="5" cellpadding="5">
        <tr>
          <td style="width: 20%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#2C7ADC" style="cursor: pointer;" onclick="allProject()">
              <tr>
                <td align="center" class="title4">项目申报</td>
              </tr>
            </table>
          </td>
          <td style="width: 20%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#16C288" style="cursor: pointer;" onclick="finishedProject()">
              <tr>
                <td align="center" class="title4">项目立项</td>
              </tr>
            </table>
          </td>
          <td style="width: 20%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#2C7ADC" style="cursor: pointer;" onclick="allProject()">
              <tr>
                <td align="center" class="title4">项目执行</td>
              </tr>
            </table>
          </td>
          <td style="width: 20%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#16C288" style="cursor: pointer;" onclick="finishedProject()">
              <tr>
                <td align="center" class="title4">验收及结题</td>
              </tr>
            </table>
          </td>
          <td style="width: 20%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#2C7ADC" style="cursor: pointer;" onclick="finishedProject()">
              <tr>
                <td align="center" class="title4">成果管理</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="subMenu" code="projectDepartment_sb">
            <!-- <li onclick="addTab('申报书审核', 'tPmDeclareController.do?goTaskListTab&businessCode=declare', 'default')">申报书评审</li>
            <li onclick="addTab('申报需求', 'tPmDeclareController.do?goTaskListTab&businessCode=reportReq', 'default')">申报需求</li>
            <li onclick="addTab('进账合同审批', 'tPmIncomeContractApprController.do?goReceiveTab', 'default')">进账合同审批</li> -->
          </td>
          <td class="subMenu" code="projectDepartment_lx">
            <!-- <li>下发通知</li>
            <li>进账合同扫描件</li> -->
          </td>
          <td class="subMenu" code="projectDepartment_zx">
            <!-- <li onclick="addTab('开题报告', 'tPmDeclareController.do?goTaskListTab&businessCode=openSubject', 'default')">开题报告<span class="count">3</span></li>
            <li onclick="addTab('任务书/任务节点审核', 'tPmTaskController.do?goReceiveTab', 'default')">任务书/任务节点审批</li>
            <li onclick="addTab('来款申请审批', 'tPmIncomeApplyController.do?incomeApplyTab', 'default')">来款申请审批</li>
            <li onclick="addTab('预算审批', 'tPmProjectFundsApprController.do?goReceiveTab', 'default')">预算审批</li>
            <li onclick="addTab('项目节点验收审批', 'tPmContractNodeCheckController.do?tPmContractNodeCheckReceiveTab', 'default')">项目节点验收审批</li>
            <li onclick="addTab('出账合同审批', 'tPmOutcomeContractApprController.do?goReceiveTab', 'default')">出账合同审批</li>
            <li onclick="addTab('出账合同节点考核', 'tPmContractNodeCheckController.do?outcomeContractNodeCheckReceiveTab', 'default')">出账合同节点考核</li>
            <li onclick="addTab('支付申请审批', 'tPmContractNodeCheckController.do?payApplyCheckReceiveTab', 'default')">支付申请审批</li>
            <li>项目信息变更审批</li> -->
          </td>
          <td class="subMenu" code="projectDepartment_ysjt">
            <!-- <li onclick="addTab('结题申请审批', 'tPmFinishApplyController.do?goReceiveTab', 'default')">结题审批</li> -->
          </td>
          <td class="subMenu" code="projectDepartment_cgjd">
            <!-- <li onclick="addTab('鉴定计划', 'tBAppraisalProjectController.do?appraisalProjectForDepartment', 'default')">鉴定计划</li>
            <li onclick="addTab('鉴定申请审批审查', 'tBAppraisaApprovalController.do?tBAppraisaApprovalReceiveTab', 'default')">鉴定申请审批审查</li>
            <li onclick="addTab('鉴定申请审查', 'tBAppraisalApplyController.do?tBAppraisalApplyListForDepartment', 'default')">鉴定申请审查</li>
            <li onclick="addTab('鉴定会审批', 'tBAppraisalMeetingController.do?tBAppraisalMeeting', 'default')">鉴定会审批</li>
            <li onclick="addTab('鉴定材料审查', 'tBAppraisalReportMaterialController.do?tBAppraisalReportMaterial', 'default')">鉴定材料审查</li>
            <li onclick="addTab('成果奖励审批', 'tBResultRewardController.do?goReceiveTab', 'default')">成果奖励审批</li>
            <li onclick="addTab('成果推广', 'tBResultSpreadController.do?tBResultSpreadListForDepartment', 'default')">成果推广</li> -->
          </td>
        </tr>
      </table>
    </div>
    
  </div>
  <script type="text/javascript">
  function cancelPass(id){
  	$.messager.confirm('确认','您确认将该项目取消锁定吗？',function(r){    
  	    if (r){    
  	    	changeState(id, '0'); 
  	    }    
  	});
  }

  function changeState(id, state){
  	$.ajax({
      	url:'tPmProjectController.do?applyPass',
      	type:'post',
      	data:{id:id, state:state},
      	success:function(result){
      		result = $.parseJSON(result);
      		$("#tPmProjectList").datagrid('reload');
      		tip(result.msg);
      	}
      });
  }
  
  
  
  </script>
</body>

