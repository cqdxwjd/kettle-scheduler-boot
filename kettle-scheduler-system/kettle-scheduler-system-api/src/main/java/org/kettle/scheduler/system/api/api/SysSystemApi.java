package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.entity.System;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 16:30
 */
@Api(tags = "系统管理API")
@RequestMapping("/sys/system")
public interface SysSystemApi {

    @ApiOperation(value = "获取系统列表")
    @GetMapping("/getSystemList.do")
    Result<List<System>> getSystemList();

    @ApiOperation(value = "根据ID查询系统列表")
    @PostMapping("/getSystemById.do")
    Result<System> getSystemById(@RequestParam String id);

    @ApiOperation(value = "新增系统")
    @PostMapping("/addSystem.do")
    Result addSystem(@RequestBody System system);

    @ApiOperation(value = "修改系统信息")
    @PostMapping("/updateSystem.do")
    Result updateSystem(@RequestBody System system);

    @ApiOperation(value = "根据ID删除系统")
    @PostMapping("/deleteSystem.do")
    Result deleteSystem(@RequestParam String id);

    @ApiOperation(value = "搜索系统")
    @GetMapping("/searchSystem")
    Result<List<System>> searchSystem(@RequestParam String keyword);
}
