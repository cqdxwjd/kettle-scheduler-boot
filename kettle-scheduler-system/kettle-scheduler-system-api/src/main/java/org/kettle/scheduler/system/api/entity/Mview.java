package org.kettle.scheduler.system.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-27 17:48
 */
@Data
public class Mview implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String owner;

    private String mviewName;

    private Date lastRefreshDate;

    private String refreshMethod;

    private String invalid;

    private String mviewTagId;

    private String query;

    private String status;
}
