package test;

import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;

public class RelativeUser {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ActivitiService activitiService;
    
    
    //加密密码
    private static String encrypt(String username,String password) {
        String enPassword = PasswordUtil.encrypt(password, username, PasswordUtil.getStaticSalt());
        return enPassword;
    }
    /**
     * 解密密文字符串
     * 
     * @param ciphertext
     *            待解密的密文字符串
     * @param username
     *            生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
     * @param salt
     *            盐值(如需解密,该参数需要与加密时使用的一致)
     * @return 解密后的明文字符串
     * @throws Exception
     */
    private static String decrypt(String ciphertext, String username) {
        String enPassword = PasswordUtil.decrypt(ciphertext, username, PasswordUtil.getStaticSalt());
        return enPassword;
    }
    
    public static void main(String[] args) {
        //测试解密
        String dePassword = "";
        String username = "0909071030";
        String ciphertext = "86e61c4628f45fff";
        String password = "123456";
        dePassword = decrypt(ciphertext, username);
//        dePassword = encrypt(username, password);
        System.out.println(dePassword);
        
    }
    
}
