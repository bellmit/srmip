package com.kingtake.project.service.impl.funds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.freehep.graphicsio.swf.SWFAction.GetVariable;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.funds.TPmContractFundsEntity;
import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;

import jodd.util.StringUtil;

@Service("tPmContractFundsService")
@Transactional
public class TPmContractFundsServiceImpl extends CommonServiceImpl implements TPmContractFundsServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmContractFundsEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmContractFundsEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmContractFundsEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmContractFundsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmContractFundsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmContractFundsEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmContractFundsEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
        sql = sql.replace("#{num}", String.valueOf(t.getNum()));
        sql = sql.replace("#{content_id}", String.valueOf(t.getContentId()));
        sql = sql.replace("#{content}", String.valueOf(t.getContent()));
        sql = sql.replace("#{money}", String.valueOf(t.getMoney()));
        sql = sql.replace("#{remark}", String.valueOf(t.getRemark()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    public void init(String apprId){
    	// 项目预算审批
    	TPmProjectFundsApprEntity appr = commonDao.get(TPmProjectFundsApprEntity.class, apprId);
    	// 查询之前是否有预算
    	List<TPmProjectFundsApprEntity> apprList = commonDao.getSession()
    			.createCriteria(TPmProjectFundsApprEntity.class)
    			.add(Restrictions.ne("id", apprId))
    			.add(Restrictions.eq("tpId", appr.getTpId()))
    			.add(Restrictions.eq("fundsCategory", "2"))
    			.addOrder(Order.desc("createDate")).list();
    	
    	if(apprList != null && apprList.size() > 0){
    		// 如果之前有预算，将最后一次的预算金额和历史金额相加作为此次的历史金额
    		initLast(appr, apprList.get(0));
            initParent(apprId);
    	}else{
    		// 如果之前没有预算，到预算类别中初始化
    		initFixed(apprId);
    		initParent(apprId);
    	}
    	
    	// 添加预算附表
    	TPmFundsBudgetAddendumEntity addendum = new TPmFundsBudgetAddendumEntity();
    	addendum.setPid(apprId);
		addendum.setAddChildFlag("1");
		addendum.setContent("合计");
		commonDao.save(addendum);
    }
    
    /**
     * 获取年度预算明细初始化页面
     * @param iProjectId 项目ID
     * @return 年度预算明细列表
     */
    public List<Map<String, Object>> initYearFundsDetailInfo(String iProjectId) {
    	// 查询之前是否有做年度预算
    	List<TPmProjectFundsApprEntity> apprList = commonDao.getSession()
    			.createCriteria(TPmProjectFundsApprEntity.class)
    			.add(Restrictions.eq("fundsCategory", "2"))
    			.add(Restrictions.eq("tpId", iProjectId))
    			.addOrder(Order.desc("createDate")).list();
    	List<Map<String, Object>> fundsList = new ArrayList<Map<String, Object>>();
    	if(apprList != null && apprList.size() > 0){
    		fundsList = getLastFundsDetailList(apprList.get(0));
    	}else{
    		fundsList = getFixedFundsDetailList(iProjectId);
    	}
    	// 将数据分级保存
        Map<Integer, Object> levelMap = new HashMap<Integer, Object>();
        for (int i = 0; i < fundsList.size(); i++) {
            Map<String, Object> map = fundsList.get(i);
            int level = Integer.parseInt(map.get("LEVEL").toString());
            List<Map<String, Object>> temp = (levelMap.get(level) == null ? new ArrayList<Map<String, Object>>()
                    : (List<Map<String, Object>>) levelMap.get(level));
            temp.add(map);
            levelMap.put(level, temp);
        }

        // 获得树的层数
        int maxLevel = 0;
        if (fundsList.size() > 0) {
            maxLevel = Integer.parseInt(fundsList.get(fundsList.size() - 1).get("LEVEL").toString());
        }
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
        if (levelMap.size() > 0) {
            result.addAll((Collection<? extends Map<String, Object>>) levelMap.get(1));
        }
        return result;
    }
    
    /**
     * 初始化预算类型表中的预算
     * @param apprId
     */
    public void initFixed(String apprId) {
        TPmProjectFundsApprEntity projectFunds = this.commonDao.get(TPmProjectFundsApprEntity.class, apprId);//查询预算审批主表记录
        String projectId = projectFunds.getTpId();
        String feeTypeId = "";
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
            TBFundsPropertyEntity feeType = project.getFeeType();
            feeTypeId = feeType.getId();//获取经费类型id
        }
    	// 预算类别表
        List<TBApprovalBudgetRelaEntity> srcs = commonDao.getSession().createCriteria(
        		TBApprovalBudgetRelaEntity.class)
                .add(Restrictions.eq("projApproval", feeTypeId))
        		.addOrder(Order.asc("sortCode")).list();
        // 预算表
        List<TPmContractFundsEntity> dests = new ArrayList<TPmContractFundsEntity>();
        
        // 合计字段
        TPmContractFundsEntity sum = new TPmContractFundsEntity();
        sum.setContent("合计");
        sum.setAddChildFlag("0");
        sum.setTpId(apprId);
        commonDao.save(sum);
        
        for (int i = 0; i < srcs.size(); i++) {
            TBApprovalBudgetRelaEntity src = srcs.get(i);
            TPmContractFundsEntity dest = new TPmContractFundsEntity();
            dest.setContentId(src.getId());
            dest.setContent(src.getBudgetNae());
            dest.setAddChildFlag(src.getAddChildFlag());
            if(src.getTBApprovalBudgetRelaEntity() == null){
            	dest.setParent(sum.getId());
            }
            dest.setNum(src.getSortCode());
            dest.setTpId(apprId);

            dests.add(dest);
        }
        // 批量保存
        commonDao.batchSave(dests);
    }
    
    /**
     * 根据经费类型ID获取模板配置
     * @param projectId 项目ID
     * @return 预算明细
     */
    private List<Map<String, Object>> getFixedFundsDetailList(String projectId){
    	 TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
         TBFundsPropertyEntity feeType = project.getFeeType();
         String feeTypeId = feeType.getId();//获取经费类型id
         List<Map<String, Object>> funds = new ArrayList<Map<String, Object>>();
         String sql = "SELECT ID, LEVEL, PARENT_ID as PARENT, SORT_CODE as NUM, BUDGET_NAE as CONTENT," + 
     			"        0 AS HISTORYMONEY, ADD_CHILD_FLAG AS ADDCHILDFLAG " + 
     			"        FROM t_b_approval_budget_rela" + 
     			"        WHERE PROJ_APPROVAL = ? " + 
     			"        START WITH PARENT_ID IS NULL CONNECT BY PRIOR ID = PARENT_ID " + 
     			"        ORDER BY LEVEL, PARENT_ID DESC, SORT_CODE";
     	List<Map<String, Object>> otherFunds = commonDao.findForJdbc(sql, feeTypeId);
         // 合计字段
         Map<String, Object> sum = new HashMap<String, Object>();
         sum.put("CONTENT", "合计");
         sum.put("ADDCHILDFLAG", "0");
         sum.put("LEVEL", "1");
         sum.put("ID", "0");
         funds.add(sum);
         funds.addAll(otherFunds);
         return funds;
    }
    
    /**
     * 预算明细
     * @param lastAppr 最新一次上报
     * @return 预算明细
     */
    private List<Map<String, Object>> getLastFundsDetailList(TPmProjectFundsApprEntity lastAppr) {
    	
    	String sql = "SELECT ID, LEVEL, PARENT, NUM, CONTENT," + 
    			"        HISTORY_MONEY AS HISTORYMONEY, MONEY, ADD_CHILD_FLAG  AS ADDCHILDFLAG, REMARK ,UNIT,PRICE,AMOUNT " + 
    			"        FROM T_PM_CONTRACT_FUNDS" + 
    			"        WHERE T_P_ID = ? " + 
    			"        START WITH PARENT IS NULL CONNECT BY PRIOR ID = PARENT " + 
    			"        ORDER BY LEVEL, PARENT DESC, NUM";
    	
    	List<Map<String, Object>> funds = commonDao.findForJdbc(sql, lastAppr.getId());
    	return funds;
    }
    
    
    
    /**
     * 从最近的一次预算中初始化此次预算
     * @param appr
     * @param lastAppr
     */
    public void initLast(TPmProjectFundsApprEntity appr, TPmProjectFundsApprEntity lastAppr){
    	// 获得最近一次预算的详细信息
    	List<TPmContractFundsEntity> lastFunds = commonDao.getSession()
    			.createCriteria(TPmContractFundsEntity.class)
    			.add(Restrictions.eq("apprId", lastAppr.getId()))
    			.addOrder(Order.desc("num")).list();
    	// 本次预算的容器
    	List<TPmContractFundsEntity> funds = new ArrayList<TPmContractFundsEntity>();
    	
    	String parentId = null;
    	// 遍历上次预算信息，生成本次预算信息
    	for(int i = 0; i < lastFunds.size(); i++){
    		TPmContractFundsEntity lastFund = lastFunds.get(i);
    		TPmContractFundsEntity fund = new TPmContractFundsEntity();
    		if(StringUtil.isEmpty(lastFund.getNum())) {//编号为空，表示当前为合计
    			fund.setMoney(appr.getTotalFunds().doubleValue());
    		}
    		fund.setTpId(appr.getId());
    		fund.setContentId(lastFund.getContentId());
    		fund.setContent(lastFund.getContent());
    		fund.setNum(lastFund.getNum());
            fund.setHistoryMoney((lastFund.getHistoryMoney() == null ? 0 : lastFund.getHistoryMoney())
                        + (lastFund.getMoney() == null ? 0 : lastFund.getMoney()));
    		fund.setRemark(lastFund.getRemark());
    		fund.setAddChildFlag(lastFund.getAddChildFlag());
    		
    		if(i == 0){
    			commonDao.save(fund);
    			parentId = fund.getId();
    		}else{
    			if(fund.getNum().length() == 2){
    				fund.setParent(parentId);
    			}
    			funds.add(fund);
    		}
    		
    	}
    	commonDao.batchSave(funds);
    }
    
    //根据编码设置各个明细预算的父节点
    public void initParent(String apprId) {
        String sql = "UPDATE T_PM_CONTRACT_FUNDS T1 SET PARENT = ( "
        		+ " SELECT ID FROM T_PM_CONTRACT_FUNDS T2 "
        		+ " WHERE T2.T_P_ID = :apprId AND SUBSTR(T1.NUM, 0, length(T1.NUM) - 2) = t2.num) "
        		+ " WHERE T1.PARENT IS NULL AND T1.T_P_ID = :apprId AND LENGTH(T1.NUM) > 2";
        commonDao.getSession().createSQLQuery(sql)
        	.setParameter("apprId", apprId).executeUpdate();
    }

    @Override
    public void updateParentsMoney(String parent, double oldMoney, double newMoney) {
        if (parent != null) {
            TPmContractFundsEntity funds = commonDao.get(TPmContractFundsEntity.class, parent);
            funds.setMoney((funds.getMoney() == null ? 0 : funds.getMoney()) - oldMoney + newMoney);
            commonDao.updateEntitie(funds);

            updateParentsMoney(funds.getParent(), oldMoney, newMoney);
        }
    }

    @Override
    public List<Map<String, Object>> getTreeBySql(String sql, String projectId,String apprId) {
        List<Map<String, Object>> list = commonDao.getSession().createSQLQuery(sql)
        		.setParameter("apprId", projectId)
        		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        
        Map<String, String> totalSumMap = getTotalFundsList(apprId);
        int len = list == null ? 0 : list.size();
        for (int i = 0; i < len; i++) {
        	Map<String, Object> oneDetail = list.get(i);
			String NUM = oneDetail.get("NUM") == null ? "" : oneDetail.get("NUM").toString();
			String totalNum = totalSumMap.get(NUM);
			if(totalNum == null) {
				totalNum = "0";
			}
			if(NUM.length() > 4) {
				totalNum = "--";
			}
			oneDetail.put("TOTALMONEY", totalNum);
		}

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
        int maxLevel = 0;
        if (list.size() > 0) {
            maxLevel = Integer.parseInt(list.get(list.size() - 1).get("LEVEL").toString());
        }
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
        if (levelMap.size() > 0) {
            result.addAll((Collection<? extends Map<String, Object>>) levelMap.get(1));
        }
        return result;
    }
    
    @Override
    public List<Map<String, Object>> queryPlanTree(String apprId){
    	List treeList = new ArrayList();
    	if(!StringUtil.isEmpty(apprId)){
    		//查询预算类型
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select k.budget_category from t_pm_project t ");
    		sql.append(" left join T_PM_BUDGET_FUNDS_REL k on t.fee_type = k.funds_type ");
    		sql.append(" where t.id= ");
    		sql.append(" (select h.t_p_id from t_pm_project_funds_appr h where h.id= ? ) ");
    		List<Map<String, Object>> typeList = this.findForJdbc(sql.toString(), apprId);
    		if(typeList != null && typeList.size() > 0){
    			Object budgetType = typeList.get(0).get("budget_category");
    			String sql2 = " select 1 as \"level\",t.category_code,t.category_name as CONTENT from T_PM_BUDGET_CATEGORY t where t.budget_category= ? ";
    			List<Map<String, Object>> oneList = this.findForJdbc(sql2, budgetType);
    			if(oneList != null && oneList.size() > 0){
    				for(int i=0;i<oneList.size();i++){
    					Map oneMap = oneList.get(i);
    					Object code = oneMap.get("category_code");
    					String sql3 = " select 2 as \"level\",t.category_code_dtl,t.detail_name as CONTENT,K.MONEY "
    							+ " from T_PM_BUDGET_CATEGORY_attr t  left join t_pm_contract_funds k "
    							+ " on t.id = k.content_id where t.category_code=? ";
    					List<Map<String, Object>> twoList = this.findForJdbc(sql3, code);
    					oneMap.put("children", twoList);
    				}
    				treeList.addAll(oneList);
    			}
    			
    		}
    	}

		return treeList;

    }
    @Override
    //年度预算树加载修改   modified by xiaozhongliang  2018-08-22
    public List<Map<String, Object>> querycontractTree2(String apprId,String projectId){
    	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
    	String sql = "select A.ID as ID, " +
    			" A.CONTENT_ID," + 
    			"A.NUM," +
    			"A.PARENT," +
    			"A.CONTENT," +
    			"A.MONEY as \"money\"," +
    			"A.HISTORY_MONEY as \"historyMoney\"," +
    			"A.VARIABLE_MONEY as \"variableMoney\"," + 
    			"nvl(A.ADD_CHILD_FLAG,B.DETAIL_SYMBOL) as ADDCHILDFLAG, " +
    			"A.REMARK " + 
    			"from T_PM_CONTRACT_FUNDS A left join T_PM_BUDGET_CATEGORY_ATTR B on A.CONTENT_ID=B.ID " +
    			"where A.T_P_ID = ? and T_APPR_ID=? " +
    			"order by NUM ";
    	String sql2 = "select ALL_FEE,YEAR_FUNDS_PLAN,TOTAL_FUNDS,REIN_FUNDS_PLAN,REIN_FUNDS_PLAN,ACCOUNT_FUNDS,FUNDS_CATEGORY " + 
    				"from t_pm_project_funds_appr where T_P_ID=? and FUNDS_CATEGORY='2' and CW_STATUS is null ";
    	
    	List<Map<String, Object>> listData = this.findForJdbc(sql,  projectId,apprId);
    	Map<String,Object> map = this.findOneForJdbc(sql2, projectId);
    	
    	//根节点
    	for(int i = 0 ; i <  listData.size() ; i++){
    		Map<String, Object> data = listData.get(i);
    		if(StringUtil.isEmpty(MapUtils.getString(data, "PARENT",""))){
    			data.put("level", "1");
    			data.put("ADDCHILDFLAG", "0");
    			data.put("CONTENT", "合计");
    			double totalFunds = MapUtils.getDoubleValue(data, "variableMoney",0);
    			if(map != null && map.size() > 0 ){
    				totalFunds += MapUtils.getDoubleValue(map, "TOTAL_FUNDS");
    				data.put("variableMoney", totalFunds);
    			}
    			treeList.add(data);
    			listData.remove(i);
    		}
    		
    	}
    	addChild(treeList,listData,1);
    	return treeList;
    }
    private void addChild(List<Map<String, Object>> treeList,List<Map<String, Object>> list,int num){
    	num++;
    	for(int i = 0 ; i < treeList.size() ;i++){
    		Map<String, Object> treeData = treeList.get(i);
    		String Id = MapUtils.getString(treeData, "CONTENT_ID");
        	List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
    		treeData.put("children",childList);	
        	List<Map<String, Object>> copyList = new ArrayList<Map<String, Object>>();
        	copyList.addAll(list);
        	for(int t = 0 ; t <  list.size() ; t++){
        		Map<String, Object> data = list.get(t);
        		String parent = MapUtils.getString(data, "PARENT");
        		if(parent.equals(Id)){
        			data.put("level", num);
        			childList.add(data);
        			copyList.remove(data);
        		}
        	}
    		addChild(childList, copyList,num);
    	}
    	
    }
    
    @Override
    public List<Map<String, Object>> querycontractTree(String apprId){
    	List treeList = new ArrayList();
    	if(!StringUtil.isEmpty(apprId)){
    		//查询预算类型
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select t.id,k.budget_category from t_pm_project t ");
    		sql.append(" left join T_PM_BUDGET_FUNDS_REL k on t.fee_type = k.funds_type ");
    		sql.append(" where t.id= ");
    		sql.append(" (select h.t_p_id from t_pm_project_funds_appr h where h.id= ? ) ");
    		List<Map<String, Object>> typeList = this.findForJdbc(sql.toString(), apprId);
    		if(typeList != null && typeList.size() > 0){
    			Object projectId = typeList.get(0).get("id");
    			Object budgetType = typeList.get(0).get("budget_category");
    			String sql2 = " select t.id,1 as \"level\",t.category_code as num,t.category_name as CONTENT,0 as MONEY,K.MONEY as HISTORYMONEY,0 as ADDCHILDFLAG, K.MONEY as TOTALMONEY "
    					+ "from T_PM_BUDGET_CATEGORY t left join t_pm_contract_funds k "
    					+ " on t.id = k.content_id where t.category_code= ? and k.t_p_id = ? ";
    			List<Map<String, Object>> oneList = this.findForJdbc(sql2, budgetType, projectId);
    			if(oneList != null && oneList.size() > 0){
    				for(int i=0;i<oneList.size();i++){
    					Map oneMap = oneList.get(i);
    					Object code = oneMap.get("num");
    					String sql3 = " select t.id,2 as \"level\",t.category_code_dtl as num,k.content_id,t.detail_name as CONTENT,K.MONEY,K.MONEY as HISTORYMONEY,1 as ADDCHILDFLAG, K.MONEY as TOTALMONEY,K.REMARK "
    							+ " from T_PM_BUDGET_CATEGORY_attr t  left join t_pm_contract_funds k "
    							+ " on t.id = k.content_id where t.category_code=? and k.t_p_id = ? and k.parent is null ";
    					List<Map<String, Object>> twoList = this.findForJdbc(sql3, code, projectId);
    					
    					for(int j=0;j<twoList.size();j++){
    						Map twoMap = twoList.get(j);
    						Object contentId = twoMap.get("content_id");
    						String sql4 = " select t.id,3 as \"level\",t.num,t.content,t.money,3 as ADDCHILDFLAG,t.REMARK from t_pm_contract_funds t where t.parent='"+contentId+"' and t.t_p_id='"+projectId+"' ";
        					List<Map<String, Object>> threeList = this.findForJdbc(sql4);
        					
        					//统计年度预算的金额
        					double countNum = 0;
        					for(int k=0;k<threeList.size();k++){
        						double num = MapUtils.getDoubleValue(threeList.get(k), "money",0.0);
        						countNum += num;
        					}
        					twoMap.put("MONEY", countNum);
        					twoMap.put("children", threeList);
    					}
    					
    					
    					oneMap.put("children", twoList);
    				}
    				treeList.addAll(oneList);
    			}
    			
    		}
    	}

		return treeList;

    }
    
    
    
    private Map<String, String> getTotalFundsList(String apprId){
    	Map<String, String> totalSumMap = new HashMap<String, String>();
    	TPmProjectFundsApprEntity apprEntity = this.get(TPmProjectFundsApprEntity.class, apprId);
    	String projectId = apprEntity.getTpId();
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum(a.MONEY) as \"totalMoney\", a.NUM as \"num\"");
    	sql.append(" from T_PM_CONTRACT_FUNDS a, T_PM_PROJECT_FUNDS_APPR b where a.T_P_ID = b.ID and b.FUNDS_CATEGORY = 1");
    	sql.append(" and b.FINISH_FLAG in(0,1,2)");
    	sql.append(" and (b.CW_STATUS in(0, 1) or b.CW_STATUS is null)");
    	sql.append(" and b.T_P_ID = ? group by a.NUM");
    	List<Map<String, Object>> sumList = this.findForJdbc(sql.toString(), projectId);
    	int len = sumList.size();
    	for (int i = 0; i < len; i++) {
    		Map<String, Object> oneSum = sumList.get(i);
    		String totalMoney = oneSum.get("totalMoney") == null ? "0" : oneSum.get("totalMoney").toString();
    		String num = oneSum.get("num") == null ? "" : oneSum.get("num").toString();
    		totalSumMap.put(num, totalMoney);
		}
    	sql.delete(0, sql.length());
    	sql.append("select sum(b.TOTAL_FUNDS) as \"totalMoney\" from T_PM_PROJECT_FUNDS_APPR b");
    	sql.append(" where b.FUNDS_CATEGORY = 1");
    	sql.append(" and b.FINISH_FLAG in(0,1,2)");
    	sql.append(" and (b.CW_STATUS in(0, 1) or b.CW_STATUS is null)");
    	sql.append(" and b.T_P_ID = ?");
    	Map<String, Object> totlSumList = this.findOneForJdbc(sql.toString(), projectId);
    	String totalMoney = totlSumList.get("totalMoney") == null ? "" : totlSumList.get("totalMoney").toString();
    	totalSumMap.put("", totalMoney);
    	return totalSumMap;
    }

   /* @Override
    public void batSaveChange(TPmContractFundsEntity[] contracts, TPmPlanFundsEntity[] plans) {
        if (contracts != null && contracts.length > 0) {
            for (int i = 0; i < contracts.length; i++) {
                if (contracts[i] != null && StringUtil.isNotEmpty(contracts[i].getId())) {
                    commonDao.updateEntitie(contracts[i]);
                }
            }
        }

        if (plans != null && plans.length > 0) {
            for (int i = 0; i < plans.length; i++) {
                if (plans[i] != null && StringUtil.isNotEmpty(plans[i].getId())) {
                    commonDao.updateEntitie(plans[i]);
                }
            }
        }
    }*/



	@Override
	public void update(TPmContractFundsEntity tPmContractFunds) throws Exception {
		
		TPmContractFundsEntity contractFunds = commonDao.get(TPmContractFundsEntity.class, tPmContractFunds.getId());
		double histMoney = (tPmContractFunds.getHistoryMoney() != null ? tPmContractFunds.getHistoryMoney() : 0) - 
				(contractFunds.getHistoryMoney() != null ? contractFunds.getHistoryMoney() : 0);
		contractFunds.setMoney(tPmContractFunds.getMoney());
		contractFunds.setHistoryMoney(tPmContractFunds.getHistoryMoney());
		contractFunds.setVariableMoney(tPmContractFunds.getVariableMoney());
		contractFunds.setContent(tPmContractFunds.getContent());
		contractFunds.setRemark(tPmContractFunds.getRemark());
//		contractFunds.setAddChildFlag(tPmContractFunds.getAddChildFlag());
		//更新字节点
		this.saveOrUpdate(contractFunds);
		
		/*if(Integer.parseInt(tPmContractFunds.getAddChildFlag()) < 1 && !"10".equals(contractFunds.getNum())
				&& !"1001".equals(contractFunds.getNum())
				&& !"1002".equals(contractFunds.getNum())
				&& !"1003".equals(contractFunds.getNum())
				&& !"1004".equals(contractFunds.getNum())){
			
			String tPid = contractFunds.getTpId();
			String content = contractFunds.getParent();
			//根节点
			String sql = "select ID,MONEY,VARIABLE_MONEY,HISTORY_MONEY from T_PM_CONTRACT_FUNDS where T_P_ID=? and CONTENT_ID=? and PARENT is null ";
			Map<String,Object> tailMap = this.findOneForJdbc(sql, tPid,content);
			//更新跟节点
			String sqlUp = "update T_PM_CONTRACT_FUNDS set VARIABLE_MONEY=?,HISTORY_MONEY=? where T_P_ID=? and CONTENT_ID=? and PARENT is null";
			
			double tailVarMoney = MapUtils.getDoubleValue(tailMap, "VARIABLE_MONEY") - histMoney;
			double historyMoney = MapUtils.getDoubleValue(tailMap, "HISTORY_MONEY") + histMoney;
			//this.executeSql(sqlUp, tailVarMoney,historyMoney,tPid,content);
		}*/
		
		
        
        
	}
  /**
   * 根据录入的原材料查询到对应的项目信息
   * @return
   */
  public TPmProjectEntity getProject(TPmContractFundsEntity funds){
    //取得对应的预算审批表
    TPmProjectFundsApprEntity appr = commonDao.get(TPmProjectFundsApprEntity.class, funds.getTpId());
    //取得对应的项目信息
    TPmProjectEntity proj = commonDao.get(TPmProjectEntity.class, appr.getTpId());
    return proj;
  }
	@Override
	public void del(String id) {
		TPmContractFundsEntity funds = commonDao.getEntity(TPmContractFundsEntity.class, id);
        commonDao.delete(funds);
        
        // 更新其祖辈节点
        if(funds.getVariableMoney() != null && funds.getVariableMoney() != 0){
        	updateParentsMoney(funds);
        }
	}
	private void updateParentsMoney(TPmContractFundsEntity tPmContractFunds){
		String parent = tPmContractFunds.getParent();
		String tpId = tPmContractFunds.getTpId();
		String tApprId = tPmContractFunds.gettApprId();
		String sql = "select ID from t_pm_contract_funds where T_P_ID=? and CONTENT_ID=? and T_APPR_ID=?";
		Map<String,Object> data = this.findOneForJdbc(sql, tpId,parent,tApprId);
		TPmContractFundsEntity parentFunds = commonDao.getEntity(TPmContractFundsEntity.class, MapUtils.getString(data, "ID"));
			
		double variableMoney = parentFunds.getVariableMoney() + tPmContractFunds.getVariableMoney();
    	double historyMoney = parentFunds.getHistoryMoney() - tPmContractFunds.getVariableMoney();
    	parentFunds.setVariableMoney(variableMoney);
    	parentFunds.setHistoryMoney(historyMoney);
		commonDao.updateEntitie(parentFunds);
	}
}