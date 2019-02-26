package org.jeecgframework.web.system.listener;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.IgnoreLazyPropertyFilter;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.impl.LogContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("logEventListener")
public class LogEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    @Autowired
    private SystemService systemService;

    /**
     * 字段描述.
     */
    private Map<String, Object> logEntityMap = LogContainer.logEntityMap;

    @Override
    public void onPostInsert(PostInsertEvent paramPostInsertEvent) {
        Object obj = paramPostInsertEvent.getEntity();
        if (obj instanceof TSLog) {
            return;
        }
        String operateObjName = getFieldDesc(obj.getClass().getName(), "#operateObjName#");
        if (StringUtils.isEmpty(operateObjName)) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("模块：").append(operateObjName).append("，");
        if (obj instanceof TSFilesEntity) {
            TSFilesEntity attachment = (TSFilesEntity) obj;
            sb.append("上传了附件：" + attachment.getAttachmenttitle());
            systemService.addBusinessLog(operateObjName, Globals.Log_Type_UPLOAD, Globals.Log_Leavel_INFO,
                   sb.toString() , "", "");
            return;
        }
        sb.append("新增了一条记录，");
        String json = JSON.toJSONString(obj, SerializerFeature.DisableCheckSpecialChar);
        sb.append("数据为：" + json);
        systemService.addBusinessLog(operateObjName, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO,
 sb.toString(),
                "", json);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent paramPostUpdateEvent) {
        Object obj = paramPostUpdateEvent.getEntity();
        String operateObjName = getFieldDesc(obj.getClass().getName(), "#operateObjName#");
        if (StringUtils.isEmpty(operateObjName)) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("模块：").append(operateObjName).append("，");
        if (obj instanceof TSFilesEntity) {
            TSFilesEntity attachment = (TSFilesEntity) obj;
            sb.append("附件更新了：" + attachment.getAttachmenttitle());
            systemService.addBusinessLog(operateObjName, Globals.Log_Type_UPLOAD, Globals.Log_Leavel_INFO,
                    sb.toString(), "", "");
            return;
        }
        sb.append("id为").append(paramPostUpdateEvent.getId()).append("的对象更新了，<br>");
        int[] properties = paramPostUpdateEvent.getDirtyProperties();
        if (properties == null) {
            return;
        }
        EntityPersister persister = paramPostUpdateEvent.getPersister();
        String[] propertyNames = persister.getPropertyNames();
        Object[] oldState = paramPostUpdateEvent.getOldState();
        Object[] newState = paramPostUpdateEvent.getState();
        for (int i = 0; i < properties.length; i++) {
            if ((oldState[properties[i]] == null || "".equals(oldState[properties[i]]))
                    && (newState[properties[i]] == null || "".equals(newState[properties[i]]))) {
                continue;
            }
            String filed = propertyNames[properties[i]];
            sb.append("字段：").append(getFieldDesc(obj.getClass().getName(), filed)).append("，原值：")
                    .append(oldState[properties[i]]);
            sb.append("，新值：").append(newState[properties[i]]);
            sb.append("<br>");
        }
        systemService.addBusinessLog(operateObjName, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO,
                sb.toString(), "", "");
    }

    @Override
    public void onPostDelete(PostDeleteEvent paramPostDeleteEvent) {
        Object obj = paramPostDeleteEvent.getEntity();
        String operateObjName = getFieldDesc(obj.getClass().getName(), "#operateObjName#");
        if (StringUtils.isEmpty(operateObjName)) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("模块：").append(operateObjName).append("，");
        if (obj instanceof TSAttachment || obj instanceof TSFilesEntity) {
            TSAttachment attachment = (TSAttachment) obj;
            systemService.addBusinessLog(operateObjName, Globals.Log_Type_UPLOAD, Globals.Log_Leavel_INFO,
 "附件："
                    + attachment.getAttachmenttitle() + "被删除了", "", "");
            return;
        }
        sb.append("id为").append(paramPostDeleteEvent.getId());
        sb.append("记录被删除，");
        String json = JSON.toJSONString(obj, new IgnoreLazyPropertyFilter(), SerializerFeature.DisableCheckSpecialChar);
        sb.append("数据为：").append(json);
        systemService.addBusinessLog(operateObjName, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO,
                sb.toString(), json, "");
    }


    /**
     * 获取字段描述.
     * 
     * @param clazzName
     * @param field
     * @return
     */
    private String getFieldDesc(String clazzName, String field) {
        String res = null;
        if (!"#operateObjName#".equals(field)) {
            res = field;
        }
        if (logEntityMap.containsKey(clazzName)) {
            Map<String, Object> fieldMap = (Map<String, Object>) logEntityMap.get(clazzName);
            if (fieldMap.containsKey(field)) {
                res = (String) fieldMap.get(field);
            }
        }
        return res;
    }

}
