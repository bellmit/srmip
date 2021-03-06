package com.kingtake.office.controller.information;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
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

import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.information.TOHandoverEntity;
import com.kingtake.office.service.information.TOHandoverServiceI;



/**   
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-07-13 17:03:15
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOHandoverController")
public class TOHandoverController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOHandoverController.class);

	@Autowired
	private TOHandoverServiceI tOHandoverService;
	@Autowired
	private TBCodeTypeServiceI tCodeTypeService;
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
	 * ???????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOHandover")
	public ModelAndView tOHandover(HttpServletRequest request) {
	    TSUser user = ResourceUtil.getSessionUserName();
	    String uid = user.getId();
	    request.setAttribute("uid", uid);
	    request.setAttribute("submitNo", SrmipConstants.SUBMIT_NO);
	    request.setAttribute("submitYes", SrmipConstants.SUBMIT_YES);
	    request.setAttribute("submitReturn", SrmipConstants.SUBMIT_RETURN);
	    request.setAttribute("submitReceive", SrmipConstants.SUBMIT_RECEIVE);
		return new ModelAndView("com/kingtake/office/information/tOHandoverList");
	}

	/**
	 * easyui AJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TOHandoverEntity tOHandover,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOHandoverEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOHandover, request.getParameterMap());
		try{
		    //?????????????????????????????????????????????1-????????? 3-?????????
		    String[] visibleFlagArr = {SrmipConstants.SUBMIT_YES, SrmipConstants.SUBMIT_RECEIVE};
		    //???????????????????????????????????????????????????????????????????????????????????????visibleFlagArr???????????????????????????
            cq.or(Restrictions.eq("handoverId", ResourceUtil.getSessionUserName().getId()),
                    Restrictions.and(Restrictions.eq("receiver", ResourceUtil.getSessionUserName().getId()), 
                            Restrictions.in("submitFlag", visibleFlagArr)));
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOHandoverService.getDataGridReturn(cq, true);
		List<Object> results = dataGrid.getResults();
        for (Object obj : results) {
            TOHandoverEntity tmp = (TOHandoverEntity) obj;
            if (StringUtils.isNotEmpty(tmp.getReceiver()) && StringUtils.isEmpty(tmp.getReceiverName())) {
                TSUser receiverUser = this.systemService.get(TSUser.class, tmp.getReceiver());
                tmp.setReceiverName(receiverUser.getRealName());
            }
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOHandoverEntity tOHandover, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOHandover = systemService.getEntity(TOHandoverEntity.class, tOHandover.getId());
		message = "??????????????????????????????";
		try{
			tOHandoverService.delete(tOHandover);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TOHandoverEntity tOHandover = systemService.getEntity(TOHandoverEntity.class, 
				id
				);
				tOHandoverService.delete(tOHandover);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * ??????-????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddUpdate")
	@ResponseBody
	public AjaxJson doAddUpdate(TOHandoverEntity tOHandover, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
	    try {
	        if(StringUtil.isNotEmpty(tOHandover.getId())){
	            message = "??????????????????????????????";
	            TOHandoverEntity t = tOHandoverService.get(TOHandoverEntity.class, tOHandover.getId());
	            // ???????????????????????????
	            MyBeanUtils.copyBeanNotNull2Bean(tOHandover, t);
	            tOHandoverService.updateEntitie(t);
	            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
	        }else{
	            message = "??????????????????????????????";
	            tOHandoverService.save(tOHandover);
	            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
	        }    
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "??????????????????????????????";
	        throw new BusinessException(e.getMessage());
	    }
		j.setObj(tOHandover);
		j.setMsg(message);
		return j;
	}
	
	/**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOHandoverEntity tOHandover, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(tOHandover.getId())){
            message = "????????????????????????";
            try {
                this.tOHandoverService.doSubmit(tOHandover);
                j.setSuccess(true);
            } catch (Exception e) {
                e.printStackTrace();
                message = "????????????????????????";
            }
        }
        j.setObj(tOHandover);
        j.setMsg(message);
        return j;
    }
    
    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doRecieve")
    @ResponseBody
    public AjaxJson doRecieve(TOHandoverEntity tOHandover, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(tOHandover.getId())){
            message = "????????????????????????";
            TOHandoverEntity t = tOHandoverService.get(TOHandoverEntity.class, tOHandover.getId());
            try {
                // ???????????????????????????
                MyBeanUtils.copyBeanNotNull2Bean(tOHandover, t);
                //???????????????????????????????????????3-?????????????????????????????????????????????
                t.setSubmitFlag(SrmipConstants.SUBMIT_RECEIVE);
                t.setRecieveTime(DateUtils.getDate());
                tOHandoverService.updateEntitie(t);
            } catch (Exception e) {
                e.printStackTrace();
                message = "??????????????????????????????";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setObj(tOHandover);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doReturn")
    @ResponseBody
    public AjaxJson doReturn(TOHandoverEntity tOHandover, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(tOHandover.getId())){
            message = "????????????????????????";
            TOHandoverEntity t = tOHandoverService.get(TOHandoverEntity.class, tOHandover.getId());
            try {
                // ???????????????????????????
                MyBeanUtils.copyBeanNotNull2Bean(tOHandover, t);
                //?????????????????????????????????????????????2-????????????????????????????????????????????????????????????null
                t.setSubmitFlag(SrmipConstants.SUBMIT_RETURN);
                t.setSubmitTime(DateUtils.getDate());
                t.setRecieveTime(null);
                tOHandoverService.updateEntitie(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "??????????????????????????????";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setObj(tOHandover);
        j.setMsg(message);
        return j;
    }
	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOHandoverEntity tOHandover, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOHandover.getId())) {
			tOHandover = tOHandoverService.getEntity(TOHandoverEntity.class, tOHandover.getId());
		}else{
		    //???????????????????????????????????????????????????????????????????????????????????????????????????0-?????????
            TSUser user = ResourceUtil.getSessionUserName();
            TSDepart depart = user.getCurrentDepart();
            tOHandover.setHandoverId(user.getId());
            tOHandover.setHandoverName(user.getRealName());
            tOHandover.setHandoverDepartId(depart.getId());
            tOHandover.setHandoverDepartName(depart.getDepartname());
		    tOHandover.setSubmitFlag(SrmipConstants.SUBMIT_NO);
		    tOHandover.setHandoverTime(DateUtils.getDate());
		}
		req.setAttribute("tOHandoverPage", tOHandover);
		return new ModelAndView("com/kingtake/office/information/tOHandover");
	}
	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOHandoverEntity tOHandover, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOHandover.getId())) {
			tOHandover = tOHandoverService.getEntity(TOHandoverEntity.class, tOHandover.getId());
			//???????????????id???????????????????????????
            if(StringUtil.isNotEmpty(tOHandover.getReceiver())){
                TSUser user = systemService.getEntity(TSUser.class, tOHandover.getReceiver());
                if(user != null ){
                    tOHandover.setReceiverName(user.getRealName());
                }
            }
			//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????3-???????????????????????????????????????
			if(!ResourceUtil.getSessionUserName().getId().equals(tOHandover.getHandoverId()) && 
			        ResourceUtil.getSessionUserName().getId().equals(tOHandover.getReceiver()) &&
			        (tOHandover.getRecieveTime() == null) && 
			        !SrmipConstants.SUBMIT_RECEIVE.equals(tOHandover.getSubmitFlag())){
			    tOHandover.setSubmitFlag(SrmipConstants.SUBMIT_RECEIVE);
			    tOHandover.setRecieveTime(DateUtils.getDate());
			    tOHandoverService.updateEntitie(tOHandover);
			}
			req.setAttribute("tOHandoverPage", tOHandover);
		}
		return new ModelAndView("com/kingtake/office/information/tOHandover");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/information/tOHandoverUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOHandoverEntity tOHandover,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        String ids = request.getParameter("ids");
        List<TOHandoverEntity> tOHandovers = null;
		CriteriaQuery cq = new CriteriaQuery(TOHandoverEntity.class, dataGrid);
        if (StringUtils.isEmpty(ids)) {
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOHandover, request.getParameterMap());
		//?????????????????????????????????????????????1-????????? 3-?????????
	    String[] visibleFlagArr = {SrmipConstants.SUBMIT_YES, SrmipConstants.SUBMIT_RECEIVE};
	    //???????????????????????????????????????????????????????????????????????????????????????visibleFlagArr???????????????????????????
        cq.or(Restrictions.eq("handoverId", ResourceUtil.getSessionUserName().getId()),
                Restrictions.and(Restrictions.eq("receiver", ResourceUtil.getSessionUserName().getId()), 
                        Restrictions.in("submitFlag", visibleFlagArr)));
        cq.addOrder("handoverTime", SortDirection.asc);
        cq.add();
            tOHandovers = this.tOHandoverService.getListByCriteriaQuery(cq, false);
        } else {
            String[] idArray = ids.split(",");
            tOHandovers = new ArrayList<TOHandoverEntity>();
            for (String id : idArray) {
                TOHandoverEntity handOver = this.tOHandoverService.get(TOHandoverEntity.class, id);
                tOHandovers.add(handOver);
            }
        }
		//???????????????id???????????????????????????
		for(TOHandoverEntity toh:tOHandovers){
		    if(StringUtil.isNotEmpty(toh.getReceiver())){
		        TSUser user = systemService.getEntity(TSUser.class, toh.getReceiver());
		        if(user != null ){
		            toh.setReceiverName(user.getRealName());
		        }
		    }
		}
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer bzgznr = new StringBuffer();//??????????????????
        StringBuffer xzgzjh = new StringBuffer();//??????????????????
        StringBuffer zygzqk = new StringBuffer();//??????????????????
        for (int i = 0;i<tOHandovers.size();i++) {
            TOHandoverEntity toh = tOHandovers.get(i);
            bzgznr.append((i + 1) + "."+"\t");
            bzgznr.append(DateUtils.formatDate(toh.getHandoverTime(), "MM???dd??????"));
            bzgznr.append(" ");
            bzgznr.append(getVal(toh.getHandoverContent())+"???");
            bzgznr.append(" (" + toh.getHandoverName() + ")");
            bzgznr.append("\r\n");

            xzgzjh.append((i + 1) + ".");
            xzgzjh.append(getVal(toh.getNextWeekWorkContent())+"???");
            xzgzjh.append(" (" + toh.getHandoverName() + ")");
            xzgzjh.append("\r\n");

            zygzqk.append((i + 1) + ".");
            zygzqk.append(getVal(toh.getMainTask())+"???");
            zygzqk.append(" (" + toh.getHandoverName() + ")");
            zygzqk.append("\r\n");
        }
        map.put("exportTime", DateUtils.formatDate());
        map.put("bzgzyd", bzgznr.toString());
        map.put("xzgzjh", xzgzjh.toString());
        map.put("cdzyrwqk", zygzqk.toString());
        modelMap.put(TemplateWordConstants.FILE_NAME, "????????????_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/jbcl.docx");
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}

    /**
     * ?????????
     * 
     * @param content
     * @return
     */
    private String getVal(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
            return content;
    }

	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOHandoverEntity tOHandover,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOHandoverEntity.class);
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
			MultipartFile file = entity.getValue();// ????????????????????????
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TOHandoverEntity> listTOHandoverEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOHandoverEntity.class,params);
				for (TOHandoverEntity tOHandover : listTOHandoverEntitys) {
					tOHandoverService.save(tOHandover);
				}
				j.setMsg("?????????????????????");
			} catch (Exception e) {
				j.setMsg("?????????????????????");
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
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "goCopy")
    public ModelAndView goCopy(TOHandoverEntity tOHandover, HttpServletRequest request) {
        String ids = request.getParameter("ids");
        if (StringUtils.isEmpty(ids)) {
            request.setAttribute("tOHandoverPage", tOHandover);
        } else {
            String[] idList = ids.split(",");
            StringBuffer handoverContentSb = new StringBuffer();
            StringBuffer nextWeekWorkContentSb = new StringBuffer();
            StringBuffer mainTaskSb = new StringBuffer();
            StringBuffer remarkSb = new StringBuffer();
            for (String id : idList) {
                TOHandoverEntity tmp = this.systemService.get(TOHandoverEntity.class, id);
                if (handoverContentSb.length() > 0) {
                    handoverContentSb.append("\n");
                }
                if (nextWeekWorkContentSb.length() > 0) {
                    nextWeekWorkContentSb.append("\n");
                }
                if (mainTaskSb.length() > 0) {
                    mainTaskSb.append("\n");
                }
                if (remarkSb.length() > 0) {
                    remarkSb.append("\n");
                }
                if (StringUtils.isNotEmpty(tmp.getHandoverContent())) {
                    handoverContentSb.append(tmp.getHandoverContent());
                }
                if (StringUtils.isNotEmpty(tmp.getNextWeekWorkContent())) {
                    nextWeekWorkContentSb.append(tmp.getNextWeekWorkContent());
                }
                if (StringUtils.isNotEmpty(tmp.getMainTask())) {
                    mainTaskSb.append(tmp.getMainTask());
                }
                if (StringUtils.isNotEmpty(tmp.getRemark())) {
                    remarkSb.append(tmp.getRemark());
                }
            }
            tOHandover.setHandoverContent(handoverContentSb.toString());
            tOHandover.setNextWeekWorkContent(nextWeekWorkContentSb.toString());
            tOHandover.setMainTask(mainTaskSb.toString());
            tOHandover.setRemark(remarkSb.toString());
            TSUser user = ResourceUtil.getSessionUserName();
            TSDepart depart = user.getCurrentDepart();
            tOHandover.setHandoverId(user.getId());
            tOHandover.setHandoverName(user.getRealName());
            tOHandover.setHandoverDepartId(depart.getId());
            tOHandover.setHandoverDepartName(depart.getDepartname());
            tOHandover.setSubmitFlag(SrmipConstants.SUBMIT_NO);
            tOHandover.setHandoverTime(DateUtils.getDate());
            request.setAttribute("tOHandoverPage", tOHandover);
        }
        return new ModelAndView("com/kingtake/office/information/tOHandover");
    }
}
