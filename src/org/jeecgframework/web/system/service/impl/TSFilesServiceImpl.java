package org.jeecgframework.web.system.service.impl;

import java.io.File;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.ConvertorPool;
import org.jeecgframework.web.system.pojo.base.ConvertorPool.ConvertorObject;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.TSFilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codecLib.mpa.Constants;

/**
 * 
 * @author 张代浩
 *
 */
@Service("tSFilesService")
@Transactional
public class TSFilesServiceImpl extends CommonServiceImpl implements TSFilesService {
    private static boolean isRunning = false;

    public static final String PATH_CLASS_ROOT = Constants.class.getResource("/").getPath();
    public static final String FILE_PATH = PATH_CLASS_ROOT.substring(1, PATH_CLASS_ROOT.length() - 22);

    /**
     * 获取未转换文件list
     * 
     * @return
     * @throws Exception
     */
    public List<TSAttachment> searchConvertFile() throws Exception {
        // 查询所有转换后url为空的附件记录
        CriteriaQuery cq = new CriteriaQuery(TSAttachment.class);
        cq.isNull("isconvert");
        cq.add();
        List<TSAttachment> files = this.getListByCriteriaQuery(cq, false);
        return files;
    }

    /**
     * 保存转换后html文件路径
     * 
     * @param pubAttach
     * @throws Exception
     */
    public void saveConvertFile(TSAttachment file) throws Exception {
        String realPath = file.getRealpath();
        String fileType = file.getExtend();

        String folderPath = realPath.substring(0, realPath.lastIndexOf("/") + 1);
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1, realPath.lastIndexOf("."));

        String convertPath = folderPath + fileName + ".html";//转换访问地址

        // 附件文件件夹
        if (fileType.equals("jpg11") || fileType.equals("gif11")) {
            file.setSwfpath(realPath);
            file.setIsconvert("0");//转换成功
            this.updateEntitie(file);
        } else if (fileType.equals("html") || fileType.toUpperCase().equals("HTML")) {
            file.setSwfpath(realPath);
            file.setIsconvert("0");//转换成功
            this.updateEntitie(file);
        } else {
            int flag = this.convertFunction(realPath, fileType, convertPath);
            if (flag == 0) {
                file.setSwfpath(convertPath);
            }
            file.setIsconvert(String.valueOf(flag));
            this.updateEntitie(file);
        }
    }

    public int convertFunction(String realPath, String fileType, String convertPath) {
        int flag = 7;
        ConvertorObject convertobj = ConvertorPool.getInstance().getConvertor();

        String convertArg0 = FILE_PATH + realPath.replace('\\', '/');
        String convertArg1 = FILE_PATH + convertPath.replace('\\', '/');
        // 根据文件类型调用不同方法
        if (fileType.equals("pdf") || fileType.toUpperCase().equals("PDF")) {
            flag = convertobj.convertor.convertPdfToHtml(convertArg0, convertArg1);
        }

        if (fileType.equals("doc") || fileType.toUpperCase().equals("DOC") || fileType.equals("docx")
                || fileType.toUpperCase().equals("DOCX") || fileType.equals("xls")
                || fileType.toUpperCase().equals("XLS") || fileType.equals("xlsx")
                || fileType.toUpperCase().equals("XLSX") || fileType.equals("dot")
                || fileType.toUpperCase().equals("DOT")) {
            flag = convertobj.convertor.convertMStoHTML(convertArg0, convertArg1);
        }

        if (fileType.equals("jpg") || fileType.toUpperCase().equals("JPG") || fileType.equals("gif")
                || fileType.toUpperCase().equals("GIF")) {
            flag = convertobj.convertor.convertPicToHtml(convertArg0, convertArg1);
        }

        if (fileType.equals("txt") || fileType.toUpperCase().equals("TXT")) {
            String res = convertobj.convertor.convertToHTML(convertArg0, convertArg1);
            if (StringUtil.isNotEmpty(res)) {
                flag = 0;
            } else {
                flag = 3;
            }
        }

        System.out.println("0 转换成功1：传入的文件，找不到2：传入的文件，打开失败3：转换过程异常失败4：传入的文件有密码5：targetFileName的后缀名错误");
        System.out.println("flag==" + flag);

        convertobj.convertor.deleteTempFiles();
        ConvertorPool.getInstance().returnConvertor(convertobj);// 资源释放

        return flag;
    }

    /**
     * 定时执行转换任务（包括流程附件）
     */
    @Override
    public void task() {
        System.out.println("########" + PATH_CLASS_ROOT);
        System.out.println("##任务开始，判断之前是否还在运行=" + DateUtils.formatDate2());
        try {
            if (!isRunning) {
                isRunning = true;
                System.out.println("##开始执行指定任务=" + DateUtils.formatDate2());
                System.out.println("##开始执行指定任务=" + this);
                System.out.println("##path=" + System.getProperty("user.home"));
                System.out.println("##开始执行查询未转换文件任务=" + DateUtils.formatDate2());
                List<TSAttachment> attList = this.searchConvertFile();
                if (attList != null && attList.size() > 0) {
                    System.out.println("##开始执行文件转换任务=" + DateUtils.formatDate2());
                    for (int i = 0; i < attList.size(); i++) {
                        TSAttachment pubAttach = attList.get(i);
                        this.saveConvertFile(pubAttach);
                    }
                }
                isRunning = false;
            } else {
                System.out.println("##上一次任务执行还未结束=" + DateUtils.formatDate2());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TSFilesEntity> getFileListByBid(String bid) {
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", bid);
        cq.add();
        List<TSFilesEntity> files = this.getListByCriteriaQuery(cq, false);
        return files;
    }

    @Override
    public Boolean ifFileExists(String relativePath) {
        File file = new File(FILE_PATH + relativePath);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

}
