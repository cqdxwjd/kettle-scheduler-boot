package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.api.entity.Ftp;
import org.kettle.scheduler.system.biz.mapper.FtpMappr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 19:34
 */

@Service
public class FtpService {

    @Autowired
    FtpMappr ftpMappr;

    public List<Ftp> getFtpList() {
        return ftpMappr.getFtpList();
    }

    public Ftp getFtpById(String id) {
        return ftpMappr.getFtpById(id);
    }

    public int addFtp(Ftp ftp) {
        return ftpMappr.addFtp(ftp);
    }

    public int addFtpList(List<Ftp> ftpList) {
        return ftpMappr.addFtpList(ftpList);
    }

    public int updateFtp(Ftp ftp) {
        return ftpMappr.updateFtp(ftp);
    }

    public int deleteFtp(String id) {
        return ftpMappr.deleteFtp(id);
    }
}
