package org.kettle.scheduler.system.biz.thread;

import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.biz.util.OracleBackUpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 08:30
 */
@Component
public class ImplDbThread {

    Logger logger = LoggerFactory.getLogger(ImplDbThread.class);

    @Async("implDbExecutor")
    public void startImplDB(DatasourceUser datasourceUser,String address){
        logger.info("导入oracle文件，文件路径："+address);
        OracleBackUpUtil.resumeDataBaseOracle(datasourceUser, address);
    }
}
