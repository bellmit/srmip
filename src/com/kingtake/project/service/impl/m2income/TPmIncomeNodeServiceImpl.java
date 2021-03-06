package com.kingtake.project.service.impl.m2income;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.dao.TPmIncomeNodeDao;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.m2income.TPmIncomeNodeServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmIncomeNodeService")
@Transactional
public class TPmIncomeNodeServiceImpl extends CommonServiceImpl implements TPmIncomeNodeServiceI, ProjectListServiceI {
    @Autowired
    private TPmIncomeNodeDao tPmIncomeNodeDao;

 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmIncomeNodeEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmIncomeNodeEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmIncomeNodeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmIncomeNodeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmIncomeNodeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmIncomeNodeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmIncomeNodeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{income_time}",String.valueOf(t.getIncomeTime()));
 		sql  = sql.replace("#{income_amount}",String.valueOf(t.getIncomeAmount()));
 		sql  = sql.replace("#{income_explain}",String.valueOf(t.getIncomeExplain()));
 		sql  = sql.replace("#{audit_flag}",String.valueOf(t.getAuditFlag()));
 		sql  = sql.replace("#{audit_userid}",String.valueOf(t.getAuditUserid()));
 		sql  = sql.replace("#{audit_username}",String.valueOf(t.getAuditUsername()));
 		sql  = sql.replace("#{audit_time}",String.valueOf(t.getAuditTime()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    /**
     * 机关跳转获取项目列表
     */
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String finishStatus = request.getParameter("finishStatus");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int curPage = Integer.valueOf(page);
        int pageSize = Integer.valueOf(rows);
        int start = (curPage - 1) * pageSize;
        int end = curPage * pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(projectNo)) {
            paramMap.put("projectNo", projectNo);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            paramMap.put("projectName", projectName);
        }
        if (StringUtils.isNotEmpty(beginDate_begin)) {
            paramMap.put("beginDate_begin", beginDate_begin);
        }
        if (StringUtils.isNotEmpty(beginDate_end)) {
            paramMap.put("beginDate_end", beginDate_end);
        }
        if (StringUtils.isNotEmpty(finishStatus)) {
            paramMap.put("finishStatus", finishStatus);
        }
        int count = this.tPmIncomeNodeDao.getProjectCount(paramMap);
        List<Map<String, Object>> list = this.tPmIncomeNodeDao.getProjectList(start, end, paramMap);
        dataGrid.setTotal(count);
        dataGrid.setResults(list);

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return this.tPmIncomeNodeDao.getAuditCount();
    }

    @Override
    public void deleteAddAttach(TPmIncomeNodeEntity t) {
        this.delAttachementByBid(t.getId());
        this.commonDao.delete(t);
    }
}