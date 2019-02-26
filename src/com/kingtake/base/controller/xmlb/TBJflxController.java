package com.kingtake.base.controller.xmlb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.entity.xmlb.TBJflxEntity;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.base.service.xmlx.TBJflxServiceI;
import com.kingtake.common.constant.ProjectConstant;

/**
 * @Title: Controller
 * @Description: 经费类型信息表
 * @author onlineGenerator
 * @date 2015-07-16 17:50:27
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJflxController")
public class TBJflxController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBJflxController.class);

    @Autowired
    private TBJflxServiceI tBJflxService;
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
     * 经费类型信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBJflxList")
    public ModelAndView tBJflxList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/xmlb/jflx/tBJflxList");
    }

    /**
     * 经费类型信息表列表 
     * 
     * @return
     */
    @RequestMapping(params = "getJflxTree")
    @ResponseBody
    public JSONArray getJflxTree(TBJflxEntity jflx, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBJflxEntity.class);
        cq.isNull("parentType");
        cq.eq("validFlag", "1");
        cq.addOrder("code", SortDirection.asc);
        cq.add();
        List<TBJflxEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBJflxEntity parent : parentList) {
            JSONObject pJson = getTreeGrid(parent);
            jsonArray.add(pJson);
        }
        return jsonArray;
    }
    
    /**
     * 获取树形列表
     * 
     * @param parent
     * @return
     */
    private JSONObject getTreeGrid(TBJflxEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("jflxmc", parent.getJflxmc());
        json.put("code", parent.getCode());
        CriteriaQuery subCq = new CriteriaQuery(TBJflxEntity.class);
        subCq.eq("parentType.id", parent.getId());
        subCq.eq("validFlag", "1");
        subCq.addOrder("code", SortDirection.asc);
        subCq.add();
        List<TBJflxEntity> subList = this.systemService.getListByCriteriaQuery(subCq, false);
        if (subList.size() > 0) {
            JSONArray array = new JSONArray();
            for (TBJflxEntity sub : subList) {
                JSONObject subJson = getTreeGrid(sub);
                array.add(subJson);
            }
            json.put("children", array);
        }
        return json;
    }
    
    /**
     * 经费类型信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBJflxEntity jflx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(jflx.getId())) {
        	jflx = tBJflxService.getEntity(TBJflxEntity.class, jflx.getId());
            req.setAttribute("tBJflx", jflx);
        }
        return new ModelAndView("com/kingtake/base/xmlb/jflx/tBJflx-add");
    }
    
    /**
     * 添加经费类型信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBJflxEntity jflx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "经费类型信息表添加成功";
        try {
        	jflx.setValidFlag("1");
        	tBJflxService.save(jflx);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "经费类型信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "getJflxList")
    @ResponseBody
    public List<ComboBox> getJflxList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<ComboBox> comboboxList = new ArrayList<ComboBox>();
        CriteriaQuery cq = new CriteriaQuery(TBJflxEntity.class);
        if (StringUtils.isNotEmpty(id)) {
            cq.notEq("id", id);
        }
        cq.addOrder("code", SortDirection.asc);
        cq.eq("validFlag", "1");
        cq.add();
        List<TBJflxEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
        
        for (TBJflxEntity type : list) {
            ComboBox combo = new ComboBox();
            combo.setId(type.getId());
            combo.setText(type.getJflxmc());
            comboboxList.add(combo);
        }
        return comboboxList;
    }
    
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBJflxEntity jflx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(jflx.getId())) {
        	jflx = tBJflxService.getEntity(TBJflxEntity.class, jflx.getId());
            req.setAttribute("tBJflx", jflx);
        }
        return new ModelAndView("com/kingtake/base/xmlb/jflx/tBJflx-update");
    }
    
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBJflxEntity jflx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "经费类型信息表更新成功";
        TBJflxEntity t = tBJflxService.get(TBJflxEntity.class, jflx.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(jflx, t);
            tBJflxService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "经费类型信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBJflxEntity jflx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "经费类型信息表删除成功";
        try {
            this.tBJflxService.delJflx(jflx);
        } catch (Exception e) {
            e.printStackTrace();
            message = "经费类型信息表删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
