package com.kingtake.zscq.controller.zsdj;
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

import com.kingtake.zscq.entity.zsdj.TZZsdjEntity;
import com.kingtake.zscq.service.zsdj.TZZsdjServiceI;



/**
 * @Title: Controller
 * @Description: 证书登记
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZZsdjController")
public class TZZsdjController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(TZZsdjController.class);

	@Autowired
    private TZZsdjServiceI tZZsdjService;
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
    public AjaxJson doUpdate(TZZsdjEntity tZZsdj, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "证书登记保存成功！";
        try {
            this.tZZsdjService.saveZsdj(tZZsdj);
        } catch (Exception e) {
            e.printStackTrace();
            message = "证书登记保存失败！";
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
    public ModelAndView goUpdate(TZZsdjEntity tZZsdj, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZZsdj.getZlsqId())) {
            List<TZZsdjEntity> list = tZZsdjService.findByProperty(TZZsdjEntity.class, "zlsqId", tZZsdj.getZlsqId());
            if (list.size() > 0) {
            	tZZsdj = list.get(0);
            }
        }
        if (StringUtil.isNotEmpty(tZZsdj.getId())) {
        	tZZsdj = tZZsdjService.getEntity(TZZsdjEntity.class, tZZsdj.getId());
        }
        if (StringUtils.isEmpty(tZZsdj.getFjbm())) {
        	tZZsdj.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZZsdj.getFjbm(), "");
            tZZsdj.setAttachments(fileList);
        }
        req.setAttribute("tZZsdjPage", tZZsdj);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zsdj/tZZsdj-update");
    }
	
	

}
