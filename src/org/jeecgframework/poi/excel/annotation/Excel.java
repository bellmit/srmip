package org.jeecgframework.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 导出基本注释
 * 
 * @author JueYue
 * @date 2014年6月20日 下午10:25:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * 导出时，对应数据库的字段 主要是用户区分每个字段， 不能有annocation重名的 导出时的列名
     * 
     * 导出排序跟定义了annotation的字段的顺序有关 可以使用a_id,b_id来确实是否使用
     */
    public String name();

    /**
     * 导出时在excel中每个列的宽 单位为字符，一个汉字=2个字符 如 以列名列内容中较合适的长度
     * 
     * 例如姓名列6 【姓名一般三个字】 性别列4【男女占1，但是列标题两个汉字】 限制1-255
     */
    public int width() default 10;

    /**
     * 导出时在excel中每个列的高度 单位为字符，一个汉字=2个字符
     */
    public int height() default 10;

    /**
     * 值得替换 导出是{a_id,b_id} 导入反过来,所以只用写一个
     */
    public String[] replace() default {};

    /**
     * 导出类型 1 是文本 2 是图片,3是函数 默认是文本
     */
    public int type() default 1;

    /**
     * 导出类型 1 从file读取 2 是从数据库中读取 默认是文件 同样导入也是一样的
     */
    public int imageType() default 1;

    /**
     * 导入路径,如果是图片可以填写,默认是upload/className/ IconEntity这个类对应的就是upload/Icon/
     * 
     */
    public String savePath() default "upload";

    /**
     * 展示到第几个可以使用a_id,b_id来确定不同排序
     */
    public String orderNum() default "0";

    /**
     * 是否换行 即支持\n
     */
    public boolean isWrap() default true;

    /**
     * 是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
     */
    public boolean needMerge() default false;

    /**
     * 纵向合并内容相同的单元格
     */
    public boolean mergeVertical() default false;

    /**
     * 合并单元格依赖关系,比如第二列合并是基于第一列 则{1}就可以了
     */
    public int[] mergeRely() default {};

    /**
     * 导出时间设置,如果字段是Date类型则不需要设置 数据库如果是string 类型,这个需要设置这个数据库格式
     */
    public String databaseFormat() default "yyyyMMddHHmmss";

    /**
     * 导出的时间格式,以这个是否为空来判断是否需要格式化日期
     */
    public String exportFormat() default "";

    /**
     * 导入的时间格式,以这个是否为空来判断是否需要格式化日期
     */
    public String importFormat() default "";

    /**
     * 时间格式,相当于同时设置了exportFormat 和 importFormat
     */
    public String format() default "";

    /**
     * cell 写入的设置的函数
     */
    public String cellFormula() default "";

    /**
     * 导出时是否进行转换
     */
    public boolean isExportConvert() default false;
    
    /**
     * 导出是否按照金额格式化，千分位显示，并保留两位小数
     * 
     * @return
     */
    public boolean isAmount() default false;
}
