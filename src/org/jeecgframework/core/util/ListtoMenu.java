package org.jeecgframework.core.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 动态菜单栏生成
 *
 * @author 张代浩
 *
 */
public class ListtoMenu {

	@Autowired
	private static MutiLangServiceI mutiLangService;

	/**
	 * 拼装easyui菜单JSON方式
	 *
	 * @param set
	 * @param set1
	 * @return
	 */
	public static String getMenu(List<TSFunction> set, List<TSFunction> set1) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"menus\":[");
		for (TSFunction node : set) {
			String iconClas = "default";// 权限图标样式
			if (node.getTSIcon() != null) {
				iconClas = node.getTSIcon().getIconClas();
			}
			buffer.append("{\"menuid\":\"" + node.getId() + "\",\"icon\":\""
					+ iconClas + "\"," + "\"menuname\":\""
			+ getMutiLang(node.getFunctionName()) + "\",\"menus\":[");
			iterGet(set1, node.getId(), buffer);
			buffer.append("]},");
		}
		buffer.append("]}");

		// 将,\n]替换成\n]

		String tmp = buffer.toString();

		tmp = tmp.replaceAll(",\n]", "\n]");
		tmp = tmp.replaceAll(",]}", "]}");
		return tmp;

	}

	static int count = 0;

	/**
	 * @param args
	 */

	static void iterGet(List<TSFunction> set1, String pid, StringBuffer buffer) {

		for (TSFunction node : set1) {

			// 查找所有父节点为pid的所有对象，然后拼接为json格式的数据
			count++;
			if (node.getTSFunction().getId().equals(pid))

			{
				buffer.append("{\"menuid\":\"" + node.getId()
						+ " \",\"icon\":\"" + node.getTSIcon().getIconClas()
						+ "\"," + "\"menuname\":\"" + getMutiLang(node.getFunctionName())
						+ "\",\"url\":\"" + node.getFunctionUrl() + "\"");
				if (count == set1.size()) {
					buffer.append("}\n");
				}
				buffer.append("},\n");

			}
		}

	}

	/**
	 * 拼装Bootstarp菜单
	 *
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getBootMenu(List<TSFunction> pFunctions,
			List<TSFunction> functions) {
		StringBuffer menuString = new StringBuffer();
		menuString.append("<ul>");
		for (TSFunction pFunction : pFunctions) {
			menuString
					.append("<li><a href=\"#\"><span class=\"icon16 icomoon-icon-stats-up\"></span><b>"
			+ getMutiLang(pFunction.getFunctionName()) + "</b></a>");
			int submenusize = pFunction.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</li>");
			}
			if (submenusize > 0) {
				menuString.append("<ul class=\"sub\">");
			}
			for (TSFunction function : functions) {

				if (function.getTSFunction().getId().equals(pFunction.getId())) {
					menuString
							.append("<li><a href=\""
									+ function.getFunctionUrl()
									+ "\" target=\"contentiframe\"><span class=\"icon16 icomoon-icon-file\"></span>"
									+ getMutiLang(function.getFunctionName()) + "</a></li>");
				}
			}
			if (submenusize > 0) {
				menuString.append("</ul></li>");
			}
		}
		menuString.append("</ul>");
		return menuString.toString();

	}

	/**
	 * 拼装EASYUI菜单
	 *
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getEasyuiMenu(List<TSFunction> pFunctions,
			List<TSFunction> functions) {
		StringBuffer menuString = new StringBuffer();
		for (TSFunction pFunction : pFunctions) {
			menuString.append("<div  title=\"" + getMutiLang(pFunction.getFunctionName())
					+ "\" iconCls=\"" + pFunction.getTSIcon().getIconClas()
					+ "\">");
			int submenusize = pFunction.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</div>");
			}
			if (submenusize > 0) {
				menuString.append("<ul>");
			}
			for (TSFunction function : functions) {

				if (function.getTSFunction().getId().equals(pFunction.getId())) {
					String icon = "folder";
					if (function.getTSIcon() != null) {
						icon = function.getTSIcon().getIconClas();
					}
					// menuString.append("<li><div> <a class=\""+function.getFunctionName()+"\" iconCls=\""+icon+"\" target=\"tabiframe\"  href=\""+function.getFunctionUrl()+"\"> <span class=\"icon "+icon+"\" >&nbsp;</span> <span class=\"nav\">"+function.getFunctionName()+"</span></a></div></li>");
					menuString.append("<li><div onclick=\"addTab(\'"
							+ getMutiLang(function.getFunctionName()) + "\',\'"
							+ function.getFunctionUrl() + "&clickFunctionId="
							+ function.getId() + "\',\'" + icon
							+ "\')\"  title=\"" + getMutiLang(function.getFunctionName())
							+ "\" url=\"" + function.getFunctionUrl()
							+ "\" iconCls=\"" + icon + "\"> <a class=\""
							+ getMutiLang(function.getFunctionName())
							+ "\" href=\"#\" > <span class=\"icon " + icon
							+ "\" >&nbsp;</span> <span class=\"nav\" >"
							+ getMutiLang(function.getFunctionName())
							+ "</span></a></div></li>");
				}
			}
			if (submenusize > 0) {
				menuString.append("</ul></div>");
			}
		}
		return menuString.toString();

	}

	/**
	 * 拼装EASYUI 多级 菜单
	 *
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getEasyuiMultistageMenu(
			Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(0);
		for (TSFunction function : list) {
			menuString.append("<div   title=\"" + getMutiLang(function.getFunctionName())
					+ "\" iconCls=\"" + function.getTSIcon().getIconClas()
					+ "\">");
			int submenusize = function.getTSFunctions().size();
			if (submenusize == 0) {
				menuString.append("</div>");
			}
			if (submenusize > 0) {
				menuString.append("<ul>");
			}
			menuString.append(getChild(function,1,map));
			if (submenusize > 0) {
				menuString.append("</ul></div>");
			}
		}
		return menuString.toString();
	}

//        update-start--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色

    /**
     * 拼装EASYUI 多级 菜单  下级菜单为树形
     * @param map  the map of Map<Integer, List<TSFunction>>
     * @param style 样式：easyui-经典风格、shortcut-shortcut风格
     * @return
     */
	public static String getEasyuiMultistageTree(Map<Integer, List<TSFunction>> map, String style) {
		if(map==null||map.size()==0||!map.containsKey(0)){return "不具有任何权限,\n请找管理员分配权限";}
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(0);
        int curIndex = 0;
        if ("easyui".equals(style)) {
            for (TSFunction function : list) {
                if(curIndex == 0) { // 第一个菜单，默认展开
                    menuString.append("<li iconCls=\"" + function.getTSIcon().getIconClas() + "\" >");
                } else {
                    menuString.append("<li state='closed' iconCls=\"" + function.getTSIcon().getIconClas() + "\" >");
                }
                menuString.append("<span>").append(getMutiLang(function.getFunctionName())).append("</span>");
                int submenusize = function.getTSFunctions().size();
                if (submenusize == 0) {
                    menuString.append("</li>");
                }
                if (submenusize > 0) {
                    menuString.append("<ul>");
                }
                menuString.append(getChildOfTree(function,1,map));
                if (submenusize > 0) {
                    menuString.append("</ul></li>");
                }
                curIndex++;
            }
        } else if("shortcut".equals(style)) {
           Collections.reverse(list);
            for (TSFunction function : list) {
                menuString.append("<div   title=\"" + getMutiLang(function.getFunctionName())
                        + "\" iconCls=\"" + function.getTSIcon().getIconClas()
                        + "\">");
                int submenusize = function.getTSFunctions().size();
                if (submenusize == 0) {
                    menuString.append("</div>");
                }
                if (submenusize > 0) {
                    menuString.append("<ul class=\"easyui-tree\"  fit=\"false\" border=\"false\">");
                }
                menuString.append(getChildOfTree(function,1,map));
                if (submenusize > 0) {
                    menuString.append("</ul></div>");
                }
            }
        }
		return menuString.toString();
	}
//        update-end--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色

	/**
	 * 获取顶级菜单的下级菜单-----面板式菜单
	 * @param parent
	 * @param level
	 * @param map
	 * @return
	 */
	private static String getChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				if(function.getTSFunctions().size()==0||!map.containsKey(level+1)){
					menuString.append(getLeaf(function));
				}else if(map.containsKey(level+1)){
					menuString.append("<div  class=\"easyui-accordion\"  fit=\"false\" border=\"false\">");
					menuString.append("<div></div>");//easy ui 默认展开第一级,所以这里设置一个控制,就不展开了
					menuString.append("<div title=\"" + getMutiLang(function.getFunctionName())
							+ "\" iconCls=\"" + function.getTSIcon().getIconClas()
							+ "\"><ul>");
					menuString.append(getChild(function,level+1,map));
					menuString.append("</ul></div>");
					menuString.append("</div>");
				}
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取树形菜单
	 * @param parent
	 * @param level
	 * @param map
	 * @return
	 */
	private static String getChildOfTree(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				if(function.getTSFunctions().size()==0||!map.containsKey(level+1)){
					menuString.append(getLeafOfTree(function));
				}else if(map.containsKey(level+1)){
					menuString.append("<li state=\"closed\" iconCls=\"" + function.getTSIcon().getIconClas()+"\" ><span>"+ getMutiLang(function.getFunctionName()) +"</span>");
					menuString.append("<ul >");
					menuString.append(getChildOfTree(function,level+1,map));
					menuString.append("</ul></li>");
				}
			}
		}
		return menuString.toString();
	}
	/**
	 * 获取叶子节点
	 * @param function
	 * @return
	 */
	private static String getLeaf(TSFunction function){
		StringBuffer menuString = new StringBuffer();
		String icon = "folder";
		if (function.getTSIcon() != null) {
			icon = function.getTSIcon().getIconClas();
		}
		menuString.append("<li><div onclick=\"addTab(\'");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("\',\'");
		menuString.append(function.getFunctionUrl());
		menuString.append("&clickFunctionId=");
		menuString.append(function.getId());
		menuString.append("\',\'");
		menuString.append(icon);
		menuString.append("\')\"  title=\"");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("\" url=\"");
		menuString.append(function.getFunctionUrl());
		menuString.append("\" iconCls=\"");
		menuString.append(icon);
		menuString.append("\"> <a class=\"");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("\" href=\"#\" > <span class=\"icon ");
		menuString.append(icon);
		menuString.append("\" >&nbsp;</span> <span class=\"nav\" >");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("</span></a></div></li>");
		return menuString.toString();
	}
	/**
	 * 获取叶子节点  ------树形菜单的叶子节点
	 * @param function
	 * @return
	 */
	private static String getLeafOfTree(TSFunction function){
		StringBuffer menuString = new StringBuffer();
		String icon = "folder";
		if (function.getTSIcon() != null) {
			icon = function.getTSIcon().getIconClas();
		}
		menuString.append("<li iconCls=\"");
		menuString.append(icon);
		menuString.append("\"> <a onclick=\"addTab(\'");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("\',\'");
		menuString.append(function.getFunctionUrl());
		menuString.append("&clickFunctionId=");
		menuString.append(function.getId());
		menuString.append("\',\'");
		menuString.append(icon);
		menuString.append("\')\"  title=\"");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("\" url=\"");
		menuString.append(function.getFunctionUrl());
		menuString.append("\" href=\"#\" ><span class=\"nav\" >");
		menuString.append(getMutiLang(function.getFunctionName()));
		menuString.append("</span></a></li>");
		return menuString.toString();
	}
	/**
	 * 拼装bootstrap头部菜单
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getBootstrapMenu(Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		menuString.append("<ul class=\"nav\">");
		List<TSFunction> pFunctions = map.get(0);
		if(pFunctions==null || pFunctions.size()==0){
			return "";
		}
		for (TSFunction pFunction : pFunctions) {
			//是否有子菜单
			boolean hasSub = pFunction.getTSFunctions().size()==0?false:true;

			//绘制一级菜单
			menuString.append("	<li class=\"dropdown\"> ");
			menuString.append("		<a href=\"javascript:;\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"> ");
			menuString.append("			<span class=\"bootstrap-icon\" style=\"background-image: url('"+pFunction.getTSIcon().getIconPath()+"')\"></span> "+pFunction.getFunctionName()+" ");
			if(hasSub){
				menuString.append("			<b class=\"caret\"></b> ");
			}
			menuString.append("		</a> ");
			//绘制二级菜单
			if(hasSub){
				menuString.append(getBootStrapChild(pFunction, 1, map));
			}
			menuString.append("	</li> ");
		}
		menuString.append("</ul>");
		return menuString.toString();
	}
	/**
	* @Title: getBootStrapChild
	* @Description: 递归获取bootstrap的子菜单
	*  @param parent
	*  @param level
	*  @param map
	* @return String
	* @throws
	 */
	private static String getBootStrapChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer menuString = new StringBuffer();
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		menuString.append("		<ul class=\"dropdown-menu\"> ");
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				boolean hasSub = function.getTSFunctions().size()!=0 && map.containsKey(level+1);
				String menu_url = function.getFunctionUrl();
				if(StringUtils.isNotEmpty(menu_url)){
					menu_url += "&clickFunctionId="+function.getId();
				}
				menuString.append("		<li onclick=\"showContent(\'"+ getMutiLang(function.getFunctionName()) +"\',\'"+menu_url+"\')\"  title=\""+ getMutiLang(function.getFunctionName()) +"\" url=\""+function.getFunctionUrl()+"\" ");
				if(hasSub){
					menuString.append(" class=\"dropdown-submenu\"");
				}
				menuString.append(" > ");
				menuString.append("			<a href=\"javascript:;\"> ");
				menuString.append("				<span class=\"bootstrap-icon\" style=\"background-image: url('"+function.getTSIcon().getIconPath()+"')\"></span>		 ");
				menuString.append(getMutiLang(function.getFunctionName()));
				menuString.append("			</a> ");
				if(hasSub){
					menuString.append(getBootStrapChild(function,level+1,map));
				}
				menuString.append("		</li> ");
			}
		}
		menuString.append("		</ul> ");
		return menuString.toString();
	}


	//update-start--Author:gaofeng  Date:2014-02-14：新增webos头部菜单导航,多级菜单
	/**
	 * 拼装webos头部菜单
	 * @param pFunctions
	 * @param functions
	 * @return
	 */
	public static String getWebosMenu(Map<Integer, List<TSFunction>> map) {
		StringBuffer menuString = new StringBuffer();
		StringBuffer DeskpanelString = new StringBuffer();
		StringBuffer dataString = new StringBuffer();
		String menu = "";
		String desk = "";
		String data = "";

		//menu的全部json，这里包括对菜单的展示及每个二级菜单的点击出详情
//		menuString.append("[");
		menuString.append("{");
		//绘制data.js数组，用于替换data.js中的app:{//桌面1 'dtbd':{ appid:'2534',,······
		dataString.append("{app:{");
		//绘制Deskpanel数组，用于替换webos-core.js中的Icon1:['dtbd','sosomap','jinshan'],······
		DeskpanelString.append("{");

		List<TSFunction> pFunctions = map.get(0);
		if(pFunctions==null || pFunctions.size()==0){
			return "";
		}
		int n = 1;
		for (TSFunction pFunction : pFunctions) {
			//是否有子菜单
			boolean hasSub = pFunction.getTSFunctions().size()==0?false:true;
			//绘制一级菜单
//			menuString.append("{ ");
			menuString.append("\""+ pFunction.getId() + "\":");
			menuString.append("{\"id\":\""+pFunction.getId()+"\",\"name\":\""+pFunction.getFunctionName()+"\",\"path\":\""+pFunction.getTSIcon().getIconPath()+"\",\"level\":\""+pFunction.getFunctionLevel()+"\",");
			menuString.append("\"child\":{");

			//绘制Deskpanel数组
			DeskpanelString.append("Icon"+n+":[");

			//绘制二级菜单
			if(hasSub){
//				menuString.append(getWebosChild(pFunction, 1, map));
				DeskpanelString.append(getWebosDeskpanelChild(pFunction, 1, map));
				dataString.append(getWebosDataChild(pFunction, 1, map));
			}
			DeskpanelString.append("],");
			menuString.append("}},");
			n++;
		}

		menu = menuString.substring(0, menuString.toString().length()-1);
//		menu += "]";
		menu += "}";

		data = dataString.substring(0, dataString.toString().length()-1);
		data += "}}";

		desk = DeskpanelString.substring(0, DeskpanelString.toString().length()-1);
		desk += "}";

		//初始化为1，需减少一个个数。
		n = n-1;

//		System.out.println("-------------------");
//		System.out.println(menu+"$$"+desk+"$$"+data+"$$"+"{\"total\":"+n+"}");
		return menu+"$$"+desk+"$$"+data+"$$"+n;
	}
	private static String getWebosDeskpanelChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer DeskpanelString = new StringBuffer();
		String desk = "";
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				DeskpanelString.append("'"+function.getId()+"',");
			}
		}
		desk = DeskpanelString.substring(0, DeskpanelString.toString().length()-1);
		return desk;
	}
	private static String getWebosDataChild(TSFunction parent,int level,Map<Integer, List<TSFunction>> map){
		StringBuffer dataString = new StringBuffer();
		String data = "";
		List<TSFunction> list = map.get(level);
		if(list==null || list.size()==0){
			return "";
		}
		for (TSFunction function : list) {
			if (function.getTSFunction().getId().equals(parent.getId())){
				dataString.append("'"+function.getId()+"':{ ");
				dataString.append("appid:'"+function.getId()+"',");
				dataString.append("url:'"+function.getFunctionUrl()+"',");
                //        update-begin--Author:zhangguoming  Date:20140509 for：添加云桌面图标
//				dataString.append(getIconandName(function.getFunctionName()));
				dataString.append(getIconAndNameForDesk(function));
                //        update-end--Author:zhangguoming  Date:20140509 for：添加云桌面图标
				dataString.append("asc :"+function.getFunctionOrder());
				dataString.append(" },");
			}
		}
//		data = dataString.substring(0, dataString.toString().length()-1);
		data = dataString.toString();
		return data;
	}

    //        update-begin--Author:zhangguoming  Date:20140512 for：添加云桌面图标管理
    private static String getIconAndNameForDesk(TSFunction function) {
        StringBuffer dataString = new StringBuffer();

        String colName = function.getTSIconDesk() == null ? null : function.getTSIconDesk().getIconPath();
        colName = (colName == null || colName.equals("")) ? "plug-in/sliding/icon/default.png" : colName;
        String functionName = getMutiLang(function.getFunctionName());

        dataString.append("icon:'" + colName + "',");
        dataString.append("name:'"+functionName+"',");

        return dataString.toString();
    }
    /**
	*  @Title: getMutiLang
	*  @Description: 转换菜单多语言
	*  @param functionName
	* @return String
	* @throws
	 */
	private static String getMutiLang(String functionName){
		//add by Rocky, 处理多语言
		if(mutiLangService == null)
		{
			mutiLangService = ApplicationContextUtil.getContext().getBean(MutiLangServiceI.class);
		}

		String lang_context = mutiLangService.getLang(functionName);
		return lang_context;
	}
    //        update-end--Author:zhangguoming  Date:20140512 for：添加云桌面图标管理
	//update-start--Author:gaofeng  Date:2014-02-14：新增Webos头部菜单导航，多级菜单
}