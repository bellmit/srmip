package com.kingtake.project.service.impl.change;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.impl.LogContainer;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.entity.xmlb.TBJflxEntity;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.project.entity.change.TBPmProjectChangeEntity;
import com.kingtake.project.entity.change.TBPmProjectChangePropertyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.change.TBPmProjectChangeServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBPmProjectChangeService")
@Transactional
public class TBPmProjectChangeServiceImpl extends CommonServiceImpl implements TBPmProjectChangeServiceI,
        ProjectListServiceI {

    private static final String format = "yyyy-MM-dd HH:mm:ss";

    private Map<String, Object> logEntityMap = LogContainer.logEntityMap;

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TBPmProjectChangeEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TBPmProjectChangeEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TBPmProjectChangeEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TBPmProjectChangeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TBPmProjectChangeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TBPmProjectChangeEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBPmProjectChangeEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
        sql = sql.replace("#{change_reason}", String.valueOf(t.getChangeReason()));
        sql = sql.replace("#{change_according}", String.valueOf(t.getChangeAccording()));
        sql = sql.replace("#{change_time}", String.valueOf(t.getChangeTime()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{bpm_status}", String.valueOf(t.getBpmStatus()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 生成项目变更信息
     */
    @Override
    public List<TBPmProjectChangePropertyEntity> createProjectChange(TPmProjectEntity tPmProject) {
        TPmProjectEntity t = this.commonDao.get(TPmProjectEntity.class, tPmProject.getId());
        String[] excludeFields = new String[] { "projectType", "feeType", "mergeFlag", "createBy",
                "createName",
                "createDate", "updateBy", "updateName", "updateDate", "confirmBy", "confirmName", "confirmDate",
                "approvalUserid", "approvalDate", "approvalStatus", "approvalDeptId", "planFlag", "msgText",
                "projectTypeStr", "feeTypeStr", "devDepartStr", "dutyDepartStr","parentProjectName", "incomeApplyFlag",
                "schoolCooperationFlag","assignFlag","reportState","bmlx","jflxStr","xmlbStr","glxmStr"};
        List<String> hideFields = new ArrayList<String>();
        hideFields.add("manageDepartCode");
        hideFields.add("projectManagerId");
        List<String> excludeFieldList = Arrays.asList(excludeFields);
        Field[] fields = TPmProjectEntity.class.getDeclaredFields();
        List<TBPmProjectChangePropertyEntity> changePropertyList = new ArrayList<TBPmProjectChangePropertyEntity>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (excludeFieldList.contains(field.getName())) {
                continue;
            }
            try {
                ReflectHelper ohelper = new ReflectHelper(t);
                ReflectHelper nhelper = new ReflectHelper(tPmProject);
                Object oldValue = ohelper.getMethodValue(field.getName());
                Object newValue = nhelper.getMethodValue(field.getName());
                if (Date.class.equals(field.getType())) {//日期类型
                    String oldDate = "";
                    String newDate = "";
                    if (oldValue != null) {
                        oldDate = DateUtils.formatDate((Date) oldValue, format);
                    }
                    if (newValue != null) {
                        newDate = DateUtils.formatDate((Date) newValue, format);
                    }
                    if (!oldDate.equals(newDate)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldDate);
                        changeProperty.setNewValue(newDate);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("date");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if (TPmProjectEntity.class.equals(field.getType())) {//项目类型
                    String oldVal = "";
                    String extOld = "";
                    String newVal = "";
                    String extNew = "";
                    if (oldValue != null) {
                        TPmProjectEntity oldProject = (TPmProjectEntity) oldValue;
                        oldVal = oldProject.getProjectName();
                        extOld = oldProject.getId();
                    }
                    if (newValue != null) {
                        TPmProjectEntity tmp = (TPmProjectEntity) newValue;
                        if (StringUtils.isNotEmpty(tmp.getId())) {
                            TPmProjectEntity newProject = this.commonDao.get(TPmProjectEntity.class, tmp.getId());
                            newVal = newProject.getProjectName();
                            extNew = newProject.getId();
                        } else {
                            continue;
                        }
                    }
                    if (!extOld.equals(extNew)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setExtOldValue(extOld);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setExtNewValue(extNew);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("project");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if (TSDepart.class.equals(field.getType())) {//部门类型
                    String oldVal = "";
                    String extOld = "";
                    String newVal = "";
                    String extNew = "";
                    if (oldValue != null) {
                        TSDepart oldDepart = (TSDepart) oldValue;
                        if (StringUtils.isEmpty(oldDepart.getDepartname())) {
                            oldDepart = this.commonDao.get(TSDepart.class, oldDepart.getId());
                        }
                        if (oldDepart != null && StringUtils.isNotEmpty(oldDepart.getId())){
                        	oldVal = oldDepart.getDepartname();
                            extOld = oldDepart.getId();
                        }                        
                    }
                    if (newValue != null) {
                        TSDepart newDepart = (TSDepart) newValue;
                        if (StringUtils.isEmpty(newDepart.getDepartname())) {
                            newDepart = this.commonDao.get(TSDepart.class, newDepart.getId());
                        }
                        if (newDepart != null && StringUtils.isNotEmpty(newDepart.getId())){
                        	newVal = newDepart.getDepartname();
                            extNew = newDepart.getId();
                        }                        
                    }
                    if (!extOld.equals(extNew)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setExtOldValue(extOld);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setExtNewValue(extNew);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("depart");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if (TBXmlbEntity.class.equals(field.getType())) {//TBXmlbEntity  项目类别
                    String oldVal = "";
                    String extOld = "";
                    String newVal = "";
                    String extNew = "";
                    if (oldValue != null) {
                    	TBXmlbEntity oldDepart = (TBXmlbEntity) oldValue;
                        if (StringUtils.isEmpty(oldDepart.getXmlx())) {
                            oldDepart = this.commonDao.get(TBXmlbEntity.class, oldDepart.getId());
                        }
                        if (oldDepart != null && StringUtils.isNotEmpty(oldDepart.getId())){
                        	oldVal = oldDepart.getXmlx();
                            extOld = oldDepart.getId();
                        }                        
                    }
                    if (newValue != null) {
                    	TBXmlbEntity newDepart = (TBXmlbEntity) newValue;
                        if (StringUtils.isEmpty(newDepart.getXmlx())) {
                            newDepart = this.commonDao.get(TBXmlbEntity.class, newDepart.getId());
                        }
                        if (newDepart != null && StringUtils.isNotEmpty(newDepart.getId())){
                        	newVal = newDepart.getXmlx();
                            extNew = newDepart.getId();
                        }                        
                    }
                    if (!extOld.equals(extNew)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setExtOldValue(extOld);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setExtNewValue(extNew);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("xmlb");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if (TBJflxEntity.class.equals(field.getType())) {//经费类型
                    String oldVal = "";
                    String extOld = "";
                    String newVal = "";
                    String extNew = "";
                    if (oldValue != null) {
                    	TBJflxEntity oldDepart = (TBJflxEntity) oldValue;
                        if (StringUtils.isEmpty(oldDepart.getJflxmc())) {
                            oldDepart = this.commonDao.get(TBJflxEntity.class, oldDepart.getId());
                        }
                        if (oldDepart != null && StringUtils.isNotEmpty(oldDepart.getId())){
                        	oldVal = oldDepart.getJflxmc();
                            extOld = oldDepart.getId();
                        }                        
                    }
                    if (newValue != null) {
                    	TBJflxEntity newDepart = (TBJflxEntity) newValue;
                        if (StringUtils.isEmpty(newDepart.getJflxmc())) {
                            newDepart = this.commonDao.get(TBJflxEntity.class, newDepart.getId());
                        }
                        if (newDepart != null && StringUtils.isNotEmpty(newDepart.getId())){
                        	newVal = newDepart.getJflxmc();
                            extNew = newDepart.getId();
                        }                        
                    }
                    if (!extOld.equals(extNew)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setExtOldValue(extOld);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setExtNewValue(extNew);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("jflx");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if (BigDecimal.class.equals(field.getType())) {//数字类型
                    BigDecimal oldVal = BigDecimal.ZERO;
                    BigDecimal newVal = BigDecimal.ZERO;
                    if (oldValue != null) {
                        oldVal = (BigDecimal) oldValue;
                    }
                    if (newValue != null) {
                        newVal = (BigDecimal) newValue;
                    }
                    if (newVal.compareTo(oldVal) != 0) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal.toString());
                        changeProperty.setNewValue(newVal.toString());
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("bigDecimal");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                } else if ("secretDegree".equals(field.getName())) {//项目密级
                    String oldVal = "";
                    String newVal = "";
                    String extOld = "";
                    String extNew = "";
                    if (oldValue != null) {
                        extOld = oldValue.toString();
                    }
                    if (newValue != null) {
                        extNew = newValue.toString();
                    }
                    if (!extOld.equals(extNew)) {
                        TBCodeTypeEntity param = new TBCodeTypeEntity();
                        param.setCodeType("0");
                        param.setCode("XMMJ");
                        TBCodeTypeEntity tmpCodeType = this.tBCodeTypeService.getCodeTypeByCode(param);
                        if (tmpCodeType != null) {
                            TBCodeDetailEntity codeDetailOld = this.tBCodeTypeService.findCodeDetailByCode(
                                    tmpCodeType.getId(), extOld);
                            TBCodeDetailEntity codeDetailNew = this.tBCodeTypeService.findCodeDetailByCode(
                                    tmpCodeType.getId(), extNew);
                            oldVal = codeDetailOld.getName();
                            newVal = codeDetailNew.getName();
                            TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                            changeProperty.setOldValue(oldVal);
                            changeProperty.setExtOldValue(extOld);
                            changeProperty.setNewValue(newVal);
                            changeProperty.setExtNewValue(extNew);
                            changeProperty.setPropertyName(field.getName());
                            changeProperty.setPropertyType("codeType");
                            changeProperty.setPropertyDesc(getFieldDesc(
                                    "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                            changePropertyList.add(changeProperty);
                        }
                    } 
                } else if ("matchSituation".equals(field.getName())) {//配套情况
                    String oldVal = "";
                    String newVal = "";
                    String extOld = "";
                    String extNew = "";
                    if (oldValue != null) {
                        extOld = oldValue.toString();
                    }
                    if (newValue != null) {
                        extNew = newValue.toString();
                    }
                    if (!extOld.equals(extNew)) {
                        TBCodeTypeEntity param = new TBCodeTypeEntity();
                        param.setCodeType("1");
                        param.setCode("PTQK");
                        TBCodeTypeEntity tmpCodeType = this.tBCodeTypeService.getCodeTypeByCode(param);
                        if (tmpCodeType != null) {
                            TBCodeDetailEntity codeDetailOld = this.tBCodeTypeService.findCodeDetailByCode(
                                    tmpCodeType.getId(), extOld);
                            TBCodeDetailEntity codeDetailNew = this.tBCodeTypeService.findCodeDetailByCode(
                                    tmpCodeType.getId(), extNew);
                            oldVal = codeDetailOld.getName();
                            newVal = codeDetailNew.getName();
                            TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                            changeProperty.setOldValue(oldVal);
                            changeProperty.setExtOldValue(extOld);
                            changeProperty.setNewValue(newVal);
                            changeProperty.setExtNewValue(extNew);
                            changeProperty.setPropertyName(field.getName());
                            changeProperty.setPropertyType("codeType");
                            changeProperty.setPropertyDesc(getFieldDesc(
                                    "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                            changePropertyList.add(changeProperty);
                        }
                    }
                }else if ("approvalAuthority".equals(field.getName())) {//审批权限
                    String oldVal = "";
                    String newVal = "";
                    String extOld = "";
                    String extNew = "";
                    if (oldValue != null) {
                        extOld = oldValue.toString();
                    }
                    if (newValue != null) {
                        extNew = newValue.toString();
                    }
                    if (!extOld.equals(extNew)) {
                    	if(extOld.equals("0")){
                    		oldVal = "旧审批权限";
                    	}else if(extOld.equals("1")){
                    		oldVal = "新审批权限";
                    	}
                    	if(extNew.equals("0")){
                    		newVal = "旧审批权限";
                    	}else if(extNew.equals("1")){
                    		newVal = "新审批权限";
                    	}
                    	TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setExtOldValue(extOld);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setExtNewValue(extNew);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("codeType");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                }else if ("certificates".equals(field.getName())) {//附件
                	  List<TSFilesEntity> oldList = null;
                	  List<TSFilesEntity> newList = null;
                	  if (oldValue != null) {
                		 oldList = (List<TSFilesEntity>) oldValue;
                      }
                      if (newValue != null) {
                    	  newList = (List<TSFilesEntity>) newValue;
                      }
                      if(CollectionUtils.isEmpty(oldList) && CollectionUtils.isEmpty(newList)) {
                		  continue;
                	  }
                      //TODO附件变化情况
                      if(oldList.size() != newList.size()) {
                    	  
                      }
                }else {//其他类型当做字符串处理
                    String oldVal = "";
                    String newVal = "";
                    if (oldValue != null) {
                        oldVal = oldValue.toString();
                    }
                    if (newValue != null) {
                        newVal = newValue.toString();
                    }
                    if (!oldVal.equals(newVal)) {
                        TBPmProjectChangePropertyEntity changeProperty = new TBPmProjectChangePropertyEntity();
                        changeProperty.setOldValue(oldVal);
                        changeProperty.setNewValue(newVal);
                        changeProperty.setPropertyName(field.getName());
                        changeProperty.setPropertyType("string");
                        changeProperty.setPropertyDesc(getFieldDesc(
                                "com.kingtake.project.entity.manage.TPmProjectEntity", field.getName()));
                        changePropertyList.add(changeProperty);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException("生成项目变更失败", e);
            }
        }
        for (TBPmProjectChangePropertyEntity changeProperty : changePropertyList) {
            if (hideFields.contains(changeProperty.getPropertyName())) {
                changeProperty.setShowFlag("0");
            }
        }
        return changePropertyList;
    }

    /**
     * 获取值
     * 
     * @param obj
     * @return
     */
    private String getValue(Object obj) {
        String str = "";
        if (obj != null) {
            str = obj.toString();
        } else {
            str = "";
        }
        return str;
    }

    /**
     * 保存项目变更申请表
     */
    @Override
    public void saveProjectChange(TBPmProjectChangeEntity projectChange) {
        try {
            if (StringUtils.isNotEmpty(projectChange.getId())) {
                TBPmProjectChangeEntity t = this.commonDao.get(TBPmProjectChangeEntity.class, projectChange.getId());
                MyBeanUtils.copyBeanNotNull2Bean(projectChange, t);
                this.commonDao.saveOrUpdate(t);
            } else {
                String projectId = projectChange.getProjectId();
                TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
                if (project != null) {
                    projectChange.setProjectName(project.getProjectName());
                }
                projectChange.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                this.commonDao.save(projectChange);
                String propertyChangeStr = projectChange.getPropertyChangeStr();
                List<TBPmProjectChangePropertyEntity> changeProjectList = JSON.parseArray(propertyChangeStr,
                        TBPmProjectChangePropertyEntity.class);
                for (TBPmProjectChangePropertyEntity entity : changeProjectList) {
                    entity.setChangeId(projectChange.getId());
                    this.commonDao.save(entity);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("保存项目信息变更申请失败!", e);
        }
    }

    /**
     * 获取字段描述.
     * 
     * @param clazzName
     * @param field
     * @return
     */
    private String getFieldDesc(String clazzName, String field) {
        String res = null;
        if (!"#operateObjName#".equals(field)) {
            res = field;
        }
        if (logEntityMap.containsKey(clazzName)) {
            Map<String, Object> fieldMap = (Map<String, Object>) logEntityMap.get(clazzName);
            if (fieldMap.containsKey(field)) {
                res = (String) fieldMap.get(field);
            }
        }
        return res;
    }

    /**
     * 审核通过之后，修改
     * 
     * @param changeId
     */
    @Override
    public void changeProjectAfterAudit(String changeId) {
        TBPmProjectChangeEntity changeEntity = this.commonDao.get(TBPmProjectChangeEntity.class, changeId);
        
        CriteriaQuery cq = new CriteriaQuery(TBPmProjectChangePropertyEntity.class);
        cq.eq("changeId", changeId);
        cq.add();
        List<TBPmProjectChangePropertyEntity> changeList = this.commonDao.getListByCriteriaQuery(cq, false);
        TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, changeEntity.getProjectId());
        TPmProjectEntity project2 = this.commonDao.get(TPmProjectEntity.class, project.getGlxm().getId());
        
        getModifiedVlalue(changeList, project);
        getModifiedVlalue(changeList, project2);
        
        
        this.commonDao.updateEntitie(project);
        this.commonDao.updateEntitie(project2);
        
    }

	private void getModifiedVlalue(List<TBPmProjectChangePropertyEntity> changeList,TPmProjectEntity project) {
		ReflectHelper helper = new ReflectHelper(project);
        for (TBPmProjectChangePropertyEntity propertyEntity : changeList) {
            if("project".equals(propertyEntity.getPropertyType())){
                String projectId = propertyEntity.getExtNewValue();
                TPmProjectEntity tmpProject = this.commonDao.get(TPmProjectEntity.class, projectId);
                helper.setMethodValue(propertyEntity.getPropertyName(), tmpProject);
            } else if ("depart".equals(propertyEntity.getPropertyType())) {
                String departId = propertyEntity.getExtNewValue();
                TSDepart tmpDepart = this.commonDao.get(TSDepart.class, departId);
                helper.setMethodValue(propertyEntity.getPropertyName(), tmpDepart);
            } else if ("date".equals(propertyEntity.getPropertyType())) {
                Date date = null;
                try {
                    date = DateUtils.parseDate(propertyEntity.getNewValue(), "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                helper.setMethodValue(propertyEntity.getPropertyName(), date);
            } else if ("bigDecimal".equals(propertyEntity.getPropertyType())) {
                BigDecimal big = new BigDecimal(propertyEntity.getNewValue());
                helper.setMethodValue(propertyEntity.getPropertyName(), big);
            } else if ("codeType".equals(propertyEntity.getPropertyType())) {
                helper.setMethodValue(propertyEntity.getPropertyName(), propertyEntity.getExtNewValue());
            } else {
                helper.setMethodValue(propertyEntity.getPropertyName(), propertyEntity.getNewValue());
            }
        }
	}

	@Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = param.get("key");
        return activitiService.getAuditCount(businessCode);
    }

}