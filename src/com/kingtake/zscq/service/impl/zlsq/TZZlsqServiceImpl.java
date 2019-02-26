package com.kingtake.zscq.service.impl.zlsq;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.zlsq.TZZlsqServiceI;

@Service("tZZlsqService")
@Transactional
public class TZZlsqServiceImpl extends CommonServiceImpl implements TZZlsqServiceI,ApprFlowServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TZZlsqEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TZZlsqEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TZZlsqEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TZZlsqEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TZZlsqEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TZZlsqEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TZZlsqEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{gdh}",String.valueOf(t.getGdh()));
 		sql  = sql.replace("#{wcdw}",String.valueOf(t.getWcdw()));
 		sql  = sql.replace("#{lx}",String.valueOf(t.getLx()));
 		sql  = sql.replace("#{glxm}",String.valueOf(t.getGlxm()));
 		sql  = sql.replace("#{mc}",String.valueOf(t.getMc()));
 		sql  = sql.replace("#{fmr}",String.valueOf(t.getFmr()));
 		sql  = sql.replace("#{dyfmrsfzh}",String.valueOf(t.getDyfmrsfzh()));
 		sql  = sql.replace("#{dljg_id}",String.valueOf(t.getDljgId()));
 		sql  = sql.replace("#{lxr}",String.valueOf(t.getLxr()));
 		sql  = sql.replace("#{lxrdh}",String.valueOf(t.getLxrdh()));
 		sql  = sql.replace("#{bz}",String.valueOf(t.getBz()));
 		sql  = sql.replace("#{fjbm}",String.valueOf(t.getFjbm()));
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
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TZZlsqEntity t = commonDao.get(TZZlsqEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setApprStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setApprFinishTime(new Date());
                t.setApprFinishUserId(user.getId());
                t.setApprFinishUserName(user.getRealName());
                String gdh = PrimaryGenerater.getInstance().generateZlsqGdh();
                t.setGdh(gdh);
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void delZlsq(TZZlsqEntity tZZlsq) {
        tZZlsq = commonDao.getEntity(TZZlsqEntity.class, tZZlsq.getId());
        this.delAttachementByBid(tZZlsq.getFjbm());
        this.commonDao.delete(tZZlsq);
    }

    @Override
    public void doBack(String id) {
        TZZlsqEntity t = commonDao.get(TZZlsqEntity.class, id);
        //将审批状态修改为被驳回
        t.setApprStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TZZlsqEntity entity = this.get(TZZlsqEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getApprStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getApprStatus())) {
              entity.setApprStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TZZlsqEntity t = commonDao.get(TZZlsqEntity.class, id);
        if(t!=null){
            appName = t.getMc();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TZZlsqEntity entity = this.get(TZZlsqEntity.class, id);
        if (entity != null) {
              entity.setApprStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }
}