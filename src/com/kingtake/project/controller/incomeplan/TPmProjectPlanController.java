package com.kingtake.project.controller.incomeplan;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jsp.webpage.workflow.listener.listener_jsp;
import org.apache.log4j.Logger;
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
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelLeavedetailEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanExportEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.invoice.TBPmInvoiceEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.incomeplan.TPmIncomePlanServiceI;
import com.kingtake.project.service.incomeplan.TPmProjectPlanServiceI;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Title: Controller
 * @Description: 计划下达主表
 * @author onlineGenerator
 * @date 2016-01-21 20:20:22
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectPlanController")
public class TPmProjectPlanController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomePlanController.class);

    @Autowired
    private TPmProjectPlanServiceI tBPmProjectPlanService;
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
     * 计划下达列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectPlanList")
    public ModelAndView incomePlanList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeplan/projectPlanListForDepartment");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(TPmProjectPlanEntity tPmProjectPlan, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectPlanEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectPlan,
                request.getParameterMap());
        try {
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmProjectPlanService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }    
    
    /**
	 * 加载明细列表[计划下达各项目分配]
	 * 
	 * @return
	 */
	@RequestMapping(params = "projectPlanDetailList")
	public ModelAndView projectPlanDetailList(TPmProjectPlanEntity tPmProjectPlan, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tPmProjectPlan.getId();
		//===================================================================================
		//查询-计划下达分配表信息
	    String hql0 = "from tPmIncomePlanEntity where 1 = 1 AND projectPlanId = ? ";
	    try{
	    	List<TPmIncomePlanEntity> tPmIncomePlanEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tPmIncomePlanEntityList", tPmIncomePlanEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/kingtake/project/incomeplan/projectPlanDetailList");
	}

    /**
     * 计划下达新增和编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TPmProjectPlanEntity tPmProjectPlan, HttpServletRequest req) {
    	if (StringUtil.isNotEmpty(tPmProjectPlan.getId())) {
    		tPmProjectPlan = tBPmProjectPlanService.getEntity(TPmProjectPlanEntity.class, tPmProjectPlan.getId());
    	}
        req.setAttribute("tPmProjectPlanPage", tPmProjectPlan);
        return new ModelAndView("com/kingtake/project/incomeplan/tPmProjectPlanEdit");
    }

    /**
     * 保存计划下达信息
     *
     * @return
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmProjectPlanEntity tPmProjectPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划下达更新成功";        
        try {        	
        	if(StringUtil.isEmpty(tPmProjectPlan.getId())){
        		//生成二维码
            	Long count = tBPmProjectPlanService.getCountForJdbc("select count(*) from T_PM_PROJECT_PLAN") + 1;
            	String barcodeNum = "0000000" + count;
            	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String currentYear = sdf.format(date);
            	String barcode = "KJ" + currentYear + barcodeNum;
            	tPmProjectPlan.setBarcode(barcode);
            	tPmProjectPlan.setAduitStatus("0");
                this.tBPmProjectPlanService.save(tPmProjectPlan);
        	}else{
        		TPmProjectPlanEntity t = tBPmProjectPlanService.get(TPmProjectPlanEntity.class, tPmProjectPlan.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmProjectPlan, t);
                tBPmProjectPlanService.saveOrUpdate(t);
        	}        	                        
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存计划下达失败，原因：" + e.getMessage());
            return j;
        }
        j.setObj(tPmProjectPlan);
        j.setMsg(message);
        return j;
    }

    /**
     * 删除计划下达信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectPlanEntity tPmProjectPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划下达删除成功";
        try {
        	tBPmProjectPlanService.delete(tPmProjectPlan);
        } catch (Exception e) {
            e.printStackTrace();
            message = "计划下达删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }       

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/invoice/tPmIncomeApplyUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
//        List<TPmIncomeApplyEntity> tPmIncomeApplys = this.tBPmIncomePlanService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目发票信息");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeApplyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目发票信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
//        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeApplys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目来款申请信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmIncomeApplyEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

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
                List<TPmIncomeApplyEntity> listtPmIncomeApplyEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeApplyEntity.class, params);
                for (TPmIncomeApplyEntity tPmIncomeApply : listtPmIncomeApplyEntitys) {
//                	tBPmIncomePlanService.save(tPmIncomeApply);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
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
	 * 导出word
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
		/** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl文件是放在代码目录下的export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("jhjffptzs.ftl");
        /** 读取需要打印的对象 **/
        String planId = request.getParameter("id");
        TPmProjectPlanEntity projectPlan = this.tBPmProjectPlanService.get(TPmProjectPlanEntity.class, planId);
        /** 准备数据 **/
        Map<String,Object> dataMap = new HashMap<>();

        /** 图片路径 **/
        String str = "";
        try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      //xx  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);
	      str = projectPlan.getBarcode();  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code" + str + ".png");
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
        String imagePath = classPath + "exportWord\\Code" + str + ".png";
        /** 将图片转化为**/
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
        /** 进行base64位编码 **/
        BASE64Encoder encoder = new BASE64Encoder();

        /** 在ftl文件中有${textDeal}这个标签**/
//        dataMap.put("textDeal","一下省略一万字");
        /** 图片数据**/
        dataMap.put("barcode",encoder.encode(data));
        /** 文字数据**/        
        dataMap.put("documentNo", projectPlan.getDocumentNo());
        dataMap.put("documentName", projectPlan.getDocumentName());
        dataMap.put("createTime", projectPlan.getCreateDate().toString());
        dataMap.put("fundsSubject", projectPlan.getFundsSubject());
        Integer num = Integer.parseInt(projectPlan.getBarcode().substring(projectPlan.getBarcode().length() - 7, projectPlan.getBarcode().length()));
        dataMap.put("num", num.toString());

        /** 表格数据初始化 **/
        List<TPmIncomePlanEntity> planDetailList = this.systemService.findByProperty(TPmIncomePlanEntity.class, "projectPlanId", projectPlan.getId());
        
        List<TPmIncomePlanExportEntity> planExportEntities = new ArrayList<>();
        Integer no = 1;
        BigDecimal bDecimal = new BigDecimal(0.00);
        BigDecimal totalAmount = new BigDecimal(0.00);
        for (TPmIncomePlanEntity planDetail : planDetailList) {
        	TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, planDetail.getProject().getId());
        	planExportEntities.add(new TPmIncomePlanExportEntity(no.toString()
        			,projectEntity.getAccountingCode() == null ? "" : projectEntity.getAccountingCode() 
        		    ,projectEntity.getProjectName() == null ? "" : projectEntity.getProjectName() 
        			,projectEntity.getDevDepart().getDepartname() == null ? "" : projectEntity.getDevDepart().getDepartname()
        			,projectEntity.getProjectManager() == null ? "" : projectEntity.getProjectManager()
        			,planDetail.getPlanAmount() == null ? bDecimal : planDetail.getPlanAmount()
        			,""
        			,planDetail.getUniversityAmount() == null ? bDecimal : planDetail.getUniversityAmount()
        			,planDetail.getAcademyAmount() == null ? bDecimal : planDetail.getAcademyAmount()
        			,planDetail.getDepartmentAmount() == null ? bDecimal : planDetail.getDepartmentAmount()));
        	totalAmount = totalAmount.add(planDetail.getPlanAmount());
        	no++;
		}
        
        
        dataMap.put("planDetailList",planExportEntities);
        dataMap.put("totalAmount",totalAmount);

        /** 指定输出word文件的路径 **/
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String exportTime = dateFormat.format( now ); 
        String outFilePath = classPath + "\\exportWord\\jhfp" + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
			template.process(dataMap,out);
			String FileName = "jhfp" + exportTime + ".doc";
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
     * 下载word
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
            throw new BusinessException("下载word失败", e);
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
		 * 导出word
		 * 
		 * @param request
		 * @param response
	     * @throws java.io.IOException 
	     * @throws TemplateException 
		 */
		@RequestMapping(params = "doSendAudit")
		@ResponseBody
	    public AjaxJson doSendAudit(HttpServletRequest request,HttpServletResponse response){
	    	AjaxJson j = new AjaxJson();
	    	
	    	String jhid =  request.getParameter("id");
	    	StringBuilder sb = new StringBuilder();
	    	
	    	//检查计划相关的 各计划文件类项目是否有做分配，如果还是0，或没有记录，则不通过
	    	String sql = "select f.JE, p.PROJECT_NAME from t_pm_fpje f inner join t_pm_project p on f.xmid = p.id  where f.jhwjid = ?  and p.LXYJ in( '1','3' ) and p.Scbz<>'1' ";
			List<Map<String, Object>> list = this.tBPmProjectPlanService.findForJdbc(sql, jhid);
			
			if(list.size() == 0) {
				sb.append("当前计划下暂无任何立项依据为计划文件的项目");
			}else {
				for(Map<String, Object> xm : list) {
					String je = (String)xm.get("JE");
					if(Double.valueOf(je)<=0) {
						sb.append("[").append(xm.get("PROJECT_NAME")).append("] 项目还未填分配金额#");
					}
				}
			}
			
			if(sb.length()>0) {
				j.setSuccess(false);
				j.setMsg(sb.toString());
			}else {
				j.setSuccess(true);
			}
			
	    	return j;
	    }
		
		/**
		 * 导出word
		 * 
		 * @param request
		 * @param response
	     * @throws java.io.IOException 
	     * @throws TemplateException 
		 */
		@RequestMapping(params = "sendAudit")
		@ResponseBody
	    public AjaxJson sendAudit(HttpServletRequest request,HttpServletResponse response){
	    	AjaxJson j = new AjaxJson();
	    	TSUser user =  ResourceUtil.getSessionUserName();
	    	String jhid =  request.getParameter("id");
	    	String receiveUseIds = request.getParameter("receiveUseIds");
	    	String receiveUseNames = request.getParameter("receiveUseNames");
	    	String senderTip = request.getParameter("senderTip");
	    	
	    	//检查计划相关的 各计划文件类项目是否有做分配，如果还是0，或没有记录，则不通过
	    	String msg = "操作成功";
	    	try {
	    		String sql = "update t_Pm_Project_plan  set receive_UseIds=? , receive_UseNames=? ,"
	    				+ "submit_Time=sysdate , submit_userId=? , submit_userName=?, submit_Msg=?,"
	    				+ "aduit_status=1 where id= ?";
	    		this.tBPmProjectPlanService.executeSql(sql,  receiveUseIds, receiveUseNames, user.getId(), user.getRealName(), senderTip, jhid);
	    	}catch(Exception e) {
	    		msg="操作失败";
	    	}
	    	j.setMsg(msg);
	    	return j;
	    }


    /**
     * 计划下达-批量导入项目信息
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "batchImportProject")
    @ResponseBody
    public String batchImportProject(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName ="";
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();

            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String uploadPath = System.getProperty("catalina.home")+"\\webapps\\excelFile\\";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMSS");
                    fileName = sdf.format(new Date()) + "_" + file.getOriginalFilename();
                    String path = uploadPath + fileName;
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return fileName;
    }
		
	  
}
