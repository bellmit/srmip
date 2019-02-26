-- Create table
create table T_PM_BUDGET_FUNDS_REL
(
  ID                   VARCHAR2(32) not null,
  FUNDS_TYPE           VARCHAR2(32) not null,
  PROJECT_CATEGORY     VARCHAR2(32),
  BUDGET_CATEGORY      VARCHAR2(32) not null,
  BUDGET_CATEGORY_NAME VARCHAR2(32),
  INDIRECT_FEE_CALU_ID VARCHAR2(32),
  INDIRECT_FEE_CALU    VARCHAR2(32),
  UNIVERSITY_PROP      VARCHAR2(32),
  UNIT_PROP            VARCHAR2(32),
  DEV_UNIT_PROP        VARCHAR2(32),
  PROJECTGROUP_PROP    VARCHAR2(32)
  
)
tablespace SRMIP
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );