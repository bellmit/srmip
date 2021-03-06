package org.jeecgframework.core.extend.hqlsearch;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.annotation.query.QueryTimeFormat;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.extend.hqlsearch.parse.ObjectParseUtil;
import org.jeecgframework.core.extend.hqlsearch.parse.PageValueConvertRuleEnum;
import org.jeecgframework.core.extend.hqlsearch.parse.vo.HqlRuleEnum;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.springframework.util.NumberUtils;

/**
 * 
 * @author 张代浩
 * @de
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HqlGenerateUtil {

	/** 时间查询符号 */
	private static final String END = "_end";
	private static final String BEGIN = "_begin";

	private static final SimpleDateFormat time = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	/**
	 * 自动生成查询条件HQL 模糊查询 不带有日期组合
	 * 
	 * @param cq
	 * @param searchObj
	 * @throws Exception
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj) {
		installHqlJoinAlias(cq, searchObj, getRuleMap(), null, "");
		cq.add();
	}

	/**
	 * 自动生成查询条件HQL（扩展区间查询功能）
	 * 
	 * @param cq
	 * @param searchObj
	 * @param parameterMap
	 *            request参数集合，封装了所有查询信息
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj,
			Map<String, String[]> parameterMap) {
		installHqlJoinAlias(cq, searchObj, getRuleMap(), parameterMap, "");
		cq.add();
	}

	/**
	 * 添加Alias别名的查询
	 * 
	 * @date 2014年1月19日
	 * @param cq
	 * @param searchObj
	 * @param parameterMap
	 * @param alias
	 */
	private static void installHqlJoinAlias(CriteriaQuery cq, Object searchObj,
			Map<String, TSDataRule> ruleMap,
			Map<String, String[]> parameterMap, String alias) {
		PropertyDescriptor origDescriptors[] = PropertyUtils
				.getPropertyDescriptors(searchObj);
		String aliasName, name, type;
		for (int i = 0; i < origDescriptors.length; i++) {
			aliasName = (alias.equals("") ? "" : alias + ".")
					+ origDescriptors[i].getName();
			name = origDescriptors[i].getName();
			type = origDescriptors[i].getPropertyType().toString();
			try {
				if (judgedIsUselessField(name)
						|| !PropertyUtils.isReadable(searchObj, name)) {
					continue;
				}
				// 如果规则包含这个属性
				if (ruleMap.containsKey(aliasName)) {
					addRuleToCriteria(ruleMap.get(aliasName), aliasName,
							origDescriptors[i].getPropertyType(), cq);
				}

				// 添加 判断是否有区间值
				String beginValue = null;
				String endValue = null;
				if (parameterMap != null
						&& parameterMap.containsKey(name + BEGIN)) {
					beginValue = parameterMap.get(name + BEGIN)[0].trim();
				}
				if (parameterMap != null
						&& parameterMap.containsKey(name + END)) {
					endValue = parameterMap.get(name + END)[0].trim();
				}

				Object value = PropertyUtils.getSimpleProperty(searchObj, name);
				// 根据类型分类处理
				if (type.contains("class java.lang")
						|| type.contains("class java.math")) {
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
					if (value != null && !value.equals("")) {
						HqlRuleEnum rule = PageValueConvertRuleEnum
								.convert(value);
						value = PageValueConvertRuleEnum.replaceValue(rule,
								value);
						ObjectParseUtil.addCriteria(cq, aliasName, rule, value);
                    } else if (parameterMap != null) {
                        if (StringUtils.isNotEmpty(beginValue)) {//数字范围的查询
                            ObjectParseUtil.addCriteria(cq, aliasName, HqlRuleEnum.GE, convertValue(type, beginValue));
                        }
                        if (StringUtils.isNotEmpty(endValue)) {
                            ObjectParseUtil.addCriteria(cq, aliasName, HqlRuleEnum.LE, convertValue(type, endValue));
                        }
					}
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
				} else if ("class java.util.Date".equals(type)) {
					QueryTimeFormat format = origDescriptors[i].getReadMethod()
							.getAnnotation(QueryTimeFormat.class);
					SimpleDateFormat userDefined = null;
					if (format != null) {
						userDefined = new SimpleDateFormat(format.format());
					}
					if (StringUtils.isNotBlank(beginValue)) {
						if (userDefined != null) {
							cq.ge(aliasName, userDefined.parse(beginValue));
						} else if (beginValue.length() == 19) {
							cq.ge(aliasName, time.parse(beginValue));
						} else if (beginValue.length() == 10) {
							cq.ge(aliasName,
									time.parse(beginValue + " 00:00:00"));
						}
					}
					if (StringUtils.isNotBlank(endValue)) {
						if (userDefined != null) {
							cq.ge(aliasName, userDefined.parse(beginValue));
						} else if (endValue.length() == 19) {
							cq.le(aliasName, time.parse(endValue));
						} else if (endValue.length() == 10) {
							// 对于"yyyy-MM-dd"格式日期，因时间默认为0，故此添加" 23:23:59"并使用time解析，以方便查询日期时间数据
							cq.le(aliasName, time.parse(endValue + " 23:23:59"));
						}
					}
					if (isNotEmpty(value)) {
						cq.eq(aliasName, value);
					}
				} else if (!StringUtil.isJavaClass(origDescriptors[i]
						.getPropertyType())) {
					Object param = PropertyUtils.getSimpleProperty(searchObj,
							name);
					if (isHaveRuleData(ruleMap, aliasName) ||( isNotEmpty(param)
							&& itIsNotAllEmpty(param))) {
						// 如果是实体类,创建别名,继续创建查询条件
						// ------------update--Author:JueYue Date:20140521
						// for：用户反馈
						cq.createAlias(aliasName,
								aliasName.replaceAll("\\.", "_"));
						// ------------end--Author:JueYue Date:20140521 for：用户反馈
						installHqlJoinAlias(cq, param, ruleMap, parameterMap,
								aliasName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 范围比较，类型转换.
     * 
     * @param type
     * @param value
     * @return
     */
    private static Object convertValue(String type, String value) {
        if (type.contains("class java.lang.Double")) {
            return Double.valueOf(value);
        }else if(type.contains("class java.math.BigDecimal")){
            return new BigDecimal(value);
        } else if (type.contains("class java.lang.Integer")) {
            return Integer.valueOf(value);
        } else {
            return value;
        }
    }

	/**
	 * 判断数据规则是不是包含这个实体类
	 * 
	 * @param ruleMap
	 * @param aliasName
	 * @return
	 */
	private static boolean isHaveRuleData(Map<String, TSDataRule> ruleMap,
			String aliasName) {
		for (String key : ruleMap.keySet()) {
			if (key.contains(aliasName)) {
				return true;
			}
		}
		return false;
	}

	private static void addRuleToCriteria(TSDataRule tsDataRule,
			String aliasName, Class propertyType, CriteriaQuery cq) {
		HqlRuleEnum rule = HqlRuleEnum.getByValue(tsDataRule
				.getRuleConditions());
		if (rule.equals(HqlRuleEnum.IN)) {
			String[] values = tsDataRule.getRuleValue().split(",");
			Object[] objs = new Object[values.length];
			if (! propertyType.equals(String.class)) {
				for (int i = 0; i < values.length; i++) {
					objs[i] = NumberUtils
							.parseNumber(values[i], propertyType);
				}
			}else {
				objs = values;
			}
			ObjectParseUtil.addCriteria(cq, aliasName, rule, objs);
		} else {
			if (propertyType.equals(String.class)) {
				ObjectParseUtil.addCriteria(cq, aliasName, rule,
						converRuleValue(tsDataRule.getRuleValue()));
			} else {
				ObjectParseUtil.addCriteria(cq, aliasName, rule, NumberUtils
						.parseNumber(tsDataRule.getRuleValue(), propertyType));
			}
		}
	}

	private static String converRuleValue(String ruleValue) {
		String value = ResourceUtil.getUserSystemData(ruleValue);
		return value!= null ? value : ruleValue;
	}

	private static boolean judgedIsUselessField(String name) {
		return "class".equals(name) || "ids".equals(name)
				|| "page".equals(name) || "rows".equals(name)
				|| "sort".equals(name) || "order".equals(name);
	}

	/**
	 * 判断是不是空
	 */
	public static boolean isNotEmpty(Object value) {
		return value != null && !"".equals(value);
	}

	/**
	 * 判断这个类是不是所以属性都为空
	 * 
	 * @param param
	 * @return
	 */
	private static boolean itIsNotAllEmpty(Object param) {
		boolean isNotEmpty = false;
		try {
			PropertyDescriptor origDescriptors[] = PropertyUtils
					.getPropertyDescriptors(param);
			String name;
			for (int i = 0; i < origDescriptors.length; i++) {
				name = origDescriptors[i].getName();
				if ("class".equals(name)
						|| !PropertyUtils.isReadable(param, name)) {
					continue;
				}
				if (Map.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Map<?, ?> map = (Map<?, ?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (map != null && map.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (Collection.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Collection<?> c = (Collection<?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (c != null && c.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (StringUtil.isNotEmpty(PropertyUtils
						.getSimpleProperty(param, name))) {
					isNotEmpty = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNotEmpty;
	}

	private static Map<String, TSDataRule> getRuleMap() {
		Map<String, TSDataRule> ruleMap = new HashMap<String, TSDataRule>();
		List<TSDataRule> list =JeecgDataAutorUtils.loadDataSearchConditonSQL(); //(List<TSDataRule>) ContextHolderUtils
			//	.getRequest().getAttribute(Globals.MENU_DATA_AUTHOR_RULES);
		if(list != null){
			for (TSDataRule rule : list) {
				ruleMap.put(rule.getRuleColumn(), rule);
			}
		}
		return ruleMap;
	}

    /**
     * 将数据规则添加到查询对象中.
     * 
     * @param tsDataRule
     * @param propertyType
     * @param cq
     */
    public static void addRuleToCriteria(TSDataRule tsDataRule, Class propertyType, CriteriaQuery cq) {
        HqlRuleEnum rule = HqlRuleEnum.getByValue(tsDataRule.getRuleConditions());
        if (rule.equals(HqlRuleEnum.IN)) {
            String[] values = tsDataRule.getRuleValue().split(",");
            Object[] objs = new Object[values.length];
            if (!propertyType.equals(String.class)) {
                for (int i = 0; i < values.length; i++) {
                    objs[i] = NumberUtils.parseNumber(values[i], propertyType);
                }
            } else {
                objs = values;
            }
            ObjectParseUtil.addCriteria(cq, tsDataRule.getRuleColumn(), rule, objs);
        } else {
            if (propertyType.equals(String.class)) {
                ObjectParseUtil.addCriteria(cq, tsDataRule.getRuleColumn(), rule,
                        converRuleValue(tsDataRule.getRuleValue()));
            } else {
                ObjectParseUtil.addCriteria(cq, tsDataRule.getRuleColumn(), rule,
                        NumberUtils.parseNumber(tsDataRule.getRuleValue(), propertyType));
            }
        }
    }

}
