<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.kingtake.common.constant.DepartConstant" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
    	<input id="scientificInstitutionFlag" type="hidden" value="${scientificInstitutionFlag}"> 
        <t:datagrid  name="departList"  title="common.department.list"
                    actionUrl="departController.do?departgrid&scientificInstitutionFlag=${scientificInstitutionFlag}"
                    treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.department.name" field="departname" treefield="text"></t:dgCol>
            <t:dgCol title="parent.depart" field="TSPDepart.departname" treefield="parentText" hidden="true"></t:dgCol>
            <t:dgCol title="机构简称" field="shortname" treefield="fieldMap.shortname"></t:dgCol>
            <t:dgCol title="position.desc" field="description" treefield="src" hidden="true"></t:dgCol>
            <t:dgCol title="common.org.code" field="orgCode" treefield="fieldMap.orgCode" hidden="true"></t:dgCol>
            <%-- <t:dgCol title="common.org.type" field="orgType" dictionary="orgtype" treefield="fieldMap.orgType"></t:dgCol> --%>
            <%-- <t:dgCol title="学科方向编码" field="subjectDirectionCode" treefield="fieldMap.subjectDirectionCode"></t:dgCol> --%>
            <t:dgCol title="学科方向" field="subjectDirectionName"  treefield="fieldMap.subjectDirectionName" hidden="true"></t:dgCol>
            <t:dgCol title="主管部门" field="manager_depart" treefield="fieldMap.manager_depart" hidden="true"></t:dgCol>
            <t:dgCol title="负责人" field="leader_official" treefield="fieldMap.leader_official" hidden="true"></t:dgCol>
            <t:dgCol title="排序码" field="sortCode" treefield="order"></t:dgCol>
 			<t:dgCol title="有效标记" field="validFlag"  treefield="fieldMap.validFlag" dictionary="validStatus" width="60"  hidden="true"></t:dgCol>
    		<t:dgCol title="是否科研机构" field="scientificInstitutionFlag" treefield="fieldMap.scientificInstitutionFlag" codeDict="0,SFBZ" ></t:dgCol><!--  queryMode="group" dictionary="scientificInstitutionFlag" -->
<%--    			<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol> --%>
<%--             <t:dgToolBar title="机构录入" icon="icon-add" funname="addOrg"></t:dgToolBar> --%>
<%--             <t:dgToolBar title="机构编辑"  icon="icon-edit" url="departController.do?update" width="675" height="530" funname="update"></t:dgToolBar> --%>
<%--             <t:dgDelOpt url="departController.do?del&id={id}" title="common.delete" ></t:dgDelOpt> --%>
<%--             <t:dgFunOpt funname="queryUsersByDepart(id)" title="view.member"></t:dgFunOpt> --%>
<%--             <t:dgFunOpt funname="setRoleByDepart(id,text)" title="role.set"></t:dgFunOpt> --%>
        </t:datagrid>
    </div>
    </div>
<%-- <div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div> --%>
</div>
<script type="text/javascript">

//update-start--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
 	//scientificInstitutionFlag 是否科研组织机构   1：是   0：否 
	var flag=$("#scientificInstitutionFlag").val();
    $(function() {
        var li_east = 0;
        if(flag==<%=DepartConstant.SCIENCE_INSTITU%>){
            //设置列表的标题
            $("#departList").datagrid({
                title:'科研机构列表'
            });
            //隐藏列treefield="parentText"
            $("#departList").datagrid('showColumn',"parentText");
        }
        
    });
    
    function addOrg() {
        var id = "";
        var rowFlag = "";
        var rowsData = $('#departList').datagrid('getSelections');
        //选中行，且不是科研机构时，获取选中行的ID
        if (rowsData.length >= 1 && flag!=<%=DepartConstant.SCIENCE_INSTITU%>) {
            id = rowsData[0].id;
	        //获取选中行科研机构标志
    	    rowFlag = rowsData[0]["fieldMap.scientificInstitutionFlag"];
        }
        var url = "departController.do?add&TSPDepart.id=" + id+"&scientificInstitutionFlag="+flag;
        
        //如果当前窗口是组织机构窗口，点击科研机构禁用新增按钮
        if(flag ==<%=DepartConstant.ORGANIZE_INSTITU%> &&
                rowFlag == <%=DepartConstant.SCIENCE_INSTITU%>){
            $("a[icon='icon-edit']").attr('disabled',"true");
    	    tip('科研机构下不能新增下级机构,请重新选择!');
        }else {
	        add('<t:mutiLang langKey="common.add.param" langArg="common.department"/>', url, "departList",'675px','530px');
        }
    }

    function queryUsersByDepart(departid){
        var title = '<t:mutiLang langKey="member.list"/>';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#userListpanel').panel("refresh", "departController.do?userList&departid=" + departid);
    }
    /**
     * 为 组织机构 设置 角色
     * @param departid 组织机构主键
     * @param departname 组织机构名称
     */
    function setRoleByDepart(departid, departname){
        var currentTitle = $('#main_depart_list').layout('panel', 'east').panel('options').title;
        if(li_east == 0 || currentTitle.indexOf("<t:mutiLang langKey="current.org"/>") < 0){
            $('#main_depart_list').layout('expand','east');
        }
        var title = departname + ':<t:mutiLang langKey="current.org"/>';
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 200});
        var url = {
            href:"roleController.do?roleTree&orgId=" + departid
        };
        $('#userListpanel').panel(url);
        $('#userListpanel').panel("refresh");
    }
    //
//update-end--Author:zhangguoming  Date:20140821 for：为组织机构设置角色
//-->
</script>