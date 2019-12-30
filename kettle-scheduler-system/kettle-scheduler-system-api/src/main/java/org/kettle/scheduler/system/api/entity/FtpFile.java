package org.kettle.scheduler.system.api.entity;

import lombok.Data;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 12:03
 */

@Data
public class FtpFile {

    private String id;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String fileTime;
    private String errorMessage;
    private String status;
    private String ftpId;
}
