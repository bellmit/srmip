alter table t_pm_funds_budget_addendum add ztnd varchar2(32);
alter table t_pm_funds_budget_addendum add zxh  VARCHAR2(32);
alter table t_pm_funds_budget_addendum add sxh  VARCHAR2(32);
alter table t_pm_funds_budget_addendum add jzrq date;
alter table t_pm_funds_budget_addendum add kjpzh varchar2(32);
alter table t_pm_funds_budget_addendum add xmdm varchar2(32);
alter table t_pm_funds_budget_addendum add mxdm varchar2(32);
alter table t_pm_funds_budget_addendum add zhy varchar2(50);
alter table t_pm_funds_budget_addendum add jd varchar2(2);
alter table t_pm_funds_budget_addendum add je varchar2(32);
alter table t_pm_funds_budget_addendum add kmdm varchar2(32);
alter table t_pm_funds_budget_addendum add kzlx varchar2(2);

comment on column t_pm_funds_budget_addendum.KZLX is '开支类型：1开支，2借贷，3还款';
