package com.kingtake.project.service.impl.contractnodecheck;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.contractnodecheck.TPmContractNodeCheckServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("projectNodeCheckService")
@Transactional
public class ProjectNodeCheckServiceImpl extends CommonServiceImpl implements ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmContractNodeCheckServiceI tPmContractNodeCheckService;

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        Map<String, Object> params = new HashMap<String, Object>();
        TSUser user = ResourceUtil.getSessionUserName();
        params.put("operateStatus", "0");
        params.put("userId", user.getId());
        //params.put("departId", user.getCurrentDepart().getId());
        Integer count = tPmContractNodeCheckService.getProjectNodeCheckCount(params);
        return count;
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmContractNodeCheckEntity t = commonDao.get(TPmContractNodeCheckEntity.class, id);
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
        TPmContractNodeCheckEntity t = commonDao.get(TPmContractNodeCheckEntity.class, id);
        //将审批状态修改为被驳回
        t.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TPmContractNodeCheckEntity entity = this.get(TPmContractNodeCheckEntity.class, id);
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
        TPmContractNodeCheckEntity t = commonDao.get(TPmContractNodeCheckEntity.class, id);
        if(t!=null){
            appName = t.getContractName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmContractNodeCheckEntity entity = this.get(TPmContractNodeCheckEntity.class, id);
        if (entity != null) {
              entity.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

}