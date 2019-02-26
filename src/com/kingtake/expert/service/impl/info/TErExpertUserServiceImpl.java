package com.kingtake.expert.service.impl.info;

import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.expert.entity.info.TErExpertUserEntity;
import com.kingtake.expert.service.info.TErExpertUserServiceI;

@Service("tErExpertUserService")
@Transactional
public class TErExpertUserServiceImpl extends CommonServiceImpl implements TErExpertUserServiceI {
    
    @Override
    public TErExpertUserEntity checkUserExits(TErExpertUserEntity user) {
        CriteriaQuery cq = new CriteriaQuery(TErExpertUserEntity.class);
        cq.eq("userName", user.getUserName());
        cq.eq("userPwd", PasswordUtil.encrypt(user.getUserPwd(), user.getUserName(), PasswordUtil.getStaticSalt()));
        cq.eq("expertValidFlag", SrmipConstants.YES);
        cq.add();
        List<TErExpertUserEntity> expertUserList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (expertUserList.size() > 0) {
            return expertUserList.get(0);
        }
        return null;
    }
 	
}