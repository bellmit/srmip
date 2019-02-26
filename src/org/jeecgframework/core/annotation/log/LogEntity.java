package org.jeecgframework.core.annotation.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要记录日志的实体类加上此注解,值为模块分类
 * 
 * @author admin
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.TYPE)
public @interface LogEntity {
    public String value();
}
