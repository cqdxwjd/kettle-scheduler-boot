package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 转换监控表
 *
 * @author lyf
 */
@Data
@Entity
@Table(name = "k_trans_monitor")
public class TransMonitor implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id")
    private String id;
    /**
     * 监控的转换的ID
     */
    @Column(name = "monitor_trans_id")
    private Integer monitorTransId;


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


    /**
     * 添加者
     */
    @Column(name = "add_user")
    private Integer addUser;

    /**
     * 添加时间, 添加{@code @CreatedDate}注解后, 系统会自动添加日期
     */
    @CreatedDate
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 编辑者
     */
    @Column(name = "edit_user")
    private Integer editUser;

    /**
     * 编辑时间, 添加{@code @LastModifiedDate}注解后, 系统会自动更新日期
     */
    @LastModifiedDate
    @Column(name = "edit_time")
    private Date editTime;

    /**
     * 是否删除（1：删除；0：存在）
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

}