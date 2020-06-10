package org.kettle.scheduler.system.biz.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class CountSumBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总任务数
     */
    @Id
    @Column(name = "sum")
    private Long sum;

}
