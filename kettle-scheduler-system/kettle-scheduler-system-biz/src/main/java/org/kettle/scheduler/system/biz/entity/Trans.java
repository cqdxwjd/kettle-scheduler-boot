package org.kettle.scheduler.system.biz.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 转换表
 *
 * @author lyf
 */
@Entity
@Table(name = "k_trans")
@Data
@EqualsAndHashCode(callSuper = true)
public class Trans extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEQ_TRANS", strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;


    /**
     * 分类ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 转换名称
     */
    @Column(name = "trans_name")
    private String transName;

    /**
     * 转换描述
     */
    @Column(name = "trans_description")
    private String transDescription;

    /**
     * 执行类型（rep：资源库；file：文件）
     */
    @Column(name = "trans_type")
    private String transType;

    /**
     * 转换保存路径（可以是资源库中的路径也可以是服务器中保存作业文件的路径）
     */
    @Column(name = "trans_path")
    private String transPath;

    /**
     * 转换的资源库ID
     */
    @Column(name = "trans_repository_id")
    private Integer transRepositoryId;

    /**
     * 定时策略（外键ID）
     */
    @Column(name = "trans_quartz")
    private Integer transQuartz;

	/**
	 * 同步策略(T+n)方式
	 */
	@Column(name = "sync_strategy")
	private String syncStrategy;

    /**
     * 日志级别(Basic，Detailed，Error，Debug，Minimal，Rowlevel）
     */
    @Column(name = "trans_log_level")
    private String transLogLevel;

    /**
     * 状态（1：正在运行；2：已停止）
     */
    @Column(name = "trans_status")
    private Integer transStatus;

    /**
     * 转换中使用到的参数信息，JSON格式
     */
    @Column(name = "trans_params")
    private String transParams;
}