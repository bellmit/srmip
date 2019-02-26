package com.kingtake.office.enumer;

public enum WarnStatusEnum {
    
    UNFINISHIED("0", "未完成"), FINISHED("1", "已完成");
    
    String value;
    
    String desc;
    
    WarnStatusEnum(String val, String des) {
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
