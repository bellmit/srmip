package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.declare.army.TPmFundsBudgetOutsideEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetOutsideServiceI;

@Service("tPmFundsBudgetOutsideService")
@Transactional
public class TPmFundsBudgetOutsideServiceImpl extends CommonServiceImpl implements TPmFundsBudgetOutsideServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmFundsBudgetOutsideEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmFundsBudgetOutsideEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmFundsBudgetOutsideEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmFundsBudgetOutsideEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmFundsBudgetOutsideEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmFundsBudgetOutsideEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmFundsBudgetOutsideEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_declare_id}",String.valueOf(t.getProjDeclareId()));
 		sql  = sql.replace("#{budget_id}",String.valueOf(t.getBudgetId()));
 		sql  = sql.replace("#{budget_name}",String.valueOf(t.getBudgetName()));
 		sql  = sql.replace("#{outside_content}",String.valueOf(t.getOutsideContent()));
 		sql  = sql.replace("#{outside_unit}",String.valueOf(t.getOutsideUnit()));
 		sql  = sql.replace("#{funds}",String.valueOf(t.getFunds()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public void init(String projDeclareId, Class clazz)throws Exception {
		// 申报书信息
		Object obj = commonDao.get(clazz, projDeclareId);
		// 获得项目信息
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, clazz.getMethod("getTpId").invoke(obj).toString());
		
		// 复制
		// 源数据
		List<TBApprovalBudgetRelaEntity> srcs = commonDao.getSession().createCriteria(TBApprovalBudgetRelaEntity.class)
			.add(Restrictions.eq("projApproval", project.getPlanContractFlag()))
			.add(Restrictions.eq("budgetNae", ProjectConstant.PROJECT_BUDGET_OUTSIDE)).list();
		for(int i = 0; i < srcs.size(); i++){
			srcs.addAll(commonDao.getSession().createCriteria(TBApprovalBudgetRelaEntity.class)
					.add(Restrictions.eq("TBApprovalBudgetRelaEntity.id", srcs.get(i).getId())).list());
		}
		// 目的数据
		List<TPmFundsBudgetOutsideEntity> dests = new ArrayList<TPmFundsBudgetOutsideEntity>();
		for(int i = 0; i < srcs.size(); i++){
			TBApprovalBudgetRelaEntity src = srcs.get(i);
			TPmFundsBudgetOutsideEntity dest = new TPmFundsBudgetOutsideEntity();
			dest.setBudgetId(src.getId());
			dest.setBudgetName(src.getBudgetNae());
			dest.setAddChildFlag(src.getAddChildFlag());
			dest.setParent(src.getTBApprovalBudgetRelaEntity() == null ? null : src.getTBApprovalBudgetRelaEntity().getId());
			dest.setProjDeclareId(projDeclareId);
			
			dests.add(dest);
		}
		// 批量保存
		commonDao.batchSave(dests);
	}

	@Override
	public void initUpdate(String projDeclareId) {
		List<TPmFundsBudgetOutsideEntity> list = commonDao.findByProperty(TPmFundsBudgetOutsideEntity.class, "projDeclareId", projDeclareId);
		// 修改节点之间的关系
		for(int i = 0; i < list.size(); i++){
			TPmFundsBudgetOutsideEntity child = list.get(i);
			if(!StringUtil.isEmpty(child.getParent())){
				for(int j = 0; j < list.size(); j++){
					TPmFundsBudgetOutsideEntity parent = list.get(j);
					if(parent.getBudgetId().equals(child.getParent())){
						child.setParent(parent.getId());
						commonDao.updateEntitie(child);
					}
				}
			}
		}
	}

	@Override
	public void updateParentsMoney(String parent, double oldMoney, double newMoney) {
		if(parent != null){
			TPmFundsBudgetOutsideEntity funds = commonDao.get(TPmFundsBudgetOutsideEntity.class, parent);
			funds.setFunds(funds.getFunds() - oldMoney + newMoney);
			commonDao.updateEntitie(funds);
			
			updateParentsMoney(funds.getParent(), oldMoney, newMoney);
		}
		
	}

    @Override
    public List<Map<String, Object>> datagrid(TPmFundsBudgetOutsideEntity tPmFundsBudgetOutside,
            HttpServletRequest request) {
        // 根据申报书id查询外协费是否已存在 
        List<TPmFundsBudgetOutsideEntity> funds = this.commonDao.findByProperty(TPmFundsBudgetOutsideEntity.class,
                "projDeclareId",
                tPmFundsBudgetOutside.getProjDeclareId());
        if (funds == null || funds.size() == 0) {
            // 没有预算，添加初始数据
            String flag = request.getParameter("flag");
            Class clazz = TBPmDeclareArmyResearchEntity.class;
            if ("repair".equals(flag)) {
                clazz = TBPmDeclareRepairEntity.class;
            }
            try {
                this.init(tPmFundsBudgetOutside.getProjDeclareId(), clazz);
                this.initUpdate(tPmFundsBudgetOutside.getProjDeclareId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (TPmFundsBudgetOutsideEntity entity : funds) {
                if (StringUtils.isEmpty(entity.getBudgetName())) {
                    this.commonDao.delete(entity);
                }
            }
        }

        // 查询所有满足条件的值
        String sql = "SELECT ID, LEVEL, BUDGET_NAME AS NAME, PARENT, ADD_CHILD_FLAG AS ADDCHILDFLAG, "
                + " OUTSIDE_UNIT AS UNIT, FUNDS, MEMO, OUTSIDE_CONTENT AS CONTENT, 'outTree' AS TREE FROM T_PM_FUNDS_BUDGET_OUTSIDE "
                + " WHERE PROJ_DECLARE_ID = ? " + " START WITH PARENT IS NULL CONNECT BY PRIOR ID = PARENT "
                + " ORDER BY LEVEL, PARENT DESC";
        List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, tPmFundsBudgetOutside.getProjDeclareId());

        // 将数据分级保存
        Map<Integer, Object> levelMap = new HashMap<Integer, Object>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            int level = Integer.parseInt(map.get("LEVEL").toString());
            List<Map<String, Object>> temp = (levelMap.get(level) == null ? new ArrayList<Map<String, Object>>()
                    : (List<Map<String, Object>>) levelMap.get(level));
            temp.add(map);
            levelMap.put(level, temp);
        }

        // 获得树的层数
        int maxLevel = Integer.parseInt(list.get(list.size() - 1).get("LEVEL").toString());
        // 组装为easyui的treegrid数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (int level = maxLevel; level > 1; level--) {
            List<Map<String, Object>> children = (List<Map<String, Object>>) levelMap.get(level);
            List<Map<String, Object>> parents = (List<Map<String, Object>>) levelMap.get(level - 1);
            for (int i = 0; i < children.size(); i++) {
                Map<String, Object> child = children.get(i);
                for (int j = 0; j < parents.size(); j++) {
                    Map<String, Object> parent = parents.get(j);
                    if (parent.get("ID").equals(child.get("PARENT"))) {
                        List<Map<String, Object>> childrenList = (parent.get("children") == null ? new ArrayList<Map<String, Object>>()
                                : (List<Map<String, Object>>) parent.get("children"));
                        childrenList.add(child);
                        if (parent.get("children") == null) {
                            parent.put("children", childrenList);
                        }
                        break;
                    }
                }
            }
        }
        result.addAll((Collection<? extends Map<String, Object>>) levelMap.get(1));
        return result;
    }
}