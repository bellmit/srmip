package com.kingtake.common.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 专家评审常量
 * 
 * @author admin
 * 
 */
public class ExpertReviewConstant {
    /**
     * 评审类型-项目
     */
    public static String REVIEW_TYPE_PROJECT = "1";

    public static Map<String, String> reviewTypeMap = new HashMap<String, String>();

    static{
        reviewTypeMap.put(REVIEW_TYPE_PROJECT, "项目");
    }
    

}
