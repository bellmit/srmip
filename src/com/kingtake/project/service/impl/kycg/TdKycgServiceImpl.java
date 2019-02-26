package com.kingtake.project.service.impl.kycg;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.kycg.TdKycgEntity;
import com.kingtake.project.service.kycg.TdKycgServiceI;

@Service("tdKycgService")
@Transactional
public class TdKycgServiceImpl extends CommonServiceImpl implements TdKycgServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TdKycgEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TdKycgEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TdKycgEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TdKycgEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TdKycgEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TdKycgEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TdKycgEntity t){
 		sql  = sql.replace("#{cgdm}",String.valueOf(t.getCgdm()));
 		sql  = sql.replace("#{xmdm}",String.valueOf(t.getXmdm()));
 		sql  = sql.replace("#{jcbh}",String.valueOf(t.getJcbh()));
 		sql  = sql.replace("#{cgmc}",String.valueOf(t.getCgmc()));
 		sql  = sql.replace("#{wcdw}",String.valueOf(t.getWcdw()));
 		sql  = sql.replace("#{xmzlxr}",String.valueOf(t.getXmzlxr()));
 		sql  = sql.replace("#{xmzlxrs}",String.valueOf(t.getXmzlxrs()));
 		sql  = sql.replace("#{jglxr}",String.valueOf(t.getJglxr()));
 		sql  = sql.replace("#{jglxfs}",String.valueOf(t.getJglxfs()));
 		sql  = sql.replace("#{jdsj}",String.valueOf(t.getJdsj()));
 		sql  = sql.replace("#{jddd}",String.valueOf(t.getJddd()));
 		sql  = sql.replace("#{jdxs}",String.valueOf(t.getJdxs()));
 		sql  = sql.replace("#{cgzt}",String.valueOf(t.getCgzt()));
 		sql  = sql.replace("#{sqrq}",String.valueOf(t.getSqrq()));
 		sql  = sql.replace("#{gdh}",String.valueOf(t.getGdh()));
 		sql  = sql.replace("#{scrq}",String.valueOf(t.getScrq()));
 		sql  = sql.replace("#{sbrq}",String.valueOf(t.getSbrq()));
 		sql  = sql.replace("#{jdsph}",String.valueOf(t.getJdsph()));
 		sql  = sql.replace("#{tzbh}",String.valueOf(t.getTzbh()));
 		sql  = sql.replace("#{clsbrq}",String.valueOf(t.getClsbrq()));
 		sql  = sql.replace("#{sppj}",String.valueOf(t.getSppj()));
 		sql  = sql.replace("#{zsbh}",String.valueOf(t.getZsbh()));
 		sql  = sql.replace("#{wcr}",String.valueOf(t.getWcr()));
 		sql  = sql.replace("#{zslqr}",String.valueOf(t.getZslqr()));
 		sql  = sql.replace("#{zslqrq}",String.valueOf(t.getZslqrq()));
 		sql  = sql.replace("#{bz}",String.valueOf(t.getBz()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}