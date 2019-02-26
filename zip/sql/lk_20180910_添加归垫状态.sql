alter table t_b_pm_payfirst drop (GD_STATUS);
alter table t_b_pm_payfirst add GD_STATUS VARCHAR2(2) default 0;