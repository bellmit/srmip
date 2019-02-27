<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专利申请</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function(){
        $("#dljgName").combobox({
            url:'tZDljgxxController.do?getDljgList',
            editable:false,
            valueField:'id',
            textField:'text',
            onLoadSuccess:function(){
                var dljgId = $("#dljgId").val();
                if(dljgId!=""){
                    $(this).combobox('setValue',dljgId);
                }
            },
            onChange:function(newValue, oldValue){
                $("#dljgId").val(newValue);
            }
        });
    });
    
    function clearProj(){
        $("#glxmId").val('');
        $("#glxm").val('');
    }
</script>
</head>
<body>
<div style="margin: 0 auto;width: 600px;">
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tZZlsqController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tZZlsqPage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 归档号: </label></td>
        <td class="value"><input id="gdh" name="gdh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.gdh}' readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">归档号</label></td>
        <td align="right"><label class="Validform_label"> 完成单位: <font color="red">*</font></label></td>
        <td class="value">
        <input id="wcdw" name="wcdw" type="hidden" value='${tZZlsqPage.wcdw}' >
        <t:departComboTree id="wcdwId" name="wcdwId" idInput="wcdwId" nameInput="wcdw" value="${tZZlsqPage.wcdwId}"
                        lazy="false" width="156" ></t:departComboTree>
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">完成单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 类型: <font color="red">*</font></label></td>
        <td class="value">
        <t:codeTypeSelect name="lx" type="select" codeType="1" code="ZLLX" id="lx" defaultVal="${tZZlsqPage.lx}" extendParam='{style:"width: 156px","datatype":"*"}'></t:codeTypeSelect> 
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">类型</label></td>
        <td align="right"><label class="Validform_label"> 关联项目: </label></td>
        <td class="value">
        <input type="hidden" id="glxmId" name="glxmId" value="${tZZlsqPage.glxmId}">
        <input id="glxm" name="glxm" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.glxm}' onfocus="clearProj()">
        <t:choose url="tPmProjectController.do?projectSelect" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="glxmId,glxm"
              isclear="true"></t:choose>
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">关联项目</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 名称: <font color="red">*</font></label></td>
        <td class="value"><input id="mc" name="mc" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.mc}' datatype="*1-100"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">名称</label></td>
        <td align="right"><label class="Validform_label"> 发明人: <font color="red">*</font></label></td>
        <td class="value"><input id="fmr" name="fmr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.fmr}' datatype="*1-250"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">发明人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 第一发明人<br/>身份证号: <font color="red">*</font></label></td>
        <td class="value"><input id="dyfmrsfzh" name="dyfmrsfzh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.dyfmrsfzh}' datatype="idcard"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一发明人身份证号</label></td>
        <td align="right"><label class="Validform_label"> 代理机构: <font color="red">*</font></label></td>
        <td class="value">
        <input id="dljgId" name="dljgId" type="hidden" value='${tZZlsqPage.dljgId}' datatype="*">
        <input id="dljgName" type="text" style="width:156px;">
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">代理机构</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 联系人: <font color="red">*</font></label></td>
        <td class="value"><input id="lxr" name="lxr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxr}' datatype="*1-250"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人</label></td>
        <td align="right"><label class="Validform_label"> 联系人电话: <font color="red">*</font></label></td>
        <td class="value"><input id="lxrdh" name="lxrdh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxrdh}' datatype="*1-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人电话</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="bz" style="width: 460px;" class="inputxt" rows="3" name="bz" datatype="byterange" min="0" max="4000">${tZZlsqPage.bz}</textarea> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">备注</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td colspan="3" class="value"><input type="hidden" value="${tZZlsqPage.fjbm }" id="bid" name="fjbm" />
        <span style="color:red;font-size: 10px;">1、海军工程大学专利申请保密审批表-纸质版<br/>2、技术交底书</span>
          <table style="max-width: 360px;" id="fileShow">
            <c:forEach items="${tZZlsqPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFilesToFTP&businessType=zlsq" dialog="false" auto="true"
              onUploadSuccess="uploadSuccess"></t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
  </div>
  <c:if test="${!empty tZZlsqPage.id}">
  <div style="height:250px;width: 680px;margin: 0 auto;">
  <t:datagrid name="tZSqrxxList" checkbox="true" fitColumns="false" title="申请人信息" actionUrl="tZSqrxxController.do?datagrid&zlsqId=${tZZlsqPage.id}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="xm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="dh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="居民身份证件号码"  field="idno" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电子邮箱"  field="dzyx"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="国籍"  field="gj"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="居所地"  field="jsd"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="邮政编码"  field="yzbm" hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="详细地址"  field="xxdz"  hidden="true"   queryMode="group"  width="120" overflowView="true"></t:dgCol>
   <t:dgCol title="所属部门"  field="ssbm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${opt ne 'view'}">
   <t:dgDelOpt title="删除" url="tZSqrxxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tZSqrxxController.do?goUpdate&zlsqId=${tZZlsqPage.id}" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tZSqrxxController.do?goUpdate" funname="update"></t:dgToolBar>
   </c:if>
  </t:datagrid>
  </div>
  </div>
  </c:if>
</body>
<script src="webpage/com/kingtake/zscq/zlsq/tZZlsq.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script> 
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>