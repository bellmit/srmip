package com.kingtake.expert.controller.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ReviewConstant;
import com.kingtake.expert.entity.info.TErExpertUserEntity;
import com.kingtake.expert.entity.reviewmain.TErMainTypeRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErProjectExpertRelaEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.entity.reviewtype.TErReviewTypeEntity;
import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;
import com.kingtake.expert.service.info.TErExpertUserServiceI;

/**
 * 登陆初始化控制器
 * 
 * @author 张代浩
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/expertLoginController")
public class ExpertLoginController extends BaseController {
    private final Logger log = Logger.getLogger(LoginController.class);
    private SystemService systemService;
    private UserService userService;
    private String message = null;

    @Autowired
    private TErExpertUserServiceI tErExpertUserService;

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
     * 修改密码
     * 
     * @return
     */
    @RequestMapping(params = "changepassword")
    public String changepassword(HttpServletRequest request) {
        TErExpertUserEntity user = ResourceUtil.getSessionExpert();
        request.setAttribute("user", user);
        return "com/kingtake/expert/login/changepassword";
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
        TErExpertUserEntity user = ResourceUtil.getSessionExpert();
        String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
        try {
            user.setUserPwd(PasswordUtil.encrypt(newpassword, user.getUserName(), PasswordUtil.getStaticSalt()));
            systemService.updateEntitie(user);
        } catch (Exception e) {
            throw new BusinessException("修改密码失败！", e);
        }
        j.setMsg("修改密码成功！");
        return j;
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
    public AjaxJson checkuser(TErExpertUserEntity expertUser, HttpServletRequest req) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
        AjaxJson j = new AjaxJson();
        if (req.getParameter("langCode") != null) {
            req.getSession().setAttribute("lang", req.getParameter("langCode"));
        }
            TErExpertUserEntity u = tErExpertUserService.checkUserExits(expertUser);
            if (u == null) {
                j.setMsg(mutiLangService.getLang("common.username.or.password.error"));
                j.setSuccess(false);
                return j;
            } else {
                saveLoginSuccessInfo(req, u);
                j.setMsg("登录成功，请稍后...");
                j.setSuccess(true);
            }
       /* }*/
        return j;
    }

    private void saveLoginSuccessInfo(HttpServletRequest req, TErExpertUserEntity user) {
        HttpSession session = ContextHolderUtils.getSession();
        message = mutiLangService.getLang("common.user") + ": " + user.getUserName()
                + mutiLangService.getLang("common.login.success");

        ExpertClient expertClient = new ExpertClient();
        expertClient.setIp(IpUtil.getIpAddr(req));
        expertClient.setLogindatetime(new Date());
        expertClient.setExpert(user);
        ExpertClientManager.getInstance().addClinet(session.getId(), expertClient);
        Client client = new Client();
        client.setIp(IpUtil.getIpAddr(req));
        client.setLogindatetime(new Date());
        List<TSUser> userList = this.systemService.findByProperty(TSUser.class, "userName", "expert");
        if (userList.size() > 0) {
            client.setUser(userList.get(0));
            ClientManager.getInstance().addClinet(session.getId(), client);
        }
        // 添加登陆日志
        systemService.addBusinessLog("专家登录", Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "用户" + user.getUserName()
                + "登录成功", "", "");
    }

    /**
     * 用户登录
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "login")
    public ModelAndView login(ModelMap modelMap, HttpServletRequest request) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
        TErExpertUserEntity expert = ResourceUtil.getSessionExpert();
        if (expert != null) {
            modelMap.put("expert", expert);
            return new ModelAndView("com/kingtake/expert/login/expert_main");
        } else {
            return new ModelAndView("com/kingtake/expert/login/login");
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
        TErExpertUserEntity user = ResourceUtil.getSessionExpert();
        systemService.addBusinessLog("专家登录", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO, "用户" + user.getUserName()
                + "已退出", "", "");
        ExpertClientManager.getInstance().removeClinet(session.getId());
        ClientManager.getInstance().removeClinet(session.getId());
        ModelAndView modelAndView = new ModelAndView(new RedirectView("expertLoginController.do?login"));
        return modelAndView;
    }

    /**
     * 专家的首页，显示专家相关的项目
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForExpert")
    @ResponseBody
    public JSONObject datagridForExpert(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TErExpertUserEntity entity = ResourceUtil.getSessionExpert();
        CriteriaQuery relaCq = new CriteriaQuery(TErProjectExpertRelaEntity.class);
        relaCq.eq("expertId", entity.getId());
        relaCq.add();
        List<TErProjectExpertRelaEntity> relaList = this.systemService.getListByCriteriaQuery(relaCq, false);
        Set<String> mainList = new HashSet<String>();
        for (TErProjectExpertRelaEntity rela : relaList) {
            mainList.add(rela.getReviewId());
        }
        CriteriaQuery cq = new CriteriaQuery(TErReviewMainEntity.class, dataGrid);
        if(mainList.size()>0){
            cq.in("id", mainList.toArray());
        }else{
            cq.in("id", new Object[]{"id"});
        }
        cq.eq("reviewStatus", ReviewConstant.REVIEW_STATUS_SEND);//已提交
        cq.add();
        int count = this.systemService.getCountByCriteriaQuery(cq);
        CriteriaQuery mainCq = new CriteriaQuery(TErReviewMainEntity.class, dataGrid);
        mainCq.setCurPage(Integer.valueOf(request.getParameter("page")));
        mainCq.setPageSize(Integer.valueOf(request.getParameter("rows")));
        if(mainList.size()>0){
            mainCq.in("id", mainList.toArray());
        }else{
            mainCq.in("id", new Object[]{"id"});
        }
        mainCq.eq("reviewStatus", ReviewConstant.REVIEW_STATUS_SEND);//已提交
        mainCq.addOrder("createDate", SortDirection.desc);
        mainCq.add();
        List<TErReviewMainEntity> reviewMainList = this.systemService.getListByCriteriaQuery(mainCq, true);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (TErReviewMainEntity mainEntity : reviewMainList) {
            JSONObject mainJson = new JSONObject();
            String[] fields = dataGrid.getField().split(",");
            ReflectHelper helper = new ReflectHelper(mainEntity);
            for (String field : fields) {
                Object obj = helper.getMethodValue(field);
                if (obj instanceof Date) {
                    obj = DateUtils.formatDate((Date) obj, "yyyy-MM-dd HH:mm:ss");
                }
                mainJson.put(field, obj);
            }
            array.add(mainJson);
        }
        json.put("rows", array);
        json.put("total", count);
        return json;
    }

    /**
     * 跳转到专家的评审主界面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goExpertReviewMainListForExpert")
    public ModelAndView goExpertReviewMainListForExpert(TErReviewProjectEntity entity, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("com/kingtake/expert/login/tErReviewMainListForExpert");
    }

    /**
     * 获取子列表信息
     * 
     * @param tErReviewMain
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getDetailList")
    @ResponseBody
    public JSONObject getDetailList(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response) {
        TErExpertUserEntity expert = ResourceUtil.getSessionExpert();
        JSONObject json = new JSONObject();
        tErReviewMain = this.systemService.get(TErReviewMainEntity.class, tErReviewMain.getId());
        String sql = "select proj.id,proj.project_id projectId,proj.project_name projectName from t_er_review_main review join t_er_review_project proj on "
                + "review.id=proj.t_e_id join t_er_project_expert pe on proj.id=pe.project_id where pe.expert_id=? and review.id=?";
        List<Map<String,Object>> dataList = this.systemService.findForJdbc(sql, new Object[]{expert.getId(),tErReviewMain.getId()});
        JSONArray subProjectArr = new JSONArray();
        for (Map<String,Object> tmpMap:dataList) {
            JSONObject subJson = new JSONObject();
            String reviewProjectId = (String) tmpMap.get("id");
            subJson.put("id", reviewProjectId);
            subJson.put("projectId", tmpMap.get("projectId"));
            subJson.put("projectName", tmpMap.get("projectName"));
            CriteriaQuery cq = new CriteriaQuery(TErSuggestionEntity.class);
            cq.eq("expertId", expert.getId());
            cq.eq("reviewProject.id", reviewProjectId);
            cq.add();
            List<TErSuggestionEntity> suggestions = this.systemService.getListByCriteriaQuery(cq, false);
            if (suggestions.size() > 0) {
                TErSuggestionEntity suggestion = suggestions.get(0);
                subJson.put("reviewResultStr", suggestion.getExpertResultStr());
                subJson.put("reviewResult", suggestion.getExpertResult());
                subJson.put("reviewScore", suggestion.getExpertScore());
                subJson.put("reviewSuggestion", suggestion.getExpertContent());
            }
            subProjectArr.add(subJson);
        }
        json.put("rows", subProjectArr);
        json.put("total", subProjectArr.size());
        return json;
    }

    /**
     * 跳转到专家评审界面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goExpertReviewInfoForExpert")
    public ModelAndView goExpertReviewInfo(TErReviewProjectEntity entity, HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isNotEmpty(entity.getId())) {
            entity = this.systemService.get(TErReviewProjectEntity.class, entity.getId());
            TErExpertUserEntity expertUser = ResourceUtil.getSessionExpert();
            CriteriaQuery cq = new CriteriaQuery(TErSuggestionEntity.class);
            cq.eq("reviewProject.id", entity.getId());
            cq.eq("expertId", expertUser.getId());
            cq.add();
            List<TErSuggestionEntity> suggestions = this.systemService.getListByCriteriaQuery(cq, false);
            if (suggestions.size() > 0) {
                request.setAttribute("suggestionEntity", suggestions.get(0));
            }
            request.setAttribute("reviewProjectEntity", entity);
        }
        return new ModelAndView("com/kingtake/expert/login/expertReviewInfoForExpert");
    }

    /**
     * 获取项目内容树
     * 
     * @param entity
     * @param request
     * @param response
     */
    @RequestMapping(params = "getProjectTypeTree")
    @ResponseBody
    public JSONArray getProjectTypeTree(TErReviewProjectEntity entity, HttpServletRequest request,
            HttpServletResponse response) {
        JSONArray array = new JSONArray();
        if (StringUtils.isNotEmpty(entity.getId())) {
            entity = this.systemService.get(TErReviewProjectEntity.class, entity.getId());
        }
        String projectId = entity.getProjectId();
        String mainId = entity.getReviewMain().getId();
        List<TErMainTypeRelaEntity> mainTypeRelaList = this.systemService.findByProperty(TErMainTypeRelaEntity.class,
                "teMainId", mainId);
        if (mainTypeRelaList.size() > 0) {
            List<String> typeIdList = new ArrayList<String>();
            for (TErMainTypeRelaEntity rela : mainTypeRelaList) {
                typeIdList.add(rela.getTeTypeId());
            }
            CriteriaQuery cq = new CriteriaQuery(TErReviewTypeEntity.class);
            cq.in("id", typeIdList.toArray());
            cq.addOrder("sortCode", SortDirection.asc);
            cq.add();
            List<TErReviewTypeEntity> reviewTypeList = this.systemService.getListByCriteriaQuery(cq, false);
            if (reviewTypeList.size() > 0) {
                for (TErReviewTypeEntity type : reviewTypeList) {
                    JSONObject json = new JSONObject();
                    json.put("id", type.getId());
                    json.put("text", type.getTitle());
                    Map<String, String> attMap = new HashMap<String, String>();
                    attMap.put("url", type.getUrl().replace("${projectId}", projectId));
                    json.put("attributes", attMap);
                    array.add(json);
                }
            }
        }
        return array;
    }

    /**
     * 评审项目信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "saveReviewResultForExpert")
    @ResponseBody
    public AjaxJson saveReviewResultForExpert(TErSuggestionEntity suggestion, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "专家评审结果保存成功";
        TErExpertUserEntity expertUserEntity = ResourceUtil.getSessionExpert();
        try {
            if (StringUtils.isNotEmpty(suggestion.getId())) {
                TErSuggestionEntity t = this.systemService.get(TErSuggestionEntity.class, suggestion.getId());
                MyBeanUtils.copyBeanNotNull2Bean(suggestion, t);
                t.setExpertId(expertUserEntity.getId());
                t.setExpertName(expertUserEntity.getName());
                t.setExpertTime(new Date());
                this.systemService.saveOrUpdate(t);
            } else {
                suggestion.setExpertId(expertUserEntity.getId());
                suggestion.setExpertName(expertUserEntity.getName());
                suggestion.setExpertTime(new Date());
                this.systemService.save(suggestion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专家评审结果保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setSuccess(true);
        return j;
    }

}