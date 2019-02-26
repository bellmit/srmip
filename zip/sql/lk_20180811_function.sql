update t_pm_sidecatalog set url='tPmProjectFundsApprController.do?tPmProjectFundsAppr&projectId=${projectId}' where id='40288aec529b8d0301529bae202b000b';
update t_s_function set functionurl = 'tPmProjectPlanController.do?projectPlanList',functionname = '计划下达' where functionname = '计划下达查询及审核';
commit;