--模拟财务审核
select t.*,t.rowid from t_pm_project t order by create_date desc; --项目基本信息表

select t2.cw_status,t2.aduit_status from t_pm_fpje t1  
	 left join T_PM_PROJECT_PLAN t2  on t1.jhwjid = T2.ID  
	 where t1.xmid = '2c948a826519d240016519e69b2c0019'
	 and t2.aduit_status is not null
	 group by  t2.cw_status,T2.aduit_status;
 
 select jhwjid from t_pm_fpje where xmid = '2c948a826519d240016519e69b2c0019'
 
 update T_PM_PROJECT_PLAN set aduit_status = 2 where ID = '2c948a826519d240016519e527280018'