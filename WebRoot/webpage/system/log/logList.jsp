<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
    $("#logListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
    
    $("input[name='operatetime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("input[name='operatetime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

    $("input").css("height", "24px");
    
}); 

function clickRow(rowIndex, rowData){
    $("#userNameTxt").val(rowData.username);
    $("#ipTxt").val(rowData.ip);
    var opType = {};
    //1登录/2退出/3插入/4删除/5更新/6上传/7其它
    opType['1']="登录";
    opType['2']="退出";
    opType['3']="插入";
    opType['4']="删除";
    opType['5']="更新";
    opType['6']="上传";
    opType['7']="其它";
    $("#opTypeTxt").val(opType[rowData.operatetype]);
    $("#opObjTxt").val(rowData.opObjectName);
    $("#opTimeTxt").val(rowData.operatetime);
    $("#logContentTxt").val(rowData.logcontent);
    $('#viewLogWin').window({
        zIndex:99999,
        inline:true
    });
    $('#viewLogWin').window("open"); 
  }
</script>
<style>
.right:{
  text-align: right;
  width: 400px;
}
.txt{
  width:150px;
}
</style>
<div id="tempSearchColums" style="padding: 3px; height: 25px;display:none;">
    <!-- update---Author:赵俊夫  Date:20130507 for：需要加name=searchColums属性 -->
    <div name="searchColums" style="float: right; padding-right: 15px;">
        <t:mutiLang langKey="log.level"/>: <!-- update---Author:宋双旺  Date:20130414 for：改变值进行查询 -->
        <select name="operatetype" id="operatetype" onchange="logListsearch();">
            <option value=""><t:mutiLang langKey="select.loglevel"/></option>
            <option value="1"><t:mutiLang langKey="common.login"/></option>
            <option value="2"><t:mutiLang langKey="common.logout"/></option>
            <option value="3"><t:mutiLang langKey="common.insert"/></option>
            <option value="4"><t:mutiLang langKey="common.delete"/></option>
            <option value="5"><t:mutiLang langKey="common.update"/></option>
            <option value="6"><t:mutiLang langKey="common.upload"/></option>
            <option value="7"><t:mutiLang langKey="common.other"/></option>
        </select>
       <%--add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
        <span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="操作时间 "><t:mutiLang langKey="operate.time"/>: </span>
            <input type="text" name="operatetime_begin" style="width: 100px; height: 24px;">~
            <input type="text" name="operatetime_end" style="width: 100px; height: 24px; margin-right: 20px;">
        </span>
        <%--add-end--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
        <%-- <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="logListsearch();"><t:mutiLang langKey="common.query"/></a> --%>
    </div>
</div>
<t:datagrid title="log.manage" name="logList" fitColumns="false" actionUrl="logController.do?datagrid" idField="id" sortName="operatetime" pageSize="100" queryMode="group" sortOrder="desc" extendParams="nowrap:false,onDblClickRow:clickRow,">
	<t:dgCol title="log.level" field="loglevel" hidden="true"></t:dgCol>
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" field="username" query="true" align="center" width="80"></t:dgCol>
	<t:dgCol title="operate.ip" field="ip" align="center" width="120"></t:dgCol>
	<t:dgCol title="操作类型" field="operatetype" align="center" dictionary="operatetype" width="80"></t:dgCol>
	<t:dgCol title="操作对象" field="opObjectName" query="true" align="center" width="180"></t:dgCol>
	<t:dgCol title="operate.time" field="operatetime" align="center" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
	<t:dgCol title="log.content" field="logcontent" align="center" width="600"></t:dgCol>
</t:datagrid>

<div id="viewLogWin" class="easyui-window" title="日志详情" data-options="closed:true,modal:true" style="width:400px;height:360px;">
<div style="margin: 10px;"><span class="right"><label >用户名：&nbsp;&nbsp;&nbsp;</label><input id="userNameTxt" class="txt" type="text" readonly ></span></div>
<div style="margin: 10px;"><span class="right"><label >操作IP：&nbsp;&nbsp;&nbsp;</label><input id="ipTxt" class="txt" type="text" readonly ></span></div>
<div style="margin: 10px;"><span class="right"><label >操作类型：</label><input id="opTypeTxt" type="text" class="txt" readonly ></span></div>
<div style="margin: 10px;"><span class="right"><label >操作对象：</label><input id="opObjTxt" type="text" class="txt" readonly ></span></div>
<div style="margin: 10px;"><span class="right"><label >操作时间：</label><input id="opTimeTxt" type="text" class="txt" readonly ></span></div>
<div style="margin: 10px;"><span class="right"><label >日志内容：</label><textarea id="logContentTxt" rows="5" style="width:260px;" readonly ></textarea></span></div>
</div>
