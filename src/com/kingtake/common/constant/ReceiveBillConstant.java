package com.kingtake.common.constant;

import java.util.HashMap;
import java.util.Map;

public class ReceiveBillConstant {
    /**
     * 处理状态_未处理
     */
    public static final String OPERATE_UNTREATED = "0";
    /**
     * 处理状态_已处理
     */
    public static final String OPERATE_TREATED = "1";
    /**
     * 公文状态_未生成
     */
    public static final String BILL_UNCREATED = "0";
    /**
     * 公文状态_流转中
     */
    public static final String BILL_FLOWING = "1";
    /**
     * 公文状态_已完成
     */
    public static final String BILL_COMPLETE = "2";
    /**
     * 公文状态_已归档
     */
    public static final String BILL_ARCHIVED = "3";
    /**
     * 公文状态_被驳回
     */
    public static final String BILL_REBUT = "4";

    /**
     * 承办单位意见
     */
    public static final String DUTY_OPINION = "00";
    /**
     * 机关部(院)领导阅批
     */
    public static final String OFFICE_REVIEW = "01";
    /**
     * 校首长阅批
     */
    public static final String LEADER_REVIEW = "02";
    /**
     * 收发文标志-收文
     */
    public static final String SRFLAG_RECEIVE = "0";
    /**
     * 收发文标志-发文
     */
    public static final String SRFLAG_SEND = "1";
    /**
     * 审批状态:(1-->"通过"; 0-->"驳回")
     */
    public static final String AUDIT_PASS = "1";
    public static final String AUDIT_REBUT = "0";
    public static final Map<String, String> AUDIT_FLAG = new HashMap<String, String>();
    static {
        AUDIT_FLAG.put(AUDIT_REBUT, "驳回");
        AUDIT_FLAG.put(AUDIT_PASS, "通过");
    };

    public static final Map<String, String> REVIEW_TRANSLATE = new HashMap<String, String>();

    public static final Map<String, String> GRADE_TRANSLATE = new HashMap<String, String>();

    static {
        REVIEW_TRANSLATE.put(DUTY_OPINION, "承办单位意见");
        REVIEW_TRANSLATE.put(OFFICE_REVIEW, "机关部(院)领导阅批");
        REVIEW_TRANSLATE.put(LEADER_REVIEW, "校首长阅批");

        GRADE_TRANSLATE.put("1", "秘密");
        GRADE_TRANSLATE.put("2", "机密");
        GRADE_TRANSLATE.put("3", "绝密");
    }
}
