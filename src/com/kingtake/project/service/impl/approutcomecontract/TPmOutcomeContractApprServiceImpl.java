package com.kingtake.project.service.impl.approutcomecontract;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.dao.TPmOutcomeContractApprDao;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.funds.TPmMaterialEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceMaterialEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.approutcomecontract.TPmOutcomeContractApprServiceI;
import com.kingtake.project.service.impl.appr.ApprServiceImpl;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmOutcomeContractApprService")
@Transactional
public class TPmOutcomeContractApprServiceImpl extends ApprServiceImpl implements TPmOutcomeContractApprServiceI,
        ProjectListServiceI ,ApprFlowServiceI{
    @Autowired
    private TPmOutcomeContractApprDao tPmOutcomeContractApprDao;

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//???????????????????????????sql??????
		this.doDelSql((TPmOutcomeContractApprEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//???????????????????????????sql??????
 		this.doAddSql((TPmOutcomeContractApprEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//???????????????????????????sql??????
 		this.doUpdateSql((TPmOutcomeContractApprEntity)entity);
 	}
 	
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmOutcomeContractApprEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmOutcomeContractApprEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmOutcomeContractApprEntity t){
	 	return true;
 	}
 	
 	/**
	 * ??????sql????????????
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmOutcomeContractApprEntity t){
 		sql  = sql.replace("#{standard_outline}",String.valueOf(t.getStandardOutline()));
 		sql  = sql.replace("#{finish_flag}",String.valueOf(t.getFinishFlag()));
 		sql  = sql.replace("#{finish_time}",String.valueOf(t.getFinishTime()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getProject().getId()));
 		sql  = sql.replace("#{apply_unit}",String.valueOf(t.getApplyUnit()));
 		sql  = sql.replace("#{projectname_subjectcode}",String.valueOf(t.getProjectnameSubjectcode()));
 		sql  = sql.replace("#{contract_name}",String.valueOf(t.getContractName()));
 		sql  = sql.replace("#{approval_unit}",String.valueOf(t.getApprovalUnit()));
 		sql  = sql.replace("#{the_third}",String.valueOf(t.getTheContractThird()));
 		sql  = sql.replace("#{start_time}",String.valueOf(t.getStartTime()));
 		sql  = sql.replace("#{end_time}",String.valueOf(t.getEndTime()));
 		sql  = sql.replace("#{total_funds}",String.valueOf(t.getTotalFunds()));
 		sql  = sql.replace("#{acquisition_method}",String.valueOf(t.getAcquisitionMethod()));
 		sql  = sql.replace("#{acquisition_reason}",String.valueOf(t.getAcquisitionReason()));
 		sql  = sql.replace("#{inquiry_member}",String.valueOf(t.getInquiryMember()));
 		sql  = sql.replace("#{technical_manual}",String.valueOf(t.getTechnicalManual()));
 		sql  = sql.replace("#{busi_manage_depart}",String.valueOf(t.getBusiManageDepart()));
 		sql  = sql.replace("#{contract_object}",String.valueOf(t.getContractObject()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_OUTCOME);
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public Workbook getPriceTemplate(TPmOutcomeContractApprEntity tPmOutcomeContractAppr) {
        HSSFWorkbook wb = new HSSFWorkbook();
        generateCover(wb, tPmOutcomeContractAppr);//??????
        if (!ProjectConstant.CONTRACT_BUY.equals(tPmOutcomeContractAppr.getContractType())) {
            generateMaster(wb, tPmOutcomeContractAppr);//????????????
        } else {
            generatePurchase(tPmOutcomeContractAppr, wb);//?????????????????????
        }
        return wb;
    }

    /**
     * ????????????
     * 
     * @param wb
     * @param tPmOutcomeContractAppr
     */
    private void generateCover(HSSFWorkbook wb, TPmOutcomeContractApprEntity tPmOutcomeContractAppr) {
        HSSFSheet sheet = wb.createSheet("??????");
        int rownum = 0;
        HSSFCellStyle titleStyle = getTitleStyle(wb);
        HSSFCellStyle redStyle = getRedStyle(wb);
        HSSFCellStyle pstyle = getStyle(wb);
        HSSFRow row0 = sheet.createRow(rownum);
        HSSFCell mjCell = row0.createCell(8);
        mjCell.setCellValue("?????????");
        HSSFCell mjValCell = row0.createCell(9);
        mjValCell.setCellValue("");
        //?????????????????????
        String[] values = { "??????", "??????", "??????" };
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(values);
        HSSFDataValidation data_validation = new HSSFDataValidation(new CellRangeAddressList(0, 0, 9, 9), constraint);
        sheet.addValidationData(data_validation);
        rownum++;
        HSSFRow row1 = sheet.createRow(rownum);
        HSSFCell bhCell = row1.createCell(8);
        bhCell.setCellValue("?????????");
        HSSFCell bhValCell = row1.createCell(9);
        bhValCell.setCellValue("");
        bhValCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        rownum++;
        rownum++;
        HSSFRow row3 = sheet.createRow(rownum);
        HSSFCell btCell = row3.createCell(2);
        if (ProjectConstant.CONTRACT_PRODUCE.equals(tPmOutcomeContractAppr.getContractType())) {
            btCell.setCellValue("????????????????????????????????????");
        } else if (ProjectConstant.CONTRACT_DEVELOP.equals(tPmOutcomeContractAppr.getContractType())) {
            btCell.setCellValue("??????????????????????????????");
        } else if (ProjectConstant.CONTRACT_BUY.equals(tPmOutcomeContractAppr.getContractType())) {
            btCell.setCellValue("??????????????????????????????");
        }
        btCell.setCellStyle(titleStyle);
        row3.createCell(3).setCellValue("");
        row3.createCell(4).setCellValue("");
        row3.createCell(5).setCellValue("");
        rownum++;
        HSSFRow row4 = sheet.createRow(rownum);
        row4.createCell(2).setCellValue("");
        row4.createCell(3).setCellValue("");
        row4.createCell(4).setCellValue("");
        row4.createCell(5).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 2, 5));
        rownum++;
        rownum++;
        rownum++;
        HSSFRow row7 = sheet.createRow(rownum);
        HSSFCell projectNameCell = row7.createCell(0);//??????????????????
        projectNameCell.setCellValue("???????????????");
        projectNameCell.setCellStyle(pstyle);
        row7.createCell(1).setCellValue("");
        HSSFCell projectNameValueCell = row7.createCell(2);//??????????????????
        projectNameValueCell.setCellStyle(redStyle);
        projectNameValueCell.setCellValue(tPmOutcomeContractAppr.getProjectnameSubjectcode());
        row7.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 3));

        HSSFCell contractNameCell = row7.createCell(4);//??????????????????
        contractNameCell.setCellValue("???????????????");
        contractNameCell.setCellStyle(pstyle);
        row7.createCell(5).setCellValue("");
        HSSFCell contractNameValueCell = row7.createCell(6);//??????????????????
        contractNameValueCell.setCellValue(tPmOutcomeContractAppr.getContractName());
        contractNameValueCell.setCellStyle(redStyle);
        row7.createCell(7).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 7));

        rownum++;
        rownum++;
        HSSFRow row9 = sheet.createRow(rownum);
        HSSFCell partACell = row9.createCell(0);//??????????????????
        partACell.setCellValue("???????????????");
        partACell.setCellStyle(pstyle);
        row9.createCell(1).setCellValue("");
        HSSFCell cell92 = row9.createCell(2);
        cell92.setCellValue("");
        cell92.setCellStyle(redStyle);
        cell92.setCellType(HSSFCell.CELL_TYPE_STRING);
        row9.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 2, 3));

        HSSFCell partBCell = row9.createCell(4);//??????????????????
        partBCell.setCellValue("???????????????");
        partBCell.setCellStyle(pstyle);
        row9.createCell(5).setCellValue("");
        HSSFCell cell96 = row9.createCell(6);
        cell96.setCellValue("");
        cell96.setCellStyle(redStyle);
        cell96.setCellType(HSSFCell.CELL_TYPE_STRING);
        row9.createCell(7).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 6, 7));

        rownum++;
        rownum++;
        HSSFRow row11 = sheet.createRow(rownum);
        HSSFCell priceUserNameCell = row11.createCell(0);//??????????????????
        priceUserNameCell.setCellValue("??????????????????");
        priceUserNameCell.setCellStyle(pstyle);
        row11.createCell(1).setCellValue("");
        HSSFCell cell112 = row11.createCell(2);
        cell112.setCellValue("");
        cell112.setCellStyle(redStyle);
        cell112.setCellType(HSSFCell.CELL_TYPE_STRING);
        row11.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 3));

        HSSFCell priceDateCell = row11.createCell(4);//??????????????????
        priceDateCell.setCellValue("???????????????");
        priceDateCell.setCellStyle(pstyle);
        row11.createCell(5).setCellValue("");
        HSSFCell cell116 = row11.createCell(6);
        cell116.setCellValue("yyyy-MM-dd");
        cell116.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell116.setCellStyle(redStyle);
        row11.createCell(7).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 6, 7));

        rownum++;
        rownum++;
        HSSFRow row13 = sheet.createRow(rownum);
        HSSFCell auditUserNameCell = row13.createCell(0);//??????????????????
        auditUserNameCell.setCellValue("??????????????????");
        auditUserNameCell.setCellStyle(pstyle);
        row13.createCell(1).setCellValue("");
        HSSFCell cell132 = row13.createCell(2);
        cell132.setCellValue("");
        cell132.setCellStyle(redStyle);
        cell132.setCellType(HSSFCell.CELL_TYPE_STRING);
        row13.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 2, 3));

        HSSFCell auditDateCell = row13.createCell(4);//??????????????????
        auditDateCell.setCellValue("???????????????");
        auditDateCell.setCellStyle(pstyle);
        row13.createCell(5).setCellValue("");
        HSSFCell cell136 = row13.createCell(6);
        cell136.setCellValue("yyyy-MM-dd");
        cell136.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell136.setCellStyle(redStyle);
        row13.createCell(7).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 6, 7));

        rownum++;
        rownum++;
        HSSFRow row15 = sheet.createRow(rownum);
        HSSFCell submitDateCell = row15.createCell(0);//??????????????????
        submitDateCell.setCellValue("???????????????");
        submitDateCell.setCellStyle(pstyle);
        row15.createCell(1).setCellValue("");
        HSSFCell cell152 = row15.createCell(2);
        cell152.setCellValue("yyyy-MM-dd");
        cell152.setCellStyle(redStyle);
        cell152.setCellType(HSSFCell.CELL_TYPE_STRING);
        row15.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(15, 15, 2, 3));
        for (int i = 0; i < 7; i++) {
            sheet.setColumnWidth(i, 3000);
        }
    }

    /**
     * ????????????-??????????????????????????????
     * 
     * @param wb
     * @param planContractFlag
     */
    private void generateMaster(HSSFWorkbook wb,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr) {
        String title = "??????????????????????????????";
        HSSFSheet sheet = wb.createSheet(title);
        generateTitle(title, tPmOutcomeContractAppr, sheet, "???");//???????????????????????????
        String[] titles = { "??????", "??????", "?????????", "?????????", "?????????", "?????????", "??????" };
        int rownum = 5;
        HSSFRow titleRow = sheet.createRow(rownum);
        HSSFCellStyle pStyle = getStyle(wb);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(pStyle);
        }
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 5000);
        List<TBApprovalBudgetRelaEntity> budgetRelaList = this.commonDao.findByProperty(
                TBApprovalBudgetRelaEntity.class, "projApproval", tPmOutcomeContractAppr.getContractType());
        List<TBApprovalBudgetRelaEntity> masterList = new ArrayList<TBApprovalBudgetRelaEntity>();
        for(int i = 0; i < budgetRelaList.size(); i++){
            TBApprovalBudgetRelaEntity detail = budgetRelaList.get(i);
            // ????????????null:????????????
            if (detail.getTBApprovalBudgetRelaEntity() == null) {
                masterList.add(detail);
                List<TBApprovalBudgetRelaEntity> children = detail.getTBApprovalBudgetRelaEntitys();
                if(children != null && children.size() > 0){
                    for(int j = 0; j < children.size(); j++){
                        TBApprovalBudgetRelaEntity childDetail = children.get(j);
                        masterList.add(childDetail);
                    }
                }
            }
        }
        if (masterList.size() > 0) {
            for (TBApprovalBudgetRelaEntity entity : masterList) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                HSSFCell cell0 = row.createCell(0);//??????
                cell0.setCellValue(entity.getSortCode());
                cell0.setCellStyle(pStyle);
                HSSFCell cell1 = row.createCell(1);//??????
                cell1.setCellValue(entity.getBudgetNae());
                cell1.setCellStyle(pStyle);
                HSSFCell cell2 = row.createCell(2);//?????????
                cell2.setCellValue("0.00");
                cell2.setCellStyle(pStyle);
                HSSFCell cell3 = row.createCell(3);//?????????
                cell3.setCellValue("0.00");
                cell3.setCellStyle(pStyle);
                HSSFCell cell4 = row.createCell(4);//?????????
                cell4.setCellValue("0.00");
                cell4.setCellStyle(pStyle);
                HSSFCell cell5 = row.createCell(5);//?????????
                cell5.setCellValue("0.00");
                cell5.setCellStyle(pStyle);
                HSSFCell cell6 = row.createCell(6);//??????
                cell6.setCellValue("");
                cell6.setCellStyle(pStyle);
                cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
            
            generateDetail(masterList, tPmOutcomeContractAppr, wb);//????????????
        }
    }
    
    /**
     * ????????????
     * 
     * @param masterList
     */
    private void generateDetail(List<TBApprovalBudgetRelaEntity> masterList,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HSSFWorkbook wb) {
        List<TBApprovalBudgetRelaEntity> mergeEntityList = new ArrayList<TBApprovalBudgetRelaEntity>();
        for (TBApprovalBudgetRelaEntity entity : masterList) {
            if (StringUtils.isNotEmpty(entity.getShowFlag())) {
                if (entity.getTBApprovalBudgetRelaEntity() == null) {
                    mergeEntityList.add(entity);
                } else {
                    generateTable(entity, tPmOutcomeContractAppr, wb);
                }
            }
        }
        if (mergeEntityList.size() > 0) {
            generateProfit(mergeEntityList, tPmOutcomeContractAppr, wb);//????????????????????????
        }
    }

    /**
     * ????????????????????????
     * 
     * @param showFlag
     * @param sheet
     */
    private void generateTitle(String title, TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HSSFSheet sheet,
            String unit) {
        CellStyle pStyle = getStyle(sheet.getWorkbook());
        HSSFRow row1 = sheet.createRow(1);
        HSSFCell titleCell = row1.createCell(4);
        HSSFCellStyle titleStyle = getTitleStyle(sheet.getWorkbook());
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 6));
        HSSFRow secondTitleRow = sheet.createRow(4);
        HSSFCell contractNameCell = secondTitleRow.createCell(0);
        contractNameCell.setCellValue("???????????????" + tPmOutcomeContractAppr.getContractName());
        contractNameCell.setCellStyle(pStyle);
        HSSFCell unitCell = secondTitleRow.createCell(9);
        unitCell.setCellValue("???????????????" + unit);
        unitCell.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 9, 10));
    }

    /**
     * ????????????
     * 
     * @param title
     * @param tPmOutcomeContractAppr
     * @param sheet
     */
    private void generateTable(TBApprovalBudgetRelaEntity entity,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HSSFWorkbook wb) {
        switch (entity.getShowFlag()) {
        case "1":
            generateManage(entity, tPmOutcomeContractAppr, wb);
            break;
        case "2":
            generateMaterial(entity, tPmOutcomeContractAppr, wb);
            break;
        case "3":
            generateSalary(entity, tPmOutcomeContractAppr, wb);
            break;
        case "5":
            generateDesign(entity, tPmOutcomeContractAppr, wb);
            break;
        case "6":
            generateOutCorp(entity, tPmOutcomeContractAppr, wb);
            break;
        case "7":
            generateTrial(entity, tPmOutcomeContractAppr, wb);
            break;

        }
    }

    //?????????
    private void generateManage(TBApprovalBudgetRelaEntity entity, TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        String[] titles = { "??????","??????","????????????","?????????","?????????","??????" };
        int rownum = 5;
        HSSFRow headRow = sheet.createRow(rownum);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = headRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(pStyle);
        }
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 5000);
        List<TBApprovalBudgetRelaEntity> list = new ArrayList<TBApprovalBudgetRelaEntity>();
        list.add(entity);
        List<TBApprovalBudgetRelaEntity> subList = entity.getTBApprovalBudgetRelaEntitys();
        if (subList != null && subList.size() > 0) {
            list.addAll(subList);
        }
        for (TBApprovalBudgetRelaEntity relaEntity : list) {
            rownum++;
            HSSFRow row = sheet.createRow(rownum);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(relaEntity.getSortCode());
            cell0.setCellStyle(pStyle);
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(relaEntity.getBudgetNae());
            cell1.setCellStyle(pStyle);
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue("");
            cell2.setCellStyle(pStyle);
            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue("0.00");
            cell3.setCellStyle(pStyle);
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue("0.00");
            cell4.setCellStyle(pStyle);
            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue("");
            cell5.setCellStyle(pStyle);
            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);

        if ("1".equals(relaEntity.getAddChildFlag())) {
            rownum++;
            HSSFRow sRow = sheet.createRow(rownum);
            HSSFCell cell = sRow.createCell(0);
            cell.setCellValue("???????????????");
            cell.setCellStyle(summaryStyle);
                for (int i = 1; i < titles.length; i++) {
                HSSFCell celli = sRow.createCell(i);
                celli.setCellValue("");
                celli.setCellStyle(summaryStyle);
                    celli.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
        }
        }
        generateRemark(entity, sheet);//????????????

    }

    //?????????
    private void generateMaterial(TBApprovalBudgetRelaEntity entity,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("??????");
        cell1.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("??????");
        cell2.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));
        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("????????????");
        cell3.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 3, 3));
        HSSFCell cell4 = head1Row.createCell(4);
        cell4.setCellValue("????????????");
        cell4.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 4, 4));
        HSSFCell cell5 = head1Row.createCell(5);
        cell5.setCellValue("????????????");
        cell5.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 5, 5));

        HSSFCell cell6 = head1Row.createCell(6);
        cell6.setCellValue("?????????");
        cell6.setCellStyle(pStyle);
        HSSFCell cell66 = head2Row.createCell(6);
        cell66.setCellValue("????????????");
        cell66.setCellStyle(pStyle);
        HSSFCell cell67 = head2Row.createCell(7);
        cell67.setCellValue("????????????");
        cell67.setCellStyle(pStyle);
        HSSFCell cell68 = head2Row.createCell(8);
        cell68.setCellValue("??????");
        cell68.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 8));

        HSSFCell cell9 = head1Row.createCell(9);
        cell9.setCellValue("?????????");
        cell9.setCellStyle(pStyle);
        HSSFCell cell69 = head2Row.createCell(9);
        cell69.setCellValue("????????????");
        cell69.setCellStyle(pStyle);
        HSSFCell cell610 = head2Row.createCell(10);
        cell610.setCellValue("????????????");
        cell610.setCellStyle(pStyle);
        HSSFCell cell611 = head2Row.createCell(11);
        cell611.setCellValue("??????");
        cell611.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 9, 11));

        HSSFCell cell12 = head1Row.createCell(12);
        cell12.setCellValue("??????");
        cell12.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 12, 12));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 3000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 3000);
        sheet.setColumnWidth(12, 5000);
        List<TBApprovalBudgetRelaEntity> subList = entity.getTBApprovalBudgetRelaEntitys();
        if (subList != null && subList.size() > 0) {
            for (TBApprovalBudgetRelaEntity subEntity : subList) {
            rownum++;
            HSSFRow row = sheet.createRow(rownum);
            HSSFCell subcell0 = row.createCell(0);
                subcell0.setCellValue(subEntity.getSortCode());
                subcell0.setCellStyle(pStyle);
            HSSFCell subcell1 = row.createCell(1);
                subcell1.setCellValue(subEntity.getBudgetNae());
                subcell1.setCellStyle(pStyle);
            HSSFCell subcell2 = row.createCell(2);
                subcell2.setCellValue("");
                subcell2.setCellStyle(pStyle);
                subcell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell subcell3 = row.createCell(3);
                subcell3.setCellValue("");
                subcell3.setCellStyle(pStyle);
                subcell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell4 = row.createCell(4);
                subcell4.setCellValue("");
                subcell4.setCellStyle(pStyle);
                subcell4.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell5 = row.createCell(5);
                subcell5.setCellValue("");
                subcell5.setCellStyle(pStyle);
                subcell5.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell6 = row.createCell(6);
                subcell6.setCellValue("0");
                subcell6.setCellStyle(pStyle);
                HSSFCell subcell7 = row.createCell(7);
                subcell7.setCellValue("0.00");
                subcell7.setCellStyle(pStyle);
                HSSFCell subcell8 = row.createCell(8);
                subcell8.setCellValue("0.00");
                subcell8.setCellStyle(pStyle);
                HSSFCell subcell9 = row.createCell(9);
                subcell9.setCellValue("0");
                subcell9.setCellStyle(pStyle);
                HSSFCell subcell10 = row.createCell(10);
                subcell10.setCellValue("0.00");
                subcell10.setCellStyle(pStyle);
                HSSFCell subcell11 = row.createCell(11);
                subcell11.setCellValue("0.00");
                subcell11.setCellStyle(pStyle);
                HSSFCell subcell12 = row.createCell(12);
                subcell12.setCellValue("");
                subcell12.setCellStyle(pStyle);
                subcell12.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ("1".equals(subEntity.getAddChildFlag())) {
                rownum++;
                HSSFRow sRow = sheet.createRow(rownum);
                HSSFCell cell = sRow.createCell(0);
                cell.setCellValue("???????????????");
                cell.setCellStyle(summaryStyle);
                    for (int i = 1; i <= 12; i++) {
                    HSSFCell celli = sRow.createCell(i);
                    celli.setCellValue("");
                    celli.setCellStyle(summaryStyle);
                        celli.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
            }
        }
        }
        generateRemark(entity, sheet);//????????????
    }

    //?????????
    private void generateSalary(TBApprovalBudgetRelaEntity entity, TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("??????/??????");
        cell1.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("????????????");
        cell2.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));

        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("?????????");
        cell3.setCellStyle(pStyle);
        HSSFCell cell63 = head2Row.createCell(3);
        cell63.setCellValue("????????????");
        cell63.setCellStyle(pStyle);
        HSSFCell cell64 = head2Row.createCell(4);
        cell64.setCellValue("????????????");
        cell64.setCellStyle(pStyle);
        HSSFCell cell65 = head2Row.createCell(5);
        cell65.setCellValue("???/??????");
        cell65.setCellStyle(pStyle);
        HSSFCell cell66 = head2Row.createCell(6);
        cell66.setCellValue("??????");
        cell66.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 6));

        HSSFCell cell7 = head1Row.createCell(7);
        cell7.setCellValue("?????????");
        cell7.setCellStyle(pStyle);
        HSSFCell cell67 = head2Row.createCell(7);
        cell67.setCellValue("????????????");
        cell67.setCellStyle(pStyle);
        HSSFCell cell68 = head2Row.createCell(8);
        cell68.setCellValue("????????????");
        cell68.setCellStyle(pStyle);
        HSSFCell cell69 = head2Row.createCell(9);
        cell69.setCellValue("???/??????");
        cell69.setCellStyle(pStyle);
        HSSFCell cell610 = head2Row.createCell(10);
        cell610.setCellValue("??????");
        cell610.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 10));

        HSSFCell cell11 = head1Row.createCell(11);
        cell11.setCellValue("??????");
        cell11.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 11, 11));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 5000);
        List<TBApprovalBudgetRelaEntity> list = new ArrayList<TBApprovalBudgetRelaEntity>();
        list.add(entity);
        List<TBApprovalBudgetRelaEntity> subList = entity.getTBApprovalBudgetRelaEntitys();
        if (subList != null && subList.size() > 0) {
            list.addAll(subList);
        }
        for (TBApprovalBudgetRelaEntity subEntity : list) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                HSSFCell subcell0 = row.createCell(0);
                subcell0.setCellValue(subEntity.getSortCode());
            subcell0.setCellStyle(pStyle);
            HSSFCell subcell1 = row.createCell(1);
            subcell1.setCellValue("");
            subcell1.setCellStyle(pStyle);
            subcell1.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell subcell2 = row.createCell(2);
            subcell2.setCellValue("");
            subcell2.setCellStyle(pStyle);
            subcell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell subcell3 = row.createCell(3);
            subcell3.setCellValue("0");
            subcell3.setCellStyle(pStyle);
            HSSFCell subcell4 = row.createCell(4);
            subcell4.setCellValue("0");
            subcell4.setCellStyle(pStyle);
            HSSFCell subcell5 = row.createCell(5);
            subcell5.setCellValue("0.00");
            subcell5.setCellStyle(pStyle);
            HSSFCell subcell6 = row.createCell(6);
            subcell6.setCellValue("0.00");
            subcell6.setCellStyle(pStyle);
            HSSFCell subcell7 = row.createCell(7);
            subcell7.setCellValue("0");
            subcell7.setCellStyle(pStyle);
            HSSFCell subcell8 = row.createCell(8);
            subcell8.setCellValue("0");
            subcell8.setCellStyle(pStyle);
            HSSFCell subcell9 = row.createCell(9);
            subcell9.setCellValue("0.00");
            subcell9.setCellStyle(pStyle);
            HSSFCell subcell10 = row.createCell(10);
            subcell10.setCellValue("0.00");
            subcell10.setCellStyle(pStyle);
            HSSFCell subcell11 = row.createCell(11);
            subcell11.setCellValue("");
            subcell11.setCellStyle(pStyle);
            subcell11.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ("1".equals(subEntity.getAddChildFlag())) {
                    rownum++;
                    HSSFRow sRow = sheet.createRow(rownum);
                    HSSFCell cell = sRow.createCell(0);
                    cell.setCellValue("???????????????");
                    cell.setCellStyle(summaryStyle);
                for (int i = 1; i <= 11; i++) {
                        HSSFCell celli = sRow.createCell(i);
                        celli.setCellValue("");
                        celli.setCellStyle(summaryStyle);
                    celli.setCellType(HSSFCell.CELL_TYPE_STRING);
                    }
                }
        }
        generateRemark(entity, sheet);//????????????
    }

    //?????????
    private void generateProfit(List<TBApprovalBudgetRelaEntity> entityList,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle pStyle = getStyle(wb);
        CellStyle summaryStyle = getSummaryStyle(wb);
        String budgetName = "";
        if (entityList.size() > 1) {
            for(TBApprovalBudgetRelaEntity entity :entityList){
                budgetName = budgetName + "???" + entity.getBudgetNae();
            }
            budgetName = budgetName.substring(1, budgetName.length());
        } else {
            budgetName = entityList.get(0).getBudgetNae();
        }
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("??????");
        cell1.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("??????????????????");
        cell2.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));

        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("?????????");
        cell3.setCellStyle(pStyle);
        HSSFCell cell63 = head2Row.createCell(3);
        cell63.setCellValue("?????????(%)");
        cell63.setCellStyle(pStyle);
        HSSFCell cell64 = head2Row.createCell(4);
        cell64.setCellValue("??????");
        cell64.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 4));

        HSSFCell cell5 = head1Row.createCell(5);
        cell5.setCellValue("?????????");
        cell5.setCellStyle(pStyle);
        HSSFCell cell65 = head2Row.createCell(5);
        cell65.setCellValue("?????????(%)");
        cell65.setCellStyle(pStyle);
        HSSFCell cell66 = head2Row.createCell(6);
        cell66.setCellValue("??????");
        cell66.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 5, 6));

        HSSFCell cell7 = head1Row.createCell(7);
        cell7.setCellValue("??????");
        cell7.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 7, 7));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 5000);
        for (TBApprovalBudgetRelaEntity entity : entityList) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                HSSFCell subcell0 = row.createCell(0);
                subcell0.setCellValue(entity.getSortCode());
            subcell0.setCellStyle(pStyle);
                HSSFCell subcell1 = row.createCell(1);
                subcell1.setCellValue(entity.getBudgetNae());
            subcell1.setCellStyle(pStyle);
                HSSFCell subcell2 = row.createCell(2);
                subcell2.setCellValue("");
                subcell2.setCellStyle(pStyle);
            subcell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell3 = row.createCell(3);
                subcell3.setCellValue("");
                subcell3.setCellStyle(pStyle);
            subcell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell4 = row.createCell(4);
                subcell4.setCellValue("0.00");
                subcell4.setCellStyle(pStyle);
                HSSFCell subcell5 = row.createCell(5);
                subcell5.setCellValue("");
                subcell5.setCellStyle(pStyle);
            subcell5.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell6 = row.createCell(6);
                subcell6.setCellValue("0.00");
                subcell6.setCellStyle(pStyle);
                HSSFCell subcell7 = row.createCell(7);
                subcell7.setCellValue("");
                subcell7.setCellStyle(pStyle);
            subcell7.setCellType(HSSFCell.CELL_TYPE_STRING);
            if ("1".equals(entity.getAddChildFlag())) {
                rownum++;
                HSSFRow sRow = sheet.createRow(rownum);
                HSSFCell cell = sRow.createCell(0);
                cell.setCellValue("???????????????");
                cell.setCellStyle(summaryStyle);
                    for (int i = 1; i <= 7; i++) {
                    HSSFCell celli = sRow.createCell(i);
                    celli.setCellValue("");
                    celli.setCellStyle(summaryStyle);
                    celli.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
            }
        }
    }

    //?????????
    private void generateDesign(TBApprovalBudgetRelaEntity entity, TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("??????");
        cell1.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("????????????");
        cell2.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));

        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("?????????");
        cell3.setCellStyle(pStyle);
        HSSFCell cell63 = head2Row.createCell(3);
        cell63.setCellValue("????????????");
        cell63.setCellStyle(pStyle);
        HSSFCell cell64 = head2Row.createCell(4);
        cell64.setCellValue("????????????");
        cell64.setCellStyle(pStyle);
        HSSFCell cell65 = head2Row.createCell(5);
        cell65.setCellValue("????????????");
        cell65.setCellStyle(pStyle);
        HSSFCell cell66 = head2Row.createCell(6);
        cell66.setCellValue("??????");
        cell66.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 6));

        HSSFCell cell7 = head1Row.createCell(7);
        cell7.setCellValue("?????????");
        cell7.setCellStyle(pStyle);
        HSSFCell cell67 = head2Row.createCell(7);
        cell67.setCellValue("????????????");
        cell67.setCellStyle(pStyle);
        HSSFCell cell68 = head2Row.createCell(8);
        cell68.setCellValue("????????????");
        cell68.setCellStyle(pStyle);
        HSSFCell cell69 = head2Row.createCell(9);
        cell69.setCellValue("????????????");
        cell69.setCellStyle(pStyle);
        HSSFCell cell610 = head2Row.createCell(10);
        cell610.setCellValue("??????");
        cell610.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 10));

        HSSFCell cell11 = head1Row.createCell(11);
        cell11.setCellValue("??????");
        cell11.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 11, 11));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 4000);
        sheet.setColumnWidth(11, 5000);
        List<TBApprovalBudgetRelaEntity> subList = entity.getTBApprovalBudgetRelaEntitys();
        if (subList != null && subList.size() > 0) {
        for (TBApprovalBudgetRelaEntity subEntity : subList) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                HSSFCell subcell0 = row.createCell(0);
                subcell0.setCellValue(subEntity.getSortCode());
                subcell0.setCellStyle(pStyle);
                HSSFCell subcell1 = row.createCell(1);
                subcell1.setCellValue(subEntity.getBudgetNae());
                subcell1.setCellStyle(pStyle);
                HSSFCell subcell2 = row.createCell(2);
                subcell2.setCellValue("");
                subcell2.setCellStyle(pStyle);
                subcell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell3 = row.createCell(3);
                subcell3.setCellValue("");
                subcell3.setCellStyle(pStyle);
                subcell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell4 = row.createCell(4);
                subcell4.setCellValue("0");
                subcell4.setCellStyle(pStyle);
                HSSFCell subcell5 = row.createCell(5);
                subcell5.setCellValue("0.00");
                subcell5.setCellStyle(pStyle);
                HSSFCell subcell6 = row.createCell(6);
                subcell6.setCellValue("0.00");
                subcell6.setCellStyle(pStyle);
                HSSFCell subcell7 = row.createCell(7);
                subcell7.setCellValue("");
                subcell7.setCellStyle(pStyle);
                subcell7.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCell subcell8 = row.createCell(8);
                subcell8.setCellValue("0");
                subcell8.setCellStyle(pStyle);
                HSSFCell subcell9 = row.createCell(9);
                subcell9.setCellValue("0.00");
                subcell9.setCellStyle(pStyle);
                HSSFCell subcell10 = row.createCell(10);
                subcell10.setCellValue("0.00");
                subcell10.setCellStyle(pStyle);
                HSSFCell subcell11 = row.createCell(11);
                subcell11.setCellValue("");
                subcell11.setCellStyle(pStyle);
                subcell11.setCellType(HSSFCell.CELL_TYPE_STRING);
                if ("1".equals(subEntity.getAddChildFlag())) {
                    rownum++;
                    HSSFRow sRow = sheet.createRow(rownum);
                    HSSFCell cell = sRow.createCell(0);
                    cell.setCellValue("???????????????");
                    cell.setCellStyle(summaryStyle);
                    for (int i = 1; i <= 11; i++) {
                        HSSFCell celli = sRow.createCell(i);
                        celli.setCellValue("");
                        celli.setCellStyle(summaryStyle);
                        celli.setCellType(HSSFCell.CELL_TYPE_STRING);
                    }
                }
        }
        }
        generateRemark(entity, sheet);//????????????
    }

    //?????????
    private void generateOutCorp(TBApprovalBudgetRelaEntity entity,
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("???????????????");
        cell1.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("????????????");
        cell2.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));
        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("??????????????????");
        cell3.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 3, 3));

        HSSFCell cell4 = head1Row.createCell(4);
        cell4.setCellValue("?????????");
        cell4.setCellStyle(pStyle);
        HSSFCell cell64 = head2Row.createCell(4);
        cell64.setCellValue("????????????");
        cell64.setCellStyle(pStyle);
        HSSFCell cell65 = head2Row.createCell(5);
        cell65.setCellValue("????????????");
        cell65.setCellStyle(pStyle);
        HSSFCell cell66 = head2Row.createCell(6);
        cell66.setCellValue("??????");
        cell66.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 6));

        HSSFCell cell7 = head1Row.createCell(7);
        cell7.setCellValue("?????????");
        cell7.setCellStyle(pStyle);
        HSSFCell cell67 = head2Row.createCell(7);
        cell67.setCellValue("????????????");
        cell67.setCellStyle(pStyle);
        HSSFCell cell68 = head2Row.createCell(8);
        cell68.setCellValue("????????????");
        cell68.setCellStyle(pStyle);
        HSSFCell cell69 = head2Row.createCell(9);
        cell69.setCellValue("??????");
        cell69.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 9));

        HSSFCell cell10 = head1Row.createCell(10);
        cell10.setCellValue("??????");
        cell10.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 10, 10));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 4000);
        sheet.setColumnWidth(9, 3000);
        sheet.setColumnWidth(10, 5000);
        rownum++;
        HSSFRow vRow = sheet.createRow(rownum);
        HSSFCell vcell0 = vRow.createCell(0);
        vcell0.setCellValue(entity.getSortCode());
        vcell0.setCellStyle(pStyle);
        HSSFCell vcell1 = vRow.createCell(1);
        vcell1.setCellValue("");
        vcell1.setCellStyle(pStyle);
        vcell1.setCellType(HSSFCell.CELL_TYPE_STRING);
        HSSFCell vcell2 = vRow.createCell(2);
        vcell2.setCellValue("");
        vcell2.setCellStyle(pStyle);
        vcell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        HSSFCell vcell3 = vRow.createCell(3);
        vcell3.setCellValue("");
        vcell3.setCellStyle(pStyle);
        vcell3.setCellType(HSSFCell.CELL_TYPE_STRING);
        HSSFCell vcell4 = vRow.createCell(4);
        vcell4.setCellValue("0");
        vcell4.setCellStyle(pStyle);
        HSSFCell vcell5 = vRow.createCell(5);
        vcell5.setCellValue("0.00");
        vcell5.setCellStyle(pStyle);
        HSSFCell vcell6 = vRow.createCell(6);
        vcell6.setCellValue("0.00");
        vcell6.setCellStyle(pStyle);
        HSSFCell vcell7 = vRow.createCell(7);
        vcell7.setCellValue("0");
        vcell7.setCellStyle(pStyle);
        HSSFCell vcell8 = vRow.createCell(8);
        vcell8.setCellValue("0.00");
        vcell8.setCellStyle(pStyle);
        HSSFCell vcell9 = vRow.createCell(9);
        vcell9.setCellValue("0.00");
        vcell9.setCellStyle(pStyle);
        HSSFCell vcell10 = vRow.createCell(10);
        vcell10.setCellValue("");
        vcell10.setCellStyle(pStyle);
        vcell10.setCellType(HSSFCell.CELL_TYPE_STRING);

        rownum++;
        HSSFRow sRow = sheet.createRow(rownum);
        HSSFCell cell = sRow.createCell(0);
        cell.setCellValue("???????????????");
        cell.setCellStyle(summaryStyle);
        for (int i = 1; i <= 12; i++) {
            HSSFCell celli = sRow.createCell(i);
            celli.setCellValue("");
            celli.setCellStyle(summaryStyle);
            celli.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
        generateRemark(entity, sheet);//????????????
    }

    //?????????
    private void generateTrial(TBApprovalBudgetRelaEntity entity, TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        CellStyle pStyle = getStyle(wb);
        String budgetName = entity.getBudgetNae();
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName + "?????????", tPmOutcomeContractAppr, sheet, "???");//????????????
        String[] titles = { "??????", "??????", "????????????", "????????????", "??????????????????/?????? ", "????????? ", "?????????", "??????" };
        int rownum = 5;
        HSSFRow headRow = sheet.createRow(rownum);
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = headRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(pStyle);
        }
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 5000);
        List<TBApprovalBudgetRelaEntity> list = new ArrayList<TBApprovalBudgetRelaEntity>();
        list.add(entity);
        List<TBApprovalBudgetRelaEntity> subList = entity.getTBApprovalBudgetRelaEntitys();
        if (subList != null && subList.size() > 0) {
            list.addAll(subList);
        }
        for (TBApprovalBudgetRelaEntity relaEntity : list) {
            rownum++;
            HSSFRow row = sheet.createRow(rownum);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(relaEntity.getSortCode());
            cell0.setCellStyle(pStyle);
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(relaEntity.getBudgetNae());
            cell1.setCellStyle(pStyle);
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue("");
            cell2.setCellStyle(pStyle);
            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue("");
            cell3.setCellStyle(pStyle);
            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue("");
            cell4.setCellStyle(pStyle);
            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFCell cell5 = row.createCell(5);
            cell5.setCellValue("0.00");
            cell5.setCellStyle(pStyle);
            HSSFCell cell6 = row.createCell(6);
            cell6.setCellValue("0.00");
            cell6.setCellStyle(pStyle);
            HSSFCell cell7 = row.createCell(7);
            cell7.setCellValue("");
            cell7.setCellStyle(pStyle);
            cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
            if ("1".equals(relaEntity.getAddChildFlag())) {
                rownum++;
                HSSFRow sRow = sheet.createRow(rownum);
                HSSFCell cell = sRow.createCell(0);
                cell.setCellValue("???????????????");
                cell.setCellStyle(summaryStyle);
                for (int i = 1; i < titles.length; i++) {
                    HSSFCell celli = sRow.createCell(i);
                    celli.setCellValue("");
                    celli.setCellStyle(summaryStyle);
                    celli.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
            }
        }
        generateRemark(entity, sheet);//????????????
    }

    /**
     * ????????????????????????????????????
     * 
     * @param entity
     * @param tPmOutcomeContractAppr
     * @param wb
     */
    private void generatePurchase(TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            HSSFWorkbook wb) {
        CellStyle summaryStyle = getSummaryStyle(wb);
        HSSFCellStyle pStyle = getStyle(wb);
        String budgetName = "????????????????????????????????????";
        HSSFSheet sheet = wb.createSheet(budgetName);
        generateTitle(budgetName, tPmOutcomeContractAppr, sheet, "???");//????????????
        int rownum = 5;
        HSSFRow head1Row = sheet.createRow(rownum);
        rownum++;
        HSSFRow head2Row = sheet.createRow(rownum);
        HSSFCell cell0 = head1Row.createCell(0);
        cell0.setCellValue("??????");
        cell0.setCellStyle(pStyle);
        head2Row.createCell(0).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        HSSFCell cell1 = head1Row.createCell(1);
        cell1.setCellValue("??????");
        cell1.setCellStyle(pStyle);
        head2Row.createCell(1).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        HSSFCell cell2 = head1Row.createCell(2);
        cell2.setCellValue("??????");
        cell2.setCellStyle(pStyle);
        head2Row.createCell(2).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));
        HSSFCell cell3 = head1Row.createCell(3);
        cell3.setCellValue("?????????????????????");
        cell3.setCellStyle(pStyle);
        head2Row.createCell(3).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 3, 3));
        HSSFCell cell4 = head1Row.createCell(4);
        cell4.setCellValue("????????????");
        cell4.setCellStyle(pStyle);
        head2Row.createCell(4).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 4, 4));
        HSSFCell cell5 = head1Row.createCell(5);
        cell5.setCellValue("????????????");
        cell5.setCellStyle(pStyle);
        head2Row.createCell(5).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 5, 5));
        HSSFCell cell6 = head1Row.createCell(6);
        cell6.setCellValue("?????? ");
        cell6.setCellStyle(pStyle);
        head2Row.createCell(6).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 6, 6));

        HSSFCell cell7 = head1Row.createCell(7);
        cell7.setCellValue("?????????");
        cell7.setCellStyle(pStyle);
        head1Row.createCell(8).setCellValue("");
        HSSFCell cell67 = head2Row.createCell(7);
        cell67.setCellValue("??????");
        cell67.setCellStyle(pStyle);
        HSSFCell cell68 = head2Row.createCell(8);
        cell68.setCellValue("??????");
        cell68.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 8));

        HSSFCell cell9 = head1Row.createCell(9);
        cell9.setCellValue("?????????");
        cell9.setCellStyle(pStyle);
        head1Row.createCell(10).setCellValue("");
        HSSFCell cell69 = head2Row.createCell(9);
        cell69.setCellValue("??????");
        cell69.setCellStyle(pStyle);
        HSSFCell cell610 = head2Row.createCell(10);
        cell610.setCellValue("??????");
        cell610.setCellStyle(pStyle);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 9, 10));

        HSSFCell cell11 = head1Row.createCell(11);
        cell11.setCellValue("??????");
        cell11.setCellStyle(pStyle);
        head2Row.createCell(11).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 11, 11));
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 3000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 5000);
        rownum++;
        HSSFRow sRow = sheet.createRow(rownum);
        HSSFCell cell = sRow.createCell(0);
        cell.setCellValue("???????????????");
        cell.setCellStyle(summaryStyle);
        String[] values = getCodeDetails();//???????????????
        for (int i = 1; i <= 11; i++) {
            HSSFCell celli = sRow.createCell(i);
            celli.setCellValue("");
            celli.setCellStyle(summaryStyle);
            celli.setCellType(HSSFCell.CELL_TYPE_STRING);
            //??????????????????
            DVConstraint constraint = DVConstraint.createExplicitListConstraint(values);
            HSSFDataValidation data_validation = new HSSFDataValidation(new CellRangeAddressList(7, 7, 1, 1),
                    constraint);
            sheet.addValidationData(data_validation);
        }
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    private String[] getCodeDetails() {
        //?????????????????????
        TBCodeTypeEntity typeEntity = new TBCodeTypeEntity();
        typeEntity.setCodeType("1");
        typeEntity.setCode("CGZL");//????????????
        List<String> valList = new ArrayList<String>();
        List<TBCodeDetailEntity> list = tBCodeTypeService.getCodeDetailByCodeType(typeEntity);
        String[] values = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            values[i] = list.get(i).getName();
        }
        return values;
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

    private HSSFCellStyle getRemarkStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setFontName("??????");
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * ????????????
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

private HSSFCellStyle getSummaryStyle(HSSFWorkbook workbook) {
    HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    Font font = workbook.createFont();
    font.setFontHeightInPoints((short) 13);
    font.setColor(HSSFColor.RED.index);
    font.setFontName("??????");
        style.setWrapText(false);
    style.setFont(font);
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
        titleStyle.setWrapText(false);
    titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
    titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return titleStyle;
}

    /**
     * ?????????Style
     * 
     * @param workbook
     * @return
     */
    public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        font.setBoldweight((short) 26);
        font.setFontName("??????");
        titleStyle.setFont(font);
        titleStyle.setWrapText(false);
        titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return titleStyle;
    }

    /**
     * ???????????????
     * 
     * @param row
     * @param column
     * @param value
     * @return
     */
    private HSSFCell addCell(HSSFRow row, int column, String value, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        if (style == null) {
            style = getStyle(row.getSheet().getWorkbook());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * ????????????
     * 
     * @param entity
     * @param wb
     */
    private void generateRemark(TBApprovalBudgetRelaEntity entity, HSSFSheet sheet) {
        HSSFCellStyle pStyle = getRemarkStyle(sheet.getWorkbook());
        String remark = entity.getMemo();
        int lastRow = sheet.getLastRowNum();
        if (lastRow < 25) {
            lastRow = 25;
        }
        HSSFRow row1 = sheet.createRow(lastRow);
        HSSFCell cell10 = row1.createCell(0);
        cell10.setCellStyle(pStyle);
        cell10.setCellValue(remark);
        sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow + 3, 0, 11));
    }
    


    /**
     * ??????????????????????????????????????????????????????
     */
    private void addMaterial(TPmOutcomeContractApprEntity appr) {
        if ("3".equals(appr.getContractType()) || "4".equals(appr.getContractType())) {
            List<TPmContractPriceCoverEntity> priceCoverList = this.commonDao.findByProperty(
                    TPmContractPriceCoverEntity.class, "contractId", appr.getId());
            if (priceCoverList.size() > 0) {
                TPmContractPriceCoverEntity priceCover = priceCoverList.get(0);
                List<TPmContractPriceMaterialEntity> materialList = this.commonDao.findByProperty(
                        TPmContractPriceMaterialEntity.class, "tpId", priceCover.getId());
                for (TPmContractPriceMaterialEntity t : materialList) {
                    // ??????????????????
                    if (StringUtil.isNotEmpty(t.getParentTypeid())) {
                        TPmContractPriceMaterialEntity parent = commonDao.get(TPmContractPriceMaterialEntity.class,
                                t.getParentTypeid());
                        if (parent != null && "?????????".equals(parent.getTypeName())) {
                            // ????????????????????????????????????????????????
                            List<TPmMaterialEntity> materials = commonDao.findByProperty(TPmMaterialEntity.class,
                                    "materialName", t.getPriceName());
                            boolean isExist = false;
                            if (materials != null && materials.size() > 0) {
                                for (int i = 0; i < materials.size(); i++) {
                                    TPmMaterialEntity material = materials.get(i);
                                    // ???????????????????????????
                                    if (StringUtil.equals(material.getMaterialModel(), t.getProdModel())
                                    // ??????????????????
                                            && (material.getMaterialPrice() == null ? 0 : material.getMaterialPrice())
                                                    - (t.getPriceAuditUnitprice() == null ? 0 : t
                                                            .getPriceAuditUnitprice()) == 0
                                            // ??????????????????
                                            && StringUtil.equals(material.getMaterialUnit(), t.getCalculateUnit())
                                            // ????????????
                                            && StringUtil.equals(material.getMaterialFactory(), t.getSupplyUnit())) {
                                        isExist = true;
                                        break;
                                    }
                                }
                            }
                            if (!isExist) {
                                TPmMaterialEntity material = new TPmMaterialEntity();
                                material.setMaterialName(t.getPriceName());
                                material.setMaterialModel(t.getProdModel());
                                material.setMaterialUnit(t.getCalculateUnit());
                                material.setMaterialPrice(t.getPriceAuditUnitprice());//???????????????
                                material.setMaterialFactory(t.getSupplyUnit());
                                material.setResourceId(t.getId());
                                material.setMateriaType(SrmipConstants.MATERIA_TYPE_ORIGINAL);
                                material.setPurchaseDept(appr.getApplyUnit());
                                material.setPurchaseTime(appr.getContractSigningTime());
                                material.setMaterialResource(SrmipConstants.LRLY_PRICE);
                                material.setSupplyDate(new Date());
                                TPmProjectEntity project = appr.getProject();
                                material.setProjectId(project.getId());
                                material.setProjectName(project.getProjectName());

                                commonDao.save(material);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void doBack(String id) {
        TPmOutcomeContractApprEntity t = commonDao.get(TPmOutcomeContractApprEntity.class, id);
        //?????????????????????????????????
        t.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TPmOutcomeContractApprEntity entity = this.get(TPmOutcomeContractApprEntity.class, id);
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
        TPmOutcomeContractApprEntity t = commonDao.get(TPmOutcomeContractApprEntity.class, id);
        if(t!=null){
            appName = t.getContractName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmOutcomeContractApprEntity entity = this.get(TPmOutcomeContractApprEntity.class, id);
        if (entity != null) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
        
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //?????????????????????
            TPmOutcomeContractApprEntity t = commonDao.get(TPmOutcomeContractApprEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //?????????????????????
                t.setFinishFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
                // addMaterial(t);//???????????????
            }

        }
    }

}