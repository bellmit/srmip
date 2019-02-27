<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
a{
	color:green;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<c:choose>
<c:when test="${flist[index].isconvert eq 1}">
<t:tab title="<label title='${flist[index].attachmenttitle}'>${fn:length(flist[index].attachmenttitle)>15?fn:substring(flist[index].attachmenttitle,0,15):flist[index].attachmenttitle}${fn:length(flist[index].attachmenttitle)>15?'...':''}</label>&nbsp;&nbsp;&nbsp;<a href='commonController.do?viewFile&fileid=${flist[index].id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity' title='下载' style='color:green;'>[下载]</a>" iframe="${rootPath}/${flist[index].ckdz}" id="${flist[index].id}"></t:tab>
</c:when>
<c:otherwise>
<t:tab title="<label title='${flist[index].attachmenttitle}'>${fn:length(flist[index].attachmenttitle)>15?fn:substring(flist[index].attachmenttitle,0,15):flist[index].attachmenttitle}${fn:length(flist[index].attachmenttitle)>15?'...':''}</label>&nbsp;&nbsp;&nbsp;<a href='commonController.do?viewFile&fileid=${flist[index].id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity' title='下载' style='color:green;'>[下载]</a>" iframe="commonController.do?noPreview&isconvert=${flist[index].isconvert}" id="${flist[index].id}"></t:tab>
</c:otherwise>
</c:choose>
<c:forEach items="${flist}" var="file" varStatus="s">
<c:if test="${s.index ne index}">
<c:choose>
<c:when test="${file.isconvert eq 1}">
<t:tab title="<label title='${file.attachmenttitle}'>${fn:length(file.attachmenttitle)>15?fn:substring(file.attachmenttitle,0,15):file.attachmenttitle}${fn:length(file.attachmenttitle)>15?'...':''}</label>&nbsp;&nbsp;&nbsp;<a href='commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity' title='下载' style='color:green;'>[下载]</a>" iframe="${rootPath}/${file.ckdz}" id="${file.id}"></t:tab>
</c:when>
<c:otherwise>
<t:tab title="<label title='${file.attachmenttitle}'>${fn:length(file.attachmenttitle)>15?fn:substring(file.attachmenttitle,0,15):file.attachmenttitle}${fn:length(file.attachmenttitle)>15?'...':''}</label>&nbsp;&nbsp;&nbsp;<a href='commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity' title='下载' style='color:green;'>[下载]</a>" iframe="commonController.do?noPreview&isconvert=${file.isconvert}" id="${file.id}"></t:tab>
</c:otherwise>
</c:choose>
</c:if>
</c:forEach>
</t:tabs>
<script type="text/javascript">
// $(document).ready(function(){
// 	$('#tt').tabs('select',${index});
//   });
</script>