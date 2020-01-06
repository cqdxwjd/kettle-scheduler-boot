package org.kettle.scheduler.system.api.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 12:13
 */
@Data
public class System {

    private String id;

    @ExcelProperty("系统名称")
    private String systemName;

    @ExcelProperty("标签ID")
    private String tagId;

    @ExcelProperty("数据来源")
    private String datasource;

    @ExcelProperty("备注")
    private String remark;

    private MviewTag mviewTag;
}
