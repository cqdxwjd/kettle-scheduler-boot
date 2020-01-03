package org.kettle.scheduler.system.biz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.kettle.scheduler.system.biz.entity.FtpFile;
import org.springframework.security.core.parameters.P;

/**
 * 描述:
 * FTP读取到的文件
 *
 * @author leo
 * @create 2020-01-01 17:57
 */

@Mapper
public interface FtpFileMapper {

    @Insert("insert into k_ftp_file(file_name,file_path,file_size,file_time,status,batch_no)values(#{fileName},#{filePath},#{fileSize},#{fileTime},'0',#{batchNo})")
    int addFtpFile(FtpFile ftpFile);

    @Select("select * from k_ftp_file where batch_no=#{batchNo} and file_name=#{fileName}")
    FtpFile getFtpFileByBatchNoAndFileName(@Param("batchNo") String batchNo, @Param("fileName") String fileName);
}
