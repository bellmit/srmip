<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专家信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    //编写自定义JS代码

    $(function() {
        //加载单位树下拉框
        $("#selectDepart").combotree({
            url : 'departController.do?getDepartTree',
            width : 155,
            editable : true,
            onSelect : function(rec) {
                $('#expertDepartId').val(rec.id);
                $('#expertDepartName').val(rec.text);
            }
        });

        $("#name").blur(function() {
            var name = $("#name").val();
            $.ajax({
                url : "userController.do?getUsernamePinyin",
                type : "POST",
                dataType : "json",
                data : {
                    "userName" : name
                },
                async : false,
                cache : false,
                success : function(data) {
                    $("#userName").val(data);
                    $("#userPwd").val("123456");
                }
            });
        });
        
        $("#expertProfession").combotree({
    	   	url : 'tBCodeTypeController.do?typeComboTree&codeType=1&code=MAJOR',
    		valueField : 'CODE',
    		textField : 'NAME',
    		onLoadSuccess : function() {
    			var projectType = $("#expertProfession").val();
    			if (projectType != "") {
    				$("#expertProfession").combobox('setValue', projectType);
    			} 
    		}
    	});

        //给单位树下拉框赋值
        $("#selectDepart").combotree("setValue", "${tErExpertPage.expertDepartName}");
        
         //给单位树下拉框赋值
        $("#expertProfession").combotree("setValue", "${tErExpertPage.expertProfession}");
    });

    //提交之前将单位名称可选可输的值 验证 + 记入相应的input标签中
    function setExpertDepartName() {
        var departName = $("#selectDepart").combotree("getText");
        var reg = /^[\w\W]{1,30}$/;
        if (reg.test(departName)) {
            $("#expertDepartName").val(departName);
        } else {
            $.Showmsg('请选择单位！');
            return false;
        }
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErExpertController.do?doUpdate" tiptype="1" tipSweep="true" beforeSubmit="setExpertDepartName">
    <input id="id" name="id" type="hidden" value="${tErExpertPage.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="expertNum" name="expertNum" type="text" style="width: 150px" class="inputxt" datatype="*1-10"
            ajaxurl="tErExpertController.do?validformExpertNum&id=${tErExpertPage.id}" value="${tErExpertPage.expertNum}"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">编号</label></td>
        <td align="right"><label class="Validform_label">
            姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*1-50" value="${tErExpertPage.name}"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">姓名</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            登录账号:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="userName" name="userName" type="text" style="width: 150px" class="inputxt" datatype="s1-30"
            ajaxurl="tErExpertController.do?validformExpertUserName&id=${tErExpertPage.id}" value="${tErExpertPage.userName}"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">登录账号</label></td>
        <%-- <td align="right">
            <label class="Validform_label">
                                  登录密码:<font color="red">*</font>
            </label>
          </td>
          <td class="value">
              <input id="userPwd" name="userPwd" type="text" style="width: 150px" class="inputxt" datatype="*1-50" value="${tErExpertPage.userPwd}">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">登录密码</label>
          </td> --%>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:&nbsp;&nbsp; </label></td>
        <td class="value"><t:codeTypeSelect id="sex" name="sex" type="select" codeType="0" code="SEX" defaultVal="${tErExpertPage.sex}" labelText="请选择"></t:codeTypeSelect> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">性别</label></td>
        <td align="right"><label class="Validform_label"> 出生年月:&nbsp;&nbsp; </label></td>
        <td class="value"><input id="expertBirthDate" name="expertBirthDate" type="text" style="width: 150px" datatype="date" ignore="ignore" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value="${tErExpertPage.expertBirthDate }" type="date" pattern="yyyy-MM-dd" />'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">出生年月</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            证件类型:<font color="red">*</font>
          </label></td>
        <td class="value"><t:codeTypeSelect id="idType" name="idType" type="select" codeType="0" code="SFZJLX" defaultVal="${tErExpertPage.idType }"></t:codeTypeSelect> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">身份证件类型</label></td>
        <td align="right"><label class="Validform_label">
            证件号码:
          </label></td>
        <td class="value"><input id="idNo" name="idNo" type="text" style="width: 150px" class="inputxt" datatype="*1-20" ignore="ignore" value="${tErExpertPage.idNo }"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">证件号码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:<font color="red">*</font>
        </label></td>
        <td class="value"><input id="expertDepartName" name="expertDepartName" type="hidden" value="${tErExpertPage.expertDepartName}"> <input id="expertDepartId" name="expertDepartId"
          type="hidden" value="${tErExpertPage.expertDepartId}"> <input id="selectDepart" type="text"> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">单位名称</label></td>
        <td align="right"><label class="Validform_label"> 专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业: </label></td>
        <td class="value"><input id="expertProfession" name="expertProfession" style="width: 155px;"><span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">专业</label></td>     
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:<font color="red">*</font>
          </label></td>
        <td class="value"><t:codeTypeSelect id="expertPosition" name="expertPosition" type="select" codeType="1" code="ZHCH" defaultVal="${tErExpertPage.expertPosition}" labelText="请选择" extendParam="{'datatype':'*'}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">职称</label></td>
        <td align="right"><label class="Validform_label"> 专业组: </label></td>
        <td class="value"><input id="officialGroup" name="officialGroup" type="text" style="width: 150px" value="${tErExpertPage.officialGroup }"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">专业组</label></td>
        <%-- <td align="right"><label class="Validform_label">
            执业资格:<font color="red">*</font>
          </label></td>
        <td class="value"><t:codeTypeSelect id="expertPraciticReq" name="expertPraciticReq" type="select" codeType="1" code="ZYZG" defaultVal="${tErExpertPage.expertPraciticReq}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">执业资格</label></td> --%>
      </tr>
      <tr>
        <%-- <td align="right">
						<label class="Validform_label">
							地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区:&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<t:codeTypeSelect id="expertDistrict" name="expertDistrict" type="select" codeType="0" code="NATIVEPLACE"
				     		defaultVal="${tErExpertPage.expertDistrict }"></t:codeTypeSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">地区</label>
					</td> --%>
        <td align="right"><label class="Validform_label"> 电话/手机: </label></td>
        <td class="value"><input id="expertPhone" name="expertPhone" type="text" style="width: 150px" class="inputxt" datatype="*1-20" ignore="ignore" value="${tErExpertPage.expertPhone }">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">电话/手机</label></td>
        <td align="right"><label class="Validform_label"> 座机: </label></td>
        <td class="value"><input id="workPhone" name="workPhone" type="text" style="width: 150px" class="inputxt" datatype="*1-50" ignore="ignore" value="${tErExpertPage.workPhone }"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">座机</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 是否正式专家: </label></td>
        <td class="value"><t:codeTypeSelect id="isOfficial" name="isOfficial" type="select" codeType="0" code="SFBZ" defaultVal="${tErExpertPage.isOfficial }"></t:codeTypeSelect> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否正式专家</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介:&nbsp;&nbsp; </label></td>
        <td class="value" colspan="3"><textarea id="expertSummary" name="expertSummary" style="width: 440px; height: 60px;" datatype="*1-2000" ignore="ignore">${tErExpertPage.expertSummary}</textarea>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">简介</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/expert/info/tErExpert.js"></script>