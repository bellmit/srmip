<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        function setOrgIds() {
            var orgIds = $("#orgSelect").combotree("getValues");
            $("#orgIds").val(orgIds);
            if(orgIds=="" || orgIds==null){
            	var tip = $("#orgIds").next(".Validform_checktip");
             	tip.text("请至少选择一项!");
             	tip.addClass("Validform_wrong");
             	return false;
           }
        }
        
        $(function(){
            var orgIds = $("#orgIds").val();
            if(orgIds!=""){
                $("#orgSelect").combotree("setValues",orgIds.split(","));
            }
            
            $("#realName").blur(function(){
                fillPinyin();
            });
            
            $("#namePinyin").blur(function(){
                fillPinyin();
            });
            
        });
        
        //填入姓名拼音
        function fillPinyin(){
            var userName = $("#realName").val();
    		var namePinyin = $("#namePinyin").val();
    		if(userName!="" && (namePinyin==""||namePinyin==null)){
        		$.ajax( {
        			url : "userController.do?getUsernamePinyin",
        			type : "POST",
        			dataType : "json",
        			data : {"userName":userName},
        			async : false,
        			cache : false,
        			success : function(data) {
        				   $("#namePinyin").val(data);
        			}
        		});
    		}
        }
        
    </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
	action="userController.do?saveUser" beforeSubmit="setOrgIds">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="10%" nowrap>
                <label class="Validform_label">  <t:mutiLang langKey="common.username"/>:<font color="red">*</font> </label>
            </td>
			<td class="value" width="40%">
                <c:if test="${user.id!=null }"> ${user.userName } </c:if>
                <c:if test="${user.id==null }">
                    <input id="userName" class="inputxt" name="userName" validType="t_s_base_user,userName,id" value="${user.userName }" datatype="s2-20" />
                   <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                   <label class="Validform_label" style="display:none;">用户名</label>
                </c:if>
            </td>
            <td align="right" width="10%" nowrap><label class="Validform_label"> <t:mutiLang langKey="common.real.name"/>:<font color="red">*</font> </label></td>
			<td class="value" width="40%">
                <input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="byterange" max="36" min="2" errormsg="">
                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
                <label class="Validform_label" style="display:none;">真实姓名</label>
            </td>
		</tr>
		<c:if test="${user.id==null }">
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>:<font color="red">*</font> </label></td>
				<td class="value">
                    <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*6-18"  sucmsg="验证通过"/>
                   <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                    <label class="Validform_label" style="display:none;">密码</label>
                </td>
                <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>:<font color="red">*</font> </label></td>
				<td class="value">
                    <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
                    <span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
                    <label class="Validform_label" style="display:none;">填写确认密码</label>
                </td>
			</tr>
		</c:if>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.department"/>:<font color="red">*</font> </label></td>
			<td class="value">
                <select class="easyui-combotree" data-options="url:'departController.do?setPFunction&lazy=false', multiple:true, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" >
                </select>
                <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}">
                <span class="Validform_checktip"><t:mutiLang langKey="please.select.department"/></span>
                <label class="Validform_label" style="display:none;">组织机构</label>
            </td>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.role"/>:<font color="red">*</font> </label></td>
			<td class="value" nowrap>
                <input name="roleid" name="roleid" type="hidden" value="${id}" id="roleid">
                <input name="roleName" class="inputxt" value="${roleName }" id="roleName" readonly="readonly" datatype="*" sucmsg="验证通过"/>
                <t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                          icon="icon-search" title="common.role.list" textname="roleName" isclear="true"></t:choose>
                <span class="Validform_checktip"><t:mutiLang langKey="role.muti.select"/></span>
                <label class="Validform_label" style="display:none;">角色</label>
            </td>
		</tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">手机号码</label>
            </td>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="officePhone" value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">办公室电话</label>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label">  证件号码: </label></td>
            <td class="value">
                <input class="inputxt" name="idNum" value="${user.idNum}"  ignore="ignore" errormsg="身份证号不正确" >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">证件号码</label>
            </td>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">邮箱</label>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label">  登录别名: </label></td>
            <td class="value">
                <input class="inputxt" name="aliasname" value="${user.aliasname}" validType="t_s_base_user,aliasname#username,id"  errormsg="" datatype="/(^[a-zA-Z0-9]{0,15}$)/">
                <span class="Validform_checktip">由字母或数字组成长度不大于15</span>
                <label class="Validform_label" style="display:none;">登录别名</label>
            </td>
    		      <input type="hidden" name="activitiSync" value="1">
        </tr>
	</table>

 <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA; ">
      <legend ><span style="color: #A52A2A;font-size: 12px;font-weight: bold;">基本信息</span></legend>
	<table cellpadding="0" cellspacing="1" class="formtable">
	     <tr>
			<td align="right" width="10%" nowrap><label class="Validform_label">姓名拼音: </label></td>
			<td class="value" width="40%">
                <input id="namePinyin" class="inputxt" name="namePinyin" value="${user.namePinyin}"  >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">姓名拼音</label>
            </td>
			<td align="right" width="10%" nowrap><label class="Validform_label">曾用名: </label></td>
			<td class="value" width="40%">
                <input class="inputxt" name="nameUsed" value="${user.nameUsed}"  >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">曾用名</label>
            </td>
		</tr>
		<tr>
		    <td align="right"><label class="Validform_label"> 性别: </label></td>
			<td class="value">
                <t:codeTypeSelect name="sex" type="select" codeType="0" code="SEX" id="sexSelect"  defaultVal="${user.sex}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">性别</label>
            </td>
            <td align="right"><label class="Validform_label"> 出生日期: </label></td>
			<td class="value">
                <input name="birthday" value="${user.birthdayStr}" formatter="yyyy-MM-dd" class="Wdate"
                    onClick="WdatePicker()" style="width: 156px;">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">出生日期</label>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> 是否编制: </label></td>
            <td class="value">
                <t:codeTypeSelect name="authorizedFlag" type="select" codeType="0" code="SFBZ" id="authorizedFlagSelect"  defaultVal="${user.authorizedFlag}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">是否编制</label>
            </td>
            <td align="right"><label class="Validform_label"> 是否干部: </label></td>
            <td class="value">
                <t:codeTypeSelect name="leaderFlag" type="select" codeType="0" code="SFBZ" id="leaderFlagSelect" defaultVal="${user.leaderFlag}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">是否干部</label>
            </td>
        </tr>
		<tr>
		    <td align="right"><label class="Validform_label"> 政治面貌: </label></td>
			<td class="value">
                <t:codeTypeSelect name="politicalStatus" type="select" codeType="0" code="POLITICALSTATUS" id="politicalStatusSelect"  defaultVal="${user.politicalStatus}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">政治面貌</label>
            </td>
            <td align="right"><label class="Validform_label"> 职务: </label></td>
			<td class="value">
                <t:codeTypeSelect name="duty" type="select" codeType="0" code="PROFESSIONAL" id="dutySelect"  labelText="---请选择---" defaultVal="${user.duty}"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">职务</label>
            </td>
		</tr>
		<tr>
		    <td align="right"><label class="Validform_label"> 籍贯: </label></td>
			<td class="value">
                <input id="nativePlace" name="nativePlace" type="text" value="${user.nativePlace}" style="width:150px;">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">籍贯</label>
            </td>
            <td align="right"><label class="Validform_label"> 民族: </label></td>
			<td class="value">
                <t:codeTypeSelect name="nation" type="select" codeType="0" code="NATION" id="nationSelect"  defaultVal="${user.nation}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">民族</label>
            </td>
		</tr>
		<tr>
		    <td align="right"><label class="Validform_label"> 是否军人: </label></td>
			<td class="value">
                <t:codeTypeSelect name="soldierFlag" type="select" codeType="0" code="SFBZ" id="soldierFlagSelect"  defaultVal="${user.soldierFlag}" labelText="---请选择---"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">是否军人</label>
            </td>
            <td align="right"><label class="Validform_label"> 军衔: </label></td>
			<td class="value">
                <t:codeTypeSelect name="rank" type="select" codeType="0" code="RANK" labelText="---请选择---" id="rankSelect"  defaultVal="${user.rank}"></t:codeTypeSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display:none;">军衔</label>
            </td>
		</tr>
		<tr>
    		<td align="right"><label class="Validform_label">  军官证号: </label></td>
                <td class="value">
                    <input class="inputxt" name="officerNum" value="${user.officerNum}" >
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display:none;">军官证号</label>
                </td>    
            <td align="right"><label class="Validform_label"> 排序: </label></td>
    			<td class="value">
                    <input class="inputxt" name="sortCode" value="${user.sortCode}" datatype="n" ignore="ignore">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display:none;">排序</label>
                </td>
		</tr>
	</table>
     </fieldset>
	</t:formvalid>
</body>