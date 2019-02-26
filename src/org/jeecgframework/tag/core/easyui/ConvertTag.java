package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
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
 * 代码集值转换标签
 * 
 */
public class ConvertTag extends TagSupport {

    private static final long serialVersionUID = 1;
    private String code; // 代码参数值编码
    private String codeType;// 代码类型,0表示标准代码，1表示科研代码
    private String paramJson;//自己传入json进行转换
    private String value;//代码值

    private static TBCodeTypeServiceI tBCodeTypeService;

    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print(end());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public String end() {
        Map<String, String> mp = queryDic();
        if (StringUtils.isNotBlank(this.paramJson)) {
            Gson gson = new Gson();
            Map<String, String> paramMap = gson.fromJson(paramJson, Map.class);
            if (paramMap.size() > 0) {
                mp = paramMap;
            }
        }
        String convertVal = "";
        if (mp != null && mp.containsKey(value)) {
            convertVal = mp.get(value);
        }
        return convertVal;
    }

    /**
     * 查询代码集参数值
     * 
     */
    private Map<String, String> queryDic() {
        Map<String, String> codeMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(codeType) && StringUtils.isNotEmpty(code)) {
            TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
            codeTypeEntity.setCode(code);
            codeTypeEntity.setCodeType(codeType);
            tBCodeTypeService = ApplicationContextUtil.getContext().getBean(TBCodeTypeServiceI.class);
            List<TBCodeDetailEntity> codeDetailList = this.tBCodeTypeService.getCodeByCodeType(codeTypeEntity);
            for (TBCodeDetailEntity tmp : codeDetailList) {
                codeMap.put(tmp.getCode(), tmp.getName());
            }
        }
        return codeMap;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void settBCodeTypeService(TBCodeTypeServiceI tBCodeTypeService) {
        ConvertTag.tBCodeTypeService = tBCodeTypeService;
    }

    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        ConvertTag tag = new ConvertTag();
        tag.setCode("XKFX");
        tag.setCodeType("1");
        tag.setValue("1");
        String tagStr = tag.end();
        System.out.println(tagStr);
    }
}
