package org.jeecgframework.workflow.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

@MiniDao
public interface ActivitiDao {
	
    //@Sql("insert into t_s_basebus(id,userid,prjstateid,busconfigid,business_code,business_name,createtime) values('${mp.id}','${mp.userid}','${mp.prjstateid}','${mp.busconfigid}','${mp.businessCode}','${mp.businessName}',sysdate)")
	@Arguments("mp")
	int insert(Map mp);
	
	/**
	 * 根据历史执行ID，查询变量内容
	 * @param EXECUTION_ID_
	 * @param NAME_
	 * @return
	 */
	@Sql("select TEXT_ from act_hi_varinst where NAME_=:name and EXECUTION_ID_=:proid")
	@Arguments({"name","proid"})
	String getHisVarinst(String name,String proid);
	
	/**
	 * 获取流程的启动和结束时间
	 * @param name
	 * @param proid
	 * @return
	 */
	 @Sql("select * from act_hi_actinst where EXECUTION_ID_=:proid and ACT_TYPE_ in ('startEvent','endEvent')")
	 @Arguments({"proid"})
	 List<Map<String,Object>> getActHiActinst(String proid);
	 
	 
	 /**
	  * 通过用户名，获取上级领导用户名
	  * @param user_name
	  * @return
	  */
	 @Arguments({"username"})
	 List<Map<String,Object>> getDeptHeadLeader(String username);
	 
	 /**
	  * 指定下一步操作人
	  * @param proInsId
	  * @param taskDefKey
	  * @param nextUser
	  */
	 @Arguments({"proInsId","taskDefKey", "page", "rows"})
	 String getTaskIdByProins(String proInsId,String taskDefKey, int page, int rows);
	 
	 /**
	  *  查询流程的历史任务节点
	  * @param proceInsId
	  * @return
	  */
	 @Arguments({"proceInsId"})
	 List<Map<String,Object>> getHistTaskNodeList(String proceInsId);
	 
	 
	 /**
	  *  修改自定义表单业务数据状态
	  * @param proceInsId
	  * @return
	  */
    @Arguments({ "id", "bpm_form_key", "bpmStatus" })
    @Sql("update ${bpm_form_key} set bpm_status = :bpmStatus where id = :id")
    void updateFormDataStatus(String id, String bpm_form_key, String bpmStatus);
	 
	 /**
	  * 根据流程ID，获取流程的发起人
	  * @param ID_  流程ID
	  */
	 @Arguments({"id"})
	 @Sql("select START_USER_ID_  from act_hi_procinst where ID_ = :id")
	 String getProcessStartUserId(String id);
	 
	 /**
	  * 根据流程ID，流程最后一个处理人
	  * @param ID_  流程ID
	  */
	//update-begin--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
	 @Arguments({"id", "page", "rows"})
	 @Sql("select ASSIGNEE_ from act_hi_taskinst where PROC_INST_ID_ = :id order by END_TIME_ DESC")
	 List<Map<String,Object>> getProcessEndUserId(String id,int page, int rows);
	//update-end--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
	 
	 /**
	  * 根据用户账号，获取用户真实名字
	  * @param username  用户账号
	  */
	 @Arguments({"username"})
	 @Sql("select realname from t_s_base_user where username = :username")
	 String getUserRealName(String username);
	 
	 
	 
	 /**
	  * 获取流程发起节点的表单
	  * @param processid  流程ID
	  */
	 @Arguments({"processid"})
	 @Sql("select modelandview from t_p_processnode where processid = :processid and processnodecode = 'start'")
	 String getProcessStartNodeView(String processid);
}
