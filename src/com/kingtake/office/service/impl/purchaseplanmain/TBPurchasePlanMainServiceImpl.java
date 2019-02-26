package com.kingtake.office.service.impl.purchaseplanmain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;
import com.kingtake.office.entity.purchaseplanmain.TBPurchasePlanMainEntity;
import com.kingtake.office.service.purchaseplanmain.TBPurchasePlanMainServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBPurchasePlanMainService")
@Transactional
public class TBPurchasePlanMainServiceImpl extends CommonServiceImpl implements TBPurchasePlanMainServiceI,ApprFlowServiceI ,ProjectListServiceI{
    @Autowired
    private CommonMessageServiceI commonMessageService;
    
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
    
  public <T> void delete(T entity) {
    super.delete(entity);
    // 执行删除操作配置的sql增强
    this.doDelSql((TBPurchasePlanMainEntity) entity);
  }

  public void addMain(TBPurchasePlanMainEntity tBPurchasePlanMain, List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList) {
    // 保存主信息
    this.save(tBPurchasePlanMain);

    /** 保存-科研采购计划明细 */
    for (TBPurchasePlanDetailEntity tBPurchasePlanDetail : tBPurchasePlanDetailList) {
      // 外键设置
      tBPurchasePlanDetail.setPurchasePlanId(tBPurchasePlanMain.getId());
      this.save(tBPurchasePlanDetail);
    }
    // 执行新增操作配置的sql增强
    this.doAddSql(tBPurchasePlanMain);
  }

  public void updateMain(TBPurchasePlanMainEntity tBPurchasePlanMain, List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList) {
    // 保存主表信息
    this.saveOrUpdate(tBPurchasePlanMain);
    // ===================================================================================
    // 获取参数
    Object id0 = tBPurchasePlanMain.getId();
    // ===================================================================================
    // 1.查询出数据库的明细数据-科研采购计划明细
    String hql0 = "from TBPurchasePlanDetailEntity where 1 = 1 AND pURCHASE_PLAN_ID = ? ";
    List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailOldList = this.findHql(hql0, id0);
    // 2.筛选更新明细数据-科研采购计划明细
    for (TBPurchasePlanDetailEntity oldE : tBPurchasePlanDetailOldList) {
      boolean isUpdate = false;
      for (TBPurchasePlanDetailEntity sendE : tBPurchasePlanDetailList) {
        // 需要更新的明细数据-科研采购计划明细
        if (oldE.getId().equals(sendE.getId())) {
          try {
            MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
            this.saveOrUpdate(oldE);
          } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
          }
          isUpdate = true;
          break;
        }
      }
      if (!isUpdate) {
        // 如果数据库存在的明细，前台没有传递过来则是删除-科研采购计划明细
        super.delete(oldE);
      }

    }
    // 3.持久化新增的数据-科研采购计划明细
    for (TBPurchasePlanDetailEntity tBPurchasePlanDetail : tBPurchasePlanDetailList) {
      if (oConvertUtils.isEmpty(tBPurchasePlanDetail.getId())) {
        // 外键设置
        tBPurchasePlanDetail.setPurchasePlanId(tBPurchasePlanMain.getId());
        this.save(tBPurchasePlanDetail);
      }
    }
    // 执行更新操作配置的sql增强
    this.doUpdateSql(tBPurchasePlanMain);
  }

  public void delMain(TBPurchasePlanMainEntity tBPurchasePlanMain) {
    // 获取参数
    Object id0 = tBPurchasePlanMain.getId();
    // ===================================================================================
    // 删除-科研采购计划明细
    String hql0 = "from TBPurchasePlanDetailEntity where 1 = 1 AND pURCHASE_PLAN_ID = ? ";
    List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailOldList = this.findHql(hql0, id0);
    this.deleteAllEntitie(tBPurchasePlanDetailOldList);
    // ===================================================================================
    // 删除主表信息
    this.delete(tBPurchasePlanMain);
  }

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBPurchasePlanMainEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBPurchasePlanMainEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBPurchasePlanMainEntity t) {
    return true;
  }

  /**
   * 替换sql中的变量
   * 
   * @param sql
   * @return
   */
  public String replaceVal(String sql, TBPurchasePlanMainEntity t) {
    sql = sql.replace("#{id}", String.valueOf(t.getId()));
    sql = sql.replace("#{duty_depart_id}", String.valueOf(t.getDutyDepartId()));
    sql = sql.replace("#{duty_depart_name}", String.valueOf(t.getDutyDepartName()));
    sql = sql.replace("#{manager_id}", String.valueOf(t.getManagerId()));
    sql = sql.replace("#{manager_name}", String.valueOf(t.getManagerName()));
    sql = sql.replace("#{project_code}", String.valueOf(t.getProjectCode()));
    sql = sql.replace("#{project_name}", String.valueOf(t.getProjectName()));
    sql = sql.replace("#{total_funds}", String.valueOf(t.getTotalFunds()));
    sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
    sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
    sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
    sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
    sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
    sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
    sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
    return sql;
  }

    
    @Override
    public void savePlan(TBPurchasePlanMainEntity tBPurchasePlanMain) {
        try{
        String planDetailStr = tBPurchasePlanMain.getPlanDetailStr();
        List<TBPurchasePlanDetailEntity> detailList = new ArrayList<TBPurchasePlanDetailEntity>();
        JSONArray array = JSONArray.parseArray(planDetailStr);//解析明细
        for(int i = 0;i<array.size();i++){
            JSONObject json = (JSONObject) array.get(i);
            TBPurchasePlanDetailEntity tmp = new TBPurchasePlanDetailEntity();
            tmp.setPlanName(json.getString("planName"));
            Double purchaseEstimates = json.getDouble("purchaseEstimates");
            tmp.setPurchaseEstimates(new BigDecimal(purchaseEstimates) );
            tmp.setPurchaseMode(json.getString("purchaseMode"));
            tmp.setPurchaseSource(json.getString("purchaseSource"));
            tmp.setPurchaseReason(json.getString("purchaseReason"));
            detailList.add(tmp);
        }
        if(StringUtils.isNotEmpty(tBPurchasePlanMain.getId())){
            TBPurchasePlanMainEntity t = this.commonDao.get(TBPurchasePlanMainEntity.class,
                    tBPurchasePlanMain.getId());
            MyBeanUtils.copyBeanNotNull2Bean(tBPurchasePlanMain, t);
            this.commonDao.updateEntitie(t);
            List<TBPurchasePlanDetailEntity> queryDetailList = this.commonDao.findByProperty(TBPurchasePlanDetailEntity.class, "purchasePlanId", tBPurchasePlanMain.getId());
            this.commonDao.deleteAllEntitie(queryDetailList);
        }else{
            tBPurchasePlanMain.setInputRole("1");//课题组入口录入
            tBPurchasePlanMain.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
            this.commonDao.save(tBPurchasePlanMain);
        }
        for(TBPurchasePlanDetailEntity tmp:detailList){
            tmp.setPurchasePlanId(tBPurchasePlanMain.getId());//设置主表id
            this.commonDao.save(tmp);
        }
        }catch(Exception e){
            throw new BusinessException(e);
        }
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtils.isNotEmpty(id)) {
            TBPurchasePlanMainEntity t = commonDao.get(TBPurchasePlanMainEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                // 操作：完成流程
                t.setFinishFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserId(user.getId());
                t.setFinishUserName(user.getRealName());
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void doBack(String id) {
        TBPurchasePlanMainEntity finishApply = this.get(TBPurchasePlanMainEntity.class, id);
        //将审批状态修改为被驳回
        finishApply.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(finishApply);
    }

    @Override
    public void doPass(String id) {
        TBPurchasePlanMainEntity entity = this.get(TBPurchasePlanMainEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getFinishFlag())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getFinishFlag())) {
                entity.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
                this.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public String getAppName(String id) {
        TBPurchasePlanMainEntity finishApply = this.get(TBPurchasePlanMainEntity.class, id);
        return finishApply.getProjectName()+"项目采购计划";
    }

    @Override
    public void doCancel(String id) {
        TBPurchasePlanMainEntity entity = this.get(TBPurchasePlanMainEntity.class, id);
        if (entity != null) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        
    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_CGJH);
    }

    @Override
    public void generateCode(String planIds, String year, String batch) {
        String[] ids = planIds.split(",");
        int num = 1;
        for(String id:ids){
            TBPurchasePlanMainEntity purchase = this.commonDao.get(TBPurchasePlanMainEntity.class, id);
            purchase.setCodeBatch(batch);
            purchase.setCodeYear(year);
            this.commonDao.updateEntitie(purchase);
            List<TBPurchasePlanDetailEntity> detailList = this.commonDao.findByProperty(TBPurchasePlanDetailEntity.class, "purchasePlanId", purchase.getId());
            for(TBPurchasePlanDetailEntity tmp:detailList){
                tmp.setCodeSerial(String.valueOf(num));
                tmp.setMergeCode(year+batch+getSeria(num));
                this.commonDao.updateEntitie(tmp);
                num++;
            }
        }
        
    }
    
    /**
     * 生成流水号
     * @return
     */
    private String getSeria(int num){
        if(num<10){
            return "00"+num;
        }else if(num<100){
            return "0"+num;
        }else {
            return ""+num;
        }
    }
}