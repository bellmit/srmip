package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.ListtoMenu;
import org.jeecgframework.web.system.pojo.base.TSFunction;


/**
 *
 * 类描述：菜单标签
 *
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String style="easyui";//菜单样式
	protected List<TSFunction> parentFun;//一级菜单
	protected List<TSFunction> childFun;//二级菜单
	protected Map<Integer, List<TSFunction>> menuFun;//菜单Map


	public void setParentFun(List<TSFunction> parentFun) {
		this.parentFun = parentFun;
	}

	public void setChildFun(List<TSFunction> childFun) {
		this.childFun = childFun;
	}

	@Override
    public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	@Override
    public int doEndTag() throws JspTagException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(end().toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
//        update-begin--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色
        if (style.equals("easyui")) {
            sb.append("<ul id=\"nav\" class=\"easyui-tree \" fit=\"true\" border=\"false\">");
            sb.append(ListtoMenu.getEasyuiMultistageTree(menuFun, style));
            sb.append("</ul>");
        }
		if(style.equals("shortcut"))
//            update-begin--Author:zhangguoming  Date:20140429 for：在IE7下 导航显示问题
//		{	sb.append("<div id=\"nav\" style=\"display:none;\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">");
		{
            sb.append("<div id=\"nav\" style=\"display:block;\" class=\"easyui-accordion\" fit=\"true\" border=\"false\">");
//            update-end--Author:zhangguoming  Date:20140429 for：在IE7下 导航显示问题
			sb.append(ListtoMenu.getEasyuiMultistageTree(menuFun, style));
			sb.append("</div>");
		}
//        update-end--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色
		if(style.equals("bootstrap"))
		{
			sb.append(ListtoMenu.getBootMenu(parentFun, childFun));
		}
		if(style.equals("json"))
		{
			sb.append("<script type=\"text/javascript\">");
			sb.append("var _menus="+ListtoMenu.getMenu(parentFun, childFun));
			sb.append("</script>");
		}
		if(style.equals("june_bootstrap"))
		{
			sb.append(ListtoMenu.getBootstrapMenu(menuFun));
		}
		return sb;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public void setMenuFun(Map<Integer, List<TSFunction>> menuFun) {
		this.menuFun = menuFun;
	}



}
