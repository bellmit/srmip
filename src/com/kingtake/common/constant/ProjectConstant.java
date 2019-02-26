package com.kingtake.common.constant;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kingtake.common.util.XmlUtil;
import com.kingtake.project.entity.price.TPmContractPriceDesignEntity;
import com.kingtake.project.entity.price.TPmContractPriceManageEntity;
import com.kingtake.project.entity.price.TPmContractPriceMaterialEntity;
import com.kingtake.project.entity.price.TPmContractPriceOutCorpEntity;
import com.kingtake.project.entity.price.TPmContractPriceProfitEntity;
import com.kingtake.project.entity.price.TPmContractPriceSalaryEntity;
import com.kingtake.project.entity.price.TPmContractPriceTrialEntity;

public class ProjectConstant {
    /**
     * 项目大类-计划
     */
    public static final String PROJECT_PLAN = "1";
    /**
     * 
     * 项目大类-合同
     */
    public static final String PROJECT_CONTRACT = "2";

    /** 生产订货类合同价款 */
    public static final String CONTRACT_PRODUCE = "3";
    /** 研制类合同价款 */
    public static final String CONTRACT_DEVELOP = "4";
    /** 采购类合同价款 */
    public static final String CONTRACT_BUY = "5";
    /**
     * 技术服务类合同价款
     */
    public static final String CONTRACT_TECH = "6";

    /**
     * 价款计算书：详细计算表类型 1：管理类 2：材料类 3：工资类 4：利益类 5：设计类 6：外协类 7：试验类
     */
    public static Map<String, Map<String, Object>> CONTRACT_PRICE_DETAIL = null;

    public static Map<String, String> FUNDS_SQL = null;
    static {
        CONTRACT_PRICE_DETAIL = new HashMap<String, Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("class", TPmContractPriceManageEntity.class);
        map1.put("url", "tPmContractPriceManageController.do");
        CONTRACT_PRICE_DETAIL.put("1", map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("class", TPmContractPriceMaterialEntity.class);
        map2.put("url", "tPmContractPriceMaterialController.do");
        CONTRACT_PRICE_DETAIL.put("2", map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("class", TPmContractPriceSalaryEntity.class);
        map3.put("url", "tPmContractPriceSalaryController.do");
        CONTRACT_PRICE_DETAIL.put("3", map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("class", TPmContractPriceProfitEntity.class);
        map4.put("url", "tPmContractPriceProfitController.do");
        CONTRACT_PRICE_DETAIL.put("4", map4);

        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("class", TPmContractPriceDesignEntity.class);
        map5.put("url", "tPmContractPriceDesignController.do");
        CONTRACT_PRICE_DETAIL.put("5", map5);

        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("class", TPmContractPriceOutCorpEntity.class);
        map6.put("url", "tPmContractPriceOutCorpController.do");
        CONTRACT_PRICE_DETAIL.put("6", map6);

        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("class", TPmContractPriceTrialEntity.class);
        map7.put("url", "tPmContractPriceTrialController.do");
        CONTRACT_PRICE_DETAIL.put("7", map7);

        FUNDS_SQL = new HashMap<String, String>();
        // 初始化预算sql语句
        String path = new File(ProjectConstant.class.getResource("/").getFile()).getPath() + File.separator
                + "fundsSql.xml";
        Document doc = XmlUtil.getDoc(path);

        // 获得根元素
        Element root = doc.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element entity = it.next();
            FUNDS_SQL.put(entity.attribute("key").getText(), entity.getTextTrim());
        }
    }

    /**
     * 进账合同审批类型和出账合同审批类型 "i"：进账 "o"：出账
     */
    public static final String INCOME_CONTRACT = "i";
    public static final String OUTCOME_CONTRACT = "o";

    /**
     * 审批处理分类--》 "0"：发送给别人审批的； "1"：别人发送给自己审批的-->已完成 "2"：别人发送给自己审批的-->未完成
     */
    public static final String SEND_APPR = "0";
    public static final String RECEIVE_FINISH_APPR = "1";
    public static final String RECEIVE_UNFINISH_APPR = "2";
    
    /**
     * 上报状态
     */
    public static final String PROJECT_UNREPORT = "0";//未生效
    public static final String PROJECT_REPORT = "1";//已上报
    
    /**
     * 项目状态
     */
    public static final String PROJECT_STATUS_APPLYING = "01";//申请中
    public static final String PROJECT_STATUS_DECLARE = "02";//申报书提交
    public static final String PROJECT_STATUS_REVIEW = "03";//审查报批
    public static final String PROJECT_STATUS_EXECUTING = "04";//正在执行
    public static final String PROJECT_STATUS_PAUSE = "05";//暂停执行
    public static final String PROJECT_STATUS_APPROVED = "06";//已验收
    public static final String PROJECT_STATUS_EVALUEATED = "07";//已结题
    public static final String PROJECT_STATUS_PASS = "99";//未立项
    public static final String PROJECT_STATUS_FINISHED = "98";//完成状态
    public static final String PROJECT_STATUS_SETUP = "97";//立项

    public static Map<String, String> planContractMap = new HashMap<String, String>();
    public static Map<String, String> projectStatusMap = new HashMap<String, String>();

    static {
        planContractMap.put(PROJECT_PLAN, "计划类");
        planContractMap.put(PROJECT_CONTRACT, "合同类");
        planContractMap.put(CONTRACT_PRODUCE, "生产订货类合同价款");
        planContractMap.put(CONTRACT_DEVELOP, "研制类合同价款");
        planContractMap.put(CONTRACT_BUY, "采购类合同价款");
        planContractMap.put(CONTRACT_TECH, "技术服务类合同价款");

        projectStatusMap.put(PROJECT_STATUS_APPLYING, "申请中");
        projectStatusMap.put(PROJECT_STATUS_DECLARE, "申报书提交");
        projectStatusMap.put(PROJECT_STATUS_REVIEW, "审查报批");
        projectStatusMap.put(PROJECT_STATUS_EXECUTING, "正在执行");
        projectStatusMap.put(PROJECT_STATUS_PAUSE, "暂停执行");
        projectStatusMap.put(PROJECT_STATUS_APPROVED, "已验收");
        projectStatusMap.put(PROJECT_STATUS_EVALUEATED, "已结题");
        projectStatusMap.put(PROJECT_STATUS_PASS, "未立项");
        projectStatusMap.put(PROJECT_STATUS_FINISHED, "完成状态");
        projectStatusMap.put(PROJECT_STATUS_SETUP, "立项");
    }

    public static final String PROJECT_BUDGET_EQUIPMENT = "设备费";
    public static final String PROJECT_BUDGET_MATERIAL = "材料费";
    public static final String PROJECT_BUDGET_OUTSIDE = "外协费";
    public static final String PROJECT_BUDGET_BUSINESS = "业务费";

    /**
     * 项目申报书是否被院系审核 "1"表示已审核 "0"表示未审核
     */
    public static final String PROJECT_DECLARE_DEPART_AUDITED = "1";
    public static final String PROJECT_DECLARE_DEPART_UNAUDITED = "0";

    /** 项目合并状态-正常 */
    public static final String PROJECT_MERGE_FLAG_NORMAL = "0";
    /** 项目合并状态-合并后新项目 */
    public static final String PROJECT_MERGE_FLAG_INITIATIVE = "1";
    /** 项目合并状态-被合并项目 */
    public static final String PROJECT_MERGE_FLAG_PASSIVE = "2";

    /** 处理状态_未处理 */
    public static final String OPERATE_UNTREATED = "0";
    /** 处理状态_已处理 */
    public static final String OPERATE_TREATED = "1";

    /** 承研单位考核 */
    public static final String AUDIT_RESEARCH = "1";
    /** 责任单位审核 */
    public static final String AUDIT_DUTY = "2";
    /** 科研部审批 */
    public static final String AUDIT_SCIENTIFIC = "3";

    public static Map<String, String> reviewMap = new HashMap<String, String>();
    static {
        reviewMap.put(AUDIT_RESEARCH, "承研单位考核");
        reviewMap.put(AUDIT_DUTY, "责任单位审核");
        reviewMap.put(AUDIT_SCIENTIFIC, "科研部审批");
    }

    /** 立项状态_已立项 */
    public static final String APPROVAL_YES = "1";
    /** 立项状态_未立项 */
    public static final String APPROVAL_NO = "0";
    /** 驳回 */
    public static final String APPROVAL_BACK = "2";

    /**
     * 项目基本信息
     */
    public static final String PROJECT_MODULE_PROJECTINFO = "1";
    /**
     * 项目过程管理
     */
    public static final String PROJECT_MODULE_PROCESSMGR = "2";

    /**
     * 通用申报书
     */
    public static final String PROJECT_DECALRE_TYPE_COMMON = "1";
    /**
     * 军内科研申报书
     */
    public static final String PROJECT_DECALRE_TYPE_ARMY = "2";
    /**
     * 维修科研
     */
    public static final String PROJECT_DECALRE_TYPE_REPAIR = "3";
    /**
     * 技术基础
     */
    public static final String PROJECT_DECALRE_TYPE_TECHNOLOGY = "4";
    /**
     * 后勤科研
     */
    public static final String PROJECT_DECALRE_TYPE_BACK = "5";
}
