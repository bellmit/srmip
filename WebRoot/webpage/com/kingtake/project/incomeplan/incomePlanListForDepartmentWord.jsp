<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
//审批状态格式化
function auditStatusFormat(value,row,index){
	  if(value=="0"){
		  return "未提交";
	  }else if(value=="1"){
		  return "待审核";
	  }else if(value=="2"){
		  return "通过";
	  }else if(value=="3"){
		  return '未通过<a href="#" style="cursor: pointer;color:red;" onclick=showMsg("'+row.id+'")>[查看意见]</a>';
    }
}

function ysStatusFormat(value,row,index){
	  if(value=="0"){
		  return "否";
	  }else if(value=="1"){
		  return "是";
	  }
}

//弹出消息
function showMsg(id){
	  $.ajax({
		 url:'tPmIncomePlanController.do?getPropose&id='+id,
		 cache:false,
		 type:'GET',
		 dataType:'json',
		 success:function(data){
			 $.messager.alert('意见',data.msg);
		 }
	  });
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmIncomePlanList" fitColumns="true" title="校内协作（计划类）" actionUrl="tPmIncomePlanController.do?datagridForDepartmentWord" idField="id" fit="true"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目id" field="project.id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联主表id" field="projectPlanId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="project.projectName" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>      
      <t:dgCol title="分配金额" field="planAmount" queryMode="single" width="120"></t:dgCol>            
      <t:dgCol title="分配时间" field="createDate" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核标志" field="approvalStatus" extendParams="formatter:auditStatusFormat," queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="是否已做预算" field="ysStatus" extendParams="formatter:ysStatusFormat," queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol> 
      <t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="生成校内协作经费申请书" funname="exportXnxzWord(id)" /> 
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });     
    
    //导出校内协作经费申请书WORD
    function exportXnxzWord(id) {    	
    	if(id == ""){
    		tip('请选择数据');
    	}else{
    		$.ajax({
                url : 'tPmIncomePlanController.do?createXnxzWord&id=' + id,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                	downloadWord(data.attributes.FileName);
                }
            });
    	}    	
    }
    
    //下载WORD
    function downloadWord(FileName) {
    	JeecgExcelExport("tPmIncomePlanController.do?downloadWord&FileName=" + FileName,"tPmIncomePlanList");
    }
</script>