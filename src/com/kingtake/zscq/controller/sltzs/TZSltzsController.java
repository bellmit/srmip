package com.kingtake.zscq.controller.sltzs;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.zscq.entity.sltzs.TZSltzsEntity;
import com.kingtake.zscq.service.sltzs.TZSltzsServiceI;



/**
 * @Title: Controller
 * @Description: 受理通知书
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZSltzsController")
public class TZSltzsController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(TZSltzsController.class);

	@Autowired
    private TZSltzsServiceI tZSltzsService;
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
     * 保存受理通知书
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate", method = RequestMethod.POST)
	@ResponseBody
    public AjaxJson doUpdate(TZSltzsEntity tZSltzs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "受理通知书保存成功！";
        try {
            this.tZSltzsService.saveSltzs(tZSltzs);
        } catch (Exception e) {
            e.printStackTrace();
            message = "受理通知书保存失败！";
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
    public ModelAndView goUpdate(TZSltzsEntity tZSltzs, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZSltzs.getZlsqId())) {
            List<TZSltzsEntity> list = tZSltzsService
                    .findByProperty(TZSltzsEntity.class, "zlsqId", tZSltzs.getZlsqId());
            if (list.size() > 0) {
                tZSltzs = list.get(0);
            }
        }
        if (StringUtil.isNotEmpty(tZSltzs.getId())) {
            tZSltzs = tZSltzsService.getEntity(TZSltzsEntity.class, tZSltzs.getId());
        }
        if (StringUtils.isEmpty(tZSltzs.getFjbm())) {
            tZSltzs.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZSltzs.getFjbm(), "");
            tZSltzs.setAttachments(fileList);
        }
        req.setAttribute("tZSltzsPage", tZSltzs);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/sltzs/tZSltzs-update");
    }
	
	

}
