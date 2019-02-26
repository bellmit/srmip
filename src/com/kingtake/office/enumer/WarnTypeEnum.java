package com.kingtake.office.enumer;

public enum WarnTypeEnum {
    
    PERSONAL_MATERS("00", "个人事项"), WORK_ITEMS("01", "工作事项");
    
    String value;
    
    String desc;
    
    WarnTypeEnum(String val, String des) {
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
