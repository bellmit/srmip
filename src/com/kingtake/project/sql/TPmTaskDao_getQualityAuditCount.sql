select count(1)
  from (select t.id,
               n.planCheckStatus    planCheckStatus,
               s.qualityCheckStatus qualityCheckStatus
          from t_pm_project t
          join (select m.t_p_id,
                      (case p.count - q.count
                        when 0 then
                         '1'
                        else
                         '0'
                      end) planCheckStatus,
                      p.create_date
                 from (select t.t_p_id,
                              count(1) as count,
                              max(t.create_date) as create_date
                         from t_pm_task_node t
                        group by t.t_p_id) p
                 left join (select t.t_p_id, count(1) as count
                             from t_pm_task_node t
                            where t.plan_check_flag = 'Y'
                            group by t.t_p_id) q
                   on p.t_p_id = q.t_p_id
                 join t_pm_task m
                   on m.id = p.t_p_id) n
            on t.id = n.t_p_id
          join (select m.t_p_id,
                      (case p.count - q.count
                        when 0 then
                         '1'
                        else
                         '0'
                      end) qualityCheckStatus,
                      p.create_date
                 from (select t.t_p_id,
                              count(1) as count,
                              max(t.create_date) as create_date
                         from t_pm_task_node t
                        group by t.t_p_id) p
                 left join (select t.t_p_id, count(1) as count
                             from t_pm_task_node t
                            where t.quality_check_flag = 'Y'
                            group by t.t_p_id) q
                   on p.t_p_id = q.t_p_id
                 join t_pm_task m
                   on m.id = p.t_p_id) s
            on t.id = s.t_p_id
         order by planCheckStatus    asc,
                  qualityCheckStatus asc,
                  n.create_date      desc) z
 where z.qualityCheckStatus = '0'