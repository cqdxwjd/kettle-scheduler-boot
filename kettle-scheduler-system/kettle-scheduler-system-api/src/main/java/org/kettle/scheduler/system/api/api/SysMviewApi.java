package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.entity.Mview;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "物化视图API")
@RequestMapping("/sys/mview")
public interface SysMviewApi {
    /**
     * 添加物化视图
     *
     * @param req {@link Mview}
     * @return {@link Result}
     */
    @ApiOperation(value = "添加物化视图")
    @PostMapping("/add.do")
    Result add(@RequestBody Mview req);

    /**
     * 通过id删除物化视图
     *
     * @param id 要删除的数据的id
     * @return {@link Result}
     */
    @ApiOperation(value = "通过id删除物化视图")
    @DeleteMapping("/delete.do")
    Result delete(@RequestParam("id") String id);

    /**
     * 批量删除物化视图
     *
     * @param ids 要删除数据的{@link List}集
     * @return {@link Result}
     */
    @ApiOperation(value = "批量删除物化视图")
    @DeleteMapping("/deleteBatch.do")
    Result deleteBatch(@RequestBody List<String> ids);

    /**
     * 更新物化视图
     *
     * @param req {@link Mview}
     * @return {@link Result}
     */
    @ApiOperation(value = "更新物化视图")
    @PutMapping("/update.do")
    Result update(@RequestBody Mview req);

    /**
     * 根据条件查询物化视图列表
     *
     * @param req {@link QueryHelper}
     * @return {@link Result}
     */
    @ApiOperation(value = "根据条件查询物化视图列表")
    @PostMapping("/findMviewListByPage.do")
    Result<PageOut<Mview>> findMviewListByPage(@RequestBody QueryHelper<Mview> req);

    /**
     * 查询物化视图明细
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图明细")
    @GetMapping("/getMviewDetail.do")
    Result<Mview> getMviewDetail(@RequestParam("id") String id);

    /**
     * 查询物化视图列表
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图列表")
    @GetMapping("/findMviewList.do")
    Result<List<Mview>> findMviewList();

    /**
     * 通过DBLINK获取到所有视图列表，和当前已存储的列表对比
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "同步物化视图")
    @GetMapping("/synchronizeMview.do")
    Result<Boolean> synchronizeMview();

    /**
     * 通过前台页面勾选对应物化视图，刷新相应
     *
     * @param mviewList
     * @return
     */
    @ApiOperation(value = "刷新物化视图")
    @GetMapping("/refreshMview.do")
    Result<Boolean> updateMview(@RequestBody List<String> mviewList);

    /**
     * 通过Mview_tag_id查询所有视图
     *
     * @param mviewList
     * @return
     */
    @ApiOperation(value = "通过Mview_tag_id查询所有视图")
    @GetMapping("/findMviewByTagId.do")
    Result<List<Mview>> findMviewByTagId(@RequestParam("tagId") String tagId);
}
