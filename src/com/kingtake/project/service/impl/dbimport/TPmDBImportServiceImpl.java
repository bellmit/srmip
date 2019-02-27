package com.kingtake.project.service.impl.dbimport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.kingtake.common.util.StringUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.ZipUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.project.entity.dbimport.TPmDBImportEntity;
import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.entity.funds.TPmProjectBalanceEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.service.dbimport.TPmDBImportServiceI;

@Service("tPmDBImportService")
@Transactional
public class TPmDBImportServiceImpl extends CommonServiceImpl implements TPmDBImportServiceI {

    private static final Logger logger = Logger.getLogger(TPmDBImportServiceImpl.class);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param
     * @return
     */
    public boolean doAddSql(TPmDBImportEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param
     * @return
     */
    public boolean doUpdateSql(TPmDBImportEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param
     * @return
     */
    public boolean doDelSql(TPmDBImportEntity t) {
        return true;
    }

    @Override
    public void importDB(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            //取得当前上传文件的文件名称  
            String myFileName = file.getOriginalFilename();
            String extend = myFileName.substring(myFileName.lastIndexOf("."));
            String timeStr = DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
            String baseDir = new File(request.getSession().getServletContext().getRealPath("/")).getParent()
                    + File.separator + "upload" + File.separator;
            String realPath = baseDir + "db" + File.separator + timeStr + File.separator;// 文件的硬盘真实路径
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();// 创建根目录
            }
            File zipFile = new File(realPath + myFileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(zipFile);
                IOUtils.copy(file.getInputStream(), fos);
            } catch (Exception e) {
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ZipUtils.extract(zipFile.getPath(), realPath);//解压文件
            importDB(realPath);//导入数据
            copyAttachment(realPath, baseDir);//拷贝附件
            TPmDBImportEntity dbImport = new TPmDBImportEntity();
            dbImport.setFileName(myFileName);
            dbImport.setFilePath("/upload/db/" + timeStr + "/" + myFileName);
            dbImport.setImpUserName(user.getRealName());
            dbImport.setImpUserId(user.getId());
            dbImport.setImpTime(new Date());
            this.commonDao.save(dbImport);
        }

    }

    /**
     * 导入数据到数据库
     * 
     * @param realPath
     */
    private void importDB(String realPath) {
        String[] jsonFileArray = { "t_pm_project.json", "t_b_appraisal_project.json", "t_b_pm_declare.json",
                "t_b_pm_declare_army_research.json", "t_b_pm_declare_back.json", "t_b_pm_declare_repair.json",
                "t_b_pm_declare_technology.json", "t_b_pm_invoice.json", "t_b_pm_open_subject.json",
                "t_b_pm_report_req.json", "t_b_result_reward.json", "t_b_result_reward_finish_user.json",
                "t_b_result_spread.json", "t_pm_abate_payfirst.json", "t_pm_check_report.json",
                "t_pm_contract_funds.json", "t_pm_funds_budget_addendum.json", "t_pm_plan_funds.json",
                "t_pm_project_funds_appr.json", "t_pm_project_funds_appr.json", "t_pm_project_funds_appr.json",
                "t_s_attachment.json", "t_s_files.json" };
        //String[] jsonFileArray = { "t_pm_project.json", "t_pm_project_member.json" };
        File file = new File(realPath + File.separator + "EXPORT" + File.separator);
        File[] fileArray = file.listFiles();
        Map<String, File> fileMap = new HashMap<String, File>();
        for (File tmp : fileArray) {
            fileMap.put(tmp.getName(), tmp);
        }

        for (String fn : jsonFileArray) {
            File tmp = fileMap.get(fn);
            if (tmp != null) {
                insertDB(tmp);
            }
        }

    }

    /**
     * 插入数据库
     * 
     * @param file
     */
    private void insertDB(File file) {
        try {
            String jsonStr = FileUtils.readFileToString(file);
            JSONObject json = JSONObject.parseObject(jsonStr);
            String tableName = (String) json.get("tableName");
            List<Map<String, Object>> colList = getDateColumns(tableName);
            int count = json.getIntValue("count");
            if (count > 0) {
                JSONArray jsonArray = json.getJSONArray("rows");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject dataJson = (JSONObject) jsonArray.get(i);
                    String id = dataJson.getString("ID");
                    Map<String, String> columnMap = new HashMap<String, String>();
                    for (Map<String, Object> map : colList) {
                        columnMap.put((String) map.get("COLUMN_NAME"), (String) map.get("DATA_TYPE"));
                    }
                    String countSql = "select count(1) from " + tableName + " where id='" + id + "'";
                    Long dataCount = this.commonDao.getCountForJdbc(countSql);
                    if (dataCount == 0) {
                        Map<String, Object> insertSqlMap = generateInsertSql(columnMap, tableName, dataJson);
                        String insertSql = (String) insertSqlMap.get("sql");
                        Object[] params = (Object[]) insertSqlMap.get("param");
                        this.commonDao.executeSql(insertSql, params);
                    } else {
                        Map<String, Object> updateSqlMap = generateUpdateSql(columnMap, tableName, dataJson);
                        String updateSql = (String) updateSqlMap.get("sql");
                        Object[] params = (Object[]) updateSqlMap.get("param");
                        this.commonDao.executeSql(updateSql, params);
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("插入json数据失败！", e);
        }
    }

    /**
     * 获取日期字段列表
     * 
     * @param tableName
     * @return
     */
    private List<Map<String, Object>> getDateColumns(String tableName){
        String sql = "select COLUMN_NAME, DATA_TYPE from user_tab_columns where table_name = UPPER('" + tableName
                + "') ";
        List<Map<String, Object>> dataList = this.commonDao.findForJdbc(sql);
        return dataList;
    }
    

    /**
     * 生成更新语句
     * 
     * @param
     * @param tableName
     * @return
     */
    private Map<String, Object> generateUpdateSql(Map<String, String> columnMap, String tableName, JSONObject json) {
        StringBuffer updateSql = new StringBuffer();
        String columnStr = "";
        Set<String> keySet = json.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            columnStr = columnStr + key + "=? " + ",";
            String dataType = columnMap.get(key);
            String param = json.getString(key);
            if ("DATE".equals(dataType)) {
                Date date = null;
                if (StringUtils.isNotEmpty(param)) {
                    try {
                        date = DateUtils.parseDate(param, "yyyy/MM/dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                params.add(date);
            } else {
                params.add(param);
            }
        }
        if (columnStr.length() > 1) {
            columnStr = columnStr.substring(0, columnStr.length() - 1);
        }
        String id = json.getString("ID");
        updateSql.append("update ").append(tableName + " set ").append(columnStr).append(" where id='").append(id)
                .append("'");
        Map<String, Object> sqlMap = new HashMap<String, Object>();
        sqlMap.put("sql", updateSql.toString());
        sqlMap.put("param", params.toArray());
        return sqlMap;
    }

    /**
     * 生成insert语句
     * 
     * @param columnMap
     * @param tableName
     * @param json
     * @return
     */
    private Map<String, Object> generateInsertSql(Map<String, String> columnMap, String tableName, JSONObject json) {
        StringBuffer insertSql = new StringBuffer();
        Set<String> keySet = json.keySet();
        String columnStr = "";
        String qStr = "";
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            columnStr = columnStr + key + ",";
            qStr = qStr + "?" + ",";
            String dataType = columnMap.get(key);
            String param = json.getString(key);
            if ("DATE".equals(dataType)) {
                Date date = null;
                if (StringUtils.isNotEmpty(param)) {
                    try {
                        date = DateUtils.parseDate(param, "yyyy/MM/dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                params.add(date);
            } else {
                params.add(param);
            }
        }
        if (columnStr.length() > 1) {
            columnStr = columnStr.substring(0, columnStr.length() - 1);
            qStr = qStr.substring(0, qStr.length() - 1);
        }

        insertSql.append("insert into ").append(tableName + " (").append(columnStr).append(")").append(" values(")
                .append(qStr).append(")");
        Map<String, Object> sqlMap = new HashMap<String, Object>();
        sqlMap.put("sql", insertSql.toString());
        sqlMap.put("param", params.toArray());
        return sqlMap;
    }

    /**
     * 拷贝附件文件
     */
    private void copyAttachment(String realPath, String baseDir) {
        try {
            File fileDir = new File(realPath + File.separator + "EXPORT" + File.separator + "FILES" + File.separator);
            File[] attFiles = fileDir.listFiles();
            if (attFiles.length > 0) {
                File uploadDir = new File(baseDir + File.separator + "files" + File.separator);
                for (File tmp : attFiles) {
                    FileUtils.copyFileToDirectory(tmp, uploadDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delImport(TPmDBImportEntity dbImport, HttpServletRequest request) {
        this.commonDao.delete(dbImport);
        String parentPath = new File(request.getSession().getServletContext().getRealPath("/")).getParent();
        File dbFile = new File(parentPath + File.separator + dbImport.getFilePath());
        File dir = dbFile.getParentFile();
        dir.delete();//删除文件夹
    }
    
    @Override
	public List exportXmlToYszb() {
		String sql = "select a.id as guid,b.project_no as xmdm,'' as xh, a.create_date as bzrq,"
                + " a.funds_type as yslx,a.funds_category as fundscategory,'0' as dqzt "
                + " from t_pm_project_funds_appr a "
				+ " left join t_pm_project b on a.t_p_id=b.id where a.FINISH_FLAG is not null and a.FINISH_FLAG <> '0' and a.cw_status <=0";
    	List list = this.findForJdbc(sql);
    	if(list!=null && list.size()>0){
    		for(int a=0;a<list.size();a++){
    			Map map = (Map) list.get(a);
    			Object yslx = map.get("yslx");
                Object fundscategory = map.get("fundscategory");
                if("1".equals(fundscategory)){
                    //总预算
                    if("1".equals(yslx)){
                        map.put("YSLX","项目总预算");
                    }else if ("7".equals(yslx)) {
                        map.put("YSLX","调整总预算");
                    }
                }else if("2".equals(fundscategory)){
                    //年度预算
                    if("6".equals(yslx)) {
                        map.put("YSLX","到款预算");
                    }else if("7".equals(yslx)) {
                        map.put("YSLX","调整预算");
                    }
                }
                map.remove("fundscategory");
    		}
    	}
		return list;
	}

	@Override
	public List exportXmlToYsmx() {
		String sql = " select distinct k.zbid,'' as guid,k.cw_status as cw_status,k.xmdm, "
                +" '' as xh,'0' as mxdm,'合计' as mxmc,'' as jldw,0 as dj,0 as sl, "
                +" sum(je) as je,'' as bz from ( "
                +" select b.cw_status,a.id as guid,b.id as zbid,c.project_no as xmdm, "
                +" '' as xh,CASE WHEN (a.num) is null THEN '0' ELSE a.num END as mxdm, "
                +" a.content as mxmc,a.unit as jldw,a.price as dj,a.amount as sl,a.money as je, '' as bz "
                +" from t_pm_contract_funds a  left join t_pm_project_funds_appr b on a.T_APPR_ID = b.id "
                +" left join t_pm_project c on b.t_p_id = c.id "
                +" where b.FINISH_FLAG is not null and b.FINISH_FLAG <> '0' "
                +" and b.cw_status <=0 and length(a.num) =2) k group by k.zbid,k.xmdm,k.cw_status "
                +" union all "
                +" select b.id as zbid,a.id as guid,b.cw_status,c.project_no as xmdm, "
                +" '' as xh,CASE WHEN (a.num) is null THEN '0' ELSE a.num END as mxdm, "
                +" a.content as mxmc,a.unit as jldw,a.price as dj,a.amount as sl,a.money as je, '' as bz "
                +" from t_pm_contract_funds a  left join t_pm_project_funds_appr b on a.T_APPR_ID = b.id "
                +" left join t_pm_project c on b.t_p_id = c.id "
                +" where b.FINISH_FLAG is not null and b.FINISH_FLAG <> '0' and b.cw_status <=0 ";
    	List list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public List exportXmlToDkzb(String year) {
		 String sql = "select a.id as guid,'' as id ,d.project_no as xmbm,to_char(a.create_date, 'yyyy-MM-dd HH:mm:ss') as rlrq,'' as fppzh, '' as htbh, a.university_amount as dxyl,"
					+ " a.academy_amount as yyl,a.department_amount as xyl,a.performance_amount as sy,a.payfirst_funds as gdje,d.kmdm as kmdm, '0' as shbz, '' as bz "
					+ " from t_pm_income_apply a left join t_pm_project b on a.t_p_id=b.id left join t_pm_project d on d.glxm=b.id left join t_b_pm_payfirst c on c.id = a.payfirst_id  "
					+ " where a.audit_status is not null or a.audit_status <> '0' and (a.cw_status is null or a.cw_status=0) and b.parent_project is null  "
					+ " AND A.ID IN ( SELECT INCOME_APPLY_ID FROM t_pm_income_rel_apply T1 "
					+ " LEFT JOIN t_pm_income T2 ON T1.INCOME_ID = T2.ID WHERE T1.INCOME_APPLY_ID = A.ID AND  T2.INCOME_YEAR =  '" + year + "' )";
		
		List list = this.findForJdbc(sql);
		return list;
	}
	
	@Override
	public List exportXmlToDkmx(String year) {
	/*	String sql = "select a.id as zbid,a.id as guid,'' as id, '' as sxh, c.income_year as dknd,a.voucher_no dkpzh,c.income_no as dksxh,"
				+ " a.income_amount as je from t_pm_income_apply a "
				+ " left join t_pm_project b on a.t_p_id=b.id "
				+ " left join t_pm_income c on a.income_id=c.id where a.audit_status is not null and a.audit_status <> '0' and a.cw_status is null and b.parent_project is null and c.INCOME_YEAR = '" + year + "'";

    String sql = "select a.id as zbid,a.id as guid,'' as id, '' as sxh, c.income_year as dknd,a.voucher_no dkpzh,"
				+ " a.income_amount as je from t_pm_income_apply a "
				+ " left join t_pm_project b on a.t_p_id=b.id "
				+ "  where a.audit_status is not null and a.audit_status <> '0' and a.cw_status is null and b.parent_project is null "
		+ " AND a.ID IN ( SELECT INCOME_APPLY_ID FROM t_pm_income_rel_apply T1 "
		+ " LEFT JOIN t_pm_income T2 ON T1.INCOME_ID = T2.ID WHERE T1.INCOME_APPLY_ID = a.ID AND  T2.INCOME_YEAR =  '" + year + "' )";

		*/

		StringBuilder sb = new StringBuilder();
		sb.append(" select a.id as zbid,a.id as guid,'' as id, '' as sxh, c.income_year as dknd,a.voucher_no dkpzh, ");
		sb.append(" c.income_no as dksxh,c.digest as dkzhy, a.apply_amount as je ");
		sb.append(" from t_pm_income c   ");
	    sb.append(" left join t_pm_income_rel_apply rel on rel.INCOME_ID = c.ID ");
		sb.append(" left join t_pm_income_apply a on a.id=rel.INCOME_APPLY_ID ");
		sb.append(" left join t_pm_project b on a.t_p_id=b.id ");
		sb.append(" where a.audit_status is not null and a.audit_status <> '0'  ");
        sb.append(" and (a.cw_status is null or a.cw_status=0) and b.parent_project is null and c.INCOME_YEAR ='" + year + "' ");
	  	List list = this.findForJdbc(sb.toString());
		return list;
	}

	@Override
	public List exportXmlToFpmx(String year,String projectId) {
		// TODO Auto-generated method stub
/*		
		String sql = "select b.id as zbid,a.id as guid,'' as id,'' as sxh,a.invoice_year as pjnd,a.invoice_num1 as pjh,"
				+ " a.invoice_amount as je "
				+ " from t_b_pm_invoice a "
				+ " left join t_pm_income_apply b on a.id=b.invoice_id left join t_pm_income d on b.income_id=d.id"
		        + " left join t_pm_project c on b.t_p_id=c.id where b.audit_status is not null and b.audit_status <> '0' and b.cw_status is null and c.parent_project is null and d.INCOME_YEAR = '" + year + "'";

*/
		String sql = "select b.id as zbid,a.id as guid,'' as id,'' as sxh,a.invoice_year as pjnd,a.invoice_num1 as pjh,"
				+ " a.invoice_amount as je "
				+ " from t_b_pm_invoice a "
				+ " left join t_pm_income_apply b on a.id=b.invoice_id "
		        + " left join t_pm_project c on b.t_p_id=c.id where b.audit_status is not null and b.audit_status <> '0' and b.cw_status is null and c.parent_project is null "
		+ " AND a.ID IN ( SELECT INCOME_APPLY_ID FROM t_pm_income_rel_apply T1 "
		+ " LEFT JOIN t_pm_income T2 ON T1.INCOME_ID = T2.ID WHERE T1.INCOME_APPLY_ID = A.ID AND  T2.INCOME_YEAR =  '" + year + "' ) ";

		if(StringUtil.checkObj(projectId)){
            sql+=" and a.project_id='"+ projectId +"'";
        }
		List list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public List exportXmlToXnxz(String year) {
		String sql = " select d.id as guid,'' as id,a.project_no as xmdm,e.contract_code as htbh,e.contract_name as htmc, "
                     + " e.contract_signing_time as qdsj,d.income_time as fpsj,d.create_name as tzr,d.check_user_realname as shr, "
                     + " 0 as shbz,'' as bz from t_pm_project A "
                     + " left join t_pm_project B on A.GLXM=B.PARENT_PROJECT or A.ID=B.PARENT_PROJECT"
                     + " left join t_pm_project C on C.GLXM=B.ID "
                     + " left join t_pm_income_apply D on D.T_P_ID=C.ID "
                     + " left join t_Pm_Income_Contract_Appr e on b.id = e.t_p_id"
                     + " where a.project_no is not null "
                     + " order by C.PROJECT_NO ";
    	List list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public List exportXmlToXnmx(String year) {
			String sql = "select d.id as zbid,d.id as guid,'' as id,'' as sxh,c.project_no as xmbm,d.apply_amount as je, "
                     + " '' as gdje,'' as kmdm,'' as bz from t_pm_project A "
			         + " left join t_pm_project B on A.GLXM=B.PARENT_PROJECT or A.ID=B.PARENT_PROJECT "
                     + " left join t_pm_project C on C.GLXM=B.ID "
                     + " left join t_pm_income_apply D on D.T_P_ID=C.ID"
                     + " where a.project_no is not null order by C.PROJECT_NO ";
    	List list = this.findForJdbc(sql);
		return list;
	}
	
	@Override
	public List exportXmlToXnmxHt(String id) {
		// TODO Auto-generated method stub
		String sql = "select b.apply_amount as fpje,b.income_amount as je,"
				+ " b.university_amount as dxyl,b.academy_amount as xyl,b.department_amount as xyl,b.performance_amount as sy,"
				+ " b.payfirst_funds as gdje,b.payfirst_id as kmdm "
				+ " from t_pm_project a left join t_pm_income_apply b on a.id=b.t_p_id "
				+ " where a.parent_project is not null and a.plan_contract_flag is not null and a.plan_contract_flag=2 and a.id='"+id+"'";
    	List list = this.findForJdbc(sql);
		return list;
	}
	
	@Override
	public List exportXmlToXnmxJh(String id) {
		// TODO Auto-generated method stub
		String sql = "select b.plan_amount as fpje,b.plan_amount as je,"
				+ " b.university_amount as dxyl,b.academy_amount as xyl,b.department_amount as xyl,b.performance_amount as sy,"
				+ " b.payfirst_funds as gdje,b.payfirst_id as kmdm "
				+ " from t_pm_project a left join T_PM_INCOME_PLAN b on a.id=b.t_p_id "
				+ " where a.parent_project is not null and a.plan_contract_flag is not null and a.plan_contract_flag=1 and a.id='"+id+"'";
    	List list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public List exportXmlToDzjf(String year) {
		String sql = "select a.id as guid,'' as xh, c.project_no as xmdm,a.pay_subjectcode as kmdm,"
				+ "a.create_date as dzrq,a.pay_funds as je,a.create_name as pzr, '0' as dqzt, '' as bz,a.gd_status as gdzt,'0' as shbz "
				+ " from t_b_pm_payfirst a left join t_pm_project b on a.project_id=b.id "
				+ " left join t_pm_project c on b.id = c.glxm where (a.BPM_STATUS>=1 and a.BPM_STATUS<4) "
				+ "  and a.cw_status is null and a.gd_status = 0 and a.pay_year = '" + year + "'";
    	List list = this.findForJdbc(sql);
		return list;
	}
	
	
	@Override
	public List exportXmlToDzjfmx(String year) {
		// TODO Auto-generated method stub
		String sql = "select a.id as xh, b.project_no as xmdm,'' as gdrq,'' as gdxh, 1 as gdsxh, a.pay_funds as je, '' as bz,"
				+ " case b.school_cooperation_flag when '0' then b.lxyj when '1' then '3' end as gdtype "
				+ " from t_b_pm_payfirst a "
				+ " left join t_pm_project b on a.project_id=b.id where (a.BPM_STATUS>=1 and a.BPM_STATUS<4)"
				+ " and a.gd_status = 1"
				+ " and a.pay_year = '" + year + "'";
    	List list = this.findForJdbc(sql);
		return list;
	}
	
	@Override
	public List exportXmlToTzys() {
		String sql = "select project_no as xmdm"
				+ " from t_pm_project"
				+ " where tzys_status = '1' and project_no is not null ";
    	List list = this.findForJdbc(sql);   
    	if(list!=null && list.size()>0){
    		for(int a=0;a<list.size();a++){
    			Map map = (Map) list.get(a);
		    	String updateSql = "update T_PM_PROJECT set tzys_status='2' where project_no='"+map.get("xmdm").toString()+"'";
		    	this.updateBySqlString(updateSql);
    		}
    	}
		return list;
	}

	@Override
	public List exportXmlToJhzb(String year) {
		String sql = "select a.id as guid, '' as id, a.document_no as wjh,a.document_name as wjm,a.document_time as fwsj,"
				+ " a.create_date as fpsj, a.create_name as tzr, '' as shr, '' as bz, '0' as shbz,'' as jfkm, '' as fppzh,'' as bmdm "
				+ " from T_PM_PROJECT_PLAN a "
				+ " where (a.cw_status is null or a.cw_status=0) and a.plan_year = '" + year +"'";
    	List list = this.findForJdbc(sql);
    	if(list!=null && list.size()>0){
    		for(int a=0;a<list.size();a++){
    			Map map = (Map) list.get(a);
		    	Object guid = map.get("guid");
		    	String sql2 = " select b.kmdm from T_PM_INCOME_PLAN t left join t_pm_project b on t.t_p_id = b.id"
                        + " where t.project_plan_id='"+ guid +"'";
		    	List<Map<String, Object>> idList = this.findForJdbc(sql2);
		    	if (idList != null && idList.size() == 1) {
		    	    //一个计划下达只给一个项目分钱
                    Object kmdm = idList.get(0).get("kmdm");
                    map.put("KMDM",kmdm);
                }
    		}
    	}
		return list;
	}

	@Override
	public List exportXmlToJhmx(String year) {
		/*String sql = "select  c.id as zbid,a.id as guid, '' as id, '' as sxh, b.project_no as xmbm,'' as bz, d.PAY_SUBJECTCODE as kmdm,a.plan_amount as je,"
				+ " a.university_amount as dxyl,a.academy_amount as yyl,a.department_amount as xyl,a.performance_amount as sy,a.payfirst_funds as gdje "
				+ " from T_PM_INCOME_PLAN a left join t_pm_project b on a.t_p_id=b.id "
				+ " left join t_pm_project_plan c on a.project_plan_id = c.id left join t_b_pm_payfirst d on d.id = a.payfirst_id"
				+ " where a.approval_status is not null and a.approval_status <> 0 and b.parent_project is null and c.cw_status is null and c.plan_year = '" + year +"'";*/


		String sql = " select a.id as zbid,b.id as guid,'' as id,'' as sxh,b.project_no as xmbm,'' as bz,'' as kmdm,c.je as je, "
                +" '' as dxyl,'' as yyl,'' as xyl,'' as sy,'' as gdje from t_pm_project_plan a "
                +" left join t_pm_project b on a.id = b.jhid "
                +" left join t_pm_fpje c on b.id = c.xmid "
                +" where b.parent_project is null and (a.cw_status is null or a.cw_status = 0 ) "
                +" and a.plan_year= '" + year + "'";

    	List list = this.findForJdbc(sql);
		return list;
	}

    @Override
    public List exportXmlToGdmx(String year) {
        String sql1=" SELECT a.id as gdxh,a.gddz_id as xh,b.project_no as xmdm,'' as gdsxh,"
                +" to_char(a.create_date,'yyyy-MM-dd') as gdrq,a.gd_mondy as je,a.reason as bz, "
                +" case b.school_cooperation_flag when '0' then b.lxyj when '1' then '3' end as gdtype "
                +" FROM T_B_PM_GD_INFO a left join t_pm_project b on a.project_id = b.id "
                +" WHERE a.yp_year='"+year+"' ";
        List<Map<String, Object>> list = this.findForJdbc(sql1);
        /*if(list != null && list.size() > 0){
            for(int i=0;i<list.size();i++){
                Map map = list.get(i);

            }
        }*/

        return list;
    }
	
	@Override
	public List selectProject() {
		String sql = "select a.id,a.project_no as xmdm,a.project_name as xmmc,c.id as cybm,a.project_manager as fzr,"
				+ " b.contract_Code  as htbh,b.contract_Name as htmc,a.all_fee as zjf,a.begin_date as qsrq,a.end_date as zzrq,'' as kmdm, "
				+ " a.fee_type as jflx,a.source_unit as htdw, b.develop_content as yznr,a.approval_authority as spqx, "
				+ "  '' as cymd,a.project_type as yslb,a.jjfjsfs as jjfjsfs,a.dxbl as dxbl,a.cydwbl as cydwbl,a.xmzbl as xmzbl,a.Project_Status as xmzt,'' as jtrq,'' as bz "
				+ " from t_pm_project a left join t_Pm_Income_Contract_Appr b on a.id=b.t_p_id "
				+ " left join t_s_depart c on a.developer_depart = c.id where a.project_no is not null and a.kmdm is null";
    	List projectList = this.findForJdbc(sql);
		return projectList;
	}
	
	@Override
	public List selectOutCome() {
		String sql = "select b.id,a.project_no,b.CONTRACT_SIGNING_TIME,b.contract_Code,b.contract_Name,b.start_Time,b.end_Time,"
				+ " b.acquisition_Method,b.total_Funds,"
				+ " c.unit_name_b,c.bank_b,c.account_Name_B,c.account_Id_B,b.total_funds "
				+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,t_pm_contract_basic c,t_b_code_type d,t_b_code_detail e "
				+ " where a.id=b.t_p_id and b.id=c.in_out_contractid "
				//+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid "
				+ " and b.contract_type=e.code and d.id=e.code_type_id and d.code='HTLB' "
                +"  and a.project_no is not null ";
    	List outComeList = this.findForJdbc(sql);
		return outComeList;
	}

    @Override
    public List selectOutCome2() {
        String sql = " select a.id as guid,b.project_no as xmdm,'' as xh,a.contract_signing_time as qdrq, "
                + " a.contract_code as htbh,a.contract_name as htmc,a.start_Time as qsrq,a.end_Time as zzrq, "
                + " '' as yf,a.acquisition_Method as cgfs,'' as htlx,a.total_Funds as zjf,'' as bz, "
                + " c.bank_b as khh,c.account_Name_B as hm,c.account_Id_B as zh "
                + " from t_Pm_Outcome_Contract_Appr a left join t_pm_project b on a.t_p_id = b.id "
                + " left join t_pm_contract_basic c on a.id=c.in_out_contractid ";
        List outComeList = this.findForJdbc(sql);
        return outComeList;
    }
	
	@Override
	public List selectOutComeNode() {
		String sql = "select b.id as zbid,c.id as guid,c.complete_Date,c.pay_amount,d.finish_time"
				+ " from t_pm_project a,t_Pm_Outcome_Contract_Appr b,T_PM_CONTRACT_NODE c "
				+ " left join T_PM_CONTRACT_NODE_CHECK d on c.id=d.contract_node_id "
				+ " where a.id=b.t_p_id and b.id=c.in_out_contractid ";
				//+ " where a.id=b.t_p_id and b.finish_flag='2' and a.project_status='04' and b.id=c.in_out_contractid";
    	List outComeNodeList = this.findForJdbc(sql);
		return outComeNodeList;
	}
	
	@Override
    public List importXml(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List list = new ArrayList();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            //取得当前上传文件的文件名称  
            String myFileName = file.getOriginalFilename();
        	TPmDBImportEntity dbImportEntity = new TPmDBImportEntity();
        	dbImportEntity.setFileName(myFileName);
        	dbImportEntity.setImpTime(new java.util.Date());
        	dbImportEntity.setImpUserId(user.getId());
        	dbImportEntity.setImpUserName(user.getRealName());
        	this.save(dbImportEntity);
            String timeStr = DateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS");
            String baseDir = new File(request.getSession().getServletContext().getRealPath("/")).getParent()
                    + File.separator + "upload" + File.separator;
            String realPath = baseDir + "db" + File.separator + timeStr + File.separator;// 文件的硬盘真实路径
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();// 创建根目录
            }
            File zipFile = new File(realPath + myFileName);
            /*String [] name = myFileName.split("\\.");
            zipFile.renameTo(new File(realPath+"/"+name[0]+".zip"));
            File newFile = new File(realPath+"/"+name[0]+".zip");*/
            String extend = myFileName.substring(myFileName.lastIndexOf("."));
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(zipFile);
                IOUtils.copy(file.getInputStream(), fos);
            } catch (Exception e) {
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ZipUtils.extract(zipFile.getPath(), realPath);//解压文件
            File names=new File(realPath);
            File[] tempList = names.listFiles();
            //System.out.println("该目录下对象个数："+tempList.length);
            list = new ArrayList();
            list.add(realPath);
            for (int i = 0; i < tempList.length; i++) {
            	if (tempList[i].isFile()) {
            		String fileName = tempList[i].getName();
            		String fileType = fileName.substring(fileName.lastIndexOf("."));
            		if(fileType.equals(".xml")){
            			list.add(tempList[i].getName());
            		}
            	}
            	/*if (tempList[i].isDirectory()) {
            		System.out.println("文件夹："+tempList[i]);
            	}*/
            }
        }
        return list;
	}

	@Override
	public void saveXml(List xmlList,Object cwnd) {
		for (int i = 0; i < xmlList.size(); i++) {
			Map map = (Map) xmlList.get(i);
			if(map!=null){
				//获取表名
				String tableName = map.get("tableName").toString();
				if(i==0){logger.info("导入财务交互文件："+tableName);}

                if(tableName.equals("td_xmxx")){
				    Object xmdm = map.get("xmdm");
				    Object kmdm = map.get("kmdm");
                    String sql  = " update t_pm_project set kmdm='"+kmdm+"' where project_no='"+ xmdm +"'";
                    this.updateBySqlString(sql);
                }

				//导入到款信息
				if(tableName.equals("td_jzpzzb")){
					TPmIncomeEntity tPmIncomeEntity = new TPmIncomeEntity();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
					Date date=null;
					try {
						date = sdf.parse(map.get("dzrq").toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tPmIncomeEntity.setIncomeYear(map.get("pznd").toString());
					tPmIncomeEntity.setIncomeTime(date);
					tPmIncomeEntity.setCertificate(map.get("dzpzh").toString());
					tPmIncomeEntity.setIncomeNo(map.get("dzsxh").toString());
					tPmIncomeEntity.setIncomeSubject(map.get("dzkm").toString());
					tPmIncomeEntity.setDigest(map.get("dzzhy").toString());
					BigDecimal dzjd=new BigDecimal(map.get("dzjd").toString() == "" ? "0" : map.get("dzjd").toString());
					BigDecimal dzje=new BigDecimal(map.get("dzje").toString() == "" ? "0" : map.get("dzje").toString());
					tPmIncomeEntity.setIncomeBorrow(dzjd);
					tPmIncomeEntity.setIncomeAmount(dzje);
					tPmIncomeEntity.setBalance(dzje);
		            this.commonDao.save(tPmIncomeEntity);
				}
				//导入项目余额明细
				if(tableName.equals("td_xmye")){
					TPmProjectBalanceEntity tPmProjectBalanceEntity = new TPmProjectBalanceEntity();
					tPmProjectBalanceEntity.setProjectNo(map.get("xmdm").toString());
					tPmProjectBalanceEntity.setBalanceNo(map.get("mxdm").toString());
					tPmProjectBalanceEntity.setBalanceName(map.get("mxmc").toString());
					BigDecimal lcje=new BigDecimal(map.get("lcje").toString() == "" ? "0" : map.get("lcje").toString());
					BigDecimal je=new BigDecimal(map.get("je").toString() == "" ? "0" : map.get("je").toString());
					BigDecimal zxje=new BigDecimal(map.get("zxje").toString() == "" ? "0" : map.get("zxje").toString());
					BigDecimal jkje=new BigDecimal(map.get("jkje").toString() == "" ? "0" : map.get("jkje").toString());
					BigDecimal ye=new BigDecimal(map.get("ye").toString() == "" ? "0" : map.get("ye").toString());
					tPmProjectBalanceEntity.setLcAmount(lcje);
					tPmProjectBalanceEntity.setYsAmount(je);
					tPmProjectBalanceEntity.setZxAmount(zxje);
					tPmProjectBalanceEntity.setJkAmount(jkje);
					tPmProjectBalanceEntity.setBalanceAmount(ye);
					tPmProjectBalanceEntity.setYsStatus("0");
					this.commonDao.save(tPmProjectBalanceEntity);
				}
				//导入预算接受反馈表
				if(tableName.equals("td_mxys_zb")){
					String sql = "update t_pm_project_funds_appr set cw_status='"+map.get("dqzt").toString()+"' where id='"+map.get("guid")+"'";
					String sql2= "update t_pm_project_plan set YS_STATUS='1' where "	//更新计划预算状态
							+ "id in (select B.JHWJID from t_pm_project_funds_appr a left join  T_PM_FPJE b  on a.T_P_ID=b.XMID "
							+ "where a.id='"+map.get("guid")+"')";
					this.updateBySqlString(sql2);
			    	int count = this.updateBySqlString(sql);
				}
				//导入到款分配反馈表
				if(tableName.equals("td_dkrl_zb")){
                    if(map.get("id") == null){
                        map.put("id","1");//序号不存在时，默认给1
                    }
					String dybh = cwnd + "," + map.get("id");
					String sql = "update t_pm_income_apply set cw_status='"+map.get("shbz")
							+"',dybh='"+dybh+"' where id='"+map.get("guid")+"'";
			    	int count = this.updateBySqlString(sql);
			    	
			    	String sql1 = "update t_b_pm_payfirst set GD_STATUS='2' where PROJECT_ID='"+map.get("xmbm")+"'";
			    	this.updateBySqlString(sql);
				}
				//导入计划分配反馈表
				if(tableName.equals("td_jhfp_zb")){
                    if(map.get("id") == null){
                        map.put("id","1");//序号不存在时，默认给1
                    }
					String dybh = cwnd + "," + map.get("id");
					String sql = "update T_PM_PROJECT_PLAN set cw_status='"+map.get("shbz").toString()
							+"',dybh='"+dybh+"' where id='"+map.get("guid")+"'";
			    	int count = this.updateBySqlString(sql);
				}
				//导入校内协作反馈表
				if(tableName.equals("td_xnxz_zb")){
                    if(map.get("id") == null){
                        map.put("id","1");//序号不存在时，默认给1
                    }
					String dybh = cwnd + "," + map.get("id");
	    			String updateSql = "";
	    	        String guid = map.get("guid").toString();
	    	        TPmIncomeApplyEntity tomincomeApplyEntity = this.commonDao.getEntity(TPmIncomeApplyEntity.class, guid);
	    	        if(tomincomeApplyEntity != null){
	    	        	updateSql = "update t_pm_income_apply set cw_status='"+map.get("shbz")+"',dybh='"+dybh+"' where id='"+guid+"'";
	    	        }else{
	    	        	updateSql = "update T_PM_INCOME_PLAN set cw_status='"+map.get("shbz")+"',dybh='"+dybh+"' where id='"+guid+"'";
	    	        }    	        
			    	this.updateBySqlString(updateSql);
						
//					String sql = "update t_pm_project set cw_status='"+map.get("shbz").toString()+"' where id='"+map.get("guid").toString()+"'";
//			    	int count = this.updateBySqlString(sql);
				}
				//导入垫支经费反馈表
				if(tableName.equals("td_jfdz")){
                    if(map.get("xh") == null){
                        map.put("xh","1");//序号不存在时，默认给1
                    }
					String dybh = cwnd + "," + map.get("xh");
					String sql = "update t_b_pm_payfirst set cw_status='"+map.get("dqzt")+"',dybh='"+dybh+"' where id='"+map.get("guid")+"'";
			    	int count = this.updateBySqlString(sql);
				}
				
				//导入项目执行情况
				if(tableName.equals("td_xmkz_yqnd")){
					TPmFundsBudgetAddendumEntity entity = new TPmFundsBudgetAddendumEntity();
					entity.setZtnd(String.valueOf(map.get("ztnd")));
					entity.setZxh(String.valueOf(map.get("zxh")));
					entity.setSxh(String.valueOf(map.get("sxh")));
					entity.setKjpzh(String.valueOf(map.get("kjpzh")));
					entity.setXmdm(String.valueOf(map.get("xmdm")));
					entity.setMxdm(String.valueOf(map.get("mxdm")));
					entity.setKmdm(String.valueOf(map.get("kmdm")));
					entity.setZhy(String.valueOf(map.get("zhy")));
					entity.setJd(String.valueOf(map.get("jd")));
					entity.setJe(String.valueOf(map.get("je")));

                    String kmdm = String.valueOf(map.get("kmdm"));
                    String jd = String.valueOf(map.get("jd"));
                    if(StringUtil.checkObj(kmdm)){
                        if(kmdm.startsWith("4") && "0".equals(jd)){
                            entity.setKzlx("1");
                        }else if(kmdm.startsWith("17") && "0".equals(jd)){
                            entity.setKzlx("2");
                        }else if(kmdm.startsWith("17") && "1".equals(jd)){
                            entity.setKzlx("3");
                        }
                    }
					try {
						if(StringUtil.checkObj(map.get("jzrq"))){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							entity.setJzrq(sdf.parse(map.get("jzrq").toString()));
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					this.commonDao.save(entity);

				}

				//调整预算申请反馈表
				if(tableName.equals("td_tzyssq")){
				    Object xmdm = map.get("xmdm");
				    Object sqzt = map.get("sqzt"); // 1：申请成功；0：不成功
				    Object bz = map.get("bz");

				    if ("1".equals(sqzt)) {
				        // tzys_status=3 申请成功
				        String sql = " update t_pm_project set tzys_status=3 where project_no='"+ xmdm +"'";
                        this.updateBySqlString(sql);
                    } else {
                        // tzys_status=4 申请失败
                        String sql = " update t_pm_project set tzys_status=4 where project_no='"+ xmdm +"'";
                        this.updateBySqlString(sql);
                    }

				    /*String sql = " update t_pm_project_funds_appr " +
                            "set cw_status_tzyssq='"+sqzt+"',bz='"+bz+"'" +
                            " where t_p_id=(SELECT a.id FROM t_pm_project a WHERE a.project_no='"+xmdm+"') ";
                    this.updateBySqlString(sql);*/
                }
				
			}
		}
	}

	/**
	 * 获取财务交互编号
	 * 交互编号财务已返回，则编号+1，否则，取未返回（最大）交互编号
	 */
	@Override
	public int getMaxCwjhbh(String year) {
		int jhbh = 1;
		String sql = " SELECT T.JHBH,T.RE_STATUS FROM T_B_CWJHBH T "
				+ "WHERE T.CWND = '"+ year +"' ORDER BY T.JHBH DESC ";
		List list = this.findForJdbc(sql);
		if(list.size() > 0){
			Map map = (Map)list.get(0);
			int oldJhbh = Integer.parseInt(String.valueOf(map.get("JHBH")));
			Object returnStatus = map.get("RE_STATUS");
			if("0".equals(returnStatus)){
				return oldJhbh;
			}else{
				addJhbh(year,oldJhbh + 1);
				return oldJhbh + 1;
			}
		}else{
			addJhbh(year,jhbh);
			return jhbh;
		}
	}

	private void addJhbh(String year, int jhbh) {
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String sql = " insert into T_B_CWJHBH(id,cwnd,jhbh,re_status) "
				+ "values('"+id+"','"+year+"','"+jhbh+"','0') ";
		this.updateBySqlString(sql);	
	}

	@Override
	public Map validateFirst(List list) {
		Object isFirst = "1";
		Object cwnd = null;
		Object jhbh = null;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if(map!=null){
				//获取表名
				String tableName = map.get("tableName").toString();
				//更新交互编号
				if(tableName.equals("td_kjnd")){
					cwnd = map.get("kjnd");
					jhbh = map.get("jhbh");
					if(StringUtil.checkObj(cwnd) && StringUtil.checkObj(jhbh)){
						String sql = " select * from T_B_CWJHBH where cwnd='"+cwnd+"' and jhbh='"+jhbh+"'";
						List<Map<String, Object>> li = this.findForJdbc(sql);
						if(li.size() == 1){
							if("0".equals(li.get(0).get("RE_STATUS"))){
								String sql1 = " update T_B_CWJHBH set re_status = 1 where cwnd='"+cwnd+"' and jhbh='"+jhbh+"' and re_status = 0 ";
								this.commonDao.updateBySqlString(sql1);
							}else{
								isFirst = "0";
							}
						}
						
					}
				}
			}
		}
		
		Map map = new HashMap();
		map.put("isFirst", isFirst);
		map.put("cwnd", cwnd);
		map.put("jhbh", jhbh);
		return map;
	}


}