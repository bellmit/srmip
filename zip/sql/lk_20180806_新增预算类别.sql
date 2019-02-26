create table T_PM_BUDGET_CATEGORY
(
  ID             VARCHAR2(32),
  BUDGET_CATEGORY	VARCHAR2(4),
  CATEGORY_CODE VARCHAR2(32),
  CATEGORY_NAME VARCHAR2(32)
)
tablespace SRMIP
  pctfree 10
  initrans 1
  maxtrans 255;

commit;

