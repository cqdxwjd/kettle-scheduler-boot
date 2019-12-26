package org.kettle.scheduler.system.biz.repository;

import org.kettle.scheduler.system.biz.entity.Mview;
import org.kettle.scheduler.system.biz.entity.Quartz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:39
 */
public interface MviewRepository extends JpaRepository<Mview, Integer>, JpaSpecificationExecutor<Mview> {
}
