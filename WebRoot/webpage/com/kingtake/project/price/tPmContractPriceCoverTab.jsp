<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		 var parent;
		    var dialog = W.$.dialog.list['processDialog'];
		    if (dialog == undefined) {
		        parent = frameElement.api.opener;
		    } else {
		        parent = dialog.content;
		    }
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}

    $("#importBtn").click(function() {
        var windowapi = frameElement.api, W = windowapi.opener;
        var url = "tPmContractPriceCoverController.do?upload&tpId=" + $("#id").val();
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

    });
    
   
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
</script>
<style>
	.panel-header, .panel-body{
		border-color:white;
	}
</style>

<input id="id" value="${cover.id }"  type="hidden" />
<input id="contractId" value="${contractId }"  type="hidden" />

<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<div id="tt" class="easyui-tabs" data-options="border:false, fit:true">   
			<div title="封面">
				<iframe frameborder="0"  src="tPmContractPriceCoverController.do?goUpdate&id=${cover.id }&read=${read}" 
					style="width:100%;height:95%;"></iframe>	
			</div> 
			<c:if test="${cover.contractType == CONTRACT_BUY}">
				<div title="总表">
					<iframe frameborder="0" src="tPmContractPricePurchaseController.do?goUpdate&tpId=${cover.id }&read=${read}" 
						style="width:99%;height:99%;"></iframe>	
				</div>
			</c:if>
			<c:if test="${cover.contractType == CONTRACT_TECH}">
				<div title="总表">
					<iframe frameborder="0" src="tPmContractPriceTechController.do?goUpdate&tpId=${cover.id }&read=${read}" 
						style="width:99%;height:99%;"></iframe>	
				</div>
			</c:if>
			<c:if test="${cover.contractType != CONTRACT_BUY && cover.contractType != CONTRACT_TECH}">
				<div title="总表">
					<iframe frameborder="0" src="tPmContractPriceMasterController.do?goUpdate&tpId=${cover.id }&read=${read}" 
						style="width:99%;height:99%;"></iframe>	
				</div> 
				<c:forEach items="${tabs}" var="tab">
					<div title=${tab.name }>
						<iframe frameborder="0"  src="${tab.url }" style="width:99%;height:99%;">
						</iframe>
					</div>
				</c:forEach> 
				<div title=${other.name }>
					<iframe frameborder="0" scrolling="no" src="${other.url }" style="width:99%;height:99%;">
					</iframe>
				</div>
			</c:if>
		</div>
	</div>
</div>

<div id="tab-tools">
    <img alt="提供模板下载，数据导入，导出等功能" title="提供模板下载，数据导入，导出等功能" src="plug-in\easyui1.4.2\themes\icons\tip.png">
    <a id="exportXlsBtn" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-put" title="下载模板">下载模板</a>
    <a id="exportBtn" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-put" title="导出">导出</a>
    <c:if test="${read ne '1' }">
       <a id="importBtn" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-putout" title="导入">导入</a>
    </c:if>
</div>


<script type="text/javascript">
$(document).ready(function(){
	$('#tt').tabs({
	    onSelect:function(title,index){
	    	var tab = $('#tt').tabs('getSelected');  
	    	var iframeTab = tab.find('iframe');
	    	var src = iframeTab.attr("src");
	    	iframeTab.attr("src", src);
	    },
	    tools : '#tab-tools'
	});
	
	//模板下载
    $("#exportXlsBtn").click(function(){
        var id = $("#contractId").val();
        window.location.href = "tPmOutcomeContractApprController.do?exportXlsByT&id="+id;
    });
	//导出
    $("#exportBtn").click(function(){
        var id = $("#contractId").val();
        window.location.href = "tPmOutcomeContractApprController.do?exportXlsByT&id="+id;
    });
});
</script>
