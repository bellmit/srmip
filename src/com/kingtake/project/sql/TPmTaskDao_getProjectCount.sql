select count(1)
  from (select t.id,
       t.project_no         projectNo,
       t.project_name       projectName,
       t.project_manager    projectManager,
       t.begin_date         beginDate,
       t.end_date           endDate,
       t.manager_phone      managerPhone,
       p.project_type_name  projectTypeName,
       d.departname         devDepartName,
       n.planCheckStatus    planCheckStatus,
       s.qualityCheckStatus qualityCheckStatus
  from t_pm_project t
  left join t_b_project_type p
    on t.project_type = p.id
  left join t_s_depart d
    on t.developer_depart = d.id
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
    <#if param? size gt 0 >
 where 1=1
 <#if param.projectNo ?exists>
 and t.project_no like '%'||:param.projectNo||'%'
 </#if>
 <#if param.projectName ?exists>
 and t.project_name like '%'||:param.projectName||'%'
 </#if>
 <#if param.beginDate_begin ?exists >
 and t.begin_date >= to_date('${param.beginDate_begin}','yyyy-mm-dd') 
 </#if>
 <#if param.beginDate_end ?exists >
 and t.begin_date <= to_date('${param.beginDate_end}','yyyy-mm-dd')
 </#if>
 <#if param.planCheckStatus ?exists>
 and n.planCheckStatus=:param.planCheckStatus
 </#if>
 <#if param.qualityCheckStatus ?exists>
 and s.qualityCheckStatus=:param.qualityCheckStatus
 </#if>
 </#if>
 order by planCheckStatus asc, qualityCheckStatus asc, n.create_date desc)