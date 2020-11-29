package org.kettle.scheduler.system.biz.repository;

import org.kettle.scheduler.system.biz.entity.HomeMonitorTaskCounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HomeMonitorTaskCountsRepository extends JpaRepository<HomeMonitorTaskCounts, Integer>, JpaSpecificationExecutor<HomeMonitorTaskCounts> {


}