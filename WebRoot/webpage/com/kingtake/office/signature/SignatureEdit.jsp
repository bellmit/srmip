<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="DBstep.iFileUpLoad2000.*" %>
<%@ page import="org.jeecgframework.core.util.iDBManager2000" %>
<%@ page import="oracle.sql.BLOB" %>
<%@ page import="oracle.jdbc.*" %>
<html>
<%!
boolean mResult;
int i=0;
DBstep.iFileUpLoad2000 FileObj;
iDBManager2000 DbaObj;
String mUserName;
String mSignatureID;
int mMarkSize;
String mMarkType;
String mMarkPath;
byte[] mMarkBody;


private void PutAtBlob(BLOB vField,int vSize) throws IOException
{
  try
  {
    OutputStream outstream=vField.getBinaryOutputStream();
    outstream.write(mMarkBody,0, vSize);
    outstream.close();
  }
  catch(SQLException e)
  {

  }
}
%>
<head>
<title>印签管理</title>
<link rel='stylesheet' type='text/css' href='signature.css'>
</head>
<body bgcolor="#ffffff">
<div align="center"><font size=4 color=ff0000>印签管理</font></div>
<hr size=1>
<br>
<%
FileObj = new DBstep.iFileUpLoad2000(request);
DbaObj=new iDBManager2000();

String mSignatureID=FileObj.Request("SignatureID");
String mUserName=FileObj.Request("UserName");
String mPassword=FileObj.Request("Password");
String mMarkName=FileObj.Request("MarkName");
if (FileObj.FileName("MarkFile").equalsIgnoreCase("")) {
  mMarkSize=0;
}
else {
  mMarkSize=FileObj.FileSize("MarkFile");
  mMarkType=FileObj.ExtName("MarkFile");
  mMarkBody=FileObj.FileBody("MarkFile");
}

if (DbaObj.OpenConnection())
{
   String mSql="select MarkName from Signature where  id <>'" + mSignatureID + "' and MarkName='" + mMarkName + "'";
   try
   {
     ResultSet result=DbaObj.ExecuteQuery(mSql) ;
     if (result.next())
     {
       out.write("保存失败，数据库中已存在相同的印签〖" + mMarkName + "〗<input type='button' value='返 回' onclick='javascript:history.back();'");
       mResult=false;
     }
     else
     {
       mSql="Update Signature Set UserName=?,Password=?,MarkName=?,MarkSize=?,MarkType=?,MarkBody=EMPTY_BLOB(),markDate=sysdate  Where id='"+mSignatureID+"'";
       mResult=true;
     }
     result.close();
   }
   catch(Exception e)
   {
     e.printStackTrace();
   }

   if (mResult)
   {
     java.sql.PreparedStatement prestmt=null;
     try
     {
       DbaObj.Conn.setAutoCommit(false) ;
       prestmt =DbaObj.Conn.prepareStatement(mSql);
       prestmt.setString(1, mUserName);
       prestmt.setString(2, mPassword);
       prestmt.setString(3, mMarkName);
       prestmt.setInt(4, mMarkSize);
       prestmt.setString (5 ,mMarkType);
       prestmt.execute();
       prestmt.close();


       Statement stmt=null;
       stmt = DbaObj.Conn.createStatement();
       OracleResultSet update=(OracleResultSet)stmt.executeQuery("select * from Signature where id='" + mSignatureID+"'");
       if (update.next()){
         mMarkSize=mMarkBody.length;
         PutAtBlob(((oracle.jdbc.OracleResultSet)update).getBLOB("MarkBody"),mMarkSize);
       }
       update.close();
       stmt.close();
       DbaObj.Conn.commit();
       mMarkBody=null;
       mResult=true;
     }
     catch(SQLException e)
     {
       e.printStackTrace();
       mResult=false;
     }
  }
  DbaObj.CloseConnection() ;
}

if (mResult)
{
  response.sendRedirect("SignatureList.jsp");
}
%>
</body>
</html>