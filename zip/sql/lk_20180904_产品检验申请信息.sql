-- Create table
create table T_PM_CHECK_APPLY
(
  ID               VARCHAR2(32),
  APPLY_NO         VARCHAR2(32),
  PRODUCT_NAME     VARCHAR2(32),
  PRODUCT_CODE     VARCHAR2(32),
  PRODUCT_NO       VARCHAR2(32),
  CHECK_TIME       DATE,
  CHECK_RESULT     VARCHAR2(32),
  QUALIFICATION_NO VARCHAR2(32),
  CHECK_RECORD     VARCHAR2(32),
  ATTACHMENT_CODE  VARCHAR2(32),
  STATUS           VARCHAR2(32),
  CREATE_BY        VARCHAR2(32),
  CREATE_NAME      VARCHAR2(32),
  CREATE_DATE      DATE,
  UPDATE_BY        VARCHAR2(32),
  UPDATE_NAME      VARCHAR2(32),
  UPDATE_DATE      DATE,
  T_P_ID           VARCHAR2(32)
)
tablespace SRMIP
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table T_PM_CHECK_APPLY
  is '产品检验申请信息';
-- Add comments to the columns 
comment on column T_PM_CHECK_APPLY.ID
  is '主键';
comment on column T_PM_CHECK_APPLY.APPLY_NO
  is '产品检验单编号';
comment on column T_PM_CHECK_APPLY.PRODUCT_NAME
  is '项目名称';
comment on column T_PM_CHECK_APPLY.PRODUCT_CODE
  is '产品代码';
comment on column T_PM_CHECK_APPLY.PRODUCT_NO
  is '产品编号';
comment on column T_PM_CHECK_APPLY.CHECK_TIME
  is '验收时间';
comment on column T_PM_CHECK_APPLY.CHECK_RESULT
  is '验收结果';
comment on column T_PM_CHECK_APPLY.QUALIFICATION_NO
  is '合格证书编号';
comment on column T_PM_CHECK_APPLY.CHECK_RECORD
  is '检验记录';
comment on column T_PM_CHECK_APPLY.ATTACHMENT_CODE
  is '附件编码';
comment on column T_PM_CHECK_APPLY.STATUS
  is '状态';
comment on column T_PM_CHECK_APPLY.CREATE_BY
  is '创建人英文名';
comment on column T_PM_CHECK_APPLY.CREATE_NAME
  is '创建人中文名';
comment on column T_PM_CHECK_APPLY.CREATE_DATE
  is '创建人日期';
comment on column T_PM_CHECK_APPLY.UPDATE_BY
  is '更新人英文名';
comment on column T_PM_CHECK_APPLY.UPDATE_NAME
  is '更新人中文名';
comment on column T_PM_CHECK_APPLY.UPDATE_DATE
  is '更新时间';
comment on column T_PM_CHECK_APPLY.T_P_ID
  is '项目ID';
