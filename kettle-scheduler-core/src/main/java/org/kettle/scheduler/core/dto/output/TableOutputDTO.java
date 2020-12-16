package org.kettle.scheduler.core.dto.output;


import lombok.Data;
import org.kettle.scheduler.core.dto.StepInterface;
import org.kettle.scheduler.core.dto.common.DatabaseMetaDTO;

@Data
public class TableOutputDTO implements StepInterface {

    //唯一ID
    private String id;

    //表输出
    private String name;

    //转换数据库列表
    private DatabaseMetaDTO databaseMeta;

    //输出表
    private String tableName;

    //提交记录数量
    private String commitSize;

    //裁剪表
    private boolean truncateTable;
}
