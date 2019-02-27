<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="webpage/com/kingtake/office/receivebill/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="webpage/com/kingtake/office/receivebill/css/sb-admin.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<!--     <link href="webpage/com/kingtake/office/receivebill/css/plugins/morris.css" rel="stylesheet"> -->

<!-- Custom Fonts -->
<link href="webpage/com/kingtake/office/receivebill/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
    <script type="text/javascript" src="webpage/common/html5shiv.js"></script>
    <script type="text/javascript" src="webpage/common/respond.min.js"></script>
    <t:base type="jquery"></t:base>
<style type="text/css">
body {
	background-color: #ffffff
}
</style>
</head>
<body>
 <input id="registerType" type="hidden" value="${registerType}">
  <div id="page-wrapper">
    <div class="container-fluid">
      <div id="globalMd" class="row">
        <div id="md1" class="col-lg-3 col-md-3" onclick="getToDoList()" style="cursor: pointer;">
          <div class="panel panel-yellow">
            <div class="panel-heading">
              <div class="row">
                <div class="col-xs-3">
                  <i class="fa fa-shopping-cart fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">
                  <div class="huge">
                  <c:if test="${registerType eq 0}">
                  ${countMap.untreatedReceiveBill}
                  </c:if>
                  <c:if test="${registerType eq 1}">
                  ${countMap.untreatedSendBill}
                  </c:if>
                  </div>
                  <div>待处理</div>
                </div>
              </div>
            </div>
            <a href="javascript:getToDoList();">
              <div class="panel-footer">
                <span class="pull-left">点击查看</span>
                <span class="pull-right">
                  <i class="fa fa-arrow-circle-right"></i>
                </span>
                <div class="clearfix"></div>
              </div>
            </a>
          </div>
        </div>
        <div id="md2" class="col-lg-3 col-md-3" style="cursor: pointer;" onclick="getFinishList()">
          <div class="panel panel-green">
            <div class="panel-heading">
              <div class="row">
                <div class="col-xs-3">
                  <i class="fa fa-tasks fa-5x"></i>
                </div>
                <div class="col-xs-9 text-right">
                  <div class="huge">
                  <c:if test="${registerType eq 0}">
                  ${countMap.treatedReceiveBill}
                  </c:if>
                  <c:if test="${registerType eq 1}">
                  ${countMap.treatedSendBill}
                  </c:if>
                  </div>
                  <div>已处理</div>
                </div>
              </div>
            </div>
            <a href="javascript:getFinishList();">
              <div class="panel-footer">
                <span class="pull-left">点击查看</span>
                <span class="pull-right">
                  <i class="fa fa-arrow-circle-right"></i>
                </span>
                <div class="clearfix"></div>
              </div>
            </a>
          </div>
        </div>
      </div>

      <div class="row" style="height: 800">
        <div class="col-lg-12">
          <div style="background-color: #FFFFFF;">
            <Iframe id="dataIframe" src="#" width="100%" height="400" scrolling="auto" frameborder="0" name="main"></iframe>
          </div>
        </div>
      </div>
    </div>

  </div>
  <script type="text/javascript">
            //获取待办列表
            function getToDoList() {
                $("#dataIframe")[0].contentWindow.seletTab(0); 
            }
            
            //获取已办列表
            function getFinishList() {
                $("#dataIframe")[0].contentWindow.seletTab(1); 
            }
            
           
        </script>
  <script src="webpage/com/kingtake/office/receivebill/js/jquery.js"></script>
  <script src="webpage/com/kingtake/office/receivebill/js/bootstrap.min.js"></script>
  <t:authFilter></t:authFilter>

  <script type="text/javascript">
            $(function() {
                var registerType = $("#registerType").val();
                var url;
                if(registerType=='0'){
                  url = 'scheduleOfficialController.do?tOReceiveBillListToMe';
                }else{
                  url = 'scheduleOfficialController.do?tOSendBillListToMe';
                }
                $('#dataIframe').attr('src',url);
            });
        </script>
</body>
</html>