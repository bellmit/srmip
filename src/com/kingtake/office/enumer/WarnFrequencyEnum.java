package com.kingtake.office.enumer;

public enum WarnFrequencyEnum {
    
    ONCE("1", "一次"), EVERYDAY("1", "每天"), WORKDAY("2", "工作日");
    
    String value;
    
    String desc;
    
    WarnFrequencyEnum(String val, String des) {
        this.value = val;
        this.desc = des;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

}
