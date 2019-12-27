package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysMviewTagApi;
import org.kettle.scheduler.system.api.request.MviewTagReq;
import org.kettle.scheduler.system.biz.entity.MviewTag;
import org.kettle.scheduler.system.biz.mapper.SysMviewTagMapper;
import org.kettle.scheduler.system.biz.service.SysMviewTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMviewTagApiController implements SysMviewTagApi {

    private final SysMviewTagService sysMviewTagService;

    @Autowired
    private SysMviewTagMapper sysMviewTagMapper;

    public SysMviewTagApiController(SysMviewTagService sysMviewTagService) {
        this.sysMviewTagService = sysMviewTagService;
    }

    @Override
    public Result add(MviewTagReq req) {
        sysMviewTagService.add(req);
        return Result.ok();
    }

    @Override
    public Result findMviewTagByParentId(String parentId) {
        return Result.ok(sysMviewTagService.findMviewTagByParentId(parentId));
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
    public Result update(MviewTagReq req) {
        return null;
    }

    @Override
    public Result<PageOut<MviewTagReq>> findMviewListByPage(QueryHelper<MviewTagReq> req) {
        return null;
    }

    @Override
    public Result<MviewTagReq> getMviewDetail(Integer id) {
        return null;
    }

    @Override
    public Result<List<MviewTagReq>> findMviewList() {
        //return Result.ok(sysMviewTagService.findMviewTagList());
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
    public Obje test() {
        return Result.ok(sysMviewTagMapper.getMviewListTagList());
    }
}
