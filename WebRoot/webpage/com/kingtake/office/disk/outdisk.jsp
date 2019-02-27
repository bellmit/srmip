<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript" src="plug-in/easyui/extends/datagrid-scrollview.js"></script>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="plug-in/tools/css/common.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<!-- <script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script> -->
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<script type="text/javascript" src="plug-in/jquery-plugs/hftable/jquery-hftable.js"></script>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">

</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <script type="text/javascript">
                    $(function() {
                        $('#diskList')
                                .datagrid(
                                        {
                                            idField : 'id',
                                            title : '公共网盘列表',
                                            url : 'tODiskController.do?datagridForPublic&uploadType=public&field=id,createdate,realpath,attachmenttitle,extend,',
                                            striped : true,
                                            fit : true,
                                            loadMsg : '数据加载中...',
                                            pageSize : 10,
                                            pagination : true,
                                            pageList : [ 10, 20, 30 ],
                                            sortOrder : 'asc',
                                            rownumbers : true,
                                            singleSelect : false,
                                            fitColumns : true,
                                            showFooter : true,
                                            frozenColumns : [ [ {
                                                field : 'ck',
                                                checkbox : 'true'
                                            }, ] ],
                                            columns : [ [
                                                    {
                                                        field : 'id',
                                                        title : '主键',
                                                        width : 120,
                                                        hidden : true,
                                                        sortable : true
                                                    },
                                                    {
                                                        field : 'createdate',
                                                        title : '上传时间',
                                                        width : 120,
                                                        sortable : true,
                                                        formatter : function(value, rec, index) {
                                                            return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                                                        }
                                                    },
                                                    {
                                                        field : 'realpath',
                                                        title : '文件路径',
                                                        width : 120,
                                                        hidden : true,
                                                        sortable : true
                                                    },
                                                    {
                                                        field : 'attachmenttitle',
                                                        title : '文件名',
                                                        width : 220,
                                                        formatter : fileFormatter,
                                                        sortable : true
                                                    },
                                                    {
                                                        field : 'extend',
                                                        title : '文件后缀名',
                                                        width : 120,
                                                        hidden : true,
                                                        sortable : true
                                                    }
                                                    ] ],
                                            onLoadSuccess : function(data) {
                                                $("#diskList").datagrid("clearSelections");
                                            },
                                            onClickRow : function(rowIndex, rowData) {
                                                rowid = rowData.id;
                                                gridname = 'diskList';
                                            }
                                        });
                        $('#diskList').datagrid('getPager').pagination({
                            beforePageText : '',
                            afterPageText : '/{pages}',
                            displayMsg : '显示{from}-{to}条，共{total}条记录',
                            showPageList : true,
                            showRefresh : true
                        });
                        $('#diskList').datagrid('getPager').pagination({
                            onBeforeRefresh : function(pageNumber, pageSize) {
                                $(this).pagination('loading');
                                $(this).pagination('loaded');
                            }
                        });
                    });
                    function reloadTable() {
                        try {
                            $('#' + gridname).datagrid('reload');
                            $('#' + gridname).treegrid('reload');
                        } catch (ex) {
                        }
                    }
                    function reloaddiskList() {
                        $('#diskList').datagrid('reload');
                    }
                    function getdiskListSelected(field) {
                        return getSelected(field);
                    }
                    function getSelected(field) {
                        var row = $('#' + gridname).datagrid('getSelected');
                        if (row != null) {
                            value = row[field];
                        } else {
                            value = '';
                        }
                        return value;
                    }
                    function getdiskListSelections(field) {
                        var ids = [];
                        var rows = $('#diskList').datagrid('getSelections');
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i][field]);
                        }
                        ids.join(',');
                        return ids
                    };
                    function getSelectRows() {
                        return $('#diskList').datagrid('getChecked');
                    }
                    function diskListsearch() {
                        var queryParams = $('#diskList').datagrid('options').queryParams;
                        $('#diskListtb').find('*').each(function() {
                            if ($(this).attr('isLike') == 'true' && $(this).val()) {
                                queryParams[$(this).attr('name')] = '*' + $(this).val() + '*';
                            } else {
                                queryParams[$(this).attr('name')] = $(this).val();
                            }
                        });
                        $('#diskList')
                                .datagrid(
                                        {
                                            url : 'tODiskController.do?datagridForPublic&uploadType=public&field=id,id_begin,id_end,createdate,realpath,attachmenttitle,extend,extend_begin,extend_end,',
                                            pageNumber : 1
                                        });
                    }
                    function dosearch(params) {
                        var jsonparams = $.parseJSON(params);
                        $('#diskList')
                                .datagrid(
                                        {
                                            url : 'tODiskController.do?datagridForPublic&uploadType=public&field=id,id_begin,id_end,createdate,realpath,attachmenttitle,extend,extend_begin,extend_end,',
                                            queryParams : jsonparams
                                        });
                    }
                    function diskListsearchbox(value, name) {
                        var queryParams = $('#diskList').datagrid('options').queryParams;
                        queryParams[name] = value;
                        queryParams.searchfield = name;
                        $('#diskList').datagrid('reload');
                    }
                    $('#diskListsearchbox').searchbox({
                        searcher : function(value, name) {
                            diskListsearchbox(value, name);
                        },
                        menu : '#diskListmm',
                        prompt : '请输入查询关键字'
                    });
                    function EnterPress(e) {
                        var e = e || window.event;
                        if (e.keyCode == 13) {
                            diskListsearch();
                        }
                    }
                    function searchReset(name) {
                        $("#" + name + "tb").find(":input").val("");
                        diskListsearch();
                    }
                </script>
    <table width="100%" id="diskList" toolbar="#diskListtb"></table>
    <div id="diskListtb" style="padding: 3px; height: auto">
      <div name="searchColums">
        <span style="display: -moz-inline-box; display: inline-block;">
          <span
            style="vertical-align: middle; display: -moz-inline-box; display: inline-block; margin-left: 10px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
            title="文件名">文件名：</span>
          <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" id="attachmenttitle" type="text" name="attachmenttitle" style="width: 100px" />
        </span>
      </div>
      <div style="height: 30px;" class="datagrid-toolbar">
        <span style="float: left;"></span>
        <span style="float: right">
          <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="diskListsearch()">查询</a>
          <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="searchReset('diskList')">重置</a>
        </span>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    });
</script>
<script type="text/javascript" src="webpage/com/kingtake/office/disk/disk.js"></script>