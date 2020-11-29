package org.kettle.scheduler.system.biz.controller;

import com.google.common.collect.Maps;
import org.kettle.scheduler.common.povo.PageHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.system.api.api.HomeApi;
import org.kettle.scheduler.system.api.response.HomeMonitorTaskCountRes;
import org.kettle.scheduler.system.api.response.HomeMonitorTaskRunRes;
import org.kettle.scheduler.system.biz.entity.HomeMonitorTaskCounts;
import org.kettle.scheduler.system.biz.service.HomeMonitorTaskCountsService;
import org.kettle.scheduler.system.biz.service.SysJobService;
import org.kettle.scheduler.system.biz.service.SysTransService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页信息
 *
 * @author lyf
 */
@RestController
public class HomeApiController implements HomeApi {
	private final SysJobService jobService;
	private final SysTransService transService;
	private final HomeMonitorTaskCountsService homeMonitorTaskCountsService;

	public HomeApiController(SysJobService jobService, SysTransService transService,HomeMonitorTaskCountsService homeMonitorTaskCountsService) {
		this.jobService = jobService;
		this.transService = transService;
		this.homeMonitorTaskCountsService = homeMonitorTaskCountsService;
	}

	/**
	 * 首页监控任务统计
	 *
	 * @return {@link Result}
	 */
	@Override
	public Result<HomeMonitorTaskCountRes> taskCount() {
		// 统计运行的转换数量
		Integer transTaskNum = transService.countRunTrans();
		// 统计运行的作业数量
		Integer jobTaskNum = jobService.countRunJob();

		HomeMonitorTaskCountRes res = new HomeMonitorTaskCountRes();
		res.setTotalTaskNum(transTaskNum + jobTaskNum);
		res.setJobTaskNum(jobTaskNum);
		res.setTransTaskNum(transTaskNum);
		return Result.ok(res);
	}

	/**
	 * 首页监控任务统计
	 *
	 * @return {@link Result}
	 */
	@Override
	public Result<List<HomeMonitorTaskRunRes>> runStatus() {
		PageHelper page = new PageHelper();
		page.setNumber(1);
		page.setSize(7);
		Pageable pageable = page.getPageable(Sort.by(Sort.Direction.DESC, "startDate"));

		Page<HomeMonitorTaskCounts> pageList = homeMonitorTaskCountsService.findAll(pageable);
		Map<String,HomeMonitorTaskRunRes> resMap = Maps.newHashMap();
		List<HomeMonitorTaskRunRes> list = pageList.get().map(t ->
		{

			HomeMonitorTaskRunRes res = resMap.get(t.getStartDate());
			if (res == null){
				res = new HomeMonitorTaskRunRes();
			}
			if (t.getType() == 1) {
				res.setTransRunNum(t.getCounts());
			}else {
				res.setJobRunNum(t.getCounts());
			}
			res.setRunTime(t.getStartDate());
			resMap.put(res.getRunTime(),res);
			return res;
		}).collect(Collectors.toList());
		return Result.ok(list);
	}

}