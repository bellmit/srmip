package com.kingtake.zscq.controller.zljl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.zscq.entity.zljl.TZZljlEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.zljl.TZZljlServiceI;

/**
 * @Title: Controller
 * @Description: 专利奖励
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZZljlController")
public class TZZljlController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TZZljlController.class);

    @Autowired
    private TZZljlServiceI tZZljlService;
    @Autowired
    private SystemService systemService;
    private String message;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 代理机构信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "zljlListForDepart")
    public ModelAndView zljlListForDepart(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/zljl/zljlListForDepart");
    }

    /**
     * 代理机构信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goZljlList")
    public ModelAndView goZljlList(HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        String zlsqId = request.getParameter("zlsqId");
        if (StringUtils.isNotEmpty(zlsqId)) {
            request.setAttribute("zlsqId", zlsqId);
        }
        return new ModelAndView("com/kingtake/zscq/zljl/zljlList");
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
    public void datagrid(TZZljlEntity tZZljl, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZZljlEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZZljl, request.getParameterMap());
        cq.add();
        this.tZZljlService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForZl")
    public void datagridForZl(HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String fwrBegin = request.getParameter("fwr_begin");
        String fwrEnd = request.getParameter("fwr_end");
        if (StringUtils.isEmpty(fwrBegin) || StringUtils.isEmpty(fwrEnd)) {
            dataGrid.setResults(new ArrayList());
            dataGrid.setTotal(0);
            TagUtil.datagrid(response, dataGrid);
            return;
        }
        String querySql = "select z.id,z.gdh,z.lx,z.mc,z.fmr,z.lxr,z.lxrdh,z.zlzt "
                + "from t_z_zlsq z join (select dj.zlsq_id  from t_z_zsdj dj where dj.shggr >= to_date(?, 'yyyy-mm-dd')"
                + "  and dj.shggr <= to_date(?, 'yyyy-mm-dd') union select sq.zlsq_id from t_z_sq sq "
                + "where sq.fwr >= to_date(?, 'yyyy-mm-dd') and sq.fwr <= to_date(?, 'yyyy-mm-dd')) t on z.id=t.zlsq_id ";
        List<Map<String, Object>> list = this.systemService.findForJdbc(querySql, new Object[] { fwrBegin, fwrEnd,
                fwrBegin, fwrEnd });
        dataGrid.setResults(list);
        dataGrid.setTotal(list.size());
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除代理机构信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZZljlEntity zljl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        zljl = systemService.getEntity(TZZljlEntity.class, zljl.getId());
        message = "专利奖励删除成功";
        try {
            tZZljlService.delete(zljl);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利奖励删除失败";
            j.setSuccess(false);
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
    public AjaxJson doUpdate(TZZljlEntity tZZljl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专利奖励更新成功";
        try {
            if (StringUtils.isEmpty(tZZljl.getId())) {
                this.tZZljlService.save(tZZljl);
            } else {
                TZZljlEntity t = tZZljlService.get(TZZljlEntity.class, tZZljl.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZZljl, t);
                tZZljlService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利奖励更新失败";
            j.setSuccess(false);
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
    public ModelAndView goUpdate(TZZljlEntity tZZljl, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZZljl.getId())) {
            tZZljl = tZZljlService.getEntity(TZZljlEntity.class, tZZljl.getId());
        }
        req.setAttribute("tZZljlPage", tZZljl);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zljl/zljl-update");
    }

    /**
     * 代理机构信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddZljl")
    public ModelAndView goAddZljl(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        if(StringUtils.isNotEmpty(ids)){
            request.setAttribute("ids", ids);
        }
        TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
        codeTypeEntity.setCode("ZLLX");
        codeTypeEntity.setCodeType("1");
        List<TBCodeDetailEntity> detailList = tBCodeTypeService.getCodeByCodeType(codeTypeEntity);
        request.setAttribute("detailList", detailList);
        return new ModelAndView("com/kingtake/zscq/zljl/zljl-add");
    }

    /**
     * 更新
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TZZljlEntity tZZljl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专利奖励生成成功";
        try {
            Map<String,BigDecimal> jeMap = new HashMap<String,BigDecimal>();
            TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
            codeTypeEntity.setCode("ZLLX");
            codeTypeEntity.setCodeType("1");
            List<TBCodeDetailEntity> detailList = tBCodeTypeService.getCodeByCodeType(codeTypeEntity);
            for(TBCodeDetailEntity detail:detailList){
                String code = detail.getCode();
                BigDecimal bd = null;
                String je = request.getParameter("code_"+code);
                if (StringUtils.isNotEmpty(je)) {
                    bd = new BigDecimal(je);
                    jeMap.put(code, bd);
                }
            }  
            String ids = request.getParameter("ids");
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                TZZlsqEntity zlsq = this.systemService.get(TZZlsqEntity.class, id);
                TZZljlEntity tmp = new TZZljlEntity();
                tmp.setZlsqId(id);
                BigDecimal je = jeMap.get(zlsq.getLx());
                if (je == null) {
                    je = BigDecimal.ZERO;
                }
                tmp.setJlje(je);
                tmp.setJlsj(new Date());
                tmp.setQrzt("1");//已生成
                this.systemService.save(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利奖励生成失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUploadLbd")
    public ModelAndView goUploadLbd(String id, HttpServletRequest req) {
        TZZljlEntity zljl = this.systemService.get(TZZljlEntity.class, id);
        if (StringUtils.isEmpty(zljl.getFjbm())) {
            zljl.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(zljl.getFjbm(), "");
            zljl.setAttachments(fileList);
        }
        req.setAttribute("zljl", zljl);
        return new ModelAndView("com/kingtake/zscq/zljl/zljl-upload");
    }

    /**
     * 上传领报单
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUploadLbd")
    @ResponseBody
    public AjaxJson doUploadLbd(TZZljlEntity tZZljl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "上传领报单成功";
        try {
            TZZljlEntity t = tZZljlService.get(TZZljlEntity.class, tZZljl.getId());
            t.setFjbm(tZZljl.getFjbm());
            t.setQrzt("2");//已上传领报单
            tZZljlService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "上传领报单失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 填写修改意见
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TZZljlEntity apply = this.systemService.get(TZZljlEntity.class, id);
            req.setAttribute("msgText", apply.getMsgText());
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }

    /**
     * 确认
     * 
     * @return
     */
    @RequestMapping(params = "doConfirm")
    @ResponseBody
    public AjaxJson doConfirm(TZZljlEntity zljl, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        AjaxJson j = new AjaxJson();
        String qrzt = req.getParameter("qrzt");
        String xgyj = req.getParameter("xgyj");
        try {
            if (StringUtil.isNotEmpty(zljl.getId())) {
                zljl = tZZljlService.getEntity(TZZljlEntity.class, zljl.getId());
                if ("1".equals(qrzt)) {
                    zljl.setQrzt("4");//确认
                } else {
                    zljl.setQrzt("3");//返回修改
                }
                zljl.setMsgText(xgyj);
                zljl.setCheckUserId(user.getId());
                zljl.setCheckUserName(user.getRealName());
                this.systemService.updateEntitie(zljl);
                j.setMsg("确认成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("确认失败！");
        }
        return j;
    }

    /**
     * 跳转到确认界面
     * 
     * @return
     */
    @RequestMapping(params = "goConfirm")
    public ModelAndView goConfirm(TZZljlEntity zljl, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(zljl.getId())) {
            zljl = tZZljlService.getEntity(TZZljlEntity.class, zljl.getId());
            req.setAttribute("msgText", zljl.getMsgText());
        }
        return new ModelAndView("com/kingtake/zscq/sctzs/confirmPage");
    }
}
