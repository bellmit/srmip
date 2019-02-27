<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>TD_KYCG</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tdKycgController.do?doUpdate" tiptype="1">
    <input id="cgdm" name="cgdm" type="hidden" value='${tdKycgPage.cgdm}'>
    <table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 基层编号: </label></td>
        <td class="value"><input id="jcbh" name="jcbh" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jcbh}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">基层编号</label></td>
        <td align="right"><label class="Validform_label"> 成果名称: </label></td>
        <td class="value"><input id="cgmc" name="cgmc" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.cgmc}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">成果名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 完成单位: </label></td>
        <td class="value"><t:departComboTree id="wcdw" name="wcdw" lazy="false" width="157" value="${tdKycgPage.wcdw}"></t:departComboTree>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">完成单位</label></td>
        <td align="right"><label class="Validform_label"> 项目组联系人: </label></td>
        <td class="value"><input id="xmzlxr" name="xmzlxr" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.xmzlxr}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">项目组联系人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 项目组联系方式: </label></td>
        <td class="value"><input id="xmzlxrs" name="xmzlxrs" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.xmzlxrs}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">项目组联系方式</label></td>
        <td align="right"><label class="Validform_label"> 鉴定主持单位: </label></td>
        <td class="value"><input id="jglxr" name="jglxr" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jglxr}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">鉴定主持单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 鉴定主持人: </label></td>
        <td class="value"><input id="jglxfs" name="jglxfs" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jglxfs}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">鉴定主持人</label></td>
        <td align="right"><label class="Validform_label"> 鉴定时间: </label></td>
        <td class="value"><input id="jdsj" name="jdsj" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.jdsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">鉴定时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 鉴定地点: </label></td>
        <td class="value"><input id="jddd" name="jddd" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jddd}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">鉴定地点</label></td>
        <td align="right"><label class="Validform_label"> 鉴定形式: </label></td>
        <td class="value"><input id="jdxs" name="jdxs" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jdxs}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">鉴定形式</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 当前状态: </label></td>
        <td class="value"><input id="cgzt" name="cgzt" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.cgzt}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">当前状态</label></td>
        <td align="right"><label class="Validform_label"> 申请日期: </label></td>
        <td class="value"><input id="sqrq" name="sqrq" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.sqrq}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">申请日期</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 归档号: </label></td>
        <td class="value"><input id="gdh" name="gdh" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.gdh}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">归档号</label></td>
        <td align="right"><label class="Validform_label"> 审查日期: </label></td>
        <td class="value"><input id="scrq" name="scrq" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.scrq}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">审查日期</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 申请上报日期: </label></td>
        <td class="value"><input id="sbrq" name="sbrq" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.sbrq}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">申请上报日期</label></td>
        <td align="right"><label class="Validform_label"> 鉴定审批号: </label></td>
        <td class="value"><input id="jdsph" name="jdsph" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.jdsph}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">鉴定审批号</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 通知编号: </label></td>
        <td class="value"><input id="tzbh" name="tzbh" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.tzbh}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">通知编号</label></td>
        <td align="right"><label class="Validform_label"> 成果上报日期: </label></td>
        <td class="value"><input id="clsbrq" name="clsbrq" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.clsbrq}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">成果上报日期</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 水平评价: </label></td>
        <td class="value"><input id="sppj" name="sppj" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.sppj}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">水平评价</label></td>
        <td align="right"><label class="Validform_label"> 证书编号: </label></td>
        <td class="value"><input id="zsbh" name="zsbh" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.zsbh}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">证书编号</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 第一完成人: </label></td>
        <td class="value"><input id="wcr" name="wcr" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.wcr}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">第一完成人</label></td>
        <td align="right"><label class="Validform_label"> 证书领取人: </label></td>
        <td class="value"><input id="zslqr" name="zslqr" type="text" style="width: 150px" class="inputxt" value='${tdKycgPage.zslqr}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">证书领取人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 证书领取日期: </label></td>
        <td class="value"><input id="zslqrq" name="zslqrq" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tdKycgPage.zslqrq}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">证书领取日期</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="bz" style="width: 600px;" class="inputxt" rows="6" name="bz">${tdKycgPage.bz}</textarea> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">备注</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/kycg/tdKycg.js"></script>