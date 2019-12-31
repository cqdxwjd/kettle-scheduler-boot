package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.*;
import org.kettle.scheduler.system.api.entity.Ftp;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 19:35
 */

@Mapper
public interface FtpMappr {

    @Select("select * from k_ftp")
    List<Ftp> getFtpList();

    @Select("select * from k_ftp where id=#{id}")
    Ftp getFtpById(@Param("id") String id);

    @Insert("insert into k_ftp(host,username,password,port,charset,skip_dir,file_filter,dir,system_id,file_type,impl_type,file_name_regular) values(#{host},#{username},#{password},#{port},#{charset},#{skipDir},#{fileFilter},#{dir},#{systemId},#{fileType},#{implType},#{fileNameRegular})")
    int addFtp(Ftp ftp);

    int addFtpList(List<Ftp> ftpList);

    @Update("update k_ftp set host=#{host},username=#{username},password=#{password},port=#{port},charset=#{charset},skip_dir=#{skipDir},file_filter=#{fileFilter},dir=#{dir},system_id=#{systemId},file_type=#{fileType},impl_type=#{implType},file_name_regular=#{fileNameRegular} where id=#{id}")
    int updateFtp(Ftp ftp);

    @Delete("delete k_ftp where id=#{id}")
    int deleteFtp(String id);
}
