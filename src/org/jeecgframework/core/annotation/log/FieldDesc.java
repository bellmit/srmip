package org.jeecgframework.core.annotation.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取字段描述
 * 
 * @author admin
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface FieldDesc {
    String value();
}
