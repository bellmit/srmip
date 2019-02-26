package com.kingtake.project.service.impl.contractnodecheck;
import com.kingtake.project.service.contractnodecheck.TPmContractNchkFlowLogsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.project.entity.contractnodecheck.TPmContractNchkFlowLogsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tPmContractNchkFlowLogsService")
@Transactional
public class TPmContractNchkFlowLogsServiceImpl extends CommonServiceImpl implements TPmContractNchkFlowLogsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractNchkFlowLogsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractNchkFlowLogsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractNchkFlowLogsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractNchkFlowLogsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractNchkFlowLogsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractNchkFlowLogsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractNchkFlowLogsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{contract_node_id}",String.valueOf(t.getContractNodeId()));
 		sql  = sql.replace("#{operate_userid}",String.valueOf(t.getOperateUserid()));
 		sql  = sql.replace("#{operate_username}",String.valueOf(t.getOperateUsername()));
 		sql  = sql.replace("#{operate_date}",String.valueOf(t.getOperateDate()));
 		sql  = sql.replace("#{operate_departid}",String.valueOf(t.getOperateDepartid()));
 		sql  = sql.replace("#{operate_departname}",String.valueOf(t.getOperateDepartname()));
 		sql  = sql.replace("#{operate_ip}",String.valueOf(t.getOperateIp()));
 		sql  = sql.replace("#{sender_tip}",String.valueOf(t.getSenderTip()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}