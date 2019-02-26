--椤圭洰璐熻矗浜哄彉鏇翠俊鎭〃
select * from t_b_pm_project_change
go
--  项目
select * from t_pm_project
go
--  到账信息表
select * from t_pm_income_apply
go
--  项目组成员
select * from t_pm_project_member
go
--  进账合同审批
select * from t_pm_income_contract_appr
go
--  进账合同申请表
select * from T_PM_INCOME_CONTRACT_APPR
go
--  审批发送日志表
select * from T_PM_APPR_SEND_LOGS
go
--  审批接收日志表
select * from T_PM_APPR_RECEIVE_LOGS
go
--  表单类别信息表
select * from T_PM_FORM_CATEGORY
go
--  项目经费表
select * from T_PM_BUDGET_FUNDS_REL
go
--  预算类型
select * from T_PM_BUDGET_CATEGORY
go
--  预算模型表
select * from T_PM_BUDGET_CATEGORY_ATTR order by CATEGORY_CODE,CATEGORY_CODE_DTL 
go
--  预算主表
select * from t_pm_project_funds_appr
go
-- 预算明细
select * from t_pm_contract_funds
go
-- 余额
select * from t_pm_project_balance
go
-- 计划下达主表
select * from t_pm_project_plan
go
-- 经费分配表
select * from t_pm_fpje
go
-- 垫支表
select * from t_b_pm_payfirst


