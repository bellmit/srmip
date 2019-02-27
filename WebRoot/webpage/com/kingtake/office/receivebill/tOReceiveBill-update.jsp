<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>收文阅批单信息表</title>
  
  <t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
  <link href="webpage/com/kingtake/office/receivebill/receiveBillForm.css" rel="stylesheet">
   <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
  
  function uploadFile(data){
		$("#bid").val(data.obj.id);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
	function close(){
		frameElement.api.close();
	}
	
	function choose_297e201048183a730148183ad85c0001(inputName) {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:tOSendReceiveRegController.do?selectReg', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, width: 800, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function (){
                	iframe = this.iframe.contentWindow;
                    var fileNum = iframe.gettOSendReceiveRegListSelections('fileNum');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(fileNum);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(fileNum);
                        $("input[name='"+inputName+"']").blur();
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:tOSendReceiveRegController.do?selectReg', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, parent: windowapi, width: 800, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function(){
                	iframe = this.iframe.contentWindow;
                    var fileNum = iframe.gettOSendReceiveRegListSelections('fileNum');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(fileNum);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(fileNum);
                        $("input[name='"+inputName+"']").blur();
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
	
	function parse(data){
    	var parsed = [];
        	$.each(data.rows,function(index,row){
        		parsed.push({data:row,result:row,value:row.id});
        	});
				return parsed;
}
/**
 * 选择后回调 
 * 
 * @param {Object} data
 */
function callBack(data) {
	$("#receiveUnitName1").val(data.unitName);
	$("#receiveUnitName").val(data.unitName);
}

 /**
  * 每一个选择项显示的信息
  * 
  * @param {Object} data
  */
function formatItem(data) {
	return data.unitName;
}
 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  callback="@Override uploadFile" usePlugin="password" layout="table" action="tOReceiveBillController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tOReceiveBillPage.id }">
					<input id="registerType" name="registerType" value="${tOReceiveBillPage.registerType}" type="hidden" class="inputxt">
<%-- 					<input id="receiveUnitId" name="receiveUnitId" type="hidden" value="${tOReceiveBillPage.receiveUnitId }"> --%>
					<input id="contactId" name="contactId" type="hidden" value="${tOReceiveBillPage.contactId }">
					<input id="registerId" name="registerId" type="hidden" value="${tOReceiveBillPage.registerId }">
					<input id="registerDepartId" name="registerDepartId" type="hidden" value="${tOReceiveBillPage.registerDepartId }">
					<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOReceiveBillPage.archiveUserid }">
					<input id="createBy" name="createBy" type="hidden" value="${tOReceiveBillPage.createBy }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tOReceiveBillPage.updateBy }">
					
					<input id="registerTime" name="registerTime" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.registerTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<input id="registerName" name="registerName" type="hidden" value="${tOReceiveBillPage.registerName }">
					<input id="registerDepartName" name="registerDepartName" type="hidden" value="${tOReceiveBillPage.registerDepartName }">
					<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOReceiveBillPage.archiveFlag }">
					<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOReceiveBillPage.archiveUsername }">
					<input id="archiveDate" name="archiveDate" type="hidden" value="${tOReceiveBillPage.archiveDate }">
					<input id="createName" name="createName" type="hidden" value="${tOReceiveBillPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<input id="updateName" name="updateName" type="hidden" value="${tOReceiveBillPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOReceiveBillPage.updateDate }">
					
					<input id="suggestionType" name="suggestionType" type="hidden">
					<!-- <div >
					<span>
						<button class="uibutton" id="send_btn">发送</button>
					</span>
					</div> -->
					<!-- <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="60" align="center" class="title4"><p align="center" >海军工程大学收文阅批单</p></td>
  </tr>
</table> -->
  <div align="center" style="font-size: 24px;color: #FF0000;height: 60px;">海军工程大学收文阅批单</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
				<tr>
					<td width="90" align="center" class="title2">
      				来文<br>
    				单位</td>
					<td class="title3">
					<t:autocomplete minLength="2" dataSource="commonController.do?getAutoList" closefun="close" valueField="unitPinyin" searchField="unitPinyin,unitName"
		labelField="unitName" parse="parse" formatItem="formatItem" result="callBack" name="receiveUnitName1" entityName="TBSendDocUnitEntity" datatype="*" maxRows="10" nullmsg="请输入关键字" errormsg="数据不存在,请重新输入" label="${tOReceiveBillPage.receiveUnitName }"></t:autocomplete>
<!-- 		<a id="addBtn" class="easyui-linkbutton l-btn l-btn-plain" style="vertical-align: top;" title="添加项目" data-options="iconCls:'icon-add',plain:true" onclick="add('来文单位','tBSendDocUnitController.do?goAdd',null,300,180);"></a> -->
<%-- 							<t:departComboTree id="dd" name="" idInput="receiveUnitId" nameInput="receiveUnitName" width="155" lazy="false" value="${tOReceiveBillPage.receiveUnitId }"></t:departComboTree> --%>
<%-- 							 <input id="receiveUnitId" name="receiveUnitId" type="hidden" datatype="*" value="${tOReceiveBillPage.receiveUnitId }"> --%>
							 <input id="receiveUnitName" name="receiveUnitName" type="hidden" value="${tOReceiveBillPage.receiveUnitName }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">来文单位名</label>
						</td>
					<td width="90" align="center" class="title2">公文<br>
   						 编号</td>
					<td class="title3">
					<input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumPrefix}' readonly="readonly">
				﹝20<input id="fileNumYear" name="fileNumYear" datatype="*1-2" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumYear}' readonly="readonly">﹞
				<input id="billNum" name="billNum" datatype="*1-20" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.billNum}' readonly="readonly">号
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公文编号</label>
						</td>
					<td width="90" align="center" class="title2">密级</td>
					<td class="title3">
<!-- 					     	 <input id="secrityGrade" name="secrityGrade" type="text" style="width: 150px" class="inputxt"> -->
					     	 <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOReceiveBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0"></t:codeTypeSelect> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">密级</label>
						</td>
					</tr>
				<tr>
					<td align="center" class="title2">
      					公文<br>
     					 标题</td>
					<td class="title3" colspan="5">
					     	 <input id="title" name="title" type="text"  style="border-style: none none solid none;width: 600px" class="inputxt" value="${tOReceiveBillPage.title }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					</tr>
				<tr>
					<td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      阅<br>
      批</p>
    </td>
					<td class="title3"colspan="5">
<!-- 							<input disabled="disabled" type="button" value="填写意见" onclick="fillContent('leaderReview')"> -->
<!-- 							<br> -->
					     	<textarea id="leaderReview1" name="leaderReview1" readonly="readonly"  style="width: 600px;" rows="7" class="inputxt" disabled="disabled"><c:forEach items="${slist.llist}" var="ll">${ll.receiveUsername} : ${ll.suggestionContent}。(<fmt:formatDate value='${ll.operateTime}' type="date" pattern="yyyy-MM-dd HH:mm"/>)${hh}</c:forEach>
					     	</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">校首长阅批</label>
					</td>
						
					</tr>
					<tr>
					<td align="center" class="title2">
    <p>机<br>
      关<br>
      部<br>
      (院)<br>
      领<br>
      导<br>
      阅<br>
      批</p>
    </td>
					<td class="title3" colspan="5">
<!-- 							<input disabled="disabled" type="button" value="填写意见" onclick="fillContent('officeReview')"> -->
<!-- 							<br> -->
					     	 <textarea id="officeReview1" name="officeReview1"  readonly="readonly"  style="width: 600px;" rows="9"  class="inputxt"  disabled="disabled"><c:forEach items="${slist.olist}" var="ol">${ol.receiveUsername} : ${ol.suggestionContent}。(<fmt:formatDate value='${ol.operateTime}' type="date" pattern="yyyy-MM-dd HH:mm"/>)${hh}</c:forEach>
							 </textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">领导阅批</label>
						</td>
					</tr>
				<tr>
					<td align="center" class="title2">
      <p>承<br>
        办<br>
        单<br>
        位<br>
        意<br>
    见</p>
    </td>
					<td class="title3" colspan="5">
					
<!-- 							<input disabled="disabled" type="button" value="填写意见" onclick="fillContent('dutyOpinion')"> -->
<!-- 							<br> -->
					     	<textarea id="dutyOpinion1" name="dutyOpinion1"  readonly="readonly"  style="width: 600px;" rows="8"  class="inputxt"  disabled="disabled"><c:forEach items="${slist.dlist}" var="dl">${dl.receiveUsername} : ${dl.suggestionContent}。(<fmt:formatDate value='${dl.operateTime}' type="date" pattern="yyyy-MM-dd HH:mm"/>)${hh}</c:forEach></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">承办单位意见</label>
						</td>
					
					</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="10">
					<tr>
					<td><font color="red">承办单位:</font>
							<t:departComboTree id="cc" name="cc" idInput="dutyId" nameInput="dutyName" width="155"  value="${tOReceiveBillPage.dutyName }"></t:departComboTree>
					     	<input id="dutyId" name="dutyId" type="hidden" style="width: 150px" class="inputxt">
					     	<input id="dutyName" name="dutyName" type="hidden" style="width: 150px" class="inputxt"  value="${tOReceiveBillPage.dutyName }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">承办单位</label>
						</td>
					<td><font color="red">联系人:</font>
					     	 <input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 150px" class="inputxt" value="${tOReceiveBillPage.contactName }" datatype="byterange" max="36" min="1">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人</label>
						</td>
					<td><font color="red">电话:</font>
					     	 <input id="contactTel" name="contactTel" type="text"  datatype="*1-16"  style="border-style: none none solid none;width: 150px" class="inputxt" value="${tOReceiveBillPage.contactTel }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					</tr>
					
			</table>
			<table style="width: 100%;">
			<tr>
				<td width="10%"><font color="black">附件</font></td>
     			<td width="90%">
     			<input type="hidden" value="${tOReceiveBillPage.id }" id="bid" name="bid" />
     			<table>
        <c:forEach items="${tOReceiveBillPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOReceiveBillPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
            <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
          </tr>
        </c:forEach>
      </table>
      <script type="text/javascript">
          $.dialog.setting.zIndex =2111;
          function del(url,obj){
            $.dialog.confirm("确认删除该条记录?", function(){
                $.ajax({
                async : false,
                cache : false,
                type : 'POST',
                url : url,// 请求的action路径
                success : function(data) {
                  var d = $.parseJSON(data);
                  if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    $(obj).closest("tr").hide("slow");
                  }
                }
              });  
            }, function(){
            });
          }
          </script>
      <div>
 <div class="form" id="filediv"></div>
      <t:upload  queueID="filediv" name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOReceiveBill">
  </t:upload>
  </div>
 </td>
</tr>
			</table>
			<div style="width: auto; height: 200px;">
			<div style="width: 880px; height: 1px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tOReceiveBillController.do?getStepList&id=${tOReceiveBillPage.id}"
					icon="icon-search" title="阅批单流转步骤" id="tOReceiveBillStep"></t:tab>
			</t:tabs>
			</div>
		</div>
	</t:formvalid>
 </body>
 
 <script type="text/javascript">
  //编写自定义JS代码
  function fillContent(cid){
// 	  var suggestionType;
	  var temp = $("#"+cid)[0].innerHTML;
// 	  $("#"+cid)[0].innerHTML="修改校首长意见";
	  cont('意见填写', '请填写意见：', function(r){
			if (r){
				$("#"+cid)[0].innerHTML=temp+"\n"+r;
				$("#suggestionType").val(cid);
			}
		});
  }
  
  function cont(_243,msg,fn){
	  var _244="<div>"+msg+"</div>"+"<br/>"+"<div style=\"clear:both;\"/>"+"<div><textarea datatype=\"s0-50\"  style=\"width: 450px;\" rows=\"5\"  class=\"messager-input\"></textarea></div>";
	  var _245={};
	  _245[$.messager.defaults.ok]=function(){
	  win.window("close");
	  if(fn){
	  fn($(".messager-input",win).val());
	  return false;
	  }
	  };
	  _245[$.messager.defaults.cancel]=function(){
	  win.window("close");
	  if(fn){
	  fn();
	  return false;
	  }
	  };
	  var win=_237(_243,_244,_245);
	  win.children("input.messager-input").focus();
	  return win;
	  }
  
  function _237(_238,_239,_23a){
	  var win=$("<div class=\"messager-body\"></div>").appendTo("body");
	  win.append(_239);
	  if(_23a){
	  var tb=$("<div class=\"messager-button\"></div>").appendTo(win);
	  for(var _23b in _23a){
	  $("<a></a>").attr("href","javascript:void(0)").text(_23b).css("margin-left",10).bind("click",eval(_23a[_23b])).appendTo(tb).linkbutton();
	  }
	  }
	  win.window({title:_238,noheader:(_238?false:true),width:500,height:"auto",modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,onClose:function(){
	  setTimeout(function(){
	  win.window("destroy");
	  },100);
	  }});
	  win.window("window").addClass("messager-window");
	  win.children("div.messager-button").children("a:first").focus();
	  return win;
	  };

  
  
  </script>
  <script src = "webpage/com/kingtake/office/receivebill/tOReceiveBill.js"></script>		