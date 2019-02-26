package com.kingtake.project.service.impl.learning;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.learning.TBLearningBookEntity;
import com.kingtake.project.service.learning.TBLearningBookServiceI;

@Service("tBLearningBookService")
@Transactional
public class TBLearningBookServiceImpl extends CommonServiceImpl implements TBLearningBookServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBLearningBookEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBLearningBookEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBLearningBookEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBLearningBookEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBLearningBookEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBLearningBookEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBLearningBookEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProject().getId()));
 		sql  = sql.replace("#{book_name}",String.valueOf(t.getBookName()));
 		sql  = sql.replace("#{author_first}",String.valueOf(t.getAuthorFirst()));
 		sql  = sql.replace("#{author_second}",String.valueOf(t.getAuthorSecond()));
 		sql  = sql.replace("#{author_third}",String.valueOf(t.getAuthorThird()));
 		sql  = sql.replace("#{author_other}",String.valueOf(t.getAuthorOther()));
 		sql  = sql.replace("#{author_first_depart}",String.valueOf(t.getAuthorFirstDepart()));
 		sql  = sql.replace("#{concrete_depart}",String.valueOf(t.getConcreteDepart()));
 		sql  = sql.replace("#{keyword}",String.valueOf(t.getKeyword()));
 		sql  = sql.replace("#{class_num}",String.valueOf(t.getClassNum()));
 		sql  = sql.replace("#{secret_code}",String.valueOf(t.getSecretCode()));
 		sql  = sql.replace("#{book_type}",String.valueOf(t.getBookType()));
 		sql  = sql.replace("#{publisher}",String.valueOf(t.getPublisher()));
 		sql  = sql.replace("#{isbn_num}",String.valueOf(t.getIsbnNum()));
 		sql  = sql.replace("#{publish_year}",String.valueOf(t.getPublishYear()));
 		sql  = sql.replace("#{summary}",String.valueOf(t.getSummary()));
        sql = sql.replace("#{audit_status}", String.valueOf(t.getBpmStatus()));
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
    public void deleteAddAttach(TBLearningBookEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void updateEntitieAttach(TBLearningBookEntity t) {
        this.updateAttachProjectId(t.getId(), t.getProject().getId());
        this.commonDao.updateEntitie(t);
    }
}