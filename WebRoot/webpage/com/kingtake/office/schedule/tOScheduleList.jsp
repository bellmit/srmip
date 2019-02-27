<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/fullcalendar/css/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="plug-in/fullcalendar/css/fullcalendar.print.css" media='print'>

<style type="text/css">
  #calendar{width:1000px; margin:10px auto;}
  .hand{cursor: pointer;}
  .buttonCss{width:50px; height:30px;}
</style>
<script src='plug-in/fullcalendar/js/jquery-ui-1.8.23.custom.min.js'></script>
<script src='plug-in/fullcalendar/js/fullcalendar.min.js'></script>
<script src = "webpage/com/kingtake/office/schedule/tOScheduleList.js?${tm}"></script>
<script type="text/javascript">
var scheduleId = "";
var finishFlag = "";

$(function() {
    loadSchedule();
    var right = $("td.fc-header-right");
    right.append('<span style="float:right;" class="fc-button fc-state-default fc-corner-left" onclick=openHandoverWin()><span class="fc-button-inner"><span class="fc-button-content">生成交班材料</span><span class="fc-button-effect"><span></span></span></span></span>');
	
});
</script>


<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <div id='calendar'></div>
  </div>
</div>
