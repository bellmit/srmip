<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务处理</title>
  <t:base type="jquery,tools,easyui,DatePicker"></t:base>
   <style type="text/css">
   	.t_table td label {font-size:15px;}
  </style>
  <script type="text/javascript">
  function uploadCallback(){
		parent.W.tip("任务办理成功！");
		parent.W.reloadTable();
		parent.windowapi.close();
}
  
 //设置值
 function setValue(){
     var v = $("#sendMsg").val();
     if(v=="1"){
         $("#sendMsg").val("0");
     }else{
         $("#sendMsg").val("1");  
     }
 }
  </script>
 </head>
 <body>
   <t:formvalid formid="formobj" layout="table" dialog="true" usePlugin="password">
	   <input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	   <input name="bormoney" id="bormoney" type="hidden" vartype="B" value="${bormoney}">
	   <input name="keys" id="keys" type="hidden" />
	   <input name="values" id="values" type="hidden" />
	   <input name="types" id="types" type="hidden" />
       <input name="nextCodeCount" id="nextCodeCount" type="hidden" value="${nextCodeCount}"/>
    	<div  class="ui-widget-content ui-corner-all" style="padding: 10px; margin: 10px;">
	    	<div style="margin: 15px auto; height: 50px; width: 900px;" id="tabs-project">
		    	<c:if test="${bpmLogListCount-3 > 0}">
		    		<div class="progress"></div>
		    		<div class="progress"></div>
		    	</c:if>
		    	 <c:forEach items="${bpmLogList}" var="bpmLg" varStatus="name" >
		    	 	<c:if test="${name.index < bpmLogNewListCount}">
		    	 		<div class="progress progress"></div>
		    	 		<div class="progress progress1">
			    	 		<div class="detial">
						       <b>${bpmLg.task_node }</b><br/>
						        [<span style="color:red;">时间:
						       	<fmt:formatDate value="${bpmLg.op_time }" pattern="MM-dd HH:mm:ss"/></span>]<br/>
						       [<span>操作人：${bpmLg.op_name }]</span>
						    </div>
					    </div>
		    	 	</c:if>
		    	 </c:forEach>
		    	 <c:if test="${taskName != null }">
		    	 	<div class='progress progress_cancel'></div>
		    	 	<div class="progress progress3">
			    	 	<div class="detial">
			                <span><b>${taskName}</b></span><br>
							[<span>任务人：</span>]<br>
			          	</div>
		          	</div>
		    	 </c:if>
		    	 <div class='progress progress_unstart'></div>
				 <div class='progress progress_unstart'></div>
				 <div class='progress progress_unstart'></div>	
	    	 </div>
    	 </div>
       <br/>
       <br/>
       <fieldset style="border-color:#F5F5F5;">
         <legend onclick="show_hide('baseData','showTxt')"><span>意见信息[<label id='showTxt'><font style="color:blue;">点击展开</font></label>]</span></legend>
       <div id="baseData" style="display:none">
    	 <table cellpadding="0" cellspacing="1" class="formtable t_table" >
		     <c:forEach items="${bpmLogList}" var="bpmLog">
		     	<tr height="35">
		     		<td class="value" style="padding: 0px 5px;border-top:1px dashed #00CCCC; font-size:13px;">
		     			<fmt:formatDate value="${bpmLog.op_time }" pattern="yyyy-MM-dd HH:mm:ss"/>[${bpmLog.op_name }]
		     		</td>
		     	</tr>
		     	<tr height="35">
		     		<td class="value" style="padding: 0px 5px;font-size:13px;">
		     			[<span style="color:blue">${bpmLog.task_node }</span>]${bpmLog.memo }
		     		</td>
		     	</tr>
		     	<tr>
		     		<td class="value" style="padding: 0px 5px;">
			     		<table style="max-width:600px;">
				     		<c:forEach items="${bpmLog.bpmFiles}" var="bpmFile" varStatus="idx">
				     			<tr style="heigth:35px;">
					     			<td>[<span style="color:blue">附件</span>]</td>
									<td><a href="javascript:void(0);"
										onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab2&bid=${bpmLog.id}&index=${idx.index}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile',1000,700)">${bpmFile.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
									<td  style="width:40px;"><a href="commonController.do?viewFile&fileid=${bpmFile.id}&subclassname=org.jeecgframework.workflow.pojo.base.TPBpmFile" title="下载">下载</a></td>
								</tr>
							</c:forEach>
						</table>
		     		</td>
		     	</tr>
		     </c:forEach>
         </table>
         </div>
         </fieldset>
         <table cellpadding="0" cellspacing="1" class="formtable t_table" >
		     <tr height="35" >
		     	<td class="value" style="padding: 0px 5px;">
		     		 <label class="Validform_label" style="font-size:14px;">
				      	处理意见<p></p>
				     </label>
				     <textarea name="reason"  datatype="*" vartype="S" style="resize: none;" rows="3" cols="105"></textarea>
		     		<span class="Validform_checktip"></span>
		     	</td>
		     </tr>
		    <tr> 
			  <td class="value" style="padding: 0px 5px;">
			  	<div class="form jeecgDetail" style="padding: 3px;">
			    	<input type="hidden" id="bpmlogId" name="bpmlogId" />
					<br/><t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;" buttonText="添加文件" formData="bpmlogId" uploader="activitiController.do?saveBpmFiles" dialog="false" callback="uploadCallback">
					</t:upload>
				</div>
				<div class="form" id="filediv" style="height: 50px"></div>
				</td>
			</tr> 
			<tr> 
              <input type="hidden" name="model" value="1">
			  <%-- <td class="value">
				 <input type="radio" name="model" value="1" onchange="changeModel(1);" checked/>单分支模式
				 <input type="radio" name="model" value="2" onchange="changeModel(2);"/>多分支模式
				 <span id="manyModel" style="display:none">
				 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">多分支模式默认执行所有分支：</span>
					<c:forEach items="${transitionList}" var="trans">
						<input type="checkbox" name="transition" value="${trans.nextnode}" checked disabled>${trans.Transition }
					</c:forEach>
		  		</span>
		  		<c:if test="${histListSize > 0 }">
			  		<input type="radio" name="model" value="3" onchange="changeModel(3);"/>驳回
			  		<span id="rejectModel" style="display:none">
			  			<select name="rejectModelNode">
			  				<c:forEach items="${histListNode}" var="histNode">
			  					<option value="${histNode.task_def_key_}">${histNode.name_ }</option>
			  				</c:forEach>
			  			</select>
			  		</span>
		  		</c:if>
			  </td> --%>
		 	</tr>
		 	<tr> 
			  <td class="value">
				 指定下一步操作人：
				 <input type="text" id="realName">  
				 <input name="nextuser" type="hidden" id="last" readonly="readonly"> 
				 <t:chooseUser idInput="id" icon="icon-search" title="用户列表" inputTextname="last,realName" textname="userName,realName" isclear="true" mode="single"></t:chooseUser>(如果不指定则按照系统默认)
			  </td>
		 	</tr>
            <tr> 
               <td class="value"  align="left">
                 <input id="sendMsg" type="checkbox" name="sendMsg" checked="checked" value="1" onclick="setValue()">是否发送消息
                 <input type="text" name="message" value="${message}" style="width:80%;">
               </td>
            </tr>
			<tr> 
			  <td class="value"  align="center">
			  		<div id="singleModel" style="display:black">
						<input type="hidden" name="option" id="option" />
			  			<input type="hidden" name="nextnode" id="nextnode" />
						<c:forEach items="${transitionList}" var="trans">
							<input type="button" class="Button" onclick="procPass('${trans.Transition }','${trans.nextnode}')" value="${trans.Transition }">
						</c:forEach>
			  		</div>
			  		<div id="manyModelButton" style="display:none">
			  			<input type="button" class="Button" onclick="manyModelSubmit();" value="提交">
			  			<input type="hidden" name="transStr" id="transStr">
			  		</div>
				</td>
			</tr>
			
		 </table>
  	</t:formvalid>
    <script type="text/javascript">
		function procPass(yes,nextnode){
			$("#option").val(yes);
			$("#nextnode").val(nextnode);
			var formData = {};
			$(formobj).find("input,textarea,select").each(function(){
				formData[$(this).attr("name")]= $(this).val();
			});
			//ajax方式提交iframe内的表单
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : formData,
				url : 'activitiController.do?processComplete',// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$("#bpmlogId").val(d.obj.id);
						if($(".uploadify-queue-item").length>0){
							upload();
						}else{
							var msg = d.msg;
							parent.W.tip(msg);
							parent.W.reloadTable();
							parent.windowapi.close();
						}	
					}else{
					    showMessage(d.msg);
					}
				}
			});
		}
		
		/**
		 * 单分支模式/多分支模式切换
		*/
		function changeModel(value){
			if(value == 1){
				//单分支模式
				$("#singleModel").show();
				$("#manyModel").hide();
				$("#manyModelButton").hide();
				$("#rejectModel").hide();
			}else if(value == 2){
				//多分支模式
				$("#singleModel").hide();
				$("#rejectModel").hide();
				$("#manyModel").show();
				$("#manyModelButton").show();
			}else{
				$("#singleModel").hide();
				$("#manyModel").hide();
				$("#rejectModel").show();
				$("#manyModelButton").show();
			}
			
		}
		
		/**
		 * 多分支模式 提交
		 */ 
		 function manyModelSubmit(){
		/**	//checkbox 选中
			var transStr = "";
			var trans = $("input[name='transition']");
           	for(i=0;i<trans.length;i++){
                   if(trans[i].checked==true){
                   	transStr += (trans[i].value+',');
                   }
               }
           	$("#transStr").val(transStr);
           	if(transStr == ""){
           		alert("多分支模式必须选择下一步分支");
               	return;
           	}
        */
			var formData = {};
			$(formobj).find("input,textarea,select").each(function(){
				if($(this).attr("name") == 'model'){
					formData[$(this).attr("name")]= $('input[name="model"]:checked').val();
				}else{
					formData[$(this).attr("name")]= $(this).val();
				}
			});
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : formData,
				url : 'activitiController.do?processComplete',// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$("#bpmlogId").val(d.obj.id);
						if($(".uploadify-queue-item").length>0){
							upload();
						}else{
							var msg = d.msg;
							W.tip(msg);
							W.reloadTable();
							windowapi.close();
						}	
					}
				}
			});
		}

					function show_hide(id, text) {
						if (document.all(id).style.display == "") {
							document.all(id).style.display = "none";
							document.all(text).innerHTML = '<font style="color:blue;">点击显示</font>';
							return;
						}
						document.all(id).style.display = "";
						document.all(text).innerHTML = '<font style="color:blue;">点击隐藏</font>';
					}

					//显示消息
					function showMessage(content) {
						$.dialog({
							title : '提示',
							content : content,
							width : 340,
							height : 100,
							left : '100%',
							top : '100%',
							time : 5,
							min : false,
							fixed : true,
							drag : false,
							resize : false,
							close : function() {
							}
						});
					}
				</script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
 </body>
</html>
