package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TBPmDeclareTechnologyServiceI;

@Service("tBPmDeclareTechnologyService")
@Transactional
public class TBPmDeclareTechnologyServiceImpl extends CommonServiceImpl implements TBPmDeclareTechnologyServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBPmDeclareTechnologyEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBPmDeclareTechnologyEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBPmDeclareTechnologyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBPmDeclareTechnologyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBPmDeclareTechnologyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBPmDeclareTechnologyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBPmDeclareTechnologyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_type_id}",String.valueOf(t.getProjectType()));
 		sql  = sql.replace("#{host_dept_id}",String.valueOf(t.getHostDeptId()));
 		sql  = sql.replace("#{host_dept_name}",String.valueOf(t.getHostDeptName()));
 		sql  = sql.replace("#{argument_dept_id}",String.valueOf(t.getArgumentDeptId()));
 		sql  = sql.replace("#{argument_dept_name}",String.valueOf(t.getArgumentDeptName()));
 		sql  = sql.replace("#{apply_time}",String.valueOf(t.getApplyTime()));
 		sql  = sql.replace("#{content_range}",String.valueOf(t.getContentRange()));
 		sql  = sql.replace("#{demand_analysis}",String.valueOf(t.getDemandAnalysis()));
 		sql  = sql.replace("#{domestic_analysis}",String.valueOf(t.getDomesticAnalysis()));
 		sql  = sql.replace("#{international_analysis}",String.valueOf(t.getInternationalAnalysis()));
 		sql  = sql.replace("#{feasibility_analysis}",String.valueOf(t.getFeasibilityAnalysis()));
 		sql  = sql.replace("#{related_unit_opinion}",String.valueOf(t.getRelatedUnitOpinion()));
 		sql  = sql.replace("#{project_schedule}",String.valueOf(t.getProjectSchedule()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public TBPmDeclareTechnologyEntity init(String projectId) {
		// 申报书基本信息
		TBPmDeclareTechnologyEntity research = new TBPmDeclareTechnologyEntity();
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, projectId);
		research.setTpId(projectId);
		research.setProjectType(project.getProjectType());
		research.setProjectName(project.getProjectName());
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

    @Override
    public void deleteAddAttach(TBPmDeclareTechnologyEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}