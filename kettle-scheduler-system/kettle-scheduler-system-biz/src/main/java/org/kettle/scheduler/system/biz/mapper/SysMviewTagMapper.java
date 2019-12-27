package org.kettle.scheduler.system.biz.mapper;


import org.apache.ibatis.annotations.*;
import org.kettle.scheduler.system.api.entity.MviewTag;

import java.util.List;

@Mapper
public interface SysMviewTagMapper {

    @Select("select * from k_mview_tag")
    List<MviewTag> getMviewListTagList();

    @Insert("insert into (mview_tag_code,mview_tag_name,parent_id) values(#{mviewTagCode},#{mviewTagName})")
    int add(MviewTag mviewTag);

    @Select("select * from k_mview_tag where parent_id=#{parentId}")
    List<MviewTag> findMviewTagByParentId(@Param("parentId") String parentId);

    @Update("update k_mview_tag set mview_tag_code=#{mviewTagCode},mview_tag_name=#{mviewTagName},parent_id=#{parentId} where id=#{id}")
    int update(MviewTag mviewTag);
}
