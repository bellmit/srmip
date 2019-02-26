package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;

/**
 * 项目选择器标签
 * 
 * @author admin
 * 
 */
public class ChooseProjectTag extends TagSupport {
    protected String inputId;//显示文本框字段
    protected String inputName;
    protected String icon = "icon-search";
    protected String title;
    protected String url = "tPmProjectController.do?projectMultipleSelect";
    protected String top = "50%";
    protected String left = "50%";
    protected String width = "300px";
    protected String height = "400px";
    protected Boolean isclear = false;
    protected String fun;//自定义函数
    protected String mode = "multiply";
    protected String all = "false";

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
        String confirm = "确定";
        String cancel = "取消";
        String methodname = UUIDGenerator.generate().replaceAll("-", "");
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"" + icon + "\" onClick=\"choose_"
                + methodname + "()\">选择</a>");
        if (isclear) {
            sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"icon-redo\" onClick=\"clearAll_"
                    + methodname + "();\">清空</a>");
        }
        sb.append("<script type=\"text/javascript\">");
        sb.append("var windowapi = frameElement.api, W = windowapi.opener;");
        sb.append("function choose_" + methodname + "(){");
        sb.append("if(typeof(windowapi) == 'undefined'){");
        sb.append("$.dialog({");
        sb.append("content: 'url:" + url + "&mode=" + mode + "&all=" + all + "',");
        sb.append("zIndex: 2100,");
        if (title != null) {
            sb.append("title: \'" + title + "\',");
        }
        sb.append("lock : true,");
        sb.append("width :\'" + width + "\',");
        sb.append("height :\'" + height + "\',");
        sb.append("left :\'" + left + "\',");
        sb.append("top :\'" + top + "\',");
        sb.append("opacity : 0.4,");
        sb.append("button : [ {");
        sb.append("name : \'" + confirm + "\',");
        sb.append("callback : clickcallback_" + methodname + ",");
        sb.append("focus : true");
        sb.append("}, {");
        sb.append("name : \'" + cancel + "\',");
        sb.append("callback : function() {");
        sb.append("}");
        sb.append("} ]");
        sb.append("});");
        sb.append("}else{");
        sb.append("$.dialog({");
        sb.append("content: 'url:" + url + "&mode=" + mode + "&all=" + all + "',");
        sb.append("zIndex: 2100,");
        if (title != null) {
            sb.append("title: \'" + title + "\',");
        } else {
            sb.append("title: \'项目选择\',");
        }
        sb.append("lock : true,");
        sb.append("parent:windowapi,");
        sb.append("width :\'" + width + "\',");
        sb.append("height :\'" + height + "\',");
        sb.append("left :\'" + left + "\',");
        sb.append("top :\'" + top + "\',");
        sb.append("opacity : 0.4,");
        sb.append("button : [ {");
        sb.append("name : \'" + confirm + "\',");
        sb.append("callback : clickcallback_" + methodname + ",");
        sb.append("focus : true");
        sb.append("}, {");
        sb.append("name : \'" + cancel + "\',");
        sb.append("callback : function() {");
        sb.append("}");
        sb.append("} ]");
        sb.append("});");
        sb.append("}");
        sb.append("}");
        clearAll(sb, methodname);
        callback(sb, methodname);
        sb.append("</script>");
        return sb;
    }

    /**
     * 清除
     * 
     * @param sb
     */
    private void clearAll(StringBuffer sb, String methodname) {
        if (isclear) {
            sb.append("function clearAll_" + methodname + "(){");
            sb.append("if($(\'#" + inputId + "\').length>=1){");
            sb.append("$(\'#" + inputId + "\').val('');");
            sb.append("$(\'#" + inputId + "\').blur();");
            sb.append("}");
            sb.append("if($(\"input[name='" + inputId + "']\").length>=1){");
            sb.append("$(\"input[name='" + inputId + "']\").val('');");
            sb.append("$(\"input[name='" + inputId + "']\").blur();");
            sb.append("}");
            sb.append("if($(\'#" + inputName + "\').length>=1){");
            sb.append("$(\'#" + inputName + "\').val('');");
            sb.append("$(\'#" + inputName + "\').blur();");
            sb.append("}");
            sb.append("if($(\"input[name='" + inputName + "']\").length>=1){");
            sb.append("$(\"input[name='" + inputName + "']\").val('');");
            sb.append("$(\"input[name='" + inputName + "']\").blur();");
            sb.append("}");
            sb.append("}");
        }
    }

    /**
     * 点击确定回填
     * 
     * @param sb
     */
    /*private void callback(StringBuffer sb, String methodname) {
        sb.append("function clickcallback_" + methodname + "(){");
        sb.append("iframe = this.iframe.contentWindow;");
        sb.append("var check;");
        if (mode.equals("single")) {
            sb.append("check = iframe.getSelected();");
        } else {
            sb.append("check = iframe.getChecked();");
        }
        sb.append("if($(\'#" + inputId + "\').length>=1){");
        sb.append("$(\'#" + inputId + "\').val(check[0]);");
        sb.append("$(\'#" + inputId + "\').blur();");
        sb.append("}");
        sb.append("if($(\"input[name='" + inputId + "']\").length>=1){");
        sb.append("$(\"input[name='" + inputId + "']\").val(check[0]);");
        sb.append("$(\"input[name='" + inputId + "']\").blur();");
        sb.append("}");
        sb.append("if($(\'#" + inputName + "\').length>=1){");
        sb.append("$(\'#" + inputName + "\').val(check[1]);");
        sb.append("$(\'#" + inputName + "\').blur();");
        sb.append("}");
        sb.append("if($(\"input[name='" + inputName + "']\").length>=1){");
        sb.append("$(\"input[name='" + inputName + "']\").val(check[1]);");
        sb.append("$(\"input[name='" + inputName + "']\").blur();");
        sb.append("}");
        if (StringUtil.isNotEmpty(fun)) {
            sb.append("" + fun + "();");//执行自定义函数
        }
        sb.append("}");
    }*/
    
    /**
     * 点击确定回填
     * 
     * @param sb
     */
    private void callback(StringBuffer sb, String methodname) {
        sb.append("function clickcallback_" + methodname + "(){");
        sb.append("iframe = this.iframe.contentWindow;");
        sb.append("var check;");
        if (mode.equals("single")) {
            sb.append("check = iframe.getSelected();");
        } else {
            sb.append("check = iframe.getChecked();");
        }
        sb.append("if($(\'#" + inputId + "\').length>=1){");
        sb.append("var nrid = $(\'#" + inputId + "\').val();");
        sb.append("if(check[0]!=''){");
        sb.append("if(nrid!=''){");
        sb.append("$(\'#" + inputId + "\').val(nrid +','+ check[0]);");
        sb.append("}else{");
        sb.append("$(\'#" + inputId + "\').val(check[0]);");
        sb.append("}");
        sb.append("$(\'#" + inputId + "\').blur();");
        sb.append("}");
        sb.append("}");
        sb.append("if($(\'#" + inputName + "\').length>=1){");
        sb.append("var nrid = $(\'#" + inputName + "\').val();");
        sb.append("if(check[1]!=''){");
        sb.append("if(nrid!=''){");
        sb.append("$(\'#" + inputName + "\').val(nrid +','+ check[1]);");
        sb.append("}else{");
        sb.append("$(\'#" + inputName + "\').val(check[1]);");
        sb.append("}");
        sb.append("$(\'#" + inputName + "\').blur();");
        sb.append("}");
        sb.append("}");
        if (StringUtil.isNotEmpty(fun)) {
            sb.append("" + fun + "();");//执行自定义函数
        }
        sb.append("}");
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setIsclear(Boolean isclear) {
        this.isclear = isclear;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

}
