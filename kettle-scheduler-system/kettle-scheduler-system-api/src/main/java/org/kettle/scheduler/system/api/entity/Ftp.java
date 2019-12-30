package org.kettle.scheduler.system.api.entity;

import lombok.Data;

import java.io.FileFilter;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 11:58
 */

@Data
public class Ftp {
    private String id;
    private String host;
    private String username;
    private String password;
    private int port;
    private String charset;
    private String skipDir;
    private String fileFilter;
    private String dir;
    private String systemId;
    private String fileType;
    private String impl_type;
    private String fileNameRegular;
}
