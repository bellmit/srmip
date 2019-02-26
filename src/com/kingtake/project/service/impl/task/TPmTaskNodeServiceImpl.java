package com.kingtake.project.service.impl.task;

import java.util.Date;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;

@Service("tPmTaskNodeService")
@Transactional
public class TPmTaskNodeServiceImpl extends CommonServiceImpl implements ApprFlowServiceI {

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmTaskNodeEntity t = commonDao.get(TPmTaskNodeEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void doBack(String id) {
        TPmTaskNodeEntity t = commonDao.get(TPmTaskNodeEntity.class, id);
        //将审批状态修改为被驳回
        t.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TPmTaskNodeEntity entity = this.get(TPmTaskNodeEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getOperationStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getOperationStatus())) {
              entity.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmTaskEntity t = commonDao.get(TPmTaskEntity.class, id);
        if(t!=null){
            appName = t.getTaskTitle();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmTaskEntity entity = this.get(TPmTaskEntity.class, id);
        if (entity != null) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

}