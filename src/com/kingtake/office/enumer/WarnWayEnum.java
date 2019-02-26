package com.kingtake.office.enumer;

public enum WarnWayEnum {
    
    MESSAGE("1", "消息提醒"), SCHEDULE("2", "日程管理");
    
    String value;
    
    String desc;
    
    WarnWayEnum(String val, String des) {
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
