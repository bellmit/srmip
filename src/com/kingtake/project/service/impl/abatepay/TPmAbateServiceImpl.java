package com.kingtake.project.service.impl.abatepay;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.abatepay.TPmAbateEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.abatepay.TPmAbateServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmAbateService")
@Transactional
public class TPmAbateServiceImpl extends CommonServiceImpl implements TPmAbateServiceI,
ProjectListServiceI{

    @Autowired
    private ActivitiService activitiService;
    
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmAbateEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmAbateEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmAbateEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmAbateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmAbateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmAbateEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmAbateEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{PROJECT_ID}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{reason}",String.valueOf(t.getReason()));
 		sql  = sql.replace("#{suggestion}",String.valueOf(t.getSuggestion()));
 		sql  = sql.replace("#{pay_funds}",String.valueOf(t.getPayFunds()));
 		sql  = sql.replace("#{zdfcbjme}",String.valueOf(t.getZdfcbjme()));
 		sql  = sql.replace("#{zdwxjme}",String.valueOf(t.getZdwxjme()));
 		sql  = sql.replace("#{xnxzjme}",String.valueOf(t.getXnxzjme()));
 		sql  = sql.replace("#{dxylbl}",String.valueOf(t.getDxylbl()));
 		sql  = sql.replace("#{dxylje}",String.valueOf(t.getDxylje()));
 		sql  = sql.replace("#{xyylbl}",String.valueOf(t.getXyylbl()));
 		sql  = sql.replace("#{xyylje}",String.valueOf(t.getXyylje()));
 		sql  = sql.replace("#{xylbl}",String.valueOf(t.getXylbl()));
 		sql  = sql.replace("#{xylje}",String.valueOf(t.getXylje()));
 		sql  = sql.replace("#{jysylbl}",String.valueOf(t.getJysylbl()));
 		sql  = sql.replace("#{jysylje}",String.valueOf(t.getJysylje()));
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
    public void saveAbate(TPmAbateEntity tPmAbate) {
        try {
            if (StringUtils.isNotEmpty(tPmAbate.getProjectId())) {
                TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, tPmAbate.getProjectId());
                tPmAbate.setProjectName(project.getProjectName());
            }
            if (StringUtil.isNotEmpty(tPmAbate.getId())) {
                TPmAbateEntity t = this.commonDao.get(TPmAbateEntity.class, tPmAbate.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmAbate, t);
                commonDao.saveOrUpdate(t);
            } else {
                commonDao.save(tPmAbate);
            }
        } catch (Exception e) {
            throw new BusinessException("保存减免出错", e);
        }
    }
    
    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = param.get("key");
        return activitiService.getAuditCount(businessCode);
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        
    }

    @Override
    public void deleteAddAttach(TPmAbateEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}