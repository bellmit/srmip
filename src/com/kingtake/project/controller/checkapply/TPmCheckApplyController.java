package com.kingtake.project.controller.checkapply;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.service.checkapply.TPmCheckApplyServiceI;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.checkapply.TPmCheckApplyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.manage.ProjectListServiceContext;

/**
 * @Title: Controller
 * @Description: 项目基本信息表
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmCheckApplyController")
public class TPmCheckApplyController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmCheckApplyController.class);

    @Autowired
    private TPmCheckApplyServiceI tPmCheckApplyService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    private String message;

    @Autowired
    private ProjectListServiceContext projectListServiceContext;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 项目出校检验表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectCheck")
    public ModelAndView tPmProject(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/checkapply/tPmProjectConfirmCheckList");
    }

    /**
     * 产品检验申请单页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCheckApply")
    public ModelAndView goAdd(TPmCheckApplyEntity tPmCheckApply, HttpServletRequest req) {    	
        if(this.systemService.findUniqueByProperty(TPmCheckApplyEntity.class, "projectId", tPmCheckApply.getProjectId()) != null){
        	tPmCheckApply = this.systemService.findUniqueByProperty(TPmCheckApplyEntity.class, "projectId", tPmCheckApply.getProjectId());
        }
        if(StringUtils.isEmpty(tPmCheckApply.getAttachmentCode())){
        	tPmCheckApply.setAttachmentCode(UUIDGenerator.generate());
        }
        req.setAttribute("tPmCheckApplyPage", tPmCheckApply);
        return new ModelAndView("com/kingtake/project/checkapply/tPmProject-checkApply");
    }
    
    /**
     * 保存产品检验申请表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddOrUpdate")
    @ResponseBody
    public AjaxJson doAddOrUpdate(TPmCheckApplyEntity tPmCheckApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();        
        try {
        	if(StringUtil.isEmpty(tPmCheckApply.getId())){
        		message = "产品检验申请表添加成功";
        		tPmCheckApplyService.save(tPmCheckApply);        		
        	}else{
        		message = "产品检验申请表修改成功";
        		TPmCheckApplyEntity t = tPmCheckApplyService.get(TPmCheckApplyEntity.class, tPmCheckApply.getId());
        		MyBeanUtils.copyBeanNotNull2Bean(tPmCheckApply, t);
        		tPmCheckApplyService.updateEntitie(t);
        	}        	      
        	//更新项目是否出校检验标志为“是”
    		TPmProjectEntity projectEntity = systemService.getEntity(TPmProjectEntity.class, tPmCheckApply.getProjectId());
    		projectEntity.setCheckapprovalFlag("1");
    		systemService.save(projectEntity);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "产品检验申请表保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tPmCheckApply);
        j.setMsg(message);
        return j;
    }
    
    /**
     * easyui AJAX请求数据 附件列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForCheckApply")
    public void datagridForCheckApply(TPmCheckApplyEntity tPmCheckApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String businessType = request.getParameter("businessType");
        String attachmenttitle = request.getParameter("attachmenttitle");
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String sql = "select * from (select a.id,a.attachmenttitle,a.createdate,a.extend,a.realpath "
                + "from t_s_files f join t_s_attachment a  "
                + "on a.id=f.id where f.businesstype =? and f.bid =? and (f.del_flag='' or f.del_flag='0' or f.del_flag is null) ) ";
        if (StringUtils.isNotEmpty(attachmenttitle)) {
            sql = sql + " where attachmenttitle like '%" + attachmenttitle + "%'";
        }
        sql = sql + " order by createdate desc";
        String countSql = "select count(1) from (" + sql + ")";
        Object[] param = new Object[] { businessType, tPmCheckApply.getAttachmentCode() };
        List<Map<String, Object>> dataList = this.systemService.findForJdbcParam(sql, page, rows, param);
        Long count = this.systemService.getCountForJdbcParam(countSql, param);
        dataGrid.setTotal(count.intValue());
        dataGrid.setResults(dataList);
        TagUtil.datagrid(response, dataGrid);
    }
}
