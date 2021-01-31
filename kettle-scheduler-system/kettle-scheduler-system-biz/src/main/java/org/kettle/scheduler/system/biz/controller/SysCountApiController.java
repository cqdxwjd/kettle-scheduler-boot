package org.kettle.scheduler.system.biz.controller;

import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.system.api.api.SysCountApi;
import org.kettle.scheduler.system.api.request.CountSumReq;
import org.kettle.scheduler.system.api.response.CountSumRes;
import org.kettle.scheduler.system.biz.service.SysTransMonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SysCountApiController implements SysCountApi {
    private final SysTransMonitorService transMonitorService;

    public SysCountApiController(SysTransMonitorService transMonitorService) {
        this.transMonitorService = transMonitorService;
    }
    public CountSumRes count(@RequestBody QueryHelper<CountSumReq> countSumReq){
        return transMonitorService.count(countSumReq.getQuery(),countSumReq.getPage().getPageable());
    }
    @Override
    public List<Map> getLastSevenDaysNum() {
        return transMonitorService.getLastSevenDaysNum();
    }

    @Override
    public Map getTodayNum() {
        return transMonitorService.getTodayNum();
    }

    @Override
    public Map getSystemDisk() {
        return transMonitorService.getSystemDisk();
    }

    @Override
    public List<Map> getDataBySystem() {
        return transMonitorService.getDataBySystem();
    }
}
