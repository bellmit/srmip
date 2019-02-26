package org.jeecgframework.web.system.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.PinyinUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.deptchange.TBDeptChangeConfirmEntity;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	@Override
    public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	@Override
    public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	@Override
    public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	@Override
    public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
	
	@Override
    public String getUsernamePinyin(String userName) {
        String pinyin = "";
        if (StringUtils.isNotEmpty(userName)) {
            String[] pinyins = PinyinUtil.stringToPinyin(userName);
            StringBuffer sb = new StringBuffer();
            for (String str : pinyins) {
                sb.append(str);
            }
            pinyin = sb.toString();
        }
        return pinyin;
    }
    
	@Override
    public String encrypt(String username,String password) {
	    String enPassword = PasswordUtil.encrypt(password, username, PasswordUtil.getStaticSalt());
        return enPassword;
    }

    @Override
    public void saveUserInfo(TSUser user, String odepartId, String departId) {
        this.commonDao.updateEntitie(user);
        if (StringUtils.isNotEmpty(departId)) {
            TBDeptChangeConfirmEntity confirm = new TBDeptChangeConfirmEntity();
            confirm.setUserId(user.getId());
            confirm.setUserName(user.getRealName());
            TSDepart odept = this.commonDao.get(TSDepart.class, odepartId);
            confirm.setDeptOId(odepartId);
            confirm.setDeptOName(odept.getDepartname());
            TSDepart ndept = this.commonDao.get(TSDepart.class, departId);
            confirm.setDeptNId(departId);
            confirm.setDeptNName(ndept.getDepartname());
            confirm.setConfirmStatus("0");//待确认
            confirm.setCreateDate(new Date());
            this.commonDao.save(confirm);
        }
    }
}
