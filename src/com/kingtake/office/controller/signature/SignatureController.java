package com.kingtake.office.controller.signature;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.service.signature.SignatureServiceI;



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
@RequestMapping("/signatureController")
public class SignatureController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SignatureController.class);

	@Autowired
	private SystemService systemService;
	
	@Autowired
    private SignatureServiceI signatureService;

	
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "signatureList")
    public ModelAndView signatureList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/signature/SignatureHome");
    }

    /**
     * 保存签章
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveSignature")
    public void saveSignature(HttpServletRequest request,HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setContentType("text/html; charset=utf-8");
            writer = response.getWriter();
            signatureService.saveSignature(request);
            writer.print("<script>location.href='webpage/com/kingtake/office/signature/SignatureList.jsp'</script>");
        } catch (Exception e) {
            e.printStackTrace();
            writer.print("<script>alert('" + e.getMessage() + "');history.back(-1);</script>");
        }
    }

}
