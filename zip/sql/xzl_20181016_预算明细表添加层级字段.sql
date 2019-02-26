alter table t_pm_contract_funds add NODE varchar2(32);
--垫支表添加预算ID
alter table t_b_pm_payfirst add YS_APPR_ID varchar2(32);
-- 经费分配表添加预算ID
alter table t_pm_fpje add YS_APPR_ID varchar2(32);
-- 经费分配表添加预算状态
alter table t_pm_fpje add YS_STATUS number(2);