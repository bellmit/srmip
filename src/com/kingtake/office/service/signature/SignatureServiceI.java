package com.kingtake.office.service.signature;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

public interface SignatureServiceI extends CommonService {
	
    public void saveSignature(HttpServletRequest request);
}
