package com.kingtake.project.service.impl.declare;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.IActivitiCommonDao;
import org.jeecgframework.workflow.model.activiti.Process;
import org.jeecgframework.workflow.pojo.activiti.ActHiTaskinst;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;
import com.kingtake.project.entity.declare.TPmDeclareEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;
import com.kingtake.project.entity.declare.army.TPmDeclareBackEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.entity.reportreq.TBPmReportReqEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmDeclareService")
@Transactional
public class TPmDeclareServiceImpl extends CommonServiceImpl implements TPmDeclareServiceI, ProjectListServiceI {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private IActivitiCommonDao activitiCommonDao;
    @Autowired
    private ActivitiService activitiService;
    
    private static String[] titles = { "项目名称", "用途及总要求", "起始日期", "截止日期", "年度工作要求", "主管业务部门", "责任单位", "承研单位", "负责人", "项目组成员", "总概算", "年度概算", "项目类别", "备注" };

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmDeclareEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmDeclareEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmDeclareEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmDeclareEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmDeclareEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmDeclareEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmDeclareEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
        sql = sql.replace("#{begin_date}", String.valueOf(t.getBeginDate()));
        sql = sql.replace("#{end_date}", String.valueOf(t.getEndDate()));
        sql = sql.replace("#{project_src}", String.valueOf(t.getProjectSrc()));
        sql = sql.replace("#{research_content}", String.valueOf(t.getResearchContent()));
        sql = sql.replace("#{schedule_and_achieve}", String.valueOf(t.getScheduleAndAchieve()));
        sql = sql.replace("#{bpm_status}", String.valueOf(t.getBpmStatus()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    //------------------------------工作流相关--------------------------------------//
    /**
     * 查询待办任务-基础代码
     * 
     * @param isPri
     *            是否只查询用户私有的
     * @param id
     *            标识：username或者rolecode
     * @param tsBusConfigs
     * @return
     */
    private List findBaseTodoTasks(boolean isPri, String id, HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        List results = new ArrayList();
        List<Task> tasks = new ArrayList<Task>();
        List<Task> todoList;
        List<Task> unsignedTasks;
        //        //分页参数
        //        Integer page = Integer.parseInt(request.getParameter("page"));
        //        Integer rows = Integer.parseInt(request.getParameter("rows"));
        //        Integer start = (page - 1) * rows;
        //        Integer end = page * rows - 1;
        try {
            //建立临时集合
            List<Task> tempList = new ArrayList<Task>();
            if (isPri) {
                TaskService taskService = processEngine.getTaskService();
                List<Task> todoListtemp = taskService.createTaskQuery().taskCandidateUser(id).orderByTaskCreateTime()
                        .desc().active().list();
                tempList.addAll(todoListtemp);

                // 根据当前人的ID查询
                /*
                 * 注入查询条件
                 */
                TaskQuery tq = taskService.createTaskQuery().taskAssignee(id).orderByTaskCreateTime().desc()
                        .orderByTaskPriority().desc();
                tq = installQueryParam(tq, request);
                todoList = tq.list();
                tempList.addAll(todoList);

            } else {
                // 根据当前人所在的组查询
                /*
                 * 注入查询条件
                 */
                TaskQuery tq = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(id.split(",")))
                        .orderByTaskCreateTime().desc().orderByTaskPriority().desc();
                tq = installQueryParam(tq, request);
                unsignedTasks = tq.list();
                tempList.addAll(unsignedTasks);
            }
            tasks.addAll(tempList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            // String businessKey = processInstance.getBusinessKey();
            String businessKey = getBusinessKeyByTask(task);
            Class entityClass = MyClassLoader.getClassByScn("org.jeecgframework.workflow.pojo.base.TSBaseBusQuery");// 业务基类
            Object entityObj = getEntity(entityClass, businessKey);
            if (entityObj != null) {
                ReflectHelper reflectHelper = new ReflectHelper(entityObj);
                String bc = (String) reflectHelper.getMethodValue("businessCode");
                TSPrjstatus prjStatus = (TSPrjstatus) reflectHelper.getMethodValue("TSPrjstatus");
                if (!businessCode.equals(bc) || "5".equals(prjStatus.getStatus())) {//如果业务编码不匹配,或者状态为驳回，跳过.
                    continue;
                }
                Process process = (Process) reflectHelper.getMethodValue("Process");
                process.setTask(task);
                process.setProcessInstance(processInstance);
                process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
                String userid = (String) reflectHelper.getMethodValue("userid");
                if (userid != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName", userid);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("userRealName", baseUser.getRealName());
                    }
                }
                String assigneeUserId = task.getAssignee();
                if (assigneeUserId != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName",
                            assigneeUserId);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("assigneeName", baseUser.getRealName());
                    }
                }
                results.add(entityObj);
            }
        }
        return results;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List findPriTodoTasks(String userId, HttpServletRequest request) {
        return findBaseTodoTasks(true, userId, request);
    }

    @Override
    public List findGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request) {
        StringBuilder roleIds = new StringBuilder();
        //用户所在的组可能有多个，需要合并
        for (TSRoleUser role : roles) {
            roleIds.append(role.getTSRole().getRoleCode()).append(",");
        }
        roleIds.deleteCharAt(roleIds.length() - 1);
        List resulttemp = findBaseTodoTasks(false, roleIds.toString(), request);
        return resulttemp;
    }

    @Override
    public List findHistoryTasks(String userName, HttpServletRequest request) {
        //历史任务直接查询activiti的act_hi_taskinst表
        //分页参数
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        String businessCode = request.getParameter("businessCode");
        String sql = "select o.id_ id,o.proc_def_id_ procDefId,d.name_ prodef_name,p.id_  proInsl_procInstId,o.name_ name,p.start_user_id_  proInsl_startUserId,o.assignee_ assignee,o.start_time_ startTime,o.end_time_ endTime,o.duration_ duration,b.business_name,b.id business_key "
                + "from act_hi_taskinst o join act_hi_procinst p on o.proc_inst_id_ = p.id_ "
                + "join act_re_procdef d on d.id_ = o.proc_def_id_ join t_s_basebus b on b.id = p.business_key_ "
                + "where o.duration_ > 0 and o.assignee_ = '" + userName + "'";
        if (StringUtils.isNotEmpty(businessCode)) {
            sql = sql + " and b.business_code='" + businessCode + "'";
        }
        sql = sql + " order by o.START_TIME_ desc";
        List<Map<String, Object>> result = this.findForJdbc(sql, page, rows);
        ActHiTaskinst ins = new ActHiTaskinst();
        for (Map<String, Object> tmp : result) {
            if (tmp.containsKey("DURATION")) {
                String durationStr = ins.dealTimeFromNum(Long.valueOf(((BigDecimal) tmp.get("DURATION")).intValue()));
                tmp.put("durationStr", durationStr);
            }
        }
        return result;
    }

    public Long countPriTodaoTask(String userName, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        Long size = 0L;
        // 根据当前人的ID查询
        TaskQuery tq = taskService.createTaskQuery().taskAssignee(userName).orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc();
        installQueryParam(tq, request);
        size = tq.count();
        return size;
    }

    @Override
    public Long countGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        Long size = 0L;
        StringBuilder roleIds = new StringBuilder();
        //用户所在的组可能有多个，需要合并
        for (TSRoleUser role : roles) {
            roleIds.append(role.getTSRole().getRoleCode()).append(",");
        }
        roleIds.deleteCharAt(roleIds.length() - 1);
        // 根据当前组的ID查询
        TaskQuery tq = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(roleIds.toString().split(",")))
                .orderByTaskPriority().desc().orderByTaskCreateTime().desc();
        installQueryParam(tq, request);
        size = tq.count();
        return size;
    }

    @Override
    public Long countHistoryTasks(String userName, HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        String sql = "select count(1) hsize "
                + "from act_hi_taskinst o join act_hi_procinst p on o.proc_inst_id_ = p.id_ "
                + "join act_re_procdef d on d.id_ = o.proc_def_id_ join t_s_basebus b on b.id = p.business_key_ "
                + "where o.duration_ > 0 and o.assignee_ = '" + userName + "'";
        if (StringUtils.isNotEmpty(businessCode)) {
            sql = sql + " and b.business_code='" + businessCode + "'";
        }
        sql = sql + " order by o.START_TIME_ desc";
        Map r = this.findOneForJdbc(sql);
        Long size = Long.parseLong(r.get("hsize").toString());
        return size;
    }

    /**
     * 拼装过滤条件
     * 
     * @param tq
     * @param request
     * @return
     */
    private TaskQuery installQueryParam(TaskQuery tq, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        if (StringUtil.isNotEmpty(procDefId)) {
            tq = tq.processDefinitionId(procDefId);
        }
        if (StringUtil.isNotEmpty(procName)) {
            tq = tq.processDefinitionName(procName);
        }
        return tq;
    }

    private String installCountH(String sql, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        StringBuilder s = new StringBuilder(sql);
        if (StringUtil.isNotEmpty(procDefId)) {
            s.append(" and o.proc_def_id_ = ? ");
        }
        if (StringUtil.isNotEmpty(procName)) {
            s.append(" and ap.name_ = ? ");
        }
        return s.toString();
    }

    private Object[] installCountHv(String userName, HttpServletRequest request) {
        //查询条件
        List reList = new ArrayList(0);
        reList.add(userName);
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        if (StringUtil.isNotEmpty(procDefId)) {
            reList.add(procDefId);
        }
        if (StringUtil.isNotEmpty(procName)) {
            reList.add(procName);
        }
        return reList.toArray();
    }

    /**
     * 获取业务ID
     * 
     * @param taskId
     * @return
     */
    public String getBusinessKeyByTask(Task task) {
        String businessKey = "";
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
        HistoricProcessInstance hiproins = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
        if (hiproins != null) {
            if (hiproins.getSuperProcessInstanceId() != null && hiproins.getBusinessKey() == null) {
                hiproins = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(hiproins.getSuperProcessInstanceId()).singleResult();
                businessKey = hiproins.getBusinessKey();
            } else {
                businessKey = hiproins.getBusinessKey();
            }
        }
        return businessKey;
    }

    /**
     * 查询流程定义对象
     * 
     * @param processDefinitionId
     *            流程定义ID
     * @return
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = param.get("key");
        return activitiService.getAuditCount(businessCode);
    }
    /**
     * 保存项目基本信息和人员信息到申报书和关联的成员信息表中
     * @param projectId
     * @return
     */
    @Override
    public TPmDeclareEntity firstSave(String projectId) {
      TPmDeclareEntity declare = new TPmDeclareEntity();
      // 项目信息
      TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, projectId);
      declare.setTpId(projectId);
      declare.setProject(project);
      declare.setProjectNo(project.getProjectNo());
      declare.setProjectTypeId(project.getProjectType().getId());
      declare.setProjectName(project.getProjectName());
      declare.setSecretCode(project.getSecretDegree());
      declare.setApplyDate(new Date());
      declare.setBeginDate(project.getBeginDate());
      declare.setEndDate(project.getEndDate());
      declare.setProjectSrc(project.getProjectSource());
      
        declare.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);//流程状态初始化为未提交
      //申报单位
      if (project.getDevDepart() != null) {
          declare.setBuildUnitId(project.getDevDepart().getId());
          declare.setBuildUnitName(project.getDevDepart().getDepartname());
      }
      //业务分管机关
      if (project.getManageDepartCode() != null) {
          declare.setBranchUnitId(project.getManageDepartCode());
          declare.setBranchUnitName(project.getManageDepart());
      }
      // 负责人
      declare.setProjectManagerId(project.getProjectManagerId());
      declare.setProjectManagerName(project.getProjectManager());
      declare.setContactPhone(project.getManagerPhone());
      
//      commonDao.save(declare);
//      // 将项目人员信息添加到t_pm_declare_member
//      List<TPmProjectMemberEntity> members = commonDao.findByProperty(TPmProjectMemberEntity.class, "project.id", projectId);
//      for(TPmProjectMemberEntity member : members){
//        TPmDeclareMemberEntity m = new TPmDeclareMemberEntity();
//        m.setProjDeclareId(declare.getId());
//        m.setUserid(member.getUserId());
//        m.setName(member.getName());
//        m.setSex(member.getSex());
//        m.setBirthday(member.getBirthday());
//        m.setDegree(member.getDegree());
//        m.setPosition(member.getPosition());
//        m.setTaskDivide(member.getTaskDivide());
//        m.setSuperUnit(member.getSuperUnitId());
//        m.setProjectManager(member.getProjectManager());
//        commonDao.save(m);
        //      }
      return declare;
    }

    @Override
    public void doResubmit(TPmPlanDetailEntity planDetail) {
        planDetail = this.commonDao.findUniqueByProperty(TPmPlanDetailEntity.class, "declareId",
                planDetail.getDeclareId());
        if("declare".equals(planDetail.getDeclareType())){
            TPmDeclareEntity declare = this.commonDao.get(TPmDeclareEntity.class, planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        } else if ("army".equals(planDetail.getDeclareType())) {
            TBPmDeclareArmyResearchEntity declare = this.commonDao.get(TBPmDeclareArmyResearchEntity.class,
                    planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        } else if ("repair".equals(planDetail.getDeclareType())) {
            TBPmDeclareRepairEntity declare = this.commonDao.get(TBPmDeclareRepairEntity.class,
                    planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        } else if ("back".equals(planDetail.getDeclareType())) {
            TPmDeclareBackEntity declare = this.commonDao.get(TPmDeclareBackEntity.class, planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        } else if ("tech".equals(planDetail.getDeclareType())) {
            TBPmDeclareTechnologyEntity declare = this.commonDao.get(TBPmDeclareTechnologyEntity.class,
                    planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        } else if ("reportReq".equals(planDetail.getDeclareType())) {
            TBPmReportReqEntity declare = this.commonDao.get(TBPmReportReqEntity.class, planDetail.getDeclareId());
            declare.setPlanStatus("3");
            this.commonDao.updateEntitie(declare);
        }
        planDetail.setAuditStatus("3");
        this.commonDao.updateEntitie(planDetail);

    }

    @Override
    public String getDeclareStatus(String bpmStatus, String planStatus) {
        if ("1".equals(bpmStatus) || "".equals(bpmStatus)) {
            return "未提交";
        } else if ("2".equals(bpmStatus)) {
            return "院系审核中";
        } else if ("3".equals(bpmStatus) && StringUtils.isEmpty(planStatus)) {
            return "已通过院系审核";
        } else if ("3".equals(bpmStatus) && "1".equals(planStatus)) {
            return "已通过机关审核";
        } else if ("3".equals(bpmStatus) && "2".equals(planStatus)) {
            return "机关审核已驳回";
        } else if ("3".equals(bpmStatus) && "3".equals(planStatus)) {
            return "机关审核重新提交";
        }else if("4".equals(bpmStatus)){
            return "已作废";
        }else if("5".equals(bpmStatus)){
            return "院系审核被驳回";
        }else if("6".equals(bpmStatus)){
            return "不需要审批";
        }
        return null;
    }

    @Override
    public void deleteAddAttach(TPmDeclareEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
    
    @Override
    public Workbook exportProject(String id) {
    	String sql = "  select d.t_p_id from t_b_pm_declare d where d.id= '" + id + "'"
    	           + " union select r.t_p_id from t_b_pm_declare_army_research r where r.id= '" + id + "'"
    	           + " union select b.t_p_id from t_b_pm_declare_back b where b.id= '" + id + "'"
    	           + " union select re.t_p_id from t_b_pm_declare_repair re where re.id= '" + id + "'"
    	           + " union select t.t_p_id from t_b_pm_declare_technology t where t.id= '" + id + "'";
    	Map projectMap = this.findOneForJdbc(sql);
    	String projectId = projectMap.get("t_p_id").toString();
    	TPmProjectEntity projectEntity = this.commonDao.get(TPmProjectEntity.class, projectId);
    
        HSSFWorkbook wb = new HSSFWorkbook();
        createSheet(wb, projectEntity);
        return wb;
    }
    
    /**
     * 创建sheet页
     * 
     * @param wb
     * @param year
     * @param list
     */
      private void createSheet(HSSFWorkbook wb,TPmProjectEntity projectEntity){
        HSSFSheet sheet = wb.createSheet();
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleStyle = getTitleStyle(wb);
          for (int i = 0; i < titles.length; i++) {
              HSSFCell cell = titleRow.createCell(i);
              cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
          }
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(11, 3000);       
        sheet.setColumnWidth(12, 5000);
        int rownum = 1;

        HSSFRow row = sheet.createRow(rownum);
        HSSFCell cell0 = row.createCell(0);//项目名称
        cell0.setCellValue(projectEntity.getProjectName());
        HSSFCell cell1 = row.createCell(1);//用途及总要求
        cell1.setCellValue("");
        HSSFCell cell2 = row.createCell(2);//起始日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cell2.setCellValue(sdf.format(projectEntity.getBeginDate()));
        HSSFCell cell3 = row.createCell(3);//截止日期
        cell3.setCellValue(sdf.format(projectEntity.getEndDate()));
        HSSFCell cell4 = row.createCell(4);//年度工作要求
        cell4.setCellValue("");
        HSSFCell cell5 = row.createCell(5);//主管业务部门
        cell5.setCellValue(projectEntity.getManageDepart());
        HSSFCell cell6 = row.createCell(6);//责任单位  
        if(projectEntity.getDutyDepart() != null){
        	cell6.setCellValue(projectEntity.getDutyDepart().getDepartname());
        }
        HSSFCell cell7 = row.createCell(7);//承研单位
        if(projectEntity.getDevDepart() != null){
        	cell7.setCellValue(projectEntity.getDevDepart().getDepartname());
        }        
        HSSFCell cell8 = row.createCell(8);//负责人+电话
        cell8.setCellValue(projectEntity.getProjectManager() + "(" + projectEntity.getManagerPhone() + ")");
        HSSFCell cell9 = row.createCell(9);//项目组成员
        String sql = "select name from t_pm_project_member where t_p_id='" + projectEntity.getId() + "'";
        List<Map<String, Object>> memberListMap = this.commonDao.findForJdbc(sql);
        String memberName = "";
        if(memberListMap.size()>0){
        	for(Map<String, Object> map : memberListMap){
        		memberName += map.get("name") + ",";
        	}
        }
        memberName = memberName.substring(0, memberName.length() - 1);
        cell9.setCellValue(memberName);
        HSSFCell cell10 = row.createCell(10);//总概算
        cell10.setCellValue("");
        HSSFCell cell11 = row.createCell(11);//年度概算
        cell11.setCellValue("");
        HSSFCell cell12 = row.createCell(12);//项目类别
        cell12.setCellValue(projectEntity.getProjectType().getProjectTypeName());
        HSSFCell cell13 = row.createCell(13);//备注
        cell13.setCellValue("");
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
}