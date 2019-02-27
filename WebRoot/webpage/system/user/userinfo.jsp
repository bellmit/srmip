<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools,easyui"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
  <div id="viewDiv">
      <t:formvalid formid="formobj1" layout="div" dialog="true" >
      <fieldset class="step">
      <legend>
      <span><t:mutiLang langKey="common.userinfo" />
      <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" style="float: right;margin-right: 10px;"  onclick="doEdit()" >编辑</a>
      </span>
      </legend>
        <div class="form">
          <label class="form">
            用户名:
          </label>
          ${user.userName }
        </div>
        <div class="form">
          <label class="form">
            姓名:
          </label>
          ${user.realName}
        </div>
        <div class="form">
          <label class="form"> 部门: </label>
          ${user.currentDepart.departname}
        </div>
        <div class="form">
          <label class="form">
            手机号码:
          </label>
          ${user.mobilePhone}
        </div>
        <div class="form">
          <label class="form">办公电话: </label>
          ${user.officePhone}
        </div>
        <div class="form">
          <label class="form">邮箱: </label>
          ${user.email}
        </div>
        </fieldset>
      </t:formvalid> 
  </div>
  <div id="editDiv" style="display: none;">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="userController.do?saveUserInfo">
    <input type="hidden" name="id" id="id" value="${user.id }">
    <input type="hidden" id="odepartId" name="odepartId" value="${user.currentDepart.id}">
    <fieldset class="step">
      <legend>
      <span>
        <t:mutiLang langKey="common.userinfo" />
     <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  id="saveBtn" style="display: none;float: right;margin-right: 10px;" onclick="doSave()" >保存</a>
     </span>
      </legend>
        <div class="form">
          <label class="form">
            用户名:
          </label>
          ${user.userName }
        </div>
        <div class="form">
          <label class="form">
            姓名:
          </label>
          ${user.realName}
        </div>
        <div class="form">
          <label class="form"> 部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门: </label>
          <t:departComboTree id="departId" name="departId" nameInput="departName" lazy="false" value="${user.currentDepart.id}"  width="173"></t:departComboTree>
          <input id="departName" name="departName" type="hidden" value='${user.currentDepart.departname}'>
          <%-- <input id="departId" name="departId" type="hidden" value='${user.currentDepart.id}'> --%>
        </div>
        <div class="form">
          <label class="form">
            手机号码:
          </label>
          <input id="mobilePhone" name="mobilePhone" type="text" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
        </div>
        <div class="form">
          <label class="form">办公电话: </label>
          <input id="officePhone" name="officePhone" type="text" value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
        </div>
        <div class="form">
          <label class="form">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱: </label>
          <input id="email" name="email" type="text" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
        </div>
       </fieldset>
    </t:formvalid>
  </div>
</body>
<script type="text/javascript">
    function doEdit() {
        $("#viewDiv").hide();
        $("#editDiv").show();
        $("#saveBtn").show();
    }
    
    function doSave() {
        var odepartId = $("#odepartId").val();
        var ndepartId = $("#departId").combobox("getValue");
        if(odepartId!=ndepartId){
            $.messager.confirm('确认','部门发生调动需要机关审批，确定提交吗？',function(r){    
                if (r){    
                    $("#formobj").find("#btn_sub").click();    
                }    
            }); 
        }else{
            $("#formobj").find("#btn_sub").click();    
        }
    }
    
</script>
</html>

