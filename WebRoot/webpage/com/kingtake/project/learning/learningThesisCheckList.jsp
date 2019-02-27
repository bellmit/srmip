<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">

</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBLearningThesisList" checkbox="true" fitColumns="false" title="学术论文信息表" actionUrl="tBLearningThesisController.do?checkList&businessCode=learning_thesis" pagination="false" idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目id"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="中文题名"  field="titleCn" frozenColumn="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="第一作者"  field="authorFirstName" frozenColumn="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="提交人"  field="createName" frozenColumn="true" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="操作" frozenColumn="true" field="opt" width="150"></t:dgCol>
   <t:dgCol title="第二作者"  field="authorSecond"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="第三作者"  field="authorThird"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="期刊名称"  field="magazineName" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="期刊分类"  field="periodicalType" codeDict="1,QKFL"   queryMode="single" width="80"></t:dgCol>
   <t:dgCol title="会议论文分类"  field="articleType" codeDict="1,HYLWFL" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="卷"  field="volumeNum"    queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="期"  field="phaseNum"    queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="文章页码"  field="pageNum" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="资助基金"  field="sustentationFund" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="summary"   hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收录情况索引名称"  field="indexName" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="收录情况收录号"  field="collectionNum" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="英文题名"  field="titleEn"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="其他作者"  field="authorOther" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="第一作者所属院系"  field="authorFirstDepart.departname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="具体单位"  field="concreteDepart"    queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="关键词"  field="keyword"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级"  field="secretCode"  codeDict="0,WXBMDJ"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="发表年度"  field="publishYear"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="月份"  field="publishMonth"    queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="任务id"  field="taskId"  hidden="true" width="40"></t:dgCol>
   <t:dgCol title="任务名称"  field="taskName"  hidden="true"  width="40"></t:dgCol>
   <t:dgCol title="分配人"  field="assigneeName"  hidden="true"  queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"  dictionary="bpm_status" hidden="true"  queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
   <t:dgConfOpt exp="assigneeName#empty#true" url="activitiController.do?claim&taskId={taskId}" message="确定签收?" title="签收"></t:dgConfOpt>
   <t:dgFunOpt exp="assigneeName#empty#false" funname="openhandleMix(taskId,taskName)" title="办理"></t:dgFunOpt>
   <t:dgFunOpt exp="assigneeName#empty#false" funname="selectEntruster(taskId,taskName)" title="委托"></t:dgFunOpt>
  </t:datagrid>
  </div>
  <input type="hidden" id="id">
  <input type="hidden" id="businessName">
 </div>
 <script src = "webpage/com/kingtake/project/learning/tBLearningThesisList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBLearningThesisListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLearningThesisListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLearningThesisListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLearningThesisListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBLearningThesisController.do?upload', "tBLearningThesisList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBLearningThesisController.do?exportXls","tBLearningThesisList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBLearningThesisController.do?exportXlsByT","tBLearningThesisList");
}

function getId(){
    var id = $("#id").val();
    return id;
}

function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = '100%';
	var height = '100%';
	var role = $("#role").val();
	var url = "tBLearningThesisController.do?goAddUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

 </script>