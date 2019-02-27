<%@ page language="java" import="com.kingtake.common.constant.ProjectConstant"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        //编辑时审批已处理：提示不可编辑
        if (location.href.indexOf("tip=true") != -1) {
        	var win;
    		var dialog = W.$.dialog.list['processDialog'];
    	    if (dialog == undefined) {
    	    	win = frameElement.api.opener;
    	    } else {
    	    	win = dialog.content;
    	    }
            var msg = $("#tipMsg", win.document).val();
            tip(msg);
        }

        $('#tt').tabs({
            tools:"#tab-tools"
        }); 

        /* $("#importBtn").click(function() {
            var windowapi = frameElement.api, W = windowapi.opener;
            var url = "tPmProjectFundsApprController.do?upload&tpId=" + $("#tpId").val() + "&flag=" + $("#flag").val();
            $.dialog({
                title : "导入文件",
                content : 'url:' + url,
                cache : false,
                lock : true,
                zIndex : 2100,
                parent : windowapi,
                button : [ {
                    name : "开始上传",
                    callback : function() {
                            iframe = this.iframe.contentWindow;
                            $.dialog.setting.zIndex = 2200;
                            $.dialog.confirm('导入会导致已有数据被覆盖，您确定操作吗?', function() {
                            iframe.upload();
                        }, function() {
                        });
                        return false;
                    },
                    focus : true
                }, {
                    name : "取消上传",
                    callback : function() {
                        iframe = this.iframe.contentWindow;
                        iframe.cancel();
                    }
                } ]
            }).zindex();
        }); */
        
        //模板下载
        /* $("#exportXlsBtn").click(function(){
            var tpId = $("#tpId").val();
            var planContractFlag = $("#flag").val();
        	window.location.href = "tPmProjectFundsApprController.do?exportXlsByT&tpId="+tpId+"&planContractFlag="+planContractFlag;
        }); */

    });

    //刷新tab页
    function callback(msg) {
        tip(msg);
        var iframes = $("iframe");
        for (var i = 0; i < iframes.length; i++) {
            var currTab = iframes[i]; //获得iframe
            var src = $(currTab).attr('src');
            $(currTab).attr("src", src);
        }
    }
    
    /**
     * 比较原材料
     */
    function searchMaterial() {
    	frameElement.api.opener.$.dialog({
    		content : 'url:tPmMaterialController.do?tPmMaterialRead',
    		lock : true,
    		parent : frameElement.api,
    		width : 900,
    		height : 500,
    		title : '原材料比较'
    	});
    }
    
    /**
     * 查看减免
     */
    function searchAbate(abateId) {
    	frameElement.api.opener.$.dialog({
    		content : 'url:tPmAbateController.do?goAddUpdate&load=detail&id='+abateId,
    		lock : true,
    		parent : frameElement.api,
    		width : 900,
    		height : 500,
    		title : '查看减免'
    	});
    }
</script>


<input id="planContractFlag" type="hidden" value="${planContractFlag}">
<input id="finishFlag" type="hidden" value="${finishFlag}">
<input id="tpId" type="hidden" value="${tpId}">
<input id="projectId" type="hidden" value="${projectId}">
<t:tabs id="tt" iframe="true" tabPosition="top" >

  <!-- 合同类 -->
  <c:if test="${planContractFlag eq PROJECT_CONTRACT}">
    <t:tab href="tPmContractFundsController.do?tPmContractFunds&tpId=${tpId}&edit=${edit}&changeFlag=${changeFlag }"
      icon="icon-search" title="预算主表（单位：元）" id="o"></t:tab>
    <t:tab href="tPmFundsBudgetAddendumController.do?tPmFundsBudgetAddendum&tpId=${tpId}&edit=${edit}"
      icon="icon-search" title="预算附表（单位：元）" id="o"></t:tab>
  </c:if>

  <!-- 计划类 -->
  <c:if test="${planContractFlag eq PROJECT_PLAN}">
    <t:tab href="tPmPlanFundsController.do?tPmPlanFunds&tpId=${tpId}&edit=${edit}&changeFlag=${changeFlag }"
      icon="icon-search" title="预算主表（单位：元）" id="o"></t:tab>
    <t:tab href="tPmFundsBudgetAddendumController.do?tPmFundsBudgetAddendum&tpId=${tpId}&edit=${edit}"
      icon="icon-search" title="预算附表（单位：元）" id="o"></t:tab>
  </c:if>

</t:tabs>

<div id="tab-tools">
  <%-- <c:if test="${finishFlag eq '0'}">
	  <a id="exportXlsBtn" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-put" title="下载模板"></a>
	  <a id="importBtn" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-putout" title="导入"></a>
  </c:if> --%>
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" title="原材料比较"
		onclick="searchMaterial()">原材料比较</a>
  <c:if test="${!empty abateId}">
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" title="查看减免"
		onclick='searchAbate("${abateId}")'>查看减免</a>
  </c:if>
</div>


