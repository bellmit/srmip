<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*,java.text.*,java.util.*,java.sql.*,org.jeecgframework.core.util.iDBManager2000" %>
<html>
<head>
<title>印签管理</title>
<link rel='stylesheet' type='text/css' href='signature.css'>
<script language="javascript">
function ConfirmDel(FileUrl){
	if (confirm('是否确定删除该印签！')){
		location.href=FileUrl;
	}
}

function DoMenu(MenuValue){
    location.href=MenuValue;
    return true;
}
</script>
</head>
<body bgcolor="#ffffff">
<div align="center"><font size=4 color=ff0000>印签管理</font></div>
<hr size=1>
<br>
<table border=0  cellspacing='0' cellpadding='0' width=100% align=center class=TBStyle>
	<tr>
		<td colspan=2 class="TDTitleStyle" nowrap>
		  <input type=button name="Add" value="增加印签"  onclick="javascript:location.href='SignatureAddFrm.jsp'">
		</td>
		<td colspan=3 class="TDTitleStyle">&nbsp;</td>
	</tr>
	<tr>
		<!-- <td nowrap align=center class="TDTitleStyle" height="26">编号</td> -->
		<td nowrap align=center class="TDTitleStyle" height="30">用户名称</td>
		<td nowrap align=center class="TDTitleStyle" height="30">印签名称</td>
		<td nowrap align=center class="TDTitleStyle" height="30">印签类型</td>
		<td nowrap align=center class="TDTitleStyle" height="30">操作</td>
	</tr>
	<%
	iDBManager2000 DbaObj=new iDBManager2000();
           if (DbaObj.OpenConnection())
           {
             try
             {
               ResultSet result=DbaObj.ExecuteQuery("select id,UserName,MarkName,MarkType from Signature order by MARKDATE desc") ;
               while ( result.next() )
               {
                String mSignatureID=String.valueOf(result.getString("id"));
        %>
	<tr>
		<%-- <td class="TDStyle"><%=mSignatureID%>&nbsp;</td> --%>
		<td class="TDStyle" height="30"><%=result.getString("UserName")%>&nbsp;</td>
		<td class="TDStyle" height="30"><%=result.getString("MarkName")%>&nbsp;</td>
		<td class="TDStyle" height="30"><%=result.getString("MarkType")%>&nbsp;</td>
		<td class="TDStyle" width=148 nowrap height="30">
			<input type=button onclick="javascript:location.href='SignatureEditFrm.jsp?SignatureID=<%=mSignatureID%>';" name="Edit" value=" 修 改 ">
			<input type=button onclick="javascript:ConfirmDel('SignatureDel.jsp?SignatureID=<%=mSignatureID%>');" name="Del" value=" 删 除 ">
		</td>
	</tr>
	<%
              }
          result.close() ;
             }
             catch(Exception e)
             {
               System.out.println(e.toString());
             }
             DbaObj.CloseConnection() ;
           }
           else
           {
             out.println("OpenDatabase Error") ;
           }

        %>
</table>
</body>
</html>
