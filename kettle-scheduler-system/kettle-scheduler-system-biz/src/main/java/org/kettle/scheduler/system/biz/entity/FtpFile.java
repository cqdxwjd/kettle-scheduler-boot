package org.kettle.scheduler.system.biz.entity;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-29 16:39
 */
@Data
public class FtpFile implements Comparable<FtpFile> {

    String id;
    String fileName;
    String fileDir;
    String filePath;
    long fileSize;
    Date fileTime;
    String errorMessage;
    String status;
    String batchNo;

    public FtpFile(String fileName, String fileDir, String filePath, long fileSize, Date fileTime,String batchNo) {
        this.fileName = fileName;
        this.fileDir = fileDir;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileTime = fileTime;
        this.batchNo = batchNo;
    }

    public FtpFile() {
    }


    @Override
    public int compareTo(@NotNull FtpFile o) {
        return fileName.compareTo(o.getFileName());
    }
}
