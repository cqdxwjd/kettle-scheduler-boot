package org.kettle.scheduler.system.api.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.entity.Ftp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 19:25
 */

@Api(tags = "FTP管理")
@RequestMapping("/sys/ftp")
public interface FtpApi {

    @GetMapping("/getFtpList")
    @ApiOperation(value = "获取FTP列表")
    Result<PageInfo> getFtpList(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int rows);

    @GetMapping("/getFtpById")
    @ApiOperation(value = "根据ID获取FTP信息")
    Result<Ftp> getFtpById(@RequestParam  String id);

    @PostMapping("/addFtp")
    @ApiOperation(value = "新增FTP配置")
    Result addFtp(@RequestBody Ftp ftp);

    @PostMapping("/addFtpList")
    @ApiOperation("批量增加FTP配置信息")
    Result addFtpList(@RequestBody List<Ftp> ftpList);

    @PostMapping("/updateFtp")
    @ApiOperation(value = "修改FTP信息")
    Result updateFtp(@RequestBody Ftp ftp);

    @PostMapping("/deleteFtp")
    @ApiOperation(value = "删除FTP")
    Result deleteFtp(@RequestParam String id);

    @GetMapping("/searchFtp")
    @ApiOperation(value = "搜索Ftp")
    Result<PageInfo> searchFtp(@RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "10") int rows,
                               @RequestParam String keyword);
}
