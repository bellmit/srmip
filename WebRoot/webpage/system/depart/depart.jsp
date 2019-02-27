<%@ page language="java" import="java.util.*,com.kingtake.common.constant.DepartConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<style type="text/css">
   .addition_label{
      /* width: 250px;
      float: left;
      text-align: left;
      margin-right: 15px;
      line-height: 20px; */
      font-size: 12px;
      font-weight: bold;
      color: #5E7595;
      text-shadow: 1px 1px 1px #fff;
      /* overflow:hidden; */
      text-overflow:ellipsis;
    }
    
    .a_label{
      line-height: 20px;
    }
    
</style>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
//update-start--Author:lxp  Date:2015617 for：添加学科方向的处理方法
function changeOrgType() { // 处理组织类型，不显示公司选择项
     var orgTypeSelect = $("#orgType");
     var optionNum = orgTypeSelect.get(0).options.length;
     if(optionNum == 1) {
         $("#orgType option:first").remove();
         var zuzhi = '<option value="0" <c:if test="${orgType=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
         var keyan = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="科研组织机构"/></option>';
         orgTypeSelect.append(zuzhi).append(keyan);
     }
 }
    
//保存时修改机构值
function setSubDicIds() {
   var subDicIds = $("#subdic").combotree("getValues");
   var subDicNames = $("#subdic").combotree("getText");
   var sciGroups = $("#scii").combotree("getValues");
   var sciGroupnames = $("#scii").combotree("getText");
   //scientificInstitutionFlag 是否科研组织机构   1：是   0：否 
   var flag=$("#scientificInstitutionFlag").val();
   var orgIds=$("#cc").val();
   if(flag==<%=DepartConstant.SCIENCE_INSTITU%>){
       if(orgIds=="" || orgIds==null){
        	var tip = $("#departid_").next(".Validform_checktip");
         	tip.text("请至少选择一项!");
         	tip.addClass("Validform_wrong");
         	return false;
       }
   }
   $("#subdicid_").val(subDicIds); 
   $("#subdicname_").val(subDicNames);
   $("#sciGroup_").val(sciGroups); 
   $("#sciGroupname_").val(sciGroupnames);
}
    
function uploadFile(data){
	var id = $("#id").val();
   	var obj = data.obj;
	$("#bid").val(data.obj.id);
	if($(".uploadify-queue-item").length>0){
		upload();
	}else{
		//frameElement.api.opener.reloadTable();
		if(id){
			frameElement.api.opener.updateRow(obj);
		}else{
			frameElement.api.opener.addRow(obj);
		}
		frameElement.api.opener.tip(data.msg);
		frameElement.api.close();
	}
}
	
function close(){
	frameElement.api.close();
}
    
	$(function() {
	  //scientificInstitutionFlag 是否科研组织机构   1：是   0：否
	    var flag=$("input[name='scientificInstitutionFlag']:checked").val();
	    if(flag==""||flag==<%=DepartConstant.ORGANIZE_INSTITU%>){
	        $(".scienInstiFlag").hide();
	    }else{
	        $(".scienInstiFlag").show();
	    }
	  	
	  	$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}&scientificInstitutionFlag='+<%=DepartConstant.ORGANIZE_INSTITU%>,
            width: 155,
            onClick: function(node){
                $("#cc").val(node.id);
		        var orgIds = $("#cc").combotree("getValues");
		        $("#departid_").val(orgIds);
			}
        });	
	  	
         $('#subdic').combotree({
 			 url : 'tBCodeTypeController.do?getCodeTypeTree&codeType=1&code=XKFX&lazy=false',
             width: 155,
             multiple:true, 
             cascadeCheck:false,
             onClick: function(node){
                 $("#subdicid_").val(node.code);
                 $("#subdicname_").val(node.name);
 			},onLoadSuccess:function(){
             }
      	});
         var subdicList = $("#subdicid_").val();
         if(subdicList!=""){
             $("#subdic").combotree("setValues",subdicList.split(","));
         }
        
        $('#scii').combotree({
			url : 'departController.do?getDepartGroup',
            width: 155,
            multiple:true, 
            cascadeCheck:false,
            onClick: function(node) {
                $("#scii").val(node.id);
		        var orgIds = $("#scii").combotree("getValues");
		        $("#sciGroup_").val(orgIds);
            },onLoadSuccess:function(){
            }
        });
        var sciList = $("#sciGroup_").val();
        if(sciList!=""){
            $('#scii').combotree("setValues",sciList.split(","));
        } 
       /* $("#scii").combotree("setValues", ); */  
        
        /* if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="0" <c:if test="${orgType=='0'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        } */
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
       /*  if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        } */
        
      	//查看模式情况下,删除和上传附件功能禁止使用
		if(location.href.indexOf("load=detail")!=-1){
			$(".jeecgDetail").hide();
		} 
        
		$("input[name='scientificInstitutionFlag']").click(function(){
		    var flag = $("input[name='scientificInstitutionFlag']:checked").val();
		    if(flag==0){
		        $(".scienInstiFlag").hide("slow");
		    }else{
		        $(".scienInstiFlag").show("slow");
		    }
		});
		
       $("#bmlx").val("${depart.bmlx }");
	});
	
    
</script>
</head>
<body style="overflow-y: auto" scroll="no">
<input id="scientificInstitutionFlag" type="hidden" value="${scientificInstitutionFlag}"> 
<t:formvalid formid="formobj" layout="div" dialog="true" callback="@Override uploadFile" 
	action="systemController.do?saveDepart" beforeSubmit="setSubDicIds">
	<input id="id" name="id" type="hidden" value="${depart.id }">
	<fieldset class="step" style="padding-bottom: 20px;">
        <div class="form">
            <label class="Validform_label">机构名称:<font color="red">*</font>
            </label>
            <input name="departname" class="inputxt" value="${depart.departname }"  datatype="/^[^\s]{2,100}$/">
            <span class="Validform_checktip">机构名称范围2~100位字符，中间不能有空格</span><%-- <t:mutiLang langKey="departmentname.rang1to20"/> --%>
        </div>
        <div class="form">
            <label class="Validform_label"> 机构简称: </label>
            <input name="shortname" class="inputxt" value="${depart.shortname }" datatype="/^[^\s]{2,50}$/"  ignore="ignore">
            <span class="Validform_checktip">机构简称范围2~50位字符，中间不能有空格</span>
        </div>
        <div class="form">
            <label class="Validform_label"> 部门类型: </label>
            <select id="bmlx" name="bmlx">
            	<option value="0">学院</option>
            	<option value="1">直属系</option>
            	<option value="2">基础部</option>
            	<option value="3">振声所</option>
            	<option value="4">电研所</option>
            	<option value="5">校机关</option>
            	<option value="6">其他直属单位</option>
            </select>
            
            <span class="Validform_checktip">请选择部门所属类型</span>
        </div>
        <div class="form">
            <label class="Validform_label"> 是否科研机构:<font color="red">*</font> </label>
            <c:if test="${empty depart.scientificInstitutionFlag }">
                <t:codeTypeSelect id="scientificInstitutionFlag" name="scientificInstitutionFlag" code="SFBZ" codeType="0" type="radio" 
                defaultVal="${scientificInstitutionFlag}" ></t:codeTypeSelect> 
            </c:if>
            <c:if test="${!empty depart.scientificInstitutionFlag }">
                <t:codeTypeSelect id="scientificInstitutionFlag" name="scientificInstitutionFlag" code="SFBZ" codeType="0" type="radio" 
                defaultVal="${depart.scientificInstitutionFlag}"></t:codeTypeSelect> 
            </c:if>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="position.desc"/>: </label>
            <input name="description" class="inputxt" value="${depart.description }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="parent.depart"/>: </label>
            <input id="cc"  value="${depart.TSPDepart.departname}">
            <input id="departid_"  name="TSPDepart.id" type="hidden" value="${depart.TSPDepart.id}" >
            <span class="Validform_checktip"><t:mutiLang langKey="please.select.department"/></span>
        </div>
         <div class="form scienInstiFlag">
            <label class="Validform_label"> 学科方向名称: </label>
            <input id="subdic"  value="${depart.subjectDirectionCode}">
            <input id="subdicid_"  name="subjectDirectionCode" type="hidden" value="${depart.subjectDirectionCode}">
        	<input id="subdicname_"  name="subjectDirectionName" type="hidden" value="${depart.subjectDirectionName}">
        </div>
        <div class="form scienInstiFlag">
            <label class="Validform_label"> 科研机构组成单位: </label>
            <input id="scii"  value="${depart.sci_insti_group}">
            <input id="sciGroup_" name="sci_insti_group" type="hidden" value="${depart.sci_insti_group }">
            <input id="sciGroupname_" name="sci_insti_groupname" type="hidden" value="${depart.sci_insti_groupname }"> 
        </div>
        <div class="form">
            <label class="Validform_label"> 主管部门: </label>
            <input name="manager_depart" class="inputxt" value="${depart.manager_depart }" >
        </div>
        <div class="form">
            <label class="Validform_label"> 负责参谋: </label>
            <input id="leader_official" name="leader_official" class="inputxt" value="${depart.leader_official }" >
            <input id="leaderOfficialId" name="leaderOfficialId" type="hidden" value="${depart.leaderOfficialId }" >
            <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" 
                        isclear="true" inputTextname="leaderOfficialId,leader_official" 
                        idInput="leaderOfficialId" mode="multiply" ></t:chooseUser>
        </div>
		<div class="form">
            <label class="Validform_label"> 排序码: </label>
            <input name="sortCode" class="inputxt" value="${depart.sortCode }" datatype="n" ignore="ignore">
            <span class="Validform_checktip">可自行填写，为空时自动生成</span>
        </div>
        <div class="form scienInstiFlag">
            <label class="Validform_label"> 批复文件: </label>
            <input type="hidden" value="${depart.id}" id="bid" name="bid" />
            <div class="form">
              <div class="form" id="filediv">
              	<table>
	              <c:forEach items="${depart.certificates}" var="file" varStatus="idx">
	              		<tr>
	                      <td><a class="addition_label" href="javascript:void(0);" onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${depart.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
	                      <td style="width:40px;"><a class="addition_label" href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
	                      <td style="width:40px;"><a  href="javascript:void(0)" class="jeecgDetail addition_label" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
	                     </tr>
	              </c:forEach>
	             </table>
              </div>
              <div class="form jeecgDetail">
                <t:upload name="fiels" id="file_upload" 
                	extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" 
                	buttonText="添加文件" formData="bid" 
                	uploader="commonController.do?saveUploadFiles&businessType=depart">
                </t:upload>
              </div>
            </div>
        </div>
        <input type="hidden" id="orgCode" name="orgCode" value="${depart.orgCode }">
        <input type="hidden" id="validFlag" name="validFlag" value="1">
	</fieldset>
</t:formvalid>
</body>
</html>
