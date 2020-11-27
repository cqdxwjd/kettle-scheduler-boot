package org.kettle.scheduler.core.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 转换步骤实体类
 *
 * @author chenzhao
 */
@Data
public class TransSetpDTO {

    //转换ID
    private String setpId;

    //步骤名称
    private String setpName;

    //步骤类型，后续替换为枚举类
    private String setpType;

    //步骤属性
    private SetpMetaDTO setpMeta;

    //步骤变更日期
    private Date setpChangedDate;

}
