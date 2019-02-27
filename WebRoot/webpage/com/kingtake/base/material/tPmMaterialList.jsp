<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <div id="tb" style="padding-left: 20px;">
      <div style="margin-top: 5px;">
      <table>
      <tr>
      <td>
        <label>名称：</label>
        <input name="materialName" id="materialName" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        <td>
        <label>规格、型号：</label>
        <input name="materialModel" id="materialModel" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        <td>
        <label>生产厂家：</label>
        <input name="materialFactory" id="materialFactory" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        <td>
        <label>计量单位：</label>
        <input name="materialUnit" id="materialUnit" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        </tr>
        <tr>
        <td colspan="2">
        <label>单价：</label>
        <input name="materialPrice_begin" class="easyui-numberbox" data-options="min:0,precision:2" id="materialPrice_begin" style="height:25px;width:120px;" />&nbsp;~
        <input name="materialPrice_end" class="easyui-numberbox" data-options="min:0,precision:2" id="materialPrice_end" style="height:25px;width:120px;" />
        </td>
        <td colspan="2">
        <label>录入日期：</label>
        <input name="supplyDate_begin" id="supplyDate_begin" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />&nbsp;~
        <input name="supplyDate_end" id="supplyDate_end" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        </tr>
        <tr>
        <td>
        <label>来源：</label>
        <input name="materialResource" id="materialResource" readonly="readonly" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        <td>
        <label>来源项目：</label>
        <input name="projectName" id="projectName" style="height: 25px; line-height: 25px; border: 1px solid #CACACA;" />
        </td>
        <td>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="reloadMaterial()">查询</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="searchReset()">重置</a>
        </td>
        </tr>
        </table>
      </div>
      <div style="margin-top: 5px;" id="opToolbar">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add('录入','tPmMaterialController.do?goUpdate','material', 500, 300)">录入</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="update('编辑','tPmMaterialController.do?goUpdate','material', 500, 300)">编辑</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="doDel()">删除</a>
      </div>
    </div>
    <table id="material" fit="true" class="easyui-datagrid"
      data-options="singleSelect:true, pagination:true, rownumbers:true, border:false, toolbar:'#tb',
				url:'tPmMaterialController.do?datagrid&field=id,materialName,materialModel,materialFactory,materialUnit,materialPrice,supplyDate,materialResource,projectName'">
      <thead>
        <tr>
          <th data-options="field:'id', hidden:true">id</th>
          <th data-options="field:'materialName', width:125">名称</th>
          <th data-options="field:'materialModel', width:125">规格、型号</th>
          <th data-options="field:'materialFactory', width:125">生产厂家</th>
          <th data-options="field:'materialUnit', width:100">计量单位</th>
          <th data-options="field:'materialPrice',align:'right', width:100" formatter="formatCurrency">单价</th>
          <th
            data-options="field:'supplyDate', width:'125',
	              		formatter:function(value){
	              			return new Date().format('yyyy-MM-dd', value);
	              		}">录入日期</th>
          <th
            data-options="field:'materialResource', width:'100',
	              		formatter:function(value){
	              			for(var i in lrly){
	              				if(lrly[i].code == value){
	              					return lrly[i].name;
	              				}
	              			}
	              		}">来源</th>
          <th data-options="field:'projectName',width:'150'">来源项目</th>
        </tr>
      </thead>
    </table>
  </div>
</div>

<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
  var lrly = {};
  $(document).ready(function() {
    $.ajax({
      type : 'post',
      url : 'tBCodeTypeController.do?getComboboxListByKey',
      data : {
        codeType : '1',
        code : 'LRLY'
      },
      success : function(data) {
        lrly = $.parseJSON(data);
      }
    });

    //reloadMaterial();
    
    $("#tb").find("input[name='supplyDate_begin']").attr("class","Wdate").attr("style","height:20px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tb").find("input[name='supplyDate_end']").attr("class","Wdate").attr("style","height:20px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tb").find("input").keypress(function(event){
        EnterPress(event);
    });
    
    $("#materialResource").combobox({
        url:'tBCodeTypeController.do?getComboboxListByKey&codeType=1&code=LRLY',    
        valueField:'code',    
        textField:'name',
        editable:false
    });
        
  });
  
  function searchReset(){
      $("#tb").find("input").val("");
  }

  function reloadMaterial() {
     var materialName = $.trim($("#materialName").val());
     if(materialName!=""){
         materialName = "*"+materialName+"*";
     }
     var materialModel = $.trim($("#materialModel").val());
     if(materialModel!=""){
         materialModel = "*"+materialModel+"*";
     }
     var materialFactory = $.trim($("#materialFactory").val());
     if(materialFactory!=""){
         materialFactory = "*"+materialFactory+"*";
     }
     var materialUnit = $.trim($("#materialUnit").val());
     if(materialUnit!=""){
         materialUnit = "*"+materialUnit+"*";
     }
     var materialPrice_begin = $.trim($("#materialPrice_begin").val());
     var materialPrice_end = $.trim($("#materialPrice_end").val());
     var supplyDate_begin = $.trim($("#supplyDate_begin").val());
     var supplyDate_end = $.trim($("#supplyDate_end").val());
     var materialResource = $.trim($("#materialResource").combobox('getValue'));
     var projectName = $.trim($('#projectName').val());
     if(projectName!=""){
         projectName = "*"+projectName+"*";
     }
    $("#material").datagrid('load', {
      materialName : materialName,
      materialModel: materialModel,
      materialFactory: materialFactory,
      materialUnit: materialUnit,
      materialPrice_begin : materialPrice_begin,
      materialPrice_end : materialPrice_end,
      supplyDate_begin : supplyDate_begin,
      supplyDate_end : supplyDate_end,
      materialResource : materialResource,
      projectName : projectName
    });
  }
  
    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            reloadMaterial();
        }
    }

    function doDel() {
        var data = $("#material").datagrid('getSelected');
        if (data) {
            $.messager.confirm('确认', '确认删除该条记录吗？', function(r) {
                if (r) {
                    $.ajax({
                        type : 'post',
                        url : 'tPmMaterialController.do?doDel',
                        data : 'id=' + data.id,
                        success : function(result) {
                            result = $.parseJSON(result);
                            showMsg(result.msg);
                            reloadMaterial();
                        }
                    });
                }
            });
        } else {
            showMsg('请选择需要删除的记录！');
        }
    }

    function showMsg(msg) {
        $.messager.show({
            title : '提示',
            msg : msg,
            timeout : 3000,
            showType : 'slide'
        });

    }
</script>