package com.kingtake.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueUtil implements JsonValueProcessor {
    private String format = "yyyy-MM-dd";

    public JsonDateValueUtil() {
        super();
    }

    public JsonDateValueUtil(String format) {
        super();
        this.format = format;
    }

    public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }

    private Object process(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat format = new SimpleDateFormat(this.format, Locale.CHINA);
            return format.format(value);
        }
        return value == null ? "" : value.toString();
    }
}
