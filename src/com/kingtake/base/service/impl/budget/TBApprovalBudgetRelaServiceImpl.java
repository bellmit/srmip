package com.kingtake.base.service.impl.budget;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.sideccatalog.TPmTypeCatalogRelaEntity;
import com.kingtake.base.service.budget.TBApprovalBudgetRelaServiceI;

@Service("tBApprovalBudgetRelaService")
@Transactional
public class TBApprovalBudgetRelaServiceImpl extends CommonServiceImpl implements TBApprovalBudgetRelaServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBApprovalBudgetRelaEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBApprovalBudgetRelaEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBApprovalBudgetRelaEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBApprovalBudgetRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBApprovalBudgetRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBApprovalBudgetRelaEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBApprovalBudgetRelaEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_approval}",String.valueOf(t.getProjApproval()));
 		sql  = sql.replace("#{budget_nae}",String.valueOf(t.getBudgetNae()));
 		sql  = sql.replace("#{parent_id}",String.valueOf(t.getTBApprovalBudgetRelaEntity().getId()));
 		sql  = sql.replace("#{scale_flag}",String.valueOf(t.getScaleFlag()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{sort_code}",String.valueOf(t.getSortCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void saveProjectTypeInfoRela(String projectTypeId, String catalogListStr) {
        String sql = "delete from T_PM_TYPE_CATALOG_RELA t where t.T_TYPE_ID=?";
        this.commonDao.executeSql(sql, projectTypeId);
        List<TPmTypeCatalogRelaEntity> list = new ArrayList<TPmTypeCatalogRelaEntity>();
        JSONArray array = JSONArray.parseArray(catalogListStr);
        for (int i = 0; i < array.size(); i++) {
            TPmTypeCatalogRelaEntity relaEntity = new TPmTypeCatalogRelaEntity();
            JSONObject obj = (JSONObject) array.get(i);
            relaEntity.setCatalogId(obj.getString("id"));
            relaEntity.setTypeId(projectTypeId);
            list.add(relaEntity);
        }
        this.commonDao.batchSave(list);
    }

    /**
     * 保存从其它对应项目类型的关联模块复制到当前项目类型的数据，先删除当前项目类型关联模块数据再新增
     * 
     * @param void
     */
    public String saveCatalogRelaFromCopy(String projectTypeId, String fromProjectId)
    {
      //删除当前项目类型的关联模块数据
      String sql = "delete from T_PM_TYPE_CATALOG_RELA t where t.T_TYPE_ID=?";
      this.commonDao.executeSql(sql, projectTypeId);
      String typeCatalogRelaStr = "";
      //插入复制过来的项目类型的关联模块数据
      List<TPmTypeCatalogRelaEntity> fromList = this.commonDao.findHql("from TPmTypeCatalogRelaEntity t where t.typeId=?", fromProjectId);
      if (fromList.size() > 0 ) {
    	  StringBuffer sb = new StringBuffer();
    	  for(TPmTypeCatalogRelaEntity fromBean:fromList){
    		  TPmTypeCatalogRelaEntity newBean = new TPmTypeCatalogRelaEntity();
    		  String newCatalogId = fromBean.getCatalogId();//关联模块
    		  String newTypeId = projectTypeId;
    		  newBean.setCatalogId(newCatalogId);
    		  newBean.setTypeId(newTypeId);
    		  this.commonDao.save(newBean);
    		  sb.append(fromBean.getCatalogId()).append(",");
    	  }
    	  typeCatalogRelaStr = sb.toString();
          typeCatalogRelaStr = typeCatalogRelaStr.substring(0, typeCatalogRelaStr.length() - 1);
      }
      return typeCatalogRelaStr;
    }
    
    /**
     * 保存从其它对应项目类型的预算类别复制到当前项目类型的数据，先删除当前项目类型数据再新增
     * 
     * @param void
     */
    public void saveApprovalBudgetFromCopy(String id, String fromProjectId)
    {
      //删除当前项目类型的预算数据
      String sql = "delete from T_B_APPROVAL_BUDGET_RELA t where t.PROJ_APPROVAL=?";
      this.commonDao.executeSql(sql, id);

      //插入复制过来的项目类型的预算数据
      List<TBApprovalBudgetRelaEntity> fromList = this.commonDao.findHql("from TBApprovalBudgetRelaEntity t where t.TBApprovalBudgetRelaEntity.id is null and t.projApproval=?", fromProjectId);
      for(TBApprovalBudgetRelaEntity fromBean:fromList){
        TBApprovalBudgetRelaEntity newBean = new TBApprovalBudgetRelaEntity();
        newBean.setProjApproval(id);
        newBean.setBudgetNae(fromBean.getBudgetNae());
        newBean.setScaleFlag(fromBean.getScaleFlag());
        newBean.setSortCode(fromBean.getSortCode());
        newBean.setAddChildFlag(fromBean.getAddChildFlag());
        newBean.setShowFlag(fromBean.getShowFlag());
        newBean.setMemo(fromBean.getMemo());
        this.commonDao.save(newBean);
        saveBudget4Loop(fromBean.getId(),newBean.getId(),id);
      }
    }
    
    /**
     * 保存从其它对应经费类型的预算类别复制到当前经费类型的数据，先删除当前经费类型数据再新增
     * 
     * @param void
     */
    public void saveApprovalBudgetFromCopyFund(String id, String fromFundPropertyId)
    {
      //删除当前经费类型的预算数据
      String sql = "delete from T_B_APPROVAL_BUDGET_RELA t where t.PROJ_APPROVAL=?";
      this.commonDao.executeSql(sql, id);

      //插入复制过来的项目类型的预算数据
      List<TBApprovalBudgetRelaEntity> fromList = this.commonDao.findHql("from TBApprovalBudgetRelaEntity t where t.TBApprovalBudgetRelaEntity.id is null and t.projApproval=?", fromFundPropertyId);
      for(TBApprovalBudgetRelaEntity fromBean:fromList){
        TBApprovalBudgetRelaEntity newBean = new TBApprovalBudgetRelaEntity();
        newBean.setProjApproval(id);
        newBean.setBudgetNae(fromBean.getBudgetNae());
        newBean.setScaleFlag(fromBean.getScaleFlag());
        newBean.setSortCode(fromBean.getSortCode());
        newBean.setAddChildFlag(fromBean.getAddChildFlag());
        newBean.setShowFlag(fromBean.getShowFlag());
        newBean.setMemo(fromBean.getMemo());
        this.commonDao.save(newBean);
        saveBudget4Loop(fromBean.getId(),newBean.getId(),id);
      }
    }
    /**
     * 递归查询保存
     * @param oldId
     * @param newId
     * @param typeId
     */
    public void saveBudget4Loop(String oldId,String newId,String typeId){
      List<TBApprovalBudgetRelaEntity> fromList = this.commonDao.findHql("from TBApprovalBudgetRelaEntity t where t.TBApprovalBudgetRelaEntity.id = ?", oldId);
      for(TBApprovalBudgetRelaEntity fromBean:fromList){
        TBApprovalBudgetRelaEntity newBean = new TBApprovalBudgetRelaEntity();
        newBean.setProjApproval(typeId);
        newBean.setBudgetNae(fromBean.getBudgetNae());
        TBApprovalBudgetRelaEntity parentBean = new TBApprovalBudgetRelaEntity();
        parentBean.setId(newId);
        newBean.setTBApprovalBudgetRelaEntity(parentBean);
        newBean.setScaleFlag(fromBean.getScaleFlag());
        newBean.setSortCode(fromBean.getSortCode());
        newBean.setAddChildFlag(fromBean.getAddChildFlag());
        newBean.setShowFlag(fromBean.getShowFlag());
        newBean.setMemo(fromBean.getMemo());
        this.commonDao.save(newBean);
        saveBudget4Loop(fromBean.getId(),newBean.getId(),typeId);
      }
    }
    
}