<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script>
var flag=false;
    $(function() {
    	if($('#mode').val()=='multiply'){
    		flag=true;
    	}
        //监听树的点击事件
        $("#processtree").tree({
            checkbox:flag,
            onlyLeafCheck:flag,
            onClick : function(node) {
            }
        });
        
    });
    
    
    
    function changeSortType(sortType) {
        $("#sortType").val(sortType);//将分类类型存起来 
        var url = "projectMgrController.do?projectSelectTree&all="+$('#all').val();
        var data = {
            "sortKey" : sortType
        };
        loadTree(url, data);
    }

    function loadTree(url, data) {
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
        var cxm = $("#cxm").val();
        var projectManager = $("#projectManager").val();
       // var value = $("#search").searchbox("getValue");
        var data = {
            "sortKey" : sortType,
            "cxm" : cxm,
            "projectManager" : projectManager
        };
        var url = "projectMgrController.do?projectSelectTreeByCxmSbr&all="+$('#all').val();
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
        createdetailwindow("新建项目","tPmProjectController.do?goAdd","100%","100%");
    }
    
    function getChecked(){
    	var id = [];
		var name=[];
		var checked=[];
		var data = $("#processtree").tree('getChecked');
		for(var i=0;i<data.length;i++){
			id[i]=data[i].id;
			name[i]=data[i].text;
		}
		var ids=id.join(",");
		var names = name.join(",");
		checked[0]=ids;
		checked[1]=names;
		return checked;
    }
    
    function getSelected(){
    	var select=[];
		var data = $("#processtree").tree('getSelected');
		select[0]=data.id;
		select[1]=data.text;
		return select;
    }

</script>
  <input id="sortType" type="hidden" value="1">
  <input id="projectId" type="hidden">
  <input id="planContractFlag" type="hidden">
  <input id="mode" type="hidden" value="${mode}">
  <input id="all" type="hidden" value="${all}">
  <div id="layoutDiv" class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height: 70px;text-align: center;vertical-align: top;overflow: hidden;">
          <!-- <input id="search" class="easyui-searchbox" style="vertical-align: middle;height:26px;width:170%;"
            data-options="prompt:'输入项目名称',searcher:doSearch"> -->
            <input type="text" id="cxm" name="cxm" placeholder="项目申请号" value="2018010315264710013" style="width:70%;vertical-align: middle;height:26px;margin-left:-28px;margin-bottom: 10px" >
            <input type="text" id="projectManager" name="projectManager" value="管理员" placeholder="申报人" style="width:70%;vertical-align: middle;height:26px;" >
            <a id="searchBtn" class="easyui-linkbutton" onclick="doSearch()" data-options="iconCls:'icon-search',plain:true" title="查询" style="vertical-align: top;"></a>
<!--           <a id="addBtn" class="easyui-linkbutton" onclick="addProject()" data-options="iconCls:'icon-add',plain:true" title="添加项目" style="vertical-align: top;"></a> -->
        </div>
    <div region="center" style="padding: 1px;" id="process-panelss">
      <div class="easyui-layout" data-options="fit:true,border:false" style="padding: 10px;">
        <!-- <div data-options="region:'north'" style="height: 30px;" align="center">
          <span><img src="plug-in/easyui/themes/icons/lxxs.png"  style="width: 43px;"
            onclick="changeSortType(1)"></img></span>
            <span><img src="plug-in/easyui/themes/icons/year.png" style="width: 43px;"
            onclick="changeSortType(2)"></img></span> 
            <span><img  src="plug-in/easyui/themes/icons/lb.png" style="width: 43px;"
             onclick="changeSortType(3)"></img></span> 
             <span><img src="plug-in/easyui/themes/icons/xz.png" style="width: 43px;"
             onclick="changeSortType(4)"></img></span>
        </div> -->
        <div id="treeDiv" data-options="region:'center'">
          <ul id="processtree" class="easyui-tree" >
          </ul>
        </div>
      </div>
    </div>
  </div>
