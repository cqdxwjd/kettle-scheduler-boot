package org.kettle.scheduler.system.biz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.FtpApi;
import org.kettle.scheduler.system.api.entity.Ftp;
import org.kettle.scheduler.system.biz.service.FtpService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Result<PageInfo> getFtpList(int page, int rows) {
        PageHelper.startPage(page, rows);
        PageInfo<Ftp> pageinfo = new PageInfo<>(ftpService.getFtpList());
        return Result.ok(pageinfo);
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

    @Override
    public Result<PageInfo> searchFtp(int page, int rows, String keyword) {
        PageHelper.startPage(page, rows);
        PageInfo<Ftp> pageInfo = new PageInfo<>(ftpService.searchFtp(keyword));
        return Result.ok(pageInfo);
    }
}
