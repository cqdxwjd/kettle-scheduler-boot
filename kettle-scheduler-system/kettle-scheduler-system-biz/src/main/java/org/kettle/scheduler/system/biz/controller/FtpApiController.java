package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.FtpApi;
import org.kettle.scheduler.system.api.entity.Ftp;
import org.kettle.scheduler.system.biz.service.FtpService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.peer.FramePeer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 19:33
 */
@RestController
public class FtpApiController implements FtpApi {

    @Autowired
    FtpService ftpService;

    @Override
    public Result<List<Ftp>> getFtpList() {
        return Result.ok(ftpService.getFtpList());
    }

    @Override
    public Result<Ftp> getFtpById(String id) {
        return Result.ok(ftpService.getFtpById(id));
    }

    @Override
    public Result addFtp(Ftp ftp) {
        return Result.ok(ftpService.addFtp(ftp));
    }

    @Override
    public Result addFtpList(List<Ftp> ftpList) {
        return Result.ok(ftpService.addFtpList(ftpList));
    }

    @Override
    public Result updateFtp(Ftp ftp) {
        return Result.ok(ftpService.updateFtp(ftp));
    }

    @Override
    public Result deleteFtp(String id) {
        return Result.ok(ftpService.deleteFtp(id));
    }
}
