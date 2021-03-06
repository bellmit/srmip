package org.jeecgframework.workflow.controller.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StreamUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.web.cgform.service.migrate.MigrateForm;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.pojo.base.TPForm;
import org.jeecgframework.workflow.pojo.base.TPFormpro;
import org.jeecgframework.workflow.pojo.base.TPListerer;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessListener;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.pojo.base.TPProcessnodeFunction;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSTable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @ClassName: ProcessController
 * @Description: TODO(?????????????????????)
 * @author jeecg
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/processController")
public class ProcessController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProcessController.class);
	private UserService userService;
	private SystemService systemService;
	private String message;
	private ActivitiService activitiService;
	protected RepositoryService repositoryService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	public void setActivitiService(ActivitiService activitiService) {
		this.activitiService = activitiService;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processDesigner")
	public ModelAndView processDesigner(HttpServletRequest request) {
		String processid = oConvertUtils.getString(request.getParameter("id"), "0");
		request.setAttribute("processid", processid);// ??????ID
		return new ModelAndView("designer/index");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	//update-begin--Author:chenxu  Date:20130408 for?????????????????????????????????????????????
	@RequestMapping(params = "processProperties")
	public ModelAndView processProperties(HttpServletRequest request) {
		String turn = oConvertUtils.getString(request.getParameter("turn"));
		String id = oConvertUtils.getString(request.getParameter("id"));// ??????????????????Id
		String checkbox = oConvertUtils.getString(request.getParameter("checkbox"));// ??????????????????
		String processId = oConvertUtils.getString(request.getParameter("processId"));// ??????ID
		TPProcess tProcess = systemService.findUniqueByProperty(TPProcess.class, "id", processId);
		if (tProcess != null) {
			request.setAttribute("processDefinitionId", tProcess.getId());
			if(tProcess.getTSType() != null){
				request.setAttribute("typeId", tProcess.getTSType().getId());
			}
			
		}
		TSTypegroup typegroup = systemService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "process");
		List<TSType> proTypeList = systemService.findByProperty(TSType.class, "TSTypegroup.id", typegroup.getId());
		request.setAttribute("checkbox", checkbox);
		request.setAttribute("id", id);
		request.setAttribute("proTypeList", proTypeList);
		request.setAttribute("processId", processId);
		return new ModelAndView("designer/" + turn + "");
	}
	//update-end--Author:chenxu  Date:20130408 for?????????????????????????????????????????????
	/**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processIframe")
	public ModelAndView processIframe(HttpServletRequest request) {
		String typeid = request.getParameter("typeid");
		request.setAttribute("typeid", typeid);
		List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class, "typegroupcode", "process");
		request.setAttribute("typegroupList", typegroupList);
		return new ModelAndView("workflow/process/processIframe");
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processComboBox")
	@ResponseBody
	public List<ComboBox> processComboBox(HttpServletResponse response, HttpServletRequest request) {
		ComboBox comboBox = new ComboBox();
		comboBox.setId("typecode");
		comboBox.setText("typename");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		TSTypegroup typegroup = systemService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "process");
		List<TSType> proTypeList = systemService.findByProperty(TSType.class, "TSTypegroup.id", typegroup.getId());
		comboBoxs = TagUtil.getComboBox(proTypeList, null, comboBox);
		return comboBoxs;
	}

	/**
	 * ????????????????????????
	 */
	@RequestMapping(params = "processTypeTree")
	@ResponseBody
	public List<ComboTree> processTypeTree(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSType.class);
		if (comboTree.getId() != null) {
			cq.eq("TSType.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSType");
		}
		// cq.eq("typegroupcode","process");
		cq.add();
		List<TSType> typeList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "typename", "TSTypes", "typecode");
		comboTrees = systemService.ComboTree(typeList, comboTreeModel, null,true);
		return comboTrees;
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processList")
	public ModelAndView processList(HttpServletRequest request) {
		String typeid = request.getParameter("typeid");
		request.setAttribute("typeid", typeid);
		return new ModelAndView("workflow/process/processList");
	}

	/**
	 * ????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getProcessXml")
	@ResponseBody
	public void getProcessXml(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/xml;charset=UTF-8");
		String processId = oConvertUtils.getString(request.getParameter("processId"));
		TPProcess tProcess = systemService.getEntity(TPProcess.class, processId);
		String retstr;
		try {
			retstr = StreamUtils.InputStreamTOString(StreamUtils.byteTOInputStream(tProcess.getProcessxml()));
			response.getWriter().write(retstr);
			//System.out.println(retstr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @return ????????????
	 */
	@RequestMapping(params = "saveProcess")
	@ResponseBody
	public AjaxJson saveProcess(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String processDefinitionId = oConvertUtils.getString(request.getParameter("processDefinitionId"));
		String processDescriptor = oConvertUtils.getString(request.getParameter("processDescriptor"));
		String processName = oConvertUtils.getString(request.getParameter("processName"));
		String processkey = oConvertUtils.getString(request.getParameter("processkey"));// ??????ID
		String params = oConvertUtils.getString(request.getParameter("params"));
		String typeid = oConvertUtils.getString(request.getParameter("typeid"));// ????????????
		String nodes = oConvertUtils.getString(request.getParameter("nodes"));// ????????????
		TSType tsType = systemService.getEntity(TSType.class, typeid);// ????????????
		TPProcess tProcess = systemService.getEntity(TPProcess.class, processDefinitionId);
		if (tProcess != null) {
			tProcess.setProcessname(processName);
			tProcess.setTSType(tsType);
			tProcess.setProcesskey(processkey);
			if (!processDescriptor.equals("")) {
				tProcess.setProcessxml(StreamUtils.StringTObyte(processDescriptor));
			}
			systemService.updateEntitie(tProcess);
			j.setMsg("??????????????????");
		} else {
			List<TPProcess> processes = systemService.findByProperty(TPProcess.class, "processkey", processkey);
			if (processes.size() == 0) {
				tProcess = new TPProcess();
				tProcess.setProcessname(processName);
				tProcess.setProcessstate(WorkFlowGlobals.Process_Deploy_NO);
				tProcess.setTSType(tsType);
				tProcess.setProcesskey(processkey);
				if (!processDescriptor.equals("")) {
					tProcess.setProcessxml(StreamUtils.StringTObyte(processDescriptor));
				}
				systemService.save(tProcess);
				j.setMsg("??????????????????");
			} else {
				j.setMsg("??????ID?????????");
			}
		}
		if (nodes != null && nodes.length() > 3) {
			String[] temp = nodes.split("@@@");
			for (int i = 0; i < temp.length; i++) {
				TPProcessnode tProcessnode = null;
				String[] fileds = temp[i].split("###");
				String tid = fileds[0].substring(3);
				String name = fileds[1].substring(9);
				tProcessnode = activitiService.getTPProcessnode(tid, processkey);
				if (tProcessnode == null) {
					tProcessnode = new TPProcessnode();
					tProcessnode.setProcessnodecode(tid);
					tProcessnode.setProcessnodename(name);
					tProcessnode.setTPProcess(tProcess);
					tProcessnode.setTPForm(null);
					systemService.save(tProcessnode);
				} else {
					tProcessnode.setProcessnodecode(tid);
					tProcessnode.setProcessnodename(name);
					tProcessnode.setTPProcess(tProcess);
					tProcessnode.setTPForm(null);
					systemService.updateEntitie(tProcessnode);
				}

			}
		}

		return j;
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addOrupdateVariable")
	public ModelAndView addOrupdateVariable(TPProcesspro processpro,

	HttpServletRequest request) {
		TPProcessnode processnode = null;
		String processproid = ResourceUtil.getParameter("processproid");// ????????????ID
		String processId = ResourceUtil.getParameter("processId");// ????????????ID
		String processNode = request.getParameter("processNode");// ??????????????????
		String processDefinitionId = request.getParameter("processDefinitionId");// ????????????ID
		request.setAttribute("processid", processId);
		if (processpro.getId() != null) {
			processpro = systemService.getEntity(TPProcesspro.class, processpro.getId());
			processnode = processpro.getTPProcessnode();
			request.setAttribute("processpro", processpro);
			request.setAttribute("processnode", processnode);
		}
		request.setAttribute("processId", processId);
		request.setAttribute("processNode", processNode);
		request.setAttribute("processDefinitionId", processDefinitionId);
		return new ModelAndView("designer/processpro");
	}

	/**
	 * ??????????????????
	 * 
	 */
	@RequestMapping(params = "saveVariable")
	@ResponseBody
	public AjaxJson saveVariable(TPProcesspro tProcesspro, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String processproId = ResourceUtil.getParameter("processproid");// ????????????ID
		String processId = ResourceUtil.getParameter("processId");// ????????????ID
		String processNode = ResourceUtil.getParameter("procesnode");// ??????????????????
		String processDefinitionId = ResourceUtil.getParameter("processDefinitionId");// ????????????ID
		TPProcess tProcess = null;// ????????????
		TPProcessnode tProcessnode = null;// ????????????
		if (StringUtil.isNotEmpty(processDefinitionId)) {
			tProcess = systemService.getEntity(TPProcess.class, oConvertUtils.getString(processDefinitionId));// ????????????????????????
		} else {
			if (StringUtil.isNotEmpty(processId)) {
				tProcess = systemService.findUniqueByProperty(TPProcess.class, "processkey", processId);
				if (tProcess == null) {
					tProcess = new TPProcess();
					tProcess.setProcesskey(processId);
					systemService.save(tProcess);// ????????????
				} else {
					systemService.updateEntitie(tProcess);// ????????????
				}
			}
		}
		if (StringUtil.isNotEmpty(processNode)) {
			tProcessnode = systemService.findUniqueByProperty(TPProcessnode.class, "processnodecode", processNode);
			if (tProcessnode == null) {
				tProcessnode = new TPProcessnode();
				tProcessnode.setTPProcess(tProcess);
				tProcessnode.setTPForm(null);
				tProcessnode.setProcessnodecode(processNode);
				systemService.save(tProcessnode);// ????????????
			} else {
				tProcessnode.setTPProcess(tProcess);
				tProcessnode.setTPForm(null);
				tProcessnode.setProcessnodecode(processNode);
				systemService.updateEntitie(tProcessnode);// ????????????
			}
		}

		if (StringUtil.isNotEmpty(processproId)) {
			tProcesspro.setTPProcess(tProcess);
			systemService.updateEntitie(tProcesspro);
		} else {
			tProcesspro.setTPProcess(tProcess);
			tProcesspro.setTPProcessnode(tProcessnode);
			systemService.save(tProcesspro);
		}
		j.setMsg("??????????????????!");

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param variableId
	 *            ????????????ID
	 */
	@RequestMapping(params = "deleteVariable")
	@ResponseBody
	public AjaxJson deleteVariable(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String variableId = oConvertUtils.getString(request.getParameter("variableId"));
		systemService.deleteEntityById(TPProcesspro.class, variableId);
		j.setMsg("??????????????????!");

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getVariables")
	@ResponseBody
	public void getVariables(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String processNode = oConvertUtils.getString(request.getParameter("processNode"));// ??????????????????
		String processId = oConvertUtils.getString(request.getParameter("processId"));// ????????????id
		TPProcess tProcess = null;
		if (StringUtil.isNotEmpty(processId)) {
			tProcess = systemService.findUniqueByProperty(TPProcess.class, "processkey", processId);
		}
		if (tProcess != null) {
			CriteriaQuery cq = new CriteriaQuery(TPProcesspro.class, dataGrid);
			cq.createAlias("TPProcessnode", "TPProcessnode");
			cq.eq("TPProcessnode.processnodecode", processNode);
			cq.eq("TPProcess.id", tProcess.getId());
			cq.add();
			this.systemService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getVariable")
	@ResponseBody
	public void getVariable(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String variableId = oConvertUtils.getString(request.getParameter("variableId"));
		CriteriaQuery cq = new CriteriaQuery(TPProcesspro.class, dataGrid);
		cq.eq("processproid", variableId);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processTabs")
	public ModelAndView processTabs(HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		return new ModelAndView("workflow/process/processTabs");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processpro")
	public ModelAndView processpro(HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		return new ModelAndView("workflow/process/processproList");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "busbase")
	public ModelAndView busbase(HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		return new ModelAndView("workflow/process/busbaseList");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processtype")
	public ModelAndView processtype() {
		return new ModelAndView("workflow/process/processtypeList");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "processnode")
	public ModelAndView processnode(HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		return new ModelAndView("workflow/processnode/processnodeList");
	}

	/**
	 * ????????????????????????
	 */

	@RequestMapping(params = "processGrid")
	public void processGrid(TPProcess tPProcess, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typeid = oConvertUtils.getString(request.getParameter("typeid"));
		CriteriaQuery cq = new CriteriaQuery(TPProcess.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPProcess,request.getParameterMap());
		if (StringUtil.isNotEmpty(typeid)) {
			cq.eq("TSType.id", typeid);
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????
	 */

	@RequestMapping(params = "processproList")
	public void processproList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String processid = request.getParameter("processid");
		CriteriaQuery cq = new CriteriaQuery(TPProcesspro.class, dataGrid);
		if (StringUtil.isNotEmpty(processid)) {
			cq.eq("TPProcess.id", processid);
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX??????????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridBus")
	public void datagridBus(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String processid = request.getParameter("processid");
		CriteriaQuery cq = new CriteriaQuery(TSBusConfig.class, dataGrid);
		if (StringUtil.isNotEmpty(processid)) {
			cq.eq("TPProcess.id", processid);
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridtype")
	public void datagridtype(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSType.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		;
	}

	/**
	 * easyuiAJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridNode")
	public void datagridNode(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String processid = request.getParameter("processid");
		CriteriaQuery cq = new CriteriaQuery(TPProcessnode.class, dataGrid);
		if (StringUtil.isNotEmpty(processid)) {
			cq.eq("TPProcess.id", processid);
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(TSType processtype, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		processtype = systemService.getEntity(TSType.class, processtype.getId());
		message = "????????????: " + processtype.getTypename() + "????????? ??????";
		systemService.delete(processtype);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delBus")
	@ResponseBody
	public AjaxJson delBus(TSBusConfig busbase, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        busbase = systemService.getEntity(TSBusConfig.class, busbase.getId());
        message = "????????????: " + busbase.getBusname() + "???????????????";
        try {
            systemService.delete(busbase);
        } catch (Exception e) {
            message = "???????????????" + busbase.getBusname() + "??????????????????";
            throw new RuntimeException(message, e);
        }
        j.setMsg(message);
        return j;
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delprocess")
	@ResponseBody
	public AjaxJson delprocess(TPProcess process, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		process = systemService.getEntity(TPProcess.class, process.getId());
		message = "??????: " + process.getProcessname() + "????????? ??????";
		systemService.delete(process);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delPro")
	@ResponseBody
	public AjaxJson delPro(TPProcesspro processpro, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		processpro = systemService.getEntity(TPProcesspro.class, processpro.getId());
		message = "????????????: " + processpro.getProcessproname() + "????????? ??????";
		systemService.delete(processpro);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ?????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delNode")
	@ResponseBody
	public AjaxJson delNode(TPProcessnode processnode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		processnode = systemService.getEntity(TPProcessnode.class, processnode.getId());
		message = "????????????: " + processnode.getProcessnodename() + "????????? ??????";
		systemService.delete(processnode);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveType")
	@ResponseBody
	public AjaxJson saveType(TSType processtype, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (processtype.getId() != null) {
			message = "????????????: " + processtype.getTypename() + "???????????????";
			userService.saveOrUpdate(processtype);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "????????????: " + processtype.getTypename() + "???????????????";
			userService.saveOrUpdate(processtype);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveBus")
	@ResponseBody
	public AjaxJson saveBus(TSBusConfig busbase, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(busbase.getId())) {
			message = "????????????: " + busbase.getBusname() + "???????????????";
			userService.saveOrUpdate(busbase);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "????????????: " + busbase.getBusname() + "???????????????";
			userService.save(busbase);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "savePro")
	@ResponseBody
	public AjaxJson savePro(TPProcesspro processpro, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(processpro.getId())) {
			message = "????????????: " + processpro.getProcessproname() + "???????????????";
			userService.saveOrUpdate(processpro);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "????????????: " + processpro.getProcessproname() + "???????????????";
			userService.save(processpro);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveNode")
	@ResponseBody
	public AjaxJson saveNode(TPProcessnode processnode, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String formid = oConvertUtils.getString(request.getAttribute("fromid"));
		TPForm form = systemService.getEntity(TPForm.class, formid);
		processnode.setTPForm(form);
		if (StringUtil.isNotEmpty(processnode.getId())) {
			message = "????????????: " + processnode.getProcessnodename() + "???????????????";
			userService.saveOrUpdate(processnode);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "????????????: " + processnode.getProcessnodename() + "???????????????";
			userService.save(processnode);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateType")
	public ModelAndView addorupdateType(TSType processtype, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(processtype.getId())) {
			processtype = systemService.getEntity(TSType.class, processtype.getId());
			req.setAttribute("processtype", processtype);
		}
		return new ModelAndView("workflow/process/processtype");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateBus")
	public ModelAndView addorupdateBus(TSBusConfig busbase, HttpServletRequest req) {
		String processid = req.getParameter("processid");
		TSType type = systemService.findUniqueByProperty(TSType.class, "typecode", WorkFlowGlobals.DataBase_Bus);
		if (type != null) {
			List<TSTable> tableList = systemService.findByProperty(TSTable.class, "TSType.id", type.getId());
			req.setAttribute("tableList", tableList);// ?????????
		}
		if (StringUtil.isNotEmpty(busbase.getId())) {
			busbase = systemService.getEntity(TSBusConfig.class, busbase.getId());
			req.setAttribute("busbase", busbase);
		}
		req.setAttribute("processid", processid);// ????????????ID
		return new ModelAndView("workflow/process/busbase");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdatePro")
	public ModelAndView addorupdatePro(TPProcesspro processpro, HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		List<TPProcessnode> nodeList = systemService.findByProperty(TPProcessnode.class, "TPProcess.id", processid);
		request.setAttribute("nodeList", nodeList);
		List<TSType> typeList = systemService.loadAll(TSType.class);
		request.setAttribute("typeList", typeList);
		List<TPForm> forms = systemService.loadAll(TPForm.class);
		request.setAttribute("forms", forms);
		if (StringUtil.isNotEmpty(processpro.getId())) {
			processpro = systemService.getEntity(TPProcesspro.class, processpro.getId());
			request.setAttribute("processpro", processpro);
		}
		return new ModelAndView("workflow/process/processpro");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateNode")
	public ModelAndView addorupdateNode(TPProcessnode processnode, HttpServletRequest request) {
		String processid = request.getParameter("processid");
		request.setAttribute("processid", processid);
		List<TPProcess> processList = systemService.loadAll(TPProcess.class);
		request.setAttribute("processList", processList);
		List<TPForm> formList = systemService.loadAll(TPForm.class);
		request.setAttribute("formList", formList);
		if (processnode.getId() != null) {
			processnode = systemService.getEntity(TPProcessnode.class, processnode.getId());
			request.setAttribute("processnode", processnode);
		}
		return new ModelAndView("workflow/processnode/processnode");
	}

	/**
	 * ????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "deployProcess")
	@ResponseBody
	public AjaxJson deployProcess(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String processid = request.getParameter("processid");
		TPProcess process = systemService.getEntity(TPProcess.class, processid);
		if (process != null) {
			try {
				repositoryService.createDeployment().addInputStream(process.getProcesskey() + ".bpmn", StreamUtils.byteTOInputStream(process.getProcessxml())).name(process.getProcesskey()).deploy();
				process.setProcessstate(WorkFlowGlobals.Process_Deploy_YES);
				systemService.updateEntitie(process);
				message = "????????????";
			} catch (Exception e) {
				e.printStackTrace();
				message = "????????????";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * *********************????????????************************
	 */
	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "form")
	public ModelAndView form(HttpServletRequest request) {
		// String processid=request.getParameter("processid");
		// request.setAttribute("processid",processid);
		return new ModelAndView("workflow/form/formsList");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "formpro")
	public ModelAndView formpro(HttpServletRequest request) {
		String formid = request.getParameter("formid");
		TPForm form = systemService.get(TPForm.class, formid);
		request.setAttribute("form", form);
		return new ModelAndView("workflow/form/formproList");
	}

	/**
	 * easyuiAJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridForm")
	public void datagridForm(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		/*
		 * String processid=request.getParameter("processid"); String formid=""; if (processid!=null) { List<TProcessnode> processnodes=systemService.findByProperty (TProcessnode.class,"TProcess.processid" ,oConvertUtils.getInt(processid,0)); if (processnodes.size()>1) { for (TProcessnode tProcessnode : processnodes) { formid+=tProcessnode.getTForm().getId().toString()+","; } } } String[] fid=formid.length()>0?(formid.substring(0,formid.length()-1). split(",")):null;
		 */
		CriteriaQuery cq = new CriteriaQuery(TPForm.class, dataGrid);
		/*
		 * if (fid!=null) { cq.in("formid",oConvertUtils.getInts(fid)); cq.add(); }else{ cq.eq("formid",Integer.valueOf(0)); cq.add(); }
		 */
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagridFPro")
	public void datagridFPro(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String formid = request.getParameter("formid");
		CriteriaQuery cq = new CriteriaQuery(TPFormpro.class, dataGrid);
		if (StringUtil.isNotEmpty(formid)) {
			cq.eq("TPForm.id", formid);
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateForm")
	public ModelAndView addorupdateForm(TPForm form, HttpServletRequest req) {
		List<TPProcess> processList = systemService.loadAll(TPProcess.class);
		req.setAttribute("processList", processList);
		List<TSType> typeList = systemService.loadAll(TSType.class);
		req.setAttribute("typeList", typeList);
		if (form.getId() != null) {
			form = systemService.getEntity(TPForm.class, form.getId());
			req.setAttribute("form", form);
		}
		return new ModelAndView("workflow/form/form");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateFPro")
	public ModelAndView addorupdateFPro(TPFormpro formpro, HttpServletRequest req) {
		String formid = req.getParameter("formid");
		req.setAttribute("formid", formid);
		String processid = req.getParameter("processid");
		req.setAttribute("processid", processid);
		List<TSType> typeList = systemService.loadAll(TSType.class);
		req.setAttribute("typeList", typeList);
		if (formpro.getId() != null) {
			formpro = systemService.getEntity(TPFormpro.class, formpro.getId());
			req.setAttribute("formpro", formpro);
		}
		return new ModelAndView("workflow/form/formpro");
	}

	/**
	 * ????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveForm")
	@ResponseBody
	public AjaxJson saveForm(TPForm form, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(form.getId())) {
			message = "??????: " + form.getFormname() + "???????????????";
			systemService.saveOrUpdate(form);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "??????: " + form.getFormname() + "???????????????";
			userService.save(form);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveFPro")
	@ResponseBody
	public AjaxJson saveFPro(TPFormpro formpro, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(formpro.getId())) {
			message = "????????????: " + formpro.getFormproname() + "???????????????";
			systemService.saveOrUpdate(formpro);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "????????????: " + formpro.getFormproname() + "???????????????";
			userService.save(formpro);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}

		return j;
	}

	/**
	 * ????????????delprocess
	 * 
	 * @return
	 */
	@RequestMapping(params = "delForm")
	@ResponseBody
	public AjaxJson delForm(TPForm form, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		form = systemService.getEntity(TPForm.class, form.getId());
		message = "??????: " + form.getFormname() + "????????? ??????";
		systemService.delete(form);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delFPro")
	@ResponseBody
	public AjaxJson delFPro(TPFormpro formpro, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		formpro = systemService.getEntity(TPFormpro.class, formpro.getId());
		message = "????????????: " + formpro.getFormproname() + "????????? ??????";
		systemService.delete(formpro);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		return j;
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	// @RequestMapping(params = "processproList")
	// @ResponseBody
	// public List<ComboBox> processproList(HttpServletResponse
	// response,HttpServletRequest request,ComboBox comboBox) {
	// Integer
	// processid=oConvertUtils.getInt(request.getParameter("processid"),0);
	// String formproid=request.getParameter("formproid");
	// List<TProcesspro> prpros=null;
	// TFormpro
	// formpro=systemService.getEntity(TFormpro.class,oConvertUtils.getInt(formproid,0));
	// if (formproid.length()>0 && formpro.getId().length()>0) {
	// if (formproid!=null) {
	// CriteriaQuery cq1=new CriteriaQuery(TProcesspro.class);
	// cq1.in("processproid",oConvertUtils.getInts(formpro.getId().split(",")));
	// cq1.add();
	// prpros=systemService.getListByCriteriaQuery(cq1);
	// }
	// }
	//
	// List<ComboBox> comboBoxs=new ArrayList<ComboBox>();
	// List<TProcesspro> processpro=new ArrayList<TProcesspro>();
	// if (processid != null) {
	// CriteriaQuery cq=new CriteriaQuery(TProcesspro.class);
	// cq.eq("TProcess.processid",processid);
	// cq.add();
	// processpro=systemService.getListByCriteriaQuery(cq);
	// if (processpro.size()>0) {
	// comboBoxs=TagUtil.getComboBox(processpro,prpros,comboBox);
	// }
	// }
	//
	// return comboBoxs;
	// }

	@RequestMapping(params = "addpro")
	public ModelAndView addpro(HttpServletRequest request) {
		String typeid = request.getParameter("id");
		request.setAttribute("typeid", typeid);
		return new ModelAndView("workflow/process/process");
	}

	@RequestMapping(params = "choosePro")
	public ModelAndView choosePro(HttpServletRequest request) {
		List<TPForm> formList = systemService.loadAll(TPForm.class);
		request.setAttribute("formList", formList);
		return new ModelAndView("workflow/process/process");
	}

	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseListener")
	public ModelAndView chooseListener(HttpServletRequest request) {
		String typeid = oConvertUtils.getString(request.getParameter("typeid"));// ????????????
		return new ModelAndView("designer/listenerList", "typeid", typeid);
	}

	/**
	 * ????????????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "listenerGrid")
	@ResponseBody
	public void listenerGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Short listenerstate = oConvertUtils.getShort(request.getParameter("status"));
		Short typeid = oConvertUtils.getShort(request.getParameter("typeid"));
		CriteriaQuery cq = new CriteriaQuery(TPListerer.class, dataGrid);
		if (StringUtil.isNotEmpty(listenerstate)) {
			cq.eq("listenerstate", listenerstate);
		}
		if (StringUtil.isNotEmpty(typeid)) {
			cq.eq("typeid", typeid);
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	
	
	/**
	 * ????????????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "listenerGridYouXiao")
	@ResponseBody
	public void listenerGridYouXiao(TPListerer tplisterer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		if(oConvertUtils.isNotEmpty(ids)){
			tplisterer.setId(ids);
		}else{
			tplisterer.setId("????????????");
		}
		CriteriaQuery cq = new CriteriaQuery(TPListerer.class, dataGrid);
		HqlGenerateUtil.installHql(cq, tplisterer);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * ???????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "saveListener")
	@ResponseBody
	public AjaxJson saveListener(TPListerer tpListerer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String event = "";
		Short typeid = oConvertUtils.getShort(request.getParameter("typeid"));
		if (typeid.equals(WorkFlowGlobals.Listener_Type_Excution)) {
			event = oConvertUtils.getString(request.getParameter("executioneven"));
		} else {
			event = oConvertUtils.getString(request.getParameter("taskeven"));
		}
		tpListerer.setListenereven(event);
		if (StringUtil.isNotEmpty(tpListerer.getId())) {
			message = "??????: " + tpListerer.getListenername() + "????????????";
			userService.saveOrUpdate(tpListerer);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE, WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "??????: " + tpListerer.getListenername() + "????????????";
			tpListerer.setListenerstate(WorkFlowGlobals.Listener_No);
			userService.save(tpListerer);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT, WorkFlowGlobals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * ???????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delListeren")
	@ResponseBody
	public AjaxJson delListeren(TPListerer tpListerer, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tpListerer = systemService.getEntity(TPListerer.class, tpListerer.getId());
		if (tpListerer.getTProcessListeners().size() == 0) {
			message = "??????: " + tpListerer.getListenername() + " ????????????";
			systemService.delete(tpListerer);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);

		} else {
			message = "??????: " + tpListerer.getListenername() + "??????????????????????????????";
		}

		j.setMsg(message);
		return j;
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(params = "processUpload")
	public ModelAndView processUpload(String id,HttpServletRequest req) {
		req.setAttribute("id", id);
		return new ModelAndView("workflow/process/processUpload");
	}
	
	/**
	 * ??????????????????
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "doProcessUpload")
	public AjaxJson doProcessUpload(HttpServletRequest request,HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		String ls_file = "";
		UploadFile uploadFile = new UploadFile(request, ls_file);
		uploadFile.setCusPath("");
		uploadFile.setSwfpath("");
		String uploadbasepath = uploadFile.getBasePath();// ?????????????????????
		if (uploadbasepath == null) {
			uploadbasepath = ResourceUtil.getConfigByName("uploadpath");
		}
		String path = uploadbasepath + "\\";// ????????????????????????????????????
		String realPath = uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("\\")+ path;// ???????????????????????????
		message = null;
		try {
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdir();// ????????????????????????????????????
			}
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// ????????????????????????
				fileName = mf.getOriginalFilename();// ???????????????
				String savePath = realPath + fileName;
				File savefile = new File(savePath);
				String ls_tmp = savefile.getName();
				FileCopyUtils.copy(mf.getBytes(), savefile);
				MigrateForm.unzip(savePath, "");
				String sqlFileDir = realPath + ls_tmp.substring(0, ls_tmp.lastIndexOf("."));
				File sqlDirFile = new File(sqlFileDir);
				String bpmfilename = sqlDirFile.getPath() + "/";
				if(sqlDirFile.isDirectory()){
					bpmfilename += sqlDirFile.list()[0];
				}
				File bpm = new File(bpmfilename);
				InputStream in = new FileInputStream(bpm);
				
				//????????????ID???????????????
				String id = request.getParameter("id");
				TPProcess tProcess = systemService.getEntity(TPProcess.class, id);
				tProcess.setProcessxml(StreamUtils.InputStreamTOByte(in));
				systemService.updateEntitie(tProcess);// ????????????
			}
		} catch (Exception e1) {
			LogUtil.error(e1.toString());
			message = e1.toString();
		}
		if (StringUtil.isNotEmpty(message))
			j.setMsg("??????????????????," + message);
		else
			j.setMsg("????????????????????????");

		return j;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "setListeren")
	@ResponseBody
	public AjaxJson setListeren(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		Short status = oConvertUtils.getShort(request.getParameter("status"));
		TPListerer tpListerer = systemService.getEntity(TPListerer.class, id);
		if (tpListerer != null) {
			tpListerer.setListenerstate(status);
			systemService.updateEntitie(tpListerer);
			if (status.equals(WorkFlowGlobals.Listener_No)) {
				j.setMsg("???????????????");
			} else {
				j.setMsg("???????????????");
			}
		}
		return j;
	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "setProcessListener")
	@ResponseBody
	public AjaxJson setProcessListener(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		TPProcessListener tpProcessListener = systemService.getEntity(TPProcessListener.class, id);
		if (tpProcessListener != null) {
			Short status = (WorkFlowGlobals.Process_Listener_NO.equals(tpProcessListener.getStatus())) ? WorkFlowGlobals.Process_Listener_YES : WorkFlowGlobals.Process_Listener_NO;
			tpProcessListener.setStatus(status);
			systemService.updateEntitie(tpProcessListener);
			if (status.equals(WorkFlowGlobals.Process_Listener_NO)) {
				j.setSuccess(false);
				j.setMsg("???????????????");
			} else {
				j.setMsg("???????????????");
			}
		}
		return j;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "delProcesListeren")
	@ResponseBody
	public AjaxJson delProcesListeren(TPProcessListener tpProcessListener, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tpProcessListener = systemService.getEntity(TPProcessListener.class, tpProcessListener.getId());
		message = "??????: " + tpProcessListener.getTPListerer().getListenername() + " ????????????";
		systemService.delete(tpProcessListener);
		systemService.addLog(message, WorkFlowGlobals.Log_Type_DEL, WorkFlowGlobals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * ????????????????????????
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getNodelisteners")
	@ResponseBody
	public void getNodelisteners(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		Short type = oConvertUtils.getShort(request.getParameter("type"));
		String processNode = oConvertUtils.getString(request.getParameter("processNode"));// ??????id
		String processkey = oConvertUtils.getString(request.getParameter("processId"));// ??????KEY
		CriteriaQuery cq = new CriteriaQuery(TPProcessListener.class, dataGrid);
		if (type.equals(WorkFlowGlobals.Listener_Type_Task)) {
			TPProcessnode tProcessnode = activitiService.getTPProcessnode(processNode, processkey);
			if (tProcessnode != null) {
				cq.eq("TPProcessnode.id", tProcessnode.getId());
			}
		} else {
			cq.eq("nodename", processNode);//?????????????????????????????????nodename
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, false);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????
	 * 
	 */
	@RequestMapping(params = "saveProcessListener")
	@ResponseBody
	public AjaxJson saveProcessListener(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Short type = oConvertUtils.getShort(request.getParameter("type"));// ????????????
		String listenerid = oConvertUtils.getString(request.getParameter("listenerid"));// ??????ID
		String processkey = oConvertUtils.getString(request.getParameter("processkey"));// ????????????ID
		String taskDefinitionKey = ResourceUtil.getParameter("processNode");// ??????????????????
		TPProcess tProcess = null;// ????????????
		TPProcessnode tProcessnode = null;// ????????????
		if (StringUtil.isNotEmpty(processkey)) {
			tProcess = systemService.findUniqueByProperty(TPProcess.class, "processkey", processkey);
			if (tProcess == null) {
				tProcess = new TPProcess();
				tProcess.setProcesskey(processkey);
				systemService.save(tProcess);// ????????????
			}
		}
		if (type.equals(WorkFlowGlobals.Listener_Type_Task)) {
			if (StringUtil.isNotEmpty(taskDefinitionKey)) {
				tProcessnode = activitiService.getTPProcessnode(taskDefinitionKey, processkey);
				if (tProcessnode == null) {
					tProcessnode = new TPProcessnode();
					tProcessnode.setTPProcess(tProcess);
					tProcessnode.setTPForm(null);
					tProcessnode.setProcessnodecode(taskDefinitionKey);
					systemService.save(tProcessnode);// ????????????
				} else {
					tProcessnode.setTPProcess(tProcess);
					tProcessnode.setTPForm(null);
					tProcessnode.setProcessnodecode(taskDefinitionKey);
					systemService.updateEntitie(tProcessnode);// ????????????
				}
			}
		}
		if (StringUtil.isNotEmpty(listenerid)) {
			String[] listens = listenerid.split(",");
			int len = listens.length;
			for (int i = 0; i < len; i++) {
				TPProcessListener tPProcessListener = new TPProcessListener();
				TPListerer tPListerer = systemService.getEntity(TPListerer.class, listens[i]);
				tPProcessListener.setTPListerer(tPListerer);
				if (type.equals(WorkFlowGlobals.Listener_Type_Task)) {
					tPProcessListener.setTPProcessnode(tProcessnode);
				}
				if (type.equals(WorkFlowGlobals.Listener_Type_Excution)) {
					tPProcessListener.setTPProcess(tProcess);
					tPProcessListener.setNodename(taskDefinitionKey);
				}
				tPProcessListener.setStatus(WorkFlowGlobals.Process_Deploy_NO);
				systemService.save(tPProcessListener);
			}
		}

		return j;
	}

	/**
	 * ????????????????????????
	 */

	@RequestMapping(params = "busConfigGrid")
	public void busConfigGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSBusConfig.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "listenerList")
	public ModelAndView listenerList(HttpServletRequest request) {
		return new ModelAndView("workflow/listener/listenerList");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "aouListener")
	public ModelAndView aouListener(TPListerer tpListerer, HttpServletRequest request) {
		if (tpListerer.getId() != null) {
			tpListerer = systemService.getEntity(TPListerer.class, tpListerer.getId());
			request.setAttribute("tpListerer", tpListerer);
		}
		return new ModelAndView("workflow/listener/listener");
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "nodeFun")
	public ModelAndView nodeFun(HttpServletRequest request) {
		String id = request.getParameter("nodeId");
		request.setAttribute("nodeId", id);
		return new ModelAndView("workflow/processnode/nodeRoleSet");
	}
	
	/**
	 * ?????????????????????
	 * 
	 * @param role
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setNodeAuthority")
	@ResponseBody
	public List<ComboTree> setNodeAuthority(TPProcessnode node,
			HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (comboTree.getId() != null) {
			cq.eq("TSFunction.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSFunction");
		}
		cq.notEq("functionLevel", Short.parseShort("-1"));
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
		Collections.sort(functionList, new NumberComparator());
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		String nodeId = request.getParameter("nodeId");
		List<TSFunction> loginActionlist = new ArrayList<TSFunction>();// ??????????????????
		node = this.systemService.get(TPProcessnode.class, nodeId);
		if (node != null) {
			List<TPProcessnodeFunction> proFunctionList = systemService
					.findByProperty(TPProcessnodeFunction.class, "TPProcessnode.id",
							node.getId());
			if (proFunctionList.size() > 0) {
				for (TPProcessnodeFunction proFunction : proFunctionList) {
					TSFunction function = (TSFunction) proFunction
							.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		ComboTreeModel comboTreeModel = new ComboTreeModel("id",
				"functionName", "TSFunctions");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel,
				loginActionlist, false);

		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateNodeAuthority")
	@ResponseBody
	public AjaxJson updateNodeAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String nodeId = request.getParameter("nodeId");
			String nodefunctions = request.getParameter("nodefunctions");
			TPProcessnode node = this.systemService.get(TPProcessnode.class, nodeId);
			List<TPProcessnodeFunction> nodeFunctionList = systemService
					.findByProperty(TPProcessnodeFunction.class, "TPProcessnode.id",node.getId());
			Map<String, TPProcessnodeFunction> map = new HashMap<String, TPProcessnodeFunction>();
			for (TPProcessnodeFunction functionOfNode : nodeFunctionList) {
				map.put(functionOfNode.getTSFunction().getId(), functionOfNode);
			}
			String[] nodeFunctions = nodefunctions.split(",");
			Set<String> set = new HashSet<String>();
			for (String s : nodeFunctions) {
				set.add(s);
			}
			updateNodeCompare(set, node, map);
			j.setMsg("??????????????????");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("??????????????????");
		}
		return j;
	}
	
	/**
	 * ????????????
	 * 
	 * @param set
	 *            ?????????????????????
	 * @param role
	 *            ????????????
	 * @param map
	 *            ??????????????????
	 */
	private void updateNodeCompare(Set<String> set, TPProcessnode node,Map<String, TPProcessnodeFunction> map) {
		List<TPProcessnodeFunction> entitys = new ArrayList<TPProcessnodeFunction>();
		List<TPProcessnodeFunction> deleteEntitys = new ArrayList<TPProcessnodeFunction>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TPProcessnodeFunction rf = new TPProcessnodeFunction();
				TSFunction f = this.systemService.get(TSFunction.class, s);
				rf.setTSFunction(f);
				rf.setTPProcessnode(node);
				entitys.add(rf);
			}
		}
		Collection<TPProcessnodeFunction> collection = map.values();
		Iterator<TPProcessnodeFunction> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);

	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param functionId
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(params = "nodeOperaListForFunction")
	public ModelAndView nodeOperaListForFunction(HttpServletRequest request,String functionId, String nodeId) {
		CriteriaQuery cq = new CriteriaQuery(TSOperation.class);
		cq.eq("TSFunction.id", functionId);
		//update-begin--Author:anchao  Date:20140822 for???[bugfree???]????????????????????????????????????--------------------
		cq.eq("status", Short.valueOf("0"));
		//update-end--Author:anchao  Date:20140822 for???[bugfree???]????????????????????????????????????--------------------
		cq.add();
		List<TSOperation> operationList = this.systemService.getListByCriteriaQuery(cq, false);
		Set<String> operationCodes = activitiService.getNodeOperaCodesByNodeIdAndFunctionId(nodeId, functionId);
		request.setAttribute("operationList", operationList);
		request.setAttribute("operationcodes", operationCodes);
		request.setAttribute("functionId", functionId);
		return new ModelAndView("workflow/processnode/nodeOperaListForFunction");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateNodeOperation")
	@ResponseBody
	public AjaxJson updateNodeOperation(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String nodeId = request.getParameter("nodeId");
		String functionId = request.getParameter("functionId");
		String operationcodes = null;
		try {
			operationcodes = URLDecoder.decode(
					request.getParameter("operationcodes"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
		cq1.eq("TPProcessnode.id", nodeId);
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TPProcessnodeFunction> rFunctions = systemService.getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TPProcessnodeFunction tsProFunction = rFunctions.get(0);
			tsProFunction.setOperation(operationcodes);
			systemService.saveOrUpdate(tsProFunction);
		}
		j.setMsg("????????????????????????");
		return j;
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @param functionId
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(params = "nodeDataRuleListForFunction")
	public ModelAndView nodeDataRuleListForFunction(HttpServletRequest request,String functionId, String nodeId) {
		CriteriaQuery cq = new CriteriaQuery(TSDataRule.class);
		cq.eq("TSFunction.id", functionId);
		cq.add();
		List<TSDataRule> dataRuleList = this.systemService.getListByCriteriaQuery(cq, false);
		Set<String> dataRulecodes = activitiService.getOperationCodesByNodeIdAndruleDataId(nodeId, functionId);
		request.setAttribute("dataRuleList", dataRuleList);
		request.setAttribute("dataRulecodes", dataRulecodes);
		request.setAttribute("functionId", functionId);
		return new ModelAndView("workflow/processnode/nodeDateRuleListForFunction");
	}
	
	
	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateNodeDataRule")
	@ResponseBody
	public AjaxJson updateNodeDataRule(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String nodeId = request.getParameter("nodeId");
		String functionId = request.getParameter("functionId");
		// update-begin--Author:chenxu Date:201403024 for???410
		String dataRulecodes = null;
		try {
			dataRulecodes = URLDecoder.decode(request.getParameter("dataRulecodes"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// update-end--Author:chenxu Date:20140324 for???410
		CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
		cq1.eq("TPProcessnode.id", nodeId);
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TPProcessnodeFunction> rFunctions = systemService.getListByCriteriaQuery(
				cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TPProcessnodeFunction tsNodeFunction = rFunctions.get(0);
			tsNodeFunction.setDataRule(dataRulecodes);
			systemService.saveOrUpdate(tsNodeFunction);
		}
		j.setMsg("????????????????????????");
		return j;
	}

	
}
