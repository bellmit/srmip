package com.kingtake.office.controller.meeting;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.DictEntity;
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

import com.kingtake.office.entity.meeting.TOMeetingEntity;
import com.kingtake.office.service.meeting.TOMeetingServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;



/**
 * @Title: Controller
 * @Description: 会议登记管理
 * @author onlineGenerator
 * @date 2015-07-03 19:01:03
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOMeetingController")
public class TOMeetingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOMeetingController.class);

	@Autowired
	private TOMeetingServiceI tOMeetingService;
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
     * 获得所有会议室
     * 
     * @param request
     */
    @RequestMapping(params = "combobox")
    @ResponseBody
    public List<DictEntity> combobox(HttpServletRequest request) {
        List<DictEntity> rooms = systemService.queryDict("T_O_MEETING_ROOM", "ID", "ROOM_NAME");
        return rooms;
    }

    /**
     * 会议信息列表给待办信息
     * 
     * @return
     */
    @RequestMapping(params = "toPortal")
    public ModelAndView tONoticeForPortal(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/meeting/tOMeetingListForPortal");
    }

    /**
     * 会议登记管理列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tOMeeting")
	public ModelAndView tOMeeting(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingList");
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
	public void datagrid(TOMeetingEntity tOMeeting,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOMeetingEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOMeeting, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tOMeetingService.getDataGridReturn(cq, true);
        List<TOMeetingEntity> dataList = dataGrid.getResults();
        for (TOMeetingEntity meeting : dataList) {
            if (StringUtils.isNotEmpty(meeting.getProjectId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, meeting.getProjectId());
                meeting.setProjectName(project.getProjectName());
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * 删除会议登记管理
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOMeetingEntity tOMeeting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOMeeting = systemService.getEntity(TOMeetingEntity.class, tOMeeting.getId());
        message = "会议登记管理删除成功";
		try{
			tOMeetingService.delete(tOMeeting);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "会议登记管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 批量删除会议登记管理
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "会议登记管理删除成功";
		try{
			for(String id:ids.split(",")){
				TOMeetingEntity tOMeeting = systemService.getEntity(TOMeetingEntity.class, 
				id
				);
				tOMeetingService.delete(tOMeeting);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "会议登记管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * 添加会议登记管理
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOMeetingEntity tOMeeting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "会议登记管理添加成功";
		try{
			tOMeetingService.save(tOMeeting);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "会议登记管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 更新会议登记管理
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOMeetingEntity tOMeeting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "会议登记管理更新成功";
		TOMeetingEntity t = tOMeetingService.get(TOMeetingEntity.class, tOMeeting.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOMeeting, t);
			tOMeetingService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "会议登记管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	 /**
     * 会议室空闲验证
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doTimeValidate")
    @ResponseBody
    public boolean doTimeValidate(String beginDate, String endDate, String roomName, String id,
    		HttpServletRequest request) {
        CriteriaQuery cq = new CriteriaQuery(TOMeetingEntity.class);
        boolean flag = true ; 
        try{
            //自定义追加查询条件
            String query_beginDate_begin = request.getParameter("beginDate");
            String query_beginDate_end = request.getParameter("beginDate");
            if(StringUtil.isNotEmpty(query_beginDate_begin)){
                cq.ge("beginDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_beginDate_begin));
            }
            if(StringUtil.isNotEmpty(query_beginDate_end)){
                cq.le("beginDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_beginDate_end));
            }
            String query_endDate_begin = request.getParameter("endDate");
            String query_endDate_end = request.getParameter("endDate");
            if(StringUtil.isNotEmpty(query_endDate_begin)){
                cq.ge("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_endDate_begin));
            }
            if(StringUtil.isNotEmpty(query_endDate_end)){
                cq.le("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_endDate_end));
            }
            cq.notEq("id", id);
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.eq("meetingRoomName", roomName);
        cq.add();
        List<TOMeetingEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if(list.size() > 0 ) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 会议登记管理新增页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOMeetingEntity tOMeeting, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOMeeting.getId())) {
			tOMeeting = tOMeetingService.getEntity(TOMeetingEntity.class, tOMeeting.getId());
			req.setAttribute("tOMeetingPage", tOMeeting);
		}
		return new ModelAndView("com/kingtake/office/meeting/tOMeeting-add");
	}
	
    /**
     * 会议登记管理编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOMeetingEntity tOMeeting, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOMeeting.getId())) {
			tOMeeting = tOMeetingService.getEntity(TOMeetingEntity.class, tOMeeting.getId());
            req.setAttribute("tOMeetingPage", tOMeeting);
            if (StringUtil.isNotEmpty(tOMeeting.getProjectId())) {
                TPmProjectEntity project = tOMeetingService.getEntity(TPmProjectEntity.class, tOMeeting.getProjectId());
                req.setAttribute("projectName", project.getProjectName());
            }
		}
		return new ModelAndView("com/kingtake/office/meeting/tOMeeting-update");
	}
	
    /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingUpload");
	}
	
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOMeetingEntity tOMeeting,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOMeetingEntity.class, dataGrid);
		try {
			tOMeeting.setMeetingTitle(URLDecoder.decode(tOMeeting.getMeetingTitle(),"utf-8"));
			if(StringUtil.isNotEmpty(tOMeeting.getHostUnitName())) {
			    tOMeeting.setHostUnitName(URLDecoder.decode(tOMeeting.getHostUnitName(),"utf-8"));
			}
			tOMeeting.setMeetingRoomName(URLDecoder.decode(tOMeeting.getMeetingRoomName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOMeeting, request.getParameterMap());
		List<TOMeetingEntity> tOMeetings = this.tOMeetingService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "会议登记管理");
		modelMap.put(NormalExcelConstants.CLASS,TOMeetingEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("会议登记管理列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOMeetings);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOMeetingEntity tOMeeting,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "会议登记管理");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOMeetingEntity.class);
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
				List<TOMeetingEntity> listTOMeetingEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOMeetingEntity.class,params);
				for (TOMeetingEntity tOMeeting : listTOMeetingEntitys) {
					tOMeetingService.save(tOMeeting);
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
     * 待办信息
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForPortal")
    public void datagridForPortal(HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int start = (page - 1) * rows + 1;
        int end = (page) * rows;
        TSUser user = ResourceUtil.getSessionUserName();
        Map<String, String> param = new HashMap<String, String>();
        param.put("userId", user.getId());
        List<Map<String, String>> results = this.tOMeetingService.getPortalList(param, start, end);
        Integer count = this.tOMeetingService.getPortalCount(param);
        dataGrid.setResults(results);
        dataGrid.setTotal(count);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 待办信息
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "portalList")
    public ModelAndView portalList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        return new ModelAndView("com/kingtake/office/meeting/portalList");
    }

    /**
     * 获得空闲的会议室
     * 
     * @param request
     */
    @RequestMapping(params = "getFreeMeetingRooms")
    @ResponseBody
    public List<Map<String, Object>> getFreeMeetingRooms(HttpServletRequest request) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String sql = "select mr.id, mr.room_name name from t_o_meeting_room mr where mr.id not in"
                + " (select r.id from t_o_meeting_room r join t_o_meeting m on r.id = m.meeting_room_id where"
                + " ((m.begin_date <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')  and m.end_date >= to_date(?, 'yyyy-mm-dd hh24:mi:ss')) or"
                + " (m.begin_date <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and m.end_date >= to_date(?, 'yyyy-mm-dd hh24:mi:ss'))))";
        List<Map<String, Object>> rooms = systemService.findForJdbc(sql, new Object[] { beginDate, beginDate, endDate,
                endDate });
        return rooms;
    }

    /**
     * 会议登记管理新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goMeetingUserInfo")
    public ModelAndView goMeetingUserInfo(TOMeetingEntity tOMeeting, HttpServletRequest req) {
        String meetingRoomId = req.getParameter("meetingRoomId");
        if (StringUtil.isNotEmpty(meetingRoomId)) {
            req.setAttribute("meetingRoomId", meetingRoomId);
        }
        return new ModelAndView("com/kingtake/office/meeting/tOMeetingUseInfoList");
    }
}
