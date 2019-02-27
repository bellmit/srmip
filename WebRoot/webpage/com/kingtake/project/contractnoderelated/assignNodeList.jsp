<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmNodeList"  fitColumns="true" 
			actionUrl="tPmContractNodeRelatedController.do?assignDatagrid&tpid=${tpid}&contractNodeId=${contractNodeId}&inOutFlag=${inOutFlag}" idField="id" fit="true" queryMode="group"
			sortName="createDate" sortOrder="desc" pagination="false" title="节点指定" >
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<c:if test="${inOutFlag eq 'i'}">
			<t:dgCol title="来款时间" field="Time" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="来款金额(元)" field="Amount" align="right" queryMode="group" width="80" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="可指定金额(元)" field="BALANCE" align="right" queryMode="group" width="80" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="创建人" field="CREATE_NAME" queryMode="group" width="60"></t:dgCol>
			</c:if>
			<c:if test="${inOutFlag eq 'o'}">
			<t:dgCol title="支付时间" field="Time" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="支付金额(元)" field="Amount" align="right" queryMode="group" width="80" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="可指定金额(元)" field="BALANCE" align="right" queryMode="group" width="80" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="创建人" field="CREATE_NAME" queryMode="group" width="80"></t:dgCol>
			</c:if>
		
		</t:datagrid>
		<input id="selectRow" type="hidden" />
	</div>
</div>
<!-- <script src = "webpage/com/kingtake/project/m2income/tPmIncomeNodeList.js"></script>		 -->
<script type="text/javascript">
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmNodeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmNodeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
</script>