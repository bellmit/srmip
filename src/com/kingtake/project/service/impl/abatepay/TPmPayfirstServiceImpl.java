package com.kingtake.project.service.impl.abatepay;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.abatepay.TPmPayfirstEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.abatepay.TPmPayfirstServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmPayfirstService")
@Transactional
public class TPmPayfirstServiceImpl extends CommonServiceImpl implements TPmPayfirstServiceI,
ProjectListServiceI{
    @Autowired
    private ActivitiService activitiService;
    
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmPayfirstEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmPayfirstEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmPayfirstEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmPayfirstEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmPayfirstEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmPayfirstEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmPayfirstEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{PROJECT_ID}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{pay_funds}",String.valueOf(t.getPayFunds()));
 		sql  = sql.replace("#{reason}",String.valueOf(t.getReason()));
 		sql  = sql.replace("#{pay_source}",String.valueOf(t.getPaySource()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void savePayFirst(TPmPayfirstEntity tPmPayfirst) {
        try {
            if (StringUtils.isNotEmpty(tPmPayfirst.getProjectId())) {
                TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, tPmPayfirst.getProjectId());
                tPmPayfirst.setProjectName(project.getProjectName());
            }
            if (StringUtil.isNotEmpty(tPmPayfirst.getId())) {
                TPmPayfirstEntity t = this.commonDao.get(TPmPayfirstEntity.class, tPmPayfirst.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmPayfirst, t);
                commonDao.saveOrUpdate(t);
            } else {
                commonDao.save(tPmPayfirst);
            }
        } catch (Exception e) {
            throw new BusinessException("保存垫支出错", e);
        }
    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = param.get("key");
        return activitiService.getAuditCount(businessCode);
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        
    }

    @Override
    public void deleteAddAttach(TPmPayfirstEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

	@Override
	public Map addGdInfo(Map map) {
 		Map result = new HashMap();
        String  PROJECT_ID = MapUtils.getString(map,"projectId");
        double GD_MONDY = MapUtils.getDouble(map,"GD_MONDY");

		//查询已审核且未做预算的到款记录
		String sql = " select t.id,t.apply_amount,t.payfirst_funds "
				+" from T_PM_INCOME_APPLY t where t.audit_status='2' "
				+" and (t.ys_status = '0' or t.ys_status is null) "
				+" and t.t_p_id='"+ PROJECT_ID +"'";
		List<Map<String, Object>> list = this.findForJdbc(sql);
		if(list != null && list.size() > 0 ){
			double payAmount = 0;//待归垫总金额
			for(int i=0;i<list.size();i++){
				Map applyMap = list.get(i);
				double applyAmount = applyMap.get("APPLY_AMOUNT")==null? 0:Double.parseDouble(applyMap.get("APPLY_AMOUNT").toString());
				double payFirstAmount = applyMap.get("PAYFIRST_FUNDS")==null? 0:Double.parseDouble(applyMap.get("PAYFIRST_FUNDS").toString());
				double chae = applyAmount - payFirstAmount;//到款金额-归垫金额 = 待归垫金额
				payAmount+=chae;
			}
			if(GD_MONDY > payAmount){
				result.put("code", 1);
				result.put("msg", "归垫金额超出总归垫金额，请重新输入！");
				return result;
			}

			for(int i=0;i<list.size();i++){
				Map applyMap = list.get(i);
				Object keyId = applyMap.get("ID");
				double applyAmount = applyMap.get("APPLY_AMOUNT")==null? 0:Double.parseDouble(applyMap.get("APPLY_AMOUNT").toString());
				double payFirstAmount = applyMap.get("PAYFIRST_FUNDS")==null? 0:Double.parseDouble(applyMap.get("PAYFIRST_FUNDS").toString());
				double chae = applyAmount - payFirstAmount;//到款金额-归垫金额 = 待归垫金额
				if(chae > 0){
					//归垫
					if(GD_MONDY > chae){
						String sql2 = " update T_PM_INCOME_APPLY set PAYFIRST_FUNDS='"+applyAmount+"' where id='"+keyId+"' ";
						this.executeSql(sql2);
                        GD_MONDY = GD_MONDY - chae;

                        map.put("GDDZ_ID",keyId);
                        map.put("GD_MONDY",chae);
                        addGdinfoLog(map);
					}else{
						double zgdje = payFirstAmount + GD_MONDY;
						String sql2 = " update T_PM_INCOME_APPLY set PAYFIRST_FUNDS='"+zgdje+"' where id='"+keyId+"' ";
						this.executeSql(sql2);

                        //map.put("GD_MONDY",GD_MONDY);
                        map.put("GDDZ_ID",keyId);
                        addGdinfoLog(map);
						break;//钱全部归垫完，终止循环
					}
				}
			}
		}else{
			result.put("code", 1);
			result.put("msg", "项目无到账金额，无法归垫！");
			return result;
		}



		result.put("code", 0);
		result.put("msg", "成功归垫！");
		return result;
	}

	private void addGdinfoLog(Map map){
        String id = UUID.randomUUID().toString().replaceAll("-","");
        TSUser user = ResourceUtil.getSessionUserName();
        String  PROJECT_ID = MapUtils.getString(map,"projectId");
        String PROJECT_NO = MapUtils.getString(map,"projectNo");
        String REASON = MapUtils.getString(map,"REASON");
        String CREATE_BY = user.getUserName();
        String CREATE_NAME = user.getRealName();
        double GD_MONDY = MapUtils.getDouble(map,"GD_MONDY");
        String PAY_SUBJECTCODE = MapUtils.getString(map,"paySubjectcode");
        String YP_YEAR = MapUtils.getString(map,"payYear");
        String  GDDZ_ID = MapUtils.getString(map,"GDDZ_ID");

        //新增归垫记录
        StringBuilder sb = new StringBuilder();
        sb.append("insert into T_B_PM_GD_INFO (ID,PROJECT_ID,PROJECT_NO,REASON,CREATE_BY,CREATE_NAME," +
                "CREATE_DATE,JZ_ID,GD_MONDY,PAY_SUBJECTCODE,CW_STATUS,GD_STATUS,YP_YEAR,GDDZ_ID)");
        sb.append(" values (?,?,?,?,?,?,sysdate,null,?,?,null,null,?,?)");
        this.executeSql(sb.toString(),id,PROJECT_ID,PROJECT_NO,REASON,CREATE_BY,CREATE_NAME,GD_MONDY,PAY_SUBJECTCODE,YP_YEAR,GDDZ_ID);
    }

	@Override
	public List doSearchGDList(Map map) {
		String  PROJECT_ID = MapUtils.getString(map,"projectId");
		int page = MapUtils.getIntValue(map,"page");
		int rows = MapUtils.getIntValue(map,"rows");
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from T_B_PM_GD_INFO where PROJECT_ID = '"+PROJECT_ID+"' order by CREATE_DATE desc");
		return this.findForJdbc(sb.toString(),page,rows);
	}
}