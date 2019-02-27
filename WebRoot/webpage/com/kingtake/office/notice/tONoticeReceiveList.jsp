<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
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
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTONoticeReceiveBtn" href="#">添加</a> <a id="delTONoticeReceiveBtn" href="#">删除</a>
</div>

<table border="0" cellpadding="2" cellspacing="0" id="tONoticeReceive_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						接收人id
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						接收人姓名
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						是否阅读
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						阅读时间
				  </td>
	</tr>
	<tbody id="add_tONoticeReceive_table">	
	<c:if test="${fn:length(tONoticeReceiveList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tONoticeReceiveList[0].id" type="hidden"/>
					<input name="tONoticeReceiveList[0].noticeId" type="hidden"/>
				  <td align="left">
					       	<input name="tONoticeReceiveList[0].receiverId" maxlength="32" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                >
					  <label class="Validform_label" style="display: none;">接收人id</label>
					</td>
				  <td align="left">
					  	<input name="tONoticeReceiveList[0].receiverName" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">接收人姓名</label>
					</td>
				  <td align="left">
					  	<input name="tONoticeReceiveList[0].readFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">是否阅读</label>
					</td>
				  <td align="left">
							<input name="tONoticeReceiveList[0].readTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">阅读时间</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tONoticeReceiveList)  > 0 }">
		<c:forEach items="${tONoticeReceiveList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tONoticeReceiveList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tONoticeReceiveList[${stuts.index }].noticeId" type="hidden" value="${poVal.noticeId }"/>
				   <td align="left">
					       	<input name="tONoticeReceiveList[${stuts.index }].receiverId" maxlength="32" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.receiverId }">
					  <label class="Validform_label" style="display: none;">接收人id</label>
				   </td>
				   <td align="left">
					  	<input name="tONoticeReceiveList[${stuts.index }].receiverName" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.receiverName }">
					  <label class="Validform_label" style="display: none;">接收人姓名</label>
				   </td>
				   <td align="left">
					  	<input name="tONoticeReceiveList[${stuts.index }].readFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.readFlag }">
					  <label class="Validform_label" style="display: none;">是否阅读</label>
				   </td>
				   <td align="left">
							<input name="tONoticeReceiveList[${stuts.index }].readTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					                value="<fmt:formatDate value='${poVal.readTime}' type="date" pattern="yyyy-MM-dd"/>">  
					  <label class="Validform_label" style="display: none;">阅读时间</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
