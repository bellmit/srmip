package com.kingtake.office.service.impl.news;

import java.io.Serializable;
import java.util.Date;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.news.TONewsEntity;
import com.kingtake.office.service.news.TONewsServiceI;

@Service("tONewsService")
@Transactional
public class TONewsServiceImpl extends CommonServiceImpl implements TONewsServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TONewsEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TONewsEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TONewsEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TONewsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TONewsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TONewsEntity t) {
        return true;
    }

    @Override
    public void doSubmit(TONewsEntity tONews) {
        try {
            TONewsEntity t = commonDao.get(TONewsEntity.class, tONews.getId());
            // 防止更新时附件丢失
            MyBeanUtils.copyBeanNotNull2Bean(tONews, t);
            //点击提交时，更改交班状态：1-已提交；状态变更时间：当前时间
            t.setSubmitFlag(SrmipConstants.SUBMIT_YES);
            t.setSubmitTime(new Date());
            commonDao.updateEntitie(t);
            String receiverId = t.getCheckUserId();
            String messageType = "要讯";
            String messageTitle = "要讯";
            String messageContent = "您有一条新的要讯待处理！<a href=\"#\" style=\"color:skyblue;\" onclick='addTab(\"要讯接收\",\"tONewsController.do?tONews\");'>"
                    + t.getTitle() + "</a>";
            commonMessageService.sendMessage(receiverId, messageType, messageTitle, messageContent);
        } catch (Exception e) {
            throw new BusinessException("提交要讯失败！", e);
        }
    }
}