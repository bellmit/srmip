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
 		 var tr =  $("#add_tOTravelLeavedetail_table_template tr").clone();
		 var text = tr.html();
		 num = $("#add_tOTravelLeavedetail_table").find('>tr').size();
		 text = text.replace(/#index#/g,num);
		 tr.html(text);
//		 console.log(tr.html());
//		 console.log("--------------------------------------------");
//		 console.log(text);

	 	 $("#add_tOTravelLeavedetail_table").append(tr);
	 	 //resetTrNum('add_tOTravelLeavedetail_table');
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
	    $("#tOTravelLeavedetail_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});

		var num = $("#add_tOTravelLeavedetail_table").find('>tr').size();

		//如果input是禁用状态，则隐藏link,否则显示link
		console.log($("input#leaveName").attr("disabled"));
		if($("input#leaveName").attr("disabled") == "disabled"){
			for(i=0;i<num;i++){
				$('a#choose'+i).hide();
				$('a#clearAll'+i).hide();
			}
		}
		else {
			for(i=0;i<num;i++){
				$('a#choose'+i).show();
				$('a#clearAll'+i).show();
			}
		}
    });
</script>

<script src="tOTravelLeave.js"></script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTOTravelLeavedetailBtn" href="#">添加</a> <a id="delTOTravelLeavedetailBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="0" cellspacing="0" id="tOTravelLeavedetail_table" >
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						起始时间
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						截止时间
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						外出地点
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						请假事由
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						同行人员
				  </td>
	</tr>
	<input id="deptId_hidden_" type="hidden">
	<tbody id="add_tOTravelLeavedetail_table">	
	<c:if test="${fn:length(tOTravelLeavedetailList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 40px;" name="xh">1</div></td>
				<td align="center"><input style="width:40px;"  type="checkbox" name="ck"/></td>
					<input name="tOTravelLeavedetailList[0].id" type="hidden"/>
				    <input name="tOTravelLeavedetailList[0].toId" type="hidden"/>
				  <td align="left">
							<input  id="beginDate" name="tOTravelLeavedetailList[0].startTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endDate\')}'})"  style="width:160px;"
					               >  
					  <label class="Validform_label" style="display: none;">起始时间</label>
					</td>
				  <td align="left">
							<input id="endDate" name="tOTravelLeavedetailList[0].endTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'beginDate\')}'})"  style="width:160px;"
					               >  
					  <label class="Validform_label" style="display: none;">截止时间</label>
					</td>
				  <td align="left">
					  	<input name="tOTravelLeavedetailList[0].address" maxlength="150" 
					  		type="text" class="inputxt"  style="width:160px;"
					               >
					  <label class="Validform_label" style="display: none;">外出地点</label>
					</td>
				  <td align="left">
					  	<input name="tOTravelLeavedetailList[0].leaveReason" maxlength="800" 
					  		type="text" class="inputxt"  style="width:160px;"
					               >
					  <label class="Validform_label" style="display: none;">请假事由</label>
				  </td>
				  <td align="left">
					  <input id ="withPeopleName0" name="tOTravelLeavedetailList[0].withPeopleName" maxlength="100"
						   type="text" class="inputxt"  style="width:160px;">
					  <input id ="withPeopleID0" name="tOTravelLeavedetailList[0].withPeopleID" maxlength="32"
							 type="hidden" class="inputxt"  style="width:160px;">

					  <a href="#" id="choose0" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-search" onclick="custom_choose('',0,'withPeopleID','withPeopleName')" title="选择">选择</a>
					  <a href="#" id="clearAll0" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-redo" onclick="custom_clearAll(0,'withPeopleName');" title="清空">清空</a>
					  <label class="Validform_label" style="display: none;">同行人员</label>
				  </td>
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
