<%@ page language="java" import="java.util.*,com.kingtake.common.constant.SrmipConstants" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<title>项目管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<style>
#projectMenuDiv ul li {
	list-style: none;
	float: left;
	display: block;
}

#portalDiv ul li {
	list-style: none;
}

.feature-presenter-text-heading {
	text-align: left;
}

.feature-presenter-text-description {
	text-align: left;
}

.feature-presenter-text-description a {
	line-height: 200%;
	font-size: 12px;
}

.feature-presenter-text-container {
	width: 150px;
}

.feature-presenter-circle-container {
	background: none;
}

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
	line-height: 180%;
	color: #ffffff;
	font-family: "Microsoft YaHei", "微软雅黑",;
}

body,td,th {
	font-size: 14px;
	line-height: 180%;
	font-family: "Microsoft YaHei", "微软雅黑",;
	text-align:center;
}
#auditTab td:hover{
	background-color: #FF594F;
	cursor: pointer;
	color:#fff;
	font-weight: bolder;
}

#auditTab td font{
	float:right;
	margin-right:20px;
	color:#2c7adc;
	font-weight: bold;font-size:30px;
}

</style>
<script>
    $(function() {
        $.ajax({
            url : 'projectMgrController.do?getPresenterMenu',
            dataType : "json",
            type : 'POST',
            success : function(data) {
                var countStr = data.countStr;
                if (countStr) {//审核数目添加到table中
                    $(countStr).appendTo($("#auditTab"));
                 }
            }
        });
    });

</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td valign="top" bgcolor="#FFFFFF" style="height: 100px;"><table width="100%" border="0"
        cellpadding="0" cellspacing="0" background="images/project/xm_list_bg.jpg">
       <!--  <tr>
          <td height="38" class="title4" style="padding-left: 10px; padding-bottom: 6px">项目动态：</td>
        </tr> -->
      </table>
      <table id="auditTab" width="100%" border="0" cellspacing="5" cellpadding="5">
      </table>
  </td>
  </tr>
</table>
