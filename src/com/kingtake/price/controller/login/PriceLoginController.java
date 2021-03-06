package com.kingtake.price.controller.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.controller.core.LoginController;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.manager.ExpertClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.ExpertClient;
import org.jeecgframework.web.system.pojo.base.TSConfig;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleOrg;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
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
 * ????????????????????????
 * 
 * @author ?????????
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/priceLoginController")
public class PriceLoginController extends BaseController {
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


    /**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("userName", user.getUserName());
		return new ModelAndView("com/kingtake/price/login/home");
	}
    

    /**
	 * ??????????????????
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
		AjaxJson j = new AjaxJson();
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
                if (true) {
//                    update-start-Author:zhangguoming  Date:20140825 for????????????????????????????????????????????????????????????????????????????????????
                    Map<String, Object> attrMap = new HashMap<String, Object>();
                    j.setAttributes(attrMap);

                    String orgId = req.getParameter("orgId");
                    if (oConvertUtils.isEmpty(orgId)) { // ??????????????????????????????????????????????????????????????????
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
		return j;
	}

//  update-start-Author:zhangguoming  Date:20140825 for????????????????????????????????????
  /**
   * ????????????????????????????????????????????????????????????????????????????????????????????????
   * @param req request
   * @param user ??????????????????
   * @param orgId ????????????
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
      // ??????????????????
      //systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
      systemService.addBusinessLog("??????", Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "??????" + user.getUserName()
              + "????????????", "", "");
  }
//  update-end-Author:zhangguoming  Date:20140825 for????????????????????????????????????

  /**
	 * ????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public ModelAndView login(ModelMap modelMap,HttpServletRequest request) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		if (user != null) {
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				roles += role.getRoleName() + ",";
			}
			if (roles.length() > 0) {
				roles = roles.substring(0, roles.length() - 1);
			}
          modelMap.put("roleName", roles);
          modelMap.put("userName", user.getUserName());
          modelMap.put("realName", user.getRealName());
          // update-start-Author:zhangguoming  Date:20140914 for??????????????????????????????????????????
          modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart().getDepartname());
          // update-end-Author:zhangguoming  Date:20140914 for??????????????????????????????????????????
          request.getSession().setAttribute("CKFinder_UserRole", "admin");
          request.getSession().setAttribute("lang", "zh-cn");
          
			return new ModelAndView("com/kingtake/price/login/price_main");
		} else {
			return new ModelAndView("com/kingtake/price/login/login");
		}

	}

	/**
	 * ????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = ResourceUtil.getSessionUserName();
        systemService.addBusinessLog("??????", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO, "??????" + user.getUserName()
                + "?????????", "", "");
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"priceLoginController.do?login"));
		return modelAndView;
	}

    
	
	/**
	 * @Title: top
	 * @author gaofeng
	 * @Description: shortcut??????????????????
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "shortcut_top")
	public ModelAndView shortcut_top(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		HttpSession session = ContextHolderUtils.getSession();
		// ??????????????????
		if (user.getId() == null) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(
					new RedirectView("priceLoginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/shortcut_top");
	}
	
	
	/**
	 * ???????????????map
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
			   //update-begin--Author:anchao  Date:20140913 for???????????????--------------------
	            if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
					//??????????????????????????? ??????????????????????????????
					continue;
				}
	            
	            if  (!Globals.Function_PRICE_MENU_BUSINESSCODE.equals(function.getBusinessCode()))
	            {
					//??????????????????????????????????????????
					continue;	    										
	            }
	            
	            
	            
	            
	          //update-end--Author:anchao  Date:20140913 for???????????????--------------------
				if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
					functionMap.put(function.getFunctionLevel() + 0,
							new ArrayList<TSFunction>());
				}
				functionMap.get(function.getFunctionLevel() + 0).add(function);
			}
			// ???????????????
			Collection<List<TSFunction>> c = functionMap.values();
			for (List<TSFunction> list : c) {
				Collections.sort(list, new NumberComparator());
			}
		}
		return functionMap;
	}

	
	/**
	 * ????????????????????????
	 * 
	 * @param user
	 * @return
	 */
	private Map<String, TSFunction> getUserFunction(TSUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
        //update-start--Author:JueYue  Date:2014-5-28 for:????????????,??????????????????????????????
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {
            //update-end--Author:JueYue  Date:2014-5-28 for:????????????,??????????????????????????????
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
//            update-begin--Author:zhangguoming  Date:20140821 for???????????????????????????????????????????????????????????????????????????
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
                assembleFunctionsByRole(loginActionlist, role);
			}
//            update-start-Author:zhangguoming  Date:20140825 for????????????????????????????????????????????????
            String orgId = client.getUser().getCurrentDepart().getId();
//            update-end-Author:zhangguoming  Date:20140825 for????????????????????????????????????????????????
            List<TSRoleOrg> orgRoleList = systemService.findByProperty(TSRoleOrg.class, "tsDepart.id", orgId);
            for (TSRoleOrg roleOrg : orgRoleList) {
                TSRole role = roleOrg.getTsRole();
                assembleFunctionsByRole(loginActionlist, role);
            }
//            update-end--Author:zhangguoming  Date:20140821 for???????????????????????????????????????????????????????????????????????????
            client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
	}
	
//  update-begin--Author:zhangguoming  Date:20140821 for????????????????????????????????????????????????
  /**
   * ?????? ???????????? ?????? ??????????????????
   * @param loginActionlist ???????????????????????????
   * @param role ????????????
   */
  private void assembleFunctionsByRole(Map<String, TSFunction> loginActionlist, TSRole role) {
      List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
      for (TSRoleFunction roleFunction : roleFunctionList) {
          TSFunction function = roleFunction.getTSFunction();
        //update-begin--Author:anchao  Date:20140822 for???[bugfree???]????????????????????????????????????--------------------
          if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
				//??????????????????????????? ??????????????????????????????
				continue;
			}
          
          if  (!Globals.Function_PRICE_MENU_BUSINESSCODE.equals(function.getBusinessCode()))
          {
				//??????????????????????????????????????????
				continue;	    										
          }
        //update-end--Author:anchao  Date:20140822 for???[bugfree???]????????????????????????????????????--------------------
          loginActionlist.put(function.getId(), function);
      }
  }
//  update-end--Author:zhangguoming  Date:20140821 for????????????????????????????????????????????????
	
}