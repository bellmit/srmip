SELECT t.*, level
  FROM t_pm_contract_funds t
 WHERE t.T_P_ID = '${tpId}'
 START WITH t.PARENT IS NULL
CONNECT BY PRIOR ID = PARENT
 ORDER BY NUM asc Nulls first