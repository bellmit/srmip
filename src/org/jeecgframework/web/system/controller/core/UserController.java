package org.jeecgframework.web.system.controller.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.PinyinUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.RoletoJson;
import org.jeecgframework.core.util.SetListSort;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.base.service.rtx.TBSyncUserDeptTempServiceI;
import com.kingtake.common.service.CommonUserServiceI;

/**
 * @ClassName: UserController
 * @Description: TODO(用户管理处理类)
 * @author 张代浩
 */
@Scope("prototype")
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
    /**
     * Logger for this class
     */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(UserController.class);

    private UserService userService;
    private SystemService systemService;
    @Autowired
    private ActivitiService activitiService;
    private String message = null;

    @Autowired
    private TBCodeTypeServiceI tbCodeTypeService;

    @Autowired
    private CommonUserServiceI commonUserService;

    @Autowired
    private TBSyncUserDeptTempServiceI tBSyncUserDeptTempService;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 菜单列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "menu")
    public void menu(HttpServletRequest request, HttpServletResponse response) {
        SetListSort sort = new SetListSort();
        TSUser u = ResourceUtil.getSessionUserName();
        // 登陆者的权限
        Set<TSFunction> loginActionlist = new HashSet();// 已有权限菜单
        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", u.getId());
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id",
                    role.getId());
            if (roleFunctionList.size() > 0) {
                for (TSRoleFunction roleFunction : roleFunctionList) {
                    TSFunction function = roleFunction.getTSFunction();
                    loginActionlist.add(function);
                }
            }
        }
        List<TSFunction> bigActionlist = new ArrayList();// 一级权限菜单
        List<TSFunction> smailActionlist = new ArrayList();// 二级权限菜单
        if (loginActionlist.size() > 0) {
            for (TSFunction function : loginActionlist) {
                if (function.getFunctionLevel() == 0) {
                    bigActionlist.add(function);
                } else if (function.getFunctionLevel() == 1) {
                    smailActionlist.add(function);
                }
            }
        }
        // 菜单栏排序
        Collections.sort(bigActionlist, sort);
        Collections.sort(smailActionlist, sort);
        String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
        // request.setAttribute("loginMenu",logString);
        try {
            response.getWriter().write(logString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    @RequestMapping(params = "chooseUserList")
    public String chooseUserList(HttpServletRequest request) {
        // 给部门查询条件中的下拉框准备数据
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        return "system/user/chooseUserList";
    }

    /**
     * 用户列表页面跳转[跳转到标签和手工结合的html页面]
     * 
     * @return
     */
    @RequestMapping(params = "userDemo")
    public String userDemo(HttpServletRequest request) {
        // 给部门查询条件中的下拉框准备数据
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        return "system/user/userList2";
    }

    /**
     * 用户列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "user")
    public String user(HttpServletRequest request) {
        // 给部门查询条件中的下拉框准备数据
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        return "system/user/userList";
    }

    /**
     * 用户信息
     * 
     * @return
     */
    @RequestMapping(params = "userinfo")
    public String userinfo(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("user", user);
        return "system/user/userinfo";
    }

    /**
     * 修改密码
     * 
     * @return
     */
    @RequestMapping(params = "changepassword")
    public String changepassword(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("user", user);
        return "system/user/changepassword";
    }

    /**
     * 修改密码
     * 
     * @return
     */
    @RequestMapping(params = "savenewpwd")
    @ResponseBody
    public AjaxJson savenewpwd(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        String password = oConvertUtils.getString(request.getParameter("password"));
        String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
        String pString = PasswordUtil.encrypt(password, user.getUserName(), PasswordUtil.getStaticSalt());
        if (!pString.equals(user.getPassword())) {
            j.setMsg("原密码不正确");
            j.setSuccess(false);
        } else {
            try {
                user.setPassword(PasswordUtil.encrypt(newpassword, user.getUserName(), PasswordUtil.getStaticSalt()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            systemService.updateEntitie(user);
            this.tBSyncUserDeptTempService.addTempUpdateUser(user);//修改密码加一条记录到中间表
            j.setMsg("修改成功");

        }
        return j;
    }

    /**
     * 
     * 修改用户密码
     * 
     * @author Chj
     */

    @RequestMapping(params = "changepasswordforuser")
    public ModelAndView changepasswordforuser(TSUser user, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());
            req.setAttribute("user", user);
            idandname(req, user);
            System.out.println(user.getPassword() + "-----" + user.getRealName());
        }
        return new ModelAndView("system/user/adminchangepwd");
    }

    @RequestMapping(params = "savenewpwdforuser")
    @ResponseBody
    public AjaxJson savenewpwdforuser(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = oConvertUtils.getString(req.getParameter("id"));
        String password = oConvertUtils.getString(req.getParameter("password"));
        if (StringUtil.isNotEmpty(id)) {
            TSUser users = systemService.getEntity(TSUser.class, id);
            System.out.println(users.getUserName());
            users.setPassword(PasswordUtil.encrypt(password, users.getUserName(), PasswordUtil.getStaticSalt()));
            users.setStatus(Globals.User_Normal);
            users.setActivitiSync(users.getActivitiSync());
            systemService.updateEntitie(users);
            message = "用户: " + users.getUserName() + "密码重置成功";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            this.tBSyncUserDeptTempService.addTempUpdateUser(users);//修改密码加一条记录到中间表
        }

        j.setMsg(message);

        return j;
    }

    /**
     * 锁定账户
     * 
     * 
     * @author Chj
     */
    @RequestMapping(params = "lock")
    @ResponseBody
    public AjaxJson lock(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();

        TSUser user = systemService.getEntity(TSUser.class, id);
        if ("admin".equals(user.getUserName())) {
            message = "超级管理员[admin]不可锁定";
            j.setMsg(message);
            return j;
        }
        if (user.getStatus() != Globals.User_Forbidden) {
            user.setStatus(Globals.User_Forbidden);
            userService.updateEntitie(user);
            message = "用户：" + user.getUserName() + "锁定成功";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

        } else {
            message = "锁定账户失败";
        }

        j.setMsg(message);
        return j;
    }

    /**
     * 得到角色列表
     * 
     * @return
     */
    @RequestMapping(params = "role")
    @ResponseBody
    public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
        String id = request.getParameter("id");
        List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
        List<TSRole> roles = new ArrayList();
        if (StringUtil.isNotEmpty(id)) {
            List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", id);
            if (roleUser.size() > 0) {
                for (TSRoleUser ru : roleUser) {
                    roles.add(ru.getTSRole());
                }
            }
        }
        List<TSRole> roleList = systemService.getList(TSRole.class);
        comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);
        return comboBoxs;
    }

    /**
     * 得到部门列表
     * 
     * @return
     */
    @RequestMapping(params = "depart")
    @ResponseBody
    public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
        String id = request.getParameter("id");
        List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
        List<TSDepart> departs = new ArrayList();
        if (StringUtil.isNotEmpty(id)) {
            TSUser user = systemService.get(TSUser.class, id);
            // if (user.getTSDepart() != null) {
            // TSDepart depart = systemService.get(TSDepart.class,
            // user.getTSDepart().getId());
            // departs.add(depart);
            // }
            // todo zhanggm 获取指定用户的组织机构列表
            List<TSDepart[]> resultList = systemService.findHql(
                    "from TSDepart d,TSUserOrg uo where d.id=uo.orgId and uo.id=?", id);
            for (TSDepart[] departArr : resultList) {
                departs.add(departArr[0]);
            }
        }
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
        return comboBoxs;
    }

    /**
     * easyuiAJAX用户列表请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden };
        cq.in("status", userstate);

        // update-start--Author:zhangguoming Date:20140827 for：添加 组织机构 查询条件
        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        // 获取 当前组织机构的用户信息
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }
        // update-end--Author:zhangguoming Date:20140827 for：添加 组织机构 查询条件

        // update-start--Author:lxp Date:20150626 for：添加 出生日期查询条件
        String ctBegin = request.getParameter("birthday_begin");
        String ctEnd = request.getParameter("birthday_end");
        if (StringUtil.isNotEmpty(ctBegin)) {
            try {
                cq.ge("birthday", new SimpleDateFormat("yyyy-MM-dd").parse(ctBegin));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cq.add();
        }
        if (StringUtil.isNotEmpty(ctEnd)) {
            try {
                cq.le("birthday", new SimpleDateFormat("yyyy-MM-dd").parse(ctEnd));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cq.add();
        }
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        // update-start--Author:gaofeng Date:20140822 for：添加用户的角色展示
        List<TSUser> cfeList = new ArrayList<TSUser>();
        for (Object o : dataGrid.getResults()) {
            if (o instanceof TSUser) {
                TSUser cfe = (TSUser) o;
                if (cfe.getId() != null && !"".equals(cfe.getId())) {
                    List<TSRoleUser> roleUser = systemService
                            .findByProperty(TSRoleUser.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        String roleName = "";
                        for (TSRoleUser ru : roleUser) {
                            roleName += ru.getTSRole().getRoleName() + ",";
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setUserKey(roleName);
                    }
                }
                cfeList.add(cfe);
            }
        }
        // update-end--Author:gaofeng Date:20140822 for：添加用户的角色展示
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 用户信息录入和更新
     * 
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSUser user, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        if ("admin".equals(user.getUserName())) {
            message = "超级管理员[admin]不可删除";
            j.setMsg(message);
            return j;
        }
        user = systemService.getEntity(TSUser.class, user.getId());
        List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        if (!user.getStatus().equals(Globals.User_ADMIN)) {
            tBSyncUserDeptTempService.addTempDeleteUser(user);//新增一条记录到同步RTX中间表
            if (roleUser.size() > 0) {
                // 删除用户时先删除用户和角色关系表
                delRoleUser(user);
                // update-start--Author:zhangguoming Date:20140825 for：添加业务逻辑
                systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据
                // update-end--Author:zhangguoming Date:20140825 for：添加业务逻辑
                userService.delete(user);
                message = "用户：" + user.getUserName() + "删除成功";
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            } else {
                userService.delete(user);
                message = "用户：" + user.getUserName() + "删除成功";
            }
        } else {
            message = "超级管理员不可删除";
        }
        j.setMsg(message);
        return j;
    }

    public void delRoleUser(TSUser user) {
        // 同步删除用户角色关联表
        List<TSRoleUser> roleUserList = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        if (roleUserList.size() >= 1) {
            for (TSRoleUser tRoleUser : roleUserList) {
                systemService.delete(tRoleUser);
            }
        }
    }

    /**
     * 检查用户名
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "checkUser")
    @ResponseBody
    public ValidForm checkUser(HttpServletRequest request) {
        ValidForm v = new ValidForm();
        String userName = oConvertUtils.getString(request.getParameter("param"));
        String code = oConvertUtils.getString(request.getParameter("code"));
        List<TSUser> roles = systemService.findByProperty(TSUser.class, "userName", userName);
        if (roles.size() > 0 && !code.equals(userName)) {
            v.setInfo("用户名已存在");
            v.setStatus("n");
        }
        return v;
    }

    /**
     * 用户录入
     * 
     * @param user
     * @param req
     * @return
     */

    @RequestMapping(params = "saveUser")
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, TSUser user) {
        AjaxJson j = new AjaxJson();
        // 得到用户的角色
        String roleid = oConvertUtils.getString(req.getParameter("roleid"));
        String password = oConvertUtils.getString(req.getParameter("password"));
        if (StringUtil.isNotEmpty(user.getId())) {
            TSUser users = systemService.getEntity(TSUser.class, user.getId());
            systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId());
            saveUserOrgList(req, user);
            users.setEmail(user.getEmail());
            users.setOfficePhone(user.getOfficePhone());
            users.setMobilePhone(user.getMobilePhone());
            users.setRealName(user.getRealName());
            users.setStatus(Globals.User_Normal);
            users.setActivitiSync(user.getActivitiSync());
            users.setNamePinyin(user.getNamePinyin());
            users.setNameUsed(user.getNameUsed());
            users.setSex(user.getSex());
            users.setBirthday(user.getBirthday());
            users.setIdType(user.getIdType());
            users.setIdNo(user.getIdNo());
            users.setPoliticalStatus(user.getPoliticalStatus());
            users.setDuty(user.getDuty());
            users.setNativePlace(user.getNativePlace());
            users.setNation(user.getNation());
            users.setSoldierFlag(user.getSoldierFlag());
            users.setRank(user.getRank());
            users.setSortCode(user.getSortCode());
            users.setAuthorizedFlag(user.getAuthorizedFlag());
            users.setLeaderFlag(user.getLeaderFlag());
            users.setOfficerNum(user.getOfficerNum());
            users.setIdNum(user.getIdNum());
            users.setAliasname(user.getAliasname());
            systemService.updateEntitie(users);
            List<TSRoleUser> ru = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            systemService.deleteAllEntitie(ru);
            message = "用户: " + users.getUserName() + "更新成功";
            if (StringUtil.isNotEmpty(roleid)) {
                saveRoleUser(users, roleid);
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

        } else {
            TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName", user.getUserName());
            if (users != null) {
                message = "用户: " + users.getUserName() + "已经存在";
            } else {
                user.setPassword(PasswordUtil.encrypt(password, user.getUserName(), PasswordUtil.getStaticSalt()));
                // if (user.getTSDepart().equals("")) {
                // user.setTSDepart(null);
                // }
                user.setStatus(Globals.User_Normal);
                systemService.save(user);
                // todo zhanggm 保存多个组织机构
                saveUserOrgList(req, user);
                message = "用户: " + user.getUserName() + "添加成功";
                if (StringUtil.isNotEmpty(roleid)) {
                    saveRoleUser(user, roleid);
                }
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }

        }

        if (j.isSuccess()) {
            tBSyncUserDeptTempService.addTempUpdateUser(user);//新增一条记录到同步RTX中间表
        }
        j.setMsg(message);

        return j;
    }

    // update-start--Author:zhangguoming Date:20140825 for：添加新的业务逻辑方法
    /**
     * 保存 用户-组织机构 关系信息
     * 
     * @param request
     *            request
     * @param user
     *            user
     */
    private void saveUserOrgList(HttpServletRequest request, TSUser user) {
        String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

        List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
        List<String> orgIdList = extractIdListByComma(orgIds);
        for (String orgId : orgIdList) {
            TSDepart depart = new TSDepart();
            depart.setId(orgId);

            TSUserOrg userOrg = new TSUserOrg();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
        }
        if (!userOrgList.isEmpty()) {
            systemService.batchSave(userOrgList);
            commonUserService.clearUserContact(user.getId());//单位发生变化，清理常用联系人
        }
    }

    // update-end--Author:zhangguoming Date:20140825 for：添加新的业务逻辑方法

    protected void saveRoleUser(TSUser user, String roleidstr) {
        String[] roleids = roleidstr.split(",");
        for (int i = 0; i < roleids.length; i++) {
            TSRoleUser rUser = new TSRoleUser();
            TSRole role = systemService.getEntity(TSRole.class, roleids[i]);
            rUser.setTSRole(role);
            rUser.setTSUser(user);
            systemService.save(rUser);

        }
    }

    /**
     * 用户选择角色跳转页面
     * 
     * @return
     */
    @RequestMapping(params = "roles")
    public String roles() {
        return "system/user/users";
    }

    /**
     * 角色显示列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridRole")
    public void datagridRole(TSRole tsRole, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsRole);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyuiAJAX请求数据： 用户选择角色列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(TSUser user, HttpServletRequest req) {
        List<TSDepart> departList = new ArrayList<TSDepart>();
        String departid = oConvertUtils.getString(req.getParameter("departid"));
        if (!StringUtil.isEmpty(departid)) {
            departList.add((TSDepart) systemService.getEntity(TSDepart.class, departid));
        } else {
            departList.addAll((List) systemService.getList(TSDepart.class));
        }
        req.setAttribute("departList", departList);
        // update-start--Author:zhangguoming Date:20140825
        // for：往request作用域中添加数据：组装页面中组织机构combobox多选框的数据
        List<String> orgIdList = new ArrayList<String>();
        if (StringUtil.isNotEmpty(user.getId())) {
            user = systemService.getEntity(TSUser.class, user.getId());

            req.setAttribute("user", user);
            idandname(req, user);

            orgIdList = systemService.findHql(
                    "select d.id from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?",
                    new String[] { user.getId() });
        }
        String orgIdStr = "";
        for (String org : orgIdList) {
            orgIdStr = orgIdStr + org + ",";
        }
        req.setAttribute("orgIds", orgIdStr);

        // 获得用户的角色
        String roleId = req.getParameter("roleId");
        if (StringUtil.isNotEmpty(roleId)) {
            TSRole role = systemService.get(TSRole.class, roleId);
            req.setAttribute("id", roleId);
            req.setAttribute("roleName", role.getRoleName());
        }

        return new ModelAndView("system/user/user");
    }

    // update-start--Author:zhangguoming Date:20140825 for：添加新的业务逻辑方法
    /**
     * 用户的登录后的组织机构选择页面
     * 
     * @param request
     *            request
     * @return 用户选择组织机构页面
     */
    @RequestMapping(params = "userOrgSelect")
    public ModelAndView userOrgSelect(HttpServletRequest request) {
        List<TSDepart> orgList = new ArrayList<TSDepart>();
        String userId = oConvertUtils.getString(request.getParameter("userId"));

        List<Object[]> orgArrList = systemService.findHql(
                "from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[] { userId });
        for (Object[] departs : orgArrList) {
            orgList.add((TSDepart) departs[0]);
        }
        request.setAttribute("orgList", orgList);

        TSUser user = systemService.getEntity(TSUser.class, userId);
        request.setAttribute("user", user);

        return new ModelAndView("system/user/userOrgSelect");
    }

    // update-end--Author:zhangguoming Date:20140825 for：添加新的业务逻辑方法

    public void idandname(HttpServletRequest req, TSUser user) {
        List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        String roleId = "";
        String roleName = "";
        if (roleUsers.size() > 0) {
            for (TSRoleUser tRoleUser : roleUsers) {
                roleId += tRoleUser.getTSRole().getId() + ",";
                roleName += tRoleUser.getTSRole().getRoleName() + ",";
            }
        }
        req.setAttribute("id", roleId);
        req.setAttribute("roleName", roleName);

    }

    /**
     * 根据部门和角色选择用户跳转页面
     */
    @RequestMapping(params = "choose")
    public String choose(HttpServletRequest request) {
        List<TSRole> roles = systemService.loadAll(TSRole.class);
        request.setAttribute("roleList", roles);
        return "system/membership/checkuser";
    }

    /**
     * 部门和角色选择用户的panel跳转页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "chooseUser")
    public String chooseUser(HttpServletRequest request) {
        String departid = request.getParameter("departid");
        String roleid = request.getParameter("roleid");
        request.setAttribute("roleid", roleid);
        request.setAttribute("departid", departid);
        return "system/membership/userlist";
    }

    /**
     * 部门和角色选择用户的用户显示列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridUser")
    public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String departid = request.getParameter("departid");
        String roleid = request.getParameter("roleid");
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        if (departid.length() > 0) {
            cq.eq("TDepart.departid", oConvertUtils.getInt(departid, 0));
            cq.add();
        }
        String userid = "";
        if (roleid.length() > 0) {
            List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TRole.roleid",
                    oConvertUtils.getInt(roleid, 0));
            if (roleUsers.size() > 0) {
                for (TSRoleUser tRoleUser : roleUsers) {
                    userid += tRoleUser.getTSUser().getId() + ",";
                }
            }
            cq.in("userid", oConvertUtils.getInts(userid.split(",")));
            cq.add();
        }
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 根据部门和角色选择用户跳转页面
     */
    @RequestMapping(params = "roleDepart")
    public String roleDepart(HttpServletRequest request) {
        List<TSRole> roles = systemService.loadAll(TSRole.class);
        request.setAttribute("roleList", roles);
        return "system/membership/roledepart";
    }

    /**
     * 部门和角色选择用户的panel跳转页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "chooseDepart")
    public ModelAndView chooseDepart(HttpServletRequest request) {
        String nodeid = request.getParameter("nodeid");
        ModelAndView modelAndView = null;
        if (nodeid.equals("role")) {
            modelAndView = new ModelAndView("system/membership/users");
        } else {
            modelAndView = new ModelAndView("system/membership/departList");
        }
        return modelAndView;
    }

    /**
     * 部门和角色选择用户的用户显示列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridDepart")
    public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 测试
     * 
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "test")
    public void test(HttpServletRequest request, HttpServletResponse response) {
        String jString = request.getParameter("_dt_json");
        DataTables dataTables = new DataTables(request);
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
        String username = request.getParameter("userName");
        if (username != null) {
            cq.like("userName", username);
            cq.add();
        }
        DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq, true);
        TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
    }

    /**
     * 用户列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "index")
    public String index() {
        return "bootstrap/main";
    }

    /**
     * 用户列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "main")
    public String main() {
        return "bootstrap/test";
    }

    /**
     * 测试
     * 
     * @return
     */
    @RequestMapping(params = "testpage")
    public String testpage(HttpServletRequest request) {
        return "test/test";
    }

    /**
     * 设置签名跳转页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "addsign")
    public ModelAndView addsign(HttpServletRequest request) {
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        return new ModelAndView("system/user/usersign");
    }

    /**
     * 用户录入
     * 
     * @param user
     * @param req
     * @return
     */

    @RequestMapping(params = "savesign", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson savesign(HttpServletRequest req) {
        UploadFile uploadFile = new UploadFile(req);
        String id = uploadFile.get("id");
        TSUser user = systemService.getEntity(TSUser.class, id);
        uploadFile.setRealPath("signatureFile");
        uploadFile.setCusPath("signature");
        uploadFile.setByteField("signature");
        uploadFile.setBasePath("resources");
        uploadFile.setRename(false);
        uploadFile.setObject(user);
        AjaxJson j = new AjaxJson();
        message = user.getUserName() + "设置签名成功";
        systemService.uploadFile(uploadFile);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        j.setMsg(message);

        return j;
    }

    /**
     * 测试组合查询功能
     * 
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "testSearch")
    public void testSearch(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        if (user.getUserName() != null) {
            cq.like("userName", user.getUserName());
        }
        if (user.getRealName() != null) {
            cq.like("realName", user.getRealName());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "changestyle")
    public String changeStyle(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        if (user == null) {
            return "login/login";
        }
        String indexStyle = "shortcut";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
                continue;
            }
            if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
                indexStyle = cookie.getValue();
            }
        }
        request.setAttribute("indexStyle", indexStyle);
        return "system/user/changestyle";
    }

    /**
     * @Title: saveStyle
     * @Description: 修改首页样式
     * @param request
     * @return AjaxJson
     * @throws
     */
    @RequestMapping(params = "savestyle")
    @ResponseBody
    public AjaxJson saveStyle(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(Boolean.FALSE);
        TSUser user = ResourceUtil.getSessionUserName();
        if (user != null) {
            String indexStyle = request.getParameter("indexStyle");
            if (StringUtils.isNotEmpty(indexStyle)) {
                Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
                // 设置cookie有效期为一个月
                cookie.setMaxAge(3600 * 24 * 30);
                response.addCookie(cookie);
                j.setSuccess(Boolean.TRUE);
                j.setMsg("样式修改成功，请刷新页面");
            }
            // update-start--Author:JueYue Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
            ClientManager.getInstance().getClient().getFunctions().clear();
            // update-end--Author:JueYue Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
        } else {
            j.setMsg("请登录后再操作");
        }
        return j;
    }

    /**
     * 获取姓名拼音
     * 
     * @return
     */
    @RequestMapping(params = "getUsernamePinyin")
    @ResponseBody
    public String getUsernamePinyin(String userName) {
        String pinyin = "";
        if (StringUtils.isNotEmpty(userName)) {
            String[] pinyins = PinyinUtil.stringToPinyin(userName);
            StringBuffer sb = new StringBuffer();
            for (String str : pinyins) {
                sb.append(str);
            }
            pinyin = sb.toString();
        }
        return pinyin;
    }

    /**
     * 用户数据初始化数据处理
     * 
     * @param password
     * @return
     */
    @RequestMapping(params = "initDate")
    @ResponseBody
    public String initDate(String password) {
        if (StringUtil.isEmpty(password)) {
            return "失败";
        }
        updateUser(password);
        String j = "成功！";
        return j;
        // reu.updateUser();
    }

    //人工导入用户信息初始化时使用，synToActiviti 同步用户到Activiti数据库
    public void syncToActiviti(TSUser user) {
        //用逗号组装用户对应角色ID
        List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        String roleId = "";
        if (roleUsers.size() > 0) {
            for (TSRoleUser tRoleUser : roleUsers) {
                roleId += tRoleUser.getTSRole().getId() + ",";
            }
        }

        //获取用户ID
        String userId = user.getId();
        String activitiSync = user.getActivitiSync().toString();
        if (StringUtil.isEmpty(activitiSync)) {
            return;
        }
        if (StringUtil.isNotEmpty(roleId)) {
            try {
                activitiService.save(user, roleId, user.getActivitiSync());//同步用户到引擎
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

    //人工导入用户信息初始化时使用
    public void updateUser(String password) {
        String sql = "SELECT ID FROM T_S_USER";
        List<String> Ids = systemService.findListbySql(sql);
        for (String id : Ids) {
            TSUser u = systemService.getEntity(TSUser.class, id);
            //设置用户姓名拼音
            /*
             * if(StringUtil.isEmpty(u.getNamePinyin())){
             * u.setNamePinyin(userService.getUsernamePinyin(u.getRealName())); }
             */

            //设置同步工作流标志
            /* u.setActivitiSync((short) 1); */

            //设置用户密码,默认为123456并加密保存
            /*
             * password = userService.encrypt(u.getUserName(), password); u.setPassword(password);
             */

            //同步用户到Activiti数据库
            syncToActiviti(u);
        }

    }

    /**
     * 修改用户信息
     * 
     * @return
     */
    @RequestMapping(params = "saveUserInfo")
    @ResponseBody
    public AjaxJson saveUserInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        TSUser user = this.systemService.get(TSUser.class, id);
        String mobilePhone = request.getParameter("mobilePhone");
        String officePhone = request.getParameter("officePhone");
        String email = request.getParameter("email");
        user.setMobilePhone(mobilePhone);
        user.setOfficePhone(officePhone);
        user.setEmail(email);
        String departId = request.getParameter("departId");
        String odepartId = request.getParameter("odepartId");
        try {
            if (!departId.equals(odepartId)) {
                this.userService.saveUserInfo(user, odepartId, departId);
                j.setMsg("用户信息修改成功，部门变动已提交确认，请稍等!");
            } else {
                this.userService.saveUserInfo(user, null, null);
                j.setMsg("保存用户信息成功！请重新登录查看");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("用户信息修改失败！");
        }
        return j;
    }

}