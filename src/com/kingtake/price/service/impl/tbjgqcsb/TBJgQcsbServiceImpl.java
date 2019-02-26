package com.kingtake.price.service.impl.tbjgqcsb;
import com.kingtake.price.service.tbjgqcsb.TBJgQcsbServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.price.entity.tbjgqcsb.TBJgQcsbEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBJgQcsbService")
@Transactional
public class TBJgQcsbServiceImpl extends CommonServiceImpl implements TBJgQcsbServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBJgQcsbEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBJgQcsbEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBJgQcsbEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgQcsbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgQcsbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgQcsbEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBJgQcsbEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{ptmc}",String.valueOf(t.getPtmc()));
 		sql  = sql.replace("#{xtsbmc}",String.valueOf(t.getXtsbmc()));
 		sql  = sql.replace("#{qcsbmc}",String.valueOf(t.getQcsbmc()));
 		sql  = sql.replace("#{xhgg}",String.valueOf(t.getXhgg()));
 		sql  = sql.replace("#{jldw}",String.valueOf(t.getJldw()));
 		sql  = sql.replace("#{czdw}",String.valueOf(t.getCzdw()));
 		sql  = sql.replace("#{dj}",String.valueOf(t.getDj()));
 		sql  = sql.replace("#{cgsj}",String.valueOf(t.getCgsj()));
 		sql  = sql.replace("#{sjdw}",String.valueOf(t.getSjdw()));
 		sql  = sql.replace("#{htlxjddw}",String.valueOf(t.getHtlxjddw()));
 		sql  = sql.replace("#{beiz}",String.valueOf(t.getBeiz()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}