package com.kingtake.base.service.impl.rtx;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.Base64Encoder;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rtx.RTXSvrApi;
import rtx.RTXSvrApiContainer;

import com.kingtake.base.entity.rtx.TBSyncUserDeptTempEntity;
import com.kingtake.base.service.rtx.TBSyncUserDeptTempServiceI;

@Service("tBSyncUserDeptTempService")
@Transactional
public class TBSyncUserDeptTempServiceImpl extends CommonServiceImpl implements TBSyncUserDeptTempServiceI {

    @Override
    public void addTempUpdateDepart(TSDepart depart) {
        try {
            if (StringUtils.isNotEmpty(depart.getId())) {
                depart = this.commonDao.get(TSDepart.class, depart.getId());
                TBSyncUserDeptTempEntity tmpEntity = new TBSyncUserDeptTempEntity();
                if (StringUtils.isEmpty(depart.getOrgCode())) {
                    throw new BusinessException("组织机构编码不能为空！");
                }
                tmpEntity.setDeptId(depart.getOrgCode());
                tmpEntity.setDeptName(depart.getDepartname());
                tmpEntity.setSyncType("1");//更新部门
                tmpEntity.setSyncStatus("0");//同步状态为初始化
                if (depart.getTSPDepart() != null) {
                    TSDepart pDepart = this.commonDao.get(TSDepart.class, depart.getTSPDepart().getId());
                    if (pDepart != null) {
                        tmpEntity.setParentDeptId(pDepart.getOrgCode());
                    }
                }
                tmpEntity.setCreateTime(new Date());
                this.commonDao.save(tmpEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTempDeleteDepart(TSDepart depart) {
        try {
            if (StringUtils.isNotEmpty(depart.getId())) {
                depart = this.commonDao.get(TSDepart.class, depart.getId());
                saveDepartTemp(depart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDepartTemp(TSDepart depart) {
        TBSyncUserDeptTempEntity tmpEntity = new TBSyncUserDeptTempEntity();
        tmpEntity.setDeptId(depart.getOrgCode());
        tmpEntity.setDeptName(depart.getDepartname());
        tmpEntity.setSyncType("2");//删除部门
        tmpEntity.setSyncStatus("0");//同步状态为初始化
        tmpEntity.setCreateTime(new Date());
        this.commonDao.save(tmpEntity);
    }

    /**
     * type 1 新增，2 编辑
     */
    @Override
    public void addTempUpdateUser(TSUser user) {
        try {
            if (StringUtils.isNotEmpty(user.getId())) {
                user = this.commonDao.get(TSUser.class, user.getId());
                List<TSUserOrg> userOrgList = user.getUserOrgList();
                TSUserOrg userOrg = userOrgList.get(0);//取第一个单位
                TSDepart depart = this.commonDao.get(TSDepart.class, userOrg.getTsDepart().getId());
                TBSyncUserDeptTempEntity tmpEntity = new TBSyncUserDeptTempEntity();
                tmpEntity.setDeptId(depart.getOrgCode());
                tmpEntity.setDeptName(depart.getDepartname());
                tmpEntity.setSyncType("3");//更新用户
                tmpEntity.setSyncStatus("0");//同步状态为初始化
                tmpEntity.setUserName(user.getUserName());
                tmpEntity.setRealName(user.getRealName());
                String pwd = PasswordUtil.decrypt(user.getPassword(), user.getUserName(), PasswordUtil.getStaticSalt());
                String encodePwd = Base64Encoder.encodeData(pwd);
                tmpEntity.setPwd(encodePwd);//解密之后的密码
                if (StringUtils.isNotEmpty(user.getEmail())) {
                    tmpEntity.setEmail(user.getEmail());
                }
                if (StringUtils.isNotEmpty(user.getSex())) {
                    tmpEntity.setGender(user.getSex());
                }
                if (StringUtils.isNotEmpty(user.getMobilePhone())) {
                    tmpEntity.setMobile(user.getMobilePhone());
                }
                if (StringUtils.isNotEmpty(user.getOfficePhone())) {
                    tmpEntity.setPhone(user.getOfficePhone());
                }
                if (depart.getTSPDepart() != null) {
                    tmpEntity.setParentDeptId(depart.getTSPDepart().getOrgCode());
                }
                tmpEntity.setCreateTime(new Date());
                this.commonDao.save(tmpEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTempDeleteUser(TSUser user) {
        try {
            if (StringUtils.isNotEmpty(user.getId())) {
                user = this.commonDao.get(TSUser.class, user.getId());
                TBSyncUserDeptTempEntity tmpEntity = new TBSyncUserDeptTempEntity();
                tmpEntity.setUserName(user.getUserName());
                tmpEntity.setRealName(user.getRealName());
                tmpEntity.setSyncType("4");//删除部门
                tmpEntity.setSyncStatus("0");//同步状态为初始化
                tmpEntity.setCreateTime(new Date());
                this.commonDao.save(tmpEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void syncRtxUserDept() {
        RTXSvrApi rtxApi = RTXSvrApiContainer.getRtxApiInstance();
        if (rtxApi == null) {
            return;
        }
        CriteriaQuery cq = new CriteriaQuery(TBSyncUserDeptTempEntity.class);
        cq.eq("syncStatus", "0");
        cq.addOrder("createTime", SortDirection.asc);
        cq.add();
        List<TBSyncUserDeptTempEntity> tmpList = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TBSyncUserDeptTempEntity tmp : tmpList) {
            syncToRtx(tmp, rtxApi);
        }
    }

    private void syncToRtx(TBSyncUserDeptTempEntity tmp, RTXSvrApi rtxApi) {
        try {

            if ("1".equals(tmp.getSyncType())) {
                String deptName = rtxApi.GetDeptName(tmp.getDeptId());
                if (StringUtils.isNotEmpty(deptName)) {
                    String parentId = null;
                    if(StringUtils.isEmpty(tmp.getParentDeptId())){
                        parentId = "0";
                    } else {
                        parentId = tmp.getParentDeptId();
                    }
                    rtxApi.setDept(tmp.getDeptId(), "", tmp.getDeptName(), parentId);
                } else {
                    String pdeptName = rtxApi.GetDeptName(tmp.getParentDeptId());
                    if (StringUtils.isEmpty(pdeptName)) {
                        throw new BusinessException("父单位不存在！");
                    }
                    rtxApi.addDept(tmp.getDeptId(), "", tmp.getDeptName(), tmp.getParentDeptId());
                }
            } else if ("2".equals(tmp.getSyncType())) {
                String deptName = rtxApi.GetDeptName(tmp.getDeptId());
                if (StringUtils.isNotEmpty(deptName)) {
                    rtxApi.deleteDept(tmp.getDeptId(), "1");
                }
            } else if ("3".equals(tmp.getSyncType())) {
                boolean flag = rtxApi.userIsExist(tmp.getUserName());
                if (flag) {//存在则更新
                    rtxApi.SetUserSimpleInfoEx(tmp.getUserName(), tmp.getDeptId(), tmp.getRealName(), tmp.getEmail(),
                            tmp.getGender(), tmp.getMobile(), tmp.getPhone(), Base64Encoder.decodeData(tmp.getPwd()));
                } else {//不存在则新增
                    rtxApi.addUser(tmp.getUserName(), tmp.getDeptId(), tmp.getRealName(), tmp.getPwd());
                    rtxApi.SetUserSimpleInfo(tmp.getUserName(), tmp.getRealName(), tmp.getEmail(), tmp.getGender(),
                            tmp.getMobile(), tmp.getPhone(), Base64Encoder.decodeData(tmp.getPwd()));
                }
            } else if ("4".equals(tmp.getSyncType())) {
                boolean existFlag = rtxApi.userIsExist(tmp.getUserName());
                if (existFlag) {
                    rtxApi.deleteUser(tmp.getUserName());
                }
            }
            tmp.setSyncStatus("1");
        } catch (Exception e) {
            e.printStackTrace();
            tmp.setSyncStatus("2");//失败
            String msg = e.getMessage();
            if (msg != null && msg.length() > 200) {
                msg = msg.substring(0, 200);
            }
            tmp.setSyncMsg(msg);//错误信息
        }
        this.commonDao.updateEntitie(tmp);
    }

}