package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.SysMviewTagApi;
import org.kettle.scheduler.system.api.entity.MviewTag;
import org.kettle.scheduler.system.biz.service.SysMviewTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysMviewTagApiController implements SysMviewTagApi {

    @Autowired
    SysMviewTagService sysMviewTagService;

    @Override
    public Result add(MviewTag mviewTag) {
        sysMviewTagService.add(mviewTag);
        return Result.ok();
    }

    @Override
    public Result<List<MviewTag>> findMviewTagByParentId(String parentId) {
        return Result.ok(sysMviewTagService.findMviewTagByParentId(parentId));
    }

    @Override
    public Result delete(String id) {
        Result<Object> result = null;
        int delete = sysMviewTagService.delete(id);
        if (delete > 0) {
            result = Result.ok();
        } else {
            result = Result.error("请先删除下级项目！");
        }
        return result;
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        return null;
    }

    @Override
    public Result update(MviewTag mviewTag) {
        return Result.ok(sysMviewTagService.update(mviewTag));
    }

    @Override
    public Result<PageOut<MviewTag>> findMviewListByPage(QueryHelper<MviewTag> req) {
        return null;
    }

    @Override
    public Result<MviewTag> getMviewDetail(Integer id) {
        return null;
    }

    @Override
    public Result<List<MviewTag>> findMviewList() {
        return Result.ok(sysMviewTagService.findMviewTagList());
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
