package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.system.api.request.CountSumReq;
import org.kettle.scheduler.system.api.response.CountSumRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "数据统计API")
@RequestMapping("/sys/count")
public interface SysCountApi {
    /**
     * 数据合计
     * @param countSumReq
     * @return
     */
    @ApiOperation(value = "合计")
    @PostMapping ("/sum.do")
    public CountSumRes count(@RequestBody QueryHelper<CountSumReq> countSumReq);

    /**
     * 获取最近七天采集情况
     * @return
     */
    @ApiOperation(value = "获取最近七天采集情况")
    @GetMapping ("/getLastSevenDaysNum.do")
    public List<Map> getLastSevenDaysNum();

    /**
     * 获取今天采集情况
     * @return
     */
    @ApiOperation(value = "获取今天采集情况")
    @GetMapping ("/getTodayNum.do")
    public Map getTodayNum();

    /**
     * 获取ODS服务器表空间使用情况
     * @return
     */
    @ApiOperation(value = "获取ODS服务器表空间使用情况")
    @GetMapping ("/getSystemDisk.do")
    public Map getSystemDisk();

    /**
     * 通过日志获取各业务系统数据情况
     * @return
     */
    @ApiOperation(value = "获取ODS采集情况，按业务系统汇总")
    @GetMapping ("/getDataBySystem.do")
    public List<Map> getDataBySystem();
}

