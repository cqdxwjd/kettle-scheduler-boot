package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.DatasourceUserApi;
import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.biz.service.DataSourceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 20:32
 */
@RestController
public class DatasourceUserApiController implements DatasourceUserApi {

    @Autowired
    DataSourceUserService dataSourceUserService;


    @Override
    public Result<List<DatasourceUser>> getDatasourceUserList() {
        return Result.ok(dataSourceUserService.getDatasourceUserList());
    }

    @Override
    public Result<DatasourceUser> getDatasourceUserById(String id) {
        return Result.ok(dataSourceUserService.getDatasourceUserById(id));
    }

    @Override
    public Result addDatasourceUser(DatasourceUser datasourceUser) {
        return Result.ok(dataSourceUserService.addDatasourceUser(datasourceUser));
    }

    @Override
    public Result addDatasourceUserList(List<DatasourceUser> datasourceUserList) {
        return Result.ok(dataSourceUserService.addDatasourceUserList(datasourceUserList));
    }

    @Override
    public Result updateDatasourceUser(DatasourceUser datasourceUser) {
        return Result.ok(dataSourceUserService.updateDatasourceUser(datasourceUser));
    }

    @Override
    public Result deleteDatasourceUser(String id) {
        return Result.ok(dataSourceUserService.deleteDatasourceUser(id));
    }
}
