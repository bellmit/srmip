package com.kingtake.project.service.impl.approutcomecontract;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmOutcomeContractConfirmService")
@Transactional
public class TPmOutcomeContractConfirmServiceImpl extends CommonServiceImpl implements ProjectListServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
	
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
    }

    /**
     * 获取审批数目
     */
    @Override
    public int getAuditCount(Map<String, String> param) {
        int count = 0;
        TSUser user = ResourceUtil.getSessionUserName();
        List<TPmProjectEntity> projectList = this.commonDao.findByProperty(TPmProjectEntity.class,
                "approvalUserid", user.getId());
        if (projectList.size() > 0) {
            List<String> projectIdList = new ArrayList<String>();
            for (TPmProjectEntity proj : projectList) {
                projectIdList.add(proj.getId());
            }
            CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class);
            cq.in("project.id", projectIdList.toArray());
            cq.eq("contractBookFlag", "1");//查询未确认的数据
            cq.add();
            List<TPmOutcomeContractApprEntity> apprList = this.commonDao.getListByCriteriaQuery(cq, true);
            count = apprList.size();
        }
        return count;
    }

}