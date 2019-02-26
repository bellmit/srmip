package com.kingtake.expert.controller.reviewproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.expert.entity.info.TErExpertEntity;
import com.kingtake.expert.entity.info.TErExpertUserEntity;
import com.kingtake.expert.entity.reviewmain.TErMainExpertRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErMainTypeRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErProjectExpertRelaEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.service.reviewproject.TErReviewProjectServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;



/**   
 * @Title: Controller
 * @Description: 评审项目信息表
 * @author onlineGenerator
 * @date 2015-08-18 16:51:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tErReviewProjectController")
public class TErReviewProjectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TErReviewProjectController.class);

	@Autowired
	private TErReviewProjectServiceI tErReviewProjectService;
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
	 * 评审项目信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tErReviewProject")
	public ModelAndView tErReviewProject(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/expert/reviewproject/tErReviewProjectList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public JSON datagrid(TErReviewProjectEntity tErReviewProject,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONArray json = new JSONArray();
		CriteriaQuery cq = new CriteriaQuery(TErReviewProjectEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErReviewProject, request.getParameterMap());
		try{
		//自定义追加查询条件			
			if(StringUtil.isEmpty(tErReviewProject.getReviewMain().getId())){
				cq.eq("reviewMain.id", "1");
			}
			else{				
				List<TErReviewProjectEntity> tErReviewProjects = this.tErReviewProjectService.getListByCriteriaQuery(cq,false);
				for (TErReviewProjectEntity tErReviewProjectEntity : tErReviewProjects) {
					String expertId = "";
					String expertName = "";
					List<TErProjectExpertRelaEntity> TErProjectExpertRelas = this.systemService.findByProperty(TErProjectExpertRelaEntity.class, "projectId", tErReviewProjectEntity.getProjectId());
					for (TErProjectExpertRelaEntity tErProjectExpertRelaEntity : TErProjectExpertRelas) {
						TErExpertEntity tErExpert = this.systemService.getEntity(TErExpertEntity.class, tErProjectExpertRelaEntity.getExpertId());
						expertId += tErExpert.getId() + ",";
						expertName += tErExpert.getName() + ",";
					}				
					if(StringUtil.isNotEmpty(expertId)){
						expertId = expertId.substring(0, (expertId.length() - 1));
					}
					if(StringUtil.isNotEmpty(expertName)){
						expertName = expertName.substring(0, (expertName.length() - 1));
					}
			            JSONObject jo = new JSONObject();
			            jo.put("id", tErReviewProjectEntity.getProjectId());
			            jo.put("projectName", tErReviewProjectEntity.getProjectName());
			            jo.put("expertId", expertId);
			            jo.put("expertName", expertName);
			            json.add(jo);
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return json;
	}

	/**
	 * 删除评审项目信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TErReviewProjectEntity tErReviewProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tErReviewProject = systemService.getEntity(TErReviewProjectEntity.class, tErReviewProject.getId());
		message = "评审项目信息表删除成功";
		try{
			tErReviewProjectService.delete(tErReviewProject);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "评审项目信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除评审项目信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "评审项目信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TErReviewProjectEntity tErReviewProject = systemService.getEntity(TErReviewProjectEntity.class, 
				id
				);
				tErReviewProjectService.delete(tErReviewProject);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "评审项目信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加评审项目信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TErReviewProjectEntity tErReviewProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "评审项目信息表添加成功";
		try{
			tErReviewProjectService.save(tErReviewProject);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "评审项目信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新评审项目信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TErReviewProjectEntity tErReviewProject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "评审项目信息表更新成功";
		TErReviewProjectEntity t = tErReviewProjectService.get(TErReviewProjectEntity.class, tErReviewProject.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tErReviewProject, t);
			tErReviewProjectService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "评审项目信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 评审项目信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TErReviewProjectEntity tErReviewProject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tErReviewProject.getId())) {
			tErReviewProject = tErReviewProjectService.getEntity(TErReviewProjectEntity.class, tErReviewProject.getId());
			req.setAttribute("tErReviewProjectPage", tErReviewProject);
		}
        return new ModelAndView("com/kingtake/expert/reviewproject/tErReviewProject-add");
	}
		
	/**
	 * 评审项目信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TErReviewProjectEntity tErReviewProject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tErReviewProject.getId())) {
			tErReviewProject = tErReviewProjectService.getEntity(TErReviewProjectEntity.class, tErReviewProject.getId());
			req.setAttribute("tErReviewProjectPage", tErReviewProject);
		}
        return new ModelAndView("com/kingtake/expert/reviewproject/tErReviewProject-update");
	}
	
    /**
     * 评审项目信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "saveReviewResult")
    @ResponseBody
    public AjaxJson saveReviewResult(TErReviewProjectEntity tErReviewProject, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "项目评审结果保存成功";
        TErReviewProjectEntity t = this.tErReviewProjectService.get(TErReviewProjectEntity.class,
                tErReviewProject.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tErReviewProject, t);
            TSUser user = ResourceUtil.getSessionUserName();
            t.setResultInputUserid(user.getId());
            t.setResultInputUsername(user.getRealName());
            t.setResultInputDate(new Date());
            tErReviewProjectService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目评审结果保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setSuccess(true);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/expert/reviewproject/tErReviewProjectUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TErReviewProjectEntity tErReviewProject,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TErReviewProjectEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErReviewProject, request.getParameterMap());
		List<TErReviewProjectEntity> tErReviewProjects = this.tErReviewProjectService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"评审项目信息表");
		modelMap.put(NormalExcelConstants.CLASS,TErReviewProjectEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("评审项目信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tErReviewProjects);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TErReviewProjectEntity tErReviewProject,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "评审项目信息表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TErReviewProjectEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TErReviewProjectEntity> listTErReviewProjectEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TErReviewProjectEntity.class,params);
				for (TErReviewProjectEntity tErReviewProject : listTErReviewProjectEntitys) {
					tErReviewProjectService.save(tErReviewProject);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	/**
	 * 选择项目和专家页面跳转
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "goChooseForProjectAndExpert")
	public ModelAndView goChooseForProjectAndExpert(TErReviewProjectEntity tErReviewProject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tErReviewProject.getId())) {
			tErReviewProject = systemService.getEntity(TErReviewProjectEntity.class, tErReviewProject.getId());
			if (StringUtils.isNotEmpty(tErReviewProject.getReviewMain().getId())) {
				TErReviewMainEntity tErReviewMain = systemService.getEntity(TErReviewMainEntity.class, tErReviewProject.getReviewMain().getId());
	            CriteriaQuery projectCq = new CriteriaQuery(TErReviewProjectEntity.class);
	            projectCq.eq("reviewMain.id", tErReviewMain.getId());
	            projectCq.add();
	            List<TErReviewProjectEntity> projectList = this.systemService.getListByCriteriaQuery(projectCq, false);
	            StringBuffer projectSb = new StringBuffer();
	            StringBuffer projectNameSb = new StringBuffer();
	            for (TErReviewProjectEntity project : projectList) {
	                projectSb.append(project.getProjectId()).append(",");
	                TPmProjectEntity tmpProject = this.systemService.get(TPmProjectEntity.class, project.getProjectId());
	                String projectName = tmpProject.getProjectName();
	                projectNameSb.append(projectName).append(",");
	            }
	            String projectIds = projectSb.toString();
	            String projectNames = projectNameSb.toString();
	            if (projectIds.length() > 0) {
	                projectIds = projectIds.substring(0, projectIds.length() - 1);
	                projectNames = projectNames.substring(0, projectNames.length() - 1);
	            }
	            req.setAttribute("projectIds", projectIds);
	            req.setAttribute("projectNames", projectNames);
	            CriteriaQuery expCq = new CriteriaQuery(TErMainExpertRelaEntity.class);
	            expCq.eq("teMainId", tErReviewMain.getId());
	            expCq.add();
	            List<TErMainExpertRelaEntity> expList = this.systemService.getListByCriteriaQuery(expCq, false);
	            List<TErExpertEntity> expertList = new ArrayList<TErExpertEntity>();
	            for (TErMainExpertRelaEntity relaEntity : expList) {
	                TErExpertUserEntity expert = this.systemService.get(TErExpertUserEntity.class,
	                        relaEntity.getTeExpertId());
	                TErExpertUserEntity expertTmp = new TErExpertUserEntity();
	                try {
	                    PropertyUtils.copyProperties(expertTmp, expert);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                expertTmp.setUserPwd(PasswordUtil.decrypt(expertTmp.getUserPwd(), expertTmp.getUserName(),
	                        PasswordUtil.getStaticSalt()));
	                expertList.add(expertTmp);
	            }
	            if (expertList.size() > 0) {
	            	req.setAttribute("expertListStr", JSON.toJSONString(expertList));
	            }            
	        }
		}		
        return new ModelAndView("com/kingtake/expert/reviewproject/tErReviewProject-choose");
	}
	
	/**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagridForProjectExpertList")
    @ResponseBody
    public JSON datagridForProjectExpertList(TErReviewProjectEntity tErReviewProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        JSONArray json = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TErReviewProjectEntity.class, dataGrid);
        if (StringUtil.isEmpty(tErReviewProject.getReviewMain().getId())) {
            cq.eq("reviewMain.id", "1");
        }else{
            cq.eq("reviewMain.id", tErReviewProject.getReviewMain().getId());
        }
        cq.add();
        List<TErReviewProjectEntity> tErReviewProjects = this.tErReviewProjectService.getListByCriteriaQuery(cq, false);
        for (TErReviewProjectEntity tErReviewProjectEntity : tErReviewProjects) {
            String expertId = "";
            String expertName = "";
            List<TErProjectExpertRelaEntity> TErProjectExpertRelas = this.systemService.findByProperty(
                    TErProjectExpertRelaEntity.class, "projectId", tErReviewProjectEntity.getId());
            for (TErProjectExpertRelaEntity tErProjectExpertRelaEntity : TErProjectExpertRelas) {
                TErExpertEntity tErExpert = this.systemService.getEntity(TErExpertEntity.class,
                        tErProjectExpertRelaEntity.getExpertId());
                expertId += tErExpert.getId() + ",";
                expertName += tErExpert.getName() + ",";
            }
            if (StringUtil.isNotEmpty(expertId)) {
                expertId = expertId.substring(0, (expertId.length() - 1));
            }
            if (StringUtil.isNotEmpty(expertName)) {
                expertName = expertName.substring(0, (expertName.length() - 1));
            }
            JSONObject jo = new JSONObject();
            jo.put("id", tErReviewProjectEntity.getId());
            jo.put("projectId", tErReviewProjectEntity.getProjectId());
            jo.put("projectName", tErReviewProjectEntity.getProjectName());
            jo.put("expertId", expertId);
            jo.put("expertName", expertName);
            json.add(jo);
        }
        return json;
    }
}
