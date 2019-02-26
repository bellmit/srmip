package com.kingtake.common.service;

import java.util.List;
import java.util.Map;

public interface CommonUserServiceI {

    /**
     * 保存常用联系人
     */
    public void saveUserContact(String userId, String contactIds, String departIds);

    /**
     * 清理常用联系人
     * 
     * @param userId
     */
    public void clearUserContact(String userId);

    /**
     * 查询用户列表
     * 
     * @param param
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> getUserList(Map<String, Object> param, int start, int end);

    /**
     * 查询记录数目
     * 
     * @param param
     * @return
     */
    public Integer getUserCount(Map<String, Object> param);

}
