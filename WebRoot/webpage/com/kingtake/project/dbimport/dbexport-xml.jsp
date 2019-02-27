<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>XML数据包导出</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
    $(function(){
    	var myDate = new Date();
    	var year = myDate.getFullYear();
    	$("#year").val(year);
    })

    //提交前检验
    function checkData() {    
    	
    }
    
    //回调
        function uploadCallback(data){
        	var win = frameElement.api.opener;
            if (data.success == true) {                
                win.tip(data.msg + ",交互编号为："+data.obj);                
                win.$('#dbImportList').datagrid('reload'); 
                win.ExportXmlToCwNew();      
                frameElement.api.close();        
            }
    }
    
</script>
</head>
<body>
  <div>
    <t:formvalid formid="formobj" dialog="true" layout="table" action="dbImportController.do?exportXmlToCw" callback="@Override uploadCallback" tiptype="1" beforeSubmit="checkData">
      <table id="documentInfo" style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right" ><label class="Validform_label"> 年度: </label> <font color="red">*</font></td>
          <td class="value">
          <input id="year" name="year" type="text" datatype="*1-4" style="width: 250px" readonly="readonly">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">年度</label></td>          
        </tr>                          
      </table>      
    </t:formvalid>
  </div>
</body>