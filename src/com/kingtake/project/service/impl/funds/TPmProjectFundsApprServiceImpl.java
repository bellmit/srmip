package com.kingtake.project.service.impl.funds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.dao.TPmProjectFundsApprDao;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.funds.TPmContractFundsEntity;
import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.entity.funds.TPmMaterialEntity;
import com.kingtake.project.entity.funds.TPmPlanFundsEntity;
import com.kingtake.project.entity.funds.TPmProjectBalanceEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;
import com.kingtake.project.service.funds.TPmPlanFundsServiceI;
import com.kingtake.project.service.funds.TPmProjectFundsApprServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;

@Service("tPmProjectFundsApprService")
@Transactional
public class TPmProjectFundsApprServiceImpl extends CommonServiceImpl implements TPmProjectFundsApprServiceI,
        ProjectListServiceI,ApprFlowServiceI {
    @Autowired
    private TPmContractFundsServiceI tPmContractFundsService;

    @Autowired
    private TPmProjectFundsApprDao tPmProjectFundsApprDao;

    @Autowired
    private TPmPlanFundsServiceI tPmPlanFundsService;
    
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
    
    @Autowired
    private TPmProjectServiceI tPmProjectService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //???????????????????????????sql??????
        this.doDelSql((TPmProjectFundsApprEntity) entity);
    }

    @Override
    public void delMain(TPmProjectFundsApprEntity tPmProjectFundsApprEntity) {
        //??????-??????????????????
        commonDao.getSession().createSQLQuery("delete from T_PM_CONTRACT_FUNDS WHERE T_P_ID = :apprId")
        	.setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        //??????-??????????????????
        commonDao.getSession().createSQLQuery("delete from T_PM_PLAN_FUNDS WHERE T_P_ID = :apprId")
    		.setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        // ????????????
        commonDao.getSession().createSQLQuery("delete from T_PM_FUNDS_BUDGET_ADDENDUM WHERE PID = :apprId")
			.setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        
        //???????????????????????????????????????
        commonDao.getSession().createSQLQuery("update T_PM_PROJECT_PLAN set YS_STATUS = 0, YS_APPR_ID = null where YS_APPR_ID = :apprId")
            .setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        
        //???????????????????????????????????????
        commonDao.getSession().createSQLQuery("update T_PM_INCOME_APPLY set YS_STATUS = 0, YS_APPR_ID = null where YS_APPR_ID = :apprId")
            .setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        
        //???????????????????????????????????????
        commonDao.getSession().createSQLQuery("update T_PM_PROJECT_BALANCE set YS_STATUS = 0, YS_APPR_ID = null where YS_APPR_ID = :apprId")
        .setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        
        // ????????????????????????
        commonDao.getSession().createSQLQuery("delete from t_pm_contract_funds WHERE T_P_ID = :apprId")
			.setParameter("apprId", tPmProjectFundsApprEntity.getId()).executeUpdate();
        
        this.delete(tPmProjectFundsApprEntity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //???????????????????????????sql??????
        this.doAddSql((TPmProjectFundsApprEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //???????????????????????????sql??????
        this.doUpdateSql((TPmProjectFundsApprEntity) entity);
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmProjectFundsApprEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmProjectFundsApprEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmProjectFundsApprEntity t) {
        return true;
    }

    /**
     * ??????sql????????????
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmProjectFundsApprEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
        sql = sql.replace("#{voucher_num}", String.valueOf(t.getVoucherNum()));
        sql = sql.replace("#{invoice_num}", String.valueOf(t.getInvoiceNum()));
        sql = sql.replace("#{start_year}", String.valueOf(t.getStartYear()));
        sql = sql.replace("#{end_year}", String.valueOf(t.getEndYear()));
        sql = sql.replace("#{account_funds}", String.valueOf(t.getAccountFunds()));
        sql = sql.replace("#{content}", String.valueOf(t.getContent()));
        sql = sql.replace("#{developer_audit_opinion}", String.valueOf(t.getDeveloperAuditOpinion()));
        sql = sql.replace("#{duty_audit_opinion}", String.valueOf(t.getDutyAuditOpinion()));
        sql = sql.replace("#{research_audit_opinion}", String.valueOf(t.getResearchAuditOpinion()));
        sql = sql.replace("#{finance_audit_opinion}", String.valueOf(t.getFinanceAuditOpinion()));
        sql = sql.replace("#{developer_approval_opinion}", String.valueOf(t.getDeveloperApprovalOpinion()));
        sql = sql.replace("#{year_funds_plan}", String.valueOf(t.getYearFundsPlan()));
        sql = sql.replace("#{rein_funds_plan}", String.valueOf(t.getReinFundsPlan()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }
    
    
    

    @Override
    public List<Map<String, Object>> initField(String projApproval) {
        StringBuffer sql = new StringBuffer(
                "SELECT ID, BUDGET_NAE AS NAME , SORT_CODE AS NUM  FROM T_B_APPROVAL_BUDGET_RELA ");
        if (StringUtil.isNotEmpty(projApproval)) {
            sql.append(" WHERE PROJ_APPROVAL = '" + projApproval + "'");
            sql.append(" AND SHOW_FLAG = '"+SrmipConstants.YES+"'");
        }
        sql.append(" ORDER BY SORT_CODE");
        List<Map<String, Object>> tBApprovalBudgetRelaList = commonDao.findForJdbc(sql.toString());

        return tBApprovalBudgetRelaList;
    }

    @Override
    public List<Map<String, Object>> initFieldAndVal(String projApproval, String tpId) {
        List<Map<String, Object>> tBApprovalBudgetRelaList = new ArrayList<Map<String, Object>>();
        //?????????????????????????????????????????????
        if (projApproval.equals(ProjectConstant.PROJECT_PLAN)) {//?????????
            StringBuffer sql = new StringBuffer("SELECT ID , CONTENT AS NAME, MONEY, NUM FROM T_PM_PLAN_FUNDS  ");
            sql.append(" WHERE T_P_ID ='" + tpId + "'");
            sql.append(" AND CONTENT_ID IN ( SELECT ID FROM T_B_APPROVAL_BUDGET_RELA WHERE SHOW_FLAG = '"+SrmipConstants.YES+"')");
            sql.append(" ORDER BY NUM");
            tBApprovalBudgetRelaList = commonDao.findForJdbc(sql.toString());
        } else if (projApproval.equals(ProjectConstant.PROJECT_CONTRACT)) {//?????????
            String sql = "SELECT ID, CONTENT AS NAME, MONEY, NUM FROM T_PM_CONTRACT_FUNDS "
            		+ " WHERE T_P_ID ='" + tpId + "'"
            		+ " AND CONTENT_ID IN ( SELECT ID FROM T_B_APPROVAL_BUDGET_RELA WHERE SHOW_FLAG = '"+SrmipConstants.YES+ "')" 
            		+ " ORDER BY NUM";
            tBApprovalBudgetRelaList = commonDao.findForJdbc(sql);
        }
        return tBApprovalBudgetRelaList;
    }

    /**
     * ?????????????????????
     * 
     * @param project
     */
   /* @Override
    public String initFundsChange(TPmProjectEntity project) {
        // ??????????????????????????????
        TPmProjectFundsApprEntity appr = new TPmProjectFundsApprEntity();
        appr.setProject(project);
        appr.setProjectName(project.getProjectName());
        appr.setFeeType(project.getFeeType().getFundsCode());
        appr.setChangeFlag(SrmipConstants.YES);
        appr.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
        commonDao.save(appr);

        *//** ????????? *//*
        if (ProjectConstant.PROJECT_CONTRACT.equals(project.getPlanContractFlag())) {
            initContractChanges(project, appr);

            *//** ????????? *//*
        } else {
            *//** ????????? *********************************************************************//*
            initPlanChanges(project, appr);

        }
        return appr.getId();
    }*/

    /*private void initContractChanges(TPmProjectEntity project, TPmProjectFundsApprEntity appr) {
        // ????????????content_id?????????????????????
        tPmContractFundsService.init(appr.getId());
        // ??????????????????
        StringBuffer sql = new StringBuffer(" UPDATE T_PM_CONTRACT_FUNDS T1 SET T1.VARIABLE_MONEY = ( "
                + " SELECT T2.MONEY FROM " + " (SELECT CONTENT_ID, SUM(MONEY) AS MONEY FROM T_PM_CONTRACT_FUNDS "
                + " WHERE T_P_ID IN (SELECT ID FROM T_PM_PROJECT_FUNDS_APPR "
                + " WHERE T_P_ID = :projectId AND ID != :apprId AND FINISH_FLAG = :finishFlag) "
                + " AND CONTENT_ID IS NOT NULL GROUP BY CONTENT_ID)T2 " + " WHERE T1.CONTENT_ID = T2.CONTENT_ID) "
                + " WHERE T1.T_P_ID = :apprId ");
        commonDao.getSession().createSQLQuery(sql.toString()).setParameter("projectId", project.getId())
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH).setParameter("apprId", appr.getId())
                .executeUpdate();
        // ???????????????content_id?????????????????????
        sql.setLength(0);
        sql.append(" SELECT SUM(T1.MONEY) AS VARIABLEMONEY, T1.CONTENT, T2.CONTENT_ID AS PARENT, "
                + " :apprId AS APPRID, '2' AS ADDCHILDFLAG, 0 AS MONEY "
                + " FROM T_PM_CONTRACT_FUNDS T1, T_PM_CONTRACT_FUNDS T2 "
                + " WHERE T1.T_P_ID IN (SELECT ID FROM T_PM_PROJECT_FUNDS_APPR "
                + " WHERE T_P_ID = :projectId AND ID != :apprId AND FINISH_FLAG = :finishFlag) "
                + " AND T1.CONTENT_ID IS NULL AND T1.PARENT = T2.ID " + " GROUP BY T1.CONTENT, T2.CONTENT_ID ");
        List<TPmContractFundsEntity> noContentList = commonDao.getSession().createSQLQuery(sql.toString())
                .addScalar("variableMoney", StandardBasicTypes.DOUBLE).addScalar("money", StandardBasicTypes.DOUBLE)
                .addScalar("content", StandardBasicTypes.STRING).addScalar("parent", StandardBasicTypes.STRING)
                .addScalar("apprId", StandardBasicTypes.STRING).addScalar("addChildFlag", StandardBasicTypes.STRING)
                .setParameter("projectId", project.getId())
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH).setParameter("apprId", appr.getId())
                .setResultTransformer(Transformers.aliasToBean(TPmContractFundsEntity.class)).list();
        commonDao.batchSave(noContentList);
        // ?????????????????????
        tPmContractFundsService.initParent(appr.getId());
    }*/

    /*private void initPlanChanges(TPmProjectEntity project, TPmProjectFundsApprEntity appr) {
        // ????????????content_id?????????????????????
    	tPmPlanFundsService.init(appr.getId());
        // ??????????????????
        StringBuffer sql = new StringBuffer(" UPDATE T_PM_PLAN_FUNDS_ONE T1 SET T1.VARIABLE_MONEY = ( "
                + " SELECT T2.MONEY FROM " + " (SELECT EQUIPMENT_ID, SUM(MONEY) AS MONEY FROM T_PM_PLAN_FUNDS_ONE "
                + " WHERE T_P_ID IN (SELECT ID FROM T_PM_PROJECT_FUNDS_APPR "
                + " WHERE T_P_ID = :projectId AND ID != :apprId AND FINISH_FLAG = :finishFlag) "
                + " AND EQUIPMENT_ID IS NOT NULL GROUP BY EQUIPMENT_ID)T2 "
                + " WHERE T1.EQUIPMENT_ID = T2.EQUIPMENT_ID) " + " WHERE T1.T_P_ID = :apprId ");
        commonDao.getSession().createSQLQuery(sql.toString()).setParameter("projectId", project.getId())
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH).setParameter("apprId", appr.getId())
                .executeUpdate();
        // ???????????????content_id?????????????????????
        sql.setLength(0);
        sql.append(" SELECT SUM(T1.MONEY) AS VARIABLEMONEY, T1.EQUIPMENT_NAME AS EQUIPMENTNAME, T2.EQUIPMENT_ID AS PARENT, "
                + " :apprId AS APPRID, '2' AS ADDCHILDFLAG, 0 AS MONEY "
                + " FROM T_PM_PLAN_FUNDS_ONE T1, T_PM_PLAN_FUNDS_ONE T2 "
                + " WHERE T1.T_P_ID IN (SELECT ID FROM T_PM_PROJECT_FUNDS_APPR "
                + " WHERE T_P_ID = :projectId AND ID != :apprId AND FINISH_FLAG = :finishFlag) "
                + " AND T1.EQUIPMENT_ID IS NULL AND T1.PARENT = T2.ID "
                + " GROUP BY T1.EQUIPMENT_NAME, T2.EQUIPMENT_ID ");
        List<TPmPlanFundsEntity> noContentList = commonDao.getSession().createSQLQuery(sql.toString())
                .addScalar("variableMoney", StandardBasicTypes.DOUBLE).addScalar("money", StandardBasicTypes.DOUBLE)
                .addScalar("equipmentName", StandardBasicTypes.STRING).addScalar("parent", StandardBasicTypes.STRING)
                .addScalar("apprId", StandardBasicTypes.STRING).addScalar("addChildFlag", StandardBasicTypes.STRING)
                .setParameter("projectId", project.getId())
                .setParameter("finishFlag", ApprovalConstant.APPRSTATUS_FINISH).setParameter("apprId", appr.getId())
                .setResultTransformer(Transformers.aliasToBean(TPmPlanFundsEntity.class)).list();
        commonDao.batchSave(noContentList);
        // ?????????????????????
        tPmPlanFundsService.initParent(appr.getId());
    }*/

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
    	TSUser user = ResourceUtil.getSessionUserName();
    	String sql = "select count(*) from t_pm_project_funds_appr_audit where RECIVE_USERIDS like '%"+user.getId()+"%' and  audit_status='1'";
        Long count = this.commonDao.getCountForJdbc(sql);
        //return count.intValue();
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_FUNDS);
    }

    /**
     * ??????excel??????
     */
    @Override
    public HSSFWorkbook getFundsTemplate(String tpId,String planContractFlag) {
        HSSFWorkbook wb = new HSSFWorkbook();
        if (ProjectConstant.PROJECT_PLAN.equals(planContractFlag)) {
            generatePlanFunds(wb, planContractFlag, tpId);
        } else {
            generateContractFunds(wb, planContractFlag, tpId);
        }
        generateAddNum(wb, tpId);
        return wb;
    }

    /**
     * ???????????????????????????
     * 
     * @param wb
     * @param planContractFlag
     */
    private void generatePlanFunds(HSSFWorkbook wb, String planContractFlag, String tpId) {
        String[] TITLES = { "???????????????", "??????", "????????????", "??????", "??????", "??????", "??????", "??????", "??????" };
        List<Map<String, Object>> resultOne = getFundsList(planContractFlag, tpId);
        if (resultOne.size() > 0) {
            HSSFSheet sheet = wb.createSheet("??????????????????????????????");
            int rownum = 0;
            HSSFRow titleRow = sheet.createRow(rownum);
            HSSFCellStyle redStyle = getRedStyle(wb);
            HSSFCellStyle headStyle = getHeaderStyle(wb);
            HSSFCellStyle greenStyle = getGreenStyle(wb);
            for (int i = 0; i < TITLES.length; i++) {
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(TITLES[i]);
                cell.setCellStyle(headStyle);
            }
            sheet.setColumnWidth(0, 8000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 10000);
            HSSFCellStyle style = getStyle(wb);
            for (Map<String, Object> dataMap : resultOne) {
                HSSFCellStyle textStyle = style;
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                String num = dataMap.get("NUM") == null ? "" : dataMap.get("NUM").toString();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < num.length(); i++) {
                    sb.append(" ");
                }
                String addChildFlag = dataMap.get("ADD_CHILD_FLAG") == null ? "" : dataMap.get("ADD_CHILD_FLAG")
                        .toString();
                if ("0".equals(addChildFlag)) {
                    textStyle = redStyle;
                } else if ("1".equals(addChildFlag)) {
                    textStyle = greenStyle;
                }
                HSSFCell cell0 = row.createCell(0);
                String equipmentName = sb.toString()
                        + (dataMap.get("EQUIPMENT_NAME") == null ? "" : dataMap.get("EQUIPMENT_NAME").toString());
                cell0.setCellValue(equipmentName);
                cell0.setCellStyle(textStyle);
                HSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(num);
                cell1.setCellStyle(textStyle);
                HSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(dataMap.get("HISTORY_MONEY") == null ? "" : dataMap.get("HISTORY_MONEY").toString());
                cell2.setCellStyle(textStyle);
                HSSFCell cell3 = row.createCell(3);
                cell3.setCellValue(dataMap.get("MODEL") == null ? "" : dataMap.get("MODEL").toString());
                cell3.setCellStyle(textStyle);
                HSSFCell cell4 = row.createCell(4);
                cell4.setCellValue(dataMap.get("UNIT") == null ? "" : dataMap.get("UNIT").toString());
                cell4.setCellStyle(textStyle);
                HSSFCell cell5 = row.createCell(5);
                cell5.setCellValue(dataMap.get("AMOUNT") == null ? "" : dataMap.get("AMOUNT").toString());
                cell5.setCellStyle(textStyle);
                HSSFCell cell6 = row.createCell(6);
                cell6.setCellValue(dataMap.get("PRICE") == null ? "" : dataMap.get("PRICE").toString());
                cell6.setCellStyle(textStyle);
                HSSFCell cell7 = row.createCell(7);
                cell7.setCellValue(dataMap.get("MONEY") == null ? "" : dataMap.get("MONEY").toString());
                cell7.setCellStyle(textStyle);
                HSSFCell cell8 = row.createCell(8);
                cell8.setCellValue(dataMap.get("REMARK") == null ? "" : dataMap.get("REMARK").toString());
                cell8.setCellStyle(textStyle);
            }
        }
    }

    /**
     * ???????????????????????????
     * 
     * @param wb
     * @param planContractFlag
     */
    private void generateContractFunds(HSSFWorkbook wb, String planContractFlag, String tpId) {
        String[] TITLES = { "???????????????", "??????", "????????????", "??????", "??????" };
        String titleName = "????????????";
        List<Map<String, Object>> resultOne = getFundsList(planContractFlag, tpId);
        if (resultOne.size() > 0) {
            HSSFSheet sheet = null;
            sheet = wb.createSheet(titleName);
            int rownum = 0;
            HSSFRow titleRow = sheet.createRow(rownum);
            HSSFCellStyle headStyle = getHeaderStyle(wb);
            HSSFCellStyle redStyle = getRedStyle(wb);
            HSSFCellStyle greenStyle = getGreenStyle(wb);
            for (int i = 0; i < TITLES.length; i++) {
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(TITLES[i]);
                cell.setCellStyle(headStyle);
            }
            sheet.setColumnWidth(0, 8000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 10000);
            HSSFCellStyle style = getStyle(wb);
            for (Map<String, Object> dataMap : resultOne) {
                HSSFCellStyle textStyle = style;
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                String num = dataMap.get("NUM") == null ? "" : dataMap.get("NUM").toString();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < num.length(); i++) {
                    sb.append(" ");
                }
                String addChildFlag = dataMap.get("ADD_CHILD_FLAG") == null ? "" : dataMap.get("ADD_CHILD_FLAG")
                        .toString();
                if ("0".equals(addChildFlag)) {
                    textStyle = redStyle;
                } else if ("1".equals(addChildFlag)) {
                    textStyle = greenStyle;
                }
                HSSFCell cell0 = row.createCell(0);
                cell0.setCellValue(sb.toString()
                        + (dataMap.get("CONTENT") == null ? "" : dataMap.get("CONTENT").toString()));
                cell0.setCellStyle(textStyle);
                HSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(num);
                cell1.setCellStyle(textStyle);
                HSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(dataMap.get("HISTORY_MONEY") == null ? "" : dataMap.get("HISTORY_MONEY").toString());
                cell2.setCellStyle(textStyle);
                HSSFCell cell3 = row.createCell(3);
                cell3.setCellValue(dataMap.get("MONEY") == null ? "" : dataMap.get("MONEY").toString());
                cell3.setCellStyle(textStyle);
                HSSFCell cell4 = row.createCell(4);
                cell4.setCellValue(dataMap.get("REMARK") == null ? "" : dataMap.get("REMARK").toString());
                cell4.setCellStyle(textStyle);
            }
        }
    }

    /**
     * ??????????????????
     * 
     * @param showFlag
     * @param projApproval
     * @return
     */
    private List<Map<String, Object>> getFundsList(String projApproval, String tpId) {
        List<Map<String, Object>> list = null;
        if (ProjectConstant.PROJECT_PLAN.equals(projApproval)) {
            list = tPmProjectFundsApprDao.getPlanFundsList(tpId);
        } else {
            list = tPmProjectFundsApprDao.getContractFundsList(tpId);
        }
        return list;
    }

    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setFontName("??????");
        style.setFont(font);
        style.setWrapText(false);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * ??????
     * 
     * @param workbook
     * @return
     */
    private HSSFCellStyle getRedStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setFontName("??????");
        font.setColor(HSSFColor.RED.index);
        style.setFont(font);
        style.setWrapText(false);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * ??????
     * 
     * @param workbook
     * @return
     */
    private HSSFCellStyle getGreenStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setFontName("??????");
        font.setColor(HSSFColor.BLUE.index);
        style.setFont(font);
        style.setWrapText(false);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //        style.setFillBackgroundColor(HSSFColor.GREEN.index);// ???????????????
        //        style.setFillForegroundColor(HSSFColor.GREEN.index);// ???????????????
        //        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    /**
     * ?????????Style
     * 
     * @param workbook
     * @return
     */
    public HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight((short) 26);
        font.setFontName("??????");
        titleStyle.setFont(font);
        titleStyle.setWrapText(true);
        titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return titleStyle;
    }

    /**
     * ???????????????????????????
     * 
     * @param wb
     * @param planContractFlag
     */
    private void generateAddNum(HSSFWorkbook wb, String tpId) {
        String[] titles = { "????????????", "??????", "??????", "??????", "??????", "??????", "??????" };
        HSSFSheet sheet = wb.createSheet("??????????????????????????????");
        int rownum = 0;
        HSSFRow titleRow = sheet.createRow(rownum);
        HSSFCellStyle headStyle = getHeaderStyle(wb);
        HSSFCellStyle redStyle = getRedStyle(wb);
        HSSFCellStyle greenStyle = getGreenStyle(wb);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headStyle);
        }
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 10000);
        CriteriaQuery cq = new CriteriaQuery(TPmFundsBudgetAddendumEntity.class);
        cq.eq("pid", tpId);
        cq.addOrder("num", SortDirection.asc);
        cq.add();
        List<Map<String, Object>> addendumList = this.tPmProjectFundsApprDao.getAddendumFundList(tpId);
        HSSFCellStyle style = getStyle(wb);
        for (Map<String, Object> dataMap : addendumList) {
            HSSFCellStyle textStyle = style;
            rownum++;
            HSSFRow sRow = sheet.createRow(rownum);
            String num = dataMap.get("NUM") == null ? "" : dataMap.get("NUM").toString();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < num.length(); i++) {
                sb.append(" ");
            }
            String addChildFlag = dataMap.get("ADD_CHILD_FLAG") == null ? "" : dataMap.get("ADD_CHILD_FLAG").toString();
            if ("0".equals(addChildFlag)) {
                textStyle = redStyle;
            } else if ("1".equals(addChildFlag)) {
                textStyle = greenStyle;
            }
            HSSFCell cell0 = sRow.createCell(0);
            cell0.setCellValue(sb.toString()
                    + (dataMap.get("CONTENT") == null ? "" : dataMap.get("CONTENT").toString()));
            cell0.setCellStyle(textStyle);
            HSSFCell cell1 = sRow.createCell(1);
            cell1.setCellValue(num);
            cell1.setCellStyle(textStyle);
            HSSFCell cell2 = sRow.createCell(2);
            cell2.setCellValue(dataMap.get("MODEL") == null ? "" : dataMap.get("MODEL").toString());
            cell2.setCellStyle(textStyle);
            HSSFCell cell3 = sRow.createCell(3);
            cell3.setCellValue(dataMap.get("ACCOUNT") == null ? "" : dataMap.get("ACCOUNT").toString());
            cell3.setCellStyle(textStyle);
            HSSFCell cell4 = sRow.createCell(4);
            cell4.setCellValue(dataMap.get("PRICE") == null ? "" : dataMap.get("PRICE").toString());
            cell4.setCellStyle(textStyle);
            HSSFCell cell5 = sRow.createCell(5);
            cell5.setCellValue(dataMap.get("MONEY") == null ? "" : dataMap.get("MONEY").toString());
            cell5.setCellStyle(textStyle);
            HSSFCell cell6 = sRow.createCell(6);
            cell6.setCellValue(dataMap.get("REMARK") == null ? "" : dataMap.get("REMARK").toString());
            cell6.setCellStyle(textStyle);
        }
    }

	@Override
	public void init(TPmProjectFundsApprEntity t) {
	    if(StringUtils.isNotEmpty(t.getIncomeApplyId())){//??????????????????id???????????????????????????????????????????????????
	        TPmIncomeApplyEntity incomeApply = this.commonDao.get(TPmIncomeApplyEntity.class, t.getIncomeApplyId());
	        incomeApply.setFundsFlag("1");
	        this.commonDao.updateEntitie(incomeApply);
	    }
		commonDao.save(t);
		
/*		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, t.getTpId());
		
		if(ProjectConstant.PROJECT_CONTRACT.equals(project.getPlanContractFlag())){
			tPmContractFundsService.init(t.getId());
		}else{
			tPmPlanFundsService.init(t.getId());
		}*/
	}

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //?????????????????????
            TPmProjectFundsApprEntity t = commonDao.get(TPmProjectFundsApprEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getFinishFlag())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //?????????????????????
                t.setFinishFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
                addMateria(t);//???????????????
            }
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     * 
     * @param appr
     */
    private void addMateria(TPmProjectFundsApprEntity appr) {
        String projectId = appr.getTpId();
        TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
        if ("1".equals(project.getPlanContractFlag())) {
            List<TPmPlanFundsEntity> planFundsList = this.commonDao.findByProperty(TPmPlanFundsEntity.class, "apprId",
                    appr.getId());
            for (TPmPlanFundsEntity t : planFundsList) {
                // ??????????????????
                if (StringUtil.isNotEmpty(t.getParent())) {
                    TPmPlanFundsEntity parent = commonDao.get(TPmPlanFundsEntity.class, t.getParent());
                    if (parent != null && "?????????".equals(parent.getContent())) {
                        // ????????????????????????????????????????????????
                        List<TPmMaterialEntity> materials = commonDao.findByProperty(TPmMaterialEntity.class,
                                "materialName", t.getContent());
                        boolean isExist = false;
                        if (materials != null && materials.size() > 0) {
                            for (int i = 0; i < materials.size(); i++) {
                                TPmMaterialEntity material = materials.get(i);
                                // ??????????????????
                                if ((material.getMaterialPrice() == null ? 0 : material.getMaterialPrice())
                                        - (t.getMoney() == null ? 0 : t.getMoney()) == 0) {
                                    isExist = true;
                                    break;
                                }
                            }
                        }
                        if (!isExist) {
                            TPmMaterialEntity material = new TPmMaterialEntity();
                            material.setMaterialName(t.getContent());
                            material.setMaterialPrice(t.getMoney());
                            material.setMaterialModel(t.getModel());
                            material.setMaterialUnit(t.getUnit());
                            material.setMateriaType(SrmipConstants.MATERIA_TYPE_ORIGINAL);//?????????
                            material.setResourceId(t.getId());
                            material.setMaterialResource(SrmipConstants.LRLY_FUNDS);
                            material.setSupplyDate(new Date());
                            material.setProjectId(project.getId());
                            material.setProjectName(project.getProjectName());
                            commonDao.save(material);
                        }
                    }
                }
            }
        } else {
            List<TPmContractFundsEntity> contractFundsList = this.commonDao.findByProperty(
                    TPmContractFundsEntity.class,
                    "apprId", appr.getId());
            for (TPmContractFundsEntity t : contractFundsList) {
                // ??????????????????
                if (StringUtil.isNotEmpty(t.getParent())) {
                    TPmContractFundsEntity parent = commonDao.get(TPmContractFundsEntity.class, t.getParent());
                    if (parent != null && "?????????".equals(parent.getContent())) {
                        // ????????????????????????????????????????????????
                        List<TPmMaterialEntity> materials = commonDao.findByProperty(TPmMaterialEntity.class,
                                "materialName", t.getContent());
                        boolean isExist = false;
                        if (materials != null && materials.size() > 0) {
                            for (int i = 0; i < materials.size(); i++) {
                                TPmMaterialEntity material = materials.get(i);
                                // ??????????????????
                                if ((material.getMaterialPrice() == null ? 0 : material.getMaterialPrice())
                                        - (t.getMoney() == null ? 0 : t.getMoney()) == 0) {
                                    isExist = true;
                                    break;
                                }
                            }
                        }
                        if (!isExist) {
                            TPmMaterialEntity material = new TPmMaterialEntity();
                            material.setMaterialName(t.getContent());
                            material.setMaterialPrice(t.getMoney());
                            material.setResourceId(t.getId());
                            material.setMateriaType(SrmipConstants.MATERIA_TYPE_ORIGINAL);//?????????
                            material.setMaterialResource(SrmipConstants.LRLY_FUNDS);
                            material.setSupplyDate(new Date());
                            material.setProjectId(project.getId());
                            material.setProjectName(project.getProjectName());
                            commonDao.save(material);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void doBack(String id) {
        TPmProjectFundsApprEntity t = commonDao.get(TPmProjectFundsApprEntity.class, id);
        //?????????????????????????????????
        t.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TPmProjectFundsApprEntity entity = this.get(TPmProjectFundsApprEntity.class, id);
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
        String appName = "";
        TPmProjectFundsApprEntity t = commonDao.get(TPmProjectFundsApprEntity.class, id);
        if(t!=null){
            appName = t.getProjectName()+"????????????";
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmProjectFundsApprEntity entity = this.get(TPmProjectFundsApprEntity.class, id);
        if (entity != null) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

    @Override
    public void saveFundsAppr(TPmProjectFundsApprEntity tPmProjectFundsAppr) {
        try {
            if (StringUtil.isEmpty(tPmProjectFundsAppr.getId())) {
                this.init(tPmProjectFundsAppr);
            } else {
                TPmProjectFundsApprEntity t = this.commonDao.get(TPmProjectFundsApprEntity.class,
                        tPmProjectFundsAppr.getId());
                if(StringUtils.isNotEmpty(t.getIncomeApplyId())){
                   TPmIncomeApplyEntity oincomeApply = this.commonDao.get(TPmIncomeApplyEntity.class, t.getIncomeApplyId());
                   oincomeApply.setFundsFlag(null);
                   this.commonDao.updateEntitie(oincomeApply);
                }
                if(StringUtils.isNotEmpty(tPmProjectFundsAppr.getIncomeApplyId())){
                    TPmIncomeApplyEntity tmpIncomeApply = this.commonDao.get(TPmIncomeApplyEntity.class,
                            tPmProjectFundsAppr.getIncomeApplyId());
                    tmpIncomeApply.setFundsFlag("1");
                    this.commonDao.updateEntitie(tmpIncomeApply);
                }
                MyBeanUtils.copyBeanNotNull2Bean(tPmProjectFundsAppr, t);
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

   
	@Override
	public HashMap<String,Object> checkBudget(String projectId) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("checkpass", false);
		//??????????????????-
		TPmProjectEntity lxProject = this.getEntity(TPmProjectEntity.class, projectId);
		String lxyj = lxProject.getLxyj();

		//????????????????????????[???????????????]
		String sql = "select * from t_pm_project_funds_appr where t_p_id='"+projectId+"' and funds_category=1";
    	List<Map<String,Object>> budgetList = this.findForJdbc(sql);
    	int fundsApprCount = budgetList.size();
    	
    	if(fundsApprCount == 0) {
    		StringBuilder sb = new StringBuilder();
    		if("1".equals(lxyj)) {//??????
    			sb.append(" select t2.cw_status,t2.aduit_status from t_pm_fpje t1 ");
    			sb.append(" left join T_PM_PROJECT_PLAN t2 ");
    			sb.append(" on t1.jhwjid = T2.ID ");
    			sb.append(" where t1.xmid = '"+projectId+"' ");
    			sb.append(" and t2.aduit_status is not null ");
    			sb.append(" group by  t2.cw_status,T2.aduit_status ");
    		}else if("2".equals(lxyj)){//??????
    			sb.append(" select cw_status,audit_status from t_pm_income_apply ");
    			sb.append(" where t_p_id ='"+projectId+"' ");
    		}
    		
        	List<Map<String,Object>> resultList = this.findForJdbc(sb.toString());
        	if(CollectionUtils.isEmpty(resultList)) {
        		
        	}else {
        		String cwStatus = MapUtils.getString(resultList.get(0), "cw_status","");
           		String aduit_status = MapUtils.getString(resultList.get(0), "aduit_status","");
           		
           		String operType = null;
           		if("1".equals(lxyj)) {
           			operType ="????????????????????????";
           		}else {
           			operType ="??????????????????";
           		}
           		if(!"2".equals(aduit_status) && !"3".equals(aduit_status)) {
           			result.put("msg",operType+ "?????????????????????.");
           			return result;
           		}else if("3".equals(aduit_status)) {
           			result.put("msg",operType+ "?????????????????????.");
           			return result;
           		}
           		if(!"1".equals(cwStatus) && !"2".equals(cwStatus)) {
           			result.put("msg",operType+ "????????????????????????");
           			return result;
           		}else if("2".equals(cwStatus)) {
           			result.put("msg",operType+ "?????????????????????");
           			return result;
           		}
        	}
    	}
    	
    	
		//????????????????????????????????????????????????,????????????????????????????????????????????????
		String sql0 = "select * from t_pm_project_funds_appr where t_p_id='"+projectId+"' and funds_category=1 and finish_flag in (0,1) ";
    	List budgetList0 = this.findForJdbc(sql0);
    	if(budgetList0.size()>0) {
    		result.put("msg","?????????????????????????????????,???????????????????????????????????????");
   			return result;
    	}
		
		//??????????????????--[??????????????????]
    	Double allFee = lxProject.getAllFee() == null? Double.valueOf("0"):lxProject.getAllFee().doubleValue(); //MapUtils.getDouble(lxProject, "ALL_FEE", Double.valueOf("0"));
    	
    	//??????????????????
    	String sql1 = "select BALANCE_AMOUNT from t_pm_project_balance where  project_no='"+lxProject.getProjectNo()+"'";
     	List<Map<String,Object>> balanceList = this.findForJdbc(sql1);
     	Double freeFee = Double.valueOf("0");
     	if(CollectionUtils.isNotEmpty(balanceList) ) {
     		Map<String,Object> data = balanceList.get(0);
     		freeFee = MapUtils.getDoubleValue(data, "BALANCE_AMOUNT", Double.valueOf("0"));
     	}
    	
    	//????????????????????????[?????????????????????]
    	Double allFunds = Double.valueOf("0");//???????????????????????????
    	if(CollectionUtils.isNotEmpty(budgetList)) {
    		for(Map<String,Object> fundsAppr : budgetList) {
    			Double totalFunds = MapUtils.getDouble(fundsAppr, "TOTAL_FUNDS", Double.valueOf("0"));
    			allFunds = allFunds + totalFunds;
    		}
    	}
    	int fundsType = 0;
    	//?????????????????????
    	if(freeFee == 0 && fundsApprCount ==0) {
    		//??????????????????????????????????????????????????????????????????
    		fundsType = 1;
    	}else if(freeFee >0 && fundsApprCount ==0) {
    		result.put("msg","???????????????,????????????????????????");
   			return result;
    	}else if(fundsApprCount > 0 && allFee>allFunds) {
    		//?????????????????????????????? ?????? ???????????????????????????  ????????????????????????
    		fundsType = 3; 
    	}else{
    		//?????????????????????????????? ???????????? ??????????????????????????? ???????????????????????????
    		fundsType = 4; 
    	}
    	result.put("checkpass", true);
    	result.put("fundsType",fundsType);
		return result;
	}
	
	@Override
	public List getProjectId(String projectId) {
		String sql = "select tzys_status from t_pm_project where id='"+projectId+"'";
    	List projectList = this.findForJdbc(sql);
		return projectList;
	}
	
	@Override
	public int editProjectLjysStatus(String projectId,int tzys_status) {
		String sql = "update t_pm_project set tzys_status="+tzys_status+" where id='"+projectId+"'";
    	int count = this.updateBySqlString(sql);
		return count;
	}

	/**
	 * ??????????????????
	 */
	/* (non-Javadoc)
	 * @see com.kingtake.project.service.funds.TPmProjectFundsApprServiceI#saveFundsDetail(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void saveFundsDetail(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request) {
		String projectId = tPmProjectFundsAppr.getTpId();
		String apprId = tPmProjectFundsAppr.getId();
    	
    	TPmContractFundsEntity entity = null;
    	List<TPmContractFundsEntity> list = new ArrayList<TPmContractFundsEntity>();
    	if(!StringUtil.isEmpty(projectId)){
    		//??????????????????
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select k.budget_category from t_pm_project t ");
    		sql.append(" left join T_PM_BUDGET_FUNDS_REL k on t.fee_type = k.funds_type ");
    		sql.append(" where t.id=? ");
    		List<Map<String, Object>> typeList = this.findForJdbc(sql.toString(), projectId);
    		if(typeList != null && typeList.size() > 0){
    			Object budgetType = typeList.get(0).get("budget_category");
    			String sql2 = " select t.id,t.category_code as SORT_CODE,t.category_name as BUDGET_NAE from T_PM_BUDGET_CATEGORY t where t.CATEGORY_CODE= ? ";
    			List<Map<String, Object>> oneList = this.findForJdbc(sql2, budgetType);
    			if(oneList != null && oneList.size() > 0){
    				for(int i=0;i<oneList.size();i++){
    					Map oneMap = oneList.get(i);
    					String id = String.valueOf(oneMap.get("id"));
    					String sort_code = String.valueOf(oneMap.get("SORT_CODE"));
    					String content = String.valueOf(oneMap.get("BUDGET_NAE"));
    					String fee = request.getParameter(id);
    					double money = Double.valueOf(fee.replace(",", ""));
    					
    					String uuid = UUID.randomUUID().toString().replace("-", "");
    					entity = new TPmContractFundsEntity();
    					entity.setId(uuid);
    					entity.setTpId(projectId);
    					entity.setNum(sort_code);
    					entity.setContentId(id);
    					entity.setContent(content);
    					entity.setMoney(money);
    					entity.settApprId(apprId);
    					//entity.setVariableMoney(money);
    					list.add(entity);
    					
    					Object code = oneMap.get("SORT_CODE");
    					String sql3 = " select t.id,t.category_code_dtl as SORT_CODE,t.detail_name as BUDGET_NAE,DETAIL_SYMBOL from T_PM_BUDGET_CATEGORY_attr t where t.category_code=? ";
    					List<Map<String, Object>> twoList = this.findForJdbc(sql3, code);
    					for(int j=0;j<twoList.size();j++){
    						Map twoMap = twoList.get(j);
    						String sub_id = String.valueOf(twoMap.get("id"));
        					String sub_sort_code = String.valueOf(twoMap.get("SORT_CODE"));
        					String sub_content = String.valueOf(twoMap.get("BUDGET_NAE"));
        					String DETAIL_SYMBOL = MapUtils.getString(twoMap, "DETAIL_SYMBOL","1");
        					String sub_fee = request.getParameter(sub_id);
    						
        					String uuid2 = UUID.randomUUID().toString().replace("-", "");
        					entity = new TPmContractFundsEntity();
        					entity.setId(uuid2);
        					entity.setTpId(projectId);
        					entity.setNum(sub_sort_code);
        					entity.setContentId(sub_id);
        					entity.setContent(sub_content);
        					entity.settApprId(apprId);
        					if(sub_sort_code.length() > 2){
        						entity.setParent(sub_id.substring(0,sub_id.length() - 2));
        					}else{
        						entity.setParent(sort_code);
        					}
        					sub_fee = StringUtil.isNotEmpty(sub_fee)  ? sub_fee : "0";
        					
    						double sub_money = Double.valueOf(sub_fee.replace(",", ""));
    						entity.setMoney(sub_money);
							//entity.setVariableMoney(sub_money);
    						
        					list.add(entity);
    						
    					}
    				}
    				
    			}
    			
    		}
    		this.batchSave(list);
    		
    	}
	}


}