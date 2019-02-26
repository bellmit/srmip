select count(1) from(select * from (select meeting.id,
         '1' as type,
         '您有个会议未参加，会议主题是'||meeting.meeting_title as content
    from t_o_meeting meeting
   where instr(meeting.attendees_id, '${param.userId}') > 0
     and meeting.begin_date > sysdate
  
  union all
  
  select hand.id,
         '2' as type,
          '您有个交班材料需要接收,交班人：'||hand.handover_name as content
    from t_o_handover hand
   where hand.RECEIVER_ID = '${param.userId}'
   
   union all
   
   select leave.id,
          '3' as type,
          '您有个请假信息未填写出差情况阅批单，请尽快办理' as content
     from t_o_travel_leave leave
     left join t_o_travel_report report
       on leave.id = report.t_o_id
    where report.id is null and leave.leave_id='${param.userId}'
    
    union all
    
    select schedule.id,
          '4' as type,
          '您今天有个日程安排，'||schedule.title as content
     from t_o_schedule schedule
    where schedule.user_id='${param.userId}'
    and schedule.begin_time <= sysdate
      and schedule.end_time >= sysdate
)tb 
)