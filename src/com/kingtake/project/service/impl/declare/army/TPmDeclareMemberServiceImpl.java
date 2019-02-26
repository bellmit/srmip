package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.declare.army.TPmDeclareMemberEntity;
import com.kingtake.project.service.declare.army.TPmDeclareMemberServiceI;

@Service("tPmDeclareMemberService")
@Transactional
public class TPmDeclareMemberServiceImpl extends CommonServiceImpl implements TPmDeclareMemberServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmDeclareMemberEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmDeclareMemberEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmDeclareMemberEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmDeclareMemberEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmDeclareMemberEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmDeclareMemberEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmDeclareMemberEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_declare_id}",String.valueOf(t.getProjDeclareId()));
 		sql  = sql.replace("#{userid}",String.valueOf(t.getUserid()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{birthday}",String.valueOf(t.getBirthday()));
 		sql  = sql.replace("#{degree}",String.valueOf(t.getDegree()));
 		sql  = sql.replace("#{position}",String.valueOf(t.getPosition()));
 		sql  = sql.replace("#{task_divide}",String.valueOf(t.getTaskDivide()));
 		sql  = sql.replace("#{super_unit}",String.valueOf(t.getSuperUnit()));
 		sql  = sql.replace("#{project_manager}",String.valueOf(t.getProjectManager()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{postal_address}",String.valueOf(t.getPostalAddress()));
 		sql  = sql.replace("#{post_code}",String.valueOf(t.getPostCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
}