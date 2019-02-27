<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBBookSecretList" checkbox="false" onDblClick="goDetail()" fitColumns="true" title="专著保密信息表" actionUrl="tBBookSecretController.do?datagrid&type=${type}" idField="id" fit="true"
      queryMode="group" sortName="createDate" sortOrder="desc">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="编号" field="reviewNumber" isLike="true"  queryMode="single" query="true" width="60"></t:dgCol>
      <t:dgCol title="著作名称" field="bookName" queryMode="group" width="170"></t:dgCol>
      <t:dgCol title="第一作者姓名" field="firstAuthor" queryMode="single" query="true" isLike="true"  width="70"></t:dgCol>
      <t:dgCol title="所属院id" field="subordinateDeptId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="所属院系" field="subordinateDeptName" hidden="true" query="false" queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="具体单位id" field="concreteDeptId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="具体单位" field="concreteDeptName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="密级" field="secretDegree" queryMode="group" width="30" codeDict="0,XMMJ"></t:dgCol>
      <t:dgCol title="出版社" field="press" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="发行范围" field="issueScope" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="固定联系电话" field="fixTelephone" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="移动联系电话" field="mobileTelephone" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="其他作者姓名" field="otherAuthor" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="著作内容要点" field="bookContentKey" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="备注" field="memo" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审查状态" field="checkFlag" queryMode="group" codeDict="1,SCZT" width="40"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="140" frozenColumn="true"></t:dgCol>
      <!-- 表格操作列 -->
      <!-- type=1表示机关用户，能看到审查按钮 -->
      <c:if test="${type == '1'}">
        <t:dgFunOpt exp="checkFlag#eq#0" funname="checkBook(id)" title="审查"></t:dgFunOpt>
      </c:if>
      <c:if test="${type != '1'}">
        <t:dgFunOpt exp="checkFlag#eq#0" funname='goEdit(id)' title="编辑"></t:dgFunOpt>
      </c:if>
      <t:dgDelOpt exp="checkFlag#eq#0" title="删除" url="tBBookSecretController.do?doDel&id={id}" />
      <t:dgFunOpt funname="exportTemplate(id)" title="导出"></t:dgFunOpt>      
      <!-- 表格上方按钮 -->
      <!-- type!=1表示课题组用户，需要录入按钮 -->
      <c:if test="${type != '1'}">
        <t:dgToolBar title="录入" icon="icon-add" url="tBBookSecretController.do?goEdit" funname="add" width="650" height="540"></t:dgToolBar>
      </c:if>
      <c:if test="${type == '1'}">
        <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
        <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
      </c:if>
      <t:dgToolBar title="查看" icon="icon-search" url="tBBookSecretController.do?goEdit" funname="detail" width="650" height="540"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/booksecret/tBBookSecretList.js"></script>
<script type="text/javascript">
  //导出
  function ExportXls() {
    JeecgExcelExport("tBBookSecretController.do?exportXls", "tBBookSecretList");
  }

  //模板导出
  function exportTemplate(id) {
    $.dialog({
      content : 'url:tBBookSecretController.do?goAudit&id=' + id,
      lock : true,
      width : window.top.document.body.offsetWidth,
      height : window.top.document.body.offsetHeight - 100,
      left : '0%',
      title : "导出",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {

      }
    });
  }
  //编辑
  function goEdit(id) {
    $.dialog({
      content : 'url:tBBookSecretController.do?goEdit&id=' + id,
      lock : true,
      width : '650px',
      height : '540px',
      title : "修改",
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }
  //查看
  function goDetail() {
    var rows = $("#tBBookSecretList").datagrid("getSelections");
    if (rows.length == 0) {
      tip("请至少选择一条数据进行查看！");
      return false;
    }
    var id = rows[0].id;
    $.dialog({
      content : 'url:tBBookSecretController.do?goEdit&id=' + id + '&load=detail&checkFlag=${type}',
      lock : true,
      width : '650px',
      height : '540px',
      title : "查看",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }

  //机关审查(checkFlag=1，其它值均为编辑)
  function checkBook(id) {
    $.dialog({
      content : 'url:tBBookSecretController.do?goEdit&checkFlag=1&id=' + id,
      lock : true,
      width : '630px',
      height : '540px',
      title : "专著保密审查",
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal : '关闭',
      cancel : function() {

      }
    });
  }
  
//模板下载
  function ExportXlsByT() {
  	JeecgExcelExport("tBBookSecretController.do?exportXlsByT","tBBookSecretList");
  }
  
//导入
  function ImportXls() {
  	openuploadwin('Excel导入', 'tBBookSecretController.do?upload', "tBBookSecretList");
  }
</script>