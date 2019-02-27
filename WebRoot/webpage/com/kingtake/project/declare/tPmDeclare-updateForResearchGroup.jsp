<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目申报</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src="webpage/com/kingtake/project/declare/tPmDeclare.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript">
  function checkData() {
    var beginDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    if (beginDate > endDate) {
      //tip("起始日期不能大于结束日期!");
      $.Showmsg("开始时间不能大于结束时间!");
      return false;
    }
  }

  function saveBaseInfo(){
    $("#formobj").submit();
  }
  
  $(function() {
    var read = $("#read").val();
    if (read == '1') {
      //无效化所有表单元素，只能进行查看
      $(":input").attr("disabled", "true");
      //隐藏选择框和清空框
      $("a[icon='icon-search']").css("display", "none");
      $("a[icon='icon-redo']").css("display", "none");
      $("a[icon='icon-save']").css("display", "none");
      $("a[icon='icon-ok']").css("display", "none");
      //隐藏下拉框箭头
      $(".combo-arrow").css("display", "none");
      //隐藏添加附件
      $("#filediv").parent().css("display", "none");
      //隐藏附件的删除按钮
      $(".jeecgDetail").parent().css("display", "none");
      //隐藏easyui-linkbutton
      $("#formobj .easyui-linkbutton").css("display", "none");
    }
  });
  
  function saveCallback(data) {
	  $.messager.alert('提示',data.msg,'info',function(){
      	if (data.success) {
      		window.location.reload();
      	}
      });
  }
</script>
</head>
<body>
  <input id="read" value=${read } type="hidden" />
  <div style="height: 880px;">
    <div title="${declare.projectName }：申报书基本信息">
      <div style="padding: 5px 0; background-color: #f4f4f4;">
        <table style="width: 865px; margin: 0 auto;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td align="center" bgcolor="#E5EFFF">
              <b>${declare.projectName }：申报书基本信息</b>
            </td>
            <td width="25%" bgcolor="#E5EFFF" align="right">
              <c:if test="${read == '0' }">
                <a id="saveBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" onclick="saveBaseInfo();">保存</a>
              </c:if>
            </td>
          </tr>
        </table>
        <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmDeclareController.do?doAddUpdate" tiptype="1" beforeSubmit="checkData"
          callback="@Override saveCallback">
          <input id="id" name="id" type="hidden" value="${declare.id }">
          <input id="projectId" name="project.id" type="hidden" value="${declare.project.id }">
          <input id="tpId" name="tpId" type="hidden" value="${declare.tpId }">
          <input id="createBy" name="createBy" type="hidden" value="${declare.createBy }">
          <input id="createName" name="createName" type="hidden" value="${declare.createName }">
          <input id="createDate" name="createDate" type="hidden" value="${declare.createDate }">
          <input id="updateBy" name="updateBy" type="hidden" value="${declare.updateBy }">
          <input id="updateName" name="updateName" type="hidden" value="${declare.updateName }">
          <input id="updateDate" name="updateDate" type="hidden" value="${declare.updateDate }">
          <table cellpadding="0" cellspacing="1" class="formtable" style="width: 865px; margin: 0 auto;">
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  项目编号:
                </label>
              </td>
              <td style="width: 75%;" class="value">
                  <input id="projectNo" name="projectNo" type="text" class="inputxt" readonly="readonly" style="width: 300px" value="${declare.projectNo}" >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">项目编号</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  项目名称:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="projectName" name="projectName" type="text" class="inputxt" style="width: 300px" value="${declare.projectName}" datatype="*1-100">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">项目名称</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  负责人:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="projectManagerId" name="projectManagerId" type="hidden" style="width: 150px" class="inputxt"  value="${declare.projectManagerId}">
                <input id="orgId" name="orgId" type="hidden" style="width: 150px" class="inputxt">
                <input id="projectManagerName" name="projectManagerName" type="text" datatype="*" style="width: 300px" class="inputxt" readonly="readonly"  value="${declare.projectManagerName}">
                <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId" isclear="true" inputTextname="projectManagerId,projectManagerName,orgId" idInput="projectManagerId"
                  mode="single"></t:chooseUser>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">负责人</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  联系电话:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="contactPhone" name="contactPhone" type="text" class="inputxt" style="width: 300px" value="${declare.contactPhone}" datatype="*1-30">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">联系电话</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  通信地址:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="mailingAddress" name="mailingAddress" type="text" class="inputxt" style="width: 300px" value="${declare.mailingAddress}" datatype="*1-200">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">通信地址</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  邮政编码:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="postalCode" name="postalCode" type="text" class="inputxt" style="width: 300px" value="${declare.postalCode}" datatype="*1-6">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">邮政编码</label>
              </td>
            </tr>
            <tr>
              <td style="width: 25%;" align="right">
                <label class="Validform_label">
                  研究时限:
                  <font color="red">*</font>
                </label>
              </td>
              <td style="width: 75%;" class="value">
                <input id="beginDate" name="beginDate" type="text" style="width: 133px" class="Wdate" onClick="WdatePicker()"
                  value='<fmt:formatDate value='${declare.beginDate}' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">研究时限开始时间</label>-&nbsp;&nbsp;
                <input id="endDate" name="endDate" type="text" style="width: 133px" class="Wdate" onClick="WdatePicker()"
                  value='<fmt:formatDate value='${declare.endDate}' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">研究时限结束时间</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  项目来源:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
                <input id="projectSrc" name="projectSrc" type="text" style="width: 300px" class="inputxt" datatype="*2-100" value='${declare.projectSrc}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">项目来源</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  建设单位:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
              <input name="buildUnitName" id="buildUnitName" value='${declare.buildUnitName}' style="width: 300px" class="inputxt" >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">建设单位</label>
                    <script type="text/javascript">
                      //选择承研单位时，将承研单位的父单位加入责任单位
              $(function(){
                $('#buildUnitName').combotree({
                  url : 'departController.do?getDepartTree&lazy=false',
                  width : '306',
                  height : '27',
                  multiple : false,
                  onSelect : function(record){
                    $("#buildUnitId").val(record.id);
                  }
                });
              });
            </script>
                      <input id="buildUnitId" name="buildUnitId" type="hidden" datatype="*" value='${declare.buildUnitId}'> 
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  建设地点:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
                <input id="constructionAddr" name="constructionAddr" type="text" style="width: 300px" class="inputxt" datatype="*2-200" value='${declare.constructionAddr}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">建设地点</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  编制日期:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
                <input id="applyDate" name="applyDate" datatype="date" type="text" style="width: 300px"  class="Wdate" onClick="WdatePicker()"
                  value='<fmt:formatDate value='${declare.applyDate}' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">编制日期</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label"> 流程处理状态: </label>
              </td>
              <td class="value">
                <input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly">
                <input id="bpmStatus" value="${declare.bpmStatus }" type="hidden"  />
                <input id="planStatus" value="${declare.planStatus }" type="hidden"  />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">流程处理状态</label>
             </td>
             </tr>
            <%-- <input type="hidden" name="bpmStatus" value='<c:if test="${empty declare.bpmStatus }">1</c:if><c:if test="${!empty declare.bpmStatus }">${declare.bpmStatus}</c:if>'> --%>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  合作单位:
                </label>
              </td>
              <td class="value">
                <textarea id="coorperationUnit" name="coorperationUnit" rows="3" style="width: 300px;" class="inputxt" datatype="byterange" max="100" min="0">${declare.coorperationUnit}</textarea>
                <span class="Validform_checktip"></span>
                <label>最多可输入100个汉字</label>
                <label class="Validform_label" style="display: none;">主要研究内容</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  主要研究内容:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
                <textarea id="researchContent" name="researchContent" rows="4" style="width: 300px;" class="inputxt" datatype="*2-1000">${declare.researchContent}</textarea>
                <span class="Validform_checktip"></span>
                <label>最多可输入1000个汉字</label>
                <label class="Validform_label" style="display: none;">主要研究内容</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  研究进度及成果形式:
                  <font color="red">*</font>
                </label>
              </td>
              <td class="value">
                <textarea id="scheduleAndAchieve" name="scheduleAndAchieve" rows="3" style="width: 300px;" class="inputxt" datatype="*2-1000">${declare.scheduleAndAchieve}</textarea>
                <span class="Validform_checktip"></span>
                <label>最多可输入1000个汉字</label>
                <label class="Validform_label" style="display: none;">研究进度及成果形式</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${declare.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width: 360px;" id="fileShow">
                  <c:forEach items="${declare.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                    uploader="commonController.do?saveUploadFilesToFTP&businessType=declare" ></t:upload>
                </div>
              </td>
            </tr>
          </table>
          <div id="btnDiv" class="form" style="display: none;">
            <div class="ui_buttons" style="text-align: right">
              <input id="submitBtn" class=" ui_state_highlight" type="button" value="提交">
            </div>
          </div>
        </t:formvalid>
        </div>
              <div style="padding: 5px 0; background-color: #f4f4f4;">
        <table style="width: 865px; margin: 0 auto;" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td align="center" bgcolor="#E5EFFF">
              <b>${declare.projectName }：申报书基本信息</b>
            </td>
            <td width="25%" bgcolor="#E5EFFF" align="right">
              <c:if test="${read == '0' }">
                <a id="saveBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" onclick="saveBaseInfo();">保存</a>
              </c:if>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</body>
