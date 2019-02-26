package org.jeecgframework.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class iDBManager2000 {

  public String ClassString=null;
  public String ConnectionString=null;
  public String UserName=null;
  public String PassWord=null;

  public Connection Conn;
  public Statement Stmt;


  public iDBManager2000() {


    //For ODBC
    //ClassString="sun.jdbc.odbc.JdbcOdbcDriver";
    //ConnectionString=("jdbc:odbc:DBDemo");
    //UserName="dbdemo";
    //PassWord="dbdemo";


    //For Access Driver
    //ClassString="sun.jdbc.odbc.JdbcOdbcDriver";
    //ConnectionString=("jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=C:\\DBstep.mdb;ImplicitCommitSync=Yes;MaxBufferSize=512;MaxScanRows=128;PageTimeout=5;SafeTransactions=0;Threads=3;UserCommitSync=Yes;").replace('\\','/');

    //For SQLServer Driver
    //ClassString="com.microsoft.jdbc.sqlserver.SQLServerDriver";
    //ConnectionString="jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=DBSignature";
    //UserName="sa";
    //PassWord="";

    //For Oracle Driver
        if (StringUtils.isEmpty(ClassString)) {
            Properties props = new Properties();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties");
            try {
                props.load(inputStream);
                ClassString = props.getProperty("jdbc.driver");
                ConnectionString = props.getProperty("jdbc.url.jeecg");
                UserName = props.getProperty("jdbc.username.jeecg");
                PassWord = props.getProperty("jdbc.password.jeecg");
            } catch (IOException e) {
                throw new BusinessException("获取数据库连接信息出错！", e);
            }
        }

    //For MySQL Driver
    //ClassString="org.gjt.mm.mysql.Driver";
    //ConnectionString="jdbc:mysql://localhost/softforum?user=...&password=...&useUnicode=true&characterEncoding=8859_1";
    //ClassString="com.mysql.jdbc.Driver";
    //ConnectionString="jdbc:mysql://localhost/dbstep?user=root&password=&useUnicode=true&characterEncoding=gb2312";

    //For Sybase Driver
    //ClassString="com.sybase.jdbc.SybDriver";
    //ConnectionString="jdbc:sybase:Tds:localhost:5007/tsdata"; //tsdataΪ�����ݿ���
    //Properties sysProps = System.getProperties();
    //SysProps.put("user","userid");
    //SysProps.put("password","user_password");
    //If using Sybase then DriverManager.getConnection(ConnectionString,sysProps);
  }

  public boolean OpenConnection()
  {
   boolean mResult=true;
   try
   {
     Class.forName(ClassString);
     if ((UserName==null) && (PassWord==null))
     {
       Conn= DriverManager.getConnection(ConnectionString);
     }
     else
     {
       Conn= DriverManager.getConnection(ConnectionString,UserName,PassWord);
     }

     Stmt=Conn.createStatement();
     mResult=true;
   }
   catch(Exception e)
   {
     System.out.println(e.toString());
     mResult=false;
   }
   return (mResult);
  }

  //�ر���ݿ�����
  public void CloseConnection()
  {
   try
   {
     Stmt.close();
     Conn.close();
   }
   catch(Exception e)
   {
     System.out.println(e.toString());
   }
  }
  public String GetDateTime()
  {
   Calendar cal  = Calendar.getInstance();
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   String mDateTime=formatter.format(cal.getTime());
   return (mDateTime);
  }

  public  java.sql.Date  GetDate()
  {
    java.sql.Date mDate;
    Calendar cal  = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String mDateTime=formatter.format(cal.getTime());
    return (java.sql.Date.valueOf(mDateTime));
  }

  public int GetMaxID(String vTableName,String vFieldName)
  {
   int mResult=0;
   boolean mConn=true;
   String mSql=new String();
   mSql = "select max("+vFieldName+")+1 as MaxID from " + vTableName;
   try
   {
       if (Conn!=null){
           mConn=Conn.isClosed();
       }
       if (mConn){
         OpenConnection();
       }

       ResultSet result=ExecuteQuery(mSql);
       if (result.next())
       {
         mResult=result.getInt("MaxID");
       }
       result.close();

       if (mConn)
       {
         CloseConnection();
       }

     }
     catch(Exception e)
     {
       System.out.println(e.toString());
   }
   return (mResult);
 }

  public ResultSet ExecuteQuery(String SqlString)
  {
    ResultSet result=null;
    try
    {
      result=Stmt.executeQuery(SqlString);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    return (result);
  }

  public int ExecuteUpdate(String SqlString)
  {
    int result=0;
    try
    {
      result=Stmt.executeUpdate(SqlString);
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    return (result);
  }

  /**
   * ���ܻ����ã���ȡ��ǰ���������ڣ��ַ��ͣ�
   * @return ��ǰ���ڣ��ַ��ͣ�
   */
  public String GetDateAsStr()
  {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String mDateTime = formatter.format(cal.getTime());
    return (mDateTime);
  }
}