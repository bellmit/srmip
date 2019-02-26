package com.kingtake.common.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingtake.common.util.PropertiesUtil;

public class SrmipConstants {

    public static final String YES = "1";
    public static final String NO = "0";
    
    /**
     * 收发文标志
     * 0：收文标志
     * 1：发文标志
     */
    public static final String SWBZ = "0";
    public static final String FWBZ = "1";
    /**
     * 标准代码集
     */
    public static Map<String, Map<String, Object>> dicStandardMap = 
    		new HashMap<String, Map<String, Object>>();
    /**
     * 科研代码集
     */
	public static Map<String, Map<String, Object>> dicResearchMap = 
			new HashMap<String, Map<String, Object>>();
    
    public static final String INITIALIZED = "1";//初始化状态，未提交
    public static final String PROCESSING = "2";//已提交，处理中
    public static final String FINISHED = "3";//已完成

    public static final int PAGESIZE = 10;
    
    public static final String DIGIT[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    /** solr请求地址 */
    public static final String SOLR_URL;
    /** solr的配置文件 */
    public static final String SOLR_SEARCH_CONFIG = null;

    /** solr需要搜索的表信息 */
    public static final List<Map<String, Object>> SOLR_TABLE_LIST = null;
    public static final String SOLR_TABLE_ATTR = "attr";
    public static final String SOLR_TABLE_METHOD = "method";

    /** solr.xml文件的entity标签 */
    public static final String SOLR_KEY_ENTITY = "entity";
    /** solr.xml文件的table标签 */
    public static final String SOLR_KEY_TABLE = "table";
    /** solr.xml文件的tableName标签 */
    public static final String SOLR_KEY_TABLENAME = "tableName";
    /** solr.xml文件的id标签 */
    public static final String SOLR_KEY_ID = "id";
    /** solr.xml文件的title标签 */
    public static final String SOLR_KEY_TITLE = "title";
    /** solr.xml文件的content标签 */
    public static final String SOLR_KEY_CONTENT = "content";

    /** solr索引最后的更新日期 */
    public static Date SOLR_LAST_UPDATETIME;

    /** 提供下载工具路劲*/
    public static String PLUGIN_URL;
    
    static {
        // 获得solr请求地址=====================start============================
        Map<String, String> map = PropertiesUtil.read("solr.properties");
        SOLR_URL = map.get("url");
        
        Map<String, String> srmipMap = PropertiesUtil.read("srmip.properties");
        PLUGIN_URL = srmipMap.get("url");
        
        /*SOLR_SEARCH_CONFIG = map.get("configFile");
        // 获得solr请求地址======================end===========================

        // 获得solr搜索的表信息====================start============================
        SOLR_TABLE_LIST = new ArrayList<Map<String, Object>>();
        String path = new File(SolrOperate.class.getResource("/").getFile()).getPath() + File.separator
                + SOLR_SEARCH_CONFIG;
        Document doc = XmlUtil.getDoc(path);

        // 获得根元素
        Element root = doc.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element entity = it.next();
            Map<String, Object> attrMap = new HashMap<String, Object>();

            if (SrmipConstants.SOLR_KEY_ENTITY.equals(entity.getName())) {
                // 获得实体类
                Element table = entity.element(SrmipConstants.SOLR_KEY_TABLE);
                attrMap.put(SrmipConstants.SOLR_KEY_TABLE, table.getTextTrim());
                Element tableName = entity.element(SrmipConstants.SOLR_KEY_TABLENAME);
                attrMap.put(SrmipConstants.SOLR_KEY_TABLENAME, tableName.getTextTrim());

                // 遍历属性
                List<Map<String, String>> attrMethods = new ArrayList<Map<String, String>>();
                List<Element> elements = entity.elements();
                for (Element e : elements) {
                    if (!SrmipConstants.SOLR_KEY_TABLE.equals(e.getName())
                            && !SrmipConstants.SOLR_KEY_TABLENAME.equals(e.getName())) {
                        if (SrmipConstants.SOLR_KEY_ID.equals(e.getName())) {
                            String attr = e.getTextTrim();
                            Map<String, String> attrMethod = new HashMap<String, String>();
                            attrMethod.put(SOLR_TABLE_ATTR, attr);
                            attrMethod.put(SOLR_TABLE_METHOD,
                                    "get" + attr.substring(0, 1).toUpperCase() + attr.substring(1));
                            attrMethods.add(attrMethod);
                        } else {
                            String[] attrs = e.getTextTrim().split(",");
                            for (String attr : attrs) {
                                attr = attr.trim();
                                Map<String, String> attrMethod = new HashMap<String, String>();
                                attrMethod.put(SOLR_TABLE_ATTR, attr + "_content");
                                attrMethod.put(SOLR_TABLE_METHOD,
                                        "get" + attr.substring(0, 1).toUpperCase() + attr.substring(1));
                                attrMethods.add(attrMethod);
                            }
                        }
                    }
                }
                attrMap.put(SOLR_TABLE_ATTR + SOLR_TABLE_METHOD, attrMethods);
            }
            SOLR_TABLE_LIST.add(attrMap);
        }
        // 获得solr搜索的表信息=====================end===========================
         */    
    }
    
    public static String CONTRACT_OTHER = "0";
    public static String CONTRACT_YANZHI = "1";
    public static String CONTRACT_YANJIU = "4";
    public static String CONTRACT_JISHUFUWU = "8";
    
    public static Map<String, String[]> CONTRACT_TYPE = new HashMap<String, String[]>();
    static{
    	CONTRACT_TYPE.put(CONTRACT_OTHER, new String[]{"购置","生产订货、市场采购","合同标的<br><br>交付情况","合同标的<br><br>验收情况"});
    	CONTRACT_TYPE.put(CONTRACT_YANZHI, new String[]{"研制","研制","合同标的","主要研制<br><br>成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;果"});
    	CONTRACT_TYPE.put(CONTRACT_YANJIU, new String[]{"研究","研究","合同标的","主要研究<br><br>成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;果"});
    	CONTRACT_TYPE.put(CONTRACT_JISHUFUWU, new String[]{"技术服务","技术服务","合同工作<br><br>内容情况","工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作<br><br>完成情况"});
    };
    
    
    //tOHandover 交班材料提交状态==============start===================
    public static final String SUBMIT_NO = "0";//未提交
    public static final String SUBMIT_YES = "1";//已提交
    public static final String SUBMIT_RETURN = "2";//已返回
    public static final String SUBMIT_RECEIVE = "3";//已接收
    //tOHandover 交班材料提交状态==============end===================
    
    public static String LRLY_HAND = "0";
    public static String LRLY_FUNDS = "1";
    public static String LRLY_PRICE = "2";

    //预算类型
    public static String FUNDS_TYPE_TOTAL = "1";//总预算
    public static String FUNDS_TYPE_PART = "2";//分预算

    //发送类型
    public static String SEND_TYPE_RESEARCHACTIVITY = "researchActivity";//科研动态
    public static String SEND_TYPE_WORKPOINT = "workpoint";//工作要点
    public static String SEND_TYPE_HOLIDAY = "holiday";//假前工作计划
    public static String SEND_TYPE_SUMMARY = "summary";//总结材料
    public static String SEND_TYPE_LEADERINDICATE = "leaderIndicate";//校首长批示
    public static String SEND_TYPE_SCHEDULE = "schedule";//日程安排

    //材料类型
    public static String MATERIA_TYPE_ORIGINAL = "01";//原材料
    public static String MATERIA_TYPE_ASSIST = "02";//辅助材料
    public static String MATERIA_TYPE_COMPONENT = "03";//外购元器件
    
 // checkbox字符
    public static final String CHECKBOX_TRUE        = "☑";                   // 选中
    public static final String CHECKBOX_FALSE       = "□";                   // 未选中
}
