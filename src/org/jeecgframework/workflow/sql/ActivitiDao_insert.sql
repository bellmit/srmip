insert into t_s_basebus(
id,
userid,
prjstateid,
busconfigid,
<#if mp.businessCode ?exists >
 business_code, 
</#if>
<#if mp.businessName ?exists >
business_name,
</#if>
createtime) values(
'${mp.id}',
'${mp.userid}',
'${mp.prjstateid}',
'${mp.busconfigid}',
<#if mp.businessCode ?exists >
 '${mp.businessCode}', 
</#if>
<#if mp.businessName ?exists >
'${mp.businessName}',
</#if>
sysdate)