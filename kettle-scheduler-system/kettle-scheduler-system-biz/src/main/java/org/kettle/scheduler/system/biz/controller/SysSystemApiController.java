package org.kettle.scheduler.system.biz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysSystemApi;
import org.kettle.scheduler.system.api.entity.System;
import org.kettle.scheduler.system.biz.service.SysSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 16:34
 */
@RestController
public class SysSystemApiController implements SysSystemApi {

    @Autowired
    SysSystemService sysSystemService;

    @Override
    public Result<PageInfo> getSystemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        PageInfo<System> pageInfo = new PageInfo<>(sysSystemService.getSystemList());
        return Result.ok(pageInfo);
    }

    @Override
    public Result<System> getSystemById(String id) {
        return Result.ok(sysSystemService.getSystemById(id));
    }

    @Override
    public Result addSystem(System system) {
        return Result.ok(sysSystemService.addSystem(system));
    }

    @Override
    public Result updateSystem(System system) {
        return Result.ok(sysSystemService.updateSystem(system));
    }

    @Override
    public Result deleteSystem(String id) {
        sysSystemService.deleteSystem(id);
        return Result.ok();
    }

    @Override
    public Result<PageInfo> searchSystem(int page, int rows, String keyword) {
        PageHelper.startPage(page, rows);
        PageInfo<System> pageInfo = new PageInfo<>(sysSystemService.searchSystem(keyword));
        return Result.ok(pageInfo);
    }

}
