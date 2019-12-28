package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
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

    /**
     * 调用数据库
     * @param keyword
     * @param results
     * @return
     */
    @Select("{#{results,mode=OUT,jdbcType=VARCHAR} = call REFRESH_MVIEW(#{keyword,mode=IN,jdbcType=VARCHAR})}")
    //@Select("{#{results} = call dbms_mview.refresh@dc_edw_link('MV_ITEM_TOP_QX')}")
    Object refreshMview(@Param("keyword") String keyword, Object results);
}
