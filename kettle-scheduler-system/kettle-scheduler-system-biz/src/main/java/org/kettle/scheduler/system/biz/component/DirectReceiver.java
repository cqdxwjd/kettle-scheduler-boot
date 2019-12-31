package org.kettle.scheduler.system.biz.component;

import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.api.entity.TaskLog;
import org.kettle.scheduler.system.biz.service.TaskLogService;
import org.kettle.scheduler.system.biz.thread.ImplDbThread;
import org.kettle.scheduler.system.biz.util.OracleBackUpUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 07:59
 */
@Component
@RabbitListener(queues = "kettleDirectQueue")//监听的队列名称 kettleDirectQueue
public class DirectReceiver {

    @Autowired
    ImplDbThread implDbThread;

    @Autowired
    TaskLogService taskLogService;

    @RabbitHandler
    public void process(TaskLog taskLog) {
        //taskLogService.addTaskLog(taskLog);
        if (taskLog.getTaskType().equals("数据库导入文件")) {
            Map<String, Object> map = taskLog.getMap();
            implDbThread.startImplDB((DatasourceUser) map.get("datasourceUser"), map.get("address").toString());
        }
        System.out.println("DirectReceiver消费者收到消息  : " + taskLog.toString());
    }
}
