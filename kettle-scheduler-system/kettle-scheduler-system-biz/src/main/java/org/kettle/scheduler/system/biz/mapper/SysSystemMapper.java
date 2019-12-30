package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.*;
import org.kettle.scheduler.system.api.entity.System;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 12:34
 */

@Mapper
public interface SysSystemMapper {

    @Select("select * from k_system")
    List<System> getSystemList();

    @Select("select * from k_system where id=#{id}")
    System getSystemById(@Param("id") String id);

    @Insert("insert into k_system(system_name,tag_id,datasource) values(#{systemName},#{tagId},#{datasource})")
    int addSystem(System system);

    @Insert({
            "<script>",
            "insert into k_system(system_name,tag_id,datasource)    ",
            " SELECT A.* from (" +
                    "<foreach collection='systemList' item='item' index='index' separator='union all'>",
            " select  #{item.systemName},#{item.tagId},#{item.datasource} from dual",
            "</foreach>" +
                    ")A",
            "</script>"
    })
    int addSystemList(@Param("systemList") List<System> systemList);

    @Update("update k_system set system_name=#{systemName},tag_id=#{tagId},datasource=#{datasource} where id=#{id}")
    int updateSystem(System system);

    @Delete("delete k_system where id=#{id}")
    int deleteSystem(@Param("id") String id);

    @Select("select * from k_system where system_name like CONCAT(CONCAT('%',#{keyword}),'%')")
    List<System> searchSystem(@Param("keyword") String keyword);
}
