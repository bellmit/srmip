<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
<!--
body,td,th {
	font-size: 14px;
	color: #000000;
}
-->
</style>
<!--

//-->
 <!-- <script type="text/javascript">
	$('#addTONoticeReceiveBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTONoticeReceiveBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTONoticeReceiveBtn').bind('click', function(){   
 		 var tr =  $("#add_tONoticeReceive_table_template tr").clone();
	 	 $("#add_tONoticeReceive_table").append(tr);
	 	 resetTrNum('add_tONoticeReceive_table');
	 	 return false;
    });  
	$('#delTONoticeReceiveBtn').bind('click', function(){   
      	$("#add_tONoticeReceive_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tONoticeReceive_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tONoticeReceive_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script> -->
<!--  <div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTONoticeReceiveBtn" href="#">添加</a> <a id="delTONoticeReceiveBtn" href="#">删除</a>
</div>  -->

<table border="1" cellpadding="0" cellspacing="0" id="tONoticeReceive_table">
<tr bgcolor="#E6E6E6">
	<td></td>
	<th colspan="3" align="center">发送</th>
	<th colspan="5" align="center">接收</th>
</tr>
	<tr bgcolor="#E6E6E6">
<!-- 	<td></td> -->
		<th align="center" bgcolor="#EEEEEE" width="4%">序号</th>
				  <th align="center" bgcolor="#EEEEEE" width="8%">
						发送人
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						发送时间
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						发送意见
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="8%">
						接收人
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="10%">
						处理状态
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						处理意见
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="15%">
						处理时间
				  </th>
				  <th align="center" bgcolor="#EEEEEE" width="10%">
						是否有效
				  </th>
				  
	</tr>
	<tbody id="add_tONoticeReceive_table">	
		<c:forEach items="${stepList}" var="rf" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				   <td align="center"><span>${rf.operateUsername}</span></td>
				   <td align="center"><span><fmt:formatDate value='${rf.operateDate}' type="date" pattern="MM月dd日 hh:mm"/></span></td>
				   <td align="center" title="${rf.senderTip}"><c:choose><c:when test="${fn:length(rf.senderTip)<8}">${rf.senderTip}</c:when><c:otherwise>${fn:substring(rf.senderTip,0,8)}...</c:otherwise></c:choose></td>
				   <td align="center">
				   	<table rules="rows" cellpadding="0" cellspacing="0">
				   		<c:forEach items="${rf.rlist}" var="rl" varStatus="rls">
				   			<tr height="8px"><td>${rl.receiveUsername}</td></tr>
				   		</c:forEach>
				   	</table>
				   </td>
				   <td align="center">
				   	<table rules="rows">
				   		<c:forEach items="${rf.rlist}" var="rl" varStatus="rls">
				   			<tr height="8px"><td><t:convert codeType="1" code="CHULIZT" value="${rl.operateStatus}"></t:convert>
                </td></tr>
				   		</c:forEach>
				   	</table>
				   </td>
				   <td align="center">
				   	<table rules="rows">
				   		<c:forEach items="${rf.rlist}" var="rl" varStatus="rls">
				   			<tr height="8px"><td title="${rl.suggestionContent}"><c:choose><c:when test="${fn:length(rl.suggestionContent)<8}">${rl.suggestionContent}</c:when><c:otherwise>${fn:substring(rl.suggestionContent,0,8)}...</c:otherwise></c:choose></td></tr>
				   		</c:forEach>
				   	</table>
				   </td>
				   <td align="center">
				   	<table rules="rows">
				   		<c:forEach items="${rf.rlist}" var="rl" varStatus="rls">
				   			<tr height="8px"><td><c:choose><c:when test="${rl.operateTime!=''}"><fmt:formatDate value='${rl.operateTime}' type="date" pattern="MM月dd日 hh:mm"/></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td></tr>
				   		</c:forEach>
				   	</table>
				   </td>
				   <td align="center">
				   	<table rules="rows">
				   		<c:forEach items="${rf.rlist}" var="rl" varStatus="rls">
				   			<tr height="8px"><td><t:convert codeType="0" code="SFBZ" value="${rl.validFlag}"></t:convert></td></tr>
				   		</c:forEach>
				   	</table>
				   </td>
   			</tr>
		</c:forEach>
	</tbody>
</table>
