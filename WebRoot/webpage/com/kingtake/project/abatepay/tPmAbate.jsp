<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>减免垫支信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(document).ready(function() {
    });
</script>
<style type="text/css">
#amountTab {
	border: solid 1px blue;
	border-collapse: collapse;
	width: 436px;
}

#amountTab tr td {
	border: solid 1px blue;
	text-align: center;
}

#amountTab input {
	border:  1px red;
    width: 100px;
}
</style>
<script type="text/javascript">
    $(function() {
    	if("${typeUpdate }"==1){
    		$("#jxval").val("${tPmAbatePage.profor_ratio }");
    		$("#jdfval").val("${tPmAbatePage.moto_ratio }");
    		$("#fcbjme").hide();
    		$("#wxjme").hide();
    		$("#xzjme").hide();
    		$("#dxyj").hide();
    		$("#xyyj").hide();
    		$("#xyj").hide();
    		//$("#jysyl").hide();
    		$("#tjButton").hide();
    	}else if("${typeUpdate }"==2){
    		$("#jxval").val("${tPmAbatePage.profor_ratio }");
    		$("#jdfval").val("${tPmAbatePage.moto_ratio }");
    		$("#fcbjme").hide();
    		$("#wxjme").show();
    		$("#xzjme").show();
    		$("#dxyj").show();
    		$("#xyyj").show();
    		$("#xyj").show();
    		//$("#jysyl").show();
    		$("#tjButton").show();
    	}else{
    		$("#jxval").val("${PROFOR_RATIO }");
    		$("#jdfval").val("${MOTO_RATIO }");
    		$("#fcbjme").hide();
    		$("#wxjme").hide();
    		$("#xzjme").hide();
    		$("#dxyj").hide();
    		$("#xyyj").hide();
    		$("#xyj").hide();
    		//$("#jysyl").hide();
    		$("#tjButton").hide();
    	}
    	$("#jx").val($("#allFee").val()*$("#jxval").val()/100);
    	$("#jdf").val($("#allFee").val()*$("#jdfval").val()/100);
    	$("#payFunds").numberbox('setValue', parseFloat($.trim($("#jx").val()))+parseFloat($.trim($("#jdf").val())));
        $("#amountTab .amount").blur(function() {
            calculate();
        });
        
        $("#amountTab .percent").blur(function() {
            var allFee = parseFloat($.trim($("#allFee").val()));
            var bl = isNaN(parseFloat($.trim($(this).val()))) ? 0.00 : parseFloat($.trim($(this).val()));
            var amount = allFee*bl/100;
            $(this).parent().next("td").find("input.amount").numberbox("setValue",amount);
            calculate();
        });
        
        $("#jxval").blur(function() {
            $("#jx").val($("#allFee").val()*$("#jxval").val()/100);
            var a = parseFloat($.trim($("#jx").val()));
            var b = parseFloat($.trim($("#jdf").val()));
            $("#payFunds").numberbox('setValue', a+b);
        });
        
        $("#jdfval").blur(function() {
            $("#jdf").val($("#allFee").val()*$("#jdfval").val()/100);
            var a = parseFloat($.trim($("#jx").val()));
            var b = parseFloat($.trim($("#jdf").val()));
            $("#payFunds").numberbox('setValue', a+b);
        });
        
        //直接减免额
        $("#zdfcbjme").blur(function() {
            $("#fcb").val(($("#zdfcbjme").val()/$("#allFee").val()*100).toFixed(2));
        });
        
        //外协减免额
        $("#zdwxjme").blur(function() {
        	$("#wx").val(($("#zdwxjme").val()/$("#allFee").val()*100).toFixed(2));
        });
        
        //外协预留比例
        $("#xnxz").blur(function() {
        	$("#xnxzjme").val($("#xnxz").val()*$("#zdwxjme").val());
        });
        
        var opt = $("#opt").val();
        if(opt=='audit'){
        	//无效化所有表单元素，只能进行查看
        	if("${taskName }"=="jgsh"){
        		$("#fcbjme").show();
        		$("#wxjme").show();
        		$("#xzjme").show();
        		$("#dxyj").show();
        		$("#xyyj").show();
        		$("#xyj").show();
        		//$("#jysyl").show();
        		$("#tjButton").show();
        	}else{
        		$(":input").attr("disabled","true");
        		$(":input").css("background-color","#FFFFCC");
        		$("#fcbjme").hide();
        		$("#wxjme").hide();
        		$("#xzjme").hide();
        		$("#dxyj").hide();
        		$("#xyyj").hide();
        		$("#xyj").hide();
        		//$("#jysyl").show();
        		$("#tjButton").hide();
        	}
    		//隐藏选择框和清空框
    		$("a[icon='icon-search']").css("display","none");
    		$("a[icon='icon-redo']").css("display","none");
    		$("a[icon='icon-save']").css("display","none");
    		$("a[icon='icon-ok']").css("display","none");
    		//隐藏下拉框箭头
    		$(".combo-arrow").css("display","none");
    		//隐藏添加附件
    		$("#filediv").parent().css("display","none");
    		//隐藏附件的删除按钮
    		$(".jeecgDetail").parent().css("display","none");
    		//隐藏easyui-linkbutton
    		$(".easyui-linkbutton").css("display","none");
        }

    });

    function calculate() {
        var zdfcbjme = isNaN(parseFloat($.trim($("#zdfcbjme").val()))) ? 0.00
                : parseFloat($.trim($("#zdfcbjme").val()));
        var zdwxjme = isNaN(parseFloat($.trim($("#zdwxjme").val()))) ? 0.00 : parseFloat($.trim($("#zdwxjme").val()));
        var xnxzjme = isNaN(parseFloat($.trim($("#xnxzjme").val()))) ? 0.00 : parseFloat($.trim($("#xnxzjme").val()));
        var dxylje = isNaN(parseFloat($.trim($("#dxylje").val()))) ? 0.00 : parseFloat($.trim($("#dxylje").val()));
        var xyylje = isNaN(parseFloat($.trim($("#xyylje").val()))) ? 0.00 : parseFloat($.trim($("#xyylje").val()));
        var xylje = isNaN(parseFloat($.trim($("#xylje").val()))) ? 0.00 : parseFloat($.trim($("#xylje").val()));
        var jysylje = isNaN(parseFloat($.trim($("#jysylje").val()))) ? 0.00 : parseFloat($.trim($("#jysylje").val()));
        var sum = zdfcbjme + zdwxjme + xnxzjme + dxylje + xyylje + xylje + jysylje + parseFloat($.trim($("#jx").val())) + parseFloat($.trim($("#jdf").val()));
        $("#payFunds").numberbox('setValue', sum.toFixed(2));
    }
    
    function saveCallback(data) {
            var win;
            var dialog = W.$.dialog.list['processDialog'];
            if(dialog==undefined){
                win = frameElement.api.opener;
            }else{
                win = dialog.content;
            }
            if (data.success) {
                win.reloadTable();
                frameElement.api.close();
            }
    }

    function uploadCallback(data) {
        var win;
        var dialog = W.$.dialog.list['processDialog'];
        if(dialog==undefined){
            win = frameElement.api.opener;
        }else{
            win = dialog.content;
        }
        win.reloadTable();
        frameElement.api.close();
        win.tip("保存成功!");
    }
    
    function checkAmount(){
    	var amountArr = ['zdfcbjme','zdwxjme','xnxzjme','dxylbl','dxylje','xyylbl','xyylje','xylbl','xylje','jysylbl','jysylje','jx','jdf',];
    	for(var i=0;i<amountArr.length;i++){
    		var temp = amountArr[i];
    		var je = $("#"+temp).val();
    		if(je==""){
    			$("#"+temp).numberbox('setValue',0.00);
    		}
    	}
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmAbateController.do?doUpdate" tiptype="1" beforeSubmit="checkAmount" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tPmAbatePage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tPmAbatePage.projectId }">
    <input id="bpmStatus" type="hidden" value="${tPmAbatePage.bpmStatus}">
    <input id="opt" type="hidden" value="${opt}">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
        <td align="right"><label class="Validform_label">总经费：</label></td>
        <td class="value"><input id="allFee" class="inputxt" readonly="readonly" style="text-align: right;background-color: #EBEBE4;" name="allFee" value="${tPmAbatePage.allFee}" class="easyui-numberbox" data-options="min:0,precision:2">
          <span class="Validform_checktip"></span></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">减免金额
            <br />
                                    组成： </label> <font color="red">*</font>
          </td>
          <td class="value">
            <table id="amountTab" cellpadding="0" cellspacing="1">
              <tr>
                <td ></td>
                <td style="width: 100px;">比例(%)</td>
                <td style="width: 100px;">金额(元)</td>
              </tr>
              <tr>
                <td >绩效</td>
                <td><input type="text" id="jxval" name="profor_ratio" value=""  data-options="min:0,precision:2"></td>
                <td><input type="text" id="jx" name="profor" value="" readonly="readonly" style="background-color:#EBEBE4" data-options="min:0,precision:2" /></td>
              </tr>
              <tr>
                <td >机动费</td>
                <td><input type="text" id="jdfval" name="moto_ratio" value=""  data-options="min:0,precision:2"></td>
                <td ><input type="text" id="jdf" name="moto" value="" readonly="readonly" style="background-color:#EBEBE4" data-options="min:0,precision:2"></td>
              </tr>
              <tr id="fcbjme" style="display:none;">
                <td >直接减免额</td>
                <td><input type="text" id="fcb" name="fcb_ratio" readonly="readonly" style="background-color: #EBEBE4;" value="${tPmAbatePage.fcb_ratio }"  data-options="min:0,precision:2"></td>
                <td ><input type="text" id="zdfcbjme" name="zdfcbjme" value="${tPmAbatePage.zdfcbjme }" class="easyui-numberbox amount" data-options="min:0,precision:2"></td>
              </tr>
              <tr id="wxjme" style="display:none;">
                <td >外协减免额</td>
                <td><input type="text" id="wx" name="wx_ratio" readonly="readonly" style="background-color: #EBEBE4;" value="${tPmAbatePage.wx_ratio }"  data-options="min:0,precision:2"></td>
                <td><input type="text" id="zdwxjme" name="zdwxjme" value="${tPmAbatePage.zdwxjme }" class="easyui-numberbox amount" data-options="min:0,precision:2"></td>
              </tr>
              <tr id="xzjme" style="display:none;">
                <td >外协预留比例</td>
                <td><input type="text" id="xnxz" name="xnxz_ratio" value="${tPmAbatePage.xnxz_ratio }"  data-options="min:0,precision:2"></td>
                <td><input type="text" id="xnxzjme" name="xnxzjme" readonly="readonly" style="background-color: #EBEBE4;" value="${tPmAbatePage.xnxzjme }"  data-options="min:0,precision:2"></td>
              </tr>
              <tr id="dxyj" style="display:none;">
                <td >学校预留比例</td>
                <td ><input type="text" id="dxylbl" name="dxylbl" value="${tPmAbatePage.dxylbl }" class="easyui-numberbox percent" 
                    data-options="min:0"></td>
                <td ><input type="text" id="dxylje" name="dxylje" value="${tPmAbatePage.dxylje }" class="easyui-numberbox amount" style="background-color: #EBEBE4;" readonly="readonly"
                    data-options="min:0,precision:2"></td>
              </tr>
              <tr id="xyyj" style="display:none;">
                <td >承研单位预留比例</td>
                <td ><input type="text" id="xyylbl" name="xyylbl" value="${tPmAbatePage.xyylbl }" class="easyui-numberbox percent" 
                    data-options="min:0"></td>
                <td><input type="text" id="xyylje" name="xyylje" value="${tPmAbatePage.xyylje }" class="easyui-numberbox amount" style="background-color: #EBEBE4;" readonly="readonly"
                 data-options="min:0,precision:2"></td>
              </tr>
              <tr id="xyj" style="display:none;">
                <td >责任单位预留比例</td>
                <td ><input type="text" id="xylbl" name="xylbl" value="${tPmAbatePage.xylbl }" class="easyui-numberbox percent" 
                    data-options="min:0"></td>
                <td><input type="text" id="xylje" name="xylje" value="${tPmAbatePage.xylje }" class="easyui-numberbox amount" style="background-color: #EBEBE4;" readonly="readonly" 
                 data-options="min:0,precision:2"></td>
              </tr>
              <tr id="jysyl" style="display:none;">
                <td >教研室预留</td>
                <td ><input type="text" id="jysylbl" name="jysylbl" value="${tPmAbatePage.jysylbl }" class="easyui-numberbox percent" value="5"
                    data-options="min:0"></td>
                <td><input type="text" id="jysylje" name="jysylje" value="${tPmAbatePage.jysylje }" class="easyui-numberbox amount" style="background-color: #EBEBE4;" readonly="readonly" 
                data-options="min:0,precision:2"></td>
              </tr>
              <tr>
                <td colspan="2">合计</td>
                <td ><input type="text" id="payFunds" name="payFunds" value="${tPmAbatePage.payFunds }" class="easyui-numberbox"
                    data-options="min:0,precision:2" datatype="*" nullMsg="请填写减免金额组成"></td>
              </tr>
            </table> 
            <span class="Validform_checktip"></span>
           </td>
        </tr>
      <tr>
        <td align="right"><label class="Validform_label">减免理由：</label></td>
        <td class="value"><textarea id="reason" style="width: 430px;" class="inputxt" rows="6" ignore="ignore" datatype="*2-400" name="reason">${tPmAbatePage.reason}</textarea>
          <span class="Validform_checktip"></span></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 减免具体意见:</label></td>
        <td class="value"><textarea id="suggestion" name="suggestion" rows="2" ignore="ignore" datatype="*2-50" style="width: 430px" class="inputxt">${tPmAbatePage.suggestion}</textarea>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">减免具体意见</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td class="value"><input type="hidden" value="${tPmAbatePage.attachmentCode}" id="bid" name="attachmentCode" />
          <table style="max-width: 430px;" id="fileShow">
            <c:forEach items="${tPmAbatePage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload"  buttonText="添加文件" 
	      	                   formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess"
              uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmAbate" >
            </t:upload>
          </div></td>
      </tr>
      <tr style="display: none" id="tjButton"><td align="center" colspan="3"><input type="button" class="Button" onclick="abateSubmit();" value="提交" height="30px;"></td></tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
<script type="text/javascript">
	function abateSubmit(){
		var jdf = $("#jdf").val();
		
		//总经费
		var zs = parseFloat($.trim($("#payFunds").val()));
		//直接经费
		var zjjf = parseFloat($.trim($("#zdfcbjme").val()));
		//外协经费
		var wxjf =  parseFloat($.trim($("#zdwxjme").val()));
		//机动费比例
		var jdfbl =  parseFloat($.trim($("#jdfval").val()));
		//外协预留比例
		var wxbl =  parseFloat($.trim($("#xnxz").val()));
		
		var jdf1 = (zs-zjjf-wxjf)*jdfbl+wxjf*wxbl;
		var jdf2 = parseFloat($.trim($("#dxylje").val()))+parseFloat($.trim($("#xylje").val()))+parseFloat($.trim($("#xyylje").val()));
		if(jdf!=jdf1 || jdf!=jdf2){
			alert("费用填写有误");
		}else{
			$("#formobj").submit();
		}
	}
</script>