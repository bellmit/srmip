package org.jeecgframework.core.pool;

import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.PoolableObjectFactory;
import org.jeecgframework.core.util.FtpClientUtil;

/**
 * FTPClient工厂类，通过FTPClient工厂提供FTPClient实例的创建和销毁
 *
 * @author heaven
 */
public class FtpClientFactory {


    //给工厂传入一个参数对象，方便配置FTPClient的相关参数
    public FTPClient makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
        int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
        String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
        String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
        try {
            ftpClient.connect(ip, port);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return null;
            }
            boolean result = ftpClient.login(username, password);
            if (!result) {
                throw new RuntimeException("ftpClient登陆失败! userName:" + username + " ; password:" + password);
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setBufferSize(1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    public void destroyObject(FTPClient ftpClient) throws Exception {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            // 注意,一定要在finally代码中断开连接，否则会导致占用ftp连接情况
            try {
                ftpClient.disconnect();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }


    public boolean validateObject(FTPClient ftpClient) {
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void activateObject(FTPClient ftpClient) throws Exception {
    }

    public void passivateObject(FTPClient ftpClient) throws Exception {

    }
}
