package com.kingtake.project.controller.ys;

import com.kingtake.project.controller.incomeapply.TPmIncomeApplyController;
import com.kingtake.project.service.ys.BudgetExportService;
import com.kingtake.project.service.ys.YsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 预算打印（导出word）
 * @author liyangzhao
 * @date 2016-01-21 20:20:22
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/budgetExportController")
public class BudgetExportController extends BaseController {

    @Autowired
    private BudgetExportService budgetExportService;

    @Autowired
    private YsService ysService;

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BudgetExportController.class);


    @RequestMapping(params = "budgetExport")
    public ModelAndView budgetExport(HttpServletRequest request) {
        return new ModelAndView("budget/templates/budgetTemplate/budgetExport");
    }

    //预算导出

    /**
     * 科研项目项目总预算审批表 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWordXmzysspb")
    @ResponseBody
    public AjaxJson createWordXmzysspb(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map<String,Object> dataMap = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        AjaxJson j = new AjaxJson();
        //获取模板
        Map map = new HashMap();
        map.put("exportFtl","xmzysspb");
        Template template = getTemplate(map);

        /** 准备数据 **/
        /*Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projectName", "xxx项目");
        dataMap.put("people", "liyangzhao");
        dataMap.put("noeDate","2018");

        List list = new ArrayList();
        for(int i=0; i<10; i++){
            map.put("year","2018");
            list.add(map);
        }
        dataMap.put("dataList",list);

        String barcode = TPmIncomeApplyController.getBarCodePic("DA20180000495");
        dataMap.put("barcode",barcode);*/


        Map map1 = new HashMap();
        map1.put("T_P_ID",dataMap.get("ID"));
        map1.put("YS_TYPE","ALL");
        map1.put("FUNDS_TYPE",-1);
        map1.put("layer",1);
        map1.put("FUNDS_CATEGORY",1);
        map1.put("PID","19-10");//间接费
        Map zysMap = ysService.getTreeTemplate(map1);
        getZysje(dataMap,zysMap);
        System.out.println(dataMap);

        //输出word
        exportWord(map,dataMap,j,template);

        return j;
    }
    private  static Map<String,Object> detailMap = new HashMap<String,Object>();
    private static final String zjjf = "设备费,材料费,外部协作费,燃料动力费,会议/差旅/国际合作与交流费,出版/文献/信息传播/知识产权事务费,劳务费,专家咨询费,其他支出";
    private static final String jjjf = "学校管理费,责任单位管理费,承研单位管理费,项目组绩效津贴";
    static {
        detailMap.put("合计","hj");
        detailMap.put("设备费","sbf");
        detailMap.put("材料费","clf");
        detailMap.put("外部协作费","wbxzf");
        detailMap.put("燃料动力费","rydlf");
        detailMap.put("会议/差旅/国际合作与交流费","hyclf");
        detailMap.put("出版/文献/信息传播/知识产权事务费","cbwxf");
        detailMap.put("劳务费","lwf");
        detailMap.put("专家咨询费","zjzxf");
        detailMap.put("其他支出","qtzc");
        detailMap.put("间接费","jjf");
        detailMap.put("指定分承包费","zdfcbf");
        detailMap.put("不可预见费","bkyjf");
        detailMap.put("校内协作费","xnxzf");
        detailMap.put("学校管理费","xxglf");
        detailMap.put("责任单位管理费","zrdwglf");
        detailMap.put("承研单位管理费","cydwglf");
        detailMap.put("项目组绩效津贴","xmzjxjt");

    }
    private void getZysje(Map<String,Object> dataMap, Map zysMap) {
        List<Map> list = (List)zysMap.get("data");
        int size = list.size();
        if( size> 0){
            for(int i=0;i<size;i++){
                Map map = list.get(i);
                String detailName = MapUtils.getString(map,"DETAIL_NAME");
                Object money =  MapUtils.getString(map,"MONEY");

                //暂时根据名字去匹配赋值
                int zjjfxj = 0;//直接经费小计
                int jjjfxj = 0;//间接经费小计
                for(String str : detailMap.keySet()){
                    if(str.equals(detailName)){
                        String key = MapUtils.getString(detailMap,str);
                        if("合计".equals(str)){
                            dataMap.put(key,map.get("XE"));
                        }else{
                            dataMap.put(key,money);
                            if(zjjf.indexOf(key) != -1 && money != null){
                                zjjfxj+=Integer.parseInt(money.toString());
                            }else if(jjjf.indexOf(key) != -1  && money != null){
                                jjjfxj+=Integer.parseInt(money.toString());
                            }
                        }
                        break;
                    }
                }

                dataMap.put("zjjfxj",zjjfxj);
                dataMap.put("jjjfxj",jjjfxj);
            }
        }

    }

    /**
     * 科研项目项目年度预算审批表 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWordXmndysmxb")
    @ResponseBody
    public AjaxJson createWordXmndysmxb(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map<String,Object> dataMap = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        AjaxJson j = new AjaxJson();
        //获取模板
        Map map = new HashMap();
        map.put("exportFtl","xmndysmxb");
        Template template = getTemplate(map);

        /** 准备数据 **/
        /*Map<String,Object> dataMap = new HashMap<>();
        List list = new ArrayList();
        Map tempMap2;
        for(int i=0; i<60; i++){
            int s = i%4;
            String temp = "";
            for(int jj=0; jj<=s; jj++){
                temp+="  ";
            }
            tempMap2 = new HashMap();
            tempMap2.put("shebeifei",temp+"设备费");
            tempMap2.put("xuhao",i);
            list.add(tempMap2);
        }
        dataMap.put("dataList",list);*/


        Map map1 = new HashMap();
        map1.put("T_P_ID",dataMap.get("ID"));
        map1.put("YS_TYPE","ALL");
        map1.put("FUNDS_TYPE",-1);
        map1.put("FUNDS_CATEGORY",2);
        Map zysMap = ysService.getTreeTemplate(map1);

        List<Map> data = (List)zysMap.get("data");
        if(data != null && data.size() > 0){
            for(int i=0;i < data.size();i++){
                Map d = data.get(i);
                Object code = d.get("CATEGORY_CODE_DTL");
                Object name = d.get("DETAIL_NAME");
                //合计字段，金额为XE，而不是MONEY
                if("0".equals(code)){
                    d.put("HJ",d.get("XE"));
                }
                //名字前面添加空格，组装树结构
                String temp = "";
                for(int k=0; k<=code.toString().length(); k++){
                    temp+="  ";
                }
                d.put("DETAIL_NAME",temp+name);
            }
        }
        dataMap.put("dataList",data);
        System.out.println(data);

        //输出word
        exportWord(map,dataMap,j,template);
        return j;
    }


    /**
     * 到款分配通知书 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWord")
    @ResponseBody
    public AjaxJson createWord(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map param = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        Map map = new HashMap();
        map.put("exportFtl","dkfptzs2");
        AjaxJson j = new AjaxJson();
        //获取模板
        Template template = getTemplate(map);

        /** 准备数据 **/
        /*Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projectName", "xxx项目");
        dataMap.put("people", "liyangzhao");

        List list = new ArrayList();
        for(int i=0; i<10; i++){
            map.put("year","2018");
            list.add(map);
        }
        dataMap.put("dataList",list);

        String barcode = TPmIncomeApplyController.getBarCodePic("DA20180000495");
        dataMap.put("barcode",barcode);*/

        //入参：projectId ，dkmxId
        //map.put("projectId","402881ed5b378624015b3789863f0000");
        //map.put("dkmxId","402881e65c66ca8b015c67b3666f0004");
        Map dataMap = budgetExportService.exportDkfpList(param);
        if(dataMap.get("barcode") != null && dataMap.get("barcode") != ""){
            dataMap.put("barcode",TPmIncomeApplyController.getBarCodePic(dataMap.get("barcode").toString()));
        }

        //输出word
        exportWord(map,dataMap,j,template);

        return j;
    }

    /**
     * 计划经费分配通知书 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWordJhjfptzs")
    @ResponseBody
    public AjaxJson createWordJhjfptzs(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map param = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        Map map = new HashMap();
        map.put("exportFtl","jhjffptzs");
        AjaxJson j = new AjaxJson();
        //获取模板
        Template template = getTemplate(map);

        /** 准备数据 **/
        /*Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projectName", "xxx项目");
        dataMap.put("people", "liyangzhao");

        List list = new ArrayList();
        for(int i=0; i<10; i++){
            map.put("year","2018");
            map.put("yuan","45892");
            map.put("nameq","信息网络工程教研室");
            map.put("fuzeren","文无敌");
            list.add(map);
        }
        dataMap.put("dataList",list);

        String barcode = TPmIncomeApplyController.getBarCodePic("JA201800495");
        dataMap.put("barcode",barcode);*/

        //入参：projectId ，jhffId
        //map.put("projectId","40288b815f3c7ed6015f3cd299040057");
        //map.put("jhffId","40288b815f3c7ed6015f3cd364750059");
        Map<String,Object> dataMap = budgetExportService.exportJhffList(param);
        if(dataMap.get("dybh") != null && dataMap.get("dybh") != "" && dataMap.get("dybh").toString().split(",").length == 2){
            String dybh = dataMap.get("dybh").toString();
            String year = dybh.split(",")[0];
            String bh = dybh.split(",")[1];
            String barcode = String.format("%05d", Integer.parseInt(bh));

            barcode = "JA"+year+barcode;
            dataMap.put("barcode",TPmIncomeApplyController.getBarCodePic(barcode));
            dataMap.put("bh",bh);
        }

        //输出word
        exportWord(map,dataMap,j,template);

        return j;
    }

    /**
     * 经费垫支申请书 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWordJfdzsqs")
    @ResponseBody
    public AjaxJson createWordJfdzsqs(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map param = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        Map map = new HashMap();
        map.put("exportFtl","jfdzsqs");
        AjaxJson j = new AjaxJson();
        //获取模板
        Template template = getTemplate(map);

        /** 准备数据 **/
        /*Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projectName", "xxx项目");
        dataMap.put("datatime", "2018-02-18");

        List list = new ArrayList();
        for(int i=0; i<10; i++){
            map.put("year","2018");
            map.put("yuan","45892");
            map.put("nameq","信息网络工程教研室");
            map.put("fuzeren","文无敌");
            list.add(map);
        }
        dataMap.put("dataList",list);

        String barcode = TPmIncomeApplyController.getBarCodePic("DA201800495");
        dataMap.put("barcode",barcode);*/

        //入参：projectId ，jhffId
        //map.put("projectId","ff8080815afe5cfd015afe8867c40007");
       //map.put("jfdzId","ff8080815afe5cfd015afe99b351000d");
        Map<String,Object> dataMap = budgetExportService.exportJfdzList(param);
        if(dataMap.get("dybh") != null && dataMap.get("dybh") != "" && dataMap.get("dybh").toString().split(",").length == 2){
            String dybh = dataMap.get("dybh").toString();
            String year = dybh.split(",")[0];
            String bh = dybh.split(",")[1];
            String barcode = String.format("%05d", Integer.parseInt(bh));
            barcode = "FA"+year+barcode;

            dataMap.put("barcode",TPmIncomeApplyController.getBarCodePic(barcode));
            dataMap.put("bh",bh);
        }

        //输出word
        exportWord(map,dataMap,j,template);

        return j;
    }



    /**
     * 校内协作申请书 导出
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws TemplateException
     */
    @RequestMapping(params = "createWordXnxzjfsqs")
    @ResponseBody
    public AjaxJson createWordXnxzjfsqs(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, TemplateException {
        Map param = TagUtil.getMapByRequest(request);
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");

        Map map = new HashMap();
        map.put("exportFtl","xnxzjfsqs");
        AjaxJson j = new AjaxJson();
        //获取模板
        Template template = getTemplate(map);

        /** 准备数据 **/
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("projectName", "xxx项目");
        dataMap.put("datatime", "2018-02-18");

        List list = new ArrayList();
        for(int i=0; i<10; i++){
            map.put("year","2018");
            map.put("yuan","45892");
            map.put("nameq","信息网络工程教研室");
            map.put("fuzeren","文无敌");
            list.add(map);
        }
        dataMap.put("dataList",list);



        String barcode = TPmIncomeApplyController.getBarCodePic("DA201800495");
        dataMap.put("barcode",barcode);

        //输出word
        exportWord(map,dataMap,j,template);

        return j;
    }

    /**
     * 模板获取
     * @param map
     * @return
     * @throws IOException
     */
    private static Template getTemplate(Map map) throws IOException {
        String ftl = (String)map.get("exportFtl");//模板
        /** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl文件是放在代码目录下的export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template\\ys";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate(ftl+".ftl");

        return template;
    }

    /**
     * 导出word
     * @param map
     * @param dataMap
     * @param j
     * @param template
     * @throws IOException
     */
    private static void exportWord(Map map,Map dataMap,AjaxJson j,Template template) throws IOException {
        String ftl = (String)map.get("exportFtl");//模板
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        /** 指定输出word文件的路径 **/
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String exportTime = dateFormat.format( now );
        String outFilePath = classPath + "\\exportWord\\"+ ftl + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
            template.process(dataMap,out);
            String FileName = ftl + exportTime + ".doc";
            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("FileName", FileName);
            j.setAttributes(returnMap);
            j.setSuccess(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
        }
    }
}
