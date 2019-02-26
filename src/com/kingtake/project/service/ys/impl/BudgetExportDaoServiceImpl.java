package com.kingtake.project.service.ys.impl;
import com.fr.report.script.function.MAP;
import com.kingtake.project.service.ys.BudgetExportDaoService;
import jodd.util.StringUtil;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("budgetExportDaoService")
@Transactional
public class BudgetExportDaoServiceImpl extends CommonServiceImpl implements BudgetExportDaoService {


    @Override
    public List getDkmxList(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT B.APPLY_YEAR,B.VOUCHER_NO,B.INCOME_REMARK,B.INCOME_AMOUNT, ");
        sql.append(" B.DYBH,B.INCOME_TIME,B.CHECK_USER_REALNAME,C.INVOICE_YEAR, ");
        sql.append(" C.INVOICE_NUM1,C.MEMO,C.INVOICE_AMOUNT FROM t_pm_project A  ");
        sql.append(" left join t_pm_income_apply B on A.GLXM=B.T_P_ID  ");
        sql.append(" LEFT JOIN T_B_PM_INVOICE C on B.INVOICE_ID = C.ID ");
        sql.append(" WHERE 1=1 ");

        Object projectId = map.get("projectId");
        Object dkmxId = map.get("dkmxId");
        if(projectId != null && projectId != ""){
            sql.append(" AND A.ID='"+ projectId +"' ");
        }
        if(dkmxId != null && dkmxId != ""){
            sql.append(" AND B.ID='"+ dkmxId +"' ");
        }

        return this.findForJdbc(sql.toString());
    }

    @Override
    public List getJhffList(Map map) {
        StringBuffer sb = new StringBuffer();
        /*sb.append(" select b.project_no,b.project_name,b.developer_depart,b.project_manager, ");
        sb.append(" e.je as plan_amount,d.pay_subjectcode,a.payfirst_funds,c.dybh ");
        sb.append(" from T_PM_INCOME_PLAN a  ");
        sb.append(" left join t_pm_project b on a.t_p_id=b.id ");
        sb.append(" left join t_pm_project_plan c on a.project_plan_id = c.id ");
        sb.append(" left join t_b_pm_payfirst d on d.id = a.payfirst_id ");
        sb.append(" left join t_pm_fpje e on e.jhwjid = c.id ");
        sb.append(" where 1=1 ");*/

        sb.append(" SELECT C.project_no,C.project_name,d.departname,");
        sb.append(" C.project_manager,b.je as plan_amount,T.dybh ");
        sb.append(" FROM t_pm_project_plan T join t_pm_fpje B on B.JHWJID = T.id ");
        sb.append(" join t_pm_project C on B.XMID = C.id  ");
        sb.append(" join t_s_depart d on C.developer_depart = d.id where 1=1 ");

        Object projectId = map.get("projectId");
        Object jhffId = map.get("jhffId");
        if(projectId != null && projectId != ""){
            sb.append(" AND C.id='"+ projectId +"' ");
        }
        if(jhffId != null && jhffId != ""){
            sb.append(" AND T.id='"+ jhffId +"' ");
        }

        return this.findForJdbc(sb.toString());
    }

    @Override
    public List getJfdzList(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select 1 as xh,a.project_no,b.pay_subjectcode,a.project_name, ");
        sb.append(" c.departname,a.project_manager,b.pay_funds,b.dybh, ");
        sb.append(" to_char(b.create_date,'yyyy-MM-dd HH:mm:ss') as dzrq ");
        sb.append(" from t_pm_project a left join t_b_pm_payfirst b on A.GLXM=B.PROJECT_ID ");
        sb.append(" left join t_s_depart c on a.developer_depart = c.id ");
        sb.append(" WHERE 1=1 ");

        Object projectId = map.get("projectId");
        Object jfdzId = map.get("jfdzId");
        if(projectId != null && projectId != ""){
            sb.append(" AND a.id='"+ projectId +"' ");
        }
        if(jfdzId != null && jfdzId != ""){
            sb.append(" AND b.id='"+ jfdzId +"' ");
        }

        return this.findForJdbc(sb.toString());
    }
}
