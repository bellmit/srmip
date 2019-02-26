package com.kingtake.common.constant;

import java.util.HashMap;
import java.util.Map;

public class SendBillConstant {

    /**
     * 核稿意见
     */
    public static final String AUDIT_OPINION = "00";
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
        REVIEW_TRANSLATE.put(AUDIT_OPINION, "核稿意见");
        REVIEW_TRANSLATE.put(OFFICE_REVIEW, "机关部(院)领导阅批");
        REVIEW_TRANSLATE.put(LEADER_REVIEW, "校首长阅批");

        GRADE_TRANSLATE.put("1", "秘密");
        GRADE_TRANSLATE.put("2", "机密");
        GRADE_TRANSLATE.put("3", "绝密");
    }
}
