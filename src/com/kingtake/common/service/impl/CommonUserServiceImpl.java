package com.kingtake.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSUserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.dao.CommonUserDao;
import com.kingtake.common.service.CommonUserServiceI;

@Service("commonUserService")
@Transactional
public class CommonUserServiceImpl extends CommonServiceImpl implements CommonUserServiceI {

    @Autowired
    private CommonUserDao commonUserDao;
    /**
     * 保存常用联系人
     */
    @Override
    public void saveUserContact(String userId, String contactIds, String departIds) {
        if (StringUtils.isNotEmpty(contactIds)) {
            String[] ids = contactIds.split(",");
            String[] deptids = departIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                CriteriaQuery cq = new CriteriaQuery(TSUserContact.class);
                cq.eq("userId", userId);
                cq.eq("contactId", ids[i]);
                cq.eq("departId", deptids[i]);
                cq.add();
                List<TSUserContact> contactList = this.commonDao.getListByCriteriaQuery(cq, false);
                if (contactList.size() > 0) {//如果已存在，则更新联系次数
                    TSUserContact contact = contactList.get(0);
                    int count = contact.getCount();
                    count++;
                    contact.setCount(count);
                } else {//如果不存在，则新增
                    TSUserContact contact = new TSUserContact();
                    contact.setUserId(userId);
                    contact.setContactId(ids[i]);
                    contact.setDepartId(deptids[i]);
                    contact.setCount(1);
                    this.commonDao.save(contact);
                }
            }
        }
    }

    /**
     * 清理该用户的常用联系人数据
     * 
     * @param userId
     */
    @Override
    public void clearUserContact(String userId) {
        String sql = "delete from t_s_user_contact t where t.contact_id=?";
        this.commonDao.executeSql(sql, userId);
    }


    @Override
    public List<Map<String, Object>> getUserList(Map<String, Object> param, int start, int end) {
        return commonUserDao.getUserList(param, start, end);
    }

    @Override
    public Integer getUserCount(Map<String, Object> param) {
        return commonUserDao.getUserCount(param);
    }

}