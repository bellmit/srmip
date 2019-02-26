package com.kingtake.common.constant;

import java.util.HashMap;
import java.util.Map;

public class AbatePayConstant {
    /** 减免*/
    public static final String ABATE_FLAG = "0";
    /**垫支*/
    public static final String PAY_FLAG = "1";
    
    public static final Map<String, String> ABATE_PAY_FLAG = new HashMap<String, String>();
    
    static{
        ABATE_PAY_FLAG.put(ABATE_FLAG, "减免");
        ABATE_PAY_FLAG.put(PAY_FLAG, "垫支");
    }   
}
