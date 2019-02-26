package com.kingtake.project.service.ys.impl;

import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.ys.BudgetExportDaoService;
import com.kingtake.project.service.ys.BudgetExportService;
import com.kingtake.project.service.ys.YsService;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("budgetExportService")
@Transactional
public class BudgetExportServiceImpl  extends CommonServiceImpl implements BudgetExportService {

    @Autowired
    private BudgetExportDaoService budgetExportDaoService;

    @Autowired
    private YsService ysService;

    @Override
    public Map exportDkfpList(Map map) {
        Map result = new HashMap();
        List<TPmProjectEntity> project = this.commonDao.findByProperty(TPmProjectEntity.class,"id",map.get("projectId"));
        if(project != null && project.size() == 1){
            TPmProjectEntity entity = project.get(0);
            result.put("developer_depart",entity.getDevDepartStr());
            result.put("project_name",entity.getProjectName());
            result.put("project_manager",entity.getProjectManager());
            result.put("plan_contract_ref_no", entity.getPlanContractRefNo());
            result.put("subject_code",entity.getSubject_code());
            result.put("project_no",entity.getProjectNo());
            result.put("bz",entity.getBz());
        }

        List<Map> dkmxList = budgetExportDaoService.getDkmxList(map);
        result.put("dataList", dkmxList);

        int size = dkmxList.size();
        if(dkmxList != null &&  size> 0){
            String dybh = dkmxList.get(0).get("DYBH").toString().split(",")[1];
            Object checkName = dkmxList.get(0).get("CHECK_USER_REALNAME");
            Object year = dkmxList.get(0).get("APPLY_YEAR");
            Object dkrq = dkmxList.get(0).get("INCOME_TIME");

            String barcode = String.format("%05d", Integer.parseInt(dybh));
            result.put("barcode","DA"+year+barcode);
            result.put("checkName",checkName);
            result.put("dybh",dybh);
            result.put("dkrq",dkrq);

            int dkmxhj = 0;//到款合计
            int sjmxhj = 0;//收据合计
            for(int i=0;i<size;i++){
                Object dkmx = dkmxList.get(i).get("INCOME_AMOUNT");
                Object sjmx = dkmxList.get(i).get("INVOICE_AMOUNT");
                if(dkmx != null && dkmx != ""){
                    dkmxhj+=Integer.parseInt(dkmx.toString());
                }
                if(sjmx != null && sjmx != ""){
                    sjmxhj+=Integer.parseInt(sjmx.toString());
                }
            }
            result.put("dkmxhj",dkmxhj);
            result.put("sjmxhj",sjmxhj);
        }

        return result;
    }

    @Override
    public Map exportJhffList(Map map) {
        Map result = new HashMap();
        List<TPmProjectPlanEntity> jhff = this.commonDao.findByProperty(TPmProjectPlanEntity.class,"id",map.get("jhffId"));
        if(jhff != null && jhff.size() == 1){
            TPmProjectPlanEntity entity = jhff.get(0);
            result.put("funds_subject",entity.getFundsSubject());
            result.put("document_time",entity.getDocumentTime());
            result.put("document_no",entity.getDocumentNo());
            result.put("document_name", entity.getDocumentName());
            result.put("dybh",entity.getDybh());
        }

        List<Map> jhffList = budgetExportDaoService.getJhffList(map);
        result.put("dataList", jhffList);

        if(jhffList != null && jhffList.size() > 0){
            int amount = 0;
            for(int i=0;i<jhffList.size();i++){
                int je = jhffList.get(i).get("plan_amount") == null ? 0 : Integer.parseInt(jhffList.get(i).get("plan_amount").toString());
                amount+=je;
            }
            result.put("hj", amount);
        }

        return result;
    }

    @Override
    public Map exportJfdzList(Map map) {
        Map result = new HashMap();
        List<Map> jfdzList = budgetExportDaoService.getJfdzList(map);
        result.put("dataList", jfdzList);

        if(jfdzList.size() > 0){
            Map data = jfdzList.get(0);
            result.put("dzkmdm",data.get("project_no"));
            result.put("dzrq",data.get("dzrq"));
            result.put("dybh",data.get("dybh"));
            result.put("hj",data.get("pay_funds"));
        }

        return result;
    }
}
