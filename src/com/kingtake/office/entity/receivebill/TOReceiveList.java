package com.kingtake.office.entity.receivebill;

import java.util.List;

import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;

/**
 * @Title: Entity
 * @Description:
 * @author onlineGenerator
 * @date 2015-07-17 15:43:38
 * @version V1.0
 *
 */

@SuppressWarnings("serial")
public class TOReceiveList implements java.io.Serializable {

    /**
     * 校首长阅批列表
     */
    private List<TOFlowReceiveLogsEntity> llist;

    /**
     * 机关部阅批列表
     */
    private List<TOFlowReceiveLogsEntity> olist;

    /**
     * 承办单位意见列表
     */
    private List<TOFlowReceiveLogsEntity> dlist;

    public List<TOFlowReceiveLogsEntity> getLlist() {
        return llist;
    }

    public void setLlist(List<TOFlowReceiveLogsEntity> llist) {
        this.llist = llist;
    }

    public List<TOFlowReceiveLogsEntity> getOlist() {
        return olist;
    }

    public void setOlist(List<TOFlowReceiveLogsEntity> olist) {
        this.olist = olist;
    }

    public List<TOFlowReceiveLogsEntity> getDlist() {
        return dlist;
    }

    public void setDlist(List<TOFlowReceiveLogsEntity> dlist) {
        this.dlist = dlist;
    }

}
