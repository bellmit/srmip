package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 类描述：选择器标签
 * 
 * @author: 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class CommonWarnTag extends TagSupport {
    private String id = "warnBtn";//按钮id
    private String name = "warnBtn";//按钮name
    private String style;//按钮样式
    private String title = "添加公共提醒";//弹出框标题
    private String extendParam;//拓展属性

    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspTagException {
        try {

            JspWriter out = this.pageContext.getOut();
            out.print(end().toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public StringBuffer end() {
        StringBuffer sb = new StringBuffer();
        sb.append("<a id=\"" + id + "\" name=\"" + name + "\"");
        sb.append("value=\"\" ");
        sb.append("class=\"easyui-linkbutton\" ");
        if (StringUtils.isNotEmpty(style)) {
            sb.append(" style=" + style);
        }
        if (StringUtils.isNotEmpty(extendParam)) {
            sb.append(extendParam);
        }
        sb.append(" onclick=\"addWarn()\">");
        sb.append("添加提醒");
        sb.append("</a>");
        sb.append("<script type=\"text/javascript\">");
        sb.append("function addWarn(){");
        sb.append("add(\"" + title + "\",\"tOWarnController.do?goCommonWarn\");");
        sb.append("}");
        sb.append("</script>");
        return sb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
