package org.jeecgframework.web.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.PortalUserVo;
import org.jeecgframework.web.system.pojo.base.TPortalEntity;
import org.jeecgframework.web.system.pojo.base.TPortalLayoutEntity;
import org.jeecgframework.web.system.pojo.base.TPortalRoleEntity;
import org.jeecgframework.web.system.pojo.base.TPortalUserEntity;
import org.jeecgframework.web.system.service.TPortalServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("tPortalService")
@Transactional
public class TPortalServiceImpl extends CommonServiceImpl implements TPortalServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TPortalEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        // 执行新增操作配置的sql增强
        this.doAddSql((TPortalEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        // 执行更新操作配置的sql增强
        this.doUpdateSql((TPortalEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TPortalEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TPortalEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPortalEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPortalEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
        sql = sql.replace("#{height}", String.valueOf(t.getHeight()));
        sql = sql.replace("#{url}", String.valueOf(t.getUrl()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 获取用户的待办信息
     */
    @Override
    public TPortalUserEntity getPortalByUserName(String username) {
        TPortalUserEntity entity = getPortalInfoByUserName(username);
        if (entity == null || entity.getLayoutEntity() == null) {
            TPortalUserEntity adminEntity = getPortalInfoByUserName("admin");
            if (entity == null) {
                entity = adminEntity;
            } else {
                if (entity.getLayoutEntity() == null) {
                    entity.setLayoutEntity(adminEntity.getLayoutEntity());
                }
            }
        }
        return entity;
    }

    // /**
    // * 获取用户的待办信息
    // */
    // @Override
    // public TPortalUserEntity getLayoutByUserName(String userName) {
    // TPortalUserEntity entity = getPortalByUserName(userName);
    //
    // return entity;
    // }

    /**
     * 根据用户名查找
     * 
     * @param userName
     * @return
     */
    private TPortalUserEntity getPortalInfoByUserName(String userName) {
        TPortalUserEntity entity = null;
        CriteriaQuery cq = new CriteriaQuery(TPortalUserEntity.class);
        cq.eq("userName", userName);
        cq.add();
        List<TPortalUserEntity> portalUserList = commonDao.getListByCriteriaQuery(cq, false);
        if (portalUserList.size() > 0) {
            entity = portalUserList.get(0);
        }
        return entity;
    }

    /**
     * 获取所有的待办布局.
     * 
     * @return
     */
    @Override
    public List<TPortalLayoutEntity> getAllLayout() {
        CriteriaQuery cq = new CriteriaQuery(TPortalLayoutEntity.class);
        List<TPortalLayoutEntity> portalLayoutList = commonDao.getListByCriteriaQuery(cq, false);
        return portalLayoutList;
    }

    /**
     * 获取用户的待办信息
     */
    @Override
    public List<PortalUserVo> getAddPortalList(String username) {
        List<TPortalEntity> portalList = this.getAllPortalList(username);
        List<String> idList = new ArrayList<String>();
        List<PortalUserVo> voList = new ArrayList<PortalUserVo>();
        for (TPortalEntity e : portalList) {
            PortalUserVo vo = new PortalUserVo();
            try {
                PropertyUtils.copyProperties(vo, e);
                voList.add(vo);
            } catch (Exception e1) {
                throw new RuntimeException("copy属性失败", e1);
            }
        }
        TPortalUserEntity portalUserEntity = this.getPortalByUserName(username);
        if (portalUserEntity != null && StringUtils.isNotEmpty(portalUserEntity.getPortalData())) {
            String json = portalUserEntity.getPortalData();
            JSONArray jsonArr = (JSONArray) JSONArray.parse(json);
            for (int i = 0; i < jsonArr.size(); i++) {
                String id = jsonArr.getJSONObject(i).getString("id");
                idList.add(id);
            }
        }

        // 判断是否已添加
        for (PortalUserVo vo : voList) {
            if (idList.contains(vo.getId())) {
                vo.setStatus("1");
            }
        }

        return voList;
    }

    /**
     * 查询所有的待办配置
     */
    @Override
    public List<TPortalEntity> getAllPortalList(String userName) {
        String sql = "select t.portal_id from t_s_portal_role t join t_s_role r"
                + " on t.role_id = r.id join t_s_role_user ru on r.id = ru.roleid "
                + "join t_s_base_user u on ru.userid = u.id where u.username = ?";
        List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, userName);
        List<String> idList = new ArrayList<String>();
        for (Map<String, Object> map : list) {
            idList.add((String) map.get("portal_id"));
        }
        List<TPortalEntity> portalList = null;
        if (idList.size() > 0) {
        CriteriaQuery portalCq = new CriteriaQuery(TPortalEntity.class);
        portalCq.in("id", idList.toArray());
        portalCq.addOrder("sort", SortDirection.asc);
        portalCq.add();
            portalList = this.commonDao.getListByCriteriaQuery(portalCq, false);
        } else {
            portalList = new ArrayList<TPortalEntity>();
        }
        return portalList;
    }

    /**
     * 添加待办
     * 
     * @param userName
     * @param id
     */
    @Override
    public void addPortal(String userName, String ids) {
        TPortalUserEntity portalUserEntity = this.getPortalInfoByUserName(userName);
        TPortalUserEntity adminPrtalUserEntity = this.getPortalInfoByUserName("admin");
        CriteriaQuery cq = new CriteriaQuery(TPortalEntity.class);
        cq.in("id", ids.split(","));
        cq.add();
        List<TPortalEntity> portalList = commonDao.getListByCriteriaQuery(cq, false);
        int split = 3;
        if (portalUserEntity == null) {
            portalUserEntity = new TPortalUserEntity();
            portalUserEntity.setUserName(userName);
            if (adminPrtalUserEntity != null && adminPrtalUserEntity.getLayoutEntity() != null) {
                portalUserEntity.setLayoutEntity(adminPrtalUserEntity.getLayoutEntity());
            }
        } else {
            if (portalUserEntity.getLayoutEntity() != null) {
                split = portalUserEntity.getLayoutEntity().getSplit();
            }
        }
        String portalJson = generatePortalJson(portalList, split);
        portalUserEntity.setPortalData(portalJson);
        this.commonDao.saveOrUpdate(portalUserEntity);
    }

    /**
     * 生成json字符串.
     * 
     * @param portalList
     * @return
     */
    private static String generatePortalJson(List<TPortalEntity> portalList, int split) {
        JSONArray array = new JSONArray();
        int col = 0;
        for (int i = 0; i < portalList.size(); i++) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", portalList.get(i).getId());
            jsonObj.put("title", portalList.get(i).getTitle());
            jsonObj.put("url", portalList.get(i).getUrl());
            jsonObj.put("height", portalList.get(i).getHeight());
            jsonObj.put("colNo", col);
            col++;
            if (i == split - 1) {
                col = 0;
            }
            array.add(jsonObj);
        }
        return JSONArray.toJSONString(array);
    }

    /**
     * 修改布局.
     * 
     * @param portalUserEntity
     */
    @Override
    public void setLayout(String userName, String layoutId) {
        TPortalLayoutEntity layoutEntity = commonDao.get(TPortalLayoutEntity.class, layoutId);
        TPortalUserEntity portalUser = this.getPortalInfoByUserName(userName);
        TPortalUserEntity adminPortalUser = this.getPortalInfoByUserName("admin");
        if (portalUser == null) {
            portalUser = new TPortalUserEntity();
            portalUser.setUserName(userName);
        }
        portalUser.setLayoutEntity(layoutEntity);
        String portalData = portalUser.getPortalData();
        if (portalData == null && adminPortalUser.getPortalData() != null) {
            portalData = adminPortalUser.getPortalData();
        }
        if (portalData != null) {
            JSONArray jsonArray = JSONArray.parseArray(portalData);
            int col = 0;
            int split = layoutEntity.getSplit();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                jsonObj.put("colNo", col);
                col++;
                if (i == split - 1) {
                    col = 0;
                }
            }
            String arrayStr = jsonArray.toJSONString();
            portalUser.setPortalData(arrayStr);
        }
        this.commonDao.saveOrUpdate(portalUser);
    }

    /**
     * 保存待办修改
     */
    @Override
    public void savePortalSetting(PortalUserVo portalUserVo) {
        TPortalUserEntity portalUser = this.getPortalInfoByUserName(portalUserVo.getUserName());
        if (portalUser == null) {
            portalUser = new TPortalUserEntity();
            portalUser.setUserName(portalUserVo.getUserName());
            // 获取admin账号的布局配置
            TPortalUserEntity portalUserEntity = this.getPortalInfoByUserName("admin");
            portalUser.setLayoutEntity(portalUserEntity.getLayoutEntity());
        }
        String portalData = portalUserVo.getPortalData();
        JSONArray jsonArray = JSONArray.parseArray(portalData);
        JSONArray array = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            String id = json.getString("id");
            String col = json.getString("colNo");
            TPortalEntity entity = this.get(TPortalEntity.class, id);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", entity.getId());
            jsonObj.put("title", entity.getTitle());
            jsonObj.put("url", entity.getUrl());
            jsonObj.put("height", entity.getHeight());
            jsonObj.put("colNo", col);
            array.add(jsonObj);
        }
        portalUser.setPortalData(array.toJSONString());
        this.commonDao.saveOrUpdate(portalUser);
    }

    public static void main(String args[]) {
        List<TPortalEntity> portalList = new ArrayList<TPortalEntity>();
        TPortalEntity e = new TPortalEntity();
        e.setId("11111111111");
        e.setTitle("aaa");
        e.setHeight(44);
        e.setUrl("www.baidu.com");
        portalList.add(e);
        TPortalEntity e1 = new TPortalEntity();
        e1.setId("2222");
        e1.setTitle("bb");
        e1.setHeight(44);
        e1.setUrl("www.baidu.com");
        portalList.add(e1);
        TPortalEntity e2 = new TPortalEntity();
        e2.setId("333333");
        e2.setTitle("ccc");
        e2.setHeight(44);
        e2.setUrl("www.baidu.com");
        portalList.add(e2);
        TPortalEntity e3 = new TPortalEntity();
        e3.setId("4444444");
        e3.setTitle("ccdddddd");
        e3.setHeight(44);
        e3.setUrl("www.baidu.com");
        portalList.add(e3);
        TPortalEntity e4 = new TPortalEntity();
        e4.setId("55555555");
        e4.setTitle("eeeeeeee");
        e4.setHeight(44);
        e4.setUrl("www.baidu.com");
        portalList.add(e4);
        System.out.println(generatePortalJson(portalList, 3));

    }

    @Override
    public void setRolePortal(String roleId, String portals) {
        String sql = "delete from t_s_portal_role t where t.role_id=?";
        try {
            this.commonDao.executeSql(sql, roleId);//删除关联
            String[] portalIds = portals.split(",");
            List<TPortalRoleEntity> portalRoleList = new ArrayList<TPortalRoleEntity>();
            for (String portal : portalIds) {
                TPortalRoleEntity entity = new TPortalRoleEntity();
                entity.setPortalId(portal);
                entity.setRoleId(roleId);
                portalRoleList.add(entity);
            }
            if (portalRoleList.size() > 0) {
                commonDao.batchSave(portalRoleList);//批量保存关联
            }
        } catch (Exception e) {
            throw new BusinessException("设置portal与角色关联失败", e);
        }

    }

}