package com.kingtake.project.service.impl.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.dao.TPmTaskDao;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceContext;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.task.TPmTaskServiceI;

@Service("tPmTaskService")
@Transactional
public class TPmTaskServiceImpl extends CommonServiceImpl implements TPmTaskServiceI, ProjectListServiceI, ApprFlowServiceI {

    @Autowired
    private ProjectListServiceContext projectListServiceContext;
    
    @Autowired
    private TPmTaskDao tPmTaskDao;

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmTaskEntity) entity);
    }

    @Override
    public void addMain(TPmTaskEntity tPmTask, List<TPmTaskNodeEntity> tPmTaskNodeList) {
        //保存主信息
        this.save(tPmTask);

        /** 保存-任务节点管理 */
        for (TPmTaskNodeEntity tPmTaskNode : tPmTaskNodeList) {
            //外键设置
            tPmTaskNode.setTpId(tPmTask.getId());
            if (StringUtil.isEmpty(tPmTaskNode.getPlanCheckFlag())) {
                tPmTaskNode.setPlanCheckFlag("N");
            }
            if (StringUtil.isEmpty(tPmTaskNode.getQualityCheckFlag())) {
                tPmTaskNode.setQualityCheckFlag("N");
            }
            this.save(tPmTaskNode);
        }
        //执行新增操作配置的sql增强
        this.doAddSql(tPmTask);
    }

    @Override
    public void updateMain(TPmTaskEntity tPmTask, List<TPmTaskNodeEntity> tPmTaskNodeList) {
        //保存主表信息
        this.saveOrUpdate(tPmTask);
        //===================================================================================
        //获取参数
        Object id0 = tPmTask.getId();
        //===================================================================================
        //1.查询出数据库的明细数据-任务节点管理
        String hql0 = "from TPmTaskNodeEntity where 1 = 1 AND t_P_ID = ? ";
        List<TPmTaskNodeEntity> tPmTaskNodeOldList = this.findHql(hql0, id0);
        //2.筛选更新明细数据-任务节点管理
        for (TPmTaskNodeEntity oldE : tPmTaskNodeOldList) {
            boolean isUpdate = false;
            for (TPmTaskNodeEntity sendE : tPmTaskNodeList) {
                //需要更新的明细数据-任务节点管理
                if (oldE.getId().equals(sendE.getId())) {
                    try {
                        Date curTime = DateUtils.getDate();
                        TSBaseUser curUser = ResourceUtil.getSessionUserName();

                        if (StringUtil.isEmpty(sendE.getPlanCheckFlag())) {
                            sendE.setPlanCheckFlag("N");
                            sendE.setPlanCheckTie(null);
                            sendE.setPlanCheckUserid(null);
                            sendE.setPlanCheckUsername(null);
                        } else {
                            sendE.setPlanCheckTie(curTime);
                            sendE.setPlanCheckUserid(curUser.getId());
                            sendE.setPlanCheckUsername(curUser.getRealName());
                        }
                        if (StringUtil.isEmpty(sendE.getQualityCheckFlag())) {
                            sendE.setQualityCheckFlag("N");
                            sendE.setQualityCheckTime(null);
                            sendE.setQualityCheckUserid(null);
                            sendE.setQualityCheckUsername(null);
                        } else {
                            sendE.setQualityCheckTime(curTime);
                            sendE.setQualityCheckUserid(curUser.getId());
                            sendE.setQualityCheckUsername(curUser.getRealName());
                        }
                        MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
                        this.saveOrUpdate(oldE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException(e.getMessage());
                    }
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                //如果数据库存在的明细，前台没有传递过来则是删除-任务节点管理
                super.delete(oldE);
            }

        }
        //3.持久化新增的数据-任务节点管理
        for (TPmTaskNodeEntity tPmTaskNode : tPmTaskNodeList) {
            if (oConvertUtils.isEmpty(tPmTaskNode.getId())) {
                //外键设置
                tPmTaskNode.setTpId(tPmTask.getId());
                this.save(tPmTaskNode);
            }
        }
        //执行更新操作配置的sql增强
        this.doUpdateSql(tPmTask);
    }

    @Override
    public void delMain(TPmTaskEntity tPmTask) {
        //获取参数
        Object id0 = tPmTask.getId();
        //===================================================================================
        //删除-任务节点管理
        String hql0 = "from TPmTaskNodeEntity where 1 = 1 AND t_P_ID = ? ";
        List<TPmTaskNodeEntity> tPmTaskNodeOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(tPmTaskNodeOldList);
        //===================================================================================
        
        //删除主表信息及附件
        this.delAttachementByBid(tPmTask.getId());
        this.delete(tPmTask);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmTaskEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmTaskEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmTaskEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmTaskEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
        sql = sql.replace("#{task_title}", String.valueOf(t.getTaskTitle()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 查询项目列表
     */
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        String projectNo = request.getParameter("projectNo");
        String projectName = request.getParameter("projectName");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String planCheckStatus = request.getParameter("planCheckStatus");
        String qualityCheckStatus = request.getParameter("qualityCheckStatus");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int curPage = Integer.valueOf(page);
        int pageSize = Integer.valueOf(rows);
        int start = (curPage - 1) * pageSize;
        int end = curPage * pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(projectNo)) {
            paramMap.put("projectNo", projectNo);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            paramMap.put("projectName", projectName);
        }
        if (StringUtils.isNotEmpty(beginDate_begin)) {
            paramMap.put("beginDate_begin", beginDate_begin);
        }
        if (StringUtils.isNotEmpty(beginDate_end)) {
            paramMap.put("beginDate_end", beginDate_end);
        }
        if (StringUtils.isNotEmpty(planCheckStatus)) {
            paramMap.put("planCheckStatus", planCheckStatus);
        }
        if (StringUtils.isNotEmpty(qualityCheckStatus)) {
            paramMap.put("qualityCheckStatus", qualityCheckStatus);
        }
        List<Map<String, Object>> list = this.tPmTaskDao.getProjectList(start, end, paramMap);
        int count = this.tPmTaskDao.getProjectCount(paramMap);
        dataGrid.setTotal(count);
        dataGrid.setResults(list);
    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_TASK);
    }

    

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmTaskEntity t = commonDao.get(TPmTaskEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserId(user.getId());
                t.setFinishUserName(user.getRealName());
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void doBack(String id) {
        TPmTaskEntity t = commonDao.get(TPmTaskEntity.class, id);
        //将审批状态修改为被驳回
        t.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TPmTaskEntity entity = this.get(TPmTaskEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
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