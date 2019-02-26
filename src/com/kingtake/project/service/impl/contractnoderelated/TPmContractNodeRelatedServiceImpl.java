package com.kingtake.project.service.impl.contractnoderelated;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.contractnoderelated.TPmContractNodeRelatedEntity;
import com.kingtake.project.service.contractnoderelated.TPmContractNodeRelatedServiceI;

@Service("tPmContractNodeRelatedService")
@Transactional
public class TPmContractNodeRelatedServiceImpl extends CommonServiceImpl implements TPmContractNodeRelatedServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmContractNodeRelatedEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmContractNodeRelatedEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmContractNodeRelatedEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TPmContractNodeRelatedEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TPmContractNodeRelatedEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmContractNodeRelatedEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmContractNodeRelatedEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{contract_node_id}", String.valueOf(t.getContractNodeId()));
        sql = sql.replace("#{amount}", String.valueOf(t.getAmount()));
        sql = sql.replace("#{income_pay_node_id}", String.valueOf(t.getIncomePayNodeId()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }
}