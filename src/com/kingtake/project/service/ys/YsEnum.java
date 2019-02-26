package com.kingtake.project.service.ys;

public enum YsEnum {
	//预算类型 ：1 总预算，2 年度预算，3 调增预算，5零基预算 ，6开支计划，7调整预算， 
	ZYS_BUDGET("1","总预算"),
	ND_BUDGET("2","年度预算"),
	TZ_BUDGET("3","调增预算"),
	YE_BUDGET("4","余额预算"),
	LJ_BUDGET("5","零基预算"),
	KZ_BUDGET("6","开支计划"),
	TZH_BUDGET("7","调整预算"),
	//合同状态： 0：未发送；1：已发送；2：已完成；3：被驳回
	HT_STATUS_0("HT_0","未发送"),
	HT_STATUS_1("HT_1","已发送"),
	HT_STATUS_2("HT_2","通过"),
	HT_STATUS_3("HT_3","驳回"),
	//财务状态：1是已提交,2财务已审核
	CW_STATUS_0("CW_0","未发送"),
	CW_STATUS_1("CW_1","通过"),
	CW_STATUS_2("CW_2","通过"),
	CW_STATUS_3("CW_3","已否决"),
	CW_STATUS_4("CW_4","已提交"),
	//垫支
	DZ_STATUS_1("DZ_1","未发送"),
	DZ_STATUS_2("DZ_2","已发送"),
	DZ_STATUS_3("DZ_3","通过"),
	//预算状态
	YS_STATUS_0("YS_0","否"),
	YS_STATUS_1("YS_1","是"),
	;
	private final String key;
    private final String value;
    private YsEnum(String key,String value){
        this.key = key;
        this.value = value;
    }
    
  //根据key获取枚举
    public static YsEnum getEnumByKey(String key){
        if(null == key){
            return null;
        }
        for(YsEnum temp:YsEnum.values()){
            if(temp.getKey().equals(key)){
                return temp;
            }
        }
        return null;
    }
    
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
    
    public static void main(String[] args) {
		System.out.println(YsEnum.KZ_BUDGET.getKey());
		System.out.println(YsEnum.KZ_BUDGET.getValue());
	}
}
