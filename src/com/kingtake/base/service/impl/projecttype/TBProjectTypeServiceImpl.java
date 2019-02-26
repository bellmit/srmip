package com.kingtake.base.service.impl.projecttype;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.service.projecttype.TBProjectTypeServiceI;

@Service("tBProjectTypeService")
@Transactional
public class TBProjectTypeServiceImpl extends CommonServiceImpl implements TBProjectTypeServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBProjectTypeEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBProjectTypeEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBProjectTypeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBProjectTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBProjectTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBProjectTypeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBProjectTypeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_type_name}",String.valueOf(t.getProjectTypeName()));
 		sql  = sql.replace("#{approval_code}",String.valueOf(t.getApprovalCode()));
 		sql  = sql.replace("#{funds_property_id}",String.valueOf(t.getFundsPropertyId()));
 		sql  = sql.replace("#{sort_code}",String.valueOf(t.getSortCode()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    /**
     * 逻辑删除。
     */
    @Override
    public void delProjectType(TBProjectTypeEntity tBProjectType) {
        tBProjectType = commonDao.getEntity(TBProjectTypeEntity.class, tBProjectType.getId());
        logicDelete(tBProjectType);
    }

    /**
     * 递归删除
     * 
     * @param tBProjectType
     */
    private void logicDelete(TBProjectTypeEntity tBProjectType) {
        tBProjectType.setValidFlag("0");
        this.commonDao.updateEntitie(tBProjectType);
        List<TBProjectTypeEntity> subProjectTypes = this.commonDao.findByProperty(TBProjectTypeEntity.class,
                "parentType.id", tBProjectType.getId());
        for (TBProjectTypeEntity tmp : subProjectTypes) {
            logicDelete(tmp);
        }
    }
}