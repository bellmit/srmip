--到账信息表
create table T_PM_INCOME_CONTRACT_NODE_RELA(
    ID varchar(32),
    INCOME_APPLY_ID varchar(50),
    CONTRACT_NODE_ID varchar(50),
    CNODE_AMOUNT varchar(200),
    REMARK varchar(500),
    NODE_NAME varchar(500)
)
--到账分配到账信息关联表
create table  t_pm_income_rel_apply(
    ID varchar(36),
    INCOME_ID varchar(36),
    INCOME_APPLY_ID varchar(36)
)
create table T_PM_INCOME_APPLY_LIST(
    income_Apply_Id varchar(32),
    CERTIFICATE varchar(200),
    BALANCE number(15)
)
