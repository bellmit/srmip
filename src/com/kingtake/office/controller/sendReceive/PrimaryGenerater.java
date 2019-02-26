package com.kingtake.office.controller.sendReceive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.serial.TSSerialNumberEntity;
import com.kingtake.project.entity.thesis.TBDepartThesisSecretCodeEntity;

public class PrimaryGenerater {
    //    private static final String SERIAL_NUMBER = "XXXXX"; // 流水号格式
    private static PrimaryGenerater primaryGenerater = null;

    private static SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

    private PrimaryGenerater() {
    }

    /**
     * 取得PrimaryGenerater的单例实现
     * 
     * @return
     */
    public static PrimaryGenerater getInstance() {
        if (primaryGenerater == null) {
            synchronized (PrimaryGenerater.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new PrimaryGenerater();
                }
            }
        }
        return primaryGenerater;
    }

    /**
     * 生成下一个编号
     */
    public synchronized String generaterNextNumber(String businessCode) {
        String next = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        if (StringUtil.isNotEmpty(businessCode)) {
            CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
            cq.eq("businessCode", businessCode);
            cq.add();
            List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TSSerialNumberEntity serialNum = list.get(0);
                if (serialNum.getNextNumber().substring(0, 4).equals(formatter.format(date))) {//相同年份直接取

                } else {//不同年份从0001开始
                    serialNum.setNextNumber(formatter.format(date) + "0001");
                }
                next = serialNum.getNextNumber();
                //                serialNum.setNextNumber(String.valueOf(Integer.parseInt(next) + 1));
                //                systemService.updateEntitie(serialNum);
            } else {
                TSSerialNumberEntity serialNum = new TSSerialNumberEntity();
                serialNum.setBusinessCode(businessCode);
                serialNum.setNextNumber(formatter.format(date) + "0001");
                next = serialNum.getNextNumber();
                //                serialNum.setNextNumber(String.valueOf(Integer.parseInt(next) + 1));
                //                systemService.save(serialNum);
            }
        }
        return next;
    }

    public synchronized void updateNext(String businessCode, String currentNum) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        if (StringUtil.isNotEmpty(businessCode)) {
            CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
            cq.eq("businessCode", businessCode);
            cq.add();
            List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TSSerialNumberEntity serialNum = list.get(0);
                if (currentNum.substring(0, 4).equals(formatter.format(date))) {//相同年份直接加1
                    serialNum.setNextNumber(String.valueOf(Integer.parseInt(currentNum) + 1));
                } else {//不同年份从0001开始
                    serialNum.setNextNumber(formatter.format(date) + "0001");
                }
                systemService.updateEntitie(serialNum);
            } else {
                TSSerialNumberEntity serialNum = new TSSerialNumberEntity();
                serialNum.setBusinessCode(businessCode);
                serialNum.setNextNumber(formatter.format(date) + "0002");//新增流水号下一条从0002开始
                systemService.save(serialNum);
            }
        }
    }

    public synchronized String generaterNextApprApprovalNum() {
        String businessCode = "apprApprovalNum";
        String next = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        if (StringUtil.isNotEmpty(businessCode)) {
            CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
            cq.eq("businessCode", businessCode);
            cq.add();
            List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TSSerialNumberEntity serialNum = list.get(0);
                if (serialNum.getNextNumber().substring(0, 4).equals(formatter.format(date))) {//相同年份直接取

                } else {//不同年份从0001开始
                    serialNum.setNextNumber(formatter.format(date) + "001");
                }
                next = serialNum.getNextNumber();
                //                serialNum.setNextNumber(String.valueOf(Integer.parseInt(next) + 1));
                //                systemService.updateEntitie(serialNum);
            } else {
                TSSerialNumberEntity serialNum = new TSSerialNumberEntity();
                serialNum.setBusinessCode(businessCode);
                serialNum.setNextNumber(formatter.format(date) + "001");
                next = serialNum.getNextNumber();
                //                serialNum.setNextNumber(String.valueOf(Integer.parseInt(next) + 1));
                //                systemService.save(serialNum);
            }
        }
        return next;
    }

    public synchronized void updateApprApprovalNext(String currentNum) {
        String businessCode = "apprApprovalNum";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        if (StringUtil.isNotEmpty(businessCode)) {
            CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
            cq.eq("businessCode", businessCode);
            cq.add();
            List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TSSerialNumberEntity serialNum = list.get(0);
                if (currentNum.substring(0, 4).equals(formatter.format(date))) {//相同年份直接加1
                    serialNum.setNextNumber(String.valueOf(Integer.parseInt(currentNum) + 1));
                } else {//不同年份从0001开始
                    serialNum.setNextNumber(formatter.format(date) + "001");
                }
                systemService.updateEntitie(serialNum);
            } else {
                TSSerialNumberEntity serialNum = new TSSerialNumberEntity();
                serialNum.setBusinessCode(businessCode);
                serialNum.setNextNumber(formatter.format(date) + "002");//新增流水号下一条从0002开始
                systemService.save(serialNum);
            }
        }
    }

    /**
     * 生成合同编号
     * 
     * @return
     */
    public synchronized String generaterNextContractCode(String businessCode) {
        String next = null;
        TSSerialNumberEntity serialNum = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(date);
        if (StringUtil.isNotEmpty(businessCode)) {
            CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
            cq.eq("businessCode", businessCode);
            cq.add();
            List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                serialNum = list.get(0);
                if (serialNum.getNextNumber().substring(0, 4).equals(year)) {//相同年份直接取
                    String maxNum = serialNum.getNextNumber();
                    String max = maxNum.substring(5, maxNum.length());
                    int num = Integer.parseInt(max);
                    num++;
                    String newNum = maxNum.substring(0, 5) + (getNumberStr(num, 3));
                    serialNum.setNextNumber(newNum);
                } else {//不同年份从001开始
                    if ("incomeContractCode".equals(businessCode)) {
                        serialNum.setNextNumber(year + "J" + "001");
                    } else if ("outcomeContractCode".equals(businessCode)) {
                        serialNum.setNextNumber(year + "C" + "001");
                    }
                }
                next = serialNum.getNextNumber();
            } else {
                serialNum = new TSSerialNumberEntity();
                serialNum.setBusinessCode(businessCode);
                if ("incomeContractCode".equals(businessCode)) {
                    serialNum.setNextNumber(year + "J" + "001");
                } else if ("outcomeContractCode".equals(businessCode)) {
                    serialNum.setNextNumber(year + "C" + "001");
                }
                serialNum.setBusinessCode(businessCode);
                next = serialNum.getNextNumber();
            }
            this.systemService.saveOrUpdate(serialNum);
        }
        return next;
    }

    /**
     * 生成论文保密审查编号
     * 
     * @return
     */
    public synchronized String generaterNextThesisSecretCode(String concreteDeptId, String subordinateDeptId) {
        String next = null;
        TSSerialNumberEntity serialNum = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(date);
        CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
        cq.eq("businessCode", "thesisSecret");
        cq.add();
        List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            serialNum = list.get(0);
            if (serialNum.getNextNumber().substring(0, 4).equals(year)) {//相同年份直接取
                String maxNum = serialNum.getNextNumber();
                String max = maxNum.substring(maxNum.length() - 4, maxNum.length() - 1);
                int num = Integer.parseInt(max);
                String secretNum = getSecretNum(concreteDeptId, subordinateDeptId);
                num++;
                serialNum.setNextNumber(year + "-" + secretNum + (getNumberStr(num, 3)) + "#");
            } else {//不同年份从001开始
                String secretNum = getSecretNum(concreteDeptId, subordinateDeptId);
                serialNum.setNextNumber(year + "-" + secretNum + "001#");
            }
        } else {
            String secretNum = getSecretNum(concreteDeptId, subordinateDeptId);
            serialNum = new TSSerialNumberEntity();
            serialNum.setBusinessCode("thesisSecret");
            serialNum.setNextNumber(year + "-" + secretNum + "001#");
        }
        next = serialNum.getNextNumber();
        this.systemService.saveOrUpdate(serialNum);
        return next;
    }

    /**
     * 生成论著保密审查编号
     * 
     * @return
     */
    public synchronized String generaterNextBookSecretCode() {
        String next = null;
        TSSerialNumberEntity serialNum = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(date);
        CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
        cq.eq("businessCode", "bookSecret");
        cq.add();
        List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            serialNum = list.get(0);
            String secretNum = "14";
            if (serialNum.getNextNumber().substring(0, 4).equals(year)) {//相同年份直接取
                String maxNum = serialNum.getNextNumber();
                String max = maxNum.substring(maxNum.length() - 4, maxNum.length() - 1);
                int num = Integer.parseInt(max);
                num++;
                serialNum.setNextNumber(year + "-" + secretNum + (getNumberStr(num, 3)) + "#");
            } else {//不同年份从001开始
                serialNum.setNextNumber(year + "-" + secretNum + "001#");
            }
        } else {
            String secretNum = "14";
            serialNum = new TSSerialNumberEntity();
            serialNum.setBusinessCode("bookSecret");
            serialNum.setNextNumber(year + "-" + secretNum + "001#");
        }
        next = serialNum.getNextNumber();
        this.systemService.saveOrUpdate(serialNum);
        return next;
    }

    /**
     * 获取保密编号
     * 
     * @param concreteDeptId
     * @param subordinateDeptId
     * @return
     */
    private String getSecretNum(String concreteDeptId, String subordinateDeptId) {
        TBDepartThesisSecretCodeEntity tmp = null;
        List<TBDepartThesisSecretCodeEntity> codeList1 = systemService.findByProperty(
                TBDepartThesisSecretCodeEntity.class, "departId", concreteDeptId);
        if (codeList1.size() > 0) {
            tmp = codeList1.get(0);
            return tmp.getSecretCode();
        } else {
            List<TBDepartThesisSecretCodeEntity> codeList2 = systemService.findByProperty(
                    TBDepartThesisSecretCodeEntity.class, "departId", subordinateDeptId);
            if (codeList2.size() > 0) {
                tmp = codeList2.get(0);
                return tmp.getSecretCode();
            }
        }
        return "";
    }

    /**
     * 获取数字字符串
     * 
     * @param num
     * @return
     */
    public static String getNumberStr(int num, int count) {
        String numStr = String.valueOf(num);
        int len = numStr.length();
        if (len < count) {  
            for (int i = 0; i < count - len; i++) {
                numStr = "0" + numStr;
            }
        }
        return numStr;
    }

    /**
     * 生成项目编码,规则为yyyy+00001五位递增数字
     * 
     * @return
     */
    public synchronized String generaterProjectNo() {
        String businessCode = "project";
        String next = null;
        TSSerialNumberEntity serialNum = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(date);
        CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
        cq.eq("businessCode", businessCode);
        cq.add();
        List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            serialNum = list.get(0);
            if (serialNum.getNextNumber().substring(0, 4).equals(year)) {//相同年份直接取
                String maxNum = serialNum.getNextNumber();
                String max = maxNum.substring(5, maxNum.length());
                int num = Integer.parseInt(max);
                num++;
                String newNum = maxNum.substring(0, 5) + (getNumberStr(num, 5));
                serialNum.setNextNumber(newNum);
            } else {//不同年份从00001开始
                serialNum.setNextNumber(year + "00001");
            }
            next = serialNum.getNextNumber();
        } else {
            serialNum = new TSSerialNumberEntity();
            serialNum.setBusinessCode(businessCode);
            serialNum.setNextNumber(year + "00001");
            serialNum.setBusinessCode(businessCode);
            next = serialNum.getNextNumber();
        }
        this.systemService.saveOrUpdate(serialNum);
        return next;
    }

    /**
     * 生成专利申请归档号,规则为ZL-年份-***三位递增数字
     * 
     * @return
     */
    public synchronized String generateZlsqGdh() {
        String businessCode = "zlsq";
        String next = null;
        TSSerialNumberEntity serialNum = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String year = formatter.format(date);
        CriteriaQuery cq = new CriteriaQuery(TSSerialNumberEntity.class);
        cq.eq("businessCode", businessCode);
        cq.add();
        List<TSSerialNumberEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            serialNum = list.get(0);
            if (serialNum.getNextNumber().substring(3, 7).equals(year)) {//相同年份直接取
                String maxNum = serialNum.getNextNumber();
                String max = maxNum.substring(8, maxNum.length());
                int num = Integer.parseInt(max);
                num++;
                String newNum = maxNum.substring(0, 8) + (getNumberStr(num, 3));
                serialNum.setNextNumber(newNum);
            } else {//不同年份从001开始
                serialNum.setNextNumber("ZL-" + year + "-001");
            }
            next = serialNum.getNextNumber();
        } else {
            serialNum = new TSSerialNumberEntity();
            serialNum.setBusinessCode(businessCode);
            serialNum.setNextNumber("ZL-" + year + "-001");
            serialNum.setBusinessCode(businessCode);
            next = serialNum.getNextNumber();
        }
        this.systemService.saveOrUpdate(serialNum);
        return next;
    }
    
    public static void main(String args[]){
        String str = PrimaryGenerater.getNumberStr(240,4);
        System.out.println(str);
    }

}
