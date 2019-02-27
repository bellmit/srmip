<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专家评审信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
a,img {
	border: 0;
}

body {
	font: 12px/180% Arial, Helvetica, sans-serif, "新宋体";
}

.select-result a {
	padding: 0 25px;
	display: inline-block;
	background: #AA36ED url("images/close.gif") top 2px right -4px no-repeat;
	border: solid 1px;
	height: 24px;
	text-decoration: none;
	margin: 2px 5px;
	text-align: center;
	vertical-align: middle;
	color: white;
	border-radius: 4px;
}

.select-result a:hover {
	background-position: top 3px right -3px
}
</style>
<script>
    //回调保存方法
    function saveProjectResult() {
        var param = {};
        var reviewResult = $("#reviewResult").val();
        /* if (reviewResult == "") {
            tip('评审结果不能为空！');
            return param;
        } */
        param["reviewResult"] = reviewResult;

        var reviewScore = $.trim($("#reviewScore").val());
        /* if (reviewScore == "") {
            tip('评审分数不能为空！');
            return param;
        }
        if(!/^\d+(\.\d+)?$/.test(reviewScore)){
            tip('评审分数输入不合法，请重新输入！');
            return param;
        }
        var floatVal = parseFloat(reviewScore);
        if(isNaN(floatVal)||floatVal<0||floatVal>10){
            tip('评审分数为0-10之间的整数或小数！');
            return param;
        } */
        param["reviewScore"] = reviewScore;

        var reviewSuggestion = $.trim($("#reviewSuggestion").val());
        if (reviewSuggestion != "") {
           if (reviewSuggestion.length>250) {
                tip('评审意见长度不能大于250！');
                return param;
           }
           param["reviewSuggestion"] = reviewSuggestion;
        } 
       param["id"]=$("#reviewProjectId").val();
       var win = frameElement.api.opener;
       $.ajax({
           url : 'tErReviewProjectController.do?saveReviewResult',
           data : param,
           dataType : 'json',
           type : 'POST',
           success : function(data) {
               if(data.success){
                 frameElement.api.close();
                 win.tip(data.msg);
                 win.$("#tErReviewMainList").datagrid("reload");
               }else{
                   win.tip(data.msg);
               }
           }
       });
       return param;
    }
    
    //计算平均值
    function calAvgNum(){
    var projectScore = $("#reviewScore").val();
        if (projectScore == "") {
            var rows = $("#expertSuggestionList").datagrid("getRows");
            var sum = 0;
            var count = 0;
            for (var i = 0; i < rows.length; i++) {
                if(rows[i].expertScore==""){
                    continue;
                }
                count++;
                sum = sum + parseFloat(rows[i].expertScore);
            }
            var avgNum = 0.00;
            if (count > 0) {
                avgNum = (sum / count).toFixed(2);
            }
            $("#reviewScore").val(avgNum);
        }
    }
    
    function expand(){
        var id = $("#reviewProjectId").val();
        $("#reviewTypeTree").tree({
            url : 'expertLoginController.do?getProjectTypeTree&id='+id,
            onClick : function(node){
                createdetailwindow("查看评审材料",node.attributes.url,'100%','100%');
            }        
        }); 
    }
    
    var processTypeOpt = 
        [ {
            "processType" : "01",
            "name" : "项目申报"
        }, {
            "processType" : "02",
            "name" : "项目立项"
        }, {
            "processType" : "03",
            "name" : "项目执行"
        }, {
            "processType" : "04",
            "name" : "验收及结题"
        }, {
            "processType" : "05",
            "name" : "成果鉴定"
        } ];
        var apprTypeOpt = {
            "01" : [ {
                "apprType" : "01",
                "name" : "申报书评审"
            }, {
                "apprType" : "02",
                "name" : "进账合同审批"
            } ],
            "03" : [ {
                "apprType" : "01",
                "name" : "来款申请审核"
            }, {
                "apprType" : "02",
                "name" : "出账合同审批"
            } ],
            "04" : [ {
                "apprType" : "01",
                "name" : "结题申请审批"
            }, {
                "apprType" : "02",
                "name" : "合同验收报告"
            } ],
            "05" : [ {
                "apprType" : "01",
                "name" : "鉴定申请审批"
            } ]
        };

        $(function(){
            initSelect();//初始化过程
        });
        
        //初始化process
        function initSelect(){
            for(var i=0;i<processTypeOpt.length;i++){
                $("#processType").append("<option value=\""+processTypeOpt[i].processType+"\">"+processTypeOpt[i].name+"</option>");
            }
            var reviewProcess = $("#reviewProcess").val();
            if (reviewProcess == "") {
                var apprType1 = apprTypeOpt['01'];
                for (var i = 0; i < apprType1.length; i++) {
                    $("#apprType").append("<option value=\""+apprType1[i].apprType+"\">" + apprType1[i].name + "</option>");
                }
            } else {
                    var processArray = reviewProcess.split("_");
                    var apprType1 = apprTypeOpt[processArray[0]];
                    $("#processType").val(processArray[0]);
                    for (var i = 0; i < apprType1.length; i++) {
                        $("#apprType").append("<option value=\""+apprType1[i].apprType+"\">" + apprType1[i].name + "</option>");
                    }
                    $("#apprType").val(processArray[1]);
            }
            $("#processType").change(
                    function() {
                        $("#apprType").empty();
                        var processType = $("#processType").val();
                        var apprTypeList = apprTypeOpt[processType];
                        if (apprTypeList.length > 0) {
                            for (var i = 0; i < apprTypeList.length; i++) {
                                $("#apprType").append(
                                        "<option value=\""+apprTypeList[i].apprType+"\">" + apprTypeList[i].name
                                                + "</option>");
                            }
                        }
                    });

        }
</script>
</head>
<body>
<input type="hidden" id="reviewProjectId" value="${reviewProjectEntity.id}">
  <div class="easyui-layout" fit="true" split="true">
    <div region="west" title="填写评审信息" style="padding: 1px; width: 400px;">
      <form id="mainForm">
        <table style="width: 100%; line-height: 2.5;" cellpadding="0" cellspacing="1">
        <tr style="display:none;">
                <td style="width: width: 135px; padding: 5px;" align="left"><label>
                    选择过程: <font color="red">*</font>
                  </label></td>
                  <td>
                <select id="processType" style="width: 100px;">
                </select>
                <select id="apprType" style="width:150px;">
                </select>
                <input id="reviewProcess" name="reviewProcess" type="hidden" value="${reviewProjectEntity.reviewMain.reviewProcess }">
                </td>
          </tr>
          <tr>
            <td style="width: 35%" align="left"><label> 依据文号: <font color="red">*</font></label></td>
            <td style="width: 65%"><input id="accordingNum" name="accordingNum" type="text" style="width: 160px"
              readonly="readonly" datatype="*" nullmsg="依据文号不能为空" value="${reviewProjectEntity.reviewMain.accordingNum}" > 
               <label class="Validform_label" style="display: none;">依据文号</label>
            </td>
          </tr>
          <tr>
            <td align="left"><label> 评审标题: <font color="red">*</font></label></td>
            <td><input id="reviewTitle" name="reviewTitle" type="text" value="${reviewProjectEntity.reviewMain.reviewTitle}" style="width: 172px;" 
              readonly="readonly"> <label class="Validform_label" style="display: none;">评审标题</label></td>
          </tr>
          <tr>
            <td align="left"><label> 评审内容: <font color="red">*</font></label></td>
            <td><textarea id="reviewContent" name="reviewContent" rows="5" 
                style="width: 172px; border-color: #54A5D5;" readonly="readonly">${reviewProjectEntity.reviewMain.reviewContent}</textarea>
              <label class="Validform_label" style="display: none;">评审内容</label></td>
          </tr>
          <td align="left" style="padding:5px;"><label>
                    计划评审开始时间: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewDateBegin" name="planReviewDateBegin" type="text" style="width: 172px" class="Wdate" disabled="true"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" datatype="date" nullmsg="计划评审开始时间不能为空"
                    value='<fmt:formatDate value='${reviewProjectEntity.reviewMain.planReviewDateBegin}' type="date" pattern="yyyy-MM-dd HH:mm"/>'> <label class="Validform_label" style="display: none;">计划评审开始时间</label></td>                
              </tr>
              <tr>
              	<td align="left" style="padding:5px;"><label>
                    计划评审结束时间: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewDateEnd" name="planReviewDateEnd" type="text" style="width: 172px" class="Wdate" disabled="true" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" datatype="date" nullmsg="计划评审结束时间不能为空"
                    value='<fmt:formatDate value='${reviewProjectEntity.reviewMain.planReviewDateEnd}' type="date" pattern="yyyy-MM-dd HH:mm"/>'> <label class="Validform_label" style="display: none;">计划评审结束时间</label></td>
              </tr>
              <tr>
                <td align="left" style="padding:5px;"><label>
                    专家评审开始时间: 
                  </label></td>
                <td style="padding:5px;"><input id="expertReviewDateBegin" name="expertReviewDateBegin" type="text" style="width: 172px" class="Wdate" disabled="true" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="date" nullmsg="专家评审开始时间不能为空"
                    value='<fmt:formatDate value='${reviewProjectEntity.reviewMain.expertReviewDateBegin}' type="date" pattern="yyyy-MM-dd"/>'> <label class="Validform_label" style="display: none;">专家评审开始时间</label></td>                
              </tr>
              <tr>
              	<td align="left" style="padding:5px;"><label>
                    专家评审结束时间: 
                  </label></td>
                <td style="padding:5px;"><input id="expertReviewDateEnd" name="expertReviewDateEnd" type="text" style="width: 172px" class="Wdate" disabled="true" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="date" nullmsg="专家评审结束时间不能为空"
                    value='<fmt:formatDate value='${reviewProjectEntity.reviewMain.expertReviewDateEnd}' type="date" pattern="yyyy-MM-dd"/>'> <label class="Validform_label" style="display: none;">专家评审结束时间</label></td>
              </tr>
              <tr>
              	<td align="left" style="padding:5px;"><label>
                    计划会审地点: 
                  </label></td>
                <td style="padding:5px;"><input id="planReviewAddress" name="planReviewAddress" type="text" value='${reviewProjectEntity.reviewMain.planReviewAddress}' style="width: 172px;" readOnly="readOnly" nullmsg="计划会审地点不能为空"> <label class="Validform_label" style="display: none;">计划会审地点</label></td>
              </tr>
          <tr>          	
            <td align="left"><label> 评审方式: <font color="red">*</font></label></td>
            <td><t:codeTypeSelect name="reviewMode" type="select" codeType="1" code="PSFS" id="reviewMode" defaultVal="${reviewProjectEntity.reviewMain.reviewMode}"
                extendParam="{style:\"width:178px;\",disabled:disabled}"></t:codeTypeSelect></td>
          </tr>
        </table>
      </form>
    </div>
    <div region="center" style="padding: 1px;">
      <div class="easyui-layout" data-options="fit:true,split:true">
      <div data-options="title:'专家意见',region:'north'" style="height:300px;">
   <t:datagrid name="expertSuggestionList" checkbox="true" fitColumns="true"  actionUrl="tErSuggestionController.do?datagrid&reviewProject.id=${reviewProjectEntity.id}" idField="id" fit="true" queryMode="group" pagination="false" onLoadSuccess="calAvgNum">
   <t:dgCol title="id"  field="id"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目主键"  field="reviewProject_projectId"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="reviewProject_projectName" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专家主键"  field="expertId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专家名称"  field="expertName"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="评审时间"  field="expertTime"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="专家评分(总分10分)"  field="expertScore"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专家结论"  field="expertResult" codeDict="0,PSJL"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="理由或建议"  field="expertContent"    queryMode="group"  width="150"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tErSuggestionController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
  </div>
        <div data-options="title:'机关打分',region:'center'">
        <input type="hidden" id="reviewProjectId" value="${reviewProjectEntity.id}">
        <table style="width: 100%;line-height: 2.5;" cellpadding="0" cellspacing="1">
          <tr>
            <td style="width:20%;">评审结果：</td>
            <td style="width:80%;">
             <t:codeTypeSelect name="reviewResult" type="select" codeType="0" code="PSJL" id="reviewResult" defaultVal="${reviewProjectEntity.reviewResult}"></t:codeTypeSelect>
            </td>
          </tr>
          <tr>
            <td>评审分数(总分10分)：</td>
            <td>
            <input id="reviewScore" name="reviewScore"  style="width:156px;height:25px;border: solid 1px #54A5D5;"   
                 value="${reviewProjectEntity.reviewScore}">  
            </td>
          </tr>
          <tr>
            <td>评审意见：</td>
            <td><textarea id="reviewSuggestion" name="reviewSuggestion" style="width:90%;border-color: #54A5D5;" rows="4">${reviewProjectEntity.reviewSuggestion}</textarea></td>
          </tr>
        </table>
      </div>
      </div>
      </div>
      <div region="east" style="padding: 1px;" data-options="title:'评审材料',collapsed:true,onExpand:expand">
          <ul id="reviewTypeTree" class="easyui-tree" >
          </ul>
      </div>
     </div>
</body>
</html>