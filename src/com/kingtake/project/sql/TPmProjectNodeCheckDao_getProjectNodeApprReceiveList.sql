select * 
  from (select r.*,rownum rn
          from (select * from (SELECT APPR.ID                    AS APPR_ID,
       APPR.CONTRACT_NAME,
       '1' PROJECT_NODE_TYPE ,
       APPR.CONTRACT_NODE_ID,
       APPR.PROJECT_NAME,
       NODE.NODE_NAME,
       NODE.IN_OUT_CONTRACTID,
       CONTRACT.T_P_ID            AS PROJECTID,
       SEND.OPERATE_USERNAME,
       SEND.OPERATE_DATE,
       EXT.LABEL,
       EXT.HANDLER_TYPE,
       RECEIVE.ID,
       RECEIVE.REBUT_FLAG,
       RECEIVE.SUGGESTION_CODE,
       RECEIVE.SUGGESTION_CONTENT,
       RECEIVE.OPERATE_STATUS,
       RECEIVE.OPERATE_TIME
  FROM T_PM_CONTRACT_NODE        NODE,
       T_PM_CONTRACT_NODE_CHECK  APPR,
       T_PM_INCOME_CONTRACT_APPR CONTRACT,
       T_PM_APPR_SEND_LOGS       SEND,
       T_PM_APPR_RECEIVE_LOGS    RECEIVE,
       T_PM_FORM_CATEGORY        EXT
 WHERE NODE.ID = APPR.CONTRACT_NODE_ID
   AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID
   AND APPR.ID = SEND.APPR_ID
   AND SEND.ID = RECEIVE.SEND_ID
   AND RECEIVE.SUGGESTION_TYPE = EXT.ID
   AND RECEIVE.RECEIVE_USERID = '${param.userId}'
   
   
   UNION ALL
   
   SELECT APPR.ID                    AS APPR_ID,
       TASK.TASK_TITLE CONTRACT_NAME ,
       '2' PROJECT_NODE_TYPE ,
       APPR.ID CONTRACT_NODE_ID,
       PROJ.PROJECT_NAME,
       APPR.TASK_CONTENT AS NODE_NAME,
       TASK.ID AS IN_OUT_CONTRACTID,
       TASK.T_P_ID            AS PROJECTID,
       SEND.OPERATE_USERNAME,
       SEND.OPERATE_DATE,
       EXT.LABEL,
       EXT.HANDLER_TYPE,
       RECEIVE.ID,
       RECEIVE.REBUT_FLAG,
       RECEIVE.SUGGESTION_CODE,
       RECEIVE.SUGGESTION_CONTENT,
       RECEIVE.OPERATE_STATUS,
       RECEIVE.OPERATE_TIME
  FROM 
       T_PM_TASK_NODE  APPR,
       T_PM_TASK TASK,
       T_PM_PROJECT PROJ,
       T_PM_APPR_SEND_LOGS       SEND,
       T_PM_APPR_RECEIVE_LOGS    RECEIVE,
       T_PM_FORM_CATEGORY        EXT
 WHERE TASK.ID = APPR.T_P_ID
   AND TASK.T_P_ID = PROJ.ID
   AND APPR.ID = SEND.APPR_ID
   AND SEND.ID = RECEIVE.SEND_ID
   AND RECEIVE.SUGGESTION_TYPE = EXT.ID
   AND RECEIVE.RECEIVE_USERID = '${param.userId}'
   
)tb 
 <#if param? size gt 0 >
 where 1=1 
 <#if param.projectName ?exists>
 and tb.project_name like '%'||:param.projectName||'%' 
 </#if>
 <#if param.operateStatus ?exists>
 AND tb.OPERATE_STATUS = :param.operateStatus 
 </#if>
 <#if param.contractName ?exists>
 AND tb.CONTRACT_NAME LIKE '%'||:param.contractName||'%' 
 </#if>
 <#if param.nodeName ?exists>
 AND tb.NODE_NAME LIKE '%'||:param.nodeName||'%'
 </#if>
 </#if>
 ORDER BY tb.OPERATE_DATE desc, tb.OPERATE_TIME desc
 )r where rownum<= ${end})n
 where n.rn >=${start}