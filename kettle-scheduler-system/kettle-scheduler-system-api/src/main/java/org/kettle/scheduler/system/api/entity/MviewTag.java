package org.kettle.scheduler.system.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @auther leo
 * @create 2019-12-27 16:15
 */
@Data
public class MviewTag implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String mviewTagCode;

    private String mviewTagName;

    private String parentId;
}
