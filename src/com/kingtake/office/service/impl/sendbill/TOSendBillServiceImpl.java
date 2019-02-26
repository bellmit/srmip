package com.kingtake.office.service.impl.sendbill;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.dao.SendBillDao;
import com.kingtake.office.dao.SendReceivePortalDao;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.sendbill.TOSendBillServiceI;

@Service("tOSendBillService")
@Transactional
public class TOSendBillServiceImpl extends CommonServiceImpl implements TOSendBillServiceI {

    @Autowired
    private SendReceivePortalDao sendReceiveDao;

    @Autowired
    private SendBillDao sendBillDao;

    @Autowired
    private TOMessageServiceI tOMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOSendBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOSendBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOSendBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOSendBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOSendBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOSendBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOSendBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{send_year}",String.valueOf(t.getSendYear()));
 		sql  = sql.replace("#{send_num}",String.valueOf(t.getSendNum()));
 		sql  = sql.replace("#{send_unit}",String.valueOf(t.getSendUnit()));
 		sql  = sql.replace("#{send_type_code}",String.valueOf(t.getSendTypeCode()));
 		sql  = sql.replace("#{send_type_name}",String.valueOf(t.getSendTypeName()));
 		sql  = sql.replace("#{secrity_grade}",String.valueOf(t.getSecrityGrade()));
 		sql  = sql.replace("#{print_num}",String.valueOf(t.getPrintNum()));
 		sql  = sql.replace("#{send_title}",String.valueOf(t.getSendTitle()));
 		sql  = sql.replace("#{draft_explain}",String.valueOf(t.getDraftExplain()));
 		sql  = sql.replace("#{draft_date}",String.valueOf(t.getDraftDate()));
 		sql  = sql.replace("#{undertake_unit_id}",String.valueOf(t.getUndertakeUnitId()));
 		sql  = sql.replace("#{undertake_unit_name}",String.valueOf(t.getUndertakeUnitName()));
 		sql  = sql.replace("#{nuclear_draft_userid}",String.valueOf(t.getNuclearDraftUserid()));
 		sql  = sql.replace("#{nuclear_draft_username}",String.valueOf(t.getNuclearDraftUsername()));
 		sql  = sql.replace("#{contact_id}",String.valueOf(t.getContactId()));
 		sql  = sql.replace("#{contact_name}",String.valueOf(t.getContactName()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{archive_flag}",String.valueOf(t.getArchiveFlag()));
 		sql  = sql.replace("#{archive_userid}",String.valueOf(t.getArchiveUserid()));
 		sql  = sql.replace("#{archive_username}",String.valueOf(t.getArchiveUsername()));
 		sql  = sql.replace("#{archive_date}",String.valueOf(t.getArchiveDate()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{back_userid}",String.valueOf(t.getBackUserid()));
 		sql  = sql.replace("#{back_username}",String.valueOf(t.getBackUsername()));
 		sql  = sql.replace("#{back_suggestion}",String.valueOf(t.getBackSuggestion()));
 		sql  = sql.replace("#{send_unit_id}",String.valueOf(t.getSendUnitId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public List<Map<String, String>> getPortalList(Map<String, String> param, int start, int end) {
        return sendReceiveDao.getPortalList(param, start, end);
    }

    @Override
    public Integer getPortalCount(Map<String, String> param) {
        return sendReceiveDao.getPortalCount(param);
    }

    @Override
    public void doFinish(HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        String receiverid = req.getParameter("receiverid");
        String realName = req.getParameter("realName");
        if (StringUtil.isNotEmpty(id)) {
            TOSendBillEntity tOSendBill = commonDao.get(TOSendBillEntity.class, id);
            tOSendBill.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
            commonDao.updateEntitie(tOSendBill);
            TOSendReceiveRegEntity reg = commonDao.get(TOSendReceiveRegEntity.class, tOSendBill.getRegId());
            reg.setGenerationFlag(ReceiveBillConstant.BILL_COMPLETE);
            reg.setArchiveUserid(receiverid);
            reg.setArchiveUsername(realName);
            commonDao.updateEntitie(reg);
            TOFlowReceiveLogsEntity receive = commonDao.get(TOFlowReceiveLogsEntity.class, rid);
            receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receive.setOperateTime(new Date());
            receive.setSuggestionCode("1");
            commonDao.updateEntitie(receive);
            tOMessageService.sendMessage(receiverid, "公文[" + tOSendBill.getSendTitle() + "]完成提醒！", "发文",
                    "公文[" + tOSendBill.getSendTitle() + "]已完成，请到协同办公->发文登记中进行归档操作！", tOSendBill.getContactId());
        }
    }

    @Override
    public void archive(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        String fileNumPrefix = request.getParameter("fileNumPrefix");
        String sendYear = request.getParameter("sendYear");
        String sendNum = request.getParameter("sendNum");
        tOSendBill = commonDao.getEntity(TOSendBillEntity.class, tOSendBill.getId());
        TSUser user = ResourceUtil.getSessionUserName();
        tOSendBill.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
        tOSendBill.setArchiveUserid(user.getId());
        tOSendBill.setArchiveUsername(user.getRealName());
        tOSendBill.setArchiveDate(new Date());
        tOSendBill.setSendNum(sendNum);
        tOSendBill.setSendYear(sendYear);
        tOSendBill.setFileNumPrefix(fileNumPrefix);
        commonDao.save(tOSendBill);
        TOSendReceiveRegEntity reg = commonDao.get(TOSendReceiveRegEntity.class, tOSendBill.getRegId());
        reg.setFileNum(sendNum);
        reg.setFileNumPrefix(fileNumPrefix);
        reg.setFileNumYear(sendYear);
        commonDao.updateEntitie(reg);
        CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        cq.eq("sendReceiveId", tOSendBill.getId());
        cq.eq("operateStatus", SrmipConstants.NO);
        cq.add();
        List<TOFlowReceiveLogsEntity> list = commonDao.getListByCriteriaQuery(cq, false);
        for (TOFlowReceiveLogsEntity re : list) {//归档时将未处理接收记录改为无效
            re.setValidFlag(SrmipConstants.NO);
            commonDao.updateEntitie(re);
        }
    }

    @Override
    public void doArchive(HttpServletRequest req) {
        String regId = req.getParameter("regId");
        String fileNumPrefix = req.getParameter("fileNumPrefix");
        String sendYear = req.getParameter("sendYear");
        String sendNum = req.getParameter("sendNum");
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtil.isNotEmpty(regId)) {
            TOSendReceiveRegEntity tOSendReceiveReg = commonDao.get(TOSendReceiveRegEntity.class, regId);
            CriteriaQuery cq = new CriteriaQuery(TOSendBillEntity.class);
            cq.eq("regId", regId);
            cq.eq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
            cq.add();
            List<TOSendBillEntity> list = commonDao.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TOSendBillEntity sendBill = list.get(0);
                sendBill.setArchiveFlag(ReceiveBillConstant.BILL_ARCHIVED);
                sendBill.setArchiveDate(new Date());
                sendBill.setArchiveUserid(user.getId());
                sendBill.setArchiveUsername(user.getRealName());
                if (StringUtils.isNotEmpty(sendNum)) {
                    sendBill.setSendNum(sendNum);
                }
                if (StringUtils.isNotEmpty(sendYear)) {
                    sendBill.setSendYear(sendYear);
                }
                if (StringUtils.isNotEmpty(fileNumPrefix)) {
                    sendBill.setFileNumPrefix(fileNumPrefix);
                }
                commonDao.updateEntitie(sendBill);
                tOSendReceiveReg.setGenerationFlag(ReceiveBillConstant.BILL_ARCHIVED);
                tOSendReceiveReg.setArchiveUserid(user.getId());
                tOSendReceiveReg.setArchiveDate(new Date());
                tOSendReceiveReg.setArchiveUsername(user.getRealName());
                if(StringUtils.isNotEmpty(sendNum)){
                   tOSendReceiveReg.setFileNum(sendNum);
                }
                if(StringUtils.isNotEmpty(fileNumPrefix)){
                   tOSendReceiveReg.setFileNumPrefix(fileNumPrefix);
                }
                if(StringUtils.isNotEmpty(sendYear)){
                   tOSendReceiveReg.setFileNumYear(sendYear);
                }
                String merge = (StringUtils.isEmpty(tOSendReceiveReg.getFileNumPrefix()) ? "" : tOSendReceiveReg
                        .getFileNumPrefix())
                        + (StringUtils.isEmpty(tOSendReceiveReg.getFileNumYear()) ? "" : ("〔20"
                                + tOSendReceiveReg.getFileNumYear() + "〕")) + tOSendReceiveReg.getFileNum();
                tOSendReceiveReg.setMergeFileNum(merge);
                commonDao.updateEntitie(tOSendReceiveReg);
            }
        }

    }

    @Override
    public List<Map<String, Object>> getSendBillList(Map<String, String> param, int start, int end) {
        return sendBillDao.getSendBillList(param, start, end);
    }

    @Override
    public Integer getSendBillCount(Map<String, String> param) {
        return sendBillDao.getSendBillCount(param);
    }

    /**
     * 获取公文编号参数
     * 
     * @param reg
     * @return
     */
    @Override
    public Map<String, String> getFileNum(TOSendReceiveRegEntity reg) {
        Map<String, String> dataMap = new HashMap<String, String>();
        String prefix = null;
        if ("1".equals(reg.getIsUnion())) {//如果是校军联，则直接前缀为校军联
            prefix = "校军联";
            dataMap.put("prefix", prefix);
        } else {//否则，按照承研部门获取配置
            String deptId = null;
            if (StringUtils.isNotEmpty(reg.getUndertakeDeptId()) && reg.getUndertakeDeptId().contains(",")) {
                deptId = reg.getUndertakeDeptId().split(",")[0];
            } else {
                deptId = reg.getUndertakeDeptId();
            }
            List<Map<String, Object>> dataList = this.commonDao.findForJdbc(
                    "select t.file_num_prefix from t_o_dept_file_num t where t.dept_id=?", deptId);
            if (dataList.size() > 0) {
                Map<String, Object> data = dataList.get(0);
                prefix = (String) data.get("file_num_prefix");
                dataMap.put("prefix", prefix);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());
        dataMap.put("year", year.substring(2, 4));
        String fileNumLikeStr = prefix + "〔" + year + "〕";
        String fileNumSql = "select t.merge_file_num from t_o_send_receive_reg t where t.merge_file_num like ? and t.REG_TYPE=?";
        List<Map<String, Object>> numMapList = this.commonDao.findForJdbc(fileNumSql, new Object[] {
                fileNumLikeStr + "%", reg.getRegType() });
        int fileNum = 1;
        if (numMapList.size() > 0) {
            List<Integer> numList = new ArrayList<Integer>();
            for (Map<String, Object> map : numMapList) {
                String mergeFileNum = (String) map.get("merge_file_num");
                String tempStr = mergeFileNum.replaceAll(fileNumLikeStr, "");
                try {
                    int temp = Integer.parseInt(tempStr);
                    numList.add(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Collections.sort(numList);
            }
            if (numList.size() > 0) {
                fileNum = numList.get(numList.size() - 1);
                fileNum++;
            }
        }
        dataMap.put("fileNum", String.valueOf(fileNum));
        return dataMap;
    }
}