package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.biz.entity.FtpFile;
import org.kettle.scheduler.system.biz.mapper.FtpFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author leo
 * @create 2020-01-01 18:00
 */
@Service
public class FtpFileService {

    @Autowired
    FtpFileMapper ftpFileMapper;

    public int addFtpFile(FtpFile ftpFile) {
        return ftpFileMapper.addFtpFile(ftpFile);
    }
    public FtpFile getFtpFileByBatchNoAndFileName(String batchNo,String fileName){
        return ftpFileMapper.getFtpFileByBatchNoAndFileName(batchNo,fileName);
    }
}
