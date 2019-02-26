package com.kingtake.project.service.contractnodecheck;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;
import com.kingtake.project.entity.contractnodecheck.TPmOutContractNodeCheckEntity;

public interface TPmOutContractNodeCheckServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);

    public void finishAppr(HttpServletRequest request);

    /**
     * 同意支付
     * @param id
     */
    public void doPassPay(String id);

    /**
     * 不同意支付
     * @param apply
     */
    public void doReject(TPmOutContractNodeCheckEntity apply);
 	

 	

}
