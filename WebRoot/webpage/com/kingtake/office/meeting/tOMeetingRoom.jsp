<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会议室表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
   function setOrgIds() {
       var orgIdsname_ = $("#orgIds").combotree("getText");
       if(orgIdsname_=="" || orgIdsname_==null){
        	var tip = $("#orgIdsname_").next(".Validform_checktip");
         	tip.text("所属单位不能为空!");
         	tip.addClass("Validform_wrong");
         	return false;
       }
       $("#orgIdsname_").val(orgIdsname_);
    }
    
    $(function(){
        $('#orgIds').combotree({
			url : 'departController.do?getDepartTree',
            width: 155,
            panelHeight: 160,
            editable:true,
            onClick: function(node){
                $("#orgIds").val(node.id);
		        var orgIds_ = $("#orgIds").combotree("getValues");
		        var orgIdsname_ = $("#orgIds").combotree("getText");
		        $("#orgIds_").val(orgIds_);
		        $("#orgIdsname_").val(orgIdsname_);
			}
        });	
        
        if(location.href.indexOf("load=detail")!=-1){
            //隐藏下拉框箭头
            $(".combo-arrow").css("display","none");
        }
        
      // $("#orgIds").combotree("setValues", ${departList});  
    });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOMeetingRoomController.do?doAddUpdate" tiptype="1" beforeSubmit="setOrgIds">
		<input id="id" name="id" type="hidden" value="${tOMeetingRoomPage.id }">
		<input id="roomNum" name="roomNum" type="hidden" value="${tOMeetingRoomPage.roomNum }">
		<input id="createBy" name="createBy" type="hidden" value="${tOMeetingRoomPage.createBy }">
        <input id="createName" name="createName" type="hidden" value="${tOMeetingRoomPage.createName }">
        <input id="createDate" name="createDate" type="hidden" value="${tOMeetingRoomPage.createDate }">
        <input id="updateBy" name="updateBy" type="hidden" value="${tOMeetingRoomPage.updateBy }">
        <input id="updateName" name="updateName" type="hidden" value="${tOMeetingRoomPage.updateName }">
        <input id="updateDate" name="updateDate" type="hidden" value="${tOMeetingRoomPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">会议室名称:<font color="red">*</font></label></td>
				<td class="value" colspan="3">
		     	    <input id="roomName" name="roomName" style="width: 565px;" class="inputxt" datatype="*1-15"  type="text" validType="t_o_meeting_room,ROOM_NAME,id" value='${tOMeetingRoomPage.roomName}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">会议室名称</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">所&nbsp;属&nbsp;单&nbsp;位:<font color="red">*</font></label></td>
                <td class="value">
                    <%-- <select class="easyui-combotree" data-options="url:'departController.do?getOrgTree', editable:true, cascadeCheck:false"
                            id="orgSelect" name="orgSelect" >
                        <c:forEach items="${departList}" var="depart">
                            <option value="${depart.id }">${depart.departname}</option>
                        </c:forEach>
                    </select> --%>
                    <input id="orgIds"  value="${tOMeetingRoomPage.departName}" style="height: 25px;">
                    <input id="orgIds_"  name="departId" type="hidden" value="${tOMeetingRoomPage.departId}">
                    <input id="orgIdsname_"  name="departName" type="hidden" value="${tOMeetingRoomPage.departName}">
                    <span class="Validform_checktip"><t:mutiLang langKey="please.select.department"/></span>
                    <label class="Validform_label" style="display:none;">所属单位名称</label>
                </td>
				<td align="right"><label class="Validform_label">最大容纳人数:<font color="red">*</font></label></td>
				<td class="value">
				    <input id="maxnum" name="maxnum"  class="easyui-numberbox" style="width: 161px; padding: 3px 2px; border: 1px solid #a5aeb6;"
                    data-options="min:0,max:999999,groupSeparator:','" dataType="*1-7"
                    value='${tOMeetingRoomPage.maxnum}' >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">最大容纳人数</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">联&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;人:&nbsp;&nbsp;<font color="red">*</font></label></td>
				<td class="value">
				    <input id="contact" name="contact" class="inputxt" value='${tOMeetingRoomPage.contact}' dataType="*1-25">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">联系人</label>
				</td>
				<td align="right"><label class="Validform_label">联&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;电&nbsp;&nbsp;话:<font color="red">*</font></label></td>
				<td class="value">
				    <input id="phone" name="phone" class="inputxt" datatype="*1-50" value='${tOMeetingRoomPage.phone}' 
                      errormsg="手机号码不正确" style="width: 161px">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">联系电话</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点:<font color="red">*</font></label></td>
				<td class="value" colspan="3">
				    <input id="address" name="address" class="inputxt" style="width: 565px;" value='${tOMeetingRoomPage.address}' datatype="byterange" max="60" min="1" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">地点</label>
				</td>
			</tr>
            <tr>
              <td align="right"><label class="Validform_label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;</label></td>
              <td class="value" colspan="3">
                    <textarea  id="memo" name="memo" datatype="byterange"  max="200" min="0"  rows="4" style="width: 565px;" class="inputxt">${tOMeetingRoomPage.memo}</textarea>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">备注</label>
              </td>
            </tr>
		</table>
	</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/meeting/tOMeetingRoom.js"></script>	
</html>	