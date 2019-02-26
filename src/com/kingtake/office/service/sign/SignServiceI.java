package com.kingtake.office.service.sign;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

public interface SignServiceI extends CommonService {
	
    public void saveSignature(HttpServletRequest request);

    /**
     * 删除印章
     * 
     * @param id
     */
    public void deleteSign(String id);

    /**
     * 查看印章
     * 
     * @param request
     * @param response
     * @return
     */
    //public String viewSign(HttpServletRequest request, HttpServletResponse response);
}
