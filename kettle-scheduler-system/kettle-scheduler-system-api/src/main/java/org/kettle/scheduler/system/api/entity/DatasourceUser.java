package org.kettle.scheduler.system.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 12:07
 */

@Data
public class DatasourceUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String password;
    private String tablespace;
    private String admdivcode;
    private String dbType;
    private String systemId;
    private String databaseName;
    private String databasePort;
    private String databaseHost;
    private Date lastImplDate;
}
