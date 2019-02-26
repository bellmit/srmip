package com.kingtake.project.service.impl.funds;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.dao.TPmProjectFundsApprDao;
import com.kingtake.project.entity.declare.army.TPmFundsBudgetEntity;
import com.kingtake.project.entity.funds.TPmContractFundsEntity;
import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.entity.funds.TPmPlanFundsEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;
import com.kingtake.project.service.funds.TPmFundsBudgetAddendumServiceI;
import com.kingtake.project.service.funds.TPmPlanFundsServiceI;

@Service("tPmPlanFundsService")
@Transactional
public class TPmPlanFundsServiceImpl extends CommonServiceImpl implements TPmPlanFundsServiceI {

    private static final Log logger = LogFactory.getLog(TPmPlanFundsServiceImpl.class);
    @Autowired
    private TPmContractFundsServiceI tPmContractFundsService;

    @Autowired
    private TPmFundsBudgetAddendumServiceI tPmFundsBudgetAddendumService;

    @Autowired
    private TPmProjectFundsApprDao tPmProjectFundsApprDao;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmPlanFundsEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmPlanFundsEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmPlanFundsEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmPlanFundsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmPlanFundsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmPlanFundsEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmPlanFundsEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getApprId()));
        sql = sql.replace("#{num}", String.valueOf(t.getNum()));
        sql = sql.replace("#{equipment_name}", String.valueOf(t.getContent()));
        sql = sql.replace("#{model}", String.valueOf(t.getModel()));
        sql = sql.replace("#{unit}", String.valueOf(t.getUnit()));
        sql = sql.replace("#{amount}", String.valueOf(t.getAmount()));
        sql = sql.replace("#{price}", String.valueOf(t.getPrice()));
        sql = sql.replace("#{money}", String.valueOf(t.getMoney()));
        sql = sql.replace("#{equipmentId}", String.valueOf(t.getContentId()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void init(String apprId) {
    	// 项目预算审批
    	TPmProjectFundsApprEntity appr = commonDao.get(TPmProjectFundsApprEntity.class, apprId);
    	// 查询之前是否有预算
    	List<TPmProjectFundsApprEntity> apprList = commonDao.getSession()
    			.createCriteria(TPmProjectFundsApprEntity.class)
    			.add(Restrictions.ne("id", apprId))
    			.add(Restrictions.eq("tpId", appr.getTpId()))
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
     * 初始化预算类型表中的预算
     * @param apprId
     */
    private void initFixed(String apprId) {
        TPmProjectFundsApprEntity projectFunds = this.commonDao.get(TPmProjectFundsApprEntity.class, apprId);//查询预算审批主表记录
        String projectId = projectFunds.getTpId();
        String feeTypeId = "";
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
            TBFundsPropertyEntity feeType = project.getFeeType();
            feeTypeId = feeType.getId();//获取预算类型id
        }
    	// 预算类别表
        List<TBApprovalBudgetRelaEntity> srcs = commonDao.getSession().createCriteria(
        		TBApprovalBudgetRelaEntity.class)
                .add(Restrictions.eq("projApproval", feeTypeId))
        		.addOrder(Order.asc("sortCode")).list();
        // 预算表
        List<TPmPlanFundsEntity> dests = new ArrayList<TPmPlanFundsEntity>();
        
        // 合计字段
        TPmPlanFundsEntity sum = new TPmPlanFundsEntity();
        sum.setContent("合计");
        sum.setAddChildFlag("0");
        sum.setApprId(apprId);
        commonDao.save(sum);
        
        for (int i = 0; i < srcs.size(); i++) {
            TBApprovalBudgetRelaEntity src = srcs.get(i);
            TPmPlanFundsEntity dest = new TPmPlanFundsEntity();
            dest.setContentId(src.getId());
            dest.setContent(src.getBudgetNae());
            dest.setAddChildFlag(src.getAddChildFlag());
            if(src.getTBApprovalBudgetRelaEntity() == null){
            	dest.setParent(sum.getId());
            }
            dest.setNum(src.getSortCode());
            dest.setApprId(apprId);

            dests.add(dest);
        }
        // 批量保存
        commonDao.batchSave(dests);
    }
    
    /**
     * 从最近的一次预算中初始化此次预算
     * @param appr
     * @param lastAppr
     */
    private void initLast(TPmProjectFundsApprEntity appr, TPmProjectFundsApprEntity lastAppr){
    	// 获得最近一次预算的详细信息
    	List<TPmPlanFundsEntity> lastFunds = commonDao.getSession()
    			.createCriteria(TPmPlanFundsEntity.class)
    			.add(Restrictions.eq("apprId", lastAppr.getId()))
    			.addOrder(Order.desc("num")).list();
    	// 本次预算的容器
    	List<TPmPlanFundsEntity> funds = new ArrayList<TPmPlanFundsEntity>();
    	
    	String parentId = null;
    	// 遍历上次预算信息，生成本次预算信息
    	for(int i = 0; i < lastFunds.size(); i++){
    		TPmPlanFundsEntity lastFund = lastFunds.get(i);
    		TPmPlanFundsEntity fund = new TPmPlanFundsEntity();
    		
    		fund.setApprId(appr.getId());
    		fund.setContentId(lastFund.getContentId());
    		fund.setContent(lastFund.getContent());
    		fund.setNum(lastFund.getNum());
            if (SrmipConstants.FUNDS_TYPE_PART.equals(appr.getFundsType())
                    && SrmipConstants.FUNDS_TYPE_PART.equals(lastAppr.getFundsType())) {
                fund.setHistoryMoney((lastFund.getHistoryMoney() == null ? 0 : lastFund.getHistoryMoney())
                        + (lastFund.getMoney() == null ? 0 : lastFund.getMoney()));
            }
    		fund.setModel(lastFund.getModel());
    		fund.setUnit(lastFund.getUnit());
    		fund.setPrice(lastFund.getPrice());
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
    	
    	/*// 初始化固定预算
    	initFixed(apprId);
    	// 初始化固定预算的历史金额（只计算完成审核的预算）
        StringBuffer sql = new StringBuffer(ProjectConstant.FUNDS_SQL.get("planFixedHistory"));
        commonDao.getSession().createSQLQuery(sql.toString())
        		.setParameter("projectId", projectId)
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH)
                .setParameter("apprId", apprId)
                .executeUpdate();
        
        // 初始化可变预算预算，并计算历史金额（只计算完成审核的预算）
        sql.setLength(0);
        sql.append(ProjectConstant.FUNDS_SQL.get("planVariableHistory"));
        List<TPmPlanFundsEntity> noContentList = commonDao.getSession().createSQLQuery(sql.toString())
                .addScalar("historyMoney", StandardBasicTypes.DOUBLE)
                .addScalar("content", StandardBasicTypes.STRING)
                .addScalar("num", StandardBasicTypes.STRING)
                .addScalar("apprId", StandardBasicTypes.STRING)
                .addScalar("addChildFlag", StandardBasicTypes.STRING)
                .setParameter("projectId", projectId)
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH)
                .setParameter("apprId", apprId)
                .setResultTransformer(Transformers.aliasToBean(TPmPlanFundsEntity.class)).list();
        commonDao.batchSave(noContentList);*/
    }

    @Override
    public void initParent(String apprId) {
    	String sql = "UPDATE T_PM_PLAN_FUNDS T1 SET PARENT = ( "
        		+ " SELECT ID FROM T_PM_PLAN_FUNDS T2 "
        		+ " WHERE T2.T_P_ID = :apprId AND SUBSTR(T1.NUM, 0, length(T1.NUM) - 2) = t2.num) "
        		+ " WHERE T1.PARENT IS NULL AND T1.T_P_ID = :apprId AND LENGTH(T1.NUM) > 2";
        commonDao.getSession().createSQLQuery(sql)
        	.setParameter("apprId", apprId).executeUpdate();
    }

    @Override
    public void updateParentsMoney(String parent, double oldMoney, double newMoney) {
        if (parent != null) {
            TPmPlanFundsEntity funds = commonDao.get(TPmPlanFundsEntity.class, parent);
            funds.setMoney((funds.getMoney() == null ? 0 : funds.getMoney()) - oldMoney + newMoney);
            commonDao.updateEntitie(funds);

            updateParentsMoney(funds.getParent(), oldMoney, newMoney);
        }
    }

    @Override
    public void update(TPmPlanFundsEntity tPmPlanFundsOne) throws Exception{
    	TPmPlanFundsEntity t = commonDao.get(TPmPlanFundsEntity.class, tPmPlanFundsOne.getId());
    	double oldMoney = t.getMoney() == null ? 0 : t.getMoney();
    	double newMoney = tPmPlanFundsOne.getMoney() == null ? 0 : tPmPlanFundsOne.getMoney();
	    //更新其祖辈节点
	    if(t.getMoney() != tPmPlanFundsOne.getMoney()){
	        updateParentsMoney(t.getParent(), oldMoney, newMoney);
	    }
		MyBeanUtils.copyBeanNotNull2Bean(tPmPlanFundsOne, t);
		
		commonDao.saveOrUpdate(t);
    }
    
    @Override
    public String updateAndReturn(TPmPlanFundsEntity tPmPlanFundsOne) throws Exception{
    	TPmPlanFundsEntity t = commonDao.get(TPmPlanFundsEntity.class, tPmPlanFundsOne.getId());
    	double oldMoney = t.getMoney() == null ? 0 : t.getMoney();
    	double newMoney = tPmPlanFundsOne.getMoney() == null ? 0 : tPmPlanFundsOne.getMoney();
	    //更新其祖辈节点
	    if(t.getMoney() != tPmPlanFundsOne.getMoney()){
	        updateParentsMoney(t.getParent(), oldMoney, newMoney);
	    }
	    
	    //返回字符串
	    String result;
	    
	    //总预算金额
	    TPmProjectFundsApprEntity projectFundsApprEntity = this.commonDao.findUniqueByProperty(TPmProjectFundsApprEntity.class, "id", t.getApprId());
	    String sql = "select money from T_PM_PLAN_FUNDS where t_p_id = '" + t.getApprId() + "' and parent is null";
	    List<Map<String, Object>> totalMoenyMap = this.commonDao.findForJdbc(sql);
	    BigDecimal money = new BigDecimal(totalMoenyMap.get(0).get("money").toString());
	    if(money.compareTo(projectFundsApprEntity.getTotalFunds()) == 1){
	    	result = "预算明合计大于预算金额，保存失败！";
	    }else{
	    	MyBeanUtils.copyBeanNotNull2Bean(tPmPlanFundsOne, t);
			
			commonDao.saveOrUpdate(t);
			result = "保存成功！";
	    }
		
	    return result;
    }
    
    /**
     * 根据录入的原材料查询到对应的项目信息
     * @return
     */
    public TPmProjectEntity getProject(TPmPlanFundsEntity funds){
      //取得对应的预算审批表
      TPmProjectFundsApprEntity appr = commonDao.get(TPmProjectFundsApprEntity.class, funds.getApprId());
      //取得对应的项目信息
      TPmProjectEntity proj = commonDao.get(TPmProjectEntity.class, appr.getTpId());
      return proj;
    }
    
    @Override
	public void del(String id) {
		TPmPlanFundsEntity tPmPlanFunds = commonDao.getEntity(TPmPlanFundsEntity.class, id);
	    // 更新其祖辈节点
        if(tPmPlanFunds.getMoney() != null && tPmPlanFunds.getMoney() != 0){
        	updateParentsMoney(tPmPlanFunds.getParent(), tPmPlanFunds.getMoney(), 0);
        }
        commonDao.delete(tPmPlanFunds);
	}

    /**
     * 导入excel
     * 
     * @param workbook
     *            excel对象
     * @param tpId
     *            预算主表主键
     * @param showType
     *            类型 合同类还是计划类
     */
    @Override
    public void importExcel(HSSFWorkbook workbook, String tpId, String showType) {
        if (ProjectConstant.PROJECT_PLAN.equals(showType)) {
            importPlanFunds(workbook, tpId, showType);//导入计划类预算主表
        } else {
            importContractFunds(workbook, tpId, showType);//导入合同类预算主表
        }
        importFundsAddum(workbook, tpId, showType);//导入附表
    }

    /**
     * 导入计划类预算表
     * 
     * @param workbook
     * @param tpId
     * @param showType
     */
    private void importPlanFunds(HSSFWorkbook workbook, String tpId, String showType) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        int rownum = sheet.getLastRowNum();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            HSSFCell cell0 = row.getCell(0);
            if (cell0 == null) {
                continue;
            }
            String value = StringUtils.trim(cell0.getStringCellValue());
            if ("".equals(value) || "此处可添加子项".equals(value)) {
                continue;
            }
            String equipmentName = getCellVal(row, 0);
            String num = getCellVal(row, 1);
            if (num.length() % 2 != 0) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，序号不合法，请检查");
            }
            if (StringUtils.isEmpty(equipmentName) || StringUtils.isEmpty(num)) {//如果项目名称，则过滤掉
                continue;
            }
            String historyMoney = getCellVal(row, 2);
            String model = getCellVal(row, 3);
            String unit = getCellVal(row, 4);
            String amount = getCellVal(row, 5);
            if (!checkNumber(amount)) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，数量列的值不合法，请检查");
            }

            String price = getCellVal(row, 6);
            if (!checkNumber(price)) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，单价列的值不合法，请检查");
            }
            String money = getCellVal(row, 7);
            if (StringUtils.isEmpty(money)) {
                money = "0.00";
            }
            if (!checkNumber(money)) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，金额列的值不合法，请检查");
            }
            String remark = getCellVal(row, 8);
            TPmPlanFundsEntity queryEntity = getPlanFund(num, tpId);
            if (queryEntity == null) {
                queryEntity = new TPmPlanFundsEntity();
                queryEntity.setContent(equipmentName);
                queryEntity.setNum(num);
                queryEntity.setHistoryMoney(0.00);
                queryEntity.setModel(model);
                queryEntity.setUnit(unit);
                queryEntity.setAmount(StringUtils.isEmpty(amount) ? 0 : Double.valueOf(amount).intValue());
                queryEntity.setPrice(StringUtils.isEmpty(price) ? 0.00 : Double.valueOf(price));
                queryEntity.setMoney(StringUtils.isEmpty(money) ? 0.00 : Double.valueOf(money));
                queryEntity.setRemark(remark);
                queryEntity.setApprId(tpId);
                queryEntity.setAddChildFlag("3");
                TPmPlanFundsEntity parentFund = getPlanParentFund(num, tpId);
                if (parentFund != null) {
                    queryEntity.setParent(parentFund.getId());
                    this.commonDao.save(queryEntity);
                }
            } else {
                if ("2".equals(queryEntity.getAddChildFlag()) || "3".equals(queryEntity.getAddChildFlag())) {
                queryEntity.setModel(model);
                queryEntity.setUnit(unit);
                    queryEntity.setAmount(StringUtils.isEmpty(amount) ? 0 : Double.valueOf(amount).intValue());
                queryEntity.setPrice(StringUtils.isEmpty(price) ? 0.00 : Double.valueOf(price));
                    queryEntity.setMoney(StringUtils.isEmpty(price) ? 0.00 : Double.valueOf(money));
                queryEntity.setRemark(remark);
                this.commonDao.updateEntitie(queryEntity);
                }
            }
        }
        rebuildPlanMoney(tpId);//重新计算金额
    }

    /**
     * 计划类预算重新计算金额
     * 
     * @param tpId
     */
    private void rebuildPlanMoney(String tpId) {
        CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class);
        cq.eq("apprId", tpId);
        cq.isNull("parent");
        cq.addOrder("num", SortDirection.asc);
        cq.add();
        List<TPmPlanFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TPmPlanFundsEntity entity : entities) {
            entity.setMoney(calculatePlan(entity));
            this.commonDao.updateEntitie(entity);
        }
    }

    /**
     * 检查数字类型
     * 
     * @param value
     * @return
     */
    private boolean checkNumber(String value) {
        if (StringUtils.isNotEmpty(value)) {
        String regex = "^(([0-9]|([1-9][0-9]{0,7}))((\\.[0-9]{1,2})?))$";
        return value.matches(regex);
        } else {
            return true;
        }
    }

    /**
     * 计划类预算计算金额
     * 
     * @param entity
     * @return
     */
    private Double calculatePlan(TPmPlanFundsEntity entity) {
        CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class);
        cq.eq("apprId", entity.getApprId());
        cq.eq("parent", entity.getId());
        cq.addOrder("num", SortDirection.asc);
        cq.add();
        List<TPmPlanFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        BigDecimal sum = BigDecimal.ZERO;
        if (entities.size() > 0) {
            for (TPmPlanFundsEntity sub : entities) {
                Double money = calculatePlan(sub);
                sub.setMoney(money);
                this.commonDao.updateEntitie(sub);
                sum = sum.add(new BigDecimal(money));
            }
            return sum.doubleValue();
        } else {
            return entity.getMoney() == null ? 0.00 : entity.getMoney();
        }
    }

    /**
     * 合同预算重新计算金额
     * 
     * @param tpId
     */
    private void rebuildContractMoney(String tpId) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class);
        cq.eq("apprId", tpId);
        cq.isNull("parent");
        cq.addOrder("num", SortDirection.asc);
        cq.add();
        List<TPmContractFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TPmContractFundsEntity entity : entities) {
            entity.setMoney(calculateContract(entity));
            this.commonDao.updateEntitie(entity);
        }
    }

    /**
     * 计算金额
     * 
     * @param entity
     * @return
     */
    private Double calculateContract(TPmContractFundsEntity entity) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class);
        cq.eq("apprId", entity.getTpId());
        cq.eq("parent", entity.getId());
        cq.addOrder("num", SortDirection.asc);
        cq.add();
        List<TPmContractFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        BigDecimal sum = BigDecimal.ZERO;
        if (entities.size() > 0) {
            for (TPmContractFundsEntity sub : entities) {
                Double money = calculateContract(sub);
                sub.setMoney(money);
                this.commonDao.updateEntitie(sub);
                sum = sum.add(new BigDecimal(money));
            }
            return sum.doubleValue();
        } else {
            return entity.getMoney() == null ? 0.00 : entity.getMoney();
        }
    }


    /**
     * 查询计划类预算
     * 
     * @param num
     * @param tpId
     * @return
     */
    private TPmPlanFundsEntity getPlanFund(String num, String tpId) {
        TPmPlanFundsEntity queryEnt = null;
        CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class);
        cq.eq("num", num);
        cq.eq("apprId", tpId);
        cq.add();
        List<TPmPlanFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() > 0) {
            queryEnt = entities.get(0);
        }
        return queryEnt;
    }

    /**
     * 查询计划类预算
     * 
     * @param num
     * @param tpId
     * @return
     */
    private TPmContractFundsEntity getContractFund(String num, String tpId) {
        TPmContractFundsEntity queryEnt = null;
        CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class);
        cq.eq("num", num);
        cq.eq("apprId", tpId);
        cq.add();
        List<TPmContractFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() > 0) {
            queryEnt = entities.get(0);
        }
        return queryEnt;
    }

    /**
     * 查询父节点
     * 
     * @param num
     * @param tpId
     * @return
     */
    private TPmPlanFundsEntity getPlanParentFund(String num, String tpId) {
        TPmPlanFundsEntity queryEnt = null;
        try {
        CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class);
        String subStr = num.substring(0, num.length() - 2);
        cq.eq("num", subStr);
        cq.eq("apprId", tpId);
        cq.add();
        List<TPmPlanFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() > 0) {
            queryEnt = entities.get(0);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryEnt;
    }

    /**
     * 查询父节点
     * 
     * @param num
     * @param tpId
     * @return
     */
    private TPmContractFundsEntity getContractParentFund(String num, String tpId) {
        TPmContractFundsEntity queryEnt = null;
        try {
            CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class);
            String subStr = num.substring(0, num.length() - 2);
            cq.eq("num", subStr);
            cq.eq("apprId", tpId);
            cq.add();
            List<TPmContractFundsEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
            if (entities.size() > 0) {
                queryEnt = entities.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryEnt;
    }


    /**
     * 导入合同类预算
     * 
     * @param workbook
     * @param tpId
     * @param showType
     */
    private void importContractFunds(HSSFWorkbook workbook, String tpId, String showType) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        int rownum = sheet.getLastRowNum();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            HSSFCell cell0 = row.getCell(0);
            if (cell0 == null) {
                continue;
            }
            String value = StringUtils.trim(cell0.getStringCellValue());
            if ("".equals(value) || "此处可添加子项".equals(value)) {
                continue;
            }
            String equipmentName = getCellVal(row, 0);
            String num = getCellVal(row, 1);
            if (num.length() % 2 != 0) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，序号不合法，请检查");
            }
            if (StringUtils.isEmpty(equipmentName) || StringUtils.isEmpty(num)) {//如果项目名称，则过滤掉
                continue;
            }
            String historyMoney = getCellVal(row, 2);
            String money = getCellVal(row, 3);
            if (StringUtils.isEmpty(money)) {
                money = "0.00";
            }
            if (!checkNumber(money)) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，金额列的值不合法，请检查");
            }
            String remark = getCellVal(row, 4);
            TPmContractFundsEntity queryEntity = getContractFund(num, tpId);
            if (queryEntity == null) {
                queryEntity = new TPmContractFundsEntity();
                queryEntity.setContent(equipmentName);
                queryEntity.setNum(num);
                queryEntity.setHistoryMoney(0.00);
                queryEntity.setMoney(StringUtils.isEmpty(money) ? 0.00 : Double.valueOf(money));
                queryEntity.setRemark(remark);
                queryEntity.setTpId(tpId);
                queryEntity.setAddChildFlag("3");
                TPmContractFundsEntity parentFund = getContractParentFund(num, tpId);
                if (parentFund != null) {
                    queryEntity.setParent(parentFund.getId());
                    this.commonDao.save(queryEntity);
                }
            } else {
                if ("2".equals(queryEntity.getAddChildFlag()) || "3".equals(queryEntity.getAddChildFlag())) {
                    queryEntity.setRemark(remark);
                    queryEntity.setMoney(StringUtils.isEmpty(money) ? 0.00 : Double.valueOf(money));
                    this.commonDao.updateEntitie(queryEntity);
                }
            }
        }
        rebuildContractMoney(tpId);//重新计算金额
    }

    private String getCellVal(HSSFRow row, int j) {
        HSSFCell cell = row.getCell(j);
        String cellValue = "";
        if (cell != null) {
            int cellType = cell.getCellType();
            if (Cell.CELL_TYPE_NUMERIC == cellType) {
                cellValue = StringUtils.trim(String.valueOf(cell.getNumericCellValue()));
            } else {
                cellValue = StringUtils.trim(cell.getStringCellValue());
            }
        }
        return cellValue;
    }


    /**
     * 导入预算附表
     * 
     * @param workbook
     * @param tpId
     * @param showType
     */
    private void importFundsAddum(HSSFWorkbook workbook, String tpId, String showType) {
        HSSFSheet sheet = workbook.getSheetAt(1);
        if (sheet == null) {
            logger.error("预算附表不存在！");
            return;
        }
        int rownum = sheet.getLastRowNum();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            HSSFCell cell0 = row.getCell(0);
            if (cell0 == null) {
                continue;
            }
            String value = cell0.getStringCellValue();
            if ("".equals(value) || "此处可添加子项".equals(value)) {
                continue;
            }
            String content = getCellVal(row, 0);
            String num = getCellVal(row, 1);
            if (num.length() % 2 != 0) {
                throw new BusinessException("导入预算主表第" + (i + 1) + "行，序号不合法，请检查");
            }
            if (StringUtils.isEmpty(content) || StringUtils.isEmpty(num)) {//内容或序号为空不处理
                continue;
            }
            TPmFundsBudgetAddendumEntity addendumEntity = getAddendumFund(num, tpId);
            if (addendumEntity == null) {
                addendumEntity = new TPmFundsBudgetAddendumEntity();
                addendumEntity.setContent(content);
                addendumEntity.setNum(num);
                addendumEntity.setPid(tpId);
                addendumEntity.setAddChildFlag("3");
            }

            String model = getCellVal(row, 2);
            addendumEntity.setModel(model);
            String amount = getCellVal(row, 3);
            if (!checkNumber(amount)) {
                throw new BusinessException("导入预算附表第" + (i + 1) + "行，数量列的值不合法，请检查");
            }

            addendumEntity.setAccount("".equals(amount) ? "0" : amount);
            String price = getCellVal(row, 4);
            if (!checkNumber(price)) {
                throw new BusinessException("导入预算附表第" + (i + 1) + "行，单价列的值不合法，请检查");
            }
            addendumEntity.setPrice("".equals(price) ? 0.00 : Double.valueOf(price));
            String money = getCellVal(row, 5);
            if (!checkNumber(money)) {
                throw new BusinessException("导入预算附表第" + (i + 1) + "行，金额列的值不合法，请检查");
            }
            addendumEntity.setMoney("".equals(money) ? 0.00 : Double.valueOf(money));
            String remark = getCellVal(row, 6);
            addendumEntity.setRemark(remark);
            this.commonDao.saveOrUpdate(addendumEntity);
        }
        rebuildAddendumMoney(tpId);//重新计算金额
    }

    /**
     * 查询预算附表记录
     * 
     * @param num
     * @param tpId
     * @return
     */
    private TPmFundsBudgetAddendumEntity getAddendumFund(String num, String tpId) {
        TPmFundsBudgetAddendumEntity queryEnt = null;
        CriteriaQuery cq = new CriteriaQuery(TPmFundsBudgetAddendumEntity.class);
        cq.eq("num", num);
        cq.eq("pid", tpId);
        cq.add();
        List<TPmFundsBudgetAddendumEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() > 0) {
            queryEnt = entities.get(0);
        }
        return queryEnt;
    }

    /**
     * 重新计算金额
     */
    private void rebuildAddendumMoney(String tpId) {
        CriteriaQuery cq = new CriteriaQuery(TPmFundsBudgetAddendumEntity.class);
        cq.isNull("num");
        cq.eq("pid", tpId);
        cq.add();
        List<TPmFundsBudgetAddendumEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() == 0) {
            throw new BusinessException("合计行不存在！");
        }
        if (entities.size() >1) {
            throw new BusinessException("合计行有多个！");
        }
        TPmFundsBudgetAddendumEntity sumEntity = entities.get(0);
        String sql = "select sum(t.money) as sum from t_pm_funds_budget_addendum t where t.pid = ? and t.num is not null";
         List<Map<String,Object>> sumList = this.commonDao.findForJdbc(sql, tpId);
         Double sum = null;
         if(sumList.size()>0){
             Map<String,Object> dataMap = sumList.get(0);
             sum = dataMap.get("SUM")==null?0.00:((BigDecimal)dataMap.get("SUM")).doubleValue();
             sumEntity.setMoney(sum);
             this.commonDao.updateEntitie(sumEntity);
         }
    }
}