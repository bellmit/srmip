package com.kingtake.expert.page.info;

import com.kingtake.expert.entity.info.TErExpertUserEntity;

/**
 * 专家信息界面对象
 * 
 * @author admin
 * 
 */
public class TErExpertPage extends TErExpertUserEntity {

    private static final long serialVersionUID = 1L;

    public String getSexStr() {
        return convertGetSex();
    }

    public String getExpertPositionStr() {
        return convertGetExpertPosition();
    }

    public String getExpertPraciticReqStr() {
        return convertGetExpertPraciticReq();
    }

    public String getExpertProfessionStr() {
        return convertGetExpertProfession();
    }

}
