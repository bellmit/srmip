package com.kingtake.project.service.impl.plandraft;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.declare.TPmDeclareEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;
import com.kingtake.project.entity.declare.army.TPmDeclareBackEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDraftEntity;
import com.kingtake.project.entity.reportreq.TBPmReportReqEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.plandraft.TPmPlanDraftServiceI;

@Service("tPmPlanDraftService")
@Transactional
public class TPmPlanDraftServiceImpl extends CommonServiceImpl implements TPmPlanDraftServiceI, ProjectListServiceI, ApprFlowServiceI{

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
    
    private static String[] titles = { "项目名称", "用途及总要求", "起始日期", "截止日期", "年度工作要求", "主管业务部门", "责任单位", "承研单位", "负责人", "项目组成员", "总概算", "年度概算", "项目类别", "备注" };

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmPlanDraftEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmPlanDraftEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmPlanDraftEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TPmPlanDraftEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TPmPlanDraftEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmPlanDraftEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmPlanDraftEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{plan_name}", String.valueOf(t.getPlanName()));
        sql = sql.replace("#{plan_time}", String.valueOf(t.getPlanTime()));
        sql = sql.replace("#{remark}", String.valueOf(t.getRemark()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{plan_status}", String.valueOf(t.getPlanStatus()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void savePlanDraft(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request) {
        try {
            if (StringUtils.isNotEmpty(tPmPlanDraft.getId())) {
                TPmPlanDraftEntity t = this.commonDao.get(TPmPlanDraftEntity.class, tPmPlanDraft.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmPlanDraft, t);
                this.commonDao.updateEntitie(t);
                deletePlanDetail(tPmPlanDraft.getId());
            } else {
                tPmPlanDraft.setPlanStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                this.commonDao.save(tPmPlanDraft);
            }
            String rowStr = request.getParameter("planDetailStr");
            JSONArray array = JSONArray.parseArray(rowStr);
            for (int i = 0; i < array.size(); i++) {
                TPmPlanDetailEntity detailEntity = new TPmPlanDetailEntity();
                JSONObject json = (JSONObject) array.get(i);
                String projectId = (String) json.get("id");
                String declareId = (String) json.get("declareId");
                String declareType = (String) json.get("declareType");
                detailEntity.setDeclareId(declareId);
                detailEntity.setPlanId(tPmPlanDraft.getId());
                detailEntity.setDeclareType(declareType);
                detailEntity.setProjectId(projectId);
                detailEntity.setAuditStatus("0");//初始化状态
                this.commonDao.save(detailEntity);
                TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
                project.setPlanFlag("1");
                this.commonDao.updateEntitie(project);
            }

        } catch (Exception e) {
            throw new BusinessException("保存计划草案失败！", e);
        }
    }

    @Override
    public void deletePlan(TPmPlanDraftEntity tPmPlanDraft) {
        deletePlanDetail(tPmPlanDraft.getId());
        this.commonDao.delete(tPmPlanDraft);
    }

    /**
     * 删除计划草案明细
     * 
     * @param planId
     */
    private void deletePlanDetail(String planId) {
        List<TPmPlanDetailEntity> detailList = this.commonDao.findByProperty(TPmPlanDetailEntity.class, "planId",
                planId);
        for (TPmPlanDetailEntity detail : detailList) {
            TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, detail.getProjectId());
            project.setPlanFlag("0");//重置为未生成计划草案
            this.commonDao.updateEntitie(project);
            this.commonDao.delete(detail);
        }
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            // 操作：完成流程
            TPmPlanDraftEntity t = commonDao.get(TPmPlanDraftEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getPlanStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                // 操作：完成流程
                t.setPlanStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserId(user.getId());
                t.setFinishUserName(user.getRealName());
                commonDao.saveOrUpdate(t);
             }
            
        }
   }


    @Override
    public AjaxJson doAudit(TPmPlanDetailEntity planDetail, HttpServletRequest req) {
        AjaxJson json = new AjaxJson();
        String planStatus = null;
        String opt = req.getParameter("opt");
        TPmPlanDetailEntity t = this.commonDao.get(TPmPlanDetailEntity.class, planDetail.getId());
        if ("pass".equals(opt)) {
            t.setAuditStatus("1");//通过
            this.commonDao.updateEntitie(t);
            TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, t.getProjectId());
            project.setProjectStatus(ProjectConstant.PROJECT_STATUS_EXECUTING);
            this.commonDao.updateEntitie(project);
            List<TPmPlanDetailEntity> planDetailList = this.commonDao.findByProperty(TPmPlanDetailEntity.class,
                    "planId", t.getPlanId());
            boolean finishFlag = true;
            for (TPmPlanDetailEntity detail : planDetailList) {
                if (!"1".equals(detail.getAuditStatus())) {
                    finishFlag = false;
                }
            }
            if (finishFlag) {
                TPmPlanDraftEntity plan = this.commonDao.get(TPmPlanDraftEntity.class, t.getPlanId());
                plan.setFinishTime(new Date());
                plan.setPlanStatus(ApprovalConstant.APPRSTATUS_FINISH);
                this.commonDao.updateEntitie(plan);
            }
            json.setMsg("审核通过操作成功！");
            planStatus = "1";
        } else if ("reject".equals(opt)) {
            t.setAuditStatus("2");//不通过
            t.setMsgText(planDetail.getMsgText());
            this.commonDao.updateEntitie(t);
            json.setMsg("审核不通过操作成功！");
            planStatus = "2";
        }

        //更新申报信息状态
        if ("declare".equals(t.getDeclareType())) {
            TPmDeclareEntity declare = this.commonDao.get(TPmDeclareEntity.class, t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        } else if ("army".equals(t.getDeclareType())) {
            TBPmDeclareArmyResearchEntity declare = this.commonDao.get(TBPmDeclareArmyResearchEntity.class,
                    t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        } else if ("repair".equals(t.getDeclareType())) {
            TBPmDeclareRepairEntity declare = this.commonDao.get(TBPmDeclareRepairEntity.class, t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        } else if ("back".equals(t.getDeclareType())) {
            TPmDeclareBackEntity declare = this.commonDao.get(TPmDeclareBackEntity.class, t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        } else if ("tech".equals(t.getDeclareType())) {
            TBPmDeclareTechnologyEntity declare = this.commonDao.get(TBPmDeclareTechnologyEntity.class,
                    t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        } else if ("reportReq".equals(t.getDeclareType())) {
            TBPmReportReqEntity declare = this.commonDao.get(TBPmReportReqEntity.class, t.getDeclareId());
            declare.setPlanStatus(planStatus);
            this.commonDao.updateEntitie(declare);
        }
        return json;
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_PLAN_DRAFT);
    }

    @Override
    public void doBack(String id) {
        TPmPlanDraftEntity t = commonDao.get(TPmPlanDraftEntity.class, id);
        //将审批状态修改为被驳回
        t.setPlanStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TPmPlanDraftEntity entity = this.get(TPmPlanDraftEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getPlanStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getPlanStatus())) {
              entity.setPlanStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmPlanDraftEntity t = commonDao.get(TPmPlanDraftEntity.class, id);
        if(t!=null){
            appName = t.getPlanName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmPlanDraftEntity entity = this.get(TPmPlanDraftEntity.class, id);
        if (entity != null) {
              entity.setPlanStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }
    
    @Override
    public Workbook exportProject(String id) {
    	List<TPmPlanDetailEntity> detailList = this.commonDao.findByProperty(TPmPlanDetailEntity.class,
                "planId", id);
    
        HSSFWorkbook wb = new HSSFWorkbook();
        createSheet(wb, detailList);
        return wb;
    }
    
    /**
     * 创建sheet页
     * 
     * @param wb
     * @param year
     * @param list
     */
      private void createSheet(HSSFWorkbook wb,List<TPmPlanDetailEntity> detailList){
        HSSFSheet sheet = wb.createSheet();
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleStyle = getTitleStyle(wb);
          for (int i = 0; i < titles.length; i++) {
              HSSFCell cell = titleRow.createCell(i);
              cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
          }
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 3000);       
        sheet.setColumnWidth(12, 5000);
        int rownum = 0;
        
        for (TPmPlanDetailEntity detail : detailList) {
        	rownum++;
            TPmProjectEntity projectEntity = this.commonDao.get(TPmProjectEntity.class, detail.getProjectId());
            
            HSSFRow row = sheet.createRow(rownum);
            HSSFCell cell0 = row.createCell(0);//项目名称
            cell0.setCellValue(projectEntity.getProjectName());
            HSSFCell cell1 = row.createCell(1);//用途及总要求
            cell1.setCellValue("");
            HSSFCell cell2 = row.createCell(2);//起始日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cell2.setCellValue(sdf.format(projectEntity.getBeginDate()));
            HSSFCell cell3 = row.createCell(3);//截止日期
            cell3.setCellValue(sdf.format(projectEntity.getEndDate()));
            HSSFCell cell4 = row.createCell(4);//年度工作要求
            cell4.setCellValue("");
            HSSFCell cell5 = row.createCell(5);//主管业务部门
            cell5.setCellValue(projectEntity.getManageDepart());
            HSSFCell cell6 = row.createCell(6);//责任单位  
            if(projectEntity.getDutyDepart() != null){
            	cell6.setCellValue(projectEntity.getDutyDepart().getDepartname());
            }
            HSSFCell cell7 = row.createCell(7);//承研单位
            if(projectEntity.getDevDepart() != null){
            	cell7.setCellValue(projectEntity.getDevDepart().getDepartname());
            }        
            HSSFCell cell8 = row.createCell(8);//负责人+电话
            cell8.setCellValue(projectEntity.getProjectManager() + "(" + projectEntity.getManagerPhone() + ")");
            HSSFCell cell9 = row.createCell(9);//项目组成员
            String sql = "select name from t_pm_project_member where t_p_id='" + projectEntity.getId() + "'";
            List<Map<String, Object>> memberListMap = this.commonDao.findForJdbc(sql);
            String memberName = "";
            if(memberListMap.size()>0){
            	for(Map<String, Object> map : memberListMap){
            		memberName += map.get("name") + ",";
            	}
            }
            memberName = memberName.substring(0, memberName.length() - 1);
            cell9.setCellValue(memberName);
            HSSFCell cell10 = row.createCell(10);//总概算
            cell10.setCellValue("");
            HSSFCell cell11 = row.createCell(11);//年度概算
            cell11.setCellValue("");
            HSSFCell cell12 = row.createCell(12);//项目类别
            cell12.setCellValue(projectEntity.getProjectType().getProjectTypeName());
            HSSFCell cell13 = row.createCell(13);//备注
            cell13.setCellValue("");
        }               
      }
      
      /**
       * 表明的Style
       * 
       * @param workbook
       * @return
       */
      public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
          HSSFCellStyle titleStyle = workbook.createCellStyle();
          Font font = workbook.createFont();
          font.setFontHeightInPoints((short) 12);
          font.setBoldweight((short) 26);
          font.setFontName("宋体");
          titleStyle.setFont(font);
          titleStyle.setWrapText(false);
          titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
          titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
          return titleStyle;
      }
}