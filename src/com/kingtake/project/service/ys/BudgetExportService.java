package com.kingtake.project.service.ys;

import java.util.Map;

public interface BudgetExportService {

    //导出到款分配
    public Map exportDkfpList(Map map);

    //导出计划经费分配
    public Map exportJhffList(Map map);

    //导出经费垫支
    public Map exportJfdzList(Map map);

}
