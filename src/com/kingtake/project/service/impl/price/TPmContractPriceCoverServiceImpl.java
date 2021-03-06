package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceDesignEntity;
import com.kingtake.project.entity.price.TPmContractPriceManageEntity;
import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;
import com.kingtake.project.entity.price.TPmContractPriceMaterialEntity;
import com.kingtake.project.entity.price.TPmContractPriceOutCorpEntity;
import com.kingtake.project.entity.price.TPmContractPriceProfitEntity;
import com.kingtake.project.entity.price.TPmContractPricePurchaseEntity;
import com.kingtake.project.entity.price.TPmContractPriceSalaryEntity;
import com.kingtake.project.entity.price.TPmContractPriceTrialEntity;
import com.kingtake.project.service.price.TPmContractPriceCoverServiceI;

@Service("tPmContractPriceCoverService")
@Transactional
public class TPmContractPriceCoverServiceImpl extends CommonServiceImpl implements TPmContractPriceCoverServiceI {
    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//???????????????????????????sql??????
		this.doDelSql((TPmContractPriceCoverEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//???????????????????????????sql??????
 		this.doAddSql((TPmContractPriceCoverEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//???????????????????????????sql??????
 		this.doUpdateSql((TPmContractPriceCoverEntity)entity);
 	}
 	
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceCoverEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceCoverEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceCoverEntity t){
	 	return true;
 	}
 	
 	/**
	 * ??????sql????????????
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceCoverEntity t){
 		sql  = sql.replace("#{price_username}",String.valueOf(t.getPriceUsername()));
 		sql  = sql.replace("#{price_date}",String.valueOf(t.getPriceDate()));
 		sql  = sql.replace("#{audit_userid}",String.valueOf(t.getAuditUserid()));
 		sql  = sql.replace("#{audit_username}",String.valueOf(t.getAuditUsername()));
 		sql  = sql.replace("#{audit_date}",String.valueOf(t.getAuditDate()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{contract_id}",String.valueOf(t.getContractId()));
 		sql  = sql.replace("#{contract}",String.valueOf(t.getContract()));
 		sql  = sql.replace("#{contract_party_a}",String.valueOf(t.getContractPartyA()));
 		sql  = sql.replace("#{contract_party_b}",String.valueOf(t.getContractPartyB()));
 		sql  = sql.replace("#{submit_date}",String.valueOf(t.getSubmitDate()));
 		sql  = sql.replace("#{price_type}",String.valueOf(t.getContractType()));
 		sql  = sql.replace("#{price_userid}",String.valueOf(t.getPriceUserid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public TPmContractPriceCoverEntity init(String type, String contractId) {
		// ????????????
		TPmContractPriceCoverEntity cover = new TPmContractPriceCoverEntity();
		cover.setContractType(type);
		cover.setContractId(contractId);
		TPmOutcomeContractApprEntity contract = commonDao.get(TPmOutcomeContractApprEntity.class, contractId);
		cover.setContract(contract.getContractName());
		TPmContractBasicEntity contractBasic = this.commonDao.findUniqueByProperty(TPmContractBasicEntity.class, "inOutContractid", contract.getId());
		cover.setContractPartyA(contractBasic.getUnitNameA());
		cover.setContractPartyB(contractBasic.getUnitNameB());
		cover.setSerialNumber(contract.getContractCode());
		commonDao.save(cover);
		
        // ?????????????????????????????????????????????????????????????????????????????????
        if (!ProjectConstant.CONTRACT_BUY.equals(type) && !ProjectConstant.CONTRACT_TECH.equals(type)) {
			// ????????????????????????????????????????????????
			List<TBApprovalBudgetRelaEntity> details = commonDao.getSession().createCriteria(TBApprovalBudgetRelaEntity.class)
				.add(Restrictions.eq("projApproval", type)).list();
									
			// ????????????????????????????????????????????????????????????
			for(int i = 0; i < details.size(); i++){
				TBApprovalBudgetRelaEntity detail = details.get(i);
				
				// ????????????null:????????????
				if(detail.getTBApprovalBudgetRelaEntity() == null){
					TPmContractPriceMasterEntity master = new TPmContractPriceMasterEntity();
					master.setTpId(cover.getId());
					this.setMasterFromBudget(detail, master);
					commonDao.save(master);
					
					List<TBApprovalBudgetRelaEntity> children = detail.getTBApprovalBudgetRelaEntitys();
					if(children != null && children.size() > 0){
						for(int j = 0; j < children.size(); j++){
							TBApprovalBudgetRelaEntity childDetail = children.get(j);
							TPmContractPriceMasterEntity childMaster = new TPmContractPriceMasterEntity();
							childMaster.setTpId(cover.getId());
							this.setMasterFromBudget(childDetail, childMaster);
							childMaster.setParent(master.getId());
							commonDao.save(childMaster);
						}
					}
				}
			}
				
			// ??????????????????show_flag??????
			for(int i = 0; i < details.size(); i++){
				TBApprovalBudgetRelaEntity detail = details.get(i);
				String showFlag = detail.getShowFlag();
				
				if(StringUtil.isNotEmpty(showFlag)){
					Class clazz = null;
					try {
						clazz = (Class) ProjectConstant.CONTRACT_PRICE_DETAIL.get(showFlag).get("class");
						
						Object obj = clazz.newInstance();
						
						Method setTpId = clazz.getMethod("setTpId", String.class);
						setTpId.invoke(obj, cover.getId());
						
						this.setAttr(obj, clazz, detail);
						
						commonDao.save(obj);
						
						List<TBApprovalBudgetRelaEntity> children = detail.getTBApprovalBudgetRelaEntitys();
						if(children != null && children.size() > 0){
							for(int j = 0; j < children.size(); j++){
								TBApprovalBudgetRelaEntity child = children.get(j);
								
								Object childObj = clazz.newInstance();
								
								setTpId.invoke(childObj, cover.getId());
								
								this.setAttr(childObj, clazz, child);
								
								Method setParentTypeid = clazz.getMethod("setParentTypeid", String.class);
								setParentTypeid.invoke(childObj, clazz.getMethod("getId").invoke(obj));
								
								commonDao.save(childObj);
							}
						}
						
					} catch (NoSuchMethodException e) {
						System.out.println("???????????????");
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		return cover;
	}
	
	
	private void setMasterFromBudget(TBApprovalBudgetRelaEntity detail, TPmContractPriceMasterEntity master){
		master.setPriceBudgetId(detail.getId());
		master.setPriceBudgetName(detail.getBudgetNae());
		master.setSortCode(detail.getSortCode());
		master.setShowFlag(detail.getShowFlag());
	}
	
	private void setAttr(Object obj, Class clazz, TBApprovalBudgetRelaEntity src){
		try {
			Method setSerialNum = clazz.getMethod("setSerialNum", String.class);
			setSerialNum.invoke(obj, src.getSortCode());
			
			Method setTypeId = clazz.getMethod("setTypeId", String.class);
			setTypeId.invoke(obj, src.getId());
			
			Method setTypeName = clazz.getMethod("setTypeName", String.class);
			setTypeName.invoke(obj, src.getBudgetNae());
			
			Method setAddChildFlag = clazz.getMethod("setAddChildFlag", String.class);
			setAddChildFlag.invoke(obj, src.getAddChildFlag());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

    /**
     * ??????excel??????
     */
    @Override
    public void importExcel(HSSFWorkbook wb, String tpId) {
        TPmContractPriceCoverEntity coverEntity = this.commonDao.get(
                TPmContractPriceCoverEntity.class, tpId);
        //???????????????????????????????????????
        Map<Integer, String> produceMap = new LinkedHashMap<Integer, String>();
        produceMap.put(0, "cover");
        produceMap.put(2, "2");
        produceMap.put(3, "3");
        produceMap.put(4, "1");
        produceMap.put(5, "1");
        produceMap.put(6, "1");
        produceMap.put(7, "1");
        produceMap.put(1, "master");
        produceMap.put(8, "4");
        //???????????????????????????????????????
        Map<Integer, String> developMap = new LinkedHashMap<Integer, String>();
        developMap.put(0, "cover");
        developMap.put(2, "5");
        developMap.put(3, "2");
        developMap.put(4, "6");
        developMap.put(5, "1");
        developMap.put(6, "7");
        developMap.put(7, "1");
        developMap.put(8, "3");
        developMap.put(9, "1");
        developMap.put(1, "master");
        developMap.put(10, "4");
        Map<Integer, String> buyMap = new LinkedHashMap<Integer, String>();
        buyMap.put(0, "cover");
        buyMap.put(1, "purchase");
        //String[] produceArr = { "cover", "master", "2", "3", "1", "1", "1", "1","4" };
        //String[] devArr = { "cover", "master", "5", "2", "6", "1", "7", "1", "3", "1", "4" };
        //String[] buyArr = { "cover", "purchase" };
        //???????????????????????????????????????
        Map<String, Map<Integer, String>> priceConfigMap = new HashMap<String, Map<Integer, String>>();
        priceConfigMap.put(ProjectConstant.CONTRACT_PRODUCE, produceMap);
        priceConfigMap.put(ProjectConstant.CONTRACT_DEVELOP, developMap);
        priceConfigMap.put(ProjectConstant.CONTRACT_BUY, buyMap);
        Map<Integer, String> config = priceConfigMap.get(coverEntity.getContractType());
        Set<Entry<Integer, String>> configSet = config.entrySet();
        Iterator it = configSet.iterator();
        while (it.hasNext()) {
            Entry<Integer, String> entry = (Entry<Integer, String>) it.next();
            int idx = entry.getKey();
            String type = entry.getValue();
            HSSFSheet sheet = wb.getSheetAt(idx);
                switch (type) {//??????????????????????????????????????? 1???????????? 2???????????? 3???????????? 4???????????? 5???????????? 6???????????? 7????????????
                case "cover":
                importCover(sheet, coverEntity);
                    break;
            case "master":
                importMaster(sheet, coverEntity);
                break;
            case "1":
                importManage(sheet, coverEntity);
                break;
                case "2":
                importMaterial(sheet, coverEntity);
                    break;
            case "3":
                importSalary(sheet, coverEntity);
                break;
            case "4":
                importProfit(sheet, coverEntity);
                break;
            case "5":
                importDesign(sheet, coverEntity);
                break;
            case "6":
                importOutCorp(sheet, coverEntity);
                break;
            case "7":
                importTrial(sheet, coverEntity);
                break;
                case "purchase":
                importPurchase(sheet, coverEntity);
                    break;
                }
            }
    }


    /**
     * ????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importCover(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        HSSFRow row0 = sheet.getRow(0);
        HSSFRow row1 = sheet.getRow(1);
        String secrityGrade = getCellVal(row0, 9);
        String cod = getCodeByValue("0", "XMMJ", secrityGrade);
        coverEntity.setSecrityGrade(cod);
        String seriaNum = getCellVal(row1, 9);
        coverEntity.setSerialNumber(seriaNum);
        HSSFRow row7 = sheet.getRow(7);
        String projectName = getCellVal(row7, 2);
        String contractName = getCellVal(row7, 6);
        coverEntity.setContract(contractName);
        HSSFRow row9 = sheet.getRow(9);
        String contractPartA = getCellVal(row9, 2);
        coverEntity.setContractPartyA(contractPartA);
        String contractPartB = getCellVal(row9, 6);
        coverEntity.setContractPartyB(contractPartB);
        HSSFRow row11 = sheet.getRow(11);
        String priceUserName = getCellVal(row11, 2);
        coverEntity.setPriceUsername(priceUserName);
        String priceDate = getCellVal(row11, 6);

        HSSFRow row13 = sheet.getRow(13);
        String auditUserName = getCellVal(row13, 2);
        coverEntity.setAuditUsername(auditUserName);
        String auditDate = getCellVal(row13, 6);
        HSSFRow row15 = sheet.getRow(15);
        String submitDate = getCellVal(row15, 2);
        Date pDate = null;
        Date aDate = null;
        Date sDate = null;
        try {
            pDate = DateUtils.parseDate(priceDate, "yyyy-MM-dd");
            aDate = DateUtils.parseDate(auditDate, "yyyy-MM-dd");
            sDate = DateUtils.parseDate(submitDate, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        coverEntity.setPriceDate(pDate);
        coverEntity.setAuditDate(aDate);
        coverEntity.setSubmitDate(sDate);
        this.commonDao.saveOrUpdate(coverEntity);
    }

    /**
     * ???????????????????????????
     * 
     * @param codeType
     * @param code
     * @param value
     * @return
     */
    private String getCodeByValue(String codeType, String code, String value) {
        String cod = "";
        TBCodeTypeEntity typeEntity = new TBCodeTypeEntity();
        typeEntity.setCodeType(codeType);
        typeEntity.setCode(code);//
        List<TBCodeDetailEntity> list = tBCodeTypeService.getCodeDetailByCodeType(typeEntity);
        for (TBCodeDetailEntity entity : list) {
            if (value.equals(entity.getName())) {
                cod = entity.getCode();
                break;
            }
        }
        return cod;
    }

    /**
     * ????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importMaster(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        for (int i = 6; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String sortCode = getCellVal(row, 0);//??????
            String valuationColumn = getCellVal(row, 4);//?????????
            String memo = getCellVal(row, 6);//??????
            CriteriaQuery cq = new CriteriaQuery(TPmContractPriceMasterEntity.class);
            cq.eq("sortCode", sortCode);
            cq.eq("tpId", coverEntity.getId());
            cq.add();
            List<TPmContractPriceMasterEntity> list = this.commonDao.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TPmContractPriceMasterEntity entity = list.get(0);
            entity.setSortCode(sortCode);
                entity.setValuationColumn("".equals(valuationColumn) ? 0.00 : Double.valueOf(valuationColumn));
                entity.setPriceDiffColumn(new BigDecimal(entity.getPriceColumn() == null ? 0.00 : entity
                        .getPriceColumn()).subtract(
                        new BigDecimal(entity.getValuationColumn())).doubleValue());
            entity.setMemo(memo);
            this.commonDao.updateEntitie(entity);
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceMasterEntity.class);
        cq.eq("tpId", coverEntity.getId());
        cq.isNull("parent");
        cq.add();
        List<TPmContractPriceMasterEntity> parentMasterList = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TPmContractPriceMasterEntity master : parentMasterList) {
            CriteriaQuery subCq = new CriteriaQuery(TPmContractPriceMasterEntity.class);
            subCq.eq("tpId", coverEntity.getId());
            subCq.eq("parent", master.getId());
            subCq.add();
            List<TPmContractPriceMasterEntity> subList = this.commonDao.getListByCriteriaQuery(subCq, false);
            if (subList.size() > 1) {//????????????????????????????????????
                BigDecimal priceSum = BigDecimal.ZERO;
                BigDecimal auditSum = BigDecimal.ZERO;
                for (TPmContractPriceMasterEntity subMaster : subList) {
                    Double priceAmount = subMaster.getPriceColumn() == null ? 0.00 : subMaster.getPriceColumn();
                    Double auditAmount = subMaster.getAuditColumn() == null ? 0.00 : subMaster.getAuditColumn();
                    priceSum = priceSum.add(new BigDecimal(priceAmount));
                    auditSum = auditSum.add(new BigDecimal(auditAmount));
                }
                master.setPriceColumn(priceSum.doubleValue());
                master.setAuditColumn(auditSum.doubleValue());
                master.setPriceDiffColumn(new BigDecimal(master.getPriceColumn()).subtract(
                        new BigDecimal(master.getValuationColumn())).doubleValue());
                this.commonDao.updateEntitie(master);
            }
        }
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importManage(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        HSSFRow row6 = sheet.getRow(6);
        String parentCode = getCellVal(row6, 0);//??????
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceManageEntity.class);
        cq.eq("tpId", coverEntity.getId());
        cq.isNull("parentTypeid");
        cq.eq("serialNum", parentCode);
        cq.add();
        List<TPmContractPriceManageEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() == 0) {
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceManageEntity parent = entities.get(0);
        if ("1".equals(parent.getAddChildFlag())) {
            String sql = "delete from  t_pm_contract_price_manage t where t.t_p_id=? and t.parent_typeid=?";
            this.commonDao.executeSql(sql, new Object[] { coverEntity.getId(), parent.getId() });//??????????????????
        }
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            if ("???????????????".equals(serialNum)) {
                continue;
            }
            String typeName = getCellVal(row, 1);//??????
            String priceExplain = getCellVal(row, 2);//????????????
            String priceAmount = getCellVal(row, 3);//?????????
            String auditAmount = getCellVal(row, 4);//?????????
            String memo = getCellVal(row, 5);//??????
            TPmContractPriceManageEntity entity = null;
            if ("1".equals(parent.getAddChildFlag())) {
                entity = new TPmContractPriceManageEntity();
            } else if ("0".equals(parent.getAddChildFlag())) {
                CriteriaQuery subCq = new CriteriaQuery(TPmContractPriceManageEntity.class);
                subCq.eq("tpId", coverEntity.getId());
                subCq.eq("serialNum", serialNum);
                subCq.add();
                List<TPmContractPriceManageEntity> list = this.commonDao.getListByCriteriaQuery(subCq, false);
                if (list.size() == 0) {
                    continue;
                }
                entity = list.get(0);
            }
            entity.setSerialNum(serialNum);
            entity.setTypeName(typeName);
            entity.setPriceExplain(priceExplain);
            Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
            entity.setPriceAmount(pa);
            priceSum = priceSum.add(new BigDecimal(pa));
            Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);
            auditSum = auditSum.add(new BigDecimal(aa));
            entity.setAuditAmount(aa);
            entity.setMemo(memo);
            entity.setParentTypeid(parent.getId());
            entity.setTpId(coverEntity.getId());
            entity.setAddChildFlag("2");
            entity.setParentTypeid(parent.getId());
            this.commonDao.saveOrUpdate(entity);
        }
        parent.setPriceAmount(priceSum.doubleValue());
        parent.setAuditAmount(auditSum.doubleValue());
        this.commonDao.updateEntitie(parent);
        //??????????????????
        updateMaster(coverEntity.getId(), parent.getTypeId(), priceSum.doubleValue(), auditSum.doubleValue());
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importDesign(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        CriteriaQuery parentCq = new CriteriaQuery(TPmContractPriceDesignEntity.class);
        parentCq.eq("tpId", coverEntity.getId());
        parentCq.isNull("parentTypeid");
        parentCq.add();
        List<TPmContractPriceDesignEntity> entities = this.commonDao.getListByCriteriaQuery(parentCq, false);
        if (entities.size() == 0) {
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceDesignEntity parent = entities.get(0);//?????????
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);
            String typeName = getCellVal(row, 1);
            String priceExplain = getCellVal(row, 2);
            String priceUnit = getCellVal(row, 3);
            String priceNumber = getCellVal(row, 4);
            String priceCost = getCellVal(row, 5);
            String priceAmount = getCellVal(row, 6);
            String auditUnit = getCellVal(row, 7);
            String auditNumber = getCellVal(row, 8);
            String auditCost = getCellVal(row, 9);
            String auditAmount = getCellVal(row, 10);
            String memo = getCellVal(row, 11);
            CriteriaQuery cq = new CriteriaQuery(TPmContractPriceDesignEntity.class);
            cq.eq("serialNum", serialNum);
            cq.eq("tpId", coverEntity.getId());
            cq.add();
            List<TPmContractPriceDesignEntity> entityList = this.commonDao.getListByCriteriaQuery(cq, false);
            if (entityList.size() == 0) {
                continue;
            }
            TPmContractPriceDesignEntity entity = entityList.get(0);
            entity.setPriceExplain(priceExplain);
            entity.setPriceUnit(priceUnit);
            entity.setPriceNumber("".equals(priceNumber) ? 0.00 : Double.valueOf(priceNumber));
            entity.setPriceCost("".equals(priceCost) ? 0.00 : Double.valueOf(priceCost));
            Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
            priceSum = priceSum.add(new BigDecimal(pa));
            entity.setPriceAmount(pa);
            entity.setAuditUnit(auditUnit);
            entity.setAuditNumber("".equals(auditNumber) ? 0.00 : Double.valueOf(auditNumber));
            entity.setAuditCost("".equals(auditCost) ? 0.00 : Double.valueOf(auditCost));
            Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);
            entity.setAuditAmount(aa);
            auditSum = auditSum.add(new BigDecimal(aa));
            entity.setMemo(memo);
            this.commonDao.updateEntitie(entity);
        }
        parent.setPriceAmount(priceSum.doubleValue());
        parent.setAuditAmount(auditSum.doubleValue());
        this.commonDao.updateEntitie(parent);
        //??????????????????
        updateMaster(coverEntity.getId(), parent.getTypeId(), priceSum.doubleValue(), auditSum.doubleValue());
    }

    /**
     * ?????????????????????
     * 
     * @param row
     * @param j
     * @return
     */
    private String getCellVal(HSSFRow row, int j) {
        HSSFCell cell = row.getCell(j);
        String cellValue = "";
        if (cell != null) {
            int cellType = cell.getCellType();
            if (Cell.CELL_TYPE_NUMERIC == cellType) {
                cellValue = String.valueOf(cell.getNumericCellValue());
            } else {
                cellValue = cell.getStringCellValue();
            }
        }
        return cellValue;
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importMaterial(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceMaterialEntity.class);
        cq.eq("tpId", coverEntity.getId());
        cq.isNull("parentTypeid");
        cq.add();
        List<TPmContractPriceMaterialEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() == 0) {
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceMaterialEntity parent = entities.get(0);//???????????????
        List<TPmContractPriceMaterialEntity> subMateriaList = this.commonDao.findByProperty(
                TPmContractPriceMaterialEntity.class, "parentTypeid",
                parent.getId());
        List<TPmContractPriceMaterialEntity> entityList = new ArrayList<TPmContractPriceMaterialEntity>();
        for (int i = 7; i < sheet.getLastRowNum(); i++) {
            TPmContractPriceMaterialEntity entity = new TPmContractPriceMaterialEntity();
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String typeName = getCellVal(row, 1);//??????
            if ("???????????????".equals(serialNum)) {
                continue;
            }
            String priceName = getCellVal(row, 2);//??????
            String prodModel = getCellVal(row, 3);//????????????
            String calculateUnit = getCellVal(row, 4);//????????????
            String supplyUnit = getCellVal(row, 5);//????????????
            String pricePlanNum = getCellVal(row, 6);//?????????-????????????
            String pricePlanUnitprice = getCellVal(row, 7);//?????????-????????????
            String pricePlanAmount = getCellVal(row, 8);//?????????-??????
            String priceAuditNum = getCellVal(row, 9);//?????????-????????????
            String priceAuditUnitprice = getCellVal(row, 10);//?????????-????????????
            String priceAuditAmount = getCellVal(row, 11);//?????????-??????
            String memo = getCellVal(row, 12);//??????
                entity.setTypeName(typeName);
            entity.setSerialNum(serialNum);
                entity.setPriceName(priceName);
                entity.setProdModel(prodModel);
                entity.setCalculateUnit(calculateUnit);
                entity.setSupplyUnit(supplyUnit);
            entity.setPricePlanNum("".equals(pricePlanNum) ? 0 : Double.valueOf(pricePlanNum).intValue());
            entity.setPricePlanUnitprice("".equals(pricePlanUnitprice) ? 0.00 : Double.valueOf(pricePlanUnitprice));
            Double pa = "".equals(pricePlanAmount) ? 0.00 : Double.valueOf(pricePlanAmount);
                entity.setPricePlanAmount(pa);
            entity.setPriceAuditNum("".equals(priceAuditNum) ? 0 : Double.valueOf(priceAuditNum).intValue());
            entity.setPriceAuditUnitprice("".equals(priceAuditUnitprice) ? 0.00 : Double.valueOf(priceAuditUnitprice));
            Double aa = "".equals(priceAuditAmount) ? 0.00 : Double.valueOf(priceAuditAmount);
                entity.setPriceAuditAmount(aa);
                entity.setMemo(memo);
            entityList.add(entity);
        }
        for (TPmContractPriceMaterialEntity sub : subMateriaList) {
            BigDecimal priceSum = BigDecimal.ZERO;
            BigDecimal auditSum = BigDecimal.ZERO;
            if ("1".equals(sub.getAddChildFlag())) {//?????????
                List<TPmContractPriceMaterialEntity> filterList = findContractByCode(entityList, sub.getSerialNum(),
                        "start");
                if(filterList.size()==0){
                    continue;
                }
                String sql = "delete from  t_pm_contract_price_material t where t.t_p_id=? and t.parent_typeid=?";
                this.commonDao.executeSql(sql, new Object[] { coverEntity.getId(), sub.getId() });//??????????????????
                for (TPmContractPriceMaterialEntity filter : filterList) {
                    filter.setParentTypeid(sub.getId());
                    filter.setTpId(coverEntity.getId());
                    filter.setAddChildFlag("2");
                    this.commonDao.save(filter);
                    priceSum = priceSum.add(new BigDecimal(filter.getPricePlanAmount()));
                    auditSum = auditSum.add(new BigDecimal(filter.getPriceAuditAmount()));
                }
                sub.setPricePlanAmount(priceSum.doubleValue());
                sub.setPriceAuditAmount(auditSum.doubleValue());
                this.commonDao.updateEntitie(sub);
            } else if ("2".equals(sub.getAddChildFlag())) {
                List<TPmContractPriceMaterialEntity> filterList = findContractByCode(entityList, sub.getSerialNum(),
                        "equals");
                if (filterList.size() == 0) {
                    continue;
                }
                TPmContractPriceMaterialEntity filter = filterList.get(0);
                sub.setSerialNum(filter.getSerialNum());
                sub.setTypeName(filter.getTypeName());
                sub.setPriceName(filter.getPriceName());
                sub.setProdModel(filter.getProdModel());
                sub.setCalculateUnit(filter.getCalculateUnit());
                sub.setSupplyUnit(filter.getSupplyUnit());
                sub.setPricePlanNum(filter.getPricePlanNum());
                sub.setPricePlanUnitprice(filter.getPricePlanUnitprice());
                sub.setPricePlanAmount(filter.getPricePlanAmount());
                sub.setPriceAuditNum(filter.getPriceAuditNum());
                sub.setPriceAuditUnitprice(filter.getPriceAuditUnitprice());
                sub.setPriceAuditAmount(filter.getPriceAuditAmount());
                sub.setMemo(filter.getMemo());
                this.updateEntitie(sub);
            }
        }
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (TPmContractPriceMaterialEntity sub : subMateriaList) {
            BigDecimal pa = sub.getPricePlanAmount() == null ? BigDecimal.ZERO : new BigDecimal(
                    sub.getPricePlanAmount());
            BigDecimal aa = sub.getPriceAuditAmount() == null ? BigDecimal.ZERO : new BigDecimal(
                    sub.getPriceAuditAmount());
            priceSum = priceSum.add(pa);
            auditSum = auditSum.add(aa);
        }
        parent.setPricePlanAmount(priceSum.doubleValue());
        parent.setPriceAuditAmount(auditSum.doubleValue());
        //??????????????????
        updateMaster(coverEntity.getId(), parent.getTypeId(), priceSum.doubleValue(),
                auditSum.doubleValue());
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    private List<TPmContractPriceMaterialEntity> findContractByCode(List<TPmContractPriceMaterialEntity> fundsOneList,
            String sortCode,
            String relation) {
        List<TPmContractPriceMaterialEntity> list = new ArrayList<TPmContractPriceMaterialEntity>();
        for (TPmContractPriceMaterialEntity entity : fundsOneList) {
            if ("start".equals(relation)) {
                if (entity.getSerialNum().startsWith(sortCode) && !entity.getSerialNum().equals(sortCode)) {
                    list.add(entity);
                }
            } else {
                if (entity.getSerialNum().equals(sortCode)) {
                    list.add(entity);
                }
            }
        }
        return list;
    }


    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importProfit(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        for (int i = 7; i <= sheet.getLastRowNum(); i++) {
            BigDecimal priceSum = BigDecimal.ZERO;
            BigDecimal auditSum = BigDecimal.ZERO;
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String typeName = getCellVal(row, 1);//??????
            String priceExplain = getCellVal(row, 2);//????????????
            String pricePercent = getCellVal(row, 3);//??????????????????
            String priceAmount = getCellVal(row, 4);//???????????????
            String auditPercent = getCellVal(row, 5);//??????????????????
            String auditAmount = getCellVal(row, 6);//???????????????
            String memo = getCellVal(row, 7);//??????
            CriteriaQuery profitCq = new CriteriaQuery(TPmContractPriceProfitEntity.class);
            profitCq.eq("serialNum", serialNum);
            profitCq.eq("tpId", coverEntity.getId());
            profitCq.add();
            List<TPmContractPriceProfitEntity> entitys = this.commonDao.getListByCriteriaQuery(profitCq, false);
            if (entitys.size() == 0) {
                continue;
            }
            TPmContractPriceProfitEntity entity = entitys.get(0);
            entity.setPriceExplain(priceExplain);
            Double pp = "".equals(pricePercent) ? 0.00 : Double.valueOf(pricePercent);
            entity.setPricePercent(pp);
            Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
            //            entity.setPriceAmount(pa);

            Double ap = "".equals(auditPercent) ? 0.00 : Double.valueOf(auditPercent);
            entity.setAuditPercent(ap);
            //            entity.setAuditAmount("".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount));
            Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);

            entity.setMemo(memo);
            CriteriaQuery masterQuery = new CriteriaQuery(TPmContractPriceMasterEntity.class);
            masterQuery.eq("tpId", coverEntity.getId());
            masterQuery.eq("sortCode", "01");
            masterQuery.add();
            List<TPmContractPriceMasterEntity> masterList = this.commonDao.getListByCriteriaQuery(masterQuery, false);
            if (masterList.size() == 0) {
                throw new BusinessException("??????????????????????????????");
            }
            TPmContractPriceMasterEntity master = masterList.get(0);
            Double masterPriceAmount = master.getPriceColumn() == null ? 0.00 : master.getPriceColumn();
            Double masterAuditAmount = master.getAuditColumn() == null ? 0.00 : master.getAuditColumn();
            Double priceAmountRes = new BigDecimal(masterPriceAmount).multiply(new BigDecimal(pp))
                    .divide(new BigDecimal(100)).doubleValue();//????????????????????????????????????x?????????
            entity.setPriceAmount(priceAmountRes);
            priceSum = priceSum.add(new BigDecimal(priceAmountRes));
            Double auditAmountRes = new BigDecimal(masterAuditAmount).multiply(new BigDecimal(ap))
                    .divide(new BigDecimal(100)).doubleValue();//????????????????????????????????????x?????????
            entity.setAuditAmount(auditAmountRes);
            auditSum = auditSum.add(new BigDecimal(auditAmountRes));
            this.commonDao.updateEntitie(entity);
            //??????????????????
            updateMaster(coverEntity.getId(), entity.getTypeId(), priceSum.doubleValue(), auditSum.doubleValue());
        }
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importSalary(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceSalaryEntity.class);
        cq.eq("tpId", coverEntity.getId());
        cq.isNull("parentTypeid");
        cq.add();
        List<TPmContractPriceSalaryEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if(entities.size()==0){
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceSalaryEntity parent = entities.get(0);
        String sql = "delete from  t_pm_contract_price_salary t where t.t_p_id=? and t.parent_typeid=?";
        this.commonDao.executeSql(sql, new Object[] { coverEntity.getId(), parent.getId() });//??????????????????
        int rownum = 7;
        rownum++;
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for(int i=rownum;i<=sheet.getLastRowNum();i++){
        HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String unitDepart = getCellVal(row, 1);//????????????
            String workContent = getCellVal(row, 2);//????????????
        String pricePlanPeople = getCellVal(row, 3);//?????????????????????
        String pricePlanDays = getCellVal(row, 4);//?????????????????????
        String pricePlanCost = getCellVal(row, 5);//??????????????????
        String pricePlanAmount = getCellVal(row, 6);//???????????????
        String auditPlanPeople = getCellVal(row, 7);//?????????????????????
        String auditPlanDays = getCellVal(row, 8);//?????????????????????
        String auditPlanCost= getCellVal(row, 9);//??????????????????
        String auditPlanAmount = getCellVal(row, 10);//???????????????
            String memo = getCellVal(row, 11);//??????
            TPmContractPriceSalaryEntity entity = new TPmContractPriceSalaryEntity();
            entity.setSerialNum(serialNum);
            entity.setUnitDepart(unitDepart);
            entity.setWorkContent(workContent);
            entity.setPricePlanPeople("".equals(pricePlanPeople) ? 0 : Double.valueOf(pricePlanPeople).intValue());
            entity.setPricePlanDays("".equals(pricePlanDays) ? 0.00 : Double.valueOf(pricePlanDays));
            entity.setPricePlanCost("".equals(pricePlanCost) ? 0.00 : Double.valueOf(pricePlanCost));
            Double ppa = "".equals(pricePlanAmount) ? 0.00 : Double.valueOf(pricePlanAmount);
            entity.setPricePlanAmount(ppa);
            priceSum = priceSum.add(new BigDecimal(ppa));
            entity.setAuditPlanPeople("".equals(auditPlanPeople) ? 0 : Double.valueOf(auditPlanPeople).intValue());
            entity.setAuditPlanDays("".equals(auditPlanDays) ? 0.00 : Double.valueOf(auditPlanDays));
            entity.setAuditPlanCost("".equals(auditPlanCost) ? 0.00 : Double.valueOf(auditPlanCost));
            Double apa = "".equals(auditPlanAmount) ? 0.00 : Double.valueOf(auditPlanAmount);
            entity.setAuditPlanAmount(apa);
            auditSum = auditSum.add(new BigDecimal(apa));
            entity.setMemo(memo);
            entity.setParentTypeid(parent.getId());
            entity.setTpId(coverEntity.getId());
            entity.setAddChildFlag("2");
            this.commonDao.save(entity);
        }
        parent.setPricePlanAmount(priceSum.doubleValue());
        parent.setAuditPlanAmount(auditSum.doubleValue());
        this.commonDao.updateEntitie(parent);
        //??????????????????
        updateMaster(coverEntity.getId(), parent.getTypeId(), priceSum.doubleValue(),
                auditSum.doubleValue());
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importTrial(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        CriteriaQuery parentCq = new CriteriaQuery(TPmContractPriceTrialEntity.class);
        parentCq.eq("tpId", coverEntity.getId());
        parentCq.isNull("parentTypeid");
        parentCq.add();
        List<TPmContractPriceTrialEntity> entities = this.commonDao.getListByCriteriaQuery(parentCq, false);
        if (entities.size() == 0) {
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceTrialEntity parent = entities.get(0);
        BigDecimal priceSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (int i = 7; i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String typeName = getCellVal(row, 1);//??????
            String priceExplain = getCellVal(row, 2);//????????????
            String trialAddress = getCellVal(row, 3);//????????????
            String trialDepart = getCellVal(row, 4);//????????????????????????
            String priceAmount = getCellVal(row, 5);//?????????
            String auditAmount = getCellVal(row, 6);//?????????
            String memo = getCellVal(row, 7);//??????
            if (StringUtils.isNotEmpty(serialNum)) {
                CriteriaQuery cq = new CriteriaQuery(TPmContractPriceTrialEntity.class);
                cq.eq("serialNum", serialNum);
                cq.eq("tpId", coverEntity.getId());
                cq.add();
                List<TPmContractPriceTrialEntity> entityList = this.commonDao.getListByCriteriaQuery(cq, false);
                if (entityList.size() > 0) {
                    TPmContractPriceTrialEntity entity = entityList.get(0);
                    entity.setPriceExplain(priceExplain);
                    entity.setTrialAddress(trialAddress);
                    entity.setTrialDepart(trialDepart);
                    Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
                    entity.setPriceAmount(pa);
                    priceSum = priceSum.add(new BigDecimal(pa));
                    Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);
                    entity.setAuditAmount(aa);
                    auditSum = auditSum.add(new BigDecimal(aa));
                    entity.setMemo(memo);
                    this.commonDao.updateEntitie(entity);
                }
                parent.setPriceAmount(priceSum.doubleValue());//?????????????????????
                parent.setAuditAmount(auditSum.doubleValue());
            }
            //??????????????????
            updateMaster(coverEntity.getId(), parent.getTypeId(), priceSum.doubleValue(),
                    auditSum.doubleValue());
        }
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importOutCorp(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceOutCorpEntity.class);
        cq.eq("tpId", coverEntity.getId());
        cq.isNull("parentTypeid");
        cq.add();
        List<TPmContractPriceOutCorpEntity> entities = this.commonDao.getListByCriteriaQuery(cq, false);
        if (entities.size() == 0) {
            throw new BusinessException("????????????????????????");
        }
        TPmContractPriceOutCorpEntity parent = entities.get(0);
        String sql = "delete from  t_pm_contract_price_out_corp t where t.t_p_id=? and t.parent_typeid=?";
        this.commonDao.executeSql(sql, new Object[] { coverEntity.getId(), parent.getId() });//??????????????????
        int rownum = 7;
        rownum++;
        BigDecimal planSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (int i = rownum; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String outsideName = getCellVal(row, 1);//???????????????
            String outsideQuality = getCellVal(row, 2);//????????????
            String outsideProcessUnit = getCellVal(row, 3);//??????????????????
            String priceNumber = getCellVal(row, 4);//?????????-????????????
            String priceUnitPrice = getCellVal(row, 5);//?????????-????????????
            String priceAmount = getCellVal(row, 6);//???????????????
            String auditNumber = getCellVal(row, 7);//?????????-????????????
            String auditUnitPrice = getCellVal(row, 8);//?????????-????????????
            String auditAmount = getCellVal(row, 9);//???????????????
            String memo = getCellVal(row, 10);//??????
            TPmContractPriceOutCorpEntity entity = new TPmContractPriceOutCorpEntity();
            entity.setSerialNum(serialNum);
            entity.setOutsideName(outsideName);
            entity.setOutsideQuality(outsideQuality);
            entity.setOutsideProcessUnit(outsideProcessUnit);
            entity.setPriceNumber("".equals(priceNumber) ? 0.00 : Double.valueOf(priceNumber));
            entity.setPriceUnitPrice("".equals(priceUnitPrice) ? 0.00 : Double.valueOf(priceUnitPrice));
            Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
            entity.setPriceAmount(pa);
            planSum = planSum.add(new BigDecimal(pa));
            entity.setAuditNumber("".equals(auditNumber) ? 0.00 : Double.valueOf(auditNumber));
            entity.setAuditUnitPrice("".equals(auditUnitPrice) ? 0.00 : Double.valueOf(auditUnitPrice));
            Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);
            entity.setAuditAmount(aa);
            auditSum = auditSum.add(new BigDecimal(aa));
            entity.setMemo(memo);
            entity.setParentTypeid(parent.getId());
            entity.setTpId(coverEntity.getId());
            entity.setAddChildFlag("2");
            this.commonDao.save(entity);
        }
        parent.setPriceAmount(planSum.doubleValue());
        parent.setAuditAmount(auditSum.doubleValue());
        this.commonDao.updateEntitie(parent);
        updateMaster(coverEntity.getId(), parent.getTypeId(), planSum.doubleValue(),
                auditSum.doubleValue());
    }

    /**
     * ???????????????
     * 
     * @param sheet
     * @param tPmOutcomeContractApprEntity
     */
    private void importPurchase(HSSFSheet sheet, TPmContractPriceCoverEntity coverEntity) {
        String sql = "delete from t_pm_contract_price_purchase t where t.t_p_id=?";
        this.commonDao.executeSql(sql, coverEntity.getId());//??????????????????
        int rownum = 7;
        BigDecimal planSum = BigDecimal.ZERO;
        BigDecimal auditSum = BigDecimal.ZERO;
        for (int i = rownum; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String serialNum = getCellVal(row, 0);//??????
            String purchaseCategory = getCellVal(row, 1);//??????
            String categoryName = getCellVal(row, 2);//??????
            String categoryBrandModel = getCellVal(row, 3);//??????????????????
            String produceUnit = getCellVal(row, 4);//????????????
            String calculateUnit = getCellVal(row, 5);//????????????
            String produceNum = getCellVal(row, 6);//??????
            String priceUnitprice = getCellVal(row, 7);//???????????????
            String priceAmount = getCellVal(row, 8);//???????????????
            String auditUnitprice = getCellVal(row, 9);//???????????????
            String auditAmount = getCellVal(row, 10);//???????????????
            String memo = getCellVal(row, 11);//??????
            TPmContractPricePurchaseEntity entity = new TPmContractPricePurchaseEntity();
            entity.setTpId(coverEntity.getId());
            entity.setSerialNum(serialNum);
            String pc = this.getCodeByValue("1", "CGZL", purchaseCategory);
            entity.setPurchaseCategory(pc);
            entity.setCategoryName(categoryName);
            entity.setCategoryBrandModel(categoryBrandModel);
            entity.setProduceUnit(produceUnit);
            entity.setCalculateUnit(calculateUnit);
            entity.setProduceNum("".equals(produceNum) ? 0 : Double.valueOf(produceNum).intValue());
            entity.setPriceUnitprice("".equals(priceUnitprice) ? 0.00 : Double.valueOf(priceUnitprice));
            Double pa = "".equals(priceAmount) ? 0.00 : Double.valueOf(priceAmount);
            entity.setPriceAmount(pa);
            planSum = planSum.add(new BigDecimal(pa));
            entity.setAuditUnitprice("".equals(auditUnitprice) ? 0.00 : Double.valueOf(auditUnitprice));
            Double aa = "".equals(auditAmount) ? 0.00 : Double.valueOf(auditAmount);
            entity.setAuditAmount(aa);
            auditSum = auditSum.add(new BigDecimal(aa));
            entity.setMemo(memo);
            this.commonDao.save(entity);
        }
    }
    
    private void updateMaster(String tpId, String budgetId, Double priceAmount, Double auditAmount) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractPriceMasterEntity.class);
        cq.eq("priceBudgetId", budgetId);
        cq.eq("tpId", tpId);
        cq.add();
        List<TPmContractPriceMasterEntity> list = this.commonDao.getListByCriteriaQuery(cq, false);
        if (list.size() == 0) {
            throw new BusinessException("?????????????????????????????????????????????");
        }
        TPmContractPriceMasterEntity entity = list.get(0);
        entity.setPriceColumn(priceAmount);
        entity.setAuditColumn(auditAmount);
        this.commonDao.updateEntitie(entity);
    }



}