<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>


<script type="text/javascript">
	
	$('#addTOTravelLeavedetailBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTOTravelLeavedetailBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTOTravelLeavedetailBtn').bind('click', function(){   
		 //debugger;
 		 var tr =  $("#add_projectPlanDetail_table_template tr").clone();
		 var text = tr.html();
		 num = $("#add_projectPlanDetail_table").find('>tr').size();
		 text = text.replace(/#index#/g,num);
		 tr.html(text);
//		 console.log(tr.html());
//		 console.log("--------------------------------------------");
//		 console.log(text);

	 	 $("#add_projectPlanDetail_table").append(tr);
// 	 	 resetTrNum('projectPlanDetail_table');
	 	 return false;
    });  
	$('#delTOTravelLeavedetailBtn').bind('click', function(){   
      	$("#add_tOTravelLeavedetail_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tOTravelLeavedetail_table'); 
        return false;
    }); 
    $(document).ready(function(){
        //console.info("${poVal}");
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#projectPlanDetail_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});

		var num = $("#add_projectPlanDetail_table").find('>tr').size();

    });
</script>

<!-- <script src="projectPlanDetailList.js"></script> -->
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTOTravelLeavedetailBtn" href="#">添加</a> <a id="delTOTravelLeavedetailBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="0" cellspacing="0" id="projectPlanDetail_table" >
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						分配金额<font color="red">*</font>
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						项目名称<font color="red">*</font>
				  </td>
	</tr>
	<input id="deptId_hidden_" type="hidden">
	<tbody id="add_projectPlanDetail_table">	
	<c:if test="${fn:length(tPmIncomePlanEntityList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 40px;" name="xh">1</div></td>
				<td align="center"><input style="width:40px;"  type="checkbox" name="ck"/></td>
					<input name="tPmIncomePlanEntityList[0].id" type="hidden"/>
				    <input name="tPmIncomePlanEntityList[0].projectPlanId" type="hidden"/>				  
				  <td align="left">
<!-- 					  	<input name="tPmIncomePlanEntityList[0].amount" maxlength="150"  -->
<!-- 					  		type="text" class="inputxt"  style="width:160px;" -->
<!-- 					               > -->
					  <input name="tPmIncomePlanEntityList[0].amount" type="text" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1">
					  <label class="Validform_label" style="display: none;">分配金额</label>
					</td>
            <td align="left">
            <input id="projectId0" name="tPmIncomePlanEntityList[0].project.id" type="hidden"> 
            <input id="projectName0" name="tPmIncomePlanEntityList[0].project.projectName" type="text" style="width: 160px" class="inputxt" readonly="readonly"> 
              <t:choose url="tPmProjectController.do?projectSelect" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="projectId0,projectName0"
              isclear="true" ></t:choose>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
<!-- 				  <td align="left"> -->
				  
<!-- 					  <input id ="projectName0" name="tPmIncomePlanEntityList[0].project.projectName" maxlength="100" -->
<!-- 						   type="text" class="inputxt"  style="width:160px;"> -->
<!-- 					  <input id ="projectId0" name="tPmIncomePlanEntityList[0].project.id" maxlength="32" -->
<!-- 							 type="hidden" class="inputxt"  style="width:160px;"> -->
<%-- 				      <t:chooseProject inputId="projectId0"  inputName="projectName0" icon="icon-search" title="关联项目" isclear="true" mode="single" all="true"></t:chooseProject> --%>
<!-- 					  <label class="Validform_label" style="display: none;">项目名称</label> -->
<!-- 				  </td> -->
   			</tr>
	</c:if>
	<c:if test="${fn:length(tOTravelLeavedetailList)  > 0 }">
		<c:forEach items="${tOTravelLeavedetailList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tOTravelLeavedetailList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
			        <input name="tOTravelLeavedetailList[${stuts.index }].toId" type="hidden" value="${poVal.toId }"/>
				   <td align="left">
							<input id="beginDate"  name="tOTravelLeavedetailList[${stuts.index }].startTime" maxlength="0"
					  		type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endDate\')}'})"  style="width:160px;"

					                value="<fmt:formatDate value='${poVal.startTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					  <label class="Validform_label" style="display: none;">起始时间</label>
				   </td>
				   <td align="left">
							<input id="endDate" name="tOTravelLeavedetailList[${stuts.index }].endTime" maxlength="0"
					  		type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'beginDate\')}'})"  style="width:160px;"

					                value="<fmt:formatDate value='${poVal.endTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					  <label class="Validform_label" style="display: none;">截止时间</label>
				   </td>
				   <td align="left">
					  	<input name="tOTravelLeavedetailList[${stuts.index }].address" maxlength="150"
					  		type="text" class="inputxt"  style="width:160px;"

					                value="${poVal.address }">
					  <label class="Validform_label" style="display: none;">外出地点</label>
				   </td>
				   <td align="left">
					  	<input name="tOTravelLeavedetailList[${stuts.index }].leaveReason" maxlength="800"
					  		type="text" class="inputxt"  style="width:160px;"

					                value="${poVal.leaveReason }">
					  <label class="Validform_label" style="display: none;">请假事由</label>
				   </td>
				   <td align="left">
					<input id ="withPeopleName${stuts.index}" name="tOTravelLeavedetailList[${stuts.index }].withPeopleName" maxlength="100"
						   type="text" class="inputxt"  style="width:160px;" value="${poVal.withPeopleName}">
					<input id ="withPeopleID${stuts.index}" name="tOTravelLeavedetailList[${stuts.index }].withPeopleID" maxlength="32"
						   type="hidden" class="inputxt"  style="width:160px;" value="${poVal.withPeopleID}">
					   <a href="#" id="choose${stuts.index}" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-search" onclick="custom_choose('',${stuts.index},'withPeopleID','withPeopleName')" title="选择">选择</a>
					   <a href="#" id="clearAll${stuts.index}" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-redo" onclick="custom_clearAll(${stuts.index},'withPeopleName');" title="清空">清空</a>
					<label class="Validform_label" style="display: none;">同行人员</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>
	</tbody>
</table>
