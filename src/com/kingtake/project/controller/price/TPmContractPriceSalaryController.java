package com.kingtake.project.controller.price;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.common.util.PriceUtil;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceSalaryEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceSalaryServiceI;

/**
 * @Title: Controller
 * @Description: 价款计算书：工资类
 * @author onlineGenerator
 * @date 2015-08-10 15:57:54
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractPriceSalaryController")
public class TPmContractPriceSalaryController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractPriceSalaryController.class);

    @Autowired
    private TPmContractPriceSalaryServiceI tPmContractPriceSalaryService;
    @Autowired
    private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
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
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmContractPriceSalaryEntity tPmContractPriceSalary, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        // 根据根节点加载树
        List<TPmContractPriceSalaryEntity> list = tPmContractPriceSalaryService.list(tPmContractPriceSalary);
        TagUtil.response(response, JSONHelper.collection2json(list));
    }

    /**
     * 删除
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractPriceSalaryEntity tPmContractPriceSalary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String priceBudgetId = request.getParameter("priceBudgetId");
        message = "删除成功";

        try {
            tPmContractPriceSalaryService.del(tPmContractPriceSalary.getId(), priceBudgetId, j);
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
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractPriceSalaryEntity tPmContractPriceSalary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "添加成功";
        try {
            // 获得父元素
            TPmContractPriceSalaryEntity parent = this.systemService.get(TPmContractPriceSalaryEntity.class,
                    tPmContractPriceSalary.getParentTypeid());
            // 获得子元素的最大序号
            Object max = systemService.getSession().createCriteria(TPmContractPriceSalaryEntity.class)
                    .setProjection(Projections.max("serialNum"))
                    .add(Restrictions.eq("tpId", tPmContractPriceSalary.getTpId()))
                    .add(Restrictions.eq("parentTypeid", tPmContractPriceSalary.getParentTypeid())).uniqueResult();
            tPmContractPriceSalary.setSerialNum(max == null ? parent.getSerialNum() + "01" : PriceUtil.getNum(max
                    .toString()));
            // 添加
            tPmContractPriceSalaryService.save(tPmContractPriceSalary);
            // 将实体返回给页面
            j.setObj(JSONHelper.bean2json(tPmContractPriceSalary));
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "添加失败";
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
    public AjaxJson doUpdate(TPmContractPriceSalaryEntity tPmContractPriceSalary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String priceBudgetId = request.getParameter("priceBudgetId");
        message = "更新成功";

        try {
            // 更新父辈数据
            tPmContractPriceSalaryService.update(tPmContractPriceSalary, priceBudgetId, j);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmContractPriceSalaryEntity tPmContractPriceSalary, HttpServletRequest req) {
        // 查询合同信息
        req.setAttribute("cover",
                systemService.get(TPmContractPriceCoverEntity.class, tPmContractPriceSalary.getTpId()));
        // 根节点信息
        TPmContractPriceSalaryEntity detail = (TPmContractPriceSalaryEntity) systemService.getSession()
                .createCriteria(TPmContractPriceSalaryEntity.class)
                .add(Restrictions.eq("typeId", req.getParameter("typeId")))
                .add(Restrictions.eq("tpId", tPmContractPriceSalary.getTpId())).uniqueResult();
        TBApprovalBudgetRelaEntity budget = systemService.get(TBApprovalBudgetRelaEntity.class,
                req.getParameter("typeId"));
        req.setAttribute("budget", budget);
        req.setAttribute("detail", detail);
        req.setAttribute("read", req.getParameter("read"));
        return new ModelAndView("com/kingtake/project/price/tPmContractPriceSalary-update");
    }

}
