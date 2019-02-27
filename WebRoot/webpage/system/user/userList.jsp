<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<%--update-start--Author:zhangguoming  Date:20140827 for：添加 组织机构查询条件--%>
<script>
    $(function() {
        var datagrid = $("#userListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
    });
</script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="<t:mutiLang langKey="common.department"/>">
                <t:mutiLang langKey="common.department"/>：
            </span>
            <input id="orgIds" name="orgIds" type="hidden">
            <input readonly="true" type="text" name="departname" style="width: 100px" onclick="choose_297e201048183a730148183ad85c0001()"/>
            <%--<t:choose hiddenName="orgIds" hiddenid="id"--%>
                      <%--url="departController.do?departSelect" name="departList"--%>
                      <%--icon="icon-search" title="common.department.list"--%>
                      <%--textname="departname" isclear="true"></t:choose>--%>
        </span>
         <%--add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件  出生日期--%>
        <span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="出生日期 ">出生日期:</span>
            <input type="text" name="birthday_begin" style="width: 100px; height: 24px;">~
            <input type="text" name="birthday_end" style="width: 100px; height: 24px; margin-right: 20px;">
        </span>
    </div>
</div>
<%--update-end--Author:zhangguoming  Date:20140827 for：添加 组织机构查询条件--%>

<t:datagrid name="userList" title="人员列表" actionUrl="userController.do?datagrid" 
	fit="true" fitColumns="true" idField="id" queryMode="group" extendParams="nowrap:false,">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true" 
		isLike="true" width="100"></t:dgCol>
    <%--update-start--Author:zhangguoming  Date:20140827 for：通过用户对象的关联属性值获取组织机构名称（多对多关联）--%>
	<%--<t:dgCol title="common.department" field="TSDepart_id" query="true" replace="${departsReplace}"></t:dgCol>--%>
	<t:dgCol title="common.department" field="userOrgList.tsDepart.departname" 
		sortable="false" query="false" width="100"></t:dgCol>
    <%--update-end--Author:zhangguoming  Date:20140827 for：通过用户对象的关联属性值获取组织机构名称（多对多关联）--%>
	<t:dgCol title="common.real.name" field="realName" query="true" isLike="true" width="100"></t:dgCol>
    <t:dgCol title="姓名拼音" sortable="false" field="namePinyin" width="100"></t:dgCol>
    <t:dgCol title="性别" sortable="false" field="sex" queryMode="group" codeDict="0,SEX" width="100"></t:dgCol>
	<t:dgCol title="出生日期" sortable="false" field="birthday"  formatter="yyyy-MM-dd" width="100"></t:dgCol>
    <t:dgCol title="证件号码" sortable="false" field="idNum" width="100"></t:dgCol>
    <t:dgCol title="政治面貌" sortable="false" field="politicalStatus" codeDict="0,POLITICALSTATUS" width="100"></t:dgCol>
    <t:dgCol title="籍贯" sortable="false" field="nativePlace" queryMode="group" width="100"></t:dgCol>
    <t:dgCol title="民族" sortable="false" field="nation" codeDict="0,NATION" width="100"></t:dgCol>
	<t:dgCol title="common.role" field="userKey" width="100"></t:dgCol>
    <t:dgCol title="common.phone" sortable="false" field="mobilePhone" width="100"></t:dgCol>
    <t:dgCol title="common.tel" sortable="false" field="officePhone" width="100"></t:dgCol>
    <t:dgCol title="common.common.mail" sortable="false" field="email" width="100"></t:dgCol>
    <t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1" width="100"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="common.delete" url="userController.do?del&id={id}&userName={userName}" />
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" 
		url="userController.do?addorupdate" funname="add" width="1050" height="550"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" 
		url="userController.do?addorupdate" funname="update" width="1050" height="550"></t:dgToolBar>
	<t:dgToolBar title="common.password.reset" icon="icon-edit" width="450" height="100"
		url="userController.do?changepasswordforuser" funname="update"></t:dgToolBar>
	<t:dgToolBar title="common.lock.user" icon="icon-edit" url="userController.do?lock" 
		funname="lockObj"></t:dgToolBar>
	<%-- <t:dgToolBar title="初始化用户信息" icon="icon-reload" url="userController.do?initDate" 
    funname="initDate"></t:dgToolBar> --%>
</t:datagrid>
<script type="text/javascript">

function initDate(title,url){
    var password =  "123456";
    $.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {"password":password},
		url : url,// 请求的action路径
		success : function(msg) {
				alert(msg);
				reloadTable();
		}
	});
}

function lockObj(title,url, id) {

	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('<t:mutiLang langKey="please.select.lock.item"/>');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('<t:mutiLang langKey="is.confirm"/>', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}


function lockuploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
			}
			
		}
	});
}

$(document).ready(function(){
    $("input[name='birthday_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("input[name='birthday_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

    $("input").css("height", "24px");
});
</script>

<%--update-start--Author:zhangguoming  Date:20140827 for：添加 组织机构查询条件：弹出 选择组织机构列表 相关操作--%>
<%--<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onClick="choose_297e201048183a730148183ad85c0001()">选择</a>--%>
<%--<a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" onClick="clearAll_297e201048183a730148183ad85c0001();">清空</a>--%>
<script type="text/javascript">
//    var windowapi = frameElement.api, W = windowapi.opener;
    function choose_297e201048183a730148183ad85c0001() {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, parent: windowapi, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
    function clearAll_297e201048183a730148183ad85c0001() {
        if ($('#departname').length >= 1) {
            $('#departname').val('');
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val('');
            $("input[name='departname']").blur();
        }
        $('#orgIds').val("");
    }
    function clickcallback_297e201048183a730148183ad85c0001() {
        iframe = this.iframe.contentWindow;
        var departname = iframe.getdepartListSelections('departname');
        if ($('#departname').length >= 1) {
            $('#departname').val(departname);
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val(departname);
            $("input[name='departname']").blur();
        }
        var id = iframe.getdepartListSelections('id');
        if (id !== undefined && id != "") {
            $('#orgIds').val(id);
            $("input[name='orgIds']").val(id);
        }
    }
</script>
<%--update-end--Author:zhangguoming  Date:20140827 for：添加 组织机构查询条件：弹出 选择组织机构列表 相关操作--%>
