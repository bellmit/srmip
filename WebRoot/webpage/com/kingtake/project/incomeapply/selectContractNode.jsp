<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
    function initSelectNodeTab() {
        $('#selectNodeList').datagrid({
            url:"tPmIncomeApplyController.do?selectContractNodeList&projectId=${projectId}&cType=RL",
            title : '节点分配',
            fitColumns : true,
            nowrap : true,
            width : 395,
            striped : true,
            remoteSort : false,
            singleSelect:false,
            idField : 'CONTRACTNODEID',
            editRowIndex : -1,
            columns : [ [ {
                field:'ck',
                checkbox:true
            }, {
                field : 'CONTRACTID',
                title : '合同id',
                width : 100,
                hidden : true
            }, {
                field : 'CONTRACTNAME',
                title : '合同名称',
                width : 100
            }, {
                field : 'CONTRACTNODEID',
                title : '合同节点id',
                width : 100,
                hidden : true
            } ,{
                field : 'NODENAME',
                title : '合同节点',
                width : 100
            },{
                field : 'PAYAMOUNT',
                title : '支付金额',
                width : 100
            } ] ],
            pagination : false,
            rownumbers : true,
            onLoadSuccess : function() {
            }
        });
    }
    
    $(function(){
        initSelectNodeTab(); 
    });
    
    function getNodeSelection(){
        var checkData = $("#selectNodeList").datagrid("getChecked");
        return checkData;
    }
</script>
</head>
<body>
  <div class="easyui-layout" data-options="border:false,fit:true" style="height: 305px;width:400px;">
    <div region="center" style="padding: 1px;">
      <div id="selectNodeList"></div>
    </div>
  </div>
</body>
