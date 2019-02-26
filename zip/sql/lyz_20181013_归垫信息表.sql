
create table T_B_PM_GD_INFO
(
  ID VARCHAR2(32) not null primary key,
  PROJECT_ID VARCHAR2(32),
  PROJECT_NO VARCHAR2(20),
  REASON VARCHAR2(800),
  CREATE_BY VARCHAR2(50),
  CREATE_NAME VARCHAR2(50),
  CREATE_DATE DATE,
  JZ_ID VARCHAR2(32),
  GD_MONDY NUMBER(12,2),
  PAY_SUBJECTCODE VARCHAR2(50),
  CW_STATUS VARCHAR2(4),
  GD_STATUS VARCHAR2(4),
  YP_YEAR VARCHAR2(32),
  GDDZ_ID  varchar2(32)

);
comment on table T_B_PM_GD_INFO is '项目归垫信息表';
comment on column T_B_PM_GD_INFO.ID is '主键';
comment on column T_B_PM_GD_INFO.PROJECT_ID is '项目基_主键';
comment on column T_B_PM_GD_INFO.PROJECT_NO is '项目基_编号';
comment on column T_B_PM_GD_INFO.REASON is '垫支理由';
comment on column T_B_PM_GD_INFO.CREATE_BY is '创建人';
comment on column T_B_PM_GD_INFO.CREATE_NAME is '创建人姓名';
comment on column T_B_PM_GD_INFO.CREATE_DATE is '创建时间';
comment on column T_B_PM_GD_INFO.JZ_ID is '记账凭证号';
comment on column T_B_PM_GD_INFO.GD_MONDY is '归垫金额';
comment on column T_B_PM_GD_INFO.PAY_SUBJECTCODE is '垫支科目代码';
comment on column T_B_PM_GD_INFO.CW_STATUS is '从财务导入过来的数据.空为未提交，1是已审核，3已否决，4已提交';
comment on column T_B_PM_GD_INFO.GD_STATUS is '科研审核状态.空为未提交，1是已审核，3已否决';
comment on column T_B_PM_GD_INFO.YP_YEAR is '会计年度';
comment on column T_B_PM_GD_INFO.GDDZ_ID is '垫支ID';