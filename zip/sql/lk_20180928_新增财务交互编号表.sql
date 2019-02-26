create table T_B_CWJHBH
(
  ID        VARCHAR2(32),
  CWND      VARCHAR2(8),
  JHBH      VARCHAR2(8),
  RE_STATUS VARCHAR2(2),
  CREATE_DATE DATE default sysdate
)
tablespace SRMIP
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the columns 
comment on column T_B_CWJHBH.ID
  is '主键';
comment on column T_B_CWJHBH.CWND
  is '财务年度';
comment on column T_B_CWJHBH.JHBH
  is '交互编号';
comment on column T_B_CWJHBH.RE_STATUS
  is '0：财务未返回，1：财务已返回';