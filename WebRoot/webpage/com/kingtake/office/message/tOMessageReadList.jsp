<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTOMessageReadBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTOMessageReadBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTOMessageReadBtn').bind('click', function(){   
 		 var tr =  $("#add_tOMessageRead_table_template tr").clone();
	 	 $("#add_tOMessageRead_table").append(tr);
	 	 resetTrNum('add_tOMessageRead_table');
	 	 return false;
    });  
	$('#delTOMessageReadBtn').bind('click', function(){   
      	$("#add_tOMessageRead_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tOMessageRead_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tOMessageRead_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTOMessageReadBtn" href="#">添加</a> <a id="delTOMessageReadBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tOMessageRead_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						接收人姓名
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						阅读标志
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						阅读时间
				  </td>
	</tr>
	<tbody id="add_tOMessageRead_table">	
	<c:if test="${fn:length(tOMessageReadList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tOMessageReadList[0].id" type="hidden"/>
					<input name="tOMessageReadList[0].tOId" type="hidden"/>
					<input name="tOMessageReadList[0].receiverId" type="hidden"/>
					<input name="tOMessageReadList[0].delFlag" type="hidden"/>
					<input name="tOMessageReadList[0].delTime" type="hidden"/>
				  <td align="left">
					  	<input name="tOMessageReadList[0].receiverName" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">接收人姓名</label>
					</td>
				  <td align="left">
					  	<input name="tOMessageReadList[0].readFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">阅读标志</label>
					</td>
				  <td align="left">
							<input name="tOMessageReadList[0].readTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">阅读时间</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tOMessageReadList)  > 0 }">
		<c:forEach items="${tOMessageReadList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tOMessageReadList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tOMessageReadList[${stuts.index }].tOId" type="hidden" value="${poVal.tOId }"/>
					<input name="tOMessageReadList[${stuts.index }].receiverId" type="hidden" value="${poVal.receiverId }"/>
					<input name="tOMessageReadList[${stuts.index }].delFlag" type="hidden" value="${poVal.delFlag }"/>
					<input name="tOMessageReadList[${stuts.index }].delTime" type="hidden" value="${poVal.delTime }"/>
				   <td align="left">
					  	<input name="tOMessageReadList[${stuts.index }].receiverName" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.receiverName }">
					  <label class="Validform_label" style="display: none;">接收人姓名</label>
				   </td>
				   <td align="left">
					  	<input name="tOMessageReadList[${stuts.index }].readFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.readFlag }">
					  <label class="Validform_label" style="display: none;">阅读标志</label>
				   </td>
				   <td align="left">
							<input name="tOMessageReadList[${stuts.index }].readTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					                value="<fmt:formatDate value='${poVal.readTime}' type="date" pattern="yyyy-MM-dd"/>">  
					  <label class="Validform_label" style="display: none;">阅读时间</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
