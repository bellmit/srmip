package com.kingtake.project.service.ys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.util.StringUtil;

import org.apache.commons.collections.MapUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kingtake.project.service.ys.YsDaoService;
import com.kingtake.project.service.ys.YsEnum;
import com.kingtake.project.service.ys.YsService;

@Service("ysService")
@Transactional
public class YsServiceImpl extends CommonServiceImpl implements YsService{
	@Autowired
	private YsDaoService ysDaoService;
	
	@Override
	public Map<String, Object> getZysType(Map<String, Object> param) {
		Map<String,Object> dataMap = new HashMap<String, Object>();

		double zys = MapUtils.getDoubleValue(ysDaoService.getZysFunds(param), "ZYS_FUNDS");	//总预算合计
		Map<String,Object> xmFundData = ysDaoService.getXmFund(param);
		double ZJF_FUNDS = MapUtils.getDoubleValue(xmFundData, "ZJF_FUNDS");
		if(zys == 0){  // 总预算
			dataMap.put("code", YsEnum.ZYS_BUDGET.getKey());
			dataMap.put("value", YsEnum.ZYS_BUDGET.getValue());
		}else if((ZJF_FUNDS - zys) > 0){//调增
			dataMap.put("code", YsEnum.TZ_BUDGET.getKey());
			dataMap.put("value", YsEnum.TZ_BUDGET.getValue());
		}else{//调整
			Object tzyssq = ysDaoService.getTzyssqRessult(param);
			if("3".equals(tzyssq)){
				dataMap.put("code", YsEnum.TZH_BUDGET.getKey());
				dataMap.put("value", YsEnum.TZH_BUDGET.getValue());
			} else if("4".equals(tzyssq)){
				dataMap.put("code", "8");
				dataMap.put("value", "调整预算审核未通过！");
			} else if(tzyssq == null || tzyssq == ""){
				dataMap.put("code", "9");
				dataMap.put("value", "请先进行调整预算申请！");
			} else {
				dataMap.put("code", "10");
				dataMap.put("value", "调整预算申请审核未通过！");
			}

		}
		
		return dataMap;
	}

	@Override
	public Map<String, Object> getNdysType(Map<String, Object> param) {
		Map<String,Object> dataMap = new HashMap<String, Object>();
		param.put("FUNDS_CATEGORY", 1);	//总预算
		if(ysDaoService.getYsList(param).size() <= 0){
			dataMap.put("code", -1);
			dataMap.put("value", "项目还没有总预算，请先做总预算");
			return dataMap;
		}
		//年度预算
		param.put("FUNDS_CATEGORY", 2);	//年度预算
		double dz = MapUtils.getDoubleValue(ysDaoService.getDzFunds(param), "DZ_FUNDS");	//垫支合计
		double rl = MapUtils.getDoubleValue(ysDaoService.getRlFunds(param), "RL_FUNDS");	//认领合计
		double fpje = MapUtils.getDoubleValue(ysDaoService.getFpFunds(param), "FP_JE");		//分配合计
		double ys_je = dz + rl + fpje;
		param.put("FUNDS_STATUS", 0);	//未做预算
		double dz_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getDzFunds(param), "DZ_FUNDS");	//垫支合计
		double rl_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getRlFunds(param), "RL_FUNDS");	//认领合计
		double fpje_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getFpFunds(param), "FP_JE");	//分配合计
		double kbj_je = dz_Ys_0 + rl_Ys_0 + fpje_Ys_0;
		param.put("FUNDS_STATUS", 1);	//已做预算
		double dz_Ys_1 = MapUtils.getDoubleValue(ysDaoService.getDzFunds(param), "DZ_FUNDS");	//垫支合计
		double rl_Ys_1 = MapUtils.getDoubleValue(ysDaoService.getRlFunds(param), "RL_FUNDS");	//认领合计
		double fpje_Ys_1 = MapUtils.getDoubleValue(ysDaoService.getFpFunds(param), "FP_JE");	//分配合计
		double yys_je = dz_Ys_1 + rl_Ys_1 + fpje_Ys_1;
		
		if(ys_je <= 0){
			dataMap.put("code", -1);
			dataMap.put("value", "项目还没有到款，无法做预算");
			return dataMap;
		}else if(kbj_je == 0){//调整
			dataMap.put("code", YsEnum.TZH_BUDGET.getKey());
			dataMap.put("value", YsEnum.TZH_BUDGET.getValue());
		}else if( ys_je == kbj_je || (ys_je - yys_je) == kbj_je){//开支计划
			dataMap.put("code", YsEnum.KZ_BUDGET.getKey());
			dataMap.put("value", YsEnum.KZ_BUDGET.getValue());
		}else if(kbj_je > 0){//调增
			dataMap.put("code", YsEnum.TZ_BUDGET.getKey());
			dataMap.put("value", YsEnum.TZ_BUDGET.getValue());
		}else{
			dataMap.put("code", -1);
			dataMap.put("value", "数据异常无法完成预算，请核查数据");
		}
		
		return dataMap;
	}

	@Override
	public List<Map<String, Object>>  getDzFundsList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>>  getRlFundsList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>>  getZysFundsList(Map<String, Object> param) {
		param.put("FUNDS_CATEGORY", 1);
		return ysDaoService.getYsList(param);
	}

	@Override
	public List<Map<String, Object>>  getZysDetailList(Map<String, Object> param) {
		return ysDaoService.getYsDetailList(param);
	}

	@Override
	public List<Map<String, Object>>  getNdysFundsList(Map<String, Object> param) {
		param.put("FUNDS_CATEGORY", 2);
		return ysDaoService.getYsList(param);
	}

	@Override
	public List<Map<String, Object>>  getNdysDetailList(Map<String, Object> param) {
		
		return ysDaoService.getYsDetailList(param);
	}

	/**
	 * 获取总预算合计
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getZysTotalList(Map<String, Object> param) {
		param.put("FUNDS_CATEGORY",1);
		List<Map<String,Object>> list = ysDaoService.getYsTotal(param);
		Map<String,Object> hjData = new HashMap<>();
		double sumMoney = 0d;
		for(int i = 0 ; i < list.size() ; i++){
			Map<String,Object> data = list.get(i);
			if(MapUtils.getInteger(data, "CATEGORY_CODE") == 0){
				hjData = data;
			}else if(MapUtils.getInteger(data, "NODE",-1) == 1){
				sumMoney += MapUtils.getDoubleValue(data, "MONEY");
			}
		}
		hjData.put("MONEY", sumMoney);
		return list;
	}
	/**
	 * 获取年度预算合计
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getNdysTotalList(Map<String, Object> param) {
		param.put("FUNDS_CATEGORY",2);
		List<Map<String,Object>> list = ysDaoService.getYsTotal(param);
		Map<String,Object> hjData = new HashMap<>();
		double sumMoney = 0d;
		for(int i = 0 ; i < list.size() ; i++){
			Map<String,Object> data = list.get(i);
			if(MapUtils.getInteger(data, "CATEGORY_CODE") == 0){
				hjData = data;
			}else if(MapUtils.getInteger(data, "NODE",-1) == 1){
				sumMoney += MapUtils.getDoubleValue(data, "MONEY");
			}
		}
		hjData.put("MONEY", sumMoney);
		return list;
	}

	@Override
	public Map<String, Object> getTreeTemplate(Map<String, Object> param) {
		String YS_TYPE = MapUtils.getString(param, "YS_TYPE","ALL");
		Map<String,Object> mapData = new HashMap<String,Object>();
		String projectId = MapUtils.getString(param, "T_P_ID");
		Map<String,Object> projectData = ysDaoService.getProjectById(projectId);
		if(YS_TYPE.equals("ALL")){
			mapData = this.getZysTemplate(param);
		}else if(YS_TYPE.equals("YEAR")){
			mapData = this.getNdysTemplate(param);
		}
		return mapData;
	}
	/**
	 * 总预算树
	 * @param param
	 * @return
	 */
	private Map<String, Object> getZysTemplate(Map<String, Object> param) {
		Map<String, Object> data = new HashMap<>();
		int FUNDS_TYPE = MapUtils.getIntValue(param, "FUNDS_TYPE",0);
		data = ysDaoService.getYsData(param);
		if(data.size() > 0 && FUNDS_TYPE > 0){
			String HT_STATUS = "HT_" + MapUtils.getString(data, "FINISH_FLAG","0");
			String CW_STATUS = "CW_" + MapUtils.getString(data, "CW_STATUS","0");
			String yslx = YsEnum.getEnumByKey(MapUtils.getString(data, "FUNDS_TYPE")).getValue();
			if((!HT_STATUS.equals(YsEnum.HT_STATUS_2.getKey()) ||
					!CW_STATUS.equals(YsEnum.CW_STATUS_1.getKey())) ){
				data.put("code", -1);
				data.put("msg", yslx + "没有通过审核");
				
				return data;
			}
		}
			
		Map<String, Object> xmFund = ysDaoService.getXmFund(param);
		if(FUNDS_TYPE == -1){
			xmFund.put("FP_FUNDS", 0);
		}
		List<Map<String,Object>> list = ysDaoService.getPmTemplate(param);
		for(int i = 0 ; i < list.size() ; i++){
			Map<String,Object> map = list.get(i);
			if(MapUtils.getInteger(map, "PID") == -1){
				map.put("XE", xmFund.get("ZJF_FUNDS"));
				break;
			}
		}
		data = TagUtil.getDataJson(list);
		return data;
	}
	/**
	 * 年度预算树
	 * @param param
	 * @return
	 */
	private Map<String, Object> getNdysTemplate(Map<String, Object> param) {
		Map<String, Object> data = new HashMap<>();
		String ysId = MapUtils.getString(param, "ysId");
		if(StringUtil.isNotEmpty(MapUtils.getString(param, "pageType"))){
			param.put("FUNDS_TYPE", -1);
		}
		
		Map<String,Object> ndData = ysDaoService.getYsData(param);//判断预算是否审核
		if(ndData.size() > 0 && MapUtils.getIntValue(param, "FUNDS_TYPE") > 0){
			String HT_STATUS = "HT_" + MapUtils.getString(ndData, "FINISH_FLAG","0");
			String CW_STATUS = "CW_" + MapUtils.getString(ndData, "CW_STATUS","0");
			String yslx = YsEnum.getEnumByKey(MapUtils.getString(ndData, "FUNDS_TYPE")).getValue();
			if((!HT_STATUS.equals(YsEnum.HT_STATUS_2.getKey()) ||
					!CW_STATUS.equals(YsEnum.CW_STATUS_1.getKey())) ){
				data.put("code", -1);
				data.put("msg", yslx + "没有通过审核");
				return data;
			}
		}
		List<Map<String,Object>> ndTree = new ArrayList<>();
		if(MapUtils.getString(param, "FUNDS_TYPE").equals(YsEnum.TZ_BUDGET.getKey())){
			ndTree = ysDaoService.getYsTotal(param);
		}else{
			ndTree = ysDaoService.getPmTemplate(param);//年度预算树
			ysId = StringUtil.isNotEmpty(ysId)?ysId:MapUtils.getString(ndData, "ID");
			param.put("ysId", ysId);
			List<Map<String,Object>> detailTree = ysDaoService.getDetailFunds(param);//明细
			ndTree.addAll(detailTree);
		}
		
		//总预算树
		param.put("FUNDS_CATEGORY", 1);
		Map<String,Object> zysData = ysDaoService.getYsData(param);
		param.put("layer", 1);
		param.put("ysId", MapUtils.getString(zysData, "ID"));
		param.put("PID", "19-10");
		List<Map<String,Object>> zysTree = ysDaoService.getPmTemplate(param);//总预算树
		
		param.put("FUNDS_STATUS", 0);	//未做预算
		double dz_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getDzFunds(param), "DZ_FUNDS");	//垫支合计
		double rl_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getRlFunds(param), "RL_FUNDS");	//认领合计
		double fpje_Ys_0 = MapUtils.getDoubleValue(ysDaoService.getFpFunds(param), "FP_JE");	//分配合计
		double kbj_je = dz_Ys_0 + rl_Ys_0 + fpje_Ys_0;
		for(int i = 0 ; i < ndTree.size() ;i++){
			Map<String,Object> ndMap = ndTree.get(i);
			if(MapUtils.getString(ndMap, "ID").equals("0-0")){
				if(MapUtils.getInteger(param, "FUNDS_TYPE") == -1){
					ndMap.put("XE", MapUtils.getDouble(ndData, "TOTAL_FUNDS"));
				}else{
					ndMap.put("XE", kbj_je);
				}
				continue;
			}
			for(int t = 0 ; t < zysTree.size() ; t++){
				Map<String,Object> zysMap = zysTree.get(t);
				if(MapUtils.getString(ndMap, "ID").equals(MapUtils.getString(zysMap, "ID"))){
					ndMap.put("XE", MapUtils.getDouble(zysMap, "MONEY"));
					zysTree.remove(t);
					break;
				}
			}
		}
		
		data = TagUtil.getDataJson(ndTree);
		Map<String, Object> xmFund = ysDaoService.getXmFund(param);
		data.putAll(xmFund);
		return data;
	}
	
	/**
	 * 新增、编辑预算
	 * @param param
	 * @return
	 */
	@Override
	public int saveOrUpdateFund(Map<String, Object> param){
		int FUNDS_TYPE = MapUtils.getIntValue(param, "code");
		List<Map> dataList = JSON.parseArray(MapUtils.getString(param, "list"), Map.class);
		for(int i = 0 ; i < dataList.size() ; i++){
			Map data = dataList.get(i);
			if(MapUtils.getInteger(data, "NODE") == 0){
				dataList = (List<Map>)data.get("children");
				param.put("TOTAL_FUNDS", MapUtils.getInteger(data, "XE"));
				break;
			}
		}
		int num = 0;
		if(FUNDS_TYPE >= 0){ //新增
			Map<String, Object> map = getProjectFunds(param);
			//主表
			num += ysDaoService.addFundAppr(map);
			//明细表
			List<Map<String,Object>> list = calculateDetailFunds(dataList,param);
			map.put("list", list);
			num += ysDaoService.addFunds(map);
			//更新到款状态
			if(num > 0 && MapUtils.getInteger(param, "FUNDS_CATEGORY") == 2){
				ysDaoService.updateYSstatus(map);
			}
		}else{//编辑
			List<Map<String,Object>> list = calculateDetailFunds(dataList,param);
			param.put("list", list);
			num += ysDaoService.editFunds(param);
		}
		return num;
	}
	
	/**
	 * 组装预算信息
	 * @param param
	 * @return
	 */
	private Map<String, Object> getProjectFunds(Map<String, Object> param){
		TSUser user = ResourceUtil.getSessionUserName();
		Map<String, Object> map = new HashMap<>();
		map.put("T_P_ID", MapUtils.getString(param, "ID"));
		String beginData = MapUtils.getString(param, "BEGIN_DATE_EN");
		map.put("START_YEAR", beginData.substring(0,beginData.indexOf("-")));
		String endData = MapUtils.getString(param, "END_DATE_EN");
		map.put("END_YEAR", beginData.substring(0,endData.indexOf("-")));
		map.put("DEPARTS_ID", MapUtils.getString(param, "DEVELOPER_DEPART"));//承研单位ID
		map.put("DEPARTS_NAME", MapUtils.getString(param, "DEVELOPER_DEPART_EN"));//承研单位
		map.put("PROJECT_MANAGER", MapUtils.getString(param, "PROJECT_MANAGER"));//负责人ID
		map.put("PROJECT_MANAGER_NAME", MapUtils.getString(param, "PROJECT_MANAGER"));//负责人名称
		map.put("PROJECT_NAME", MapUtils.getString(param, "PROJECT_NAME"));
		map.put("FEE_TYPE", MapUtils.getString(param, "FEE_TYPE"));
		map.put("ALL_FEE", MapUtils.getInteger(param, "ALL_FEE"));
		map.put("MEMBERS_ID", MapUtils.getString(param, "MEMBERS_ID"));
		map.put("MEMBERS_NAME", MapUtils.getString(param, "MEMBERS_NAME"));
		map.put("CREATE_BY", user.getId());//创建人ID
		map.put("CREATE_NAME", user.getUserName());//创建人名称
//		map.put("CREATE_NAME", new Date());//创建时间
		map.put("FUNDS_TYPE", MapUtils.getString(param, "code")); //预算类型：-1、编辑、新增
		map.put("FUNDS_CATEGORY", MapUtils.getString(param, "FUNDS_CATEGORY"));//预算种类：  1、总预算、2、年度
		map.put("TOTAL_FUNDS", MapUtils.getInteger(param, "TOTAL_FUNDS"));// 预算金额
		map.put("CW_STATUS", "0");	//财务审核
		map.put("FINISH_FLAG", "0");//科研审核
		return map;
	}
	/**
	 * 组装明细数据
	 * @param list
	 * @return
	 */
	private List<Map<String,Object>> calculateDetailFunds(List<Map> list ,Map<String, Object> param){
		List<Map<String,Object>> rList = new ArrayList<>();
		for(int i = 0 ; i < list.size() ; i++){
			Map<String,Object> data = list.get(i);
			if(MapUtils.getInteger(data,"HISTORY_MONEY", -1) < 0 && MapUtils.getInteger(data,"MONEY", -1) < 0){
				continue;
			}
			Map<String,Object> mapData = new HashMap<>();
			String FUNDS_ID = MapUtils.getString(data, "FUNDS_ID");
			if(StringUtil.isNotEmpty(FUNDS_ID)){
				mapData.put("ID", FUNDS_ID);
			}
			mapData.put("T_P_ID", MapUtils.getString(param, "ID"));
			mapData.put("NUM", MapUtils.getString(data, "CATEGORY_CODE_DTL"));
			mapData.put("CONTENT_ID", MapUtils.getString(data, "ID"));
			mapData.put("CONTENT",  MapUtils.getString(data, "DETAIL_NAME"));
			mapData.put("PARENT", MapUtils.getString(data, "PID"));
			mapData.put("ADD_CHILD_FLAG", MapUtils.getString(data, "DETAIL_SYMBOL"));
			mapData.put("UNIT", MapUtils.getString(data, "UNIT"));
			mapData.put("PRICE", MapUtils.getInteger(data, "PRICE"));
			mapData.put("AMOUNT", MapUtils.getInteger(data, "AMOUNT"));
			mapData.put("T_APPR_ID", MapUtils.getString(param, "T_APPR_ID"));
			mapData.put("REMARK", MapUtils.getString(data, "DETAIL_DESC"));
			mapData.put("VARIABLE_MONEY", MapUtils.getLong(data, "VARIABLE_MONEY"));
			mapData.put("HISTORY_MONEY", MapUtils.getLong(data, "HISTORY_MONEY"));
			mapData.put("MONEY", MapUtils.getLong(data, "MONEY"));
			mapData.put("NODE", MapUtils.getLong(data, "NODE"));
			rList.add(mapData);
			//添加子数据
			List<Map> childrenList = JSON.parseArray(MapUtils.getString(data, "children"), Map.class);
			if(childrenList.size() > 0){
				rList.addAll(calculateDetailFunds(childrenList, param));
			}
		}
		return rList;
	}
	
	/**
	 * 
	 * delZysContractFunds:(删除预算)
	 * @author liangzhe
	 *
	 * @param param
	 */
	@Override
	public int delZysContractFunds(Map<String, Object> param) {
		// TODO Auto-generated method stub
		int contractNum = ysDaoService.delContractFunds(param);
		int apprNum = ysDaoService.delProjectFundsAppr(param);
		return apprNum;
	}

	/**
	 * 获取分配列表
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getFpTotalList(Map<String, Object> param) {
		List<Map<String, Object>> list = ysDaoService.getFpTotalList(param);
		for(int i = 0 ; i < list.size() ;i++){
			Map<String, Object> data = list.get(i);
			if(MapUtils.getIntValue(data, "FUNDS_TYPE") == 3){
				data.put("ADUIT_STATUS_TEXT", YsEnum.getEnumByKey("DZ_" + MapUtils.getString(data, "AUDIT_STATUS")).getValue());
			}else{
				data.put("ADUIT_STATUS_TEXT", YsEnum.getEnumByKey("HT_" + MapUtils.getString(data, "AUDIT_STATUS")).getValue());
			}
			data.put("CW_STATUS_TEXT", YsEnum.getEnumByKey("CW_" + MapUtils.getString(data, "CW_STATUS","0")).getValue());
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getDzTotalList(Map<String, Object> param) {
		List<Map<String, Object>> list = ysDaoService.getDzTotalList(param);
		for(int i = 0 ; i < list.size() ;i++){
			Map<String, Object> data = list.get(0);
			String dzStatus = "DZ_" + MapUtils.getString(data, "BPM_STATUS","0");
			String cwStatus = "CW_" + MapUtils.getString(data, "CW_STATUS","0");
			String ysStatus = "YS_" + MapUtils.getString(data, "YS_STATUS","0");
			data.put("BPM_STATUS_TEXT", YsEnum.getEnumByKey(dzStatus).getValue());
			data.put("CW_STATUS_TEXT", YsEnum.getEnumByKey(cwStatus).getValue());
			data.put("YS_STATUS_TEXT", YsEnum.getEnumByKey(ysStatus).getValue());
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> getProjectExecInfo(Map param) {
		Object tpId = param.get("T_P_ID");
		if(tpId == null || tpId == ""){
			return new ArrayList();
		}
		//获取预算数据
		List<Map<String,Object>> ndTree = getNdysTotalList(param);
		
		
		//获取开支数据
		List<Map<String, Object>> spendingList = ysDaoService.getSpendingList(param);
		Map<String,List> nodeMap = getPnodeMap(spendingList);//获取父节点数据集合

		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int size = ndTree.size();
		for(int i=0;i<size;i++){
			Map map = ndTree.get(i);
			//result.add(map);
			
			Object key = map.get("CATEGORY_CODE_DTL");
			Object pid = map.get("ID");
			List<Map<String,Object>> childList = nodeMap.get(key);
			if(childList != null && childList.size() > 0){
				int kzje = 0;//总开支金额,执行金额
				int jdje = 0;//借贷金额
				int hkje = 0;//还款金额
				//int ye = 0;//余额 = 预算金额 - 总开支金额
				int money = map.get("MONEY") == null ? 0 : Integer.parseInt(map.get("MONEY").toString());
				for(int j=0;j<childList.size();j++){
					Map child = childList.get(j);
					child.put("PID",pid);
					Object kzlx = child.get("KZLX");
					if("1".equals(kzlx)){//开支
						int kz = child.get("KZJE")==null ? 0:Integer.parseInt(child.get("KZJE").toString());
						kzje+=kz;
					}else if("2".equals(kzlx)){//借贷
						int jd = child.get("KZJE")==null ? 0:Integer.parseInt(child.get("KZJE").toString());
						jdje+=jd;
						child.put("JDJE",child.get("KZJE"));
						child.put("KZJE",null);

					}else if("3".equals(kzlx)){//还款
						int hk = child.get("KZJE")==null ? 0:Integer.parseInt(child.get("KZJE").toString().split(".")[0]);
						hkje+=hk;
						child.put("JDJE","-"+child.get("KZJE"));
						child.put("KZJE",null);
					}
				}

				map.put("JDJE",jdje - hkje);
				map.put("KZJE",kzje);
				map.put("YE",money - kzje - jdje + hkje);
				result.add(map);
				result.addAll(childList);
			}else{
				result.add(map);
			}

		}
		
		return result;
	}
	
	/**
	 * 新增、修改协作费
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> saveOrUpdateXZFunds(Map param){
		Map<String,Object> map = new HashMap<>();
		TSUser user = ResourceUtil.getSessionUserName();
		String userName = user.getUserName();
		String realName = user.getRealName();
		param.put("USER_NAME", userName);
		param.put("REAL_NAME", realName);
		
		if(MapUtils.getBooleanValue(param, "isExdit",false)){//编辑
			int flg = ysDaoService.updateXZFunds(param);
			map.put("code", flg);
		}else{//新增
			String id = ysDaoService.addXZFunds(param);
			map.put("id", id);
		}
		return map;
	}
	/**
	 * 新增、编辑开支
	 * @param param
	 * @return
	 */
	@Override
	public Map<String,Object> saveOrUpdateKz(Map param){
		Map<String,Object> map = new HashMap<>();
		if(MapUtils.getBooleanValue(param, "isExdit",false)){//编辑
			
		}else{//新增
			ysDaoService.addBudetAddendum(param);
		}
		return map;
	}
	/**
	 * 协作费
	 * @param param
	 * @return
	 */
	public Map<String,Object> getXZFunds(Map<String,Object> param){
		Map<String,Object> ysData = ysDaoService.getYsData(param);
		param.put("T_APPR_ID", MapUtils.getString(ysData, "ID"));
		Map<String,Object> xzYSFunds = ysDaoService.getXZFunds(param);
		Map<String,Object> xzKzFunds = ysDaoService.getKZFunds(param);
		double HISTORY_MONEY = MapUtils.getDoubleValue(xzYSFunds, "HISTORY_MONEY");
		double MONEY = MapUtils.getDoubleValue(xzYSFunds, "MONEY");
		double kzJE = MapUtils.getDoubleValue(xzKzFunds, "JE");
		double fpJe = HISTORY_MONEY + MONEY - kzJE;
		xzYSFunds.put("FPJE", fpJe);
		xzYSFunds.put("APPLY_YEAR", MapUtils.getString(ysData, "START_YEAR"));
		return xzYSFunds;
	}
	
	/**
	 * 关联协作项目
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getXzProjectList(Map<String,Object> param){
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> data = new HashMap<>();
		List<Map<String,Object>> xzList = ysDaoService.getXzProjectList(param);
		//项目分组
		for(int i = 0 ; i < xzList.size() ; i++){
			List<Map<String,Object>> xmList = new ArrayList<>();
			Map<String,Object> xzData = xzList.get(i);
			String PROJECT_NO = MapUtils.getString(xzData, "PROJECT_NO");
			for(Entry<String, Object> entry : data.entrySet()){
				String key = entry.getKey();
				if(PROJECT_NO.equals(key)){
					xmList = (List)data.get(PROJECT_NO);
					break;
				}
			}
			xmList.add(xzData);
			data.put(PROJECT_NO, xmList);
		}
		//计算协作项目经费
		for(Entry<String, Object> entry : data.entrySet()){
			List<Map<String,Object>> values = (List)entry.getValue();
			Map<String,Object> xmData = new HashMap<>();
			double dshMoney = 0;//待提交
			double ztMoney = 0;//在途
			double fpMoney = 0;//已分配
			String INCOME_ID = "";//待提交到款ID
			for(int i = 0 ; i < values.size() ;i++){
				xmData = values.get(i);
				int AUDIT_STATUS = MapUtils.getIntValue(xmData, "AUDIT_STATUS",0);
				int CW_STATUS = MapUtils.getIntValue(xmData, "CW_STATUS",0);
				double money = MapUtils.getDoubleValue(xmData, "APPLY_AMOUNT");
				if( AUDIT_STATUS == 0 && CW_STATUS == 0){//待提交
					dshMoney += money;
					INCOME_ID = MapUtils.getString(xmData, "INCOME_ID");
				}else if(AUDIT_STATUS == 2 && CW_STATUS == 1){//已分配
					fpMoney += money;
				}else if(AUDIT_STATUS != 3 && CW_STATUS != 3){//在途
					ztMoney += money;
				}
			}
			xmData.remove("AUDIT_STATUS");
			xmData.remove("CW_STATUS");
			xmData.remove("APPLY_AMOUNT");
			xmData.put("INCOME_ID", INCOME_ID);
			xmData.put("D_SH_MONEY", dshMoney);
			xmData.put("ZT_MONEY", ztMoney);
			xmData.put("FP_MONEY", fpMoney);
			list.add(xmData);
		}
		return list;
	}
	
	/**
	 * 组装父节点数据集合
	 * key：父节点ID； value：开支list
	 * @param list
	 * @return
	 */
	private Map<String,List> getPnodeMap(List<Map<String, Object>> list) {
		Map<String,List> result = new HashMap<String,List>();
		int size = list.size();
		for(int i=0;i<size;i++){
			Map map = list.get(i);
			String key = map.get("CATEGORY_CODE_DTL").toString();
			List value =result.get(key);
			if(value == null){
				value = new ArrayList();
			}
			
			value.add(map);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 调整预算申请
	 * @param param
	 */
	@Override
	public Map adjustYsApply(Map param) {
		return ysDaoService.adjustYsApply(param);
	}
}
