package com.kingtake.office.service.impl.seal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.office.entity.seal.TOSealUseEntity;
import com.kingtake.office.service.seal.TOSealUseServiceI;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;

@Service("tOSealUseService")
@Transactional
public class TOSealUseServiceImpl extends CommonServiceImpl implements TOSealUseServiceI, ApprFlowServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TOSealUseEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        // 执行新增操作配置的sql增强
        this.doAddSql((TOSealUseEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        // 执行更新操作配置的sql增强
        this.doUpdateSql((TOSealUseEntity) entity);
    }

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOSealUseEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOSealUseEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOSealUseEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOSealUseEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{number_word}", String.valueOf(t.getNumberWord()));
        sql = sql.replace("#{number_symbol}", String.valueOf(t.getNumberSymbol()));
        sql = sql.replace("#{material_name}", String.valueOf(t.getMaterialName()));
        sql = sql.replace("#{pages_num}", String.valueOf(t.getPagesNum()));
        sql = sql.replace("#{copies_num}", String.valueOf(t.getCopiesNum()));
        sql = sql.replace("#{secret_degree}", String.valueOf(t.getSecretDegree()));
        sql = sql.replace("#{seal_type}", String.valueOf(t.getSealType()));
        sql = sql.replace("#{operator_departid}", String.valueOf(t.getOperatorDepartid()));
        sql = sql.replace("#{operator_id}", String.valueOf(t.getOperatorId()));
        sql = sql.replace("#{operator_name}", String.valueOf(t.getOperatorName()));
        sql = sql.replace("#{main_content}", String.valueOf(t.getMainContent()));
        sql = sql.replace("#{accordings}", String.valueOf(t.getAccordings()));
        sql = sql.replace("#{apply_date}", String.valueOf(t.getApplyDate()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtils.isNotEmpty(id)) {
            TOSealUseEntity t = commonDao.get(TOSealUseEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                // 操作：完成流程
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
        TOSealUseEntity finishApply = this.get(TOSealUseEntity.class, id);
        //将审批状态修改为被驳回
        finishApply.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(finishApply);
    }

    @Override
    public String getAppName(String id) {
        TOSealUseEntity finishApply = this.get(TOSealUseEntity.class, id);
        return finishApply.getMaterialName();
    }

    @Override
    public void doPass(String id) {
        TOSealUseEntity entity = this.get(TOSealUseEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public void doCancel(String id) {
        TOSealUseEntity entity = this.get(TOSealUseEntity.class, id);
        if (entity != null) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

    @Override
    public void saveSealUse(TOSealUseEntity tOSealUse) {
        try {
            if (StringUtils.isNotEmpty(tOSealUse.getId())) {
                TOSealUseEntity t = commonDao.get(TOSealUseEntity.class, tOSealUse.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOSealUse, t);
                commonDao.saveOrUpdate(t);
            } else {
                String serialNum = getSerialNum();
                tOSealUse.setSearialNum(serialNum);
                commonDao.save(tOSealUse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * 查询流水号
     * @return
     */
    private String getSerialNum(){
        String serialNum = "";
        int year = DateUtils.getYear();
        String sql = "select t.searial_num from t_o_seal_use t where t.searial_num like '"+year+"%' order by t.create_date desc";
        List<Map<String,Object>> dataMap = this.commonDao.findForJdbc(sql);
        if(dataMap.size()>0){
            Map<String,Object> map = dataMap.get(0);
            String queryNum = (String) map.get("searial_num");
            String tmp = queryNum.replace(String.valueOf(year), "");
            int num = Integer.parseInt(tmp);
            num++;
            serialNum = year + PrimaryGenerater.getNumberStr(num, 4);
        }else{
            serialNum = year+"0001";
        }
        return serialNum;
    }
}