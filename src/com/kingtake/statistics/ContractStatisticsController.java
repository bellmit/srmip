package com.kingtake.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.ApprovalConstant;

/**
 * 网络硬盘
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/ContractStatisticsController")
public class ContractStatisticsController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ContractStatisticsController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 跳转到外协合同统计查询界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goOutcomeContractNumLine")
    public ModelAndView goOutcomeContractNumLine(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/statistics/contractnum-statistics");
    }

    /**
     * 获取线图的数据
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOutcomeContractNumLineData")
    @ResponseBody
    public JSONObject getOutcomeContractNumLineData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Calendar cal = Calendar.getInstance();
        List<String> xArr = new ArrayList<String>();
        int year = cal.get(Calendar.YEAR);
        for (int i = 4; i >= 0; i--) {
            xArr.add((year - i) + "");
        }
        json.put("xAxis", xArr);
        List<Integer> numList = new ArrayList<Integer>();
        String sql = "select count(1) from t_pm_outcome_contract_appr appr where appr.finish_flag=? and to_char(appr.CONTRACT_SIGNING_TIME,'yyyy')=?";
        for (String x : xArr) {
            Long num = this.systemService.getCountForJdbcParam(sql, new Object[] { ApprovalConstant.APPRSTATUS_FINISH,
                    x });
            numList.add(num.intValue());
        }
        json.put("series", numList);
        return json;
    }

    /**
     * 跳转到外协合同统计查询界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goOutcomeContractAmountLine")
    public ModelAndView goOutcomeContractAmountLine(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/statistics/contractsum-statistics");
    }

    /**
     * 获取线图的数据
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOutcomeContractAmountLineData")
    @ResponseBody
    public JSONObject getOutcomeContractAmountLineData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Calendar cal = Calendar.getInstance();
        List<String> xArr = new ArrayList<String>();
        int year = cal.get(Calendar.YEAR);
        for (int i = 4; i >= 0; i--) {
            xArr.add((year - i) + "");
        }
        json.put("xAxis", xArr);
        List<BigDecimal> numList = new ArrayList<BigDecimal>();
        String sql = "select sum(appr.total_funds) sum from t_pm_outcome_contract_appr appr where appr.finish_flag=? and to_char(appr.CONTRACT_SIGNING_TIME,'yyyy')=?";
        for (String x : xArr) {
            Map<String,Object> sumMap = this.systemService.findOneForJdbc(sql, new Object[] {
                    ApprovalConstant.APPRSTATUS_FINISH,
                    x });
            BigDecimal sum = (BigDecimal) sumMap.get("sum");
            sum = (sum == null) ? BigDecimal.ZERO : sum;
            numList.add(sum);
        }
        json.put("series", numList);
        return json;
    }

    /**
     * 跳转到外协合同统计查询tab界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goOutcomeContractStatisticsTab")
    public ModelAndView goOutcomeContractStatisticsTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/statistics/statistics-tab");
    }

    /**
     * 跳转到外协合同采购方式查询界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goOutcomeContractCgfsLine")
    public ModelAndView goOutcomeContractCgfsLine(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/statistics/contractcgfs-statistics");
    }

    /**
     * 获取柱状图的数据
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOutcomeContractCgfsLineData")
    @ResponseBody
    public JSONObject getOutcomeContractCgfsLineData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        Calendar cal = Calendar.getInstance();
        List<String> xArr = new ArrayList<String>();
        int year = cal.get(Calendar.YEAR);
        for (int i = 4; i >= 0; i--) {
            xArr.add((year - i) + "");
        }
        json.put("xAxis", xArr);
        TBCodeTypeEntity codeType = new TBCodeTypeEntity();
        codeType.setCodeType("1");
        codeType.setCode("CGFS");
        List<TBCodeDetailEntity> codeDetails = tBCodeTypeService.getCodeByCodeType(codeType);
        String sql = "select count(1) sum from t_pm_outcome_contract_appr appr where appr.finish_flag=? and to_char(appr.CONTRACT_SIGNING_TIME,'yyyy')=? and appr.acquisition_method=? ";
        List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
        for (TBCodeDetailEntity entity : codeDetails) {
            Map<String, Object> numMap = new HashMap<String, Object>();
            List<Integer> numList = new ArrayList<Integer>();
        for (String x : xArr) {
                Long num = this.systemService.getCountForJdbcParam(sql, new Object[] {
                        ApprovalConstant.APPRSTATUS_FINISH, x, entity.getCode() });
                numList.add(num.intValue());
        }
            numMap.put("name",entity.getName());
            numMap.put("data" , numList);
            series.add(numMap);
        }
        json.put("series", series);
        return json;
    }

    public static void main(String args[]) {
        BigDecimal big = new BigDecimal("100333332.99");
        System.out.println(big.doubleValue());
    }
}
