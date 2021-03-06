package com.kingtake.project.service.impl.appraisal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalMemberEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.appraisal.TBAppraisalApplyServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBAppraisalApplyService")
@Transactional
public class TBAppraisalApplyServiceImpl extends CommonServiceImpl implements TBAppraisalApplyServiceI,
        ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TOMessageServiceI tOMessageService;
    
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //???????????????????????????sql??????
        this.doDelSql((TBAppraisalApplyEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //???????????????????????????sql??????
        this.doAddSql((TBAppraisalApplyEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //???????????????????????????sql??????
        this.doUpdateSql((TBAppraisalApplyEntity) entity);
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBAppraisalApplyEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBAppraisalApplyEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBAppraisalApplyEntity t) {
        return true;
    }

    @Override
    public void saveApplyAndMember(TBAppraisalApplyEntity tBAppraisalApply, String memberStr, String sendIds) {
        Boolean basicNumChangeFlag = false;
        if (StringUtil.isNotEmpty(tBAppraisalApply.getId())) {
            TBAppraisalApplyEntity t = this.get(TBAppraisalApplyEntity.class, tBAppraisalApply.getId());
            if (!tBAppraisalApply.getBasicNum().equals(t.getBasicNum())) {
                //??????????????????????????????????????????
                basicNumChangeFlag = true;
            }
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalApply, t);
            } catch (Exception e) {
                throw new BusinessException("??????????????????", e);
            }
            this.updateEntitie(t);
        } else {
            // ????????????
            tBAppraisalApply.setAuditStatus(ApprovalConstant.APPLYSTATUS_UNSEND);//????????????????????????
            // ??????????????????????????????
            basicNumChangeFlag = true;
            this.save(tBAppraisalApply);
        }
        if (basicNumChangeFlag) {
            //???????????????????????????
            String basicNum = tBAppraisalApply.getBasicNum().replace("-", "");
            String currentNum = basicNum.substring(basicNum.length() - 7, basicNum.length());
            PrimaryGenerater gen = PrimaryGenerater.getInstance();
            gen.updateApprApprovalNext(currentNum);
        }
        //?????????????????????
        List<TBAppraisalApplyMemRelaEntity> memberRelaList = this.findByProperty(TBAppraisalApplyMemRelaEntity.class,
                "tbId", tBAppraisalApply.getId());
        if (memberRelaList.size() > 0) {
            //?????????????????????
            for (TBAppraisalApplyMemRelaEntity relaEntity : memberRelaList) {
                this.deleteEntityById(TBAppraisalMemberEntity.class, relaEntity.getMemberId());
            }
            //?????????????????????
            this.deleteAllEntitie(memberRelaList);
        }

        //json?????????
        if (StringUtils.isNotEmpty(memberStr)) {
            JSONArray array = JSONArray.parseArray(memberStr);
            for (int i = 0; i < array.size(); i++) {
                TBAppraisalMemberEntity memberEntity = new TBAppraisalMemberEntity();
                JSONObject json = (JSONObject) array.get(i);
                memberEntity.setMemberName(json.getString("memberName"));
                memberEntity.setMemberPosition(json.getString("memberPosition"));
                memberEntity.setMemberProfession(json.getString("memberProfession"));
                memberEntity.setWorkUnit(json.getString("workUnit"));
                memberEntity.setMemo(json.getString("memo"));
                this.commonDao.save(memberEntity);
                TBAppraisalApplyMemRelaEntity memRela = new TBAppraisalApplyMemRelaEntity();
                memRela.setMemberId(memberEntity.getId());
                memRela.setTbId(tBAppraisalApply.getId());
                this.commonDao.save(memRela);
            }
        }
    }

    /**
     * ??????sql????????????
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBAppraisalApplyEntity t) {
        sql = sql.replace("#{finish_contact_phone}", String.valueOf(t.getFinishContactPhone()));
        sql = sql.replace("#{appraisal_contact_name}", String.valueOf(t.getAppraisalContactName()));
        sql = sql.replace("#{appraisal_contact_phone}", String.valueOf(t.getAppraisalContactPhone()));
        sql = sql.replace("#{register_code}", String.valueOf(t.getRegisterCode()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{finish_unit}", String.valueOf(t.getFinishUnit()));
        sql = sql.replace("#{begin_time}", String.valueOf(t.getBeginTime()));
        sql = sql.replace("#{end_time}", String.valueOf(t.getEndTime()));
        sql = sql.replace("#{archive_num}", String.valueOf(t.getArchiveNum()));
        sql = sql.replace("#{finish_contact_id}", String.valueOf(t.getFinishContactId()));
        sql = sql.replace("#{finish_contact_name}", String.valueOf(t.getFinishContactName()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY);
    }

    @Override
    public AjaxJson submitApply(TBAppraisalApplyEntity entity, String sendIds) {
        AjaxJson j = new AjaxJson();
        try {
            entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
            commonDao.updateEntitie(entity);
            TSUser user = ResourceUtil.getSessionUserName();
            tOMessageService.sendMessage(sendIds, "???????????????????????????", "??????????????????",
                    "?????????????????????????????????????????????????????????->????????????->??????????????????????????????", user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("????????????????????????");
        }
        j.setMsg("????????????????????????");
        return j;
    }

    @Override
    public AjaxJson saveAttached(TBAppraisalApplyAttachedEntity attached) {
        AjaxJson j = new AjaxJson();
        String message = "???????????????";
        //String role = req.getParameter("role");
        try {
            if (StringUtil.isNotEmpty(attached.getId())) {
                TBAppraisalApplyAttachedEntity t = commonDao
                        .get(TBAppraisalApplyAttachedEntity.class, attached.getId());
                MyBeanUtils.copyBeanNotNull2Bean(attached, t);
                commonDao.updateEntitie(t);
                //if (role.equals("depart")) {//?????????????????????
                if (StringUtil.isNotEmpty(attached.getApplyId())) {
                    TBAppraisalApplyEntity apply = commonDao.get(TBAppraisalApplyEntity.class, attached.getApplyId());
                    apply.setAuditStatus(ApprovalConstant.APPLYSTATUS_APPROVAL_VIEW);
                    commonDao.updateEntitie(apply);
                }
                // }
                j.setObj(t.getId());
            } else {
                // if (role.equals("research")) {//??????????????????????????????
                if (StringUtil.isNotEmpty(attached.getApplyId())) {
                    TBAppraisalApplyEntity apply = commonDao.get(TBAppraisalApplyEntity.class, attached.getApplyId());
                    apply.setAuditStatus(ApprovalConstant.APPLYSTATUS_APPROVAL_VIEW);
                    commonDao.updateEntitie(apply);

                    commonDao.save(attached);
                    j.setObj(attached.getId());
                }
            }
        } catch (Exception e) {
            message = "???????????????";
            j.setSuccess(false);
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    @Override
    public void deleteAddAttach(TBAppraisalApplyEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //?????????????????????
            TBAppraisalApplyEntity t = commonDao.get(TBAppraisalApplyEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //?????????????????????
                t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
            }

        }
        
    }

    @Override
    public void doBack(String id) {
        TBAppraisalApplyEntity t = commonDao.get(TBAppraisalApplyEntity.class, id);
        //?????????????????????????????????
        t.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TBAppraisalApplyEntity entity = this.get(TBAppraisalApplyEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TBAppraisalApplyEntity t = commonDao.get(TBAppraisalApplyEntity.class, id);
        if(t!=null){
            appName = t.getAchievementName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TBAppraisalApplyEntity entity = this.get(TBAppraisalApplyEntity.class, id);
        if (entity != null) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }
}