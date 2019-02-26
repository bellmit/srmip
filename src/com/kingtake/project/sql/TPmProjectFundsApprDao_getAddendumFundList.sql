 select *
   from t_pm_funds_budget_addendum t
  where t.pid = '${tpId}'
  order by num asc nulls first
