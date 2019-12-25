package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysMviewApi;
import org.kettle.scheduler.system.api.request.MviewReq;
import org.kettle.scheduler.system.api.response.QuartzRes;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMviewApiController implements SysMviewApi {

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
    public Result<PageOut<QuartzRes>> findQuartzListByPage(QueryHelper<MviewReq> req) {
        return null;
    }

    @Override
    public Result<QuartzRes> getQuartzDetail(Integer id) {
        return null;
    }

    @Override
    public Result<List<QuartzRes>> findQuartzList() {
        return null;
    }
}
