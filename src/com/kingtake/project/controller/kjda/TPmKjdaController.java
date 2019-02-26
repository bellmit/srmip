package com.kingtake.project.controller.kjda;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.systemfile.TPmQualitySystemFileEntity;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.service.manage.TPmProjectServiceI;

/**
 * @Title: Controller
 * @Description: 科技档案
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmKjdaController")
public class TPmKjdaController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmKjdaController.class);

    @Autowired
    private TPmProjectServiceI tPmProjectService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 科技档案项目列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmKjdaProjectList")
    public ModelAndView tPmKjdaProjectList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/kjda/tPmProjectListForKjda");
    }
    
    /**
     * 归档列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmGdProjectList")
    public ModelAndView tPmGdProjectList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/gd/tPmProjectListForGd");
    }

    /**
     * 科技档案页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmKjda")
    public ModelAndView tPmKjda(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("tPmProjectPage", project);
            TPmTaskEntity task = this.systemService.findUniqueByProperty(TPmTaskEntity.class, "project.id", projectId);
            if (task != null) {
                request.setAttribute("tPmTaskPage", task);
            }
            CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
            cq.eq("projectId", projectId);
            cq.addOrder("createdate", SortDirection.desc);
            cq.add();
            List<TSFilesEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
            request.setAttribute("fileList", list);
        }
        return new ModelAndView("com/kingtake/project/kjda/tPmKjda");
    }
    

    /**
     * 跳转到附件文件界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goFileUpload")
    public ModelAndView goFileUpload(HttpServletRequest request) {
        String opt = request.getParameter("opt");
        String bid = request.getParameter("bid");
        if (StringUtils.isNotEmpty(bid)) {
            request.setAttribute("bid", bid);
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(bid, "");
            request.setAttribute("attachments", attachments);
        }
        request.setAttribute("opt", opt);
        return new ModelAndView("com/kingtake/zscq/sqwj/fileUpload");
    }
    
    /**
     * 批量归档
     * 
     * @return
     */
    @RequestMapping(params = "qrgd")
    @ResponseBody
    public AjaxJson qrgd(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "归档成功";
        try {
            for (String id : ids.split(",")) {
            	String sql = "update t_pm_project set gd_status='1' where id='"+id+"' ";
            	this.systemService.updateBySqlString(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "归档失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}
