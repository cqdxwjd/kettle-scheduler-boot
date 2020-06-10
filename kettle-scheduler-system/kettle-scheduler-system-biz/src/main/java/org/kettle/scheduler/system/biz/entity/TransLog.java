package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志表
 */
@Entity
@Table(name = "k_log")
@Data
public class TransLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;
    /**
     * 区划
     */
    @Column(name = "admdivcode")
    private String admdivcode;
    /**
     * 步骤类型
     * 1为读
     * 2为写
     * 3为其他
     *
     */
    @Column(name ="type")
    private Integer type;
    /**
     * 转换名
     */
    @Column(name ="transname")
    private String transname;
    /**
     * 步骤名
     */
    @Column(name = "stepname")
    private String stepname;
    /**
     * 当前步骤生成的记录数（从表输出、文件读入）
     */
    @Column(name = "i")
    private Integer i;
    /**
     *当前步骤输出的记录数（输出的文件和表）
     */
    @Column(name = "O")
    private Integer O;
    /**
     *当前步骤从前一步骤读取的记录数
     */
    @Column(name = "r")
    private Integer r;
    /**
     *当前步骤向后面步骤抛出的记录数
     */
    @Column(name = "w")
    private Integer w;
    /**
     *当前步骤更新过的记录数
     */
    @Column(name = "u")
    private Integer u;
    /**
     *当前步骤处理的记录数
     */
    @Column(name = "e")
    private Integer e;
    /**
     * 完成时间
     */
    @Column(name = "time")
    private Date time;
    /**
     * 分类id
     */
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;
}
