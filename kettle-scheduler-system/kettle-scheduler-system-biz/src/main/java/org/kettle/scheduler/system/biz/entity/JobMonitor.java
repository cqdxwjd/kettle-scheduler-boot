package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 作业监控表
 *
 * @author lyf
 */
@Table(name = "K_JOB_MONITOR")
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class JobMonitor extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_JOB_MONITOR", strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;
    /**
     * 监控的作业ID
     */
    @Column(name = "monitor_job_id")
    private Integer monitorJobId;

    /**
     * 成功次数
     */
    @Column(name = "monitor_success")
    private Integer monitorSuccess;

    /**
     * 失败次数
     */
    @Column(name = "monitor_fail")
    private Integer monitorFail;

    /**
     * 监控状态（是否启动，1:启动；2:停止）
     */
    @Column(name = "monitor_status")
    private Integer monitorStatus;

    /**
     * 运行状态（起始时间-结束时间,起始时间-结束时间……）
     */
    @Column(name = "run_status")
    private String runStatus;

    /**
     * 上一次执行时间
     */
    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    /**
     * 下一次执行时间
     */
    @Column(name = "next_execute_time")
    private Date nextExecuteTime;


}