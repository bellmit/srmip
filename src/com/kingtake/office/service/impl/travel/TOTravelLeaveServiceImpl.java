package com.kingtake.office.service.impl.travel;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelLeavedetailEntity;
import com.kingtake.office.entity.travel.TOTravelReportEntity;
import com.kingtake.office.service.travel.TOTravelLeaveServiceI;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;


@Service("tOTravelLeaveService")
@Transactional
public class TOTravelLeaveServiceImpl extends CommonServiceImpl implements TOTravelLeaveServiceI,ApprFlowServiceI {
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOTravelLeaveEntity)entity);
 	}
	
	@Override
    public void addMain(TOTravelLeaveEntity tOTravelLeave,
	        List<TOTravelLeavedetailEntity> tOTravelLeavedetailList){
			//保存主信息
			this.save(tOTravelLeave);
		
			/**保存-差旅-人员请假详细信息表*/
			for(TOTravelLeavedetailEntity tOTravelLeavedetail:tOTravelLeavedetailList){
				//外键设置
				tOTravelLeavedetail.setToId(tOTravelLeave.getId());
				this.save(tOTravelLeavedetail);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tOTravelLeave);
	}

	
	@Override
    public void updateMain(TOTravelLeaveEntity tOTravelLeave,
	        List<TOTravelLeavedetailEntity> tOTravelLeavedetailList) {
		//保存主表信息
		this.updateEntitie(tOTravelLeave);
		//===================================================================================
		//获取参数
		Object id0 = tOTravelLeave.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-差旅-人员请假详细信息表
	    String hql0 = "from TOTravelLeavedetailEntity where 1 = 1 AND t_O_ID = ? ";
	    List<TOTravelLeavedetailEntity> tOTravelLeavedetailOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-差旅-人员请假详细信息表
		for(TOTravelLeavedetailEntity oldE:tOTravelLeavedetailOldList){
			boolean isUpdate = false;
				for(TOTravelLeavedetailEntity sendE:tOTravelLeavedetailList){
					//需要更新的明细数据-差旅-人员请假详细信息表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-差旅-人员请假详细信息表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-差旅-人员请假详细信息表
			for(TOTravelLeavedetailEntity tOTravelLeavedetail:tOTravelLeavedetailList){
				if(oConvertUtils.isEmpty(tOTravelLeavedetail.getId())){
					//外键设置
					tOTravelLeavedetail.setToId(tOTravelLeave.getId());
					this.save(tOTravelLeavedetail);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tOTravelLeave);
	}

	
	@Override
    public void delMain(TOTravelLeaveEntity tOTravelLeave) {
	    //获取参数
	    Object id0 = tOTravelLeave.getId();
	    //===================================================================================
	    //删除-出差情况阅批单信息
        String hql1 = "from TOTravelReportEntity where 1 = 1 AND toId = ? ";
        
	    List<TOTravelReportEntity> reportList = this.findHql(hql1,id0);
	    this.deleteAllEntitie(reportList);
	    //===================================================================================
	    //删除-差旅-人员请假详细信息表
	    String hql0 = "from TOTravelLeavedetailEntity where 1 = 1 AND t_O_ID = ? ";
	    List<TOTravelLeavedetailEntity> tOTravelLeavedetailOldList = this.findHql(hql0,id0);
	    this.deleteAllEntitie(tOTravelLeavedetailOldList);
	    //===================================================================================
		//删除主表信息
		this.delete(tOTravelLeave);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TOTravelLeaveEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TOTravelLeaveEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TOTravelLeaveEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOTravelLeaveEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProject().getId()));
 		sql  = sql.replace("#{leave_id}",String.valueOf(t.getLeaveId()));
 		sql  = sql.replace("#{leave_name}",String.valueOf(t.getLeaveName()));
 		sql  = sql.replace("#{depart_id}",String.valueOf(t.getDepartId()));
 		sql  = sql.replace("#{depart_name}",String.valueOf(t.getDepartName()));
 		sql  = sql.replace("#{leave_time}",String.valueOf(t.getLeaveTime()));
 		sql  = sql.replace("#{duty}",String.valueOf(t.getDuty()));
 		sql  = sql.replace("#{unit_opinion}",String.valueOf(t.getUnitOpinion()));
 		sql  = sql.replace("#{opinion_userid}",String.valueOf(t.getOpinionUserid()));
 		sql  = sql.replace("#{opinion_username}",String.valueOf(t.getOpinionUsername()));
 		sql  = sql.replace("#{opinion_date}",String.valueOf(t.getOpinionDate()));
 		sql  = sql.replace("#{depart_instruc}",String.valueOf(t.getDepartInstruc()));
 		sql  = sql.replace("#{instruc_userid}",String.valueOf(t.getInstrucUserid()));
 		sql  = sql.replace("#{instruc_username}",String.valueOf(t.getInstrucUsername()));
 		sql  = sql.replace("#{instruc_date}",String.valueOf(t.getInstrucDate()));
 		sql  = sql.replace("#{back_leave_state}",String.valueOf(t.getBackLeaveState()));
 		sql  = sql.replace("#{back_leave_date}",String.valueOf(t.getBackLeaveDate()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{reissued_flag}",String.valueOf(t.getReissuedFlag()));
 		sql  = sql.replace("#{reissued_time}",String.valueOf(t.getReissuedTime()));
 		sql  = sql.replace("#{reissued_reason}",String.valueOf(t.getReissuedReason()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
    

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TOTravelLeaveEntity t = commonDao.get(TOTravelLeaveEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setApprStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setApprFinishTime(new Date());
                t.setApprFinishUserId(user.getId());
                t.setApprFinishUserName(user.getRealName());
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void doBack(String id) {
        TOTravelLeaveEntity t = commonDao.get(TOTravelLeaveEntity.class, id);
        //将审批状态修改为被驳回
        t.setApprStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TOTravelLeaveEntity entity = this.get(TOTravelLeaveEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getApprStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getApprStatus())) {
              entity.setApprStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TOTravelLeaveEntity t = commonDao.get(TOTravelLeaveEntity.class, id);
        if(t!=null){
            appName = t.getTitle();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TOTravelLeaveEntity entity = this.get(TOTravelLeaveEntity.class, id);
        if (entity != null) {
              entity.setApprStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

}