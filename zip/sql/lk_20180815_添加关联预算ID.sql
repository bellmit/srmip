--计划下达信息表		添加关联预算ID字段
alter table t_pm_income_plan add YS_APPR_ID VARCHAR2(32)
go
--到账信息表		添加关联预算ID字段
alter table t_pm_income_apply add YS_APPR_ID VARCHAR2(32)