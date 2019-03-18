package com.kingtake.project.service.impl.incomeplan;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.kingtake.common.util.ReadExcel;
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

import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.incomeplan.TPmIncomePlanServiceI;
import com.kingtake.project.service.incomeplan.TPmProjectPlanServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmProjectPlanService")
@Transactional
public class TPmProjectPlanServiceImpl extends CommonServiceImpl implements TPmProjectPlanServiceI, ProjectListServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmProjectPlanEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmProjectPlanEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmProjectPlanEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param t
     * @return
     */
    @Override
    public boolean doAddSql(TPmProjectPlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param t
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmProjectPlanEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param t
     * @return
     */
    @Override
    public boolean doDelSql(TPmProjectPlanEntity t) {
        return true;
    }
    
    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }
    
    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmIncomePlanEntity.class);
        cq.eq("checkUserId", user.getId());
//        cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        cq.eq("approvalStatus", "1");//审核状态为1 已提交
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomePlanEntity> incomePlanList = this.commonDao.getListByCriteriaQuery(cq, false);
        return incomePlanList.size();
    }

    /**
     * 导入计划下达项目信息
     * @param fileName
     */
    @Override
    public void importExcelProject(String fileName) {
        String uploadPath = System.getProperty("catalina.home")+"\\webapps\\excelFile\\";
        String filePath = uploadPath + fileName;

        ReadExcel obj = new ReadExcel();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        File file = new File(filePath);
        List excelList = obj.readExcel(file);

        List<Map> dataList = new ArrayList<Map>();
        Map data = null;
        String[] arr = {"","","","","","","","","","",""};
        int size = excelList.size();
        for (int i = 0; i < size; i++) {
            List list = (List) excelList.get(i);
            data = new HashMap();
            int size2 = list.size();
            for (int j = 0; j < size2; j++) {
                //System.out.print(list.get(j));
                data.put(arr[i], list.get(j));
            }
            //System.out.println();
            dataList.add(data);
        }

        batchAddProject(dataList);

    }

    private void batchAddProject(List<Map> dataList){
        int size = dataList.size();
        for(int i = 0;i < size;i++){
            Map data = dataList.get(i);

            String uuid = UUID.randomUUID().toString().replace("-", "");



        }
    }
}