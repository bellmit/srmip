package com.kingtake.expert.service.impl.reviewmain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ExpertReviewConstant;
import com.kingtake.expert.dao.ExpertReviewDao;
import com.kingtake.expert.entity.info.TErExpertEntity;
import com.kingtake.expert.entity.reviewmain.TErMainTypeRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErProjectExpertRelaEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;
import com.kingtake.expert.service.reviewmain.TErReviewMainServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tErReviewMainService")
@Transactional
public class TErReviewMainServiceImpl extends CommonServiceImpl implements TErReviewMainServiceI, ProjectListServiceI {
    @Autowired
    private ExpertReviewDao expertReviewDao;
    
    private static String[] titles = { "项目名称", "总经费", "负责人", "承研单位", "专家名称", "评审结果", "专家打分", "专家意见" };

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TErReviewMainEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TErReviewMainEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TErReviewMainEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TErReviewMainEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TErReviewMainEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TErReviewMainEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TErReviewMainEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{according_num}", String.valueOf(t.getAccordingNum()));
        sql = sql.replace("#{doc_num}", String.valueOf(t.getDocNum()));
        sql = sql.replace("#{review_title}", String.valueOf(t.getReviewTitle()));
        sql = sql.replace("#{review_content}", String.valueOf(t.getReviewContent()));
        sql = sql.replace("#{plan_review_date_begin}", String.valueOf(t.getPlanReviewDateBegin()));
        sql = sql.replace("#{plan_review_date_end}", String.valueOf(t.getPlanReviewDateEnd()));
        sql = sql.replace("#{review_mode}", String.valueOf(t.getReviewMode()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 查询项目列表
     */
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        String projectNo = request.getParameter("projectNo");
        String projectName = request.getParameter("projectName");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String finishStatus = request.getParameter("finishStatus");
        String approvalstatus = request.getParameter("approvalstatus");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int curPage = Integer.valueOf(page);
        int pageSize = Integer.valueOf(rows);
        int start = (curPage - 1) * pageSize;
        int end = curPage * pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(projectNo)) {
            paramMap.put("projectNo", projectNo);
        }
        if (StringUtils.isNotEmpty(projectName)) {
            paramMap.put("projectName", projectName);
        }
        if (StringUtils.isNotEmpty(beginDate_begin)) {
            paramMap.put("beginDate_begin", beginDate_begin);
        }
        if (StringUtils.isNotEmpty(beginDate_end)) {
            paramMap.put("beginDate_end", beginDate_end);
        }
        if (StringUtils.isNotEmpty(finishStatus)) {
            paramMap.put("finishStatus", finishStatus);
        }
        if (StringUtils.isNotEmpty(approvalstatus)) {
            paramMap.put("approvalstatus", approvalstatus);
        }
        List<Map<String, Object>> list = this.expertReviewDao.getProjectList(start, end, paramMap);
        int count = this.expertReviewDao.getProjectCount(paramMap);
        dataGrid.setTotal(count);
        dataGrid.setResults(list);
    }

    /**
     * 获取查询提交评审状态
     * 
     * @return
     */
    private String getReviewStatus(String projectId) {
        String reviewStatus = "未提交";
        List<TErReviewProjectEntity> list = this.commonDao.findByProperty(TErReviewProjectEntity.class, "projectId",
                projectId);
        int count = list.size();
        if (count > 0) {
            reviewStatus = "已提交";
        }
        return reviewStatus;
    }

    @Override
    public TErReviewMainEntity saveReviewMainInfo(HttpServletRequest request) {
        TErReviewMainEntity mainEntity = null;
        try {
            String id = request.getParameter("id");
            String reviewProcess = request.getParameter("reviewProcess");
            String accordingNum = request.getParameter("accordingNum");
            String reviewTitle = request.getParameter("reviewTitle");
            String reviewContent = request.getParameter("reviewContent");
            String planReviewDateBegin = request.getParameter("planReviewDateBegin");
            String planReviewDateEnd = request.getParameter("planReviewDateEnd");
            String expertReviewDateBegin = request.getParameter("expertReviewDateBegin");
            String expertReviewDateEnd = request.getParameter("expertReviewDateEnd");
            String planReviewAddress = request.getParameter("planReviewAddress");
            String reviewMode = request.getParameter("reviewMode");
            String attachmentCode = request.getParameter("attachmentCode");
            String reviewTypes = request.getParameter("reviewTypes");
            String[] reviewTypeArr = reviewTypes.split(",");
            String projectExpertStr = request.getParameter("projectExpertStr");
            JSONArray array = JSON.parseArray(projectExpertStr);
            if (StringUtils.isNotEmpty(id)) {
                mainEntity = this.commonDao.get(TErReviewMainEntity.class, id);
                //清理数据
                String suggestionSql = "delete from t_er_suggestion sug where sug.review_project_id in (select pro.project_id from t_er_review_project pro where pro.t_e_id=?)";
                this.commonDao.executeSql(suggestionSql, id);
                String expSql = "delete from t_er_review_expert_rela t where t.T_E_ID=?";
                this.commonDao.executeSql(expSql, id);
                String typeSql = "delete from t_er_content t where t.T_E_ID=?";
                this.commonDao.executeSql(typeSql, id);
                String projectSql = "delete from t_er_review_project t where t.T_E_ID=?";
                this.commonDao.executeSql(projectSql, id);
                String projexpSql = "delete from t_er_project_expert t where t.review_id=?";
                this.commonDao.executeSql(projexpSql, id);
                mainEntity.setReviewProcess(reviewProcess);
                mainEntity.setAccordingNum(accordingNum);
                mainEntity.setReviewTitle(reviewTitle);
                mainEntity.setReviewContent(reviewContent);
                
                if(StringUtils.isNotEmpty(planReviewDateBegin)){
                	mainEntity.setPlanReviewDateBegin(DateUtils.parseDate(planReviewDateBegin, "yyyy-MM-dd HH:mm"));
                }
                if(StringUtils.isNotEmpty(planReviewDateEnd)){
                	mainEntity.setPlanReviewDateEnd(DateUtils.parseDate(planReviewDateEnd, "yyyy-MM-dd HH:mm"));
                }
                if(StringUtils.isNotEmpty(expertReviewDateBegin)){
                	mainEntity.setExpertReviewDateBegin(DateUtils.parseDate(expertReviewDateBegin, "yyyy-MM-dd"));
                }
                if(StringUtils.isNotEmpty(expertReviewDateEnd)){
                	mainEntity.setExpertReviewDateEnd(DateUtils.parseDate(expertReviewDateEnd, "yyyy-MM-dd"));
                }
                mainEntity.setPlanReviewAddress(planReviewAddress);
                mainEntity.setReviewMode(reviewMode);
                this.commonDao.updateEntitie(mainEntity);
            } else {
                mainEntity = new TErReviewMainEntity();
                mainEntity.setReviewProcess(reviewProcess);
                mainEntity.setAccordingNum(accordingNum);
                mainEntity.setReviewTitle(reviewTitle);
                mainEntity.setReviewContent(reviewContent);
                mainEntity.setAttachmentCode(attachmentCode);
                if(StringUtils.isNotEmpty(planReviewDateBegin)){
                	mainEntity.setPlanReviewDateBegin(DateUtils.parseDate(planReviewDateBegin, "yyyy-MM-dd HH:mm"));
                }
                if(StringUtils.isNotEmpty(planReviewDateEnd)){
                	mainEntity.setPlanReviewDateEnd(DateUtils.parseDate(planReviewDateEnd, "yyyy-MM-dd HH:mm"));
                }
                if(StringUtils.isNotEmpty(expertReviewDateBegin)){
                	mainEntity.setExpertReviewDateBegin(DateUtils.parseDate(expertReviewDateBegin, "yyyy-MM-dd"));
                }
                if(StringUtils.isNotEmpty(expertReviewDateEnd)){
                	mainEntity.setExpertReviewDateEnd(DateUtils.parseDate(expertReviewDateEnd, "yyyy-MM-dd"));
                }
                mainEntity.setPlanReviewAddress(planReviewAddress);
                mainEntity.setReviewMode(reviewMode);
                mainEntity.setReviewStatus("0");
                this.commonDao.save(mainEntity);
            }
            List<TErMainTypeRelaEntity> mainTypeRelaEntityList = new ArrayList<TErMainTypeRelaEntity>();
            for (int i = 0; i < reviewTypeArr.length; i++) {
                TErMainTypeRelaEntity entity = new TErMainTypeRelaEntity();
                entity.setTeMainId(mainEntity.getId());
                entity.setTeTypeId(reviewTypeArr[i]);
                mainTypeRelaEntityList.add(entity);
            }
            this.commonDao.batchSave(mainTypeRelaEntityList);
            List<TErReviewProjectEntity> reviewProjectEntityList = new ArrayList<TErReviewProjectEntity>();
            for (int i = 0; i < array.size(); i++) {
                TErReviewProjectEntity entity = new TErReviewProjectEntity();
                JSONObject json = (JSONObject) array.get(i);
                String projectId = (String) json.get("projectId");
                TPmProjectEntity projectEntity = this.commonDao.get(TPmProjectEntity.class, projectId);
                if (projectEntity != null) {
                    entity.setProjectId(projectEntity.getId());
                    entity.setProjectName(projectEntity.getProjectName());
                    entity.setReviewType(ExpertReviewConstant.REVIEW_TYPE_PROJECT);
                    entity.setReviewMain(mainEntity);
                    this.commonDao.save(entity);
                    //保存每一个项目对应的评审专家
                    List<TErProjectExpertRelaEntity> projectExpertRelaList = new ArrayList<TErProjectExpertRelaEntity>();
                    String expertIds = (String) json.get("expertId");
                    String[] projectExpertIds = expertIds.split(",");
                    for (int j = 0; j < projectExpertIds.length; j++) {                  	
                    	String expertId = projectExpertIds[j];
                    	TErProjectExpertRelaEntity projectExpertRelaEntity = new TErProjectExpertRelaEntity();
                    	projectExpertRelaEntity.setExpertId(expertId);
                    	projectExpertRelaEntity.setProjectId(entity.getId());
                    	projectExpertRelaEntity.setReviewId(mainEntity.getId());
                    	projectExpertRelaList.add(projectExpertRelaEntity);
                    }
                    this.commonDao.batchSave(projectExpertRelaList);
                }
            }

            List<TErSuggestionEntity> suggestionList = new ArrayList<TErSuggestionEntity>();
            for (int i = 0; i < reviewProjectEntityList.size(); i++) {
            	TErReviewProjectEntity reviewProject = reviewProjectEntityList.get(i);
            	List<TErProjectExpertRelaEntity> projectExpertRelaList = this.commonDao.findByProperty(TErProjectExpertRelaEntity.class, "projectId", reviewProject.getProjectId());
            	for (TErProjectExpertRelaEntity tErProjectExpertRelaEntity : projectExpertRelaList) {
            		TErSuggestionEntity suggestion = new TErSuggestionEntity();
            		TErExpertEntity expertTmp  = this.commonDao.get(TErExpertEntity.class,tErProjectExpertRelaEntity.getExpertId());
            		if(expertTmp!=null){
            			suggestion.setExpertId(expertTmp.getId());
            			suggestion.setExpertName(expertTmp.getName());
            		}
            		suggestion.setReviewProject(reviewProject);
            		suggestionList.add(suggestion);
				}
            }
            if (suggestionList.size() > 0) {
                this.commonDao.batchSave(suggestionList);
            }
        } catch (Exception e) {
            throw new BusinessException("保存专家评审信息出错！", e);
        }
        return mainEntity;
    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return this.expertReviewDao.getAuditProjectCount();
    }

    @Override
    public void deleteReviewMain(TErReviewMainEntity tErReviewMain) {
        this.delAttachementByBid(tErReviewMain.getAttachmentCode());
        String suggestionSql = "delete from t_er_suggestion sug where sug.review_project_id in (select pro.project_id from t_er_review_project pro where pro.t_e_id=?)";
        this.commonDao.executeSql(suggestionSql, tErReviewMain.getId());
        String expSql = "delete from t_er_review_expert_rela t where t.T_E_ID=?";
        this.commonDao.executeSql(expSql, tErReviewMain.getId());
        String typeSql = "delete from t_er_content t where t.T_E_ID=?";
        this.commonDao.executeSql(typeSql, tErReviewMain.getId());
        String projectSql = "delete from t_er_review_project t where t.T_E_ID=?";
        this.commonDao.executeSql(projectSql, tErReviewMain.getId());
        this.commonDao.delete(tErReviewMain);
    }

    @Override
    public Workbook export() {
        CriteriaQuery cq = new CriteriaQuery(TErReviewMainEntity.class);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TErReviewMainEntity> mainList = this.commonDao.getListByCriteriaQuery(cq, false);
        Map<String, List<TErReviewMainEntity>> yearMainMap = new LinkedHashMap<String, List<TErReviewMainEntity>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        for (TErReviewMainEntity tmp : mainList) {
            String year = sdf.format(tmp.getCreateDate());
            if (yearMainMap.containsKey(year)) {
                List<TErReviewMainEntity> list = yearMainMap.get(year);
                list.add(tmp);
            } else {
                List<TErReviewMainEntity> list = new ArrayList<TErReviewMainEntity>();
                list.add(tmp);
                yearMainMap.put(year, list);
            }
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        Set<Entry<String, List<TErReviewMainEntity>>> entrySet = yearMainMap.entrySet();
        for (Entry<String, List<TErReviewMainEntity>> entry : entrySet) {
            createSheet(wb, entry.getKey(), entry.getValue());
        }
        return wb;
        }

    /**
     * 创建sheet页
     * 
     * @param wb
     * @param year
     * @param list
     */
      private void createSheet(HSSFWorkbook wb,String year,List<TErReviewMainEntity> list){
        HSSFSheet sheet = wb.createSheet(year);
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleStyle = getTitleStyle(wb);
          for (int i = 0; i < titles.length; i++) {
              HSSFCell cell = titleRow.createCell(i);
              cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
          }
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 5000);
        int rownum = 0;
        for (TErReviewMainEntity entity : list) {
            CriteriaQuery proCq = new CriteriaQuery(TErReviewProjectEntity.class);
            proCq.eq("reviewMain.id", entity.getId());
            proCq.add();
            List<TErReviewProjectEntity> reviewProjectList = this.commonDao.getListByCriteriaQuery(proCq, false);
            for (TErReviewProjectEntity reviewProject : reviewProjectList) {
                CriteriaQuery suggestionCq = new CriteriaQuery(TErSuggestionEntity.class);
                suggestionCq.eq("reviewProject.id", reviewProject.getId());
                suggestionCq.add();
                List<TErSuggestionEntity> suggestionList = this.commonDao.getListByCriteriaQuery(suggestionCq, false);
                TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, reviewProject.getProjectId());
                if(project != null)
                {
                	String devDepartName = project.getDevDepart() == null ? "" : project.getDevDepart().getDepartname();
                    String allFee = amountFormat(project.getAllFee());
                    for (TErSuggestionEntity suggeston : suggestionList) {
                        rownum++;
                        HSSFRow row = sheet.createRow(rownum);
                        HSSFCell cell0 = row.createCell(0);//项目名称
                        cell0.setCellValue(reviewProject.getProjectName());
                        HSSFCell cell1 = row.createCell(1);//总经费
                        cell1.setCellValue(allFee);
                        HSSFCell cell2 = row.createCell(2);//负责人
                        cell2.setCellValue(project.getProjectManager());
                        HSSFCell cell3 = row.createCell(3);//承研单位
                        cell3.setCellValue(devDepartName);
                        HSSFCell cell4 = row.createCell(4);//专家名称
                        cell4.setCellValue(suggeston.getExpertName());
                        HSSFCell cell5 = row.createCell(5);//评审结果
                        cell5.setCellValue(suggeston.getExpertResultStr());
                        HSSFCell cell6 = row.createCell(6);//专家打分
                        String score = suggeston.getExpertScore() == null ? "" : suggeston.getExpertScore().toString();
                        cell6.setCellValue(score);
                        HSSFCell cell7 = row.createCell(7);//专家意见
                        cell7.setCellValue(suggeston.getExpertContent());
                    }
                }                
            }
        }
      }

    /**
     * 表明的Style
     * 
     * @param workbook
     * @return
     */
    public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight((short) 26);
        font.setFontName("宋体");
        titleStyle.setFont(font);
        titleStyle.setWrapText(false);
        titleStyle.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return titleStyle;
    }
    
    /**
     * 金额格式化
     * 
     * @param amount
     * @return
     */
    private String amountFormat(BigDecimal amount) {
        if (amount == null) {
            return "";
        }
        BigDecimal big = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(big.toString());
        return big.toString();
    }

}