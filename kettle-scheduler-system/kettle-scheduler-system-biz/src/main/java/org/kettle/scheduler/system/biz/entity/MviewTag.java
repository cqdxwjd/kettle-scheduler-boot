package org.kettle.scheduler.system.biz.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-26 17:50
 */
@Data
@Entity
//@EqualsAndHashCode(callSuper = true)
@Table(name = "k_mview_tag")
public class MviewTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "mview_Tag_Code")
    private String mview_Tag_Code;

    @Column(name = "mview_Tag_Name")
    private String mview_Tag_Name;

    @Column(name =  "parent_Id")
    private String parentId;
}
