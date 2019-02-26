package com.kingtake.project.service.impl.appraisal;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.appraisal.TBAppraisalNoFinishEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.service.appraisal.TBAppraisalNoFinishServiceI;

@Service("tBAppraisaNoFinishService")
@Transactional
public class TBAppraisalNoFinishServiceImpl extends CommonServiceImpl implements TBAppraisalNoFinishServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAppraisalNoFinishEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAppraisalNoFinishEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAppraisalNoFinishEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalNoFinishEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalNoFinishEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalNoFinishEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAppraisalNoFinishEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_b_id}",String.valueOf(t.getTbId()));
 		sql  = sql.replace("#{change_appraisa_time}",String.valueOf(t.getChangeAppraisaTime()));
 		sql  = sql.replace("#{no_finish_reason}",String.valueOf(t.getNoFinishReason()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public AjaxJson saveNoFinish(TBAppraisalNoFinishEntity tBAppraisalNoFinish) {
        AjaxJson j = new AjaxJson();
        TBAppraisalNoFinishEntity t = null;
        String message = null;
        try {
            if (StringUtil.isNotEmpty(tBAppraisalNoFinish.getId())) {
                message = "鉴定计划未完成情况说明更新成功";
                t = commonDao.get(TBAppraisalNoFinishEntity.class, tBAppraisalNoFinish.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalNoFinish, t);
                commonDao.updateEntitie(t);
                j.setObj(JSONHelper.bean2json(t));
            } else {
                message = "鉴定计划未完成情况说明添加成功";
                tBAppraisalNoFinish.setState(ApprovalConstant.APPRSTATUS_UNSEND);
                commonDao.save(tBAppraisalNoFinish);
                j.setObj(JSONHelper.bean2json(tBAppraisalNoFinish));
                // 更新鉴定计划状态
                TBAppraisalProjectEntity appraisalProject = commonDao.get(TBAppraisalProjectEntity.class,
                        tBAppraisalNoFinish.getTbId());
                appraisalProject.setState("8");//未完成
                commonDao.updateEntitie(appraisalProject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划未完成情况说明更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;

    }
}