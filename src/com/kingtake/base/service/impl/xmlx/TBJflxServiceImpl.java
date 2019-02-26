package com.kingtake.base.service.impl.xmlx;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.xmlb.TBJflxEntity;
import com.kingtake.base.service.xmlx.TBJflxServiceI;

@Service("tBJflxService")
@Transactional
public class TBJflxServiceImpl extends CommonServiceImpl implements TBJflxServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBJflxEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBJflxEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBJflxEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJflxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJflxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJflxEntity t){
	 	return true;
 	}
 	
 	@Override
    public void delJflx(TBJflxEntity jflx) {
 		jflx = commonDao.getEntity(TBJflxEntity.class, jflx.getId());
        logicDelete(jflx);
    }
 	
 	/**
     * 递归删除
     * 
     */
    private void logicDelete(TBJflxEntity jflx) {
    	jflx.setValidFlag("0");
        this.commonDao.updateEntitie(jflx);
        List<TBJflxEntity> subProjectTypes = this.commonDao.findByProperty(TBJflxEntity.class,
                "parentType.id", jflx.getId());
        for (TBJflxEntity tmp : subProjectTypes) {
            logicDelete(tmp);
        }
    }
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBJflxEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{jflxmc}",String.valueOf(t.getJflxmc()));
 		sql  = sql.replace("#{code}",String.valueOf(t.getCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}