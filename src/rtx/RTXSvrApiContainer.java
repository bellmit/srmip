package rtx;

import org.jeecgframework.core.util.ResourceUtil;

/**
 * rtxapi单例容器
 * 
 * @author admin
 * 
 */
public class RTXSvrApiContainer {

    private static RTXSvrApi rtxApi = null;
    
    static {
        try {
            rtxApi = new RTXSvrApi();
            String rtxIp = ResourceUtil.getConfigByName("rtx.server.ip");
            rtxApi.setServerIP(rtxIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RTXSvrApiContainer() {
    }

    /**
     * 获取rtxApi实例
     * 
     * @return
     */
    public static RTXSvrApi getRtxApiInstance() {
        return rtxApi;
    }

}
