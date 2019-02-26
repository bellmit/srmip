package org.jeecgframework.web.system.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSConfig;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleOrg;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSSession;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 登陆初始化控制器
 * @author 张代浩
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{
	private final Logger log = Logger.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;
	private String message = null;

	@Autowired
	private MutiLangServiceI mutiLangService;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}

	/**
	 * admin账户密码初始化
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pwdInit")
	public ModelAndView pwdInit(HttpServletRequest request) {
		ModelAndView modelAndView = null;
		TSUser user = new TSUser();
		user.setUserName("admin");
		String newPwd = "123456";
		userService.pwdInit(user, newPwd);
		modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder
				.setDataSourceType(DataSourceType.dataSource_jeecg);
		AjaxJson j = new AjaxJson();
        if (req.getParameter("langCode")!=null) {
        	req.getSession().setAttribute("lang", req.getParameter("langCode"));
        }
        String randCode = req.getParameter("randCode");
       if (StringUtils.isEmpty(randCode)) {
            j.setMsg(mutiLangService.getLang("common.enter.verifycode"));
            j.setSuccess(false);
        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
            // todo "randCode"和验证码servlet中该变量一样，通过统一的系统常量配置比较好，暂时不知道系统常量放在什么地方合适
            j.setMsg(mutiLangService.getLang("common.verifycode.error"));
            j.setSuccess(false);
        } else {
            int users = userService.getList(TSUser.class).size();
            
            if (users == 0) {
                j.setMsg("a");
                j.setSuccess(false);
            } else {
                TSUser u = userService.checkUserExits(user);
                if(u == null) {
                    j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
                    j.setSuccess(false);
                    return j;
                }
                TSUser u2 = userService.getEntity(TSUser.class, u.getId());
            
                if (u != null&&u2.getStatus()!=0) {
                    // if (user.getUserKey().equals(u.getUserKey())) {
                   
                	
                    if (true) {
//                        update-start-Author:zhangguoming  Date:20140825 for：处理用户有多个组织机构的情况，以弹出框的形式让用户选择
                        Map<String, Object> attrMap = new HashMap<String, Object>();
                        j.setAttributes(attrMap);

                        String orgId = req.getParameter("orgId");
                        if (oConvertUtils.isEmpty(orgId)) { // 没有传组织机构参数，则获取当前用户的组织机构
                            Long orgNum = systemService.getCountForJdbc("select count(1) from t_s_user_org where user_id = '" + u.getId() + "'");
                            if (orgNum > 1) {
                                attrMap.put("orgNum", orgNum);
                                attrMap.put("user", u2);
                            } else {
                                Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=?", u2.getId());
                                saveLoginSuccessInfo(req, u2, (String) userOrgMap.get("orgId"));
                            }
                        } else {
                            attrMap.put("orgNum", 1);

                            saveLoginSuccessInfo(req, u2, orgId);
                        }
                    } else {
                        j.setMsg(mutiLangService.getLang("common.check.shield"));
                        j.setSuccess(false);
                    }
                } else {
                	j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
                    j.setSuccess(false);
                }
            }
        }
		return j;
	}

//    update-start-Author:zhangguoming  Date:20140825 for：记录用户登录的相关信息
    /**
     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param req request
     * @param user 当前登录用户
     * @param orgId 组织主键
     */
    private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user, String orgId) {
        TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
        user.setCurrentDepart(currentDepart);

        HttpSession session = ContextHolderUtils.getSession();
        message = mutiLangService.getLang("common.user") + ": " + user.getUserName() + "["
                + currentDepart.getDepartname() + "]" + mutiLangService.getLang("common.login.success");

        Client client = new Client();
        client.setIp(IpUtil.getIpAddr(req));
        client.setLogindatetime(new Date());
        client.setUser(user);
        ClientManager.getInstance().addClinet(session.getId(), client);
        // 添加登陆日志
        //systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
        systemService.addBusinessLog("登录", Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "用户" + user.getUserName()
                + "登录成功", "", "");
    }
//    update-end-Author:zhangguoming  Date:20140825 for：记录用户登录的相关信息

    /**
	 * 用户登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap,HttpServletRequest request) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		String roleCodes = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
				roleCodes += role.getRoleCode()+",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
            modelMap.put("roleName", roles);
            modelMap.put("userName", user.getUserName());
            modelMap.put("realName", user.getRealName());
            // update-start-Author:zhangguoming  Date:20140914 for：获取当前登录用户的组织机构
            modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart().getDepartname());
            // update-end-Author:zhangguoming  Date:20140914 for：获取当前登录用户的组织机构
            request.getSession().setAttribute("CKFinder_UserRole", "admin");
            request.getSession().setAttribute("currentRoleCodes",roleCodes); 
            
			//request.getSession().setAttribute("lang", "en");
            TSSession session = new TSSession();
            if(request.getSession().getId()!=null && !request.getSession().getId().equals("")){
            	session.setSessionId(request.getSession().getId());
            	session.setUserId(user.getId());
            	systemService.save(session);
            }
			
			// 默认风格
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
			// 要添加自己的风格，复制下面三行即可
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("bootstrap")) {
				return "main/bootstrap_main";
			}
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("shortcut")) {
				return "main/shortcut_main";
			}

//			update-start--Author:gaofeng  Date:2014-01-24 for:新增首页风格按钮选项
			if (StringUtils.isNotEmpty(indexStyle)
					&& indexStyle.equalsIgnoreCase("sliding")) {
				return "main/sliding_main";
			}
//			update-start--Author:gaofeng  Date:2014-01-24 for:新增首页风格按钮选项
			
			return "main/main";
		} else {
			return "login/login";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
        //		systemService.addLog("用户" + user.getUserName() + "已退出",
        //				Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
        systemService.addBusinessLog("登录", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO, "用户" + user.getUserName()
                + "已退出", "", "");
		String sql = " select * from t_s_session where session_id = '"+session.getId()+"'";
		List<Map<String, Object>> list = this.systemService.findForJdbc(sql);
        System.out.println(list.get(0).get("ID"));
        TSSession sess = systemService.getEntity(TSSession.class, list.get(0).get("ID").toString());
        systemService.delete(sess);
        ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
        ModelAndView modelAndView = new ModelAndView();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
            modelAndView.setView(new RedirectView("loginController.do?login"));
		}else{
            List<TSConfig> configs = userService.loadAll(TSConfig.class);
            for (TSConfig tsConfig : configs) {
                request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
            }
            modelAndView.setViewName("main/left");
            request.setAttribute("menuMap", getFunctionMap(user));
        }
		return modelAndView;
	}

	/**
	 * 获取权限的map
	 * 
	 * @param user
	 * @return
	 */
	private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
		Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
		Map<String, TSFunction> loginActionlist = getUserFunction(user);
		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
			   //update-begin--Author:anchao  Date:20140913 for：菜单过滤--------------------
	            if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
					//如果为表单或者弹出 不显示在系统菜单里面
					continue;
				}
	          //update-end--Author:anchao  Date:20140913 for：菜单过滤--------------------
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0,
							new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// 菜单栏排序
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return
	 */
	private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
        //update-start--Author:JueYue  Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {
            //update-end--Author:JueYue  Date:2014-5-28 for:风格切换,菜单懒加载失效的问题
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
//            update-begin--Author:zhangguoming  Date:20140821 for：重构方法体，并加载用户组织机构下角色所拥有的权限
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
                assembleFunctionsByRole(loginActionlist, role);
			}
//            update-start-Author:zhangguoming  Date:20140825 for：获取当前登录用户的组织机构主键
            String orgId = client.getUser().getCurrentDepart().getId();
//            update-end-Author:zhangguoming  Date:20140825 for：获取当前登录用户的组织机构主键
            List<TSRoleOrg> orgRoleList = systemService.findByProperty(TSRoleOrg.class, "tsDepart.id", orgId);
            for (TSRoleOrg roleOrg : orgRoleList) {
                TSRole role = roleOrg.getTsRole();
                assembleFunctionsByRole(loginActionlist, role);
            }
//            update-end--Author:zhangguoming  Date:20140821 for：重构方法体，并加载用户组织机构下角色所拥有的权限
            client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}

//    update-begin--Author:zhangguoming  Date:20140821 for：抽取方法，获取角色下的权限列表
    /**
     * 根据 角色实体 组装 用户权限列表
     * @param loginActionlist 登录用户的权限列表
     * @param role 角色实体
     */
    private void assembleFunctionsByRole(Map<String, TSFunction> loginActionlist, TSRole role) {
        List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
        for (TSRoleFunction roleFunction : roleFunctionList) {
            TSFunction function = roleFunction.getTSFunction();
          //update-begin--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
            if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
				//如果为表单或者弹出 不显示在系统菜单里面
				continue;
			}
          //update-end--Author:anchao  Date:20140822 for：[bugfree号]字段级权限（表单，列表）--------------------
            loginActionlist.put(function.getId(), function);
        }
    }
//    update-end--Author:zhangguoming  Date:20140821 for：抽取方法，获取角色下的权限列表

    /**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("userName", user.getUserName());
		return new ModelAndView("main/home");
	}
	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}
	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/bootstrap_top");
	}
	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "shortcut_top")
	public ModelAndView shortcut_top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top");
	}
	
	 
    /**
     * 获取项目管理菜单信息（有则返回，无则不返回）
     * @return
     */
    @RequestMapping(params = "getProjectMenu")
    @ResponseBody
    public AjaxJson getProjectMenu() {
    	AjaxJson j = new AjaxJson();
    	j.setSuccess(false);
    	TSUser user = ResourceUtil.getSessionUserName();
    	Map<String, TSFunction> loginActionlist = getUserFunction(user);
		if (loginActionlist.size() > 0) {
			Collection<TSFunction> allFunctions = loginActionlist.values();
			for (TSFunction function : allFunctions) {
				if(function.getFunctionType().intValue() != Globals.Function_TYPE_FROM.intValue() && "项目管理".equals(function.getFunctionName())) {
					j.setSuccess(true);
					Map<String, Object> menu = new HashMap<String, Object>();
					menu.put("functionName", function.getFunctionName());
					menu.put("functionUrl", function.getFunctionUrl());
					j.setAttributes(menu);
					break;
				}
			}
		}
		return j;
    }
	
	
	
	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
    /*@RequestMapping(params = "primaryMenu")
    @ResponseBody
	public String getPrimaryMenu() {
		List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getSessionUserName()).get(0);
        String floor = "";
//        update-start--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        if (primaryMenu == null) {
            return floor;
        }
//        update-end--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        for (TSFunction function : primaryMenu) {
            if(function.getFunctionLevel() == 0) {

            	String lang_key = function.getFunctionName();
            	String lang_context = mutiLangService.getLang(lang_key);
            	
                if("Online 开发".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
                }else if("统计查询".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />" + " </li> ";
                }else if("系统管理".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
                }else if("常用示例".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
                }else if("系统监控".equals(lang_context)){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
                }else if(lang_context.contains("消息推送")){
                	String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>消息推送</div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/msg.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/msg_up.png' style='display: none;' />"
                            + s +"</li> ";
                } else if (lang_context.contains("课题组项目管理入口")) {
                    String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;' project='2'>项目管理</div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/research.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/research_up.png' style='display: none;' />"
                            + s
                            + "</li> ";
                } else if (lang_context.contains("机关项目管理入口")) {
                    String s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;' project='1'>项目管理</div>";
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/department.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/department_up.png' style='display: none;' />"
                            + s
                            + "</li> ";
                }else{
                    //其他的为默认通用的图片模式
                    String s = "";
                    if(lang_context.length()>=5 && lang_context.length()<7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context +"</span></div>";
                    }else if(lang_context.length()<5){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>"+ lang_context +"</div>";
                    }else if(lang_context.length()>=7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ lang_context.substring(0, 6) +"</span></div>";
                    }
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/default.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
                            + s +"</li> ";
                }
            }
        }
		
		return floor;
	}*/
	
	/**
     * @Title: top
     * @author:lxp
     * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
     * @return AjaxJson
     * @throws
     */
    @RequestMapping(params = "primaryMenu")
    @ResponseBody
    public String getPrimaryMenu() {
        List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getSessionUserName()).get(0);
        String floor = "";
//        update-start--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        if (primaryMenu == null) {
            return floor;
        }
        Collections.reverse(primaryMenu);
//        update-end--Author:zhangguoming  Date:20140923 for：用户没有任何权限，首页没有退出按钮的bug
        for (TSFunction function : primaryMenu) {
            if(function.getFunctionLevel() == 0) {

                String lang_key = function.getFunctionName();
                String lang_context = mutiLangService.getLang(lang_key);
                
                String tsIcon = function.getTSIcon().getIconPath();
                String businessCode = function.getBusinessCode();
                
                //其他的为默认通用的图片模式
                String s = "";
                if(tsIcon != null && tsIcon != ""){
                    if ("projectForResearch".equals(businessCode)) {//课题组项目管理特殊处理
                        s = "<div style='width:67px;position: absolute;text-align:center;font-size:12px;color:#fff;' project='2'>"
                                + lang_context + "</div>";
                    } else {
                        if(lang_context.length()>=5 && lang_context.length()<7){
                            s = "<div style='width:67px;position: absolute;text-align:center;font-size:12px;color:#fff;letter-spacing:-1px;'>"+ lang_context +"</div>";
                        } else if(lang_context.length()<5){
                            s = "<div style='width:67px;position: absolute;text-align:center;font-size:12px;color:#fff;'>"+ lang_context +"</div>";
                        } else if(lang_context.length()>=7){
                            s = "<div style='width:67px;position: absolute;text-align:center;font-size:12px;color:#fff;letter-spacing:-1px;'>"+ lang_context.substring(0, 6) +"</div>";
                        }
                    }
                    floor += " <li style='position: relative;' onmouseover='mouseOver(this)' onmouseOut='mouseOut(this)'><div class='backUp' style='background:url(plug-in/login/images/top.png) no-repeat center center;"
                            + " text-align: center;width:67px; height:62px' >"
                            + "<img src='"+tsIcon+"' style='width:32px; height:32px; margin-top:5px;' /> "
                            + s +"</div></li> ";
                }
            }
        }
        
        return floor;
    }
    
	/**
	 * 返回数据
	 */
	@RequestMapping(params = "getPrimaryMenuForWebos")
	@ResponseBody
	public AjaxJson getPrimaryMenuForWebos() {
		AjaxJson j = new AjaxJson();
		//将菜单加载到Session，用户只在登录的时候加载一次
		Object getPrimaryMenuForWebos =  ContextHolderUtils.getSession().getAttribute("getPrimaryMenuForWebos");
		if(oConvertUtils.isNotEmpty(getPrimaryMenuForWebos)){
			j.setMsg(getPrimaryMenuForWebos.toString());
		}else{
			String PMenu = ListtoMenu.getWebosMenu(getFunctionMap(ResourceUtil.getSessionUserName()));
			ContextHolderUtils.getSession().setAttribute("getPrimaryMenuForWebos", PMenu);
			j.setMsg(PMenu);
		}
		return j;
	}
}