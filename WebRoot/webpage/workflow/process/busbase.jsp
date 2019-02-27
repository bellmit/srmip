<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>业务信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" action="processController.do?saveBus">
   <input name="id" type="hidden" value="${busbase.id}">
   <input name="TPProcess.id" type="hidden" value="${processid}">
   <fieldset class="step">
    <div class="form">
      <label class="Validform_label">
      	业务名称:
     </label>
     <input name="busname" value="${busbase.busname }" datatype="s2-50" class="inputxt">
     <span class="Validform_checktip">节点名称范围在2~50位字符,且不为空</span>
    </div>
    
     <div class="form">
	      <label class="Validform_label">
	      	业务表单类型:
	      </label>
	      <t:dictSelect field="formType" typeGroupCode="bpm_form_type" hasLabel="false" defaultVal="online"></t:dictSelect>
    </div>
    
    <div class="form" id="tstable_div" style="display: none">
     <label class="Validform_label">
      	业务实体:
     </label>
     <select name="TSTable.id">
     		 <option value="">请选择</option>
	       <c:forEach items="${tableList }" var="table">
	     	 <option value="${table.id}" <c:if test="${table.id eq busbase.TSTable.id}">selected="selected"</c:if>>
	     	 ${table.tableTitle}
	      	</option>
	      </c:forEach>
     </select>
    </div>
    
    <div class="form" id="onlineId_div">
	      <label class="Validform_label">
	      	Online表名:
	      </label>
	      <input name="onlineId" value="${busbase.onlineId }" validType="t_s_busconfig,online_id,id" datatype="s2-50" class="inputxt" ignore="ignore">
	      <span class="Validform_checktip">节点名称范围在2~50位字符</span>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>


<script type="text/javascript">
<!--
$("select[name='formType']").change(function(){
	if("online"==this.value){
		$("#tstable_div").hide();
		$("#onlineId_div").show();
	}else{
		$("#tstable_div").show();
		$("#onlineId_div").hide();
	}
});
//-->
</script>