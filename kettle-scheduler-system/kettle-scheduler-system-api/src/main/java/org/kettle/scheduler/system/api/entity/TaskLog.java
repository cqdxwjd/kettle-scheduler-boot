package org.kettle.scheduler.system.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 描述:
 * 任务日志
 *
 * @author leo
 * @create 2019-12-31 08:09
 */
@Data
public class TaskLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String taskType;
    private String taskStatus;
    private String taskMessage;
    private Date taskDate;
    private Map<String, Object> map;

    public TaskLog() {
    }

    public TaskLog(String taskType, String taskStatus, String taskMessage, Date taskDate, Map<String, Object> map) {
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.taskMessage = taskMessage;
        this.taskDate = taskDate;
        this.map = map;
    }

    @Override
    public String toString() {
        return "TaskLog{" +
                "id='" + id + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskMessage='" + taskMessage + '\'' +
                ", taskDate=" + taskDate +
                '}';
    }
}
