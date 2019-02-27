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
        //判断是否需要显示单位
        var needDepart = false;
        var needDepartStr = $("#needDepart").val();
        if(needDepartStr=='1'){
            needDepart = true;
        }
        
        $('#hotUserList').datagrid({
            //fit : true,
            fitColumns : true,
            nowrap : true,
            striped : true,
            remoteSort : false,
            idField : 'id',
            toolbar : [ {
                iconCls : 'icon-add',
                text : '全选',
                handler : function() {
                    var rows = $("#hotUserList").datagrid("getRows");
                    for (var i = rows.length - 1; i >= 0; i--) {
                        addUser(rows[i]);
                    }
                }
            } ],
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
                width : 100
            }, {
                field : 'realName',
                title : '中文名',
                width : 100
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
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#selectedUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    status : rowData.status,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#hotUserList").datagrid("deleteRow", rowIndex);
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
                width : 100
            }, {
                field : 'realName',
                title : '中文名',
                width : 100
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
                if(!needDepart){
                    $(this).datagrid("hideColumn",'departId');
                    $(this).datagrid("hideColumn",'departName');
                }
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#hotUserList").datagrid("appendRow", {
                    id : rowData.id,
                    userName : rowData.userName,
                    realName : rowData.realName,
                    status : rowData.status,
                    departId : rowData.departId,
                    departName : rowData.departName
                });
                $("#selectedUserList").datagrid("deleteRow", rowIndex);
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
        
        //隐藏待选人员的组织机构
        if(!needDepart){
            $("#hotUserList").datagrid("hideColumn",'departId');
            $("#hotUserList").datagrid("hideColumn",'departName');
        }

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
                                    status : rows[i].status,
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
                                            status : rows[i].status,
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

        $("#addBtn").bind('click', function() {
            var rows = $("#hotUserList").datagrid("getChecked");
            for (var i = rows.length - 1; i >= 0; i--) {
                addUser(rows[i]);
            }
        });

        $("#removeBtn").bind('click', function() {
            var rows = $("#selectedUserList").datagrid("getChecked");
            for (var i = rows.length - 1; i >= 0; i--) {
                removeUser(rows[i]);
            }
        });

    });

    //增加用户
    function addUser(row) {
        var index = $("#hotUserList").datagrid("getRowIndex", row);
        $("#selectedUserList").datagrid("appendRow", {
            id : row.id,
            userName : row.userName,
            realName : row.realName,
            status : row.status,
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
            status : row.status,
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
        var rows = $('#selectedUserList').datagrid("getRows");
        var vals = [];
        for (var i = 0; i < rows.length; i++) {
            vals.push(rows[i][field]);
        }
        return vals.join(",");
    }


   function getSeletedRows(){
         var rows = $("#selectedUserList").datagrid("getRows");
         return rows;
     }
     
   function alertMsg(msg){
       tip(msg);
     }
</script>
</head>
<body>
  <div id="layoutDiv" class="easyui-layout" data-options="fit:true">
    <input id="userIds" type="hidden" value="${userIds}"> 
    <input id="departIds" type="hidden" value="${departIds}"> 
    <input id="mode" type="hidden" value="${mode}">
    <input id="needDepart" type="hidden" value="${needDepart}">
    <div region="west" style="width: 200px;" title="组织机构">
      <div>
        <ul id="departTree" class="easyui-tree"
          data-options="url:'departController.do?getDepartTree',checkbox:true,cascadeCheck:false">
        </ul>
      </div>
    </div>

    <div region="center">
      <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west'" style="width: 300px;" title="待选人员">
          <table id="hotUserList"></table>
        </div>
        <div id="btnDiv" data-options="region:'center'"
          style="width: 40px; background-color: #EFF3F9; text-align: center; vertical-align: middle; display: table-cell;">
          <ul>
            <li><img id="addBtn" src="plug-in/easyui/themes/icons/right.png"
              onMouseOver="this.src='plug-in/easyui/themes/icons/right2.png'"
              onMouseOut="this.src='plug-in/easyui/themes/icons/right.png'"></img></li>
            <li><img id="removeBtn" src="plug-in/easyui/themes/icons/left.png"
              onMouseOver="this.src='plug-in/easyui/themes/icons/left2.png'"
              onMouseOut="this.src='plug-in/easyui/themes/icons/left.png'"></img></li>
          </ul>
        </div>
        <div data-options="region:'east'" style="width: 300px;" title="已选人员">
          <table id="selectedUserList"></table>
        </div>
      </div>
    </div>

  </div>
</body>
</html>