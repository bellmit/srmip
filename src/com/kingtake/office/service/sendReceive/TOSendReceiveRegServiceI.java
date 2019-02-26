package com.kingtake.office.service.sendReceive;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;

public interface TOSendReceiveRegServiceI extends CommonService {

    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOSendReceiveRegEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOSendReceiveRegEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOSendReceiveRegEntity t);

    /**
     * 根据公文种类查找模板文件
     */
    public List<Map<String, Object>> findModelByRegType(String regType);

    /**
     * 将收发文登记中的附件copy到公文
     */
    public boolean copyFile(String regId, String billId, String businessType);

    /**
     * 更新公文
     * 
     * @param tOSendReceiveReg
     */
    public void updateBill(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request);

    /**
     * 拷贝
     * 
     * @param tOSendReceiveReg
     */
    public void doCopy(TOSendReceiveRegEntity tOSendReceiveReg);

    /**
     * 拷贝正文
     * 
     * @param regId
     * @return
     */
    public String copyContentFile(String regId);

    /**
     * 保存历史版本
     * 
     * @param tOSendReceiveReg
     */
    public void doHistory(TOSendReceiveRegEntity tOSendReceiveReg);

    /**
     * 导入excel
     * 
     * @param wb
     */
    public String importSendBillExcel(HSSFWorkbook wb);
    public String importReceiveBillExcel(HSSFWorkbook wb);

    /**
     * 下载导入模板
     * 
     * @param regesterType
     * @param response
     */
    public void downloadTemplate(String regesterType, HttpServletRequest request, HttpServletResponse response);
}
