package org.jeecgframework.web.system.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.minidao.factory.PackagesToScanUtil;

public class LogContainer {

    public static final Map<String, Object> logEntityMap = new HashMap<String, Object>();

    public static void initLogEntity() {
        // 1.扫描项目下，所有class，判断带有标签@LogEntity

        // 2.扫描@LogEntity注解进行加载

        // 3.扫描@FieldDesc注解中各字段的描述

        //扫描Src目录下
        Set<Class<?>> classSet = PackagesToScanUtil.getClasses(".*");
        for (Class<?> clazz : classSet) {
            if (clazz.isAnnotationPresent(LogEntity.class)) {
                LogEntity logEntity = clazz.getAnnotation(LogEntity.class);
                if (!logEntityMap.containsKey(clazz.getName())) {
                    Map<String, Object> clazzMap = new HashMap<String, Object>();
                    if (StringUtil.isNotEmpty(logEntity.value())) {
                        clazzMap.put("#operateObjName#", logEntity.value());
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(FieldDesc.class)) {
                                FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class);
                                clazzMap.put(field.getName(), fieldDesc.value());
                            }
                        }
                        logEntityMap.put(clazz.getName(), clazzMap);
                    }

                }
            }
        }
    }
}
