<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil" %>
<% 
//取出配置文件中类的信息
String ProductCaption = null;
ProductCaption = ResourceUtil.getConfigByName("NTKO.ProductCaption");
// ProductCaption = new String(ProductCaption.getBytes("iso-8859-1"),"gb2312");//编码转换
String ProductKey = null;
ProductKey = ResourceUtil.getConfigByName("NTKO.ProductKey");
String ClsID = null;
ClsID = ResourceUtil.getConfigByName("NTKO.ClsID");
String VerSion = null;
VerSion = ResourceUtil.getConfigByName("NTKO.VerSion");

%>
<script type="text/javascript">
//加载pdf插件
function addPDFPlugin(){
    if(OFFICE_CONTROL_OBJ==undefined){
        OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
    }
    OFFICE_CONTROL_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.0.0","<%=basePath%>/OfficeControl/ntkooledocall.cab",51,true);	
}
</script>

