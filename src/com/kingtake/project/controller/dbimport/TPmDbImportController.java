package com.kingtake.project.controller.dbimport;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingtake.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.cgform.service.migrate.MigrateForm;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;
import com.kingtake.common.service.ExportXmlServiceI;
import com.kingtake.common.service.ImportXmlServiceI;
import com.kingtake.project.controller.contractnode.TPmContractNodeController;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractnodecheck.TPmOutContractNodeCheckEntity;
import com.kingtake.project.entity.dbimport.TPmDBImportEntity;
import com.kingtake.project.entity.funds.TPmProjectBalanceEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.approutcomecontract.TPmOutcomeContractApprServiceI;
import com.kingtake.project.service.contractnodecheck.TPmOutContractNodeCheckServiceI;
import com.kingtake.project.service.dbimport.TPmDBImportServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * ????????????
 *
 * @author admin
 *
 */
@SuppressWarnings("CheckStyle")
@Scope("prototype")
@Controller
@RequestMapping("/dbImportController")
public class TPmDbImportController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractNodeController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private ExportXmlServiceI exportXmlServiceI;

    @Autowired
    private ImportXmlServiceI importXmlServiceI;

    @Autowired
    private TPmDBImportServiceI tPmDBImportService;

    @Autowired
    private TPmProjectServiceI tPmProjectService;

    @Autowired
    private TPmOutcomeContractApprServiceI tPmOutcomeContractApprService;

    @Autowired
    private TPmOutContractNodeCheckServiceI tPmOutContractNodeCheckService;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();

    /**
     * ???????????????????????????
     *
     * @param request ??????
     * @return ModelAndView ??????
     */
    @RequestMapping(params = "dbimport")
    public ModelAndView dbimport(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/dbimport/dbImportList");
    }


    /**
     * easyui AJAX????????????
     *
     * @param dbImport
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TPmDBImportEntity dbImport, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmDBImportEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dbImport,
                request.getParameterMap());
        cq.addOrder("impTime", SortDirection.desc);
        cq.add();
        this.tPmDBImportService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmDBImportEntity dbImport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        dbImport = systemService.getEntity(TPmDBImportEntity.class, dbImport.getId());
        message = "??????????????????????????????";
        try {
            tPmDBImportService.delImport(dbImport, request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmDBImportEntity dbImport, HttpServletRequest req) {
        if (StringUtils.isNotEmpty(dbImport.getId())) {
            dbImport = this.tPmDBImportService.get(TPmDBImportEntity.class, dbImport.getId());
            req.setAttribute("dbImport", dbImport);
        }
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNode-update");
    }

    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
        this.tPmDBImportService.importDB(request);
            j.setMsg("?????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/dbimport/dbimport-upload");
    }

    /**
     * ????????????????????????excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportProjectXls")
    public void exportProjectXls(TPmProjectEntity tpmProject, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Workbook wb = this.tPmProjectService.exportProject();
            String fileName = "?????????????????????.xls";
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(fileName.getBytes(), "iso8859-1"));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
    }


    /**
     * ??????excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsHt")
    public String exportXlsHt(TPmProjectEntity tpmProject, TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
    		HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<TPmProjectEntity> tpmProjects = this.tPmProjectService.findByQueryString("select h from TPmProjectEntity as h,TPmOutcomeContractApprEntity as t where h.id=t.project and t.finishFlag='2'");
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:", "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tpmProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr);
        TSUser user = ResourceUtil.getSessionUserName();
        List<TPmOutcomeContractApprEntity> tpmProjects = this.tPmOutcomeContractApprService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmOutcomeContractApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????", "?????????:", "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tpmProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsJd")
    public String exportXlsJd(TPmOutContractNodeCheckEntity tPmOutContractNodeCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmOutContractNodeCheckEntity.class);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutContractNodeCheck);
        TSUser user = ResourceUtil.getSessionUserName();
        List<TPmOutContractNodeCheckEntity> tpmProjects = this.tPmOutContractNodeCheckService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmOutContractNodeCheckEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:", "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tpmProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ?????????????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToYszb")
    @ResponseBody
    public AjaxJson exportXmlToYszb(HttpServletRequest request, String classPath) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToYszb();
        	if(list != null && list.size() > 0) {
//        		for ( int a = 0;a < list.size();a++) {
//            		Map map = (Map)list.get(a);
//            		map.put("xh", "");
//            		map.put("dqzt", "0");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_mxys_zb", classPath);
            	if (x > 0) {
            	    j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ?????????????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToYsmx")
    @ResponseBody
    public AjaxJson exportXmlToYsmx(HttpServletRequest request, String classPath) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToYsmx();
        	if (list != null && list.size() > 0){
//        		for (int a = 0;a < list.size();a++){
//            		Map map = (Map)list.get(a);
//            		map.put("xh", "");
//            		map.put("bz", "");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_mxys_mx", classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ???????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToDkzb")
    @ResponseBody
    public AjaxJson exportXmlToDkzb(HttpServletRequest request, String classPath,String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToDkzb(year);
        	if (list != null && list.size() > 0) {
//        		for (int a = 0;a < list.size();a++) {
//            		Map map = (Map)list.get(a);
//            		map.put("id", "");
//            		map.put("fppzh", "");
//            		map.put("htbh", "");
//            		map.put("bz", "0");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_dkrl_zb", classPath);
        		if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ?????????????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToDkmx")
    @ResponseBody
    public AjaxJson exportXmlToDkmx(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToDkmx(year);
        	if (list != null && list.size() > 0) {
//        		for (int a = 0;a < list.size();a++){
//            		Map map = (Map)list.get(a);
//            		map.put("id", "");
//            		map.put("sxh", "");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_dkrl_mx", classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ?????????????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToFpmx")
    @ResponseBody
    public AjaxJson exportXmlToFpmx(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToFpmx(year,null);
        	if(list!=null && list.size()>0){
//        		for(int a=0;a<list.size();a++){
//            		Map map = (Map)list.get(a);
//            		String fp1 = "";
//            		String fp2 = "";
//            		if(map.get("INVOICE_NUM1") != null){
//            			fp1 = map.get("INVOICE_NUM1").toString();
//            		}
//            		if(map.get("INVOICE_NUM2") != null){
//            			fp2 = map.get("INVOICE_NUM2").toString();
//            		}
//            		if(!StringUtils.isEmpty(fp2)){
//            			map.put("pjh", fp1+","+fp2);
//            		}else{
//            			map.put("pjh", fp1);
//            		}
//            		map.remove("INVOICE_NUM1");
//            		map.remove("INVOICE_NUM2");
//            		map.put("id", "");
//            		map.put("sxh", "");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_dkrl_pjmx" ,classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToXnxz")
    @ResponseBody
    public AjaxJson exportXmlToXnxz(HttpServletRequest request, String classPath,String year) {
    	AjaxJson j = new AjaxJson();
        try {
//        	List list = this.tPmDBImportService.exportXmlToXnxz(year);
            List list = new ArrayList();
        	if(list!=null && list.size()>0){
        		int x = this.exportXmlServiceI.toXmlService(list, "td_xnxz_zb", classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToXnmx")
    @ResponseBody
    public AjaxJson exportXmlToXnmx(HttpServletRequest request, String classPath,String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List htList = null;
        	List jhList = null;
			Map flagMap = null;
//        	List list = this.tPmDBImportService.exportXmlToXnmx(year);
            List list = new ArrayList();

        	if (list != null && list.size( ) > 0) {
        		int x = this.exportXmlServiceI.toXmlService(list, "td_xnxz_mx", classPath);
        		if (x > 0) {
        			j.setMsg("??????????????????D???exportCw????????????");
        		} else {
        			j.setSuccess(false);
        			j.setMsg("???????????????");
        		}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToDzjf")
    @ResponseBody
    public AjaxJson exportXmlToDzjf(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToDzjf(year);
        	//???????????????????????????????????????????????????????????????????????????????????????????????????
        	if(list!=null && list.size()>0){
        		int x = this.exportXmlServiceI.toXmlService(list, "td_jfdz" ,classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }


    /**
     * ????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToDzjfmx")
    @ResponseBody
    public AjaxJson exportXmlToDzjfmx(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToDzjfmx(year);
        	if (list != null && list.size() > 0) {
        		int x = this.exportXmlServiceI.toXmlService(list,"td_jfdz_gdmx", classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }


    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToKjnd")
    @ResponseBody
    public AjaxJson exportXmlToKjnd(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	int jhbh = this.tPmDBImportService.getMaxCwjhbh(year);
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("kjnd", year);
        	map.put("jhbh", String.valueOf(jhbh));
        	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        	list.add(map);
        		int x = this.exportXmlServiceI.toXmlService(list, "td_kjnd", classPath);
            	if (x > 0) {
            		j.setMsg("??????????????????D???exportCw????????????");
            	} else {
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
            	j.setObj(jhbh);
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToTzys")
    @ResponseBody
    public AjaxJson exportXmlToTzys(HttpServletRequest request, String classPath) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToTzys();
        	if(list!=null && list.size()>0){
        		int x = this.exportXmlServiceI.toXmlService(list, "td_tzyssq" ,classPath);
        		if(x>0){
        			j.setMsg("??????????????????D???exportCw????????????");
        		}else{
        			j.setSuccess(false);
        			j.setMsg("???????????????");
        		}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToJhzb")
    @ResponseBody
    public AjaxJson exportXmlToJhzb(HttpServletRequest request, String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToJhzb(year);
        	if(list!=null && list.size()>0){
//        		for(int a=0;a<list.size();a++){
//            		Map map = (Map)list.get(a);
//            		map.put("id", "");
//            		map.put("tzr", "");
//            		map.put("shr", "");                                                                                                                                                                                                                                                                                                                             .
//            		map.put("bz", "");
//            		map.put("fppzh", "");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_jhfp_zb",classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToJhmx")
    @ResponseBody
    public AjaxJson exportXmlToJhmx(HttpServletRequest request,String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.exportXmlToJhmx(year);
        	if(list!=null && list.size()>0){
//        		for(int a=0;a<list.size();a++){
//            		Map map = (Map)list.get(a);
//            		map.put("id", "");
//            		map.put("sxh", "");
//            		map.put("bz", "");
//            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_jhfp_mx",classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @param request
     * @param classPath
     */
    public AjaxJson exportXmlToGdmx (HttpServletRequest request, String classPath, String year){
        AjaxJson j = new AjaxJson();
        try {
            List list = this.tPmDBImportService.exportXmlToGdmx(year);
            if(list!=null && list.size()>0){
                int x = this.exportXmlServiceI.toXmlService(list, "td_gdmx",classPath);
                if(x>0){
                    j.setMsg("??????????????????D???exportCw????????????");
                }else{
                    j.setSuccess(false);
                    j.setMsg("???????????????");
                }
            }
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ???????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToProject")
    @ResponseBody
    public AjaxJson exportXmlToProject(HttpServletRequest request,String classPath, String year) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.selectProject();
        	if(list!=null && list.size()>0){
        		for(int a=0;a<list.size();a++){
            		Map map = (Map)list.get(a);
                    map.put("jtrq", "");
                    map.put("bz", "");

                    String id = map.get("id").toString();
                    List invoiveList = this.tPmDBImportService.exportXmlToFpmx(year, id);
                    if (invoiveList != null && invoiveList.size() > 0) {
                        map.put("FPBZ", false);
                    }else{
                        map.put("FPBZ", true);
                    }
                    map.remove("ID");
            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_xmxx" , classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ???????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToOutCome")
    @ResponseBody
    public AjaxJson exportXmlToOutCome(HttpServletRequest request, String classPath) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.selectOutCome();
        	if(list!=null && list.size()>0){
        		for(int a=0;a<list.size();a++){
            		Map map = (Map)list.get(a);
            		map.put("guid", map.remove("ID"));
            		map.put("xmdm", map.remove("PROJECT_NO"));
            		map.put("xh", "");
            		map.put("qdrq", map.remove("CONTRACT_SIGNING_TIME"));
            		map.put("htbh", map.remove("CONTRACT_CODE"));
            		map.put("htmc", map.remove("CONTRACT_NAME"));
            		map.put("qsrq", map.remove("START_TIME"));
            		map.put("zzrq", map.remove("END_TIME"));
            		map.put("yf", map.remove("UNIT_NAME_B"));
            		map.put("cgfs", map.remove("ACQUISITION_METHOD"));
            		map.put("htlx", "");
            		map.put("zjf", map.remove("TOTAL_FUNDS"));
            		map.put("bz", "");
            		map.put("khh", map.remove("BANK_B"));
            		map.put("hm", map.remove("ACCOUNT_NAME_B"));
            		map.put("zh", map.remove("ACCOUNT_ID_B"));
            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_wxqk" ,classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ???????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToOutCome2")
    @ResponseBody
    public AjaxJson exportXmlToOutCome2(HttpServletRequest request, String classPath) {
        AjaxJson j = new AjaxJson();
        try {
            List list = this.tPmDBImportService.selectOutCome2();
            if(list!=null && list.size()>0){
                int x = this.exportXmlServiceI.toXmlService(list, "td_wxqk" ,classPath);
                if(x>0){
                    j.setMsg("??????????????????D???exportCw????????????");
                }else{
                    j.setSuccess(false);
                    j.setMsg("???????????????");
                }
            }
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param request
     * @param classPath
     */
    @RequestMapping(params = "exportXmlToOutComeNode")
    @ResponseBody
    public AjaxJson exportXmlToOutComeNode(HttpServletRequest request, String classPath) {
    	AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.selectOutComeNode();
        	if(list!=null && list.size()>0){
        		for(int a=0;a<list.size();a++){
            		Map map = (Map)list.get(a);
//            		map.put("guid", map.remove("ID"));
            		map.put("xh", "");
            		map.put("sxh", "");
            		map.put("fkrq", map.remove("COMPLETE_DATE"));
            		map.put("je", map.remove("PAY_AMOUNT"));
            		if(map.get("FINISH_TIME")!=null && !"".equals(map.get("FINISH_TIME"))){
            			map.put("zfbz", "1");
            		}else{
            			map.put("zfbz", "0");
            		}
            		if(map.get("FINISH_TIME")!=null && !"".equals(map.get("FINISH_TIME"))){
            			map.put("zfzt", "1");
            		}else{
            			map.put("zfzt", "0");
            		}
            		map.remove("FINISH_TIME");
            		map.put("wcqk", "");
            		map.put("bz", "");
            	}

        		int x = this.exportXmlServiceI.toXmlService(list, "td_wxqk_zfjd",classPath);
            	if(x>0){
            		j.setMsg("??????????????????D???exportCw????????????");
            	}else{
            		j.setSuccess(false);
            		j.setMsg("???????????????");
            	}
        	}
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
        return j;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping(params = "ImportCw")
    public ModelAndView importDkxx(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/dbimport/dbimport-xml-ajax");
    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @RequestMapping(params = "exportXmlToCwOpen")
    public ModelAndView exportXmlToCwOpen(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/dbimport/dbexport-xml");
    }

    /**
     * ??????xml???
     *
     * @return
     */
    @RequestMapping(params = "importXml", method = RequestMethod.POST)
    @ResponseBody
    public String importXml(HttpServletRequest request, HttpServletResponse response) {
        //AjaxJson j = new AjaxJson();
        try {
        	List list = this.tPmDBImportService.importXml(request);
        	//???????????????????????????????????????????????????????????????????????????
        	if(list.contains("td_jzpzzb.xml") == true){
        		String sql = "delete from T_PM_INCOME";
		    	int count = this.systemService.executeSql(sql);
        	}

        	Object jhbh = null;
        	if(list.contains("td_kjnd.xml") == true){
        		String fileV = list.get(0).toString()+"\\td_kjnd.xml";
        		File dirCwnd = new File(fileV);
        		List kjndList = this.importXmlServiceI.importXml(dirCwnd);
        		Map valMap = this.tPmDBImportService.validateFirst(kjndList);
        		Object cwnd = valMap.get("cwnd");
        		Object isFirst = valMap.get("isFirst");
        		jhbh = valMap.get("jhbh");

        		if("1".equals(isFirst)){
        			List xmlList = new ArrayList<Map>();
                	String file = "";
                	for(int a=1;a<list.size();a++){
                		file = list.get(0).toString()+"\\"+list.get(a).toString();
                		File dir = new File(file);
                		xmlList = this.importXmlServiceI.importXml(dir);
                		//????????????????????????????????????????????????,????????????????????????????????????
                		if(dir.getName().equals("td_xmye.xml")){
                			for (int i = 0; i < xmlList.size(); i++) {
                				Map map = (Map) xmlList.get(i);
                				List<TPmProjectBalanceEntity> tPmProjectBalanceEntities = this.systemService.findByProperty(TPmProjectBalanceEntity.class, "projectNo", map.get("xmdm").toString());
                				if(tPmProjectBalanceEntities.size() != 0){
                					this.systemService.deleteAllEntitie(tPmProjectBalanceEntities);
                					TPmProjectEntity tPmProjectEntity = this.systemService.findUniqueByProperty(TPmProjectEntity.class, "projectNo", map.get("xmdm").toString());
                					if(tPmProjectEntity!=null){
                						tPmProjectEntity.setTzys_status("3");
                    					this.systemService.save(tPmProjectEntity);
                					}
                				}
                			}
                		}
                		this.tPmDBImportService.saveXml(xmlList,cwnd);
                	}
        		}else{
        			//j.setMsg("?????????????????????????????????"+jhbh+"????????????");
        			return "?????????????????????????????????"+jhbh+"????????????";
        		}
        	}

            //j.setMsg("??????????????????,??????????????????"+jhbh);
        	return "??????????????????,??????????????????"+jhbh;
        } catch (Exception e) {
            e.printStackTrace();
            //.setSuccess(false);
            //j.setMsg("?????????????????????");
            return "?????????????????????";
        }
        //return j;
    }

    /**
     * ??????xml???
     *
     * @return
     */
    @RequestMapping(params = "downloadTemplate")
    public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) {
        OutputStream out = null;
        InputStream templateIs = null;
        try {
        	String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        	String fileName = "cs.kyd";
        	String path = classPath + "exportCw\\cs.kyd";

            	templateIs = new FileInputStream(path);
                fileName = POIPublicUtil
                        .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            out = response.getOutputStream();
            IOUtils.copy(templateIs, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("?????????????????????", e);
        } finally {
            IOUtils.closeQuietly(templateIs);
            IOUtils.closeQuietly(out);
        }
    }

    @RequestMapping(params = "exportXmlToCw", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson exportXmlToCw(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
        	String year = request.getParameter("year");
        	String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        	MigrateForm.delAllFile(classPath + "/exportCw/");
        	//??????????????????
        	AjaxJson j1 = exportXmlToKjnd(request,classPath,year);
        	j.setObj(j1.getObj());//??????????????????

        	//?????????????????????????????????????????????????????????????????????????????????????????????cw_status??????4??????????????????
        	exportXmlToOutCome2(request,classPath);
        	exportXmlToOutComeNode(request,classPath);
        	exportXmlToProject(request,classPath,year);
        	//???????????????????????????????????????????????????????????????cw_status??????4??????????????????
        	exportXmlToYsmx(request,classPath);
        	exportXmlToYszb(request,classPath);
        	//????????????????????????????????????????????????????????????????????????
        	exportXmlToDkmx(request,classPath,year);
        	exportXmlToFpmx(request,classPath,year);
        	exportXmlToDkzb(request,classPath,year);
        	//???????????????????????????????????????????????????????????????????????????????????????????????????
        	exportXmlToXnmx(request,classPath,year);
        	exportXmlToXnxz(request,classPath,year);
        	//??????,????????????
        	exportXmlToDzjf(request,classPath,year);
        	exportXmlToDzjfmx(request,classPath,year);

        	//???????????????????????????T_PM_PROJECT??????TZYS_STATUS?????????????????????????????????1-????????????????????????????????????????????????2-??????????????????
        	exportXmlToTzys(request,classPath);

        	//????????????????????????????????????????????????
        	exportXmlToJhmx(request,classPath,year);
        	exportXmlToJhzb(request,classPath,year);

        	//??????????????????
            exportXmlToGdmx(request,classPath,year);

//        	OutputStream out = null;
//            InputStream templateIs = null;

        	MigrateForm.zipXml(classPath + "/exportCw/cs.zip", "", classPath + "/exportCw/");
        	File file = new File(classPath + "/exportCw/cs.zip");
        	file.renameTo(new File(classPath + "/exportCw/cs.kyd"));

//        	String filename = file.getName();
//        	String path = classPath + "exportCw\\cs.kyd";
//
//        	InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            // ??????response
//            response.reset();
//            // ??????response???Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();

//        	templateIs = new FileInputStream(classPath + "exportCw" + File.separator + "cs.kyd");
//          fileName = "????????????????????????.xls";
//          fileName = POIPublicUtil
//                  .processFileName(request, fileName);
//      response.setHeader("Content-Disposition", "attachment; filename="
//              + "cs.kyd");
//      out = response.getOutputStream();
//      IOUtils.copy(templateIs, out);


//        	MigrateForm.delAllFile("D:/exportCw/td_dkrl_mx.xml");
//        	MigrateForm.delFolder("D:/exportCw/td_dkrl_pjmx.xml");
//        	File file_td_dkrl_mx = new File("D:/exportCw/td_dkrl_mx.xml");
//        	if(file_td_dkrl_mx.exists()){
//        		file_td_dkrl_mx.delete();
//        	}
//
//        	file_td_dkrl_mx.delete();
//        	File file_td_dkrl_pjmx = new File("D:/exportCw/td_dkrl_pjmx.xml");
//        	file_td_dkrl_pjmx.delete();
//        	File file_td_dkrl_zb = new File("D:/exportCw/td_dkrl_zb.xml");
//        	file_td_dkrl_zb.delete();
//        	File file_td_jfdz = new File("D:/exportCw/td_jfdz.xml");
//        	file_td_jfdz.delete();
//        	File file_td_jhfp_mx = new File("D:/exportCw/td_jhfp_mx.xml");
//        	file_td_jhfp_mx.delete();
//        	File file_td_jhfp_zb = new File("D:/exportCw/td_jhfp_zb.xml");
//        	file_td_jhfp_zb.delete();
//        	File file_td_mxys_mx = new File("D:/exportCw/td_mxys_mx.xml");
//        	file_td_mxys_mx.delete();
//        	File file_td_mxys_zb = new File("D:/exportCw/td_mxys_zb.xml");
//        	file_td_mxys_zb.delete();
//        	File file_td_tzys = new File("D:/exportCw/td_tzys.xml");
//        	file_td_tzys.delete();
//        	File file_td_wxqk = new File("D:/exportCw/td_wxqk.xml");
//        	file_td_wxqk.delete();
//        	File file_td_wxqk_zfjd = new File("D:/exportCw/td_wxqk_zfjd.xml");
//        	file_td_wxqk_zfjd.delete();
//        	File file_td_xmxx = new File("D:/exportCw/td_xmxx.xml");
//        	file_td_xmxx.delete();
//        	File file_td_xnxz_mx = new File("D:/exportCw/td_xnxz_mx.xml");
//        	file_td_xnxz_mx.delete();
//        	File file_td_xnxz_zb = new File("D:/exportCw/td_xnxz_zb.xml");
//        	file_td_xnxz_zb.delete();

        	TSUser user = ResourceUtil.getSessionUserName();
        	TPmDBImportEntity dbImportEntity = new TPmDBImportEntity();
        	dbImportEntity.setFileName("cs.kyd");
        	dbImportEntity.setImpTime(new java.util.Date());
        	dbImportEntity.setImpUserId(user.getId());
        	dbImportEntity.setImpUserName(user.getRealName());
        	dbImportEntity.setFilePath(classPath + "/exportCw/cs.kyd");
        	this.systemService.save(dbImportEntity);

//            j.setMsg("??????????????????D???exportCw????????????");

//            String downloadurlString = classPath + "\\exportCw\\cs.kyd";
//            j.setObj(downloadurlString);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }

    /**
	 * ??????word
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "ExportDkWord")
	public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        Map<String, Object> map = new HashMap<String, Object>();

		try
	    {
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
	      //??????. ??????????????????(=European Article Number)
	      //??????????????????????????????
	      String str = "788515004012";
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
	      saveToGIF(localBufferedImage, "EAN13.gif");
	      localJBarcode.setEncoder(Code39Encoder.getInstance());
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
	      localJBarcode.setShowCheckDigit(false);
	      //xx
	      str = "JBARCODE-39";
	      localBufferedImage = localJBarcode.createBarcode(str);
	      saveToPNG(localBufferedImage, "Code39.png");

//	      String imageString = getImageBinary("D:/Code39.png");
//	      map.put("htbh", imageString);
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }

        modelMap.put(TemplateWordConstants.FILE_NAME, "?????????????????????????????????_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/sjjh.docx");

        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}

	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString)
	  {
	    saveToFile(paramBufferedImage, paramString, "jpeg");
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
	        Template template = configuration.getTemplate("sjjh.ftl");
	        /** ???????????? **/
	        Map<String,String> dataMap = new HashMap<>();

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
		      str = "KD2017000001";
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

	        /** ???ftl????????????${textDeal}????????????**/
//	        dataMap.put("textDeal","?????????????????????");
	        /** ????????????**/
	        dataMap.put("ewmImage",encoder.encode(data));

	        /** ????????????word??????????????? **/
	        Date now = new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//?????????????????????????????????
	        String exportTime = dateFormat.format( now );
	        String outFilePath = classPath + "\\exportWord\\dkdj" + exportTime + ".doc";
	        File docFile = new File(outFilePath);
	        FileOutputStream fos = new FileOutputStream(docFile);
	        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
	        try {
				template.process(dataMap,out);
				String FileName = "dkdj" + exportTime + ".doc";
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
}
