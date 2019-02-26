package org.jeecgframework.web.system.controller.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.pool.ThreadPool;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.base.TPBpmFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.convert.DoConvert;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.solr.SolrOperate;

/**
 * 通用业务处理
 * 
 * @author 张代浩
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/commonController")
public class CommonController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CommonController.class);
    private SystemService systemService;
    private String message;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 通用列表页面跳转
     */
    @RequestMapping(params = "listTurn")
    public ModelAndView listTurn(HttpServletRequest request) {
        String turn = request.getParameter("turn");// 跳转的目标页面
        return new ModelAndView(turn);
    }

    /**
     * 附件预览页面打开链接
     * 
     * @return
     */
    @RequestMapping(params = "openViewFile")
    public ModelAndView openViewFile(HttpServletRequest request) {
        String fileid = request.getParameter("fileid");
        String subclassname = oConvertUtils.getString(request.getParameter("subclassname"),
                "org.jeecgframework.web.system.pojo.base.TSAttachment");
        String contentfield = oConvertUtils.getString(request.getParameter("contentfield"));
        Class fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
        Object fileobj = systemService.getEntity(fileClass, fileid);
        ReflectHelper reflectHelper = new ReflectHelper(fileobj);
        String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));

        StringBuffer parentPath = request.getRequestURL();
        String path = request.getContextPath();
        path = parentPath.substring(0, parentPath.indexOf(path));
        if ("dwg".equals(extend)) {
            String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
            request.setAttribute("realpath", path + "/" + realpath);
            return new ModelAndView("common/upload/dwgView");
        } else if (FileUtils.isPicture(extend)) {
            String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
            request.setAttribute("realpath", path + "/" + realpath);
            request.setAttribute("fileid", fileid);
            request.setAttribute("subclassname", subclassname);
            request.setAttribute("contentfield", contentfield);
            return new ModelAndView("common/upload/imageView");
        } else {
            String swfpath = oConvertUtils.getString(reflectHelper.getMethodValue("swfpath"));
            request.setAttribute("swfpath", path + "/" + swfpath);
            System.out.println(request.getAttribute("swfpath"));
            return new ModelAndView("common/upload/swfView");
        }

    }
    
    /**
     * 价格库预览附件（业务所有相关）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "priceView")
    public ModelAndView priceView(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String bid = request.getParameter("bid");
        String index = request.getParameter("index");
        request.setAttribute("index", index);
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", bid);
        cq.add();
        cq.addOrder("createdate", SortDirection.asc);
        List<TSFilesEntity> flist = systemService.getListByCriteriaQuery(cq, false);
        CriteriaQuery cq2 = new CriteriaQuery(TSAttachment.class);
        cq.eq("id", flist.get(0).getId());
        cq.add();
        List<TSAttachment> fileZb = systemService.getListByCriteriaQuery(cq, false);
        System.out.println(fileZb.get(0).getIsconvert());
        if (flist.size() > 0) {
            String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            request.setAttribute("rootPath", rootPath);
            request.setAttribute("flist", fileZb);
        }
        
        String rp = new File(request.getRealPath("/")).getParent();
        String wjlj=fileZb.get(0).getRealpath();
        String realPath = rp+"\\"+wjlj;
        // 2.获取要下载的文件类型和文件名
        String fileType = realPath.substring(realPath.lastIndexOf(".") + 1);
        String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
        if(fileType.equals("pdf")){
        	response.setContentType("application/pdf");
        	ServletOutputStream sos = response.getOutputStream();
            File pdf = null;  
            FileInputStream fis = null;  
            byte[] buffer = new byte[1024*1024];  
            pdf = new File(realPath);  
            response.setContentLength((int) pdf.length());  
            fis = new FileInputStream(pdf);  
            int readBytes = -1;  
            while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){  
                sos.write(buffer, 0, 1024*1024);  
            }  
            sos.close();  
            fis.close();
        }else if(fileType.equals("docx") || fileType.equals("doc")){
            // 3.设置content-disposition响应头控制浏览器弹出保存框，若没有此句则浏览器会直接打开并显示文件。中文名要经过URLEncoder.encode编码，否则虽然客户端能下载但显示的名字是乱码
            response.setContentType("application/msword"); 
            response.setHeader("content-disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 4.获取要下载的文件输入流
            InputStream in = new FileInputStream(realPath);
            int len = 0;
            // 5.创建数据缓冲区
            byte[] buffer = new byte[1024];
            // 6.通过response对象获取OutputStream流
            OutputStream out = response.getOutputStream();
            // 7.将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
        }else if(fileType.equals("jpg") || fileType.equals("png")){
        	// 载入图像
            BufferedImage buffImg = ImageIO.read(new FileInputStream(realPath));
     
            // 将四位数字的验证码保存到Session中。
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
        }else{
        	response.setHeader("content-disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 4.获取要下载的文件输入流
            InputStream in = new FileInputStream(realPath);
            int len = 0;
            // 5.创建数据缓冲区
            byte[] buffer = new byte[1024];
            // 6.通过response对象获取OutputStream流
            OutputStream out = response.getOutputStream();
            // 7.将FileInputStream流写入到buffer缓冲区
            while ((len = in.read(buffer)) > 0) {
                // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                out.write(buffer, 0, len);
            }
            in.close();
        }
        
        return null;
    }

    /**
     * 预览单个附件的方法
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goAccessory")
    public ModelAndView goAccessory(HttpServletRequest request) {
        String fileid = request.getParameter("fileid");

        List<TSAttachment> flist = new ArrayList<TSAttachment>();
        if (StringUtil.isNotEmpty(fileid)) {
            TSAttachment attachFile = systemService.get(TSAttachment.class, fileid);
            if (attachFile != null) {
                flist.add(attachFile);
            }
        }
        request.setAttribute("index", 0);
        String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        request.setAttribute("rootPath", rootPath);
        request.setAttribute("flist", flist);

        return new ModelAndView("common/upload/accessoryTab");
    }

    /**
     * 预览附件（业务所有相关）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goAccessoryTab")
    public ModelAndView goAccessoryTab(HttpServletRequest request) {
        String bid = request.getParameter("bid");
        String index = request.getParameter("index");
        request.setAttribute("index", index);
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", bid);
        cq.add();
        cq.addOrder("createdate", SortDirection.asc);
        List<TSFilesEntity> flist = systemService.getListByCriteriaQuery(cq, false);
        if (flist.size() > 0) {
            String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
            request.setAttribute("rootPath", rootPath);
            request.setAttribute("flist", flist);
        }
        return new ModelAndView("common/upload/accessoryTab");
    }

    /**
     * 预览流程附件（业务所有相关）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goAccessoryTab2")
    public ModelAndView openViewFile2(HttpServletRequest request) {
        String bid = request.getParameter("bid");
        String index = request.getParameter("index");
        request.setAttribute("index", index);
        CriteriaQuery cq = new CriteriaQuery(TPBpmFile.class);
        cq.eq("bpmlog.id", bid);
        cq.add();
        cq.addOrder("createdate", SortDirection.asc);
        List<TPBpmFile> flist = systemService.getListByCriteriaQuery(cq, false);
        if (flist.size() > 0) {
            String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            request.setAttribute("rootPath", rootPath);
            request.setAttribute("flist", flist);
        }
        return new ModelAndView("common/upload/accessoryTab");
    }

    @RequestMapping(params = "noPreview")
    public ModelAndView noPreview(HttpServletRequest request) {
        request.setAttribute("isconvert", request.getParameter("isconvert"));
        return new ModelAndView("common/upload/noPreview");
    }

    /**
     * 附件预览读取
     * 
     * @return
     */
    @RequestMapping(params = "viewFile")
    public void viewFile(HttpServletRequest request, HttpServletResponse response) {
        String fileid = oConvertUtils.getString(request.getParameter("fileid"));
        String subclassname = oConvertUtils.getString(request.getParameter("subclassname"),
                "com.jeecg.base.pojo.TSAttachment");
        Class fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
        Object fileobj = systemService.getEntity(fileClass, fileid);
        if(fileobj != null){
        	ReflectHelper reflectHelper = new ReflectHelper(fileobj);
            UploadFile uploadFile = new UploadFile(request, response);
            String contentfield = oConvertUtils.getString(request.getParameter("contentfield"), uploadFile.getByteField());
            byte[] content = (byte[]) reflectHelper.getMethodValue(contentfield);
            String path = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
            String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
            String attachmenttitle = oConvertUtils.getString(reflectHelper.getMethodValue("attachmenttitle"));
            uploadFile.setExtend(extend);
            uploadFile.setTitleField(attachmenttitle);
            uploadFile.setRealPath(path);
            uploadFile.setContent(content);
            // uploadFile.setView(true);
            systemService.viewOrDownloadFile(uploadFile);
        }        
    }

    @RequestMapping(params = "importdata")
    public ModelAndView importdata() {
        return new ModelAndView("system/upload");
    }

    /**
     * 生成XML文件
     * 
     * @return
     */
    @RequestMapping(params = "createxml")
    public void createxml(HttpServletRequest request, HttpServletResponse response) {
        String field = request.getParameter("field");
        String entityname = request.getParameter("entityname");
        ImportFile importFile = new ImportFile(request, response);
        importFile.setField(field);
        importFile.setEntityName(entityname);
        importFile.setFileName(entityname + ".bak");
        importFile.setEntityClass(MyClassLoader.getClassByScn(entityname));
        systemService.createXml(importFile);
    }

    /**
     * 生成XML文件parserXml
     * 
     * @return
     */
    @RequestMapping(params = "parserXml")
    @ResponseBody
    public AjaxJson parserXml(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        String fileName = null;
        UploadFile uploadFile = new UploadFile(request);
        String ctxPath = request.getSession().getServletContext().getRealPath("");
        File file = new File(ctxPath);
        if (!file.exists()) {
            file.mkdir();// 创建文件根目录
        }
        MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();// 获取上传文件对象
            fileName = mf.getOriginalFilename();// 获取文件名
            String savePath = file.getPath() + "/" + fileName;
            File savefile = new File(savePath);
            try {
                FileCopyUtils.copy(mf.getBytes(), savefile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        systemService.parserXml(ctxPath + "/" + fileName);
        json.setSuccess(true);
        return json;
    }

    /**
     * 自动完成请求返回数据
     * 
     * @param request
     * @param responss
     */
    @RequestMapping(params = "getAutoList")
    public void getAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
        String jsonp = request.getParameter("jsonpcallback");
        String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
        autocomplete.setTrem(trem);
        List autoList = systemService.getAutoList(autocomplete);
        String labelFields = autocomplete.getLabelField();
        String[] fieldArr = labelFields.split(",");
        String valueField = autocomplete.getValueField();
        String[] allFieldArr = null;
        if (StringUtil.isNotEmpty(valueField)) {
            allFieldArr = new String[fieldArr.length + 1];
            for (int i = 0; i < fieldArr.length; i++) {
                allFieldArr[i] = fieldArr[i];
            }
            allFieldArr[fieldArr.length] = valueField;
        }

        try {
            String str = TagUtil.getAutoList(autocomplete, autoList);
            str = "(" + str + ")";
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(JSONHelper.listtojson(allFieldArr, allFieldArr.length, autoList));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    
    @RequestMapping(params = "getAutoUserList")
    @ResponseBody
    public JSONArray getAutoUserList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
        String trem = StringUtil.getEncodePra(request.getParameter("trem"));// 重新解析参数
        
        String searchFields = autocomplete.getSearchField();
        String[] searchFieldArr = searchFields.split(",");
        
        String sql = "select * from (select u.id userId, bu.realName, u.officephone officePhone, u.mobilephone mobilePhone, uo.org_id orgId, d.departname departName "
                   + "from t_s_user u join t_s_base_user bu on u.id = bu.id left join t_s_user_org uo on u.id = uo.user_id left join t_s_depart d on uo.org_id = d.id where 1!=1 ";
        
        if(StringUtils.isNotEmpty(trem)){
        	for (int i = 0; i < searchFieldArr.length; i++) {
        		sql += "or " + searchFieldArr[i] + " like '%" + trem + "%'";
            }        	
        }  
        
        sql += "and rownum < " + autocomplete.getMaxRows() + ")";
        
        List<Map<String, Object>> autoList = systemService.findForJdbc(sql);       
        
        JSONArray array = new JSONArray();        
        for (Map<String, Object> map : autoList) {
        	JSONObject json = new JSONObject();
            for(String key: map.keySet())
            {
            	json.put(key, map.get(key));                   
            }     
            array.add(json);
        }           
        return array;
    }

    /**
     * 删除继承于TSAttachment附件的公共方法
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "delObjFile")
    @ResponseBody
    public AjaxJson delObjFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
        TSAttachment attachment = systemService.getEntity(TSAttachment.class, fileKey);
        String subclassname = attachment.getSubclassname(); // 子类类名
        Object objfile = systemService.getEntity(MyClassLoader.getClassByScn(subclassname), attachment.getId());// 子类对象
        message = "" + attachment.getAttachmenttitle() + "删除成功";
        systemService.delete(objfile);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }

    /**
     * 继承于TSAttachment附件公共列表跳转
     * 
     * @return
     */
    @RequestMapping(params = "objfileList")
    public ModelAndView objfileList(HttpServletRequest request) {
        Object object = null;// 业务实体对象
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
        TSAttachment attachment = systemService.getEntity(TSAttachment.class, fileKey);
        String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));// 业务主键
        String busentityName = oConvertUtils.getString(request.getParameter("busentityName"));// 业务主键
        String typename = oConvertUtils.getString(request.getParameter("typename"));// 类型
        String typecode = oConvertUtils.getString(request.getParameter("typecode"));// 类型typecode
        if (StringUtil.isNotEmpty(busentityName) && StringUtil.isNotEmpty(businessKey)) {
            object = systemService.get(MyClassLoader.getClassByScn(busentityName), businessKey);
            request.setAttribute("object", object);
            request.setAttribute("businessKey", businessKey);
        }
        if (attachment != null) {
            request.setAttribute("subclassname", attachment.getSubclassname());
        }
        request.setAttribute("fileKey", fileKey);
        request.setAttribute("typecode", typecode);
        request.setAttribute("typename", typename);
        request.setAttribute("typecode", typecode);
        return new ModelAndView("common/objfile/objfileList");
    }

    /**
     * 继承于TSAttachment附件公共列表数据
     */
    @RequestMapping(params = "objfileGrid")
    public void objfileGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));
        String subclassname = oConvertUtils.getString(request.getParameter("subclassname"));// 子类类名
        String type = oConvertUtils.getString(request.getParameter("typename"));
        String code = oConvertUtils.getString(request.getParameter("typecode"));
        String filekey = oConvertUtils.getString(request.getParameter("filekey"));
        CriteriaQuery cq = new CriteriaQuery(MyClassLoader.getClassByScn(subclassname), dataGrid);
        cq.eq("businessKey", businessKey);
        if (StringUtil.isNotEmpty(type)) {
            cq.createAlias("TBInfotype", "TBInfotype");
            cq.eq("TBInfotype.typename", type);
        }
        if (StringUtil.isNotEmpty(filekey)) {
            cq.eq("id", filekey);
        }
        if (StringUtil.isNotEmpty(code)) {
            cq.createAlias("TBInfotype", "TBInfotype");
            cq.eq("TBInfotype.typecode", code);
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 保存附件
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "saveUploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveUploadFiles(HttpServletRequest request, HttpServletResponse response,
            TSFilesEntity tsFilesEntity) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
        String bid = oConvertUtils.getString(request.getParameter("bid"));// 资金ID
        tsFilesEntity.setBid(bid);
        tsFilesEntity.setCreatedate(new Timestamp(new Date().getTime()));
        if (StringUtil.isNotEmpty(fileKey)) {
            tsFilesEntity.setId(fileKey);
            tsFilesEntity = systemService.getEntity(TSFilesEntity.class, fileKey);
        }

        if (StringUtils.isNotEmpty(tsFilesEntity.getProjectId())) {
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tsFilesEntity.getProjectId());
            tsFilesEntity.setProjectName(project.getProjectName());
        }
        UploadFile uploadFile = new UploadFile(request, tsFilesEntity);
        uploadFile.setCusPath("files");
        uploadFile.setSwfpath("swfpath");
        uploadFile.setByteField(null);// 不存二进制内容
        tsFilesEntity = systemService.uploadFile(uploadFile);
        attributes.put("fileKey", tsFilesEntity.getId());
        attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + tsFilesEntity.getId());
        attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + tsFilesEntity.getId());
        attributes.put("fileName", tsFilesEntity.getAttachmenttitle());
        attributes.put("fileid", tsFilesEntity.getId());

        j.setMsg("文件添加成功");
        j.setAttributes(attributes);
        
        TSAttachment wj = this.systemService.get(TSAttachment.class, tsFilesEntity.getId());
        String dz=new File(request.getSession().getServletContext().getRealPath("/")).getParent() + "/" ;
        String dz1=request.getSession().getServletContext().getRealPath("/") ;

        saveConvertFile(wj,dz,dz1);
        
	   
	        // 保存索引
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("ID", tsFilesEntity.getId());
	        map.put("TITLE", tsFilesEntity.getAttachmenttitle());
	        map.put("PATH", tsFilesEntity.getRealpath());
	        map.put("EXTEND", tsFilesEntity.getExtend());
	        list.add(map);
	
	        String realPath = new File(request.getRealPath("/")).getParent();
	        SolrOperate.addFilesIndexNoContent(list, realPath);
	   
	    if( !"0".equals(request.getParameter("convertFile")) ) {
        }
        
        return j;
    }

    /**
     * 删除文档
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "delFile")
    @ResponseBody
    public AjaxJson delFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        TSFilesEntity file = systemService.getEntity(TSFilesEntity.class, id);
        if (file != null) {
            message = "" + file.getAttachmenttitle() + "被删除成功";
            this.systemService.deleteFile(file);
            j.setSuccess(true);
            j.setMsg(message);
            // 删除索引
            List<String> ids = new ArrayList<String>();
            ids.add(id);
            SolrOperate.deleteIndexByIds(ids);
        }
        return j;
    }
    
    /**
     * 保存附件到ftp
     * 
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "saveUploadFilesToFTP", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveUploadFilesToFTP(HttpServletRequest request, HttpServletResponse response,
            final TSFilesEntity tsFilesEntity) {
        AjaxJson j = new AjaxJson();
        try {
            this.systemService.saveFilesToFTP(request, tsFilesEntity);
            final String realPath = new File(request.getRealPath("/")).getParent();
            addIndex(tsFilesEntity,realPath);//添加索引
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("fileKey", tsFilesEntity.getId());
            attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + tsFilesEntity.getId());
            attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + tsFilesEntity.getId());
            attributes.put("fileName", tsFilesEntity.getAttachmenttitle());
            attributes.put("fileid", tsFilesEntity.getId());

            j.setMsg("文件添加成功");
            j.setAttributes(attributes);
            j.setMsg("文件上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("文件上传失败！");
        }
        return j;
    }
    
    //单独起线程，添加索引
    private void addIndex(final TSFilesEntity tsFilesEntity,final String realPath){
        
        ThreadPool.execute(new Runnable(){
            @Override
            public void run() {
             // 保存索引
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", tsFilesEntity.getId());
                map.put("TITLE", tsFilesEntity.getAttachmenttitle());
                map.put("PATH", tsFilesEntity.getRealpath());
                map.put("EXTEND", tsFilesEntity.getExtend());
                list.add(map);
                try{
                SolrOperate.addFilesIndexNoContent(list, realPath);
                tsFilesEntity.setAddIndexFlag("1");//成功
                systemService.updateEntitie(tsFilesEntity);
                }catch(Exception e){
                    String msg = e.getMessage();
                    if(msg!=null&&msg.length()>1000){
                        msg = msg.substring(0,1000)+"...";
                    }
                    tsFilesEntity.setAddIndexFlag("0");//失败
                    tsFilesEntity.setAddIndexMsg(msg);//错误信息
                    systemService.updateEntitie(tsFilesEntity);
                }
            }
        });
    }
    
    
    /**
     * 查看FTP附件
     * 
     * @return
     */
    @RequestMapping(params = "viewFTPFile")
    public void viewFTPFile(HttpServletRequest request, HttpServletResponse response) {
        String fileid = oConvertUtils.getString(request.getParameter("fileid"));
        String subclassname = oConvertUtils.getString(request.getParameter("subclassname"),
                "com.jeecg.base.pojo.TSAttachment");
        Class fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
        Object fileobj = systemService.getEntity(fileClass, fileid);
        ReflectHelper reflectHelper = new ReflectHelper(fileobj);
        UploadFile uploadFile = new UploadFile(request, response);
        String contentfield = oConvertUtils.getString(request.getParameter("contentfield"), uploadFile.getByteField());
        byte[] content = (byte[]) reflectHelper.getMethodValue(contentfield);
        String path = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
        String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
        String attachmenttitle = oConvertUtils.getString(reflectHelper.getMethodValue("attachmenttitle"));
        String size = oConvertUtils.getString(reflectHelper.getMethodValue("fileSize"));
        uploadFile.setExtend(extend);
        uploadFile.setTitleField(attachmenttitle);
        uploadFile.setRealPath(path);
        uploadFile.setContent(content);
        if (StringUtils.isNotEmpty(size)) {
            uploadFile.setSize(Long.valueOf(size));
        } else {
            uploadFile.setSize(0);
        }
        this.systemService.viewFTPFile(uploadFile);
    }
    
    /**
     * 跳转到通用的上传页面
     * @param request
     * @return
     */
    @RequestMapping(params = "goFileUpload")
    public ModelAndView goFileUpload(HttpServletRequest request) {
        String opt = request.getParameter("opt");//op值为view即查看
        String attachmentCode = request.getParameter("attachmentCode");
        String businessType = request.getParameter("businessType");
        String projectId = request.getParameter("projectId");
        request.setAttribute("opt", opt);
        if(StringUtils.isNotEmpty(attachmentCode)){
            request.setAttribute("attachmentCode", attachmentCode);
            List<TSFilesEntity> attachments = this.systemService.getAttachmentByCode(attachmentCode, "");
            request.setAttribute("attachments", attachments);
        }
        request.setAttribute("businessType", businessType);
        if(StringUtils.isNotEmpty(projectId)){
           request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/common/upload/fileUpload");
    }
    
    /**
     * 删除FTP文档
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "delFTPFile")
    @ResponseBody
    public AjaxJson delFTPFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        try {
            TSFilesEntity file = systemService.getEntity(TSFilesEntity.class, id);
            if (file != null) {
                message = "" + file.getAttachmenttitle() + "被删除成功";
                this.systemService.deleteFTPFile(file);
                removeIndex(id);//删除索引
                j.setMsg(message);
            }
        } catch (Exception e) {
            j.setSuccess(true);
            j.setMsg("文件删除失败！");
            e.printStackTrace();
        }
        return j;
    }

    //单独起线程，删除索引
    private void removeIndex(final String id) {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> ids = new ArrayList<String>();
                    ids.add(id);
                    SolrOperate.deleteIndexByIds(ids);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    //文件转换
	public void saveConvertFile(TSAttachment wj, String dz,String dz1) {
		// 附件文件件夹
		String attachFolder = dz;
		String sourceFileName = wj.getRealpath();
		String targetFolder = "";
		String targetFileName = "";
		// 2014-01-17修改附件上传功能，统一转换成html
		if (wj.getExtend().equals("jpg11") || wj.getExtend().equals("gif11")) {

			// 如果是图片不用转换，直接用上传地址替换转换后地址
			wj.setCkdz(wj.getRealpath());
			systemService.updateEntitie(wj);

		} else {
			// substr在文件夹上加"Z"

			targetFolder = wj.getRealpath().substring(0, wj.getRealpath().lastIndexOf("/") + 1)
					+ java.text.MessageFormat.format("{0,date,yyyyMM}", wj.getCreatedate()) + "Z";

			targetFileName = wj.getRealpath().substring(wj.getRealpath().lastIndexOf("/") + 1, wj.getRealpath().lastIndexOf("."))
					+ ".html";

			String filepath = dz1 + "/" + targetFolder;
			// 判断文件存储目录是否存在，如不存在，则创建
			File fileFolder = new File(filepath);
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			// System.out.println(attachFolder + "/" +sourceFileName);
			// System.out.println(attachFolder + "/" + targetFolder + "/"
			// +targetFileName);

			int flag = 7;
			// 如果不是图片文件才今天文件转换
			if (wj.getExtend().equals("pdf") || wj.getExtend().toUpperCase().equals("PDF")) {
				String ywj = attachFolder+sourceFileName;
				String wjm = wj.getRealpath().substring(wj.getRealpath().lastIndexOf("/") + 1, wj.getRealpath().lastIndexOf("."))
						+ ".pdf";
				String xwj = dz1+targetFolder+"/"+wjm;
				fileCopy(ywj,xwj); 
				wj.setCkdz(targetFolder+"/"+wjm);
				wj.setIsconvert("1");
				systemService.updateEntitie(wj);
				flag = 7;
			}
			DoConvert convert = new DoConvert();
			flag = convert.doconvert(wj.getExtend(), attachFolder, sourceFileName, filepath, targetFileName);
			// System.out.println("flag== "+flag + " ====0
			// 转换成功1：传入的文件，找不到2：传入的文件，打开失败3：转换过程异常失败4：传入的文件有密码5：targetFileName的后缀名错误");

			if (flag == 0) {
				wj.setCkdz(targetFolder + "/" + targetFileName);
				wj.setIsconvert("1");
				systemService.updateEntitie(wj);
			}

		}
	}

	/**
	 * 文件拷贝的方法
	 */
	private static void fileCopy(String src, String des) {
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(des);
			int length = 0;
			byte[] buffer = new byte[1024]; // 一字节缓冲
			while((length=fis.read(buffer)) != -1){
			    fos.write(buffer, 0, length);
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
