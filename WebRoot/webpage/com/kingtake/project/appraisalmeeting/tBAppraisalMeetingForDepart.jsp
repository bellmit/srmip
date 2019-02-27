<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
 <head>
  <title>鉴定会信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.deleteMember{
cursor: pointer;
}
.meetingTip{
color: red;
font-size: 25px;
}
</style>
 </head>
 <body>
 <%
request.setAttribute("send", ApprovalConstant.APPRSTATUS_SEND);
request.setAttribute("unsend", ApprovalConstant.APPRSTATUS_UNSEND);
request.setAttribute("finish", ApprovalConstant.APPLYSTATUS_FINISH);
request.setAttribute("expert", ApprovalConstant.MEETING_ATTEND_EXPERT);
request.setAttribute("member", ApprovalConstant.MEETING_UNATTEND_MEMBER);
%>
  <div>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tBAppraisalMeetingController.do?doUpdate" callback="@Override saveCallback" tiptype="1" btnsub="saveBtn">
    <input id="id" name="id" type="hidden" value="${tBAppraisalMeetingPage.id }">
    <input id="projectId" type="hidden" value="${projectId }">
    <input id="applyId" name="applyId" type="hidden" value="${tBAppraisalMeetingPage.applyId }">
    <input id="applyAttachedApplyId" name="applyAttached.applyId" type="hidden" value="${tBAppraisalMeetingPage.applyAttached.applyId }">
    <input id="hostDepartid" name="hostDepartid" type="hidden" value="${tBAppraisalMeetingPage.hostDepartid }">
    <input id="checkMemberIds" name="checkMemberIds" type="hidden">
    <input id="meetingDeptCode" name="meetingDeptCode"  type="hidden" value="${tBAppraisalMeetingPage.meetingDeptCode }">
    <fieldset>
          <legend>
            <b>申请表审批信息</b>
          </legend>
          <table style="width: 100%;">
            <tr>
              <td align="right" width="15%">
                <label class="Validform_label"> 海军机关批复意见:</label>
              </td>
              <td class="value" width="85%" colspan="3">
                <textarea id="navalAuthorityView" name="applyAttached.navalAuthorityView" style="width: 80%" rows="3" class="researchInput">${tBAppraisalMeetingPage.applyAttached.navalAuthorityView}</textarea>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label"> 附件: </label>
              </td>
              <td class="value" colspan="3">
                <input type="hidden" value="${tBAppraisalMeetingPage.applyAttached.attachmentCode}" id="bid1" name="applyAttached.attachmentCode" />
                <table style="max-width: 92%;" id="fileShow1">
                  <c:forEach items="${tBAppraisalMeetingPage.applyAttached.certificates}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                  <td>
                    <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;
                  </td>
                  <td style="width: 40px;">
                    <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                  </td>
                  <td style="width: 40px;">
                    <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                  </td>
                </tr>
                  </c:forEach>
                </table>
                <div>
                <div class="form" id="filediv1"></div>
                <span id="file_upload1span"><input type="file" name="fiels" id="file_upload1" /></span>
               </div>
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <!-- <input id="saveBtn2" value="提交" type="button" class="jeecgDetail"> -->
              </td>
            </tr>
          </table>

          <table style="width: 100%;">
            <tr>
              <td align="right">
                <label class="Validform_label"> 申批号: </label>
              </td>
              <td class="value" colspan="3">
                <t:codeTypeSelect name="applyAttached.applyPrefix" type="SELECT" codeType="1" code="ZSXDDW" id="applyPrefix" extendParam="{style='width:80px;',class='departInput'}"
                  defaultVal="${tBAppraisalMeetingPage.applyAttached.applyPrefix}"></t:codeTypeSelect>
                ( <input id="applyYear" name="applyAttached.applyYear" type="text" style="width: 60px" value='${tBAppraisalMeetingPage.applyAttached.applyYear}' class='departInput'>
                ) 鉴审批字<input id="applyNum" name="applyAttached.applyNum" type="text" style="width: 80px" value='${tBAppraisalMeetingPage.applyAttached.applyNum}' class='departInput'>号
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">申请鉴定单位批准时间:</label>
              </td>
              <td class="value" >
                <input id="applyApproveDate" name="applyAttached.applyApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalMeetingPage.applyAttached.applyApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">申请鉴定单位批准时间</label>
              </td>
              <td align="right">
                <label class="Validform_label">主持鉴定单位批准时间:</label>
              </td>
              <td class="value" >
                <input id="hostApproveDate" name="applyAttached.hostApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalMeetingPage.applyAttached.hostApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">主持鉴定单位批准时间</label>
              </td>
              <td align="right">
                <label class="Validform_label">组织鉴定单位批准时间:</label>
              </td>
              <td class="value" >
                <input id="organizeApproveDate" name="applyAttached.organizeApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalMeetingPage.applyAttached.organizeApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
              </td>
            </tr>
          </table>
        </fieldset>
        <fieldset>
        <legend><b>鉴定会信息</b></legend>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 鉴定会时间: </label>
        </td>
        <td class="value">
          <input id="meetingDate" name="meetingDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBAppraisalMeetingPage.meetingDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定会时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 鉴定会地点: </label>
        </td>
        <td class="value">
          <input id="meetingAddr" name="meetingAddr" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalMeetingPage.meetingAddr}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定会地点</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 联系人: </label>
        </td>
        <td class="value">
          <input id="contactName" name="contactName" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalMeetingPage.contactName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">联系人</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 联系电话: </label>
        </td>
        <td class="value">
          <input id="contactPhone" name="contactPhone" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalMeetingPage.contactPhone}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">联系电话</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 报到时间: </label>
        </td>
        <td class="value">
          <input id="registerTime" name="registerTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBAppraisalMeetingPage.registerTime}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">报到时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 报到地点: </label>
        </td>
        <td class="value">
          <input id="registerAddr" name="registerAddr" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalMeetingPage.registerAddr}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">报到地点</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 主持单位: </label>
        </td>
        <td class="value">
          <input id="hostDepartname" name="hostDepartname" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalMeetingPage.hostDepartname}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主持单位</label>
        </td>
      </tr>
      <tr>
          <td align="right">
            <label class="Validform_label"> 附件: </label>
          </td>
          <td class="value" colspan="3">
            <input type="hidden" value="${tBAppraisalMeetingPage.attachmentCode}" id="bid" name="attachmentCode" />
            <table style="max-width: 92%;" id="fileShow">
              <c:forEach items="${tBAppraisalMeetingPage.certificates}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td>
                    <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;
                  </td>
                  <td style="width: 40px;">
                    <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                  </td>
                  <td style="width: 40px;">
                    <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
								uploader="commonController.do?saveUploadFilesToFTP&businessType=researchAccessory" ></t:upload>
            </div>
          </td>
        </tr>
    </table>
    <div style="width: 1000px;margin: auto;">
    <table id="memberList" ></table>
    <table id="nonparticipantMemberList" ></table>
    <table id="participants" ></table>
    </div>
    </fieldset>
    <fieldset>
    <legend>机关录入</legend>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
        <td>
          <label class="Validform_label">备注:</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 通知编号: </label>
        </td>
        <td class="value">
          <input id="noticeNum" name="noticeNum" type="text" datatype="*0-20" style="width: 150px" class="departInput" value='${tBAppraisalMeetingPage.noticeNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">通知编号</label>
        </td>
      </tr>
      <tr>
          <td align="right">
            <label class="Validform_label"> 附件: </label>
          </td>
          <td class="value" colspan="3">
            <input type="hidden" value="${tBAppraisalMeetingPage.departAccessoryCode}" id="bid2" name="departAccessoryCode" />
            <table style="max-width: 92%;" id="fileShow2">
              <c:forEach items="${tBAppraisalMeetingPage.departAccessorys}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td>
                    <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;
                  </td>
                  <td style="width: 40px;">
                    <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                  </td>
                  <td style="width: 40px;">
                    <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                  </td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv2"></div>
              <span id="file_upload2span"><input type="file" name="fiels" id="file_upload2" /></span>
            </div>
          </td>
        </tr>
      </table>
    </fieldset>
  <div align="center" style="margin-top: 10px;">
    <a id="saveBtn" class="easyui-linkbutton">保存</a>
    </div>
  </t:formvalid>
  </div>
 </body>
 <script type="text/javascript">
    $(document).ready(function(){
    	//如果页面是详细查看页面
		var localUrl = location.href;
  if(localUrl.indexOf("load=detail")!=-1){
	//无效化所有表单元素，只能进行查看
		$(".inputxt").attr("disabled","true");
		$(".Wdate").attr("disabled","true");
		//隐藏选择框和清空框
		$("a[icon='icon-search']").css("display","none");
		$("a[icon='icon-redo']").css("display","none");
		//隐藏下拉框箭头
		$(".combo-arrow").css("display","none");
		//隐藏添加附件
		$("#filediv").parent().css("display","none");
		//隐藏附件的删除按钮
		$(".jeecgDetail").parent().css("display","none");
		$("input[type='button']").removeAttr("disabled");
  }

//编辑时审批已处理：提示不可编辑
if(localUrl.indexOf("tip=true") != -1){
  var msg = $("#tabMsg").val();
  tip(msg);
} 

    	initTab();//初始化表格
    	initUpload();//初始化附件
    });
    
    
  //初始化成员表格
	function initTab(){
		var toolbar = [];
		var participantsToolBar = [];
		if(location.href.indexOf("load=detail") == -1){//不包含
			toolbar = [ {
	            iconCls : 'icon-search',
	            text : '人员选择',
	            handler : function() {
	            	W.$.dialog({
									content : 'url:tBAppraisalMemberController.do?tBAppraisalMember&applyId='+$('#applyId').val(),
									title : '推荐鉴定委员会成员:',
									lock : true,
									opacity : 0.3,
									parent:windowapi,
									width : '700px',
									height : '400px',
									ok:function(){
										iframe = this.iframe.contentWindow;
										//向参会专家列表中加值
										var checkedMember = iframe.getMemberChecked();
										var ckIds = new Array(checkedMember.length);
										for(var i=0;i<checkedMember.length;i++){
											ckIds[i]=checkedMember[i].id;
										}
										var checkMemberIds=ckIds.join(',');
										$.ajax({
											url:'tBAppraisalMeetingController.do?selectMember',
											data:{
												checkMemberIds:checkMemberIds,
												meetingId:$('#meetingDeptCode').val(),
												applyId:$('#applyId').val()
											},
											type:'post',
											success:function(data){
												reloadMemberTables();
											}
										})
									},
									cancel:function(){
										
									},
								});
	            }
	        },{
	            iconCls : 'icon-add',
	            text : '新增',
	            handler : function() {
	            	W.$.dialog({
									content : 'url:tBAppraisalMeetingController.do?goAddMember&meetingId='+$('#meetingDeptCode').val()+'&memberType=${expert}',
									title : '新增参会专家',
									lock : true,
									opacity : 0.3,
									width : '400px',
									height : '150px',
									parent:windowapi,
									ok:function(){
										iframe = this.iframe.contentWindow;
										saveObj();
										return false;
									},
									cancel:function(){
							
									},
							}).zindex();
	           }
	        }];
		
			participantsToolBar =[{
	            iconCls : 'icon-add',
	            text : '新增',
	            handler : function() {
	            	W.$.dialog({
									content : 'url:tBAppraisalMeetingController.do?goAddMember&meetingId='+$('#meetingDeptCode').val()+'&memberType=${member}',
									title : '新增参会人员',
									lock : true,
									opacity : 0.3,
									parent:windowapi,
									width : '400px',
									height : '150px',
									ok:function(){
										iframe = this.iframe.contentWindow;
										saveObj();
										return false;
									},
									cancel:function(){
									},
							}).zindex();
	           }
	        }];
		}
	    $('#memberList').datagrid({
	        title:'参会专家',
	        fitColumns : true,
	        nowrap : true,
	        striped : true,
	        remoteSort : false,
	        idField : 'id',
	        width : '100%',
	        editRowIndex:-1,
	        toolbar : toolbar,
	        columns : [ [ 
	         {
	            field : 'id',
	            title : 'id',
	            width : 100,
	            hidden : true
	        }, {
	            field : 'departname',
	            title : '单位',
	            width : 100
	        }, {
	            field : 'quota',
	            title : '员额',
	            width : 100,
	        },
	        {
	            field : 'member',
	            title : '特邀专家',
	            width : 100,
	            formatter: function(value,row,index){
	            	console.log(row);
		            var result= new Array();
	            	if(row.memberList.length>0){
		            	for(var i=0;i<row.memberList.length;i++){
		            		
		  			      if(location.href.indexOf("load=detail")!=-1){
		            		if(row.memberList[i].FLAG =="1"){//新增人员名字颜色区分
		            			result[i]="<font color='green'>"+row.memberList[i].MEMBER_NAME+"</font>";
		            		}else{
		            			result[i]=row.memberList[i].MEMBER_NAME;
		            		}
		  			      }else{
		            		if(row.memberList[i].FLAG =="1"){//新增人员名字颜色区分
		            			result[i]="<font color='green'>"+row.memberList[i].MEMBER_NAME+"</font><span class='deleteMember' onclick=\"deleteMember('"+row.id+"','"+row.memberList[i].ID+"')\" title=\"移除该参会人员\"><img src=\"webpage/com/kingtake/project/appraisalmeeting/cancel.png\"></span>"
		            		}else{
		            			result[i]=row.memberList[i].MEMBER_NAME+"<span class='deleteMember' onclick=\"deleteMember('"+row.id+"','"+row.memberList[i].ID+"')\" title=\"移除该参会人员\"><img src=\"webpage/com/kingtake/project/appraisalmeeting/cancel.png\"></span>"
		            		}
		  			      }
		            	}
		            }
	            	return result.join("<br>");
	            }
	        },
	        {
	            field : 'memo',
	            title : '操作',
	            width : 180,
	            editor:{
	                type:'validatebox',
	                options:{
	                    validType:'maxLength[180]'
	                }
				}
	        } ] ],
	        onDblClickRow:function(rowIndex, rowData){
	            },
	            onBeforeEdit : function(rowIndex, rowData) {
	
	            },
	            onAfterEdit : function(rowIndex, rowData) {
	
	            },
	            pagination : false,
	            rownumbers : true,
	            onLoadSuccess : function() {
	            }
	        });
	    
	    $('#nonparticipantMemberList').datagrid({
	        title:'未参会专家',
	        fitColumns : true,
	        nowrap : true,
	        striped : true,
	        remoteSort : false,
	        idField : 'id',
	        width : '100%',
	        editRowIndex:-1,
	        columns : [ [ 
	         {
	            field : 'id',
	            title : 'id',
	            width : 100,
	            hidden : true
	        }, {
	            field : 'departname',
	            title : '单位',
	            width : 100
	        }, {
	            field : 'quota',
	            title : '员额',
	            width : 100,
	        },
	        {
	            field : 'member',
	            title : '特邀专家',
	            width : 100,
	            formatter: function(value,row,index){
		            var result= new Array();
	            	if(row.memberList.length>0){
		            	for(var i=0;i<row.memberList.length;i++){
		            		result[i]=row.memberList[i].MEMBER_NAME;
		            	}
		            }
	            	return result.join(",");
	            }
	        } ] ],
	        onDblClickRow:function(rowIndex, rowData){
	            },
	            onBeforeEdit : function(rowIndex, rowData) {
	
	            },
	            onAfterEdit : function(rowIndex, rowData) {
	
	            },
	            pagination : false,
	            rownumbers : true,
	            onLoadSuccess : function() {
	            }
	        });
	    
	    
	    $('#participants').datagrid({
	        title:'参会人员',
	        fitColumns : true,
	        nowrap : true,
	        striped : true,
	        remoteSort : false,
	        idField : 'id',
	        width : '100%',
	        editRowIndex:-1,
	        toolbar : participantsToolBar,
	        columns : [ [ 
	         {
	            field : 'id',
	            title : 'id',
	            width : 100,
	            hidden : true
	        }, {
	            field : 'departname',
	            title : '单位',
	            width : 100
	        }, {
	            field : 'quota',
	            title : '员额',
	            width : 100,
	        },
	        {
	            field : 'member',
	            title : '名称',
	            width : 100,
	            formatter: function(value,row,index){
	            	console.log(row);
		            var result= new Array();
	            	if(row.memberList.length>0){
		            	for(var i=0;i<row.memberList.length;i++){
		            		if(location.href.indexOf("load=detail")!=-1){
			            		if(row.memberList[i].FLAG =="1"){//新增人员名字颜色区分
			            			result[i]="<font color='green'>"+row.memberList[i].MEMBER_NAME+"</font>";
			            		}else{
			            			result[i]=row.memberList[i].MEMBER_NAME;
			            		}
			  			      }else{
		            		if(row.memberList[i].FLAG =="1"){//新增人员名字颜色区分
		            			result[i]="<span class=\"deleteMember\" onclick=deleteMember('"+row.id+"','"+row.memberList[i].ID+"') title=\"移除该参会人员\"><font color='green'>"+row.memberList[i].MEMBER_NAME+"</font><img src=\"webpage/com/kingtake/project/appraisalmeeting/cancel.png\"></span>"
		            		}else{
		            			result[i]="<span class=\"deleteMember\" onclick=deleteMember('"+row.id+"','"+row.memberList[i].ID+"') title=\"移除该参会人员\">"+row.memberList[i].MEMBER_NAME+"<img src=\"webpage/com/kingtake/project/appraisalmeeting/cancel.png\"></span>"
		            		}
			  			    	  
			  			      }
		            	}
		            }
	            	return result.join("<br>");
	            }
	        },
	        {
	            field : 'memo',
	            title : '操作',
	            width : 180,
	            editor:{
	                type:'validatebox',
	                options:{
	                    validType:'maxLength[180]'
	                }
				}
	        } ] ],
	        onDblClickRow:function(rowIndex, rowData){
	            },
	            onBeforeEdit : function(rowIndex, rowData) {
	
	            },
	            onAfterEdit : function(rowIndex, rowData) {
	
	            },
	            pagination : false,
	            rownumbers : true,
	            onLoadSuccess : function() {
	            }
	        });
	
	    loadData('memberList','1');
	    loadData('nonparticipantMemberList','0');
	    loadData('participants','2');

    }
	function loadData(listId,memberType){
	      //加载数据
	        var id = $("#meetingDeptCode").val();
	        if (id != "") {
	            var x_url = "tBAppraisalMeetingController.do?getMemberListByMeetingId&meetingId=" + id+"&memberType="+memberType;
	            $('#'+listId).datagrid('options').url = x_url;
	            setTimeout(function() {
	                $('#'+listId).datagrid('load');
	            }, 0);

	        }
	    }
	function deleteMember(departId,memberId){
		var meetingId = $('#meetingDeptCode').val();
		$.ajax({
			url:'tBAppraisalMeetingController.do?deleteMember',
			data:{
				meetingId:meetingId,
				departId:departId,
				memberId:memberId
			},
			type:'post',
			success:function(data){
				reloadMemberTables();
			}
		}); 
	}
	
	function addAttendExpert(departId){
    	W.$.dialog({
						content : 'url:tBAppraisalMemberController.do?goAddAttendExpert&meetingId='+$('#meetingDeptCode').val()+'&departId='+departId,
						title : '新增参会专家',
						lock : true,
						opacity : 0.3,
						parent:windowapi,
						width : '700px',
						height : '400px',
						ok:function(){
							iframe = this.iframe.contentWindow;
						},
						cancel:function(){
						},
					});
	}
	
	//保存回调
	function saveCallback(data) {
		window.location.reload();
    }
	
	function subMeeting(id,operator,userId,departId){
    	$.ajax({
    		url:'tBAppraisalMeetingController.do?doSubmit',
    		data:{'id':id,'operator':operator,'userId':userId,'departId':departId},
    		type:'post',
    		success:function(data){
    			window.location.reload();
    		}
    	});
    }
    
  //提交流程
    function startProcess(){
      var id = $("#id").val();
        //流程对应表单URL
        var url = 'tPmDeclareController.do?goSelectOperator2';
       if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定提交鉴定会申请吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180,id);
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定提交鉴定会申请吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180,id);
            }, function() {
            },windowapi).zindex();
        }
       
    }		

//打开选择人窗口
function openOperatorDialog(title,url,width,height,id){
	    var width = width?width:700;
	  	var height = height?height:400;
	  	if(width=="100%"){
	  		width = window.top.document.body.offsetWidth;
	  	}
	  	if(height=="100%"){
	  		height =window.top.document.body.offsetHeight-100;
	  	}
	  	
	  	if(typeof(windowapi) == 'undefined'){
	  		$.dialog({
	  			content: 'url:'+url,
	  			lock : true,
	  			width:width,
	  			height:height,
	  			title:title,
	  			opacity : 0.3,
	  			cache:false,
	  		    ok: function(){
	  		      iframe = this.iframe.contentWindow;
			    	var operator = iframe.getOperator();
			    	var userId = iframe.getUserId();
			    	var departId = iframe.getDepartId();
			    	if(operator=="" || operator ==undefined){
			    	    return false;
			    	}
			    	subMeeting(id,operator,userId,departId);
	  		    },
	  		    cancelVal: '关闭',
	  		    cancel: true 
	  		}).zindex();
	  	}else{
	  		W.$.dialog({
	  			content: 'url:'+url,
	  			lock : true,
	  			width:width,
	  			height:height,
	  			parent:windowapi,
	  			title:title,
	  			opacity : 0.3,
	  			cache:false,
	  		    ok: function(){
	  		      iframe = this.iframe.contentWindow;
			    	var operator = iframe.getOperator();
			    	var userId = iframe.getUserId();
			    	var departId = iframe.getDepartId();
			    	if(operator=="" || operator ==undefined){
			    	    return false;
			    	}
			    	subMeeting(id,operator,userId,departId);
	  		    },
	  		    cancelVal: '关闭',
	  		    cancel: true 
	  		}).zindex();
	  	}
	  }
	  

//查看修改意见
function viewMsg(){
    $('#msgDialog').window('open');    
}

function initUpload(){
	$('#file_upload1').uploadify({
                buttonText : '上传鉴定申请表扫描件',
                auto : true,
                progressData : 'speed',
                multi : true,
                height : 25,
                width : 150,
                overrideEvents : [ 'onDialogClose' ],
                fileTypeDesc : '文件格式:',
                queueID : 'filediv1',
                fileTypeExts : '*.*',
                fileSizeLimit : '500MB',
                swf : 'plug-in/uploadify/uploadify.swf',
                uploader : 'commonController.do?saveUploadFilesToFTP&businessType=scanningApply',
                onUploadStart : function(file) {
                    var bid = $('#bid1').val();
                    var projectId = $('#projectId').val();
                    $('#file_upload1').uploadify("settings", "formData", {
                        'bid' : bid,
                        'projectId':projectId    
                    });
                },
                onQueueComplete : function(queueData) {
                },
                onUploadSuccess : function(file, data, response) {
                	var d=$.parseJSON(data);
                	updateUploadSuccessTable("fileShow1",d,file,response);
                },
                onFallback : function() {
                    tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                },
                onSelectError : function(file, errorCode, errorMsg) {
                    switch (errorCode) {
                    case -100:
                        tip("上传的文件数量已经超出系统限制的"
                                + $('#file_upload1').uploadify('settings',
                                        'queueSizeLimit') + "个文件！");
                        break;
                    case -110:
                        tip("文件 ["
                                + file.name
                                + "] 大小超出系统限制的"
                                + $('#file_upload1').uploadify('settings',
                                        'fileSizeLimit') + "大小！");
                        break;
                    case -120:
                        tip("文件 [" + file.name + "] 大小异常！");
                        break;
                    case -130:
                        tip("文件 [" + file.name + "] 类型不正确！");
                        break;
                    }
                },
                onUploadProgress : function(file, bytesUploaded,
                        bytesTotal, totalBytesUploaded, totalBytesTotal) {
                }
            });
	
	$('#file_upload2').uploadify({
        buttonText : '添加附件',
        auto : true,
        progressData : 'speed',
        multi : true,
        height : 25,
        width : 150,
        overrideEvents : [ 'onDialogClose' ],
        fileTypeDesc : '文件格式:',
        queueID : 'filediv2',
        fileTypeExts : '*.*',
        fileSizeLimit : '500MB',
        swf : 'plug-in/uploadify/uploadify.swf',
        uploader : 'commonController.do?saveUploadFilesToFTP&businessType=departAccessory',
        onUploadStart : function(file) {
            var bid = $('#bid2').val();
            var projectId = $('#projectId').val();
            $('#file_upload2').uploadify("settings", "formData", {
                'bid' : bid,
                'projectId':projectId    
            });
        },
        onQueueComplete : function(queueData) {
        },
        onUploadSuccess : function(file, data, response) {
        	var d=$.parseJSON(data);
        	updateUploadSuccessTable("fileShow2",d,file,response);
        },
        onFallback : function() {
            tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
        },
        onSelectError : function(file, errorCode, errorMsg) {
            switch (errorCode) {
            case -100:
                tip("上传的文件数量已经超出系统限制的"
                        + $('#file_upload2').uploadify('settings',
                                'queueSizeLimit') + "个文件！");
                break;
            case -110:
                tip("文件 ["
                        + file.name
                        + "] 大小超出系统限制的"
                        + $('#file_upload2').uploadify('settings',
                                'fileSizeLimit') + "大小！");
                break;
            case -120:
                tip("文件 [" + file.name + "] 大小异常！");
                break;
            case -130:
                tip("文件 [" + file.name + "] 类型不正确！");
                break;
            }
        },
        onUploadProgress : function(file, bytesUploaded,
                bytesTotal, totalBytesUploaded, totalBytesTotal) {
        }
    });
}

//刷新表格
function reloadMemberTables(){
	$("#memberList").datagrid("reload");
	$("#nonparticipantMemberList").datagrid("reload");
	$("#participants").datagrid("reload");
}
    </script>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
 <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
