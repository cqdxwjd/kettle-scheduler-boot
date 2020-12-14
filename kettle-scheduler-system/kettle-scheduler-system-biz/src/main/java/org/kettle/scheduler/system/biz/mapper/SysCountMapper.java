package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCountMapper {

    @Select("select rownum,day,data_num from (select t.day,sum(t.num) as data_num from V_DATA_COLLECT_COUNTS t where day>to_char(sysdate-7,'yyyy-MM-dd') group by day order by day asc)")
    List<Map> getLastSevenDaysNum();

    @Select("SELECT ( SELECT sum(num) FROM V_DATA_COLLECT_COUNTS WHERE day = to_char(sysdate, 'yyyy-MM-dd') ) - ( SELECT sum(num) FROM v_data_collect_counts WHERE day = to_char(sysdate - 1, 'yyyy-MM-dd') AND category_id IN ( SELECT category_id FROM V_DATA_COLLECT_COUNTS WHERE day = to_char(sysdate, 'yyyy-MM-dd') ) ) AS today_num FROM dual")
    Map getTodayNum();

    /**
     * 通过dblink获取表空间使用情况
     * @return
     */
    @Select("SELECT round(SUM(bytes / (1024 * 1024 * 1024)), 0) AS ts_size FROM dba_tablespaces t, dba_data_files d WHERE t.tablespace_name = d.tablespace_name")
    Map<String, String> getSystemDisk();

    /**
     * 按系统获取采集数量情况
     * @return
     */
    @Select("select * from V_DATA_COLLECT_COUNTS WHERE day = to_char(sysdate, 'yyyy-MM-dd')")
    List<Map> getDataBySystem();
}
