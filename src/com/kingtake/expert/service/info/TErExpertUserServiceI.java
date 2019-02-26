package com.kingtake.expert.service.info;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.expert.entity.info.TErExpertUserEntity;

public interface TErExpertUserServiceI extends CommonService {

    public TErExpertUserEntity checkUserExits(TErExpertUserEntity user);
	
}
