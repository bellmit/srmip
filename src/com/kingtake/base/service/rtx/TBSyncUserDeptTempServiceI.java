package com.kingtake.base.service.rtx;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

public interface TBSyncUserDeptTempServiceI extends CommonService {

    /**
     * 更新部门
     * 
     * @param depart
     */
    public void addTempUpdateDepart(TSDepart depart);

    /**
     * 删除部门
     * 
     * @param depart
     */
    public void addTempDeleteDepart(TSDepart depart);

    /**
     * 更新用户
     * 
     * @param depart
     */
    public void addTempUpdateUser(TSUser user);

    /**
     * 删除用户
     * 
     * @param depart
     */
    public void addTempDeleteUser(TSUser user);

    /**
     * 同步数据到rtx
     */
    public void syncRtxUserDept();

}
