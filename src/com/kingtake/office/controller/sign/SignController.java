package com.kingtake.office.controller.sign;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.service.sign.SignServiceI;



/**   
 * @Title: Controller
 * @Description: 电子签章
 * @author onlineGenerator
 * @date 2015-10-06 10:42:25
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/signController")
public class SignController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(SignController.class);

	@Autowired
	private SystemService systemService;
	
	@Autowired
    private SignServiceI signService;

	
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "signatureList")
    public ModelAndView signatureList(HttpServletRequest request) {
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String sql = "select t.id,t.name,t.username,t.password,t.filename,t.esppath,t.createdate,t.picpath from t_o_sign t order by t.createdate desc";
        List<Map<String, Object>> mapList = this.systemService.findForJdbc(sql);
        for (Map<String, Object> map : mapList) {
            String picpath = (String) map.get("picpath");
            String imgPath = "http://" + serverName + ":" + port + "/" + picpath;
            map.put("picpath", imgPath);
        }

        request.setAttribute("signList", mapList);
        return new ModelAndView("com/kingtake/office/sign/signEdit");
    }

    /**
     * 保存签章
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveSignature")
    public void saveSignature(HttpServletRequest request,HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            signService.saveSignature(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除签章
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "deleteSign")
    @ResponseBody
    public AjaxJson deleteSign(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String id = request.getParameter("id");
            signService.deleteSign(id);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("删除印章失败！");
        }
        return json;
    }

    /**
     * 删除签章
     * 
     * @param request
     * @return
     */
    //    @RequestMapping(params = "viewSign")
    //    @ResponseBody
    //    public JSONObject viewSign(HttpServletRequest request, HttpServletResponse response) {
    //        JSONObject json = new JSONObject();
    //        String url = "";
    //        try {
    //            url = signService.viewSign(request, response);
    //            json.put("url", url);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return json;
    //    }

}
