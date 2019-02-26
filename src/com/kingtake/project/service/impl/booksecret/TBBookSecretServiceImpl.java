package com.kingtake.project.service.impl.booksecret;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.booksecret.TBBookSecretEntity;
import com.kingtake.project.entity.thesis.TBThesisSecretEntity;
import com.kingtake.project.service.booksecret.TBBookSecretServiceI;

@Service("tBBookSecretService")
@Transactional
public class TBBookSecretServiceImpl extends CommonServiceImpl implements TBBookSecretServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBBookSecretEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBBookSecretEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBBookSecretEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBBookSecretEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBBookSecretEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBBookSecretEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBBookSecretEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{book_name}",String.valueOf(t.getBookName()));
 		sql  = sql.replace("#{secret_degree}",String.valueOf(t.getSecretDegree()));
 		sql  = sql.replace("#{press}",String.valueOf(t.getPress()));
 		sql  = sql.replace("#{issue_scope}",String.valueOf(t.getIssueScope()));
 		sql  = sql.replace("#{fix_telephone}",String.valueOf(t.getFixTelephone()));
 		sql  = sql.replace("#{mobile_telephone}",String.valueOf(t.getMobileTelephone()));
 		sql  = sql.replace("#{first_author}",String.valueOf(t.getFirstAuthor()));
 		sql  = sql.replace("#{other_author}",String.valueOf(t.getOtherAuthor()));
 		sql  = sql.replace("#{book_content_key}",String.valueOf(t.getBookContentKey()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{review_number}",String.valueOf(t.getReviewNumber()));
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
    public void deleteAddAttach(TBBookSecretEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
    
    @Override
    public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) {
        OutputStream out = null;
        InputStream templateIs = null;
        try {
            String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
            String fileName = "";

            templateIs = new FileInputStream(classPath + "export" + File.separator + "template" + File.separator
                + "bookSecretTemplate.xls");
            fileName = "专著审批登记导入模板.xls";

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
    
    public String importExcel(HSSFWorkbook wb) {
    	String msg = "";
    	int sheetNum = wb.getNumberOfSheets();
    	int sheetSuccess = 0;
    	for(int i = 0; i < sheetNum; i ++){
    		HSSFSheet sheet = wb.getSheetAt(i);
            if (sheet == null) {
            	msg += "EXCEL文档的第" + (i + 1) + "页为空，成功导入0条数据！<br>";
                return msg;
            }
            List<TBBookSecretEntity> bookList = new ArrayList<TBBookSecretEntity>();
            int rownum = sheet.getLastRowNum();
            int rownumSuccess = 0;
            TSUser user = ResourceUtil.getSessionUserName();
            String subordinateDeptNameStr = wb.getSheetName(i);
            for (int j = 1; j <= rownum; j++) {
                HSSFRow row = sheet.getRow(j);
                //备注，如果有错误信息写不进系统，则都记录在备注当中
                String memo = getCellValue(row, 9);
                
                //检验此行数据是否为空
                if (row == null) {
                	msg += "第" + (i + 1) + "页第" + (j + 1) + "行数据为空，导入失败！<br>";
                    continue;                
                }           
                //检验论文题目是否为空
                String bookName = getCellValue(row, 3);
                if(StringUtils.isEmpty(bookName)){
                	msg += "第" + (i + 1) + "页第" + (j + 1) + "行数据专著名称为空，导入失败！<br>";
                    continue;
                }
                //检验创建时间是否为空
                String createTimeStr = getCellValue(row, 8);
                if(StringUtils.isEmpty(createTimeStr)){
                	msg += "第" + (i + 1) + "页第" + (j + 1) + "行数据创建时间为空，导入失败！<br>";
                    continue;
                }
                //检验创建时间格式是否正确
                HSSFCell cellCreateTime = row.getCell(8);           
                if(cellCreateTime.getCellType() != 1){
                	msg += "第" + (i + 1) + "页第" + (j + 1) + "行数据创建时间格式错误，导入失败！<br>";
                    continue;
                }
                TBBookSecretEntity bookSecretEntity = new TBBookSecretEntity();
                if(bookName.indexOf(".") > -1){
                	bookName = bookName.substring(0,bookName.indexOf("."));
            	}        
                bookSecretEntity.setBookName(bookName);
                if (StringUtils.isNotEmpty(createTimeStr)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Date createDate = sdf.parse(createTimeStr);
                        bookSecretEntity.setCreateDate(createDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                String reviewNumber = getCellValue(row, 0);
                bookSecretEntity.setReviewNumber(reviewNumber);
                
                String author = getCellValue(row, 1);
                String[] authorAry = null;
                if(StringUtil.isNotEmpty(author)){
                	authorAry = author.split("、");
                	bookSecretEntity.setFirstAuthor(authorAry[0]);
                	List<TSBaseUser> userList = this.commonDao.findByProperty(TSBaseUser.class, "realName", authorAry[0]);
                	if (userList.size() > 0) {
                		TSBaseUser creator = userList.get(0);
                		bookSecretEntity.setCreateBy(creator.getId());
                		bookSecretEntity.setCreateName(creator.getRealName());
                    }                	
                	String otherAuthor = "";
                	for(int k = 1; k < authorAry.length; k++){
                		otherAuthor = authorAry[k] + "、";
                	}
                	if(StringUtil.isNotEmpty(otherAuthor)){
                		otherAuthor = otherAuthor.substring(0,(otherAuthor.length() - 1));
                    	bookSecretEntity.setOtherAuthor(otherAuthor);
                	}                	
                }
                
                String concreteDeptName = getCellValue(row, 2);
                if(StringUtil.isNotEmpty(concreteDeptName)){
                	TSDepart concreteDept = getDeptByName(concreteDeptName);
                    if (concreteDept != null) {
                    	bookSecretEntity.setConcreteDeptId(concreteDept.getId());
                    	bookSecretEntity.setConcreteDeptName(concreteDept.getDepartname());
//                    	if(concreteDept.getTSPDepart()!=null){
//                    		bookSecretEntity.setSubordinateDeptId(concreteDept.getTSPDepart().getId());
//                    		bookSecretEntity.setSubordinateDeptName(concreteDept.getTSPDepart().getDepartname());
//                    	}                    	
                    }else{
                    	bookSecretEntity.setConcreteDeptName(concreteDeptName);
                    	memo += "“具体单位”数据有误，系统中不存在，原数据是：" + concreteDeptName + "；";
                    }
                }
                
                if(StringUtil.isNotEmpty(subordinateDeptNameStr)){
                	TSDepart subordinateDept = getDeptByName(subordinateDeptNameStr);
                    if (subordinateDept != null) {
                    	bookSecretEntity.setSubordinateDeptId(subordinateDept.getId());
                    	bookSecretEntity.setSubordinateDeptName(subordinateDept.getDepartname());                    	                    
                    }else{
                    	bookSecretEntity.setSubordinateDeptName(subordinateDeptNameStr);
                    	memo += "“所属院、直属系”数据有误，系统中不存在，原数据是：" + subordinateDeptNameStr + "；";
                    }
                }
                
                String secretDegreeStr = getCellValue(row, 4);
                if (StringUtils.isNotEmpty(secretDegreeStr)) {
                    try {                
                        String secretDegree = getCodeValue("XMMJ", "0", secretDegreeStr);
                        if(StringUtils.isNotEmpty(secretDegree)){
                        	bookSecretEntity.setSecretDegree(secretDegree);
                        }                
                        else{
                        	bookSecretEntity.setSecretDegree("6");
                        	memo += "“密级”数据有误，系统中不存在，原数据是：" + secretDegreeStr + "；";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                	bookSecretEntity.setSecretDegree("6");
                }
                
                String press = getCellValue(row, 6);
                bookSecretEntity.setPress(press);               

                String mobileTelephone = getCellValue(row, 7);
                bookSecretEntity.setMobileTelephone(mobileTelephone);
                
//                String memo = getCellValue(row, 9);
                bookSecretEntity.setMemo(memo);
                
                String checkExpert = getCellValue(row, 10);
                bookSecretEntity.setCheckExpert(checkExpert);
                
                bookSecretEntity.setCheckFlag("0");
                
                bookList.add(bookSecretEntity);
                rownumSuccess++;
            }
            this.commonDao.batchSave(bookList);
            sheetSuccess++;
            msg += "EXCEL文档第" + sheetSuccess + "页共" + rownum + " 条数据，成功导入" + rownumSuccess + "条！";
    	}
        
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
    
  //通过单位名获取单位
    private TSDepart getDeptByName(String name) {
        List<TSDepart> deptList = this.commonDao.findByProperty(TSDepart.class, "departname", name);
        if (deptList.size() > 0) {
            TSDepart dept = deptList.get(0);
            return dept;
        }
        return null;
    }
    
  //根据代码集的名称得到值
    private String getCodeValue(String code, String codeType, String text) {
        String sql = "select d.code from t_b_code_type t join t_b_code_detail d "
                + "on t.id=d.code_type_id where t.code=? and t.code_type=? and d.name=?";
        List<Map<String, Object>> listMap = this.commonDao.findForJdbc(sql, new Object[] { code, codeType, text });
        if(listMap.size()>0){
            Map<String, Object> map = listMap.get(0);
            String codeValue = (String) map.get("code");
            return codeValue;
        }
        return "";
    }
}