package com.kingtake.project.service.impl.tpmincome;
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.project.service.tpmincome.TPmIncomeServiceI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("tPmIncomeService")
@Transactional
public class TPmIncomeServiceImpl extends CommonServiceImpl implements TPmIncomeServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmIncomeEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmIncomeEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmIncomeEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmIncomeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmIncomeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmIncomeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmIncomeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{income_time}",String.valueOf(t.getIncomeTime()));
 		sql  = sql.replace("#{certificate}",String.valueOf(t.getCertificate()));
 		sql  = sql.replace("#{digest}",String.valueOf(t.getDigest()));
 		sql  = sql.replace("#{income_amount}",String.valueOf(t.getIncomeAmount()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
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
    public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) {
        OutputStream out = null;
        InputStream templateIs = null;
        try {
            String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
            String fileName = "";
            
            	templateIs = new FileInputStream(classPath + "export" + File.separator + "template" + File.separator
                        + "inComeTemplate.xls");
                fileName = "到账信息导入模板.xls";
                fileName = POIPublicUtil
                        .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            out = response.getOutputStream();
            IOUtils.copy(templateIs, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出模板失败", e);
        } finally {
            IOUtils.closeQuietly(templateIs);
            IOUtils.closeQuietly(out);
        }
    }
 	
 	@Override
    public String importExcel(HSSFWorkbook wb) {
    	String msg = "";
        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
        	msg += "EXCEL文档为空，成功导入0条数据！<br>";
            return msg;
        }
        List<TPmIncomeEntity> inComeList = new ArrayList<TPmIncomeEntity>();
        int rownum = sheet.getLastRowNum();
        int rownumSuccess = 0;
        TSUser user = ResourceUtil.getSessionUserName();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);            
            if (row == null) {
            	msg += "第" + (i + 1) + "行数据为空，导入失败！<br>";
                continue;
            }
            //到账日期
            String time = getCellValue(row, 1);
            if(StringUtils.isEmpty(time)){
            	msg += "第" + (i + 1) + "行数据到账日期为空，导入失败！<br>";
                continue;
            }
            HSSFCell cellTime = row.getCell(1);           
            if(cellTime.getCellType() != 1){
            	msg += "第" + (i + 1) + "行数据到账日期格式错误，导入失败！<br>";
                continue;
            }
            //凭证号
            String certificate = getCellValue(row, 2);
            if(StringUtils.isEmpty(certificate)){
            	msg += "第" + (i + 1) + "行数据凭证号为空，导入失败！<br>";
                continue;
            }
            //到账金额
            String incomeAmountStr = getCellValue(row, 3);
            if(StringUtils.isEmpty(incomeAmountStr)){
            	msg += "第" + (i + 1) + "行数据到账金额为空，导入失败！<br>";
                continue;
            }
//            HSSFCell countCell = row.getCell(2);
//            if(countCell.getCellType() != 1){
//            	msg += "第" + (i + 1) + "行数据到账金额格式错误，导入失败！<br>";
//                continue;
//            }            
            //摘要
            String digest = getCellValue(row, 4);
            //会计年度
            String accountingYear = getCellValue(row, 5);
            //备注
            String remark = getCellValue(row, 6);
            
            TPmIncomeEntity inCome = new TPmIncomeEntity();
            String intValidate="^\\d+$";
            String floatValidate="^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
            if(incomeAmountStr.matches(intValidate) == true || incomeAmountStr.matches(floatValidate) == true){
            	BigDecimal incomeAmount =new BigDecimal(incomeAmountStr);   
            	inCome.setIncomeAmount(incomeAmount);
            	inCome.setBalance(incomeAmount);
            }
            else{
            	msg += "第" + (i + 1) + "行数据到账金额格式错误，导入失败！<br>";
            	continue;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date incomeTime = sdf.parse(time);
                inCome.setIncomeTime(incomeTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(certificate.indexOf(".") != -1){
            	certificate = certificate.split("\\.")[0];
            }
            inCome.setCertificate(certificate);
            inCome.setDigest(digest);
            inCome.setAccountingYear(accountingYear);
            inCome.setRemark(remark);
                                   
            inComeList.add(inCome);
            rownumSuccess++;
        }
        this.commonDao.batchSave(inComeList);
        msg += "EXCEL文档共" + rownum + " 条数据，成功导入" + rownumSuccess + "条！";
        return msg;
    }
 	
 	/**
     * 获取单元格值
     * 
     * @param row
     * @param index
     * @return
     */
    private String getCellValue(HSSFRow row, int index) {
        HSSFCell cell = row.getCell(index);
        if(cell==null){
            return "";
        }
        int cellType = cell.getCellType();
        String value = "";
        if (HSSFCell.CELL_TYPE_NUMERIC == cellType) {
            value = String.valueOf(cell.getNumericCellValue());
        } else {
            value = cell.getStringCellValue();
        }
        return value;
    }
}