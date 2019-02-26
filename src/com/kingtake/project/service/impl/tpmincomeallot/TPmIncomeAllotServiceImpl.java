package com.kingtake.project.service.impl.tpmincomeallot;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.tpmincomeallot.TPmIncomeAllotServiceI;

@Service("tPmIncomeAllotService")
@Transactional
public class TPmIncomeAllotServiceImpl extends CommonServiceImpl implements TPmIncomeAllotServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmIncomeAllotEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmIncomeAllotEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmIncomeAllotEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmIncomeAllotEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmIncomeAllotEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmIncomeAllotEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmIncomeAllotEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_manager_id}",String.valueOf(t.getProjectManagerId()));
 		sql  = sql.replace("#{project_manager}",String.valueOf(t.getProjectManager()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{balance}",String.valueOf(t.getBalance()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void saveUpdateIncomeAllot(TPmIncomeAllotEntity t) {
        if (StringUtils.isNotEmpty(t.getId())) {
            this.commonDao.updateEntitie(t);
        } else {
            this.commonDao.save(t);
        }
        TPmIncomeEntity income = this.commonDao.get(TPmIncomeEntity.class, t.getIncome().getId());
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class);
        cq.eq("income.id", income.getId());
        cq.add();
        List<TPmIncomeAllotEntity> list = commonDao.getListByCriteriaQuery(cq, false);
        BigDecimal total = new BigDecimal(0);
        for (TPmIncomeAllotEntity allot : list) {
            total = total.add(allot.getAmount());
        }
        income.setBalance(income.getIncomeAmount().subtract(total));
        this.commonDao.updateEntitie(income);
    }
}