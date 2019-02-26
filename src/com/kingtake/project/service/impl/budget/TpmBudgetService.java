package com.kingtake.project.service.impl.budget;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.service.budget.TpmBudgetServiceI;

@Service("tpmBudgetService")
@Transactional
public class TpmBudgetService  extends CommonServiceImpl implements TpmBudgetServiceI {

	//获取模板左侧
	public List<Map<String, Object>> getLeft(Map mp){
		StringBuffer sql = new StringBuffer(128);
		sql.append(" select t.* from T_PM_BUDGET_CATEGORY t order by t.category_code asc");		
		List<Map<String, Object>> list = this.findForJdbc(sql.toString());
		return list;
	}
	//获取模板树
	public List<Map<String, Object>> getTemplate(Map mp){
		Object code = mp.get("CATEGORY_CODE");
		StringBuffer sql = new StringBuffer(128);
		sql.append(" select * from T_PM_BUDGET_CATEGORY_ATTR");
		sql.append(" where 1=1");
		sql.append(" and category_code =?");
		sql.append(" or category_code ='0'");//预算编制需要合计，  模板不需要合计，待前端传参数完善。
		sql.append(" start with id = '0-0'");
		sql.append(" connect by prior id = pid");
		
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),code);
		return list;
	}
	
	
	public List<Map<String,Object>> getPmTemplate(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "projectId");
		String FUNDS_TYPE = MapUtils.getString(param, "yslx");
		StringBuilder sql = new StringBuilder();
		sql.append("select T.*,nvl(E.MONEY,0) as MONEY ");
		sql.append(" from T_PM_PROJECT A left join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=B.FUNDS_TYPE ");
		sql.append(" left join T_PM_BUDGET_CATEGORY_ATTR T on T.category_code =B.BUDGET_CATEGORY ");
		sql.append(" left join t_pm_project_funds_appr D on A.ID=D.T_P_ID ");
		sql.append(" left join t_pm_contract_funds E on D.ID=E.T_APPR_ID and T.ID=E.CONTENT_ID ");
		sql.append(" where A.ID=? ");
		
		int ysType = MapUtils.getIntValue(param, "ysType", -1);//预算类型  1：总预算，2：年度预算
		if(ysType > 0 ){
			sql.append(" and D.FUNDS_CATEGORY=" + ysType);
		}
		int layer = MapUtils.getInteger(param, "layer",-1); //层级
		if( layer > 0 ){
			sql.append(" and T.NODE=" + layer);
		}
		String ysId = MapUtils.getString(param, "ysId"); // 预算ID
		if(StringUtil.check(ysId)){
			sql.append(" and D.ID='" + ysId + "' ");
		}
		
		sql.append(" order by T.CATEGORY_CODE_DTL ");
		
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),projectId);
		return list;
	}
	//预算首页-立项项目查询
	@Override
	public Map getlxProjectList(Map mp) throws UnsupportedEncodingException {
		String usrRoleCodes = String.valueOf(mp.get("usrRoleCodes"));
		TSUser user = ResourceUtil.getSessionUserName();
		 String userName = user.getUserName();
		
		StringBuffer sql = new StringBuffer(128);
		sql.append(" select t.ID,t.PROJECT_NO,t.LX_STATUS,t.PROJECT_NAME,t.PROJECT_MANAGER,t.GLXM, ");
		sql.append(" t.PROJECT_TYPE,t.ACCOUNTING_CODE,t.PLAN_CONTRACT_REF_NO,t.PLAN_CONTRACT_NAME, ");
		sql.append(" t.SOURCE_UNIT,t.PROJECT_SOURCE,t.ALL_FEE,t.JJFJSFS,t.SUBJECT_CODE,T.FEE_TYPE,t.dnjf, ");
		sql.append(" t.ZMYE,t.BZ,t.DXBL,t.ZRDWBL,t.CYDWBL,t.XMZBL,t.XMML,t6.FUNDS_NAME, ");
		sql.append(" to_char(t.BEGIN_DATE,'yyyy-MM-dd') as BEGIN_DATE_EN, ");
		sql.append(" to_char(t.END_DATE,'yyyy-MM-dd') as END_DATE_EN,t1.XMLB as XMLBEN, ");
		sql.append(" t2.DEPARTNAME as DEVELOPER_DEPART_EN,t3.CATEGORY_NAME as YSLX_EN, ");
		sql.append(" wm_concat(to_char(t5.ID)) as MEMBERS_ID,  ");
		sql.append(" wm_concat(to_char(t5.NAME)) as MEMBERS_NAME  ");
		sql.append(" from t_pm_project t ");
		sql.append(" left join t_b_xmlb t1 on t.xmlb = t1.id ");
		sql.append(" left join t_s_depart t2 on t.developer_depart = t2.id ");
		sql.append(" left join T_PM_BUDGET_CATEGORY t3 on t.yslx = t3.CATEGORY_CODE ");
		sql.append(" left join t_b_project_type t4 on t.project_type = t4.id ");
		sql.append(" left join t_pm_project_member t5 on t.id = t5.t_p_id ");
		sql.append(" left join t_b_funds_property t6 on t.fee_type = t6.id ");
		sql.append(" where 1=1 and t.LX_STATUS = '1' and  (T.SCBZ = 0 OR T.SCBZ IS null) and t.MERGE_FLAG != '2' ");
		
		
		if(StringUtil.isNotEmpty(mp.get("key")) && StringUtil.isNotEmpty(mp.get("value"))){
			Object key = mp.get("key");
			String value= new String(mp.get("value").toString().getBytes( "ISO-8859-1" ), "utf-8");

			if("projectNo".equals(key)){
				sql.append(" and t.PROJECT_NO like '%"+ value +"%'");
			}else if("projectName".equals(key)){
				sql.append(" and t.PROJECT_NAME like '%"+ value +"%'");
			}else if("devDepart".equals(key)){
				sql.append(" and t2.DEPARTNAME like '%"+ value +"%'");
			}else if("projectType".equals(key)){
				sql.append(" and t4.PROJECT_TYPE_NAME like '%"+ value +"%'");
			}else if("xmlb".equals(key)){
				sql.append(" and t1.XMLB like '%"+ value +"%'");
			}
		}
		String  parent_project = MapUtils.getString(mp, "T_P_ID");//母项目ID
		if(StringUtils.isNotEmpty(parent_project)){
			sql.append(" and t.GLXM IN (SELECT ID FROM t_pm_project WHERE PARENT_PROJECT = '"+parent_project+"') ");
		}
		/* add by gt
         * 普通个人只能看到自己的
         * 部门领导 可以看本部门以及子部门的
         * 超级管理员
         * */
		if(!"admin".equals(userName)) {
	        List<Map<String,Object>> list = this.findForJdbc(
	        		"select USER_ID from t_s_user_org where org_id in(SELECT ID FROM t_s_depart START WITH ID=? CONNECT BY PRIOR ID=PARENTDEPARTID )"
	        		, user.getCurrentDepart().getId());
	        
	        String managerIds[] = new String[list.size()];
	        for(int i=0; i<list.size(); i++){
	        	managerIds[i] = (String)list.get(i).get("USER_ID");
	        }
	        
	        //如果个人拥有的角色中包含code为DEPARTMENT_LEADER
	        if(usrRoleCodes.contains("DPT_LEADER")) {
	        	String managerIdsStr = "'" + managerIds.toString().replace(",", "','") + "'";
	        	sql.append(" and t.PROJECT_MANAGER_ID in ("+ managerIdsStr +"') ");
	        }else{
	        	sql.append(" and t.PROJECT_MANAGER_ID ='"+ user.getId() +"'");
	        }
        }
		
		sql.append(" GROUP BY ");
		sql.append(" t.ID,t.PROJECT_NO,t.LX_STATUS,t.PROJECT_NAME,t.PROJECT_MANAGER,t.GLXM, ");
		sql.append(" t.PROJECT_TYPE,t.ACCOUNTING_CODE,t.PLAN_CONTRACT_REF_NO,t.PLAN_CONTRACT_NAME,T.FEE_TYPE,t.dnjf, ");
		sql.append(" t.SOURCE_UNIT,t.PROJECT_SOURCE,t.ALL_FEE,t.JJFJSFS,t.SUBJECT_CODE,t.DXBL,t.ZMYE,t.BZ, ");
		sql.append(" t.BEGIN_DATE,t.END_DATE,t1.XMLB,t2.DEPARTNAME,t3.CATEGORY_NAME,t.XMML,t.ZRDWBL,t.CYDWBL,t.XMZBL,t6.FUNDS_NAME ");
		sql.append(" ORDER BY T.BEGIN_DATE DESC ");
		
		List<Map<String, Object>> countList = this.findForJdbc(sql.toString());
		int count = countList.size();
		
		int page = 1;
		int limit = 10;
		if(StringUtil.isNotEmpty(mp.get("page")) && StringUtil.isNotEmpty(mp.get("limit"))){
			page = Integer.parseInt(mp.get("page").toString());
			limit = Integer.parseInt(mp.get("limit").toString());
		}
		List<Map<String, Object>> List = this.findForJdbc(sql.toString(), page, limit);
		
		Map<String, Object> json = new HashMap<String, Object>();
	    json.put("code", 0);
	    json.put("msg", "成功");
		json.put("data", List);
		json.put("count", count);
		return json;
		
	}
	@Override
	public List<Map<String, Object>> getPmCategory(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String projectId = MapUtils.getString(param, "projectId");
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT  C.ID, C.CATEGORY_CODE, C.CATEGORY_NAME, C.BUDGET_CATEGORY FROM T_PM_BUDGET_CATEGORY c  LEFT JOIN T_PM_PROJECT P ON c.CATEGORY_CODE = P.YSLX WHERE P.ID = ? ");
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(), projectId);
		return list;
	}

}
