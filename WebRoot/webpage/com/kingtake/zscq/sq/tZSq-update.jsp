<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>授权</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
  <div style="margin: 0 auto; width: 600px;">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tZSqController.do?doUpdate" tiptype="1" beforeSubmit="checkData">
      <input id="id" name="id" type="hidden" value="${sqPage.id }">
      <input id="zlsqId" name="zlsqId" type="hidden" value="${sqPage.zlsqId }">
      <input id="role" type="hidden" value="${role }">
      <textarea id="nfListStr" name="nfListStr" style="display:none;"></textarea>
      <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label">
              发文日: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="fwr" name="fwr" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date"
              value='<fmt:formatDate value='${sqPage.fwr}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发文日</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              专利登记费: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="djf" name="djf" type="text" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',min:0,max:9999999999" style="width: 150px"  value='${sqPage.djf}'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">专利登记费</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              专利证书印花税: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="zsyhs" name="zsyhs" type="text" style="width: 150px" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',min:0,max:9999999999"  value='${sqPage.zsyhs}'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">专利证书印花税</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              总金额: &nbsp;&nbsp;
            </label></td>
          <td class="value"><input id="zje" name="zje" type="text" style="width: 150px" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',min:0,max:9999999999"  value='${sqPage.zje}'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">专利证书印花税</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
             要求缴纳时间: &nbsp;&nbsp;
            </label></td>
          <td class="value"><input id="yqjnsj" name="yqjnsj" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date" ignore="ignore" errorMsg="请录入要求缴纳时间"
              value='<fmt:formatDate value='${sqPage.yqjnsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;"> 要求缴纳时间</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp;&nbsp;&nbsp; </label></td>
          <td colspan="3" class="value"><input type="hidden" value="${sqPage.fjbm }" id="bid" name="fjbm" />
            <table style="max-width: 360px;" id="fileShow">
              <c:forEach items="${sqPage.attachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${sqPage.fjbm}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=sq" dialog="false" auto="true"
                onUploadSuccess="updateUploadSuccess"></t:upload>
            </div></td>
        </tr>
      </table>
    </t:formvalid>
    <div style="margin-top: 100px;">
    <table id="nfTab" style="height: 200px;"></table>
    </div>
  </div>
</body>
<script type="text/javascript">
    $(function() {
        var role = $("#role").val();
        
        $.extend($.fn.validatebox.defaults.rules, {
            year: {
                validator: function (value, param) {
                  return /^[12]\d{3}$/.test(value);
                },
                message: '请输入正确的年份'
              }
        });

        var toolbar = [];
        if(role=='depart'){
            toolbar.push({
            iconCls : 'icon-add',
            text : '添加',
            handler : function() {
                $('#nfTab').datagrid('appendRow', {
                    fyspsj : '',
                    jnje : '',
                });
            }
        }, {
            iconCls : 'icon-remove',
            text : '删除',
            handler : function() {
                var selected = $("#nfTab").datagrid("getSelected");
                var index = $("#nfTab").datagrid('getRowIndex', selected);
                $("#nfTab").datagrid("deleteRow", index);
            }
        });
        }
        $('#nfTab').datagrid({
            title : '年费',
            fitColumns : true,
            nowrap : true,
            striped : true,
            remoteSort : false,
            idField : 'id',
            toolbar : toolbar,
            columns : [ [ {
                field : 'id',
                title : 'id',
                width : 100,
                hidden : true
            }, {
                field : 'fyspsj',
                title : '年度',
                width : 100,
                formatter:function(value,rec,index){
                    return new Date().format('yyyy',value);
                },
                editor : {
                    type:'validatebox',
                    options:{
                        validType: 'year' ,
                        required:true
                    }
                }
            }, {
                field : 'jnje',
                title : '金额',
                width : 100,
                editor : {
                    type : 'numberbox',
                    options : {
                        min:0,
                        precision:2,
                        groupSeparator:','
                    }
                }
            } ] ],
            onDblClickRow : function(rowIndex, rowData) {
                $(this).datagrid('beginEdit', rowIndex);
            },
            onBeforeEdit : function(rowIndex, rowData) {
            },
            onAfterEdit : function(rowIndex, rowData) {
            },
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            }
        });

        loadData();
        
        if(role=="fmr"){
           $(":input").attr("disabled", "true");
           $("#filediv").parent().css("display", "none");
           $(".jeecgDetail").parent().css("display", "none");
        }
    });

    function loadData() {
        //加载数据
        var id = $("#id").val();
        if (id != "") {
            var x_url = "tZSqController.do?nfList&sqId=" + id+"&field=fyspsj,jnje";
            $('#nfTab').datagrid('options').url = x_url;
            setTimeout(function() {
                $('#nfTab').datagrid('load');
            }, 0);

        }
    }
    
    //获取表格中的值
    function checkData() {
            var rows = $('#nfTab').datagrid("getRows");
            if (rows.length == 0) {
                tip("没有录入年费！");
                return true;
            }
            var delRows = [];
            for (var i = 0; i < rows.length; i++) {
                var editors = $('#nfTab').datagrid("getEditors", i);
                if (editors.length == 0) {
                    if (rows[i].jnje == "") {
                        delRows.push(i);
                    }
                    continue;
                }
                if (!$('#nfTab').datagrid('validateRow', i)) {
                    return false;
                }
                $('#nfTab').datagrid("endEdit", i);
            }
            for (var i = 0; i < delRows.length; i++) {
                $('#nfTab').datagrid("deleteRow", delRows[i]);
            }
            rows = $('#nfTab').datagrid("getRows");
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total = total + parseFloat(rows[i].jnje);
            }
            var zje = $("#zje").numberbox('getValue');
            if(zje==""){
                $("#zje").numberbox('setValue',total);
            }
            var nodeListStr = JSON.stringify(rows);
            $("#nfListStr").val(nodeListStr);
    }
    
    function getId(){
        var id = $("#id").val();
        return id;
    }
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>