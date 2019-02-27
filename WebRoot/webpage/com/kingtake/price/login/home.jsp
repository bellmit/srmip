<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="portal"></t:base>
<script type="text/javascript">
	$(function() {
		//动态添加面板
	   getPortal();
	}); 

	function getPortal() {
		$.ajax({
			type : 'GET',
			url : 'tPortalController.do?getPortal',
			dataType : 'json',
			data : {
				'userName' : $('#userName').val()
			},
			success : function(data) {
				var portalUser = data.portalUser;
				var portalData = portalUser.portalData;
				var layout = portalUser.layoutEntity;
				if(layout!=null&&layout!=undefined){
					$("#split").val(layout.split);
					$("#pp").empty().html(layout.layoutCode);
				}
				 $('#pp').portal({
						border : false,
						fit : true,
						closable : true,
						collapsible : true
					}); 
				var data = $.parseJSON(portalData);
				for (var j = 0; j < data.length; j++) {
					var p = $('<div/>').appendTo($('#pp'));
					var param = {};
					param['border'] = 'true';
					param['collapsible'] = 'true';
					param['closable'] = 'true';
					param['title'] = data[j].title;
					param['height'] = data[j].height;
					param['content'] =  '<iframe src="' + data[j].url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>';
					param['id'] = data[j].id;
					param['onClose']=onClose;
					param['onMove']=changeListner;
					p.panel(param);
					$('#pp').portal('add', {
						panel : p,
						columnIndex : data[j].colNo
						
					});
				}
				$('#pp').portal('resize');
			}
		});
	}
	
	//面板拖动或者删除事件监听
	function changeListner(){
		$("#savePortalBtn").linkbutton("enable");
	}
	
	//删除面板事件监听
	function onClose(){
		$("#pp").portal('remove',$(this));
		changeListner();
	}

	//保存待办信息 	
	function savePortalSettings() {
		$("#savePortalBtn").linkbutton('disable');
		var userName = $('#userName').val();
		var colNo = $("#pp").find('.portal-column-td').length;
		var data = {};
		var portalData=[];
		for (var i = 0; i < colNo; i++) {
			var panels = $("#pp").portal('getPanels', i);
			for (var j = 0; j < panels.length; j++) {
				var option = {};
				option['id'] = panels[j].panel('options').id;
				option['colNo'] = i;
				portalData.push(option);
			}
		}
		data['userName']=userName;
		data['portalData']=JSON.stringify(portalData);
		$.ajax({
			type : 'POST',
			url : 'tPortalController.do?savePortalUser',
			dataType : 'json',
			data : data,
			success : function(data) {
	            tip(data.msg);
				if (data.success) {
					var tab = $("#maintabs").tabs("getSelected");
					tab.panel('refresh', 'loginController.do?home');
				}else{
					$("#savePortalBtn").linkbutton('enable');
				}
			}

		});

	}

	//添加待办模块
	function addPortalSettings(){
		//刷新数据
		$("#portalList").datagrid("reload");
		$("#selectPortalWin").window('open');
	}
	
	function loadSuccess(data){
			var rows = data.rows;
			for(var i=0;i<rows.length;i++){
				if(rows[i].status=="1"){
					 $("#portalList").datagrid("selectRow",i); 
				}
			}
	}
	
	//增加待办	
	function addPortal(rows){
		var ids = [];
		for(var i=0;i<rows.length;i++){
			ids.push(rows[i].id);
		}
		var userName = $("#userName").val();
		$.ajax({
			type : 'POST',
			url : 'tPortalController.do?addPortal',
			dataType : 'json',
			data : {'ids':ids.join(","),'userName':userName},
			success : function(data) {
	            tip(data.msg);
				if (data.success) {
					$("#selectPortalWin").window("close");
					var tab = $("#maintabs").tabs("getSelected");
					tab.panel('refresh', 'loginController.do?home');
				} 
			}

		});
	}
	
	var toolbar = [{
		text:'保存',
		iconCls:'icon-save',
		handler:function(){
			var rows = getSelectRows();
			addPortal(rows);
			}
	}];
	
	//设置布局
	function setLayout(){
		add('设置布局','tPortalController.do?goSetLayout&userName='+$("#userName").val(),'tPortalList',550,280);
	}
	
	function callback(){
		var tab = $("#maintabs").tabs("getSelected");
		tab.panel('refresh', 'loginController.do?home');
	}
</script>
<input type="hidden" id="userName" value="${userName}" />
<input type="hidden" id="split" value="3"/>
<div style="height:100%;width:97%;border:1px;float:left;">
<div id="pp" style="">
	<div style="width: 30%;"></div>
	<div style="width: 40%;"></div>
	<div style="width: 30%;"></div>
</div>
</div>
<div
	style="width: 20px; float:right; margin-right: 8px;border:1px;margin-top:20px;">
	<div>
		<a id="savePortalBtn" href="#" class="easyui-linkbutton" title="保存"
			data-options="plain:true,iconCls:'icon-save'"
			onclick="savePortalSettings()"></a>
	</div>
	<div>
		<a id="addPortalBtn" href="#" class="easyui-linkbutton" title="添加"
			data-options="plain:true,iconCls:'icon-add'"
			onclick="addPortalSettings()"></a>
	</div>
	<a id="changeLayoutBtn" href="#" class="easyui-linkbutton" title="调整布局"
		data-options="plain:true,iconCls:'icon-edit'"
		onclick="setLayout()"></a>
</div>
<div id="selectPortalWin" class="easyui-window" title="添加待办" style="width:600px;height:300px;"
	data-options="iconCls:'icon-add',modal:true,closed:true">
	<t:datagrid name="portalList" 
	            autoLoadData="true"
				actionUrl="tPortalController.do?getAdddPortalList&userName=${userName}"
				idField="id" fit="true" fitColumns="true" queryMode="group"
				pagination="false" checkbox="true" onLoadSuccess="loadSuccess"
				extendParams="toolbar:toolbar,">
				<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="height" field="height" hidden="true"></t:dgCol>
				<t:dgCol title="portalId" field="portalId" hidden="true"></t:dgCol>
				<t:dgCol title="模块名称" field="title" width="200"></t:dgCol>
				<t:dgCol title="地址" field="url" width="300" hidden="true"></t:dgCol>
			</t:datagrid>
</div>
