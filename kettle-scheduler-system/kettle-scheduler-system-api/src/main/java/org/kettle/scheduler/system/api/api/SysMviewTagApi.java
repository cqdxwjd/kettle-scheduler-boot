package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.request.MviewTagReq;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-27 13:29
 */
@Api(tags = "物化视图TAG API")
@RequestMapping("/sys/mviewTag")
public interface SysMviewTagApi {

    /**
     * 添加物化视图
     *
     * @param req {@link MviewTagReq}
     * @return {@link Result}
     */
    @ApiOperation(value = "添加物化视图")
    @PostMapping("/add.do")
    Result add(@RequestBody MviewTagReq req);

    /**
     * 获取一级物化视图标签
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "获取一级物化视图标签")
    @GetMapping("/findMviewTagByParentId.do")
    Result findMviewTagByParentId(String parentId);
    /**
     * 通过id删除物化视图
     *
     * @param id 要删除的数据的id
     * @return {@link Result}
     */
    @ApiOperation(value = "通过id删除物化视图")
    @DeleteMapping("/delete.do")
    Result delete(@RequestParam("id") Integer id);

    /**
     * 批量删除物化视图
     *
     * @param ids 要删除数据的{@link List}集
     * @return {@link Result}
     */
    @ApiOperation(value = "批量删除物化视图")
    @DeleteMapping("/deleteBatch.do")
    Result deleteBatch(@RequestBody List<Integer> ids);

    /**
     * 更新物化视图
     *
     * @param req {@link MviewTagReq}
     * @return {@link Result}
     */
    @ApiOperation(value = "更新物化视图")
    @PutMapping("/update.do")
    Result update(@RequestBody MviewTagReq req);

    /**
     * 根据条件查询物化视图列表
     *
     * @param req {@link QueryHelper}
     * @return {@link Result}
     */
    @ApiOperation(value = "根据条件查询物化视图列表")
    @PostMapping("/findMviewListByPage.do")
    Result<PageOut<MviewTagReq>> findMviewListByPage(@RequestBody QueryHelper<MviewTagReq> req);

    /**
     * 查询物化视图明细
     *
     * @param id 根据id查询
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图标签明细")
    @GetMapping("/getMviewDetail.do")
    Result<MviewTagReq> getMviewDetail(@RequestParam("id") Integer id);

    /**
     * 查询物化视图列表
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图列表")
    @GetMapping("/findMviewList.do")
    Result<List<MviewTagReq>> findMviewList();

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
}
