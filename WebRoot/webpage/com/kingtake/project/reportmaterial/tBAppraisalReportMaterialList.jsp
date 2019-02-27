<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAppraisalReportMaterialList" fitColumns="true" title="上报材料信息表" actionUrl="tBAppraisalReportMaterialController.do?datagridForApply&operateStatus=${operateStatus}" idField="id" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="水平评价"  field="levelEvaluation"  codeDict="1,SPPJ"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="批准时间"  field="approveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定证书年度"  field="certificateYear"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定证书下达机关"  field="certificateFromUnit"  codeDict="1,ZSXDDW"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定证书号"  field="certificateNumber"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证书领取人"  field="certificateReceiptor"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证书领取时间"  field="receiptorReceiveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查标志"  field="checkFlag" codeDict="1,SPZT"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查人id"  field="checkUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查人姓名"  field="checkUsername"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查时间"  field="checkDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="消息内容"  field="msgText"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="关联鉴定计划id"  field="appraisalProjectId"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt exp="checkFlag#eq#1" title="审查" funname="audit(id)" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tBAppraisalReportMaterialController.do?goUpdateForDepart" funname="detail"></t:dgToolBar>
   <t:dgFunOpt exp="checkFlag#eq#2" title="查看审核" funname="view(id)"></t:dgFunOpt>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/reportmaterial/tBAppraisalReportMaterialList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBAppraisalReportMaterialListtb").find("input[name='approveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='approveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='receiptorReceiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='receiptorReceiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='checkDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='checkDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalReportMaterialListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
        function audit(id) {
            var auditD = $.dialog({
                content : 'url:tBAppraisalReportMaterialController.do?goUpdateForDepart&id=' + id,
                title : '鉴定材料审查',
                lock : true,
                opacity : 0.3,
                width : '700px',
                height : '400px',
                okVal : '通过',
                ok : function() {
                    var dialog = this;
                    iframe = this.iframe.contentWindow;
                    $.dialog.confirm('确定通过吗?', function(r) {
                    if(r){
                    $.ajax({
                        url : 'tBAppraisalReportMaterialController.do?doAudit&id=' + id,
                        type : 'post',
                        dataType : 'json',
                        success : function(data) {
                            tip(data.msg);
                            if(data.success){
                            dialog.close();
	                        reloadTable();
                            }
                        }
                    });
                    }
                    });
                    return false;
                },
                button : [ {
                    name : '驳回',
                    callback : function(data) {
                        $.dialog({
                            content : 'url:tBAppraisalApplyController.do?goPropose&type=3&id=' + id,
                            title : '填写修改意见',
                            lock : true,
                            opacity : 0.3,
                            width : '450px',
                            height : '120px',
                            ok : function() {
                                var dialog = this;
                                iframe = this.iframe.contentWindow;
                                var msgText = iframe.getMsgText();
                                var proposeIframe = iframe;
                                $.ajax({
                                    url : 'tBAppraisalReportMaterialController.do?doPropose',
                                    data : {
                                        id : id,
                                        msgText : msgText
                                    },
                                    type : 'post',
                                    success : function(data) {
                                        data = $.parseJSON(data);
                                        tip(data.msg);
                                        if(data.success){
                                            reloadTable();
                                            auditD.close();
                                            dialog.close();
                                        }
                                    }
                                });
                            },
                            cancel : function() {
                                reloadTable();
                            },
                        }).zindex();
                        return false;
                    }
                } ],
                cancel : function() {
                    reloadTable();
                },
            });
        }

        //导入
        function ImportXls() {
            openuploadwin('Excel导入', 'tBAppraisalReportMaterialController.do?upload', "tBAppraisalReportMaterialList");
        }

        //导出
        function ExportXls() {
            JeecgExcelExport("tBAppraisalReportMaterialController.do?exportXls", "tBAppraisalReportMaterialList");
        }

        //模板下载
        function ExportXlsByT() {
            JeecgExcelExport("tBAppraisalReportMaterialController.do?exportXlsByT", "tBAppraisalReportMaterialList");
        }
        
        function goDetail(rowIndex, rowData){
            var url = "tBAppraisalReportMaterialController.do?goUpdateForDepart";
            var id = rowData.id;
            url = url +"&id="+id+"&load=detail";
            createdetailwindow("查看", url,null,null);
        }
        
        function view(id) {
    		$.dialog({
    					content : 'url:tBAppraisalReportMaterialController.do?goUpdateForDepart&id='
    							+ id,
    					title : '查看鉴定材料',
    					lock : true,
    					opacity : 0.3,
    					width : window.top.document.body.offsetWidth,
    					height : window.top.document.body.offsetHeight - 100,
    					cancel : function() {
    						reloadTable();
    					},
    				});
    	}
    </script>