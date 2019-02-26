package org.jeecgframework.poi.excel.view;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.tools.ant.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 验收自定义视图
 * 
 * @author admin
 * 
 */
public class AppraisalExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook hssfWorkbook,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String codedFileName = "临时文件.xls";
        if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
            String fileName = POIPublicUtil.processFileName(httpServletRequest,
                    (String) model.get(NormalExcelConstants.FILE_NAME));
            codedFileName = fileName + ".xls";
        }
        httpServletResponse.setHeader("content-disposition", "attachment;filename=" + codedFileName);
        if (model.containsKey(NormalExcelConstants.MAP_LIST)) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) model.get(NormalExcelConstants.MAP_LIST);
            for (Map<String, Object> map : list) {
                new ExcelExportServer().createSheet(hssfWorkbook, (ExportParams) map.get(NormalExcelConstants.PARAMS),
                        (Class<?>) map.get(NormalExcelConstants.CLASS),
                        (Collection<?>) map.get(NormalExcelConstants.DATA_LIST));
            }
        } else {
            new ExcelExportServer().createSheet(hssfWorkbook, (ExportParams) model.get(NormalExcelConstants.PARAMS),
                    (Class<?>) model.get(NormalExcelConstants.CLASS),
                    (Collection<?>) model.get(NormalExcelConstants.DATA_LIST));
        }
        workBookHandle(hssfWorkbook);
    }

    /**
     * 处理workbookk
     * 
     * @param workbook
     */
    private void workBookHandle(HSSFWorkbook workbook) {
        String departName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
        String currentDate = DateUtils.format(new Date(), "yyyy年MM月dd日");
        HSSFSheet sheet = workbook.getSheetAt(0);
        sheet.removeMergedRegion(1);
        HSSFRow row = sheet.createRow(1);
        HSSFCellStyle ztStyle0 = (HSSFCellStyle) workbook.createCellStyle();
        // 创建字体对象   
        Font ztFont = workbook.createFont();
        ztFont.setFontHeightInPoints((short) 15);
        ztFont.setFontName("宋体");
        ztStyle0.setFont(ztFont);
        ztStyle0.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("填报单位：" + departName + "（盖章）");
        cell0.setCellStyle(ztStyle0);
        HSSFCellStyle ztStyle1 = (HSSFCellStyle) workbook.createCellStyle();
        ztStyle1.setFont(ztFont);
        ztStyle1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        Cell cell1 = row.createCell(10);
        cell1.setCellValue(currentDate);
        cell1.setCellStyle(ztStyle1);
    }

}
