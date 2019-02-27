<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择人员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css"></link>
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
            fit : true,
            fitColumns : true,
            nowrap : true,
            striped : true,
            remoteSort : false,
            idField : 'id',
            toolbar : "#hotUserListtb",
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
                field : 'name',
                title : '姓名',
                width : 100
            }, {
                field : 'userName',
                title : '登录账号',
                width : 100,
                hidden:true
                
            }, {
                field : 'userPwd',
                title : '登录密码',
                width : 100,
                hidden:true
               
            }, {
                field : 'sexStr',
                title : '性别',
                width : 60
            }, {
                field : 'expertProfessionStr',
                title : '专业',
                width : 100
            }, {
                field : 'expertPositionStr',
                title : '职称',
                width : 100
            }, {
                field : 'expertPraciticReqStr',
                title : '执业资格',
                width : 100
            },{
                field : 'expertPhone',
                title : '联系方式',
                width : 100,
                hidden:true
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            },
            onDblClickRow : function(rowIndex, rowData) {
                if (!checkExist(rowData)) {
                    $("#selectedUserList").datagrid("appendRow", {
                        id : rowData.id,
                        name : rowData.name,
                        userName : rowData.userName,
                        userPwd : rowData.userPwd,
                        sexStr : rowData.sexStr,
                        expertProfessionStr : rowData.expertProfessionStr,
                        expertPositionStr : rowData.expertPositionStr,
                        expertPraciticReqStr : rowData.expertPraciticReqStr,
                        expertPhone : rowData.expertPhone
                    });
                    $("#hotUserList").datagrid("deleteRow", rowIndex);
                }
            }
        });
       /*  $('#hotUserList').datagrid('getPager').pagination({
            beforePageText : '',
            afterPageText : '/{pages}',
            displayMsg : '{from}-{to}共 {total}条',
            showPageList : true,
            showRefresh : true
        });
        $('#hotUserList').datagrid('getPager').pagination({
            onBeforeRefresh : function(pageNumber, pageSize) {
                $(this).pagination('loading');
                $(this).pagination('loaded');
            }
        }); */

        var urlHotUser = "tErExpertController.do?dataList";

        //加载初始化的数据
        setTimeout(function() {
            $("#hotUserList").datagrid("options").url = urlHotUser;
            $("#hotUserList").datagrid("reload");
        }, 0);

        $('#selectedUserList').datagrid({
            fit : true,
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
                field : 'name',
                title : '姓名',
                width : 100
            }, {
                field : 'userName',
                title : '登录账号',
                width : 100,
                hidden:true
                
            }, {
                field : 'userPwd',
                title : '登录密码',
                width : 100,
                hidden:true
               
            },{
                field : 'sexStr',
                title : '性别',
                width : 60
            }, {
                field : 'expertProfessionStr',
                title : '专业',
                width : 100
            }, {
                field : 'expertPositionStr',
                title : '职称',
                width : 100
            }, {
                field : 'expertPraciticReqStr',
                title : '执业资格',
                width : 100
            },{
                field : 'expertPhone',
                title : '联系方式',
                width : 100,
                hidden:true
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            },
            onDblClickRow : function(rowIndex, rowData) {
                $("#hotUserList").datagrid("appendRow", {
                    id : rowData.id,
                    name : rowData.name,
                    userName : rowData.userName,
                    userPwd : rowData.userPwd,
                    sexStr : rowData.sexStr,
                    expertProfessionStr : rowData.expertProfessionStr,
                    expertPositionStr : rowData.expertPositionStr,
                    expertPraciticReqStr : rowData.expertPraciticReqStr,
                    expertPhone : rowData.expertPhone
                });
                $("#selectedUserList").datagrid("deleteRow", rowIndex);
            }
        });

        var excludeIds = $("#excludeIds").val();

        if (excludeIds != "") {
            var urlSelectedUser = "tErExpertController.do?getInitList";
            $("#selectedUserList").datagrid("options").queryParams["ids"] = excludeIds;
            setTimeout(function() {
                $("#selectedUserList").datagrid("options").url = urlSelectedUser;
                $("#selectedUserList").datagrid("reload");
            }, 0);
        }
        //加载初始化的数据

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

        $("#autoName").autocomplete({
            source : "tErExpertController.do?autoExpertList",
            minLength : 1,
            select : function(event, ui) {
                $("#autoName").val(ui.item.label);
                $.ajax({
                    url : "tErExpertController.do?getExpertById",
                    dataType : "json",
                    data : {
                        "id" : ui.item.value
                    },
                    success : function(rowData) {
                        if (!checkExist(rowData)) {
                            $("#selectedUserList").datagrid("appendRow", {
                                id : rowData.id,
                                name : rowData.name,
                                userName : rowData.userName,
                                userPwd : rowData.userPwd,
                                sexStr : rowData.sexStr,
                                expertProfessionStr : rowData.expertProfessionStr,
                                expertPositionStr : rowData.expertPositionStr,
                                expertPraciticReqStr : rowData.expertPraciticReqStr,
                                expertPhone : rowData.expertPhone
                            });
                        }
                    }
                });
                return false;
            }
        });

    });

    //增加用户
    function addUser(rowData) {
        if (!checkExist(rowData)) {
            var index = $("#hotUserList").datagrid("getRowIndex", rowData);
            $("#selectedUserList").datagrid("appendRow", {
                id : rowData.id,
                name : rowData.name,
                userName : rowData.userName,
                userPwd : rowData.userPwd,
                sexStr : rowData.sexStr,
                expertProfessionStr : rowData.expertProfessionStr,
                expertPositionStr : rowData.expertPositionStr,
                expertPraciticReqStr : rowData.expertPraciticReqStr,
                expertPhone : rowData.expertPhone
            });
            $("#hotUserList").datagrid("deleteRow", index);
        }
    }

    //移除用户
    function removeUser(rowData) {
        var index = $("#selectedUserList").datagrid("getRowIndex", rowData);
        $("#hotUserList").datagrid("appendRow", {
            id : rowData.id,
            name : rowData.name,
            userName : rowData.userName,
            userPwd : rowData.userPwd,
            sexStr : rowData.sexStr,
            expertProfessionStr : rowData.expertProfessionStr,
            expertPositionStr : rowData.expertPositionStr,
            expertPraciticReqStr : rowData.expertPraciticReqStr,
            expertPhone : rowData.expertPhone
        });
        $("#selectedUserList").datagrid("deleteRow", index);
    }

    //检查是否已存在
    function checkExist(rowData) {
        var rows = $("#selectedUserList").datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].id == rowData.id) {
                return true;
            }
        }
        return false;
    }

    //获取指定字段的值
    function getselectedUserListSelections() {
        var rows = $('#selectedUserList').datagrid("getRows");
        return rows;
    }

    function getSeletedRows() {
        var rows = $("#selectedUserList").datagrid("getRows");
        return rows;
    }

    function alertMsg(msg) {
        tip(msg);
    }

    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            expertListsearch();
        }
    }
    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        expertListsearch();
    }

    function expertListsearch() {
        var queryParams = $('#hotUserList').datagrid('options').queryParams;
        $('#hotUserListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        var url = "tErExpertController.do?dataList";
        $('#hotUserList').datagrid({
            url : url,
            pageNumber : 1
        });
    }
</script>
</head>
<body>
  <input id="excludeIds" type="hidden" value="${excludeIds}">
  <div id="layoutDiv" class="easyui-layout" data-options="fit:true">
    <input id="userIds" type="hidden" value="${userIds}"> <input id="departIds" type="hidden"
      value="${departIds}"> <input id="mode" type="hidden" value="${mode}"> <input id="needDepart"
      type="hidden" value="${needDepart}">
    <div region="center">
      <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west'" style="width: 500px;" title="待选专家">
          <table id="hotUserList"></table>
          <div id="hotUserListtb" style="padding: 3px; height: auto">
            <div name="searchColums">
              <span style="display: -moz-inline-box; display: inline-block;"><span
                style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;"
                title="姓名">姓名：</span><input onkeypress="EnterPress(event)" onkeydown="EnterPress()" type="text"
                id="autoName" name="name" isLike="true" style="width: 101px; margin-left: 4px;" /> <span
                style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;"
                title="性别">性别：</span> <t:codeTypeSelect name="sex" type="select" codeType="0" code="SEX" id="sex" labelText="请选择"
                  extendParam="{\"style\":\"width: 106px;margin-top:4px;\"}"></t:codeTypeSelect> <br /> <span
                style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;"
                title="专业">专业：</span> <t:codeTypeSelect name="expertProfession" type="select" codeType="1" code="MAJOR" labelText="请选择"
                  id="expertProfession" extendParam="{\"style\":\"width: 106px;margin-top:2px;\"}"></t:codeTypeSelect> <span
                style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;"
                title="职称">职称：</span> <t:codeTypeSelect name="expertPosition" type="select" codeType="1" code="ZHCH" labelText="请选择"
                  id="expertPosition" extendParam="{\"style\":\"width: 106px;margin-top:2px;\"}"></t:codeTypeSelect> <br />
                <span
                style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;"
                title="职业资格">职业资格：</span> <t:codeTypeSelect name="expertPraciticReq" type="select" codeType="1" labelText="请选择"
                  code="ZYZG" id="expertPraciticReq" extendParam="{\"style\":\"width: 106px;margin-top:2px;\"}"></t:codeTypeSelect>
              </span>
            </div>
            <div style="height: 30px;" class="datagrid-toolbar">
              <span style="float: left;"></span><span style="float: right"><a href="#" class="easyui-linkbutton"
                iconCls="icon-search" data-options="plain:true" onclick="expertListsearch()">查询</a><a href="#"
                class="easyui-linkbutton" iconCls="icon-reload" data-options="plain:true"
                onclick="searchReset('hotUserList')">重置</a></span>
            </div>
          </div>
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
        <div data-options="region:'east'" style="width: 500px;" title="已选专家">
          <table id="selectedUserList"></table>
        </div>
      </div>
    </div>

  </div>
</body>
</html>