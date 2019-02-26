select count(1) from(select * from (SELECT T.FILE_NUM_PREFIX || '20' || T.SEND_YEAR || T.SEND_NUM AS FILE_NUM,
       T.id,T.SEND_TITLE,T.SEND_TYPE_CODE,T.SECRITY_GRADE,T.UNDERTAKE_UNIT_NAME,T.CONTACT_NAME,T.CONTACT_ID,T.ARCHIVE_FLAG,
       R.id AS RID,T.CREATE_DATE
  FROM T_O_SEND_BILL T
  JOIN T_O_FLOW_RECEIVE_LOGS R
    ON T.ID = R.SEND_RECEIVE_ID
    AND R.OPERATE_STATUS = '${param.operateStatus}'
    AND R.RECEIVE_USERID = '${param.userId}'
    where 1=1
   <#if param.title ?exists>
   AND T.SEND_TITLE LIKE '%${param.title}%' 
   </#if>
   <#if param.sendTypeCode ?exists>
   AND T.SEND_TYPE_CODE = '${param.sendTypeCode}' 
   </#if>
 
 UNION ALL
 
 SELECT T.FILE_NUM_PREFIX || '20' || T.APPROVAL_YEAR ||
        T.APPLICATION_FILENO AS FILE_NUM,
        T.ID,T.TITLE AS SEND_TITLE,'13' AS SEND_TYPE_CODE,T.SECRITY_GRADE,T.UNDERTAKE_UNIT_NAME,T.CONTACT_NAME,T.CONTACT_ID,T.ARCHIVE_FLAG,
        R.id AS RID,T.CREATE_DATE
   FROM T_O_APPROVAL T
   JOIN T_O_FLOW_RECEIVE_LOGS R
     ON T.ID = R.SEND_RECEIVE_ID
     AND R.OPERATE_STATUS = '${param.operateStatus}'
     AND R.RECEIVE_USERID = '${param.userId}'
    where 1=1
   <#if param.title ?exists>
   AND T.TITLE LIKE '%${param.title}%' 
   </#if>
   <#if param.sendTypeCode ?exists>
   AND '13' = '${param.sendTypeCode}' 
   </#if>
  
  ORDER BY CREATE_DATE DESC
)tb 
)