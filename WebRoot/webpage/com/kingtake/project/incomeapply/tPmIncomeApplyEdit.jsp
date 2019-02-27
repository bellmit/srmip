<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目来款申请信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    var isNew = false;
    $(function() {
        $("#cc").combobox({
            onLoadSuccess : function() {
                if (isNew) {
                    var data = $("#cc").combobox("getData");
                    if (data.length > 0) {
                        $("#cc").combobox("setValue", data[0].id);
                    }
                }
            },
            onChange : function(newValue, oldValue) {
                $.ajax({
                    url : 'tPmIncomeApplyController.do?getInvoice',
                    type : 'GET',
                    dataType : 'json',
                    cache : 'false',
                    data : {
                        'invoiceId' : newValue
                    },
                    success : function(data) {
                        $("#applyAmount").numberbox('setValue', data.invoiceAmount);
                    }
                });
                $("#invoiceId").val(newValue);
            }
        });
        
        //垫支科目代码下拉选择框事件
        $("#payfirstId").combobox({
            onLoadSuccess : function() {
            	if($("#payfirstId").val() != ""){
            		$("#payfirstFundsLabel").css('visibility','visible');
                	$("#payfirstFundsText").css('visibility','visible');
            	}
            },
            onChange : function(newValue, oldValue) {
            	$("#payfirstFundsLabel").css('visibility','visible');
            	$("#payfirstFundsText").css('visibility','visible');
            }
        });

        //初始化表格
        var planContractFlag = $("#planContractFlag").val();
        if (planContractFlag == '2') {
            initTab();
        }
        
        //判断是否有子项目，有的话则显示
        var hasSubprojectFlag = $("#hasSubprojectFlag").val();
        if (hasSubprojectFlag == 'true') {
        	initschoolCooperationTab();
        	$('#schoolCooperationList').css("display","block");
        }else{
        	$('#schoolCooperationList').css("display","none");
        }
        
        //判断是否校内协作
        var schoolCooperationFlag = $("#schoolCooperationFlag").val();
        if (schoolCooperationFlag == '1') {
        	$("#fundsSources").val('2');
        }else{
        	$("#fundsSources").val('0');
        } 
    });

    //编写自定义JS代码
    function uploadFile(data) {
    	if(W.$.dialog.list['processDialog'] != undefined){
    		var win = W.$.dialog.list['processDialog'].content;
            win.reloadTable();
            win.tip(data.msg);
    	}            
            if(data.success){
               frameElement.api.close();
           }
    }
    
    function uploadCallback(){
        var win = W.$.dialog.list['processDialog'].content;
        win.reloadTable();
        win.tip("保存数据成功!");
        frameElement.api.close();
    }
    
    //打开弹出框
    function openIncomeWin() {
        var projectId = $("#projectId").val();
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                /* content : 'url:tPmIncomeApplyController.do?incomeAllotList&select=1&projectId=' + projectId, */
                content : 'url:tPmContractNodeController.do?tPmIncomeApplyNodeList&contractId=index&projectId=' + projectId,
                lock : true,
                width : '800px',
                height : '400px',
                top : '0%',
                title : "选择到账信息",
                opacity : 0.3,
                cache : false,
                ok : function() {
                	 iframe = this.iframe.contentWindow;
                     /* var voucherNo = iframe.gettPmIncomeListSelections("certificate");
                     var incomeId = iframe.gettPmIncomeListSelections("id");
                     var incomeAmount = iframe.gettPmIncomeListSelections("incomeAmount");
                     $("#voucherNo").val(voucherNo);
                     $("#incomeId").val(incomeId);
                     $("#incomeAmount").numberbox('setValue',incomeAmount); */
                	 var je = iframe.getJe();
                     $("#applyAmount").numberbox('setValue',je);
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                /* content : 'url:tPmIncomeApplyController.do?incomeAllotList&select=1&projectId=' + projectId, */
                content : 'url:tPmContractNodeController.do?tPmIncomeApplyNodeList&contractId=index&projectId=' + projectId,
                lock : true,
                width : '800px',
                height : '400px',
                top : '0%',
                title : "选择到账信息",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    /* var voucherNo = iframe.gettPmIncomeListSelections("certificate");
                    var incomeId = iframe.gettPmIncomeListSelections("id");
                    var incomeAmount = iframe.gettPmIncomeListSelections("incomeAmount");
                    $("#voucherNo").val(voucherNo);
                    $("#incomeId").val(incomeId);
                    $("#incomeAmount").numberbox('setValue',incomeAmount); */
                    var je = iframe.getJe();
                    $("#applyAmount").numberbox('setValue',je);
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
    }
    //
    function clearIncome() {
        $("#voucherNo").val('');
    }

    //弹出新增发票窗口
    function addInvoice() {
        var projectId = $("#projectId").val();
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:tPmIncomeApplyController.do?addInvoice&projectId=' + projectId,
                lock : true,
                width : '480px',
                height : '400px',
                top : '10%',
                title : "新增发票",
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    isNew = true;
                    return false;
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                content : 'url:tPmIncomeApplyController.do?addInvoice&projectId=' + projectId,
                lock : true,
                width : '500px',
                height : '450px',
                top : '10%',
                title : "新增发票",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    isNew = true;
                    return false;
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }

    }

    //加载发票信息
    function reloadInvoice() {
        $("#cc").combobox("reload");
    }

    //初始化成员表格
    function initTab() {
        var toolbar = [];
        toolbar = [ {
            iconCls : 'icon-add',
            text : '指定合同节点',
            handler : function() {
                openSelectNodeWin();
            }
        }, {
            iconCls : 'icon-remove',
            text : '删除',
            handler : function() {
                var checked = $("#nodeList").datagrid("getSelections");
                for (var i = 0; i < checked.length; i++) {
                    var index = $("#nodeList").datagrid('getRowIndex', checked[i]);
                    $("#nodeList").datagrid("deleteRow", index);
                }
            }
        } ];
        var incomeApplyId = $("#id").val();
        var url = "";
        if (incomeApplyId != "") {
            url = 'tPmIncomeApplyController.do?getNodeList&incomeApplyId=' + incomeApplyId;
        }
        $('#nodeList').datagrid({
            url : url,
            title : '节点分配',
            fitColumns : true,
            nowrap : true,
            height : 200,
            //width : 900,
            striped : true,
            remoteSort : false,
            idField : 'id',
            editRowIndex : -1,
            singleSelect : true,
            toolbar : toolbar,
            columns : [ [ {
                field : 'id',
                title : 'id',
                width : 100,
                hidden : true
            }, {
                field : 'nodeName',
                title : '合同节点',
                width : 100,
                formatter:function(value,row,index){
                    return '<a href="#" style="color:blue;" onclick=viewContract("'+row.contractId+'")>'+value+'</a>';
                }
            }, {
                field : 'contractId',
                title : '合同id',
                width : 100,
                hidden : true
            },{
                field : 'contractNodeId',
                title : '合同节点id',
                width : 100,
                hidden : true
            }, {
                field : 'nodeAmount',
                title : '节点指定金额',
                width : 100,
                editor : {
                    type : 'numberbox',
                    options : {
                        min : 0,
                        precision : '2',
                        groupSeparator : ','
                    }
                },
            }, {
                field : 'remark',
                title : '备注',
                width : 250,
                editor : {
                    type : 'text'
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
                var auditStatus = $("#auditStatus").val();
                if (auditStatus == '1'||auditStatus == '2'||location.href.indexOf("load=detail")!=-1) {
                    $("a.jeecgDetail").hide();
                    $('div.datagrid-toolbar').hide();
                }
            }
        });
    }

    //打开选择合同节点窗口
    function openSelectNodeWin() {
        var projectId = $("#projectId").val();
        var url = 'tPmIncomeApplyController.do?selectContractNode&projectId=' + projectId;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:' + url,
                lock : true,
                width : '400px',
                height : '370px',
                top : '0%',
                title : "选择合同节点",
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var selection = iframe.getNodeSelection();
                    for (var i = 0; i < selection.length; i++) {
                        $("#nodeList").datagrid("appendRow", {
                            id : '',
                            contractNodeId : selection[i].CONTRACTNODEID,
                            nodeName : selection[i].NODENAME+"("+selection[i].CONTRACTNAME+")",
                            nodeAmount : selection[i].PAYAMOUNT,
                            contractId : selection[i].CONTRACTID,
                            remark : ''
                        });
                    }
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                content : 'url:' + url,
                lock : true,
                width : '400px',
                height : '370px',
                top : '0%',
                title : "选择合同节点",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var selection = iframe.getNodeSelection();
                    for (var i = 0; i < selection.length; i++) {
                        $("#nodeList").datagrid("appendRow", {
                            id : '',
                            contractNodeId : selection[i].CONTRACTNODEID,
                            nodeName : selection[i].NODENAME+"("+selection[i].CONTRACTNAME+")",
                            nodeAmount : selection[i].PAYAMOUNT,
                            contractId : selection[i].CONTRACTID,
                            remark : ''
                        });
                    }
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
    }
    
    //点击查看合同
    function viewContract(id){
        var url = "tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false&load=detail&id="+id;
        createdetailwindow("查看合同及节点信息", url,750,600);
    }

    //获取表格中的值
    function checkData() {
    	
    	var applyAmount = $("#applyAmount").numberbox("getValue");
    	var incomeAmount = $("#incomeAmount").numberbox("getValue");
    	if(parseFloat(applyAmount)>parseFloat(incomeAmount)){
    		tip("认领金额不能大于来款金额！");
    		return false;
    	}
        if ($('#nodeList').length > 0) {
            var rows = $('#nodeList').datagrid("getRows");
//             if (rows.length == 0) {
//                 tip("请先指定合同节点！");
//                 return false;
//             }
            var delRows = [];
            for (var i = 0; i < rows.length; i++) {
                var editors = $('#nodeList').datagrid("getEditors", i);
                if (editors.length == 0) {
                    if (rows[i].nodeAmount == "") {
                        delRows.push(i);
                    }
                    continue;
                }
                if (!$('#nodeList').datagrid('validateRow', i)) {
                    return false;
                }
                $('#nodeList').datagrid("endEdit", i);
            }
            for (var i = 0; i < delRows.length; i++) {
                $('#nodeList').datagrid("deleteRow", delRows[i]);
            }
            rows = $('#nodeList').datagrid("getRows");
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total = total + parseFloat(rows[i].nodeAmount);
            }
            
            applyAmount = applyAmount.replace(",","");
            if (parseFloat(applyAmount) < total) {
                tip("分配金额合计大于认领金额！");
                return false;
            }
            var nodeListStr = JSON.stringify(rows);
            $("#nodeListStr").val(nodeListStr);
        }
                              
        //判断直接经费、间接经费和归垫经费加起来是否超过了认领金额
        var directFunds = parseFloat($("#directFunds").numberbox("getValue"));
        var indirectFunds = parseFloat($("#indirectFunds").numberbox("getValue"));
        var payfirstFunds = $("#payfirstFunds").numberbox("getValue") == "" ? 0 : parseFloat($("#payfirstFunds").numberbox("getValue"));
        //判断归垫经费是否大于所选的垫支科目代码的金额
        var payfirstFundsBz = "true";
        if(payfirstFunds != 0){
        	var payfirstId = $("input[name='payfirstId']").val();
        	$.ajax({
                url : 'tPmIncomeApplyController.do?checkPayfirst',
                type : 'GET',
                dataType : 'json',
                cache : 'false',
                async: false,
                data : {
                    'payfirstId' : payfirstId,
                    'payfirstFunds' : payfirstFunds
                },
                success : function(data) {
                    if(data.msg != "OK"){
                    	payfirstFundsBz = "false";
                    }
                }
            });
        }
        if(payfirstFundsBz == "false"){
        	tip("归垫经费大于所选的垫支科目代码的金额！");
            return false;
        }
        
        var totalFunds = directFunds + indirectFunds + payfirstFunds;
        if (parseFloat(applyAmount) < totalFunds) {
            tip("经费构成合计大于认领金额！");
            return false;
        }
        
//         $('#nodeList').length > 0
//         var rows = $('#schoolCooperationList').datagrid("getRows");
// 		alert($('#schoolCooperationList').css('display'));
        if ($('#schoolCooperationList').css('display') == "block") {
            var rows = $('#schoolCooperationList').datagrid("getRows");
            var delRows = [];
            for (var i = 0; i < rows.length; i++) {
                var editors = $('#schoolCooperationList').datagrid("getEditors", i);
                if (editors.length == 0) {
                    if (rows[i].APPLYAMOUNT == "" || rows[i].APPLYAMOUNT == null) {
                        delRows.push(i);
                    }
                    continue;
                }
                if (!$('#schoolCooperationList').datagrid('validateRow', i)) {
                    return false;
                }
                $('#schoolCooperationList').datagrid("endEdit", i);
            }
            for (var i = 0; i < delRows.length; i++) {
                $('#schoolCooperationList').datagrid("deleteRow", delRows[i]);
            }
            rows = $('#schoolCooperationList').datagrid("getRows");
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total = total + parseFloat(rows[i].APPLYAMOUNT);
            }
            
            applyAmount = applyAmount.replace(",","");
            if (parseFloat(applyAmount) < total + totalFunds) {
                tip("经费构成合计大于认领金额！");
                return false;
            }

            var schoolCooperationListStr = JSON.stringify(rows);
            $("#schoolCooperationListStr").val(schoolCooperationListStr);
        }               
        
        //判断预留金额加起来是否超过了认领金额
        var ylFlag = $("#ylFlag").val();
        if(ylFlag == "true"){
        	/* var universityAmount = parseFloat($("#universityAmount").numberbox("getValue")); */
            var academyAmount = parseFloat($("#academyAmount").numberbox("getValue"));
            var departmentAmount = parseFloat($("#departmentAmount").numberbox("getValue"));
            var performanceAmount = parseFloat($("#performanceAmount").numberbox("getValue"));
            var indirectFunds = parseFloat($("#indirectFunds").numberbox("getValue"));            
            var totalAmount = academyAmount + departmentAmount + performanceAmount;
            if (parseFloat(indirectFunds) < totalAmount) {
                tip("预留金额模块合计大于间接经费！");
                return false;
            }
        }        
    }
    
    function openDialog(msgText){
        W.$.dialog({
			content: '<textarea id="msgTextArea" rows="5" cols="5" style="width: 327px; height: 188px;" readonly="true">'+msgText+'</textarea>',
			lock : true,
			width:350,
			height:200,
			parent:windowapi,
			title:'查看修改意见',
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
    }
    
    //查看发票
    function showInvoice(invoiceId){
    	var url = "tBPmInvoiceController.do?goEdit&id="+invoiceId+"&load=detail";
    	var width = 400;
    	var height = 500;
    	var title = "查看发票";
    	createdetailwindow(title, url,width,height);
    }
    
    //初始化校内协作经费表格
    function initschoolCooperationTab() {
//         var toolbar = [];
//         toolbar = [ {
//             iconCls : 'icon-add',
//             text : '录入',
//             handler : function() {
//             	openSelectSchoolCooperationWin();
//             }
//         }, {
//             iconCls : 'icon-remove',
//             text : '删除',
//             handler : function() {
//                 var checked = $("#schoolCooperationList").datagrid("getSelections");
//                 for (var i = 0; i < checked.length; i++) {
//                     var index = $("#schoolCooperationList").datagrid('getRowIndex', checked[i]);
//                     $("#schoolCooperationList").datagrid("deleteRow", index);
//                 }
//             }
//         } ];		
        var projectId = $("#projectId").val();
        var id = $("#id").val();
        var url = "";
        if (projectId != "") {
            url = 'tPmProjectController.do?getSubprojectList&projectId=' + projectId + '&applyId=' + id;
        }
        $('#schoolCooperationList').datagrid({
            url : url,
            title : '校内协作经费',
            fitColumns : true,
            nowrap : true,
            height : 200,
            //width : 900,
            striped : true,
            remoteSort : false,
            idField : 'id',
            editRowIndex : -1,
            singleSelect : true,
//             toolbar : toolbar,
            columns : [ [ {
                field : 'APPLYID',
                title : 'applyId',
                width : 100,
                hidden : true
            }, {
                field : 'PROJECTNAME',
                title : '项目名称',
                width : 100
            }, {
                field : 'PROJECTID',
                title : '项目id',
                width : 100,
                hidden : true
            },{
                field : 'APPLYAMOUNTSUM',
                title : '已分配金额',
                width : 100
            }, {
                field : 'APPLYAMOUNT',
                title : '分配金额',
                width : 100,
                editor : {
                    type : 'numberbox',
                    options : {
                        min : -99999999.99,
                        precision : '2',
                        groupSeparator : ','
                    }
                },
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
                var auditStatus = $("#auditStatus").val();
                if (auditStatus == '1'||auditStatus == '2'||location.href.indexOf("load=detail")!=-1) {
                    $("a.jeecgDetail").hide();
                    $('div.datagrid-toolbar').hide();
                }
            }
        });
    }
    
    //打开录入校内协作页面
//     function openSelectSchoolCooperationWin() {
//         var projectId = $("#projectId").val();
//         var url = 'tPmIncomeApplyController.do?goSchoolCooperation';
//         if (typeof (windowapi) == 'undefined') {
//             $.dialog({
//                 content : 'url:' + url,
//                 lock : true,
//                 width : '400px',
//                 height : '270px',
//                 top : '20%',
//                 title : "新增校内协作经费",
//                 opacity : 0.3,
//                 cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     var schoolCooperationInfo = iframe.getSchoolCooperationInfo();
//                         $("#schoolCooperationList").datagrid("appendRow", {
//                             id : '',
//                             projectName : schoolCooperationInfo[1],
//                             projectId : schoolCooperationInfo[0],
//                             applyAmount : schoolCooperationInfo[2]
//                         });
//                 },
//                 cancelVal : '关闭',
//                 cancel : function() {
//                 }
//             });
//         } else {
//             W.$.dialog({
//                 content : 'url:' + url,
//                 lock : true,
//                 width : '400px',
//                 height : '270px',
//                 top : '20%',
//                 title : "新增校内协作经费",
//                 opacity : 0.3,
//                 parent : windowapi,
//                 cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     var schoolCooperationInfo = iframe.getSchoolCooperationInfo();
//                     $("#schoolCooperationList").datagrid("appendRow", {
//                         id : '',
//                         projectName : schoolCooperationInfo[1],
//                         projectId : schoolCooperationInfo[0],
//                         applyAmount : schoolCooperationInfo[2]
//                     });
//                 },
//                 cancelVal : '关闭',
//                 cancel : function() {
//                 }
//             });
//         }
//     }
</script>
</head>
<body>
  <div style="">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tPmIncomeApplyController.do?doSave" tiptype="1" callback="@Override uploadFile" beforeSubmit="checkData">
      <input id="id" name="id" type="hidden" value="${tPmIncomeApplyPage.id }">
      <input id="projectId" name="project.id" type="hidden" value="${tPmIncomeApplyPage.project.id }">
      <input id="applyYear" name="applyYear" type="hidden" value="${tPmIncomeApplyPage.applyYear }">
      <input id="createBy" name="createBy" type="hidden" value="${tPmIncomeApplyPage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tPmIncomeApplyPage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tPmIncomeApplyPage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tPmIncomeApplyPage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tPmIncomeApplyPage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tPmIncomeApplyPage.updateDate }">
      <input id="planContractFlag" type="hidden" value="${planContractFlag}">
      <input id="hasSubprojectFlag" type="hidden" value="${hasSubprojectFlag}">
      <input id="schoolCooperationFlag" type="hidden" value="${schoolCooperationFlag}">
      <input id="ylFlag" type="hidden" value="${ylFlag}">
      <table style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right" ><label class="Validform_label"> 到账凭证号: </label> <font color="red">*</font></td>
          <td class="value" colspan="3">
          <input id="voucherNo" name="voucherNo" type="text" datatype="*1-50" style="width: 250px" value='${tPmIncomeApplyPage.voucherNo}'>
          <input id="incomeId" name="incomeId" type="hidden"  value='${tPmIncomeApplyPage.incomeId}'>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">到账凭证号</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 发票信息: </label> </td>
          <td class="value" colspan="3"><input id="cc" class="easyui-combobox" value="${tPmIncomeApplyPage.invoice.id}" style="width: 256px;"
              data-options="valueField:'id',editable:false,textField:'text',url:'tPmIncomeApplyController.do?getInvoiceList&projectId=${tPmIncomeApplyPage.project.id}'" /> <input id="invoiceId" name="invoice.id"
              type="hidden" value="${tPmIncomeApplyPage.invoice.id}" > <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" title="新增发票"
              onclick="addInvoice();"></a>
              <c:if test="${!empty tPmIncomeApplyPage.invoice.id}"><a href="#" onclick='showInvoice("${tPmIncomeApplyPage.invoice.id}")' style="cursor: pointer;text-decoration: underline; ">查看发票</a></c:if>
               <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发票信息</label></td>
        </tr>
        <tr>
        <td align="right" width="120px"><label class="Validform_label"> 认领金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="applyAmount" name="applyAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox" readonly="readonly"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.applyAmount}'>
          <a class="easyui-linkbutton"
              data-options="plain:true" style="border: solid 1px;" onclick="openIncomeWin();">选择</a> <a class="easyui-linkbutton" data-options="plain:true" style="border: solid 1px;"
              onclick="clearIncome();">清空</a>
              <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">认领金额</label></td>
        <td align="right" width="120px"><label class="Validform_label"> 来款金额(元): </label> </td>
          <td class="value"><input id="incomeAmount" name="incomeAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox" readonly="readonly" 
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.incomeAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">来款金额</label></td>
        </tr>       
        <tr>
          <td align="right" width="100px"><label class="Validform_label"> 认领时间: </label> </td>
          <td class="value"><input id="incomeTime" name="incomeTime" type="text" datatype="date" style="width: 250px;background-color: #FFFFCC;" readonly="readonly" 
              value='<fmt:formatDate value='${tPmIncomeApplyPage.incomeTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
              style="display: none;">认领时间</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 申请人: </label></td>
          <td class="value"><input id="applyUser" name="applyUser" type="text" style="width: 250px" value='${tPmIncomeApplyPage.applyUser}' ignore="ignore" datatype="*1-25"> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申请人</label></td>
          <td align="right"><label class="Validform_label"> 申请单位: </label></td>
          <td class="value"><input id="applyDept" name="applyDept" type="text" style="width: 250px" value='${tPmIncomeApplyPage.applyDept}' ignore="ignore" datatype="*1-25"> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">申请单位</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 来款说明: </label></td>
          <td class="value" colspan="3"><textarea id="incomeRemark" name="incomeRemark" placeholder="请填写来款说明" datatype="byterange" max="4000" min="0" style="width: 500px" class="inputxt" rows="4">${tPmIncomeApplyPage.incomeRemark}</textarea>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来款说明</label></td>
          <td align="right"><label class="Validform_label"> </label></td>
          <td class="value"></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tPmIncomeApplyPage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tPmIncomeApplyPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmIncomeApply" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
      </table>
      <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">经费构成</span>
      </legend>
      <div>
      <table style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      	<tr>
        	<td align="right"><label class="Validform_label"> 经费来源: </label><font color="red">*</font></td>
          	<td class="value">      
          		<t:codeTypeSelect name="fundsSources" type="select" codeType="1" code="JFLY" id="fundsSources" labelText="请选择" extendParam="{datatype:'*'; style:'width:255px;'; disabled:'true'}" ></t:codeTypeSelect> 
          		<span class="Validform_checktip"></span> 
          		<label class="Validform_label" style="display: none;">经费来源</label>   		
            </td>
        </tr>
      	<tr>
        <td align="right" width="120px"><label class="Validform_label"> 直接经费(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="directFunds" name="directFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.directFunds}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">直接经费</label></td>
        <td align="right" width="120px"><label class="Validform_label"> 间接经费(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="indirectFunds" name="indirectFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.indirectFunds}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">间接经费</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 垫支科目代码: </label> </td>
          <td class="value">
          	<input id="payfirstId" name="payfirstId" class="easyui-combobox" value="${tPmIncomeApplyPage.payfirstId}" data-options="valueField:'id',editable:false,textField:'text',url:'tPmPayfirstController.do?getPayFirstList&projectId=${tPmIncomeApplyPage.project.id}'" style="width: 256px;" /> 
<%--           	<input id="payfirstId" name="payfirstId" type="hidden" value="${tPmIncomeApplyPage.payfirstId}" >          --%>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">垫支科目代码</label></td>
          <td align="right" width="120px" id="payfirstFundsLabel" style="visibility:hidden;"><label class="Validform_label"> 归垫经费(元): </label></td>
          <td class="value" id="payfirstFundsText" style="visibility:hidden;">
          	<input id="payfirstFunds" name="payfirstFunds" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" min="1" value='${tPmIncomeApplyPage.payfirstFunds}'> 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">归垫经费</label></td>
        </tr>
      </table> 
      <div style="margin: 10px auto; width: 900px;">
        <div id="schoolCooperationList" style="display:none;"></div>
      </div>       
      </div>
    </fieldset>    
    <c:if test="${ylFlag eq true}">
    <fieldset id="ylfs" style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">预留金额模块</span>
      </legend>
      <div>
      <table style="width: 100%; margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      	<tr>
        <%-- <td align="right" width="150px"><label class="Validform_label"> 大学预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="universityAmount" name="universityAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.universityAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">大学预留金额</label></td> --%>
        <td align="right" width="150px"><label class="Validform_label"> 院预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="academyAmount" name="academyAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.academyAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">院预留金额</label></td>
        </tr>
        <tr>
        <td align="right" width="150px"><label class="Validform_label"> 系预留金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="departmentAmount" name="departmentAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.departmentAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">系预留金额</label></td>
        <td align="right" width="150px"><label class="Validform_label"> 绩效奖励金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="performanceAmount" name="performanceAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.performanceAmount}'> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">绩效奖励金额</label></td>
        </tr>
      </table>      
      </div>
    </fieldset> 
    </c:if>  
      <input id="schoolCooperationListStr" name="schoolCooperationListStr" type="hidden">
      <input id="nodeListStr" name="nodeListStr" type="hidden">
    </t:formvalid>
    <c:if test="${planContractFlag eq '2'}">
      <div style="margin: 10px auto; width: 900px;">
        <div id="nodeList"></div>
      </div>
    </c:if>
  </div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>