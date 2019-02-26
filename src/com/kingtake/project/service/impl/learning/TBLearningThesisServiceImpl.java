package com.kingtake.project.service.impl.learning;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSTable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.learning.TBLearningThesisEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.learning.TBLearningThesisServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBLearningThesisService")
@Transactional
public class TBLearningThesisServiceImpl extends CommonServiceImpl implements TBLearningThesisServiceI,
        ProjectListServiceI {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private TaskJeecgService taskJeecgService;

    @Autowired
    private ActivitiDao activitiDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectListServiceI tPmDeclareService;

 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBLearningThesisEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBLearningThesisEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBLearningThesisEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBLearningThesisEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBLearningThesisEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBLearningThesisEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBLearningThesisEntity t){
 		sql  = sql.replace("#{volume_num}",String.valueOf(t.getVolumeNum()));
 		sql  = sql.replace("#{phase_num}",String.valueOf(t.getPhaseNum()));
 		sql  = sql.replace("#{page_num}",String.valueOf(t.getPageNum()));
 		sql  = sql.replace("#{sustentation_fund}",String.valueOf(t.getSustentationFund()));
 		sql  = sql.replace("#{summary}",String.valueOf(t.getSummary()));
 		sql  = sql.replace("#{index_name}",String.valueOf(t.getIndexName()));
 		sql  = sql.replace("#{collection_num}",String.valueOf(t.getCollectionNum()));
        sql = sql.replace("#{audit_status}", String.valueOf(t.getBpmStatus()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProject().getId()));
 		sql  = sql.replace("#{title_cn}",String.valueOf(t.getTitleCn()));
 		sql  = sql.replace("#{title_en}",String.valueOf(t.getTitleEn()));
 		sql  = sql.replace("#{author_first}",String.valueOf(t.getAuthorFirst()));
 		sql  = sql.replace("#{author_second}",String.valueOf(t.getAuthorSecond()));
 		sql  = sql.replace("#{author_third}",String.valueOf(t.getAuthorThird()));
 		sql  = sql.replace("#{author_other}",String.valueOf(t.getAuthorOther()));
 		sql  = sql.replace("#{author_first_depart}",String.valueOf(t.getAuthorFirstDepart()));
 		sql  = sql.replace("#{concrete_depart}",String.valueOf(t.getConcreteDepart()));
 		sql  = sql.replace("#{keyword}",String.valueOf(t.getKeyword()));
 		sql  = sql.replace("#{secret_code}",String.valueOf(t.getSecretCode()));
 		sql  = sql.replace("#{magazine_name}",String.valueOf(t.getMagazineName()));
        sql = sql.replace("#{publish_time}", String.valueOf(t.getPublishTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public AjaxJson startProcess(HttpServletRequest request, Variable var) {
        AjaxJson j = new AjaxJson();
        try {
            String message = null;
            String id = oConvertUtils.getString(request.getParameter("id"));
            String businessName = oConvertUtils.getString(request.getParameter("businessName"));
            String businessCode = oConvertUtils.getString(request.getParameter("businessCode"));
            String formUrl = oConvertUtils.getString(request.getParameter("formUrl"));
            String tableName = oConvertUtils.getString(request.getParameter("tableName"));
            String nextUser = oConvertUtils.getString(request.getParameter("nextUser"));
            if (StringUtils.isEmpty(id)) {
                j.setMsg("发起流程失败，传入业务id为空，请检查");
                j.setSuccess(false);
                return j;
            }
            if (StringUtils.isEmpty(businessCode)) {
                j.setMsg("发起流程失败，传入业务编码为空，请检查");
                j.setSuccess(false);
                return j;
            }
            if (StringUtils.isEmpty(formUrl)) {
                j.setMsg("发起流程失败，传入业务表单url地址为空，请检查");
                j.setSuccess(false);
                return j;
            }
            if (StringUtils.isEmpty(tableName)) {
                j.setMsg("发起流程失败，传入业务表名为空，请检查");
                j.setSuccess(false);
                return j;
            }
            TSTable tsTable = this.findUniqueByProperty(TSTable.class, "tableName", tableName);
            TSBusConfig tsBusbase = this.findUniqueByProperty(TSBusConfig.class, "TSTable.id", tsTable.getId());
            if (tsBusbase != null) {
                Class entityClass = MyClassLoader.getClassByScn(tsBusbase.getTSTable().getEntityName());
                Object objbus = this.getEntity(entityClass, id);
                ReflectHelper reflectHelper = new ReflectHelper(objbus);
                String bpmStatus = (String) reflectHelper.getMethodValue("bpmStatus");
                //String createBy = (String) reflectHelper.getMethodValue("createBy");
                String createBy = "";
                TSUser user = ResourceUtil.getSessionUserName();
                if(user!=null){
                    createBy = user.getUserName();//将发起流程人设置为当前登录人
                }
                if ("1".equals(bpmStatus)) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put(WorkFlowGlobals.BPM_DATA_ID, id);//表主键
                    variables.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, formUrl + "&id=" + id);//表单url
                    variables.put(WorkFlowGlobals.BPM_FORM_KEY, tableName);//表名
                    variables.put(WorkFlowGlobals.BPM_BUSINESS_NAME, businessName);//业务名称
                    variables.put(WorkFlowGlobals.BPM_BUSINESS_CODE, businessCode);//业务名称
                    variables.put(WorkFlowGlobals.VALID_FLAG, '1');//是否合法，1表示正常，0表示作废
                    variables.put("FIRSTASSIGNEE", nextUser);//第一个节点审核人，作为参数传递，可以直接在流程设计中配置
                    activitiService.startOnlineWorkflow(createBy, id, variables, tsBusbase);
                    //                    if (oConvertUtils.isNotEmpty(nextUser)) {
                    //                        //指定单个执行人
                    //                        Task currentTask = taskService.createTaskQuery().processInstanceId(instance.getId())
                    //                                .singleResult();
                    //                        if (currentTask != null) {
                    //                            taskService.setAssignee(currentTask.getId(), nextUser);
                    //                        }
                    //                    }
                    String update_status_sql = "update " + tableName + " set bpm_status = "
                            + WorkFlowGlobals.BPM_BUS_STATUS_2 + " where id=" + "'" + id + "'";
                    this.executeSql(update_status_sql);
                    Map<String, Object> mp = new HashMap<String, Object>();
                    mp.put("id", id);
                    mp.put("userid", createBy);
                    mp.put("businessCode", businessCode);
                    mp.put("businessName", businessName);
                    mp.put("prjstateid", 2);
                    mp.put("busconfigid", tsBusbase.getId());
                    activitiDao.insert(mp);
                    message = "提交成功,已进入办理流程";
                } else {
                    message = "已提交,正在办理中";
                }
            } else {
                message = "流程未关联实体,请在流程配置中配置业务";
            }
            j.setMsg(message);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        param.put("key", "learning_thesis");
        return tPmDeclareService.getAuditCount(param);
    }

    @Override
    public void deleteAddAttach(TBLearningThesisEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void updateEntitieAttach(TBLearningThesisEntity t) {
        this.updateAttachProjectId(t.getId(), t.getProject().getId());
        this.commonDao.updateEntitie(t);
    }


}