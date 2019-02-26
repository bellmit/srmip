package com.kingtake.project.service.impl.incomeapply;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.incomeapply.TPmIncomeApplyServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmIncomeApplyService")
@Transactional
public class TPmIncomeApplyServiceImpl extends CommonServiceImpl implements TPmIncomeApplyServiceI, ProjectListServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmIncomeApplyEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmIncomeApplyEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmIncomeApplyEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmIncomeApplyEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmIncomeApplyEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmIncomeApplyEntity t) {
        return true;
    }

    @Override
    public void saveIncomeApply(TPmIncomeApplyEntity tPmIncomeApply, List<TPmIncomeContractNodeRelaEntity> nodeList) {
        try {
            if (StringUtils.isNotEmpty(tPmIncomeApply.getId())) {// 修改
                TPmIncomeApplyEntity t = this.commonDao.get(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeApply, t);
                this.commonDao.saveOrUpdate(t);
                CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractNodeRelaEntity.class);
                cq.eq("incomeApplyId", tPmIncomeApply.getId());
                cq.add();
                List<TPmIncomeContractNodeRelaEntity> relaList = this.commonDao.getListByCriteriaQuery(cq, false);
                this.commonDao.deleteAllEntitie(relaList);
                for (TPmIncomeContractNodeRelaEntity relaEntity : nodeList) {
                    relaEntity.setIncomeApplyId(tPmIncomeApply.getId());
                    this.commonDao.save(relaEntity);
                }
                
            } else {
            	//生成二维码
            	Long count = this.commonDao.getCountForJdbc("select count(*) from T_PM_INCOME_APPLY where PARENT_APPLY_ID is null ") + 1;
            	String barcodeNum = "0000000" + count;
            	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String currentYear = sdf.format(date);
            	String barcode = "KD" + currentYear + barcodeNum;
            	tPmIncomeApply.setBarcode(barcode);
                tPmIncomeApply.setAuditStatus("0");//审核状态初始化为0，未提交
                tPmIncomeApply.setYsStatus("0");//审核状态初始化为0，未提交
                this.commonDao.save(tPmIncomeApply);
                for (TPmIncomeContractNodeRelaEntity relaEntity : nodeList) {
                    relaEntity.setIncomeApplyId(tPmIncomeApply.getId());
                    this.commonDao.save(relaEntity);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("保存来款申请出错!", e);
        }
    }

    /**
     * 审核通过，自动分配到账
     */
    @Override
    public void doAudit(TPmIncomeApplyEntity tPmIncomeApply) {
        this.commonDao.saveOrUpdate(tPmIncomeApply);
        if(!StringUtils.isEmpty(tPmIncomeApply.getIncomeId())){
        	TPmIncomeEntity income = this.commonDao.get(TPmIncomeEntity.class, tPmIncomeApply.getIncomeId());//到账信息凭证号不会重复
            TPmIncomeAllotEntity allotEntity = new TPmIncomeAllotEntity();
            allotEntity.setIncome(income);
            allotEntity.setAmount(tPmIncomeApply.getIncomeAmount());
            TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, tPmIncomeApply.getProject().getId());
            allotEntity.setProjectId(project.getId());
            allotEntity.setProjectManagerId(project.getProjectManagerId());
            allotEntity.setProjectManager(project.getProjectManager());
            if (project.getDevDepart() != null) {
                allotEntity.setProjectMgrDept(project.getDevDepart().getDepartname());
            }
            allotEntity.setIncomeDept(tPmIncomeApply.getApplyDept());
            allotEntity.setProjectName(project.getProjectName());
            this.commonDao.save(allotEntity);
        }               
    }
    
    /**
     * 审核不通过，到账返还
     */
    @Override
    public void doReject(TPmIncomeApplyEntity tPmIncomeApply) {
        this.commonDao.saveOrUpdate(tPmIncomeApply);
        if(!StringUtils.isEmpty(tPmIncomeApply.getIncomeId())){
        	TPmIncomeEntity income = this.commonDao.get(TPmIncomeEntity.class, tPmIncomeApply.getIncomeId());//到账信息凭证号不会重复
            income.setBalance(income.getBalance().add(tPmIncomeApply.getApplyAmount()));
            this.commonDao.updateEntitie(income);
        }        
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
       /* CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class);
        cq.eq("checkUserId", user.getId());
//        cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        cq.eq("auditStatus", "1");//审核状态为1 已提交
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomeApplyEntity> incomeApplyList = this.commonDao.getListByCriteriaQuery(cq, false);*/
        
        
        String sql = "select sum(cnt) from (" + 
        		" select count(*) as cnt" + 
        		"        from t_pm_income_apply  a left join t_b_pm_invoice b on a.INVOICE_ID=b.ID" + 
        		"         where a.CHECK_USER_ID='"+user.getId()+"' and a.audit_status = '1'" + 
        		" union" + 
        		" select count( b.id) as cnt from t_Pm_Project_plan b where " + 
        		"        aduit_status = '1' and b.receive_UseIds like '%"+user.getId()+"%'" + 
        		"        and b.id in( select JHWJID from t_pm_fpje t inner join t_pm_project p on t.xmid=p.id where p.LXYJ = '1' and p.Scbz<>'1') " + 
        		")";     
        
        return  this.commonDao.getCountForJdbc(sql).intValue();
    }

    @Override
    public void deleteAddAttach(TPmIncomeApplyEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void deleteApply(TPmIncomeApplyEntity tPmIncomeApply) {
        tPmIncomeApply = this.commonDao.get(TPmIncomeApplyEntity.class,tPmIncomeApply.getId());
        String sql = " delete from t_pm_income_contract_node_rela t where t.income_apply_id=? ";
        this.commonDao.executeSql(sql, new Object[]{tPmIncomeApply.getId()});
        this.deleteAddAttach(tPmIncomeApply);
//        this.commonDao.delete(tPmIncomeApply);        
    }

    @Override
    public void doSubmit(TPmIncomeApplyEntity tPmIncomeApply) {
        tPmIncomeApply.setAuditStatus("1");//auditStatus 为1 已提交
        this.commonDao.updateEntitie(tPmIncomeApply);
        //认领提交的时候意见把账面可分配余余额减掉，提交审核通过的时候不需要再判断和更新
        //if(!StringUtils.isEmpty(tPmIncomeApply.getIncomeId())){
        //	TPmIncomeEntity incomeEntity = this.commonDao.get(TPmIncomeEntity.class,tPmIncomeApply.getIncomeId());
        //    if(incomeEntity.getBalance().compareTo(tPmIncomeApply.getApplyAmount())<0){
        //        throw new BusinessException("认领金额大于到账剩余金额，不能申请!");
        //    }
        //    incomeEntity.setBalance(incomeEntity.getBalance().subtract(tPmIncomeApply.getApplyAmount()));//将来款金额减下来
        //    this.commonDao.updateEntitie(incomeEntity);
        //}        
    }

}