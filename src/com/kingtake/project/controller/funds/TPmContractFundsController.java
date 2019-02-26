package com.kingtake.project.controller.funds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fr.base.core.UUID;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.util.PriceUtil;
import com.kingtake.project.entity.funds.TPmContractFundsEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;

/**
 * @Title: Controller
 * @Description: 合同类项目经费预算主表
 * @author onlineGenerator
 * @date 2015-07-26 15:49:54
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractFundsController")
public class TPmContractFundsController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractFundsController.class);

    @Autowired
    private TPmContractFundsServiceI tPmContractFundsService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 预算展示页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractFunds")
    public ModelAndView tPmContractFunds(HttpServletRequest request) {
        //关联预算审批ID
        String apprId = request.getParameter("tpId");
        request.setAttribute("apprId", apprId);
        //是否可编辑
        boolean edit = "false".equals(request.getParameter("edit")) ? false : true;
    	request.setAttribute("edit", edit);
        // 是否预算变更
    	request.setAttribute("changeFlag", request.getParameter("changeFlag"));
        TPmProjectFundsApprEntity fundApprEntity = this.systemService.get(TPmProjectFundsApprEntity.class, apprId);
        if (fundApprEntity != null && "3".equals(fundApprEntity.getFundsType())) {//零基预算做提示
            if (StringUtils.isNotEmpty(fundApprEntity.getTpId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, fundApprEntity.getTpId());
                BigDecimal allFee = project.getAllFee() == null ? BigDecimal.ZERO : project.getAllFee();
                BigDecimal history = BigDecimal.ZERO;
                CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class);
                cq.eq("apprId", apprId);
                cq.isNull("num");
                cq.add();
                List<TPmContractFundsEntity> contractFundsList = this.systemService.getListByCriteriaQuery(cq, false);
                if (contractFundsList.size() > 0) {
                    TPmContractFundsEntity fundsEntity = contractFundsList.get(0);
                    Double historyMoney = fundsEntity.getHistoryMoney();
                    if (historyMoney != null) {
                        history = new BigDecimal(historyMoney);
                    }
                }
                String tip = "可做零基预算的金额为：" + allFee + "（总预算）-" + history + "（历次金额）=" + (allFee.subtract(history));
                request.setAttribute("tip", tip);
            }
        }
        return new ModelAndView("com/kingtake/project/funds/tPmContractFundsList");
    }
    
    /**
     * 预算详情展示：树
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        //关联预算基本信息ID
        String apprId = request.getParameter("apprId");
        String projectId = request.getParameter("tpId");

        List<Map<String, Object>> result2 = tPmContractFundsService.querycontractTree2(apprId,projectId);
        TagUtil.listToJson(response, result2);
    }
    
    /**
     * 预算展示页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goYearFundsTotalDetail")
    public ModelAndView goYearFundsTotalDetail(HttpServletRequest request) {
    	request.setAttribute("projectId", request.getParameter("projectId"));
    	return new ModelAndView("com/kingtake/project/funds/tPmProjectYearFundsTotalDetail");
    }
    
    @RequestMapping(params = "datagridForSum")
    public void datagridForSum(HttpServletRequest request, HttpServletResponse response) {
        //关联预算基本信息ID
        String projectId = request.getParameter("projectId");
        StringBuffer sql = new StringBuffer();
    	sql.append("select sum(a.MONEY) as \"MONEY\", sum(a.HISTORY_MONEY) as \"HISTORYMONEY\", a.NUM as \"NUM\",");
    	sql.append(" a.CONTENT as \"CONTENT\" from T_PM_CONTRACT_FUNDS a, T_PM_PROJECT_FUNDS_APPR b where a.T_P_ID = b.ID");
    	sql.append(" and b.FINISH_FLAG in(0,1,2)");
    	sql.append(" and (b.CW_STATUS in(0, 1) or b.CW_STATUS is null)");
    	sql.append(" and b.T_P_ID = ?");
    	sql.append(" and b.FUNDS_CATEGORY = 2");
    	sql.append(" group by a.NUM, a.CONTENT");
    	sql.append(" order by length(a.NUM) asc, a.NUM");
    	List<Map<String, Object>> list = this.systemService.findForJdbc(sql.toString(), projectId);
    	Map<String, String> totalSumMap = getTotalFundsListByProjectId(projectId);
    	int len = list.size();
    	for (int i = 0; i < len; i++) {
    		Map<String, Object> oneData = list.get(i);
    		String level = "";
			String NUM = oneData.get("NUM") == null ? "" : oneData.get("NUM").toString();
			String totalNum = totalSumMap.get(NUM);
			if(totalNum == null) {
				totalNum = "0";
			}
			if(NUM.length() > 4) {
				totalNum = "--";
			}
			oneData.put("TOTALMONEY", totalNum);
			String id = "";
			String parent = "";
			if("".equals(NUM)) {
				id = "0";
				level = "1";
			}else {
				id = NUM;
				level = ((NUM.length() / 2) + 1) + "";
				if("2".equals(level)) {
					parent = "0";
				}else {
					parent = NUM.substring(0, NUM.length()-2);
				}
			}
			oneData.put("ID", id);
			oneData.put("LEVEL", level);
			oneData.put("PARENT", parent);
			
		}
    	// 将数据分级保存
        Map<Integer, Object> levelMap = new HashMap<Integer, Object>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            int level = Integer.parseInt(map.get("LEVEL").toString());
            List<Map<String, Object>> temp = (levelMap.get(level) == null ? new ArrayList<Map<String, Object>>()
                    : (List<Map<String, Object>>) levelMap.get(level));
            temp.add(map);
            levelMap.put(level, temp);
        }

        // 获得树的层数
        int maxLevel = 0;
        if (levelMap.keySet().size() > 0) {
            maxLevel = levelMap.keySet().size();
        }
        // 组装为easyui的treegrid数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (int level = maxLevel; level > 1; level--) {
            List<Map<String, Object>> children = (List<Map<String, Object>>) levelMap.get(level);
            List<Map<String, Object>> parents = (List<Map<String, Object>>) levelMap.get(level - 1);
            for (int i = 0; i < children.size(); i++) {
                Map<String, Object> child = children.get(i);
                for (int j = 0; j < parents.size(); j++) {
                    Map<String, Object> parent = parents.get(j);
                    if (parent.get("ID").equals(child.get("PARENT"))) {
                        List<Map<String, Object>> childrenList = (parent.get("children") == null ? new ArrayList<Map<String, Object>>()
                                : (List<Map<String, Object>>) parent.get("children"));
                        childrenList.add(child);
                        if (parent.get("children") == null) {
                            parent.put("children", childrenList);
                        }
                        break;
                    }
                }
            }
        }
        if (levelMap.size() > 0) {
            result.addAll((Collection<? extends Map<String, Object>>) levelMap.get(1));
        }
    	TagUtil.listToJson(response, result);
    }
    
    
    
    private Map<String, String> getTotalFundsListByProjectId(String projectId){
    	Map<String, String> totalSumMap = new HashMap<String, String>();
    	StringBuffer sql = new StringBuffer();
    	sql.append("select sum(a.MONEY) as \"totalMoney\", a.NUM as \"num\"");
    	sql.append(" from T_PM_CONTRACT_FUNDS a, T_PM_PROJECT_FUNDS_APPR b where a.T_P_ID = b.ID and b.FUNDS_CATEGORY = 1");
    	sql.append(" and b.FINISH_FLAG in(0,1,2)");
    	sql.append(" and (b.CW_STATUS in(0, 1) or b.CW_STATUS is null)");
    	sql.append(" and b.T_P_ID = ? group by a.NUM");
    	List<Map<String, Object>> sumList = this.systemService.findForJdbc(sql.toString(), projectId);
    	int len = sumList.size();
    	for (int i = 0; i < len; i++) {
    		Map<String, Object> oneSum = sumList.get(i);
    		String totalMoney = oneSum.get("totalMoney") == null ? "0" : oneSum.get("totalMoney").toString();
    		String num = oneSum.get("num") == null ? "" : oneSum.get("num").toString();
    		totalSumMap.put(num, totalMoney);
		}
    	sql.delete(0, sql.length());
    	sql.append("select sum(b.TOTAL_FUNDS) as \"totalMoney\" from T_PM_PROJECT_FUNDS_APPR b");
    	sql.append(" where b.FUNDS_CATEGORY = 1");
    	sql.append(" and b.FINISH_FLAG in(0,1,2)");
    	sql.append(" and (b.CW_STATUS in(0, 1) or b.CW_STATUS is null)");
    	sql.append(" and b.T_P_ID = ?");
    	Map<String, Object> totlSumList = this.systemService.findOneForJdbc(sql.toString(), projectId);
    	String totalMoney = totlSumList.get("totalMoney") == null ? "" : totlSumList.get("totalMoney").toString();
    	totalSumMap.put("", totalMoney);
    	return totalSumMap;
    }
    
    /**
     * 删除
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try {
        	tPmContractFundsService.del(tPmContractFunds.getId());
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd2")
    @ResponseBody
    public AjaxJson doAdd2(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "预算项目添加成功";
        try {
            TPmContractFundsEntity parentEntity = this.systemService.get(TPmContractFundsEntity.class,
                    tPmContractFunds.getParent());
        	// 查询数据库的最大序号
			Object max = systemService.getSession().createCriteria(TPmContractFundsEntity.class)
				.add(Restrictions.eq("parent", tPmContractFunds.getParent()))
				.setProjection(Projections.max("num")).uniqueResult();
            tPmContractFunds.setNum(max == null ? parentEntity.getNum() + "01" : PriceUtil.getNum(max.toString()));
        	// 保存
            tPmContractFundsService.save(tPmContractFunds);
            // 将新生成的实体返回给页面
            j.setObj(JSONHelper.bean2json(tPmContractFunds));
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "预算项目添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    
    /**
     * 添加
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "预算项目添加成功";
        try {
            TPmContractFundsEntity parentEntity = this.systemService.get(TPmContractFundsEntity.class,
                    tPmContractFunds.getParent());
        	// 查询数据库的最大序号
			Object max = systemService.getSession().createCriteria(TPmContractFundsEntity.class)
				.add(Restrictions.eq("parent", parentEntity.getContentId()))
				.setProjection(Projections.max("num")).uniqueResult();
			String pContentId = parentEntity.getContentId();
			String num = max == null ? parentEntity.getNum() + "01" : PriceUtil.getNum(max.toString());
			String contentId = pContentId.substring(0, pContentId.indexOf("-") + 1) + num;
			
			tPmContractFunds.setContentId(contentId);
            tPmContractFunds.setNum(num);
            tPmContractFunds.setParent(parentEntity.getContentId());
            tPmContractFunds.setTpId(parentEntity.getTpId());
            tPmContractFunds.settApprId(parentEntity.gettApprId());
        	// 保存
            tPmContractFundsService.save(tPmContractFunds);
            // 将新生成的实体返回给页面
            j.setObj(JSONHelper.bean2json(tPmContractFunds));
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            
        	/*String parentSql = " select t.t_p_id from t_pm_project_funds_appr t where t.id='"+ tPmContractFunds.getApprId() +"' ";
        	List<Map<String, Object>> parentList = this.systemService.findForJdbc(parentSql);
        	if(parentList != null && parentList.size() > 0){
        		String num = tPmContractFunds.getNum();
        		String contentId = tPmContractFunds.getParent();
        		String projectId = parentList.get(0).get("t_p_id") ==null? "" : parentList.get(0).get("t_p_id").toString();
        		
        		String sql = " select num from t_pm_contract_funds t where t.t_p_id = '"+projectId+"' and t.content_id='"+contentId+"' ";
        		List<Map<String, Object>> maxList = this.systemService.findForJdbc(sql);
        		Object parentNum = maxList.get(0).get("maxnum");
        		
        		String sql2 = " select max(num) as maxnum from t_pm_contract_funds t where t.t_p_id = '"+projectId+"' and t.parent='"+contentId+"' ";
        		List<Map<String, Object>> subList = this.systemService.findForJdbc(sql2);
        		String newNum;
        		if(subList.get(0).get("maxnum") == null){
                	newNum = num + "01";
                }else{
                	//转int前面的0丢失，故前面补0
                	newNum = "0" + (Integer.parseInt(String.valueOf(subList.get(0).get("maxnum"))) + 1);
                }
        		
        		String uuid = UUID.randomUUID().toString().replace("-", "");
                TPmContractFundsEntity entity = new TPmContractFundsEntity();
                entity.setId(uuid);
                entity.setApprId(String.valueOf(projectId));
                entity.setNum(String.valueOf(newNum));
                entity.setParent(String.valueOf(contentId));
                entity.setAddChildFlag("3");
                // 保存
                tPmContractFundsService.save(entity);
                j.setObj(JSONHelper.bean2json(entity));
        		
        	}*/
        	
        	
        } catch (Exception e) {
            e.printStackTrace();
            message = "预算项目添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "预算项目更新成功";
        try {
        	tPmContractFundsService.update(tPmContractFunds);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "预算项目更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    
    /**
     * 更新
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate2")
    @ResponseBody
    public AjaxJson doUpdate2(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "预算项目更新成功";
        try {
        	request.getParameter("NUM");
        	tPmContractFundsService.update(tPmContractFunds);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "预算项目更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/funds/tPmContractFundsUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmContractFundsEntity tPmContractFunds, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractFundsEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractFunds,
                request.getParameterMap());
        List<TPmContractFundsEntity> tPmContractFundss = this.tPmContractFundsService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "T_PM_CONTRACT_FUNDS");
        modelMap.put(NormalExcelConstants.CLASS, TPmContractFundsEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("T_PM_CONTRACT_FUNDS列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmContractFundss);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

}
