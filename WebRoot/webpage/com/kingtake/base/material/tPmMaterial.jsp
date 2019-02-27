<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>原材料</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  function reloadMaterial(data){
		frameElement.api.opener.reloadMaterial();
		frameElement.api.opener.showMsg(data.msg);
	    frameElement.api.close();
	}
  </script>
 </head>
 
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmMaterialController.do?doSaveOrUpdate" tiptype="1" callback="@Override reloadMaterial">
		<input id="id" name="id" type="hidden" value="${tPmMaterial.id }">
        
		<table cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right"><label class="Validform_label">名称:</label><font color="red">*</font></td>
                <td class="value">
                  <input id="materialName" name="materialName" type="text" style="width: 150px" class="inputxt"
                 	value='${tPmMaterial.materialName}' datatype="*">
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">名称</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">规格、型号:</label></td>
                <td class="value">
                  <input id="materialModel" name="materialModel" type="text" style="width: 150px" class="inputxt"
                 	value='${tPmMaterial.materialModel}'>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">规格型号</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">生产厂家:</label></td>
                <td class="value">
                  <input id="materialFactory" name="materialFactory" type="text" style="width: 150px" class="inputxt"
                 	value='${tPmMaterial.materialFactory}'>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">生产厂家</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">计量单位:</label></td>
                <td class="value">
                  <input id="materialFactory" name="materialFactory" type="text" style="width: 150px" class="inputxt"
                 	value='${tPmMaterial.materialFactory}'>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">计量单位</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">单价:</label></td>
                <td class="value">
                  <input id="materialPrice" name="materialPrice" type="text" style="width: 150px" 
                  	class="easyui-numberbox inputxt" data-options="min:0, precision:2"
                 	value='${tPmMaterial.materialPrice}'>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">单价</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">材料种类:</label></td>
                <td class="value">
                <t:codeTypeSelect name="materiaType" type="select" codeType="1" code="CLZL" id="materiaType" defaultVal="${tPmMaterial.materiaType}" labelText="请选择" ></t:codeTypeSelect>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">材料种类</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">采购单位:</label></td>
                <td class="value">
                  <input id="purchaseDept" name="purchaseDept" type="text" style="width: 150px" 
                  value='${tPmMaterial.purchaseDept}'>
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">采购单位</label>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">采购时间:</label></td>
                <td class="value">
                  <input id="purchaseTime" name="purchaseTime" type="text" style="width: 150px" class="Wdate" 
                  value="<fmt:formatDate value='${tPmMaterial.purchaseTime}' type='both' pattern='yyyy-MM-dd'/>" 
                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                  <span class="Validform_checktip"></span>
                  <label class="Validform_label" style="display: none;">采购时间</label>
                </td>
            </tr>
		</table>
	</t:formvalid>
 </body>
