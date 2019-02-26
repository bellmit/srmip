package com.kingtake.office.controller.memo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
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

import com.kingtake.office.entity.memo.TOPrivateMemoEntity;
import com.kingtake.office.service.memo.TOPrivateMemoServiceI;

/**
 * @Title: Controller
 * @Description: 备忘录
 * @author onlineGenerator
 * @date 2015-07-09 16:02:34
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOPrivateMemoController")
public class TOPrivateMemoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOPrivateMemoController.class);

    @Autowired
    private TOPrivateMemoServiceI tOPrivateMemoService;
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
     * 备忘录列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOPrivateMemo")
    public ModelAndView tOPrivateMemo(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/memo/tOPrivateMemoList");
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
    public void datagrid(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOPrivateMemoEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOPrivateMemo,
                request.getParameterMap());
        try {
            // 自定义追加查询条件
            cq.eq("createBy", ResourceUtil.getSessionUserName().getUserName());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tOPrivateMemoService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除备忘录
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOPrivateMemo = systemService.getEntity(TOPrivateMemoEntity.class, tOPrivateMemo.getId());
        message = "备忘录删除成功";
        try {
            tOPrivateMemoService.deleteMemo(tOPrivateMemo);
        } catch (Exception e) {
            e.printStackTrace();
            message = "备忘录删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除备忘录
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "备忘录删除成功";
        try {
            for (String id : ids.split(",")) {
                TOPrivateMemoEntity tOPrivateMemo = systemService.getEntity(TOPrivateMemoEntity.class, id);
                tOPrivateMemoService.deleteMemo(tOPrivateMemo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "备忘录删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加备忘录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "备忘录添加成功";
        try {
            tOPrivateMemoService.save(tOPrivateMemo);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "备忘录添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOPrivateMemo);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新备忘录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "备忘录更新成功";
        TOPrivateMemoEntity t = tOPrivateMemoService.get(TOPrivateMemoEntity.class, tOPrivateMemo.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOPrivateMemo, t);
            tOPrivateMemoService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "备忘录更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOPrivateMemo);
        j.setMsg(message);
        return j;
    }

    /**
     * 备忘录新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOPrivateMemo.getId())) {
            tOPrivateMemo = tOPrivateMemoService.getEntity(TOPrivateMemoEntity.class, tOPrivateMemo.getId());
            req.setAttribute("tOPrivateMemoPage", tOPrivateMemo);
        }
        return new ModelAndView("com/kingtake/office/memo/tOPrivateMemo-add");
    }

    /**
     * 备忘录编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOPrivateMemoEntity tOPrivateMemo, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOPrivateMemo.getId())) {
            tOPrivateMemo = tOPrivateMemoService.getEntity(TOPrivateMemoEntity.class, tOPrivateMemo.getId());
            req.setAttribute("tOPrivateMemoPage", tOPrivateMemo);
        }
        return new ModelAndView("com/kingtake/office/memo/tOPrivateMemo-update");
    }
    
    
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOPrivateMemoEntity toPrivateMemoEntity,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {    	        
        CriteriaQuery cq = new CriteriaQuery(TOPrivateMemoEntity.class, dataGrid);
                        
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, toPrivateMemoEntity, request.getParameterMap());
        cq.eq("createBy", ResourceUtil.getSessionUserName().getUserName());
        cq.add();
        
        List<TOPrivateMemoEntity> toMemoEntities = this.tOPrivateMemoService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME,"个人办公-个人备忘录");
        modelMap.put(NormalExcelConstants.CLASS,TOPrivateMemoEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("个人办公-个人备忘录", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
            "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST,toMemoEntities);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    

}
