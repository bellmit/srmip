package org.apache;
/**
 * <PRE>
 * 添加类描述
 * </PRE>
 *
 * 项 目名称：传送光网智能分析平台 </BR>
 * 技术支持：广东凯通科技股份有限公司 (c) 2017</BR>
 * 
 * @version 1.0 2018年3月12日
 * @author Administrator
 * @since JDK1.8
 */
import java.lang.reflect.InvocationTargetException;  

import javax.naming.NamingException;  
  
public interface AnnotationProcessor {  
    public void postConstruct(Object instance) throws IllegalAccessException,  
            InvocationTargetException;  
  
    public void preDestroy(Object instance) throws IllegalAccessException,  
            InvocationTargetException;  
  
    public void processAnnotations(Object instance)  
            throws IllegalAccessException, InvocationTargetException,  
            NamingException;  
}  
