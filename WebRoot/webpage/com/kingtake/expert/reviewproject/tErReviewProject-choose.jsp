<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>项目与专家选择</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
        initExpert();//初始化专家
    });
  
//初始化专家信息
function initExpert() {
   var expertListStr = $("#expertListStr").val();
   if (expertListStr != "") {
       var expertListArr = $.parseJSON(expertListStr);
       for (var i = 0; i < expertListArr.length; i++) {
           addExpert(expertListArr[i]);
       }
   }
}
  </script>
 </head>
 <body>
  <div class="easyui-layout" fit="true" split="true">
  	<div data-options="region:'west'" title="选择评审项目" style="padding: 1px; width: 900px;">
    	<table style="margin-left: 5px;">
            <tr>
              <td style="padding:5px; width:70px;">选择项目：</td>
              <td style="padding:5px;">
                 <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onClick="selectProject()">选择</a> 
                 <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" onClick="clearProject();">清空</a>
              </td>
            </tr>
            <tr>
            <input id="projectIds" name="projectIds" type="hidden" value="${projectIds}">
              <td colspan="2">
              	 <textarea id="projectNames" rows="4" cols="5" style="height: 350px; width: 800px;font-size: 20px;" readonly="true">${projectNames}</textarea>		
              </td>
            </tr>
         </table>
     </div>
     <div data-options="region:'center'" title="选择专家">
      <table style="width: 100%;" cellpadding="0" cellspacing="1" >
        <tr>
          <td style=""><a id="selectBtn" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="selectExp();">选择</a> <a id="clearBtn" class="easyui-linkbutton"
              data-options="iconCls:'icon-redo',plain:true" onclick="clearExp();">清空</a></td>
        </tr>
        <tr>
          <td colspan="2">
            <div id="expertDiv" class="select-result" style="height: 100%; margin: 2px; border: solid 1px rgb(194, 183, 183); border-radius: 4px;height:100%;"></div> <input type="hidden" id="expertIds"
              name="expertIds">
              <input id="expertListStr" type="hidden" value="${expertListStr}">
          </td>
        </tr>
      </table>
      </div>
  </div>
 </body>