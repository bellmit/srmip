select count(1)
  from (select t.id,
       t.project_no projectNo,
       t.project_name projectName,
       t.project_manager projectManager,
       t.begin_date beginDate,
       t.end_date endDate,
       t.manager_phone managerPhone,
       p.project_type_name projectTypeName,
       d.departname devDepartName,
       (case nvl(n.status, 0)
         when 0 then
          '0'
         else
          '1'
       end) finishStatus,
       m.create_date,
       (case  nvl(a.status, 0)
         when 0 then
          '0'
         else
          '1'
       end) approvalstatus
  from t_pm_project t
  left join t_b_project_type p
    on t.project_type = p.id
  left join t_s_depart d
    on t.developer_depart = d.id
  join((select b.t_p_id, max(b.create_date) create_date
          from t_b_pm_declare b
         where b.depart_audited = '1'
         group by b.t_p_id)
union (select a.t_p_id, max(a.create_date) create_date
         from t_b_pm_declare_army_research a
        where a.depart_audited = '1'
        group by a.t_p_id)
union (select a.t_p_id, max(a.create_date) create_date
         from t_b_pm_declare_repair a
        where a.depart_audited = '1'
        group by a.t_p_id)
union (select a.t_p_id, max(a.create_date) create_date
         from t_b_pm_declare_technology a
        where a.depart_audited = '1'
        group by a.t_p_id)
union (select a.t_p_id, max(a.create_date) create_date
         from t_b_pm_declare_back a
        where a.depart_audited = '1'
        group by a.t_p_id)) m
    on t.id = m.t_p_id
  left join (select count(1) as status, s.project_id
               from t_er_review_project s
              group by s.project_id) n
    on t.id = n.project_id
   left join (select  count(1)  as status, m.project_id
          from t_o_approval_project_summary m
         join t_pm_project mt
      on mt.id = m.project_id
         group by m.project_id) a
     on t.id = a.project_id
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
 <#if param.finishStatus ?exists>
 and (case nvl(n.status, 0)
         when 0 then
          '0'
         else
          '1'
       end)=:param.finishStatus
 </#if>
 <#if param.approvalstatus ?exists>
 and (case  nvl(a.status, 0)
         when 0 then
          '0'
         else
          '1'
       end)=:param.approvalstatus
 </#if>
 </#if>
 order by finishStatus asc, create_date desc
)