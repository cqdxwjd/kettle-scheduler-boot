package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.api.entity.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 08:53
 */
@Service
public class TaskLogService {

    @Autowired
    TaskLogService taskLogService;

    public int addTaskLog(TaskLog taskLog) {
        return taskLogService.addTaskLog(taskLog);
    }
}
