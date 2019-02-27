<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
    });
    
    function validFormatter(value,row,index){
        if(value=='1'){
            return '有效';
        }else if(value=='0'){
            return '<font color="red">失效</font>';
        }else{
            return '<font color="red">待审</font>';
        }
      }
</script>
</head>
<body>
      <t:datagrid name="tPmQualitySupplierList" checkbox="false" fitColumns="true" title='供方名录信息表' 
			actionUrl="tPmQualitySupplierController.do?datagrid&validFlag=1" idField="id" fit="true" queryMode="group"
			onDblClick="detailTPmQualitySupplierList" >
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="供方名称" field="supplierName" isLike="true" query="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="等级" field="supplierLevel" hidden="true" codeDict="1,GFDJ" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="类别" field="supplierType" codeDict="1,GFLB" query="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="提供产品" field="supplierProduct" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="通信地址" field="supplierAddress" hidden="false" queryMode="single" query="true" overflowView="true" isLike="true" width="120"></t:dgCol>
        <t:dgCol title="邮编" field="supplierPostcode" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="联系电话" field="supplierPhone" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="传真" field="supplierFax" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="联系人" field="supplierContact" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="有效标记" field="validFlag" queryMode="group" width="120" extendParams="formatter:validFormatter,"></t:dgCol>
        <t:dgCol title="失效时间" field="supplierTime" formatter="yyyy-MM-dd" queryMode="group" width="90"></t:dgCol>
        <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
        <%-- <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
        <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
        <t:dgToolBar title="查看" icon="icon-search" url="tPmQualitySupplierController.do?goUpdate" funname="detail" height="400"></t:dgToolBar>
      </t:datagrid>
    </div>
  <script src="webpage/com/kingtake/project/supplier/tPmQualitySupplierList.js"></script>
  <script type="text/javascript">
            function detailTPmQualitySupplierList(rowIndex, rowDate) {
                var title = "查看";
                var url = "tPmQualitySupplierController.do?goUpdate&id=" + rowDate.id + "&load=detail";
                var height = 400;
                createdetailwindow(title, url, null, height);
            }

            $(document).ready(
                    function() {
                        //给时间控件加上样式
                        $("#tPmQualitySupplierListtb").find("input[name='createDate_begin']").attr("class", "Wdate")
                                .attr("style", "height:20px;width:90px;").click(function() {
                                    WdatePicker({
                                        dateFmt : 'yyyy-MM-dd'
                                    });
                                });
                        $("#tPmQualitySupplierListtb").find("input[name='createDate_end']").attr("class", "Wdate")
                                .attr("style", "height:20px;width:90px;").click(function() {
                                    WdatePicker({
                                        dateFmt : 'yyyy-MM-dd'
                                    });
                                });
                        $("#tPmQualitySupplierListtb").find("input[name='updateDate_begin']").attr("class", "Wdate")
                                .attr("style", "height:20px;width:90px;").click(function() {
                                    WdatePicker({
                                        dateFmt : 'yyyy-MM-dd'
                                    });
                                });
                        $("#tPmQualitySupplierListtb").find("input[name='updateDate_end']").attr("class", "Wdate")
                                .attr("style", "height:20px;width:90px;").click(function() {
                                    WdatePicker({
                                        dateFmt : 'yyyy-MM-dd'
                                    });
                                });
                    });

            //导入
            function ImportXls() {
                openuploadwin('Excel导入', 'tPmQualitySupplierController.do?upload', "tPmQualitySupplierList");
            }

            //导出
            function ExportXls() {
                JeecgExcelExport("tPmQualitySupplierController.do?exportXls", "tPmQualitySupplierList");
            }

            //模板下载
            function ExportXlsByT() {
                JeecgExcelExport("tPmQualitySupplierController.do?exportXlsByT", "tPmQualitySupplierList");
            }

            function goAudit(id, index) {
                var url = "tPmQualitySupplierController.do?goAudit&id=" + id;
                createwindow("资质审核详情", url, 800, 400);
            }
        </script>
  <%@ include file="/webpage/com/kingtake/common/toastr/toastr.jsp"%>
</body>
</html>