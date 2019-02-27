<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	function setFlag(name,id,type){
	    var flag = $("input[name='"+name+"']")[0].checked;
	        //$("input[name='"+name+"']").val("Y");
        var data = {};
        data['id']=id;
        if(flag){
           if(type=="1"){
               data['planCheckFlag']="Y";
            }else{
               data['qualityCheckFlag']="Y";
            }
       }else{
           if(type=="1"){
	           data['planCheckFlag']="N";
	        }else{
	           data['qualityCheckFlag']="N";
	        } 
       }
       $.ajax({
            type: "POST",
            url: "tPmTaskController.do?doAudit",
            data: data,
            dataType :"json",
            success: function(data){
              tip(data.msg);
              frameElement.api.opener.reloadTable();
            }
        });
	}
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		
		var type = $("#type").val();
		if(type=='1'){
		   $(".quality").attr("disabled","disabled");
		}else{
		   $(".plan").attr("disabled","disabled");
		}
    });
    
  //打开完成情况窗口
	 function openFinishWindow(id, read) {
	     var url = 'tPmTaskController.do?goFinishPage';
	     url += '&id=' + id;
	     if (read == '1') {
	         url=url+"&load=detail";
	         createdetailwindow('完成情况', url, 650, 400);
	     } else {
	         createwindow('完成情况', url, 650, 400);
	     }
	 }
</script>

<input type="hidden" id="type" value="${type}">
<table border="1" style="border-style: dotted; border-color: #A8D7E9;" cellpadding="2" cellspacing="0" id="tPmTaskNode_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
    	  <td align="left" bgcolor="#EEEEEE">计划开始时间</td>
    	  <td align="left" bgcolor="#EEEEEE">实际完成时间</td>
    	  <td align="left" bgcolor="#EEEEEE">任务内容</td>
    	  <td align="left" class="plan" bgcolor="#EEEEEE">计划处检查</td>
    	  <td align="left" class="quality" bgcolor="#EEEEEE">质量办检查</td>
    	  <td align="left" class="quality" bgcolor="#EEEEEE">是否完成</td>
    	  <td align="left" class="quality" bgcolor="#EEEEEE">查看完成情况</td>
	</tr>
	<c:if test="${fn:length(tPmTaskNodeList)  <= 0 }">
      <tr>
        <td align="center"><div style="width: 25px;" name="xh">1</div></td>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
            <input name="tPmTaskNodeList[0].updateName" type="hidden" />
            <input name="tPmTaskNodeList[0].updateDate" type="hidden" />
            <input name="tPmTaskNodeList[0].id" type="hidden" />
            <input name="tPmTaskNodeList[0].tpId" type="hidden" />
            <input name="tPmTaskNodeList[0].planCheckUserid" type="hidden" />
            <input name="tPmTaskNodeList[0].qualityCheckUserid" type="hidden" />
            <input name="tPmTaskNodeList[0].createBy" type="hidden" />
            <input name="tPmTaskNodeList[0].createName" type="hidden" />
            <input name="tPmTaskNodeList[0].createDate" type="hidden" />
            <input name="tPmTaskNodeList[0].updateBy" type="hidden" />
            <input name="tPmTaskNodeList[0].planCheckUsername" type="hidden"> 
            <input name="tPmTaskNodeList[0].planCheckTie" type="hidden" > 
            <input name="tPmTaskNodeList[0].qualityCheckUsername" type="hidden"> 
            <input name="tPmTaskNodeList[0].qualityCheckTime" type="hidden" > 
        <td align="left">
            <input name="tPmTaskNodeList[0].planTime" maxlength="0" type="text"
             style="width: 120px;" disabled="disabled"> 
            <label class="Validform_label" style="display: none;">计划完成时间</label>
        </td>
        <td align="left">
            <input name="tPmTaskNodeList[0].finishTime" maxlength="0" type="text" 
               style="width: 120px;" disabled="disabled"> 
            <label class="Validform_label" style="display: none;">实际完成时间</label>
        </td>
        <td align="left">
        <textarea name="tPmTaskNodeList[0].taskContent" maxlength="400" type="text" datatype="*2-200"
                disabled='true' style="width: 320px;" >${poVal.taskContent }</textarea>
            <label class="Validform_label" style="display: none;" >任务内容</label>
        </td>
        <td align="left" class="plan">
            <input name="tPmTaskNodeList[0].planCheckFlag" maxlength="1" type="checkbox" class="inputxt"
              style="width: 50px;" onclick="setFlag(name)" > 
            <label class="Validform_label" style="display: none;">计划处检查</label>
        </td>
        <td align="left" class="quality">
            <input name="tPmTaskNodeList[0].qualityCheckFlag" maxlength="1" type="checkbox"
              class="inputxt" style="width: 50px;" onclick="setFlag(name)" > 
            <label class="Validform_label" style="display: none;">质量办检查</label>
        </td>
        <td align="left" style="width:100px;">
                 <c:choose>
                    <c:when test="${poVal.finishFlag  eq '1' }"><font color="red">是</font></c:when>
                    <c:otherwise >否</c:otherwise>
                 </c:choose> 
            </td>
          <td align="left" style="width: 100px;">
          <a href="#" style="text-decoration: underline;color: blue; " onclick="openFinishWindow('${poVal.id}','1');">查看完成情况</a>
          </td>
      </tr>
    </c:if>
	<c:if test="${fn:length(tPmTaskNodeList)  > 0 }">
		<c:forEach items="${tPmTaskNodeList}" var="poVal" varStatus="stuts">
        <tr>
          <td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
              <input name="tPmTaskNodeList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }" />
              <input name="tPmTaskNodeList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }" />
              <input name="tPmTaskNodeList[${stuts.index }].id" type="hidden" value="${poVal.id }" />
              <input name="tPmTaskNodeList[${stuts.index }].tpId" type="hidden" value="${poVal.tpId }" />
              <input name="tPmTaskNodeList[${stuts.index }].planCheckUserid" type="hidden" value="${poVal.planCheckUserid }" />
              <input name="tPmTaskNodeList[${stuts.index }].qualityCheckUserid" type="hidden" value="${poVal.qualityCheckUserid }" />
              <input name="tPmTaskNodeList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }" />
              <input name="tPmTaskNodeList[${stuts.index }].createName" type="hidden" value="${poVal.createName }" />
              <input name="tPmTaskNodeList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }" />
              <input name="tPmTaskNodeList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }" />
              <input name="tPmTaskNodeList[${stuts.index }].planCheckUsername" type="hidden" value="${poVal.planCheckUsername }" />
              <input name="tPmTaskNodeList[${stuts.index }].planCheckTie" type="hidden" value="${poVal.planCheckTie }" />
              <input name="tPmTaskNodeList[${stuts.index }].qualityCheckUsername" type="hidden" value="${poVal.qualityCheckUsername }" />
              <input name="tPmTaskNodeList[${stuts.index }].qualityCheckTime" type="hidden" value="${poVal.qualityCheckTime }" />
          <td align="left">
              <input name="tPmTaskNodeList[${stuts.index }].planTime" maxlength="0" type="text"
                disabled="disabled" style="width: 120px;"
                value="<fmt:formatDate value='${poVal.planTime}' type="date" pattern="yyyy-MM-dd"/>"> 
              <label class="Validform_label" style="display: none;">计划完成时间</label>
          </td>
          <td align="left">
              <input name="tPmTaskNodeList[${stuts.index }].finishTime" maxlength="0" type="text"
                disabled="disabled" style="width: 120px;"
                value="<fmt:formatDate value='${poVal.finishTime}' type="date" pattern="yyyy-MM-dd"/>"> 
              <label class="Validform_label" style="display: none;">实际完成时间</label>
          </td>
          <td align="left">
              <textarea name="tPmTaskNodeList[${stuts.index }].taskContent" maxlength="400" type="text" datatype="*2-200"
                disabled='true' style="width: 320px;" >${poVal.taskContent }</textarea>
              <label class="Validform_label" style="display: none;">任务内容</label>
          </td>
          <c:if test="${poVal.planCheckFlag  eq 'Y' }">
            <td align="left" >
                <input name="tPmTaskNodeList[${stuts.index }].planCheckFlag" maxlength="1" type="checkbox"
                  class="inputxt plan" style="width: 50px;" value="${poVal.planCheckFlag }" checked="checked"
                  onclick="setFlag(name,'${poVal.id}','1')"  title="审核人：${poVal.planCheckUsername }，审核时间：<fmt:formatDate value='${poVal.planCheckTie}' pattern='yyyy-MM-dd HH:mm:ss' />"> 
                <label class="Validform_label" style="display: none;">计划处检查</label>
            </td>
          </c:if>
          <c:if test="${poVal.planCheckFlag  ne 'Y' }">
            <td align="left" >
                <input name="tPmTaskNodeList[${stuts.index }].planCheckFlag" maxlength="1" type="checkbox"
                  class="inputxt plan" style="width: 50px;" value="${poVal.planCheckFlag }" 
                  onclick="setFlag(name,'${poVal.id}','1')"> 
                <label class="Validform_label" style="display: none;">计划处检查</label>
            </td>
          </c:if>
          <c:if test="${poVal.qualityCheckFlag  eq 'Y' }">
            <td align="left" >
                <input name="tPmTaskNodeList[${stuts.index }].qualityCheckFlag" 
                	maxlength="1" type="checkbox" class="inputxt quality" 
                	style="width: 50px;" value="${poVal.qualityCheckFlag }" 
                	checked="checked" onclick="setFlag(name,'${poVal.id}','2')"
                  	title="审核人：${poVal.qualityCheckUsername }，审核时间：<fmt:formatDate value='${poVal.qualityCheckTime}' pattern='yyyy-MM-dd HH:mm:ss' />"> 
                <label class="Validform_label" style="display: none;">质量办检查</label>
            </td>
          </c:if>
          <c:if test="${poVal.qualityCheckFlag  ne  'Y' }">
            <td align="left" >
                <input name="tPmTaskNodeList[${stuts.index }].qualityCheckFlag" 
                	maxlength="1" type="checkbox" class="inputxt quality" 
                	style="width: 50px;" value="${poVal.qualityCheckFlag }" 
                	onclick="setFlag(name,'${poVal.id}','2')"> 
                <label class="Validform_label" style="display: none;">质量办检查</label>
            </td>
          </c:if>
          <td align="left" style="width:100px;">
                 <c:choose>
                    <c:when test="${poVal.finishFlag  eq '1' }"><font color="red">是</font></c:when>
                    <c:otherwise >否</c:otherwise>
                 </c:choose> 
            </td>
          <td align="left" style="width: 100px;">
          <c:if test="${poVal.finishFlag  eq '1' }">
            <a href="#" style="text-decoration: underline;color: blue;cursor: pointer; " onclick="openFinishWindow('${poVal.id}','1');">查看完成情况</a>
          </c:if>
          </td>
        </tr>
      </c:forEach>
	</c:if>	
</table>
