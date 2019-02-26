package com.kingtake.project.controller.manage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: 项目基本信息
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectInfoController")
public class TPmProjectInfoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectController.class);

    @Autowired
    private SystemService systemService;

    /**
     * 项目
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectInfo")
    public ModelAndView tPmProjectInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("lxyj", "1");
        request.setAttribute("needContract", "0");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null && StringUtils.isNotEmpty(project.getProjectName())) {
                request.setAttribute("projectName", project.getProjectName());
                request.setAttribute("lxyj", project.getLxyj());
            }
            
            if("2".equals(project.getLxyj())) {
            	//判断是否上传合同（合同类，必须上传）
                String sql = "select t1.T_P_ID from t_pm_income_contract_appr t1 left join t_pm_project t2 on t1.T_P_ID = T2.id  where t1.T_P_ID = '" + projectId + "' and t2.lxyj=2";
            	List<Map<String, Object>> contractList = systemService.findForJdbc(sql);
            	if(CollectionUtils.isEmpty(contractList)) {
            		 request.setAttribute("needContract", "1");
            	}
            }
            List<TPmProjectEntity> projectList = systemService.findByProperty(TPmProjectEntity.class,"glxm.id", projectId);
            if(CollectionUtils.isNotEmpty(projectList) && projectList.get(0)!=null) {
            	 request.setAttribute("lxStatus", "1");
            }else {
            	 request.setAttribute("lxStatus", "0");
            }
          
        }
        return new ModelAndView("com/kingtake/project/manage/projectInfo");
    }

    /**
     * 项目过程管理
     * 
     * @return
     */
    @RequestMapping(params = "projectProcessMgr")
    public ModelAndView projectProcessMgr(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null && StringUtils.isNotEmpty(project.getProjectName())) {
                request.setAttribute("projectName", project.getProjectName());
                
                String sql2 = "select ID from t_pm_project where PROJECT_NO is not null and CXM = ?";  //lijun查询是否已立项
                List<Map<String, Object>> rList = this.systemService.findForJdbc(sql2, new Object[] { project.getCxm() });
                if(rList != null && rList.size()>0) { //如果项目已经立项则打上标识
                	request.setAttribute("LX_FLAG", "1");
                }else {
                	request.setAttribute("LX_FLAG", "0");
                }
                request.setAttribute("LXYJ", project.getLxyj());
            }
            String sql = "select a.id,a.project_name,a.project_manager,a.fee_type,c.* "
            		+ " from t_pm_project a left join t_b_funds_property c on c.id=a.fee_type "
            		+ " where a.id=?";
            List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, new Object[] { projectId });
            if(resultList!=null){
            	request.setAttribute("PROFOR_FLAG", resultList.get(0).get("PROFOR_FLAG"));
            	request.setAttribute("PROFOR_RATIO", resultList.get(0).get("PROFOR_RATIO"));
            	request.setAttribute("MOTO_FLAG", resultList.get(0).get("MOTO_FLAG"));
            	request.setAttribute("MOTO_RATIO", resultList.get(0).get("MOTO_RATIO"));
            }
        }
        return new ModelAndView("com/kingtake/project/manage/projectProcessMgr");
    }

}
