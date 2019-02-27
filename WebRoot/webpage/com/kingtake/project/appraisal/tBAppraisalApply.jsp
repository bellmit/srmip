<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>鉴定申请表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">

                    function uploadCallback() {
                        reloadCurrentPage();
                    }


                    $(function() {
                    	var localUrl = window.location.href;
                        //编辑时审批已处理：提示不可编辑
                        if (localUrl.indexOf("tip=true") != -1) {
                            var msg = $("#tabMsg").val();
                            tip(msg);
                        } 

                        var auditStatus = $("#auditStatus").val();
                        if (auditStatus == '1') {
                            $("#formobj input").attr("disabled", "true");
                            $("#formobj select").attr("disabled", "true");
                            //隐藏选择框和清空框
                            $("#formobj a[icon='icon-search']").css("display", "none");
                            $("#formobj a[icon='icon-redo']").css("display", "none");
                            $("#formobj .easyui-linkbutton").css("display", "none");
                            //隐藏下拉框箭头
                            $("#formobj .combo-arrow").css("display", "none");
                            //隐藏添加附件
                            $("#formobj .jeecgDetail").parent().css("display", "none");
                        }
                    });
                    

                    //查看修改意见
                    function viewMsg() {
                        $('#msgDialog').window('open');
                    }
                </script>
</head>
<body>
  <%
      request.setAttribute("unaudit", ApprovalConstant.APPLYSTATUS_UNSEND);
  			request.setAttribute("send", ApprovalConstant.APPLYSTATUS_SEND);
  			request.setAttribute("audited",ApprovalConstant.APPLYSTATUS_AUDITED);
  			request.setAttribute("approvalView",ApprovalConstant.APPLYSTATUS_APPROVAL_VIEW);
  			request.setAttribute("finish", ApprovalConstant.APPLYSTATUS_FINISH);
  %>
    <!-- 表单在审批过程中显示保存按钮 -->
  <c:if test="${opt eq 'audit' }">
    <input id="saveBtn" value="保存" class="Button" onclick="saveForm()" type="button" style="position: fixed; right: 50px;">
  </c:if>
  <t:formvalid formid="formobj" dialog="true"  beforeSubmit="saveMembersBeforeSubmit" layout="table" action="tBAppraisalApplyController.do?doUpdate" tiptype="1"
    callback="@Override saveCallback">
    
    <div style="margin-left: 90px;">
      <input id="id" name="id" type="hidden" value="${tBAppraisalApply.id }">
      <input id="projectId" type="hidden" value="${tBAppraisalApply.projectId }">
      <input id="auditStatus" name="auditStatus" type="hidden" value="${tBAppraisalApply.auditStatus}">
       <input id="projectId" name="projectId" type="hidden" style="width: 465px" class="inputxt" readonly="readonly" value='${tBAppraisalApply.projectId}'>
            <input id="projectName" name="projectName" type="hidden" style="width: 465px" class="inputxt" readonly="readonly" value='${tBAppraisalApply.projectName}'>
      <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right" style="width: 165px;">
            <label class="Validform_label">成果名称:<font color="red">*</font></label>
          </td>
          <td class="value" colspan="5">
            <input id="achievementName" name="achievementName" type="text" style="width: 516px" class="inputxt" datatype="*1-100"  value='${tBAppraisalApply.achievementName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果名称</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">申请单位:&nbsp;</label>
          </td>
          <td class="value" colspan="3">
            <input id="approvalUnitName" name="approvalUnitName" type="text" style="width: 516px;" class="inputxt" readonly="readonly" value='${tBAppraisalApply.approvalUnitName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">申请单位</label>
          </td>
          <td align="right">
            <label class="Validform_label">起止时间:&nbsp;</label>
          </td>
          <td class="value">
            <input id="beginTime" name="beginTime" type="hidden" value='${tBAppraisalApply.beginTime}'>
            <input id="endTime" name="endTime" type="hidden" value='${tBAppraisalApply.endTime}'>
            <input id="dateRange" type="text" style="width: 180px" class="inputxt" readonly="readonly">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">起止时间</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">项目类型:&nbsp;</label>
          </td>
          <td class="value" colspan="3">
            <input id="projectType" name="projectType" style="width: 516px;"  value="${tBAppraisalApply.projectType}">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目类型</label>
          </td>
          <td align="right">
            <label class="Validform_label">总&nbsp;经&nbsp;费:&nbsp;</label>
          </td>
          <td class="value">
            <input id="totalAmount" name="totalAmount" type="text" style="width: 170px; text-align: right;" class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
              value='${tBAppraisalApply.totalAmount}'>元
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">总经费</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">联&nbsp;系&nbsp;人:&nbsp;</label>
          </td>
          <td class="value" style="width: 200px;">
            <input id="contactName" name="contactName" type="text" style="width: 180px" readonly="readonly" class="inputxt"
              value='${tBAppraisalApply.contactName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 联系电话: </label>
          </td>
          <td class="value" style="width: 200px;">
            <input id="contactPhone" name="contactPhone" type="text" style="width: 150px" readonly="readonly" class="inputxt" value='${tBAppraisalApply.contactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        <tr>
         <td align="right">
            <label class="Validform_label"> 其它关联项目:&nbsp;</label>
          </td>
          <td class="value" colspan="5">
            <input id="qtglxm" name="qtglxm" type="text" style="width: 520px" readonly="readonly" class="inputxt"
              value='${tBAppraisalApply.qtglxm}'>
            <input id="qtglxmId" name="qtglxmId" type="hidden" 
              value='${tBAppraisalApply.qtglxmId}'>
              <t:choose url="tPmProjectController.do?projectSelect&multiply=1" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="qtglxmId,qtglxm"
              isclear="true"></t:choose>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系人</label>
          </td>
        </tr>
        </table>
        <fieldset style="border-color: gray;border-style: dotted;width: 1100px;">
        <legend style="color: red;">成果完成单位信息</legend>
        <table>
        <tr>
          <td align="right" style="width: 165px;">
            <label class="Validform_label">单位名称&nbsp;&nbsp;:</label>
          </td>
          <td class="value" style="width: 230px;">
            <input id="finishUnit" name="finishUnit" type="text" style="width: 200px" class="inputxt" value='${tBAppraisalApply.finishUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果完成单位名称</label>
          </td>
          <td align="right">
            <label class="Validform_label">联&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;人:</label>
          </td>
          <td class="value" style="width: 270px;">
            <input id="finishContactId" name="finishContactId" type="hidden" value='${tBAppraisalApply.finishContactId}'>
            <input id="finishContactName" name="finishContactName" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.finishContactName}'>
            <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="finishContactId,finishContactName" idInput="finishContactId" mode="single"></t:chooseUser>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果完成单位联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label">联系电话: </label>
          </td>
          <td class="value" style="width: 250px;">
            <input id="finishContactPhone" name="finishContactPhone" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.finishContactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        </table>
        </fieldset>
        <fieldset style="border-color: gray;border-style: dotted;width: 1100px;">
        <legend style="color: red;">主持鉴定单位信息</legend>
        <table>
        <tr>
          <td align="right" style="width: 165px;">
            <label class="Validform_label">单位名称<img alt="请上传技术规范和质量保证大纲等附件" title="单位填写至海军业务局业务处（室），联系人填写至姓名、职务（职称），联系方式填写军线区号-号码，手机" src="plug-in\easyui1.4.2\themes\icons\tip.png">: </label>
          </td>
          <td class="value" style="width: 230px;">
            <input id="hostUnit" name="hostUnit" type="text" style="width: 200px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.hostUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">主持鉴定单位名称</label>
          </td>
          <td align="right">
            <label class="Validform_label">联&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;人:</label>
          </td>
          <td class="value" style="width: 270px;">
            <input id="appraisalContactName" name="appraisalContactName" type="text" style="width: 180px" height="23px" class="inputxt" value='${tBAppraisalApply.appraisalContactName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">主持鉴定单位联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label">联系电话:</label>
          </td>
          <td class="value" style="width: 250px;">
            <input id="appraisalContactPhone" name="appraisalContactPhone" type="text" style="width: 180px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.appraisalContactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        </table>
        </fieldset>
        <table>
        <tr>
          <td align="right">
            <label class="Validform_label">基层编号: <font color="red">*</font>
            </label>
          </td>
          <td class="value" colspan="3">
            <input id="basicNum" name="basicNum" type="text" style="width: 360px;" datatype="*" class="inputxt" readonly="readonly" value='${tBAppraisalApply.basicNum}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">基层编号</label>
            <a class="easyui-linkbutton" href="javascript:getSerialNum();" icon="icon-edit">获取基层编号</a>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 成&nbsp;&nbsp;果&nbsp;&nbsp;类&nbsp;&nbsp;别&nbsp;:</label>
          </td>
          <td class="value">
            <t:codeTypeSelect id="resultType" name="resultType" type="select" code="CGLB" codeType="1" defaultVal="${tBAppraisalApply.resultType}" extendParam="{style:'width:180px',class:'inputxt'}"></t:codeTypeSelect>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果类别</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 鉴定形式:</label>
          </td>
          <td class="value">
            <t:codeTypeSelect id="appraisalForm" name="appraisalForm" type="select" code="JDXS" codeType="1" defaultVal="${tBAppraisalApply.appraisalForm}"
              extendParam="{style:'width:180px',class:'inputxt'}"></t:codeTypeSelect>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">鉴定形式</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 计划鉴定时间:</label>
          </td>
          <td class="value">
            <input id="appraisalTime" name="appraisalTime" type="text" style="width: 174px;" class="Wdate" onClick="WdatePicker()" 
              value="<fmt:formatDate value='${tBAppraisalApply.appraisalTime}' 
							type='date' pattern='yyyy-MM-dd'/>">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">计划鉴定时间</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 计划鉴定地点:</label>
          </td>
          <td class="value">
            <input id="appraisalAddress" name="appraisalAddress" type="text" style="width: 174px" class="inputxt" value="${tBAppraisalApply.appraisalAddress}">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">计划鉴定地点</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;: </label>
          </td>
          <td class="value" colspan="3">
            <input type="hidden" value="${tBAppraisalApply.attachmentCode}" id="bid" name="attachmentCode" />
            <table style="max-width: 92%;" id="fileShow">
              <c:forEach items="${tBAppraisalApply.certificates}" var="file" varStatus="idx">
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
								uploader="commonController.do?saveUploadFilesToFTP&businessType=tBAppraisalApply"
                ></t:upload>
            </div>
          </td>
        </tr>
        <tr>
        <td colspan="4">
        <label style="color: red;">
           附件说明： 一般应该经过部队应用或试用才能鉴定，而且在材料中（科技报告、鉴定证书）体现应用情况或试用情况。
鉴定应提供的材料： 
1．项目任务书或者项目合同书；
2．科技报告；
3．需测试的应提供测试报告及其主要实验、测试记录报告；
4．应用情况报告；
5．专利受理通知书或专利证书；
6．计量测试成果需提供测试报告；
7．研制类成果（包括软件）需提供使用说明或操作手册。
注：1、该表的填写请参考第3页的相关要求；
2、申请表从海军机关返回后，请尽快送科研部存档；
3、红色字体为提醒注意部分，打印前请删除。
            
            </label>
        </td>
        </tr>
      </table>
    </div>
    <!-- 修改日志表用 -->
    <input id="sendIds" name="sendIds" type="hidden">
    <input type="hidden" id="memberStr" name="memberStr" />

  </t:formvalid>
  <div  style="padding: 1px;width: 1000px;height: 200px;margin: auto;">
     <table id="memberList" ></table>
  </div>
  <div>
  <label style="color: red;">
  填写要求：（前面两页双面打印，该页不打印）<br/>
    一、项目名称：与计划名称一致，如有变更，请提交更名报告。<br/>
    二、登记编号：由组织鉴定机关填写（上交时，不填）。<br/>
    三、成果类别：指新技术研究、新技术应用、技术开发、技术基础、技术革新、计量、装备论证、装备试验、装备维修、软科学（不含软件）、科学理论、论证研究、试验研究等。<br/>
    四、鉴定形式：会议鉴定、检测鉴定和函审鉴定。<br/>
    五、推荐鉴定委员会专家： <br/>
    1、鉴定委员会专家以海军科研院所专家为主，同时兼顾成果的以后的报奖、推广应用，人数不得少于7人（每个单位最多不超过2人），奇偶不限，必须是相关专业，应有高级职称； <br/>
    2、在同一单位不得邀请两名以上（含两名）相同专业的专家；尽量少邀请作战部队、军代局和工厂的专家（一般不超过2人），不能邀请参加研制单位的专家；<br/>
3、军内科研项目一般应邀请装备研究院（装备研究院的专家除院领导外其专家要填写到所）的专家参加，维改的项目一般应邀请装备技术研究所的专家参加；<br/>
4、完成人员、完成人员所在单位的专家、完成单位的专家都不得推荐。<br/>
5、根据保密要求，所有涉及引进装备、型号等项目鉴定，原则上只能推荐军队和国防工业部门有关专家。（国防工业部门含船舶工业集团、船舶重工集团、电子科技集团、兵器工业集团、兵器装备集团、航天科技集团、航天科工集团、航空工业第一集团、航空工业第二集团、核工业集团公司、核工业建设集团公司、北京理工大学、南京理工大学、北京航空航天大学、南京航空航天大学、西北工业大学、哈尔滨工业大学、哈尔滨工程大学）<br/>
6、专家技术职称只能填写教授、高工、研究员等，不能填写总工、总师等。<br/>
7、单位名称填写原则：单位不准使用代号。部队、军队院校、军队科研单位、军事代表系统，省略“中国人民解放军”字样，如单位公章为：“中国人民解放军海军装备研究院舰船论证研究所”，应填为“海军装备研究院舰船论证研究所”，其它不能省略，如不能填为：海军装备研究院舰船所；军队工厂省略“中国人民”字样，如单位公章为：“中国人民解放军第四八一○工厂”，应填为“解放军第四八一○工厂”；国家部（委）级省略“中国”字样，如“中国航天科技集团第一研究院”，应填写为“航天科技集团第一研究院”；其余应与盖章相一致。<br/>  
  
  </label>
  </div>
  <c:if test="${tBAppraisalApply.auditStatus eq audited or tBAppraisalApply.auditStatus eq approvalView or tBAppraisalApply.auditStatus eq finish}">
    <script type="text/javascript">
                    $(".jeecgDetail").parent().css("display", "none");
                    $("#Validform_msg").remove();
                    $('input').attr("disabled", "true");
                    $('textarea').attr("disabled", "true");
                    $('.easyui-linkbutton').hide();
                    $('.Wdate').attr("disabled", "true");
                </script>
    
  </c:if>
  <script type="text/javascript">
            $(document).ready(function() {
                        $("#dateRange").val(
                                $("#beginTime").val().substring(0, 10) + "~" + $("#endTime").val().substring(0, 10));
                        if ($("#finishUnit").val()=="") {
                            $("#finishUnit").val("海军工程大学");
                        } 
                        //extend();
                        initTab();
                    });

            //初始化成员表格
            function initTab() {
                var toolbar = [];
                var localUrl = location.href;
                if (localUrl.indexOf("load=detail") == -1) {//不包含
                    toolbar = [ {
                        iconCls : 'icon-add',
                        text : '添加',
                        handler : function() {
                        	openMemberWindow();
                        }
                    }, {
                        iconCls : 'icon-remove',
                        text : '删除',
                        handler : function() {
                            var selected = $("#memberList").datagrid("getSelected");
                            var index = $("#memberList").datagrid('getRowIndex', selected);
                            $("#memberList").datagrid("deleteRow", index);
                        }
                    } ];
                }
                $('#memberList').datagrid({
                    title : '推荐鉴定委员会成员',
                    fitColumns : true,
                    nowrap : true,
                    striped : true,
                    remoteSort : false,
                    idField : 'id',
                    width : '100%',
                    height:200,
                    editRowIndex : -1,
                    toolbar : toolbar,
                    columns : [ [ {
                        field : 'id',
                        title : 'id',
                        width : 100,
                        hidden : true
                    }, {
                        field : 'memberName',
                        title : '姓名',
                        width : 100
                    }, {
                        field : 'memberPosition',
                        title : '技术职称',
                        width : 100,
                        formatter : function(value, row, index) {
                                return row.memberPositionStr;
                            }
                    }, {
                        field : 'memberPositionStr',
                        title : '技术职称',
                        hidden : true
                    },{
                        field : 'memberProfession',
                        title : '专业',
                        width : 100,
                        formatter : function(value, row, index) {
                                return row.memberProfessionStr;
                        }
                    },{
                        field : 'memberProfessionStr',
                        title : '专业',
                        hidden : true
                    },{
                        field : 'workUnit',
                        title : '工作单位',
                        width : 100
                    }, {
                        field : 'memo',
                        title : '备注',
                        width : 180
                    } ] ],
                    onDblClickRow : function(rowIndex, rowData) {
                        	var width = 600;
                        	var height = 200;
                        	var title = "查看成员";
                        	var url = "tBAppraisalApplyController.do?goAppraisalApplyMember";
                        	if(typeof(windowapi) == 'undefined'){
                        		$.dialog({
                        			content: 'url:'+url,
                        			lock : true,
                        			width:width,
                        			height:height,
                        			title:title,
                        			opacity : 0.3,
                        			data : rowData,
                        			cache:false,
                        		    ok: function(){
                        		    	iframe = this.iframe.contentWindow;
                        				var param = iframe.getFormData();
                        				if(!param['check']){
                        					return false;
                        				}
                        				$("#memberList").datagrid("updateRow",{
                        					index:rowIndex,row:{
                        					"memberName":param['memberName'],
                        					"memberPositionStr":param['memberPositionName'],
                        					"memberPosition":param['memberPosition'],
                        					"memberProfession":param['memberProfession'],
                        					"memberProfessionStr":param['memberProfessionName'],
                        					"workUnit":param['workUnit'],
                        					"memo":param['memo']
                        					}
                        				});
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
                        			data : rowData,
                        			parent:windowapi,
                        			title:title,
                        			opacity : 0.3,
                        			cache:false,
                        		    ok: function(){
                        		    	iframe = this.iframe.contentWindow;
                        				var param = iframe.getFormData();
                        				if(!param['check']){
                        					return false;
                        				}
                        				$("#memberList").datagrid("updateRow",{
                        					index:rowIndex,row:{
                        					"memberName":param['memberName'],
                        					"memberPositionStr":param['memberPositionName'],
                        					"memberPosition":param['memberPosition'],
                        					"memberProfession":param['memberProfession'],
                        					"memberProfessionStr":param['memberProfessionName'],
                        					"workUnit":param['workUnit'],
                        					"memo":param['memo']
                        					}
                        				});
                        		    },
                        		    cancelVal: '关闭',
                        		    cancel: true 
                        		}).zindex();
                        	}
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

                loadData();

            }
            
           function openMemberWindow(){
            	var width = 600;
            	var height = 200;
            	var title = "添加成员";
            	var url = "tBAppraisalApplyController.do?goAppraisalApplyMember";
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
            				var param = iframe.getFormData();
            				if(!param['check']){
            					return false;
            				}
            				$("#memberList").datagrid("appendRow",{
            					"memberName":param['memberName'],
            					"memberPositionStr":param['memberPositionName'],
            					"memberPosition":param['memberPosition'],
            					"memberProfession":param['memberProfession'],
            					"memberProfessionStr":param['memberProfessionName'],
            					"workUnit":param['workUnit'],
            					"memo":param['memo']
            				});
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
            				var param = iframe.getFormData();
            				if(!param['check']){
            					return false;
            				}
            				$("#memberList").datagrid("appendRow",{
            					"memberName":param['memberName'],
            					"memberPositionStr":param['memberPositionName'],
            					"memberPosition":param['memberPosition'],
            					"memberProfession":param['memberProfession'],
            					"memberProfessionStr":param['memberProfessionName'],
            					"workUnit":param['workUnit'],
            					"memo":param['memo']
            				});
            		    },
            		    cancelVal: '关闭',
            		    cancel: true 
            		}).zindex();
            	}
            }

            function loadData() {
                //加载数据
                var id = $("#id").val();
                if (id != "") {
                    var x_url = "tBAppraisalApplyController.do?getMemberListByApply&tbId=" + id;
                    $('#memberList').datagrid('options').url = x_url;
                    setTimeout(function() {
                        $('#memberList').datagrid('load');
                    }, 0);

                }
            }

            function saveMembersBeforeSubmit() {
                var rows = $('#memberList').datagrid("getRows");
                var memberStr = JSON.stringify(rows);
                $("#memberStr").val(memberStr);
            }

            /**
             * 保存或更新
             */
            function saveOrUpdate() {
                var rows = $('#memberList').datagrid("getRows");
                for (var i = 0; i < rows.length; i++) {
                    if ($('#memberList').datagrid('validateRow', i)) {
                        $('#memberList').datagrid("endEdit", i);
                    } else {
                        return false;
                    }
                }
                rows = $('#memberList').datagrid("getRows");
                var memberRows = [];
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].memberName != "" && rows[i].memberName != undefined) {
                        memberRows.push(rows[i]);
                    }
                }
                var memberStr = JSON.stringify(memberRows);
                var data = $("#formobj").serialize();
                data = data + '&memberStr=' + memberStr;
                $.ajax({
                    type : 'post',
                    url : 'tBAppraisalApplyController.do?doUpdate',
                    data : data,
                    success : function(result) {
                        result = $.parseJSON(result);
                        tip(result.msg);
                        frameElement.api.opener.reloadTable();

                        // 将id保存到页面
                        if (!$("#id").val()) {
                            var obj = $.parseJSON(result.obj);
                            $("#id").val(obj.id);
                            $("#state").val(obj.state);
                        }
                        loadButton($("#id").val(), $("#state").val());
                        loadData();
                    }
                });
                return false;
            }

            function loadButton(id, state) {
                frameElement.api.button();
                if (!id) {
                    // 新建：保存、关闭
                    frameElement.api.button({
                        name : '保存',
                        callback : saveOrUpdate,
                        focus : true
                    }, {
                        name : '关闭'
                    });
                } else if (state == "0") {
                    // 未发送：保存、提交、关闭
                    frameElement.api.button({
                        name : '保存',
                        callback : saveOrUpdate,
                    }, {
                        name : '提交',
                        //callback:toManager,
                        focus : true
                    }, {
                        name : '关闭'
                    });
                } else {
                    // 已提交
                    frameElement.api.button({
                        name : '关闭'
                    });
                }
            }

            //校验拓展
            function extend() {
                $.extend($.fn.validatebox.defaults.rules, {
                    maxLength : {
                        validator : function(value, param) {
                            return value.length <= param[0];
                        },
                        message : '输入长度不能超过{0}'
                    }
                });
            }
            
            function getSerialNum() {
                $.ajax({
                    async : false,
                    cache : false,
                    type : 'POST',
                    url : 'tBAppraisaApprovalController.do?getApprApprovalNum',// 请求的action路径
                    success : function(data) {
                        var d = $.parseJSON(data).obj;
                        var year = d.substr(0, 4);
                        var num = d.substr(4, 7);
                        $('#basicNum').val('HJ-' + '502-' + year + '-' + num);
                    }
                });
            }
            
            function saveCallback(data) {
            	var win;
                var dialog = W.$.dialog.list['processDialog'];
                if(dialog==undefined){
                    win = frameElement.api.opener;
                }else{
                    win = dialog.content;
                }
                if (data.success) {
                    win.reloadTable();
                    frameElement.api.close();
                }
            }
            
            function saveForm(){
            	$("#formobj").find("#btn_sub").click();
            }
        </script>
        <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
        <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>