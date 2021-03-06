package org.jeecgframework.tag.core.easyui;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.MutiLangUtil;

/**
 *
 * 类描述：列表字段处理项目
 *
 * 张代浩
 *
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridColumnTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    protected String title;
    protected String field;
    protected Integer width;
    protected String rowspan;
    protected String colspan;
    protected String align;
    protected boolean sortable = true;
    protected boolean checkbox;
    protected String formatter;
    protected String formatter1;
    // update-start-Author:zhangguoming Date:20140921 for：TASK #458 列表hidden=false，才是隐藏好像有点问题
    protected boolean hidden = false;
    // update-end-Author:zhangguoming Date:20140921 for：TASK #458 列表hidden=false，才是隐藏好像有点问题
    protected String replace;
    protected String treefield;
    protected boolean image;
    protected boolean query = false;
    private String queryMode = "single";// 字段查询模式：single单字段查询；scope范围查询

    // protected boolean autoLoadData = true; // 列表是否自动加载数据
    private boolean frozenColumn = false; // 是否是冰冻列 默认不是
    protected boolean bSearchable = true;
    protected String url;// 自定义链接
    protected String funname = "openwindow";// 自定义函数名称
    protected String arg;// 自定义链接传入参数字段
    protected String dictionary; // 数据字典组编码
    protected String codeDict; // 代码集编码
    protected String extend; // 扩展属性
    protected String style; // Td的CSS
    protected String imageSize;// 自定义图片显示大小
    protected String downloadName;// 附件下载
    private boolean autocomplete = false;// 自动完成
    private String extendParams;// 扩展参数
    private String langArg;
    private boolean isLike;// 是否模糊匹配
    protected boolean overflowView;//超出显示长度，是否显示全部
    
    protected String checkFun; //根据数据条件确认是不是某行数据该出现操作按钮,要求定义一个js含数，返回true,则继续生成操作控钮，否则不生成, 参见incomePlanListForDepartment2.jsp

    public String getFormatter1() {                   
    	return formatter1;                            
    }                                                 
    public void setFormatter1(String formatter1) {    
    	this.formatter1 = formatter1;                 
    }                                                 
    
    @Override
    public int doEndTag() throws JspTagException {
        title = MutiLangUtil.doMutiLang(title, langArg);

        Tag t = findAncestorWithClass(this, DataGridTag.class);
        DataGridTag parent = (DataGridTag) t;
        parent.setColumn(title, field, width, rowspan, colspan, align, sortable, checkbox, formatter, hidden, replace,
                treefield, image, imageSize, query, url, funname, arg, queryMode, dictionary, codeDict, frozenColumn,
                extend, style, downloadName, autocomplete, extendParams, isLike, overflowView, checkFun, formatter1);
        return EVAL_PAGE;
    }
    
    public String getCheckFun() {
		return checkFun;
	}

	public void setCheckFun(String checkFun) {
		this.checkFun = checkFun;
	}
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFunname(String funname) {
        this.funname = funname;
    }

    public void setbSearchable(boolean bSearchable) {
        this.bSearchable = bSearchable;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public void setTreefield(String treefield) {
        this.treefield = treefield;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public boolean isFrozenColumn() {
        return frozenColumn;
    }

    public void setFrozenColumn(boolean frozenColumn) {
        this.frozenColumn = frozenColumn;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setAutocomplete(boolean autocomplete) {
        this.autocomplete = autocomplete;
    }

    public void setExtendParams(String extendParams) {
        this.extendParams = extendParams;
    }

    public void setLangArg(String langArg) {
        this.langArg = langArg;
    }

    public void setCodeDict(String codeDict) {
        this.codeDict = codeDict;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public void setOverflowView(boolean overflowView) {
        this.overflowView = overflowView;
    }

}
