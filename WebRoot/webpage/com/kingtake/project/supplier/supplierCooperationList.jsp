<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
        <input id="supplierId" type="hidden" value="${supplierId}"> 
		<t:datagrid name="cooperationList" fitColumns="true" title='合作项目' 
			actionUrl="tPmQualitySupplierController.do?datagridForCooperationList&supplierId=${supplierId}" idField="contractId" fit="true" queryMode="group"
			onDblClick="detailTPmQualitySupplierList">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目id" field="projectId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同id" field="contractId" hidden="true" queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="合同编号" field="contractCode"   queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName"   queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="负责人" field="projectManager"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="学校单位" field="applyUnit"  queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同经费" field="totalFunds" queryMode="group" width="60"></t:dgCol>
			<t:dgCol title="签订时间" field="contractSigningTime" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
            <t:dgToolBar title="关联合同" icon="icon-add" funname="goRelatedContract" ></t:dgToolBar>
		</t:datagrid>
    </div>
	</div>
</div>
<script type="text/javascript">
function goRelatedContract(){
        var url ="tPmQualitySupplierController.do?goRelateContractList";
		var dg = W.$.dialog({
			content: 'url:'+url,
			lock : true,
			width : 800,
			height : window.top.document.body.offsetHeight-100,
			parent:windowapi,
			title:"出账合同列表",
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var checked = iframe.getChecked();
		    	if(checked==null){
		    	    return false;
		    	}
		    	var ids = [];
		        for(var i=0;i<checked.length;i++){
		            ids.push(checked[i].contractId);
		        }
		        var supplierId = $("#supplierId").val();
		        $.ajax({
		            url:'tPmQualitySupplierController.do?doRelateContract',
		            cache:false,
		            type:'POST',
		            data:{
		                supplierId:supplierId,
		                ids:ids.join(",")
		                },
		            dataType:'json',
		            success:function(data){
		                tip(data.msg);
		                if(data.success){
		                    dg.close();
		                    $("#cooperationList").datagrid("reload");
		                }
		            }
		        });
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
    
}

</script>
 </body>
 </html>