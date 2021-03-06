package com.kingtake.project.service.impl.appraisalmeeting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalMemberEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartMEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;
import com.kingtake.project.service.appraisalmeeting.TBAppraisalMeetingServiceI;

@Service("tBAppraisalMeetingService")
@Transactional
public class TBAppraisalMeetingServiceImpl extends CommonServiceImpl implements TBAppraisalMeetingServiceI {

    @Autowired
    private TOMessageServiceI messageService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //???????????????????????????sql??????
        this.doDelSql((TBAppraisalMeetingEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //???????????????????????????sql??????
        this.doAddSql((TBAppraisalMeetingEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //???????????????????????????sql??????
        this.doUpdateSql((TBAppraisalMeetingEntity) entity);
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBAppraisalMeetingEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBAppraisalMeetingEntity t) {
        return true;
    }

    /**
     * ????????????-sql??????-????????????
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBAppraisalMeetingEntity t) {
        return true;
    }

    /**
     * ??????sql????????????
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBAppraisalMeetingEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{meeting_date}", String.valueOf(t.getMeetingDate()));
        sql = sql.replace("#{meeting_addr}", String.valueOf(t.getMeetingAddr()));
        sql = sql.replace("#{contact_name}", String.valueOf(t.getContactName()));
        sql = sql.replace("#{contact_phone}", String.valueOf(t.getContactPhone()));
        sql = sql.replace("#{register_time}", String.valueOf(t.getRegisterTime()));
        sql = sql.replace("#{register_addr}", String.valueOf(t.getRegisterAddr()));
        sql = sql.replace("#{host_departid}", String.valueOf(t.getHostDepartid()));
        sql = sql.replace("#{host_departname}", String.valueOf(t.getHostDepartname()));
        sql = sql.replace("#{notice_num}", String.valueOf(t.getNoticeNum()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public boolean selectMember(String meetingId, String applyId, String checkMemberIds) {
        TBAppraisalApplyEntity apply = commonDao.get(TBAppraisalApplyEntity.class,  applyId);
        Map<String, List<TBAppraisalMemberEntity>> checkMap = new HashMap<String, List<TBAppraisalMemberEntity>>();
        Map<String, List<TBAppraisalMemberEntity>> uncheckMap = new HashMap<String, List<TBAppraisalMemberEntity>>();
        if (apply !=null) {
            List<TBAppraisalApplyMemRelaEntity> applyMemReal = super.findByProperty(
                    TBAppraisalApplyMemRelaEntity.class, "tbId", apply.getId());
            TBAppraisalMemberEntity member = null;

            for (TBAppraisalApplyMemRelaEntity real : applyMemReal) {
                member = super.get(TBAppraisalMemberEntity.class, real.getMemberId());
                String departName = member.getWorkUnit();
                if (checkMemberIds.indexOf(member.getId()) != -1) {
                    if (checkMap.containsKey(departName)) {
                        checkMap.get(departName).add(member);
                    } else {
                        List<TBAppraisalMemberEntity> l = new ArrayList<TBAppraisalMemberEntity>();
                        l.add(member);
                        checkMap.put(departName, l);
                    }
                } else {
                    if (uncheckMap.containsKey(departName)) {
                        uncheckMap.get(departName).add(member);
                    } else {
                        List<TBAppraisalMemberEntity> l = new ArrayList<TBAppraisalMemberEntity>();
                        l.add(member);
                        uncheckMap.put(departName, l);
                    }
                }
            }
            this.resetMeetingMember(meetingId);
            this.saveDepartAndMember(meetingId, ApprovalConstant.MEETING_UNATTEND_EXPERT, uncheckMap);//?????????????????????
            this.saveDepartAndMember(meetingId, ApprovalConstant.MEETING_ATTEND_EXPERT, checkMap);//??????????????????
        }
        return false;
    }

    public void saveDepartAndMember(String meetingId, String memberType, Map<String, List<TBAppraisalMemberEntity>> map) {
        Iterator it = map.entrySet().iterator();
        TBAppraisalMeetingDepartEntity depart = null;
        List<TBAppraisalMemberEntity> memberList = null;
        while (it.hasNext()) {
            Map.Entry<String, List<TBAppraisalMemberEntity>> entry = (Entry<String, List<TBAppraisalMemberEntity>>) it
                    .next();
            depart = new TBAppraisalMeetingDepartEntity();
            depart.setDepartname(entry.getKey());
            depart.setMeetingId(meetingId);
            depart.setMemberType(memberType);
            super.save(depart);
            memberList = entry.getValue();
            TBAppraisalMeetingDepartMEntity dmReal = null;
            for (TBAppraisalMemberEntity member : memberList) {
                dmReal = new TBAppraisalMeetingDepartMEntity();
                dmReal.setDepartId(depart.getId());
                dmReal.setFlag(SrmipConstants.NO);
                dmReal.setMemberId(member.getId());
                super.save(dmReal);
            }
        }
    }

    /**
     * ????????????
     * 
     * @param meetingId
     */
    public void resetMeetingMember(String meetingId) {
        CriteriaQuery dcq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class);
        dcq.eq("meetingId", meetingId);
        dcq.or(Restrictions.eq("memberType", ApprovalConstant.MEETING_ATTEND_EXPERT),
                Restrictions.eq("memberType", ApprovalConstant.MEETING_UNATTEND_EXPERT));
        dcq.add();
        List<TBAppraisalMeetingDepartEntity> dlist = super.getListByCriteriaQuery(dcq, false);
        if (dlist.size() > 0) {
            for (TBAppraisalMeetingDepartEntity depart : dlist) {
                CriteriaQuery rcq = new CriteriaQuery(TBAppraisalMeetingDepartMEntity.class);
                rcq.eq("departId", depart.getId());
                rcq.eq("flag", SrmipConstants.NO);
                rcq.add();
                List<TBAppraisalMeetingDepartMEntity> rlist = super.getListByCriteriaQuery(rcq, false);
                if (rlist.size() > 0) {
                    for (TBAppraisalMeetingDepartMEntity dmRel : rlist) {
                        super.delete(dmRel);
                    }
                }
                this.deleteNoMemberDepart(depart);
            }
        }
    }

    /**
     * ????????????
     */
    @Override
    public boolean deleteMember(String meetingId, String memberId, String departId) {
        TBAppraisalMeetingDepartEntity depart = super.get(TBAppraisalMeetingDepartEntity.class, departId);
        CriteriaQuery rcq = new CriteriaQuery(TBAppraisalMeetingDepartMEntity.class);
        rcq.eq("departId", departId);
        rcq.eq("memberId", memberId);
        rcq.add();
        List<TBAppraisalMeetingDepartMEntity> rlist = super.getListByCriteriaQuery(rcq, false);
        if (rlist.size() > 0) {
            TBAppraisalMeetingDepartMEntity dmRel = rlist.get(0);
            if (ApprovalConstant.MEETING_ATTEND_EXPERT.equals(depart.getMemberType())) { //??????????????????????????????
                if (SrmipConstants.NO.equals(dmRel.getFlag())) {//??????????????????????????????????????????????????????????????????
                    CriteriaQuery dcq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class);
                    dcq.eq("memberType", ApprovalConstant.MEETING_UNATTEND_EXPERT);
                    dcq.eq("departname", depart.getDepartname());
                    dcq.add();
                    List<TBAppraisalMeetingDepartEntity> dlist = super.getListByCriteriaQuery(dcq, false);
                    TBAppraisalMeetingDepartEntity unattendDepart = null;
                    if (dlist.size() > 0) {
                        unattendDepart = dlist.get(0);
                    } else {
                        unattendDepart = new TBAppraisalMeetingDepartEntity();
                        unattendDepart.setDepartname(depart.getDepartname());
                        unattendDepart.setMeetingId(meetingId);
                        unattendDepart.setMemberType(ApprovalConstant.MEETING_UNATTEND_EXPERT);
                        super.save(unattendDepart);
                    }
                    dmRel.setDepartId(unattendDepart.getId());
                    dmRel.setMemberId(memberId);
                    super.updateEntitie(dmRel);
                    deleteNoMemberDepart(depart);
                    return true;
                } else {
                    super.delete(dmRel);
                    deleteNoMemberDepart(depart);
                    super.deleteEntityById(TBAppraisalMemberEntity.class, memberId);
                    return true;
                }
            } else if (ApprovalConstant.MEETING_UNATTEND_MEMBER.equals(depart.getMemberType())) {//???????????????????????????
                super.delete(dmRel);
                deleteNoMemberDepart(depart);
                super.deleteEntityById(TBAppraisalMemberEntity.class, memberId);
                return true;
            }
        }
        return false;
    }

    /**
     * ?????????????????????
     * 
     * @param depart
     */
    public void deleteNoMemberDepart(TBAppraisalMeetingDepartEntity depart) {
        CriteriaQuery rcq = new CriteriaQuery(TBAppraisalMeetingDepartMEntity.class);
        rcq.eq("departId", depart.getId());
        rcq.add();
        List<TBAppraisalMeetingDepartMEntity> rlist = super.getListByCriteriaQuery(rcq, false);
        if (rlist.size() == 0) {
            super.delete(depart);
        }
    }

    @Override
    public boolean addMember(String meetingId, String memberType, String workUnit, String memberNames) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class);
        cq.eq("meetingId", meetingId);
        cq.eq("departname", workUnit);
        cq.eq("memberType", memberType);
        cq.add();
        List<TBAppraisalMeetingDepartEntity> dlist = this.getListByCriteriaQuery(cq, false);
        TBAppraisalMeetingDepartEntity depart = null;
        if (dlist.size() > 0) {
            depart = dlist.get(0);
        } else {
            depart = new TBAppraisalMeetingDepartEntity();
            depart.setDepartname(workUnit);
            depart.setMemberType(memberType);
            depart.setMeetingId(meetingId);
            super.save(depart);
        }
        String[] names = memberNames.split(",");
        TBAppraisalMemberEntity member = null;
        TBAppraisalMeetingDepartMEntity dmrel = null;
        for (String name : names) {
            member = new TBAppraisalMemberEntity();
            member.setMemberName(name);
            member.setWorkUnit(workUnit);
            super.save(member);
            dmrel = new TBAppraisalMeetingDepartMEntity();
            dmrel.setDepartId(depart.getId());
            dmrel.setMemberId(member.getId());
            dmrel.setFlag(SrmipConstants.YES);
            super.save(dmrel);
        }
        return true;
    }

    @Override
    public AjaxJson doSubmit(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = req.getParameter("id");
        String operator = req.getParameter("operator");
        String userId = req.getParameter("userId");
        String departId = req.getParameter("departId");
        if (StringUtil.isNotEmpty(id)) {
            TBAppraisalMeetingEntity meeting = commonDao.get(TBAppraisalMeetingEntity.class, id);
            meeting.setMeetingStatus(ApprovalConstant.APPRSTATUS_SEND);//????????????
            meeting.setCheckUserid(userId);
            meeting.setCheckUsername(operator);
            meeting.setCheckDepartid(departId);
            meeting.setCheckDate(new Date());
            commonDao.updateEntitie(meeting);
            TSUser user = ResourceUtil.getSessionUserName();
            messageService.sendMessage(meeting.getCheckUserid(), "????????????????????????", "?????????",
                    "??????????????????????????????????????????????????????->????????????->???????????????????????????",
                    user.getId());
        }
        return j;
    }

    @Override
    public void deleteAddAttach(TBAppraisalMeetingEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.delAttachementByBid(t.getDepartAccessoryCode());
        this.commonDao.delete(t); 
    }

    /**
     * ?????????????????????
     */
    @Override
    public void saveMeeting(TBAppraisalMeetingEntity tBAppraisalMeeting) {
        if (StringUtil.isEmpty(tBAppraisalMeeting.getId())) {
            tBAppraisalMeeting.setMeetingStatus(ApprovalConstant.APPRSTATUS_UNSEND);//???????????????
            TBAppraisalApplyAttachedEntity applyAttached = tBAppraisalMeeting.getApplyAttached();
            this.commonDao.save(applyAttached);
            commonDao.save(tBAppraisalMeeting);
        } else {
            TBAppraisalMeetingEntity t = commonDao.get(TBAppraisalMeetingEntity.class, tBAppraisalMeeting.getId());
            TBAppraisalApplyAttachedEntity attached = this.commonDao.findUniqueByProperty(TBAppraisalApplyAttachedEntity.class, "applyId", t.getApplyId());
            try {
                if(attached!=null){
                    MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalMeeting.getApplyAttached(), attached);
                }else{
                    attached = tBAppraisalMeeting.getApplyAttached();
                }
                MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalMeeting, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
            commonDao.saveOrUpdate(attached);
            commonDao.saveOrUpdate(t);
        }
    }

}