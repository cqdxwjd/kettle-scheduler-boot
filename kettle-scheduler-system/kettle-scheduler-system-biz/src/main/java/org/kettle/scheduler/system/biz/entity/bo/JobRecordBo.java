package org.kettle.scheduler.system.biz.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class JobRecordBo extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(generator = "SEQ_JOB_MONITOR", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 转换ID
     */
    @Column(name = "RECORD_JOB_ID")
    private Integer recordJobId;

    /**
     * 转换名称
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 转换描述
     */
    @Column(name = "JOB_DESCRIPTION")
    private String jobDescription;
    /**
     * 转换分类
     */
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    /**
     * 启动时间
     */
    @Column(name = "START_TIME")
    private Date startTime;

    /**
     * 停止时间
     */
    @Column(name = "STOP_TIME")
    private Date stopTime;

    /**
     * 任务执行结果（1：成功；0：失败）
     */
    @Column(name = "RECORD_STATUS")
    private Integer recordStatus;

//    @ApiModelProperty(value = "任务执行结果显示值")
//    public String getRecordStatusStr() {
//        return RunResultEnum.getEnumDesc(recordStatus);
//    }

    /**
     * 转换日志记录文件保存位置
     */
    @Column(name = "LOG_FILE_PATH")
    private String logFilePath;
}
