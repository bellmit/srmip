<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择人员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
#btnDiv ul,li {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

#btnDiv li {
	padding-bottom: 10px;
	padding-top: 10px;
}
</style>
<script>
    $(function() {
        
        $('#hotUserList').datagrid({
            //fit : true,
            fitColumns : true,
            nowrap : true,
            striped : true,
            remoteSort : false,
            idField : 'id',
            checkOnSelect : false,
            /* toolbar : [ {
                iconCls : 'icon-add',
                text : '全选',
                handler : function() {
                    var rows = $("#hotUserList").datagrid("getRows");
                    for (var i = rows.length - 1; i >= 0; i--) {
                        addUser(rows[i]);
                    }
                }
            } ], */ 
            columns : [ [ {
                field : 'ck',
                width : 40,
                checkbox : true
            }, {
                field : 'id',
                title : 'id',
                width : 100,
                hidden : true
            }, {
                field : 'userName',
                title : '用户名',
                width : 100,
                hidden : true
                
            }, {
                field : 'realName',
                title : '中文名',
                width : 100,
                formatter : nameFormatter
            },
            {
                field : 'departId',
                title : '组织机构Id',
                width : 100,
                hidden : true
            },
            {
                field : 'departName',
                title : '组织机构',
                width : 120,
                hidden : true
            }] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#selectedUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#hotUserList").datagrid("deleteRow", rowIndex);
            }, 
            onCheck : function(rowIndex,rowData){
                $("#selectedUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#hotUserList").datagrid("deleteRow", rowIndex);
                $("#hotUserList").datagrid('clearChecked');
            },
            onCheckAll : function(rows){
                for(var i=rows.length-1;i>=0;i--){
                    $("#selectedUserList").datagrid("appendRow", {
                        id : rows[i].id,
                        userName : rows[i].userName,
                        realName : rows[i].realName,
                        departId : rows[i].departId,
                        departName : rows[i].departName
                    });
                    $("#hotUserList").datagrid("deleteRow", i);
                }
                $("#hotUserList").datagrid('clearChecked');
            }
        });

        $('#selectedUserList').datagrid({
            //fit : true,
            fitColumns : true,
            nowrap : true,
            striped : true,
            sortOrder : 'desc',
            remoteSort : false,
            idField : 'id',
            checkOnSelect : false,
            columns : [ [ {
                field : 'ck',
                width : 40,
                checkbox : true
            }, {
                field : 'id',
                title : 'id',
                width : 100,
                hidden : true
            }, {
                field : 'userName',
                title : '用户名',
                width : 100,
                hidden : true
            }, {
                field : 'realName',
                title : '中文名',
                width : 100,
                formatter : nameFormatter
            },{
                field : 'departId',
                title : '组织机构Id',
                width : 100,
                hidden : true
            },
            {
                field : 'departName',
                title : '组织机构',
                width : 120,
                hidden : true
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
                
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#hotUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#selectedUserList").datagrid("deleteRow", rowIndex);
                $("#selectedUserList").datagrid('clearChecked');
            },
            onCheck : function(rowIndex,rowData){
                $("#hotUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#selectedUserList").datagrid("deleteRow", rowIndex);
                $("#selectedUserList").datagrid('clearChecked');
            },
            onCheckAll : function(rows){
                for(var i=rows.length-1;i>=0;i--){
                    $("#hotUserList").datagrid("appendRow", {
                        id : rows[i].id,
                        userName : rows[i].userName,
                        realName : rows[i].realName,
                        departId : rows[i].departId,
                        departName : rows[i].departName
                    });
                    $("#selectedUserList").datagrid("deleteRow", i);
                }
                $("#selectedUserList").datagrid('clearChecked');
            }
        });

        var userIds = $("#userIds").val();
        var departIds = $("#departIds").val();
        var url = "commonUserController.do?getInitUserList";
        if (userIds != "") {
            url = url+"&userIds="+ userIds;
        }
        if (departIds != "") {
            url = url+"&departIds="+ departIds;
        }
        //加载初始化的数据
        setTimeout(function() {
            $("#selectedUserList").datagrid("options").url = url;
            $("#selectedUserList").datagrid("reload");
        }, 0);
        

        //设置tree的参数
        $("#departTree").tree({
            onClick : function(node) {
                var data = getQueryData(node);
                $.ajax({
                    url : "commonUserController.do?getUserByDepart",
                    async : false,
                    cache : false,
                    type : 'POST',
                    dataType : 'json',
                    data : data,
                    success : function(data) {
                        $("#hotUserList").datagrid("loadData", data);
                    }
                });
            },
            onCheck : function(node, checked) {
                if (checked) {//若checkbox选中，则将该部门下的人员移到已选列表中
                    var data = getQueryData(node);
                    $.ajax({
                        async : false,
                        cache : false,
                        type : 'POST',
                        url : "commonUserController.do?getUserByDepart",
                        dataType : 'json',
                        data : data,
                        success : function(data) {
                            var rows = data.rows;
                            for (var i = 0; i < rows.length; i++) {
                                $("#selectedUserList").datagrid("appendRow", {
                                    id : rows[i].id,
                                    userName : rows[i].userName,
                                    realName : rows[i].realName,
                                    departId : rows[i].departId,
                                    departName : rows[i].departName
                                });
                                searchAndRemove(rows[i]);
                            }
                        }
                    });
                } else {//若取消checkbox勾选,则将该部门下已选中的人员移到待选人员中
                    var data = {};
                    data["departId"] = node.id;
                    $.ajax({
                        async : false,
                        cache : false,
                        type : 'POST',
                        url : "commonUserController.do?getUserByDepart",
                        dataType : 'json',
                        data : data,
                        success : function(data) {
                            var rows = data.rows;
                            for (var i = 0; i < rows.length; i++) {
                                var selectedRows = $("#selectedUserList").datagrid("getRows");
                                for (var j = 0; j < selectedRows.length; j++) {
                                    if (rows[i].id == selectedRows[j].id) {
                                        $("#hotUserList").datagrid("appendRow", {
                                            id : rows[i].id,
                                            userName : rows[i].userName,
                                            realName : rows[i].realName,
                                            departId : rows[i].departId,
                                            departName : rows[i].departName
                                        });
                                        var index = $("#selectedUserList").datagrid("getRowIndex", selectedRows[j]);
                                        $("#selectedUserList").datagrid("deleteRow", index);
                                    }
                                }
                            }
                        }
                    });
                }
            }

        });
        
        
    $("#contactTree").tree({
            onCheck : function(node, checked) {
                var idx = $("#selectedUserList").datagrid('getRowIndex', node.attributes['userId']);
                if (checked) {//若checkbox选中，则将该人员移到已选列表中
                    if (idx == -1) {//不存在则添加
                        $("#selectedUserList").datagrid("appendRow", {
                            id : node.attributes['userId'],
                            userName : node.attributes['userName'],
                            realName : node.text,
                            departId : node.attributes['deptId'],
                            departName : node.attributes['deptName']
                        });
                    }
                } else {
                    if (idx != -1) {//已存在则删除
                        $("#selectedUserList").datagrid('deleteRow', idx);
                    }
                }
            },
            onContextMenu : function(e, node) {
                e.preventDefault();
                $(this).tree('select', node.target);
                $('#mm').menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
            }
        });

        $("#addBtn").bind('click', function() {
            var rows = $("#hotUserList").datagrid("getChecked");
            for (var i = rows.length - 1; i >= 0; i--) {
                addUser(rows[i]);
            }
            $("#hotUserList").datagrid('clearChecked');
        });

        $("#removeBtn").bind('click', function() {
            var rows = $("#selectedUserList").datagrid("getChecked");
            for (var i = rows.length - 1; i >= 0; i--) {
                removeUser(rows[i]);
            }
            $("#selectedUserList").datagrid('clearChecked');
        });
        
        $('#checkedUserList').datagrid({
            fit : true,
            fitColumns : true,
            nowrap : true,
            striped : true,
            sortOrder : 'desc',
            remoteSort : false,
            idField : 'id',
            checkOnSelect : false,
            columns : [ [ {
                field : 'ck',
                width : 40,
                checkbox : true
            }, {
                field : 'id',
                title : 'id',
                width : 100,
                hidden : true
            }, {
                field : 'userName',
                title : '用户名',
                width : 100,
                hidden : true
            }, {
                field : 'realName',
                title : '中文名',
                width : 100,
                formatter : nameFormatter
            },{
                field : 'departId',
                title : '组织机构Id',
                width : 100,
                hidden : true
            },
            {
                field : 'departName',
                title : '组织机构',
                width : 120,
                hidden : true
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
                
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#checkedUserList").datagrid("deleteRow", rowIndex);
                $("#checkedUserList").datagrid('clearChecked');
            },
            onCheck : function(rowIndex,rowData){
                $("#checkedUserList").datagrid("deleteRow", rowIndex);
                $("#checkedUserList").datagrid('clearChecked');
            },
            onCheckAll : function(rows){
                for(var i=rows.length-1;i>=0;i--){
                    $("#checkedUserList").datagrid("deleteRow", i);
                }
                $("#checkedUserList").datagrid('clearChecked');
            }
        });
        
    });

    //字段格式化
    function nameFormatter(value, row, index) {
        var departStr = "";
        if(row.departName!=undefined){
            departStr = "("+row.departName+")";
        }
        return value + departStr;
    }

    //增加用户
    function addUser(row) {
        var index = $("#hotUserList").datagrid("getRowIndex", row);
        $("#selectedUserList").datagrid("appendRow", {
            id : row.id,
            userName : row.userName,
            realName : row.realName,
            departId : row.departId,
            departName : row.departName
        });
        $("#hotUserList").datagrid("deleteRow", index);
    }

    //移除用户
    function removeUser(row) {
        var index = $("#selectedUserList").datagrid("getRowIndex", row);
        $("#hotUserList").datagrid("appendRow", {
            id : row.id,
            userName : row.userName,
            realName : row.realName,
            departId : row.departId,
            departName : row.departName
        });
        $("#selectedUserList").datagrid("deleteRow", index);
    }

    //组装参数
    function getQueryData(node) {
        var rows = $('#selectedUserList').datagrid("getRows");
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }
        var data = {};
        data["departId"] = node.id;
        if (ids.length > 0) {
            data["ids"] = ids.join(",");
        }
        return data;
    }

    //搜索待选列表并移除
    function searchAndRemove(row) {
        var rows = $("#hotUserList").datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].id == row.id) {
                var index = $("#hotUserList").datagrid("getRowIndex", rows[i]);
                $("#hotUserList").datagrid("deleteRow", index);
            }
        }
    }

    //获取指定字段的值
    function getselectedUserListSelections(field) {
        var tab = $('#chooseUserTabs').tabs('getSelected');
        var index = $('#chooseUserTabs').tabs('getTabIndex',tab);
        var rows;
        if(index==0){
            rows = $("#selectedUserList").datagrid("getRows");
 	    }else{
            rows = $("#checkedUserList").datagrid("getRows");
 	    }
        var vals = [];
        for (var i = 0; i < rows.length; i++) {
            vals.push(rows[i][field]);
        }
        return vals.join(",");
    }

    function getSeletedRows() {
        var tab = $('#chooseUserTabs').tabs('getSelected');
        var index = $('#chooseUserTabs').tabs('getTabIndex',tab);
        var rows;
	    if(index==0){
           rows = $("#selectedUserList").datagrid("getRows");
	    }else{
           rows = $("#checkedUserList").datagrid("getRows");
	    }
        return rows;
    }

    function alertMsg(msg) {
        tip(msg);
    }

    function clean() {
        var node = $('#contactTree').tree('getSelected');
        var param = {
            'id' : node.id
        };
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            data : param,
            url : 'commonUserController.do?deleteUserContact',// 请求的action路径
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    $('#contactTree').tree('reload');
                }
            }
        });
    }
    
    function showAll(){
        $("#contactTree").tree('options').url="commonUserController.do?getContactList&needMore=true";
        $("#contactTree").tree('reload');
    }
    
    function doSearch() {
        var keyWord = $("#search").searchbox("getValue");
        var options = $("#contactTree").tree('options');
        $("#contactTree").tree('options').url="commonUserController.do?getContactList";
        $("#contactTree").tree('options').onBeforeLoad=function(node, param){param.keyWord=keyWord};
        $("#contactTree").tree('reload');
    }
    
    
    //勾选人员列表
    function checkUser(rowIndex, rowData){
        /* var checkedRows = $("#userList").datagrid("getChecked");
        var checked = false;
        for(var i=0;i<checkedRows.length;i++){
            if(checkedRows[i].id==rowData.id){
                checked = true;
                break;
            }
        } */
        var rows = $("#checkedUserList").datagrid("getRows");
        var exist = false;
        var index;
        for(var i=0;i<rows.length;i++){
            if(rows[i].id==rowData.id){
                exist = true;
                index = i;
                break;
            }
        }
        if(!exist){
            $("#checkedUserList").datagrid("appendRow", {
                id : rowData.id,
                userName : rowData.userName,
                realName : rowData.realName,
                departId : rowData.departId,
                departName : rowData.departName
            });
        }else if(exist){
            $("#checkedUserList").datagrid("deleteRow", index);
        }
        
    }
    
</script>
</head>
<body>
  <div id="chooseUserTabs" class="easyui-tabs" data-options="tabPosition:'bottom',fit:true,plain:true,border:true">
  <div title="根据单位选择">
  <div  id="layoutDiv" class="easyui-layout" data-options="fit:true">
    <input id="userIds" type="hidden" value="${userIds}"> 
    <input id="departIds" type="hidden" value="${departIds}"> 
    <input id="mode" type="hidden" value="${mode}">
    <div data-options="region:'west'" style="width: 200px;" >
      <div>
        <ul id="contactTree" class="easyui-tree"
          data-options="url:'commonUserController.do?getContactList',checkbox:true,cascadeCheck:false,onlyLeafCheck:true">
        </ul>
      </div>
      <div>
        <ul id="departTree" class="easyui-tree"
          data-options="url:'departController.do?getDepartTree',animate: true,checkbox:true,cascadeCheck:false">
        </ul>
      </div>
    </div>

    <div data-options="region:'center'">
      <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west'" style="width: 250px;" title="待选人员">
          <table id="hotUserList"></table>
        </div>
        <div id="btnDiv" data-options="region:'center'"
          style="width: 20px; background-color: #EFF3F9; text-align: center; vertical-align: middle; display: table-cell;">
        </div>
        <div data-options="region:'east'" style="width: 250px;" title="已选人员">
          <table id="selectedUserList"></table>
        </div>
      </div>
    </div>
  </div>
  </div>
  <div title="根据人员列表选择">
  <div class="easyui-layout" data-options="fit:true">
   <div data-options="region:'center'" title="人员列表">
      <t:datagrid name="userList" actionUrl="commonUserController.do?userDatagrid" fit="true" fitColumns="true" idField="id" queryMode="group" extendParams="nowrap:false," checkbox="true" onCheck="checkUser" onUncheck="checkUser">
        <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
        <%-- <t:dgCol title="用户名" sortable="false" field="userName" query="true" isLike="true" width="100"></t:dgCol> --%>
        <t:dgCol title="部门" field="departName" sortable="false"  width="150"></t:dgCol>
        <t:dgCol title="部门id" field="departId" hidden="true"  width="80"></t:dgCol>
        <t:dgCol title="真实姓名" field="realName" query="true" width="80"></t:dgCol>
        <t:dgCol title="用户名" field="userName" hidden="true" query="true" width="80"></t:dgCol>
        <t:dgCol title="性别" sortable="false" field="sex" queryMode="group" codeDict="0,SEX" width="60"></t:dgCol>
        <%-- <t:dgCol title="出生日期" sortable="false" field="birthday" formatter="yyyy-MM-dd" width="100"></t:dgCol> --%>
        <%-- <t:dgCol title="证件号码" sortable="false" field="idNum" width="150"></t:dgCol> --%>
        <%-- <t:dgCol title="政治面貌" sortable="false" field="politicalStatus" codeDict="0,POLITICALSTATUS" width="100"></t:dgCol> --%>
        <%-- <t:dgCol title="籍贯" sortable="false" field="nativePlace" queryMode="group" width="100"></t:dgCol> --%>
        <%-- <t:dgCol title="民族" sortable="false" field="nation" codeDict="0,NATION" width="100"></t:dgCol> --%>
        <%-- <t:dgCol title="角色" field="userKey" width="100"></t:dgCol> --%>
        <t:dgCol title="手机" sortable="false" field="mobilephone" width="100"></t:dgCol>
        <t:dgCol title="办公电话" sortable="false" field="officephone" width="80"></t:dgCol>
        <%-- <t:dgCol title="邮箱" sortable="false" field="email" width="100"></t:dgCol> --%>
        <%-- <t:dgCol title="状态" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1" width="100"></t:dgCol> --%>
      </t:datagrid>
    </div>
    </div>
    <div data-options="region:'east'" style="width: 250px;" title="已选人员">
          <table id="checkedUserList"></table>
        </div>
  </div>
  </div>
  
  <div id="mm" class="easyui-menu">
    <div onclick="clean()" data-options="iconCls:'icon-cancel'">清理</div>
  </div>
</body>
</html>