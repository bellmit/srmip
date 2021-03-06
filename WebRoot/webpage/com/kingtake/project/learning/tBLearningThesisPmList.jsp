<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function bpmStatusFormatter(value, row, index) {
        if (value == '1') {
            return '待提交';
        } else if (value == '2') {
            return '流转中(' + row.taskName + ')';
        } else if (value == '3') {
            return '已完成';
        } else if (value == '4') {
            return '作废';
        } else if (value == '5') {
            return '已驳回(' + row.taskName + ')';
        }
    }
</script>
<%--<div class="easyui-layout" fit="false" style="height:400px;">--%>
  <%--<div region="center" style="padding: 1px;">--%>
<div style="width: 100%;height: 300px;">
    <input id="projectId" value="${projectId }" type="hidden"/>
    <input id="role" type="hidden" value="${role}">
    <t:datagrid name="tBLearningThesisPmList" checkbox="true" fitColumns="false" title="学术论文信息表" actionUrl="tBLearningThesisController.do?datagrid&project.id=${projectId}" idField="id"
      fit="false" onDblClick="dblDetail" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="中文题名" field="titleCn" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="第一作者" field="authorFirstName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="第二作者" field="authorSecond" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="第三作者" field="authorThird" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="期刊名称" field="magazineName" isLike="true" query="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="期刊分类" field="periodicalType" codeDict="1,QKFL" query="true" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="会议论文分类" field="articleType" codeDict="1,HYLWFL" query="true" queryMode="single" width="90"></t:dgCol>
      <t:dgCol title="卷" field="volumeNum" queryMode="group" width="40"></t:dgCol>
      <t:dgCol title="期" field="phaseNum" queryMode="group" width="40"></t:dgCol>
      <t:dgCol title="文章页码" field="pageNum" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="资助基金" field="sustentationFund" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="摘要" field="summary" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="收录情况索引名称" field="indexName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="收录情况收录号" field="collectionNum" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="英文题名" field="titleEn" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="其他作者" field="authorOther" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="第一作者所属院系" field="authorFirstDepart.departname" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="具体单位" field="concreteDepart" queryMode="group" width="180"></t:dgCol>
      <t:dgCol title="关键词" field="keyword" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="密级" field="secretCode" codeDict="0,WXBMDJ" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="所属项目" field="project_projectName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="发表时间" field="publishTime" formatter="yyyy-MM-dd" query="true" queryMode="single" width="70"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" queryMode="group" width="120" extendParams="formatter:bpmStatusFormatter,"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="任务名称" field="taskName" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="分配人" field="assigneeName" hidden="true" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tBLearningThesisController.do?goAddUpdate" funname="detail" width="900" height="600"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  <%--</div>--%>
  <input type="hidden" id="id">
  <input type="hidden" id="businessName">
</div>

<script src="webpage/com/kingtake/project/learning/tBLearningThesisList.js"></script>
<script type="text/javascript">
$(document).ready(
        function() {
            //修改查询条件样式
            $("#tBLearningThesisListtb").find("input[name='titleCn']").attr("style","height:23px;width:200px;");
            $("#tBLearningThesisListtb").find("select[name='periodicalType']").attr("style","height:23px;width:150px;");
            //给时间控件加上样式
            $("#tBLearningThesisListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                    "style", "height:20px;width:90px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tBLearningThesisListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                    "height:20px;width:90px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tBLearningThesisListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                    "style", "height:20px;width:90px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tBLearningThesisListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                    "height:20px;width:90px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tBLearningThesisListtb").find("input[name='publishTime_begin']").attr("class", "Wdate").attr("style",
            "height:23px;width:100px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tBLearningThesisListtb").find("input[name='publishTime_end']").attr("class", "Wdate").attr("style",
            "height:23px;width:100px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });

            $("#tBLearningThesisPmList").datagrid({
                height:300
            });
            
        });
    //导出
    function ExportXls() {
        JeecgExcelExport("tBLearningThesisController.do?exportXls", "tBLearningThesisList");
    }

    function dblDetail(rowIndex, rowDate) {
        var title = "查看";
        var width = '900px';
        var height = '600px';
        var role = $("#role").val();
        var url = "tBLearningThesisController.do?goAddUpdate&load=detail&id=" + rowDate.id;
        createdetailwindow(title, url, width, height);
    }
    
</script>