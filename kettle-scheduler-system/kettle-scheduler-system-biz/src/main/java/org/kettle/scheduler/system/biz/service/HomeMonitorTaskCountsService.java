package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.biz.component.EntityManagerUtil;
import org.kettle.scheduler.system.biz.entity.HomeMonitorTaskCounts;
import org.kettle.scheduler.system.biz.repository.HomeMonitorTaskCountsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @auther chen1
 * @create 2020-07-06 15:11
 */
@Service
public class HomeMonitorTaskCountsService {

    private final HomeMonitorTaskCountsRepository homeMonitorTaskCountsRepository;
    private final EntityManagerUtil entityManagerUtil;

    public HomeMonitorTaskCountsService(HomeMonitorTaskCountsRepository homeMonitorTaskCountsRepository, EntityManagerUtil entityManagerUtil) {
        this.homeMonitorTaskCountsRepository = homeMonitorTaskCountsRepository;
        this.entityManagerUtil = entityManagerUtil;
    }


    public Page<HomeMonitorTaskCounts> findAll(Pageable pageable) {
        return homeMonitorTaskCountsRepository.findAll(pageable);
    }
}