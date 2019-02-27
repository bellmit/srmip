<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文档检索</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
 
<style type="text/css">
body, h1, h2, h3, h4, h5, h6, hr, p, blockquote, dl, dt, dd, ul, ol, li,
  pre, form, fieldset, legend, button, input, textarea, th, td {
  margin: 0;
  padding: 0
}

html {
  color: #000;
  overflow-y: scroll;
  overflow: -moz-scrollbars-vertical
}

body, button, input, select, textarea {
  font-size: 12px;
  font-family: arial, 'Hiragino Sans GB', 'Microsoft Yahei', '微软雅黑', '宋体',
    \5b8b\4f53, Tahoma, Arial, Helvetica, STHeiti
}

button, input, select, textarea {
  font-size: 100%
}

button {
  cursor: pointer
}

.inline {
  display: inline
}

.error {
  color: #F00;
  font-size: 12px
}

.wordwrap {
  word-break: break-all;
  word-wrap: break-word
}

body {
  text-align: center
}

body, form, #s_fm {
  position: relative
}

#kw {
  width: 521px;
  height: 20px;
  padding: 9px 7px;
  padding: 11px 7px 7px\9;
  font: 16px arial;
  border: 1px solid #d8d8d8;
  border-bottom: 1px solid #ccc;
  vertical-align: top;
  outline: none
}

#kw {
  margin: 0
}

.btn_wr {
  position: absolute;
  left: 536px;
  top: 0;
  *top: 1px;
  margin: 0 3px 0 0
}

#nv a, #nv b, .btn, #lk {
  font-size: 14px
}

.btn {
  cursor: pointer;
  color: white;
  background-color: #38f;
  width: 102px;
  height: 38px;
  font-size: 16px;
  border: 0
}

.s_btn_wr {
  width: 102px;
  height: 38px;
  border: 1px solid #38f;
  border-bottom: 1px solid #2e7ae5;
  background-color: #38f
}

.s_btn_wr:hover {
  border-bottom: 1px solid #2771d9
}

.s_form {
  text-align: left;
  padding-left: 50px;
  z-index: 300;
  height: 43px
}

#head_wrapper {
  width: 680px;
  margin: 0 auto;
  padding-top: 36px
}

textarea:focus{outline:0;}

</style>
</head>

<body>
  <div id="wrapper">
    <div id="head" class="s-tips">
      <div id="head_wrapper" class="s-isindex-wrap head_wrapper s-title-img " style="width: 740px;">
        <div id="s_fm" class="s_form">
          <div class="s_form_wrapper" id="s_form_wrapper">
              <span id="s_kw_wrap" class=" s_ipt_wr quickdelete-wrap"> <input type="text" class="s_ipt" name="wd" id="kw" maxlength="100" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
              </span> <span class="btn_wr s_btn_wr " id="s_btn_wr"> <input type="button" value="文档检索" id="su" class="btn self-btn s_btn" onclick="search()">
              </span> <input type="hidden" name="rsv_enter" value="1">
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <table id="result" width="80%" border="0" cellpadding="0" cellspacing="0" style="margin-left: 10%;">
  </table>
  
  <div id="page" style="margin-left: 10%; width:80%; height:40px; line-height:40px; font-size:15px; visibility: hidden;">
  	共<label id="pages">1</label>页
  	第&nbsp;<input id="currentPage" value="1" style="width:30px;"/>&nbsp;页
  	<a href="#" onclick="toPage()">GO</a>&nbsp;&nbsp;
  	<a href="#" id="first" onclick="firstPage()">首页&nbsp;&nbsp;</a>
  	<a href="#" id="pre" onclick="prePage()">上一页&nbsp;&nbsp;</a>
  	<a href="#" id="next" onclick="nextPage()">下一页&nbsp;&nbsp;</a>
  	<a href="#" id="last" onclick="lastPage()">尾页</a>
  </div>

</body>

<script type="text/javascript">
function search(){
	var currentPage = parseInt($("#currentPage").val());
	$.ajax({
		type:'post',
		url:'solrController.do?search',
		data:'key='+$("#kw").val()+'&currentPage='+currentPage,
		success:function(json){
			var table = $("#result");
			// 清空上次的搜索结果
			table.empty();
			$("#page").css("visibility", "hidden");
			
			// 总条数
			var count = parseInt(json.total);
			// 总页数
			var pages = parseInt(json.pages);
			$("#pages").html(pages);
			if(currentPage == 1){
				$("#pre").hide();
			}else{
				$("#pre").show();
			}
			if(currentPage == pages){
				$("#next").hide();
			}else{
				$("#next").show();
			}
		  	
		  	var tr = $("<tr></tr>");
		  	table.append(tr);
		  	var td = $("<td></td>");
		  	td.css("height", "40").css("font-size", "14px").css("text-align", "left");
		  	td.html("为您找到相关结果约"+count+"个");
		  	tr.append(td);
			
			if(parseInt(count) > 0){
				$("#page").css("visibility", "visible");
				
				var rows = json.rows;
				for(var i in rows){
					var data = rows[i];
					
					tr = $("<tr></tr>");
					table.append(tr);
					td = $("<td style='hegiht:40px; line-height:40px; text-align:left; background-color:#F5F5F6; border-bottom:1px dashed black; font-size:15px; font-weight:blod;'></td>");
					tr.append(td);
					
					// 文件类型
					var extend = data.extend;
					var img = $("<img style='margin-top:4px; float:left;' src='webpage/com/kingtake/solr/image/file_extension_"+extend+".png'/>");
					td.append(img);
					// 标题
					var a = $("<a href='javascript:void(0)' onclick='openViewWin(\""+data.id+"\");'></a>");
					td.append(a);
					var title = $("<span style='color:blue'></span>");
					title.html(data.title);
					a.append(title);
					var space = $("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					td.append(space);
					// 下载
					var download;
					if(data.type=='FTP'){
					    download = $("<a href='tODiskController.do?viewDiskFile&fileid="+data.id+"&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0' style='color:blue;'>下载</a>");
					}else{
					    download = $("<a href='commonController.do?viewFile&fileid="+data.id+"&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity' style='color:blue;'>下载</a>");
					}
					td.append(download);
					
					
					tr = $("<tr></tr>");
					table.append(tr);
					td = $("<td style='line-height:30px; text-align:left;'></td>");
					var content = data.content;
					if(content){
						var textarea = $("<textarea style='width:100%; border:none;'>"+content+"</textarea>");
						td.append(textarea);
					}
					tr.append(td);
					
					tr = $("<tr></tr>");
					table.append(tr);
					td = $("<td style='height:20px; line-height:20px; text-align:left;'>&nbsp;</td>");
					tr.append(td);
				}
			}
		}
	});
}

function openViewWin(id){
	openwindow('预览','commonController.do?goAccessory&fileid='+id,'fList',1000,700);
}

function firstPage(){
	$("#currentPage").val(1);	
	search();
}

function lastPage(){
	$("#currentPage").val($("#pages").html());	
	search();
}

function prePage(){
	var currentPage = parseInt($("#currentPage").val());
	$("#currentPage").val(currentPage == 1 ? 1 : currentPage - 1);
	search();
}

function nextPage(){
	var currentPage = parseInt($("#currentPage").val());
	var pages = parseInt($("#pages").html());
	$("#currentPage").val(currentPage == pages ? pages : currentPage + 1);
	search();
}

function toPage(){
	var currentPage = parseInt($("#currentPage").val());
	var pages = parseInt($("#pages").html());
	$("#currentPage").val(currentPage >= pages ? pages : currentPage);
	search();
}

function EnterPress(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        search();
    }
}
</script>
</html>