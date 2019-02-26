package org.jeecgframework.web.system.service;

import java.util.List;

import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
/**
 * 
 * @author  lxp
 *
 */
public interface DepartService extends CommonService{

	/**
	 * 逻辑删除机构信息
	 * @param depart
	 */
	public void deleteMain(TSDepart depart);
	
	 /**
     * 生成机构排序码
     *
     * @param id
     *            机构主键
     * @param pid
     *            机构的父级主键
     * @return 组织机构排序码
     */
    Integer generateOrder(String pid);
	
    /**
     * 根据用户ID获取部门列表
     * 
     * @param user
     * @return
     */
    public List<TSDepart> getOrgByUserId(
            TSUser user);

    /**
     * 获取单位下拉列表,默认第一级展开
     * 
     * @param lazy
     * @param comboTree
     * @return
     */
    public List<ComboTree> getDepartTree(String lazy, ComboTree comboTree);

}
