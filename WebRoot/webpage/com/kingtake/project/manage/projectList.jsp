<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="excludeIds" type="hidden" value="${excludeIds}">
  <input id="plan" type="hidden" value="${plan}">
  <input id="lx" type="hidden" value="${lx}">
  <t:datagrid name="projectList" checkbox="${checkbox }" fitColumns="false" title="项目申报信息表" actionUrl="tPmProjectController.do?projectList&excludeIds=${excludeIds}&plan=${plan}&lx=${lx}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="申报编号" frozenColumn="true" field="cxm" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="项目申报名称" frozenColumn="true" field="projectName" query="true" isLike="true" queryMode="single" width="250" overflowView="true"></t:dgCol>
      <t:dgCol title="责任部门" field="dutyDepart_id" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="责任部门" field="dutyDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="承研部门id" field="devDepart_id" hidden="true" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="承研部门" field="devDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="项目组长id" field="projectManagerId" hidden="true" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="项目组长" field="projectManager" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="申报金额" field="budgetAmount" hidden="false" query="true" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="来源单位" field="sourceUnit" hidden="flase" isLike="true" queryMode="single" query="true" width="120"></t:dgCol>
      <t:dgCol title="项目类型" field="xmml" query="true" queryMode="single" width="120" overflowView="true"></t:dgCol>
      <t:dgCol title="经费类型" field="jflx_jflxmc" hidden="false" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费类型id" field="jflx_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="起始日期" field="beginDate" hidden="false" query="true" formatter="yyyy-MM-dd" queryMode="group" width="100" align="center"></t:dgCol>
      <t:dgCol title="截止日期" field="endDate" hidden="false" formatter="yyyy-MM-dd" queryMode="group" width="100" align="center"></t:dgCol>
      <t:dgCol title="外来编号" field="outsideNo" hidden="false" queryMode="single" isLike="true" query="true" width="120"></t:dgCol>
      <t:dgCol title="秘密等级" field="secretDegree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="120"></t:dgCol>
      <t:dgCol title="项目状态" field="projectStatus" hidden="flase" queryMode="group" replace="申请中_01,申报书提交_02,审查报批_03,正在执行_04,暂停执行_05,已验收_06,已结题_07,未立项_99,完成状态_98" width="120"></t:dgCol>
      <t:dgCol title="母项目编码" field="parentPmProject_projectNo" hidden="false" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="会计编码" field="accountingCode" hidden="flase" queryMode="single" isLike="true" width="120" query="true"></t:dgCol>
      <t:dgCol title="关键字" field="accordingNum" hidden="true" width="60"></t:dgCol>
      <t:dgCol title="确认状态" field="approvalStatus" hidden="true" width="60"></t:dgCol>
      <t:dgCol title="项目简介" field="projectAbstract" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="分管部门" field="manageDepart" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="联系人" field="contact" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="联系人电话" field="contactPhone" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划合同标志" field="planContractFlag" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="子类型" field="subType" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同计划文号" field="planContractRefNo" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="合同日期" field="contractDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="合同计划名称" field="planContractName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目来源" field="projectSource" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="负责人电话" field="managerPhone" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费单列" field="feeSingleColumn" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="是否需要鉴定" field="appraisalFlag" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目子类" field="xmlb_xmlb" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目子类" field="xmlb_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费类型" field="jflx_jflxmc" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费类型id" field="jflx_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费类型" field="feeTypeStr" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经费类型id" field="feeType_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目属性id" field="xmlx" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目性质id" field="xmxz" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="任务指定标志位id" field="rwzd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="立项依据标志位id" field="lxyj" hidden="true" queryMode="group" width="12"></t:dgCol>
      <t:dgCol title="是否校内协作" field="schoolCooperationFlag" hidden="true" queryMode="group" width="12"></t:dgCol>
      <t:dgCol title="是否需要鉴定" field="appraisalFlag" hidden="true" queryMode="group" width="12"></t:dgCol>
      <t:dgCol title="配套情况" field="matchSituation" hidden="true" queryMode="group" width="12"></t:dgCol>
      
      <t:dgCol title="来源计划/合同名称" field="planContractName" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="审批权限" field="approvalAuthority" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="来源计划/合同文号" field="planContractRefNo" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="型号背景" field="modelBackground" hidden="true" queryMode="group" width="30"></t:dgCol>
      
      <t:dgCol title="舰艇舷号" field="shipNumber" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="是否联合校外单位申报" field="lhsb" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="机关主管参谋id" field="officeStaff" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="机关主管参谋" field="officeStaffRealname" hidden="true" queryMode="group" width="30"></t:dgCol>
      <t:dgCol title="主管部门/甲方单位" field="sourceUnit" hidden="true" queryMode="group" width="30"></t:dgCol>
      
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="webpage/common/util.js"></script>