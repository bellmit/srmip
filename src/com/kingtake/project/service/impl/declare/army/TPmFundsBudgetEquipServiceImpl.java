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
import com.kingtake.project.entity.declare.army.TPmFundsBudgetEquipEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetEquipServiceI;

@Service("tPmFundsBudgetEquipService")
@Transactional
public class TPmFundsBudgetEquipServiceImpl extends CommonServiceImpl implements TPmFundsBudgetEquipServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmFundsBudgetEquipEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmFundsBudgetEquipEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmFundsBudgetEquipEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmFundsBudgetEquipEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmFundsBudgetEquipEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmFundsBudgetEquipEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmFundsBudgetEquipEntity t){
 		sql  = sql.replace("#{quantity}",String.valueOf(t.getQuantity()));
 		sql  = sql.replace("#{funds}",String.valueOf(t.getFunds()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_declare_id}",String.valueOf(t.getProjDeclareId()));
 		sql  = sql.replace("#{budget_id}",String.valueOf(t.getBudgetId()));
 		sql  = sql.replace("#{budget_name}",String.valueOf(t.getBudgetName()));
 		sql  = sql.replace("#{model_config}",String.valueOf(t.getModelConfig()));
 		sql  = sql.replace("#{unit_price}",String.valueOf(t.getUnitPrice()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public void init(String projDeclareId, Class clazz) throws Exception {
		// 申报书信息
		Object obj = commonDao.get(clazz, projDeclareId);
		// 获得项目信息
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, clazz.getMethod("getTpId").invoke(obj).toString());
		
		// 复制
		// 源数据
		List<TBApprovalBudgetRelaEntity> srcs = commonDao.getSession().createCriteria(TBApprovalBudgetRelaEntity.class)
			.add(Restrictions.eq("projApproval", project.getPlanContractFlag()))
			.add(Restrictions.eq("budgetNae", ProjectConstant.PROJECT_BUDGET_EQUIPMENT)).list();
		for(int i = 0; i < srcs.size(); i++){
			srcs.addAll(commonDao.getSession().createCriteria(TBApprovalBudgetRelaEntity.class)
					.add(Restrictions.eq("TBApprovalBudgetRelaEntity.id", srcs.get(i).getId())).list());
		}
		// 目的数据
		List<TPmFundsBudgetEquipEntity> dests = new ArrayList<TPmFundsBudgetEquipEntity>();
		for(int i = 0; i < srcs.size(); i++){
			TBApprovalBudgetRelaEntity src = srcs.get(i);
			TPmFundsBudgetEquipEntity dest = new TPmFundsBudgetEquipEntity();
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
		List<TPmFundsBudgetEquipEntity> list = commonDao.findByProperty(TPmFundsBudgetEquipEntity.class, "projDeclareId", projDeclareId);
		// 修改节点之间的关系
		for(int i = 0; i < list.size(); i++){
			TPmFundsBudgetEquipEntity child = list.get(i);
			if(!StringUtil.isEmpty(child.getParent())){
				for(int j = 0; j < list.size(); j++){
					TPmFundsBudgetEquipEntity parent = list.get(j);
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
			TPmFundsBudgetEquipEntity funds = commonDao.get(TPmFundsBudgetEquipEntity.class, parent);
			funds.setFunds(funds.getFunds() - oldMoney + newMoney);
			commonDao.updateEntitie(funds);
			
			updateParentsMoney(funds.getParent(), oldMoney, newMoney);
		}
		
	}
	
    @Override
    public List<Map<String, Object>> datagrid(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip, HttpServletRequest request) {
        // 根据申报书id查询设备费是否已存在 
        List<TPmFundsBudgetEquipEntity> funds = this.commonDao.findByProperty(TPmFundsBudgetEquipEntity.class,
                "projDeclareId",
                tPmFundsBudgetEquip.getProjDeclareId());
        if (funds == null || funds.size() == 0) {
            // 没有预算，添加初始数据
            String flag = request.getParameter("flag");
            Class clazz = TBPmDeclareArmyResearchEntity.class;
            if ("repair".equals(flag)) {
                clazz = TBPmDeclareRepairEntity.class;
            }
            try {
                this.init(tPmFundsBudgetEquip.getProjDeclareId(), clazz);
                this.initUpdate(tPmFundsBudgetEquip.getProjDeclareId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (TPmFundsBudgetEquipEntity entity : funds) {
                if (StringUtils.isEmpty(entity.getBudgetName())) {
                    this.commonDao.delete(entity);
                }
            }
        }

        // 查询所有满足条件的值
        String sql = "SELECT ID, LEVEL, BUDGET_NAME AS NAME, PARENT, ADD_CHILD_FLAG AS ADDCHILDFLAG, "
                + " MODEL_CONFIG AS CONFIG, UNIT_PRICE AS PRICE, QUANTITY, FUNDS, MEMO, 'equTree' AS TREE FROM T_PM_FUNDS_BUDGET_EQUIP "
                + " WHERE PROJ_DECLARE_ID = ? " + " START WITH PARENT IS NULL CONNECT BY PRIOR ID = PARENT "
                + " ORDER BY LEVEL, PARENT DESC";
        List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, tPmFundsBudgetEquip.getProjDeclareId());

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