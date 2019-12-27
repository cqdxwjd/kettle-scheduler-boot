package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.kettle.scheduler.system.api.entity.Mview;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-27 17:41
 */
@Mapper
public interface SysMviewMapper {

    @Select("select * from k_mview")
    List<Mview> findMviewList();

    @Select("select * from k_mview where mview_tag_id=#{tagId}")
    List<Mview> findMviewByTagId(@Param("tagId") String tagId);
}
