package com.kingtake.project.service.impl.resultspread;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.resultspread.TBResultSpreadEntity;
import com.kingtake.project.service.resultspread.TBResultSpreadServiceI;

@Service("tBResultSpreadService")
@Transactional
public class TBResultSpreadServiceImpl extends CommonServiceImpl implements TBResultSpreadServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBResultSpreadEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBResultSpreadEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBResultSpreadEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBResultSpreadEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBResultSpreadEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBResultSpreadEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBResultSpreadEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{tech_dev_unit}",String.valueOf(t.getTechDevUnit()));
 		sql  = sql.replace("#{finish_userid}",String.valueOf(t.getFinishUserid()));
 		sql  = sql.replace("#{finish_username}",String.valueOf(t.getFinishUsername()));
 		sql  = sql.replace("#{tech_summary}",String.valueOf(t.getTechSummary()));
 		sql  = sql.replace("#{patent_status}",String.valueOf(t.getPatentStatus()));
 		sql  = sql.replace("#{reward_info}",String.valueOf(t.getRewardInfo()));
 		sql  = sql.replace("#{tech_status}",String.valueOf(t.getTechStatus()));
 		sql  = sql.replace("#{apply_scope}",String.valueOf(t.getApplyScope()));
 		sql  = sql.replace("#{change_expect}",String.valueOf(t.getChangeExpect()));
 		sql  = sql.replace("#{devotion_requirement}",String.valueOf(t.getDevotionRequirement()));
 		sql  = sql.replace("#{expect_benefit}",String.valueOf(t.getExpectBenefit()));
 		sql  = sql.replace("#{result_contact}",String.valueOf(t.getResultContact()));
 		sql  = sql.replace("#{result_phone}",String.valueOf(t.getResultPhone()));
 		sql  = sql.replace("#{change_info}",String.valueOf(t.getChangeInfo()));
 		sql  = sql.replace("#{cooperative_unit}",String.valueOf(t.getCooperativeUnit()));
 		sql  = sql.replace("#{transfer_form}",String.valueOf(t.getTransferForm()));
 		sql  = sql.replace("#{contract_info}",String.valueOf(t.getContractInfo()));
 		sql  = sql.replace("#{contract_deadline}",String.valueOf(t.getContractDeadline()));
 		sql  = sql.replace("#{contract_amount}",String.valueOf(t.getContractAmount()));
 		sql  = sql.replace("#{contract_income}",String.valueOf(t.getContractIncome()));
 		sql  = sql.replace("#{resolve_difficult}",String.valueOf(t.getResolveDifficult()));
 		sql  = sql.replace("#{change_contact}",String.valueOf(t.getChangeContact()));
 		sql  = sql.replace("#{change_phone}",String.valueOf(t.getChangePhone()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void deleteAddAttach(TBResultSpreadEntity t) {
        this.delAttachementByBid(t.getId());
        this.commonDao.delete(t);
    }
}