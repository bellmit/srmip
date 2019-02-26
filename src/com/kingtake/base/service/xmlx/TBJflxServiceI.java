package com.kingtake.base.service.xmlx;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.base.entity.xmlb.TBJflxEntity;

public interface TBJflxServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	public void delJflx(TBJflxEntity jflx);
 	
}
