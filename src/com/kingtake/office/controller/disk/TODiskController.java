package com.kingtake.office.controller.disk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.FtpClientUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeModel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.entity.disk.TODiskEntity;
import com.kingtake.office.entity.disk.TOGroupEntity;
import com.kingtake.office.entity.disk.TOGroupMemberEntity;
import com.kingtake.office.service.disk.TODiskServiceI;
import com.kingtake.project.controller.contractnode.TPmContractNodeController;
import com.kingtake.project.entity.dbimport.TPmDBImportEntity;

/**
 * ????????????
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tODiskController")
public class TODiskController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractNodeController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private TODiskServiceI tODiskService;

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ???????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "disk")
    public ModelAndView disk(HttpServletRequest request) {
        String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
        int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
        String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
        String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
        String prefix = "ftp://" + username + ":" + password + "@" + ip + ":" + port + "/";
        request.setAttribute("prefix", prefix);
        return new ModelAndView("com/kingtake/office/disk/outdisk");
    }

    /**
     * ???????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goDisk")
    public ModelAndView goDisk(HttpServletRequest request, HttpServletResponse response) {
        String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
        int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
        String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
        String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
        String prefix = "ftp://" + username + ":" + password + "@" + ip + ":" + port + "/";
        request.setAttribute("prefix", prefix);
        return new ModelAndView("com/kingtake/office/disk/disk");
    }

    /**
     * ?????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goPersonalDisk")
    public ModelAndView goPersonalDisk(HttpServletRequest request) {
        String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
        int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
        String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
        String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
        String prefix = "ftp://" + username + ":" + password + "@" + ip + ":" + port + "/";
        request.setAttribute("prefix", prefix);
        return new ModelAndView("com/kingtake/office/disk/personaldisk");
    }
    
    

    
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goRecycle")
    public ModelAndView goRecycle(HttpServletRequest request) {
    	
    	String uploadType = request.getParameter("uploadType");
    	
    	String groupId = request.getParameter("groupId");
    	
    	request.setAttribute("uploadType", uploadType);
    	
    	request.setAttribute("groupId", groupId);
    	
        return new ModelAndView("com/kingtake/office/disk/diskRecycle");
    }
    
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doHide")
    @ResponseBody
    public AjaxJson doHide(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "??????????????????";
        try {
            this.tODiskService.doLogicDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "??????????????????!";
        try {
            this.tODiskService.doBack(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????!";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doThoroughDelete")
    @ResponseBody
    public AjaxJson doThoroughDelete(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            tODiskService.doThoroughDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    

    @RequestMapping(params = "saveDiskFiles", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveDiskFiles(HttpServletRequest request, HttpServletResponse response, TSFilesEntity tsFilesEntity) {
        AjaxJson j = new AjaxJson();
        try {
            this.tODiskService.saveDiskToFTP(request, tsFilesEntity);
            j.setMsg("?????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForPublic")
    public void datagridForPublic(TODiskEntity disk, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String uploadType = request.getParameter("uploadType");
        String attachmenttitle = request.getParameter("attachmenttitle");
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String sql = "select * from (select a.id,a.attachmenttitle,a.createdate,a.extend,a.realpath "
                + "from t_o_disk d join t_s_files f on d.attachement_code=f.bid join t_s_attachment a  "
                + "on a.id=f.id where  d.upload_type=? and (f.del_flag='' or f.del_flag='0' or f.del_flag is null)) ";
        if (StringUtils.isNotEmpty(attachmenttitle)) {
            sql = sql + " where attachmenttitle like '%" + attachmenttitle + "%'";
        }
        sql = sql + " order by createdate desc";
        String countSql = "select count(1) from (" + sql + ")";
        Object[] param = new Object[] { uploadType };
        List<Map<String, Object>> dataList = this.systemService.findForJdbcParam(sql, page, rows, param);
        Long count = this.systemService.getCountForJdbcParam(countSql, param);
        dataGrid.setTotal(count.intValue());
        dataGrid.setResults(dataList);
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
    @RequestMapping(params = "datagridForPersonal")
    public void datagridForPersonal(TODiskEntity disk, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uploadType = request.getParameter("uploadType");
        String attachmenttitle = request.getParameter("attachmenttitle");
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        String sql = "select * from (select a.id,a.attachmenttitle,a.createdate,a.extend,a.realpath "
                + "from t_o_disk d join t_s_files f on d.attachement_code=f.bid join t_s_attachment a  "
                + "on a.id=f.id where d.user_id=? and d.upload_type=? and (f.del_flag='' or f.del_flag='0' or f.del_flag is null) ) ";
        if (StringUtils.isNotEmpty(attachmenttitle)) {
            sql = sql + " where attachmenttitle like '%" + attachmenttitle + "%'";
        }
        sql = sql + " order by createdate desc";
        String countSql = "select count(1) from (" + sql + ")";
        Object[] param = new Object[] { user.getId(), uploadType };
        List<Map<String, Object>> dataList = this.systemService.findForJdbcParam(sql, page, rows, param);
        Long count = this.systemService.getCountForJdbcParam(countSql, param);
        dataGrid.setTotal(count.intValue());
        dataGrid.setResults(dataList);
        TagUtil.datagrid(response, dataGrid);
    }
    
    

    
    /**
     * easyui AJAX??????-????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForRecycle")
    public void datagridForRecycle(TODiskEntity disk, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();                
        String uploadType = request.getParameter("uploadType");
        
        if (StringUtils.equals(uploadType, "group")) {
        	String groupId = request.getParameter("groupId");
            String attachmenttitle = request.getParameter("attachmenttitle");
            CriteriaQuery diskCq = new CriteriaQuery(TODiskEntity.class);
            diskCq.eq("groupId", groupId);
            diskCq.eq("uploadType", "group");
            diskCq.addOrder("uploadTime", SortDirection.desc);
            diskCq.add();
            List<TODiskEntity> diskList = this.systemService.getListByCriteriaQuery(diskCq, false);
            if (diskList.size() == 0) {
                dataGrid.setTotal(0);
                dataGrid.setResults(new ArrayList());
                TagUtil.datagrid(response, dataGrid);
                return;
            }
            List<String> attachmentCodeList = new ArrayList<String>();
            for (TODiskEntity tmp : diskList) {
                attachmentCodeList.add(tmp.getAttachmentCode());
            }
            CriteriaQuery fileCq = new CriteriaQuery(TSFilesEntity.class,dataGrid);
            fileCq.in("bid", attachmentCodeList.toArray());
            fileCq.eq("businessType", "groupDisk");                
            fileCq.eq("delFlag","1");
                                          
            if (StringUtils.isNotEmpty(attachmenttitle)) {
                fileCq.like("attachmenttitle", "%" + attachmenttitle + "%");
            }
            fileCq.addOrder("delTime", SortDirection.desc);
            fileCq.add();
//            List<TSFilesEntity> fileList = this.systemService.getListByCriteriaQuery(fileCq, true);
//            int count = this.systemService.getCountByCriteriaQuery(fileCq);
//            dataGrid.setTotal(count);
//            dataGrid.setResults(fileList);
            this.systemService.getDataGridReturn(fileCq, true);
            TagUtil.datagrid(response, dataGrid);
        }
        else
        {
            String attachmenttitle = request.getParameter("attachmenttitle");
            int page = Integer.parseInt(request.getParameter("page"));
            int rows = Integer.parseInt(request.getParameter("rows"));
            String sql = "select * from (select a.id,a.attachmenttitle,a.createdate,a.extend,a.realpath,f.del_username as delUserName,f.del_Time as delTime "
                    + "from t_o_disk d join t_s_files f on d.attachement_code=f.bid join t_s_attachment a  "
                    + "on a.id=f.id where d.user_id=? and d.upload_type=? and f.del_flag='1' ) ";
            if (StringUtils.isNotEmpty(attachmenttitle)) {
                sql = sql + " where attachmenttitle like '%" + attachmenttitle + "%'";
            }
            sql = sql + " order by createdate desc";
            String countSql = "select count(1) from (" + sql + ")";
            Object[] param = new Object[] { user.getId(), uploadType };
            List<Map<String, Object>> dataList = this.systemService.findForJdbcParam(sql, page, rows, param);
            Long count = this.systemService.getCountForJdbcParam(countSql, param);
            dataGrid.setTotal(count.intValue());
            dataGrid.setResults(dataList);
            TagUtil.datagrid(response, dataGrid);        
        }
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForGroup")
    public void datagridForGroup(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String groupId = request.getParameter("groupId");
        String attachmenttitle = request.getParameter("attachmenttitle");
        CriteriaQuery diskCq = new CriteriaQuery(TODiskEntity.class);
        diskCq.eq("groupId", groupId);
        diskCq.eq("uploadType", "group");
        diskCq.addOrder("uploadTime", SortDirection.desc);
        diskCq.add();
        List<TODiskEntity> diskList = this.systemService.getListByCriteriaQuery(diskCq, false);
        if (diskList.size() == 0) {
            dataGrid.setTotal(0);
            dataGrid.setResults(new ArrayList());
            TagUtil.datagrid(response, dataGrid);
            return;
        }
        List<String> attachmentCodeList = new ArrayList<String>();
        for (TODiskEntity tmp : diskList) {
            attachmentCodeList.add(tmp.getAttachmentCode());
        }
        CriteriaQuery fileCq = new CriteriaQuery(TSFilesEntity.class, dataGrid);
        fileCq.in("bid", attachmentCodeList.toArray());
        fileCq.eq("businessType", "groupDisk");                
//        fileCq.eq("delFlag","0");
        
        fileCq.or(Restrictions.eq("delFlag","0"), Restrictions.isNull("delFlag"));
                                      
        if (StringUtils.isNotEmpty(attachmenttitle)) {
            fileCq.like("attachmenttitle", "%" + attachmenttitle + "%");
        }
        fileCq.addOrder("createdate", SortDirection.desc);
        fileCq.add();
        this.systemService.getDataGridReturn(fileCq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TODiskEntity disk, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        disk = systemService.getEntity(TODiskEntity.class, disk.getId());
        message = "??????????????????????????????";
        try {
            tODiskService.delete(disk);
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
            dbImport = this.tODiskService.get(TPmDBImportEntity.class, dbImport.getId());
            req.setAttribute("dbImport", dbImport);
        }
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNode-update");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goGroupDisk")
    public ModelAndView goGroupDisk(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/disk/groupdisk");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "groupTree")
    @ResponseBody
    public List<TreeModel> groupTree(HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TOGroupMemberEntity.class);
        cq.eq("userId", user.getId());
        cq.add();
        List<TOGroupMemberEntity> memberList = this.systemService.getListByCriteriaQuery(cq, false);
        Set<String> groupSet = new HashSet<String>();
        for (TOGroupMemberEntity member : memberList) {
            groupSet.add(member.getGroupId());
        }
        List<TOGroupEntity> groupList = new ArrayList<TOGroupEntity>();
        if (groupSet.size() > 0) {
            CriteriaQuery groupCq = new CriteriaQuery(TOGroupEntity.class);
            groupCq.in("id", groupSet.toArray());
            groupCq.add();
            groupList = this.systemService.getListByCriteriaQuery(groupCq, false);
        }
        TreeModel root = new TreeModel();
        root.setIconCls("default");
        root.setText("??????????????????");
        root.setId("root");
        List<TreeModel> childNode = new ArrayList<TreeModel>();
        for (TOGroupEntity group : groupList) {
            TreeModel node = new TreeModel();
            node.setId(group.getId());
            node.setText(group.getName());
            node.setIconCls("default");
            childNode.add(node);
        }
        root.setChildren(childNode);
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        treeList.add(root);
        return treeList;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goGroupDiskAdd")
    public ModelAndView goGroupDiskAdd(TOGroupEntity group, HttpServletRequest req) {
        if (StringUtils.isNotEmpty(group.getId())) {
            group = this.systemService.get(TOGroupEntity.class, group.getId());
        }
        req.setAttribute("tOGroupPage", group);
        return new ModelAndView("com/kingtake/office/disk/group-add");
    }

    @RequestMapping(params = "doAddGroup", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doAddGroup(TOGroupEntity group, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            this.tODiskService.createGroup(group);
            j.setMsg("?????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }

    @RequestMapping(params = "goGroupDetail")
    public ModelAndView goGroupDetail(TOGroupEntity group, HttpServletRequest request, HttpServletResponse response) {
        group = this.systemService.get(TOGroupEntity.class, group.getId());
        request.setAttribute("groupId", group.getId());
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TOGroupMemberEntity.class);
        cq.eq("groupId", group.getId());
        cq.eq("userId", user.getId());
        cq.eq("isMgr", "1");
        cq.add();
        List<TOGroupMemberEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            request.setAttribute("isMgr", "1");
        } else {
            request.setAttribute("isMgr", "0");
        }
        String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
        int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
        String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
        String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
        String prefix = "ftp://" + username + ":" + password + "@" + ip + ":" + port + "/";
        request.setAttribute("prefix", prefix);
        return new ModelAndView("com/kingtake/office/disk/groupdisk-detail");
    }

    /**
     * ???????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridMember")
    public void datagridMember(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String groupId = request.getParameter("groupId");
        CriteriaQuery memberCq = new CriteriaQuery(TOGroupMemberEntity.class, dataGrid);
        memberCq.eq("groupId", groupId);
        memberCq.addOrder("sort", SortDirection.asc);
        memberCq.addOrder("joinTime", SortDirection.desc);
        memberCq.add();
        this.systemService.getDataGridReturn(memberCq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSelectMember")
    public ModelAndView goSelectMember(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/disk/selectMember");
    }

    /**
     * ???????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddMember")
    @ResponseBody
    public AjaxJson doAddMember(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String groupId = request.getParameter("groupId");
        String userId = request.getParameter("userId");
        try {
            this.tODiskService.saveGroupMember(groupId, userId);
            j.setMsg("????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("????????????????????????");
        }
        return j;
    }

    /**
     * ???????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doRemoveMember")
    @ResponseBody
    public AjaxJson doRemoveMember(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String groupId = request.getParameter("groupId");
        String userId = request.getParameter("userId");
        try {
            this.tODiskService.removeGroupMember(groupId, userId);
            j.setMsg("????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("????????????????????????");
        }
        return j;
    }

    /**
     * ??????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "breakGroup")
    @ResponseBody
    public AjaxJson breakGroup(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String groupId = request.getParameter("groupId");
        try {
            this.tODiskService.breakGroup(groupId);
            j.setMsg("?????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goGroupDiskMain")
    public ModelAndView goGroupDiskMain(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/disk/empty-group");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "viewDiskFile")
    public void viewDiskFile(HttpServletRequest request, HttpServletResponse response) {
        String fileid = oConvertUtils.getString(request.getParameter("fileid"));
        String subclassname = oConvertUtils.getString(request.getParameter("subclassname"),
                "com.jeecg.base.pojo.TSAttachment");
        Class fileClass = MyClassLoader.getClassByScn(subclassname);// ??????????????????
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
        this.tODiskService.viewDiskFile(uploadFile);
    }
}
