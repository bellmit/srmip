package com.kingtake.office.controller.sendReceive;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kingtake.base.entity.senddocunit.TBSendDocUnitEntity;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.sendReceive.TORegHistoryEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

/**
 * @Title: Controller
 * @Description: 收发文登记
 * @author onlineGenerator
 * @date 2015-07-15 19:36:41
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOSendReceiveRegController")
public class TOSendReceiveRegController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOSendReceiveRegController.class);

    @Autowired
    private TOSendReceiveRegServiceI tOSendReceiveRegService;
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
     * 收发文登记列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOSendReceiveReg")
    public ModelAndView tOSendReceiveReg(HttpServletRequest request) {
        String registerType = request.getParameter("registerType");
        if (StringUtil.isNotEmpty(registerType)) {
            request.setAttribute("registerType", registerType);
            if (registerType.equals("0")) {
                request.setAttribute("title", "收文登记");
            } else if (registerType.equals("1")) {
                request.setAttribute("title", "发文登记");
            }
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveRegList");
    }

    /**
     * easyui AJAX请求数据 选择列表用
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSendReceiveReg,
                request.getParameterMap());
        try {
            // 自定义追加查询条件
            String query_registerDate_begin = request.getParameter("registerDate_begin");
            String query_registerDate_end = request.getParameter("registerDate_end");
            if (StringUtil.isNotEmpty(query_registerDate_begin)) {
                cq.ge("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_begin));
            }
            if (StringUtil.isNotEmpty(query_registerDate_end)) {
                cq.le("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.addOrder("generationFlag", SortDirection.asc);
        cq.add();
        this.tOSendReceiveRegService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据 收发文登记列表用
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagridForList")
    public void datagridForList(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSendReceiveReg,
                request.getParameterMap());
        try {
            // 自定义追加查询条件
            String query_registerDate_begin = request.getParameter("registerDate_begin");
            String query_registerDate_end = request.getParameter("registerDate_end");
            if (StringUtil.isNotEmpty(query_registerDate_begin)) {
                cq.ge("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_begin));
            }
            if (StringUtil.isNotEmpty(query_registerDate_end)) {
                cq.le("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_end));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询收发文登记出错", e);
        }
        TSUser user = ResourceUtil.getSessionUserName();
        cq.or(Restrictions.eq("createBy", user.getUserName()), Restrictions.eq("archiveUserid", user.getId()));
        cq.addOrder("createDate", SortDirection.desc);
//        cq.addOrder("generationFlag", SortDirection.asc);
        cq.add();
        this.tOSendReceiveRegService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除收发文登记
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOSendReceiveReg = systemService.getEntity(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
        message = "收发文登记删除成功";
        try {
            tOSendReceiveRegService.delete(tOSendReceiveReg);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收发文登记删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除收发文登记
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收发文登记删除成功";
        try {
            for (String id : ids.split(",")) {
                TOSendReceiveRegEntity tOSendReceiveReg = systemService.getEntity(TOSendReceiveRegEntity.class, id);
                tOSendReceiveRegService.delete(tOSendReceiveReg);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "收发文登记删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加收发文登记
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收发文登记添加成功";
        try {
            String merge = (StringUtils.isEmpty(tOSendReceiveReg.getFileNumPrefix()) ? "" : tOSendReceiveReg
                    .getFileNumPrefix())
                    + (StringUtils.isEmpty(tOSendReceiveReg.getFileNumYear()) ? "" : ("〔20"
                            + tOSendReceiveReg.getFileNumYear() + "〕")) + tOSendReceiveReg.getFileNum();
            tOSendReceiveReg.setMergeFileNum(merge);
            if (StringUtil.isEmpty(tOSendReceiveReg.getOffice())) {
                String receiveUnitName1 = request.getParameter("receiveUnitName1");
                CriteriaQuery cq = new CriteriaQuery(TBSendDocUnitEntity.class);
                cq.eq("unitName", receiveUnitName1);
                cq.add();
                List<TBSendDocUnitEntity> list = systemService.getListByCriteriaQuery(cq, false);
                if (list.size() <= 0) {//当输入的来文单位不存在时加到来文单位中去
                    TBSendDocUnitEntity sdu = new TBSendDocUnitEntity();
                    sdu.setUnitName(receiveUnitName1);
                    systemService.save(sdu);
                }
                tOSendReceiveReg.setOffice(receiveUnitName1);
            }
            if (tOSendReceiveReg.getRegisterType().equals("0")) {
                //收文登记创建人即为归档人
                tOSendReceiveReg.setArchiveUserid(ResourceUtil.getSessionUserName().getId());
            }
            tOSendReceiveRegService.save(tOSendReceiveReg);
            //保存成功变更流水号
            String businessCode = request.getParameter("businessCode");
            String currentNum = request.getParameter("currentNum");
            PrimaryGenerater gen = PrimaryGenerater.getInstance();
            gen.updateNext(businessCode, currentNum);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收发文登记添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOSendReceiveReg);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新收发文登记
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收发文登记更新成功";
        TOSendReceiveRegEntity t = tOSendReceiveRegService.get(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
        try {
            Boolean typeFlag = false;
            if (StringUtils.isNotEmpty(tOSendReceiveReg.getFilesId())) {
                typeFlag = tOSendReceiveReg.getFilesId().equals(t.getFilesId());//判断案卷号是否发生改变
            }
            MyBeanUtils.copyBeanNotNull2Bean(tOSendReceiveReg, t);
            String merge = (StringUtils.isEmpty(t.getFileNumPrefix()) ? "" : t.getFileNumPrefix())
                    + (StringUtils.isEmpty(t.getFileNumYear()) ? "" : ("〔20" + t.getFileNumYear()) + "〕")
                    + t.getFileNum();
            t.setMergeFileNum(merge);
            tOSendReceiveRegService.saveOrUpdate(t);
            if (!typeFlag) {//案卷号发生变化则变更流水号记录表
                //变更流水号
                String businessCode = request.getParameter("businessCode");
                String currentNum = request.getParameter("currentNum");
                PrimaryGenerater gen = PrimaryGenerater.getInstance();
                gen.updateNext(businessCode, currentNum);
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收发文登记更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOSendReceiveReg);
        j.setMsg(message);
        return j;
    }

    /**
     * 收发文登记新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest req) {
        String registerType = req.getParameter("registerType");
        if (StringUtil.isNotEmpty(registerType)) {
            req.setAttribute("registerType", registerType);
            tOSendReceiveReg.setRegisterType(registerType);
        }
        tOSendReceiveReg.setGenerationFlag(SrmipConstants.NO);
        tOSendReceiveReg.setRegisterDate(new Date());
        if (StringUtil.isNotEmpty(tOSendReceiveReg.getId())) {
            tOSendReceiveReg = tOSendReceiveRegService
                    .getEntity(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
        }
        req.setAttribute("tOSendReceiveRegPage", tOSendReceiveReg);
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-add");
    }

    /**
     * 收发文登记编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendReceiveReg.getId())) {
            tOSendReceiveReg = tOSendReceiveRegService
                    .getEntity(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
            req.setAttribute("tOSendReceiveRegPage", tOSendReceiveReg);
            if (StringUtils.isNotEmpty(tOSendReceiveReg.getContentFileId())) {
                TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOSendReceiveReg.getContentFileId());
                req.setAttribute("file", file);
            }
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-update");
    }

    @RequestMapping(params = "findModelByRegType")
    public ModelAndView findModelByRegType(HttpServletRequest req, String regType) {
        List<Map<String, Object>> list = tOSendReceiveRegService.findModelByRegType(regType);
        req.setAttribute("regType", regType);
        req.setAttribute("list", list);
        return new ModelAndView("com/kingtake/office/sendReceive/templateSelect");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        String regesterType = req.getParameter("regesterType");
        if (StringUtils.isNotEmpty(regesterType)) {
            req.setAttribute("regesterType", regesterType);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-upload");
    }

    /**
     * 选择收发文页面跳转
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "selectReg")
    public ModelAndView selectReg(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/sendReceive/RegList");
    }

    @RequestMapping(params = "getSerialNum")
    @ResponseBody
    public AjaxJson getSerialNum(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String nextNo = null;
        String businessCode = req.getParameter("businessCode");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());
        String likeStr = businessCode+year+"%";
        String sql = "select max(fileid) fileid from (select substr(t.files_id,7,10) fileid from t_o_send_receive_reg t where t.files_id like ?)";
        Map<String,Object> dataMap = this.systemService.findOneForJdbc(sql, new Object[]{likeStr});
        String fileid = (String) dataMap.get("fileid");
        if(StringUtils.isNotEmpty(fileid)){
            Integer num = Integer.parseInt(fileid);
            num++;
            nextNo = businessCode+year + PrimaryGenerater.getNumberStr(num,4);
        }else{
            nextNo = businessCode+year + "0001";
        }
        j.setObj(nextNo);
        return j;
    }
    
    @RequestMapping(params = "checkFileNum")
    @ResponseBody
    public AjaxJson checkFileNum(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String mergeFileNum = req.getParameter("mergeFileNum");
        String id = req.getParameter("id");
        CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class);
        if (StringUtil.isNotEmpty(id)) {
            cq.notEq("id", id);
        }
        cq.eq("mergeFileNum", mergeFileNum);
        cq.add();
        List<TOSendReceiveRegEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            j.setObj(false);
        } else {
            j.setObj(true);
        }
        return j;
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSendReceiveReg,
                request.getParameterMap());
        TSUser user = ResourceUtil.getSessionUserName();
        cq.or(Restrictions.eq("createBy", user.getUserName()), Restrictions.eq("archiveUserid", user.getId()));
        List<TOSendReceiveRegEntity> tOSendReceiveRegs = this.tOSendReceiveRegService.getListByCriteriaQuery(cq, false);
        String registerType = request.getParameter("registerType");
        if (StringUtil.isNotEmpty(registerType)) {
            if (registerType.equals(SrmipConstants.SWBZ)) {//SWBZ :收文列表
                modelMap.put(NormalExcelConstants.FILE_NAME, "收文登记");
                modelMap.put(NormalExcelConstants.CLASS, TOSendReceiveRegEntity.class);
                modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收文登记列表", "导出人:"
                        + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
            } else if (registerType.equals(SrmipConstants.FWBZ)) {//FWBZ :发文列表
                modelMap.put(NormalExcelConstants.FILE_NAME, "发文登记");
                modelMap.put(NormalExcelConstants.CLASS, TOSendReceiveRegEntity.class);
                modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("发文登记列表", "导出人:"
                        + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
            }
        }else{
            modelMap.put(NormalExcelConstants.FILE_NAME, "收发文登记");
            modelMap.put(NormalExcelConstants.CLASS, TOSendReceiveRegEntity.class);
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收发文登记列表", "导出人:"
                    + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        }
        modelMap.put(NormalExcelConstants.DATA_LIST, tOSendReceiveRegs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public void exportXlsByT(HttpServletRequest request, HttpServletResponse response) {
        String regesterType = request.getParameter("registerType");
        tOSendReceiveRegService.downloadTemplate(regesterType,request, response);
    }

    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String regesterType = request.getParameter("regesterType");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
            	String msg = "";
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
                if ("1".equals(regesterType)) {//发文
                	msg = tOSendReceiveRegService.importSendBillExcel(wb);
                }else if ("0".equals(regesterType)) {//收文
                    msg = tOSendReceiveRegService.importReceiveBillExcel(wb);
                }
                j.setMsg("文件导入成功！<br>" + msg);
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                e.printStackTrace();
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

    @RequestMapping(params = "getTemplateByRegType")
    @ResponseBody
    public JSONArray getTemplateByRegType(HttpServletRequest request, HttpServletResponse response) {
        JSONArray array = new JSONArray();
        String regType = request.getParameter("regType");
        List<Map<String, Object>> list = tOSendReceiveRegService.findModelByRegType(regType);
        if (list.size() > 0) {
            array = (JSONArray) JSON.toJSON(list);
        }
        return array;
    }

    /**
     * 收发文登记编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateBill")
    public ModelAndView goUpdateBill(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendReceiveReg.getId())) {
            tOSendReceiveReg = tOSendReceiveRegService
                    .getEntity(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
            req.setAttribute("tOSendReceiveRegPage", tOSendReceiveReg);
            if (StringUtils.isNotEmpty(tOSendReceiveReg.getContentFileId())) {
                TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                        tOSendReceiveReg.getContentFileId());
                req.setAttribute("file", file);
            }
            tOSendReceiveRegService.doHistory(tOSendReceiveReg);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-updateBill");
    }

    /**
     * 更新公文
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateBill")
    @ResponseBody
    public AjaxJson doUpdateBill(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "更新公文成功";
        try {
            this.tOSendReceiveRegService.updateBill(tOSendReceiveReg, request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新公文失败";
            j.setSuccess(false);
        }
        j.setObj(tOSendReceiveReg);
        j.setMsg(message);
        return j;
    }

    /**
     * 打包下载正文及附件
     * 
     * @return
     */
    @RequestMapping(params = "doZip")
    public void doZip(HttpServletRequest request, HttpServletResponse response) {
        String regId = request.getParameter("regId");
        TOSendReceiveRegEntity reg = this.systemService.get(TOSendReceiveRegEntity.class, regId);
        String contentFileId = reg.getContentFileId();
        List<Map<String, String>> pathMapList = new ArrayList<Map<String, String>>();

        if (StringUtils.isNotEmpty(contentFileId)) {
            TOOfficeOnlineFilesEntity file = this.systemService.get(TOOfficeOnlineFilesEntity.class, contentFileId);
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", reg.getTitle() + "." + file.getExtend());
            map.put("path", file.getRealpath());
            pathMapList.add(map);
        }

        List<TSFilesEntity> files = this.systemService.getAttachmentByCode(regId, "");
        if(files.size()>0){
            for(TSFilesEntity file:files){
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", file.getAttachmenttitle() + "." + file.getExtend());
                map.put("path", file.getRealpath());
                pathMapList.add(map);
            }
        }
        if (pathMapList.size() > 0) {
            this.systemService.doZip(request, response, pathMapList, regId, reg.getTitle() + ".zip");
        }
    }

    /**
     * 收发文登记编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCopy")
    public ModelAndView goCopy(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendReceiveReg.getId())) {
            tOSendReceiveReg = tOSendReceiveRegService
                    .getEntity(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
            TOSendReceiveRegEntity reg = new TOSendReceiveRegEntity();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tOSendReceiveReg, reg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            reg.setId(null);
            reg.setCertificates(null);
            reg.setCreateDate(new Date());
            reg.setGenerationFlag("0");
            reg.setContentFileId(null);
            reg.setFilesId(null);
            reg.setRegisterDate(new Date());
            req.setAttribute("tOSendReceiveRegPage", reg);
            req.setAttribute("registerType", reg.getRegisterType());
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-add");
    }

    /**
     * 收发文历史版本页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goHistory")
    public ModelAndView goHistory(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest req) {
        String regId = req.getParameter("regId");
        if (StringUtils.isNotEmpty(regId)) {
            req.setAttribute("regId", regId);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tORegHistoryList");
    }

    /**
     * 历史版本列表
     * 
     * @param tOSendReceiveReg
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForHistory")
    public void datagridForHistory(TORegHistoryEntity tORegHistory, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TORegHistoryEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                .installHql(cq, tORegHistory,
                request.getParameterMap());
        cq.addOrder("createTime", SortDirection.desc);
        cq.add();
        this.tOSendReceiveRegService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 打包下载正文及附件
     * 
     * @return
     */
    @RequestMapping(params = "doHistoryZip")
    public void doHistoryZip(HttpServletRequest request, HttpServletResponse response) {
        String historyId = request.getParameter("id");
        TORegHistoryEntity history = this.systemService.get(TORegHistoryEntity.class, historyId);
        String contentFileId = history.getContentFileId();
        List<Map<String, String>> pathMapList = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotEmpty(contentFileId)) {
            TOOfficeOnlineFilesEntity file = this.systemService.get(TOOfficeOnlineFilesEntity.class, contentFileId);
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", history.getTitle() + "." + file.getExtend());
            map.put("path", file.getRealpath());
            pathMapList.add(map);
        }
        List<TSFilesEntity> files = this.systemService.getAttachmentByCode(history.getAttachmentCode(), "");
        if (files.size() > 0) {
            for (TSFilesEntity file : files) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", file.getAttachmenttitle() + "." + file.getExtend());
                map.put("path", file.getRealpath());
                pathMapList.add(map);
            }
        }
        if (pathMapList.size() > 0) {
            this.systemService.doZip(request, response, pathMapList, historyId, history.getTitle() + ".zip");
        }
    }


}
