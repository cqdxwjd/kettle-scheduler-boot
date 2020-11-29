package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 首页监控任务运行状况统计视图
 *
 * @author xieyonggao
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "v_home_monitor_task_counts")
@EntityListeners(AuditingEntityListener.class)
@Data
public class HomeMonitorTaskCounts implements Serializable {

    /**
     * 主键id
     */
    @Id
//    @GeneratedValue(generator="SEQ_TRANS",strategy = GenerationType.SEQUENCE)
    @Column(name = "union_id", insertable = false, nullable = false)
    private String unionId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "type")
    private Integer type;

    @Column(name = "counts")
    private Integer counts;
}