package org.jeecgframework.web.system.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.web.system.pojo.base.ExpertClient;

/**
 * 对在线用户的管理
 * @author JueYue
 * @date 2013-9-28
 * @version 1.0
 */
public class ExpertClientManager {
	
    private static ExpertClientManager instance = new ExpertClientManager();
	
    private ExpertClientManager() {
		
	}
	
    public static ExpertClientManager getInstance() {
		return instance;
	}
	
    private Map<String, ExpertClient> map = new HashMap<String, ExpertClient>();
	
	/**
	 * 
	 * @param sessionId
	 * @param client
	 */
    public void addClinet(String sessionId, ExpertClient client) {
		map.put(sessionId, client);
	}
	/**
	 * sessionId
	 */
	public void removeClinet(String sessionId){
		map.remove(sessionId);
	}
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
    public ExpertClient getClient(String sessionId) {
		return map.get(sessionId);
	}
	/**
	 *
	 * @return
	 */
    public ExpertClient getClient() {
		return map.get(ContextHolderUtils.getSession().getId());
	}
	/**
	 * 
	 * @return
	 */
    public Collection<ExpertClient> getAllClient() {
		return map.values();
	}

}
