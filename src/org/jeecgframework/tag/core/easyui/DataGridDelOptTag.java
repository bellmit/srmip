package org.jeecgframework.tag.core.easyui;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 类描述：列表删除操作项标签
 * 
 * @author 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridDelOptTag extends TagSupport {
	protected String url;
	protected String title;
	private String message;//询问链接的提示语
	private String exp;//判断链接是否显示的表达式
	private String funname;//自定义函数名称
	private String checkFun;//判断是否出现此按钮，参考projectPlanListForDepartment.jsp内使用
	
	public String getCheckFun() {
		return checkFun;
	}
	public void setCheckFun(String checkFun) {
		this.checkFun = checkFun;
	}

	private String operationCode;//按钮的操作Code
	private String langArg;
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		title = MutiLangUtil.doMutiLang(title, langArg);
		
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setDelUrl(url, title, message, exp, funname, operationCode, checkFun);
		return EVAL_PAGE;
	}
	public void setFunname(String funname) {
		this.funname = funname;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	
	public void setLangArg(String langArg) {
		this.langArg = langArg;
	}
}
