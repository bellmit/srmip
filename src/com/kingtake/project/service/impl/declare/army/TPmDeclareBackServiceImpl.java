package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TPmDeclareBackEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TPmDeclareBackServiceI;

@Service("tPmDeclareBackService")
@Transactional
public class TPmDeclareBackServiceImpl extends CommonServiceImpl implements TPmDeclareBackServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmDeclareBackEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmDeclareBackEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmDeclareBackEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmDeclareBackEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmDeclareBackEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmDeclareBackEntity t){
	 	return true;
 	}
 	
 	/**
     * 删除业务数据同时删除附件
     */
 	@Override
    public void deleteAddAttach(TPmDeclareBackEntity t){
 	   this.delAttachementByBid(t.getAttachmentCode());
       this.commonDao.delete(t);
 	};

 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmDeclareBackEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_type_id}",String.valueOf(t.getProjectType()));
 		sql  = sql.replace("#{manage_depart}",String.valueOf(t.getManageDepart()));
 		sql  = sql.replace("#{developer_dept_id}",String.valueOf(t.getDeveloperDeptId()));
 		sql  = sql.replace("#{great_special_flag}",String.valueOf(t.getGreatSpecialFlag()));
 		sql  = sql.replace("#{developer_dept_name}",String.valueOf(t.getDeveloperDeptName()));
 		sql  = sql.replace("#{project_begin_date}",String.valueOf(t.getProjectBeginDate()));
 		sql  = sql.replace("#{project_end_date}",String.valueOf(t.getProjectEndDate()));
 		sql  = sql.replace("#{project_source}",String.valueOf(t.getProjectSource()));
 		sql  = sql.replace("#{research_necessity}",String.valueOf(t.getResearchNecessity()));
 		sql  = sql.replace("#{situation_analysis}",String.valueOf(t.getSituationAnalysis()));
 		sql  = sql.replace("#{main_research_content}",String.valueOf(t.getMainResearchContent()));
 		sql  = sql.replace("#{research_schedule}",String.valueOf(t.getResearchSchedule()));
 		sql  = sql.replace("#{result_form}",String.valueOf(t.getResultForm()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public TPmDeclareBackEntity init(String projectId) {
		// 申报书信息
		TPmDeclareBackEntity research = new TPmDeclareBackEntity();
		
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, projectId);
		research.setTpId(projectId);
		research.setProjectType(project.getProjectType());
		research.setProjectName(project.getProjectName());
		research.setManageDepart(project.getManageDepart());
		research.setGreatSpecialFlag(project.getGreatSpecialFlag());
		research.setProjectBeginDate(project.getBeginDate());
		research.setProjectEndDate(project.getEndDate());
		research.setProjectSource(project.getProjectSource());
        research.setBpmStatus(SrmipConstants.INITIALIZED);//流程状态初始化为未提交
        
        //		commonDao.save(research);
        //		
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
}