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
public class FtpFile implements Comparable<FtpFile>{

    String id;
    String fileName;
    String filePath;
    long size;
    Date time;
    String errorMessage;
    String status;

    public FtpFile(String fileName, String filePath, long size, Date time) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.size = size;
        this.time = time;
    }

    public FtpFile() {
    }


    @Override
    public int compareTo(@NotNull FtpFile o) {
        return fileName.compareTo(o.getFileName());
    }
}
