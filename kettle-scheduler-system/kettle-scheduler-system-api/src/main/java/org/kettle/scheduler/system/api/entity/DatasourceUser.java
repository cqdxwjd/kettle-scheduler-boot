package org.kettle.scheduler.system.api.entity;

import lombok.Data;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 12:07
 */

@Data
public class DatasourceUser {

    private String id;
    private String username;
    private String password;
    private String tablespace;
    private String admdivcode;
    private String dbType;
    private String systemId;
}
