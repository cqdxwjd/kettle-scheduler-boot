package org.kettle.scheduler.system.api.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.system.api.request.CountSumReq;
import org.springframework.web.bind.annotation.*;

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
    public Object count(@RequestBody QueryHelper<CountSumReq> countSumReq);
}

