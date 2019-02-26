package com.kingtake.project.service.impl.incomeplan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.incomeplan.TPmIncomePlanServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmIncomePlanService")
@Transactional
public class TPmIncomePlanServiceImpl extends CommonServiceImpl implements TPmIncomePlanServiceI, ApprFlowServiceI, ProjectListServiceI {
																								
    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmIncomePlanEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmIncomePlanEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmIncomePlanEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmIncomePlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmIncomePlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmIncomePlanEntity t) {
        return true;
    }
    
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }
    
    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
        /*CriteriaQuery cq = new CriteriaQuery(TPmIncomePlanEntity.class);
        cq.eq("checkUserId", user.getId());
      	//cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        cq.eq("approvalStatus", "1");//审核状态为1 已提交
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomePlanEntity> incomePlanList = this.commonDao.getListByCriteriaQuery(cq, false);*/
        //select count(*)  from t_Pm_Project_plan a ,t_pm_appr_receive_logs b where a.id=b.APPR_ID and a.ADUIT_STATUS='1' and b.RECEIVE_USERID like '%"+user.getId()+"%'";
        String sql = "select count(*)  from t_Pm_Project_plan a  where  a.ADUIT_STATUS='1' "
        		+ " and a.id in (select b.APPR_ID from t_pm_appr_receive_logs b where b.RECEIVE_USERID like '%"+user.getId()+"%')";
        long cnt = this.commonDao.getCountForJdbc(sql);
        return (int)cnt;
    }

    /**
     * 计划下达提交
     * 
     * @param id
     * @return
     */
    @Override
    public void doSubmit(TPmIncomePlanEntity tPmIncomePlan) {
    	tPmIncomePlan.setApprovalStatus("1");//approvalStatus 为1 已提交
        this.commonDao.updateEntitie(tPmIncomePlan);        
    }
    
    /**
     * 审核不通过
     */
    @Override
    public void doReject(TPmIncomePlanEntity tPmIncomePlan) {
        this.commonDao.saveOrUpdate(tPmIncomePlan);
    }

	@Override
	public void sumMoney(String planId, String projectId) {
		String sql ="select plan_year,pp.id,f.xmid,sum(je) as sumje from t_pm_project_plan pp ";
		sql = sql + "join t_pm_fpje f on f.jhwjid = pp.id ";
		sql = sql + " where f.xmid = ?";
		sql = sql + "group by plan_year,pp.id,f.xmid ";
		List<Map<String, Object>> list = this.findForJdbc(sql, projectId);
		BigDecimal beforeMoney = sumMoney(list,false);
		BigDecimal nowMoney = sumMoney(list,true);
		String excSql = " update t_pm_project set yap =? ,dnjf = ? ,zmye = (ALL_FEE-?) where id = ?"; 
		this.executeSql(excSql, beforeMoney.toString(),nowMoney.toString(),beforeMoney.add(nowMoney),projectId);
		
	}
	/**
	 * 计算金额
	 * @param list
	 * @param isNow true:当年经费 false:已安排
	 * @return
	 */
	private BigDecimal sumMoney(List<Map<String, Object>> list,boolean isNow){
		int now = Calendar.getInstance().get(Calendar.YEAR);
		BigDecimal sum = new BigDecimal("0.00");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Integer planYear = Integer.valueOf(map.get("PLAN_YEAR").toString());
			String sumje = map.get("SUMJE").toString();
			if(isNow&&planYear.intValue()==now){
				sum = sum.add(new BigDecimal(sumje));
			}else if( (!isNow) && planYear.intValue()<now){
				sum = sum.add(new BigDecimal(sumje));
			}
		}
		return sum;
	}

	@Override
	public void updateFinishFlag(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doBack(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCancel(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPass(String id) {
		// TODO Auto-generated method stub
		//t_Pm_Project_plan
		TPmProjectPlanEntity entity = this.get(TPmProjectPlanEntity.class, id);
		//TPmIncomePlanEntity entity = this.get(TPmIncomePlanEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAduitStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAduitStatus())) {
              entity.setAduitStatus(ApprovalConstant.APPRSTATUS_SEND);
              //this.saveOrUpdate(entity);
              super.saveOrUpdate(entity);
            }
        }
	}

	@Override
	public String getAppName(String id) {
		// TODO Auto-generated method stub
		String appName = "";
		TPmIncomePlanEntity t = commonDao.get(TPmIncomePlanEntity.class, id);
        if(t!=null){
            appName = t.getDocumentName();
        }
        return appName;
	}

	
}