//追加下拉菜单
function appendDivListForProject(mid,gid,title,url,menu1){
    var  planContractFlag = $("#planContractFlag").val();
    $.ajax({
		cache : false,
		type : 'POST',
        url : url,
        data :{"menu":menu1,"planContractFlag":planContractFlag, projectId:$("#projectId").val()},
        datatype : 'html',
		success : function(data) {
		    var menuStr = eval(data);
		    var divStr = "<div id='"+gid+"' style=\"width:150px\"></div>";
		    $("#"+gid).replaceWith(divStr);
			$("#"+gid).html(menuStr);
			$("#"+gid).menu({
			     onClick : function(node) {
			        var projectId = $("#projectId").val();
			        if(projectId==""){
			             tip("请先选择项目!");
			        } else{
			            var url =$(node.target).find("a").attr("subhref");
			            if(url != null && url != ""){
			                url += "&projectId=" + projectId;
			            }
	                	$("#contentFrame").attr("src", url);
			        }
	            }  
			});
			//Js创建菜单按钮
			$('#'+mid).menubutton({    
			    menu: '#'+gid
			}); 
			
		}
	});
}
    
//展开父节点
function expandNode(treeId, id) {
    var node = $('#'+treeId).tree('find',id); //获取指定节点
    var parent = null;
    if(node){
        parent = $('#'+treeId).tree('getParent',node.target); //获取指定节点的父节点
    }
    if(parent) {
        $('#'+treeId).tree('expand',parent.target);
        expandNode(parent.id);
    }
}
  	
//重新聚焦选中节点
function focusNode(treeId, nodeId) {
  var selectNode = $('#'+treeId).tree('find',nodeId);//获取重新加载树后项目节点
  if(selectNode) {
      expandNode(treeId, selectNode.id);//展开父节点
      $('#'+treeId).tree('select', selectNode.target); //重新聚焦选中的节点
	  clickNode(selectNode);
  }
}

/**
 * 修改项目树筛选条件
 * @param sortType：1--立项形式，2--年度，3--类别，4--性质
 */
function changeSortType(sortType) {
	//获取选中项目节点
	var node =  $('#processtree').tree('getSelected');
	//将分类类型存起来 
    $("#sortType").val(sortType);
    var url = "projectMgrController.do?projectTree";
    var data = {
        "sortKey" : sortType
    };
    loadTree(url, data);
    
    if(node){
    	//聚焦选中节点
        focusNode("processtree", node.id); 
    }
}

/**
 * 加载树
 * @param url
 * @param data
 */
function loadTree(url, data) {
    var projectIds = window.top.projectIds;
    if(projectIds!=""){
        data['ids']=projectIds;
    }
   $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : url,
        dataType : 'json',
        data : data,
        success : function(data) {
            $("#processtree").tree("loadData", data);
        }
    }); 
}

function doSearch() {
    var sortType = $("#sortType").val();
    var value = $("#search").searchbox("getValue");
    var data = {
        "sortKey" : sortType,
        "projectName" : "*" + value + "*"
    };
    var url = "projectMgrController.do?projectTree";
    loadTree(url, data);
}
    
//基本信息跳转
function goBase(){
    var projectId = $("#projectId").val();
    if(projectId==""){
        tip("请先选择项目!");
    } else{
        url = "tPmProjectController.do?goUpdateForResearchGroup&id="+ projectId;
        $("#contentFrame").attr("src", url);
    }
}

function addProject(){
    //createdetailwindow("新建项目","tPmProjectController.do?goAdd","1100px","500px");
    createdetailwindow("新增项目","tPmProjectController.do?goAdd&entryType=KTZ","100%","100%");
}

function getParameter(){
    return $("#projectNameNode").val();
}
    
//刷新项目树
function doRefresh(){
	var index = $("#treeTabs").tabs("getSelected").panel("options").title;
	
	if('草稿箱'==index){
		initTree('unapprovalTree');
	}else if('待审批'==index){
		initTree('approvalTree',1);
	}else{
	    /*var sortType = $("#sortType").val();
	    var data = {
	            "sortKey" : sortType,
	            "projectName" : "" + $("#search").val() + ""
	    };
	    var url = "projectMgrController.do?projectTree";
	    loadTree(url, data);*/
	    doSearch();
	}
}

//复制项目
function doCopy(){
    var selected = $("#processtree").tree("getSelected");
    if(selected==null||selected.id.indexOf("treeId") != -1){
        $.messager.alert("警告","请先选择一个项目");
        return false;
    }
    createdetailwindow("新增项目","tPmProjectController.do?goCopy&id="+selected.id,"100%","100%");
}