package com.kingtake.project.service.appraisalmeeting;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;

public interface TBAppraisalMeetingServiceI extends CommonService {

    @Override
    public <T> void delete(T entity);

    @Override
    public <T> Serializable save(T entity);

    @Override
    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TBAppraisalMeetingEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TBAppraisalMeetingEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TBAppraisalMeetingEntity t);

    public boolean selectMember(String meetingId, String apprProjectId, String checkMemberIds);

    public boolean deleteMember(String meetingId, String memberId, String departId);

    public boolean addMember(String meetingId, String memberType, String workUnit, String memberNames);

    /**
     * 提交审批
     * 
     * @param req
     * @return
     */
    public AjaxJson doSubmit(HttpServletRequest req);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBAppraisalMeetingEntity t);

    /**
     * 保存鉴定会信息
     * @param tBAppraisalMeeting
     */
    public void saveMeeting(TBAppraisalMeetingEntity tBAppraisalMeeting);
}
