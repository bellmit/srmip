package com.kingtake.project.controller.funds;


public enum IndirectEnum {
	JJ_PROP("10"),
	JJ_PROP_NAME("间接费"),
	UNIVERSITY_PROP("1001"),
	UNIVERSITY_PROP_NAME("学校管理费"),
	UNIT_PROP("1002"),
	UNIT_PROP_NAME("责任单位管理费"),
	DEV_UNIT_PROP("1003"),
	DEV_UNIT_PROP_NAME("承研单位管理费"),
	PROJECTGROUP_PROP("1004"),
	PROJECTGROUP_PROP_NAME("项目组绩效津贴");
    
    public final String value;
    
    private IndirectEnum(String value)
    {
        this.value = value;
    }

    public String getName() {
        return value;
    }
    public static void main(String[] args) {
    	String[] keys = {"UNIVERSITY_PROP_","UNIT_PROP","DEV_UNIT_PROP"};
    	System.out.println(IndirectEnum.UNIVERSITY_PROP.value);
    	System.out.println(IndirectEnum.valueOf(IndirectEnum.class, "UNIT_PROP").value);
		
	}
}
