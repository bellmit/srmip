package com.kingtake.project.service.impl.contractnodecheck;
import com.kingtake.project.service.contractnodecheck.TPmContractNchkReciLogsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.project.entity.contractnodecheck.TPmContractNchkReciLogsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tPmContractNchkReciLogsService")
@Transactional
public class TPmContractNchkReciLogsServiceImpl extends CommonServiceImpl implements TPmContractNchkReciLogsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractNchkReciLogsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractNchkReciLogsEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractNchkReciLogsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractNchkReciLogsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractNchkReciLogsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractNchkReciLogsEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractNchkReciLogsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{contract_node_id}",String.valueOf(t.getContractNodeId()));
 		sql  = sql.replace("#{receive_userid}",String.valueOf(t.getReceiveUserid()));
 		sql  = sql.replace("#{receive_username}",String.valueOf(t.getReceiveUsername()));
 		sql  = sql.replace("#{receive_departid}",String.valueOf(t.getReceiveDepartid()));
 		sql  = sql.replace("#{receive_departname}",String.valueOf(t.getReceiveDepartname()));
 		sql  = sql.replace("#{operate_time}",String.valueOf(t.getOperateTime()));
 		sql  = sql.replace("#{operate_status}",String.valueOf(t.getOperateStatus()));
 		sql  = sql.replace("#{suggestion_type}",String.valueOf(t.getSuggestionType()));
 		sql  = sql.replace("#{suggestion_content}",String.valueOf(t.getSuggestionContent()));
 		sql  = sql.replace("#{valid_flag}",String.valueOf(t.getValidFlag()));
 		sql  = sql.replace("#{suggestion_code}",String.valueOf(t.getSuggestionCode()));
 		sql  = sql.replace("#{nchk_flow_id}",String.valueOf(t.getNchkFlowId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}