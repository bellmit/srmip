package com.kingtake.project.service.impl.manage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.incomeplan.TPmFpje;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;

@Service("tPmProjectService")
@Transactional
public class TPmProjectServiceImpl extends CommonServiceImpl implements TPmProjectServiceI,ApprFlowServiceI,ProjectListServiceI {

    @Autowired
    private TOMessageServiceI tOMessageService;
    
    private static String[] titles = { "????????????","????????????","?????????","????????????(????????????)??????","????????????","??????????????????","??????????????????","????????????","?????????","??????????????????","????????????"};
    
    private static String[] titlesHt = { "????????????","????????????","????????????","????????????","????????????","????????????","????????????","????????????","?????????","??????????????????","???????????????","????????????","????????????"};
    
    private static String[] titlesJd = { "????????????","????????????","????????????","????????????","????????????","??????????????????","??????","????????????","????????????"};
    
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//???????????????????????????sql??????
		this.doDelSql((TPmProjectEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//???????????????????????????sql??????
 		this.doAddSql((TPmProjectEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//???????????????????????????sql??????
 		this.doUpdateSql((TPmProjectEntity)entity);
 	}
 	
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmProjectEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmProjectEntity t){
	 	return true;
 	}
 	/**
	 * ????????????-sql??????-????????????
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmProjectEntity t){
	 	return true;
 	}
 	
 	/**
	 * ??????sql????????????
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmProjectEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_no}",String.valueOf(t.getProjectNo()));
 		sql  = sql.replace("#{project_status}",String.valueOf(t.getProjectStatus()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_manager}",String.valueOf(t.getProjectManager()));
 		sql  = sql.replace("#{project_abstract}",String.valueOf(t.getProjectAbstract()));
 		sql  = sql.replace("#{begin_date}",String.valueOf(t.getBeginDate()));
 		sql  = sql.replace("#{end_date}",String.valueOf(t.getEndDate()));
 		sql  = sql.replace("#{manage_depart}",String.valueOf(t.getManageDepart()));
 		sql  = sql.replace("#{manager_phone}",String.valueOf(t.getManagerPhone()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{plan_contract_flag}",String.valueOf(t.getPlanContractFlag()));
 		sql  = sql.replace("#{project_type}",String.valueOf(t.getProjectType()));
 		sql  = sql.replace("#{outside_no}",String.valueOf(t.getOutsideNo()));
 		sql  = sql.replace("#{fee_type}",String.valueOf(t.getFeeType()));
 		sql  = sql.replace("#{sub_type}",String.valueOf(t.getSubType()));
 		sql  = sql.replace("#{accounting_code}",String.valueOf(t.getAccountingCode()));
 		sql  = sql.replace("#{plan_contract_ref_no}",String.valueOf(t.getPlanContractRefNo()));
 		sql  = sql.replace("#{contract_date}",String.valueOf(t.getContractDate()));
 		sql  = sql.replace("#{plan_contract_name}",String.valueOf(t.getPlanContractName()));
 		sql  = sql.replace("#{source_unit}",String.valueOf(t.getSourceUnit()));
 		sql  = sql.replace("#{project_source}",String.valueOf(t.getProjectSource()));
        sql = sql.replace("#{developer_depart}", String.valueOf(t.getDevDepart().getDepartname()));
 		sql  = sql.replace("#{duty_depart}",String.valueOf(t.getDutyDepart()));
 		sql  = sql.replace("#{fee_single_column}",String.valueOf(t.getFeeSingleColumn()));
 		sql  = sql.replace("#{secret_degree}",String.valueOf(t.getSecretDegree()));
 		sql  = sql.replace("#{appraisal_flag}",String.valueOf(t.getAppraisalFlag()));
 		sql  = sql.replace("#{all_fee}",String.valueOf(t.getAllFee()));
        sql = sql.replace("#{parent_project}", String.valueOf(t.getParentPmProject().getProjectName()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	

  /**
   * ?????????????????????
   * @param projectId
   */
  @Override
public void researchGroupConfirm(String projectId){
    //???????????????????????????????????????
    TPmProjectEntity tPmProject = this.commonDao.get(TPmProjectEntity.class, projectId);
    tPmProject.setConfirmBy(ResourceUtil.getSessionUserName().getId());
    tPmProject.setConfirmName(ResourceUtil.getSessionUserName().getRealName());
    tPmProject.setConfirmDate(new Date());
    this.commonDao.save(tPmProject);
    
    //???????????????????????????????????????????????????????????????????????????
    if(StringUtils.isNotEmpty(tPmProject.getApprovalUserid())){
      TOMessageEntity message = new TOMessageEntity();
      message.setSendTime(new Date());
      message.setSenderName(ResourceUtil.getSessionUserName().getRealName());
      message.setSenderId(ResourceUtil.getSessionUserName().getId());
      message.setTitle("????????????????????????");
      StringBuffer sb = new StringBuffer();
      TSBaseUser user = this.get(TSBaseUser.class, tPmProject.getApprovalUserid());
      sb.append(tPmProject.getProjectName()+"???"+DateUtils.date2Str(tPmProject.getApprovalDate(), DateUtils.yyyyMMdd)+"???"+ user.getRealName() +"??????!!");
      message.setContent(sb.toString());
      this.commonDao.save(message);
    }
  }

@Override
public void doPropose(String id, String msgText) {
    if (StringUtils.isNotEmpty(id)) {
        TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, id);
        project.setApprovalStatus("2");//????????????
        project.setMsgText(msgText);
        this.commonDao.updateEntitie(project);
        if(StringUtils.isNotEmpty(msgText)&&msgText.length()>1000){
            msgText = msgText.substring(0,990)+"...";
        }
        String content = "????????????["+project.getProjectName()+"]?????????????????????"+msgText;
        String userName = project.getCreateBy();
        TSUser user = this.commonDao.findUniqueByProperty(TSUser.class, "userName", userName);
        tOMessageService.sendMessage(user.getId(), "????????????", "????????????",
                content, project.getOfficeStaff());//?????????????????????????????????
    }
    
}

@Override
public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) {
    OutputStream out = null;
    InputStream templateIs = null;
    try {
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileName = "";
        	templateIs = new FileInputStream(classPath + "export" + File.separator + "template" + File.separator
                    + "projectTemplate2.xls");
            fileName = "??????????????????????????????.xls";
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
        response.setHeader("Content-Disposition", "attachment; filename="
                + fileName);
        out = response.getOutputStream();
        IOUtils.copy(templateIs, out);
    } catch (Exception e) {
        e.printStackTrace();
        throw new BusinessException("??????????????????", e);
    } finally {
        IOUtils.closeQuietly(templateIs);
        IOUtils.closeQuietly(out);
    }
}

@Override
public String importProjecjExcel(HSSFWorkbook wb,String lxStatus,String jhid) {
	StringBuilder errs = new StringBuilder();
    HSSFSheet sheet = wb.getSheetAt(0);
    if (sheet == null) {
    	errs .append("EXCEL???????????????????????????0????????????<br>");
        return errs.toString();
    }
    //????????????
    int rownum = sheet.getLastRowNum();
    //?????????????????????
    int rownumSuccess = 0;
    //TSUser user = ResourceUtil.getSessionUserName();
    for (int i = 1; i <= rownum; i++) {
    	//??????????????????
    	StringBuilder msg = new StringBuilder();
    	
    	HSSFRow row = sheet.getRow(i);
       
        //????????????
        TPmProjectEntity project = new TPmProjectEntity();
        
        /*??????????????????*/
        
        //????????????
        String projectName = getCellValue(row, 0);
        if(StringUtils.isEmpty(projectName)) {
        	msg.append("??????????????????");
        }
        project.setProjectName(projectName);
        
        //????????????
        String projectAbstract = getCellValue(row, 1);
        if(StringUtils.isEmpty(projectAbstract)) {
        	msg.append("?????????????????????");
        }
        project.setProjectAbstract(projectAbstract);
        
        //????????????
        String secretDegreeStr = getCellValue(row, 2);
        if (StringUtils.isNotEmpty(secretDegreeStr)) {
            try {                
                String secretDegree = getCodeValue("XMMJ", "0", secretDegreeStr);
                if(StringUtils.isNotEmpty(secretDegree)){
                	project.setSecretDegree(secretDegree);
                }else{
                	project.setSecretDegree("1");
                }
            } catch (Exception e) {
            	project.setSecretDegree("1");
            }
        }else{
        	project.setSecretDegree("1");
        }
        
        //????????????
        String beginDateStr = getCellValue(row, 3);
        if (StringUtils.isEmpty(beginDateStr)) {
        	msg.append("?????????????????????");
        }else {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date beginDate = sdf.parse(beginDateStr);
	            project.setBeginDate(beginDate);
	        } catch (Exception e) {
	        	msg.append("??????????????????????????????????????????yyyy-MM-dd");
	        }
        }
        
        //????????????
        String endDateStr = getCellValue(row, 4);
        if (StringUtils.isEmpty(endDateStr)) {
        	msg.append("?????????????????????");
        }else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date endDate = sdf.parse(endDateStr);
                project.setEndDate(endDate);
            } catch (Exception e) {
            	msg.append("??????????????????????????????????????????yyyy-MM-dd");
            }
        }
        
        //????????????
        String dutyDepartName = getCellValue(row, 5);
        TSDepart dutyDepart = getDeptByName(dutyDepartName);
        if (dutyDepart != null) {
        	project.setDutyDepart(dutyDepart);
        }
        
        //????????????
        String devDepartName = getCellValue(row, 6);
        TSDepart devDepart = null;
        if(StringUtils.isEmpty(devDepartName)) {
        	msg.append("?????????????????????");
        }else {
	        devDepart = getDeptByName(devDepartName);
	        if (devDepart != null) {
	        	project.setDevDepart(devDepart);
	        }else {
	        	msg.append("??????????????????????????????????????????????????????");
	        }
        }
        
        //?????????
        String projectManagerStr = getCellValue(row, 7);
        TSUser projectManager = null;
        if(StringUtils.isEmpty(projectManagerStr)){
        	msg.append("??????????????????");
        }else {
        	projectManager = getUserByName(projectManagerStr);
	        if (projectManager != null) {
	        	project.setProjectManagerId(projectManager.getId());
	        	project.setProjectManager(projectManager.getRealName());
	        	project.setManagerPhone(projectManager.getMobilePhone());
	        }else {
	        	msg.append("???????????????????????????????????????????????????");
	        }
        }
        
        //????????????
        String manageDepart = getCellValue(row, 8);
        if (StringUtils.isEmpty(manageDepart)) {
        	msg.append("?????????????????????");
        }else {
        	project.setManageDepart(manageDepart);
        	String manageDepartCode = getCodeValue("FGBM", "1", manageDepart);
        	if(StringUtils.isEmpty(manageDepart)){
        		msg.append("??????????????????????????????????????????????????????,???????????????????????????");
            }else{
            	project.setManageDepartCode(manageDepartCode);
            }
        }
        
        //???????????????
        String projectTypeStr = getCellValue(row, 9);
        List<TBXmlbEntity> xmlb = null;
        if (StringUtils.isEmpty(projectTypeStr)) {
        	msg.append("????????????????????????");
        }else {
        	xmlb = this.commonDao.findByProperty(TBXmlbEntity.class, "xmlb", projectTypeStr);
	        if(xmlb.size() > 0){
	        	project.setXmml(xmlb.get(0).getParentType()==null?"":xmlb.get(0).getParentType().getXmlb());
	        	project.setXmlb(xmlb.get(0));
	        	project.setJflx(xmlb.get(0).getJf());
	        	project.setZgdw(xmlb.get(0).getZgdw()==null?"":xmlb.get(0).getZgdw());
	        	project.setXmlx(xmlb.get(0).getXmlx()==null?"":xmlb.get(0).getXmlx());
	        	project.setXmxz(xmlb.get(0).getXmxz()==null?"":xmlb.get(0).getXmxz());
	        	project.setXmly(xmlb.get(0).getXmly()==null?"":xmlb.get(0).getXmly());
	        }else {
	        	msg.append("?????????????????????????????????????????????????????????");
	        }
        }
        //?????????????????????
        String rwzd = getCellValue(row, 10);
        if (StringUtils.isEmpty(rwzd)) {
        	rwzd = "1";
        	project.setRwzd(rwzd);
        }else {
            rwzd = getCodeValue("RWZD", "0", rwzd);
            rwzd = StringUtils.isEmpty(rwzd) ? "1" : rwzd;
            project.setRwzd(rwzd);   
	    }
        
        //?????????????????????
        String lxyj = getCellValue(row, 11);
        if (StringUtils.isEmpty(lxyj)) {
        	lxyj="1";
        	project.setLxyj(lxyj);
        }else {
        	lxyj = getCodeValue("LXYJ", "0", lxyj);
        	lxyj = StringUtils.isEmpty(lxyj) ? "1" : lxyj;
            project.setLxyj(lxyj);   
	    }
        
        //?????????
        String contact = getCellValue(row, 12);
        if(StringUtils.isEmpty(contact)) {
        	msg.append("??????????????????");
        }
        project.setContact(contact);
        
        //????????????
        String contactPhone = getCellValue(row, 13);
        if(StringUtils.isEmpty(contactPhone)){
        	msg.append("?????????????????????");
        }
        project.setContactPhone(contactPhone);
      
        //????????????
        String accountingCode = getCellValue(row, 14);
        if(accountingCode.indexOf(".") != -1){
        	accountingCode = accountingCode.split("\\.")[0];
        }
        project.setAccountingCode(accountingCode);
        
        //???????????????
        String yxmbh = getCellValue(row, 15);
        project.setYxmbh(yxmbh);
        
        //????????????/????????????
        String planContractName = getCellValue(row, 16);
        project.setPlanContractName(planContractName);
        
        //????????????/????????????
        String planContractRefNo = getCellValue(row, 17);
        project.setPlanContractRefNo(planContractRefNo);
        
        //?????????
        String allFee = getCellValue(row, 18);
        BigDecimal bdz = new BigDecimal(0);
        if(StringUtils.isNotEmpty(allFee)){
        	try {
	        	bdz = new BigDecimal(allFee);
	            project.setAllFee(bdz);
        	}catch(Exception ex) {
        		msg.append("???????????????????????????");
        	}
        }else {
        	msg.append("??????????????????");
        }
        
        //?????????
        String yap = getCellValue(row, 19);
        BigDecimal nyap = new BigDecimal(0);
        try {
    			nyap = new BigDecimal(yap);
    			project.setYap(yap);
    	}catch(Exception ex) {
    		msg.append("??????????????????????????????");
    	}    
       
        
        //????????????
        String dnjf = getCellValue(row, 20);
        BigDecimal ndnjf = new BigDecimal(0);
        if(StringUtils.isEmpty(dnjf)) {
        	msg.append("?????????????????????");
        }else {
        	try {
        		
        		if("1".equals(lxyj)) { //?????????????????????????????????????????????????????????????????????
        			ndnjf = new BigDecimal(dnjf);
        			project.setDnjf(dnjf); 
        		}
        	}catch(Exception ex) {
        		msg.append("??????????????????????????????");
        	}    	
        }
        
        
        if(nyap.doubleValue()+ndnjf.doubleValue() > bdz.doubleValue()) {
        	msg.append("????????????+?????????????????????????????????????????????");
        }
        
        
        //????????????
        String zmye = getCellValue(row, 21);
        project.setZmye(zmye);	
        
        //??????????????????
        String schoolCooperationFlag = getCellValue(row, 22);
        schoolCooperationFlag = ("???".equals(schoolCooperationFlag)||"1".equals(schoolCooperationFlag)) ? "1" : "0";
        project.setSchoolCooperationFlag(schoolCooperationFlag);
        
        //??????
        String bz = getCellValue(row, 23);
        project.setBz(bz);
        
        //????????????
        String sourceUnit = getCellValue(row, 24);
        project.setSourceUnit(sourceUnit);
        
        project.setScbz("0");
       
        if(StringUtils.isNotEmpty(jhid)){
        	project.setJhid(jhid);
        }
        project.setApprovalStatus(ProjectConstant.APPROVAL_YES);
        project.setProjectStatus(ProjectConstant.PROJECT_STATUS_SETUP);
        project.setMergeFlag(ProjectConstant.PROJECT_MERGE_FLAG_NORMAL);
        
        String project_No = "";
        if(xmlb!=null){
        	if(xmlb.size() > 0 && StringUtils.isNotEmpty(beginDateStr) && StringUtils.isNotEmpty(endDateStr)){
        		String xmlx = xmlb.get(0).getXmlx();
                String xmxz = xmlb.get(0).getXmxz();
                String xmly = xmlb.get(0).getXmly();
                String wybh = "";
                if(xmlb.get(0).getWybh()!=null){
                	wybh = xmlb.get(0).getWybh().toString();
                }
                String bd = beginDateStr.substring(2, 4);
                String ed = endDateStr.substring(2, 4);
                
                String xmbhSql = "select t.PROJECT_NO,lsh from T_PM_PROJECT t where lsh is not null order by to_number(lsh) desc";
                List<Map<String, Object>> numMapList = this.commonDao.findForJdbc(xmbhSql);
                String mr = "0000";
                int lshTemp;
                if (numMapList!=null && numMapList.size() > 0 && numMapList.get(0).get("lsh")!="" && numMapList.get(0).get("lsh")!=null) {
                    String lsh = (String) numMapList.get(0).get("lsh");
                    lshTemp = Integer.parseInt(lsh);
                    lshTemp++;
                    if(lshTemp<10){
                    	mr = "000"+lshTemp;
                    }else if(lshTemp<100){
                    	mr = "00"+lshTemp;
                    }else if(lshTemp<1000){
                    	mr = "0"+lshTemp;
                    }else{
                    	mr = String.valueOf(lshTemp);
                    }
                }
                project.setLsh(mr);
                
                project_No = xmlx+xmxz+xmly+rwzd+wybh+lxyj+bd+ed+mr;
        	}
        }
        
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String dqsj = sdf2.format(new Date());
        project.setCxm(dqsj+project.getLsh());
        
        //????????????????????????????????????????????????
        if(msg.length()>0) {
        	msg = msg.indexOf("???")==0 ? msg.delete(0, 1) : msg;
        	errs.append("--???"+(i+1)+"????????????").append( msg.toString()).append("#");
        	continue;
        }
        
        try {
	        //?????????1????????????
        	TPmProjectEntity sbProject = (TPmProjectEntity)project.clone();
	        sbProject.setAuditStatus("2");//?????????
	        sbProject.setLxStatus(null);//?????????
	        this.commonDao.save(sbProject);
	        
	        //?????????1????????????
	        project.setProjectNo(project_No);//????????????
	        project.setAuditStatus("2");
	        project.setLxStatus(lxStatus);
	        this.commonDao.save(project);
	        
	        TPmFpje fpje = new TPmFpje();
            fpje.setJhwjid(project.getJhid());
            fpje.setXmid(project.getId());
            fpje.setJe("0");
            this.commonDao.save(fpje);
	        
	        //??????????????????
	        if(projectManager!=null){
	        	TPmProjectMemberEntity tPmProjectMember = new TPmProjectMemberEntity();               
	        	if (projectManager.getId() != null) {
	                tPmProjectMember.setUser(projectManager);
	                tPmProjectMember.setName(projectManager.getRealName());
	                tPmProjectMember.setSex(projectManager.getSex());
	                tPmProjectMember.setBirthday(projectManager.getBirthday());
	                tPmProjectMember.setPosition(projectManager.getDuty());
	            }
	            if (devDepart!=null && devDepart.getId() != null && !devDepart.getId().equals("")) {
	                tPmProjectMember.setSuperUnit(devDepart);
	                tPmProjectMember.setSuperUnitId(devDepart.getId());
	                tPmProjectMember.setSuperUnitName(devDepart.getDepartname());
	            }
	            if (project.getId() != null) {
	                tPmProjectMember.setProject(project);
	                tPmProjectMember.setProjectName(project.getProjectName());
	            }
	            tPmProjectMember.setProjectManager(SrmipConstants.YES);
	            
	            this.commonDao.save(tPmProjectMember);
	        }
        }catch(Exception ex) {
        	errs.append("--???"+(i+1)+"???????????????????????????????????????").append(ex.getMessage()).append("#");
        }
        rownumSuccess++;
    }
    
    String rs = "{\"rownum\":\""+rownum+"\",\"rownumSuccess\":\""+rownumSuccess+"\",\"errs\":\""+errs.toString()+"\"}";
    return rs;
}

/**
 * ??????????????????
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

//?????????????????????????????????
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

//????????????????????????
private TSUser getUserByName(String name) {
    List<TSUser> userList = this.commonDao.findByProperty(TSUser.class, "realName", name);
    if (userList.size() > 0) {
        TSUser user = userList.get(0);
        return user;
    }
    return null;
}

//???????????????????????????
private TSDepart getDeptByName(String name) {
    List<TSDepart> deptList = this.commonDao.findByProperty(TSDepart.class, "departname", name);
    if (deptList.size() > 0) {
        TSDepart dept = deptList.get(0);
        return dept;
    }
    return null;
}

    /**
     * ???????????????
     */
    @Override
    public BigDecimal getAllFee(String projectId) {
        TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, projectId);
        BigDecimal allFee = BigDecimal.ZERO;
        allFee = project.getAllFee();
       /* if (ProjectConstant.PROJECT_PLAN.equals(project.getPlanContractFlag())) {
            if (project.getAllFee() != null) {
                allFee = project.getAllFee();
            }
        } else {
            List<TPmIncomeContractApprEntity> incomeContractApprList = this.commonDao.findByProperty(
                    TPmIncomeContractApprEntity.class, "project.id", project.getId());
            if (incomeContractApprList.size() > 0) {
                TPmIncomeContractApprEntity incomeContract = incomeContractApprList.get(0);
                if (incomeContract.getTotalFunds() != null) {
                    allFee = incomeContract.getTotalFunds();
                }
            }
        }*/
        return allFee;
    }

    @Override
    public Workbook exportProject() {
    	String sqlXm = "select a.project_no,a.project_name,a.project_manager,a.duty_depart,d.departname as duty_depart_name,"
    			+ " a.accounting_code,b.contract_Code,b.contract_Name,"
    			+ " a.project_source,a.all_fee,a.fee_type,c.funds_Name as fee_type_name,a.project_status "
    			+ " from t_Pm_Income_Contract_Appr b,t_pm_project a "
    			+ " left join t_b_funds_property c on c.id=a.fee_type "
    			+ " left join t_s_depart d on d.id=a.duty_depart "
    			+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' ";
    	List<Map<String, Object>> projectMapXm = this.findForJdbc(sqlXm);
    	
    	String sqlHt = "select a.project_no,a.project_name,b.contract_Code,b.contract_Name,b.contract_signing_time,b.start_Time,b.end_Time,"
    			+ " b.acquisition_Method,b.total_Funds,b.contract_Type,e.name as contract_Type_name,"
    			+ " c.bank_b,c.account_Name_B,c.account_Id_B "
    			+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,t_pm_contract_basic c,t_b_code_type d,t_b_code_detail e "
    			+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid "
    			+ " and b.contract_type=e.code and d.id=e.code_type_id and d.code='HTLB'";
    	List<Map<String, Object>> projectMapHt = this.findForJdbc(sqlHt);
    	
    	String sqlJd = "select a.project_no,a.project_name,b.contract_Code,b.contract_Name,"
    			+ " c.node_name,c.complete_Date,c.pay_amount,d.finish_time "
    			+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,T_PM_CONTRACT_NODE c "
    			+ " left join T_PM_CONTRACT_NODE_CHECK d on c.id=d.contract_node_id "
    			+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid";
    	List<Map<String, Object>> projectMapJd = this.findForJdbc(sqlJd);
    
        HSSFWorkbook wb = new HSSFWorkbook();
        createSheetXm(wb, projectMapXm);
        createSheetHt(wb, projectMapHt);
        createSheetJd(wb, projectMapJd);
        return wb;
    }
    
    /**
     * ??????sheet???
     * 
     * @param wb
     * @param year
     * @param list
     */
      private void createSheetXm(HSSFWorkbook wb,List<Map<String, Object>> projectMap){
        HSSFSheet sheet = wb.createSheet("??????????????????");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleStyle = getTitleStyle(wb);
        for (int i = 0; i < titles.length; i++) {
        	HSSFCell cell = titleRow.createCell(i);
        	cell.setCellValue(titles[i]);
        	cell.setCellStyle(titleStyle);
        }
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 6000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 6000);
//        sheet.setColumnWidth(11, 6000);
//        sheet.setColumnWidth(12, 6000);
        int rownum = projectMap.size();
        for(int a=1;a<=rownum;a++){
        	Map map=projectMap.get(a-1);
        	HSSFRow row = sheet.createRow(a);
        	HSSFCell cell1 = row.createCell(0);//????????????
        	cell1.setCellValue(map.get("PROJECT_NO") == null ? "" : map.get("PROJECT_NO").toString());
            HSSFCell cell0 = row.createCell(1);//????????????
        	cell0.setCellValue(map.get("PROJECT_NAME").toString());
          	HSSFCell cell2 = row.createCell(2);//?????????
          	cell2.setCellValue(map.get("PROJECT_MANAGER")== null ? "" : map.get("PROJECT_MANAGER").toString());
//          	HSSFCell cell3 = row.createCell(3);//????????????
//          	cell3.setCellValue(map.get("DUTY_DEPART")== null ? "" : map.get("DUTY_DEPART").toString());
          	HSSFCell cell4 = row.createCell(3);//????????????
          	cell4.setCellValue(map.get("DUTY_DEPART_NAME")== null ? "" : map.get("DUTY_DEPART_NAME").toString());
          	HSSFCell cell5 = row.createCell(4);//????????????
          	cell5.setCellValue(map.get("ACCOUNTING_CODE")== null ? "" : map.get("ACCOUNTING_CODE").toString());
          	HSSFCell cell6 = row.createCell(5);//??????????????????
          	cell6.setCellValue(map.get("CONTRACT_CODE")== null ? "" : map.get("CONTRACT_CODE").toString());
          	HSSFCell cell7 = row.createCell(6);//??????????????????
          	cell7.setCellValue(map.get("CONTRACT_NAME")== null ? "" : map.get("CONTRACT_NAME").toString());
          	HSSFCell cell8 = row.createCell(7);//????????????
          	cell8.setCellValue(map.get("PROJECT_SOURCE")== null ? "" : map.get("PROJECT_SOURCE").toString());
          	HSSFCell cell9 = row.createCell(8);//?????????
          	cell9.setCellValue(map.get("ALL_FEE")== null ? "" : map.get("ALL_FEE").toString());
//          	HSSFCell cell10 = row.createCell(10);//????????????
//          	cell10.setCellValue(map.get("FEE_TYPE")== null ? "" : map.get("FEE_TYPE").toString());
          	HSSFCell cell11 = row.createCell(9);//????????????
          	cell11.setCellValue(map.get("FEE_TYPE_NAME")== null ? "" : map.get("FEE_TYPE_NAME").toString());
          	HSSFCell cell12 = row.createCell(10);//????????????
          	cell12.setCellValue(map.get("PROJECT_STATUS")== null ? "" : ProjectConstant.projectStatusMap.get(map.get("PROJECT_STATUS").toString()));
        }
      }
      
      private void createSheetHt(HSSFWorkbook wb,List<Map<String, Object>> projectMap){
          HSSFSheet sheet = wb.createSheet("??????????????????");
          HSSFRow titleRow = sheet.createRow(0);
          HSSFCellStyle titleStyle = getTitleStyle(wb);
          for (int i = 0; i < titlesHt.length; i++) {
          	HSSFCell cell = titleRow.createCell(i);
          	cell.setCellValue(titlesHt[i]);
          	cell.setCellStyle(titleStyle);
          }
          sheet.setColumnWidth(0, 6000);
          sheet.setColumnWidth(1, 6000);
          sheet.setColumnWidth(2, 6000);
          sheet.setColumnWidth(3, 6000);
          sheet.setColumnWidth(4, 5000);
          sheet.setColumnWidth(5, 5000);
          sheet.setColumnWidth(6, 6000);
          sheet.setColumnWidth(7, 6000);
          sheet.setColumnWidth(8, 5000);
          sheet.setColumnWidth(9, 3000);
          sheet.setColumnWidth(10, 6000);
          sheet.setColumnWidth(11, 3000);
          sheet.setColumnWidth(12, 3000);
          sheet.setColumnWidth(13, 3000);
          int rownum = projectMap.size();
          for(int a=1;a<=rownum;a++){
          	Map map=projectMap.get(a-1);
          	HSSFRow row = sheet.createRow(a);
          	
          	HSSFCell cell1 = row.createCell(0);//????????????
          	cell1.setCellValue(map.get("PROJECT_NO") == null ? "" : map.get("PROJECT_NO").toString());
            HSSFCell cell0 = row.createCell(1);//????????????
          	cell0.setCellValue(map.get("PROJECT_NAME").toString());
            HSSFCell cell2 = row.createCell(2);//????????????
            cell2.setCellValue(map.get("CONTRACT_CODE")== null ? "" : map.get("CONTRACT_CODE").toString());
            HSSFCell cell3 = row.createCell(3);//????????????
            cell3.setCellValue(map.get("CONTRACT_NAME")== null ? "" : map.get("CONTRACT_NAME").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFCell cell4 = row.createCell(4);//????????????
            if(map.get("CONTRACT_SIGNING_TIME") != null){
                String contractSigningTime = sdf.format(map.get("CONTRACT_SIGNING_TIME"));
                cell4.setCellValue(contractSigningTime.toString());
            }          
//            cell4.setCellValue(map.get("CONTRACT_SIGNING_TIME")== null ? "" : map.get("CONTRACT_SIGNING_TIME").toString());
            HSSFCell cell5 = row.createCell(5);//????????????
            if(map.get("START_TIME") != null){            	
                String startTime = sdf.format(map.get("START_TIME"));
                cell5.setCellValue(startTime.toString());
            }   
//            cell5.setCellValue(map.get("START_TIME")== null ? "" : map.get("START_TIME").toString());
            HSSFCell cell6 = row.createCell(6);//????????????
            if(map.get("END_TIME") != null){            	
                String endTime = sdf.format(map.get("END_TIME"));
                cell6.setCellValue(endTime.toString());
            }   
//            cell6.setCellValue(map.get("END_TIME")== null ? "" : map.get("END_TIME").toString());
            HSSFCell cell7 = row.createCell(7);//????????????
        	String acquisitionMethod = "";
            if(map.get("ACQUISITION_METHOD") != null){
            	acquisitionMethod = map.get("ACQUISITION_METHOD").toString();
            	String sql = "select d.name from t_b_code_type t join t_b_code_detail d "
                        + "on t.id=d.code_type_id where d.code=? and t.code=? ";
            	List<Map<String, Object>> listMap = this.commonDao.findForJdbc(sql, new Object[] { acquisitionMethod , "CGFS"});
                if(listMap.size()>0){
                    Map<String, Object> mapMethod = listMap.get(0);
                    acquisitionMethod = (String) mapMethod.get("name");
                }
            }
            cell7.setCellValue(acquisitionMethod);
            HSSFCell cell8 = row.createCell(8);//?????????
            cell8.setCellValue(map.get("TOTAL_FUNDS")== null ? "" : map.get("TOTAL_FUNDS").toString());
//            HSSFCell cell9 = row.createCell(9);//????????????
//            cell9.setCellValue(map.get("CONTRACT_TYPE")== null ? "" : map.get("CONTRACT_TYPE").toString());
            HSSFCell cell10 = row.createCell(9);//??????????????????
            cell10.setCellValue(map.get("CONTRACT_TYPE_NAME")== null ? "" : map.get("CONTRACT_TYPE_NAME").toString());
            HSSFCell cell11 = row.createCell(10);//?????????
            cell11.setCellValue(map.get("BANK_B")== null ? "" : map.get("BANK_B").toString());
            HSSFCell cell12 = row.createCell(11);//??????
            cell12.setCellValue(map.get("ACCOUNT_NAME_B")== null ? "" : map.get("ACCOUNT_NAME_B").toString());
            HSSFCell cell13 = row.createCell(12);//??????
            cell13.setCellValue(map.get("ACCOUNT_ID_B")== null ? "" : map.get("ACCOUNT_ID_B").toString());
          }
        }
      
      private void createSheetJd(HSSFWorkbook wb,List<Map<String, Object>> projectMap){
          HSSFSheet sheet = wb.createSheet("??????????????????");
          HSSFRow titleRow = sheet.createRow(0);
          HSSFCellStyle titleStyle = getTitleStyle(wb);
          for (int i = 0; i < titlesJd.length; i++) {
          	HSSFCell cell = titleRow.createCell(i);
          	cell.setCellValue(titlesJd[i]);
          	cell.setCellStyle(titleStyle);
          }
          sheet.setColumnWidth(0, 6000);
          sheet.setColumnWidth(1, 6000);
          sheet.setColumnWidth(2, 6000);
          sheet.setColumnWidth(3, 6000);
          sheet.setColumnWidth(4, 5000);
          sheet.setColumnWidth(5, 5000);
          sheet.setColumnWidth(6, 8000);
          sheet.setColumnWidth(7, 8000);
          sheet.setColumnWidth(8, 8000);
          int rownum = projectMap.size();
          for(int a=1;a<=rownum;a++){
          	Map map=projectMap.get(a-1);
          	HSSFRow row = sheet.createRow(a);
          	
          	HSSFCell cell1 = row.createCell(0);//????????????
          	cell1.setCellValue(map.get("PROJECT_NO") == null ? "" : map.get("PROJECT_NO").toString());
            HSSFCell cell0 = row.createCell(1);//????????????
          	cell0.setCellValue(map.get("PROJECT_NAME").toString());
            HSSFCell cell2 = row.createCell(2);//????????????
            cell2.setCellValue(map.get("CONTRACT_CODE")== null ? "" : map.get("CONTRACT_CODE").toString());
            HSSFCell cell3 = row.createCell(3);//????????????
            cell3.setCellValue(map.get("CONTRACT_NAME")== null ? "" : map.get("CONTRACT_NAME").toString());
            HSSFCell cell4 = row.createCell(4);//????????????
            cell4.setCellValue(map.get("NODE_NAME")== null ? "" : map.get("NODE_NAME").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HSSFCell cell5 = row.createCell(5);//????????????
            if(map.get("COMPLETE_DATE") != null){            	
                String completeDate = sdf.format(map.get("COMPLETE_DATE"));
                cell5.setCellValue(completeDate.toString());
            }  
//            cell5.setCellValue(map.get("COMPLETE_DATE")== null ? "" : map.get("COMPLETE_DATE").toString());
            HSSFCell cell6 = row.createCell(6);//??????
            cell6.setCellValue(map.get("PAY_AMOUNT")== null ? "" : map.get("PAY_AMOUNT").toString());
            HSSFCell cell7 = row.createCell(7);//????????????
            cell7.setCellValue(map.get("FINISH_TIME")== null ? "???" : "???");
            HSSFCell cell8 = row.createCell(8);//????????????(????????????)
            if(map.get("FINISH_TIME") != null){            	
                String finishTime = sdf.format(map.get("FINISH_TIME"));
                cell8.setCellValue(finishTime.toString());
            }  
//            cell8.setCellValue(map.get("FINISH_TIME")== null ? "" : map.get("FINISH_TIME").toString());
          }
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
          font.setFontHeightInPoints((short) 12);
          font.setBoldweight((short) 26);
          font.setFontName("??????");
          titleStyle.setFont(font);
          titleStyle.setWrapText(false);
          titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
          titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
          return titleStyle;
      }

	@Override
	public List selectProject() {
		String sql = "select a.id,a.subject_code,a.project_no,a.project_name,a.project_manager,a.duty_depart,"
				+ " b.contract_Code,b.contract_Name,a.begin_date,a.end_date,a.all_fee,a.fee_type "
				+ " from t_Pm_Income_Contract_Appr b,t_pm_project a "
				+ " where a.id=b.t_p_id";
				//+ " where a.id=b.t_p_id  and b.finish_flag='2' and a.project_status='04'";
    	List projectList = this.findForJdbc(sql);
		return projectList;
	}
	
	@Override
	public List selectOutCome() {
		String sql = "select b.id,a.project_no,b.CONTRACT_SIGNING_TIME,b.contract_Code,b.contract_Name,b.start_Time,b.end_Time,"
				+ " b.acquisition_Method,b.total_Funds,"
				+ " c.unit_name_b,c.bank_b,c.account_Name_B,c.account_Id_B,b.total_funds "
				+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,t_pm_contract_basic c,t_b_code_type d,t_b_code_detail e "
				+ " where a.id=b.t_p_id and b.id=c.in_out_contractid "
				//+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid "
				+ " and b.contract_type=e.code and d.id=e.code_type_id and d.code='HTLB'";
    	List outComeList = this.findForJdbc(sql);
		return outComeList;
	}
	
	@Override
	public List selectOutComeNode() {
		String sql = "select c.id,c.complete_Date,c.pay_amount,d.finish_time"
				+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,T_PM_CONTRACT_NODE c "
				+ " left join T_PM_CONTRACT_NODE_CHECK d on c.id=d.contract_node_id "
				+ " where a.id=b.t_p_id and b.id=c.in_out_contractid";
				//+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid";
    	List outComeNodeList = this.findForJdbc(sql);
		return outComeNodeList;
	}

	@Override
	public void updateFinishFlag(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doBack(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPass(String id) {
		// TODO Auto-generated method stub
		TPmProjectEntity entity = this.get(TPmProjectEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
	}

	@Override
	public String getAppName(String id) {
		// TODO Auto-generated method stub
		String appName = "";
		TPmProjectEntity t = commonDao.get(TPmProjectEntity.class, id);
        if(t!=null){
            appName = t.getProjectName();
        }
        return appName;
	}

	@Override
	public void doCancel(String id) {
		// TODO Auto-generated method stub
		String sql = " update t_pm_project set audit_status = 0 where id='"+id+"' ";
		commonDao.executeSql(sql);
	}

	@Override
	public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAuditCount(Map<String, String> param) {
		// TODO Auto-generated method stub
		TSUser user = ResourceUtil.getSessionUserName();
        String temp = "select a.*,b.id as jl_id,c.id as sp_id from t_pm_project a,T_PM_APPR_SEND_LOGS b,T_PM_APPR_RECEIVE_LOGS c,T_PM_FORM_CATEGORY d "
        		+ " where a.id=b.appr_id and b.id=c.SEND_ID and c.SUGGESTION_TYPE=d.id "
        		+ " and c.receive_userid = '"+user.getId()+"' and c.operate_status=0  and c.valid_flag=1 ";
        temp += "order by b.OPERATE_DATE DESC, c.OPERATE_TIME DESC";
        List<Map<String, Object>> list = commonDao.findForJdbc(temp);
        return list.size();
	}

	@Override
	public ValidForm validformCode(String tableName, String codeField, String codeValue) {
		ValidForm v = new ValidForm();

        if (StringUtil.isNotEmpty(codeValue)) {
            String sql = "select id from " + tableName + " where " + codeField + " = ?";
         
            List<Map<String, Object>> results = this.findForJdbc(sql, codeValue);
            if (results == null || results.size() ==0) {
                v.setStatus("n");
                v.setInfo("??????????????????????????????????????????");
            }
        }
        return v;
	}
	
}