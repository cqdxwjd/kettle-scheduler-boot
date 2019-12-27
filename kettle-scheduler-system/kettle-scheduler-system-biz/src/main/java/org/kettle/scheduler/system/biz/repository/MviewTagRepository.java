package org.kettle.scheduler.system.biz.repository;

import org.kettle.scheduler.system.biz.entity.MviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:39
 */
public interface MviewTagRepository extends JpaRepository<MviewTag, Integer>, JpaSpecificationExecutor<MviewTag> {

    List<MviewTag> findByparentId(String parent_Id);
}
