insert 
into 
t_b_log
      (id,OPERATETIME,
       <#if tbLog.TSUser ?exists>
       USERID,
       </#if>
       USERNAME,IP,LOGLEVEL,OPERATETYPE,LOGCONTENT,OLD_JSON,NEW_JSON,FORM_URL,BROWSER,OP_OBJECT_NAME) 
values
      (:tbLog.id,
       :tbLog.operatetime,
       <#if tbLog.TSUser ?exists>
       :tbLog.TSUser.id,
       </#if>
       :tbLog.username,
       :tbLog.ip,
       :tbLog.loglevel,
       :tbLog.operatetype,
       :tbLog.logcontent,
       :tbLog.oldJson,
       :tbLog.newJson,
       :tbLog.formUrl,
       :tbLog.browser,
       :tbLog.opObjectName
      )