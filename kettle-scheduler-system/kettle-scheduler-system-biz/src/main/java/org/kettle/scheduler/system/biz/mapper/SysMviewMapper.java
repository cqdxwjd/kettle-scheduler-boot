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

    @Select("select * from k_mview where mview_name like CONCAT(CONCAT('%',#{keyword}),'%') or mview_tag_id like CONCAT(CONCAT('%',#{keyword}),'%')")
    List<Mview> findMviewByNameOrTag(@Param("keyword") String keyword);

    /**
     * 调用数据库
     *
     * @param keyword
     * @param results
     * @return
     */
    @Select("{call REFRESH_MVIEW(#{keyword,mode=IN,jdbcType=VARCHAR})}")
    //@Select("{#{results} = call dbms_mview.refresh@dc_edw_link('MV_ITEM_TOP_QX')}")
    Object refreshMview(@Param("keyword") String keyword);

    /**
     * 根据物化视图名称，从DC_EDW中同步物化视图更新结果
     *
     * @param mviewName
     * @return
     */
    @Select("SELECT OWNER,MVIEW_NAME,LAST_REFRESH_DATE,REFRESH_METHOD,INVALID FROM ALL_MVIEW_ANALYSIS@dc_edw_link where mview_name=upper(#{mviewName})")
    List<Mview> syncMview(@Param("mviewName") String mviewName);

    /**
     * 根据物化视图所有者和物化视图名称，更新相应信息
     *
     * @param mview
     * @return
     */
    @Update("<script>update k_mview set LAST_REFRESH_DATE=#{lastRefreshDate}, REFRESH_METHOD=#{refreshMethod}, INVALID=#{invalid} where owner=#{owner} and mview_name=#{mviewName}</script>")
    int updateMview(Mview mview);
}
