package com.kingtake.common.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserContact;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.service.CommonUserServiceI;
import com.kingtake.project.controller.manage.ProjectMgrController;

/**
 * 选择人员
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/commonUserController")
public class CommonUserController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ProjectMgrController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private CommonUserServiceI commonUserService;

    /**
     * 跳转到界面
     * 
     * @return
     */
    @RequestMapping(params = "commonUser")
    public ModelAndView commonUser(HttpServletRequest request) {
        String userIds = request.getParameter("userIds");
        if (StringUtils.isNotEmpty(userIds)) {
            request.setAttribute("userIds", userIds);
        }
        String departIds = request.getParameter("departIds");
        if (StringUtils.isNotEmpty(departIds)) {
            request.setAttribute("departIds", departIds);
        }
        String needDepart = request.getParameter("needDepart");
        if (StringUtils.isNotEmpty(needDepart)) {
            request.setAttribute("needDepart", needDepart);
        }
        return new ModelAndView("com/kingtake/common/commonUser");
    }

    /**
     * 跳转到界面
     * 
     * @return
     */
    @RequestMapping(params = "commonUserMini")
    public ModelAndView commonUserMini(HttpServletRequest request) {
        String userIds = request.getParameter("userIds");
        if (StringUtils.isNotEmpty(userIds)) {
            request.setAttribute("userIds", userIds);
        }
        String departIds = request.getParameter("departIds");
        if (StringUtils.isNotEmpty(departIds)) {
            request.setAttribute("departIds", departIds);
        }
        String needDepart = request.getParameter("needDepart");
        if (StringUtils.isNotEmpty(needDepart)) {
            request.setAttribute("needDepart", needDepart);
        }
        return new ModelAndView("com/kingtake/common/commonUserMini");
    }

    /**
     * 根据部门获取人员
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getUserByDepart")
    @ResponseBody
    public JSONObject getUserByDepart(HttpServletRequest request, HttpServletResponse response) {
        String departId = request.getParameter("departId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departId", departId);
        String ids = request.getParameter("ids");
        String sql = "select  u.id, bu.username, bu.realname, bu.status,d.id as departId,d.departname"
                + " from t_s_user u join t_s_base_user bu on u.id=bu.id "
                + "join t_s_user_org o on u.id = o.user_id join t_s_depart "
                + "d on o.org_id = d.id where d.id = :departId and bu.status=1";
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            paramMap.put("userIds", Arrays.asList(idArr));
            sql = sql + "and u.id not in (:userIds)";
        }
        sql = sql + " order by u.sort";
        List<Map<String, Object>> results = systemService.findByNamedJdbc(sql, paramMap);
        List<Map<String, Object>> userMapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : results) {
            Map<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("id", map.get("ID"));
            tmp.put("userName", map.get("USERNAME"));
            tmp.put("realName", map.get("REALNAME"));
            tmp.put("status", map.get("STATUS"));
            tmp.put("departId", map.get("DEPARTID"));
            tmp.put("departName", map.get("DEPARTNAME"));
            userMapList.add(tmp);
        }
        JSONObject json = new JSONObject();
        json.put("rows", userMapList);
        json.put("total", results.size());
        return json;
    }

    /**
     * 初始化显示已选人员
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getInitUserList")
    @ResponseBody
    public JSONObject getInitUserList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String userIds = request.getParameter("userIds");
        String departIds = request.getParameter("departIds");
        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
        if (StringUtils.isNotEmpty(userIds)) {
            if (StringUtils.isNotEmpty(departIds)) {
                List<Map<String, Object>> userListMap = new ArrayList<Map<String, Object>>();
                String[] departIdArr = departIds.split(",");
                String[] userIdArr = userIds.split(",");
                String sql = "select u.id,u.username,u.realname,u.status,d.id as departId,d.departname"
                        + " from t_s_base_user u join t_s_user_org uo on uo.user_id = u.id"
                        + " join t_s_depart d on uo.org_id = d.id where u.id = ? and u.status=1 and d.id = ?";
                if (userIdArr.length > 0 && departIdArr.length > 0) {
                    for (int i = 0; i < userIdArr.length; i++) {
                        List<Map<String, Object>> tmpMap = this.systemService.findForJdbc(sql, new Object[] {
                                userIdArr[i], departIdArr[i] });
                        userListMap.addAll(tmpMap);
                    }
                }
                List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> map : userListMap) {
                    Map<String, Object> tmp = new HashMap<String, Object>();
                    tmp.put("id", map.get("ID"));
                    tmp.put("userName", map.get("USERNAME"));
                    tmp.put("realName", map.get("REALNAME"));
                    tmp.put("status", map.get("STATUS"));
                    tmp.put("departId", map.get("DEPARTID"));
                    tmp.put("departName", map.get("DEPARTNAME"));
                    resultMapList.add(tmp);
                }
                json.put("rows", resultMapList);
                json.put("total", resultMapList.size());
                return json;
            } else {
                CriteriaQuery cq = new CriteriaQuery(TSBaseUser.class);
                cq.in("id", userIds.split(","));
                cq.add();
                userList = this.systemService.getListByCriteriaQuery(cq, false);
            }
        }
        json.put("rows", userList);
        json.put("total", userList.size());
        return json;
    }
    
    @RequestMapping(params = "comboxUsers")
    @ResponseBody
    public void comboxUsers(HttpServletRequest request, HttpServletResponse response) {
    	List<TSUser> users = systemService.getList(TSUser.class);
    	TagUtil.response(response, TagUtil.listtojson(new String[]{"id", "userName"}, users));
    }

    /**
     * 获取常用联系人
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getContactList")
    @ResponseBody
    public List<ComboTree> getContactList(HttpServletRequest request, HttpServletResponse response) {
        int limit = 8;
        String needMore = request.getParameter("needMore");
        String realName = request.getParameter("keyWord");
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "select c.id, c.contact_id,u.username,u.realname,c.depart_id,d.departname "
                + "from t_s_user_contact c join t_s_base_user u on c.contact_id=u.id join t_s_depart d "
                + "on c.depart_id=d.id where c.user_id=?";
        if (StringUtils.isNotEmpty(realName)) {
            sql = sql + " and u.realname like '%" + realName + "%'";
        }
        sql = sql + "  order by c.count desc ";
        String countSql = "select count(1) count from (" + sql + ")";
        Map<String, Object> countMap = this.systemService.findOneForJdbc(countSql, user.getId());
        int count = ((BigDecimal) countMap.get("count")).intValue();
        String text = "常用联系人";
        if (count > limit) {
            if (!"true".equals(needMore)) {
                text = "常用联系人&nbsp;&nbsp;<a onclick=\"showAll();\"><font color=\"red\" size=\"5\" style=\"text-decoration: underline;cursor: pointer;\">显示更多</font></a>";
                sql = "select r.* from (" + sql + ") r where rownum<=" + limit;
            }
        }
        ComboTree root = new ComboTree();
        root.setText(text);
        root.setState("open");
        List<Map<String, Object>> contactList = this.systemService.findForJdbc(sql, user.getId());
        List<ComboTree> children = new ArrayList<ComboTree>();
        for (Map<String, Object> map : contactList) {
            ComboTree node = new ComboTree();
            node.setId((String) map.get("id"));
            node.setText((String) map.get("realname"));
            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put("userId", (String) map.get("contact_id"));
            attrs.put("userName", (String) map.get("username"));
            attrs.put("deptId", (String) map.get("depart_id"));
            attrs.put("deptName", (String) map.get("departname"));
            node.setAttributes(attrs);
            children.add(node);
        }
        root.setChildren(children);
        treeList.add(root);
        return treeList;
    }

    /**
     * 保存常用联系人
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "saveUserContact")
    public void saveUserContact(HttpServletRequest request, HttpServletResponse response) {
        String contactIds = request.getParameter("contactIds");
        String departIds = request.getParameter("departIds");
        TSUser user = ResourceUtil.getSessionUserName();
        String userId = user.getId();
        commonUserService.saveUserContact(userId, contactIds, departIds);
    }

    /**
     * 删除常用联系人
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "deleteUserContact")
    @ResponseBody
    public AjaxJson deleteUserContact(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try{
        String id = request.getParameter("id");
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtils.isNotEmpty(id)) {//删除一条记录
            this.systemService.deleteEntityById(TSUserContact.class, id);
        } else {//删除所有联系人
            List<TSUserContact> list = this.systemService.findByProperty(TSUserContact.class, "userId", user.getId());
            if(list.size()>0){
              this.systemService.deleteAllEntitie(list);
            }
        }
            json.setMsg("删除联系人成功！");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("删除联系人失败，原因：" + e.getMessage());
        }
        return json;
    }

    /**
     * 查询用户
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "userDatagrid")
    public void datagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int start = (page - 1) * rows + 1;
        int end = (page) * rows;
        Map<String, Object> param = new HashMap<String, Object>();
        String realName = request.getParameter("realName");
        if (StringUtils.isNotEmpty(realName)) {
            param.put("realName", realName);
        }
        String userName = request.getParameter("userName");
        if (StringUtils.isNotEmpty(userName)) {
            param.put("userName", userName);
        }
        List<Map<String, Object>> list = this.commonUserService.getUserList(param, start, end);
        int count = commonUserService.getUserCount(param);
        dataGrid.setResults(list);
        dataGrid.setTotal(count);
        TagUtil.datagrid(response, dataGrid);
    }

}
