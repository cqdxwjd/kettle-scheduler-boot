package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.*;
import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.biz.service.DataSourceUserService;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 20:14
 */

@Mapper
public interface DatasourceUserMapper {

    @Select("select * from k_datasource_user")
    List<DatasourceUser> getDatasourceUserList();

    @Select("select * from k_datasource_user where id=#{id}")
    DatasourceUser getDatasourceUserById(@Param("id") String id);

    @Select("select * from k_datasource_user where admdivcode=#{admdivcode}")
    DatasourceUser getDatasourceUserByAdmdivcode(@Param("admdivcode") String admdivcode);

    @Insert("insert into k_datasource_user(username,password,tablespace,admdivcode,db_type,system_id,database_name,database_port,database_host,last_Impl_Date) values(#{username},#{password},#{tablespace},#{admdivcode},#{dbType},#{systemId},#{databaseName},#{databasePort},#{databaseHost},#{lastImplDate})")
    int addDatasourceUser(DatasourceUser datasourceUser);

    int addDatasourceUserList(List<DatasourceUser> datasourceUserList);

    @Update("update k_datasource_user set username=#{username},password=#{password},tablespace=#{tablespace},admdivcode=#{admdivcode},db_type=#{dbType},system_id=#{systemId},database_name=#{databaseName},database_port=#{databasePort},database_host=#{databaseHost},last_impl_date=#{lastImplDate} where id=#{id}")
    int updateDatasourceUser(DatasourceUser datasourceUser);

    @Delete("delete k_datasource_user where id=#{id}")
    int deleteDatasourceUser(String id);

}
