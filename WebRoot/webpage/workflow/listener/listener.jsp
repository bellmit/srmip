<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>流程监听</title>
  <t:base type="jquery,tools"></t:base>
  <script type="text/javascript">
	$(function() {
		//监听类型change事件
		$("#typeid").change(function() {
			var checkValue = $("#typeid").val();
			if (checkValue == 1) {
				$("#execution").show();
				$("#task").hide();
			} else {
				$("#execution").hide();
				$("#task").show();
			}
		});
		//单选框单击事件
		$(".lt").click(function() {
			var lt = $('input[name="listenertype"]:checked').val();
			if (lt == 'javaClass') {
				$("#lv").text('类路径:');
			} else {
				$("#lv").text('表达式:');
			}

		});

	});
</script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveListener">
   <input name="id" type="hidden" value="${tpListerer.id }">
   <input name="listenerstate" type="hidden" value="${tpListerer.listenerstate}">
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
      	监听名称:
     </label>
     <input name="listenername" class="inputxt" value="${tpListerer.listenername}">
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
     <label class="Validform_label">
      	监听类型:
     </label>
     <select id="typeid" name="typeid">
      <option value="1" <c:if test="${tpListerer.typeid==1}">selected="selected"</c:if>>
      	 执行监听器
      </option>
      <option value="2" <c:if test="${tpListerer.typeid==2}">selected="selected"</c:if>>
      	 任务监听器
      </option>
     </select>
    </div>
    <div class="form">
     <label class="Validform_label">
      Event属性:
     </label>
     <select id="task" name="taskeven" <c:if test="${tpListerer.typeid!=2}">style="display: none"</c:if>>
      <option value="create" <c:if test="${tpListerer.listenereven=='create'}">selected="selected"</c:if>>
       Create
      </option>
      <option value="assignment" <c:if test="${tpListerer.listenereven=='assignment'}">selected="selected"</c:if>>
       Assignment
      </option>
      <option value="complete" <c:if test="${tpListerer.listenereven=='complete'}">selected="selected"</c:if>>
       Complete
      </option>
     </select>
     <select id="execution" name="executioneven" <c:if test="${tpListerer.typeid!=1 and !empty tpListerer}">style="display: none"</c:if>>
      <option value="start" <c:if test="${tpListerer.listenereven=='start'}">selected="selected"</c:if>>
       start
      </option>
      <option value="end" <c:if test="${tpListerer.listenereven=='end'}">selected="selected"</c:if>>
       end
      </option>
     </select>
    </div>
    <div class="form">
     <label class="Validform_label">
      	值类型:
     </label>
     Java类
     <input name="listenertype" class="lt" id="listenertype1" type="radio" value="javaClass" <c:if test="${tpListerer.listenertype=='javaClass'}">checked="checked"</c:if> checked="checked" datatype="need1" nullmsg="请选择类型" />
     &nbsp;&nbsp;&nbsp;&nbsp;表达式
     <input name="listenertype" class="lt" id="listenertype2" type="radio" value="expression" <c:if test="${tpListerer.listenertype=='expression'}">checked="checked"</c:if> />
     <span class="Validform_checktip"></span>
    </div>
    <div class="form">
     <label class="Validform_label" id="lv">
      <c:if test="${tpListerer.listenertype!='expression'}">类路径:</c:if>
      <c:if test="${tpListerer.listenertype!='javaClass' and !empty tpListerer}">表达式:</c:if>
     </label>
     <input name="listenervalue" class="inputxt" value="${tpListerer.listenervalue}" style="width:360px">
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
