package com.kingtake.project.service.ys.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jodd.util.StringUtil;

import org.apache.commons.collections.MapUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.controller.incomeapply.TPmIncomeApplyController;
import com.kingtake.project.service.ys.YsDaoService;
import com.kingtake.project.service.ys.YsEnum;

@Service("ysDaoService")
@Transactional
public class YsDaoServiceImpl extends CommonServiceImpl implements YsDaoService{
	
	/**
	 * 获取项目信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String,Object> getProjectById(String id){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_pm_project where ID=? ");
		return this.findOneForJdbc(sql.toString(), id);
	}
	/**
	 * 获取项目经费
	 * @param param
	 * @return
	 */
	
	@Override
	public Map<String,Object> getXmFund(Map<String,Object> param){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		String projectId = MapUtils.getString(param, "T_P_ID");
		StringBuilder jfSql = new StringBuilder();
		jfSql.append("select ")
			.append("A.ZMYE as YE_FUNDS, ")		//项目余额
			.append("A.ALL_FEE ZJF_FUNDS, ")		//总经费
			.append("B.BUDGET_AMOUNT FP_FUNDS ")	//计划分配
			.append("from t_pm_project A left join t_pm_project B on A.GLXM=B.ID ")
			.append("where A.ID=? ");
		
		List<Map<String,Object>> jfList = this.findForJdbc(jfSql.toString(), projectId);
		if(jfList.size() != 1){
			dataMap.put("code", "-1");
			dataMap.put("describe", "项目数据异常");
			return dataMap;
		}
		dataMap = jfList.get(0);
		return dataMap;
	}
	/**
	 * 获取分配总经费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getFpFunds(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		Map<String,Object> data = new HashMap<>();
		
		if(StringUtil.isEmpty(projectId)){
			return data;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(B.JE) as FP_JE from t_Pm_Project_plan A join t_pm_fpje B on B.JHWJID = A.id  ")	
			.append("where B.XMID=? and A.ADUIT_STATUS=2  and A.CW_STATUS=1 ");
		//是否做预算
		int status = MapUtils.getIntValue(param, "FUNDS_STATUS",-1);
		if(status == 0){
			sql.append(" and (B.YS_STATUS=0 or B.YS_STATUS is null) ");
		}else if(status == 1){
			sql.append(" and B.YS_STATUS=1 ");
		}
		List<Map<String,Object>> list =  this.findForJdbc(sql.toString(),projectId);
		if (list.size() > 0){
			return list.get(0);
		}
		return data;
	}
	/**
	 * 获取垫支总经费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getDzFunds(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		Map<String,Object> data = new HashMap<>();
		
		if(StringUtil.isEmpty(projectId)){
			return data;
		}
		//垫支
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(B.PAY_FUNDS) as DZ_FUNDS ")	
			.append("from t_pm_project A left join t_b_pm_payfirst B on A.GLXM=B.PROJECT_ID ")
			.append("where A.ID=? and B.CW_STATUS=1 and B.BPM_STATUS=3 ");
		//是否做预算
		int status = MapUtils.getIntValue(param, "FUNDS_STATUS",0);
		if(status > 0){
			sql.append(" and B.FUNDS_STATUS=" + status);
		}else{
			sql.append(" and (B.FUNDS_STATUS=0 or B.FUNDS_STATUS is null) ");
		}
		List<Map<String,Object>> list =  this.findForJdbc(sql.toString(),projectId);
		if (list.size() > 0){
			return list.get(0);
		}
		return data;
	}
	/**
	 * 获取到账总经费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getRlFunds(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		Map<String,Object> data = new HashMap<>();
		if(StringUtil.isEmpty(projectId)){
			return data;
		}
		//认领
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(B.APPLY_AMOUNT) - sum(nvl(B.PAYFIRST_FUNDS,0)) as RL_FUNDS ")
			.append("from t_pm_project A left join t_pm_income_apply B on ")
			.append("(A.GLXM=B.T_P_ID and A.SCHOOL_COOPERATION_FLAG<>1) or (A.id=B.T_P_ID and A.SCHOOL_COOPERATION_FLAG=1) ")
			.append("where B.CW_STATUS=1 and B.AUDIT_STATUS=2 and A.ID=? ");
		//是否做预算
		int status = MapUtils.getIntValue(param, "FUNDS_STATUS",-1);
		if(status == 0){
			sql.append(" and (B.YS_STATUS<>1 or B.FUNDS_FLAG<>1) ");
		}else if(status == 1){
			sql.append(" and (B.YS_STATUS=1 or B.FUNDS_FLAG=1) ");
		}
		
		List<Map<String,Object>> list =  this.findForJdbc(sql.toString(),projectId);
		if (list.size() > 0){
			return list.get(0);
		}
		return data;
	}
	
	/**
	 * 获取预算总经费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getZysFunds(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		int FUNDS_CATEGORY = MapUtils.getIntValue(param, "FUNDS_CATEGORY",1);
		Map<String,Object> data = new HashMap<>();
		if(StringUtil.isEmpty(projectId)){
			return data;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(B.TOTAL_FUNDS) as ZYS_FUNDS ")	
			.append("from t_pm_project A left join t_pm_project_funds_appr B on A.ID=B.T_P_ID ")
			.append("where A.ID=? and B.FUNDS_CATEGORY=? ");
		
		
		List<Map<String,Object>> list =  this.findForJdbc(sql.toString(),projectId,FUNDS_CATEGORY);
		if (list.size() > 0){
			return list.get(0);
		}
		return data;
	}
	
	/**
	 * 获取预算
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getYsList(Map<String, Object> param) {
		String projectId = MapUtils.getString(param, "T_P_ID");
		int FUNDS_CATEGORY = MapUtils.getIntValue(param, "FUNDS_CATEGORY",-1);
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,to_char(CREATE_DATE,'yyyy-mm-dd') as CREATE_DATE,FUNDS_TYPE,TOTAL_FUNDS,FINISH_FLAG,CW_STATUS ")//yyyy-mm-dd hh24:mi:ss
			.append( "from t_pm_project_funds_appr where T_P_ID=? ");
		if(FUNDS_CATEGORY > 0 ){
			sql.append(" and FUNDS_CATEGORY=" + FUNDS_CATEGORY);
		}
		sql.append(" order by create_date desc");
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),projectId);
		for(int i = 0 ; i < list.size() ; i++){
			Map<String,Object> data = list.get(i);
			data.put("FUNDS_TYPE_NAME", YsEnum.getEnumByKey(MapUtils.getString(data, "FUNDS_TYPE","1")).getValue());
			String FINISH_FLAG = "HT_" + MapUtils.getString(data, "FINISH_FLAG","0");
			String CW_STATUS = "CW_" + MapUtils.getString(data, "CW_STATUS","0");
			data.put("FINISH_FLAG_NAME", YsEnum.getEnumByKey(FINISH_FLAG).getValue());
			data.put("CW_STATUS_NAME", YsEnum.getEnumByKey(CW_STATUS).getValue());
		}
		return list;
	}
	
	/**
	 * 获取预算明细
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getYsDetailList(Map<String, Object> param) {
		String FEE_TYPE = MapUtils.getString(param, "FEE_TYPE");
		String T_APPR_ID  = MapUtils.getString(param, "T_APPR_ID");
		String CATEGORY_CODE = "";
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select BUDGET_CATEGORY from T_PM_BUDGET_FUNDS_REL where FUNDS_TYPE=? ");
		List<Map<String,Object>> list = this.findForJdbc(sqlBuffer.toString(),FEE_TYPE);
		if(list.size() <= 0){
			return list;
		}
		CATEGORY_CODE = MapUtils.getString(list.get(0), "BUDGET_CATEGORY");
		sqlBuffer = new StringBuffer();
		sqlBuffer.append("select T.*,nvl(A.MONEY,0) as MONEY,A.REMARK,A.UNIT,A.PRICE,A.AMOUNT from( ")
			.append("select ID,CATEGORY_CODE,CATEGORY_CODE_DTL,DETAIL_NAME,DETAIL_SYMBOL,PID,NODE from T_PM_BUDGET_CATEGORY_ATTR ")
			.append("where category_code =? or category_code ='0' ")
			.append("start with id = '0-0' ")
			.append("connect by prior id = pid ")
			.append(") T left join t_pm_contract_funds A on t.ID=A.CONTENT_ID and A.T_APPR_ID=? where 1=1 ");
		int NODE = MapUtils.getIntValue(param, "NODE",-1);
		if(NODE > 0){
			sqlBuffer.append(" and T.NODE=" + NODE + " or T.NODE=0 or PID='19-10' ");
		}
		sqlBuffer.append("UNION select CONTENT_ID as ID,")
			.append("? as CATEGORY_CODE,")
			.append("NUM as CATEGORY_CODE_DTL,")
			.append("CONTENT as DETAIL_NAME,")
			.append("nvl(ADD_CHILD_FLAG,0) as DETAIL_SYMBOL,")
			.append("PARENT as PID,")
			.append("'' as NODE,nvl(MONEY,0),REMARK,UNIT,PRICE,AMOUNT ")
			.append("from t_pm_contract_funds where T_APPR_ID=? and NODE>=3 ");
		String sql = "select * from (" + sqlBuffer.toString() + ") M order by M.CATEGORY_CODE_DTL ";
		//sql.append("order by nvl(T.CATEGORY_CODE_DTL,A.NUM) ");
		if(T_APPR_ID == null) return list;
		
		return this.findForJdbc(sql,CATEGORY_CODE,T_APPR_ID,CATEGORY_CODE,T_APPR_ID);
	}
	
	/**
	 * 获取预算合计
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getYsTotal(Map<String, Object> param){
		String ID = MapUtils.getString(param, "T_P_ID");//項目ID
		int FUNDS_CATEGORY  = MapUtils.getInteger(param, "FUNDS_CATEGORY");//預算類型
		List<Map<String,Object>> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select M.ID,M.CATEGORY_CODE,M.CATEGORY_CODE_DTL,M.DETAIL_NAME, M.DETAIL_SYMBOL,M.DETAIL_DESC,M.PID,M.NODE,M.ID,M.UNIT,M.PRICE,")
		.append("sum( M.AMOUNT) as AMOUNT, sum( M.VARIABLE_MONEY) as VARIABLE_MONEY, sum(M.HISTORY_MONEY) as HISTORY_MONEY, sum(M.MONEY) as MONEY  ")
		.append("from( select distinct T.*,  E.UNIT as UNIT, E.PRICE as PRICE,  ")
		.append("E.AMOUNT as AMOUNT, E.VARIABLE_MONEY as VARIABLE_MONEY, E.HISTORY_MONEY as HISTORY_MONEY, E.MONEY as MONEY  ")
		.append("from T_PM_PROJECT A left join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=B.FUNDS_TYPE  ")
		.append("left join T_PM_BUDGET_CATEGORY_ATTR T on T.category_code =B.BUDGET_CATEGORY ");
		if(FUNDS_CATEGORY == 1){
			sql.append(" and T.NODE<=1 or T.category_code =0 or T.PID='19-10' ");
		}else{
			sql.append(" or T.category_code =0 ");
		}
		sql.append("left join t_pm_project_funds_appr D on A.ID=D.T_P_ID  and D.FUNDS_CATEGORY=?  ")
			.append("left join t_pm_contract_funds E on D.ID=E.T_APPR_ID and T.ID=E.CONTENT_ID   ")
			.append("where A.ID=? ) M ")
			.append("group by M.ID,M.CATEGORY_CODE,M.CATEGORY_CODE_DTL,M.DETAIL_NAME, M.DETAIL_SYMBOL,M.DETAIL_DESC,M.PID,M.NODE,M.ID,M.UNIT,M.PRICE ")
			.append("order by M.CATEGORY_CODE_DTL  ");
		list = this.findForJdbc(sql.toString(),FUNDS_CATEGORY,ID);
		if(FUNDS_CATEGORY == 2){
			list.addAll(this.getYsTotalChild(param));
		}
		return list;
	}
	/**
	 * 年度预算三级以下节点
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getYsTotalChild(Map<String, Object> param){
		String ID = MapUtils.getString(param, "T_P_ID");//項目ID
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ID,T.CATEGORY_CODE, T.CATEGORY_CODE_DTL,T.DETAIL_NAME, ")
		.append("T.DETAIL_SYMBOL,T.PID,T.UNIT,T.PRICE,T.NODE,NODE, ")
		.append("sum(T.AMOUNT) as AMOUNT, ")
		.append("sum(T.VARIABLE_MONEY) as VARIABLE_MONEY, ")
		.append("sum(T.HISTORY_MONEY) as HISTORY_MONEY, ")
		.append("sum(T.MONEY) as MONEY ")
		.append("from( select B.CONTENT_ID as ID,  ")
		.append("C.BUDGET_CATEGORY as CATEGORY_CODE,  ")
		.append("B.NUM as CATEGORY_CODE_DTL,  ")
		.append("B.CONTENT as DETAIL_NAME,  ")
		.append("nvl(B.ADD_CHILD_FLAG,0) as DETAIL_SYMBOL,  ")
		.append("B.PARENT as PID,  ")
		.append("B.UNIT as UNIT,  ")
		.append("B.PRICE as PRICE,  ")
		.append("B.NODE as NODE,  ")
		.append("B.AMOUNT as AMOUNT, ") 
		.append("B.VARIABLE_MONEY as VARIABLE_MONEY,  ")
		.append("B.HISTORY_MONEY as HISTORY_MONEY,  ")
		.append("B.MONEY as MONEY  ")
		.append("from t_pm_project_funds_appr A left join t_pm_contract_funds B on A.ID=B.T_APPR_ID   ")
		.append("left join T_PM_BUDGET_FUNDS_REL C on A.FEE_TYPE=C.FUNDS_TYPE   ")
		.append("where A.T_P_ID=? and B.NUM not in (   ")
		.append("select C.CATEGORY_CODE_DTL from T_PM_PROJECT A left join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=B.FUNDS_TYPE    ")
		.append("left join T_PM_BUDGET_CATEGORY_ATTR C on C.category_code =B.BUDGET_CATEGORY where A.ID=?) ) T ")
		.append("group by T.ID,T.CATEGORY_CODE, T.CATEGORY_CODE_DTL,T.DETAIL_NAME, T.DETAIL_SYMBOL,T.PID,T.UNIT,T.PRICE,T.NODE,NODE ")
		.append("order by T.CATEGORY_CODE_DTL ");
		return this.findForJdbc(sql.toString(),ID,ID);
	}
	/**
	 * 获取模板
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getPmTemplate(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		String FUNDS_TYPE = MapUtils.getString(param, "FUNDS_TYPE","-1");
		StringBuilder sql = new StringBuilder();
		sql.append("select T.*, ")
			.append("D.FUNDS_TYPE as FUNDS_TYPE, ")
			.append("E.ID as FUNDS_ID, ")
			.append("E.UNIT as UNIT, ")
			.append("E.PRICE as PRICE, ")
			.append("E.AMOUNT as AMOUNT, ")
			.append("E.VARIABLE_MONEY as VARIABLE_MONEY, ")
			.append("E.HISTORY_MONEY as HISTORY_MONEY, ")
			.append("E.MONEY as MONEY")
			.append(" from T_PM_PROJECT A left join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=B.FUNDS_TYPE ")
			.append(" left join T_PM_BUDGET_CATEGORY_ATTR T on T.category_code =B.BUDGET_CATEGORY or T.category_code =0 ")
			.append(" left join t_pm_project_funds_appr D on A.ID=D.T_P_ID ");
		
		int FUNDS_CATEGORY = MapUtils.getIntValue(param, "FUNDS_CATEGORY", -1);//预算类型  1：总预算，2：年度预算
		if(FUNDS_CATEGORY > 0){
			sql.append(" and D.FUNDS_CATEGORY=" + FUNDS_CATEGORY);
		}

		sql.append(" left join t_pm_contract_funds E on D.ID=E.T_APPR_ID and T.ID=E.CONTENT_ID ");
		sql.append(" where A.ID=? ");


		int layer = MapUtils.getInteger(param, "layer",-1); //层级
		String pid = MapUtils.getString(param, "PID");	//父ID
		if( layer > 0 && StringUtil.isEmpty(pid)){
			sql.append(" and T.NODE<=" + layer);
		}else if(layer > 0 && !StringUtil.isEmpty(pid)){
			pid = pid.replaceAll(",", "','");
			sql.append(" and (T.NODE<=" + layer + " or PID in ('" + pid + "'))");
		}
		String ysId = MapUtils.getString(param, "ysId"); // 预算ID
		if(FUNDS_TYPE.equals(YsEnum.TZH_BUDGET.getKey())){
			Map<String,Object> data = getYsData(param);
			if( data != null){
				ysId = MapUtils.getString(data, "ID");
			}
		}
		if(!StringUtil.isEmpty(ysId)){
			sql.append(" and D.ID='" + ysId + "' ");
		}
		
		sql.append(" order by T.CATEGORY_CODE_DTL ");
		
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),projectId);
		return list;
	}
	/**
	 * 获取预算明细</br>
	 * 补充明细节点除去模板以外的数据
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getDetailFunds(Map<String,Object> param){
		String projectId = MapUtils.getString(param, "T_P_ID");
		String ysId = MapUtils.getString(param, "ysId"); // 预算ID
		StringBuilder sql = new StringBuilder();
		sql.append("select B.CONTENT_ID as ID,")
			.append("C.BUDGET_CATEGORY as CATEGORY_CODE,")
			.append("B.NUM as CATEGORY_CODE_DTL,")
			.append("B.CONTENT as DETAIL_NAME,")
			.append("nvl(B.ADD_CHILD_FLAG,0) as DETAIL_SYMBOL,")
			.append("B.PARENT as PID,B.ID as FUNDS_ID,")
			.append("B.UNIT,B.PRICE,B.AMOUNT,B.VARIABLE_MONEY,")
			.append("B.HISTORY_MONEY,B.MONEY,B.NODE ")
			.append("from t_pm_project_funds_appr A left join t_pm_contract_funds B on A.ID=B.T_APPR_ID ")
			.append("left join T_PM_BUDGET_FUNDS_REL C on A.FEE_TYPE=C.FUNDS_TYPE ")
			.append("where A.T_P_ID=? and A.ID=? ")
			.append("and B.NUM not in ( ")
			.append("select D.CATEGORY_CODE_DTL from t_pm_project_funds_appr A ")
			.append("left join T_PM_BUDGET_FUNDS_REL C on A.FEE_TYPE=C.FUNDS_TYPE ")
			.append("left join T_PM_BUDGET_CATEGORY_ATTR D on C.BUDGET_CATEGORY=D.CATEGORY_CODE ")
			.append("where A.T_P_ID=? and A.ID=? )")
			.append("order by B.NUM ");
		return this.findForJdbc(sql.toString(),projectId,ysId,projectId,ysId);
	}
	
	/**
	 * 添加预算主表
	 * @param param
	 * @return
	 */
	@Override
	public int addFundAppr(Map<String, Object> param){
		String id = UUID.randomUUID().toString().replaceAll("-","");
		param.put("T_APPR_ID", id);
		String keys = "ID,T_P_ID,START_YEAR,END_YEAR,ACCOUNT_FUNDS,DEPARTS_ID,PROJECT_MANAGER,PROJECT_NAME,"
				+ "FEE_TYPE,ALL_FEE,MEMBERS_ID,MEMBERS_NAME,PROJECT_MANAGER_NAME,DEPARTS_NAME,CREATE_BY,"
				+ "CREATE_NAME,FUNDS_TYPE,TOTAL_FUNDS,CW_STATUS,FINISH_FLAG,FUNDS_CATEGORY,CREATE_DATE";
		String[] keyArray = keys.split(",");
		Object[] valueArray = new Object[keyArray.length - 1];
		List<Object[]> list = new ArrayList<Object[]>();
		
		valueArray[0] = id;
		list.add(valueArray);
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into t_pm_project_funds_appr (" + keys + ") values (?");
		for(int i = 1 ; i < valueArray.length ; i++){
			sql.append(",?");
			valueArray[i] = param.get(keyArray[i]);
		}
		
		sql.append(",sysdate)");
		int[] nums = this.batchUpdate(sql.toString(), list);
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return sum == list.size() ? 1 : -1 ; 
	}
	
	/**
	 * 添加预算明细
	 * @param param
	 * @return
	 */
	@Override
	public int addFunds(Map<String, Object> param){
		
		String keys = "ID,T_P_ID,NUM,CONTENT_ID,CONTENT,MONEY,PARENT,ADD_CHILD_FLAG,VARIABLE_MONEY,"
				+ "HISTORY_MONEY,UNIT,PRICE,AMOUNT,T_APPR_ID,NODE,REMARK";
		String[] keyArray = keys.split(",");
		
		List<Object[]> list = new ArrayList<Object[]>();
		List<Map<String,Object>> dataList = (List<Map<String,Object>>)param.get("list");
		//拼接sql
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into t_pm_contract_funds (" + keys + ") values (?");
		for(int i = 1 ; i < keyArray.length ; i++){
			sql.append(",?");
			
		}
		sql.append(")");
		//组装数据
		for(int i = 0 ; i < dataList.size() ; i++){
			String id = UUID.randomUUID().toString().replaceAll("-","");
			Object[] valueArray = new Object[keyArray.length];
			Map<String,Object> data = dataList.get(i);
			valueArray[0] = id;
			for(int t = 1 ; t < keyArray.length ; t++){
				Object value = param.get(keyArray[t]);
				if(value == null){
					value = data.get(keyArray[t]);
				}
				valueArray[t] = value;
			}
			list.add(valueArray);
		}
		int[] nums = this.batchUpdate(sql.toString(), list);
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return sum == list.size() ? 1 : -1 ; 
	}
	/**
	 * 更新预算状态
	 * @param param
	 * @return
	 */
	@Override
	public int updateYSstatus(Map<String, Object> param){
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		String T_APPR_ID = MapUtils.getString(param, "T_APPR_ID");
		int YS_STATUS = MapUtils.getInteger(param, "YS_STATUS",1);
		String[] sqls = new String[]{
			//认领、到款
			"update t_pm_income_apply set YS_STATUS=1,YS_APPR_ID='" + T_APPR_ID + "'  where T_P_ID in (select GLXM from t_pm_project where ID='" + T_P_ID + "') and nvl(YS_STATUS,0)=0 ",
			//垫支
			"update t_b_pm_payfirst set FUNDS_STATUS=1,YS_APPR_ID='" + T_APPR_ID + "' where PROJECT_ID in (select GLXM from t_pm_project where ID='" + T_P_ID + "') and nvl(FUNDS_STATUS,0)=0",
			//分配
			"update t_pm_fpje set YS_STATUS=1,YS_APPR_ID='" + T_APPR_ID + "' where XMID='" + T_P_ID + "' and nvl(YS_STATUS,0)=0 "
		};
		if(YS_STATUS == 0){
			sqls = new String[]{
				//认领、到款
				"update t_pm_income_apply set YS_STATUS=0,YS_APPR_ID=null  where YS_APPR_ID='" + T_APPR_ID + "'",
				//垫支
				"update t_b_pm_payfirst set FUNDS_STATUS=0,YS_APPR_ID=null where YS_APPR_ID='" + T_APPR_ID + "'",
				//分配
				"update t_pm_fpje set YS_STATUS=0,YS_APPR_ID=null where YS_APPR_ID='" + T_APPR_ID + "'"
			};
		}
		int[] nums = this.batchUpdate(sqls);
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return sum == sqls.length ? 1 : -1 ; 
	}
	/**
	 * 编辑预算主表
	 * @param param
	 * @return
	 */
	public int editFundAppr(Map<String, Object> param){
		
		return 0;
	}
	/**
	 * 编辑预算明细表
	 * @param param
	 * @return
	 */
	@Override
	public int editFunds(Map<String, Object> param){
		StringBuilder sql = new StringBuilder();
		sql.append("update t_pm_contract_funds set HISTORY_MONEY=?,MONEY=?,REMARK=?,CONTENT=? where ID=? ");
		List<Map<String,Object>> dataList = (List<Map<String,Object>>)param.get("list");
		List<Object[]> list = new ArrayList<Object[]>();
		//组装数据
		for(int i = 0 ; i < dataList.size() ; i++){
			Object[] valueArray = new Object[5];
			Map<String,Object> data = dataList.get(i);
			valueArray[0] = MapUtils.getString(data, "HISTORY_MONEY");
			valueArray[1] = MapUtils.getString(data, "MONEY");
			valueArray[2] = MapUtils.getString(data, "REMARK");
			valueArray[3] = MapUtils.getString(data, "CONTENT");
			valueArray[4] = MapUtils.getString(data, "ID");
			
			list.add(valueArray);
		}
		//批量执行
		this.batchUpdate(sql.toString(), list);
		return 0;
	}
	
	
	/**
	 * 获取预算ID
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getYsData(Map<String,Object> param){
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		String FUNDS_CATEGORY = MapUtils.getString(param, "FUNDS_CATEGORY");//预算类型 1:总预算，2：年度预算
		StringBuilder sql = new StringBuilder();
		sql.append("select * from t_pm_project_funds_appr ")
			.append("where T_P_ID=? ");
		if(!StringUtil.isEmpty(FUNDS_CATEGORY)){
			sql.append(" and FUNDS_CATEGORY=" + FUNDS_CATEGORY );
		}
		sql.append(" order by create_date desc ");
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),T_P_ID);
		if(list.size() > 0 ){
			return list.get(0);
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 删除预算主表数据
	 */
	@Override
	public int delProjectFundsAppr(Map<String, Object> param) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		String id = MapUtils.getString(param, "ID","0");
		String funds = MapUtils.getString(param, "FUNDS_CATEGORY","0");
		sql.append(" delete from t_pm_project_funds_appr where id = ? and funds_category = ? ");
		int num = this.executeSql(sql.toString(),id,funds);
		if(num > 0){
			param.put("T_APPR_ID", MapUtils.getString(param, "ID"));
			param.put("YS_STATUS", 0);
			//更新预算状态
			updateYSstatus(param);
		}
		return num;
	}
	/**
	 * 删除预算从表数据
	 */
	@Override
	public int delContractFunds(Map<String, Object> param) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		String id = MapUtils.getString(param, "ID","0");
		sql.append(" delete from t_pm_contract_funds WHERE t_appr_id = ? ");
		int num = this.executeSql(sql.toString(),id);
		return num;
	}

	private static String detailNull(Object obj){
		if(obj == null) {
			return null;
		}else{
			return "'"+obj.toString()+"'";
		}

	}
	
	@Override
	public List<Map<String, Object>> getFpTotalList(Map<String, Object> param) {
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		StringBuilder sql = new StringBuilder();
		//to_char(B.SUBMIT_TIME,'yyyy-mm-dd hh:mm:ss')
		sql.append("select distinct * from (select A.ID as ID,B.ID as FPID, ")
		.append("A.PROJECT_NO as PROJECT_NO, ")
		.append("A.PROJECT_NAME as PROJECT_NAME, ")
		.append("to_number(nvl(B.FUNDS_SOURCES,0)) as FUNDS_TYPE, ")
		.append("case when FUNDS_SOURCES=2 then '校内协作' else '认领' end  as FUNDS_TYPE_TEXT, ")
		.append("B.APPLY_USER as APPLY_USER, ")
		.append("to_char(B.SUBMIT_TIME,'yyyy-mm-dd hh:mm:ss') as SUBMIT_TIME, ")
		.append("to_number(B.APPLY_AMOUNT) as APPLY_MOUNT, ")
		.append("B.APPLY_YEAR as APPLY_YEAR, ")
		.append("B.CHECK_USER_REALNAME as AUDIT_USERNAME, ")
		.append("to_char(B.AUDIT_TIME,'yyyy-mm-dd hh:mm:ss') as AUDIT_TIME, ")
		.append("to_number(nvl(B.YS_STATUS,0)) as YS_STATUS, ")
		.append("B.YS_APPR_ID as YS_APPR_ID, ")
		.append("B.audit_status as AUDIT_STATUS, ")
		.append("B.CW_STATUS as CW_STATUS ")
		.append("from t_pm_project A left join t_pm_income_apply B on A.GLXM=B.T_P_ID or A.ID=B.T_P_ID ")
		.append("where A.ID=? ")
		.append("union ")
		.append("select A.ID as ID,B.ID as FPID, ")
		.append("A.PROJECT_NO as PROJECT_NO, ")
		.append("A.PROJECT_NAME as PROJECT_NAME, ")
		.append("3 as FUNDS_TYPE, ")
		.append("'垫支' as FUNDS_TYPE_TEXT, ")
		.append("B.CREATE_NAME as APPLY_USER, ")
		.append("to_char(B.CREATE_DATE,'yyyy-mm-dd hh:mm:ss') as SUBMIT_TIME, ")
		.append("to_number(B.PAY_FUNDS) as APPLY_MOUNT, ")
		.append("B.PAY_YEAR as APPLY_YEAR, ")
		.append("'' as AUDIT_USERNAME, ")
		.append("null as AUDIT_TIME, ")
		.append("to_number(nvl(B.FUNDS_STATUS,0)) as YS_STATUS, ")
		.append("'' as YS_APPR_ID, ")
		.append("B.BPM_STATUS as AUDIT_STATUS, ")
		.append("B.CW_STATUS as CW_STATUS ")
		.append("from t_pm_project A left join t_b_pm_payfirst B on A.GLXM=B.PROJECT_ID  ")
		.append("where A.ID=?  ")
		.append("union ")
		.append("select C.ID as ID,A.ID as FPID, ")
		.append("C.PROJECT_NO as PROJECT_NO, ")
		.append("C.PROJECT_NAME as PROJECT_NAME, ")
		.append("1 as FUNDS_TYPE, ")
		.append("'分配' as FUNDS_TYPE_TEXT, ")
		.append("A.SUBMIT_USERNAME as APPLY_USER, ")
		.append("to_char(A.SUBMIT_TIME,'yyyy-mm-dd hh:mm:ss') as SUBMIT_TIME, ")
		.append("to_number(B.JE) as APPLY_MOUNT, ")
		.append("A.PLAN_YEAR as APPLY_YEAR, ")
		.append("A.AUDIT_USERNAME as AUDIT_USERNAME, ")
		.append("to_char(A.AUDIT_TIME,'yyyy-mm-dd hh:mm:ss') as AUDIT_TIME, ")
		.append("nvl(B.YS_STATUS,0) as YS_STATUS, ")
		.append("B.YS_APPR_ID as YS_APPR_ID, ")
		.append("A.ADUIT_STATUS as ADUIT_STATUS, ")
		.append("A.CW_STATUS as CW_STATUS ")
		.append("from t_Pm_Project_plan A join t_pm_fpje B on B.JHWJID = A.id   join t_pm_project C on B.XMID = C.id  ")
		.append("where  B.XMID=? ) T where T.FPID is not null order by T.FUNDS_TYPE,T.SUBMIT_TIME");
		return this.findForJdbc(sql.toString(),T_P_ID,T_P_ID,T_P_ID);
	}
	
	@Override
	public List<Map<String, Object>> getDzTotalList(Map<String, Object> param) {
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		StringBuilder sql = new StringBuilder();
		sql.append("select A.PROJECT_NAME as PROJECT_NAME,")
			.append("A.PROJECT_NO as PROJECT_NO,")
			.append("A.CREATE_BY as CREATE_BY,")
			.append("B.PAY_FUNDS as PAY_FUNDS,")
			.append("B.FUNDS_STATUS as FUNDS_STATUS,")
			.append("B.PAY_SUBJECTCODE as PAY_SUBJECTCODE,")
			.append("B.CREATE_BY as CREATE_BY,")
			.append("B.FUNDS_STATUS as FUNDS_STATUS,")
			.append("B.CW_STATUS as CW_STATUS,")
			.append("B.BPM_STATUS as BPM_STATUS ")
			.append("from t_b_pm_payfirst B left join t_pm_project A on A.GLXM=B.PROJECT_ID ")
			.append("where A.ID=?  ");
		return this.findForJdbc(sql.toString(),T_P_ID);
	}
	
	@Override
	public List<Map<String, Object>> getSpendingList(Map param) {
		Object tpId = param.get("T_P_ID");
		if(tpId == null || tpId == ""){
			return new ArrayList();
		}
		String sql = " SELECT T.ZTND,T.ZXH,T.SXH,TO_CHAR(T.JZRQ,'YYYY-MM-DD') AS JZRQ,T. KJPZH,T.XMDM,T.KMDM,"
				+ " T.MXDM AS CATEGORY_CODE_DTL,T.ZHY AS DETAIL_NAME,T.JD,T.JE AS KZJE，3 AS NODE,1 AS KZ,T.KZLX "
				+ " FROM T_PM_FUNDS_BUDGET_ADDENDUM T WHERE T.XMDM = "
				+ " (SELECT K.PROJECT_NO FROM T_PM_PROJECT K WHERE K.ID='"+tpId+"') ORDER BY t.MXDM,T.JZRQ ASC ";
		return this.findForJdbc(sql);
	}
	
	/**
	 * 添加协作费
	 * @param param
	 * @return
	 */
	@Override
	public String addXZFunds(Map<String, Object> param){
		List<Object[]> list = new ArrayList<>();
		String id = UUID.randomUUID().toString().replaceAll("-","");
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_pm_income_apply (ID,T_P_ID,CREATE_BY,CREATE_NAME,CREATE_DATE,APPLY_AMOUNT,")
			.append("YS_STATUS,CW_STATUS,AUDIT_STATUS,FUNDS_SOURCES,APPLY_YEAR,BARCODE) values (")
			.append("?,?,?,?,sysdate,?,?,?,?,?,?,?)" );
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		String CREATE_BY = MapUtils.getString(param, "USER_NAME");
		String CREATE_NAME = MapUtils.getString(param, "REAL_NAME");
		long APPLY_AMOUNT = MapUtils.getLong(param, "APPLY_AMOUNT");
//		String PARENT_APPLY_ID = MapUtils.getString(param, "PARENT_APPLY_ID");
		String APPLY_YEAR = MapUtils.getString(param, "APPLY_YEAR");
		String barCode = getApplyBarCode();//二维码
		Object[] objArry = {id,T_P_ID,CREATE_BY,CREATE_NAME,APPLY_AMOUNT,
				0,0,0,2,APPLY_YEAR,barCode};
		list.add(objArry);
		int[] nums = this.batchUpdate(sql.toString(), list);
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return sum == list.size() ? id : "" ; 
	}
	/**
	 * 添加开支
	 * @param param
	 * @return
	 */
	@Override
	public int addBudetAddendum(Map<String,Object> param){
		List<Object[]> list = new ArrayList<>();
		String id = UUID.randomUUID().toString().replaceAll("-","");
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_pm_funds_budget_addendum (ID,ZTND,JZRQ,"
				+ "XMDM,MXDM,ZHY,JE,KZLX,KJPZH) values(?,?,sysdate,?,?,?,?,?,?)");
		String ZTND = MapUtils.getString(param, "APPLY_YEAR");
		String XMDM = MapUtils.getString(param, "PROJECT_NO");
		String MXDM = MapUtils.getString(param, "NUM");
		String ZHY = MapUtils.getString(param, "PROJECT_NAME");
		String JE = MapUtils.getString(param, "APPLY_AMOUNT");
		String KJPZH = MapUtils.getString(param, "KJPZH");
		Object[] datas = {id,ZTND,XMDM,MXDM,ZHY,JE,1,KJPZH};
		list.add(datas);
		int[] nums = this.batchUpdate(sql.toString(), list);
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return sum; 
	}
	/**
	 * 生成二维码
	 * @return
	 */
	private String getApplyBarCode(){
    	Long count = commonDao.getCountForJdbc("select count(*) from T_PM_INCOME_APPLY where PARENT_APPLY_ID is not null ") + 1;
    	String barcodeNum = "0000000" + count;
    	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String currentYear = sdf.format(date);
		return "KX" + currentYear + barcodeNum;
	}
	/**
	 * 编辑协作费
	 * @param param
	 * @return
	 */
	@Override
	public int updateXZFunds(Map<String, Object> param){
		List<Object[]> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("update t_pm_income_apply set ")
			.append("UPDATE_BY=?,")
			.append("UPDATE_NAME=?,")
			.append("UPDATE_DATE=sysdate,")
			.append("APPLY_AMOUNT=?")
			.append("where id =?");
		
		String CREATE_BY = MapUtils.getString(param, "USER_NAME");
		String CREATE_NAME = MapUtils.getString(param, "REAL_NAME");
		long APPLY_AMOUNT = MapUtils.getLong(param, "APPLY_AMOUNT");
		String id = MapUtils.getString(param, "ID");
		
		Object[] objArry = {CREATE_BY,CREATE_NAME,APPLY_AMOUNT,id};
		list.add(objArry);
		int[] nums = this.batchUpdate(sql.toString(), list);
		
		int sum = 0;
		for(int i = 0 ; i < nums.length ; i++){
			sum += nums[i] == -2 ? 1 : -1;
		}
		return (sum == list.size()) ? 1 : -1;
	}
	/**
	 * 获取协作费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getXZFunds(Map<String,Object> param){
		String T_APPR_ID = MapUtils.getString(param, "T_APPR_ID");
		String NUM = MapUtils.getString(param, "NUM");
		String sql = "select * from t_pm_contract_funds where T_APPR_ID=? and NUM=?";
		
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),T_APPR_ID,NUM);
		if(list.size() > 0 ){
			return list.get(0);
		}
		return new HashMap<String, Object>();
	}
	/**
	 * 获取开支
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> getKZFunds(Map<String,Object> param){
		String T_P_ID = MapUtils.getString(param, "T_P_ID");
		String NUM = MapUtils.getString(param, "NUM");
		String sql = "select B.ID,A.MXDM,sum(JE) as JE " +
				"from t_pm_funds_budget_addendum A left join T_PM_PROJECT B on B.PROJECT_NO=A.XMDM " +
				"where B.ID=?  and A.kzlx=1 and MXDM=? group by B.ID,A.MXDM";
		List<Map<String, Object>> list = this.findForJdbc(sql.toString(),T_P_ID,NUM);
		if(list.size() > 0 ){
			return list.get(0);
		}
		return new HashMap<String, Object>();
		
	}
	/**
	 * 关联协作项目
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getXzProjectList(Map<String,Object> param){
		String id = MapUtils.getString(param, "T_P_ID");
		int page = 1;
		int limit = 10;
		if(MapUtils.getIntValue(param, "page",-1) > 0 && 
				MapUtils.getIntValue(param, "limit",-1) > 0){
			page = MapUtils.getIntValue(param, "page");
			limit = MapUtils.getIntValue(param, "limit");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select A.ID as PID,C.ID,C.PROJECT_NAME,C.GLXM, C.PROJECT_NO,C.PROJECT_STATUS, C.PROJECT_MANAGER, ")
		.append("to_char(C.BEGIN_DATE,'yyyy-MM-dd') as BEGIN_DATE, to_char(C.END_DATE,'yyyy-MM-dd') as END_DATE, C.ALL_FEE, ")
		.append("D.ID as INCOME_ID,D.AUDIT_STATUS,D.CW_STATUS,D.APPLY_YEAR,D.APPLY_AMOUNT ")
		.append("from t_pm_project A left join t_pm_project B on A.GLXM=B.PARENT_PROJECT or A.ID=B.PARENT_PROJECT  ")
		.append("left join t_pm_project C on C.GLXM=B.ID   ")
		.append("left join t_pm_income_apply D on D.T_P_ID=C.ID ")
		.append("where C.ID is not null  and A.ID='" + id + "' ")
		.append("order by C.PROJECT_NO ");
		return this.findForJdbc(sql.toString(), page, limit);
	}

	/**
	 * 调整预算申请
	 * @param param
	 */
	@Override
	public Map adjustYsApply(Map param) {
		Map result = new HashMap();
		Object tpId = param.get("tpId");
		String sql = " select t.finish_flag,t.cw_status from t_pm_project_funds_appr t WHERE t.t_p_id='"+ tpId +"'";
		List<Map<String,Object>> list = this.findForJdbc(sql);
		if (list != null && list.size() > 0) {
			int size = list.size();
			for (int i = 0;i < size;i++) {
				Map<String,Object> map = list.get(i);
				Object finish_flag = map.get("FINISH_FLAG");
				Object cw_status = map.get("CW_STATUS");
				if ((!"2".equals(finish_flag)) || (!"1".equals(cw_status))) {
					result.put("status", "1");
					result.put("msg", "总预算审核未通过，无法进行调整预算！");
					return result;
				}
			}
		} else {
			result.put("status", "1");
			result.put("msg", "请先进行总预算！");
			return result;
		}

		String sql2 = " update t_pm_project set tzys_status = '1' where id='"+ tpId +"' ";
		this.executeSql(sql2);

		result.put("status", "0");
		result.put("msg", "申请成功！");
		return result;
	}

	/**
	 * 调整预算申请结果
	 * @param map
	 * @return
	 */
	@Override
	public Object getTzyssqRessult(Map map) {
		Object tpId = map.get("T_P_ID");
		String sql = " select t.tzys_status from t_pm_project t WHERE t.id='"+ tpId +"' ";
		List<Map<String,Object>> list = this.findForJdbc(sql);
		if(list.size() > 0) {
			return list.get(0).get("tzys_status");
		}
		return null;
	}
}
