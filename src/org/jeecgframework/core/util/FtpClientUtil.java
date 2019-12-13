package org.jeecgframework.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.pool.FTPClientPool;
import org.jeecgframework.core.pool.FtpClientFactory;

/**
 * FTP客户端工具类
 * 
 * @author admin
 * 
 */
public class FtpClientUtil {

    private static ResourceBundle bundle = java.util.ResourceBundle.getBundle("ftp");

    private static  FTPClientPool pool = null;
    static{
       try {
        pool = new FTPClientPool(new FtpClientFactory());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    public static void main(String[] args) {
        try {
            String ip = (String) getFtpProps("FTP.SERVER.IP");
            int port = Integer.valueOf(getFtpProps("FTP.SERVER.PORT"));
            String username = (String) getFtpProps("FTP.SERVER.USERNAME");
            String password = (String) getFtpProps("FTP.SERVER.PASSWORD");
            //读入文件    
            FileInputStream fis = new FileInputStream("E:/工具/【研发二科】checkstyle代码检查工具idea集成.docx");
//            传送文件到FTP服务器  
            FtpClientUtil.sendFile("E:/", fis);
            //deleteFile(ip, port, username, password, "hahaha.txt");
            //从FTP服务器取得文件    
            //FileOutputStream fos = new FileOutputStream("localfile");
            //FtpClientUtil.retrieveFile( "win32_11gR2_database_1of2.zip", fos);
            System.out.println("上传文件成功......................");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * 
     * @param host
     * @param port
     * @param user
     * @param password
     * @param remoteFilename
     * @param is
     * @throws Exception
     */
    public static void sendFile(String remoteFilename, InputStream is) throws Exception {
        FTPClient ftpclient = null;
        try {
            ftpclient = pool.borrowObject();//从连接池获取
            int idx = remoteFilename.lastIndexOf("/");
            String dir = remoteFilename.substring(0, idx);
            boolean flag = ftpclient.changeWorkingDirectory(dir);
            if (!flag) {
                ftpclient.makeDirectory(dir);
                ftpclient.changeToParentDirectory();
            } else {
                ftpclient.changeWorkingDirectory("/");
            }
            ftpclient.enterLocalPassiveMode();
            //传送文件    
            boolean success = ftpclient.storeFile(remoteFilename, is);
            if (!success) {
                throw new Exception("FTP上传失败");
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        } finally {
            try {
                pool.returnObject(ftpclient); //放回连接池
            } catch (IOException e) {
            }
        }
    }

    /**
     * 文件下载
     * 
     * @param host
     * @param port
     * @param user
     * @param password
     * @param remoteFilename
     * @param os
     * @throws Exception
     */
    public static void retrieveFile(String remoteFilename,
            OutputStream os) throws Exception {
        FTPClient ftpclient = null;
        try {
            ftpclient = pool.borrowObject();
            // 取得文件    
            boolean success = ftpclient.retrieveFile(remoteFilename, os);
            if (!success) {
                throw new Exception("FTP下载失败");
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        } finally {
            try {
                pool.returnObject(ftpclient); //放回连接池
            } catch (IOException e) {
            }
        }
    }

    /**
     * 文件下载
     * 
     * @param host
     * @param port
     * @param user
     * @param password
     * @param remoteFilename
     * @param os
     * @throws Exception
     */
    public static InputStream retrieveInputStream(String host, int port, String user, String password,
            String remoteFilename)
            throws Exception {
        FTPClient ftpclient = new FTPClient();
        InputStream is = null;
        try {
            //设置服务器名和端口    
            ftpclient.connect(host, port);
            int reply = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                //连接错误    
                Exception ee = new Exception("连接失败 :" + host);
                throw ee;
            }

            //登录    
            if (ftpclient.login(user, password) == false) {
                // invalid user/password    
                Exception ee = new Exception("用户名/密码不正确");
                throw ee;
            }

            //设置传送模式   
            ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

            // 取得文件    
            is = ftpclient.retrieveFileStream(remoteFilename);

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                ftpclient.disconnect(); //解除连接   
            } catch (IOException e) {
            }
        }
        return is;
    }

    /**
     * 文件删除
     * 
     * @param host
     * @param port
     * @param user
     * @param password
     * @param remoteFilename
     * @throws Exception
     */
    public static void deleteFile(String remoteFilename)
            throws Exception {
        FTPClient ftpclient = new FTPClient();
        try {
            ftpclient = pool.borrowObject();
            //删除文件    
            ftpclient.deleteFile(remoteFilename);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                pool.returnObject(ftpclient); //放回连接池   
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获取FTP配置参数
     * 
     * @param key
     * @return
     */
    public static String getFtpProps(String key) {
        String value = bundle.getString(key);
        return value;
    }

}
