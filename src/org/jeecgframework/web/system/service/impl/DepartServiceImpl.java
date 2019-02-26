package org.jeecgframework.web.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.DepartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.DepartConstant;

/**
 * 
 * @author lxp
 * 
 */
@Service("departService")
@Transactional
public class DepartServiceImpl extends CommonServiceImpl implements DepartService {

    /**
     * 逻辑删除机构信息
     * 
     * @param depart
     */
    @Override
    public void deleteMain(TSDepart depart) {
        /*
         * depart = this.commonDao.get(TSDepart.class, depart.getId()); 
         * depart.setTSPDepart(null);
         */
        //逻辑删除，仅更改有效标志为：0
        depart.setValidFlag("0");
        this.commonDao.saveOrUpdate(depart);
    }

    @Override
    public  Integer generateOrder(String pid) {
        Integer newOrder;
        Integer curOrder;
        Integer addOrderRull = 1;
        if(pid !="" && pid != null){
            String sql = "select nvl(max(t.sort),0) sortCode from t_s_depart t  where t.parentdepartid = ?";
            Map<String, Object> pOrderMap = commonDao.findOneForJdbc(sql, pid);
            curOrder = Integer.valueOf(pOrderMap.get("sortCode").toString());
            newOrder = curOrder+ addOrderRull;
        }else{
            String sql = "select max(t.sort) sortCode from t_s_depart t  where t.parentdepartid is null";
            Map<String, Object> pOrderMap = commonDao.findOneForJdbc(sql);
            curOrder = Integer.valueOf(pOrderMap.get("sortCode").toString());
            newOrder = curOrder+ addOrderRull;
        }
        return newOrder;
    }

    @Override
    public List<TSDepart> getOrgByUserId(TSUser user) {
        String userId = user.getId();
        List<TSDepart> departList = new ArrayList<TSDepart>();
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        // 获取 当前组织机构的用户信息
        CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
        subCq.setProjection(Property.forName("tsDepart.id"));
        subCq.eq("tsUser.id", userId);
        subCq.add();
        if(StringUtil.isNotEmpty(userId)){
            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
            cq.add();
            departList = this.getListByCriteriaQuery(cq, false);
        }
        return departList;
    }
    
    /**
     * 获取单位树，第一级单位打开
     */
    @Override
    public List<ComboTree> getDepartTree(String lazy, ComboTree comboTree) {
        ComboTree treeNode = null;
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        if ("false".equals(lazy)) {
            treeNode = new ComboTree();
            CriteriaQuery parentCq = new CriteriaQuery(TSDepart.class);
            parentCq.isNull("TSPDepart");
            parentCq.eq("validFlag", DepartConstant.VALID_FLAG);
            parentCq.add();
            List<TSDepart> parentList = this.commonDao.getListByCriteriaQuery(parentCq, false);
            TSDepart parentDepart = null;
            if (parentList.size() > 0) {
                parentDepart = parentList.get(0);
            } else {
                throw new BusinessException("查询根节点失败！");
            }
            treeNode.setId(parentDepart.getId());
            treeNode.setText(parentDepart.getDepartname());
            List<ComboTree> subList = new ArrayList<ComboTree>();
            CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
            cq.eq("TSPDepart.id", parentDepart.getId());
            cq.eq("validFlag", DepartConstant.VALID_FLAG);
            cq.addOrder("sortCode", SortDirection.asc);
            cq.add();
            List<TSDepart> departsList = this.commonDao.getListByCriteriaQuery(cq, false);
            if (departsList.size() > 0) {
                treeNode.setState("open");
                for (TSDepart depart : departsList) {
                    ComboTree subCombo = new ComboTree();
                    subCombo.setId(depart.getId());
                    subCombo.setText(depart.getDepartname());
                    setChildren(subCombo);
                    subList.add(subCombo);
                }
            }
            treeNode.setChildren(subList);
            treeList.add(treeNode);
        } else {
            if (StringUtils.isNotEmpty(comboTree.getId())) {//非根节点
                TSDepart depart = this.commonDao.get(TSDepart.class, comboTree.getId());
                if (depart != null) {
                    treeNode = new ComboTree();
                    treeNode.setId(depart.getId());
                    treeNode.setText(depart.getDepartname());
                    List<ComboTree> subNodes = getChildren(treeNode, true);
                    if (subNodes.size() > 0) {
                        treeList.addAll(subNodes);
                    }
                }
            } else {//从根节点开始
                treeNode = new ComboTree();
                CriteriaQuery parentCq = new CriteriaQuery(TSDepart.class);
                parentCq.isNull("TSPDepart");
                parentCq.eq("validFlag", DepartConstant.VALID_FLAG);
                parentCq.add();
                List<TSDepart> parentList = this.commonDao.getListByCriteriaQuery(parentCq, false);
                TSDepart parentDepart = null;
                if (parentList.size() > 0) {
                    parentDepart = parentList.get(0);
                } else {
                    throw new BusinessException("查询根节点失败！");
                }
                treeNode.setId(parentDepart.getId());
                treeNode.setText(parentDepart.getDepartname());
                List<ComboTree> subNodes = getChildren(treeNode, true);
                if (subNodes.size() > 0) {
                    treeNode.setChildren(subNodes);
                    treeNode.setState("open");
                }
                treeList.add(treeNode);
            }
        }
        return treeList;
    }

    /**
     * 获取子节点
     * 
     * @param node
     * @return
     */
    private List<ComboTree> getChildren(ComboTree node, boolean lazy) {
        List<ComboTree> subList = new ArrayList<ComboTree>();
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        cq.eq("TSPDepart.id", node.getId());
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departsList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (departsList.size() > 0) {
            node.setState("open");
            for (TSDepart depart : departsList) {
                ComboTree subCombo = new ComboTree();
                subCombo.setId(depart.getId());
                subCombo.setText(depart.getDepartname());
                if (hasChildren(subCombo)) {
                    subCombo.setChildren(new ArrayList<ComboTree>());
                    subCombo.setState("closed");
                }
                subList.add(subCombo);
            }
        }
        return subList;
    }

    /**
     * 获取子节点
     * 
     * @param node
     * @return
     */
    private void setChildren(ComboTree node) {
        List<ComboTree> subList = new ArrayList<ComboTree>();
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        cq.eq("TSPDepart.id", node.getId());
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departsList = commonDao.getListByCriteriaQuery(cq, false);
        if (departsList.size() > 0) {
            node.setState("closed");
            for (TSDepart depart : departsList) {
                ComboTree subCombo = new ComboTree();
                subCombo.setId(depart.getId());
                subCombo.setText(depart.getDepartname());
                subList.add(subCombo);
                setChildren(subCombo);
            }
            node.setChildren(subList);
        }
    }

    /**
     * 判断是否有下级节点
     * 
     * @param node
     * @return
     */
    private boolean hasChildren(ComboTree node) {
        boolean flag = false;
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        cq.eq("TSPDepart.id", node.getId());
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departsList = commonDao.getListByCriteriaQuery(cq, false);
        if (departsList.size() > 0) {
            flag = true;
        }
        return flag;
    }

}