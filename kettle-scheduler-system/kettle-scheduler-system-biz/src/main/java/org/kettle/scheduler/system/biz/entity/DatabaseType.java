package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.biz.entity.basic.BasicEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据库连接类型表
 *
 * @author lyf
 */
@Table(name = "K_DATABASE_TYPE")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class DatabaseType extends BasicEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SEQ_DATABASE_TYPE", strategy = GenerationType.SEQUENCE)
    //@GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Integer id;

    /**
     * 编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;


}