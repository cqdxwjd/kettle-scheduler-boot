package org.kettle.scheduler.system.biz.repository;

import org.kettle.scheduler.system.biz.entity.TransLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransLogRepository extends JpaRepository<TransLog, Integer>, JpaSpecificationExecutor<TransLog> {

}
