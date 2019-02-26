package com.kingtake.office.controller.meeting;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
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
import org.jeecgframework.web.system.pojo.base.TSDepart;
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

import com.kingtake.office.entity.meeting.TOMeetingRoomEntity;
import com.kingtake.office.service.meeting.TOMeetingRoomServiceI;



/**   
 * @Title: Controller
 * @Description: 会议室
 * @author onlineGenerator
 * @date 2015-07-02 19:17:21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOMeetingRoomController")
public class TOMeetingRoomController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOMeetingRoomController.class);

	@Autowired
	private TOMeetingRoomServiceI tOMeetingRoomService;
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
	 * 会议室列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOMeetingRoom")
	public ModelAndView tOMeetingRoom(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingRoomList");
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
	public void datagrid(TOMeetingRoomEntity tOMeetingRoom,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOMeetingRoomEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOMeetingRoom, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOMeetingRoomService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除会议室
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOMeetingRoomEntity tOMeetingRoom, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOMeetingRoom = systemService.getEntity(TOMeetingRoomEntity.class, tOMeetingRoom.getId());
		message = "会议室删除成功";
		try{
			tOMeetingRoomService.delete(tOMeetingRoom);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "会议室删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除会议室
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "会议室删除成功";
		try{
			for(String id:ids.split(",")){
				TOMeetingRoomEntity tOMeetingRoom = systemService.getEntity(TOMeetingRoomEntity.class, 
				id
				);
				tOMeetingRoomService.delete(tOMeetingRoom);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "会议室删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
     * 添加或更新会议室
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TOMeetingRoomEntity tOMeetingRoom, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "会议室添加成功";
        if (StringUtil.isNotEmpty(tOMeetingRoom.getId())) {
            TOMeetingRoomEntity t = tOMeetingRoomService.get(TOMeetingRoomEntity.class, tOMeetingRoom.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tOMeetingRoom, t);
                tOMeetingRoomService.updateEntitie(t);
                message = MutiLangUtil.paramUpdSuccess("会议室");
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = MutiLangUtil.paramUpdFail("会议室");
                throw new BusinessException(e.getMessage());
            }
        }else{
            try{
                tOMeetingRoomService.save(tOMeetingRoom);
                message = MutiLangUtil.paramAddSuccess("会议室");
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }catch(Exception e){
                e.printStackTrace();
                message = "会议室添加失败";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setMsg(message);
        return j;
    }
	
	/**
	 * 会议室新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOMeetingRoomEntity tOMeetingRoom, HttpServletRequest req) {
	    List<TSDepart> departList = systemService.getList(TSDepart.class);
        req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(tOMeetingRoom.getId())) {
			tOMeetingRoom = tOMeetingRoomService.getEntity(TOMeetingRoomEntity.class, tOMeetingRoom.getId());
		}
		req.setAttribute("tOMeetingRoomPage", tOMeetingRoom);
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingRoom");
	}
	/**
	 * 会议室编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOMeetingRoomEntity tOMeetingRoom, HttpServletRequest req) {
	    List<TSDepart> departList = systemService.getList(TSDepart.class);
        req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(tOMeetingRoom.getId())) {
			tOMeetingRoom = tOMeetingRoomService.getEntity(TOMeetingRoomEntity.class, tOMeetingRoom.getId());
		}
		req.setAttribute("tOMeetingRoomPage", tOMeetingRoom);
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingRoom");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/meeting/tOMeetingRoomUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOMeetingRoomEntity tOMeetingRoom,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOMeetingRoomEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOMeetingRoom, request.getParameterMap());
		List<TOMeetingRoomEntity> tOMeetingRooms = this.tOMeetingRoomService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"会议室");
		modelMap.put(NormalExcelConstants.CLASS,TOMeetingRoomEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("会议室列表", "导出人："
		        +ResourceUtil.getSessionUserName().getRealName()+"  导出时间："+DateUtils.formatDate2(),"导出信息"));
		System.out.println(DateUtils.formatDate2());
		modelMap.put(NormalExcelConstants.DATA_LIST,tOMeetingRooms);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOMeetingRoomEntity tOMeetingRoom,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "会议室");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOMeetingRoomEntity.class);
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
				List<TOMeetingRoomEntity> listTOMeetingRoomEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOMeetingRoomEntity.class,params);
				for (TOMeetingRoomEntity tOMeetingRoom : listTOMeetingRoomEntitys) {
					tOMeetingRoomService.save(tOMeetingRoom);
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
}