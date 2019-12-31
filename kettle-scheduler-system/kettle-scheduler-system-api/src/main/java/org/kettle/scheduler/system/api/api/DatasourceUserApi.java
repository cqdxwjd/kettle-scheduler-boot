package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 20:25
 */
@Api(tags = "数据源用户管理API")
@RequestMapping("sys/datasourceUser")
public interface DatasourceUserApi {

    @GetMapping("/getDatasourceUserList")
    @ApiOperation("获取DatasourceUser列表")
    Result<List<DatasourceUser>> getDatasourceUserList();

    @GetMapping("/getDatasourceUserById")
    @ApiOperation("根据ID获取数据源用户信息")
    Result<DatasourceUser> getDatasourceUserById(@RequestParam String id);

    @PostMapping("/addDatasourceUser")
    @ApiOperation("新增数据源用户")
    Result addDatasourceUser(@RequestBody DatasourceUser datasourceUser);

    @PostMapping("/addDatasourceUserList")
    @ApiOperation("批量新增")
    Result addDatasourceUserList(@RequestBody List<DatasourceUser> datasourceUserList);

    @PostMapping("/updateDatasourceUser")
    @ApiOperation("修改")
    Result updateDatasourceUser(@RequestBody DatasourceUser DatasourceUser);

    @PostMapping("/deleteDatasourceUser")
    @ApiOperation("删除")
    Result deleteDatasourceUser(@RequestParam String id);
}
