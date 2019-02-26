-- Create table
create table T_PM_INCOME_REL_APPLY
(
  ID              VARCHAR2(36) not null,
  INCOME_ID       VARCHAR2(36),
  INCOME_APPLY_ID VARCHAR2(36)
)
tablespace SRMIP
  pctfree 10
  initrans 1
  maxtrans 255;