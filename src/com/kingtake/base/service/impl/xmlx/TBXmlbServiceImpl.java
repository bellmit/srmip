package com.kingtake.base.service.impl.xmlx;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.base.service.xmlx.TBXmlbServiceI;

@Service("tBXmlbService")
@Transactional
public class TBXmlbServiceImpl extends CommonServiceImpl implements TBXmlbServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBXmlbEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBXmlbEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBXmlbEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBXmlbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBXmlbEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBXmlbEntity t){
	 	return true;
 	}
 	
 	@Override
    public void delXmlb(TBXmlbEntity xmlb) {
 		xmlb = commonDao.getEntity(TBXmlbEntity.class, xmlb.getId());
        logicDelete(xmlb);
    }
 	
 	/**
     * 递归删除
     * 
     * @param tBProjectType
     */
    private void logicDelete(TBXmlbEntity xmlb) {
    	xmlb.setValidFlag("0");
        this.commonDao.updateEntitie(xmlb);
        List<TBXmlbEntity> subProjectTypes = this.commonDao.findByProperty(TBXmlbEntity.class,
                "parentType.id", xmlb.getId());
        for (TBXmlbEntity tmp : subProjectTypes) {
            logicDelete(tmp);
        }
    }
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBXmlbEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{xmlb}",String.valueOf(t.getXmlb()));
 		sql  = sql.replace("#{zgdw}",String.valueOf(t.getZgdw()));
 		sql  = sql.replace("#{jflx}",String.valueOf(t.getJflxStr()));
 		sql  = sql.replace("#{xmlx}",String.valueOf(t.getXmlx()));
 		sql  = sql.replace("#{xmxz}",String.valueOf(t.getXmxz()));
 		sql  = sql.replace("#{xmly}",String.valueOf(t.getXmly()));
 		sql  = sql.replace("#{kjbmgz}",String.valueOf(t.getKjbmgz()));
 		sql  = sql.replace("#{bz}",String.valueOf(t.getBz()));
 		sql  = sql.replace("#{sortCode}",String.valueOf(t.getSortCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}