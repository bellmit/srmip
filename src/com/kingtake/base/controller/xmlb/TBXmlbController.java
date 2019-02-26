package com.kingtake.base.controller.xmlb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.xmlb.TBJflxEntity;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.base.service.xmlx.TBXmlbServiceI;

/**
 * @Title: Controller
 * @Description: 项目类别信息表
 * @author onlineGenerator
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBXmlbController")
public class TBXmlbController extends BaseController {
    @Autowired
    private TBXmlbServiceI tBXmlbService;
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
     * 项目类别信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBXmlbList")
    public ModelAndView tBXmlbList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/xmlb/xmlb/tBXmlbList");
    }

    /**
     * 项目类别信息表列表 
     * 
     * @return
     */
    @RequestMapping(params = "getXmlbTree")
    @ResponseBody
    public JSONArray getXmlbTree(TBXmlbEntity xmlb, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBXmlbEntity.class);
        cq.isNull("parentType");
        cq.eq("validFlag", "1");
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBXmlbEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBXmlbEntity parent : parentList) {
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
    private JSONObject getTreeGrid(TBXmlbEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("xmlb", parent.getXmlb()==null?"":parent.getXmlb());
        json.put("zgdw", parent.getZgdw()==null?"":parent.getZgdw());
        json.put("jflx", parent.getJflxStr()==null?"":parent.getJflxStr());
        json.put("xmlx", parent.getXmlx()==null?"":parent.getXmlx());
        json.put("xmxz", parent.getXmxz()==null?"":parent.getXmxz());
        json.put("xmly", parent.getXmly()==null?"":parent.getXmly());
        json.put("wybh", parent.getWybh()==null?"":parent.getWybh());
        json.put("kjbmgz", parent.getKjbmgz()==null?"":parent.getKjbmgz());
        json.put("bz", parent.getBz()==null?"":parent.getBz());
        json.put("sortCode", parent.getSortCode()==null?"":parent.getSortCode());
        CriteriaQuery subCq = new CriteriaQuery(TBXmlbEntity.class);
        subCq.eq("parentType.id", parent.getId());
        subCq.eq("validFlag", "1");
        subCq.addOrder("sortCode", SortDirection.asc);
        subCq.add();
        List<TBXmlbEntity> subList = this.systemService.getListByCriteriaQuery(subCq, false);
        if (subList.size() > 0) {
            JSONArray array = new JSONArray();
            for (TBXmlbEntity sub : subList) {
                JSONObject subJson = getTreeGrid(sub);
                array.add(subJson);
            }
            json.put("children", array);
        }
        return json;
    }
    
    /**
     * 项目类别信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBXmlbEntity xmlb, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(xmlb.getId())) {
        	xmlb = tBXmlbService.getEntity(TBXmlbEntity.class, xmlb.getId());
            req.setAttribute("tBXmlb", xmlb);
        }
        return new ModelAndView("com/kingtake/base/xmlb/xmlb/tBXmlb-add");
    }
    
    /**
     * 添加项目类别信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBXmlbEntity xmlb, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类别信息表添加成功";
        try {
        	xmlb.setValidFlag("1");
        	tBXmlbService.save(xmlb);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类别信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 唯一编号验证
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "wybhCheck")
    @ResponseBody
    public AjaxJson wybhCheck(TBXmlbEntity xmlb, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        CriteriaQuery cq = new CriteriaQuery(TBXmlbEntity.class);
        cq.eq("wybh", xmlb.getWybh());
        cq.eq("validFlag", "1");
        if(xmlb.getId()!=null){
        	cq.notEq("id", xmlb.getId());
        }
        cq.add();
        int list = this.systemService.getCountByCriteriaQuery(cq);
        if(list>0){
        	message = "1";
        }else{
        	message = "2";
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "getXmlbList")
    @ResponseBody
    public List<ComboBox> getXmlbList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<ComboBox> comboboxList = new ArrayList<ComboBox>();
        CriteriaQuery cq = new CriteriaQuery(TBXmlbEntity.class);
        if (StringUtils.isNotEmpty(id)) {
            cq.notEq("id", id);
        }
        cq.addOrder("sortCode", SortDirection.asc);
        cq.eq("validFlag", "1");
        cq.add();
        List<TBXmlbEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
        
        for (TBXmlbEntity type : list) {
            ComboBox combo = new ComboBox();
            combo.setId(type.getId());
            combo.setText(type.getXmlb());
            comboboxList.add(combo);
        }
        return comboboxList;
    }
    
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBXmlbEntity xmlb, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(xmlb.getId())) {
        	xmlb = tBXmlbService.getEntity(TBXmlbEntity.class, xmlb.getId());
            req.setAttribute("tBXmlb", xmlb);
        }
        return new ModelAndView("com/kingtake/base/xmlb/xmlb/tBXmlb-update");
    }
    
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBXmlbEntity xmlb, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类别信息表更新成功";
        TBXmlbEntity t = tBXmlbService.get(TBXmlbEntity.class, xmlb.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(xmlb, t);
            tBXmlbService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类别信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "getFeeType")
    @ResponseBody
    public JSONArray getFeeType(HttpServletRequest req, HttpServletResponse response) {
    	JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBFundsPropertyEntity.class);
        cq.eq("validFlag", "1");
        cq.add();
        List<TBFundsPropertyEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBFundsPropertyEntity parent : parentList) {
    	  JSONObject json = new JSONObject();
          json.put("id", parent.getId());
          json.put("text", parent.getFundsName());
          json.put("code", parent.getFundsCode());
            jsonArray.add(json);
        }
        return jsonArray;
    }
    
    /**
     * 查询预算类型
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "getbudgetCategory")
    @ResponseBody
    public JSONArray getbudgetCategory(HttpServletRequest req, HttpServletResponse response) {
    	String sql = " select distinct CATEGORY_CODE as \"id\",CATEGORY_NAME as \"text\" from T_PM_BUDGET_CATEGORY ";
    	List<Map<String, Object>> list = this.systemService.findForJdbc(sql);
    	JSONArray jsonArray = new JSONArray();
    	for (Map parent : list) {
      	  JSONObject json = new JSONObject();
            json.put("id", parent.get("id"));
            json.put("text", parent.get("text"));
            jsonArray.add(json);
        }
       
        return jsonArray;
    }
    
    
    /**
     * 查询间接费计算方式
     * @param req
     * @param response
     * @return
     */
    /*@RequestMapping(params = "getIndirectFeeCalu")
    @ResponseBody
    public JSONArray getIndirectFeeCalu(HttpServletRequest req, HttpServletResponse response) {
    	String sql = " select distinct INDIRECT_FEE_CALU_ID as id,INDIRECT_FEE_CALU as text from T_PM_BUDGET_FUNDS_REL ";
    	List<Map<String, Object>> list = this.systemService.findForJdbc(sql);
    	JSONArray jsonArray = new JSONArray();
    	for (Map parent : list) {
      	  JSONObject json = new JSONObject();
            json.put("id", parent.get("ID"));
            json.put("text", parent.get("TEXT"));
            jsonArray.add(json);
        }
       
        return jsonArray;
    }*/
    
    @RequestMapping(params = "getJflx")
    @ResponseBody
    public JSONArray getJflx(HttpServletRequest req, HttpServletResponse response) {
    	JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBJflxEntity.class);
        cq.isNull("parentType");
        cq.eq("validFlag", "1");
        cq.addOrder("code", SortDirection.asc);
        cq.add();
        List<TBJflxEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBJflxEntity parent : parentList) {
            JSONObject pJson = getJflxTreeGrid(parent);
            jsonArray.add(pJson);
        }
        return jsonArray;
    }
    
    
    private JSONObject getJflxTreeGrid(TBJflxEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("text", parent.getJflxmc());
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
                JSONObject subJson = getJflxTreeGrid(sub);
                array.add(subJson);
            }
            json.put("children", array);
        }
        return json;
    }
    
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBXmlbEntity xmlb, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类别信息表删除成功";
        try {
            this.tBXmlbService.delXmlb(xmlb);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类别信息表删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "getXmlbAll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getXmlbAll(HttpServletRequest request, TBXmlbEntity xmlb,
            HttpServletResponse response) {
    	Object xmlbId = xmlb.getId();
    	String sql = "select * from t_b_xmlb where parent_id = '"+xmlbId+"'";
    	List<Map<String, Object>> list = systemService.findForJdbc(sql);
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(list.size()>0){
    		map.put("msg", "1");
    	}else{
            if (StringUtils.isNotEmpty(xmlb.getId())) {
            	xmlb = this.systemService.get(TBXmlbEntity.class, xmlb.getId());
            	if(xmlb == null){
            		//传入的是名称
            		String sql3 = " select * from t_b_xmlb where xmlb='"+xmlbId+"' ";
            		List<Map<String, Object>> list2 = systemService.findForJdbc(sql3);
            		if(list2.size() == 1){
            			Map data = list2.get(0);
            			map.put("zgdw", data.get("ZGDW")==null?"":data.get("ZGDW"));
                        map.put("jflx", data.get("JFLX")==null?"":data.get("JFLX"));
                        map.put("jflxStr", data.get("JFLX")==null?"":data.get("JFLX"));
                        map.put("xmlx", data.get("XMLX")==null?"":data.get("XMLX"));
                        map.put("xmxz", data.get("XMXZ")==null?"":data.get("XMXZ"));
                        map.put("xmly", data.get("XMLY")==null?"":data.get("XMLY"));
                        //map.put("xmml", data.get("PARENT_ID")==null?"":data.get("PARENT_ID"));
                        map.put("msg", "0");
                        
                        String sql4 = " select xmlb from t_b_xmlb where id='"+data.get("PARENT_ID")+"' ";
                        List<Map<String, Object>> list4 = systemService.findForJdbc(sql4);
                        map.put("xmml", list4.size() > 0 ? list4.get(0).get("XMLB") : "");
            		}
            	}else{
            		map.put("zgdw", xmlb.getZgdw()==null?"":xmlb.getZgdw());
                    map.put("jflx", xmlb.getJf().getId()==null?"":xmlb.getJf().getId());
                    map.put("jflxStr", xmlb.getJflxStr()==null?"":xmlb.getJflxStr());
                    map.put("xmlx", xmlb.getXmlx()==null?"":xmlb.getXmlx());
                    map.put("xmxz", xmlb.getXmxz()==null?"":xmlb.getXmxz());
                    map.put("xmly", xmlb.getXmly()==null?"":xmlb.getXmly());
                    map.put("xmml", xmlb.getParentType()==null?"":xmlb.getParentType().getXmlb());
                    map.put("msg", "0");
            	}
            	
                
            }
    	}
        return map;
    }
    
    
    @RequestMapping(params = "getProportion", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProportion(HttpServletRequest request, TBFundsPropertyEntity jflx,
            HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	if (StringUtils.isNotEmpty(jflx.getId())) {
            String sql = "select * from T_PM_BUDGET_FUNDS_REL where funds_type = '"+jflx.getId()+"'";
        	List<Map<String, Object>> list = systemService.findForJdbc(sql);
        	if(list.size() == 0){//传入的是名称，而不是ID
        		String sql2 = " select * from T_PM_BUDGET_FUNDS_REL where funds_type = "
        				+ "(select id from t_b_funds_property k where k.FUNDS_NAME='"+jflx.getId()+"')";
        		list = systemService.findForJdbc(sql2);
        	}
        	Map data = list.get(0);
    		map.put("msg", "0");
    		map.put("yslx", data.get("BUDGET_CATEGORY"));
    		map.put("jjfjsfs", data.get("INDIRECT_FEE_CALU"));
    		map.put("dxbl", data.get("UNIVERSITY_PROP"));
    		map.put("zrdwbl", data.get("UNIT_PROP"));
    		map.put("cydwbl", data.get("DEV_UNIT_PROP"));
    		map.put("xmzbl", data.get("PROJECTGROUP_PROP"));
    		map.put("msg", "0");
        }else{
        	map.put("msg", "1");
        }
        return map;
    }
}
