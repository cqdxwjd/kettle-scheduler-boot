package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.kettle.scheduler.system.api.entity.TaskLog;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 08:54
 */

@Mapper
public interface TaskLogMapper {

    @Insert("insert into k_task_log(task_type,task_status,task_message,task_date) values(#{taskType},#{taskStatus},#{taskMessage},#{taskDate})")
    int addTaskLog(TaskLog taskLog);

}
