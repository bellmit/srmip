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
       (case nvl(n.status,0)
         when 0 then
          '1'
         else
          '0'
       end) finishStatus
  from t_pm_project t
  left join t_b_project_type p
    on t.project_type = p.id
  left join t_s_depart d
    on t.developer_depart = d.id
  join (select sum(s.audit_flag) - count(1) as status,
               s.t_p_id,
               max(s.create_date) create_date
          from t_pm_pay_node s
         group by s.t_p_id) n
    on t.id = n.t_p_id
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
 and (case nvl(n.status,0)
         when 0 then
          '1'
         else
          '0'
       end)=:param.finishStatus
 </#if>
 </#if>
 order by finishStatus asc, n.create_date desc)