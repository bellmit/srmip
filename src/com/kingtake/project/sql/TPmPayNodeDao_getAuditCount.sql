select count(1)
  from (select t.id,
               (case n.status
                 when 0 then
                  '1'
                 else
                  '0'
               end) finishStatus
          from t_pm_project t
          join (select sum(s.audit_flag) - count(1) as status,
                      s.t_p_id,
                      max(s.create_date) create_date
                 from t_pm_pay_node s
                group by s.t_p_id) n
            on t.id = n.t_p_id
         order by finishStatus asc, n.create_date desc)
 where finishStatus = '0'