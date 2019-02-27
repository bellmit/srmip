function reloadBaseInfo(data){
	$("#Validform_msg").hide();
	showMsg(data.msg);
}

function saveBaseInfo(){
	$("#baseInfo").submit();
}

function reloadTable(){
	$("#abatePay").datagrid('reload');
}

//提交流程
function startProcess(tableName){
    $("#tableName").val(tableName);
    var url = 'tPmDeclareController.do?goSelectOperator2';
    //流程对应表单URL
   if(typeof(windowapi) == 'undefined'){
        $.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180);
        }, function() {
        }).zindex();
    }else{
        W.$.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180);
        }, function() {
        },windowapi).zindex();
    }
   
}

function submitAudit(nextUser){
   var url = "tBLearningThesisController.do?startProcess";
   var id =  $("#id").val();
   var businessCode =  "declare";
   var projectId = $("#projectId").val();
   var tableName = $("#tableName").val();
   //流程对应表单URL
   var formUrl = 'tPmDeclareController.do?goDeclareAudit&projectId='+projectId;
   var businessName = $("#projectName").val();
   var paramsData = {"id":id,"businessCode":businessCode,"formUrl":formUrl,"businessName":businessName,"tableName":tableName,"nextUser":nextUser};
   $.ajax({
       async : false,
       cache : false,
       type : 'POST',
       data : paramsData,
       url : url,// 请求的action路径
       success : function(data) {
           var d = $.parseJSON(data);
           tip(d.msg);
           if (d.success) {
               var msg = d.msg;
               setTimeout(function(){
                   window.location.reload();
               },2000);
           }
       }
   });
}

//打开选择人窗口
function openOperatorDialog(title, url, width, height) {
    var width = width ? width : 700;
    var height = height ? height : 400;
    if (width == "100%") {
        width = window.top.document.body.offsetWidth;
    }
    if (height == "100%") {
        height = window.top.document.body.offsetHeight - 100;
    }

    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var operator = iframe.getOperator();
                if (operator == "" || operator ==undefined) {
                    return false;
                }
                submitAudit(operator);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    } else {
        W.$.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            parent : windowapi,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var operator = iframe.getOperator();
                if (operator == "" || operator ==undefined) {
                    return false;
                }
                submitAudit(operator);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}

function getId() {
    var id = $("#id").val();
    return id;
}

function getTableName() {
    return $("#tableName").val();
}

function getBusinessName() {
    var businessName = $("#projectName").val();
    return businessName;
}

function getFormUrl() {
    var projectId = $("#tpId").val();
    //流程对应表单URL
    var formUrl = 'tPmDeclareController.do?goDeclareAudit&projectId='+projectId;
    return formUrl;
}

function getBusinessCode() {
    return "declare";
}

//重新加载
function customReload(){
}

// 查看流程
function viewHistory(processInstanceId) {
    goProcessHisTab(processInstanceId,'项目申报书流程');
}

/**
 * 删除成员
 */
function deleteMember(){
	var record = $("#dg").datagrid('getSelected');
	if(record){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.ajax({
		        	type:'post',
		        	data:"id="+record.id,
		        	url:'tPmDeclareMemberController.do?doDel',
		        	success:function(result){
		        		var json = $.parseJSON(result);
		        		reloadMember();
		        		showMsg(json.msg);
		        	}
		        });  
		    }    
		});  
	}else{
		showMsg("请选择一条记录后再删除！");
	}
}

function detailMember(){
	detail('查看','tPmDeclareMemberController.do?goUpdate', 'dg', 630, 230);
}

/**
 * 删除减免垫支
 */
function deleteAbatePay(){
	var record = $("#abatePay").datagrid('getSelected');
	if(record){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.ajax({
		        	type:'post',
		        	data:"id="+record.id,
		        	url:'tPmAbatePayfirstController.do?doDel',
		        	success:function(result){
		        		var json = $.parseJSON(result);
		        		reloadTable();
		        		showMsg(json.msg);
		        	}
		        });  
		    }    
		});  
	}else{
		showMsg("请选择一条记录后再删除！");
	}
}

/**
 * 查看减免垫支
 */
function detailAbatePay(){
	detail('查看','tPmAbatePayfirstController.do?goAddUpdate', 'abatePay', 620, 300)
}

/**
 * 重新加载成员信息
 */
function reloadMember(){
	$("#dg").datagrid('reload');
}

/**
 * 删除预算
 */
function deleteBudget(){
	var record = $("#budgetTable").datagrid('getSelected');
	if(record){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.ajax({
		        	type:'post',
		        	data:"id="+record.id,
		        	url:'tPmFundsBudgetController.do?doDel',
		        	success:function(result){
		        		var json = $.parseJSON(result);
		        		reloadBudget();
		        		showMsg(json.msg);
		        	}
		        });  
		    }    
		});
	}else{
		showMsg("请选择一条记录后再删除！");
	}
}

/**
 * 重新加载预算信息
 */
function reloadBudget(){
	$("#budgetTable").datagrid('reload');
}

/**
 * 删除设备费
 */
function deleteEqu(){
	var record = $("#equTable").datagrid('getSelected');
	if(record){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){    
		        $.ajax({
		        	type:'post',
		        	data:"id="+record.id,
		        	url:'tPmFundsBudgetEquipController.do?doDel',
		        	success:function(result){
		        		var json = $.parseJSON(result);
		        		reloadEqu();
		        		showMsg(json.msg);
		        	}
		        });  
		    }    
		});
	}else{
		showMsg("请选择一条记录后再删除！");
	}
}

/**
 * 重新加载设备费
 */
function reloadEqu(){
	$("#equTable").datagrid('reload');
}

function showMsg(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}

function getValue(params, url){
	var temp = params.value;
	if(params.value && params.value!=''){
		$.ajax({
			async:false,
			type:'post',
			url:url,
			data:params,
			success:function(data){
				data = $.parseJSON(data);
				if(data && data.length == 1){
					temp = data[0].name;
				}
			}
		});
	}
	return temp;
}

/**
 * 操作按钮
 */
function formatOPT(value, row, index){
	var add = "[<a href='javascript:void(0)' nodeid='"+row.ID+"' tree='"+row.TREE+"' onclick='addChild(this)'>添加</a>] ";
	var edit = "[<a href='javascript:void(0)' nodeid='"+row.ID+"' tree='"+row.TREE+"' onclick='editRow(this)'>编辑</a>] ";
	var save = "[<a href='javascript:void(0)' onclick='saveNode()'>保存</a>] ";
	var cancel = "[<a href='javascript:void(0)' nodeid='"+row.ID+"' tree='"+row.TREE+"' onclick='cancelEdit(this)'>取消</a>] ";
	var del = "[<a href='javascript:void(0)' nodeid='"+row.ID+"' tree='"+row.TREE+"' onclick='deleteRow(this)'>删除</a>] ";
	var s = "";
	
	// '0'--不可操作
	if(value == '1'){
		// 可添加子节点
    	s = add;
	}else if(value == '2'){
		// 可编辑节点
		if(row.editing){
			s = save + cancel;
		}else{
			s = edit;
		}
	}else if(value == '3'){
		// 可删除，可编辑
		if(row.editing){
			s = save + cancel + del;
		}else{
			s = edit + del;
		}
	}
	return s;
}

function freshRow(row, tree){
	$("#"+tree).treegrid('refresh', row.ID);
}

/**
 * 添加子节点
 */
function addChild(a){
	saveNode();
	
	addRow($(a).attr("tree"), $(a).attr("nodeid"));
}

function addRow(tree, parent){
	var url = getUrl(tree) + "doAdd";
	
	// 添加子节点到数据库
	$.ajax({
		type: "POST",
		url: url,
		data: {
			parent:parent,
			projDeclareId:$("#id").val(),
			addChildFlag:'3'
		},
	   	success: function(data){
	   		var json = $.parseJSON(data);
	   		var newNode = {ID:json.obj, NAME:'', ADDCHILDFLAG:'3', TREE:tree};
	   		if(tree == "mateTree"){
   				newNode.MODEL='';
   				newNode.PRICE='0';
   				newNode.QUANTITY='';
   				newNode.FUNDS='0';
   				newNode.MEMO='';
	   		}else if(tree == "outTree"){
   				newNode.UNIT='';
   				newNode.CONTENT='';
   				newNode.FUNDS='0';
   				newNode.MEMO='';
	   		}else if(tree == "busiTree"){
   				newNode.CONTENT='';
   				newNode.FUNDS='0';
   				newNode.MEMO='';
	   		}else if(tree = "equTree"){
	   			newNode.CONFIG='';
   				newNode.PRICE='0';
   				newNode.QUANTITY='';
   				newNode.FUNDS='0';
   				newNode.MEMO='';
	   		}
	   		// 添加子节点到页面
			$('#'+tree).treegrid('append',{
				parent: parent,
				data: [newNode]
			});
			
			// 将子节点变为编辑状态
			$('#'+tree).treegrid('select', json.obj).treegrid('beginEdit', json.obj);
			editId = json.obj;
			editTree = tree;
			bindCal(json.obj, tree);
	   	}
	});
}

function editRow(a){
	edit($(a).attr("nodeid"), $(a).attr("tree"));
}

function dbClickRow(row, tree){
	if((row.ADDCHILDFLAG == "2" || row.ADDCHILDFLAG == "3") && !row.editing){
		edit(row.ID, tree);
	}
}

function edit(id, tree){
	saveNode();
	
	// 将节点变为编辑状态
	$('#'+tree).treegrid('beginEdit', id);
	editId = id;
	editTree = tree;
	bindCal(id, tree);
}

function bindCal(id, tree){
	if(tree == "equTree" || tree == "mateTree"){
		// 报价栏：经费=数量*单价
		calculate(id, tree, 'QUANTITY', 'PRICE', 'FUNDS');
	}
}

/**
 * z = x * y
 * @param id
 * @param tree
 */
function calculate(id, tree, x, y, z){
	var xEditor = $("#"+tree).datagrid('getEditor', 
			{index:id, field:x});
	var yEditor = $("#"+tree).datagrid('getEditor', 
			{index:id, field:y});
	var zEditor = $("#"+tree).datagrid('getEditor', 
			{index:id, field:z});
	xEditor.target.bind("change", function(){
		var xValue = xEditor.target.val() ? parseFloat(xEditor.target.val()) : 0; 
		var yValue = yEditor.target.val() ? parseFloat(yEditor.target.val()) : 0;
		$(zEditor.target).numberbox('setValue', xValue*yValue);
	}); 
	yEditor.target.bind("change", function(){
		var xValue = xEditor.target.val() ? parseFloat(xEditor.target.val()) : 0; 
		var yValue = yEditor.target.val() ? parseFloat(yEditor.target.val()) : 0;
		$(zEditor.target).numberbox('setValue', xValue*yValue);
	});
}

function saveNode(){
	if(editTree && editId){
		var data = $("#"+editTree).treegrid('find', editId);
		var nameEditor = $("#"+editTree).datagrid('getEditor', 
				{index:editId, field:'NAME'});
		var name = "";
		if(nameEditor){
			name = nameEditor.target.val();
		}else{
			name = data.NAME;
		}
		
		
		// 如果名称为空则将提醒用户填写信息
		if(name){
			var oldMoney = data.FUNDS ? parseFloat(data.FUNDS) : 0;
			data = $('#'+editTree).treegrid('endEdit', editId).treegrid('find', editId);
			var newMoney = data.FUNDS ? parseFloat(data.FUNDS) : 0;
			var url = getUrl(editTree) + "doUpdate";
		
			// 保存数据到数据库
			$.ajax({
				type: "POST",
				async: false,
				url: url,
				data: data,
			   	success: function(result){
			   		// 更新父节点的数据
			   		updateParents(editId, oldMoney, newMoney, editTree);
			   	}
			});
			editId = "";
			editTree = "";
		}else{
			showMsg('请填写名称！');  
		}
		
	}
}

function cancelEdit(a){
	a = $(a);
	var id = a.attr("nodeid");
	var tree = a.attr("tree");
	
	$('#'+tree).treegrid('cancelEdit', id);
	var data = $('#'+tree).treegrid('find', id);
	if(!checkName(data)){
		del(data.FUNDS, data);
	}
	editId = "";
	editTree = "";
}

/**
 * 删除节点
 */
function deleteRow(a){
	a = $(a);
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		if (r){ 
			var id = $(a).attr("nodeid");
			var tree = $(a).attr("tree");
			var data = $("#"+tree).treegrid('find', id);
			var oldMoney = data.FUNDS;
			data = $('#'+tree).treegrid('endEdit', id).treegrid('find', id);
	    	del(oldMoney, data);
	    	
	    	if(editId == id){
	    		editId = "";
	    		editTree = "";
	    	}
	    }    
	}); 
}

function del(oldMoney, newData){
	var newMoney = 0;
	oldMoney = oldMoney ? parseFloat(oldMoney) : 0;
	var url = getUrl(newData.TREE) + "doDel";
	
	// 后台删除节点
	$.ajax({
		type: "POST",
		url: url,
		data: "id="+newData.ID,
	   	success: function(msg){
	   		if(oldMoney != newMoney){
	   			updateParents(newData.ID, oldMoney, newMoney, newData.TREE);
	   		}
	  		// 前台删除节点
	    	$('#'+newData.TREE).treegrid('remove', newData.ID);
	   	}
	});
}


/**
 * 根据子节点更新父节点
 */
function updateParents(id, oldMoney, newMoney, tree) {
	var parent = $('#'+tree).treegrid('getParent', id);
	if(parent){
		$('#'+tree).treegrid('update', {
			id : parent.ID,
			row : {
				FUNDS : (parent.FUNDS ? parseFloat(parent.FUNDS) : 0) - oldMoney + newMoney
			}
		});
		var data = $("#"+tree).treegrid('getParent', parent.ID);
		updateParents(parent.ID, oldMoney, newMoney, tree);
	}
}

/**
 * 检查节点名称是否为空
 * @param data
 * @returns
 */
function checkName(data){
	if(!data.NAME){
		return false;
	}
	return true;
}

function getUrl(tree){
	var url = "";
	if(tree == "mateTree"){
		url = "tPmFundsBudgetMaterialController.do?";
	}else if(tree == "outTree"){
		url = "tPmFundsBudgetOutsideController.do?";
	}else if(tree == "busiTree"){
		url = "tPmFundsBudgetBusiController.do?";
	}else if(tree == "equTree"){
		url = "tPmFundsBudgetEquipController.do?";
	}
	return url;
}

function uploadFile(data) {
    $("#bid").val(data.obj.id);
    if ($(".uploadify-queue-item").length > 0) {
        upload();
    }else{
        $("#Validform_msg").hide();
        //showMsg(data.msg);
        $.messager.alert('提示',data.msg,function(){
            window.location.reload();
        });
    }
}

function saveCallback(data) {
        $.messager.alert('提示',data.msg,'info',function(){
        	if (data.success) {
        		window.location.reload();
        	}
        });
}

function uploadCallback(){
//    tip("申报书保存成功!");
//    setTimeout(function(){
//        window.location.reload();
//    },2000);
    $.messager.alert('提示',"申报书保存成功!",function(){
        window.location.reload();
    });
}

/**
 * 查看流程意见
 */
function viewRemark(){
    var processInsId = $("#processInsId").val();
    var url = "tPmDeclareController.do?goViewProcess&processInstId="+processInsId;
    createdetailwindow("查看流程意见", url,null,null);
}

//重新提交
    function compeleteProcess(declareType) {
        var taskId = $("#taskId").val();
        var url;
        var data;
        if(taskId!=""){
            url="activitiController.do?processComplete";
            data={
                "taskId" : taskId,
                "nextCodeCount" : 1,
                "model" : '1',
                "reason" : "重新提交",
                "option" : "重新提交"
            };
        }else{
            url="tPmDeclareController.do?doResubmit";
            var id = $("#id").val();
            data={"declareId":id,"declareType":declareType}
        }
        if(typeof(windowapi)=='undefined'){
            $.dialog.confirm('您确定修改好，重新提交申报书吗?', function() {
                    $.ajax({
                        url : url,
                        type : "POST",
                        dataType : "json",
                        data : data,
                        async : false,
                        cache : false,
                        success : function(data) {
                            if (data.success) {
                                window.location.reload();
                            }
                        }
                    });
            },function(){});
        }else{
            W.$.dialog.confirm('您确定修改好，重新提交申报书吗?', function() {
                $.ajax({
                    url : url,
                    type : "POST",
                    dataType : "json",
                    data : data,
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
        },function(){},windowapi);
        }
    }
    
  //查看计划草案驳回修改意见
    function viewMsgText(id){
        $.ajax({
           url:'tPmPlanDetailController.do?getMsgText',
           type:'POST',
           cache:false,
           data:{'id':id},
           dataType:'json',
           success:function(data){
               W.$.dialog({
                  content: '<textarea id="msgTextArea" rows="5" cols="5" style="width: 327px; height: 188px;" readonly="true">'+data.msg+'</textarea>',
                  lock : true,
                  width:350,
                  height:200,
                  parent:windowapi,
                  title:'查看修改意见',
                  opacity : 0.3,
                  cache:false,
                  cancelVal: '关闭',
                  cancel: true 
              }).zindex();
           }
        });
           
        }
