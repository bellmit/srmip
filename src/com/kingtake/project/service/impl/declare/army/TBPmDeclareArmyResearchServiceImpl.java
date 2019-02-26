package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TBPmDeclareArmyResearchServiceI;

@Service("tBPmDeclareArmyResearchService")
@Transactional
public class TBPmDeclareArmyResearchServiceImpl extends CommonServiceImpl implements TBPmDeclareArmyResearchServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBPmDeclareArmyResearchEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBPmDeclareArmyResearchEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBPmDeclareArmyResearchEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBPmDeclareArmyResearchEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBPmDeclareArmyResearchEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBPmDeclareArmyResearchEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBPmDeclareArmyResearchEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_type}",String.valueOf(t.getProjectType().getId()));
 		sql  = sql.replace("#{project_manager_id}",String.valueOf(t.getProjectManagerId()));
 		sql  = sql.replace("#{project_manager_name}",String.valueOf(t.getProjectManagerName()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{post_code}",String.valueOf(t.getPostCode()));
 		sql  = sql.replace("#{apply_time}",String.valueOf(t.getApplyTime()));
 		sql  = sql.replace("#{project_summary}",String.valueOf(t.getProjectSummary()));
 		sql  = sql.replace("#{research_aim}",String.valueOf(t.getResearchAim()));
 		sql  = sql.replace("#{research_content}",String.valueOf(t.getResearchContent()));
 		sql  = sql.replace("#{research_analyse}",String.valueOf(t.getResearchAnalyse()));
 		sql  = sql.replace("#{project_cycle}",String.valueOf(t.getProjectCycle()));
 		sql  = sql.replace("#{project_schedule}",String.valueOf(t.getProjectSchedule()));
 		sql  = sql.replace("#{project_result}",String.valueOf(t.getProjectResult()));
 		sql  = sql.replace("#{project_check}",String.valueOf(t.getProjectCheck()));
 		sql  = sql.replace("#{project_extend_benefit}",String.valueOf(t.getProjectExtendBenefit()));
 		sql  = sql.replace("#{project_base_condition}",String.valueOf(t.getProjectBaseCondition()));
 		sql  = sql.replace("#{bpm_status}",String.valueOf(t.getBpmStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public TBPmDeclareArmyResearchEntity firstSave(String projectId) {
		TBPmDeclareArmyResearchEntity research = new TBPmDeclareArmyResearchEntity();
		// 项目信息
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, projectId);
		research.setTpId(projectId);
        research.setProjectNo(project.getProjectNo());
		research.setProjectType(project.getProjectType());
		research.setProjectName(project.getProjectName());
        research.setBpmStatus(SrmipConstants.INITIALIZED);//流程状态初始化为未提交
        //申报单位
        if (project.getDevDepart() != null) {
            research.setReportDepartId(project.getDevDepart().getId());
            research.setReportDepartName(project.getDevDepart().getDepartname());
        }
        //业务分管机关
        if (project.getManageDepartCode() != null) {
            research.setBranchDepartId(project.getManageDepartCode());
            research.setBranchDepartName(project.getManageDepart());
        }
        // 负责人
        research.setProjectManagerId(project.getProjectManagerId());
        research.setProjectManagerName(project.getProjectManager());
        research.setContactPhone(project.getManagerPhone());
		
        //		commonDao.save(research);
        //		// 将项目人员信息添加到t_pm_declare_member
        //		List<TPmProjectMemberEntity> members = commonDao.findByProperty(
        //				TPmProjectMemberEntity.class, "project.id", projectId);
        //		for(TPmProjectMemberEntity member : members){
        //			TPmDeclareMemberEntity m = new TPmDeclareMemberEntity();
        //			m.setProjDeclareId(research.getId());
        //			m.setUserid(member.getUserId());
        //			m.setName(member.getName());
        //			m.setSex(member.getSex());
        //			m.setBirthday(member.getBirthday());
        //			m.setDegree(member.getDegree());
        //			m.setPosition(member.getPosition());
        //			m.setTaskDivide(member.getTaskDivide());
        //			m.setSuperUnit(member.getSuperUnitId());
        //			m.setProjectManager(member.getProjectManager());
        //			commonDao.save(m);
        //		}
		
		return research;
	}

    @Override
    public void deleteAddAttach(TBPmDeclareArmyResearchEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}