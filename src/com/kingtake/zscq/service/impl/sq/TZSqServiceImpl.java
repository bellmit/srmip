package com.kingtake.zscq.service.impl.sq;

import java.io.Serializable;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ZlConstants;
import com.kingtake.zscq.entity.sq.TZJfjlEntity;
import com.kingtake.zscq.entity.sq.TZSqEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.sq.TZSqServiceI;

@Service("tZSqService")
@Transactional
public class TZSqServiceImpl extends CommonServiceImpl implements TZSqServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TZJfjlEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TZJfjlEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TZJfjlEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TZJfjlEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TZJfjlEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TZJfjlEntity t) {
        return true;
    }

    /**
     * 保存受理通知书
     */
    @Override
    public void saveSq(TZSqEntity tzSq, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(tzSq.getId())) {
                this.commonDao.save(tzSq);
                if (StringUtils.isNotEmpty(tzSq.getZlsqId())) {
                    TZZlsqEntity zlsq = this.commonDao.get(TZZlsqEntity.class, tzSq.getZlsqId());
                    zlsq.setZlzt(ZlConstants.ZLZT_ZSQ);//专利状态改为授权
                    this.commonDao.updateEntitie(zlsq);
                }
                saveDetail(tzSq, request, "0");
            } else {
                TZSqEntity t = this.commonDao.get(TZSqEntity.class, tzSq.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tzSq, t);
                if (tzSq.getYqjnsj() == null) {
                    t.setYqjnsj(null);//手动置空
                }
                this.commonDao.saveOrUpdate(t);
                saveDetail(tzSq, request, "1");
            }
        } catch (Exception e) {
            throw new BusinessException("保存授权失败！", e);
        }
    }

    //保存子表记录
    private void saveDetail(TZSqEntity tzSq, HttpServletRequest request, String delFlag) {
        if ("1".equals(delFlag)) {
            String delSql = "delete from t_z_jfjl t where t.csbj='1' and t.sq_id=?";
            this.commonDao.executeSql(delSql, new Object[] { tzSq.getId() });
        }
        String nfListStr = request.getParameter("nfListStr");
        if (StringUtils.isNotEmpty(nfListStr)) {
        JSONArray jsonArray = JSONArray.parseArray(nfListStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            TZJfjlEntity tmp = new TZJfjlEntity();
            tmp.setCsbj("1");
            tmp.setSqId(tzSq.getId());
            String year = json.getString("fyspsj");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.valueOf(year));
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.DATE, 1);
            tmp.setFyspsj(cal.getTime());
            tmp.setJnfs("2");
            tmp.setJnje(json.getBigDecimal("jnje"));
            this.commonDao.save(tmp);
        }
        }
    }

    @Override
    public void delJfjl(TZJfjlEntity jfjl) {
        this.commonDao.delete(jfjl);
    }

}