SELECT COUNT(1) 
FROM T_PM_APPR_RECEIVE_LOGS 
WHERE TABLE_NAME = 't_pm_income_contract_appr'
   AND RECEIVE_USERID = '${userId}'
   AND OPERATE_STATUS = '${operateStatus}'
   AND VALID_FLAG = '${validFlag}'