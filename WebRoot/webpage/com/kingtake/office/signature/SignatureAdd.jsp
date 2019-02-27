<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ page import="org.jeecgframework.core.util.UUIDGenerator" %>
<html>
<%!
boolean mResult;
DBstep.iFileUpLoad2000 FileObj;
iDBManager2000 DbaObj;
String mUserName;
String mMarkType;
String mMarkPath;
int mMarkSize;
String iSignatureID;
private byte[] mMarkBody;
PreparedStatement prestmt;

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
     e.printStackTrace();
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

String mUserName=FileObj.Request("UserName");
String mPassword=FileObj.Request("Password");
String mMarkName=FileObj.Request("MarkName");


if (FileObj.FileName("MarkFile").equalsIgnoreCase("")) {
  mMarkSize=0;
}
else {
  mMarkSize=FileObj.FileSize("MarkFile");
  mMarkType=FileObj.ExtName("MarkFile");
  mMarkBody=FileObj.FileBody("MarkFile") ;
}

if (DbaObj.OpenConnection())
{
  String mSql="select MarkName from Signature where MarkName='" + mMarkName + "'";
  try
  {
    ResultSet result=DbaObj.ExecuteQuery(mSql) ;
    if (result.next())
    {
      out.write("保存失败，数据库中已存在相同的印签〖" + mMarkName + "〗<input type='button' value='返 回' onclick='javascript:history.back();'");
      mResult = false;
    }
    else
    {
      mSql="Insert Into Signature (id,UserName,Password,MarkName,MarkSize,MarkType,MarkBody,markDate) values (?,?,?,?,?,?,EMPTY_BLOB(),sysdate)";
      iSignatureID= UUIDGenerator.generate();
      mResult = true;
    }
    result.close();
  }
  catch(Exception e)
  {
    System.out.println(e.toString());
    mResult=false;
  }

  if (mResult)
  {
    java.sql.PreparedStatement prestmt=null;
    try
    {
      DbaObj.Conn.setAutoCommit(false) ;
      prestmt =DbaObj.Conn.prepareStatement(mSql);
      System.out.println(iSignatureID);
      prestmt.setString(1, iSignatureID);
      prestmt.setString(2, mUserName);
      prestmt.setString(3, mPassword);
      prestmt.setString(4, mMarkName);
      prestmt.setInt(5, mMarkSize);
      prestmt.setString (6 ,mMarkType);
      prestmt.execute();
      prestmt.close();

      Statement stmt=null;
      stmt = DbaObj.Conn.createStatement();
      OracleResultSet update=(OracleResultSet)stmt.executeQuery("select * from Signature where id='" + iSignatureID+"'");
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
      System.out.println(e.getMessage());
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