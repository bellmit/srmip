
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
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script>
    $(function() {
        var id = $("#reviewProjectId").val();
        $("#reviewTypeTree").tree({
            url : 'expertLoginController.do?getProjectTypeTree&id='+id,
            onClick : function(node){
                $("#projectContentFrame").attr("src",node.attributes.url);
            }        
        });

    });
    
    //回调保存方法
    function saveProjectResult() {
        var param = {};
        var expertResult = $("#expertResult").val();
      /*if (expertResult == "") {
            tip('评审结果不能为空！');
            return param;
        }*/
        param["expertResult"] = expertResult;

        var expertScore = $.trim($("#expertScore").val());
        /* if (expertScore == "") {
            tip('评审分数不能为空！');
            return param;
        }
        if (!/^\d+(\.\d+)?$/.test(expertScore)) {
            tip('评审分数输入不合法，请重新输入！');
            return param;
        }
        var floatVal = parseFloat(expertScore);
        if (isNaN(floatVal) || floatVal<0||floatVal>10) {
            tip('评审分数为0-10之间的整数或小数！');
            return param;
        } */
        param["expertScore"] = expertScore;

        var expertContent = $.trim($("#expertContent").val());
        if (expertContent != "") {
            if(expertContent.length>150){
                tip('评审意见长度不能超过150！');
                return param;
            }
            param["expertContent"] = expertContent;
        }
        var id = $("#id").val();
        if(id!=""){
           param["id"] = id;
        }
        var reviewProjectId = $("#reviewProjectId").val();
        if(reviewProjectId!=""){
           param["reviewProject.id"]=reviewProjectId;
        }
        var win = frameElement.api.opener;
        $.ajax({
            url : 'expertLoginController.do?saveReviewResultForExpert',
            data : param,
            dataType : 'json',
            type : 'POST',
            success : function(data) {
                if (data.success) {
                    frameElement.api.close();
                    win.tip(data.msg);
                    win.$("#tErReviewMainList").datagrid("reload");
                } else {
                    win.tip(data.msg);
                }
            }
        });
        return param;
    }

</script>
</head>
<body>
  <input type="hidden" id="reviewProjectId" value="${reviewProjectEntity.id}">
  <input type="hidden" id="id" value="${suggestionEntity.id}">
  <div class="easyui-layout" fit="true" split="true">
    <div region="north" title="专家评审" style="padding: 1px; height: 84px;">
      <div id="reviewDiv" style="">
        <table>
          <tr>
            <td style="">评审结果：</td>
            <td style=""><t:codeTypeSelect name="expertResult" type="select" codeType="0" code="PSJL"
                id="expertResult" defaultVal="${suggestionEntity.expertResult}"></t:codeTypeSelect></td>
            <td>评审分数(总分10分)：</td>
            <td><input id="expertScore" name="expertScore"
              style="width: 156px; height: 25px; border: solid 1px #54A5D5;" value="${suggestionEntity.expertScore}">
            </td>
            <td>评审意见：</td>
            <td><textarea id="expertContent" name="expertContent"
                style="border-color: #54A5D5; width: 500px;" rows="2">${suggestionEntity.expertContent}</textarea>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div region="center" style="padding: 1px;">
      <div class="easyui-layout" data-options="fit:true,split:true">
        <div data-options="title:'评审材料',region:'west'" style="width:200px;">
          <ul id="reviewTypeTree" class="easyui-tree" >
          </ul>
          <table style="margin-top:10px;width:100%;">
            <c:forEach items="${reviewProjectEntity.reviewMain.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" style="color:red"
                    onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${reviewProjectEntity.reviewMain.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
              </tr>
            </c:forEach>
          </table>
        </div>
        <div data-options="region:'center'">
           <iframe id="projectContentFrame" scrolling="auto" frameborder="0" style="width:100%;height:99%;"></iframe>
        </div>
      </div>
    </div>
  </div>
</body>
</html>