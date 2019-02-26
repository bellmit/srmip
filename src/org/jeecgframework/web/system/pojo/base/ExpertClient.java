package org.jeecgframework.web.system.pojo.base;

import java.util.Map;

import com.kingtake.expert.entity.info.TErExpertUserEntity;

/**
 * 在线用户对象
 * 
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class ExpertClient implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private TErExpertUserEntity expert;

    private Map<String, TSFunction> functions;
    /**
     * 用户IP
     */
    private java.lang.String ip;
    /**
     * 登录时间
     */
    private java.util.Date logindatetime;

    public TErExpertUserEntity getExpert() {
        return expert;
    }

    public void setExpert(TErExpertUserEntity expert) {
        this.expert = expert;
    }

    public Map<String, TSFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, TSFunction> functions) {
        this.functions = functions;
    }

    public java.lang.String getIp() {
        return ip;
    }

    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }

    public java.util.Date getLogindatetime() {
        return logindatetime;
    }

    public void setLogindatetime(java.util.Date logindatetime) {
        this.logindatetime = logindatetime;
    }

}
