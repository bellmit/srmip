package com.kingtake.base.service.code;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;

public interface TBCodeTypeServiceI extends CommonService {

	public <T> void delete(T entity);

	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TBCodeTypeEntity tBCodeType,
			List<TBCodeDetailEntity> tBCodeDetailList);

	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TBCodeTypeEntity tBCodeType,
			List<TBCodeDetailEntity> tBCodeDetailList);

	public void delMain(TBCodeTypeEntity tBCodeType);

	/**
	 * 默认按钮-sql增强-新增操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doAddSql(TBCodeTypeEntity t);

	/**
	 * 默认按钮-sql增强-更新操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doUpdateSql(TBCodeTypeEntity t);

	/**
	 * 默认按钮-sql增强-删除操作
	 * 
	 * @param id
	 * @return
	 */
	public boolean doDelSql(TBCodeTypeEntity t);

	/**
	 * 根据code查询参数值.
	 * 
	 * @return
	 */
	public TBCodeDetailEntity findCodeDetailByCode(String codeTypeId,
			String code);

	/**
	 * 保存导入的数据
	 * 
	 * @param codeTypeEntitys
	 */
	public void saveCodeTypeForImport(List<TBCodeTypeEntity> codeTypeEntitys);

	/**
	 * 逻辑删除代码类别
	 * 
	 * @param codeTypeEntity
	 */
	public void deleteMain(TBCodeTypeEntity codeTypeEntity);

	/**
	 * 逻辑删除代码参数值
	 * 
	 * @param codeDetailEntity
	 */
	public void deleteDetail(TBCodeDetailEntity codeDetailEntity);

	/**
	 * 根据代码类别获取参数值
	 * 
	 * @param codeTypeEntity
	 * @return
	 */
	public List<TBCodeDetailEntity> getCodeDetailByCodeType(
			TBCodeTypeEntity codeTypeEntity);

	/**
	 * 根据编码查询
	 * 
	 * @param codeTypeEntity
	 * @return
	 */
	public TBCodeTypeEntity getCodeTypeByCode(TBCodeTypeEntity codeTypeEntity);

    /**
     * 查询代码(父子代码都查出来)
     * 
     * @param codeTypeEntity
     * @return
     */
    public List<TBCodeDetailEntity> getCodeByCodeType(TBCodeTypeEntity codeTypeEntity);

}
