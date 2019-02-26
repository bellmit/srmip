package com.kingtake.common.constant;

import java.util.HashMap;
import java.util.Map;

public class ApprovalConstant {

    /**
     * 机关部(院)领导阅批
     */
    public static final String OFFICE_REVIEW = "01";
    /**
     * 校首长阅批
     */
    public static final String LEADER_REVIEW = "02";

    public static final Map<String, String> REVIEW_TRANSLATE = new HashMap<String, String>();

    public static final Map<String, String> GRADE_TRANSLATE = new HashMap<String, String>();

    static {
        REVIEW_TRANSLATE.put(OFFICE_REVIEW, "机关部(院)领导阅批");
        REVIEW_TRANSLATE.put(LEADER_REVIEW, "校首长阅批");

        GRADE_TRANSLATE.put("1", "秘密");
        GRADE_TRANSLATE.put("2", "机密");
        GRADE_TRANSLATE.put("3", "绝密");
    }

    /**
     * 审批类型
     */
    public static final String APPR_TYPE_CHECK = "01";//合同验收报告
    public static final String APPR_TYPE_FUNDS = "02";//预算审批
    public static final String APPR_TYPE_INCOME = "03";//进账合同审批
    public static final String APPR_TYPE_OUTCOME = "04";//出账合同审批
    public static final String APPR_TYPE_NODECHECK = "05";//合同节点审批
    public static final String APPR_TYPE_APPRAISAL = "06";//成果鉴定审批
    public static final String APPR_TYPE_APPRAISAL_APPLY = "07";//成果鉴定申请
    public static final String APPR_TYPE_RESULT_REWARD = "08";//成果奖励
    public static final String APPR_TYPE_TASK = "09";//任务/任务节点
    public static final String APPR_TYPE_PAY_APPLY = "10";//支付申请
    public static final String APPR_TYPE_TASK_NODECHECK = "11";//任务节点考核
    public static final String APPR_TYPE_FINISH_APPLY = "12";//结题申请审批
    public static final String APPR_TYPE_PLAN_DRAFT = "13";//计划草案审批
    public static final String APPR_TYPE_SEAL_USE = "14";//印章使用审批
    public static final String APPR_TYPE_TRAVEL = "15";//差旅请假审批
    public static final String APPR_TYPE_ZLSQ = "16";//专利申请审批
    public static final String APPR_TYPE_CGJH = "17";//采购计划审批
    public static final String APPR_TYPE_INCOME_CONTRACT_NODECHECK = "18";//进账合同节点考核
    public static final String APPR_TYPE_SBS = "19";//申报书审批
    public static final String APPR_TYPE_XM = "20";//项目申报审批
    public static final String APPR_TYPE_INCOMEPLAN = "24";//项目申报审批
    public static final Map<String, String> APPR_TYPE_TABLENAME = new HashMap<String, String>();
    static {
        APPR_TYPE_TABLENAME.put(APPR_TYPE_CHECK, "t_pm_contract_check");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_FUNDS, "t_pm_project_funds_appr");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_INCOME, "t_pm_income_contract_appr");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_OUTCOME, "t_pm_outcome_contract_appr");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_NODECHECK, "t_pm_outcontract_node_check");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_APPRAISAL, "t_b_appraisa_approval");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_APPRAISAL_APPLY, "t_b_appraisal_apply");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_RESULT_REWARD, "t_b_result_reward");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_TASK, "t_pm_task");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_TASK_NODECHECK, "t_pm_task_node");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_PAY_APPLY, "t_pm_pay_apply");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_FINISH_APPLY, "t_pm_finish_apply");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_PLAN_DRAFT, "t_pm_plan_draft");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_SEAL_USE, "t_pm_seal_use");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_TRAVEL, "t_o_travel_leave");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_ZLSQ, "t_z_zlsq");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_CGJH, "t_b_purchase_plan");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_INCOME_CONTRACT_NODECHECK, "t_pm_contract_node_check");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_SBS, "t_b_pm_declare");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_XM, "t_pm_project");
        APPR_TYPE_TABLENAME.put(APPR_TYPE_INCOMEPLAN, "t_pm_project_plan");
    }

    public static final Map<String, String> APPR_TYPE_NAME = new HashMap<String, String>();
    static {
        APPR_TYPE_NAME.put(APPR_TYPE_CHECK, "合同验收报告");
        APPR_TYPE_NAME.put(APPR_TYPE_FUNDS, "项目预算");
        APPR_TYPE_NAME.put(APPR_TYPE_INCOME, "进账合同");
        APPR_TYPE_NAME.put(APPR_TYPE_OUTCOME, "出账合同");
        APPR_TYPE_NAME.put(APPR_TYPE_NODECHECK, "合同节点验收报告");
        APPR_TYPE_NAME.put(APPR_TYPE_APPRAISAL_APPLY, "成果鉴定申请");
        APPR_TYPE_NAME.put(APPR_TYPE_APPRAISAL, "成果鉴定");
        APPR_TYPE_NAME.put(APPR_TYPE_RESULT_REWARD, "成果奖励");
        APPR_TYPE_NAME.put(APPR_TYPE_TASK, "任务书");
        APPR_TYPE_NAME.put(APPR_TYPE_PAY_APPLY, "支付申请");
        APPR_TYPE_NAME.put(APPR_TYPE_FINISH_APPLY, "结题申请");
        APPR_TYPE_NAME.put(APPR_TYPE_PLAN_DRAFT, "计划草案");
        APPR_TYPE_NAME.put(APPR_TYPE_SEAL_USE, "印章使用");
        APPR_TYPE_NAME.put(APPR_TYPE_TRAVEL, "差旅请假");
        APPR_TYPE_NAME.put(APPR_TYPE_ZLSQ, "专利申请");
        APPR_TYPE_NAME.put(APPR_TYPE_CGJH, "采购计划");
        APPR_TYPE_NAME.put(APPR_TYPE_TASK_NODECHECK, "任务节点考核");
        APPR_TYPE_NAME.put(APPR_TYPE_INCOME_CONTRACT_NODECHECK, "进账合同节点考核");
        APPR_TYPE_NAME.put(APPR_TYPE_SBS, "申报书申请");
        APPR_TYPE_NAME.put(APPR_TYPE_XM, "项目申报");
        APPR_TYPE_NAME.put(APPR_TYPE_INCOMEPLAN, "计划下达");
    }

    /**
     * 审批角色（0：发起人；1：院系主管参谋；2：机关主管参谋；）
     */
    public static final String APPR_ROLE_OTHER = "o";
    public static final String APPR_ROLE_FAQI = "0";
    public static final String APPR_ROLE_YUANXI = "1";
    public static final String APPR_ROLE_JIGUAN = "2";

    /**
     * 列表的三种情况（0：发起审批；1：处理审批）
     */
    public static final String APPR_DATAGRID_SEND = "0";
    public static final String APPR_DATAGRID_RECEIVE = "1";

    /**
     * 将审批类型进行分类： 0.发送审批需要接收人处理审批的 1.发送审批需要院系参谋转发处理的 2.发送审批需要机关参谋转发处理的
     */
    public static final String HANDLER_TYPE_HANDLER = "0";
    public static final String HANDLER_TYPE_YANXI = "1";
    public static final String HANDLER_TYPE_JIGUAN = "2";

    /**
     * 审批状态,待生成表单:"u", 未发送:"0", 已发送:"1", 已完成:"2",被驳回: "3"
     */
    public static final String APPRSTATUS_UNAPPR = "u";
    public static final String APPRSTATUS_UNSEND = "0";
    public static final String APPRSTATUS_SEND = "1";
    public static final String APPRSTATUS_FINISH = "2";
    public static final String APPRSTATUS_REBUT = "3";

    /** 参会人员类型-未参会专家 */
    public static final String MEETING_UNATTEND_EXPERT = "0";
    /** 参会人员类型-参会专家 */
    public static final String MEETING_ATTEND_EXPERT = "1";
    /** 参会人员类型-参会人员 */
    public static final String MEETING_UNATTEND_MEMBER = "2";


    /** 申请表审核状态-未提交 */
    public static final String APPLYSTATUS_UNSEND = "0";
    /** 申请表审核状态-已提交 */
    public static final String APPLYSTATUS_SEND = "1";
    /** 申请表审核状态-已审核 */
    public static final String APPLYSTATUS_AUDITED = "2";
    /** 申请表审核状态-已提交批复意见 */
    public static final String APPLYSTATUS_APPROVAL_VIEW = "3";
    /** 已完成 */
    public static final String APPLYSTATUS_FINISH = "4";

    /**
     * 鉴定计划状态
     */
    public static final String APPRAISAL_PROJECT_UNSEND = "0";//鉴定计划未提交
    public static final String APPRAISAL_PROJECT_SEND = "1";//鉴定计划已提交
    public static final String APPRAISAL_PROJECT_APPROVAL_SEND = "2";//鉴定申请审批已提交
    public static final String APPRAISAL_PROJECT_APPROVAL_FINISH = "3";//鉴定申请审批已完成
    public static final String APPRAISAL_PROJECT_APPLY_SEND = "4";//鉴定申请已提交
    public static final String APPRAISAL_PROJECT_APPLY_FINISH = "5";//鉴定申请已完成
    public static final String APPRAISAL_PROJECT_MATERIA_SEND = "6";//鉴定材料已提交
    public static final String APPRAISAL_PROJECT_MATERIA_FINISH = "7";//鉴定材料审查通过
    public static final String APPRAISAL_PROJECT_UNFINISH = "8";//鉴定计划未完成
    public static final String APPRAISAL_PROJECT_FINISHED = "9";//鉴定计划已完成
}
