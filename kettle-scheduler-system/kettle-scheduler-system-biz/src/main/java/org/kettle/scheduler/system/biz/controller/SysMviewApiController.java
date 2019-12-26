package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysMviewApi;
import org.kettle.scheduler.system.api.request.MviewReq;
import org.kettle.scheduler.system.api.response.MviewRes;
import org.kettle.scheduler.system.biz.service.SysMviewService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMviewApiController implements SysMviewApi {

    private final SysMviewService sysMviewService;

    public SysMviewApiController(SysMviewService sysMviewService) {
        this.sysMviewService = sysMviewService;
    }

    @Override
    public Result add(MviewReq req) {
        return null;
    }

    @Override
    public Result delete(Integer id) {
        return null;
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        return null;
    }

    @Override
    public Result update(MviewReq req) {
        return null;
    }

    @Override
    public Result<PageOut<MviewRes>> findMviewListByPage(QueryHelper<MviewReq> req) {
        return null;
    }

    @Override
    public Result<MviewRes> getMviewDetail(Integer id) {
        return null;
    }

    @Override
    public Result<List<MviewRes>> findMviewList() {
        return Result.ok(sysMviewService.findMviewList());
    }

    @Override
    public Result<Boolean> synchronizeMview() {
        return null;
    }

    @Override
    public Result<Boolean> updateMview(List<String> mviewList) {
        return null;
    }
}
