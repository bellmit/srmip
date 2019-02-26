package com.kingtake.common.util;

import java.util.Map;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.common.constant.SrmipConstants;

public class ConvertDicUtil {
    private static SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);

    /**
     * 转换excel导出值
     * 
     * @param codeType 所属代码集（0：标准代码集 ；1：科研代码集）
     * @param code 类别代码
     * @param fieldValue
     * @return
     */
    public static String getConvertDic(String codeType, String code, String fieldValue) {
        if (!StringUtil.isNotEmpty(fieldValue)) {
            return "";
        }
        Object value = null;
        if ("0".equals(codeType)) {
            Map<String, Object> codeMap = SrmipConstants.dicStandardMap.get(code);
            value = codeMap.get(fieldValue);
        } else {
            Map<String, Object> codeMap = SrmipConstants.dicResearchMap.get(code);
            value = codeMap.get(fieldValue);
        }
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
        //        String sql = "SELECT D.NAME FROM T_B_CODE_DETAIL D, T_B_CODE_TYPE T \n"
        //                + "WHERE D.CODE_TYPE_ID = T.ID \n" + "AND T.CODE_TYPE = ? AND T.CODE = ? AND D.CODE = ?";
        //        Map<String, Object> fieldName = systemService.findOneForJdbc(sql, codeType, code, fieldValue);
        //        if (fieldName != null) {
        //            return fieldName.get("NAME").toString();
        //        } else {
        //            return "";
        //        }
    }
}
