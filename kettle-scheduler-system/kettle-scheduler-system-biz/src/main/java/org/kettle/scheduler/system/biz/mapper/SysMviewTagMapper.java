package org.kettle.scheduler.system.biz.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.kettle.scheduler.system.biz.entity.MviewTag;

import java.util.List;

@Mapper
public interface SysMviewTagMapper {

    @Select("select * from k_mview_tag")
    List<MviewTag> getMviewListTagList();


}
