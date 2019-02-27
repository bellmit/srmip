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
//&approvalStatus=0
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmProjectList" fitColumns="false" title="项目列表" onDblClick="dblclick" pageSize="20" pageList="[20,40,60]" checkbox="true"
	  	actionUrl="tPmProjectController.do?datagridState2&lxStatus=1&projectPlanId=${projectPlanId }" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" view="scrollview">
	   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
	   <t:dgCol title="项目编号"  field="projectNo" queryMode="group"  width="150"></t:dgCol>
	   <t:dgCol title="项目申请号"  field="cxm" queryMode="single" width="160"></t:dgCol>
	   <t:dgCol title="申报项目名称"  field="glxm.projectName" width="120"></t:dgCol>
	   <t:dgCol title="立项项目名称"  field="projectName" query="true" isLike="true" width="120"></t:dgCol>
	   <t:dgCol title="本次分配(元)"  field="dnjf"  query="false"  isLike="true" queryMode="single"  align="right" width="110" formatter1="function(v){return '<font color=red >'+transformAmount(v)+'</font>';}"></t:dgCol>
	   <t:dgCol title="总经费(元)"  field="allFee"  query="true"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
	   <t:dgCol title="已安排(元)"  field="yap"  query="true"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
	   <t:dgCol title="当年经费(元)"  field="budgetAmount"  query="true"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
	   <t:dgCol title="账面余额(元)"  field="zmye"  query="true"  isLike="true" queryMode="single"  align="right" width="110" extendParams="formatter:transformAmount,"></t:dgCol>
	   <t:dgCol title="项目类型" field="xmlx" query="true" isLike="true" queryMode="single" codeDict="0,XMLX" width="60"></t:dgCol>
	   <t:dgCol title="项目密级" field="secretDegree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="70"></t:dgCol>
	   <t:dgCol title="起始日期"  field="beginDate"  query="true"  isLike="true" queryMode="single"  width="160"></t:dgCol>
	   <t:dgCol title="截止日期"  field="endDate"  query="true"  isLike="true" queryMode="single"  width="160"></t:dgCol>
	   <t:dgCol title="责任部门"  field="dutyDepartStr"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="承研部门"  field="devDepartStr" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>  
	   <t:dgCol title="来源单位"  field="sourceUnit" width="120"></t:dgCol>  
	   <t:dgCol title="负责人"  field="projectManager"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="任务指定"  field="rwzd" codeDict="0,RWZD" width="80"></t:dgCol>
	   <t:dgCol title="立项依据"  field="lxyj" codeDict="0,LXYJ" width="80" query="true" isLike="false" queryMode="single" ></t:dgCol>
	   <t:dgCol title="联系人"  field="contact"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="联系电话"  field="contactPhone"  query="true"  isLike="true" queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="会计编码"  field="accountingCode"  query="true"  isLike="true" queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="合同名称"  field="planContractName"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="合同文号"  field="planContractRefNo"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="申报人"  field="projectManager"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
	   <t:dgCol title="项目类别母项"  field="xmml" queryMode="single"  width="100"></t:dgCol>
	   <t:dgCol title="项目类别"  field="xmlbStr" queryMode="single"  width="100"></t:dgCol>
	   <t:dgCol title="经费类型"  field="jflxStr" queryMode="single"  width="100"></t:dgCol>
	   
	   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true" checkFun="function(v,d,index){if(d.lxyj=='1' || d.lxyj=='3'){return true;}else{return false;}}"></t:dgCol>	   
	   <t:dgFunOpt funname="goFpje(id,cxm,allFee,yap)" title="填写分配金额" ></t:dgFunOpt>
	   
	   <t:dgToolBar title="增加项目" icon="icon-edit" funname="assign" ></t:dgToolBar>
	   <t:dgToolBar title="选择项目" icon="icon-edit" funname="selectProject" ></t:dgToolBar>
	   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmProjectController.do?doDelete&jhwjid=${projectPlanId }" funname="deleteALLSelect"></t:dgToolBar>
	   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmProjectController.do?goEditByLx" funname="update" width="100%" height="100%" ></t:dgToolBar>
	   <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goEditForResearchGroup"  funname="detail" width="100%" height="100%"></t:dgToolBar>
	   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportSendXlsByT"></t:dgToolBar>
	   <t:dgToolBar title="导入" icon="icon-put" funname="ImportSendXls"></t:dgToolBar>
	  </t:datagrid>
  </div>
</div>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });

    function ExportSendXlsByT() {
        JeecgExcelExport("tPmProjectController.do?exportXlsByT", "tPmProjectList");
    }
  //导入
    function ImportSendXls() {
        openuploadwin('Excel导入', 'tPmProjectController.do?upload&lxStatus=1&projectPlanId=${projectPlanId }', "tPmProjectList");
    }
  
    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    }
  
    /**

     * 去除千分位

     *@param{Object}num

     */
    function delcommafy(num){
    	 if(!num){
    		 num = "0";
    	 }
    	 num = num+"";
    	 num = num.replace(new RegExp(",","gm"),"");
    	 if(!isNumber(num)){
    		 num = "0";
    	 }
    	 return parseFloat(num);
    }
    


    function goFpje(id,cxm,allFee,yap){
        /* if(cxm==""){
            $.messager.alert('警告','还未选择申报项目，不能填写分配金额！');  
            return false;
        } */
          var url = "tPmIncomePlanController.do?goFpje&id="+id+"&projectPlanId=${projectPlanId}";
	      $.dialog({
			content : 'url:' + url,
	        title : '填写分配金额',
	        lock : true,
	        opacity : 0.3,
	        width : '450px',
	        height : '100px',
	        ok : function() {
	           iframe = this.iframe.contentWindow;
	           var msgText = iframe.getMsgText();
	           var type = iframe.getType();
	           msgText = delcommafy(msgText);
	           if( isNaN(msgText)){
	        	   //alert("所填非数值！");
	        	   $.dialog({
	            		content : "所填非数值！",
	            		title : '错误提示', lock : true, opacity : 0.3,  width : '250px', height : '120px',
	                    ok : function() { }
	                }).zindex();
	        	   return;
	           }
	           if( (msgText*1+yap*1) > allFee ) {
	        	   $.dialog({
	            		content : "当前分配金额+已安排金额之和不允许超出总金额！",
	                    title : '错误提示', lock : true, opacity : 0.3,  width : '350px', height : '120px',
	                    ok : function() { }
	                }).zindex();
	        	   //alert("当前分配鑫额 + 已安排总和 能允许超出总金额！"); 
	        	   return;
	           }
	           
	           var data;
	           if(type==1){
	        	   var fpjeId = iframe.getId();
					data={
						id:fpjeId,
						xmid : id,
		                jhwjid:"${projectPlanId}",
						je:msgText,
		                type:type
					}
			   }else{
				   data={
						   xmid : id,
			                  jhwjid:"${projectPlanId}",
			                  je : msgText,
			                  type:type
						}
				}
	           $.ajax({
	              url : 'tPmIncomePlanController.do?doFpje',
	              data : data,
	               type : 'post',
	               dataType:'json',
	               success : function(data) {
	                  tip(data.msg);
	                  reloadTable();
	               }
	           });
	       },
	       cancel : function() {
				//reloadTable();
	        },
	   }).zindex();
    }

    function assign(){
    	$.dialog({
    		content: 'url:tPmProjectController.do?goAdd&assignFlag=1&entryType=JG&type=1&projectPlanId=${projectPlanId}',
    		width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
    		title:"项目信息",
    		opacity : 0.3,
    		cache:false,
    		zIndex:2000,
    	    cancelVal: '关闭',
    	    cancel: function(){
    	    	reloadtPmProjectList();
    	    }
    	});
    }
    
    function selectProject(){
    	$.dialog({
    		content: 'url:tPmProjectController.do?projectSelect&plan=1&type=1&lx=1&projectPlanId=${projectPlanId}',
    		width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
    		title:"项目信息",
    		opacity : 0.3,
    		cache:false,
    		zIndex:2000,
    		button : [ {name : '确定',callback : selectOkProject,focus : true},   
    			       {name : '取消',callback : function() {}} ]
    	});
    }
    
    function selectOkProject(){
    	iframe = this.iframe.contentWindow;
    	var id=iframe.getprojectListSelections('id')[0];
    	if(id==undefined || id ==''){
    		return;
    	}
    	var data={
				      xmid : id,
	                  jhwjid:"${projectPlanId}",
	                  je : '0',
	                  type:'1'
				};
    	$.ajax({
            url : 'tPmIncomePlanController.do?addSelectLxProject',
            data : data,
             type : 'post',
             dataType:'json',
             success : function(data) {
                tip(data.msg);
                reloadTable();
                tPmProjectListsearch();
             }
         });
    	
    }
    
    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomePlanController.do?goEdit&stage=xd&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomePlanController.do?goEdit&stage=xd&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
     }
  
    //打开弹窗填写修改意见
    function openMsgDialog(id){
        var url = "tPmIncomePlanController.do?goFpje&id="+id;
        var title = "填写修改意见";        
      $.dialog({
      content : 'url:' + url,
              title : '提出修改意见',
              lock : true,
              opacity : 0.3,
              width : '450px',
              height : '120px',
              ok : function() {
                  iframe = this.iframe.contentWindow;
                  var msgText = iframe.getMsgText();
                  var proposeIframe = iframe;
                  $.ajax({
                      url : 'tPmIncomePlanController.do?doPropose',
                      data : {
                          id : id,
                          msgText : msgText
                      },
                      type : 'post',
                      dataType:'json',
                      success : function(data) {
                          tip(data.msg);
                          reloadTable();
                      }
                  });
              },
              cancel : function() {
  	           reloadTable();
              },
          }).zindex();
      }
  
</script>