<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>差旅-人员请假信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.headTitle {
	background-color: #F2F7FF;
	text-align: center;
	height: 50px
}

.headLable {
	font-size: 20px;
	font-weight: bold;
	color: #5E7595;
}

.addDiv {
	padding-bottom: 5px;
}

.Wdate {
	height: 14px;
}
</style>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/office/travel/tOTravelLeave.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">

    $(document).ready(function() {
        $('#tt').tabs({
            onSelect : function(title) {
                $('#tt .panel-body').css('width', 'auto');
            }
        });
        $(".tabs-wrap").css('width', '100%');
        
        //判断是否补办，补办：1 ; 不补办：0
        var flag = $('input[name="reissuedFlag"]:checked').val();
        if(flag == 1){
            $('.reissuedInfo').show();
        }
        //是否补办绑定值改变事件
        $('#reissuedTd').change(function() {
            var flag = $('input[name="reissuedFlag"]:checked').val();
            if (flag == 1) {
                $('.reissuedInfo').show('slow', 'linear');
            } else {
                $('.reissuedInfo').hide('slow', 'linear');
                $('#reissuedReason').html("");
                $('#reissuedTime').val("");
            }
        });
        
        /* $('#cc').combobox({
			url : 'departController.do?getOrgCombobox&id=${tOTravelLeavePage.leaveId }',
            width: 155,
            editable:false,
            onSelect: function(node){
		        $("#departId").val(node.id);
		        $("#departName").val(node.text);
			}
        }); */
        
		/* if(location.href.indexOf("load=detail")!=-1){
      		//查看模式情况下,删除和上传附件功能禁止使用
			$(".jeecgDetail").hide();
			//隐藏下拉框箭头
            $(".combo-arrow").css("display","none");
		} */ 

    });
    
  	//增加校验规则
    function checkData(){
       var trs = $("#add_tOTravelLeavedetail_table tr");
       for(var i=0;i<trs.length;i++){
           var tr = trs[i];
           if(isEmpty(tr)){
               $(tr).remove();
           }
       }
    }
      
    //判断是否为空行
    function isEmpty(tr){
        var dates = $(tr).find(".Wdate");
        var inputs = $(tr).find(".inputxt");
        for(var i=0;i<dates.length;i++){
            inputs.push(dates[i]);
        }
        var flag = true;
        for(var j=0;j<inputs.length;j++){
            if($(inputs[j]).val()!=""){
                flag = false;
                break;
            }
        }
        return flag;
    }
</script>
</head>
<body style="overflow-x: hidden;" class="easyui-layout" fit="false" >
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" 
    action="tOTravelLeaveController.do?doAddUpdate"  callback="@Override uploadFile" beforeSubmit="checkData">
    <input id="id" name="id" type="hidden" value="${tOTravelLeavePage.id }">
    <div region="north" style="height:200px;" data-options="border:false">
    <table cellpadding="0" cellspacing="1" class="mytable">
      <tr>
        <td class="headTitle" colspan="6"><label class="headLable">请假申请表</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:&nbsp;&nbsp;</label></td>
        <td class="value"  colspan="3" maxLength="500">
        	<input id="title" name="title"  value="${tOTravelLeavePage.title}" style="width: 700px; height: 20px;"> 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">标题</label>                                  
        </td>
      </tr>
      <tr>
        <td align="right" width="100px"><label class="Validform_label">请&nbsp;假&nbsp;人&nbsp;:</label><font color="red">*</font></td>
        <td class="value" width="300px">
          <input id="leaveId" name="leaveId" type="hidden" value="${tOTravelLeavePage.leaveId }">
          <input id="leaveName" name="leaveName" class="inputxt" readonly="readonly" value='${tOTravelLeavePage.leaveName}' datatype="*" sucmsg="验证通过"> 
          <%-- <t:chooseUser idInput="leaveId" url="commonUserController.do?commonUser" tablename="selectedUserList"
          icon="icon-search" title="人员列表" textname="id,realName" inputTextname="leaveId,leaveName" mode="single"
          isclear="true" fun="setDepart"></t:chooseUser> --%>
          <t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="leaveId" mode="single"
			textname="id,realName,departId,departName"  
			inputTextname="leaveId,leaveName,departId,departName" ></t:chooseUser>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">请假人员姓名</label></td>
          
          
        <td align="right"><label class="Validform_label">请假人单位&nbsp;&nbsp;:</label></td>
        <td class="value">
          <%-- <input id="cc" class="easyui-combobox" data-options="valueField:'id',textField:'text'" value="${tOTravelLeavePage.departName }"/>   --%>
          <input id="departId" name="departId" type="hidden" value="${tOTravelLeavePage.departId }">
          <input id="departName" name="departName" type="text" value="${tOTravelLeavePage.departName }" style="width: 150px" class="inputxt"> 
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">请假人员单位名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">关联项目:&nbsp;&nbsp;</label></td>
        <td class="value">
          <input id="projectId" name="project.id" type="hidden" value="${tOTravelLeavePage.project.id}">
          <input id="projectName" type="text" style="width: 150px" class="inputxt" value='${tOTravelLeavePage.project.projectName}'>
          <t:chooseProject inputId="projectId"  inputName="projectName" icon="icon-search" title="关联项目" isclear="true" mode="single" all="true"></t:chooseProject>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关联项目</label></td>
        <td align="right"><label class="Validform_label">职务技术等级:</label></td>
        <td class="value">
        	<input id="duty" name="duty" value="${tOTravelLeavePage.duty}" style="width: 150px;height: 20px;" datatype="*0-30"  > 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">职务技术等级</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">请假时间:</label><font color="red">*</font></td>
        <td class="value">
          <input id="leaveTime" name="leaveTime" type="text" style="width: 150px" class="Wdate" datatype="date"
          onClick="WdatePicker()"  value='<fmt:formatDate value='${tOTravelLeavePage.leaveTime}' type="date" pattern="yyyy-MM-dd"/>'> 
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">请假时间</label></td>
        <td align="right"><label class="Validform_label">是&nbsp;&nbsp;否&nbsp;&nbsp;补&nbsp;&nbsp;办:</label></td>
        <td class="value" id="reissuedTd">
          <c:if test='${empty tOTravelLeavePage.reissuedFlag}'>
            <t:codeTypeSelect id="reissuedFlag" name="reissuedFlag" code="SFBZ" codeType="0" type="radio" defaultVal="0"></t:codeTypeSelect> 
          </c:if>
          <c:if test='${!empty tOTravelLeavePage.reissuedFlag}'>
            <t:codeTypeSelect id="reissuedFlag" name="reissuedFlag" code="SFBZ" codeType="0" type="radio" defaultVal="${tOTravelLeavePage.reissuedFlag}"></t:codeTypeSelect> 
          </c:if>
          <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">是否补办</label></td>
      </tr>
      <tr class="reissuedInfo" style="display: none;">
        <td align="right"><label class="Validform_label">补办理由:</label></td>
        <td class="value" colspan="3">
          <div class="addDiv">
            <label class="Validform_label">补办时间:</label> 
            <input id="reissuedTime" name="reissuedTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
              value='<fmt:formatDate value='${tOTravelLeavePage.reissuedTime}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">补办时间</label>
          </div>
          <div>
            <textarea id="reissuedReason" style="width: 600px;" class="inputxt" rows="4" name="reissuedReason">${tOTravelLeavePage.reissuedReason}</textarea>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">补办理由</label>
          </div>
        </td>
      </tr>
      <%-- <tr>
        <td align="right">
        	<label class="Validform_label">
        		呈报单位&nbsp;&nbsp;&nbsp;<br>
        		意&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;见:&nbsp;&nbsp;
        	</label>
        </td>
        <td class="value" colspan="3">
          <div class="addDiv">
            <label class="Validform_label">意见人姓名:</label> 
            <input id="opinionUserid" name="opinionUserid" type="hidden"
              value="${tOTravelLeavePage.opinionUserid }"> 
            <input id="opinionUsername" name="opinionUsername"
              type="text" style="width: 150px" class="inputxt" value='${tOTravelLeavePage.opinionUsername}'>
            <t:chooseUser idInput="opinionUserid" url="commonUserController.do?commonUser" mode="single"
              tablename="selectedUserList" icon="icon-search" title="人员列表" textname="id,realName"
              inputTextname="opinionUserid,opinionUsername" isclear="true"></t:chooseUser>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">意见人姓名</label>

            <label class="Validform_label">意见时间:</label> 
            <input id="opinionDate" name="opinionDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
              value='<fmt:formatDate value='${tOTravelLeavePage.opinionDate}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">意见时间</label>
          </div>
          <div>
            <textarea id="unitOpinion" style="width: 600px;" class="inputxt" rows="4" name="unitOpinion">${tOTravelLeavePage.unitOpinion}</textarea>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">呈报单位意见</label>
          </div>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">部领导批示:</label></td>
        <td class="value" colspan="3">
          <div class="addDiv">
            <label class="Validform_label">批示人姓名:</label> <input id="instrucUserid" name="instrucUserid" type="hidden"
              value="${tOTravelLeavePage.instrucUserid }"> <input id="instrucUsername" name="instrucUsername"
              type="text" style="width: 150px" class="inputxt" value='${tOTravelLeavePage.instrucUsername}' onformchange="javascript:alert(1);">
            <t:chooseUser idInput="instrucUserid" url="commonUserController.do?commonUser" tablename="selectedUserList"
              icon="icon-search" title="人员列表" textname="id,realName" inputTextname="instrucUserid,instrucUsername" isclear="true" ></t:chooseUser>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">批示人姓名</label>
            <label class="Validform_label">批示时间:</label> <input id="instrucDate" name="instrucDate" type="text"
              style="width: 150px" class="Wdate" onClick="WdatePicker()"
              value='<fmt:formatDate value='${tOTravelLeavePage.instrucDate}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">批示时间</label>
          </div>
          <div>
            <textarea id="departInstruc" style="width: 600px;" class="inputxt" rows="4" name="departInstruc">${tOTravelLeavePage.departInstruc}</textarea>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">部领导批示</label>
          </div>
        </td>
      </tr> --%>
      
    </table>
    </div>
    <div style="width: auto; height: 200px;" region="center">
      <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
      <div style="width: 800px; height: 1px;"></div>
      <%-- <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
        <t:tab href="tOTravelLeaveController.do?tOTravelLeavedetailList&id=${tOTravelLeavePage.id}" icon="icon-search"
          title="差旅-人员请假详细信息表" id="tOTravelLeavedetail"></t:tab>
      </t:tabs> --%>
      <div id="tt" tabPosition="top" border=flase style="margin: 0px; padding: 0px; overflow: hidden; width: auto;" class="easyui-tabs" fit="false">
        <div title="差旅-人员请假详细信息表" href="tOTravelLeaveController.do?tOTravelLeavedetailList&id=${tOTravelLeavePage.id}" style="margin: 0px; padding: 0px; overflow-x: hidden; overflow-y: auto;"></div>
        <div title="销假情况" >
        <table cellpadding="0" cellspacing="1" class="mytable">
        <tr>
        <td align="right"><label class="Validform_label">销假情况:&nbsp;&nbsp;</label></td>
        <td class="value" colspan="3">
          <div class="addDiv">
            <label class="Validform_label">销假时间:</label> <input id="backLeaveDate" name="backLeaveDate" type="text"
              style="width: 150px" class="Wdate" onClick="WdatePicker()"
              value='<fmt:formatDate value='${tOTravelLeavePage.backLeaveDate}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">销假时间</label>
          </div>
          <div>
            <textarea id="backLeaveState" style="width: 600px;" maxLength="100" class="inputxt" rows="4" name="backLeaveState">${tOTravelLeavePage.backLeaveState}</textarea>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">销假情况</label>
          </div>
        </td>
      </tr>
      </table>
        </div>
      </div>
    </div>
    <div region="south" data-options="border:false">
    <table cellpadding="0" cellspacing="1" class="mytable">
    <tr>
        <td align="right"><label class="Validform_label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;</label></td>
        <td class="value" colspan="3"><textarea id="remark" style="width: 600px;" class="inputxt" rows="4" maxLength="200"
            name="remark">${tOTravelLeavePage.remark}</textarea> <span class="Validform_checktip"></span> 
            <label style="">(备注中可填写其他外单位同行人员或其他事项)</label>
            <label class="Validform_label" style="display: none;">备注</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td colspan="3" class="value">
          <input type="hidden" value="${tOTravelLeavePage.id}" id="bid" name="bid" />
          <table style="max-width:600px;">
            <c:forEach items="${tOTravelLeavePage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);"
                  onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOTravelLeavePage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a
                  href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"
                  title="下载">下载</a></td>
                <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail"
                  onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload"
               buttonText="添加文件" formData="bid"
              uploader="commonController.do?saveUploadFiles&businessType=tOTravelLeave">
            </t:upload>
          </div>
         </td>
      </tr>
    </table>
    </div>
  </t:formvalid>
  <!-- 添加 附表明细 模版 -->
  <table style="display: none">
    <tbody id="add_tOTravelLeavedetail_table_template">
      <tr>
        <td align="center"><div style="width: 25px;" name="xh"></div></td>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
        <td align="left"><input name="tOTravelLeavedetailList[#index#].startTime" maxlength="0" type="text"
          class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 160px;"> <label
          class="Validform_label" style="display: none;">起始时间</label></td>
        <td align="left"><input name="tOTravelLeavedetailList[#index#].endTime" maxlength="0" type="text"
          class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 160px;"> <label
          class="Validform_label" style="display: none;">截止时间</label></td>
        <td align="left"><input name="tOTravelLeavedetailList[#index#].address" maxlength="150" type="text"
          class="inputxt" style="width: 160px;"> <label class="Validform_label" style="display: none;">外出地点</label>
        </td>
        <td align="left"><input name="tOTravelLeavedetailList[#index#].leaveReason" maxlength="800" type="text"
          class="inputxt" style="width: 160px;"> <label class="Validform_label" style="display: none;">请假事由</label>
        </td>
          <td align="left">
              <input id="withPeopleName#index#" name="tOTravelLeavedetailList[#index#].withPeopleName" maxlength="100"
                     type="text" class="inputxt"  style="width:160px;">
              <input id="withPeopleID#index#" name="tOTravelLeavedetailList[#index#].withPeopleID" maxlength="32"
                     type="hidden" class="inputxt"  style="width:160px;">
              <a href="#" id="choose#index#" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-search" onclick="custom_choose('',#index#,'withPeopleID','withPeopleName')" title="选择">选择</a>
              <a href="#" id="clearAll#index#" style="color: #0061CA;text-decoration: underline;font-size: 10px;" plain="true" icon="icon-redo" onclick="custom_clearAll(#index#,'withPeopleName');" title="清空">清空</a>
              <label class="Validform_label" style="display: none;">同行人员</label>
          </td>
      </tr>
    </tbody>
  </table>
</body>
</html>