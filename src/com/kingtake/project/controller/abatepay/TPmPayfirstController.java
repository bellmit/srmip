package com.kingtake.project.controller.abatepay;

import com.alibaba.fastjson.JSON;
import com.kingtake.project.entity.abatepay.TPmPayfirstEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.abatepay.TPmPayfirstServiceI;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
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
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;



/**   
 * @Title: Controller
 * @Description: ???????????????
 * @author onlineGenerator
 * @date 2015-07-24 11:33:01
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPayfirstController")
public class TPmPayfirstController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmPayfirstController.class);

	@Autowired
	private TPmPayfirstServiceI tPmPayfirstService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
    private ActivitiService activitiService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * ????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmPayfirstListInfo")
	public ModelAndView tPmPayfirst(HttpServletRequest request, HttpServletResponse response) {
	    String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListInfo");
	}

	/**
     * ????????????????????? ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayfirstListSearch")
    public ModelAndView tPmAbateSearch(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListSearch");
    }
    
    /**
     * ????????????????????? ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayfirstListDepartment")
    public ModelAndView tPmPayfirstListDepartment(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListForDepartment");
    }
    
    /**
     * ????????????????????? ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayfirstListProcess")
    public ModelAndView tPmPayfirstProcess(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null) {
                request.setAttribute("projectName", project.getProjectName());
            }
        }
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListProcess");
    }

	/**
	 * ???????????????????????? add by liyangzhao 10.11
	 *
	 * @return
	 */
	@RequestMapping(params = "tPmPayfirstListGdDetail")
	public ModelAndView tPmPayfirstListGdDetail(HttpServletRequest request) {
		String projectId = request.getParameter("projectId");
		String paySubjectcode = request.getParameter("paySubjectcode");
		String payYear = request.getParameter("payYear");

		request.setAttribute("projectId", projectId);
		if (StringUtils.isNotEmpty(projectId)) {
			TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
			if (project != null) {
				request.setAttribute("projectName", project.getProjectName());//????????????
				request.setAttribute("projectNo", project.getProjectNo());//????????????
				request.setAttribute("paySubjectcode",paySubjectcode);//??????????????????
				request.setAttribute("payYear",payYear);
			}
		}
		return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstListGdDetail");
	}
	/**
	 * ???????????? ????????????????????? add by liyangzhao 10.13
	 *
	 * @return
	 */
	@RequestMapping(params = "tPmPayfirstListGdAdd")
	public void tPmPayfirstListGdAdd(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		Map json = new HashedMap();
		try {
			json = this.tPmPayfirstService.addGdInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "????????????");
		}finally {
			String jsonStr = JSON.toJSONString(json);
			TagUtil.responseOut(response, jsonStr);
		}
	}

	/**
	 * ???????????? ????????????????????? add by liyangzhao 10.13
	 *
	 * @return
	 */
	@RequestMapping(params = "tPmPayfirstListGdSearch")
	public void tPmPayfirstListGdSearch(TPmPayfirstEntity tPmPayfirst,HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		List list = this.tPmPayfirstService.doSearchGDList(param);
		dataGrid.setResults(list);
		dataGrid.setTotal(list.size());
		TagUtil.datagrid(response, dataGrid);
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
	public void datagrid(TPmPayfirstEntity tPmPayfirst,HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
	    CriteriaQuery cq = new CriteriaQuery(TPmPayfirstEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPayfirst, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tPmPayfirstService.getDataGridReturn(cq, true);
		for (Object entity : dataGrid.getResults()) {
		    TPmPayfirstEntity tmp = (TPmPayfirstEntity) entity;
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tmp.getProjectId());
            tmp.setProjectName(project.getProjectName());
            if (!"1".equals(tmp.getBpmStatus())) {
                Map<String, Object> dataMap = activitiService.getProcessInstance(tmp.getId());
                if (dataMap != null) {
                    String taskName = (String) dataMap.get("taskName");
                    if (StringUtils.isNotEmpty(taskName)) {
                        tmp.setTaskName(taskName);
                    }
                    String processInstId = (String) dataMap.get("processInstId");
                    if (StringUtils.isNotEmpty(processInstId)) {
                        tmp.setProcessInstId(processInstId);
                    }
                    String taskId = (String) dataMap.get("taskId");
                    if (StringUtils.isNotEmpty(taskId)) {
                        tmp.setTaskId(taskId);
                    }
                    String assignee = (String) dataMap.get("assignee");
                    if (StringUtils.isNotEmpty(assignee)) {
                        tmp.setAssigneeName(assignee);
                    }
                }
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridEasyUI")
	public void datagridEasyUI(TPmPayfirstEntity tPmPayfirst,HttpServletRequest request, 
			HttpServletResponse response) {
	    List<TPmPayfirstEntity> list = systemService.getSession()
	    		.createCriteria(TPmPayfirstEntity.class)
	    		.add(Restrictions.eq("projectId", tPmPayfirst.getProjectId()))
	    		.addOrder(Order.desc("createDate")).list();
		TagUtil.listToJson(response, list);
	}
	

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmPayfirstEntity tPmPayfirst, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmPayfirst = systemService.getEntity(TPmPayfirstEntity.class, tPmPayfirst.getId());
		message = "???????????????????????????";
		try{
			tPmPayfirstService.deleteAddAttach(tPmPayfirst);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "???????????????????????????";
		try{
			for(String id:ids.split(",")){
				TPmPayfirstEntity tPmPayfirst = systemService.getEntity(
						TPmPayfirstEntity.class, id);
				tPmPayfirstService.deleteAddAttach(tPmPayfirst);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
     * ?????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmPayfirstEntity tPmPayfirst, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(StringUtil.isEmpty(tPmPayfirst.getId())){
            	//???????????????
            	Long count = this.tPmPayfirstService.getCountForJdbc("select count(*) from t_b_pm_payfirst") + 1;
            	String barcodeNum = "0000000" + count;
            	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String currentYear = sdf.format(date);
            	String barcode = "KZ" + currentYear + barcodeNum;
            	tPmPayfirst.setBarcode(barcode);
                tPmPayfirst.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
            }
            this.tPmPayfirstService.savePayFirst(tPmPayfirst);
            message = "???????????????????????????";
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            j.setSuccess(false);
        }
        j.setObj(tPmPayfirst);
        j.setMsg(message);
        return j;
    }
	

	/**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TPmPayfirstEntity tPmPayfirst, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPayfirst.getId())) {
            tPmPayfirst = tPmPayfirstService.getEntity(TPmPayfirstEntity.class, tPmPayfirst.getId());
        }
        //??????
        if(StringUtils.isEmpty(tPmPayfirst.getAttachmentCode())){
            tPmPayfirst.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmPayfirst.getAttachmentCode(), "");
            tPmPayfirst.setCertificates(certificates);
        }
        req.setAttribute("tPmPayfirstPage", tPmPayfirst);
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirst");
    }
    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TPmPayfirstEntity tPmPayfirst, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPayfirst.getId())) {
            tPmPayfirst = tPmPayfirstService.getEntity(TPmPayfirstEntity.class, tPmPayfirst.getId());
        }
        //??????
        if(StringUtils.isEmpty(tPmPayfirst.getAttachmentCode())){
            tPmPayfirst.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmPayfirst.getAttachmentCode(), "");
            tPmPayfirst.setCertificates(certificates);
        }
        req.setAttribute("tPmPayfirstPage", tPmPayfirst);
        return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirst");
    }
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/abatepay/tPmPayfirstUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmPayfirstEntity tPmPayfirst,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmPayfirstEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPayfirst, request.getParameterMap());
		List<TPmPayfirstEntity> tPmPayfirsts = this.tPmPayfirstService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"???????????????");
		modelMap.put(NormalExcelConstants.CLASS,TPmPayfirstEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("?????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmPayfirsts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmPayfirstEntity tPmPayfirst,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmPayfirstEntity.class);
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
				List<TPmPayfirstEntity> listTPmPayfirstEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmPayfirstEntity.class,params);
				for (TPmPayfirstEntity tPmPayfirst : listTPmPayfirstEntitys) {
					tPmPayfirstService.save(tPmPayfirst);
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
     * ????????????????????????????????????????????????
     * 
     * @param tPmIncomeAllot
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getPayFirstList")
    @ResponseBody
    public List<ComboBox> getPayFirstList(TPmPayfirstEntity tPmPayfirst, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmPayfirstEntity.class);
        //????????????????????????????????????????????????
        cq.eq("bpmStatus", "3");
        if (StringUtils.isNotEmpty(tPmPayfirst.getProjectId())) {
            cq.eq("projectId", tPmPayfirst.getProjectId());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmPayfirstEntity> payfirstList = this.systemService.getListByCriteriaQuery(cq, false);
        List<ComboBox> comboboxList = new ArrayList<ComboBox>();
        for (TPmPayfirstEntity entity : payfirstList) {
            ComboBox combobox = new ComboBox();
            combobox.setId(entity.getId());
            combobox.setText(entity.getPaySubjectcode() + "???" + entity.getPayFunds() + "??????");
            comboboxList.add(combobox);
        }
        return comboboxList;
    }
    
    /**
	 * ??????word
	 * 
	 * @param request
	 * @param response
     * @throws java.io.IOException 
     * @throws TemplateException 
	 */
	@RequestMapping(params = "createWord")
	@ResponseBody
    public AjaxJson createWord(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException, TemplateException {
	AjaxJson j = new AjaxJson();
		/** ????????????????????? **/
        Configuration configuration = new Configuration();
        /** ???????????? **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl?????????????????????????????????export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template";
        /** ???????????? **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** ???????????? **/
        Template template = configuration.getTemplate("dzfptzs.ftl");
        /** ??????????????????????????? **/
        String payFirstId = request.getParameter("id");
        TPmPayfirstEntity payfirst = this.tPmPayfirstService.get(TPmPayfirstEntity.class, payFirstId);
        /** ???????????? **/
        Map<String,Object> dataMap = new HashMap<>();

        /** ???????????? **/
        String str = "";
        try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      //xx  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);
	      str = payfirst.getBarcode();  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code" + str + ".png");
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
        String imagePath = classPath + "exportWord\\Code" + str + ".png";
        /** ??????????????????**/
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
            }
        }
        /** ??????base64????????? **/
        BASE64Encoder encoder = new BASE64Encoder();

        /** ????????????**/
        dataMap.put("barcode",encoder.encode(data));
        /** ????????????**/   
        TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, payfirst.getProjectId());
        dataMap.put("projectNo", projectEntity.getProjectNo());
        dataMap.put("projectName", projectEntity.getProjectName());
        dataMap.put("developerDepart", projectEntity.getDevDepart().getDepartname());
        dataMap.put("projectManager", projectEntity.getProjectManager());
        dataMap.put("createDate", payfirst.getCreateDate().toString());
        dataMap.put("payFunds", payfirst.getPayFunds());
        dataMap.put("unpayFunds", payfirst.getPayFunds());
        dataMap.put("no", "1");
        dataMap.put("totalAmount",payfirst.getPayFunds());

        /** ????????????word??????????????? **/
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//?????????????????????????????????
        String exportTime = dateFormat.format( now ); 
        String outFilePath = classPath + "\\exportWord\\dzjf" + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
			template.process(dataMap,out);
			String FileName = "dzjf" + exportTime + ".doc";
			Map<String,Object> returnMap = new HashMap<>();
			returnMap.put("FileName", FileName);
			j.setAttributes(returnMap);
			j.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(out != null){
            out.close();
        }
        
        return j;
    }
    
    /**
     * ??????word
     * 
     * @return
     */
    @RequestMapping(params = "downloadWord")
    public void downloadWord(HttpServletRequest request,HttpServletResponse response) {
    	String fileName = request.getParameter("FileName");
        OutputStream out = null;
        InputStream templateIs = null;
        try {
        	String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");        	
        	
        	String path = classPath + "exportWord\\" + fileName;        	
            
            	templateIs = new FileInputStream(path);                
                fileName = POIPublicUtil
                        .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            out = response.getOutputStream();
            IOUtils.copy(templateIs, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("??????word??????", e);
        } finally {
            IOUtils.closeQuietly(templateIs);
            IOUtils.closeQuietly(out);
        }
    }  
    
    static void saveToPNG(BufferedImage paramBufferedImage, String paramString)  
	  {  
	    saveToFile(paramBufferedImage, paramString, "png");  
	  }  
	  
	  static void saveToGIF(BufferedImage paramBufferedImage, String paramString)  
	  {  
	    saveToFile(paramBufferedImage, paramString, "gif");  
	  }  
	  
	  static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2)  
	  {  
	    try  
	    {  
	      String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
	      String fileDirectory = classPath + "\\exportWord\\";
	      File file = new File(fileDirectory);
	      if (!file.exists()) {
	    	  file.mkdir();
	      }
	      FileOutputStream localFileOutputStream = new FileOutputStream(fileDirectory + paramString1);  
	      ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);  
	      localFileOutputStream.close();  
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
	  }  
		
		/**
		 * ?????????????????????????????????
		 * 
		 * @return
		 */
		@RequestMapping(params = "doGd")
		@ResponseBody
		public AjaxJson doGd(HttpServletRequest request, HttpServletResponse response){
			String id = request.getParameter("id");
			
			AjaxJson j = new AjaxJson();
			message = "????????????????????????";
			
			TPmPayfirstEntity tPmPayfirst = tPmPayfirstService.getEntity(
					TPmPayfirstEntity.class, id);
			if (null != tPmPayfirst) {
				// ?????????????????????1???????????????
				tPmPayfirst.setGdStatus("1");
				tPmPayfirstService.saveOrUpdate(tPmPayfirst);
				
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} else {
				message = "????????????????????????";
			}
			
			j.setMsg(message);
			return j;
		}
}
