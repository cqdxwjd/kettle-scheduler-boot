package org.kettle.scheduler.core.dto;

import lombok.Data;
import org.kettle.scheduler.core.dto.common.DatabaseMetaDTO;

import java.util.List;

/**
 * 转换步骤实体类
 *
 * @author chenzhao
 */
@Data
public class SetpMetaDTO {

    //步骤库
    private List<DatabaseMetaDTO> databaseMetaList;

    //转换数据库列表
    private DatabaseMetaDTO databaseMeta;

    //步骤命令
    private String sql;


}
