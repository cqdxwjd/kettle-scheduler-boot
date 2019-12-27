package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysMviewApi;
import org.kettle.scheduler.system.api.entity.Mview;
import org.kettle.scheduler.system.biz.service.SysMviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMviewApiController implements SysMviewApi {

    @Autowired
    SysMviewService sysMviewService;


    @Override
    public Result add(Mview req) {
        return null;
    }

    @Override
    public Result delete(String id) {
        return null;
    }

    @Override
    public Result deleteBatch(List<String> ids) {
        return null;
    }

    @Override
    public Result update(Mview req) {
        return null;
    }

    @Override
    public Result<PageOut<Mview>> findMviewListByPage(QueryHelper<Mview> req) {
        return null;
    }

    @Override
    public Result<Mview> getMviewDetail(String id) {
        return null;
    }

    @Override
    public Result<List<Mview>> findMviewList() {
        return null;
    }

    @Override
    public Result<Boolean> synchronizeMview() {
        return null;
    }

    @Override
    public Result<Boolean> updateMview(List<String> mviewList) {
        return null;
    }

    @Override
    public Result<List<Mview>> findMviewByTagId(String tagId) {
        return Result.ok(sysMviewService.findMviewList());
    }
}
