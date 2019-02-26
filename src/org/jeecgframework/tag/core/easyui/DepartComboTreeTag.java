package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 组织机构下拉数自定义标签
 * 
 * @author admin
 * 
 */
public class DepartComboTreeTag extends TagSupport {
    protected String id;// ID
    protected String url = "departController.do?getDepartTree";// 远程数据
    protected String name;// 控件名称
    protected String width;// 宽度
    protected String height;//高度
    protected String value;// 控件值
    private boolean multiple = false;//是否多选
    private String idInput;//单位id的隐藏域
    private String nameInput;//单位名称的隐藏域
    private boolean lazy = true;

    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    @Override
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
        width = (width == null) ? "140" : width;
        height = (height == null) ? "25" : height;
        if (!lazy) {
            url = url + "&lazy=false";
        }
        sb.append("<script type=\"text/javascript\">").append("$(function() { " + "$(\'#" + id + "\').combotree({		 ")
                .append("url :\'" + url + "\',").append("width :\'" + width + "\',")
                .append("height :\'" + height + "\',")
                .append("multiple:" + multiple + ",").append("onClick: function(){");
        if (StringUtils.isNotEmpty(idInput)) {
            sb.append("var orgIds = $(\"#" + id + "\").combotree(\"getValues\");");
            sb.append("$(\"#" + idInput + "\").val(orgIds);");
        }
        if (StringUtils.isNotEmpty(nameInput)) {
            sb.append("var orgNames = $(\"#" + id + "\").combotree(\"getText\");");
            sb.append("$(\"#" + nameInput + "\").val(orgNames);");
        }
        sb.append("}      ").append("});		").append("});	").append("</script>");
        sb.append("<input  name=\"" + name + "\" id=\"" + id + "\" ");
        if (value != null) {
            sb.append("value=" + value + "");
        }
        sb.append(">");
        return sb;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    public void setHeight(String height){
    	this.height = height;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setIdInput(String idInput) {
        this.idInput = idInput;
    }

    public void setNameInput(String nameInput) {
        this.nameInput = nameInput;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

}
