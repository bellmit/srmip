<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <div id="tErReviewMainList"></div>
  </div>
 </div>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui1.4.2/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui1.4.2/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
<script type="text/javascript" src="plug-in/easyui1.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="plug-in/tools/css/common.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/jquery-plugs/hftable/jquery-hftable.js"></script>
<script type="text/javascript" src="plug-in/easyui1.4.2/jquery.easyui.min.js">
<script src = "webpage/com/kingtake/expert/reviewmain/tErReviewMainList.js"></script>	
 <script type="text/javascript" src="plug-in/easyui1.4.2/extends/datagrid-detailview.js"></script>
 <script src="webpage/com/kingtake/project/manage/addTab.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 			$('#tErReviewMainList').datagrid({
 	            fit : true,
 	            title:'专家评审列表',
 	            url: "expertLoginController.do?datagridForExpert&field=id,accordingNum,docNum,reviewTitle,reviewContent,planReviewDate,reviewModeStr",
 	            fitColumns : true,
 	            singleSelect:true,
 	            nowrap : true,
 	            striped : true,
 	            remoteSort : false,
 	            idField : 'id',
 	            columns : [ [ 
 	             {
 	                field : 'id',
 	                title : 'id',
 	                width : 120,
 	                hidden : true
 	            }, {
 	                field : 'accordingNum',
 	                title : '依据文号',
 	                width : 120
 	            }, {
 	                field : 'docNum',
 	                title : '呈批文件号',
 	                width : 120,
 	                hidden : true
 	            },
 	            {
 	                field : 'reviewTitle',
 	                title : '评审标题',
 	                width : 120,
 	            },
 	            {
 	                field : 'reviewContent',
 	                title : '评审内容',
 	                width : 120,
 	            },
 	            {
 	                field : 'planReviewDate',
 	                title : '计划评审时间',
 	                width : 120,
 	            },
 	            {
 	                field : 'reviewModeStr',
 	                title : '评审方式',
 	                width : 120
 	            }
 	             ] ],
 	            pagination : true,
 	            rownumbers : true,
 	            onLoadSuccess : function() {
 	            },
 	           onClickRow:function(rowIndex, rowData){
 	               $(this).datagrid('expandRow',rowIndex);
 	           },
 	          view: detailview,
 	          detailFormatter:function(index,row){
 	              return '<div style="padding:2px"><table id="ddv_'+index+'"></table></div>';
 	          },
 	          onExpandRow: function(index,row){
 	              var ddv = $("#ddv_"+index);
 	              ddv.datagrid({
 	                  url:'expertLoginController.do?getDetailList&id='+row.id,
 	                  fitColumns:true,
 	                  singleSelect:true,
 	                  rownumbers:true,
 	                  loadMsg:'',
 	                  height:'auto',
 	                  columns:[[
 	                      {field:'id',title:'id',hidden:true,width:100},
 	                      {field:'projectId',title:'项目id',hidden:true,width:100},
 	                      {field:'projectName',title:'项目名称',width:100},
 	                      {field:'reviewResultStr',title:'评审结果',width:100},
 	                      {field:'reviewResult',title:'评审',hidden:true,width:100},
 	                      {field:'reviewScore',title:'评审分数',width:50},
 	                      {field:'reviewSuggestion',title:'评审意见',width:100},
 	                      {field:'opt',title:'操作',width:60,
 	                      formatter:function(value,rec,index){
 	                          var href='';
 	                          href+="[<a href='#' onclick=edit('"+rec.id+"')>";
 	                          href+="专家评审</a>]";
 	                          return href;
 	                       }}
 	                  ]],
 	                  onResize:function(){
 	                      $('#tErReviewMainList').datagrid('fixDetailRowHeight',index);
 	                  },
 	                  onLoadSuccess:function(){
 	                      setTimeout(function(){
 	                          $('#tErReviewMainList').datagrid('fixDetailRowHeight',index);
 	                      },0);
 	                  }
 	              });
 	              $('#tErReviewMainList').datagrid('fixDetailRowHeight',index);
 	          }
 	        });
 			 
 			$('#tErReviewMainList').datagrid('getPager').pagination({
 	            beforePageText : '',
 	            afterPageText : '/{pages}',
 	            displayMsg : '{from}-{to}共 {total}条',
 	            showPageList : true,
 	            showRefresh : true
 	        });
 	       
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tErReviewMainController.do?upload', "tErReviewMainList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tErReviewMainController.do?exportXls","tErReviewMainList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tErReviewMainController.do?exportXlsByT","tErReviewMainList");
}

function view(id){
    createdetailwindow("查看",'expertLoginController.do?goExpertReviewInfoForExpert&load=detail&id='+id,"100%","100%");
}

function edit(id){
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:expertLoginController.do?goExpertReviewInfoForExpert&id='+id,
            //zIndex : 2100,
            title : '专家评审',
            lock : true,
            width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
            opacity : 0.4,
            button : [ {
                name : '确定',
                callback : function(){
                    var iframe = this.iframe.contentWindow;
                    iframe.saveProjectResult();
                    return false;
                },
                focus : true
            }, {
                name : '取消',
                callback : function() {
                }
            } ]
        }).zindex();
    } 
    /* addTab("专家打分",'expertLoginController.do?goExpertReviewInfoForExpert&id='+id,"default"); */
}
 </script>