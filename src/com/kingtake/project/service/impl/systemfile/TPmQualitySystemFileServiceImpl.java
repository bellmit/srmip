package com.kingtake.project.service.impl.systemfile;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.systemfile.TPmQualitySystemFileEntity;
import com.kingtake.project.service.systemfile.TPmQualitySystemFileServiceI;

@Service("tPmQualitySystemFileService")
@Transactional
public class TPmQualitySystemFileServiceImpl extends CommonServiceImpl implements TPmQualitySystemFileServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmQualitySystemFileEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmQualitySystemFileEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmQualitySystemFileEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmQualitySystemFileEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmQualitySystemFileEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmQualitySystemFileEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmQualitySystemFileEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{file_name}",String.valueOf(t.getFileName()));
 		sql  = sql.replace("#{release_time}",String.valueOf(t.getReleaseTime()));
 		sql  = sql.replace("#{execute_time}",String.valueOf(t.getExecuteTime()));
 		sql  = sql.replace("#{volume_num}",String.valueOf(t.getVolumeNum()));
 		sql  = sql.replace("#{publish_year}",String.valueOf(t.getPublishYear()));
 		sql  = sql.replace("#{writing_userid}",String.valueOf(t.getWritingUserid()));
 		sql  = sql.replace("#{writing_username}",String.valueOf(t.getWritingUsername()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	@Override
    public void deleteAddAttach(TPmQualitySystemFileEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}