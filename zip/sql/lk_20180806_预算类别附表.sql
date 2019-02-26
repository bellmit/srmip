-- Create table
create table T_PM_BUDGET_CATEGORY_ATTR
(
  id                VARCHAR2(32) not null,
  category_code     VARCHAR2(32),
  category_code_dtl VARCHAR2(32),
  detail_name       VARCHAR2(128),
  detail_symbol     VARCHAR2(32),
  detail_desc       VARCHAR2(1024),
  pid               VARCHAR2(32),
  node              VARCHAR2(32)
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
  )
nologging;
-- Add comments to the columns 
comment on column T_PM_BUDGET_CATEGORY_ATTR.id
  is '主键id';
comment on column T_PM_BUDGET_CATEGORY_ATTR.category_code
  is '预算类型ID';
comment on column T_PM_BUDGET_CATEGORY_ATTR.category_code_dtl
  is '模板字段ID（记账凭证号）';
comment on column T_PM_BUDGET_CATEGORY_ATTR.detail_name
  is '模板字段名称';
comment on column T_PM_BUDGET_CATEGORY_ATTR.detail_symbol
  is '是否能加子节点标识';
comment on column T_PM_BUDGET_CATEGORY_ATTR.detail_desc
  is '详细描述说明';
comment on column T_PM_BUDGET_CATEGORY_ATTR.pid
  is '父id';
comment on column T_PM_BUDGET_CATEGORY_ATTR.node
  is '节点级别';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PM_BUDGET_CATEGORY_ATTR
  add constraint PID_ATTR primary key (ID)
  using index 
  tablespace SRMIP
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter index PID_ATTR nologging;
