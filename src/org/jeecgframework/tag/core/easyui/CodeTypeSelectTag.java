package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;

import com.google.gson.Gson;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;

/**
 * 
 * 代码集选择下拉框
 * 
 */
public class CodeTypeSelectTag extends TagSupport {

    private static final long serialVersionUID = 1;
    private String code; // 代码参数值编码
    private String codeType;// 代码类型,0表示标准代码，1表示科研代码
    private String id; // 选择表单ID EAMPLE:<select name="selectName" id = "" />
    private String defaultVal; // 默认值
    private String type;// 控件类型select|radio|checkbox
    private String name;// name属性值，用于表单提交
    private String extendParam;
    private String labelText = "";//第一个下拉列表的显示文字

    private static TBCodeTypeServiceI tBCodeTypeService;

    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print(end().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public StringBuffer end() {
        StringBuffer sb = new StringBuffer();
        List<Map<String, Object>> list = queryDic();
        if ("radio".equals(type)) {
            for (Map<String, Object> map : list) {
                radio(map.get("text").toString(), map.get("code").toString(), sb);
            }
        } else if ("checkbox".equals(type)) {
            for (Map<String, Object> map : list) {
                checkbox(map.get("text").toString(), map.get("code").toString(), sb);
            }
        } else if ("text".equals(type)) {
            for (Map<String, Object> map : list) {
                text(map.get("text").toString(), map.get("code").toString(), sb);
            }
        } else {
            sb.append("<select name=\"" + name + "\"");
            if (!StringUtils.isBlank(this.id)) {
                sb.append(" id=\"" + id + "\" ");
            }
            if (StringUtils.isNotBlank(this.extendParam)) {
                Gson gson = new Gson();
                Map<String, String> mp = gson.fromJson(extendParam, Map.class);
                for (Map.Entry<String, String> entry : mp.entrySet()) {
                    sb.append(entry.getKey() + "=\"" + entry.getValue() + "\"");
                }
            }
            sb.append(">");
            if (StringUtils.isNotEmpty(labelText)) {
                select(labelText, "", sb);
            }
            for (Map<String, Object> map : list) {
                select(map.get("text").toString(), map.get("code").toString(), sb);
            }
            sb.append("</select>");
        }
        return sb;
    }

    /**
     * 文本框方法
     * 
     * @param name
     * @param code
     * @param sb
     */
    private void text(String text, String code, StringBuffer sb) {
        sb.append("<input name='" + name + "'" + " id='" + id + "' value='" + text + "' readOnly = 'readOnly' />");
    }

    /**
     * 单选框方法
     * 
     * @作者：Alexander
     * 
     * @param name
     * @param code
     * @param sb
     */
    private void radio(String text, String code, StringBuffer sb) {
        if (code.equals(this.defaultVal)) {
            sb.append("<input type=\"radio\" name=\"" + name + "\" checked=\"checked\" value=\"" + code + "\"");
            if (!StringUtils.isBlank(this.id)) {
                sb.append(" id=\"" + id + "\"");
            }
            if (StringUtils.isNotBlank(this.extendParam)) {
                Gson gson = new Gson();
                Map<String, String> mp = gson.fromJson(extendParam, Map.class);
                for (Map.Entry<String, String> entry : mp.entrySet()) {
                    sb.append(entry.getKey() + "=\"" + entry.getValue() + "\"");
                }
            }
            sb.append(" />");
        } else {
            sb.append("<input type=\"radio\" name=\"" + name + "\" value=\"" + code + "\"");
            if (!StringUtils.isBlank(this.id)) {
                sb.append(" id=\"" + id + "\"");
            }
            if (StringUtils.isNotBlank(this.extendParam)) {
                Gson gson = new Gson();
                Map<String, String> mp = gson.fromJson(extendParam, Map.class);
                for (Map.Entry<String, String> entry : mp.entrySet()) {
                    sb.append(entry.getKey() + "=\"" + entry.getValue() + "\"");
                }
            }
            sb.append(" />");
        }
        sb.append(text);
    }

    /**
     * 复选框方法
     * 
     * @param name
     * @param code
     * @param sb
     */
    private void checkbox(String text, String code, StringBuffer sb) {
        Boolean checked = false;
        if (StringUtils.isNotEmpty(this.defaultVal)) {
            String[] values = this.defaultVal.split(",");
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (code.equals(value)) {
                    checked = true;
                    break;
                }
                checked = false;
            }
        }
        if (checked) {
            sb.append("<input type=\"checkbox\" name=\"" + name + "\" checked=\"checked\" value=\"" + code + "\"");
            if (!StringUtils.isBlank(this.id)) {
                sb.append(" id=\"" + id + "\"");
            }
            sb.append(" />");
        } else {
            sb.append("<input type=\"checkbox\" name=\"" + name + "\" value=\"" + code + "\"");
            if (!StringUtils.isBlank(this.id)) {
                sb.append(" id=\"" + id + "\"");
            }
            sb.append(" />");
        }
        sb.append(text);
    }

    /**
     * 选择框方法
     * 
     * @param name
     * @param code
     * @param sb
     */
    private void select(String text, String code, StringBuffer sb) {
        if (code.equals(this.defaultVal)) {
            sb.append(" <option value=\"" + code + "\" selected=\"selected\">");
        } else {
            sb.append(" <option value=\"" + code + "\">");
        }
        sb.append(text);
        sb.append("</option>");
    }

    /**
     * 查询代码集参数值
     * 
     */
    private List<Map<String, Object>> queryDic() {
        TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
        codeTypeEntity.setCode(code);
        codeTypeEntity.setCodeType(codeType);
        tBCodeTypeService = ApplicationContextUtil.getContext().getBean(TBCodeTypeServiceI.class);
        List<TBCodeDetailEntity> codeDetailList = this.tBCodeTypeService.getCodeByCodeType(codeTypeEntity);
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        for (TBCodeDetailEntity tmp : codeDetailList) {
            Map<String, Object> codeMap = new HashMap<String, Object>();
            codeMap.put("code", tmp.getCode());
            codeMap.put("text", tmp.getName());
            resultMap.add(codeMap);
        }
        return resultMap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        CodeTypeSelectTag tag = new CodeTypeSelectTag();
        tag.setCode("XKFX");
        tag.setCode("1");
        tag.setName("xkfxSelect");
        tag.setType("select");
        tag.setId("xkfxId");
        tag.setLabelText("请选择");
        StringBuffer tagStr = tag.end();
        System.out.println(tagStr);
    }
}
