select * 
  from (select r.*,rownum rn
          from (select * from (
         select * from (select d.id          as departId,
       d.departname,
       b.id,
       b.realname,
       b.userName,
       u.sex,
       u.mobilephone,
       u.officephone,
       d.departsort,
       u.sort
  from (select r.*,rownum departsort from (SELECT  t.id,t.departname, rownum FROM t_s_depart t START WITH t.parentdepartid is null CONNECT BY PRIOR ID = parentdepartid order by t.sort)r)  d
  join t_s_user_org o
    on d.id = o.org_id
  join  t_s_user  u
    on o.user_id = u.id
  join t_s_base_user b
      on b.id = u.id) m 
   <#if param? size gt 0 >
    where 1=1
   <#if param.realName ?exists>
   AND m.realname LIKE '%${param.realName}%' 
   </#if>
   <#if param.userName ?exists>
   AND m.userName LIKE '%${param.userName}%' 
   </#if>
   </#if>
   order by m.departsort, m.sort
)tb 
 )r where rownum<= ${end})n
 where n.rn >=${start}