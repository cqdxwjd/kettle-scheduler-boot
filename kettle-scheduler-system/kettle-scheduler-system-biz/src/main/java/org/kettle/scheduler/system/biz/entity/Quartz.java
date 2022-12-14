package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 定时任务表
 *
 * @author lyf
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "K_QUARTZ")
public class Quartz extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "SEQ_QUARTZ", strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;
    /**
     * 任务描述
     */
    @Column(name = "quartz_description")
    private String quartzDescription;

    /**
     * 定时策略
     */
    @Column(name = "quartz_cron")
    private String quartzCron;


}