<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<link rel="stylesheet" href="plug-in/tools/css/common.css" type="text/css"></link>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <table width="100%" id="tErReviewMainList" toolbar="#tErReviewMainListtb"></table>
    <div id="tErReviewMainListtb" style="padding: 3px; height: auto">

      <div name="searchColums">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addExpertJudge();">添加专家评审</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="viewMain();">查看专家评审</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="ExportXls();">导出</a>
        <div style="float:right;">
        <span style="display: -moz-inline-box; display: inline-block;">
          <span
            style="vertical-align: middle; display: -moz-inline-box; display: inline-block; margin-left: 10px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
            title="依据文号">依据文号：</span>
          <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" type="text" name="accordingNum" style="width: 100px" />
        </span>
        <span style="display: -moz-inline-box; display: inline-block;">
          <span
            style="vertical-align: middle; display: -moz-inline-box; display: inline-block; margin-left: 10px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
            title="计划评审时间">计划评审时间：</span>
          <input type="text" name="planReviewDateBegin_begin" style="width: 100px; height: 20px;" />
          <span style="display: -moz-inline-box; display: inline-block; width: 8px; text-align: right;">~</span>
          <input type="text" name="planReviewDateBegin_end" style="width: 100px; height: 20px;" />
        </span>
        <span style="display: -moz-inline-box; display: inline-block;">
          <span style="">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="tPmProjectListsearch()">查询</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="searchReset('tErReviewMainList')">重置</a>
          </span>
          </span>
          </div>
      </div>

      <!-- <div style="height: 30px;" class="datagrid-toolbar">
    
  </div> -->
    </div>
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
<script type="text/javascript" src="plug-in/easyui1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src = "webpage/com/kingtake/expert/reviewmain/tErReviewMainList.js"></script>
<script type="text/javascript" src="plug-in/easyui1.4.2/extends/datagrid-detailview.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 			$('#tErReviewMainList').datagrid({
 	            fit : true,
 	            url: "tErReviewMainController.do?datagrid&field=id,accordingNum,docNum,reviewTitle,reviewContent,planReviewDateBeginStr,reviewModeStr,createDateStr,reviewStatus",
 	            fitColumns : true,
 	           	singleSelect : true,
 	            nowrap : true,
 	            striped : true,
 	            remoteSort : false,
 	            idField : 'id',
 	            columns : [ [ 
                {field:'opt',title:'操作',width:120,
                        formatter:function(value,rec,index){
                  var href='';
                  if(rec.reviewStatus=='0'){
                  href+="[<a href='#' onclick=editMain('"+rec.id+"')>";
                  href+="编辑</a>]";
                  href+="[<a href='#' onclick=submit('"+rec.id+"')>";
                  href+="提交</a>]";
                  href+="[<a href='#' onclick=deleteReview('"+rec.id+"')>";
                  href+="删除</a>]";
                  return href;
                }}
                }, {
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
 	                width : 120
 	            },
 	            {
 	                field : 'reviewContent',
 	                title : '评审内容',
 	                width : 120
 	            },
 	            {
 	                field : 'planReviewDateBeginStr',
 	                title : '计划评审开始时间',
 	                width : 120
 	            },
 	            {
 	                field : 'reviewModeStr',
 	                title : '评审方式',
 	                width : 120
 	            },
 	            {
 	                field : 'createDateStr',
 	                title : '提交评审时间',
 	                width : 120
 	            },
 	            {
 	                field : 'reviewStatus',
 	                title : '评审状态',
 	                width : 60,
 	                formatter:function(value,rec,index){
 	                   var status='';
                       if(value=="0"){
                           status = "待提交";
                       }else if(value=="1"){
                           status = "已提交";
                       }else if(value=="2"){
                           status = "已完成";
                       }
                       return status;
 	                }
 	            } ] ],
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
 	        	  var rows = $(this).datagrid('getRows');
 	        	  for(var i in rows){
 	        		  if(i != index){
 	        			 $(this).datagrid('collapseRow',i);
 	        		  }
 	        	  }
 	        	  
 	              var ddv = $("#ddv_"+index);
 	              ddv.datagrid({
 	                  url:'tErReviewMainController.do?getDetailList&id='+row.id,
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
 	                      {field:'reviewScore',title:'评审分数',width:50},
 	                      {field:'reviewSuggestion',title:'评审意见',width:100},
 	                      {field:'opt',title:'操作',width:60,
 	                      formatter:function(value,rec,index){
 	                          var href='';
 	                          href+="[<a href='#' onclick=view('"+rec.id+"')>";
 	                          href+="查看</a>]";
 	                          href+="[<a href='#' onclick=edit('"+rec.id+"')>";
 	                          href+="编辑</a>]";
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
 	        $('#tErReviewMainList').datagrid('getPager').pagination({
 	            onBeforeRefresh : function(pageNumber, pageSize) {
 	                $(this).pagination('loading');
 	                $(this).pagination('loaded');
 	            }
 	        });
 	        
 	     //给时间控件加上样式
            $("#tErReviewMainListtb").find("input[name='planReviewDateBegin_begin']").attr("class", "Wdate").attr("style",
                    "height:20px;width:100px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
            $("#tErReviewMainListtb").find("input[name='planReviewDateBegin_end']").attr("class", "Wdate").attr("style",
                    "height:20px;width:100px;").click(function() {
                WdatePicker({
                    dateFmt : 'yyyy-MM-dd'
                });
            });
 });
 
 function EnterPress(e){
     var e = e || window.event;
     if(e.keyCode == 13){
         tPmProjectListsearch();
      }
    }
 function searchReset(name){
     $("#"+name+"tb").find(":input").val("");
     $('#tErReviewMainList').datagrid("reload",{});
 }
 
 //条件查询
 function tPmProjectListsearch(){
     var queryParams=$('#tErReviewMainList').datagrid('options').queryParams;
     var accordingNum = $('#tErReviewMainListtb').find('input[name="accordingNum"]').val();
     if(accordingNum!=""){
       queryParams['accordingNum'] = accordingNum;
     }
     var planReviewDate_begin = $('#tErReviewMainListtb').find('input[name="planReviewDateBegin_begin"]').val();
     if(planReviewDate_begin!=""){
       queryParams['planReviewDateBegin_begin'] = planReviewDate_begin;
     }
     var planReviewDate_end = $('#tErReviewMainListtb').find('input[name="planReviewDateBegin_end"]').val();
     if(planReviewDate_end!=""){
       queryParams['planReviewDateBegin_end'] = planReviewDate_end;
     }
     $('#tErReviewMainList').datagrid("reload",queryParams);
 }
 
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
    createdetailwindow("查看",'tErReviewMainController.do?goExpertReviewInfo&load=detail&id='+id,"100%","100%");('查看','tErReviewMainController.do?goUpdate','tErReviewMainList',null,null);
}

function edit(id){
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:tErReviewMainController.do?goExpertReviewInfo&id='+id,
            title : '专家评审打分',
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
}

//查看详情
function viewMain(){
    detail("查看",'tErExpertController.do?goExpertReviewMgr',"tErReviewMainList","100%","100%");
}

function editMain(id){
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:tErExpertController.do?goExpertReviewMgr&id='+id,
            title : '编辑评审',
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
                    iframe.saveExpertReview();
                    return false;
                },
                focus : true
            }, {
                name : '取消',
                callback : function() {
                }
            } ]
        }).zindex();
    }else{
        W.$.dialog({
            content : 'url:tErExpertController.do?goExpertReviewMgr&id='+id,
            title : '编辑评审',
            lock : true,
            parent:windowapi,
            width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
            opacity : 0.4,
            button : [ {
                name : '确定',
                callback : function(){
                    var iframe = this.iframe.contentWindow;
                    iframe.saveExpertReview();
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
}

/**
 * 提交评审
 */
function submit(id){
    if(typeof(windowapi) == 'undefined'){
        $.dialog.confirm("确定提交评审吗？", function() {
            doSubmit("tErReviewMainController.do?submitReview","tErReviewMainList",{'id':id});
        }, function() {
        }).zindex();
    }else{
        W.$.dialog.confirm("确定提交评审吗？", function() {
            doSubmit("tErReviewMainController.do?submitReview","tErReviewMainList",{'id':id});
        }, function() {
        },windowapi).zindex();
    }
}

/**
 * 删除评审
 */
function deleteReview(id){
    delObj("tErReviewMainController.do?doDel&id="+id ,'tErReviewMainList')
}

//新增专家评审
function addExpertJudge(){
    /* add("添加专家评审","tErExpertController.do?goExpertReviewMgr",'','100%','100%'); */ 
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:tErExpertController.do?goExpertReviewMgr',
            title : '添加专家评审',
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
                    iframe.saveExpertReview();
                    return false;
                },
                focus : true
            }, {
                name : '取消',
                callback : function() {
                }
            } ]
        }).zindex();
    } else {
        W.$.dialog({
            content : 'url:tErExpertController.do?goExpertReviewMgr',
            title : '添加专家评审',
            lock : true,
            parent : windowapi,
            width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
            opacity : 0.4,
            button : [ {
                name : '确定',
                callback : function(){
                    var iframe = this.iframe.contentWindow;
                    iframe.saveExpertReview();
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
}

function reloadTable(){
    $("#tErReviewMainList").datagrid("reload");
}
 </script>