package com.kingtake.base.service.impl.deptchange;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.deptchange.TBDeptChangeConfirmEntity;
import com.kingtake.base.service.deptchange.TBDeptChangeConfirmServiceI;
import com.kingtake.common.service.CommonMessageServiceI;

@Service("tBDeptChangeConfirmService")
@Transactional
public class TBDeptChangeConfirmServiceImpl extends CommonServiceImpl implements TBDeptChangeConfirmServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;

    @Override
    public boolean doAddSql(TBDeptChangeConfirmEntity t) {
        return false;
    }

    @Override
    public boolean doUpdateSql(TBDeptChangeConfirmEntity t) {
        return false;
    }

    @Override
    public boolean doDelSql(TBDeptChangeConfirmEntity t) {
        return false;
    }

    /**
     * 通过
     */
    @Override
    public void doPass(TBDeptChangeConfirmEntity deptChangeConfirm) {
        deptChangeConfirm = commonDao.getEntity(TBDeptChangeConfirmEntity.class, deptChangeConfirm.getId());
        deptChangeConfirm.setConfirmStatus("1");//auditStatus 为1 通过
        this.commonDao.updateEntitie(deptChangeConfirm);
        String userId = deptChangeConfirm.getUserId();
        String oDeptId = deptChangeConfirm.getDeptOId();
        String nDeptId = deptChangeConfirm.getDeptNId();
        TSUser user = this.commonDao.get(TSUser.class, userId);
        String sql = "update t_s_user_org t set t.org_id=? where t.user_id=? and t.org_id=?";
        this.commonDao.executeSql(sql, new Object[] { nDeptId, userId, oDeptId });
        String messageTitle = "";
        String messageType = "部门变动确认";
        String messageContent = "您提交的部门变动申请已确认通过！";
        commonMessageService.sendMessage(user.getId(), messageType, messageTitle, messageContent);
    }

    /**
     * 驳回
     */
    @Override
    public void doReject(TBDeptChangeConfirmEntity deptChangeConfirm) {
        TBDeptChangeConfirmEntity apply = commonDao.get(TBDeptChangeConfirmEntity.class, deptChangeConfirm.getId());
        apply.setConfirmStatus("2");//驳回
        apply.setMsgText(deptChangeConfirm.getMsgText());
        commonDao.updateEntitie(apply);
        TSUser user = this.commonDao.get(TSUser.class, apply.getUserId());
        String messageTitle = "";
        String messageType = "部门变动确认";
        String messageContent = "您提交的部门变动申请被驳回，意见：" + apply.getMsgText();
        commonMessageService.sendMessage(user.getId(), messageType, messageTitle, messageContent);
    }

}