package com.kingtake.price.service.impl.tbjgky;
import com.kingtake.price.service.tbjgky.TBJgKyServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.price.entity.tbjgky.TBJgKyEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBJgKyService")
@Transactional
public class TBJgKyServiceImpl extends CommonServiceImpl implements TBJgKyServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBJgKyEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBJgKyEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBJgKyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgKyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgKyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgKyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBJgKyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{fl}",String.valueOf(t.getFl()));
 		sql  = sql.replace("#{mc}",String.valueOf(t.getMc()));
 		sql  = sql.replace("#{ggxh}",String.valueOf(t.getGgxh()));
 		sql  = sql.replace("#{jldw}",String.valueOf(t.getJldw()));
 		sql  = sql.replace("#{dj}",String.valueOf(t.getDj()));
 		sql  = sql.replace("#{cgdw}",String.valueOf(t.getCgdw()));
 		sql  = sql.replace("#{cgsj}",String.valueOf(t.getCgsj()));
 		sql  = sql.replace("#{sccj}",String.valueOf(t.getSccj()));
 		sql  = sql.replace("#{ly}",String.valueOf(t.getLy()));
 		sql  = sql.replace("#{lyxm}",String.valueOf(t.getLyxm()));
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