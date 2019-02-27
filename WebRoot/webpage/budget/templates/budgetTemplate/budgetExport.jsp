<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">

</script>
<html>

<body>
  <input type="button" value="导出" onclick="exportWord(1)"/>
  <input type="button" value="计划经费导出" onclick="exportWord(2)"/>
  <input type="button" value="经费垫支申请书" onclick="exportWord(3)"/>
  <input type="button" value="校内协助" onclick="exportWord(4)"/>

  <h2>预算导出</h2>
  <input type="button" value="项目总预算审批表导出" onclick="exportWord(5)"/>
  <input type="button" value="项目年度预算明细表导出" onclick="exportWord(6)"/>


<div id="tPmIncomeApply"></div>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });    
    
    //导出校内协作经费申请书WORD
    function exportWord(id) {

        var controllerMap = {
            1:'createWord',
            2:'createWordJhjfptzs',
            3:'createWordJfdzsqs',
            4:'createWordXnxzjfsqs',
            5:'createWordXmzysspb',
            6:'createWordXmndysmxb'
        };
    	if(id == ""){
    		tip('请选择数据');
    	}else{
    		$.ajax({
                url : 'budgetExportController.do?'+controllerMap[id]+'&id=' + id,
                type : 'post',
                data:{a:'测试',b:'fff'},
                dataType : 'json',
                success : function(data) {
                	downloadWord(data.attributes.FileName);
                }
            });
    	}    	
    }
    //下载WORD
    function downloadWord(FileName) {

        window.location.href="http://"+window.location.host+"/srmip/tPmIncomeApplyController.do?downloadWord&FileName="+FileName;
       /* $.ajax({
            url : 'tPmIncomeApplyController.do?downloadWord&FileName=' + FileName,
            type : 'post',
            dataType : 'json',
            success : function(data) {
                //downloadWord(data.attributes.FileName);
            }
        });*/
    }
</script>