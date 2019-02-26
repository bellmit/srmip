package com.kingtake.base.service.xmlx;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.base.entity.xmlb.TBXmlbEntity;

public interface TBXmlbServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
    public void delXmlb(TBXmlbEntity xmlx);
 	
}
