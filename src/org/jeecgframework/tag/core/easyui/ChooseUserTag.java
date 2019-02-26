package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.core.util.oConvertUtils;

/**
 * 用户选择器标签
 * 
 * @author admin
 * 
 */
public class ChooseUserTag extends TagSupport {
    protected String textname;//显示文本框字段
    protected String icon;
    protected String title;
    protected String url = "commonUserController.do?commonUser";
    protected String top = "40%";
    protected String left = "44%";
    protected String width = "735px";
    protected String height = "400px";
    protected String name;
    protected String tablename = "selectedUserList";
    protected Boolean isclear = false;
    protected String fun;//自定义函数
    protected String inputTextname;
    protected String mode = "multiply";
    protected String idInput;//存放用户id的控件id
    protected String departIdInput;//存放单位id的控件id

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
        if (StringUtils.isNotEmpty(tablename)) {
            this.name = tablename;
        }
        String confirm = "确定";
        String cancel = "取消";
        String methodname = UUIDGenerator.generate().replaceAll("-", "");
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"" + icon + "\" onClick=\"choose_"
                + methodname + "()\" title=\"选择\"></a>");
        if (isclear && StringUtil.isNotEmpty(textname)) {
            sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"icon-redo\" onClick=\"clearAll_"
                    + methodname + "();\" title=\"清空\"></a>");
        }
        sb.append("<input id=\"deptId_hidden_\" type=\"hidden\">");
        sb.append("<script type=\"text/javascript\">");
        sb.append("var windowapi = frameElement.api, W = windowapi.opener;");
        sb.append("function choose_" + methodname + "(){");
        if (StringUtils.isNotEmpty(idInput)) {
            sb.append("var userIds = $('#" + idInput + "').val();");
            sb.append("var url='" + url + "';");
            sb.append("if(userIds!=''){");
            sb.append("url=url+'&userIds='+userIds;");
            sb.append("}");
        }
        if (StringUtils.isNotEmpty(departIdInput)) {
            sb.append("$('#deptId_hidden_').val($('#" + departIdInput + "').val());");
        }
        sb.append("var departIds = $('#deptId_hidden_').val();");
        sb.append("if(departIds!=''){");
        sb.append("url=url+'&departIds='+departIds;");
        sb.append("}");
        sb.append("if(typeof(windowapi) == 'undefined'){");
        sb.append("$.dialog({");
        sb.append("content: 'url:'+url,");
        sb.append("zIndex: 3100,");
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
        sb.append("content: 'url:'+url,");
        sb.append("zIndex: 99999,");
        if (title != null) {
            sb.append("title: \'" + title + "\',");
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
        String[] textnames = null;
        String[] inputTextnames = null;
        if (!oConvertUtils.isEmpty(textname)) {
            textnames = textname.split(",");
        }
        if (StringUtil.isNotEmpty(inputTextname)) {
            inputTextnames = inputTextname.split(",");
        } else {
            inputTextnames = textnames;
        }
        if (isclear && StringUtil.isNotEmpty(textname)) {
            sb.append("function clearAll_" + methodname + "(){");
            for (int i = 0; i < textnames.length; i++) {
                inputTextnames[i] = inputTextnames[i].replaceAll("\\[", "\\\\\\\\[").replaceAll("\\]", "\\\\\\\\]")
                        .replaceAll("\\.", "\\\\\\\\.");
                sb.append("if($(\'#" + inputTextnames[i] + "\').length>=1){");
                sb.append("$(\'#" + inputTextnames[i] + "\').val('');");
                sb.append("$(\'#" + inputTextnames[i] + "\').blur();");
                sb.append("}");
                sb.append("if($(\"input[name='" + inputTextnames[i] + "']\").length>=1){");
                sb.append("$(\"input[name='" + inputTextnames[i] + "']\").val('');");
                sb.append("$(\"input[name='" + inputTextnames[i] + "']\").blur();");
                sb.append("$(\"#deptId_hidden_\").val('');");
                sb.append("}");
            }
            sb.append("}");
        }
    }

    /**
     * 点击确定回填
     * 
     * @param sb
     */
    private void callback(StringBuffer sb, String methodname) {
        sb.append("function clickcallback_" + methodname + "(){");
        sb.append("iframe = this.iframe.contentWindow;");
        if ("single".equals(mode)) {
            sb.append("var rows = iframe.getSeletedRows();");
            sb.append("if (rows.length > 1) {");
            sb.append("iframe.alertMsg('只能选择一个人员！');");
            sb.append("return false;");
            sb.append(" }");
        }
        String[] textnames = null;
        String[] inputTextnames = null;
        if (StringUtil.isNotEmpty(textname)) {
            textnames = textname.split(",");
            if (StringUtil.isNotEmpty(inputTextname)) {
                inputTextnames = inputTextname.split(",");
            } else {
                inputTextnames = textnames;
            }
            for (int i = 0; i < textnames.length; i++) {
                inputTextnames[i] = inputTextnames[i].replaceAll("\\[", "\\\\\\\\[").replaceAll("\\]", "\\\\\\\\]")
                        .replaceAll("\\.", "\\\\\\\\.");
                sb.append("var " + textnames[i] + "=iframe.get" + name + "Selections(\'" + textnames[i] + "\');	");
                sb.append("if($(\'#" + inputTextnames[i] + "\').length>=1){");
                sb.append("$(\'#" + inputTextnames[i] + "\').val(" + textnames[i] + ");");
                sb.append("$(\'#" + inputTextnames[i] + "\').blur();");
                sb.append("}");
                sb.append("if($(\"input[name='" + inputTextnames[i] + "']\").length>=1){");
                sb.append("$(\"input[name='" + inputTextnames[i] + "']\").val(" + textnames[i] + ");");
                sb.append("$(\"input[name='" + inputTextnames[i] + "']\").blur();");
                sb.append("var userId = iframe.get" + name + "Selections('id'); ");
                sb.append("var userDepartId = iframe.get" + name + "Selections('departId'); ");
                sb.append("$(\"#deptId_hidden_\").val(userDepartId); ");
                sb.append("}");
            }
            sb.append("$.post(\"commonUserController.do?saveUserContact\", { 'contactIds': userId,'departIds':userDepartId } );");
        }
        if (StringUtil.isNotEmpty(fun)) {
            sb.append("" + fun + "();");//执行自定义函数
        }
        sb.append("}");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTextname(String textname) {
        this.textname = textname;
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

    public String getInputTextname() {
        return inputTextname;
    }

    public void setInputTextname(String inputTextname) {
        this.inputTextname = inputTextname;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setIdInput(String idInput) {
        this.idInput = idInput;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDepartIdInput(String departIdInput) {
        this.departIdInput = departIdInput;
    }

}
