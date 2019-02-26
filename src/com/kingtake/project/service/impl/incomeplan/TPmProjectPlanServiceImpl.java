package com.kingtake.project.service.impl.incomeplan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.incomeplan.TPmIncomePlanServiceI;
import com.kingtake.project.service.incomeplan.TPmProjectPlanServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmProjectPlanService")
@Transactional
public class TPmProjectPlanServiceImpl extends CommonServiceImpl implements TPmProjectPlanServiceI, ProjectListServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmProjectPlanEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmProjectPlanEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmProjectPlanEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmProjectPlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmProjectPlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmProjectPlanEntity t) {
        return true;
    }
    
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }
    
    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmIncomePlanEntity.class);
        cq.eq("checkUserId", user.getId());
//        cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        cq.eq("approvalStatus", "1");//审核状态为1 已提交
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomePlanEntity> incomePlanList = this.commonDao.getListByCriteriaQuery(cq, false);
        return incomePlanList.size();
    }
}