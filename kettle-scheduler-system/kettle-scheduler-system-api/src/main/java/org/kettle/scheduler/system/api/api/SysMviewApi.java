package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.request.MviewReq;
import org.kettle.scheduler.system.api.response.QuartzRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "物化视图API")
@RequestMapping("/sys/mview")
public interface SysMviewApi {
    /**
     * 添加物化视图
     *
     * @param req {@link MviewReq}
     * @return {@link Result}
     */
    @ApiOperation(value = "添加物化视图")
    @PostMapping("/add.do")
    Result add(@RequestBody MviewReq req);

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
     * @param req {@link MviewReq}
     * @return {@link Result}
     */
    @ApiOperation(value = "更新物化视图")
    @PutMapping("/update.do")
    Result update(@RequestBody MviewReq req);

    /**
     * 根据条件查询物化视图列表
     *
     * @param req {@link QueryHelper}
     * @return {@link Result}
     */
    @ApiOperation(value = "根据条件查询物化视图列表")
    @PostMapping("/findQuartzListByPage.do")
    Result<PageOut<QuartzRes>> findQuartzListByPage(@RequestBody QueryHelper<MviewReq> req);

    /**
     * 查询物化视图明细
     *
     * @param id 根据id查询
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图明细")
    @GetMapping("/getQuartzDetail.do")
    Result<QuartzRes> getQuartzDetail(@RequestParam("id") Integer id);

    /**
     * 查询物化视图列表
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "查询物化视图列表")
    @GetMapping("/findQuartzList.do")
    Result<List<QuartzRes>> findQuartzList();
}
