<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/tPmProjectList.js"></script>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmProjectList" checkbox="true" fitColumns="true" title="审查报批项目列表"
      actionUrl="tPmProjectController.do?datagridForProject&key=reviewApproval" idField="id" 
      fit="true" queryMode="group">
      
	    <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目编号" field="projectNo" query="true" queryMode="single" width="120"></t:dgCol>
		<%-- <t:dgCol title="项目状态" field="projectStatus" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="项目名称" field="projectName" query="true" queryMode="single" width="120"></t:dgCol>
		<%-- <t:dgCol title="项目简介" field="projectAbstract" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="起始日期" field="beginDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
		<t:dgCol title="截止日期" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
		<%-- <t:dgCol title="分管部门" field="manageDepart" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="负责人" field="projectManager" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="负责人电话" field="managerPhone" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="联系人" field="contact" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="联系人电话" field="contactPhone" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="计划合同标志" field="planContractFlag" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目类型" field="projectTypeName" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="外来编号" field="outsideNo" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="经费类型" field="feeType_fundsName" hidden="true" codeDict="1,XMJFLX" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="子类型" field="subType" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="会计编码" field="accountingCode" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="合同计划文号" field="planContractRefNo" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<%-- <t:dgCol title="合同日期" field="contractDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol> --%>
		<%-- <t:dgCol title="合同计划名称" field="planContractName" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="来源单位" field="sourceUnit" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目来源" field="projectSource" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="承研部门" field="devDepartName" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="责任部门" field="dutyDepart" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="经费单列" field="feeSingleColumn" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="项目密级" field="secretDegree" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="是否需要鉴定" field="appraisalFlag" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="总经费" field="allFee" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="所属母项目" field="parentPmProject_projectName" hidden="true" queryMode="group" width="120"></t:dgCol>
	    <t:dgCol title="评审提交状态" field="finishStatus" replace="已提交评审_1,未提交评审_0" query="true" queryMode="single" width="120" style="color:red_0"></t:dgCol>
	    <t:dgCol title="呈批状态" field="approvalstatus" replace="已呈批_1,未呈批_0" query="true" queryMode="single" width="120" ></t:dgCol>
	    
	    <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goUpdateForResearchGroup" 
	    	funname="detail" width="100%" height="100%"></t:dgToolBar>
	    <t:dgToolBar title="专家评审" icon="icon-edit" funname="expertJudge" width="890" height="500"></t:dgToolBar>
	    <t:dgToolBar title="呈批件" icon="icon-edit" funname="approvalDocument"></t:dgToolBar>
	    <t:dgFunOpt operationCode="viewAudit" funname="viewAudit(id)" title="查看专家评审"></t:dgFunOpt>
	    
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
        $("#tPmProjectListtb").find("input[name='beginDate_begin']")
        	.attr("class", "Wdate").attr("style", "height:23px;width:100px;")
        	.click(function() {WdatePicker({dateFmt : 'yyyy-MM-dd'});
        });
        $("#tPmProjectListtb").find("input[name='beginDate_end']")
        	.attr("class", "Wdate").attr("style", "height:23px;width:100px;")
        	.click(function() {WdatePicker({dateFmt : 'yyyy-MM-dd'});
        });
        $("#tPmProjectListtb").find("select[name='finishStatus']").attr("style", "height:23px;");
        $("#tPmProjectListtb").find("select[name='approvalstatus']").attr("style", "height:23px;");
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tPmProjectController.do?upload', "tPmProjectList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmProjectController.do?exportXls", "tPmProjectList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tPmProjectController.do?exportXlsByT", "tPmProjectList");
    }

    //专家评审
    function expertJudge(){
        var rows = $("#tPmProjectList").datagrid("getChecked");
        if(rows.length==0){
            tip('请至少选择一个项目进行专家评审！');
            return false;
        }
        var projectIds = [];
        for(var i=0;i<rows.length;i++){
            projectIds.push(rows[i].id);
        }
        /* add("添加专家评审","tErExpertController.do?goExpertReviewMgr",'','100%','100%'); */ 
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:tErExpertController.do?goExpertReviewMgr&projectIds='+projectIds.join(","),
                //zIndex : 2100,
                title : '添加专家评审',
                lock : true,
                width : window.top.document.body.offsetWidth,
        		height : window.top.document.body.offsetHeight-100,
                left : '0%',
                top : '0%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : function(){
                        var iframe = this.iframe.contentWindow;
                        iframe.saveExpertReview();
                        return false;
                    },
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            }).zindex();
        } else {
            $.dialog({
                content : 'url:tErExpertController.do?goExpertReviewMgr',
                //zIndex : 2100,
                title : '添加专家评审',
                lock : true,
                parent : windowapi,
                width : window.top.document.body.offsetWidth,
        		height : window.top.document.body.offsetHeight-100,
                left : '0%',
                top : '0%',
                opacity : 0.4,
                button : [ {
                    name : '确定',
                    callback : function(){
                        var iframe = this.iframe.contentWindow;
                        iframe.saveExpertReview();
                        return false;
                    },
                    focus : true
                }, {
                    name : '取消',
                    callback : function() {
                    }
                } ]
            }).zindex();
        }
    }
    
   function approvalDocument(){
	   var rows = $("#tPmProjectList").datagrid("getSelections");
       if(rows.length==0){
           tip('请至少选择一个项目进行呈批！');
           return false;
       }
       var projectIds = [];
       var status =true;
       for(var i=0;i<rows.length;i++){
           projectIds.push(rows[i].id);
           if(rows[i].approvalstatus == '1'){
        	   status = false;
           }
       }
       if(status){
       $.dialog({
  			id:'approvalPage',
  			content: 'url:tOApprovalProjectSummaryController.do?goAdd&projectIds='+projectIds.join(","),
  			lock : true,
  			width:900,
  			height: 520,
  			title:'呈批件',
  			cache:false, 
  			okVal:'保存',
			ok:function(){
				iframe = this.iframe.contentWindow;
				var flag = iframe.formCheck();
				saveObj();
				if(flag){
					tip("呈批件已生成，请到收发文管理中处理！");
					reloadtPmProjectList();
				}
				return false;
			},
  		    cancelVal: '关闭',
  		    cancel: true 
  		}).zindex();
       }else{
    	   tip("所选项目中有已呈批项目，请重新选择项目！");
       }
       
   }
   function viewAudit(id){
       alert("查看评审");
   }
   
 window.reloadThis =  function(){
	   reloadtPmProjectList();
   }
 
 function styler(value,row,index){
		if (value=='0'){
			return 'color:red;';
		}
	}

</script>