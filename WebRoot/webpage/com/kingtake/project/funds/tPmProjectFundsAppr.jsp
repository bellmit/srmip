<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

 <head>
  <title>收文阅批单信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 <link href="webpage/com/kingtake/project/funds/tPmProjectFundsApprForm.css" rel="stylesheet"> 
 <script type="text/javascript">	

    function initFee(){
    	calcTotalItemFee();
    }
    function calcTotalItemFee(){
    	var allFee =Number(0);
    	var fundsType = $("#fundsType").val();
		$("[feetype='total']").each(function(){
			var pid = $(this).attr('id');
			var totalFee = calcSubItemFee(pid);
			$(this).val(fmoney(totalFee,2)); 
			if(fundsType == '4'){//调整预算
				if(totalFee !=0){
					$(this).css('color','red');
				}else{
					$(this).css('color','#000000');
				}
			}
			allFee = allFee + totalFee;
		});
		return allFee;
    }
    function saveFunds(){
    	$("#formobj").submit();
    }
	function close(){
		frameElement.api.close();
	}
	
    function initEvent(){
    	initSubItemEvent();
    }
    //直接经费输入框，事件
    function initSubItemEvent(){
    	//直接费用
    	$("[feetype='sub'],[feetype='total']").keyup(function(){
    		var allFee = getFundsTotal();
			var realAllFee = calcTotalItemFee();//预算总金额
    		//判断是否超过预算总金额
    		if((realAllFee > allFee) && ( $("#fundsType").val() =='1' ||  $("#fundsType").val() =='3')){
    			//TODO,超过最大金额，不允许，提示最多可输入多少
    			$(this).val("");
    			calcTotalItemFee();
    			return;
    		}
			//如果没有超过总预算
			//计算父类--小计，并重新赋值
			var pid = $(this).attr("pid");
			var totalfee = calcSubItemFee(pid);
			$("#"+pid).val(fmoney(totalfee,2));
			
			//计算 剩余可分配总金额，并重新赋值
			if( $("#fundsType").val() =='1' || $("#fundsType").val() =='3' ){
				var surplusFee = allFee - realAllFee;
				$("#surplusFee").val(fmoney(surplusFee,2));
			}else if($("#fundsType").val() =='4'){
				$("#surplusFee").val(fmoney(realAllFee,2));
			}
    	});
    }
    //获取总预算合计（去千分位格式）
    function getFundsTotal(){
    	return  delcommafy($("#totalFunds").val());
    }
    //计算[一级父类] --小计
    function calcSubItemFee(pid){
      	
    	var allFee = parseFloat($("#BALANCE").val());
    	$("[feetype='sub']").each(function(){
			var fee = $(this).val()?$(this).val():0;
			if(fee){
				allFee =allFee + delcommafy(fee);
			}
		});
		return allFee;
    }
    //判断是否数值
    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    }
	//去除千分位
    function delcommafy(num){
    	 if(!num){
    		 num = "0";
    	 }
    	 num = num+"";
    	 num = num.replace(new RegExp(",","gm"),"");
    	 if(!isNumber(num)){
    		 num = "0";
    	 }
    	 return parseFloat(num);
    }
    //金额转为千分位格式字符串
    function fmoney(s, n) {   
       n = n > 0 && n <= 20 ? n : 2;   
       s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
       var l = s.split(".")[0].split("").reverse(),   
       r = s.split(".")[1];   
       t = "";   
       for(i = 0; i < l.length; i ++ )   
       {   
          t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
       }   
       return t.split("").reverse().join("") + "." + r;   
    } 
    //添加成功，刷新预算列表
    function reloadApprList(data){
    	tip(data.msg);
    	//frameElement.api.opener.tPmProjectFundsApprListsearch();
    	frameElement.api.config.parent.iframe.contentWindow.tPmProjectFundsApprListsearch();
        frameElement.api.close();
    }
	//提交校验
    function checkCon(a,b,c){
    	var fundsType = $('#fundsType').val();
    	if(fundsType == '1' || fundsType == '3'){//编制总预算
    		var allFee =  getFundsTotal();
    		var realAllFee = calcTotalItemFee();//预算总金额
        	if(realAllFee > allFee){
        		tip("预算金额大于总经费！");
        		return false;
        	}else if(realAllFee<allFee){
        		tip("还有差额，请分配完全部金额.");
        		return false;
        	}
        	
    	}else if(fundsType == '2'){//余额总预算
    	}else if(fundsType == '4'){
        	//判断调整还是调增
        	var realAllFee = calcTotalItemFee();//预算总金额
        	if(realAllFee != 0){//调整总预算
        		tip("调整总预算，合计差额必须等于0！");
        		return false;
        		//总预算的合计必须为零
        		//各单项的最终数必须大于已开支数
        		//单项合计不能为负数
        	}
   			var changeFlag = false;
   			$("[feetype='sub'],[feetype='total']").each(function(){
   				var fee = delcommafy($(this).val());
   				if(fee != 0){
   					changeFlag = true;
   				}
   			});
   			if(!changeFlag){
   				tip("未进行任何改变,请重新输入");
   				return false;
   			}
    	}
    	return true;
    }
 </script>
  
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmProjectFundsApprController.do?doAddOrUpdate" callback="@Override reloadApprList" beforeSubmit="checkCon"
    tiptype="1">
    
    

    <input id="id" name="id" type="hidden" value="${appr.id }">
    <input id="projectId" type="hidden" value="${appr.tpId }">
    <input id="balanceId" name="balanceId" type="hidden" value="${appr.balanceId}">
    <!-- 合同类计划类标记 -->
    <input id="planContractFlag" type="hidden" value="${planContractFlag }">
    <!-- 项目类别id -->
    <input id="projectTypeId" type="hidden" value="${projectTypeId }">
    <!-- 承研单位审核意见 -->
    <input id="developerAuditOpinion" name="developerAuditOpinion" type="hidden" value="${appr.developerAuditOpinion }">
    <!-- 责任单位审核意见 -->
    <input id="dutyAuditOpinion" name="dutyAuditOpinion" type="hidden" value="${appr.dutyAuditOpinion }">
    <!-- 科研部计划处审核意见 -->
    <input id="researchAuditOpinion" name="researchAuditOpinion" type="hidden" value="${appr.researchAuditOpinion }">
    <!-- 校务部财务处审核意见 -->
    <input id="financeAuditOpinion" name="financeAuditOpinion" type="hidden" value="${appr.financeAuditOpinion }">
    <!-- 科研部审批意见 -->
    <input id="developerApprovalOpinion" name="developerApprovalOpinion" type="hidden" value="${appr.developerApprovalOpinion }">
    <!-- 是否已完成标记 -->
    <input id="finishFlag" name="finishFlag" type="hidden" value="<%=ApprovalConstant.APPRSTATUS_UNSEND%>">
    <!-- 是否预算变更标记 -->
    <input id="changeFlag" name="changeFlag" type="hidden" value="${appr.changeFlag }">
    <!-- 预算类型 -->
    <input id="fundsType" name="fundsType" type="hidden" value="${appr.fundsType}">
    <!-- 预算类别 -->
    <input id="fundsCategory" name="fundsCategory" type="hidden" value="${type}">
     <!--总经费 -->
    <input id="allFee" name="allFee" type="hidden" value="${allfee}">


  <div width="95%" align="center" style="font-size: 24px;color: #000000;">项目总预算审批表  [<span style="font-size:24" id="fundsTypeName"></span>]</div>
		<table width="100%" border="0" cellspacing="0"  style='border-collapse:collapse;'>
			<tr>
				<td align="left">科目代码：${appr.accountingCode}</td>
				<td align="right">单位：元</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
				<tr>
					<td width="120" align="center" class="title2">承研单位</td>
					<td colspan="3" class="title3">
          				    <input id="departsId" name="departsId" type="hidden" value="${appr.departsId }"> 
							<input id="departsName" name="departsName" type="text" readonly="readonly"
							title="${appr.projectName }" value="${appr.departsName}"  style="border-style:  none none none none;width:96%"> 
							<span class="Validform_checktip"></span> 
							<label class="Validform_label" style="display: none;">承研单位</label>
							
					</td>
					<td width="120" align="center" class="title2">负责人</td>
					<td class="title3">
	                    	<input id="projectManager" name="projectManager" type="hidden" value="${appr.projectManager }"> 
							<input id="projectManagerName" name="projectManagerName" type="text" readonly="readonly"
							title="${appr.projectName }" value="${appr.projectManagerName}"  style="border-style:  none none none none;width:96%"> 
							<span class="Validform_checktip"></span> 
							<label class="Validform_label" style="display: none;">负责人</label>
					</td>
				</tr>
				
				<tr>
					<td width="120" align="center" class="title2">项目名称</td>
					<td colspan="3" class="title3">
							<input id="tpId" name="tpId" type="hidden" value="${appr.tpId }"> 
							<input id="projectName" name="projectName" type="text" readonly="readonly"
							title="${appr.projectName }" value="${appr.projectName}"  style="border-style:  none none none none;width:96%"> 
							<span class="Validform_checktip"></span> 
							<label class="Validform_label" style="display: none;">项目名称</label>
					</td>
					<td width="120" align="center" class="title2">经费性质</td>
					<td class="title3">
							<input id="feeType" name="feeType" type="hidden" value="${appr.feeType}">
							<input type="text" readonly="readonly"
							value="${feeTypeName}" readonly="readonly" style="border-style:  none none none none;width:96%width:96%">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费性质</label>
					</td>
				</tr>
				
				<tr>
					<td width="120" align="center" class="title2">项目来源</td>
					<td colspan="3" class="title3">
							<input id="projectSource" name="projectSource" type="text" readonly="readonly"
							value="${appr.projectSource}" readonly="readonly" style="border-style:  none none none none;width:96%">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费性质</label>					
					</td>
					<td width="120" align="center" class="title2">起止年度</td>
					<td class="title3">
					    ${appr.startYear}~${appr.endYear}
						<input id="startYear" name="startYear" type="hidden" value="${appr.startYear}"/>
						<input id="endYear" name="endYear" type="hidden" value="${appr.endYear}"/>
						
			            <span class="Validform_checktip"></span> 
			            <label class="Validform_label" style="display: none;">起止年度</label>			
					</td>
				</tr>
				
				<tr heigth="50">
					<td width="120" align="center" class="title2">项目组<br>成员名称</td>
					<td colspan="5" class="title3">
							<input id="membersId" name="membersId" type="hidden" value="${appr.membersId}">
							<input id="membersName" name="membersName" type="text" readonly="readonly"
							value="${appr.membersName}" style="border-style:  none none none none;width:96%">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目组成员名称</label>					
					</td>
				</tr>
								
				<tr heigth="50">
					<td width="120" align="center" class="title2">项目的主要内容<br>及技术指标</td>
					<td colspan="5" class="title3">
							<input id="receiveUnitId" name="receiveUnitId" type="hidden" value="${appr.feeType}">
							<input id="receiveUnitName" name="receiveUnitName" type="text" readonly="readonly"
							value="${feeTypeName}" readonly="readonly" style="border-style: none none none none;width:96%">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费性质</label>					
					</td>
				</tr>
				
												
				<tr>
					<td rowspan="${FOUDS_ROW_NUM}" width="120" align="center" class="title2">经费预算</td>
					<td width="120" align="center" class="title2"><font style="text-align: right;">合计</font></td>
					<td colspan="2" class="title3">
						
							<input id="totalFunds" name="totalFunds" type="text"  readonly="readonly"
							value="${appr.totalFunds}" style="font-size:20;border-style: none none solid none;width:97%;text-align: right;">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费性质</label>	
					</td>
					<td id="surplusFeeLabel" width="120" align="center" class="title2">	<font style="text-align: right;">差额:</font></td>
				    <td id="surplusFeeValue" class="title3">
							<input id="surplusFee" name="surplusFee" type="text"  readonly="readonly"
							value="${appr.totalFunds}" style="font-size:20;border-style: none none none none;width:97%;text-align: right;color:red">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>	
					</td>
				</tr>
				<c:forEach items="${TYPE_HAS_SUB.SUB_TYPE}" var="subItem" varStatus="subStatus">	
					<input id="BALANCE" type="hidden"  value="${TYPE_HAS_SUB.BALANCE }" >	
					 <c:if test="${subStatus.count eq 1 || (subStatus.count-1) % 2 eq 0}">      
						 <tr>      
					 </c:if>  
							<c:if test="${subStatus.index ==0}">
								<td rowspan="${TYPE_HAS_SUB.COS_SPAN}"  width="120" align="center" class="title2">${TYPE_HAS_SUB.BUDGET_NAE}</td>
							</c:if>
							<td width="120" align="center" class="title2">${subItem.BUDGET_NAE }</td>
							<td class="title3">
									<c:if test="${subItem.SCALE_FLAG ==1}">
										<input  id="${subItem.ID}" pid="${TYPE_HAS_SUB.ID}" name="${subItem.ID}" feetype="sub" type="text" class="easyui-numberbox"
										   data-options="min:-99999999.99,max:99999999.99,precision:2,groupSeparator:','"
										value="${subItem.MONEY }" style="border-style: solid solid solid solid;width:90%;text-align: right;">
									</c:if>
									<c:if test="${subItem.SCALE_FLAG ==0}">
										<input  id="${subItem.ID}" pid="${TYPE_HAS_SUB.ID}" name="${subItem.ID}" type="text" class="easyui-numberbox"
										value="${subItem.MONEY }" style="border-style: solid solid solid solid;width:90%;text-align: right;" readonly=true >
									</c:if>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">${subItem.BUDGET_NAE}</label>								
							</td>
							<c:if test="${subStatus.last && subStatus.count%2!=0}">
								<td width="120" align="center" class="title2">小计</td>
								<td class="title3">
										<input  id="${TYPE_HAS_SUB.ID}" name="${TYPE_HAS_SUB.ID}" feetype="total" type="text" readonly="readonly"
										value="${TYPE_HAS_SUB.MONEY }" style="border-style: none none none none;width:90%;text-align: right;">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">小计</label>								
								</td>
							</c:if>
					  <c:if test="${subStatus.count % 2 eq 0 || subStatus.count eq 2}">      
						 </tr>      
					  </c:if>  
					  <c:if test="${subStatus.last && subStatus.count%2==0}">
						<tr>
							<td width="120" align="center" class="title2">小计</td>
							<td colspan="3" class="title3">
									<input  id="${TYPE_HAS_SUB.ID}" name="${TYPE_HAS_SUB.ID}" feetype="total" type="text" readonly="readonly"
									value="${TYPE_HAS_SUB.MONEY }" style="border-style: none none none none;width:97%;text-align: right;">
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">小计</label>								
							</td>
						</tr>
					</c:if>
				</c:forEach>
								
				<c:forEach items="${TYPE_NO_SUB}" var="topItem" varStatus="topStatus">
				 		 <c:if test="${topStatus.count eq 1 || (topStatus.count-1) % 3 eq 0}">      
					     	 <tr>      
					     </c:if>  
					     
		     			<td width="120" align="center" class="title2">${topItem.BUDGET_NAE}</td>
						<td class="title3">
								<input  id="${topItem.ID}" pid="${topItem.ID}" name="${topItem.ID}" feetype="total"  type="text" class="easyui-numberbox"
											   data-options="min:-99999999.99,max:99999999.99,precision:2,groupSeparator:','"
								value="${topItem.MONEY }" style="border-style: solid solid solid solid;width:90%;text-align: right;">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">${topItem.BUDGET_NAE}</label>								
						</td>
					     
					     <c:if test="${topStatus.count % 3 eq 0 || topStatus.count eq 3}">      
					     	 </tr>      
					     </c:if>  
				</c:forEach>
				
	 </table>
     <br/>
	 <div style="text-align: center">
	    	<input type="button" value="保存" style="display:none;" id="subButton" class="Button" onclick="saveFunds()" />
	 </div>
    
	</t:formvalid>
</body>


 <script type="text/javascript">		
 $(document).ready(function() {    
	
     initFee();
     initEvent();
 	if("${type}"=="1"){
 		$("#subButton").show();
 		$("#yearFundsPlan").css("background-color","#FFFFCC");
 		$("#voucherNum").css("background-color","#FFFFCC");
 		$("#billNum").css("background-color","#FFFFCC");
 		$("#reinFundsPlan").css("background-color","#FFFFCC");
 	}else{
 		$("#subButton").hide();
 		$("#yearFundsPlan").css("background-color","#FFFFFF");
 		$("#voucherNum").css("background-color","#FFFFFF");
 		$("#billNum").css("background-color","#FFFFFF");
 		$("#reinFundsPlan").css("background-color","#FFFFFF");
 	} 
 	
     //编辑时审批已处理：提示不可编辑
     if (location.href.indexOf("tip=true") != -1) {
         var parent = frameElement.api.opener;
         var msg = $("#tipMsg", parent.document).val();
         tip(msg);
     }
     var fundsId = $("#id").val();
     var planContractFlag = $("#planContractFlag").val();
     if(planContractFlag == <%=ProjectConstant.PROJECT_PLAN%>){
         $("#conContent").css({display:"none"});
     }
     var fundsType = $("#fundsType").val();
     var totalFunds = $("#totalFunds").val();
	 var usedFee = calcTotalItemFee();
	 var fundsId = $("#id").val();
	 if(fundsId){
		 $("#surplusFee").val("0");
	 }
     if(fundsType == '1'){
    	 $("#fundsTypeName").html('编制总预算');
      	 var surplusFee = totalFunds - usedFee;
    	 $("#surplusFee").val(surplusFee);
     }else if(fundsType == '2'){
       	 $("#fundsTypeName").html('年度总预算');
    	 $("#surplusFee").val("0");
     }else if(fundsType == '3'){
    	 $("#fundsTypeName").html('调增预算');
    	 var surplusFee = totalFunds - usedFee;
    	 $("#surplusFee").val(surplusFee);
     }else if(fundsType == '4'){
    	 $("#fundsTypeName").html('调整预算');
    	 $("#surplusFee").val("0");
     }
     var edit = true;
     if("${edit}" ==''){
    	 edit ='true';
     }else{
    	 edit = '${edit}';
     }
  	 if(edit =='false'){
  		$("#subButton").hide();
  	 }
  	
 });
</script>
 
 
 	