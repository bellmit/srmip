package com.kingtake.common.service;

import java.util.List;

public interface ExportXmlServiceI {

    /**
     * 导出xml
     */
    public int toXmlService(List list, String tableName , String classPath);
}
