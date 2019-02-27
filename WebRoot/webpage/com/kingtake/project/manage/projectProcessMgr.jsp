<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目过程管理</title>
<t:base type="jquery,tools,DatePicker,bootstrap"></t:base>
<link rel="stylesheet" href="webpage/com/kingtake/project/manage/proccessPage/style.css" type="text/css"></link>
<style type="text/css">
div.menuBtn {
	width: 152px;
    height: 90px;
	line-height: 2;
	color: black;
	margin: 0;
	padding: 0;
	display: inline-block;
	_display: inline;
	*display: inline;
	zoom: 1;
	cursor: pointer;
}

div.fadeMenu {
	background:
		url('webpage/com/kingtake/project/manage/img/processFade.png');
		no-repeat left top;
}

div.activeMenu {
  	font-weight:bold;
	background:
		url('webpage/com/kingtake/project/manage/img/processActive.png');
		no-repeat left top;
}

.menuTxt_disabled{
	cursor: inherit;
	color:gray;
}
</style>
<script type="text/javascript">
    var menuData = {};
    var colorArray = ['#d9534f','#337ab7','#f0ad4e','#5cb85c','#9c27b0','olive','orange','#8bc34a',];
    var showMode = '<%=request.getParameter("showMode")%>';
    
    $(function() { 
        setContentHeight();//动态设置高度
        
        showShenPiJilu();
   		
        $.ajax({
            url : "tPmSidecatalogController.do?getProcessModuleList",
            type : "POST",
            dataType : "json",
            data : {
                "projectId" : "${projectId}"
            },
            cache : false,
            success : function(data) {
                menuData = data;
                if(showMode!="readonly"){loadModule("XMSB");}
            }
        });
        
	    //清除缓存
	    $('#memoModal').on('hidden.bs.modal', function(e) {
	            $(this).removeData('modal');
	    });
	    $('#memoModal').on('show.bs.modal', function(e) {
	            $("#memoModal").css(  { "margin-top" : function() {  return -($(this).height() / 2); }  }   );
	    });

    });

    //显示审批记录
    function showShenPiJilu(){
    	
   		if(showMode=="readonly"){
   			$("#step_btns").find("div").css("cursor","inherit");
   			$("#step_btns").find("span").addClass("menuTxt_disabled");
   		}else{
   			$("#step_btns").find("div").bind("click", toggle_1);
   		}
   		//var win = window.top ? window.top : window;
   	   	//var height = $(win).height() ;
        var documentHeight = $(document).height();
        //alert('winHeight='+height+" ,docHeight="+documentHeight);
        $("#shenPiJilu").css("height", documentHeight - 150);
        
   		var projectId = '<%=request.getParameter("projectId")%>';
		$("#shenPiJilu").show();
		$("#shenPiJilu").attr("src", "tPmApprLogsController.do?goApprTab&load=detail&apprId="+projectId+"&apprType=20");
    
		
    }
    
    //加载流程模块按钮
    function loadModule(group) {
    	
    	var frame = $("#shenPiJilu")
    	if("XMSB"!=group){
    		frame.hide();
    	}else{
    		frame.show();
    	}
    	
    	//debugger;
        var content = $("#contentDiv");
        content.empty();
        var menuArr = menuData[group];
        var frame = " <table style='margin: auto;'>";
        var count = 0;
        var col = 4;//每行显示列数，如果超过4个需要到style.css文件中新增相应的div样式
        
        var lxyj = $("#lxyj").val()+'';
        for (var i = 0; i < menuArr.length; i++) {
        	
        	if(menuArr[i].isUsed==false){
        		continue;
        	}
        	
        	//===================lijun对于项目申报只看到进账合同申请环节
        	if(group=="XMSB" && (menuArr[i].title=="申报书申请" || menuArr[i].title=="申报需求申请" || menuArr[i].title=="查看申报依据")) continue;
        	
        	//===================lijun对于项目执行不需要在界面上看到如下环节
        	if(group=="XMZX" && (menuArr[i].title=="提交开题报告" || menuArr[i].title=="减免申请" || menuArr[i].title=="任务书申请"
        			|| menuArr[i].title=="项目节点考核申请" || menuArr[i].title=="出账合同考核及支付" 
        			|| menuArr[i].title=="提交中期检查报告")) continue;
        	
        	//计划类项目没有合同，和同类项目才有合同申请     modified by luokun  2018-09-03
        	if(group=="XMSB" && menuArr[i].title=="进账合同申请" && lxyj=='1'){
        		continue;
        	}
        	
            count++;
            if (count == 0) {
                frame += "<tr>";
            }
            if(menuArr[i].title=="减免申请"){
            	frame = frame
                + "<td style=\"padding: 20px;\"><div class=\"panel titleDiv"
                + (i % col + 1)
                + "\"><div class=\"text\" onclick=openWinJm(\""
                + menuArr[i].title
                + "\",getModuleUrlJm(\""
                + menuArr[i].url
                + "\"),"
                + menuArr[i].width
                + ","
                + menuArr[i].height
                + ")>"
                + menuArr[i].title
                + "</div><div class=\"hr\"></div><div class=\"pbtn\" data-toggle=\"modal\" data-target=\"#memoModal\"  href=\"tPmSidecatalogController.do?getMemo&id="
                + menuArr[i].id + "\" >点击查看说明</div></div></td>";
            }else{
            	frame = frame
                + "<td style=\"padding: 20px;\"><div class=\"panel titleDiv"
                + (i % col + 1)
                + "\"><div class=\"text\" onclick=openWin(\""
                + menuArr[i].title
                + "\",getModuleUrl(\""
                + menuArr[i].url
                + "\"),"
                + menuArr[i].width
                + ","
                + menuArr[i].height
                + ")>"
                + menuArr[i].title
                + "</div><div class=\"hr\"></div><div class=\"pbtn\" data-toggle=\"modal\" data-target=\"#memoModal\"  href=\"tPmSidecatalogController.do?getMemo&id="
                + menuArr[i].id + "\" >点击查看说明</div></div></td>";
            }
            
            if (count == col) {
                frame += "</tr>";
            }
            if (count == col) {
                count = 0;
            }
        }
        frame = frame + "</table>";
        content.html(frame);
        var panels = $(".panel");
        var count = 0;
        for (var i = 0; i < panels.length; i++) {
            $(panels[i]).css('background-color', colorArray[count]);
            count++;
            if (count == colorArray.length) {
                count = 0;
            }
        }
    }

    //获取url
    function getModuleUrl(url) {
        var projectId = $("#projectId").val();
        return url.replace("${projectId}", projectId);
    }

    //打开窗口
    function openWin(title, url, width, height) {
        if (url != 'null') {
        	var win = window.top ? window.top : window;
            if (!width) {
            	 width = $(win).width()-100;
            }
            if (!height) {
            	height = $(win).height() - 150;
            }
            if (typeof (windowapi) == 'undefined') {
            	$.dialog({
                    id : 'processDialog',
                    content : 'url:' + url,
                    lock : true,
                    width : width,
                    height : height,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
        }
    }
    
  //获取url-减免申请
    function getModuleUrlJm(url) {
        var projectId = $("#projectId").val();
        if("${PROFOR_FLAG }"==0 && "${MOTO_FLAG }"==0 ){
        	alert("该项目无需减免");
        	return null;
        }else{
        	return url.replace("${projectId}", projectId);
        }
    }
    
  	//打开窗口-减免
    function openWinJm(title, url, width, height) {
  		
        if (url != 'null') {
        	var win = window.top ? window.top : window;
            if (!width) {
            	 width = $(win).width()-100;
            }
            if (!height) {
            	height = $(win).height() - 150;
            }
            if (typeof (windowapi) == 'undefined') {
            	if("${PROFOR_FLAG }"!=0 && "${MOTO_FLAG }"!=0 ){
            		$.dialog({
                        id : 'processDialog',
                        content : 'url:' + url,
                        lock : true,
                        width : width,
                        height : height,
                        title : title,
                        opacity : 0.3,
                        cache : false,
                        cancelVal : '关闭',
                        cancel : true
                    }).zindex();
                }
            }
        }
    }

    //设置内容高度
    function setContentHeight() {
        var documentHeight = $(document).height();
        //$("#contentDiv").css("height", documentHeight - 200);
    }
    
    
    function toggle_1(){
    	var i =  $(this).attr("val");
/*     	if($("#lxbs").val() == "0") {   //======lijun如果该项目未立项则只能操作项目申报里面的按钮
    		return false;
        } */
    	toggle(i);
    }
    
    function toggle(i){
    	//debugger;
        var clsArr = ['sb','lx','zx','ysjt','cgjd'];
        var menuBtns = $("div.menuBtn");
        for(var j=0;j<clsArr.length;j++){
        	if($("#lxbs").val() == "0" && j>1) {   //======lijun如果该项目未立项则只能操作项目申报里面的按钮
        		return false;
            } 
            
            if(i==j){
                $(menuBtns[j]).removeClass(clsArr[j]).addClass(clsArr[j]+"_active");
                $(menuBtns[j]).find("span").removeClass("menuTxt").addClass("menuTxt_active");
                var group = $(menuBtns[i]).attr("group");
                loadModule(group);
            }else{
                $(menuBtns[j]).removeClass(clsArr[j]+"_active").addClass(clsArr[j]);
                $(menuBtns[j]).find("span").removeClass("menuTxt_active").addClass("menuTxt");
            }
        }
    }
</script>
</head>
<body>
  <input id="projectId" type="hidden" value="${projectId }">
  <input id="lxbs" type="hidden" value="${LX_FLAG }">
  <input id="lxyj" type="hidden" value="${LXYJ }">
  <div id="container" class="easyui-layout" data-options="border:false,fit:true">
    <div id="container" data-options="region:'center'">
      <div style="text-align: center; margin-top: 8px;" id="step_btns">
        <div class="sb_active menuBtn" group="XMSB" val="0"><span class="menuTxt_active">项目申报</span></div>
        <div class="lx menuBtn" group="XMLX" val="1"><span class="menuTxt">项目立项</span></div>
        <div class="zx menuBtn" group="XMZX" val="2"><span class="menuTxt">项目执行</span></div>
        <div class="ysjt menuBtn" group="XMYSJT" val="3"><span class="menuTxt">项目验收及结题</span></div>
        <div class="cgjd menuBtn" group="XMCGJD" val="4"><span class="menuTxt">成果管理</span></div>
      </div>
      <div id="contentDiv" style="margin-top: 20px;margin-bottom:20px;overflow: auto;padding-bottom: 10px;">
      </div>
    </div>
  </div>

  <div class="modal hide fade" id="memoModal" tabindex="-1" role="dialog">
    <div class="modal-header">
      <button class="close" type="button" data-dismiss="modal">×</button>
      <h3 id="myModalLabel">说明</h3>
    </div>
    <div class="modal-body"></div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
  </div>
  <!-- 内容弹出框 -->
  <div class="modal hide fade" id="contentModal" tabindex="-1" role="dialog" style="width:900px;">
    <div class="modal-header">
      <button class="close" type="button" data-dismiss="modal">×</button>
      <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
      <h3 id="contentModalLabel"></h3>
    </div>
    <div class="modal-body"></div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
  </div>
  <!-- 内容弹出框 -->
  <div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px;display:none;"  
        data-options="iconCls:'icon-save',modal:true,closed:true">   
  </div> 
  <!-- <div id="win" class="easyui-window" title="查看说明" style="width:600px;height:400px;background-color: #F4F4F4;"   
        data-options="iconCls:'icon-save',modal:true,closed:true">   
  </div>  -->
  
  <iframe  id="shenPiJilu" frameborder="0" style="width: 100%; height:800px; display:none;"></iframe>
</body>

</html>