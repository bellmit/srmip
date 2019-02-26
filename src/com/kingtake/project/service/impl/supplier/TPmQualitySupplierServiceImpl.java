package com.kingtake.project.service.impl.supplier;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.supplier.TPmQualitySupplierEntity;
import com.kingtake.project.service.supplier.TPmQualitySupplierServiceI;

@Service("tPmQualitySupplierService")
@Transactional
public class TPmQualitySupplierServiceImpl extends CommonServiceImpl implements TPmQualitySupplierServiceI {

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmQualitySupplierEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmQualitySupplierEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmQualitySupplierEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TPmQualitySupplierEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TPmQualitySupplierEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmQualitySupplierEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmQualitySupplierEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{supplier_name}", String.valueOf(t.getSupplierName()));
        sql = sql.replace("#{supplier_level}", String.valueOf(t.getSupplierLevel()));
        sql = sql.replace("#{supplier_type}", String.valueOf(t.getSupplierType()));
        sql = sql.replace("#{supplier_product}", String.valueOf(t.getSupplierProduct()));
        sql = sql.replace("#{supplier_address}", String.valueOf(t.getSupplierAddress()));
        sql = sql.replace("#{supplier_postcode}", String.valueOf(t.getSupplierPostcode()));
        sql = sql.replace("#{supplier_contact}", String.valueOf(t.getSupplierContact()));
        sql = sql.replace("#{supplier_phone}", String.valueOf(t.getSupplierPhone()));
        sql = sql.replace("#{supplier_fax}", String.valueOf(t.getSupplierFax()));
        sql = sql.replace("#{valid_flag}", String.valueOf(t.getValidFlag()));
        sql = sql.replace("#{bank_name}", String.valueOf(t.getBankName()));
        sql = sql.replace("#{bank_num}", String.valueOf(t.getBankNum()));
        sql = sql.replace("#{bank_address}", String.valueOf(t.getBankAddress()));
        sql = sql.replace("#{enterprise_legal_person}", String.valueOf(t.getEnterpriseLegalPerson()));
        sql = sql.replace("#{organization_code}", String.valueOf(t.getOrganizationCode()));
        sql = sql.replace("#{register_address}", String.valueOf(t.getRegisterAddress()));
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
    public void refreshValidFlag() {
        List<TPmQualitySupplierEntity> supplierList = this.commonDao.findByProperty(TPmQualitySupplierEntity.class,
                "validFlag", "1");
        for (TPmQualitySupplierEntity supplier : supplierList) {
            if (supplier.getSupplierTime() == null || supplier.getAuditTime() == null) {
                continue;
            }
            boolean validFlag = isValid(supplier);
            if (!validFlag) {
                supplier.setValidFlag("0");
                supplier.setAuditTime(null);
                supplier.setSupplierTime(null);
            }
            this.commonDao.updateEntitie(supplier);
        }
    }

    /**
     * 判断是否有效
     * 
     * @param supplier
     * @return
     */
    private boolean isValid(TPmQualitySupplierEntity supplier) {
        boolean flag = false;
        Date supplierDate = supplier.getSupplierTime();
        Calendar supplierCal = Calendar.getInstance();
        supplierCal.setTime(supplierDate);
        supplierCal.set(Calendar.HOUR_OF_DAY, 23);
        supplierCal.set(Calendar.MINUTE, 59);
        supplierCal.set(Calendar.SECOND, 59);
        flag = supplierCal.getTime().after(new Date());
        if (flag) {//如果未失效，则判断是否超过失效年限未签合同
            CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class);
            cq.eq("approvalUnitId", supplier.getId());
            cq.addOrder("contractSigningTime", SortDirection.desc);
            cq.add();
            List<TPmOutcomeContractApprEntity> apprList = this.commonDao.getListByCriteriaQuery(cq, false);
            TPmOutcomeContractApprEntity appr = null;
            TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
            codeTypeEntity.setCodeType("1");
            codeTypeEntity.setCode("GFSXNX");
            List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
            int sxnxI = 0;
            if (codeDetailList.size() > 0) {
                TBCodeDetailEntity detail = codeDetailList.get(0);
                String sxnx = detail.getName();
                sxnxI = Integer.parseInt(sxnx);
            }
            if (sxnxI > 0) {
                if (apprList.size() > 0) {
                    appr = apprList.get(0);
                    Date signingTime = appr.getContractSigningTime();
                    flag = isValidx(sxnxI, signingTime);
                    if (!flag) {
                        supplier.setReason("供方超过失效年限" + sxnxI + "年未签合同");
                    }
                } else {
                    flag = isValidx(sxnxI, supplier.getAuditTime());
                    if (!flag) {
                        supplier.setReason("供方审核时间超过失效年限" + sxnxI + "年");
                    }
                }
            }
        } else {
            supplier.setReason("供方超过失效时间");
        }
        return flag;
    }

    /**
     * 
     * 是否超过年限
     * 
     * @return
     */
    private boolean isValidx(int sxnx, Date time) {
        Calendar supplierCal = Calendar.getInstance();
        supplierCal.setTime(time);
        supplierCal.set(Calendar.HOUR_OF_DAY, 23);
        supplierCal.set(Calendar.MINUTE, 59);
        supplierCal.set(Calendar.SECOND, 59);
        boolean flag = false;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int lastY = year - sxnx;
        cal.set(Calendar.YEAR, lastY);
        if (supplierCal.after(cal)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void saveSupplier(TPmQualitySupplierEntity tPmQualitySupplier) {
        try {
            if (StringUtils.isEmpty(tPmQualitySupplier.getId())) {
                commonDao.save(tPmQualitySupplier);
            } else {
                TPmQualitySupplierEntity t = commonDao.get(TPmQualitySupplierEntity.class, tPmQualitySupplier.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmQualitySupplier, t);
                commonDao.saveOrUpdate(t);
                if (t.getSupplierTime() != null || t.getAuditTime() != null) {
                    boolean validFlag = isValid(t);
                    if (!validFlag) {
                        t.setValidFlag("0");
                    } else {
                        t.setValidFlag("1");
                        t.setReason(null);
                    }
                    this.commonDao.updateEntitie(t);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("保存供方名录失败！");
        }
    }

    @Override
    public void doRelateContract(String supplierId, String ids) {
        String[] idArr = ids.split(",");
        TPmQualitySupplierEntity supplier = this.commonDao.get(TPmQualitySupplierEntity.class, supplierId);
        for(String id:idArr){
            TPmOutcomeContractApprEntity outcomeAppr = this.commonDao.get(TPmOutcomeContractApprEntity.class, id);
            outcomeAppr.setApprovalUnit(supplier.getSupplierName());
            outcomeAppr.setApprovalUnitId(supplier.getId());
            this.commonDao.updateEntitie(outcomeAppr);
        }
    }

}