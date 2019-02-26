package org.jeecgframework.web.system.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

public interface TSFilesService extends CommonService {
    public void task();

    public List<TSFilesEntity> getFileListByBid(String bid);

    public Boolean ifFileExists(String relativePath);
}
